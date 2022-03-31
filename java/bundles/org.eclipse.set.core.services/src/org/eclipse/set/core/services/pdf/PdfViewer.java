/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.pdf;

import java.nio.file.Path;

/**
 * A pdf viewer can render a pdf document and set different view modes.
 * 
 * @author Schaefer
 */
public interface PdfViewer {

	/**
	 * Refresh the view.
	 */
	void refresh();

	/**
	 * Show the pdf document at the given path
	 * 
	 * @param path
	 *            the path
	 */
	void show(Path path);
}
