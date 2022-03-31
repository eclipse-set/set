/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

/**
 * The default save & refresh outdated action.
 * 
 * @author Schaefer
 */
public class SaveAndRefreshAction implements SelectableAction {

	private final Messages messages;
	private final IModelSession session;
	private final Shell shell;

	/**
	 * @param source
	 *            the source
	 */
	public SaveAndRefreshAction(final BasePart<?> source) {
		this.shell = source.getToolboxShell();
		this.session = source.getModelSession();
		this.messages = source.getUtilMessages();
	}

	@Override
	public String getText() {
		return messages.OutdatedAction_SaveAndRefresh;
	}

	@Override
	public void selected(final SelectionEvent ev) {
		try {
			session.save(shell);
		} catch (final UserAbortion e) {
			// we continue normally after an user abortion
		}
	}
}
