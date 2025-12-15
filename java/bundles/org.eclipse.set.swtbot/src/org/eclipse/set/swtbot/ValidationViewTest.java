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
package org.eclipse.set.swtbot;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.swtbot.table.AbstractTableTest;
import org.eclipse.set.swtbot.table.TestFailHandle;
import org.eclipse.set.swtbot.utils.AbstractSWTBotTest;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.set.utils.table.export.ExportToCSV;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Test for changes in Validation View
 * 
 * @author Truong
 */
@TestInstance(Lifecycle.PER_CLASS)
public class ValidationViewTest extends AbstractTableTest {
	private static class ValidationViewFailHandle extends TestFailHandle {
		String csvHeader = "\"Item Group\";\"Item label\";\"Expect Value\""
				+ System.lineSeparator();

		@Override
		public void testFailed(final ExtensionContext context,
				final Throwable cause) {
			final Optional<Object> testInstance = context.getTestInstance();
			if (testInstance.isPresent() && testInstance
					.get() instanceof final ValidationViewTest tableTest) {
				exportWidgeValue(tableTest);
				exportReferenceCSV(tableTest.getTestFile(),
						VALIDATION_INFORMATION_CSV, tableTest.getReferenceDir(),
						tableTest.getTestResourceClass().getClassLoader(),
						tableTest.getClass());
			}
		}

		private void exportWidgeValue(final ValidationViewTest testInstance) {

			final List<String> currentValues = new ArrayList<>();
			final List<CSVRecord> referenceFile = testInstance.informationReference;
			for (int i = 1; i < referenceFile.size(); i++) {
				final String itemGroup = referenceFile.get(i).get(0);
				final String itemLabel = referenceFile.get(i).get(1);
				final SWTBotText currentValue = AbstractSWTBotTest.bot
						.textWithLabelInGroup(itemLabel, itemGroup);
				final String csvEntry = String.format("\"%s\";\"%s\";\"%s\"",
						itemGroup, itemLabel, currentValue.getText())
						+ System.lineSeparator();

				currentValues.add(csvEntry);
			}
			final ExportToCSV<String> exportToCSV = new ExportToCSV<>(
					csvHeader);
			final File file = getExportFile(testInstance.getTestFile(),
					VALIDATION_INFORMATION_CSV + "_current.csv",
					testInstance.getClass());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			exportToCSV.exportToCSV(Optional.of(file.toPath()), currentValues);
			SWTBotUtils.botWaitUntil(AbstractSWTBotTest.bot,
					() -> Boolean.valueOf(Files.exists(file.toPath(),
							LinkOption.NOFOLLOW_LINKS)));
		}
	}

	protected static final String RICHTEXT_REPLACE_REGEX = "<[^>]+>";
	protected static final String VALIDATION_INFORMATION_CSV = "validation_information";
	protected static final String VALIDATION_TABLE_NAME = "validation_view";

	private static List<String> splitString(final String text,
			final String regex) {
		final List<String> asList = Arrays.asList(text.split(regex));
		return asList.stream().map(e -> e.replaceAll(" ", "")).toList();
	}

	protected List<CSVRecord> informationReference;

	@BeforeEach
	@Override
	public void beforeEach() throws Exception {
		// do nothing
	}

	@Override
	public String getTestTableName() {
		return VALIDATION_TABLE_NAME;
	}

	@Override
	protected void compareValue(final ILayer nattableLayer, final int startRow,
			final int endRow) {
		for (int rowIndex = 0; rowIndex < endRow; rowIndex++) {
			for (int columnIndex = 0; columnIndex < nattableLayer
					.getPreferredColumnCount()
					- fixedColumnCount; columnIndex++) {
				final String cellValue = nattableLayer
						.getDataValueByPosition(columnIndex, rowIndex)
						.toString()
						.trim()
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "")
						.replaceAll(RICHTEXT_REPLACE_REGEX, "")
						.replaceAll(ZERO_WIDTH_SPACE, "");

				final String referenceValue = referenceData
						.get(rowIndex + startRow)
						.get(columnIndex)
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "")
						.replaceAll(ZERO_WIDTH_SPACE, "");
				assertEquals(referenceValue, cellValue);
			}
		}
	}

	protected void expectTextWidgetAreSame(final String expectContent,
			final String itemLabel, final String itemGroup) {
		assertDoesNotThrow(
				() -> bot.textWithLabelInGroup(itemLabel, itemGroup));
		final SWTBotText actual = bot.textWithLabelInGroup(itemLabel,
				itemGroup);

		final List<String> expects = splitString(expectContent, ",");
		final List<String> actuals = splitString(actual.getText(), ",");
		assertEquals(expects.size(), actuals.size());
		assertTrue(expects.containsAll(actuals));

	}

	@Override
	protected int getNattableHeaderRowCount() {
		// Filter Row
		return super.getNattableHeaderRowCount() - 1;
	}

	protected void givenReferenceCSV() throws IOException {
		referenceData = loadReferenceFile(VALIDATION_TABLE_NAME);
		// Remove CSV header info
		referenceData = referenceData.subList(4, referenceData.size());
	}

	protected void loadCsvResources() throws IOException {
		informationReference = loadReferenceFile(VALIDATION_INFORMATION_CSV);
	}

	@Test
	@ExtendWith(ValidationViewFailHandle.class)
	protected void testInformation() throws Exception {
		whenOpeningValidateView();
		loadCsvResources();
		thenExpectModelInformationEquals();
	}

	protected void thenExpectModelInformationEquals() {
		for (int i = 1; i < informationReference.size(); i++) {
			final String group = informationReference.get(i).get(0);
			final String item = informationReference.get(i).get(1);
			final String value = informationReference.get(i).get(2);
			expectTextWidgetAreSame(value, item, group);
		}
	}

	protected void whenOpeningValidateView() {
		final SWTBotNatTable nattableBot = SWTBotUtils.waitForNattable(bot,
				30000);
		layers = SWTBotUtils.getNattableLayers(nattableBot);
	}

	// The test file should only open one times by this test
	@BeforeAll
	void beforeAll() throws Exception {
		super.beforeEach();
	}

	@Test
	@ExtendWith(TestFailHandle.class)
	void testValidateReport() throws Exception {
		givenReferenceCSV();
		whenOpeningValidateView();
		bot.button("Alle ausklappen").click();
		thenRowAndColumnCountEqualReferenceCSV();
		thenTableDataEqualReferenceCSV();
	}
}
