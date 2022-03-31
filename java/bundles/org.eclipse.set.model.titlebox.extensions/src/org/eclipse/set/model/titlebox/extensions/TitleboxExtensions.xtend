/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox.extensions

import org.eclipse.set.model.titlebox.Titlebox

/**
 * Extensions for {@link Titlebox}.
 * 
 * @author Schaefer
 */
class TitleboxExtensions {

	/**
	 * Address for document type aka Dokumentenart (Feld 9)
	 */
	public static final int DOC_TYPE_ADDRESS = 81

	/**
	 * Shortcut for document type of inventory records.
	 */
	public static final String INVENTORY_RECORDS_DOC_TYPE_SHOTCUT = "B"

	/**
	 * Clear all fields and set default static fillings.
	 */
	def static void resetFields(Titlebox titlebox) {
		titlebox.field = newArrayOfSize(100)

		titlebox.set(75, "Index")
		titlebox.set(76, "Änderung")
		titlebox.set(77, "Bear/Dat")
		titlebox.set(78, "Gepr/Dat")
		titlebox.set(79, "Abnah/Dat")
		titlebox.set(80, "Übern/Dat")
		titlebox.set(82, "Erstellt")
		titlebox.set(87, "Geprüft")
		titlebox.set(90, "Freigegeben")
		titlebox.set(94, "Datum")
		titlebox.set(95, "Name")
	}

	/**
	 * Fills all empty fields with the address of the field.
	 * 
	 * @param titlebox this titlebox
	 */
	def static void fillEmptyFieldsWithAddresses(Titlebox titlebox) {
		titlebox.field.indexed.forEach [
			setAddressIfEmpty(titlebox, it.key, it.value)
		]
	}

	/**
	 * Sets the value at the given address.
	 * 
	 * @param address the address
	 * @param value the value
	 */
	def static void set(Titlebox titlebox, int address, String value) {
		val index = address - 1
		titlebox.setField(index, value)
	}

	private def static void setAddressIfEmpty(Titlebox titlebox, int index,
		String value) {
		if (value === null || value.length == 0) {
			val address = index + 1
			titlebox.set(address, address.toString)
		}
	}
}
