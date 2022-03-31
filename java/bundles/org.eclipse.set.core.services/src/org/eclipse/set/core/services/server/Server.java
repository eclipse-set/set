/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.server;

/**
 * A web server.
 * 
 * @param <T>
 *            implementation type
 * 
 * @author Schaefer
 */
public interface Server<T> {

	/**
	 * @return the URL for the server
	 */
	T getImplementation();

	/**
	 * Shutdown the server.
	 */
	void stop();

	/**
	 * Start the server.
	 */
	void start();
}
