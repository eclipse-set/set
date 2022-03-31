/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.abstracttableview;

import java.io.OutputStream;
import java.util.function.Function;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.export.ExportConfigAttributes;
import org.eclipse.nebula.widgets.nattable.export.command.ExportCommand;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.set.nattable.utils.ReferenceTableExporter;

/**
 * Csv export key action implementation
 *
 * @author Schneider
 */
public class CsvExportAction implements IKeyAction {

	private final Function<OutputStream, Void> exportEnd;

	/**
	 * @param exportEnd
	 *            the export end function
	 */
	public CsvExportAction(final Function<OutputStream, Void> exportEnd) {
		this.exportEnd = exportEnd;
	}

	@Override
	public void run(final NatTable natTable, final KeyEvent event) {
		final ReferenceTableExporter exporter = new ReferenceTableExporter();
		final Shell shell = natTable.getShell();
		exporter.setExportEnd(exportEnd);
		natTable.getConfigRegistry().registerConfigAttribute(
				ExportConfigAttributes.EXPORTER, exporter);
		natTable.doCommand(
				new ExportCommand(natTable.getConfigRegistry(), shell));
	}
}
