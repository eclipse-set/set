/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.unittest.utils;

/**
 * 
 */
@SuppressWarnings("nls")
public enum TestFile {
	/**
	 * The P-Hausen version 1.10.0.1
	 */

	PPHN_1_10_0_1_20220517_PLANPRO(
			"PPHN_1.10.0.1_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg2.planpro"),
	/**
	 * The P-Hausen version 1.10.0.3
	 */
	PPHN_1_10_0_3_20220517_PLANPRO(
			"PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.planpro"),
	/**
	 * The ppmxl P-Hausen 1.10.0.1
	 */
	PPHN_1_10_0_1_20220517_PPXML(
			"PPHN_1.10.0.1_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg2.ppxml"),
	/**
	 * The single state P-Hausen
	 */
	SINGLE_STATE_PLAN("Info__2026-04-21_10-40.planpro");

	String fileName;

	private TestFile(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}
}
