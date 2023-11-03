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
 * @param orderPriority
 *            priority for ordering the view groups. Higher priority causes the
 *            group to be shown earlier
 */
public record ToolboxViewGroup(String text, ImageDescriptor imageDescriptor,
		boolean isInitiallyExpanded, boolean isDevelopment, boolean isInvisible,
		int orderPriority) {

	/**
	 * Builder for ToolboxViewGroup
	 */
	@SuppressWarnings("javadoc")
	public static class Builder {
		private String text;
		private ImageDescriptor imageDescriptor;
		private boolean isInitiallyExpanded = false;
		private boolean isDevelopment = false;
		private boolean isInvisible = false;
		private int orderPriority = 0;

		public Builder withText(final String groupText) {
			this.text = groupText;
			return this;
		}

		public Builder withIcon(final ImageDescriptor groupImageDescriptor) {
			this.imageDescriptor = groupImageDescriptor;
			return this;
		}

		public Builder setInitiallyExpanded(final boolean initiallyExpanded) {
			this.isInitiallyExpanded = initiallyExpanded;
			return this;
		}

		public Builder setDevelopment(final boolean development) {
			this.isDevelopment = development;
			return this;
		}

		public Builder setInvisible(final boolean invisible) {
			this.isInvisible = invisible;
			return this;
		}

		public Builder withOrderPriority(final int priority) {
			this.orderPriority = priority;
			return this;
		}

		public ToolboxViewGroup build() {
			return new ToolboxViewGroup(text, imageDescriptor,
					isInitiallyExpanded, isDevelopment, isInvisible,
					orderPriority);
		}
	}
}
