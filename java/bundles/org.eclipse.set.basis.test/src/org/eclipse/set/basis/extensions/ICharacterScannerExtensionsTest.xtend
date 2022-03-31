/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.set.basis.StringScanner
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

import static extension org.eclipse.set.basis.extensions.ICharacterScannerExtensions.*

/**
 * Tests for {@link ICharacterScannerExtensions}.
 *  
 * @author Schaefer
 */
class ICharacterScannerExtensionsTest {

	@Test
	def void testReadFound() {
		val scanner = new StringScanner("StartTest")
		scanner.read("Start")
		assertEquals(5, scanner.column)
	}

	@Test
	def void testReadNotFound() {
		val scanner = new StringScanner("StartTest")
		scanner.read("art")
		assertEquals(0, scanner.column)
	}

	@Test
	def void testUnread() {
		val scanner = new StringScanner("StartTest")
		scanner.read("Start")
		assertEquals(5, scanner.column)
		scanner.unread(5)
		assertEquals(0, scanner.column)
	}
}
