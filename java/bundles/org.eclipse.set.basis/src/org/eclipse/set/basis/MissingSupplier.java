/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.function.Supplier;

/**
 * A {@link Supplier} which also can supply a special non-null Object called
 * "missing value".
 * 
 * @param <T>
 *            the type of results supplied by this supplier
 * 
 * @author Schaefer
 */
public interface MissingSupplier<T> extends Supplier<T> {

	/**
	 * The "missing value"
	 */
	static Object MISSING_VALUE = new Object();
}
