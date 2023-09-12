/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import org.eclipse.set.basis.Pair;
import org.eclipse.set.model.validationreport.ObjectState;
import org.eclipse.set.utils.BasePart;

/**
 * A jump to a specific line of the source file is requested.
 * 
 * @author Schaefer
 */
public class JumpToSourceLineEvent implements ToolboxEvent {

	private static final String TOPIC = "tooboxevents/source/line/jump"; //$NON-NLS-1$

	private final BasePart source;

	private final int lineNumber;

	private final String objectGuid;
	private final ObjectState objectState;

	/**
	 * Default event.
	 */
	public JumpToSourceLineEvent() {
		this(0, null, null);
	}

	/**
	 * @param source
	 *            the source
	 */
	public JumpToSourceLineEvent(final BasePart source) {
		this(-1, source, null);
	}

	/**
	 * @param lineNumber
	 *            the line number
	 * @param source
	 *            the source
	 * @param objectState
	 *            the state of selected object
	 */
	public JumpToSourceLineEvent(final int lineNumber, final BasePart source,
			final ObjectState objectState) {
		this.lineNumber = lineNumber;
		this.source = source;
		this.objectGuid = null;
		this.objectState = objectState;
	}

	/**
	 * @param objectGuid
	 *            the object id
	 * @param source
	 *            the source
	 */
	public JumpToSourceLineEvent(final String objectGuid,
			final BasePart source) {
		this.lineNumber = -1;
		this.source = source;
		this.objectGuid = objectGuid;
		this.objectState = null;
	}

	/**
	 * @return the line number
	 */
	public Pair<ObjectState, Integer> getLineNumber() {
		return new Pair<>(objectState,
				Integer.valueOf(lineNumber));
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
	public BasePart getSource() {
		return source;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}
}
