/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.tm;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.utils.ToolboxConfiguration;

/**
 * {@link TableExport} implementation for table model export.
 * 
 * @author Schaefer
 * 
 * @usage development tests
 */
@Component(immediate = true)
public class TableModelExport implements TableExport {

	@Override
	public void export(final Map<TableType, Table> tables,
			final ExportType exportType, final Titlebox titlebox,
			final FreeFieldInfo freeFieldInfo, final String shortcut,
			final String outputDir, final ToolboxPaths toolboxPaths,
			final OverwriteHandling overwriteHandling)
			throws FileExportException {
		if (!ToolboxConfiguration.isDevelopmentMode()) {
			return;
		}
		final Path filepath = toolboxPaths.getTableModel(shortcut,
				Paths.get(outputDir), exportType);
		final Table finalTable = tables.get(TableType.FINAL);
		final Table singleTable = tables.get(TableType.SINGLE);
		try {
			TableExtensions.save(finalTable != null ? finalTable : singleTable,
					filepath);
		} catch (final IOException e) {
			throw new FileExportException(filepath, e);
		}
	}

	@Override
	public void exportTitleboxImage(final Titlebox titlebox,
			final Path imagePath, final OverwriteHandling overwriteHandling)
			throws Exception {
		// do nothing
	}

	@Override
	public void exportTitleboxPdf(final Titlebox titlebox, final Path pdfPath,
			final OverwriteHandling overwriteHandling) throws Exception {
		// do nothing
	}
}
