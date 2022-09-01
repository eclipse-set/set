/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.textview;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.set.utils.server.AbstractWebServer;

/**
 * Web server to provide the ppxml and error lists for the browser text view
 * 
 * @author Stuecker
 */
public class TextViewServer extends AbstractWebServer {
	private static final String TEXT_VIEWER_PATH = "./web/textview"; //$NON-NLS-1$
	private static final String PROBLEMS_JSON = "/problems.json"; //$NON-NLS-1$
	private static final String MODEL_PPXML = "/model.ppxml"; //$NON-NLS-1$
	private final ArrayList<Handler> handlers = new ArrayList<>();
	private final TextViewProblemsServlet problemViewServlet = new TextViewProblemsServlet();
	private final Map<HttpServlet, String> servlets = new HashMap<>();

	/**
	 * Creates a new web server on a random free port
	 */
	public TextViewServer() {
		super();
	}

	/**
	 * Configures the web server to provide the content from applicationPath.
	 * 
	 * @throws Exception
	 *             if the underlying call to the jetty server fails
	 */
	// ServletContextHandler closes the resource
	@SuppressWarnings("resource")
	public void configure() throws Exception {
		handlers.add(new DefaultHandler());

		servlets.put(problemViewServlet, PROBLEMS_JSON);
		final ServletContextHandler context = createDefaultContextHandler("/", //$NON-NLS-1$
				servlets);

		final Path pdfViewerPath = Paths.get(TEXT_VIEWER_PATH);
		final Resource baseResource = Resource.newResource(pdfViewerPath);
		context.setBaseResource(baseResource);
		handlers.add(0, context);
		updateHandlers(handlers);
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
	public String serveModel(final Path file) throws Exception {
		final String serverPath = MODEL_PPXML;
		final ServletContextHandler fileContext = createDefaultContextHandler(
				serverPath, servlets);
		final Resource fileResource = Resource.newResource(file);
		fileContext.setBaseResource(fileResource);

		handlers.add(0, fileContext);
		updateHandlers(handlers);
		return serverPath;
	}
}
