/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.files;

import java.nio.file.Path;
import java.util.Collection;

import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.files.ToolboxFileExtension;
import org.eclipse.set.basis.files.ToolboxFileRole;

/**
 * Support for a concrete toolbox file format.
 * 
 * @author Schaefer
 */
public interface ToolboxFileFormatService {

	/**
	 * Create the toolbox file with the given format, but do not load any
	 * content.
	 * 
	 * @param format
	 *            the format
	 * @param role
	 *            the role
	 * 
	 * @return the toolbox file with the given format
	 */
	ToolboxFile create(Format format, final ToolboxFileRole role);

	/**
	 * Create a new toolbox file from the given toolbox file.
	 * 
	 * @param toolboxFile
	 *            the toolbox file
	 * 
	 * @return the new toolbox file
	 */
	ToolboxFile create(ToolboxFile toolboxFile);

	/**
	 * @param category
	 *            the category
	 * 
	 * @return the toolbox file extensions
	 */
	Collection<ToolboxFileExtension> extensionsForCategory(String category);

	/**
	 * @param format
	 *            the format
	 * 
	 * @return whether this format service supports the given format
	 */
	boolean isSupported(Format format);

	/**
	 * @param path
	 *            the path
	 * 
	 * @return whether this format service supports the given path
	 */
	boolean isSupported(Path path);

	/**
	 * @param toolboxFile
	 *            the toolbox file
	 * 
	 * @return whether this format service supports the given toolbox file
	 */
	boolean isSupported(ToolboxFile toolboxFile);

	/**
	 * Load the toolbox file with the given path and role.
	 * 
	 * @param path
	 *            the path
	 * @param role
	 *            the role
	 * 
	 * @return the toolbox file for the path
	 */
	ToolboxFile load(Path path, ToolboxFileRole role);
}
