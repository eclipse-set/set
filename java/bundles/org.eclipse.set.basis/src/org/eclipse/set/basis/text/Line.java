/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.text;

import org.eclipse.jface.text.IDocument;

/**
 * A Line in a {@link IDocument}.
 * 
 * @author Schaefer
 */
public class Line {

	private final IDocument document;
	private int number;

	/**
	 * @param document
	 *            the document
	 * @param number
	 *            the line number
	 */
	public Line(final IDocument document, final int number) {
		this.document = document;
		this.number = number;
	}

	/**
	 * @return the document
	 */
	public IDocument getDocument() {
		return document;
	}

	/**
	 * @return the line number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the line number
	 */
	public void setNumber(final int number) {
		this.number = number;
	}
}
