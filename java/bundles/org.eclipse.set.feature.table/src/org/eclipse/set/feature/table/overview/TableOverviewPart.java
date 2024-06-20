/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.table.TableService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.feature.table.messages.MessagesWrapper;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.event.EventHandler;

import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

/**
 * Overview of all tables and their errors
 * 
 * @author Peters
 *
 */
public class TableOverviewPart extends BasePart {

	private static final String TABLE_PART_ID_PREFIX = "org.eclipse.set.feature.table."; //$NON-NLS-1$

	@Inject
	@Translation
	protected Messages messages;

	@Inject
	private EnumTranslationService enumTranslationService;

	@Inject
	private ToolboxPartService toolboxPartService;

	@Inject
	private IEventBroker broker;

	@Inject
	private TableService tableService;

	@Inject
	private TableMenuService tableMenuService;

	// IMPROVE:
	// Workaround for table services not being registered in TableService
	// when no table part has been opened yet
	@Inject
	MessagesWrapper wrapper;

	private Label completenessHint;
	private Text missingTablesText;
	private Button calculateMissing;
	private Text withErrorsText;
	private Button openAllWithErrors;

	private TableErrorTableView tableErrorTableView;

	private final EventHandler tableErrorsChangeEventHandler = event -> onTableErrorsChange();
	private boolean ignoreChangeEvent = false;

	@Override
	protected void createView(final Composite parent) {
		completenessHint = new Label(parent, SWT.NONE);
		completenessHint.setText(messages.TableOverviewPart_CompletenessHint);
		final Color red = new Color(parent.getDisplay(), 255, 0, 0);
		completenessHint.addDisposeListener(e -> red.dispose());
		completenessHint.setForeground(red);

		final Group section = new Group(parent, SWT.SHADOW_ETCHED_IN);
		section.setText(messages.TableOverviewPart_TableSectionHeader);
		section.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		section.setLayout(new GridLayout(3, false));

		final Label missingTablesDesc = new Label(section, SWT.NONE);
		missingTablesDesc.setText(messages.TableOverviewPart_MissingTablesDesc);

		missingTablesText = new Text(section, SWT.BORDER);
		missingTablesText
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		missingTablesText.setEnabled(false);

		calculateMissing = new Button(section, SWT.NONE);
		calculateMissing.setText(messages.TableOverviewPart_CalculateMissing);
		calculateMissing.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				calculateAllMissingTablesEvent();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				calculateAllMissingTablesEvent();
			}
		});

		final Label withErrorsDesc = new Label(section, SWT.NONE);
		withErrorsDesc.setText(messages.TableOverviewPart_WithErrorsDesc);

		withErrorsText = new Text(section, SWT.BORDER);
		withErrorsText
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		withErrorsText.setEnabled(false);

		openAllWithErrors = new Button(section, SWT.NONE);
		openAllWithErrors.setText(messages.TableOverviewPart_OpenAllWithErrors);
		openAllWithErrors.setEnabled(false);
		openAllWithErrors.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				openAllTablesWithErrors();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				openAllTablesWithErrors();
			}
		});

		// Create table problem table view
		tableErrorTableView = new TableErrorTableView(messages, this,
				getBroker(), toolboxPartService, enumTranslationService,
				tableMenuService);
		tableErrorTableView.create(parent);

		getBroker().subscribe(Events.TABLEERROR_CHANGED,
				tableErrorsChangeEventHandler);

		update();
	}

	private void onTableErrorsChange() {
		if (!ignoreChangeEvent) {
			update();
		}
	}

	private void calculateAllMissingTablesEvent() {
		ignoreChangeEvent = true;
		try {
			getDialogService().showProgress(getToolboxShell(),
					this::calculateAllMissingTables);
		} catch (InvocationTargetException | InterruptedException e) {
			getDialogService().error(getToolboxShell(), e);
		}
		ignoreChangeEvent = false;
		update();
	}

	private void calculateAllMissingTables(final IProgressMonitor monitor) {
		final Collection<String> missingTables = getMissingTables();
		monitor.beginTask(messages.TableOverviewPart_CalculateMissingTask,
				missingTables.size());
		final TableType tableType = getModelSession().isSingleState()
				? TableType.SINGLE
				: TableType.DIFF;

		for (final String table : missingTables) {
			final TableNameInfo info = tableService.getTableNameInfo(table);
			monitor.subTask(info.getFullDisplayName());

			tableService.transformToTable(table, tableType, getModelSession());
			monitor.worked(1);
		}
	}

	private void openAllTablesWithErrors() {
		final Collection<String> tablesWithErrors = getTablesContainingErrors();
		for (final String shortCut : tablesWithErrors) {
			final String partDescriptionId = TABLE_PART_ID_PREFIX + shortCut;
			toolboxPartService.showPart(partDescriptionId);
		}
	}

	private void update() {
		final Collection<String> missingTables = getMissingTables();

		if (!ToolboxConfiguration.isDebugMode()) {
			missingTablesText.setText(tableList2DisplayString(missingTables));
			completenessHint.setVisible(!missingTables.isEmpty());
			calculateMissing.setEnabled(!missingTables.isEmpty());
		} else {
			missingTablesText.setText(messages.TableOverviewPart_DebugModeHint);
			completenessHint.setVisible(false);
		}

		final Collection<String> tablesWithErrors = getTablesContainingErrors();

		withErrorsText.setText(tableList2DisplayString(tablesWithErrors));
		openAllWithErrors.setEnabled(!tablesWithErrors.isEmpty());

		final ArrayList<TableError> allErrors = new ArrayList<>();
		tableService.getTableErrors().values().forEach(allErrors::addAll);
		tableErrorTableView.updateView(allErrors);
	}

	private Collection<String> getMissingTables() {
		final Map<String, Collection<TableError>> computedErrors = tableService
				.getTableErrors();
		final Collection<String> allTableInfos = tableService
				.getAvailableTables();

		final ArrayList<String> missingTables = new ArrayList<>();
		missingTables.addAll(allTableInfos);
		if (!ToolboxConfiguration.isDebugMode()) {
			// in debug mode we want to be able to recompute the errors
			// that's why we mark all as missing
			missingTables.removeAll(computedErrors.keySet());
		}
		return missingTables;
	}

	private Collection<String> getTablesContainingErrors() {
		final Map<String, Collection<TableError>> computedErrors = tableService
				.getTableErrors();

		final ArrayList<String> tablesWithErrors = new ArrayList<>();
		for (final Entry<String, Collection<TableError>> entry : computedErrors
				.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				tablesWithErrors.add(entry.getKey());
			}
		}
		return tablesWithErrors;
	}

	private String tableList2DisplayString(final Collection<String> tables) {
		if (tables.isEmpty()) {
			return messages.TableOverviewPart_EmptyListText;
		}
		final List<String> shortNames = new ArrayList<>(
				tables.stream().map(shortCut -> tableService
						.getTableNameInfo(shortCut).getShortName()).toList());
		Collections.sort(shortNames);
		return shortNames.stream().collect(Collectors.joining(", ")); //$NON-NLS-1$
	}

	@PreDestroy
	private void unsubscribe() {
		broker.unsubscribe(tableErrorsChangeEventHandler);
	}

	/**
	 * Create the part.
	 */
	@Inject
	public TableOverviewPart() {
		super();
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		update();
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		update();
	}
}
