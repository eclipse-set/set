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
import java.util.List;

import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.files.ToolboxFileAC;
import org.eclipse.set.basis.files.ToolboxFileRole;

/**
 * This service can provide toolbox files.
 * 
 * @author Schaefer
 */
public interface ToolboxFileService {

	/**
	 * Convert Toolboxfile Format
	 * 
	 * @param toolboxFile
	 *            original Toolboxfile
	 * @param role
	 *            toolbox file role
	 * @param tempDir
	 *            the temporary directory for provisional data
	 * @param format
	 *            convert to this format
	 * @return the new toolbox File
	 */
	ToolboxFile convertFormat(ToolboxFile toolboxFile, ToolboxFileRole role,
			Path tempDir, Format format);

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
	 *            the original toolbox file
	 * 
	 * @return the new toolbox file
	 */
	ToolboxFile create(ToolboxFile toolboxFile);

	/**
	 * @param category
	 *            the category
	 * 
	 * @return registered extensions for the given category
	 */
	List<String> extensionsForCategory(String category);

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

	/**
	 * Load an {@link AutoCloseable} toolbox file with the given path and role.
	 * 
	 * @param path
	 *            the path
	 * @param role
	 *            the role
	 * 
	 * @return the toolbox file for the path
	 */
	ToolboxFileAC loadAC(Path path, ToolboxFileRole role);
}
