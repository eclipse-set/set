/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.core.runtime.Assert;

/**
 * A {@link FillSetting} for a single non null object and a path of features.
 * 
 * @author Schaefer
 */
public class FillPathSetting implements FillSetting {

	private static void setValue(final EditingDomain editingDomain,
			final EObject owner, final EStructuralFeature feature,
			final Object value) {
		if (editingDomain != null) {
			final Command command = SetCommand.create(editingDomain, owner,
					feature, value);
			editingDomain.getCommandStack().execute(command);
		} else {
			owner.eSet(feature, value);
		}
	}

	private final EFactory factory;
	private final EObject object;

	private final EStructuralFeature[] path;

	/**
	 * @param factory
	 *            the factory
	 * @param object
	 *            the object
	 * @param path
	 *            the feature path
	 */
	public FillPathSetting(final EFactory factory, final EObject object,
			final EStructuralFeature... path) {
		Assert.isNotNull(object);
		Assert.isTrue(path.length > 0);
		this.factory = factory;
		this.object = object;
		this.path = path;
	}

	@Override
	public Object getValue() {
		return getValue(object, 0);
	}

	@Override
	public void setValue(final EditingDomain editingDomain,
			final Object value) {
		setValue(editingDomain, object, 0, value);
	}

	private Object getValue(final EObject current, final int i) {
		final Object value = current.eGet(path[i]);
		if (value == null) {
			return null;
		}
		final int next = i + 1;
		if (next < path.length) {
			return getValue((EObject) value, next);
		}
		return value;
	}

	private EObject newObject(final EStructuralFeature feature) {
		return factory.create((EClass) feature.getEType());
	}

	private void setValue(final EditingDomain editingDomain,
			final EObject current, final int i, final Object value) {
		if (i >= path.length - 1) {
			setValue(editingDomain, current, path[i], value);
		} else {
			final EStructuralFeature feature = path[i];
			final EObject currentObject = (EObject) current.eGet(feature);
			if (currentObject == null) {
				final EObject newObject = newObject(feature);
				setValue(editingDomain, current, feature, newObject);
				setValue(editingDomain, newObject, i + 1, value);
			} else {
				setValue(editingDomain, currentObject, i + 1, value);
			}
		}
	}
}
