/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.exportservice;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.ToolboxPaths.ExportPathExtension;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.NotWritable;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToFreeFieldTransformation;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.services.export.TableExport.ExportFormat;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.swt.widgets.Shell;
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

	private record TableToExport(TableInfo tableInfo, Path pdfPath,
			Path excelPath) {
		private List<String> getExportFilesName() {
			return Stream.of(pdfPath, excelPath)
					.filter(Objects::nonNull)
					.map(p -> p.getFileName().toString())
					.toList();
		}

		private static TableToExport createInstance(final TableInfo tableInfo,
				final IModelSession modelSession, final ExportType exportType,
				final Path outdir, final List<ExportFormat> exportFormate) {
			Path pdfExportPath = null;
			if (exportFormate.contains(ExportFormat.PDF)) {
				pdfExportPath = modelSession.getToolboxPaths()
						.getTableExportPath(tableInfo.shortcut(), outdir,
								exportType,
								ExportPathExtension.TABLE_PDF_EXPORT_EXTENSION);
			}

			Path excelExportPath = null;
			if (exportFormate.contains(ExportFormat.EXCEL)) {
				excelExportPath = modelSession.getToolboxPaths()
						.getTableExportPath(tableInfo.shortcut(), outdir,
								exportType,
								ExportPathExtension.TABLE_XLSX_EXPORT_EXTENSION);
			}
			return new TableToExport(tableInfo, pdfExportPath, excelExportPath);
		}
	}

	@Reference
	SessionService sessionService;

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
	public void exportMultiTable(final ExportType exportType,
			final List<TableInfo> tableInfos, final IModelSession modelSession,
			final TableCompileService compileService,
			final DialogService dialogService, final TableType tableType,
			final Set<String> controlAreaIds,
			final List<ExportFormat> exportFormate, final String outputDir,
			final IProgressMonitor monitor, final Shell shell,
			final OverwriteHandling overwriteHandling,
			final Consumer<Exception> errorHandler) {
		if (builders.isEmpty()) {
			logger.warn(
					"There are no builders registered at the export service."); //$NON-NLS-1$
		}
		try {
			final Path outdir = Paths.get(outputDir);
			final List<TableToExport> tablesToExport = tableInfos.stream()
					.map(info -> TableToExport.createInstance(info,
							modelSession, exportType, outdir, exportFormate))
					.toList();
			final List<TableToExport> confirmOverwriteTables = filterConfirmOverwriteTable(
					tablesToExport, shell, dialogService, overwriteHandling,
					outputDir);
			confirmOverwriteTables.forEach(tableToExport -> {
				monitor.subTask(tableToExport.tableInfo()
						.nameInfo()
						.getFullDisplayName());
				final Map<TableType, Table> tables = compileService.compile(
						tableToExport.tableInfo(), modelSession,
						controlAreaIds);
				final PlanProToTitleboxTransformation planProToTitleboxTransformation = new PlanProToTitleboxTransformation(
						sessionService);
				final Titlebox titleBox = planProToTitleboxTransformation
						.transform(tableToExport.tableInfo().nameInfo(),
								guid -> getAttachmentPath(modelSession, guid));

				final PlanProToFreeFieldTransformation planProToFreeFieldTransformation = PlanProToFreeFieldTransformation
						.create(sessionService);
				final FreeFieldInfo freeField = planProToFreeFieldTransformation
						.transform();
				exportFormate.forEach(format -> {
					final TableExport builder = getBuilder(format,
							tableToExport.tableInfo().shortcut());
					final Path fileoutputPath = switch (format) {
						case PDF -> tableToExport.pdfPath();
						case EXCEL -> tableToExport.excelPath();
						case TABLE_MODEL -> null;
					};
					if (builder != null && fileoutputPath != null) {
						try {
							builder.export(tables, exportType, titleBox,
									freeField,
									tableToExport.tableInfo().shortcut(),
									tableType, fileoutputPath,
									OverwriteHandling.forCheckbox(true));
						} catch (final Exception e) {
							errorHandler.accept(e);
						}
					}
				});
			});
		} catch (final Exception e) {
			errorHandler.accept(e);
		}
	}

	private static Path getAttachmentPath(final IModelSession modelSession,
			final String guid) {
		try {
			return modelSession.getToolboxFile()
					.getMediaPath(Guid.create(guid));
		} catch (final UnsupportedOperationException e) {
			return null;
		}
	}

	private static List<TableToExport> filterConfirmOverwriteTable(
			final List<TableToExport> tablesToExport, final Shell shell,
			final DialogService dialogService,
			final OverwriteHandling overwriteHandling, final String outputDir)
			throws IOException {
		final List<String> exportFilesName = tablesToExport.stream()
				.flatMap(t -> t.getExportFilesName().stream())
				.toList();
		if (!alreadyExistAnyFile(exportFilesName, outputDir)) {
			return tablesToExport;
		}

		if (overwriteHandling != null) {
			return tablesToExport.stream()
					.filter(t -> Stream.of(t.excelPath(), t.pdfPath())
							.filter(Objects::nonNull)
							.allMatch(p -> {
								try {
									return overwriteHandling.test(p);
								} catch (final NotWritable e) {
									return false;
								}

							}))
					.toList();
		}
		final List<String> confirmedOverwirteFiles = dialogService
				.confirmOverwriteMultiFile(shell, exportFilesName);
		return tablesToExport.stream()
				.filter(table -> confirmedOverwirteFiles.stream()
						.anyMatch(file -> table.getExportFilesName()
								.contains(file)))
				.toList();
	}

	private static boolean alreadyExistAnyFile(final List<String> filesName,
			final String outputDir) throws IOException {
		final Path outDir = Path.of(outputDir);
		if (!Files.exists(outDir) || !Files.isDirectory(outDir)) {
			throw new IllegalArgumentException("outputDir should be Directory"); //$NON-NLS-1$
		}
		try (Stream<Path> filesPath = Files.walk(Path.of(outputDir))) {
			return filesPath.anyMatch(
					p -> filesName.contains(p.getFileName().toString()));
		}
	}

	@Override
	public void exportTable(final Map<TableType, Table> tables,
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

	@Override
	public void exportSiteplanPdf(final List<BufferedImage> imagesData,
			final Titlebox titleBox, final FreeFieldInfo freeFieldInfo,
			final double ppm, final String outputDir,
			final ToolboxPaths toolboxPaths, final TableType tableType,
			final OverwriteHandling overwriteHandling,
			final Consumer<Exception> errorHandler) {
		for (final TableExport builder : builders) {
			try {
				builder.exportSiteplanPdf(imagesData, titleBox, freeFieldInfo,
						ppm, outputDir, toolboxPaths, tableType,
						overwriteHandling);
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
