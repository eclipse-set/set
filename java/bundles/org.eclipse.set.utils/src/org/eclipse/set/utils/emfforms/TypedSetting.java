/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.emfforms;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;

import org.eclipse.core.runtime.Assert;

/**
 * A typed {@link Setting}-like structure.
 * 
 * @param <T>
 *            the value type
 * 
 * @author Schaefer
 */
public class TypedSetting<T> {

	private final EStructuralFeature feature;
	private final EObject object;
	private final Optional<T> value;

	/**
	 * @param object
	 *            the object holding the value
	 * @param feature
	 *            the feature
	 * @param value
	 *            the value
	 */
	public TypedSetting(final EObject object, final EStructuralFeature feature,
			final Optional<T> value) {
		Assert.isNotNull(value);
		Assert.isNotNull(object);
		Assert.isNotNull(feature);
		this.value = value;
		this.object = object;
		this.feature = feature;
	}

	/**
	 * @return the object holding the value
	 */
	public EObject getEObject() {
		return object;
	}

	/**
	 * @return the feature holding the value
	 */
	public EStructuralFeature getEStructuralFeature() {
		return feature;
	}

	/**
	 * @return the value
	 */
	public Optional<T> getValue() {
		return value;
	}
}
