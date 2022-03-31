/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.nattable.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Function;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.export.csv.CsvExporter;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;

/**
 * CSV Export optimized for exporting reference tables.
 * 
 * @author Schaefer
 */
public class ReferenceTableExporter extends CsvExporter {

	private static final String CHARSET = "UTF-8"; //$NON-NLS-1$

	private static final String ROW_LABEL = "Row"; //$NON-NLS-1$

	private static String addQuotationMarks(final String exportDisplayValue) {
		return "\"" + exportDisplayValue + "\""; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String escapeQuotationMarks(
			final String exportDisplayValue) {
		return exportDisplayValue.replace("\"", "\"\""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String setRowLabel() {
		return ROW_LABEL;
	}

	private Function<OutputStream, Void> exportEnd;

	private boolean isFirstCell = true;

	/**
	 * Creates the exporter.
	 */
	public ReferenceTableExporter() {
		setCharset(CHARSET);
	}

	@Override
	public void exportCell(final OutputStream outputStream,
			final Object exportDisplayValue, final ILayerCell cell,
			final IConfigRegistry configRegistry) throws IOException {
		String newExportDisplayValue = (String) exportDisplayValue;

		// add row label in first cell
		if (isFirstCell) {
			newExportDisplayValue = setRowLabel();
			isFirstCell = false;
		}

		// escape quotation marks
		newExportDisplayValue = escapeQuotationMarks(newExportDisplayValue);

		// add quotation marks
		newExportDisplayValue = addQuotationMarks(newExportDisplayValue);

		// call super export cell
		super.exportCell(outputStream, newExportDisplayValue, cell,
				configRegistry);
	}

	@Override
	public void exportEnd(final OutputStream outputStream) throws IOException {
		super.exportEnd(outputStream);
		exportEnd.apply(outputStream);
	}

	/**
	 * @return the export end function
	 */
	public Function<OutputStream, Void> getExportEnd() {
		return exportEnd;
	}

	/**
	 * @param exportEnd
	 *            the export end function
	 */
	public void setExportEnd(final Function<OutputStream, Void> exportEnd) {
		this.exportEnd = exportEnd;
	}
}
