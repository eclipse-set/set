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
 * A toolbox specific event.
 * 
 * @author Schaefer
 */
public interface ToolboxEvent {

	/**
	 * @return the topic of this event
	 */
	String getTopic();
}
