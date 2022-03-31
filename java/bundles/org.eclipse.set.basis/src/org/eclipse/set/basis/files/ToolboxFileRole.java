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
	 * The ToolboxFile is used as a secondary planning
	 */
	SECONDARY_PLANNING,

	/**
	 * The ToolboxFile is used to start a session (default)
	 */
	SESSION,

	/**
	 * The ToolboxFile is used as a temporary integration
	 */
	TEMPORARY_INTEGRATION;

	/**
	 * Translates the role name into a directory name
	 * 
	 * @return the directory name
	 */
	public String toDirectoryName() {
		if (this == EXPORT) {
			return ToolboxConstants.TOOLBOX_DIRECTORY_NAME_EXPORT;
		} else if (this == IMPORT_INITIAL_STATE) {
			return ToolboxConstants.TOOLBOX_DIRECTORY_NAME_IMPORT_INITIAL_STATE;
		} else if (this == IMPORT_FINAL_STATE) {
			return ToolboxConstants.TOOLBOX_DIRECTORY_NAME_IMPORT_FINAL_STATE;
		} else if (this == SECONDARY_PLANNING) {
			return ToolboxConstants.TOOLBOX_DIRECTORY_NAME_SECONDARY_PLANNING;
		} else if (this == TEMPORARY_INTEGRATION) {
			return ToolboxConstants.TOOLBOX_DIRECTORY_NAME_TEMPORARY_INTEGRATION;
		}
		return ToolboxConstants.TOOLBOX_DIRECTORY_NAME_SESSION;
	}
}