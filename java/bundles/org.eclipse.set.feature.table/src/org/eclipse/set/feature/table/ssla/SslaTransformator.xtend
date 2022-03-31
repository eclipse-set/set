/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssla

import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_Aneinander
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_Aneinander_Zuordnung
import java.util.LinkedList
import java.util.List
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrAneinanderExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*

/**
 * Table transformation for a aneinandergereihte Fahrstraßen (Ssla).
 * 
 * @author Schneider
 */
class SslaTransformator extends AbstractPlanPro2TableModelTransformator {

	SslaColumns cols

	new(SslaColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.cols = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		val fstrAneinanderList = container.fstrAneinander

		// Basis_Objekt
		for (fstrAneinander : fstrAneinanderList) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(fstrAneinander)

			// A: Ssla.Grundsatzangaben.Bezeichnung
			fill(
				instance,
				cols.basis_bezeichnung,
				fstrAneinander,
				[tableDescription]
			)

			// B: Ssla.Grundsatzangaben.Fahrweg.Start
			fill(
				instance,
				cols.start,
				fstrAneinander,
				[startSignal?.bezeichnung?.bezeichnungTabelle?.wert ?: ""]
			)

			// C: Ssla.Grundsatzangaben.Fahrweg.Ziel
			fill(
				instance,
				cols.ziel,
				fstrAneinander,
				[zielSignal?.bezeichnung?.bezeichnungTabelle?.wert ?: ""]
			)

			// D: Ssla.Grundsatzangaben.Durchrutschweg_Ziel
			fill(
				instance,
				cols.durchrutschweg_ziel,
				fstrAneinander,
				[zielDweg?.bezeichnung?.bezeichnungFstrDWeg?.wert ?: ""]
			)

			// E: Ssla.Grundsatzangaben.Art
			fill(
				instance,
				cols.art,
				fstrAneinander,
				[
					fstrZielZuordnung?.fstrZugRangier?.fstrZugRangierAllg?.
						fstrArt?.wert?.literal?.substring(0, 1) ?: ""
				]
			)

			// F: Ssla.Unterwegssignal
			fill(
				instance,
				cols.unterwegssignal,
				fstrAneinander,
				[fstrUnterwegsSignalString]
			)

			// G: Ssla.Bemerkung
			fill(
				instance,
				cols.basis_bemerkung,
				fstrAneinander,
				[footnoteTransformation.transform(it, instance)]
			)

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
			zuordnungenUnterwegs.add(zu)
			zuordnungen.remove(zu)
		}

		return '''«FOR zuordnung : zuordnungenUnterwegs SEPARATOR ' - '»«
		»«zuordnung.fstrZugRangier?.fstrFahrweg?.zielSignal?.bezeichnung?.bezeichnungTabelle?.wert»«
		»«IF zuordnung.fstrZugRangier?.fstrDWeg?.bezeichnung?.bezeichnungFstrDWeg?.wert !== null »«
		»(«zuordnung.fstrZugRangier?.fstrDWeg?.bezeichnung?.bezeichnungFstrDWeg?.wert»)«ENDIF»«ENDFOR»''';
	}

	override void formatTableContent(Table table) {
		// A: Grundsatzangaben.Bezeichnung
		table.setTextAlignment(0, TextAlignment.LEFT);

		// G: Bemerkung
		table.setTextAlignment(6, TextAlignment.LEFT);
	}
}
