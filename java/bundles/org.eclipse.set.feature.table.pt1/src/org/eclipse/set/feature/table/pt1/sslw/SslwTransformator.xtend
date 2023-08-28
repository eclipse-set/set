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
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.toolboxmodel.Flankenschutz.ENUMMassnahme
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Schutz
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Zwieschutz
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.sslw.SslwColumns.*
import static org.eclipse.set.toolboxmodel.Flankenschutz.ENUMMassnahme.*
import static org.eclipse.set.toolboxmodel.Flankenschutz.ENUMZwieschutzArt.*

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

	override transformTableContent(
		MultiContainer_AttributeGroup container,
		TMFactory factory
	) {
		val flaZwieSchutzList = container.flaZwieschutz;

		for (flaZwieSchutz : flaZwieSchutzList) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			var runLeft = true;

			for (var i = 0; i < 2; i++) {
				val instance = factory.newTableRow(flaZwieSchutz, i)
				val currentFlaSchutz = this.getCurrentFlaSchutz(flaZwieSchutz,
					runLeft);
				val currentDirectionString = this.
					getCurrentDirectionString(runLeft);
				val currentOppositeDirectionString = this.
					getCurrentOppositeDirectionString(runLeft);
				val currentDirectionWeitergabe = this.
					getCurrentWeitergabe(currentFlaSchutz, runLeft);
				val currentOppositeDirectionWeitergabe = this.
					getCurrentOppositeWeitergabe(currentFlaSchutz, runLeft);
				val currentDirectionWeitergabeId = this.
					getCurrentWeitergabeId(currentFlaSchutz, runLeft);
				val currentOppositeDirectionWeitergabeId = this.
					getCurrentOppositeWeitergabeId(currentFlaSchutz, runLeft);
				val currentMassnahme = getCurrentMassnahme(flaZwieSchutz,
					runLeft)

				// A: Sslw.W_Kr_Stellung
				fill(
					instance,
					cols.getColumn(W_Kr_Stellung),
					flaZwieSchutz,
					[
						zwieschutzweiche?.bezeichnung?.bezeichnungTabelle?.
							wert + currentDirectionString
					]
				)

				// B: Sslw.Art.Eigen
				fillConditional(
					instance,
					cols.getColumn(Art_Eigen),
					flaZwieSchutz,
					[
						zwieschutzArt?.wert === ENUM_ZWIESCHUTZ_ART_EIGEN ||
							zwieschutzArt?.wert ===
								ENUM_ZWIESCHUTZ_ART_ECHT_EIGEN
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
							zwieschutzArt?.wert ===
								ENUM_ZWIESCHUTZ_ART_ECHT_EIGEN
					],
					["x"]
				)

				// D: Sslw.Verschluss
				flaZwieSchutz.flaZwieschutzElement.massnahmeL.wert
				fillConditional(
					instance,
					cols.getColumn(Verschluss),
					flaZwieSchutz,
					[
						#{ENUM_MASSNAHME_VERSCHLUSS, ENUM_MASSNAHME_VERSCHLUSS_ERSATZSCHUTZ
							}.contains(
							currentMassnahme)
					],
					["x"]
				)

				// E: Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Bezeichnung_W
				fillConditional(
					instance,
					cols.getColumn(Weiche_Gleissperre_Bezeichnung_W),
					flaZwieSchutz,
					[
						currentFlaSchutz?.flaSchutzWGsp !== null
					],
					[
						currentFlaSchutz?.weicheGleissperreElement?.
							bezeichnung?.bezeichnungTabelle?.wert
					]
				)

				// F: Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Lage
				fillConditional(
					instance,
					cols.getColumn(Weiche_Gleissperre_Lage),
					flaZwieSchutz,
					[
						currentFlaSchutz?.flaSchutzWGsp !== null
					],
					[
						currentFlaSchutz?.flaSchutzWGsp?.flaWLage?.wert?.
							translate
					]
				)

				// G: Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Zwieschutz
				fillConditional(
					instance,
					cols.getColumn(Weiche_Gleissperre_Zwieschutz),
					flaZwieSchutz,
					[
						currentFlaSchutz?.flaSchutzWGsp !== null
					],
					[
						currentFlaSchutz.hasZwieschutz.translate
					]
				)

				// H: Sslw.Ersatzschutz_unmittelbar.Signal.Bezeichnung_Sig
				fillConditional(
					instance,
					cols.getColumn(Bezeichnung_Sig),
					flaZwieSchutz,
					[
						currentFlaSchutz?.flaSchutzSignal !== null
					],
					[
						currentFlaSchutz?.signal?.bezeichnung?.
							bezeichnungTabelle?.wert
					]
				)

				// I: Sslw.Ersatzschutz_unmittelbar.Signal.Zielsperrung
				fillConditional(
					instance,
					cols.getColumn(Signal_Zielsperrung),
					flaZwieSchutz,
					[
						currentFlaSchutz?.flaSchutzSignal !== null
					],
					[
						currentFlaSchutz.hasZielsperrung.translate
					]
				)

				// J: Sslw.Ersatzschutz_Weitergabe.Weiche_Kreuzung.Bezeichnung_W_Kr
				fillSwitch(
					instance,
					cols.getColumn(Weiche_Kreuzung_Bezeichnung_W_Kr),
					flaZwieSchutz,
					new Case<Fla_Zwieschutz>([
						currentFlaSchutz?.flaSchutzWeitergabe !== null &&
							currentDirectionWeitergabeId !== null &&
							currentDirectionWeitergabe?.
								anforderer instanceof W_Kr_Gsp_Element
					], [
						currentDirectionWeitergabe?.getAnfordererBezeichnung(
							currentDirectionWeitergabe?.
								anforderer as W_Kr_Gsp_Element)
					]),
					new Case<Fla_Zwieschutz>([
						currentFlaSchutz?.flaSchutzWeitergabe !== null &&
							currentDirectionWeitergabeId !== null &&
							currentOppositeDirectionWeitergabeId !== null
						currentOppositeDirectionWeitergabe?.
							anforderer instanceof W_Kr_Gsp_Element
					], [
						currentOppositeDirectionWeitergabe?.
							getAnfordererBezeichnung(
								currentOppositeDirectionWeitergabe?.
									anforderer as W_Kr_Gsp_Element)
					])
				)

				// K: Sslw.Ersatzschutz_Weitergabe.Weiche_Kreuzung.wie_Fahrt_ueber
				fillSwitch(
					instance,
					cols.getColumn(Weiche_Kreuzung_wie_Fahrt_ueber),
					flaZwieSchutz,
					new Case<Fla_Zwieschutz>([
						currentFlaSchutz?.flaSchutzWeitergabe !== null &&
							currentDirectionWeitergabeId !== null &&
							currentOppositeDirectionWeitergabeId !== null
					], [
						"L+R"
					]),
					new Case<Fla_Zwieschutz>([
						currentFlaSchutz?.flaSchutzWeitergabe !== null &&
							currentDirectionWeitergabeId !== null &&
							currentOppositeDirectionWeitergabeId === null
					], [
						currentDirectionString
					]),
					new Case<Fla_Zwieschutz>([
						currentFlaSchutz?.flaSchutzWeitergabe !== null &&
							currentDirectionWeitergabeId === null &&
							currentOppositeDirectionWeitergabeId !== null
					], [
						currentOppositeDirectionString
					])
				)

				// L: Sslw.Technischer_Verzicht
				fillConditional(
					instance,
					cols.getColumn(Technischer_Verzicht),
					flaZwieSchutz,
					[
						currentMassnahme === ENUM_MASSNAHME_VERZICHT
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
						(currentFlaSchutz?.freimeldeZuordnungen?.filter [
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
						(	currentFlaSchutz?.freimeldeZuordnungen?.filter [
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
					[flaZwieschutzElement.nachlaufverhinderung.wert.translate]
				)

				// P: Sslw.Bemerkung		
				fill(
					instance,
					cols.getColumn(Bemerkung),
					flaZwieSchutz,
					[footnoteTransformation.transform(it, instance)]
				)

				runLeft = false;
			}

		}

		return factory.table
	}

	def ENUMMassnahme getCurrentMassnahme(
		Fla_Zwieschutz zwieschutz,
		boolean isLeft
	) {
		if (isLeft) {
			return zwieschutz?.flaZwieschutzElement?.massnahmeL.wert
		}
		return zwieschutz?.flaZwieschutzElement?.massnahmeR.wert
	}

	/**
	 * @param isLeft direction (left or right)
	 * 
	 * @return the direction  (left or right) String
	 */
	private def String getCurrentDirectionString(
		boolean isLeft
	) {
		if (true === isLeft) {
			return "L"
		} else {
			return "R"
		}
	}

	/**
	 * @param isLeft direction (left or right)
	 * 
	 * @return the opposite direction  (left or right) String
	 */
	private def String getCurrentOppositeDirectionString(
		boolean isLeft
	) {
		if (true === isLeft) {
			return "R"
		} else {
			return "L"
		}
	}

	/**
	 * @param flaZwieSchutz current Zwieschutzelement
	 * @param isLeft direction (left or right)
	 * 
	 * @return the current (left or right ) Flankenschutzmaßnahme of the Zwieschutzelement or null 
	 */
	private def Fla_Schutz getCurrentFlaSchutz(
		Fla_Zwieschutz flaZwieSchutz,
		boolean isLeft
	) {
		if (flaZwieSchutz !== null) {
			if (true === isLeft) {
				return flaZwieSchutz?.flaSchutzL
			} else {
				return flaZwieSchutz?.flaSchutzR
			}
		} else {
			return null;
		}
	}

	/**
	 * @param flaSchutz current Flankenschutz
	 * @param isLeft direction (left or right)
	 * 
	 * @return the current (left or right )Weitergabe of Flankenschutzmaßnahme or null 
	 */
	private def Fla_Schutz getCurrentWeitergabe(
		Fla_Schutz flaSchutz,
		boolean isLeft
	) {
		if (flaSchutz !== null && flaSchutz?.flaSchutzWeitergabe !== null) {
			if (true === isLeft) {
				if (flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeL !== null) {
					return flaSchutz.weitergabeL;
				}
			} else {
				if (flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeR !== null) {
					return flaSchutz.weitergabeR;
				}
			}
		}

		return null;
	}

	/**
	 * @param flaSchutz current Flankenschutz
	 * @param isLeft direction (left or right)
	 * 
	 * @return the current opposite (left or right )Weitergabe of Flankenschutzmaßnahme or null 
	 */
	private def Fla_Schutz getCurrentOppositeWeitergabe(
		Fla_Schutz flaSchutz,
		boolean isLeft
	) {
		if (flaSchutz !== null && flaSchutz?.flaSchutzWeitergabe !== null) {
			if (true === isLeft) {
				if (flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeR !== null) {
					return flaSchutz.weitergabeR;
				}
			} else {
				if (flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeL !== null) {
					return flaSchutz.weitergabeL;
				}
			}
		}

		return null;
	}

	/**
	 * @param flaSchutz current Flankenschutz
	 * @param isLeft direction (left or right)
	 * 
	 * @return the current opposite (left or right )Weitergabe ID of Flankenschutzmaßnahme or null 
	 */
	private def String getCurrentOppositeWeitergabeId(
		Fla_Schutz flaSchutz,
		boolean isLeft
	) {
		if (flaSchutz !== null && flaSchutz?.flaSchutzWeitergabe !== null) {
			if (true === isLeft) {
				return flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeR?.
					identitaet?.wert;
			} else {
				return flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeL?.
					identitaet?.wert;
			}
		}

		return null;
	}

	/**
	 * @param flaSchutz current Flankenschutz
	 * @param isLeft direction (left or right)
	 * 
	 * @return the current (left or right )Weitergabe ID of Flankenschutzmaßnahme or null 
	 */
	private def String getCurrentWeitergabeId(
		Fla_Schutz flaSchutz,
		boolean isLeft
	) {
		if (flaSchutz !== null && flaSchutz?.flaSchutzWeitergabe !== null) {
			if (true === isLeft) {
				return flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeL?.
					identitaet?.wert;
			} else {
				return flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeR?.
					identitaet?.wert;
			}
		}

		return null;
	}
}
