/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.files;

import org.eclipse.set.basis.constants.ToolboxConstants;

/**
 * The Roles in which a ToolboxFile can be used.
 * 
 * @author peters.nils
 */
public enum ToolboxFileRole {

	/**
	 * The ToolboxFile is used to be exported
	 */
	EXPORT,

	/**
	 * The ToolboxFile is used to be imported into the final state
	 */
	IMPORT_FINAL_STATE,

	/**
	 * The ToolboxFile is used to be imported into the initial state
	 */
	IMPORT_INITIAL_STATE,

	/**
	 * The ToolboxFile is used as a first planning to import
	 */
	FIRST_PLANNING_TO_IMPORT,

	/**
	 * The ToolboxFile is used as a compare planning
	 */
	COMPARE_PLANNING,

	/**
	 * The toolboxFile is used as a second planning to import
	 */
	SECOND_PLANNING_TO_IMPORT,

	/**
	 * The ToolboxFile is used to start a session (default)
	 */
	SESSION,

	/**
	 * The ToolboxFile is used as a temporary integration
	 */
	TEMPORARY_INTEGRATION,;

	/**
	 * Translates the role name into a directory name
	 * 
	 * @return the directory name
	 */
	public String toDirectoryName() {
		return switch (this) {
			case EXPORT -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_EXPORT;
			case IMPORT_INITIAL_STATE -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_IMPORT_INITIAL_STATE;
			case IMPORT_FINAL_STATE -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_IMPORT_FINAL_STATE;
			case FIRST_PLANNING_TO_IMPORT -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_FIRST_PLANNING_TO_IMPORT;
			case SECOND_PLANNING_TO_IMPORT -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_SECOND_PLANNING_TO_IMPORT;
			case COMPARE_PLANNING -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_COMPARE_PLANNING;
			default -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_SESSION;
		};
	}
}