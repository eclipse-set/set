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
 * Test fixed column by table sslf (it should fixed column A,B)
 * 
 * @author Truong
 *
 */
public class SslfFixedColumnTest extends SslfTest {

	/**
	 * Test fixed Column
	 */
	@Test
	void fixedColsTest() {
		this.fixedColsTest(0, 1);
	}

	@Disabled("Already test in another test case")
	@Override
	void testTableData() throws Exception {
		super.testTableData();
	}

}
