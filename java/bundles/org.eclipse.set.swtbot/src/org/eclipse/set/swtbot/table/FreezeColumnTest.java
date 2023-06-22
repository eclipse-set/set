/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.utils.table.BodyLayerStack;
import org.junit.jupiter.api.Test;

/**
 * Test Freeze Column function
 * 
 * @author truong
 */
public class FreezeColumnTest extends AbstractTableTest {

	@Override
	protected String getShortcut() {
		return "ssko";
	}

	@Override
	protected String getTableName() {
		return "Ssko (Schlosstabelle)";
	}

	/**
	 * Check, if table frist column is freeze or not
	 */
	@Test
	void isTableFirstColFreeze() {
		final NatTable natTable = nattableBot.widget;
		final ILayer layer = natTable.getLayer();
		assertInstanceOf(GridLayer.class, layer);
		final GridLayer gridLayer = (GridLayer) layer;

		assertInstanceOf(BodyLayerStack.class, gridLayer.getBodyLayer());
		final BodyLayerStack bodyLayerStack = (BodyLayerStack) gridLayer
				.getBodyLayer();
		final FreezeLayer freezeLayer = bodyLayerStack.getFreezeLayer();
		assertTrue(freezeLayer.isFrozen());
		assertEquals(0, freezeLayer.getTopLeftPosition().columnPosition);
		assertEquals(-1, freezeLayer.getTopLeftPosition().rowPosition);
		assertEquals(0, freezeLayer.getBottomRightPosition().columnPosition);
		assertEquals(-1, freezeLayer.getBottomRightPosition().rowPosition);
	}

	@Override
	void testTableData() throws Exception {
		return;
	}
}
