/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1

import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory

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
		// return row.footnoteText
		return ""
	}

	private def void addFootnotes(Basis_Objekt object) {
		object.IDBearbeitungsvermerk.forEach[value?.addFootnote]
	}

	private def void addFootnote(Bearbeitungsvermerk comment) {
		val footnote = comment.createFootnote
		if (row.footnotes === null)
			row.footnotes = TablemodelFactory.eINSTANCE.
				createSimpleFootnoteContainer()
		(row.footnotes as SimpleFootnoteContainer).footnotes.add(footnote)
	}

	private def String createFootnote(Bearbeitungsvermerk comment) {
		return comment?.bearbeitungsvermerkAllg?.kommentar?.wert
	}

}
