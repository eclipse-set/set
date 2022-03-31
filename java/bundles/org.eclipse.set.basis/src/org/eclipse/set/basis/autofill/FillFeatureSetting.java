/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * A {@link FillSetting} for a single feature of a non null object.
 * 
 * @author Schaefer
 */
public class FillFeatureSetting implements FillSetting {

	private final EStructuralFeature feature;
	private final EObject object;

	/**
	 * @param object
	 *            the object
	 * @param feature
	 *            the feature
	 */
	public FillFeatureSetting(final EObject object,
			final EStructuralFeature feature) {
		Assert.isNotNull(object);
		Assert.isNotNull(feature);
		this.object = object;
		this.feature = feature;
	}

	/**
	 * @return the feature
	 */
	public EStructuralFeature getFeature() {
		return feature;
	}

	/**
	 * @return the object
	 */
	public EObject getObject() {
		return object;
	}

	@Override
	public Object getValue() {
		return object.eGet(feature);
	}

	@Override
	public void setValue(final EditingDomain editingDomain,
			final Object value) {
		if (editingDomain != null) {
			final Command command = SetCommand.create(editingDomain, object,
					feature, value);
			editingDomain.getCommandStack().execute(command);
		} else {
			object.eSet(feature, value);
		}
	}
}
