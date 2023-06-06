/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.session;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;

/**
 * Implementation of {@link ToolboxPaths} using the model session.
 * 
 * @author Schaefer
 */
public class ToolboxPathsImpl implements ToolboxPaths {

	private static final String TABLE_MODEL_EXPORT_PATTERN = "%s_%s.tm"; //$NON-NLS-1$
	private static final String TABLE_PDF_EXPORT_PATTERN = "%s_%s.pdf"; //$NON-NLS-1$
	private static final String TABLE_XLSX_EXPORT_PATTERN = "%s_%s.xlsx"; //$NON-NLS-1$

	private final ModelSession modelSession;

	/**
	 * @param modelSession
	 *            the model session
	 */
	public ToolboxPathsImpl(final ModelSession modelSession) {
		this.modelSession = modelSession;
	}

	@Override
	public Path getTableModel(final String shortcut, final Path base,
			final ExportType exportType) {
		return Paths.get(base.toString(), getTableModel(shortcut, exportType));
	}

	@Override
	public Path getTablePdfExport(final String shortcut, final Path base,
			final ExportType exportType) {
		return Paths.get(base.toString(),
				getTablePdfExport(shortcut, exportType));
	}

	@Override
	public Path getTableXlsxExport(final String shortcut, final Path base,
			final ExportType exportType) {
		return Paths.get(base.toString(),
				getTableXlsxExport(shortcut, exportType));
	}

	private String getModelBaseName(final ExportType exportType) {
		final Path derivedPath = PlanProSchnittstelleExtensions.getDerivedPath(
				modelSession.getPlanProSchnittstelle(), "xxx", "xxx", //$NON-NLS-1$ //$NON-NLS-2$
				exportType);
		return PathExtensions.getBaseFileName(derivedPath);
	}

	private String getTableModel(final String shortcut,
			final ExportType exportType) {
		return String.format(TABLE_MODEL_EXPORT_PATTERN,
				getModelBaseName(exportType), shortcut);
	}

	private String getTablePdfExport(final String shortcut,
			final ExportType exportType) {
		return String.format(TABLE_PDF_EXPORT_PATTERN,
				getModelBaseName(exportType), shortcut);
	}

	private String getTableXlsxExport(final String shortcut,
			final ExportType exportType) {
		return String.format(TABLE_XLSX_EXPORT_PATTERN,
				getModelBaseName(exportType), shortcut);
	}
}
