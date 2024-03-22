/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.DomainElementList;
import org.eclipse.set.basis.MediaInfo;
import org.eclipse.set.basis.commands.AddAttachmentData;

/**
 * Implementation of {@link DomainElementList}.
 * 
 * @param <T>
 *            type of the domain element
 * @param <I>
 *            the type of the element info
 * 
 * @author Schaefer
 */
public class DomainElementListImpl<T, I extends MediaInfo<T>>
		implements DomainElementList<T, I> {

	private Adapter adapter;
	private final EObject container;
	private final EStructuralFeature containingFeature;
	private final EStructuralFeature feature;
	final List<ChangeListener<T>> changeListenerList = new LinkedList<>();

	/**
	 * @param container
	 *            the container of the list
	 * @param feature
	 *            the feature of the list
	 * @param containingFeature
	 *            the containing feature
	 */
	public DomainElementListImpl(final EObject container,
			final EStructuralFeature feature,
			final EStructuralFeature containingFeature) {
		this.container = container;
		this.feature = feature;
		this.containingFeature = containingFeature;
	}

	@Override
	public void add(final I mediaInfo) {
		final EditingDomain editingDomain = AdapterFactoryEditingDomain
				.getEditingDomainFor(container);

		final CompoundCommand compoundAddCommand = new CompoundCommand();

		// add the model element
		final Command addCommand = AddCommand.create(editingDomain, container,
				feature, mediaInfo.getElement());
		compoundAddCommand.append(addCommand);

		// add detached attachment
		if (mediaInfo.getToolboxFile().hasDetachedAttachments()) {
			final Command addAttachmentDataCommand = AddAttachmentData
					.create(mediaInfo);
			compoundAddCommand.append(addAttachmentDataCommand);
		}

		final CommandStack commandStack = editingDomain.getCommandStack();
		commandStack.execute(compoundAddCommand);
		refreshAdapter();
	}

	@Override
	public void addChangeListener(final ChangeListener<T> changeListener) {
		if (adapter == null) {
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification msg) {
					if (msg.getEventType() != Notification.REMOVING_ADAPTER) {
						for (final ChangeListener<T> listener : changeListenerList) {
							listener.listChanged(msg);
						}
					}
				}
			};
			container.eAdapters().add(adapter);
		}
		changeListenerList.add(changeListener);
	}

	@Override
	public EObject getContainer() {
		return container;
	}

	@Override
	public EStructuralFeature getContainingFeature() {
		return containingFeature;
	}

	@Override
	public List<T> getElements() {
		@SuppressWarnings("unchecked")
		final EList<T> elements = (EList<T>) container.eGet(feature);
		return elements;
	}

	@Override
	public EStructuralFeature getFeature() {
		return feature;
	}

	@Override
	public void remove(final T element) {
		final EditingDomain editingDomain = AdapterFactoryEditingDomain
				.getEditingDomainFor(container);
		final Command removeCommand = RemoveCommand.create(editingDomain,
				container, feature, element);
		final CommandStack commandStack = editingDomain.getCommandStack();
		commandStack.execute(removeCommand);
	}

	@Override
	public void removeChangeListener(final ChangeListener<T> changeListener) {
		changeListenerList.remove(changeListener);
		if (changeListenerList.isEmpty() && adapter != null) {
			container.eAdapters().remove(adapter);
			adapter = null;
		}
	}

	private void refreshAdapter() {
		container.eAdapters().remove(adapter);
		container.eAdapters().add(adapter);
	}
}
