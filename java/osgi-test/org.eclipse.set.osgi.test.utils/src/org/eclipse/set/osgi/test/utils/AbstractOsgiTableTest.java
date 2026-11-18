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
package org.eclipse.set.osgi.test.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;

/**
 * 
 */
public abstract class AbstractOsgiTableTest extends AbstractOsgiTest {
	/**
	 * the csv delimiter
	 */
	public static final String CSV_DELIMITER = ";";

	/**
	 * the index column designation
	 */
	public static final String ROW_INDEX_COL = "Lfd. Nr.";

	protected static void mockToolboxConfigurationMethode(
			final MockedStatic<ToolboxConfiguration> mockConfiguration) {
		mockConfiguration.when(ToolboxConfiguration::isDevelopmentMode)
				.thenReturn(Boolean.FALSE);
		mockConfiguration.when(ToolboxConfiguration::isDebugMode)
				.thenReturn(Boolean.FALSE);
	}

	protected IEventBroker broker;
	protected boolean isTableEmpty;
	protected List<CSVRecord> referenceData = new LinkedList<>();

	protected Table testee;

	/**
	 * @param table
	 *            test table
	 * @return the csv header of test table
	 */
	public abstract String getTableCSVHeader(Table table);

	/**
	 * @return the current test table
	 */
	public Table getTestTable() {
		return testee;
	}

	/**
	 * @return the table reference file name
	 */
	public abstract String getTestTableReferenceName();

	/**
	 * @return
	 */
	public boolean isTableEmpty() {
		return isTableEmpty;
	}

	@AfterAll
	protected void afterAll() {
		final Map<String, Object> properties = new HashMap<>();
		properties.put(EventConstants.EVENT_TOPIC, Events.CLOSE_SESSION);
		properties.put(IEventBroker.DATA, ToolboxFileRole.SESSION);
		getEventAdmin().sendEvent(new Event(Events.CLOSE_SESSION, properties));
	}

	@Override
	@BeforeAll
	protected void beforeAll() throws Exception {
		super.beforeAll();
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

	protected abstract EnumTranslationService getEnumTranslationService();

	@SuppressWarnings("boxing")
	protected String getErrorMessage(final int columnIndex, final int rowIndex,
			final String expectedValue, final String actualValue) {
		return String.format(
				"%s at row: %d, column: %d. ExpectedValue: %s - ActualValue: %s",
				getTestTableReferenceName(), rowIndex, columnIndex,
				expectedValue, actualValue);
	}

	protected abstract EventAdmin getEventAdmin();

	protected abstract GeoKanteGeometryService getGeometryService();

	protected abstract String getReferenceDir();

	protected abstract SessionService getSessionService();

	protected int getTableRowCount() {
		return TableExtensions.getTableRows(testee).size();
	}

	protected abstract Class<? extends AbstractOsgiTest> getTestResourceClass();

	protected abstract TopologicalGraphService getTopologicalGraphService();

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
		mockServices.when(Services::getCacheService)
				.thenReturn(getCacheService());
		mockServices.when(Services::getTopGraphService)
				.thenReturn(getTopologicalGraphService());
		mockServices.when(Services::getGeometryService)
				.thenReturn(getGeometryService());
		mockServices.when(Services::getEnumTranslationService)
				.thenReturn(getEnumTranslationService());
	}

	protected void setup() throws Exception {
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

	}

}
