/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.set.basis.text.Line
import org.eclipse.jface.text.BadLocationException

/**
 * Extensions for {@link Line}.
 * 
 * @author Schaefer
 */
class LineExtensions {

	/**
	 * @param line this line
	 * 
	 * @return the offset this line
	 */
	static def int getOffset(Line line) throws BadLocationException {
		return line.document.getLineOffset(line.number)
	}

	/**
	 * @param line this line
	 * 
	 * @return the length of this line
	 */
	static def int getLength(Line line) throws BadLocationException {
		return line.document.getLineLength(line.number)
	}

	/**
	 * @param line this line
	 * 
	 * @return the text of this line
	 */
	static def String getText(Line line) throws BadLocationException {
		return line.document.get(line.offset, line.length)
	}
}
