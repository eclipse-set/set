/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

/**
 * A decorator for a Comparator<String> which first attempts to compare
 * numerical values if possible.
 * 
 * This allows explicit sorting of numbers contained within a list of strings
 * 
 * @author Stuecker
 *
 */
public class NumericFirstComparatorDecorator implements Comparator<String> {
	private final Comparator<String> component;

	/**
	 * @param component
	 *            the component to decorate
	 */
	public NumericFirstComparatorDecorator(final Comparator<String> component) {
		this.component = component;
	}

	@Override
	public int compare(final String o1, final String o2) {
		if (StringUtils.isNumeric(o1) && StringUtils.isNumeric(o2)) {
			// If both strings are integers, order them numerically
			// regardless of pattern matching
			return Integer.compare(Integer.parseUnsignedInt(o1),
					Integer.parseUnsignedInt(o2));
		}

		// Defer to base comparator
		return component.compare(o1, o2);
	}

}
