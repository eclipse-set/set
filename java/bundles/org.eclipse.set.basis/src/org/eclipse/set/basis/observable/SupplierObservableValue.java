/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.basis.observable;

import java.util.function.Supplier;

import org.eclipse.core.databinding.observable.value.WritableValue;

/**
 * Observable with Supplier to get value
 * 
 * @author truong
 * @param <T>
 *            the value type
 */
public class SupplierObservableValue<T> extends WritableValue<T> {

	private final Supplier<T> supplier;

	/**
	 * @param supplier
	 *            the {@link Supplier} to get value
	 * @param valueType
	 *            the value type
	 */
	public SupplierObservableValue(final Supplier<T> supplier,
			final Object valueType) {
		super(supplier.get(), valueType);
		this.supplier = supplier;
	}

	/**
	 * Set value through supplier
	 */
	public void calculate() {
		if (getRealm().isCurrent()) {
			setValue(supplier.get());
		} else {
			getRealm().asyncExec(() -> setValue(supplier.get()));
		}
	}

}
