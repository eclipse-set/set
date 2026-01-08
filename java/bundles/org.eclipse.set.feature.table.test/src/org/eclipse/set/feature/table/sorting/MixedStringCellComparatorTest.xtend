/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sorting

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum
import org.eclipse.set.model.tablemodel.TablemodelFactory
import org.eclipse.set.utils.table.sorting.MixedStringCellComparator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.createStringCellContent
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*
import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for {@link MixedStringCellComparator}.
 * 
 * @author Schaefer
 */
class MixedStringCellComparatorTest {

	MixedStringCellComparator comparator

	@BeforeEach
	def void setUp() {
		comparator = new MixedStringCellComparator(SortDirectionEnum.ASC)
	}

	@Test
	def void testMixedCells() {
		val cell1 = TablemodelFactory.eINSTANCE.createTableCell
		val content1 = TablemodelFactory.eINSTANCE.createCompareStateCellContent
		content1.newValue = createStringCellContent("86W9")
		cell1.content = content1

		val cell2 = TablemodelFactory.eINSTANCE.createTableCell
		val content2 = TablemodelFactory.eINSTANCE.createStringCellContent
		content2.value.add("83W1")
		cell2.content = content2

		val result = comparator.compare(cell1, cell2)

		assertThat(result, is(1))
	}

	@Test
	def void testKennzahl() {
		val cell1 = TablemodelFactory.eINSTANCE.createTableCell
		val content1 = TablemodelFactory.eINSTANCE.createStringCellContent
		content1.value.add("76ZV2/75ZU2 (D)")
		cell1.content = content1

		val cell2 = TablemodelFactory.eINSTANCE.createTableCell
		val content2 = TablemodelFactory.eINSTANCE.createStringCellContent
		content2.value.add("76002/76F ()")
		cell2.content = content2

		val result = comparator.compare(cell1, cell2)

		assertTrue(result > 0)
	}
}
