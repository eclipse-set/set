/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1

import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory

import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung

/**
 * Transform basis objects to footnotes.
 * 
 * @author Schaefer
 */
class FootnoteTransformation {

	var TableRow row

	/**
	 * @param row the table row
	 */
	def void transform(Basis_Objekt object, TableRow row) {
		this.row = row
		object?.objectFootnotes?.map[value]?.toSet?.forEach[addFootnote]
	}

	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getObjectFootnotes(
		Basis_Objekt object) {
		return object.IDBearbeitungsvermerk
	}

	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getObjectFootnotes(
		Signal signal) {
		val signalFootNotes = signal?.IDBearbeitungsvermerk
		val signalRahmenFootNotes = signal?.signalRahmen?.flatMap [
			IDBearbeitungsvermerk
		]
		val signalBefestigungFootNotes = signal?.signalRahmen?.flatMap [
			signalBefestigung?.objectFootnotes
		].filterNull
		return #[signalFootNotes, signalRahmenFootNotes,
			signalBefestigungFootNotes].filterNull.flatten
	}

	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getObjectFootnotes(
		Signal_Befestigung signalBefestigung) {
		if (signalBefestigung === null) {
			return null
		}
		val befestigungFootnotes = signalBefestigung?.IDBearbeitungsvermerk
		if (signalBefestigung?.IDSignalBefestigung?.value !== null) {
			return #[befestigungFootnotes,
				signalBefestigung?.IDSignalBefestigung?.value?.objectFootnotes].
				filterNull.flatten
		}
		return befestigungFootnotes
	}

	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getObjectFootnotes(
		W_Kr_Gsp_Element gspElement) {
		val gspElementFootNotes = gspElement?.IDBearbeitungsvermerk
		val gspKomponentFootNotes = gspElement?.WKrGspKomponenten?.flatMap [
			IDBearbeitungsvermerk
		]
		val gspAnlageFootNotes = gspElement?.WKrAnlage?.IDBearbeitungsvermerk
		return #[gspElementFootNotes, gspKomponentFootNotes,
			gspAnlageFootNotes].filterNull.flatten
	}

	private def void addFootnote(Bearbeitungsvermerk comment) {
		if (row.footnotes === null)
			row.footnotes = TablemodelFactory.eINSTANCE.
				createSimpleFootnoteContainer()
		(row.footnotes as SimpleFootnoteContainer).footnotes.add(comment)
	}
}
