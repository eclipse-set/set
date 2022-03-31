/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.internal;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import org.eclipse.set.basis.DomainElementList;
import org.eclipse.set.basis.DomainElementList.ChangeListener;
import org.eclipse.set.basis.MediaInfo;

/**
 * A content provider for {@link DomainElementList}s.
 * 
 * @param <T>
 *            the type of the domain element
 * @param <I>
 *            the type of the element info
 * 
 * @author Schaefer
 */
public class DomainElementListContentProvider<T, I extends MediaInfo<T>>
		implements IStructuredContentProvider, ChangeListener<T> {

	private DomainElementList<T, I> domainElementList;
	private TableViewer tableViewer;

	@Override
	public void dispose() {
		if (domainElementList != null) {
			domainElementList.removeChangeListener(this);
		}
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		@SuppressWarnings("unchecked")
		final DomainElementList<T, I> list = (DomainElementList<T, I>) inputElement;
		return list.getElements().toArray();
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput,
			final Object newInput) {
		tableViewer = (TableViewer) viewer;
		if (newInput != null) {
			@SuppressWarnings("unchecked")
			final DomainElementList<T, I> list = (DomainElementList<T, I>) newInput;
			domainElementList = list;
			list.addChangeListener(this);
		}

		if (oldInput != null) {
			@SuppressWarnings("unchecked")
			final DomainElementList<T, I> list = (DomainElementList<T, I>) oldInput;
			list.removeChangeListener(this);
		}
	}

	@Override
	public void listChanged(final Notification msg) {
		tableViewer.refresh(true);
	}
}
