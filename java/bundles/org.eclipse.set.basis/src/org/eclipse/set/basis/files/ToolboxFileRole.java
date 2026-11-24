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
	 * The ToolboxFile is used to start a session (default)
	 */
	SESSION(1),

	/**
	 * The ToolboxFile is used as a compare planning
	 */
	COMPARE_PLANNING(2),

	/**
	 * The ToolboxFile is used as a reference plan
	 */
	REFERENCE_BASIS_PLAN(3),

	/**
	 * The Toolboxfile is used as a equivalent plan
	 */
	EQUIVALENT_PLAN(4),

	/**
	 * The ToolboxFile is used to be exported
	 */
	EXPORT(5),

	/**
	 * The ToolboxFile is used to be imported into the final state
	 */
	IMPORT_FINAL_STATE(6),

	/**
	 * The ToolboxFile is used to be imported into the initial state
	 */
	IMPORT_INITIAL_STATE(7),

	/**
	 * The ToolboxFile is used as a first planning to import
	 */
	FIRST_PLANNING_TO_IMPORT(8),

	/**
	 * The toolboxFile is used as a second planning to import
	 */
	SECOND_PLANNING_TO_IMPORT(9),

	/**
	 * The ToolboxFile is used as a temporary integration
	 */
	TEMPORARY_INTEGRATION(10),
	/**
	* 
	*/
	MERGE_PLANNING(11);

	int priority;

	private ToolboxFileRole(final int priority) {
		this.priority = priority;
	}

	// @Override
	// public int compareTo(final ToolboxFileRole another) {
	// return Integer.compare(priority, another.priority);
	// }

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
			case TEMPORARY_INTEGRATION -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_TEMPORARY_INTEGRATION;
			case MERGE_PLANNING -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_MERGE_PLANNING;
			case REFERENCE_BASIS_PLAN -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_REFERENCE_BASIS_PLAN;
			case EQUIVALENT_PLAN -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_REFERENCE_BASIS_PLAN;
			default -> ToolboxConstants.TOOLBOX_DIRECTORY_NAME_SESSION;
		};
	}
}