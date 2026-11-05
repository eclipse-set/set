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
package org.eclipse.set.feature.plazmodel.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.plazmodel.Messages;
import org.eclipse.set.feature.plazmodel.check.GeoCoordinateValid;
import org.eclipse.set.feature.plazmodel.service.PlazModelService;
import org.eclipse.set.feature.plazmodel.table.PlazModelTableTransformationService;
import org.eclipse.set.feature.plazmodel.table.PlazModelTableView;
import org.eclipse.set.model.plazmodel.PlazReport;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.osgi.test.utils.AbstractOsgiTest;
import org.eclipse.set.osgi.test.utils.TreeTableTestFailed;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

/**
 * 
 */
@ExtendWith(ServiceExtension.class)
@ExtendWith(BundleContextExtension.class)
public class PlaZModelReportTest extends AbstractPlaZModelTest {
	private static Path getMediaDirectoryPath() {
		return Path.of(UNZIP_DIR, "media");
	}

	@InjectService
	CacheService cacheService;
	@InjectBundleContext
	BundleContext context;
	@InjectService
	EnumTranslationService enumTranslationService;
	@InjectService
	EventAdmin eventAdmin;

	GeoCoordinateValid geoCoordinateValid;

	@InjectService
	GeoKanteGeometryService geometryService;

	@InjectService
	PlazModelService plazModelService;

	PlazModelTableTransformationService plazModelTransformationService;

	PlazReport plazReport;

	@InjectService
	SessionService sessionService;

	@InjectService
	TopologicalGraphService topgraphService;

	@Override
	public String getTableCSVHeader(final Table table) {
		return PlazModelTableView.HEADER_PATTERN;
	}

	@Override
	public String getTestTableReferenceName() {
		return "plaz_model";
	}

	private void givenPlaZModelReport() {
		plazReport = plazModelService.runPlazModel(modelSession);
	}

	private void givenPlaZModelTable() {
		testee = plazModelTransformationService.transform(plazReport);
		isTableEmpty = TableExtensions.getTableRows(testee).isEmpty();
	}

	private void givenPlaZModelTransformationService() {
		plazModelTransformationService = new PlazModelTableTransformationService(
				new Messages(), enumTranslationService);
	}

	@Override
	protected BundleContext getBundleContext() {
		return context;
	}

	@Override
	protected CacheService getCacheService() {
		return cacheService;
	}

	@Override
	protected EnumTranslationService getEnumTranslationService() {
		return enumTranslationService;
	}

	@Override
	protected EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	@Override
	protected GeoKanteGeometryService getGeometryService() {
		return geometryService;
	}

	@Override
	protected SessionService getSessionService() {
		return sessionService;
	}

	@Override
	protected Class<? extends AbstractOsgiTest> getTestResourceClass() {
		return getClass();
	}

	@Override
	protected TopologicalGraphService getTopologicalGraphService() {
		return topgraphService;
	}

	@SuppressWarnings("boxing")
	@Override
	protected void mockToolboxConfigurationMethode(
			final MockedStatic<ToolboxConfiguration> mockConfiguration) {
		super.mockToolboxConfigurationMethode(mockConfiguration);
		mockConfiguration.when(ToolboxConfiguration::getGeometryBlossStepLength)
				.thenReturn(Double.valueOf(0.5));
		mockConfiguration
				.when(ToolboxConfiguration::getGeometryClothoidStepLength)
				.thenReturn(Double.valueOf(0.5));
		mockConfiguration.when(ToolboxConfiguration::getTableTreeMinimum)
				.thenReturn(5);
	}

	@SuppressWarnings("boxing")
	@Override
	protected ToolboxFile mockToolboxFile() {
		final ToolboxFile mockToolboxFile = super.mockToolboxFile();
		Mockito.when(mockToolboxFile.hasMedia(ArgumentMatchers.anyString()))
				.thenAnswer(invocation -> {
					if (invocation
							.getArgument(0) instanceof final String mediaGuid) {
						final Path mediaPath = Paths.get(
								getMediaDirectoryPath().toString(), mediaGuid);
						return Files.exists(mediaPath);
					}
					throw new IllegalAccessException();
				});
		Mockito.when(mockToolboxFile.getModelPath())
				.thenReturn(Path.of(UNZIP_DIR, "content.xml"));
		Mockito.when(
				mockToolboxFile.getXMLDocument(ArgumentMatchers.anyString()))
				.thenReturn(null);
		return mockToolboxFile;
	}

	@Override
	protected Function<Pair<Integer, TableRow>, String> tableRowToString() {
		return row -> {
			final String rowValue = row.getValue()
					.getCells()
					.stream()
					.map(TableCellExtensions::getPlainStringValue)
					.map(value -> value.replace(System.lineSeparator(), ""))
					.collect(Collectors.joining(CSV_DELIMITER));
			return rowValue + System.lineSeparator();
		};
	}

	@Override
	protected void thenExpectTableDataEqualReferenceCSV() {
		final List<TableRow> tableRows = TableExtensions.getTableRows(testee);
		tableRows.sort(TreeTableTestFailed.treeRowComparator());
		assertDoesNotThrow(() -> compareValue(0, tableRows));
	}

	@Test
	@ExtendWith(TreeTableTestFailed.class)
	void testPlaZModelReport() throws Throwable {
		doTestInMockEnvironment(() -> {
			givenPlaZModelReport();
			assertNotNull(plazReport);
			givenPlaZModelTransformationService();
			assertNotNull(plazModelTransformationService);
			givenPlaZModelTable();
			givenReferenceData();
			if (isTableEmpty) {
				thenExpectNotExistReference();
			}

			thenExpectRowAndColumnCountEqualReferenceCSV();
			thenExpectTableDataEqualReferenceCSV();
		});
	}

}
