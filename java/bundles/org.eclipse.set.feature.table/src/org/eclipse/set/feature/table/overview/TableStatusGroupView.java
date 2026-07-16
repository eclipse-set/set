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
package org.eclipse.set.feature.table.overview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.services.table.TableStatus;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Group view for tables status
 * 
 * @author truong
 */
public class TableStatusGroupView {
	protected class TableSectionControl {
		Label label;
		Text text;
		Button openAllButton;
		Button exportAllButton;

		Button calculateMissingTableButton;

		public Button getCalculateMissingTableButton() {
			return calculateMissingTableButton;
		}

		private void setCalculateMissingTableButton(
				final Button calculateMissingTableButton) {
			this.calculateMissingTableButton = calculateMissingTableButton;
		}

		public Button getOpenAllButton() {
			return openAllButton;
		}

		private void setOpenAllButton(final Button button) {
			openAllButton = button;
		}

		public Button getExportAllButton() {
			return exportAllButton;
		}

		private void setExportAllButton(final Button button) {
			exportAllButton = button;
		}

		public Label getLabel() {
			return label;
		}

		public Text getText() {
			return text;
		}

		public TableSectionControl(final Label label, final Text text) {
			this.label = label;
			this.text = text;
		}

		public void addSpace(final Composite section) {
			final long buttonCount = Stream
					.of(calculateMissingTableButton, exportAllButton,
							openAllButton)
					.filter(Objects::nonNull)
					.count();
			for (long i = buttonCount; i < 2; i++) {
				final Composite space = new Composite(section, SWT.NONE);
				space.setLayout(new GridLayout());
			}
		}

	}

	Composite parent;
	Messages messages;

	private TableSectionControl missingTablesControl;
	private TableSectionControl containErrorTablesControl;
	private TableSectionControl nonTransformableTablesControl;
	private TableSectionControl containsStatesChangeTablesControl;
	private TableSectionControl containsPlanChangeTablesControl;
	private TableSectionControl emptyTablesControl;

	private TableSectionControl tableWithDataControl;
	private Label completenessHint;
	private final Pt1TableCategory tableCategory;
	private final TableService tableService;

	/**
	 * @param parent
	 *            the parent composite
	 * @param tableService
	 *            the {@link TableService}
	 * @param tableCategory
	 *            the table category
	 * @param messages
	 *            the {@link Messages}
	 */
	public TableStatusGroupView(final Composite parent,
			final TableService tableService,
			final Pt1TableCategory tableCategory, final Messages messages) {
		this.parent = parent;
		this.tableService = tableService;
		this.messages = messages;
		this.tableCategory = tableCategory;
	}

	/**
	 * Create table status view
	 */
	public void createView() {
		completenessHint = new Label(parent, SWT.NONE);
		completenessHint.setText(messages.TableOverviewPart_CompletenessHint);
		final Color red = new Color(parent.getDisplay(), 255, 0, 0);
		completenessHint.addDisposeListener(e -> red.dispose());
		completenessHint.setForeground(red);

		final Composite container = new Composite(parent, SWT.BORDER);
		container.setLayout(new FillLayout());
		container.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		final FormToolkit formToolkit = new FormToolkit(parent.getDisplay());
		final ExpandableComposite ec = formToolkit
				.createExpandableComposite(container,
						ExpandableComposite.TWISTIE
								| ExpandableComposite.TITLE_BAR
								| ExpandableComposite.EXPANDED);
		ec.setBackground(parent.getBackground());
		ec.setText(messages.TableOverviewPart_TableSectionHeader);

		final Composite section = new Composite(ec, SWT.NONE);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		section.setLayout(new GridLayout(4, false));
		ec.setClient(section);
		missingTablesControl = createSectionControl(section,
				messages.TableOverviewPart_MissingTablesDesc, true, false,
				false);
		containErrorTablesControl = createSectionControl(section,
				messages.TableOverviewPart_WithErrorsDesc, false, true, true);
		tableWithDataControl = createSectionControl(section,
				messages.TableOverviewPart_TablesWithData, false, true, true);

		containsStatesChangeTablesControl = createSectionControl(section,
				messages.TableOverviewPart_WithStateChanges, false, true, true);

		containsPlanChangeTablesControl = createSectionControl(section,
				messages.TableOverviewPart_WithPlanChanges, false, true, true);

		emptyTablesControl = createSectionControl(section,
				messages.TableOverviewPart_EmptyTable, false, false, false);
		nonTransformableTablesControl = createSectionControl(section,
				messages.TableOverviewPart_NonTransformableTable, false, false,
				false);
	}

	private TableSectionControl createSectionControl(final Composite section,
			final String labelText,
			final boolean withCalculateMissingTableButton,
			final boolean withOpenAllButton,
			final boolean withExportAllButton) {
		final Label label = new Label(section, SWT.NONE);
		label.setText(labelText);

		final Text text = new Text(section, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.setEnabled(false);
		final TableSectionControl tableSectionControl = new TableSectionControl(
				label, text);
		if (withCalculateMissingTableButton) {
			final Button calculateMissingTableButton = new Button(section,
					SWT.NONE);
			calculateMissingTableButton
					.setText(messages.TableOverviewPart_CalculateMissing);
			tableSectionControl.setCalculateMissingTableButton(
					calculateMissingTableButton);
		}
		if (withOpenAllButton) {
			final Button openAllButton = new Button(section, SWT.NONE);
			openAllButton.setText(messages.TableOverviewPart_OpenAllWithErrors);
			tableSectionControl.setOpenAllButton(openAllButton);
		}
		if (withExportAllButton) {
			final Button exportAllButton = new Button(section, SWT.NONE);
			exportAllButton.setText(messages.TableOverviewPart_Export_All);
			tableSectionControl.setExportAllButton(exportAllButton);
		}

		tableSectionControl.addSpace(section);
		return tableSectionControl;
	}

	/**
	 * @param control
	 *            the button
	 * @param getButtonFunc
	 *            function to get the button to set action
	 * @param action
	 *            the selection listener
	 */
	public static void addButtonAction(final TableSectionControl control,
			final Function<TableSectionControl, Button> getButtonFunc,
			final Runnable action) {
		final Button button = getButtonFunc.apply(control);
		if (action != null && button != null) {
			button.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(final SelectionEvent e) {
					Display.getCurrent().asyncExec(action);
				}

				@Override
				public void widgetSelected(final SelectionEvent e) {
					widgetDefaultSelected(e);
				}
			});
		}
	}

	/**
	 * @return the missing tables control
	 */
	public TableSectionControl getMissingTablesControl() {
		return missingTablesControl;
	}

	/**
	 * @return the contain error table control
	 */
	public TableSectionControl getContainErrorTablesControl() {
		return containErrorTablesControl;
	}

	/**
	 * @return the non transformable table control
	 */
	public TableSectionControl getNonTransformableTablesControl() {
		return nonTransformableTablesControl;
	}

	/**
	 * @return the contain state change control
	 */
	public TableSectionControl getContainsStatesChangeTablesControl() {
		return containsStatesChangeTablesControl;
	}

	/**
	 * @return the contains plan change control
	 */
	public TableSectionControl getContainsPlanChangeTablesControl() {
		return containsPlanChangeTablesControl;
	}

	/**
	 * @return the empty table control
	 */
	public TableSectionControl getEmptyTablesControl() {
		return emptyTablesControl;
	}

	/**
	 * @return the tables with data control
	 */
	public TableSectionControl getTableWithDatasControl() {
		return tableWithDataControl;
	}

	/**
	 * @return the completeness hint label
	 */
	public Label getCompletenessHint() {
		return completenessHint;
	}

	/**
	 * @param missingTables
	 *            the missing tables
	 * 
	 */
	public void updateControlText(final Collection<TableInfo> missingTables) {
		if (!ToolboxConfiguration.isDebugMode()) {
			completenessHint.setVisible(!missingTables.isEmpty());
			missingTablesControl.text
					.setText(tableList2DisplayString(missingTables));
			missingTablesControl.calculateMissingTableButton
					.setEnabled(!missingTables.isEmpty());
		} else {
			missingTablesControl.text
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
		setControlText(tableWithDataControl, status -> !status.isEmpty());
		setControlText(nonTransformableTablesControl,
				TableStatus::isNonTransformable);
	}

	private void setControlText(final TableSectionControl control,
			final Predicate<TableStatus> tableWithStatus) {
		final Map<TableInfo, TableStatus> tablesStatus = tableService
				.getTablesStatus(tableCategory);
		final Set<TableInfo> relevantTable = getTablesWithStatus(tablesStatus,
				tableWithStatus);
		control.getText().setText(tableList2DisplayString(relevantTable));
		if (control.getOpenAllButton() != null) {
			control.getOpenAllButton().setEnabled(!relevantTable.isEmpty());
		}
		if (control.getExportAllButton() != null) {
			control.getExportAllButton().setEnabled(!relevantTable.isEmpty());
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

	private String tableList2DisplayString(final Collection<TableInfo> tables) {
		if (tables.isEmpty()) {
			return messages.TableOverviewPart_EmptyListText;
		}
		final List<String> shortNames = new ArrayList<>(tables.stream()
				.map(tableInfo -> tableInfo.nameInfo().getShortName())
				.toList());
		Collections.sort(shortNames);
		return shortNames.stream().collect(Collectors.joining(", ")); //$NON-NLS-1$
	}
}
