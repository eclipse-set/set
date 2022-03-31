/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import java.util.function.Consumer;

import org.osgi.service.event.EventHandler;

/**
 * Handle toolbox events.
 * 
 * @param <T>
 *            the type of the event
 * 
 * @author Schaefer
 */
public interface ToolboxEventHandler<T> extends Consumer<T> {

	/**
	 * @return the service event handler registered for this toolbox event
	 *         handler
	 */
	EventHandler getEventHandler();

	/**
	 * @param eventHandler
	 *            the service event handler registered for this toolbox event
	 *            handler
	 */
	void setEventHandler(EventHandler eventHandler);
}
