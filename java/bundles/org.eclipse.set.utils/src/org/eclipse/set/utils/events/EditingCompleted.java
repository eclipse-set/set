/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

/**
 * The editing of the session model is complete.
 * 
 * @author Schaefer
 */
public class EditingCompleted extends DataEvent {

	private static final String SUBTOPIC = "/editing/completed"; //$NON-NLS-1$

	@Override
	public String getTopic() {
		return getBaseTopic() + SUBTOPIC;
	}
}
