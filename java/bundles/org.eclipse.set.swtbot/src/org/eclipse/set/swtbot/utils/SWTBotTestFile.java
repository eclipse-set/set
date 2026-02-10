/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.swtbot.utils;

import org.eclipse.set.basis.constants.ToolboxConstants;

/**
 * The test file enum
 */
public enum SWTBotTestFile implements TestFile {
	/**
	 * PHausen 1.10.0.1
	 */
	PPHN_1_10_0_1("pphn_1_10_0_1", ToolboxConstants.EXAMPLE_PROJECT_1_10_0_1),

	/**
	 * PHausen 1.10.0.3
	 */
	PPHN_1_10_0_3("pphn_1_10_0_3", ToolboxConstants.EXAMPLE_PROJECT_1_10_0_3);

	private final String fullName;
	private final String shortName;

	SWTBotTestFile(final String shortName, final String fullName) {
		this.shortName = shortName;
		this.fullName = fullName;
	}

	/**
	 * @return the full name of test file
	 */
	@Override
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the short name of test file
	 */
	@Override
	public String getShortName() {
		return shortName;
	}

}
