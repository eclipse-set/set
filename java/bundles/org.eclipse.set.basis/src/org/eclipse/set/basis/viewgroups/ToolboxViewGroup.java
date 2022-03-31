/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.basis.viewgroups;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Describes a view group of the toolbox action area.
 * 
 * @author Schaefer
 */
public class ToolboxViewGroup {

	private final ImageDescriptor imageDescriptor;
	private final boolean isDevelopment;
	private final boolean isInitiallyExpanded;
	private final boolean isInvisible;
	private final String text;

	/**
	 * @param text
	 *            the text for the view group
	 * @param imageDescriptor
	 *            the image descriptor for the view group
	 * @param isInitiallyExpanded
	 *            whether the view group shall be initially expanded
	 * @param isDevelopment
	 *            whether the view group is a development group
	 * @param isInvisible
	 *            whether the view group is invisible
	 */
	public ToolboxViewGroup(final String text,
			final ImageDescriptor imageDescriptor,
			final boolean isInitiallyExpanded, final boolean isDevelopment,
			final boolean isInvisible) {
		this.text = text;
		this.imageDescriptor = imageDescriptor;
		this.isInitiallyExpanded = isInitiallyExpanded;
		this.isDevelopment = isDevelopment;
		this.isInvisible = isInvisible;
	}

	/**
	 * @return the image descriptor for the view group
	 */
	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}

	/**
	 * @return the text for the view group
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return whether the view group is a development group
	 */
	public boolean isDevelopment() {
		return isDevelopment;
	}

	/**
	 * @return whether the view group shall be initially expanded
	 */
	public boolean isInitiallyExpanded() {
		return isInitiallyExpanded;
	}

	/**
	 * @return whether the view group is invisible
	 */
	public boolean isInvisible() {
		return isInvisible;
	}
}
