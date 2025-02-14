/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.export.checkboxmodel;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * 
 */
public class CheckBoxTreeModelProvider implements ITreeContentProvider {

	CheckboxTreeViewer viewer;

	/**
	 * @param viewer
	 */
	public CheckBoxTreeModelProvider(final CheckboxTreeViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof final CheckBoxTreeElement treeElement) {
			return treeElement.getChildElements().toArray();
		} else if (inputElement instanceof final CheckboxTreeModel treeModel) {
			return treeModel.getAllElements();
		}
		return null;

	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		return getElements(parentElement);
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final CheckBoxTreeElement treeElement) {
			return treeElement.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof final CheckBoxTreeElement treeElement) {
			return treeElement.isParent();
		}
		return false;
	}
}
