/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.services.fop;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

import org.apache.xmlgraphics.io.ResourceResolver;
import org.eclipse.set.basis.OverwriteHandling;
import org.xml.sax.SAXException;

/**
 * Provide FOP functionality.
 * 
 * @author Schaefer
 */
public interface FopService {

	/**
	 * The output format for the FOP process.
	 */
	public static enum OutputFormat {

		/**
		 * Image output
		 */
		IMAGE(org.apache.xmlgraphics.util.MimeConstants.MIME_PNG),

		/**
		 * PDF output
		 */
		PDF(org.apache.xmlgraphics.util.MimeConstants.MIME_PDF);

		private final String formatString;

		private OutputFormat(final String formatString) {
			this.formatString = formatString;
		}

		/**
		 * @return the format string
		 */
		public String getFormatString() {
			return formatString;
		}
	}

	/**
	 * PDF/A Mode.
	 */
	public static enum PdfAMode {
		/**
		 * PDF-A Mode NONE
		 */
		NONE(null),

		/**
		 * PDF-A Mode 1a
		 */
		PDF_A_1a("PDF/A-1a"), //$NON-NLS-1$

		/**
		 * PDF-A Mode 1b
		 */
		PDF_A_1b("PDF/A-1b"); //$NON-NLS-1$

		private final String modeString;

		private PdfAMode(final String modeString) {
			this.modeString = modeString;
		}

		/**
		 * @return the mode string
		 */
		public String getModeString() {
			return modeString;
		}
	}

	/**
	 * @param outputFormat
	 *            the output format
	 * @param stylesheet
	 *            the stylesheet
	 * @param xmlData
	 *            the xml data
	 * @param outputPath
	 *            the output path
	 * @param pdfAMode
	 *            the PDF/A mode
	 * @param overwriteHandling
	 *            what to do when overwriting files
	 * @param resourceResolver
	 *            a resource resolver, may be null
	 * @throws SAXException
	 *             if an SAX error occurs
	 * @throws IOException
	 *             if an I/O exception occurs
	 * @throws TransformerException
	 *             if an transformer exception occurs
	 */
	void fop(OutputFormat outputFormat, Source stylesheet, Source xmlData,
			Path outputPath, PdfAMode pdfAMode,
			OverwriteHandling overwriteHandling,
			ResourceResolver resourceResolver)
			throws SAXException, IOException, TransformerException;

	/**
	 * @return the base directory for data files
	 */
	String getBaseDir();
}
