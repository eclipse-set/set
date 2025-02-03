/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.server;

import java.util.List;

/**
 * Provide web server.
 * 
 * @param <T>
 *            implementation type
 * 
 * @author Schaefer
 */
public interface ServerFactory<T> {

	/**
	 * A handle to a server
	 */
	public interface Handle {
		//
	}

	/**
	 * @param configuration
	 *            the configuration
	 * @param tempPath 
	 *			list of paths
	 * 
	 * @return a handle to the new server
	 */
	Handle createServer(ServerConfiguration configuration, List<String> tempPath);

	/**
	 * @param handle
	 *            the handle for the server
	 * 
	 * @return the server
	 */
	Server<T> getServer(Handle handle);
}
