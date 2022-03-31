/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.jface.text.rules.ICharacterScanner

/**
 * Extensions for {@link ICharacterScanner}.
 * 
 * @author Schaefer
 */
class ICharacterScannerExtensions {

	/**
	 * Read the given text from the character scanner. If the text is not
	 * found, the scanner will be unread until the stating position.
	 *  
	 * @param scanner this character scanner
	 * @param text the text to find
	 */
	static def boolean read(ICharacterScanner scanner, String text) {
		val length = text.length
		val chars = text.toCharArray

		for (var int i = 0; i < length; i++) {
			val read = scanner.read
			if (read !== chars.get(i)) {
				scanner.unread(i + 1)
				return false
			}
		}
		return true
	}

	/**
	 * Rewinds the scanner by the given amount of characters.
	 * 
	 * @param scanner this character scanner
	 */
	static def void unread(ICharacterScanner scanner, int characters) {
		for (var int i = 0; i < characters; i++) {
			scanner.unread
		}
	}
}
