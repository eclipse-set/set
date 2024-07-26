/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.abstracttableview;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Optional;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.export.ExportConfigAttributes;
import org.eclipse.nebula.widgets.nattable.export.command.ExportCommand;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.nattable.utils.ReferenceTableExporter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Shell;

/**
 * Csv export key action implementation
 *
 * @author Schneider
 */
public class CsvExportAction implements IKeyAction {
	private static final String DEFAULT_FILENAME = "csv_reference.csv"; //$NON-NLS-1$
	private final DialogService dialogService;

	/**
	 * @param dialogService
	 *            the dialog service
	 */
	public CsvExportAction(final DialogService dialogService) {
		this.dialogService = dialogService;
	}

	@Override
	public void run(final NatTable natTable, final KeyEvent event) {
		final ReferenceTableExporter exporter = new ReferenceTableExporter() {
			@Override
			public OutputStream getOutputStream(final Shell shell) {
				try {
					final Optional<Path> path = dialogService
							.saveFileDialog(shell,
									dialogService.getCsvFileFilters(),
									Services.getUserConfigurationService()
											.getLastExportPath(),
									DEFAULT_FILENAME);
					if (path.isEmpty()) {
						return null;
					}
					return new FileOutputStream(path.get().toString());
				} catch (final FileNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		};
		final Shell shell = natTable.getShell();
		exporter.setExportEnd(s -> {
			dialogService.reportExported(shell);
			return null;
		});
		natTable.getConfigRegistry().registerConfigAttribute(
				ExportConfigAttributes.EXPORTER, exporter);
		natTable.doCommand(
				new ExportCommand(natTable.getConfigRegistry(), shell));
	}
}
