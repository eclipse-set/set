/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.nio.file.Path;

import org.eclipse.set.basis.constants.ExportType;

/**
 * Provides various toolbox file paths.
 * 
 * @author Schaefer
 */
public interface ToolboxPaths {

	/**
	 * @param shortcut
	 *            the table shortcut
	 * @param base
	 *            the base path
	 * @param exportType
	 *            the export type
	 * 
	 * @return the path to the table model export
	 */
	Path getTableModel(String shortcut, Path base, ExportType exportType);

	/**
	 * @param shortcut
	 *            the table shortcut
	 * @param base
	 *            the base path
	 * @param exportType
	 *            the export type
	 * 
	 * @return the path to the table pdf export
	 */
	Path getTablePdfExport(String shortcut, Path base, ExportType exportType);

	/**
	 * @param shortcut
	 *            the table shortcut
	 * @param base
	 *            the base path
	 * @param exportType
	 *            the export type
	 * 
	 * @return the path to the table xlsx export
	 */
	Path getTableXlsxExport(String shortcut, Path base, ExportType exportType);
}
