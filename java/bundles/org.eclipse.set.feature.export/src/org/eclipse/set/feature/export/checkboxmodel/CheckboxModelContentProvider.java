/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.checkboxmodel;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.set.core.services.export.CheckboxModelElement;

/**
 * Content provider for a CheckboxModel input
 *
 * @author Rumpf/Schaefer
 */
public class CheckboxModelContentProvider
		implements IStructuredContentProvider {
	private CheckboxModelElement[] checkboxes;

	/**
	 * Create a {@link CheckboxModelContentProvider}.
	 */
	public CheckboxModelContentProvider() {
		super();
	}

	@Override
	public void dispose() {
		// do nothing
	}

	@Override
	public Object[] getElements(final Object element) {
		return checkboxes;
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput,
			final Object newInput) {
		if (newInput instanceof CheckboxModel) {
			checkboxes = ((CheckboxModel) newInput).getElements();
		} else {
			checkboxes = new CheckboxModelElement[0];
		}
	}
}
