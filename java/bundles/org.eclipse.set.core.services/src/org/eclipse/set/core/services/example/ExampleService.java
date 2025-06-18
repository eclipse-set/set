/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.example;

import java.util.List;

import org.eclipse.set.basis.RecentOpenFile;

/**
 * Provides access to the example folder.
 * 
 * @author Schaefer
 */
public interface ExampleService {

	/**
	 * Key used to locate the {@link RecentOpenFile} within the transient data.
	 */
	String EXAMPLE_FILE_KEY = "example.file.key"; //$NON-NLS-1$

	/**
	 * @return the example files
	 */
	List<RecentOpenFile> getExampleFiles();
}
