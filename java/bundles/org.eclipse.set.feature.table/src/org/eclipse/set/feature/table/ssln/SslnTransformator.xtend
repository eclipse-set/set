/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssln

import com.google.common.collect.Lists
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Basis_Objekt
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt
import de.scheidtbachmann.planpro.model.model1902.Nahbedienbereich.NB_Zone
import de.scheidtbachmann.planpro.model.model1902.Nahbedienbereich.NB_Zone_Element
import de.scheidtbachmann.planpro.model.model1902.Nahbedienbereich.NB_Zone_Grenze
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Komponente
import de.scheidtbachmann.planpro.model.model1902.Schluesselabhaengigkeiten.Schluesselsperre
import de.scheidtbachmann.planpro.model.model1902.Signale.Signal
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import java.util.List
import org.eclipse.set.basis.Pair
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.TMFactory

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaSchutzExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MarkanterPunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbBedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneGrenzeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import static extension org.eclipse.set.utils.StringExtensions.*

/**
 * Table transformation for a Nahbedienungstabelle (SSLN).
 * 
 * @author Schaefer
 */
class SslnTransformator extends AbstractPlanPro2TableModelTransformator {

	val SslnColumns columns

	var TMFactory factory

	new(SslnColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.columns = columns
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		container.NBZone.forEach [ it |
			if (Thread.currentThread.interrupted) {
				return
			}
			it.transform
		]
		return
	}

	private def TableRow create factory.newTableRow(nbZone) transform(
		NB_Zone nbZone) {
		val row = it
		fill(columns.bereich_zone, nbZone, [bezeichnung])

		fill(columns.art, nbZone, [
			nb?.NBAllg?.NBArt?.wert?.translate
		])

		fillConditional(
			columns.untergeordnet,
			nbZone,
			[IDNBZone !== null],
			[NBZone?.NBZoneAllg?.NBZoneBezeichnung?.wert?.toString]
		)

		fillConditional(
			columns.rang_zuschaltung,
			nbZone,
			[IDNBZone !== null],
			[NBZoneAllg?.rang?.wert?.toString]
		)

		fill(
			columns.aufloesung_grenze,
			nbZone,
			[NBZoneAllg?.NBVerhaeltnisBesonders?.wert?.translate]
		)

		fillIterable(
			columns.bez_grenze,
			nbZone,
			[
				NBZoneGrenzen.map [
					markanterPunkt?.markanteStelle?.toBezeichnungGrenze(it)
				].filterNull
			],
			null
		)

		fillIterable(
			columns.weiche_gs_frei_stellbar,
			nbZone,
			[
				val nBZoneElemente = NBZoneElemente.filter [
					NBZoneElementAllg?.freieStellbarkeit?.wert &&
						nbElement instanceof W_Kr_Gsp_Komponente
				]

				nBZoneElemente.filterMultipleNbElements.map [
					'''«(nbElement as W_Kr_Gsp_Komponente)?.WKrGspElement?.bezeichnung?.bezeichnungTabelle?.wert» («NBZoneElementAllg?.NBRueckgabevoraussetzung?.wert?.translate»)'''
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		fillIterable(
			columns.verschlossen,
			nbZone,
			[
				NBZoneElemente.map[new Pair(it, nbElement)].filter [
					!first?.NBZoneElementAllg?.freieStellbarkeit?.wert &&
						second instanceof W_Kr_Gsp_Komponente
				].map[second as W_Kr_Gsp_Komponente].map [
					WKrGspElement?.bezeichnung?.bezeichnungTabelle?.wert
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		fillIterable(
			columns.signal_frei_stellbar,
			nbZone,
			[
				NBZoneElemente.map[new Pair(it, nbElement)].filter [
					first?.NBZoneElementAllg?.freieStellbarkeit?.wert &&
						second instanceof Signal
				].map [
					'''«(second as Signal)?.bezeichnung?.bezeichnungTabelle?.wert» («first?.NBZoneElementAllg?.NBRueckgabevoraussetzung?.wert?.translate»)'''
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		fillIterable(
			columns.kennlicht,
			nbZone,
			[
				NBZoneElemente.map[new Pair(it, nbElement)].filter [
					first?.NBZoneElementAllg?.freieStellbarkeit?.wert !==
						null &&
						!first.NBZoneElementAllg.freieStellbarkeit.wert.
							booleanValue && second instanceof Signal
				].map[second as Signal].filter [
					val signal = it
					nbZone.NBZoneGrenzen.forall [
						markanterPunkt?.markanteStelle?.identitaet?.wert !=
							signal.identitaet.wert
					]
				].map[bezeichnung?.bezeichnungTabelle?.wert]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		fillIterable(
			columns.ssp,
			nbZone,
			[
				NBZoneElemente.map[nbElement].filter [
					it instanceof Schluesselsperre
				].map[it as Schluesselsperre].map [
					bezeichnung?.bezeichnungTabelle?.wert
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		fillIterable(
			columns.bedien_einr,
			nbZone,
			[
				NBBedienAnzeigeElemente.map [
					bedienAnzeigeElement?.bedienEinrichtungOertlich
				].filterNull.map[bezeichnung?.bedienEinrichtOertlBez?.wert]
			],
			null,
			[it]
		)

		fillIterable(
			columns.bedienungshandlung,
			nbZone,
			[nbZone.nb?.NBFunktionalitaetNBREnums.map[toString]],
			null,
			[it],
			String.format("%n")
		)

		fill(
			columns.basis_bemerkung,
			nbZone,
			[footnoteTransformation.transform(it, row)]
		)

		return
	}

	private static def Iterable<NB_Zone_Element> filterMultipleNbElements(
		Iterable<NB_Zone_Element> elements) {
		if (elements.empty) {
			return Lists.newArrayList
		}
		val head = elements.head
		val tail = elements.tail

		if (head.hasOtherNbElementIn(tail)) {
			// we skip the head and use the other nbElement with the tail
			return tail.filterMultipleNbElements
		} else {
			// we use the head and filter the tail
			return Lists.newArrayList(head) + tail.filterMultipleNbElements
		}
	}

	private static def boolean hasOtherNbElementIn(NB_Zone_Element element,
		Iterable<NB_Zone_Element> elements) {
		return elements.exists[hasDifferentNbElementInSameWeiche(element)]
	}

	private static def boolean hasDifferentNbElementInSameWeiche(
		NB_Zone_Element element, NB_Zone_Element otherElement) {
		return element.nbElement.
			isDifferentKomponenteInSameWeiche(otherElement.nbElement)
	}

	private static def boolean isDifferentKomponenteInSameWeiche(
		Basis_Objekt komponente, Basis_Objekt otherKomponente) {
		if (komponente instanceof W_Kr_Gsp_Komponente) {
			if (otherKomponente instanceof W_Kr_Gsp_Komponente) {
				if (komponente.identitaet.wert ==
					otherKomponente.identitaet.wert) {
					return false
				}
				val wKrGspElement = komponente.WKrGspElement
				val otherWKrGspElement = otherKomponente.WKrGspElement
				return wKrGspElement?.identitaet?.wert ==
					otherWKrGspElement?.identitaet?.wert
			}
		}
		return false
	}

	private static def List<String> getNbGrenzeBezeichnung(
		List<W_Kr_Gsp_Element> wKrGspElemente) {
		return wKrGspElemente.map [
			bezeichnung?.bezeichnungTabelle?.wert?.removeSuffix("A", "B", "AB",
				"CD") ?: ""
		]
	}

	override void formatTableContent(Table table) {
		// A: Ssln.Grundsatzangaben.Bereich_Zone
		table.setTextAlignment(0, TextAlignment.LEFT);

		// N: Ssln.Bemerkung
		table.setTextAlignment(13, TextAlignment.LEFT);
	}

	private static dispatch def String toBezeichnungGrenze(
		Punkt_Objekt markanteStelle,
		NB_Zone_Grenze grenze
	) {
		return null
	}

	private static dispatch def String toBezeichnungGrenze(
		Void markanteStelle,
		NB_Zone_Grenze grenze
	) {
		return null
	}

	private static dispatch def String toBezeichnungGrenze(
		Signal markanteStelle,
		NB_Zone_Grenze grenze
	) {
		return grenze.toBezeichnungGrenze
	}

	private static dispatch def String toBezeichnungGrenze(
		W_Kr_Gsp_Komponente markanteStelle,
		NB_Zone_Grenze grenze
	) {
		val bezeichnung = getIterableFilling(
			markanteStelle.topKanten.flatMap [
				WKrGspElemente.nbGrenzeBezeichnung
			],
			ToolboxConstants.LST_OBJECT_NAME_COMPARATOR,
			"/"
		)
		val innen = markanteStelle.WKrGspElement.bezeichnung?.
			bezeichnungTabelle?.wert ?: ""
		val aussen = grenze.flaSchutzElemente
		return '''«bezeichnung» («innen», «aussen»)'''
	}

	private static dispatch def String toBezeichnungGrenze(
		FMA_Komponente markanteStelle,
		NB_Zone_Grenze grenze
	) {
		return grenze.toBezeichnungGrenze
	}

	private static def String toBezeichnungGrenze(
		NB_Zone_Grenze grenze
	) {
		val bezeichnung = grenze?.markanterPunkt?.bezeichnung?.
			bezeichnungMarkanterPunkt?.wert
		val innen = bezeichnung
		val aussen = grenze.flaSchutzElemente
		return '''«bezeichnung» («innen», «aussen»)'''
	}

	private static def String flaSchutzElemente(NB_Zone_Grenze grenze) {
		val elemente = grenze.flaSchutz.map [
			weicheGleissperreElement.bezeichnung?.bezeichnungTabelle?.wert ?: ""
		]
		return if (elemente.empty) {
			"-"
		} else {
			getIterableFilling(elemente, MIXED_STRING_COMPARATOR, " ")
		}
	}
}
