/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.pdf.utils.server;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.set.utils.server.AbstractWebServer;

/**
 * Web server to provide the PDF files and viewer files to a browser
 * 
 * @author Peters
 *
 */
public class PdfViewerServer extends AbstractWebServer {
	private static final String WEBAPPLICATION_DIRECTORY = "./webapplication/pdf"; //$NON-NLS-1$
	private static final String PDF_FILE_DIR = "/pdffile/"; //$NON-NLS-1$
	private final ArrayList<Handler> handlers = new ArrayList<>();

	/**
	 * Creates a new web PDF server on a random free port
	 */
	public PdfViewerServer() {
		super();
	}

	/**
	 * Creates a new web PDF server on a specific free port
	 * 
	 * @param port
	 *            the port to use
	 */
	public PdfViewerServer(final int port) {
		super(port);
	}

	/**
	 * Configures the web server to provide the content from applicationPath.
	 * Also configures the web server to provide the PDF files
	 * 
	 * @throws Exception
	 *             if the underlying call to the jetty server fails
	 */
	// ServletContextHandler closes the resource
	@SuppressWarnings("resource")
	public void configure() throws Exception {
		handlers.add(new DefaultHandler());
		final ServletContextHandler context = createDefaultContextHandler("/"); //$NON-NLS-1$

		final Path webDirPath = Paths.get(WEBAPPLICATION_DIRECTORY);
		final Resource baseResource = Resource.newResource(webDirPath);
		context.setBaseResource(baseResource);
		handlers.add(0, context);
		updateHandlers();
	}

	/**
	 * Creates a basic ServletContextHandler at the given path
	 * 
	 * @param path
	 *            the path to serve at
	 * @return the created ServletContextHandler
	 */
	protected static ServletContextHandler createDefaultContextHandler(
			final String path) {
		final ServletContextHandler context = new ServletContextHandler();
		context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", //$NON-NLS-1$
				"false"); //$NON-NLS-1$
		context.setContextPath(path);
		context.addServlet(new ServletHolder(new DefaultServlet()), "/"); //$NON-NLS-1$

		final ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
		errorHandler.addErrorPage(404, "/"); //$NON-NLS-1$
		context.setErrorHandler(errorHandler);
		return context;
	}

	/**
	 * Adds the specified path to the served files and returns the path from
	 * where it is accessible on the server
	 * 
	 * @param file
	 *            the file to serve
	 * 
	 * @return the URI under which the file will be accessible
	 * 
	 * @throws Exception
	 *             if the underlying call to the jetty server fails
	 */
	@SuppressWarnings("resource")
	public String serveFile(final Path file) throws Exception {
		final String serverPath = PDF_FILE_DIR + file.getFileName().toString();
		final ServletContextHandler fileContext = createDefaultContextHandler(
				serverPath);
		final Resource fileResource = Resource.newResource(file);
		fileContext.setBaseResource(fileResource);

		handlers.add(0, fileContext);
		updateHandlers();
		return serverPath;
	}

	private void updateHandlers() throws Exception {
		final boolean wasRunning = isRunning();
		if (wasRunning) {
			stop();
		}
		final HandlerCollection collection = new HandlerCollection(
				handlers.toArray(new Handler[0]));
		setHandler(collection);
		if (wasRunning) {
			start();
		}
	}
}
