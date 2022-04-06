/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskp;

import com.google.common.collect.Lists
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.eclipse.set.toolboxmodel.Ortung.FMA_Komponente
import org.eclipse.set.toolboxmodel.PZB.PZB_Element
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import java.util.List
import org.eclipse.set.basis.Wrapper
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PZBElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PZBElementZuordnungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*
import static extension org.eclipse.set.utils.graph.DigraphExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*
import static extension org.eclipse.set.utils.math.DoubleExtensions.*
import org.eclipse.set.utils.table.TMFactory

/**
 * Table transformation for a Sskp.
 * 
 * @author Rumpf
 */
class SskpTransformator extends AbstractPlanPro2TableModelTransformator {

	static final Logger logger = LoggerFactory.getLogger(
		typeof(SskpTransformator));

	SskpColumns cols;

	TMFactory factory

	new(SskpColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.cols = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		container.PZBElement.sortBy [
			PZBArt?.wert?.translate ?: ""
		].forEach [ it |
			if (Thread.currentThread.interrupted) {
				return
			}
			it.transform
		]
		return
	}

	private def TableRow create factory.newTableRow(pzb) transform(
		PZB_Element pzb) {
		val row = it

		fill(cols.signal_weiche, pzb, [
			pzb.PZBElementZuordnung?.PZBElementBezugspunktBezeichnung?.
				bezeichnungTabelle?.wert
		])
		fill(cols.pzb_schutzpunkt, pzb, [toBeSpecified])

		fill(
			cols.pzb_schutzstrecke_soll,
			pzb,
			[
				(PZBElementZuordnung?.PZBElementBezugspunkt as Signal)?.
					PZBSchutzstreckeSoll?.wert?.toTableDecimal ?: ""
			]
		)

		fill(cols.pzb_schutzstrecke_ist, pzb, [toDo])
		fill(cols.wirkfrequenz, pzb, [pzb?.PZBArt?.wert?.translate])

		val topGraph = new TopGraph(pzb.container.TOPKante)
		val bezugspunkt = pzb.bezugspunkt
		val List<? extends Punkt_Objekt> bezugspunktObjekte = switch (bezugspunkt) {
			case bezugspunkt === null:
				Lists.newArrayList
			case bezugspunkt instanceof Punkt_Objekt:
				Lists.newArrayList(bezugspunkt as Punkt_Objekt)
			case bezugspunkt instanceof W_Kr_Gsp_Element: {
				val bezugsweicheElement = (bezugspunkt as W_Kr_Gsp_Element)
				bezugsweicheElement.WKrGspKomponenten
			}
		}
		fill(
			cols.abstand_zu,
			pzb,
			[
				if (bezugspunkt === null) {
					throw new IllegalArgumentException('''No Bezugspunkt for «pzb.debugString»''')
				}
				val paths = topGraph.getPaths(bezugspunktObjekte.map [
					singlePoints
				].flatten.toList, pzb.singlePoints)
				if (paths.empty) {
					throw new IllegalArgumentException('''No path from «bezugspunkt.debugString» to «pzb.debugString»''')
				}
				return paths.fold(
					Double.MAX_VALUE, [d, p|Math.min(d, p.length)]).
					toTableDecimal
			]
		)

		fill(cols.wirksamkeit, pzb, [
			pzb?.PZBElementZuordnung?.wirksamkeit?.wert?.translate
		])
		fill(cols.pruefgeschwindigkeit, pzb, [
			pzb?.PZBElementGUE?.pruefgeschwindigkeit?.wert?.toString
		])
		fill(cols.pruefzeit, pzb, [futureVersion])
		fill(cols.messstrecke, pzb, [
			pzb?.PZBElementGUE?.GUEMessstrecke?.wert?.toString
		])
		fill(cols.messfehler, pzb, [futureVersion])
		fill(cols.anordnung, pzb, [
			pzb?.PZBElementGUE?.GUEAnordnung?.wert?.translate
		])
		fill(cols.abweich_Abstand, pzb, [
			pzb?.PZBElementGUE?.GUEAbstandAbweichend?.wert?.toTableDecimal
		])
		fill(cols.bauart, pzb, [futureVersion])
		fill(cols.energieversorgung, pzb, [
			pzb?.PZBElementGUE?.GUEEnergieversorgung?.wert?.translate
		])
		fill(cols.bef_GPE, pzb, [
			pzb?.unterbringung?.unterbringungAllg?.unterbringungBefestigung?.
				wert?.translate
		])

		fillConditional(
			cols.weichenlage,
			pzb,
			[bezugspunkt instanceof W_Kr_Gsp_Element],
			[
				(bezugspunkt as W_Kr_Gsp_Element)?.bezeichnung?.
					bezeichnungTabelle?.wert ?: ""
			]
		)

		fill(cols.fstr, pzb, [
			pzb?.PZBElementZuordnung?.fstrZugRangier?.fstrZugRangierAllg?.
				fstrBedienstring?.wert
		])

		val Wrapper<Basis_Objekt> inaGefStelle = new Wrapper
		inaGefStelle.value = null
		fillConditional(
			cols.gef_Stelle,
			pzb,
			[PZBElementGM?.PZBINA?.wert && bezugspunkt instanceof Signal],
			[
				inaGefStelle.value = it.inaGefStelle
				inaGefStelle.value.toInaGefStelleTableString
			]
		)
		if (logger.debugEnabled) {
			logger.debug('''«pzb.PZBElementZuordnung?.PZBElementBezugspunktBezeichnung?.
				bezeichnungTabelle?.wert»: «inaGefStelle.value.debugString»''')
		}

		fillIterable(
			cols.gef_Stelle_Abstand,
			pzb,
			[
				#{bezugspunkt}.filter(Signal).filter [
					inaGefStelle.value !== null
				].map[topKanten].flatten.map [
					getAbstandDispatch(bezugspunkt, inaGefStelle.value)
				]
			],
			null,
			[toTableDecimal]
		)

// "1.9 update" INA Signal was replaced by INA Markanter Punkt. How do we test the Signalbegriff for a Markanter Punkt?
//		fillIterable(
//			cols.abstand_H_Tafel,
//			pzb,
//			[
//				#{PZBElementZuordnung?.INASignal}.filterNull.filter [
//					hasSignalbegriffID(Ne5)
//				].map [
//					val signal = it
//					if (bezugspunkt === null) {
//						throw new IllegalArgumentException('''No Bezugspunkt for «pzb.debugString»''')
//					}
//					if (bezugspunkt instanceof Signal) {
//						val paths = topGraph.getPaths(bezugspunktObjekte.map [
//							singlePoints
//						].flatten.toList, signal.singlePoints)
//						if (paths.empty) {
//							throw new IllegalArgumentException('''No path from «bezugspunkt.debugString» to «signal.debugString»''')
//						}
//						return paths.fold(Double.MAX_VALUE, [ d, p |
//							Math.min(d, p.length)
//						]).toTableDecimal
//					} else {
//						return ""
//					}
//				]
//			],
//			null,
//			[it]
//		)
		fill(
			cols.abstand_H_Tafel,
			pzb,
			[toBeSpecified]
		)

		fill(
			cols.basis_bemerkung,
			pzb,
			[footnoteTransformation.transform(it, row)]
		)
		return
	}

	private static dispatch def toInaGefStelleTableString(Void object) {
		throw new IllegalArgumentException('''Missing INA Gef Stelle.''')
	}

	private static dispatch def toInaGefStelleTableString(Basis_Objekt object) {
		throw new IllegalArgumentException('''Unexpected INA Gef Stelle Type «object.class.simpleName».''')
	}

	private static dispatch def toInaGefStelleTableString(TOP_Knoten knoten) {
		return '''WA «knoten.WKrGspElement?.bezeichnung?.bezeichnungTabelle?.wert»'''
	}

	private static dispatch def toInaGefStelleTableString(Signal signal) {
		if (signal.grenzzeichen) {
			return '''Gz «signal.WKrGspElement?.bezeichnung?.bezeichnungTabelle?.wert»'''
		}
		return '''«signal.bezeichnung?.bezeichnungTabelle?.wert ?: ""»'''
	}

	private static dispatch def toInaGefStelleTableString(
		FMA_Komponente komponente) {
		return '''«komponente.bezeichnung?.bezeichnungTabelle?.wert ?: ""»'''
	}
}
