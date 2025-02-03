/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.exportservice;

import java.nio.file.Path;
import java.util.LinkedList;
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
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.services.export.TableExport.ExportFormat;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link ExportService} via delegation to registered
 * {@link TableExport}s.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true)
public class ExportServiceImpl implements ExportService {

	private static final Logger logger = LoggerFactory
			.getLogger(ExportServiceImpl.class);

	private final List<TableExport> builders = new LinkedList<>();

	/**
	 * @param builder
	 *            the new builder
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addTableExportBuilder(final TableExport builder) {
		builders.add(builder);
	}

	@Override
	public void exportPdf(final Map<TableType, Table> tables,
			final ExportType exportType, final Titlebox titlebox,
			final FreeFieldInfo freeFieldInfo, final String shortcut,
			final String outputDir, final ToolboxPaths toolboxPaths,
			final TableType tableType,
			final OverwriteHandling overwriteHandling,
			final Consumer<Exception> errorHandler) {
		if (builders.isEmpty()) {
			logger.warn(
					"There are no builders registered at the export service."); //$NON-NLS-1$
		}
		List.of(ExportFormat.PDF, ExportFormat.EXCEL).forEach(format -> {
			final TableExport builder = getBuilder(format, shortcut);
			if (builder != null) {
				try {
					builder.export(tables, exportType, titlebox, freeFieldInfo,
							shortcut, outputDir, toolboxPaths, tableType,
							overwriteHandling);
				} catch (final Exception e) {
					errorHandler.accept(e);
				}
			}
		});

	}

	private TableExport getBuilder(final ExportFormat format,
			final String shortcut) {
		final List<TableExport> relevantBuilder = builders.stream()
				.filter(builder -> builder.getExportFormat() == format)
				.toList();
		if (relevantBuilder.isEmpty()) {
			return null;
		}
		final TableExport buildForTable = relevantBuilder.stream()
				.filter(builder -> {
					try {
						return builder.getTableShortcut()
								.equalsIgnoreCase(shortcut);
					} catch (final Exception e) {
						return false;
					}
				})
				.findFirst()
				.orElse(null);

		return buildForTable == null ? relevantBuilder.getFirst()
				: buildForTable;
	}

	@Override
	public void exportTitleboxImage(final Titlebox titlebox,
			final Path imagePath, final OverwriteHandling overwriteHandling,
			final Consumer<Exception> errorHandler) {
		for (final TableExport builder : builders) {
			try {
				builder.exportTitleboxImage(titlebox, imagePath,
						overwriteHandling);
			} catch (final Exception e) {
				errorHandler.accept(e);
			}
		}
	}

	@Override
	public void exportTitleboxPdf(final Titlebox titlebox, final Path pdfPath,
			final OverwriteHandling overwriteHandling,
			final Consumer<Exception> errorHandler) {
		for (final TableExport builder : builders) {
			try {
				builder.exportTitleboxPdf(titlebox, pdfPath, overwriteHandling);
			} catch (final Exception e) {
				errorHandler.accept(e);
			}
		}
	}

	/**
	 * @param builder
	 *            the builder to be removed
	 */
	public void removeTableExportBuilder(final TableExport builder) {
		builders.remove(builder);
	}
}
