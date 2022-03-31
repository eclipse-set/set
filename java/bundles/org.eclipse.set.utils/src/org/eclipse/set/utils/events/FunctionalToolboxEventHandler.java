/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.events;

import java.util.function.Consumer;

/**
 * Functional default toolbox event handler.
 * 
 * @param <T>
 *            the type of the event
 * 
 * @author Schaefer
 */
public class FunctionalToolboxEventHandler<T extends ToolboxEvent>
		extends DefaultToolboxEventHandler<T> {

	private final Consumer<T> consumer;

	/**
	 * @param consumer
	 *            the consumer accepting the events
	 */
	public FunctionalToolboxEventHandler(final Consumer<T> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void accept(final T t) {
		consumer.accept(t);
	}
}
