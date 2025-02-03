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

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.plazmodel.Messages;
import org.eclipse.set.model.plazmodel.PlazReport;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.set.utils.table.tree.AbstractTreeLayerTable;
import org.eclipse.set.utils.xml.XMLNodeFinder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * View for the validation table
 * 
 * @author Stuecker
 *
 */
public class PlazModelTableView extends AbstractTreeLayerTable {
	@SuppressWarnings("nls")
	static final String HEADER_PATTERN = """
			PlaZ-Modell-Prüfung
			Datei: %s
			Prüfungszeit: %s
			Werkzeugkofferversion: %s


			"Lfd. Nr.";"Schweregrad";"Problemart";"Zeilennummer";"Objektart";"Attribut/-gruppe";"Bereich";"Zustand";"Meldung"
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
		final String defaultFileName = String.format(
				messages.PlazModellPart_ExportCsvFilePattern,
				PathExtensions.getBaseFileName(location));
		exportCsv(part.getToolboxShell(), part.getDialogService(),
				messages.PlazModellPart_ExportTitleMsg, defaultFileName);
	}
}
