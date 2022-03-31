/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;

/**
 * A new active view is selected.
 * 
 * @author Schaefer
 */
public class NewActiveViewEvent implements ToolboxEvent {

	private static final String TOPIC = "toolboxevents/views/active/new"; //$NON-NLS-1$

	private final MPart newActivePart;

	/**
	 * Creates a {@link NewActiveViewEvent} event without a new active part.
	 */
	public NewActiveViewEvent() {
		this(null);
	}

	/**
	 * @param newActivePart
	 *            the part of the new active view
	 */
	public NewActiveViewEvent(final MPart newActivePart) {
		this.newActivePart = newActivePart;
	}

	/**
	 * @return the part of the new active view
	 */
	public MPart getNewActivePart() {
		return newActivePart;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}
}
