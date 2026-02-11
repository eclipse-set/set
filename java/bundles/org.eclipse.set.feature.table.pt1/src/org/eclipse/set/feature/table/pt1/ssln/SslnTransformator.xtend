/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssln

import com.google.common.collect.Lists
import java.util.Set
import org.eclipse.set.basis.Pair
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Nahbedienung.ENUMNBGrenzeArt
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone_Element
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone_Grenze
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.ssln.SslnColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaSchutzExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MarkanterPunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbBedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneGrenzeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*

/**
 * Table transformation for a Nahbedienungstabelle (Ssln).
 * 
 * @author Schaefer
 */
class SslnTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
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

		// A: Ssln.Grundsatzangaben.Bereich_Zone
		fill(cols.getColumn(Bereich_Zone), nbZone, [getBezeichnung(it)])

		// B: Ssln.Grundsatzangaben.Art
		fill(
			cols.getColumn(Art),
			nbZone,
			[nb?.NBArt?.translate]
		)

		// C: Ssln.Unterstellungsverhaeltnis.untergeordnet
		fillConditional(
			cols.getColumn(untergeordnet),
			nbZone,
			[IDNBZone !== null],
			[NBZone?.bezeichnung.bezeichnungNBZone?.wert?.toString]
		)

		// D: Ssln.Unterstellungsverhaeltnis.Rang_Zuschaltung
		fillConditional(
			cols.getColumn(Rang_Zuschaltung),
			nbZone,
			[IDNBZone !== null],
			[NBZoneAllg?.rang?.wert?.toString]
		)

		// E: Ssln.Unterstellungsverhaeltnis.Aufloesung_Grenze
		fill(
			cols.getColumn(Aufloesung_Grenze),
			nbZone,
			[NBZoneAllg?.NBVerhaeltnisBesonders?.translate]
		)

		// F: Ssln.Grenze.Bez_Grenze
		fillIterable(
			cols.getColumn(Bez_Grenze),
			nbZone,
			[
				NBZoneGrenzen.filter [
					NBGrenzeArt?.wert ===
						ENUMNBGrenzeArt.ENUMNB_GRENZE_ART_ESTW_BEREICH ||
						NBGrenzeArt?.wert ===
							ENUMNBGrenzeArt.ENUMNB_GRENZE_ART_NB_ZONE
				].map [
					markanterPunkt?.markanteStelle?.toBezeichnungGrenze(it)
				].filterNull
			],
			null
		)

		// G: Ssln.Element.Weiche_Gs.frei_stellbar
		fillIterable(
			cols.getColumn(Weiche_Gs_frei_stellebar),
			nbZone,
			[
				val nBZoneElemente = NBZoneElemente.filter [
					NBZoneElementAllg?.freieStellbarkeit?.wert
				]

				nBZoneElemente.filterMultipleNbElements.map [
					val zonenElement = nbElement
					if (zonenElement instanceof W_Kr_Gsp_Komponente) {
						return it -> zonenElement.WKrGspElement
					}
					if (zonenElement instanceof W_Kr_Gsp_Element) {
						return it -> zonenElement
					}
					return null
				].filterNull.toSet.map [
					'''«value?.bezeichnung?.bezeichnungTabelle?.wert» («key?.NBZoneElementAllg?.NBRueckgabevoraussetzung?.translate»)'''
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// H: Ssln.Element.Weiche_Gs.verschlossen
		fillIterable(
			cols.getColumn(Weiche_Gs_verschlossen),
			nbZone,
			[
				val zoneElements = NBZoneElemente.filter [
					!NBZoneElementAllg?.freieStellbarkeit?.wert
				]
				val wKrGspElements = zoneElements.map [
					val zonenElement = nbElement;
					if (zonenElement instanceof W_Kr_Gsp_Komponente) {
						return new Pair(it, zonenElement.WKrGspElement)
					}
					if (zonenElement instanceof W_Kr_Gsp_Element) {
						return new Pair(it, zonenElement)
					}
					return null
				].filterNull

				return wKrGspElements.map [
					'''«second.bezeichnung?.bezeichnungTabelle?.wert» («first?.NBZoneElementAllg?.WGspLage?.translate ?: "-"»)'''
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// I: Ssln.Element.Signal.frei_stellbar
		fillIterable(
			cols.getColumn(Signal_frei_stellbar),
			nbZone,
			[
				NBZoneElemente.map[new Pair(it, nbElement)].filter [
					first?.NBZoneElementAllg?.freieStellbarkeit?.wert &&
						second instanceof Signal
				].map [
					'''«(second as Signal)?.bezeichnung?.bezeichnungTabelle?.wert» («first?.NBZoneElementAllg?.NBRueckgabevoraussetzung?.translate»)'''
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// J: Ssln.Erlaubnis.staendig
		fillIterable(
			cols.getColumn(Erlaubnis_staendig),
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

		// K: Ssln.Element.Ssp
		fillIterable(
			cols.getColumn(Ssp),
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

		// L: Ssln.Element.Bedien_Einr
		fillIterable(
			cols.getColumn(Bedien_Einr),
			nbZone,
			[
				NBBedienAnzeigeElemente.map [
					bedienAnzeigeElement?.bedienEinrichtungOertlich
				].filterNull.map[bezeichnung?.bedienEinrichtOertlBez?.wert]
			],
			null,
			[it]
		)

		// M: Ssln.NB_R.Bedienungshandlung
		fillIterable(
			cols.getColumn(Bedienungshandlung),
			nbZone,
			[
				nb?.NBFunktionalitaetNBREnums.map[toString]
			],
			null,
			[it],
			String.format("%n")
		)

		// N: Ssln.Bemerkung
		fillFootnotes(nbZone)

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

	private static dispatch def String toBezeichnungGrenze(
		Basis_Objekt markanteStelle,
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
		FMA_Komponente markanteStelle,
		NB_Zone_Grenze grenze
	) {
		return grenze.toBezeichnungGrenze
	}
	
	private static dispatch def String toBezeichnungGrenze(
		W_Kr_Gsp_Komponente markanteStelle,
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
			weicheGleissperreElement?.bezeichnung?.bezeichnungTabelle?.wert ?: ""
		]
		return if (elemente.empty) {
			"-"
		} else {
			getIterableFilling(elemente, MIXED_STRING_COMPARATOR, " ")
		}
	}
}
