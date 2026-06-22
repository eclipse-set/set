/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview;

import static org.eclipse.set.basis.constants.ToolboxConstants.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.table.internal.TableServiceUtils;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.services.table.TableStatus;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent.ControlAreaValue;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
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

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

/**
 * Overview of all tables and their errors
 * 
 * @author Peters
 *
 */
public class TableOverviewPart extends BasePart {

	private record TableSectionControl(Label label, Text text, Button button) {

	}

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

	private TableSectionControl missingTablesControl;
	private TableSectionControl containErrorTablesControl;
	private TableSectionControl nonTransformableTablesControl;
	private TableSectionControl containsStatesChangeTablesControl;
	private TableSectionControl containsPlanChangeTablesControl;
	private TableSectionControl emptyTablesControl;
	private Label completenessHint;

	private TableErrorTableView tableErrorTableView;

	private final EventHandler tableErrorsChangeEventHandler = event -> onTableErrorsChange();
	private ToolboxEventHandler<SelectedControlAreaChangedEvent> selectionControlAreaHandler;
	private EventHandler comparePlaningLoadedHandler;
	private boolean ignoreChangeEvent = false;
	private Set<String> controlAreaIds = new HashSet<>();
	private TableType tableType = null;

	@Override
	public TableType getTableType() {
		return tableType;
	}

	@PostConstruct
	void postConstruct() {
		selectionControlAreaHandler = new DefaultToolboxEventHandler<>() {
			@Override
			public void accept(final SelectedControlAreaChangedEvent t) {
				tableType = t.getTableType();
				controlAreaIds = t.getControlAreas()
						.stream()
						.map(ControlAreaValue::areaId)
						.collect(Collectors.toSet());
				// When all table already transformation, that mean the
				// calculate all table button is disable, therefore need to
				// reload the
				// tables here for update table status
				if (getMissingTables().isEmpty()) {
					final Set<TableInfo> needToTransformTable = getTableToReTransform();
					calculateAllMissingTablesEvent(
							monitor -> tableService.transformTables(monitor,
									needToTransformTable, t.getTableType(),
									controlAreaIds));
				}
				update();
			}
		};

		ToolboxEvents.subscribe(getBroker(),
				SelectedControlAreaChangedEvent.class,
				selectionControlAreaHandler);

		comparePlaningLoadedHandler = event -> {
			// When all table already transformation, that mean the calculate
			// all table button is disable, therefore need to reload the tables
			// here for update table status
			if (getMissingTables().isEmpty()) {
				final Set<TableInfo> needToTransformTable = getTableToReTransform();
				calculateAllMissingTablesEvent(
						monitor -> tableService.transformTables(monitor,
								needToTransformTable, getTableType(),
								controlAreaIds));
			}
			update();
		};
		getBroker().subscribe(Events.COMPARE_MODEL_LOADED,
				comparePlaningLoadedHandler);
	}

	@Override
	protected void createView(final Composite parent) {
		// initialize table type
		tableType = getModelSession().getTableType();

		controlAreaIds = getModelSession().getSelectedControlAreas()
				.stream()
				.map(Pair::getSecond)
				.collect(Collectors.toSet());

		completenessHint = new Label(parent, SWT.NONE);
		completenessHint.setText(messages.TableOverviewPart_CompletenessHint);
		final Color red = new Color(parent.getDisplay(), 255, 0, 0);
		completenessHint.addDisposeListener(e -> red.dispose());
		completenessHint.setForeground(red);

		final Group section = new Group(parent, SWT.SHADOW_ETCHED_IN);
		section.setText(messages.TableOverviewPart_TableSectionHeader);
		section.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		section.setLayout(new GridLayout(3, false));

		missingTablesControl = createSectionControl(section,
				messages.TableOverviewPart_MissingTablesDesc,
				messages.TableOverviewPart_CalculateMissing,
				() -> calculateAllMissingTablesEvent(
						this::calculateAllMissingTables));
		containErrorTablesControl = createSectionControl(section,
				messages.TableOverviewPart_WithErrorsDesc,
				messages.TableOverviewPart_OpenAllWithErrors,
				() -> openAllRelevantTable(TableStatus::isContainsErrors));

		containsStatesChangeTablesControl = createSectionControl(section,
				messages.TableOverviewPart_WithStateChanges,
				messages.TableOverviewPart_OpenAllWithErrors,
				() -> openAllRelevantTable(
						TableStatus::isContainsStateChanged));

		containsPlanChangeTablesControl = createSectionControl(section,
				messages.TableOverviewPart_WithPlanChanges,
				messages.TableOverviewPart_OpenAllWithErrors,
				() -> openAllRelevantTable(TableStatus::isContainsPlanChanged));

		emptyTablesControl = createSectionControl(section,
				messages.TableOverviewPart_EmptyTable, null, null);
		nonTransformableTablesControl = createSectionControl(section,
				messages.TableOverviewPart_NonTransformableTable, null, null);

		// Create table problem table view
		tableErrorTableView = new TableErrorTableView(messages, this,
				enumTranslationService, tableMenuService);
		tableErrorTableView.create(parent);

		getBroker().subscribe(Events.TABLEERROR_CHANGED,
				tableErrorsChangeEventHandler);

		update();
	}

	private static TableSectionControl createSectionControl(final Group section,
			final String labelText, final String buttonText,
			final Runnable buttonAction) {
		final Label label = new Label(section, SWT.NONE);
		label.setText(labelText);

		final Text text = new Text(section, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.setEnabled(false);
		if (buttonText == null && buttonAction == null) {
			final Composite space = new Composite(section, SWT.NONE);
			space.setLayout(new GridLayout());
			return new TableSectionControl(label, text, null);

		}
		final Button button = new Button(section, SWT.NONE);
		button.setText(buttonText);
		if (buttonAction != null) {
			button.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(final SelectionEvent e) {
					buttonAction.run();
				}

				@Override
				public void widgetSelected(final SelectionEvent e) {
					widgetDefaultSelected(e);
				}
			});
		}

		return new TableSectionControl(label, text, button);
	}

	private void onTableErrorsChange() {
		if (!ignoreChangeEvent) {
			update();
		}
	}

	private void calculateAllMissingTablesEvent(
			final Consumer<IProgressMonitor> calculateEvent) {
		ignoreChangeEvent = true;
		try {
			getDialogService().showProgress(getToolboxShell(),
					calculateEvent::accept);
		} catch (InvocationTargetException | InterruptedException e) {
			getDialogService().error(getToolboxShell(), e);
		}
		ignoreChangeEvent = false;
		update();
	}

	private void calculateAllMissingTables(final IProgressMonitor monitor) {
		TableServiceUtils.calculateAllMissingTables(tableService,
				getModelSession(), controlAreaIds, getTableCategory(), monitor,
				messages);
	}

	private Map<TableInfo, Collection<TableError>> getTableErrors() {
		return tableService.getTableErrors(getModelSession(), controlAreaIds,
				getTableCategory());
	}

	private void openAllRelevantTable(
			final Predicate<TableStatus> tableWithStatus) {
		final Map<TableInfo, TableStatus> tablesStatus = tableService
				.getTablesStatus(getTableCategory());
		tablesStatus.forEach((k, v) -> {
			final String tablePartIdPrefix = switch (getTableCategory()) {
				case ESTW -> ESTW_TABLE_PART_ID_PREFIX;
				case ETCS -> ETCS_TABLE_PART_ID_PREFIX;
				case ESTW_SUPPLEMENT -> ESTW_SUPPLEMENT_PART_ID_PREFIX;
				default -> throw new IllegalArgumentException(
						"Unexpected value: " + getTableCategory()); //$NON-NLS-1$
			};

			if (tableWithStatus.test(v)) {
				toolboxPartService.showPart(String.format("%s.%s", //$NON-NLS-1$
						tablePartIdPrefix, k.shortcut()));
			}
		});
	}

	private Pt1TableCategory getTableCategory() {
		final String elementId = getToolboxPart().getElementId();
		if (elementId.startsWith(ESTW_TABLE_PART_ID_PREFIX)) {
			return Pt1TableCategory.ESTW;
		} else if (elementId.startsWith(ETCS_TABLE_PART_ID_PREFIX)) {
			return Pt1TableCategory.ETCS;
		} else if (elementId.startsWith(ESTW_SUPPLEMENT_PART_ID_PREFIX)) {
			return Pt1TableCategory.ESTW_SUPPLEMENT;
		}
		throw new IllegalArgumentException();
	}

	private void update() {
		final Collection<TableInfo> missingTables = getMissingTables();
		if (!ToolboxConfiguration.isDebugMode()) {
			completenessHint.setVisible(!missingTables.isEmpty());
			missingTablesControl.text()
					.setText(tableList2DisplayString(missingTables));
			missingTablesControl.button.setEnabled(!missingTables.isEmpty());
		} else {
			missingTablesControl.text()
					.setText(messages.TableOverviewPart_DebugModeHint);
			completenessHint.setVisible(false);
		}

		setControlText(containErrorTablesControl,
				TableStatus::isContainsErrors);
		setControlText(containsStatesChangeTablesControl,
				TableStatus::isContainsStateChanged);
		setControlText(containsPlanChangeTablesControl,
				TableStatus::isContainsPlanChanged);
		setControlText(emptyTablesControl, TableStatus::isEmpty);
		setControlText(nonTransformableTablesControl,
				TableStatus::isNonTransformable);

		final ArrayList<TableError> allErrors = new ArrayList<>();
		getTableErrors().values().forEach(allErrors::addAll);
		tableErrorTableView.updateView(allErrors);
	}

	private void setControlText(final TableSectionControl control,
			final Predicate<TableStatus> tableWithStatus) {
		final Map<TableInfo, TableStatus> tablesStatus = tableService
				.getTablesStatus(getTableCategory());
		final Set<TableInfo> relevantTable = getTablesWithStatus(tablesStatus,
				tableWithStatus);
		control.text().setText(tableList2DisplayString(relevantTable));
		if (control.button() != null) {
			control.button().setEnabled(!relevantTable.isEmpty());
		}
	}

	private static Set<TableInfo> getTablesWithStatus(
			final Map<TableInfo, TableStatus> tablesStatus,
			final Predicate<TableStatus> predicate) {
		return tablesStatus.entrySet()
				.stream()
				.filter(entry -> predicate.test(entry.getValue()))
				.map(Entry::getKey)
				.collect(Collectors.toSet());
	}

	private Collection<TableInfo> getMissingTables() {
		return TableServiceUtils.getMissingTables(tableService,
				getModelSession(), controlAreaIds, getTableCategory());
	}

	private String tableList2DisplayString(final Collection<TableInfo> tables) {
		if (tables.isEmpty()) {
			return messages.TableOverviewPart_EmptyListText;
		}
		final List<String> shortNames = new ArrayList<>(tables.stream()
				.map(tableInfo -> tableService.getTableNameInfo(tableInfo)
						.getShortName())
				.toList());
		Collections.sort(shortNames);
		return shortNames.stream().collect(Collectors.joining(", ")); //$NON-NLS-1$
	}

	@PreDestroy
	private void unsubscribe() {
		broker.unsubscribe(tableErrorsChangeEventHandler);
		broker.unsubscribe(comparePlaningLoadedHandler);
		ToolboxEvents.unsubscribe(broker, selectionControlAreaHandler);
	}

	private Set<TableInfo> getTableToReTransform() {
		final List<String> activeTablePart = getActiveTablePart();
		return tableService.getAvailableTables()
				.stream()
				.filter(info -> info.category() == getTableCategory())
				.filter(info -> activeTablePart.stream()
						.noneMatch(
								partId -> partId.equals(info.getTablePartId())))
				.collect(Collectors.toSet());
	}

	private List<String> getActiveTablePart() {
		final String tablePartPrefix = getTableCategory().getTablePartPrefix();
		return toolboxPartService.getOpenParts()
				.stream()
				.filter(part -> part.getElementId().startsWith(tablePartPrefix)
						&& !part.getElementId()
								.equals(getToolboxPart().getElementId()))
				.map(MPart::getElementId)
				.toList();
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
