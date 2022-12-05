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
import org.eclipse.set.utils.BasePart;

/**
 * A jump to a specific line of the source file is requested.
 * 
 * @author Schaefer
 */
public class JumpToSourceLineEvent implements ToolboxEvent {

	private static final String TOPIC = "tooboxevents/source/line/jump"; //$NON-NLS-1$

	private final BasePart<? extends IModelSession> source;

	private final int lineNumber;

	private final String objectGuid;

	/**
	 * Default event.
	 */
	public JumpToSourceLineEvent() {
		this(0, null);
	}

	/**
	 * @param source
	 *            the source
	 */
	public JumpToSourceLineEvent(
			final BasePart<? extends IModelSession> source) {
		this.lineNumber = -1;
		this.source = source;
		this.objectGuid = null;
	}

	/**
	 * @param lineNumber
	 *            the line number
	 * @param source
	 *            the source
	 */
	public JumpToSourceLineEvent(final int lineNumber,
			final BasePart<? extends IModelSession> source) {
		this.lineNumber = lineNumber;
		this.source = source;
		this.objectGuid = null;
	}

	/**
	 * @param objectGuid
	 *            the object id
	 * @param source
	 *            the source
	 */
	public JumpToSourceLineEvent(final String objectGuid,
			final BasePart<? extends IModelSession> source) {
		this.lineNumber = -1;
		this.source = source;
		this.objectGuid = objectGuid;
	}

	/**
	 * @return the line number
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @return the object id
	 */
	public String getObjectGuid() {
		return objectGuid;
	}

	/**
	 * @return the source
	 */
	public BasePart<? extends IModelSession> getSource() {
		return source;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}
}
