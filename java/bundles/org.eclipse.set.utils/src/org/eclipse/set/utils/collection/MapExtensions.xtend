/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.collection

import java.util.Map
import java.util.List
import java.util.function.Supplier

class MapExtensions {
	
	def static <T, U> void add(Map<T, List<U>> map, T key, U value) {
		val values = map.get(key)
		if (values !== null) {
			values.add(value)
			return
		}
		map.put(key, newArrayList(value))
	}
	
	def static <T, U> void add(Map<T, List<U>> map, T key, List<U> values) {
		val value = map.get(key)
		if (value !== null) {
			value.addAll(values)
			return
		}
		map.put(key, values)
	}

	def static <T, U> U getValue(Map<T, U> map, T key,
		Supplier<U> getFunction) {
		val value = map.get(key)
		if (value === null) {
			return getFunction.get
		}
		return value
	}
}
