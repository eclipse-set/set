/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.about;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.set.basis.ui.VersionInfo;
import org.eclipse.set.utils.server.AbstractWebServer;

/**
 * Web server to provide the About Part
 * 
 * @author Truong
 *
 */
public class AboutServer extends AbstractWebServer {
	private final ArrayList<Handler> handlers = new ArrayList<>();

	/**
	 * 
	 */
	public AboutServer() {
		super();
	}

	/**
	 * @param path
	 *            directory path of about
	 * @param versions
	 *            version informations
	 * @throws Exception
	 *             if the underlying call to the jetty server fails
	 */
	@SuppressWarnings("resource")
	public void configure(final Path path, final VersionInfo[] versions)
			throws Exception {
		final Map<HttpServlet, String> servlets = new HashMap<>();
		servlets.put(new VersionInfoServlet(versions), "/version.json"); //$NON-NLS-1$
		final ServletContextHandler context = createDefaultContextHandler("/", //$NON-NLS-1$
				servlets);
		final Resource baseResource = Resource.newResource(path);
		context.setBaseResource(baseResource);
		handlers.add(0, context);
		updateHandlers(handlers);
	}
}
