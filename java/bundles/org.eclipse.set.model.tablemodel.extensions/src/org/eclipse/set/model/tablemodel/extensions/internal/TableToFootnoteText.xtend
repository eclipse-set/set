/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions.internal

import org.eclipse.set.model.tablemodel.Footnote
import org.eclipse.set.model.tablemodel.Table
import java.util.HashMap

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*

/**
 * Transform a table to footnotes text.
 * 
 * @author Schaefer
 */
class TableToFootnoteText {

	val map = new HashMap<Integer, Footnote>
	val builder = new StringBuilder

	/**
	 * @param table the table
	 * 
	 * @return the footnotes text for the table
	 */
	def String transform(Table table) {
		table.tableRows.forEach[footnotes.forEach[addToMap]]
		return mapText
	}

	private def void addToMap(Footnote footnote) {
		map.put(footnote.number, footnote)
	}

	private def String mapText() {
		val keys = map.keySet.sort
		keys.forEach[map.get(it).append]
		return builder.toString
	}

	private def void append(Footnote footnote) {
		builder.append('''*«footnote.number»: «footnote.text»''')
		builder.append(System.getProperty("line.separator"))
	}
}
