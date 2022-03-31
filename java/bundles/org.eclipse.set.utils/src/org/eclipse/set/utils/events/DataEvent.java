/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

/**
 * Base class for data events.
 * 
 * @author Schaefer
 */
public class DataEvent implements ToolboxEvent {

	private static final String BASE_TOPIC = "tooboxevents/data"; //$NON-NLS-1$

	/**
	 * @return the base topic
	 */
	public static String getBaseTopic() {
		return BASE_TOPIC;
	}

	@Override
	public String getTopic() {
		return getBaseTopic() + "/*"; //$NON-NLS-1$
	}
}
