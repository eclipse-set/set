/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;

/**
 * The default refresh outdated action.
 * 
 * @author Schaefer
 */
public class RefreshAction implements SelectableAction {

	private final Messages messages;
	private final Consumer<SelectionEvent> update;

	/**
	 * @param source
	 *            the source
	 * @param update
	 *            the update function
	 */
	public RefreshAction(final BasePart<?> source,
			final Consumer<SelectionEvent> update) {
		messages = source.getUtilMessages();
		this.update = update;
	}

	@Override
	public String getText() {
		return messages.OutdatedAction_Refresh;
	}

	@Override
	public void selected(final SelectionEvent e) {
		update.accept(e);
	}
}
