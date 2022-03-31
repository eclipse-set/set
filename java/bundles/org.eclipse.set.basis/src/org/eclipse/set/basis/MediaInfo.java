/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.guid.Guid;

/**
 * Additional information about an media element.
 * 
 * @param <T>
 *            the type of the media element
 * 
 * @author Schaefer
 */
public interface MediaInfo<T> {

	/**
	 * @return the media data
	 */
	byte[] getData();

	/**
	 * @return the media element
	 */
	T getElement();

	/**
	 * @return the guid of the media element
	 */
	Guid getGuid();

	/**
	 * @return the toolbox file containing the media element
	 */
	ToolboxFile getToolboxFile();
}
