/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.services.export;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.utils.ToolboxConfiguration;

/**
 * Support for exporting tables.
 * 
 * @author Schaefer
 */
public interface ExportService {

	/**
	 * Whether an implementation exports all, none or some of the provided
	 * tables depends on the functional context and requirements of the concrete
	 * export.
	 * 
	 * @param tables
	 *            the provided tables
	 * @param exportType
	 *            the export type
	 * @param titlebox
	 *            the titlebox
	 * @param freeFieldInfo
	 *            the free field info
	 * @param shortcut
	 *            the table shortcut (view id)
	 * @param outputDir
	 *            the output directory for the export
	 * @param toolboxPaths
	 *            the toolbox paths
	 * @param tableTye
	 *            the type of table
	 * @param overwriteHandling
	 *            what to do when overwriting files
	 * @param errorHandler
	 *            the error handler
	 */
	void exportPdf(Map<TableType, Table> tables, ExportType exportType,
			Titlebox titlebox, FreeFieldInfo freeFieldInfo, String shortcut,
			String outputDir, ToolboxPaths toolboxPaths, TableType tableTye,
			OverwriteHandling overwriteHandling,
			Consumer<Exception> errorHandler);

	/**
	 * Export the given titlebox as an image at the given path.
	 * 
	 * @param titlebox
	 *            the titlebox
	 * @param imagePath
	 *            the image path
	 * @param overwriteHandling
	 *            what to do when overwriting files
	 * @param errorHandler
	 *            the error handler
	 */
	void exportTitleboxImage(Titlebox titlebox, Path imagePath,
			OverwriteHandling overwriteHandling,
			Consumer<Exception> errorHandler);

	/**
	 * Export the given titlebox as an PDF at the given path.
	 * 
	 * @param titlebox
	 *            the titlebox
	 * @param pdfPath
	 *            the PDF path
	 * @param overwriteHandling
	 *            what to do when overwriting files
	 * @param errorHandler
	 *            the error handler
	 */
	void exportTitleboxPdf(Titlebox titlebox, Path pdfPath,
			OverwriteHandling overwriteHandling,
			Consumer<Exception> errorHandler);

	/**
	 * Export the siteplan as an PDF at the given path
	 * 
	 * @param imagesData
	 *            the siteplan as images
	 * @param titleBox
	 *            the {@link Titlebox}
	 * @param freeFieldInfo
	 *            the free field info
	 * @param ppm
	 *            pixel per physical meter in Openlayer at scale
	 *            {@link ToolboxConfiguration#getSiteplanExportScale()}
	 * @param outputDir
	 *            the output path
	 * @param toolboxPaths
	 *            the {@link ToolboxPaths}
	 * @param tableType
	 *            the {@link TableType}
	 * @param overwriteHandling
	 *            what to do when overwriting files
	 * @param errorHandler
	 *            the error handler
	 */
	void exportSiteplanPdf(List<BufferedImage> imagesData, Titlebox titleBox,
			FreeFieldInfo freeFieldInfo, double ppm, String outputDir,
			ToolboxPaths toolboxPaths, TableType tableType,
			OverwriteHandling overwriteHandling,
			Consumer<Exception> errorHandler);
}
