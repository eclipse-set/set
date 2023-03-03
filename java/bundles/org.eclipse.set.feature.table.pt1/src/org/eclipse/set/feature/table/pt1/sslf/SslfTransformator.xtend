/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslf

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Schutz
import org.eclipse.set.toolboxmodel.Nahbedienung.NB_Zone_Grenze
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.toolboxmodel.Flankenschutz.ENUMFahrtUeber.*

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaFreimeldeZuordnungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaSchutzExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static org.eclipse.set.feature.table.pt1.sslf.SslfColumns.*
import java.util.Set
import org.eclipse.set.model.tablemodel.ColumnDescriptor

/**
 * Table transformation for a Flankenschutztabelle (SSLF).
 * 
 * @author Schaefer
 */
class SslfTransformator extends AbstractPlanPro2TableModelTransformator {

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
	}

	override transformTableContent(
		MultiContainer_AttributeGroup container,
		TMFactory factory
	) {
		val flaSchutzList = container.flaSchutz.filter[generalbedingung].sortBy [
			wLageNbGrenze
		]

		for (flaSchutz : flaSchutzList) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(flaSchutz)

			// A: Sslf.Flankenschutzanforderer.Bezeichnung_W_Nb
			fill(
				instance,
				cols.getColumn(Bezeichnung_W_Nb),
				flaSchutz,
				[anforderer.bezeichnungTabelle]
			)

			// B: Sslf.Flankenschutzanforderer.WLage_NbGrenze
			fill(instance, cols.getColumn(WLage_NbGrenze), flaSchutz, [
				wLageNbGrenze
			])

			// C: Sslf.Unmittelbarer_Flankenschutz.Weiche_Gleissperre.Bezeichnung_W
			fillConditional(
				instance,
				cols.getColumn(Bezeichnung_W),
				flaSchutz,
				[flaSchutz.flaSchutzWGsp !== null],
				[weicheGleissperreElement.bezeichnung.bezeichnungTabelle.wert]
			)

			// D: Sslf.Unmittelbarer_Flankenschutz.Weiche_Gleissperre.Lage
			fill(
				instance,
				cols.getColumn(Weiche_Gleissperre_Lage),
				flaSchutz,
				[flaSchutzWGsp?.flaWLage?.wert?.translate ?: ""]
			)

			// E: Sslf.Unmittelbarer_Flankenschutz.Weiche_Gleissperre.Zwieschutz
			fillConditional(
				instance,
				cols.getColumn(Weiche_Gleissperre_Zwieschutz),
				flaSchutz,
				[flaSchutzWGsp !== null],
				[if (hasZwieschutz) "x" else "o"]
			)

			// F: Sslf.Unmittelbarer_Flankenschutz.Signal.Bezeichnung_Sig
			fillConditional(
				instance,
				cols.getColumn(Bezeichung_Sig),
				flaSchutz,
				[flaSchutzSignal !== null],
				[signal.bezeichnung.bezeichnungTabelle.wert]
			)

			// G: Sslf.Unmittelbarer_Flankenschutz.Signal.Zielsperrung
			fillConditional(
				instance,
				cols.getColumn(Signal_Zielsperrung),
				flaSchutz,
				[flaSchutzSignal !== null],
				[hasZielsperrung.translate]
			)

			// H: Sslf.Weitergabe.Weiche_Kreuzung.Bezeichnung_W_Kr
			fill(
				instance,
				cols.getColumn(Weiche_Kreuzung_Bezeichnung_W_Kr),
				flaSchutz,
				[weitergabeWKrBezeichnung]
			)

			// I: Sslf.Weitergabe.Weiche_Kreuzung.wie_Fahrt_ueber
			fill(
				instance,
				cols.getColumn(Weiche_Kreuzung_wie_Fahrt_ueber),
				flaSchutz,
				[weitergabeWKrWieFahrtUeber]
			)

			// J: Sslf.Weitergabe.Zusaetzlich_EKW.Bezeichnung_W_Kr
			fillConditional(
				instance,
				cols.getColumn(Zusaetzlich_EKW_Bezeichnung_W_Kr),
				flaSchutz,
				[IDFlaWeitergabeEKW !== null],
				[
					(weitergabeEKW.anforderer as W_Kr_Gsp_Element).bezeichnung.
						bezeichnungTabelle.wert
				]
			)

			// K: Sslf.Weitergabe.Zusaetzlich_EKW.wie_Fahrt_ueber
			fill(
				instance,
				cols.getColumn(Zusaetzlich_EKW_wie_Fahrt_ueber),
				flaSchutz,
				[
					weitergabeEKW?.flaSchutzAnforderer?.fahrtUeber?.wert?.
						translate ?: ""
				]
			)

			// L: Sslf.Technischer_Verzicht
			fill(
				instance,
				cols.getColumn(Technischer_Verzicht),
				flaSchutz,
				[flaVerzicht?.wert?.translate ?: ""]
			)

			// M: Sslf.Schutzraumueberwachung.freigemeldet
			fillIterable(
				instance,
				cols.getColumn(freigemeldet),
				flaSchutz,
				[
					freimeldeZuordnungen.filter[flaRaumFreimeldung.wert].map [
						fmaAnlage.gleisabschnitt
					].map [
						bezeichnung.bezeichnungTabelle.wert
					]
				],
				MIXED_STRING_COMPARATOR
			)

			// N: Sslf.Schutzraumueberwachung.nicht_freigemeldet
			fillIterable(
				instance,
				cols.getColumn(nicht_freigemeldet),
				flaSchutz,
				[
					freimeldeZuordnungen.filter[!flaRaumFreimeldung.wert].map [
						fmaAnlage.gleisabschnitt
					].map [
						bezeichnung.bezeichnungTabelle.wert
					]
				],
				MIXED_STRING_COMPARATOR
			)

			// O: Sslf.Bemerkung
			fill(
				instance,
				cols.getColumn(Bemerkung),
				flaSchutz,
				[footnoteTransformation.transform(it, instance)]
			)

		}

		return factory.table
	}

	private def boolean isGeneralbedingung(Fla_Schutz flaSchutz) {
		return newLinkedList(ENUM_FAHRT_UEBER_LINKS, ENUM_FAHRT_UEBER_RECHTS).
			contains(
				flaSchutz?.flaSchutzAnforderer?.fahrtUeber?.wert
			) || flaSchutz?.anforderer instanceof NB_Zone_Grenze
	}

	private def String getWeitergabeWKrBezeichnung(Fla_Schutz flaSchutz) {
		val hasFlaWeitergabeL = flaSchutz?.flaSchutzWeitergabe?.
			IDFlaWeitergabeL !== null
		val hasFlaWeitergabeR = flaSchutz?.flaSchutzWeitergabe?.
			IDFlaWeitergabeR !== null

		if (hasFlaWeitergabeL) {
			return (flaSchutz.weitergabeL.anforderer as W_Kr_Gsp_Element).
				bezeichnung.bezeichnungTabelle.wert
		}

		if (hasFlaWeitergabeR && !hasFlaWeitergabeL) {
			return (flaSchutz.weitergabeR.anforderer as W_Kr_Gsp_Element).
				bezeichnung.bezeichnungTabelle.wert
		}

		return ""
	}

	private def String getWeitergabeWKrWieFahrtUeber(Fla_Schutz flaSchutz) {
		val hasFlaWeitergabeL = flaSchutz?.flaSchutzWeitergabe?.
			IDFlaWeitergabeL !== null
		val hasFlaWeitergabeR = flaSchutz?.flaSchutzWeitergabe?.
			IDFlaWeitergabeR !== null

		if (hasFlaWeitergabeL && hasFlaWeitergabeR) {
			return "L+R"
		}

		if (hasFlaWeitergabeL && !hasFlaWeitergabeR) {
			return "L"
		}

		if (hasFlaWeitergabeR && !hasFlaWeitergabeL) {
			return "R"
		}

		return ""
	}

	private def String wLageNbGrenze(Fla_Schutz flaSchutz) {
		val fahrtUeber = flaSchutz?.flaSchutzAnforderer?.fahrtUeber?.wert

		if (fahrtUeber == ENUM_FAHRT_UEBER_LINKS) {
			return "L"
		}

		if (fahrtUeber == ENUM_FAHRT_UEBER_RECHTS) {
			return "R"
		}

		val anforderer = flaSchutz.anforderer

		if (anforderer instanceof NB_Zone_Grenze) {
			return anforderer?.markanterPunkt?.bezeichnung?.
				bezeichnungMarkanterPunkt?.wert ?: ""
		}

		return ""
	}

	private def dispatch String getBezeichnungTabelle(Basis_Objekt object) {
		throw new IllegalArgumentException(object.class.simpleName)
	}

	private def dispatch String getBezeichnungTabelle(
		W_Kr_Gsp_Element wKrGspElement) {
		return wKrGspElement?.bezeichnung?.bezeichnungTabelle?.wert
	}

	private def dispatch String getBezeichnungTabelle(
		NB_Zone_Grenze nbZoneGrenze) {
		val zone = nbZoneGrenze.nbZone
		return '''Nb«zone.nb.NBAllg.NBBezeichnung.wert.intValue»/«zone.NBZoneAllg.NBZoneBezeichnung.wert.intValue»'''
	}

	override void formatTableContent(Table table) {
		// A: Sslf.Flankenschutzanforderer.Bezeichnung_W_Nb
		table.setTextAlignment(0, TextAlignment.LEFT);

		// O: Sslf.Bemerkung
		table.setTextAlignment(14, TextAlignment.LEFT);
	}
}
