/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions.internal

import org.eclipse.set.model.tablemodel.Table
import java.util.HashMap

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer

/**
 * Transform a table to footnotes text.
 * 
 * @author Schaefer
 */
class TableToFootnoteText {

	val map = new HashMap<Integer, String>
	val builder = new StringBuilder

	/**
	 * @param table the table
	 * 
	 * @return the footnotes text for the table
	 */
	def String transform(Table table) {
		table.tableRows.forEach[addFootnoteContainerToMap(table, it.footnotes)]
		return mapText
	}

	private dispatch def addFootnoteContainerToMap(Table table, Void fc) {
	}

	private dispatch def addFootnoteContainerToMap(Table table,
		SimpleFootnoteContainer fc) {
		fc.footnotes.forEach[addToMap(table, it)]
	}

	private dispatch def addFootnoteContainerToMap(Table table,
		CompareFootnoteContainer fc) {
		fc.oldFootnotes.forEach[addToMap(table, it)]
		fc.unchangedFootnotes.forEach[addToMap(table, it)]
		fc.newFootnotes.forEach[addToMap(table, it)]
	}

	private def void addToMap(Table table, String footnote) {
		map.put(table.getFootnoteNumber(footnote), footnote)

	}

	private def String mapText() {
		val keys = map.keySet.sort
		keys.forEach[append(it, map.get(it))]
		return builder.toString
	}

	private def void append(Integer number, String footnote) {
		builder.append('''*«number»: «footnote»''')
		builder.append(System.getProperty("line.separator"))
	}
}
