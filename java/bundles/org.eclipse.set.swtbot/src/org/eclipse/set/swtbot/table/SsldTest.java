/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.table;

/**
 * @author truong
 *
 */
public class SsldTest extends AbstractTableTest {

	@Override
	protected String getShortcut() {
		return "ssld";
	}

	@Override
	protected String getTableName() {
		return "Ssld (Durchrutschweg- und Gefahrpunkttabelle)";
	}

}
