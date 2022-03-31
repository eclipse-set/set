/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

/**
 * A function which may throw an exception.
 * 
 * @author Schaefer
 * 
 * @param <T>
 *            the type of the input to the function
 */
@FunctionalInterface
public interface ConsumerThrowingException<T> {

	/**
	 * Performs this operation on the given argument.
	 *
	 * @param t
	 *            the input argument
	 * 
	 * @throws Exception
	 *             the exception
	 */
	void accept(T t) throws Exception;
}
