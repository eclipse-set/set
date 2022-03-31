/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.tables

import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

/**
 * Tests for {@link Tables}.
 *  
 * @author Schaefer
 */
class TablesTest {

	@Test
	def void testGetColumnIdentifier() {
		assertThat(Tables.getColumnIdentifier(0), is("A"))
		assertThat(Tables.getColumnIdentifier(1), is("B"))
		assertThat(Tables.getColumnIdentifier(2), is("C"))
		assertThat(Tables.getColumnIdentifier(25), is("Z"))
		assertThat(Tables.getColumnIdentifier(26), is("AA"))
		assertThat(Tables.getColumnIdentifier(27), is("AB"))
		assertThat(Tables.getColumnIdentifier(51), is("AZ"))
		assertThat(Tables.getColumnIdentifier(52), is("BA"))
		assertThat(Tables.getColumnIdentifier(53), is("BB"))
		assertThat(Tables.getColumnIdentifier(3000), is("DKK"))
		assertThat(Tables.getColumnIdentifier(3001), is("DKL"))
		assertThat(Tables.getColumnIdentifier(3002), is("DKM"))
	}

	@Test
	def void testGetColumnIndex() {
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(0)), is(0))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(1)), is(1))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(2)), is(2))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(25)),
			is(25))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(26)),
			is(26))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(27)),
			is(27))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(51)),
			is(51))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(52)),
			is(52))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(53)),
			is(53))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(3000)),
			is(3000))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(3001)),
			is(3001))
		assertThat(Tables.getColumnIndex(Tables.getColumnIdentifier(3002)),
			is(3002))
	}
}
