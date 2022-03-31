/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.basis.files;

import java.io.Closeable;
import java.io.IOException;

/**
 * An {@link AutoCloseable} {@link ToolboxFile}.
 * 
 * @author Schaefer
 */
public class ToolboxFileAC implements Closeable {

	private final ToolboxFile toolboxFile;

	/**
	 * @param toolboxFile
	 *            the toolbox file
	 */
	public ToolboxFileAC(final ToolboxFile toolboxFile) {
		this.toolboxFile = toolboxFile;
	}

	@Override
	public void close() throws IOException {
		toolboxFile.close();
	}

	/**
	 * @return the toolbox file
	 */
	public ToolboxFile get() {
		return toolboxFile;
	}
}
