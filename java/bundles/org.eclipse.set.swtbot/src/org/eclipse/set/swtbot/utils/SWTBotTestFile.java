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

/**
 * The test file enum
 */
public enum SWTBotTestFile implements TestFile {
	PPHN_1_10_0_1("pphn_1_10_0_1",
			"PPHN_1.10.0.1_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg2.planpro"),
	PPHN_1_10_0_3("pphn_1_10_0_3",
			"PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.planpro");

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
