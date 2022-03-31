/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import org.eclipse.jface.text.rules.ICharacterScanner;

/**
 * A simple scanner implementation.
 * 
 * @author Schaefer
 */
public class StringScanner implements ICharacterScanner {

	private int column = 0;
	private final String text;

	/**
	 * @param text
	 *            the text to be scanned
	 */
	public StringScanner(final String text) {
		this.text = text;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public char[][] getLegalLineDelimiters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int read() {
		if (column > text.length() - 1) {
			return EOF;
		}
		final char c = text.charAt(column);
		column++;
		return c;
	}

	@Override
	public void unread() {
		if (column > 0) {
			column--;
		}
	}
}
