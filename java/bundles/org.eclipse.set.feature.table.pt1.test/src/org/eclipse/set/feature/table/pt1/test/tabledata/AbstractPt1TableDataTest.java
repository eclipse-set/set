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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.internal.TableServiceContextFunction;
import org.eclipse.set.feature.table.internal.TableServiceImpl;
import org.eclipse.set.feature.table.pt1.test.Activator;
import org.eclipse.set.feature.table.pt1.test.utils.Pt1TableTestFile;
import org.eclipse.set.feature.table.pt1.test.utils.PtTable;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.services.table.TableDiffService;
import org.eclipse.set.services.table.TableDiffService.TableCompareType;
import org.eclipse.set.services.table.TableStatus;
import org.eclipse.set.unittest.utils.osgi.AbstractOSGiTableTest;
import org.eclipse.set.unittest.utils.osgi.TestFile;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;

/**
 * 
 */
public abstract class AbstractPt1TableDataTest extends AbstractOSGiTableTest {

	/**
	 * @param table
	 *            the tested table
	 * @return the minimal table header
	 */
	public static String getTableCSVHeader(final Table table) {
		final List<ColumnDescriptor> columns = TableExtensions
				.getColumns(table);
		final String header = columns.stream()
				.map(ColumnDescriptor::getColumnPosition)
				.collect(Collectors.joining(CSV_DELIMITER));
		return ROW_INDEX_COL + CSV_DELIMITER + header + System.lineSeparator();
	}

	protected static void assertInjectedAllTransformationService(
			final Collection<PlanPro2TableTransformationService> transformationServices) {
		final List<String> expectedTransformationServices = PtTable
				.givenExpectedTransformationServices();
		assertEquals(expectedTransformationServices.size(),
				transformationServices.size());
		final List<String> actualServicesName = transformationServices.stream()
				.map(service -> service.getClass().getName())
				.map(className -> className
						.substring(className.lastIndexOf(".") + 1))
				.toList();
		assertTrue(
				expectedTransformationServices.containsAll(actualServicesName));
		assertTrue(
				actualServicesName.containsAll(expectedTransformationServices));
	}

	protected static void mockToolboxConfigurationMethode(
			final MockedStatic<ToolboxConfiguration> mockConfiguration) {
		mockConfiguration.when(ToolboxConfiguration::isDevelopmentMode)
				.thenReturn(Boolean.FALSE);
		mockConfiguration.when(ToolboxConfiguration::isDebugMode)
				.thenReturn(Boolean.FALSE);
	}

	protected static Stream<Arguments> providesPtTable() {
		return PtTable.tablesToTest.stream().map(Arguments::of);
	}

	private ToolboxViewModelService toolboxViewModelService;

	protected IEventBroker broker;

	protected Map<TableCompareType, TableDiffService> diffServiceMap;

	protected Map<TableInfo, PlanPro2TableTransformationService> modelServiceMap;

	protected List<CSVRecord> referenceData = new LinkedList<>();

	protected TableServiceImpl tableService;

	protected String TEMPLATE_DIR = "../org.eclipse.set.feature/rootdir/data/export";

	protected String TEMPLATE_LOCAL_DIR = "./data/export";

	protected Table testee;

	/**
	 * @return the location of reference directory
	 */
	@Override
	public String getReferenceDir() {
		return Activator.TEST_RESOURCE_DIR + "table_reference/"
				+ getTestFile().getShortName() + "/";
	}

	/**
	 * Call execute test class to get test resource location
	 * 
	 * @return execute test class
	 */
	@Override
	public Class<? extends AbstractOSGiTableTest> getTestResourceClass() {
		// Class for get the default test resource
		if (getTestFile().equals(Pt1TableTestFile.PPHN_1_10_0_1)) {
			return AbstractPt1TableDataTest.class;
		}
		return getClass();
	}

	/**
	 * @return the current test table
	 */
	@Override
	public Table getTestTable() {
		return testee;
	}

	private void givenToolboxViewModelService() {
		toolboxViewModelService = Mockito.mock(ToolboxViewModelService.class);
		Mockito.when(toolboxViewModelService.getSession())
				.thenReturn(Optional.of(modelSession));

	}

	@AfterAll
	protected void afterAll() {
		final Map<String, Object> properties = new HashMap<>();
		properties.put(EventConstants.EVENT_TOPIC, Events.CLOSE_SESSION);
		properties.put(IEventBroker.DATA, ToolboxFileRole.SESSION);
		getEventAdmin().sendEvent(new Event(Events.CLOSE_SESSION, properties));
	}

	@BeforeAll
	protected void beforeAll() throws Exception {
		givenPlanProFile(getTestFile().getModel(getTestResourceClass()));
		setup();
	}

	protected void compareValue(final int startRow, final List<TableRow> rows) {
		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < TableExtensions
					.getColumns(testee)
					.size(); columnIndex++) {
				final TableCell dataCell = TableRowExtensions.getCell(
						rows.get(rowIndex),
						TableExtensions.getColumns(testee).get(columnIndex));
				final String cellValue = TableCellExtensions
						.getPlainStringValue(dataCell)
						.replace(System.lineSeparator(), "");
				final String referenceValue = referenceData
						.get(rowIndex + startRow)
						.get(columnIndex + 1);
				assertEquals(referenceValue, cellValue, getErrorMessage(
						columnIndex, rowIndex, referenceValue, cellValue));
			}
		}
	}

	protected void doTestInMockEnvironment(final Executable testCase)
			throws Throwable {
		try (final MockedStatic<Services> services = Mockito
				.mockStatic(Services.class);
				MockedStatic<ToolboxConfiguration> toolboxConfiguration = Mockito
						.mockStatic(ToolboxConfiguration.class)) {
			mockServicesMethode(services);
			mockToolboxConfigurationMethode(toolboxConfiguration);
			// do test with static mock
			testCase.execute();
		}
	}

	protected abstract CacheService getCacheService();

	protected abstract List<IContextFunction> getContextFunctions();

	protected abstract Object getEnumTranslationService();

	@SuppressWarnings("boxing")
	protected String getErrorMessage(final int columnIndex, final int rowIndex,
			final String expectedValue, final String actualValue) {
		return String.format(
				"%s at row: %d, column: %d. ExpectedValue: %s - ActualValue: %s",
				getTestTableReferenceName(), rowIndex, columnIndex,
				expectedValue, actualValue);
	}

	protected abstract GeoKanteGeometryService getGeometryService();

	protected abstract SessionService getSessionService();

	protected int getTableRowCount() {
		return TableExtensions.getTableRows(testee).size();
	}

	protected abstract PtTable getTableToTest();

	@SuppressWarnings("static-method")
	protected TestFile getTestFile() {
		return Pt1TableTestFile.PPHN_1_10_0_3;
	}

	protected abstract TopologicalGraphService getTopologicalGraphService();

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

	protected List<CSVRecord> loadReferenceFile(final String tableName) {
		final String fileName = getReferenceDir() + tableName
				+ "_reference.csv";
		final Builder csvBuilder = CSVFormat.Builder.create(CSVFormat.DEFAULT)
				.setHeader(getTableCSVHeader(testee).split(CSV_DELIMITER))
				.setSkipHeaderRecord(true);
		csvBuilder.setDelimiter(CSV_DELIMITER);
		final InputStream referenceResource = getTestResourceClass()
				.getClassLoader()
				.getResourceAsStream(fileName);
		if (referenceResource == null && !isTableEmpty) {
			fail(String.format("Cannot find file: %s", fileName));
		}
		try (InputStream inputStream = referenceResource;
				final Reader reader = new InputStreamReader(inputStream);
				final CSVParser csvParser = new CSVParser(reader,
						csvBuilder.build())) {
			return csvParser.getRecords();
		} catch (final NullPointerException | IOException e) {
			return null;
		}
	}

	protected void mockServicesMethode(
			final MockedStatic<Services> mockServices) {
		mockServices.when(Services::getToolboxViewModelService)
				.thenReturn(toolboxViewModelService);
		mockServices.when(Services::getCacheService)
				.thenReturn(getCacheService());
		mockServices.when(Services::getTopGraphService)
				.thenReturn(getTopologicalGraphService());
		mockServices.when(Services::getGeometryService)
				.thenReturn(getGeometryService());
		mockServices.when(Services::getEnumTranslationService)
				.thenReturn(getEnumTranslationService());
	}

	@SuppressWarnings("unchecked")
	protected void setup() throws Exception {
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

		broker = Mockito.mock(IEventBroker.class);
		Mockito.when(Boolean.valueOf(broker.post(ArgumentMatchers.anyString(),
				ArgumentMatchers.any()))).thenReturn(Boolean.TRUE);

		setupModelSession(getEventAdmin());
		while (!getGeometryService()
				.isFindGeometryComplete(ToolboxFileRole.SESSION)) {
			Thread.sleep(2000);
		}
		getSessionService().getLoadedSessions()
				.put(ToolboxFileRole.SESSION, modelSession);
		givenToolboxViewModelService();
	}

	protected void thenExpectNotExistReference() {
		assertNull(referenceData, "Empty Table shoundn't have csv reference");
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
