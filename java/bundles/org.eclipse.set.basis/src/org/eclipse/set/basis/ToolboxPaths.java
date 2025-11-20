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
	 * Export file extensions
	 */
	public enum ExportPathExtension {
		/**
		 * The table model extenesion
		 */
		TABLE_MODEL_EXPORT_EXTENSION(".tm"), //$NON-NLS-1$
		/**
		 * The pdf extension
		 */
		TABLE_PDF_EXPORT_EXTENSION(".pdf"), //$NON-NLS-1$
		/**
		 * The xlsx extension
		 */
		TABLE_XLSX_EXPORT_EXTENSION(".xlsx"), //$NON-NLS-1$

		/**
		 * The csv extension
		 */
		TABLE_CSV_EXPORT_EXTENSION(".csv"); //$NON-NLS-1$

		/**
		 * extension value
		 */
		public final String value;

		private ExportPathExtension(final String value) {
			this.value = value;
		}
	}

	/**
	 * @param shortcut
	 *            the table shortcut
	 * @param base
	 *            the base path
	 * @param exportType
	 *            the export type
	 * @param pattern
	 *            the export path pattern
	 * 
	 * @return the path to the table xlsx export
	 */
	Path getTableExportPath(String shortcut, Path base, ExportType exportType,
			ExportPathExtension pattern);
}
