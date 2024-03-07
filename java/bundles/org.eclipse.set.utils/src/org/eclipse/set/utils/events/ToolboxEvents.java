/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.google.common.collect.Maps;

/**
 * Utility for {@link ToolboxEvent}s.
 * 
 * @author Schaefer
 */
public class ToolboxEvents {

	/**
	 * Toolbox event key
	 */
	public static final String TOOLBOX_EVENT = "event"; //$NON-NLS-1$

	/**
	 * @param broker
	 *            the event broker
	 * @param event
	 *            the toolbox event
	 */
	public static void send(final IEventBroker broker,
			final ToolboxEvent event) {
		final HashMap<String, Object> data = Maps.newHashMap();
		data.put(TOOLBOX_EVENT, event);
		broker.send(event.getTopic(), data);
	}

	/**
	 * Subscribes to an event with the default topic for an event
	 * 
	 * @param <T>
	 *            the type of the event
	 * @param broker
	 *            the event broker
	 * @param eventType
	 *            the event type
	 * @param handler
	 *            the toolbox event handler
	 */
	public static <T extends ToolboxEvent> void subscribe(
			final IEventBroker broker, final Class<T> eventType,
			final ToolboxEventHandler<T> handler) {
		String topic;
		try {
			topic = eventType.getDeclaredConstructor().newInstance().getTopic();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
		subscribe(broker, eventType, handler, topic);
	}

	/**
	 * Subscribes to an event with a specific topic
	 * 
	 * @param <T>
	 *            the type of the event
	 * @param broker
	 *            the event broker
	 * @param eventType
	 *            the event type
	 * @param handler
	 *            the toolbox event handler
	 * @param topic
	 *            the topic to subscribe to
	 */
	public static <T extends ToolboxEvent> void subscribe(
			final IEventBroker broker, final Class<T> eventType,
			final ToolboxEventHandler<T> handler, final String topic) {
		final EventHandler eventHandler = (final Event event) -> {
			final T toolboxEvent = eventType
					.cast(event.getProperty(TOOLBOX_EVENT));
			handler.accept(toolboxEvent);
		};
		handler.setEventHandler(eventHandler);
		if (!broker.subscribe(topic, eventHandler)) {
			throw new RuntimeException(
					"Subscribing for " + eventType.getName() + " failed."); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * @param broker
	 *            the event broker
	 * @param handler
	 *            the toolbox event handler
	 */
	public static void unsubscribe(final IEventBroker broker,
			final ToolboxEventHandler<?> handler) {
		if (handler != null) {
			broker.unsubscribe(handler.getEventHandler());
		}
	}
}
