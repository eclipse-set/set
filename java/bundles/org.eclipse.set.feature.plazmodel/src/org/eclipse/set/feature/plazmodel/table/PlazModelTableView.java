/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.table;

import java.nio.file.Path;
import java.util.Map;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.set.basis.ToolboxPaths.ExportPathExtension;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.plazmodel.Messages;
import org.eclipse.set.model.plazmodel.PlazReport;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.set.utils.table.tree.AbstractTreeLayerTable;
import org.eclipse.set.utils.xml.XMLNodeFinder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * View for the validation table
 * 
 * @author Stuecker
 *
 */
public class PlazModelTableView extends AbstractTreeLayerTable {
	@SuppressWarnings("nls")
	static final String HEADER_PATTERN = """
			PlaZ Modell-Prüfung
			Datei: %s
			Prüfungszeit: %s
			Werkzeugkofferversion: %s


			"Lfd. Nr.";"Schweregrad";"Problemart";"Zeilennummer";"Objektart";"Objekbezeichnung";"Attribut/-gruppe";"Bereich";"Zustand";"Meldung"
			""";

	private final Messages messages;
	private final BasePart part;
	private final EnumTranslationService enumTranslationService;
	private NatTable natTable;

	private final TableMenuService tableMenuService;
	private final XMLNodeFinder xmlNodeFinder;

	/**
	 * @param part
	 *            The source part used for events
	 * @param messages
	 *            The messages
	 * @param tableMenuService
	 *            The table menu service
	 * @param enumTranslationService
	 *            the enum translation service
	 */
	public PlazModelTableView(final BasePart part, final Messages messages,
			final TableMenuService tableMenuService,
			final EnumTranslationService enumTranslationService) {
		this.part = part;
		this.messages = messages;
		this.tableMenuService = tableMenuService;
		this.enumTranslationService = enumTranslationService;
		final ToolboxFile toolboxFile = part.getModelSession().getToolboxFile();
		this.xmlNodeFinder = new XMLNodeFinder();
		xmlNodeFinder.read(toolboxFile, toolboxFile.getModelPath());
	}

	/**
	 * Creates the table view
	 * 
	 * @param parent
	 *            the parent composite
	 * @param validationReport
	 *            the validation report to show
	 * @return the nattable control
	 */
	@SuppressWarnings("boxing")
	public Control create(final Composite parent,
			final PlazReport validationReport) {
		final PlazModelTableTransformationService service = new PlazModelTableTransformationService(
				messages, enumTranslationService);

		final Table table = service.transform(validationReport);
		this.createTableBodyData(table,
				rowIndex -> validationReport.getEntries()
						.get(rowIndex - 1)
						.getLineNumber());
		tableMenuService.addMenuItem(createJumpToTextViewMenuItem(part));
		tableMenuService.addMenuItem(createJumpToSiteplanMenuItem());
		natTable = createTable(parent, table);
		return natTable;
	}

	/**
	 * Updates the table view
	 * 
	 * @param report
	 *            the new report
	 */
	public void updateView(final PlazReport report) {
		if (natTable != null) {
			final PlazModelTableTransformationService service = new PlazModelTableTransformationService(
					messages, enumTranslationService);
			bodyDataProvider.refresh(service.transform(report));
			natTable.refresh();
		}
	}

	@Override
	protected TableMenuService getTableMenuService() {
		return tableMenuService;
	}

	@Override
	protected XMLNodeFinder getXMLNodeFinder() {
		return xmlNodeFinder;
	}

	@Override
	protected String getCSVHeaderPattern() {
		return HEADER_PATTERN;
	}

	@Override
	public void exportCsv() {
		final Path location = part.getModelSession().getToolboxFile().getPath();
		final String exportFileName = part.getModelSession()
				.getToolboxPaths()
				.getTableExportPath(messages.PlazModellPart_ExportCsvFileName,
						location, ExportType.PLANNING_RECORDS,
						ExportPathExtension.TABLE_CSV_EXPORT_EXTENSION)
				.getFileName()
				.toString();
		exportCsv(part.getToolboxShell(), part.getDialogService(),
				messages.PlazModellPart_ExportTitleMsg, exportFileName);
	}

	@Override
	protected Map<Integer, Object> getDefaultFilterValue() {
		return Map.of(Integer.valueOf(2), "-GUID-Sortierung"); //$NON-NLS-1$
	}

	/**
	 * IMPROVE: when reimplementation the filter to like excel then the check
	 * box here can be removed
	 * 
	 * @param parent
	 *            the parent composite
	 */
	public void createActiveDefaultFilterCheckBox(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		// Empty
		final Composite space = new Composite(parent, SWT.NONE);
		space.setLayout(new GridLayout());
		composite.setLayout(new GridLayout(2, false));
		final Button checkDefaultFilterButton = new Button(composite,
				SWT.CHECK);
		checkDefaultFilterButton.setSelection(true);
		final Label checkDefaultFilterLabel = new Label(composite, SWT.NONE);
		checkDefaultFilterLabel
				.setText(messages.PlazModellPart_ActiveDefaultFilterCheckbox);
		checkDefaultFilterButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				setDefaultFilterState(checkDefaultFilterButton.getSelection());
			}
		});
	}
}
