/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;

/**
 * The custom binding of a basis attribute.
 * 
 * @param <T>
 *            the type of the wert-value
 * 
 * @author Schaefer
 */
public class BasisAttributeBinding<T> {

	private Listener controlListener;
	CommandStackListener commandStackListener;
	EditingDomain editingDomain;
	final BasisAttributeRenderer<T> renderer;

	/**
	 * @param renderer
	 *            the renderer
	 */
	public BasisAttributeBinding(final BasisAttributeRenderer<T> renderer) {
		this.renderer = renderer;
	}

	/**
	 * Bind the binding.
	 */
	public void bind() {
		// UI to model
		renderer.updateControl();
		controlListener = event -> renderer.updateModel();
		final Control control = renderer.getControl();
		control.addListener(SWT.Modify, controlListener);

		// model to UI
		commandStackListener = event -> {
			if (!renderer.isDisposed()) {
				// IMPROVE This globally updates all basis attribute
				// controls. We had problems with this behavior and auto
				// filling. It would be helpful to restrict updates to
				// affected controls.
				renderer.updateControl();
			}
		};
		editingDomain = AdapterFactoryEditingDomain
				.getEditingDomainFor(renderer.getParent());
		editingDomain.getCommandStack()
				.addCommandStackListener(commandStackListener);
		control.addDisposeListener(e -> editingDomain.getCommandStack()
				.removeCommandStackListener(commandStackListener));
	}

	/**
	 * Unbind the binding.
	 */
	public void unbind() {
		// UI listener
		final Control control = renderer.getControl();
		if (!control.isDisposed()) {
			control.removeListener(SWT.Modify, controlListener);
		}

		// Model listener
		editingDomain.getCommandStack()
				.removeCommandStackListener(commandStackListener);
	}
}
