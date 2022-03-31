/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import org.osgi.service.event.EventHandler;

/**
 * Abstract default toolbox event handler.
 * 
 * @param <T>
 *            the type of the event
 * 
 * @author Schaefer
 */
public abstract class DefaultToolboxEventHandler<T extends ToolboxEvent>
		implements ToolboxEventHandler<T> {

	private EventHandler eventHandler;

	@Override
	public EventHandler getEventHandler() {
		return eventHandler;
	}

	@Override
	public void setEventHandler(final EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
}
