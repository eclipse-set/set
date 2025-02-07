/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.pdf.utils;

import java.nio.file.Path;
import java.util.Optional;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.browser.DownloadListener;
import org.eclipse.set.core.services.pdf.PdfViewer;
import org.eclipse.set.utils.FileWebBrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Provides simplified API for displaying PDF files in an embedded browser.
 * 
 * @author Stuecker
 */
public class BrowserPdfViewer implements PdfViewer, DownloadListener {
	private static final String PDF_VIEWER_PATH = "./web/pdf"; //$NON-NLS-1$
	private static final String PDF_FILE_PATH_PREFIX = "pdffile/"; //$NON-NLS-1$
	private static final String HTML_PDF_VIEWER_PATH = "viewer.html"; //$NON-NLS-1$

	private FileWebBrowser browser;
	private SaveListener saveListener;
	private final Composite parent;

	/**
	 * @param parent
	 *            the parent element
	 */
	public BrowserPdfViewer(final Composite parent) {
		this.parent = parent;
	}

	/**
	 * Refresh the document view.
	 */
	@Override
	public void refresh() {
		browser.refresh();
	}

	/**
	 * View a PDF file. The instance may not be reused to view other files.
	 * 
	 * @param path
	 *            path to the PDF file
	 * 
	 */
	@Override
	public void show(final Path path) {
		try {
			if (this.browser == null) {
				browser = new FileWebBrowser(parent);
				browser.getBrowser().setDownloadListener(this);
				browser.serveRootDirectory(Path.of(PDF_VIEWER_PATH));

				GridDataFactory.swtDefaults()
						.align(SWT.FILL, SWT.FILL)
						.grab(true, true)
						.span(2, 1)
						.applyTo(browser.getControl());
			}

			final String serverPath = PDF_FILE_PATH_PREFIX
					+ path.getFileName().toString();
			browser.serveFile(serverPath, "application/pdf", path); //$NON-NLS-1$

			final String viewerUrl = "https://toolbox/" + HTML_PDF_VIEWER_PATH //$NON-NLS-1$
					+ "?file=/" //$NON-NLS-1$
					+ serverPath;
			browser.setUrl(viewerUrl);
		} catch (final Exception e) {
			throw new RuntimeException("Fehler beim Anzeigen der PDF-Datei.", //$NON-NLS-1$
					e);
		}
	}

	@Override
	public Optional<Path> beforeDownload(final String suggestedName,
			final String url) {
		if (saveListener != null) {
			return saveListener.saveFile(suggestedName);
		}
		return Optional.empty();
	}

	@Override
	public void downloadFinished(final boolean success, final Path path) {
		if (saveListener != null) {
			saveListener.saveCompleted(path);
		}
	}

	@Override
	public void setSaveListener(final SaveListener saveListener) {
		this.saveListener = saveListener;
	}
}
