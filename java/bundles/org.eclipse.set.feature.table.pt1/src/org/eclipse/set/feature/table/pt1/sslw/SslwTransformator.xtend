/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslw

import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Schutz
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Schutz_Weitergabe_AttributeGroup
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Zwieschutz
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Zwieschutz_Element_AttributeGroup
import org.eclipse.set.toolboxmodel.Flankenschutz.Massnahme_TypeClass
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.sslw.SslwColumns.*
import static org.eclipse.set.toolboxmodel.Flankenschutz.ENUMMassnahme.*
import static org.eclipse.set.toolboxmodel.Flankenschutz.ENUMZwieschutzArt.*
import static org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMWKrArt.*

import static extension org.eclipse.set.ppmodel.extensions.FlaFreimeldeZuordnungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaSchutzExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaZwieschutzExtensions.*

/**
 * Table transformation for a Zwieschutzweichentabelle (SSLW).
 * 
 * @author Schneider
 */
class SslwTransformator extends AbstractPlanPro2TableModelTransformator {

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
	}

	private def transformSingleSide(TableRow instance,
		Fla_Zwieschutz flaZwieSchutz, boolean isLeft) {

		// A: Sslw.W_Kr_Stellung
		fill(
			instance,
			cols.getColumn(W_Kr_Stellung),
			flaZwieSchutz,
			[
				zwieschutzweiche?.bezeichnung?.bezeichnungTabelle?.wert +
					(isLeft ? "L" : "R")
			]
		)

		// B: Sslw.Art.Eigen
		fillConditional(
			instance,
			cols.getColumn(Art_Eigen),
			flaZwieSchutz,
			[
				zwieschutzArt?.wert === ENUM_ZWIESCHUTZ_ART_EIGEN ||
					zwieschutzArt?.wert === ENUM_ZWIESCHUTZ_ART_ECHT_EIGEN
			],
			["x"]
		)

		// C: Sslw.Art.Echt
		fillConditional(
			instance,
			cols.getColumn(Art_Echt),
			flaZwieSchutz,
			[
				zwieschutzArt?.wert === ENUM_ZWIESCHUTZ_ART_ECHT ||
					zwieschutzArt?.wert === ENUM_ZWIESCHUTZ_ART_ECHT_EIGEN
			],
			["x"]
		)

		// D: Sslw.Verschluss
		fillConditional(
			instance,
			cols.getColumn(Verschluss),
			flaZwieSchutz,
			[
				#{
					ENUM_MASSNAHME_VERSCHLUSS,
					ENUM_MASSNAHME_VERSCHLUSS_ERSATZSCHUTZ
				}.contains(flaZwieschutzElement?.massnameLR(isLeft))
			],
			["x"]
		)

		// E: Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Bezeichnung_W
		fillConditional(
			instance,
			cols.getColumn(Weiche_Gleissperre_Bezeichnung_W),
			flaZwieSchutz,
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft)?.flaSchutzWGsp !==
					null
			],
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					weicheGleissperreElement?.bezeichnung?.
					bezeichnungTabelle?.wert
			]
		)

		// F: Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Lage
		fillConditional(
			instance,
			cols.getColumn(Weiche_Gleissperre_Lage),
			flaZwieSchutz,
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft)?.flaSchutzWGsp !==
					null
			],
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft)?.flaSchutzWGsp?.
					flaWLage?.wert?.translate
			]
		)

		// G: Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Zwieschutz
		fillConditional(
			instance,
			cols.getColumn(Weiche_Gleissperre_Zwieschutz),
			flaZwieSchutz,
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft)?.flaSchutzWGsp !==
					null
			],
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft).hasZwieschutz.
					translate
			]
		)

		// H: Sslw.Ersatzschutz_unmittelbar.Signal.Bezeichnung_Sig
		fillConditional(
			instance,
			cols.getColumn(Bezeichnung_Sig),
			flaZwieSchutz,
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft)?.flaSchutzSignal !==
					null
			],
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft)?.signal?.bezeichnung?.
					bezeichnungTabelle?.wert
			]
		)

		// I: Sslw.Ersatzschutz_unmittelbar.Signal.Rangierzielsperre
		fillConditional(
			instance,
			cols.getColumn(Signal_Zielsperrung),
			flaZwieSchutz,
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft)?.flaSchutzSignal !==
					null
			],
			[
				flaZwieschutzElement?.IDFlaSchutz(isLeft).hasZielsperrung.
					translate
			]
		)

		// J: Sslw.Ersatzschutz_Weitergabe.Weiche_Kreuzung.Bezeichnung_W_Kr
		val bezWKrNotEKW_LR = [ boolean isWeitergabeLeft |
			return new Case<Fla_Zwieschutz>([
				val weitergabe = flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe
				val anforderer = weitergabe?.
					IDFlaWeitergabeLR(isWeitergabeLeft)?.flaSchutzAnforderer?.
					IDAnfordererElement

				return (anforderer instanceof W_Kr_Gsp_Element) &&
					((anforderer as W_Kr_Gsp_Element).IDWKrAnlage?.
						WKrAnlageAllg?.WKrArt?.wert !== ENUMW_KR_ART_EKW)
			], [
				(flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe?.IDFlaWeitergabeLR(isWeitergabeLeft)?.
					flaSchutzAnforderer?.
					IDAnfordererElement as W_Kr_Gsp_Element)?.bezeichnung?.
					bezeichnungTabelle?.wert
			])
		]

		val bezWKrEKW_NoKr_LR = [ boolean isWeitergabeLeft |
			new Case<Fla_Zwieschutz>([
				val flaAnforderer = flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe?.IDFlaWeitergabeLR(isWeitergabeLeft)?.
					flaSchutzAnforderer
				val anforderer = flaAnforderer?.IDAnfordererElement

				return (anforderer instanceof W_Kr_Gsp_Element) &&
					((anforderer as W_Kr_Gsp_Element).IDWKrAnlage?.
						WKrAnlageAllg?.WKrArt?.wert === ENUMW_KR_ART_EKW) &&
					flaAnforderer?.EKWKrAnteil?.wert != Boolean.TRUE
			], [
				(flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe?.IDFlaWeitergabeLR(isWeitergabeLeft)?.
					flaSchutzAnforderer?.
					IDAnfordererElement as W_Kr_Gsp_Element)?.bezeichnung?.
					bezeichnungTabelle?.wert
			])
		]
		val bezWKrEKW_Kr_LR = [ boolean isWeitergabeLeft |
			new Case<Fla_Zwieschutz>([
				val flaAnforderer = flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe?.IDFlaWeitergabeLR(isWeitergabeLeft)?.
					flaSchutzAnforderer
				val anforderer = flaAnforderer?.IDAnfordererElement

				// The case 
				return (anforderer instanceof W_Kr_Gsp_Element) &&
					((anforderer as W_Kr_Gsp_Element).IDWKrAnlage?.
						WKrAnlageAllg?.WKrArt?.wert === ENUMW_KR_ART_EKW) &&
					flaAnforderer?.EKWKrAnteil?.wert == Boolean.TRUE
			], [
				val bezeichnung = (flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe?.IDFlaWeitergabeLR(isWeitergabeLeft)?.
					flaSchutzAnforderer?.
					IDAnfordererElement as W_Kr_Gsp_Element)?.bezeichnung
				return bezeichnung.kennzahl + "Kr" +
					bezeichnung.oertlicherElementname
			])
		]

		fillSwitchGrouped(instance,
			cols.getColumn(Weiche_Kreuzung_Bezeichnung_W_Kr), flaZwieSchutz, [
				filterNull.flatMap[filling?.apply(flaZwieSchutz)].filterNull.
					toSet
			], #[
				bezWKrNotEKW_LR.apply(true),
				bezWKrEKW_NoKr_LR.apply(true),
				bezWKrEKW_Kr_LR.apply(true)
			], #[
				bezWKrNotEKW_LR.apply(false),
				bezWKrEKW_NoKr_LR.apply(false),
				bezWKrEKW_Kr_LR.apply(false)
			])

		// K: Sslw.Ersatzschutz_Weitergabe.Weiche_Kreuzung.wie_Fahrt_ueber
		fillSwitch(
			instance,
			cols.getColumn(Weiche_Kreuzung_wie_Fahrt_ueber),
			flaZwieSchutz,
			new Case<Fla_Zwieschutz>([
				val flaSchutz = flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe
				flaSchutz?.IDFlaWeitergabeL !== null &&
					flaSchutz?.IDFlaWeitergabeR !== null
			], [
				"L+R"
			]),
			new Case<Fla_Zwieschutz>([
				val flaSchutz = flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe
				flaSchutz?.IDFlaWeitergabeL !== null &&
					flaSchutz?.IDFlaWeitergabeR === null
			], [
				"L"
			]),
			new Case<Fla_Zwieschutz>([
				val flaSchutz = flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					flaSchutzWeitergabe
				flaSchutz?.IDFlaWeitergabeL === null &&
					flaSchutz?.IDFlaWeitergabeR !== null
			], [
				"R"
			])
		)

		// L: Sslw.Technischer_Verzicht
		fillConditional(
			instance,
			cols.getColumn(Technischer_Verzicht),
			flaZwieSchutz,
			[
				flaZwieschutzElement?.massnameLR(isLeft)?.wert ===
					ENUM_MASSNAHME_VERZICHT
			],
			[
				"x"
			]
		)

		// M: Sslw.Schutzraumueberwachung.freigemeldet
		fillIterable(
			instance,
			cols.getColumn(freigemeldet),
			flaZwieSchutz,
			[
				(flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					freimeldeZuordnungen?.filter [
						flaRaumFreimeldung.wert
					] ?: newLinkedList()).map [
					fmaAnlage?.IDGleisAbschnitt?.bezeichnung?.
						bezeichnungTabelle?.wert
				]
			],
			MIXED_STRING_COMPARATOR
		)

		// N: Sslw.Schutzraumueberwachung.nicht_freigemeldet
		fillIterable(
			instance,
			cols.getColumn(nicht_freigemeldet),
			flaZwieSchutz,
			[
				(flaZwieschutzElement?.IDFlaSchutz(isLeft)?.
					freimeldeZuordnungen?.filter [
						!flaRaumFreimeldung.wert
					] ?: newLinkedList()).map [
					fmaAnlage?.IDGleisAbschnitt?.bezeichnung?.
						bezeichnungTabelle?.wert
				]
			],
			MIXED_STRING_COMPARATOR
		)

		// O: Sslw.Nachlaufverhinderung		
		fill(
			instance,
			cols.getColumn(Nachlaufverhinderung),
			flaZwieSchutz,
			[flaZwieschutzElement?.nachlaufverhinderung?.wert?.translate]
		)

		// P: Sslw.Bemerkung		
		fill(
			instance,
			cols.getColumn(Bemerkung),
			flaZwieSchutz,
			[footnoteTransformation?.transform(it, instance)]
		)

	}

	def Fla_Schutz IDFlaWeitergabeLR(
		Fla_Schutz_Weitergabe_AttributeGroup flaWeitergabe,
		boolean isWeitergabeLeft) {
		return isWeitergabeLeft
			? flaWeitergabe?.IDFlaWeitergabeL
			: flaWeitergabe?.IDFlaWeitergabeR
	}

	def Massnahme_TypeClass massnameLR(
		Fla_Zwieschutz_Element_AttributeGroup element, boolean isLeft) {
		return isLeft ? element.massnahmeL : element.massnahmeR
	}

	def Fla_Schutz IDFlaSchutz(Fla_Zwieschutz_Element_AttributeGroup element,
		boolean isLeft) {
		return isLeft ? element.IDFlaSchutzL : element.IDFlaSchutzR
	}

	override transformTableContent(
		MultiContainer_AttributeGroup container,
		TMFactory factory
	) {
		val flaZwieSchutzList = container.flaZwieschutz;
		for (flaZwieSchutz : flaZwieSchutzList) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val rowGroup = factory.newRowGroup(flaZwieSchutz)
			transformSingleSide(rowGroup.newTableRow(), flaZwieSchutz, true)
			transformSingleSide(rowGroup.newTableRow(), flaZwieSchutz, false)
		}

		return factory.table
	}
}
