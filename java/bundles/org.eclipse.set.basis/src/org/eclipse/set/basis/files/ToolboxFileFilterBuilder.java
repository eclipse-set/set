/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.files;

import java.util.Arrays;

/**
 * Builder for {@link ToolboxFileFilter}s.
 * 
 * @author Schaefer
 */
public class ToolboxFileFilterBuilder {

	/**
	 * @param name
	 *            the name of the filter
	 * 
	 * @return a builder for {@link ToolboxFileFilter}s using the given name
	 */
	public static ToolboxFileFilterBuilder forName(final String name) {
		return new ToolboxFileFilterBuilder(name);
	}

	private final ToolboxFileFilter namedFilterExtension;

	private ToolboxFileFilterBuilder(final String name) {
		namedFilterExtension = new ToolboxFileFilter(name);
	}

	/**
	 * @param extensions
	 *            the plain extensions (without placeholder) to add
	 * 
	 * @return this builder
	 */
	public ToolboxFileFilterBuilder add(final Iterable<String> extensions) {
		namedFilterExtension.add(extensions);
		return this;
	}

	/**
	 * @param extensions
	 *            the plain extensions (without placeholder) to add
	 * 
	 * @return this builder
	 */
	public ToolboxFileFilterBuilder add(final String... extensions) {
		return add(Arrays.asList(extensions));
	}

	/**
	 * @return the new {@link ToolboxFileFilter}
	 */
	public ToolboxFileFilter create() {
		return namedFilterExtension;
	}

	/**
	 * @param value
	 *            true, if a list with used extensions should be appended to the
	 *            name; false otherwise
	 * 
	 * @return this builder
	 */
	public ToolboxFileFilterBuilder filterNameWithFilterList(
			final boolean value) {
		namedFilterExtension.setFilterNameWithFilterList(value);
		return this;
	}
}
