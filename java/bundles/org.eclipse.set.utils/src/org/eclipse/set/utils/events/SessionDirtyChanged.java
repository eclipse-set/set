/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import org.eclipse.set.basis.IModelSession;

/**
 * The dirty state of the session changed.
 * 
 * @author Schaefer
 */
public class SessionDirtyChanged implements ToolboxEvent {

	private static final String TOPIC = "tooboxevents/dirty/changed"; //$NON-NLS-1$
	private final boolean dirty;
	private final IModelSession session;

	/**
	 * Default construction.
	 */
	public SessionDirtyChanged() {
		dirty = false;
		session = null;
	}

	/**
	 * @param session
	 *            the model session
	 * @param dirty
	 *            the new dirty state
	 */
	public SessionDirtyChanged(final IModelSession session,
			final boolean dirty) {
		this.session = session;
		this.dirty = dirty;
	}

	/**
	 * @return the model session
	 */
	public IModelSession getSession() {
		return session;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}

	/**
	 * @return the new dirty state
	 */
	public boolean isDirty() {
		return dirty;
	}
}
