/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.util.function.Supplier;

/**
 * Generates a sequence of integers.
 * 
 * @author Schaefer
 */
public class IntegerGenerator implements Supplier<Integer> {

	private int nextInt = 1;

	@Override
	public Integer get() {
		return Integer.valueOf(nextInt++);
	}
}
