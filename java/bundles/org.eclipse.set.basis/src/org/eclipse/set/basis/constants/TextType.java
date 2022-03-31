/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants;

/**
 * Enumeration of text types.
 * 
 * @author Schaefer
 */
public enum TextType {
	/**
	 * Used for toolbox text viewer attributes.
	 */
	TOOLBOX_VIEWER_ATTRIBUTES,

	/**
	 * Used for toolbox text viewer defaults.
	 */
	TOOLBOX_VIEWER_DEFAULTS,

	/**
	 * Used for toolbox text viewer strings.
	 */
	TOOLBOX_VIEWER_STRINGS,

	/**
	 * Used for toolbox text viewer tags.
	 */
	TOOLBOX_VIEWER_TAGS;

	/**
	 * @return the property for this enum
	 */
	public String getProperty() {
		return name().toLowerCase().replace('_', '.');
	}
}
