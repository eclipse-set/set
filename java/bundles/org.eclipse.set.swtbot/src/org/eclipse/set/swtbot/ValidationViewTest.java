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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.swtbot.table.AbstractTableTest;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.jupiter.api.Test;

/**
 * Test for changes in Validation View
 * 
 * @author Truong
 */
public class ValidationViewTest extends AbstractTableTest {
	private static final String Application_Schema_Version_Group = "Unterstütztes XML-Schema";

	private static final String File_Info_Group = "Geladene Datei";
	private static final String File_Schema_Version_Group = "Verwendetes XML-Schema (in Datei)";
	private static final String guid_expect = "FF06E818-09F6-436E-964E-5F8AC209D3AC";
	private static final String md5_expect = "FF4AC4AE448141641FEF1E2394DA3F1D";
	private static final String Model_Container_Group = "PlanPro-Container";
	private static final String modelContains_expect = "Fachdaten, Anhänge";
	private static final String RICHTEXT_REPLACE_REGEX = "<[^>]+>";
	private static final String subwork_expect = "ESTW (1), Geo (1)";
	private static final String Subwork_Group = "Untergewerke";
	private static final String valid_expect = "gültig";
	private static final String Validate_Group = "Gültigkeit";
	private static final String VALIDATION_TABLE_NAME = "validation_view";
	private static final String version_expect = "1.10.0.1";

	private static final String workable_expect = "Ja";

	private static List<String> splitString(final String text,
			final String regex) {
		final List<String> asList = Arrays.asList(text.split(regex));
		return asList.stream().map(e -> e.replaceAll(" ", "")).toList();
	}

	@Override
	public String getTestTableName() {
		return VALIDATION_TABLE_NAME;
	}

	private void expectTextWidgetAreSame(final String expectContent,
			final String textLabel, final String parentName) {
		assertDoesNotThrow(
				() -> bot.textWithLabelInGroup(textLabel, parentName));
		final SWTBotText actual = bot.textWithLabelInGroup(textLabel,
				parentName);

		final List<String> expects = splitString(expectContent, ",");
		final List<String> actuals = splitString(actual.getText(), ",");
		assertEquals(expects.size(), actuals.size());
		assertTrue(expects.containsAll(actuals));

	}

	private void thenExpectModelInformationEquals() {
		expectTextWidgetAreSame(md5_expect, "MD5", File_Info_Group);
		expectTextWidgetAreSame(guid_expect, "GUID", File_Info_Group);
		expectTextWidgetAreSame(version_expect, "PlanPro",
				File_Schema_Version_Group);
		expectTextWidgetAreSame(version_expect, "Signalbegriffe",
				File_Schema_Version_Group);
		expectTextWidgetAreSame(valid_expect, "XSD-Gültigkeit", Validate_Group);
		expectTextWidgetAreSame(valid_expect, "EMF-Gültigkeit", Validate_Group);
		expectTextWidgetAreSame(workable_expect, "Verarbeitbar",
				Validate_Group);
		expectTextWidgetAreSame(version_expect, "PlanPro",
				Application_Schema_Version_Group);
		expectTextWidgetAreSame(version_expect, "Signalbegriffe",
				Application_Schema_Version_Group);

		expectTextWidgetAreSame(subwork_expect, "Enthalten", Subwork_Group);
		expectTextWidgetAreSame(modelContains_expect, "Enthalten",
				Model_Container_Group);
	}

	private void whenOpeningValidateView() {
		final SWTBotNatTable nattableBot = SWTBotUtils.waitForNattable(bot,
				30000);
		layers = SWTBotUtils.getNattableLayers(nattableBot);
		bot.button("Alle ausklappen").click();
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
						.toString().trim()
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "")
						.replaceAll(RICHTEXT_REPLACE_REGEX, "")
						.replaceAll(ZERO_WIDTH_SPACE, "");

				final String referenceValue = referenceData
						.get(rowIndex + startRow).get(columnIndex)
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "")
						.replaceAll(ZERO_WIDTH_SPACE, "");
				assertEquals(referenceValue, cellValue);
			}
		}
	}

	@Override
	protected int getNattableHeaderRowCount() {
		// Filter Row
		return super.getNattableHeaderRowCount() - 1;
	}

	protected void givenReferenceCSV() throws IOException {
		referenceData = loadReferenceFile("validation_view");
		// Remove CSV header info
		referenceData = referenceData.subList(4, referenceData.size());
	}

	@Test
	void testInformation() throws Exception {
		whenOpeningValidateView();
		thenExpectModelInformationEquals();
	}

	@Test
	void testValidateReport() throws Exception {
		givenReferenceCSV();
		whenOpeningValidateView();
		thenRowAndColumnCountEqualReferenceCSV();
		thenTableDataEqualReferenceCSV();
	}

}
