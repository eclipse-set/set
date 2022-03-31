/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import org.eclipse.set.basis.emfforms.RendererContext;
import org.eclipse.swt.widgets.Button;

/**
 * An abstract {@link ButtonAction} which can be enabled or disabled.
 * 
 * @author Schaefer
 */
public abstract class StatefulButtonAction extends AbstractButtonAction {

	private Button registeredButton;

	protected StatefulButtonAction(final String text, final int width) {
		super(text, width);
	}

	/**
	 * @return whether the registered button is enabled
	 */
	public boolean isEnabled() {
		return registeredButton.isEnabled();
	}

	/**
	 * @return true if button is registered and not dispose
	 */
	public boolean IsRegistered() {
		return registeredButton != null && !registeredButton.isDisposed();
	}

	@Override
	public void register(final RendererContext context) {
		registeredButton = context.get(Button.class);
	}

	/**
	 * @param enabled
	 *            the new enabled state
	 */
	public void setEnabled(final boolean enabled) {
		registeredButton.setEnabled(enabled);
	}
}
