/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

/**
 * Display lines of text.
 * 
 * @author Schaefer
 */
public interface LineTextViewer {

	/**
	 * Selects a line in the viewer
	 * 
	 * @param lineNumber
	 *            the line number to select
	 */
	public void selectLine(final int lineNumber);

	/**
	 * @param parent
	 *            the parent composite
	 * @param lines
	 *            the lines of text
	 */
	void init(Composite parent, List<String> lines);

	/**
	 * @param lines
	 *            the new lines
	 */
	void update(List<String> lines);
}
