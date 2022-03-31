/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import org.eclipse.set.basis.extensions.ICharacterScannerExtensions;

/**
 * A rule to detect xml tags.
 * 
 * @author Schaefer
 */
public class TagRule implements IRule {

	private boolean isOpen = false;

	private final IToken successToken;

	/**
	 * @param successToken
	 *            the success token
	 */
	public TagRule(final IToken successToken) {
		this.successToken = successToken;
	}

	@Override
	public IToken evaluate(final ICharacterScanner scanner) {
		if (ICharacterScannerExtensions.read(scanner, ">")) { //$NON-NLS-1$
			isOpen = false;
			return successToken;
		}
		if (ICharacterScannerExtensions.read(scanner, "?>")) { //$NON-NLS-1$
			isOpen = false;
			return successToken;
		}
		if (ICharacterScannerExtensions.read(scanner, "/>")) { //$NON-NLS-1$
			isOpen = false;
			return successToken;
		}
		if (ICharacterScannerExtensions.read(scanner, "-->")) { //$NON-NLS-1$
			isOpen = false;
			return successToken;
		}
		if (ICharacterScannerExtensions.read(scanner, "<")) { //$NON-NLS-1$
			findEnd(scanner);
			return successToken;
		}
		return Token.UNDEFINED;
	}

	/**
	 * @return whether a tag is currently open
	 */
	public boolean isOpen() {
		return isOpen;
	}

	private void findEnd(final ICharacterScanner scanner) {
		for (int c = scanner.read(); !isEnd(c); c = scanner.read()) {
			// move the scanner to the end
		}
	}

	private boolean isEnd(final int c) {
		if (c == ICharacterScanner.EOF) {
			isOpen = true;
			return true;
		}
		if (c == '>') {
			isOpen = false;
			return true;
		}
		if (Character.isWhitespace(c)) {
			isOpen = true;
			return true;
		}
		isOpen = true;
		return false;
	}
}
