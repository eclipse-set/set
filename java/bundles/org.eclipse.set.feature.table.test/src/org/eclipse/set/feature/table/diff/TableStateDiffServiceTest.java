/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link TableStateDiffService}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
public class TableStateDiffServiceTest {
	// To simplify tests, all tests use a common leading object, but different
	// leading object indices
	private static Ur_Objekt LEADING_OBJECT = null;

	private static final Path SSKO_PHAUSEN_START = Paths.get("tm",
			"ssko_phausen_start.tm");
	private static final Path SSKO_PHAUSEN_ZIEL = Paths.get("tm",
			"ssko_phausen_ziel.tm");
	private static final Path SSKW_BOEHLEN_START = Paths.get("tm",
			"sskw_boehlen_start.tm");
	private static final Path SSKW_BOEHLEN_ZIEL = Paths.get("tm",
			"sskw_boehlen_ziel.tm");
	private static final Path SSKW_ERKNER_START = Paths.get("tm",
			"sskw_erkner_start.tm");
	private static final Path SSKW_ERKNER_ZIEL = Paths.get("tm",
			"sskw_erkner_ziel.tm");

	private TableStateDiffService service;

	/**
	 * Initialize test.
	 */
	@BeforeEach
	public void setUp() {
		final TableStateDiffService myService = new TableStateDiffService();
		service = myService;
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We analyze
	 * PLANPRO-2735.
	 */
	@Test
	public void test_PLANPRO_2735() {
		final Table start = TableExtensions.load(SSKW_ERKNER_START);
		final Table ziel = TableExtensions.load(SSKW_ERKNER_ZIEL);
		System.out.println(TableExtensions.toDebugString(start, 10));
		System.out.println(TableExtensions.toDebugString(ziel, 10));
		final Table diffTable = service.createDiffTable(start, ziel);
		System.out.println(TableExtensions.toDebugString(diffTable, 10));
		assertEquals("[43]/[]",
				TableExtensions.getPlainStringValue(diffTable, 44, 0));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We add
	 * delete some existing rows.
	 */
	@Test
	public void testDeletedRow() {
		final Table oldTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, oldTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 1, oldTable, "3", "4", "5");
		TableExtensions.addRow(LEADING_OBJECT, 2, oldTable, "6", "7", "8");
		TableExtensions.addRow(LEADING_OBJECT, 3, oldTable, "9", "10", "11");
		TableExtensions.addRow(LEADING_OBJECT, 4, oldTable, "12", "13", "14");

		final Table newTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, newTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 2, newTable, "6", "7", "8");
		TableExtensions.addRow(LEADING_OBJECT, 4, newTable, "12", "13", "14");

		final Table diffTable = service.createDiffTable(oldTable, newTable);

		System.out.println(TableExtensions.toDebugString(oldTable, 2));
		System.out.println(TableExtensions.toDebugString(newTable, 2));
		System.out.println(TableExtensions.toDebugString(diffTable, 10));

		assertEquals("0",
				TableExtensions.getPlainStringValue(diffTable, 0, "A"));
		assertEquals("7",
				TableExtensions.getPlainStringValue(diffTable, 2, "B"));
		assertEquals("14",
				TableExtensions.getPlainStringValue(diffTable, 4, "C"));
		assertEquals("[5]/[]",
				TableExtensions.getPlainStringValue(diffTable, 1, "C"));
		assertEquals("[9]/[]",
				TableExtensions.getPlainStringValue(diffTable, 3, "A"));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We move
	 * some existing rows.
	 */
	@Test
	public void testMovedRow1() {
		final Table oldTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, oldTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 1, oldTable, "3", "4", "5");
		TableExtensions.addRow(LEADING_OBJECT, 2, oldTable, "6", "7", "8");
		TableExtensions.addRow(LEADING_OBJECT, 3, oldTable, "9", "10", "11");
		TableExtensions.addRow(LEADING_OBJECT, 4, oldTable, "12", "13", "14");

		final Table newTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 3, newTable, "9", "10", "11");
		TableExtensions.addRow(LEADING_OBJECT, 0, newTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 2, newTable, "6", "7", "8");
		TableExtensions.addRow(LEADING_OBJECT, 1, newTable, "3", "4", "5");
		TableExtensions.addRow(LEADING_OBJECT, 4, newTable, "12", "13", "14");

		final Table diffTable = service.createDiffTable(oldTable, newTable);

		System.out.println(TableExtensions.toDebugString(oldTable, 2));
		System.out.println(TableExtensions.toDebugString(newTable, 2));
		System.out.println(TableExtensions.toDebugString(diffTable, 2));

		// we consider the order of the rows irrelevant
		assertTrue(TableExtensions.isEqual(diffTable, oldTable));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We move a
	 * single row.
	 */
	@Test
	public void testMovedRow2() {
		final Table oldTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, oldTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 1, oldTable, "3", "4", "5");
		TableExtensions.addRow(LEADING_OBJECT, 2, oldTable, "6", "7", "8");
		TableExtensions.addRow(LEADING_OBJECT, 3, oldTable, "9", "10", "11");
		TableExtensions.addRow(LEADING_OBJECT, 4, oldTable, "12", "13", "14");

		final Table newTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, newTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 2, newTable, "6", "7", "8");
		TableExtensions.addRow(LEADING_OBJECT, 3, newTable, "9", "10", "11");
		TableExtensions.addRow(LEADING_OBJECT, 4, newTable, "12", "13", "14");
		TableExtensions.addRow(LEADING_OBJECT, 1, newTable, "3", "4", "5");

		final Table diffTable = service.createDiffTable(oldTable, newTable);

		System.out.println(TableExtensions.toDebugString(oldTable, 2));
		System.out.println(TableExtensions.toDebugString(newTable, 2));
		System.out.println(TableExtensions.toDebugString(diffTable, 2));

		// we consider the order of the rows irrelevant
		assertTrue(TableExtensions.isEqual(diffTable, oldTable));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We add
	 * delete some existing rows.
	 */
	@Test
	public void testNewAndDeletedRow() {
		final Table oldTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, oldTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 1, oldTable, "3", "4", "5");
		TableExtensions.addRow(LEADING_OBJECT, 2, oldTable, "6", "7", "8");
		TableExtensions.addRow(LEADING_OBJECT, 3, oldTable, "9", "10", "11");
		TableExtensions.addRow(LEADING_OBJECT, 4, oldTable, "12", "13", "14");

		final Table newTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, newTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 5, newTable, "0", "1", "2");
		TableExtensions.addRow(LEADING_OBJECT, 2, newTable, "6", "7", "8");
		TableExtensions.addRow(LEADING_OBJECT, 4, newTable, "12", "13", "14");

		final Table diffTable = service.createDiffTable(oldTable, newTable);

		System.out.println(TableExtensions.toDebugString(oldTable, 2));
		System.out.println(TableExtensions.toDebugString(newTable, 2));
		System.out.println(TableExtensions.toDebugString(diffTable, 10));

		assertEquals("0",
				TableExtensions.getPlainStringValue(diffTable, 0, "A"));
		assertEquals("[]/[1]",
				TableExtensions.getPlainStringValue(diffTable, 5, "B"));
		assertEquals("7",
				TableExtensions.getPlainStringValue(diffTable, 2, "B"));
		assertEquals("14",
				TableExtensions.getPlainStringValue(diffTable, 4, "C"));
		assertEquals("[5]/[]",
				TableExtensions.getPlainStringValue(diffTable, 1, "C"));
		assertEquals("[9]/[]",
				TableExtensions.getPlainStringValue(diffTable, 3, "A"));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We analyze
	 * PLANPRO-2690.
	 */
	@Test
	public void testNewRow_PLANPRO_2690() {
		final Table start = TableExtensions.load(SSKW_BOEHLEN_START);
		final Table ziel = TableExtensions.load(SSKW_BOEHLEN_ZIEL);
		final Table diffTable = service.createDiffTable(start, ziel);
		System.out.println(TableExtensions.toDebugString(start, 10));
		System.out.println(TableExtensions.toDebugString(ziel, 10));
		System.out.println(TableExtensions.toDebugString(diffTable, 10));
		assertTrue(TableExtensions.getPlainStringValue(diffTable, 18, 4)
				.isEmpty());
		assertEquals("[x]/[]",
				TableExtensions.getPlainStringValue(diffTable, 0, 7));
		assertEquals("[]/[o]",
				TableExtensions.getPlainStringValue(diffTable, 18, 7));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We analyze
	 * PLANPRO-2702.
	 */
	@Test
	public void testNewRow_PLANPRO_2702() {
		final Table start = TableExtensions.load(SSKO_PHAUSEN_START);
		final Table ziel = TableExtensions.load(SSKO_PHAUSEN_ZIEL);
		System.out.println(TableExtensions.toDebugString(start, 10));
		System.out.println(TableExtensions.toDebugString(ziel, 10));
		final Table diffTable = service.createDiffTable(start, ziel);
		System.out.println(TableExtensions.toDebugString(diffTable, 10));
		assertTrue(
				TableExtensions.getPlainStringValue(diffTable, 5, 0).isEmpty());
		assertEquals("[]/[x]",
				TableExtensions.getPlainStringValue(diffTable, 5, 2));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We add some
	 * new rows.
	 */
	@Test
	public void testNewRow1() {
		final Table oldTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, oldTable, "0", "0", "0");
		TableExtensions.addRow(LEADING_OBJECT, 1, oldTable, "1", "1", "1");
		TableExtensions.addRow(LEADING_OBJECT, 2, oldTable, "2", "2", "2");
		final Table newTable = EcoreUtil.copy(oldTable);
		TableExtensions.addRow(LEADING_OBJECT, 3, newTable, "3", "3", "3");
		TableExtensions.addRow(LEADING_OBJECT, 4, newTable, "4", "4", "4");
		final Table diffTable = service.createDiffTable(oldTable, newTable);
		System.out.println(TableExtensions.toDebugString(oldTable, 1));
		System.out.println(TableExtensions.toDebugString(newTable, 1));
		System.out.println(TableExtensions.toDebugString(diffTable, 8));
		assertEquals("0",
				TableExtensions.getPlainStringValue(diffTable, 0, "A"));
		assertEquals("1",
				TableExtensions.getPlainStringValue(diffTable, 1, "B"));
		assertEquals("2",
				TableExtensions.getPlainStringValue(diffTable, 2, "C"));
		assertEquals("[]/[3]",
				TableExtensions.getPlainStringValue(diffTable, 3, "A"));
		assertEquals("[]/[3]",
				TableExtensions.getPlainStringValue(diffTable, 3, "B"));
		assertEquals("[]/[3]",
				TableExtensions.getPlainStringValue(diffTable, 3, "C"));
		assertEquals("[]/[4]",
				TableExtensions.getPlainStringValue(diffTable, 4, "B"));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We add some
	 * new rows in between.
	 */
	@Test
	public void testNewRow2() {
		final Table oldTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, oldTable, "0", "0", "0");
		TableExtensions.addRow(LEADING_OBJECT, 1, oldTable, "1", "1", "1");
		TableExtensions.addRow(LEADING_OBJECT, 2, oldTable, "2", "2", "2");
		final Table newTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, newTable, "0", "0", "0");
		TableExtensions.addRow(LEADING_OBJECT, 3, newTable, "3", "3", "3");
		TableExtensions.addRow(LEADING_OBJECT, 1, newTable, "1", "1", "1");
		TableExtensions.addRow(LEADING_OBJECT, 2, newTable, "2", "2", "2");
		TableExtensions.addRow(LEADING_OBJECT, 4, newTable, "4", "4", "4");
		final Table diffTable = service.createDiffTable(oldTable, newTable);
		System.out.println(TableExtensions.toDebugString(oldTable, 1));
		System.out.println(TableExtensions.toDebugString(newTable, 1));
		System.out.println(TableExtensions.toDebugString(diffTable, 8));
		assertEquals("0",
				TableExtensions.getPlainStringValue(diffTable, 0, "A"));
		assertEquals("1",
				TableExtensions.getPlainStringValue(diffTable, 1, "B"));
		assertEquals("2",
				TableExtensions.getPlainStringValue(diffTable, 2, "C"));
		assertEquals("[]/[3]",
				TableExtensions.getPlainStringValue(diffTable, 3, "A"));
		assertEquals("[]/[3]",
				TableExtensions.getPlainStringValue(diffTable, 3, "B"));
		assertEquals("[]/[3]",
				TableExtensions.getPlainStringValue(diffTable, 3, "C"));
		assertEquals("[]/[4]",
				TableExtensions.getPlainStringValue(diffTable, 4, "B"));
	}

	/**
	 * Test method for
	 * {@link TableStateDiffService#createDiffTable(Table, Table)} . We change
	 * the value of two table cells.
	 */
	@Test
	public void testSimpleChange() {
		final Table oldTable = TableExtensions.create("testTable", "A", "B",
				"C");
		TableExtensions.addRow(LEADING_OBJECT, 0, oldTable, "0", "0", "0");
		TableExtensions.addRow(LEADING_OBJECT, 1, oldTable, "1", "1", "1");
		TableExtensions.addRow(LEADING_OBJECT, 2, oldTable, "2", "2", "2");
		final Table newTable = EcoreUtil.copy(oldTable);
		TableExtensions.set(newTable, 0, "A", "A");
		TableExtensions.set(newTable, 1, "B", "B");
		final Table diffTable = service.createDiffTable(oldTable, newTable);
		System.out.println(TableExtensions.toDebugString(oldTable, 1));
		System.out.println(TableExtensions.toDebugString(newTable, 1));
		System.out.println(TableExtensions.toDebugString(diffTable, 10));
		assertEquals("0",
				TableExtensions.getPlainStringValue(oldTable, 0, "A"));
		assertEquals("A",
				TableExtensions.getPlainStringValue(newTable, 0, "A"));
		assertEquals("B",
				TableExtensions.getPlainStringValue(newTable, 1, "B"));
		assertEquals("[0]/[A]",
				TableExtensions.getPlainStringValue(diffTable, 0, "A"));
		assertEquals("0",
				TableExtensions.getPlainStringValue(diffTable, 0, "B"));
		assertEquals("[1]/[B]",
				TableExtensions.getPlainStringValue(diffTable, 1, "B"));
	}
}
