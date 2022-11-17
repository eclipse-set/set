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

import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.browser.DownloadListener;
import org.eclipse.set.core.services.pdf.PdfViewer;
import org.eclipse.set.pdf.utils.server.PdfViewerServer;
import org.eclipse.set.utils.WebBrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides simplified API for displaying PDF files in an embedded browser.
 * 
 * @author Stuecker
 */
public class BrowserPdfViewer implements PdfViewer, DownloadListener {
	private static final String HTML_PDF_VIEWER_PATH = "viewer.html"; //$NON-NLS-1$

	private WebBrowser browser;
	private PdfViewerServer server;
	private SaveListener saveListener;
	private final Composite parent;

	private static final Logger logger = LoggerFactory
			.getLogger(BrowserPdfViewer.class);

	/**
	 * @param parent
	 *            the parent element
	 */
	public BrowserPdfViewer(final Composite parent) {
		this.parent = parent;
		parent.addDisposeListener(e -> stopServer());
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
			if (this.server == null) {
				server = new PdfViewerServer();
				server.configure();
			}

			if (this.browser == null) {
				browser = new WebBrowser(parent);
				browser.getBrowser().setDownloadListener(this);
				GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
						.grab(true, true).span(2, 1)
						.applyTo(browser.getControl());
			}

			final String serverPath = server.serveFile(path);
			if (!server.isRunning()) {
				server.start();
			}
			final String viewerUrl = server.getRootUrl() + HTML_PDF_VIEWER_PATH
					+ "?file=" //$NON-NLS-1$
					+ URIUtil.encodePath(serverPath);
			browser.setUrl(viewerUrl);
		} catch (final Exception e) {
			throw new RuntimeException("Fehler beim Anzeigen der PDF-Datei.", //$NON-NLS-1$
					e);
		}
	}

	private void stopServer() {
		if (server != null && server.isRunning()) {
			try {
				server.stop();
			} catch (final Exception e) {
				logger.error("Server termination failed.", e); //$NON-NLS-1$
			}
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
