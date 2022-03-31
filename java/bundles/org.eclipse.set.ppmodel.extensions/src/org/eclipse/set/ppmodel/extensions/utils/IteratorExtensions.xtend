/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import java.util.Iterator

/**
 * This class extends {@link Iterator}.
 * 
 * @author Schaefer
 */
class IteratorExtensions {
	
	/**
	 * Finds the first element in this iterator of the given type. If none
	 * is found or the iterator is empty, <code>null</code> is returned.
	 * 
	 * @param iterator this iterator
	 * @param type the type
	 * 
	 * @return the first element in the given iterator of the given type
	 */
	def static <T, S extends T> S findFirst(Iterator<T> iterator, Class<S> type) {
		return iterator.findFirst[type.isInstance(it)] as S
	}
}
