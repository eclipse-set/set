/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.events;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.google.common.collect.Lists;

/**
 * Registration for {@link ToolboxEvent}s.
 * 
 * @author Schaefer
 */
public class EventRegistration {

	private final IEventBroker broker;
	private final List<ToolboxEventHandler<?>> eventHandlers = Lists
			.newArrayList();

	/**
	 * @param broker
	 *            the event broker
	 */
	public EventRegistration(final IEventBroker broker) {
		this.broker = broker;
	}

	/**
	 * Register the handler and subscribe the given event.
	 * 
	 * @param <T>
	 *            the event type
	 * @param type
	 *            the event type's class
	 * @param handler
	 *            the handler
	 */
	public <T extends ToolboxEvent> void registerHandler(final Class<T> type,
			final Consumer<T> handler) {
		final FunctionalToolboxEventHandler<T> eventHandler = new FunctionalToolboxEventHandler<>(
				handler);
		ToolboxEvents.subscribe(broker, type, eventHandler);
		eventHandlers.add(eventHandler);
	}

	/**
	 * Unregister and unsubscribe all registered event handlers.
	 */
	public void unsubscribeAll() {
		eventHandlers.forEach(h -> ToolboxEvents.unsubscribe(broker, h));
	}
}
