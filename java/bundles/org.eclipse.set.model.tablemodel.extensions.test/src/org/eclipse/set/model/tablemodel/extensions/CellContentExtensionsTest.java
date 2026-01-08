/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CellContentExtensions}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
public class CellContentExtensionsTest {

	private static CompareStateCellContent createCompareCellContent(
			final String oldValue, final String newValue) {
		final CompareStateCellContent content = TablemodelFactory.eINSTANCE
				.createCompareStateCellContent();
		content.setOldValue(createStringCellContent(oldValue));
		content.setNewValue(createStringCellContent(newValue));
		final TableCell tableCell = TablemodelFactory.eINSTANCE
				.createTableCell();
		tableCell.setContent(content);
		return content;
	}

	private static StringCellContent createStringCellContent(
			final String value) {
		final StringCellContent content = TablemodelFactory.eINSTANCE
				.createStringCellContent();
		content.getValue().add(value);
		final TableCell tableCell = TablemodelFactory.eINSTANCE
				.createTableCell();
		tableCell.setContent(content);
		return content;
	}

	/**
	 * Tests for {@link CellContentExtensions#getRichTextValue(CellContent)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRichTextValue() {
		String value;

		value = CellContentExtensions.getRichTextValue(null);
		assertThat(value, is(""));

		value = CellContentExtensions
				.getRichTextValue(createStringCellContent("Test"));
		assertThat(value,
				is("<p style=\"text-align:center\"><span>Test</span></p>"));

		value = CellContentExtensions
				.getRichTextValue(createCompareCellContent("Old", null));
		assertThat(value, is(
				"<p style=\"text-align:center\"><span style=\"background-color:rgb(255,255, 0)\"><s>Old</s></span></p>"));

		value = CellContentExtensions
				.getRichTextValue(createCompareCellContent(null, "New"));
		assertThat(value, is(
				"<p style=\"text-align:center\"><span style=\"color:rgb(255, 0, 0)\">New</span></p>"));

		value = CellContentExtensions
				.getRichTextValue(createCompareCellContent("Old", "New"));
		assertThat(value, is(
				"<p style=\"text-align:center\"><span style=\"color:rgb(255, 0, 0)\">New</span><br></br><span style=\"background-color:rgb(255,255, 0)\"><s>Old</s></span></p>"));
	}
}
