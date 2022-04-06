/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table

import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.basis.Wrapper
import org.eclipse.set.model.tablemodel.Footnote
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisObjektExtensions.*

/**
 * Transform basis objects to footnotes.
 * 
 * @author Schaefer
 */
class FootnoteTransformation {

	var TableRow row

	/**
	 * @param row the table row
	 * 
	 * @return the footnote list for the given row
	 */
	def String transform(Basis_Objekt object, TableRow row) {
		this.row = row
		object.addFootnotes
		return row.footnoteText
	}

	private def void addFootnotes(Basis_Objekt object) {
		object.comments.forEach[addFootnote]
	}

	private def void addFootnote(Bearbeitungsvermerk comment) {
		val footnote = comment.createFootnote
		row.footnotes.add(footnote)
	}

	private def Footnote createFootnote(Bearbeitungsvermerk comment) {
		val footnote = TablemodelFactory.eINSTANCE.createFootnote
		val number = comment.transform
		footnote.number = number.value.intValue
		footnote.text = comment?.bearbeitungsvermerkAllg?.kommentar?.wert
		return footnote
	}

	private def Wrapper<Integer> create new Wrapper()
	transform(Bearbeitungsvermerk vermerk) {
		value = row.table.nextFootnoteNumber
		return
	}
}
