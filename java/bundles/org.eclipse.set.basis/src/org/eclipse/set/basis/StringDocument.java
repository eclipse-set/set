/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import org.eclipse.jface.text.AbstractDocument;
import org.eclipse.jface.text.DefaultLineTracker;
import org.eclipse.jface.text.GapTextStore;

/**
 * Simple string document.
 * 
 * @author Schaefer
 */
public class StringDocument extends AbstractDocument {

	/**
	 * @param content
	 *            the content
	 */
	public StringDocument(final String content) {
		completeInitialization();
		setTextStore(new GapTextStore());
		setLineTracker(new DefaultLineTracker());
		set(content);
	}
}
