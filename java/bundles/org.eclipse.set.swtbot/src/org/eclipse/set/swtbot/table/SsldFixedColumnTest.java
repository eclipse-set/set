/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.table;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Test fixed column by table ssld (it should fixed column A,B,C)
 * 
 * @author Truong
 *
 */
public class SsldFixedColumnTest extends SsldTest {

	/**
	 * Test fixed Column
	 */
	@Test
	void fixedColsTest() {
		this.fixedColsTest(0, 1, 2);
	}

	@Disabled("Already test in another test case")
	@Override
	void testTableData() throws Exception {
		super.testTableData();
	}
}
