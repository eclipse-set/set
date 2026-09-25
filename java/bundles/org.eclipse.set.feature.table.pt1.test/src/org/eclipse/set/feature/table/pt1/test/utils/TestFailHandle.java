/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.test.utils;

import static org.eclipse.set.feature.table.pt1.test.tabledata.AbstractPt1TableDataTest.CSV_DELIMITER;
import static org.eclipse.set.feature.table.pt1.test.tabledata.AbstractPt1TableDataTest.ROW_INDEX_COL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.set.feature.table.pt1.test.tabledata.AbstractPt1TableDataTest;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.utils.table.export.ExportToCSV;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Export current state and reference table, when test fail
 * 
 * @author truong
 */
@Disabled
public class TestFailHandle implements TestWatcher {
	// /**
	// * Special fail handle that reopens the table so that the current CSV can
	// be
	// * exported
	// */
	// public static class ReopenTableBeforeFailHandle extends TestFailHandle {
	// @Override
	// public void testFailed(final ExtensionContext context,
	// final Throwable cause) {
	// final Optional<Object> testInstance = context.getTestInstance();
	// if (testInstance.isPresent() && testInstance
	// .get() instanceof final AbstractTableTest tableTest) {
	// tableTest.givenNattableBot(tableTest.tableToTest.tableName());
	// super.testFailed(context, cause);
	// final SWTBotCTabItem cTabItem = tableTest.bot
	// .cTabItem(tableTest.tableToTest.tableName());
	// UIThreadRunnable.syncExec(() -> {
	// cTabItem.activate();
	// cTabItem.close();
	// });
	// }
	// }
	// }

	private static final String CURRENT_CSV_EXTENSIONS = "_current.csv";
	private static final String DIFF_DIR = "diff";
	private static final String REFERENCE_CSV_EXTENSIONS = "_reference.csv";

	private static String getTableCSVHeader(final Table table) {
		final List<ColumnDescriptor> columns = TableExtensions
				.getColumns(table);
		final String header = columns.stream()
				.map(ColumnDescriptor::getColumnPosition)
				.collect(Collectors.joining(CSV_DELIMITER));
		return ROW_INDEX_COL + CSV_DELIMITER + header + System.lineSeparator();
	}

	protected static void exportCurrentCSV(
			final AbstractPt1TableDataTest tableTest) {
		final File file = getExportFile(tableTest, CURRENT_CSV_EXTENSIONS);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		final Table testTable = tableTest.getTestTable();
		final ExportToCSV<Pair<Integer, TableRow>> exportToCSV = new ExportToCSV<>(
				getTableCSVHeader(testTable));
		exportToCSV.exportToCSV(Optional.of(file.toPath()),
				TableExtensions.getTableRowsWithIndex(testTable), row -> {
					final String rowValue = row.getValue()
							.getCells()
							.stream()
							.map(TableCellExtensions::getRichTextValue)
							.collect(Collectors.joining(CSV_DELIMITER));
					return row.getKey().intValue() + 1 + CSV_DELIMITER
							+ rowValue + System.lineSeparator();
				});
	}

	protected static File getExportFile(
			final AbstractPt1TableDataTest tableTest, final String extension) {
		try {
			final URL parent = tableTest.getClass()
					.getProtectionDomain()
					.getCodeSource()
					.getLocation();
			final File parentDir = Path.of(parent.toURI()).toFile();
			return new File(parentDir,
					Path.of(tableTest.getReferenceDir()
							.replaceFirst(".*table_reference/", DIFF_DIR + "/"),
							tableTest.getTestTableReferenceName() + extension)
							.toString());
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private final Logger LOGGER = LoggerFactory.getLogger(TestFailHandle.class);

	@Override
	public void testFailed(final ExtensionContext context,
			final Throwable cause) {
		final Optional<Object> testInstance = context.getTestInstance();
		if (testInstance.isPresent() && testInstance
				.get() instanceof final AbstractPt1TableDataTest tableTest
				&& !tableTest.isTableEmpty()) {
			exportCurrentCSV(tableTest);
			exportReferenceCSV(tableTest);
		}
		TestWatcher.super.testFailed(context, cause);
	}

	protected void exportReferenceCSV(
			final AbstractPt1TableDataTest tableTest) {
		final File outFile = getExportFile(tableTest, REFERENCE_CSV_EXTENSIONS);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		final String tableName = tableTest.getTestTableReferenceName();
		final InputStream referenceResource = tableTest.getTestResourceClass()
				.getClassLoader()
				.getResourceAsStream(tableTest.getReferenceDir() + tableName
						+ REFERENCE_CSV_EXTENSIONS);
		// New table given't reference datei
		if (referenceResource == null) {
			LOGGER.debug(String.format("Cannot find file: %s",
					tableName + REFERENCE_CSV_EXTENSIONS));
			return;
		}
		try (final InputStream resourceStream = referenceResource;
				final FileOutputStream outputStream = new FileOutputStream(
						outFile);) {
			outputStream.write(resourceStream.readAllBytes());
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
