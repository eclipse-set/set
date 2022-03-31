/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils;

/**
 * An abstract {@link ButtonAction} providing text and width.
 * 
 * @author Schaefer
 */
public abstract class AbstractButtonAction implements ButtonAction {

	private final String text;
	private final int width;

	protected AbstractButtonAction(final String text, final int width) {
		this.text = text;
		this.width = width;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public int getWidth() {
		return width;
	}
}
