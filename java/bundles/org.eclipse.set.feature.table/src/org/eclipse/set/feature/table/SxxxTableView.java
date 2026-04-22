/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.table.internal.TableServiceUtils;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Specify handling for SxxxTableView
 */
public class SxxxTableView extends ToolboxTableView {
	private Composite calculateMissingTablesPanel;
	private EventHandler reloadWorkNotesTable;

	@Override
	@PostConstruct
	protected void postConstruct() {
		super.postConstruct();
		reloadWorkNotesTable = event -> {
			if (!event.getTopic()
					.equalsIgnoreCase(Events.RELOAD_WORKNOTES_TABLE)) {
				return;
			}
			updateModel(getToolboxPart(), transformToTableModel());
			updateCalculateMissingTablesPanel();
			natTable.refresh();
		};
		getBroker().subscribe(Events.RELOAD_WORKNOTES_TABLE,
				reloadWorkNotesTable);
	}

	@Override
	@PreDestroy
	protected void preDestroy() {
		super.preDestroy();
		getBroker().unsubscribe(reloadWorkNotesTable);
	}

	@Override
	protected void createView(final Composite parent) {
		super.createView(parent);
		addCalculateMissingTablesPanel(parent);
		// Move the panel to top position
		calculateMissingTablesPanel.moveAbove(parent.getChildren()[0]);
	}

	@Override
	protected void comparePlaningLoadedHandler(final Event event) {
		super.comparePlaningLoadedHandler(event);
		if (tableInfo != null && tableInfo.shortcut()
				.equalsIgnoreCase(ToolboxConstants.WORKNOTES_TABLE_SHORTCUT)
				|| getToolboxPart().getElementId()
						.substring(
								getToolboxPart().getElementId().lastIndexOf(".") //$NON-NLS-1$
										+ 1)
						.equalsIgnoreCase(
								ToolboxConstants.WORKNOTES_TABLE_SHORTCUT)) {
			final IModelSession compareSession = getSessionService()
					.getLoadedSession(ToolboxFileRole.COMPARE_PLANNING);
			final Collection<TableInfo> mainSessionMissingTables = getMissingTables();
			final Collection<TableInfo> compareSessionMissingTables = TableServiceUtils
					.getMissingTables(tableService, compareSession,
							controlAreaIds);
			// All tables at both session are already loaded
			if (mainSessionMissingTables.isEmpty()
					&& compareSessionMissingTables.isEmpty()) {
				return;
			}

			if (mainSessionMissingTables.isEmpty()) {
				transformComparePlanTables(compareSession,
						new HashSet<>(compareSessionMissingTables));
				return;
			}

			// Find out, which table in main session is already transformed,
			// that by compare session still untransformed
			final Set<TableInfo> compareTableToTransform = compareSessionMissingTables
					.stream()
					.filter(compareTableInfo -> mainSessionMissingTables
							.stream()
							.noneMatch(
									mainTableInfo -> compareTableInfo.shortcut()
											.equalsIgnoreCase(
													mainTableInfo.shortcut())))
					.collect(Collectors.toSet());
			if (!compareTableToTransform.isEmpty()) {
				transformComparePlanTables(compareSession,
						compareTableToTransform);
			}
		}
	}

	private void transformComparePlanTables(final IModelSession compareSession,
			final Set<TableInfo> tablesToTransform) {
		try {
			getDialogService().showProgress(getToolboxShell(),
					monitor -> tableService.transformTables(monitor,
							tablesToTransform,
							compareSession.isSingleState() ? TableType.SINGLE
									: TableType.DIFF,
							controlAreaIds));
		} catch (InvocationTargetException | InterruptedException e) {
			getDialogService().error(getToolboxShell(), e);
			Thread.currentThread().interrupt();
		}
	}

	private void addCalculateMissingTablesPanel(final Composite parent) {
		if (getMissingTables().isEmpty()) {
			return;
		}
		// custom panel
		final Composite panel = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(panel);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(panel);
		final Label label = new Label(panel, SWT.LEFT);
		GridDataFactory.fillDefaults()
				.align(SWT.BEGINNING, SWT.CENTER)
				.grab(true, false)
				.applyTo(label);
		label.setText(messages.ToolboxTableView_TableIncompleteHint);
		final Button button = new Button(panel, SWT.None);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.FILL).applyTo(button);
		button.setText(messages.ToolboxTableView_CalculateTables);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				calculateAllMissingTablesEvent();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});

		panel.setBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));

		calculateMissingTablesPanel = panel;
		updateCalculateMissingTablesPanel();
	}

	private void updateCalculateMissingTablesPanel() {
		if (calculateMissingTablesPanel == null) {
			return;
		}
		if (getMissingTables().isEmpty()) {
			final Composite parent = calculateMissingTablesPanel.getParent();
			calculateMissingTablesPanel.dispose();
			parent.layout(true, true);
			parent.update();
			calculateMissingTablesPanel = null;
		}
	}

	private Collection<TableInfo> getMissingTables() {
		return TableServiceUtils.getMissingTables(tableService,
				getModelSession(), controlAreaIds);
	}

	private void calculateAllMissingTables(final IProgressMonitor monitor) {
		TableServiceUtils.calculateAllMissingTables(tableService,
				getModelSession(), controlAreaIds, monitor, messages);
	}

	private void calculateAllMissingTablesEvent() {
		try {
			getDialogService().showProgress(getToolboxShell(),
					this::calculateAllMissingTables);
		} catch (InvocationTargetException | InterruptedException e) {
			getDialogService().error(getToolboxShell(), e);
		}
		updateCalculateMissingTablesPanel();
	}

}
