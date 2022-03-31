/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.server;

import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

/**
 * Web server wrapper to provide common web server functions
 * 
 * @author Peters
 *
 */
public abstract class AbstractWebServer {

	/*
	 * Specifying port 0 indicates to the operating system, that any free port
	 * can be used for the web server.
	 */
	private static final int ANY_FREE_PORT = 0;
	private static final String SERVER_HOST = "localhost"; //$NON-NLS-1$
	private final Server server;

	/**
	 * Creates a new web server on a random free port
	 */
	protected AbstractWebServer() {
		server = new Server(new InetSocketAddress(SERVER_HOST, ANY_FREE_PORT));
	}

	/**
	 * Creates a new web server on a specific free port
	 * 
	 * @param port
	 *            the port to use
	 */
	protected AbstractWebServer(final int port) {
		server = new Server(new InetSocketAddress(SERVER_HOST, port));
	}

	/**
	 * @return Returns a URL to the base/root location on the web server
	 */
	public String getRootUrl() {
		final Connector[] connectors = server.getConnectors();
		if (connectors.length == 0) {
			throw new RuntimeException();
		}
		final int port = ((ServerConnector) connectors[0]).getLocalPort();
		final String host = ((ServerConnector) connectors[0]).getHost();
		return "http://" + host + ":" + port + "/"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * @param handler
	 *            Adds a handler to the underlying jetty server
	 */
	public void setHandler(final Handler handler) {
		server.setHandler(handler);
	}

	/**
	 * Starts the web server
	 * 
	 * @throws Exception
	 *             if the underlying call to the jetty server fails
	 */
	public void start() throws Exception {
		server.start();
	}

	/**
	 * Stops the web server
	 * 
	 * @throws Exception
	 *             if the underlying call to the jetty server fails
	 */
	public void stop() throws Exception {
		server.stop();
	}

	/**
	 * Whether the server is running or not
	 * 
	 * @return true if the server is starting or has been started
	 */
	public boolean isRunning() {
		return server.isRunning();
	}
}
