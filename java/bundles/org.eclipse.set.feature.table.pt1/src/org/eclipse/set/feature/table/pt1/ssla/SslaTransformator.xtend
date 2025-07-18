/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssla

import java.util.LinkedList
import java.util.List
import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander_Zuordnung
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.ssla.SslaColumns.*

import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrAneinanderExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*

/**
 * Table transformation for a aneinandergereihte Fahrstraßen (Ssla).
 * 
 * @author Schneider
 */
class SslaTransformator extends AbstractPlanPro2TableModelTransformator {

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		val fstrAneinanderList = container.fstrAneinander
			.filter [isPlanningObject]
			.filterObjectsInControlArea(controlArea)

		// Basis_Objekt
		for (fstrAneinander : fstrAneinanderList) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(fstrAneinander)

			// A: Ssla.Grundsatzangaben.Bezeichnung
			fill(
				instance,
				cols.getColumn(Bezeichnung),
				fstrAneinander,
				[tableDescription]
			)

			// B: Ssla.Grundsatzangaben.Fahrweg.Start
			fill(
				instance,
				cols.getColumn(Fahrweg_Start),
				fstrAneinander,
				[startSignal?.bezeichnung?.bezeichnungTabelle?.wert ?: ""]
			)

			// C: Ssla.Grundsatzangaben.Fahrweg.Ziel
			fill(
				instance,
				cols.getColumn(Fahrweg_Ziel),
				fstrAneinander,
				[zielSignal?.bezeichnung?.bezeichnungTabelle?.wert ?: ""]
			)

			// D: Ssla.Grundsatzangaben.Durchrutschweg_Ziel
			fill(
				instance,
				cols.getColumn(Durchrutschweg_Ziel),
				fstrAneinander,
				[zielDweg?.bezeichnung?.bezeichnungFstrDWeg?.wert ?: ""]
			)

			// E: Ssla.Grundsatzangaben.Art
			fill(
				instance,
				cols.getColumn(Art),
				fstrAneinander,
				[
					if (zuordnungen.exists [
						fstrZugRangier?.fstrZug?.fstrZugArt?.wert?.literal?.
							substring(0, 1) == "Z"
					]) {
						return "Z"
					}
					return "R"
				]
			)

			// F: Ssla.Unterwegssignal
			fill(
				instance,
				cols.getColumn(Unterwegssignal),
				fstrAneinander,
				[fstrUnterwegsSignalString]
			)

			// G: Ssla.Bemerkung
			fillFootnotes(instance, fstrAneinander)
		}

		return factory.table
	}

	private def String getFstrUnterwegsSignalString(
		Fstr_Aneinander fstrAneinander) {
		val List<Fstr_Aneinander_Zuordnung> zuordnungen = fstrAneinander.
			zuordnungen
		var zuordnungenUnterwegs = new LinkedList<Fstr_Aneinander_Zuordnung>

		zuordnungen.remove(fstrAneinander.fstrZielZuordnung)

		while (zuordnungen.size > 0) {
			var zu = zuordnungen.fstrStartZuordnung
			if (zu === null) {
				throw new IllegalArgumentException(
					"Unable to find fstrStartZuordnung");
			}
			zuordnungenUnterwegs.add(zu)
			zuordnungen.remove(zu)
		}

		return '''«FOR zuordnung : zuordnungenUnterwegs SEPARATOR ' - '»«
		»«zuordnung.fstrZugRangier?.fstrFahrweg?.zielSignal?.bezeichnung?.bezeichnungTabelle?.wert»«
		»«IF zuordnung.fstrZugRangier?.fstrDWeg?.bezeichnung?.bezeichnungFstrDWeg?.wert !== null »«
		»(«zuordnung.fstrZugRangier?.fstrDWeg?.bezeichnung?.bezeichnungFstrDWeg?.wert»)«ENDIF»«ENDFOR»''';
	}
}
