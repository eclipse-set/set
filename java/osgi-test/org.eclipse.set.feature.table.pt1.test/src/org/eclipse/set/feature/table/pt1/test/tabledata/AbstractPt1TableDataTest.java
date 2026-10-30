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
package org.eclipse.set.feature.table.pt1.test.tabledata;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.internal.TableServiceContextFunction;
import org.eclipse.set.feature.table.internal.TableServiceImpl;
import org.eclipse.set.feature.table.pt1.test.utils.PtTable;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.osgi.test.utils.AbstractOsgiTableTest;
import org.eclipse.set.osgi.test.utils.AbstractOsgiTest;
import org.eclipse.set.osgi.test.utils.OsgiTestTestFile;
import org.eclipse.set.osgi.test.utils.TestFile;
import org.eclipse.set.services.table.TableDiffService;
import org.eclipse.set.services.table.TableDiffService.TableCompareType;
import org.eclipse.set.services.table.TableStatus;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * 
 */
public abstract class AbstractPt1TableDataTest extends AbstractOsgiTableTest {

	private static final String TEST_RESOURCE_DIR = "res/";

	protected static Stream<Arguments> providesPtTable() {
		return PtTable.tablesToTest.stream().map(Arguments::of);
	}

	private ToolboxViewModelService toolboxViewModelService;

	protected Map<TableCompareType, TableDiffService> diffServiceMap;

	protected Map<TableInfo, PlanPro2TableTransformationService> modelServiceMap;

	protected TableServiceImpl tableService;

	/**
	 * @return the location of reference directory
	 */
	@Override
	public String getReferenceDir() {
		return TEST_RESOURCE_DIR + "table_reference/"
				+ getTestFile().getShortName() + "/";
	}

	/**
	 * @param table
	 *            the tested table
	 * @return the minimal table header
	 */
	@Override
	public String getTableCSVHeader(final Table table) {
		final List<ColumnDescriptor> columns = TableExtensions
				.getColumns(table);
		final String header = columns.stream()
				.map(ColumnDescriptor::getColumnPosition)
				.collect(Collectors.joining(CSV_DELIMITER));
		return ROW_INDEX_COL + CSV_DELIMITER + header + System.lineSeparator();
	}

	/**
	 * Call execute test class to get test resource location
	 * 
	 * @return execute test class
	 */
	@Override
	public Class<? extends AbstractOsgiTest> getTestResourceClass() {
		// Class for get the default test resource
		if (getTestFile().equals(OsgiTestTestFile.PPHN_1_10_0_3)) {
			return AbstractPt1TableDataTest.class;
		}
		return getClass();
	}

	/**
	 * @return the table reference file name
	 */
	@Override
	public abstract String getTestTableReferenceName();

	private void givenToolboxViewModelService() {
		toolboxViewModelService = Mockito.mock(ToolboxViewModelService.class);
		Mockito.when(toolboxViewModelService.getSession())
				.thenReturn(Optional.of(modelSession));

	}

	protected abstract List<IContextFunction> getContextFunctions();

	@Override
	@SuppressWarnings("boxing")
	protected String getErrorMessage(final int columnIndex, final int rowIndex,
			final String expectedValue, final String actualValue) {
		return String.format(
				"%s at row: %d, column: %d. ExpectedValue: %s - ActualValue: %s",
				getTestTableReferenceName(), rowIndex, columnIndex,
				expectedValue, actualValue);
	}

	protected abstract PtTable getTableToTest();

	@Override
	protected TestFile getTestFile() {
		return OsgiTestTestFile.PPHN_1_10_0_3;
	}

	protected void givenReferenceCSV(final PtTable tableInfo) {
		referenceData = loadReferenceFile(tableInfo.shortcut());
	}

	protected void givenTableService() throws SecurityException,
			IllegalArgumentException, IllegalAccessException {
		tableService = Mockito.spy(new TableServiceImpl());
		FieldUtils.writeField(tableService, "modelServiceMap", modelServiceMap,
				true);
		FieldUtils.writeField(tableService, "diffServiceMap", diffServiceMap,
				true);
		FieldUtils.writeField(tableService, "cacheService", getCacheService(),
				true);
		FieldUtils.writeField(tableService, "sessionService",
				getSessionService(), true);
		FieldUtils.writeField(tableService, "broker", broker, true);
		Mockito.doReturn(getCacheService())
				.when(tableService)
				.getCacheService();

	}

	protected void givenTestTable(final PtTable table,
			final TableType tableType, final Set<String> controlAreaIds) {
		final Optional<TableInfo> optional = modelServiceMap.keySet()
				.stream()
				.filter(info -> info.shortcut()
						.equalsIgnoreCase(table.shortcut()))
				.findFirst();
		if (optional.isEmpty()) {
			throw new IllegalArgumentException(
					"TableTransformService not exist: " + table.shortcut());
		}
		testee = tableService.createDiffTable(optional.get(), tableType,
				controlAreaIds, true);
	}

	@Override
	protected void mockServicesMethode(
			final MockedStatic<Services> mockServices) {
		super.mockServicesMethode(mockServices);
		mockServices.when(Services::getToolboxViewModelService)
				.thenReturn(toolboxViewModelService);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setup() throws Exception {
		super.setup();
		final Optional<IContextFunction> tableServiceContextFunction = getContextFunctions()
				.stream()
				.filter(cf -> cf.getClass()
						.getName()
						.endsWith("TableServiceContextFunction"))
				.findFirst();
		if (tableServiceContextFunction.isEmpty()) {
			throw new RuntimeException("Cant find TableServiceContextFunction");
		}
		final Field modelServiceMapField = FieldUtils.getDeclaredField(
				TableServiceContextFunction.class, "modelServiceMap", true);
		modelServiceMap = (Map<TableInfo, PlanPro2TableTransformationService>) modelServiceMapField
				.get(tableServiceContextFunction.get());
		// Ssls is moment in development, therefore no need to tested
		final Optional<TableInfo> sslsTableInfo = modelServiceMap.keySet()
				.stream()
				.filter(tableInfo -> tableInfo.shortcut()
						.equalsIgnoreCase("ssls"))
				.findFirst();
		if (sslsTableInfo.isPresent()) {
			modelServiceMap.remove(sslsTableInfo.get());
		}

		final Field diffServiceMapsField = FieldUtils.getDeclaredField(
				TableServiceContextFunction.class, "diffServiceMap", true);
		diffServiceMap = (Map<TableCompareType, TableDiffService>) diffServiceMapsField
				.get(tableServiceContextFunction.get());
		givenToolboxViewModelService();
	}

	protected void thenExpectTableDataEqualReferenceCSV() {
		assertDoesNotThrow(
				() -> compareValue(0, TableExtensions.getTableRows(testee)));
	}

	protected void thenExpectTableStatusRelevant() {
		final Map<TableInfo, TableStatus> tablesStatus = tableService
				.getTablesStatus(Pt1TableCategory
						.getCategoryEnum(getTableToTest().category()));
		final Optional<TableStatus> status = tablesStatus.entrySet()
				.stream()
				.filter(entry -> entry.getKey()
						.nameInfo()
						.getShortName()
						.equalsIgnoreCase(getTableToTest().shortcut()))
				.map(Entry::getValue)
				.findFirst();
		assertTrue(status.isPresent());
		assertFalse(status.get().isNonTransformable());
		assertFalse(status.get().isContainsErrors());
		isTableEmpty = status.get().isEmpty();
	}

	@SuppressWarnings("boxing")
	protected void thenRowAndColumnCountEqualReferenceCSV() {
		final int nattableColumnCount = TableExtensions.getColumns(testee)
				.size();
		final int referenceColumnCount = referenceData.get(0).size() - 1;
		assertEquals(referenceColumnCount, nattableColumnCount,
				() -> String.format("%s expected column count: %d but was: %d",
						getTestTableReferenceName(), referenceColumnCount,
						nattableColumnCount));
		final long nattableRowCount = getTableRowCount();
		final long referenceRowCount = referenceData.size();
		assertEquals(referenceRowCount, nattableRowCount,
				() -> String.format("%s expected row count: %d but was: %d",
						getTestTableReferenceName(), referenceRowCount,
						nattableRowCount));
	}

	protected void whenExistReferenceCSV() {
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
	}

	protected void whenTableTransformComplete() {
		assertNotNull(testee);
	}
}
