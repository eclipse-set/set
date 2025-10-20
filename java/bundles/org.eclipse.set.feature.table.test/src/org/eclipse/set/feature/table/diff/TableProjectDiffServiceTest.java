/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.diff;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.model.planpro.Basisobjekte.Identitaet_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.PlanCompareRow;
import org.eclipse.set.model.tablemodel.PlanCompareRowType;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link TableProjectDiffService}
 */
@SuppressWarnings("nls")
public class TableProjectDiffServiceTest {

	private static Ur_Objekt createLeadingObject(final String guid) {
		final Ur_Objekt mockObj = Mockito.mock(Ur_Objekt.class);
		final Identitaet_TypeClass identitaetTypeClass = BasisobjekteFactory.eINSTANCE
				.createIdentitaet_TypeClass();
		identitaetTypeClass.setWert(guid);
		Mockito.when(mockObj.getIdentitaet()).thenReturn(identitaetTypeClass);
		return mockObj;
	}

	private TableProjectDiffService service;
	private Table mainTable;
	private Table compareTable;
	private Table diffTable;

	@BeforeEach
	void setUp() {
		service = new TableProjectDiffService();
	}

	@Test
	void testDiffTableNotExistCompareTableCellContent() {
		givenMainProjectTable();
		assertNotNull(mainTable);
		whenCompareTwoMainTable();
		thenDiffTableNotExistCompareTableCellContent();
	}

	@Test
	void testDiffTableExistCompareTableCellContent() {
		givenMainProjectTable();
		assertNotNull(mainTable);
		givenCompareProjectTable();
		assertNotNull(compareTable);

		assertDoesNotThrow(this::whenCreateDiffTable);
		thenDiffTableExistCompareTableCellContent();
	}

	@Test
	void testDiffTableExistChangedGuidRow() {
		givenMainProjectTable();
		assertNotNull(mainTable);
		givenCompareProjectTable();
		assertNotNull(compareTable);

		whenCreateDiffTableWithGuidChangedRowAtMainTable();
		thenDiffTableExsitPlanCompareRowChangedGuidRow();
	}

	@Test
	void testAddNewRowAtMainTable() {
		givenMainProjectTable();
		assertNotNull(mainTable);
		givenCompareProjectTable();
		assertNotNull(compareTable);

		whenCreateDiffTableWithNewRowAtMainTable();
		thenDiffTableExistPlanCompareRowNewRow();
	}

	@Test
	void testDeleteRowAtMainTable() {
		givenMainProjectTable();
		assertNotNull(mainTable);
		givenCompareProjectTable();
		assertNotNull(compareTable);

		whenCreateDiffTableWithDeleteRowAtMainTable();
		thenDiffTableExistPlanCompareRowDeleteRow();
	}

	private void whenCreateDiffTableWithDeleteRowAtMainTable() {
		mainTable.getTablecontent().getRowgroups().remove(2);
		diffTable = service.createDiffTable(mainTable, compareTable);
	}

	private void thenDiffTableExistPlanCompareRowDeleteRow() {
		assertNotNull(diffTable);

		final PlanCompareRow planCompareRow = TableExtensions
				.getTableRows(diffTable)
				.stream()
				.filter(PlanCompareRow.class::isInstance)
				.map(PlanCompareRow.class::cast)
				.findFirst()
				.orElse(null);
		assertNotNull(planCompareRow);
		assertEquals(PlanCompareRowType.REMOVED_ROW,
				planCompareRow.getRowType());

		assertTrue(planCompareRow.getCells()
				.stream()
				.map(TableCell::getContent)
				.allMatch(CompareTableCellContent.class::isInstance));

		assertTrue(planCompareRow.getCells()
				.stream()
				.map(TableCell::getContent)
				.map(CompareTableCellContent.class::cast)
				.allMatch(content -> content.getMainPlanCellContent() == null));
	}

	private void whenCreateDiffTableWithNewRowAtMainTable() {
		TableExtensions.addRow(createLeadingObject("5"), mainTable, "13", "15",
				"16");
		diffTable = service.createDiffTable(mainTable, compareTable);
	}

	private void whenCompareTwoMainTable() {
		diffTable = service.createDiffTable(mainTable,
				EcoreUtil.copy(mainTable));
	}

	private void thenDiffTableNotExistCompareTableCellContent() {
		final List<TableRow> tableRows = TableExtensions
				.getTableRows(diffTable);
		final boolean noneMatch = tableRows.stream()
				.flatMap(row -> row.getCells().stream())
				.noneMatch(cell -> cell
						.getContent() instanceof CompareTableCellContent);
		assertTrue(noneMatch);
	}

	private void whenCreateDiffTable() {
		diffTable = service.createDiffTable(mainTable, compareTable);
	}

	private void thenDiffTableExistCompareTableCellContent() {
		assertNotNull(diffTable);
		final List<TableRow> tableRows = TableExtensions
				.getTableRows(diffTable);
		final boolean existCompareTableCellContent = tableRows.stream()
				.flatMap(row -> row.getCells().stream())
				.anyMatch(cell -> cell
						.getContent() instanceof CompareTableCellContent);
		assertTrue(existCompareTableCellContent);

		assertInstanceOf(CompareTableCellContent.class,
				tableRows.get(2).getCells().get(0).getContent());
		assertInstanceOf(CompareTableCellContent.class,
				tableRows.get(3).getCells().get(1).getContent());
		assertEquals("10/15",
				TableExtensions.getPlainStringValue(diffTable, 3, 1));
	}

	private void whenCreateDiffTableWithGuidChangedRowAtMainTable() {
		final RowGroup firstGroup = mainTable.getTablecontent()
				.getRowgroups()
				.get(0);
		firstGroup.setLeadingObject(createLeadingObject("10"));
		diffTable = service.createDiffTable(mainTable, compareTable);

	}

	private void thenDiffTableExsitPlanCompareRowChangedGuidRow() {
		assertNotNull(diffTable);
		final List<TableRow> tableRows = TableExtensions
				.getTableRows(diffTable);
		final PlanCompareRow changedGuidRow = tableRows.stream()
				.filter(PlanCompareRow.class::isInstance)
				.map(PlanCompareRow.class::cast)
				.findFirst()
				.orElse(null);
		assertNotNull(changedGuidRow);
		assertEquals(PlanCompareRowType.CHANGED_GUID_ROW,
				changedGuidRow.getRowType());

		changedGuidRow.getCells().forEach(cell -> {
			final CellContent cellContent = cell.getContent();
			assertInstanceOf(CompareTableCellContent.class, cellContent);

			final CellContent mainCellContent = ((CompareTableCellContent) cellContent)
					.getMainPlanCellContent();
			final CellContent compareCellContent = ((CompareTableCellContent) cellContent)
					.getComparePlanCellContent();
			assertEquals(
					CellContentExtensions.getPlainStringValue(mainCellContent),
					CellContentExtensions
							.getPlainStringValue(compareCellContent));
		});
	}

	private void thenDiffTableExistPlanCompareRowNewRow() {
		assertNotNull(diffTable);

		final PlanCompareRow planCompareRow = TableExtensions
				.getTableRows(diffTable)
				.stream()
				.filter(PlanCompareRow.class::isInstance)
				.map(PlanCompareRow.class::cast)
				.findFirst()
				.orElse(null);
		assertNotNull(planCompareRow);
		assertEquals(PlanCompareRowType.NEW_ROW, planCompareRow.getRowType());

		assertTrue(planCompareRow.getCells()
				.stream()
				.map(TableCell::getContent)
				.allMatch(CompareTableCellContent.class::isInstance));

		assertTrue(planCompareRow.getCells()
				.stream()
				.map(TableCell::getContent)
				.map(CompareTableCellContent.class::cast)
				.allMatch(content -> content
						.getComparePlanCellContent() == null));
	}

	private void givenMainProjectTable() {
		mainTable = TableExtensions.create("mainTestTable", "A", "B", "C");
		TableExtensions.addRow(createLeadingObject("1"), mainTable, "0", "1",
				"2");
		TableExtensions.addRow(createLeadingObject("2"), mainTable, "3", "4",
				"5");
		TableExtensions.addRow(createLeadingObject("3"), mainTable, "6", "7",
				"8");
		TableExtensions.addRow(createLeadingObject("4"), mainTable, "9", "10",
				"11");
	}

	private void givenCompareProjectTable() {
		compareTable = TableExtensions.create("mainTestTable", "A", "B", "C");
		TableExtensions.addRow(createLeadingObject("1"), compareTable, "0", "1",
				"2");
		TableExtensions.addRow(createLeadingObject("2"), compareTable, "3", "4",
				"5");
		TableExtensions.addRow(createLeadingObject("3"), compareTable, "12",
				"7", "8");
		TableExtensions.addRow(createLeadingObject("4"), compareTable, "9",
				"15", "11");
	}
}
