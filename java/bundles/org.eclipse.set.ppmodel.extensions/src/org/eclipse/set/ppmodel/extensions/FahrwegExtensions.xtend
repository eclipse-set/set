/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.LinkedList
import java.util.List
import java.util.Set
import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.basis.graph.Digraphs
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.DirectedEdgePath
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Abhaengigkeit
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Umfahrpunkt
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Gleis.ENUMGleisart
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Kreuzung_AttributeGroup
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.ppmodel.extensions.utils.CrossingRoute
import org.eclipse.set.ppmodel.extensions.utils.GestellteWeiche
import org.eclipse.set.ppmodel.extensions.utils.GestellteWeiche.Lage
import org.eclipse.set.ppmodel.extensions.utils.TeilFahrweg
import org.eclipse.set.ppmodel.extensions.utils.TopRouting
import org.eclipse.set.ppmodel.extensions.utils.WeichenSchenkel
import org.eclipse.set.utils.ToolboxConfiguration
import org.locationtech.jts.algorithm.Angle
import org.locationtech.jts.geom.Coordinate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung.*
import static org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss.*

import static extension org.eclipse.set.basis.graph.DirectedEdgePathExtension.*
import static extension org.eclipse.set.ppmodel.extensions.BueAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ENUMWirkrichtungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MarkanterPunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CacheUtils.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*
import static extension org.eclipse.set.utils.graph.DirectedEdgeExtensions.*
import org.eclipse.set.basis.graph.TopPath
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.ppmodel.extensions.utils.TopGraph

/**
 * Extensions for {@link Fstr_Fahrweg}.
 */
class FahrwegExtensions extends BereichObjektExtensions {

	static val Logger logger = LoggerFactory.getLogger(
		typeof(FahrwegExtensions))

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @returns the Startsignal
	 */
	def static Signal getStart(Fstr_Fahrweg fahrweg) {
		return fahrweg.IDStart?.value
	}

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @returns the Zielobjekt
	 */
	def static Basis_Objekt getZielObjekt(Fstr_Fahrweg fahrweg) {
		return fahrweg.IDZiel?.value
	}

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @returns the Zielpunkt
	 */
	def static Markanter_Punkt getZielPunkt(Fstr_Fahrweg fahrweg) {
		return fahrweg.IDZiel.value as Markanter_Punkt
	}

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @returns the Ziel Punkt-Objekt
	 */
	def static Punkt_Objekt getZielPunktObjekt(Fstr_Fahrweg fahrweg) {
		return getZielPunktObjektDispatch(fahrweg.zielObjekt)
	}

	private def static dispatch Punkt_Objekt getZielPunktObjektDispatch(
		Basis_Objekt ziel) {
		return null
	}

	private def static dispatch Punkt_Objekt getZielPunktObjektDispatch(
		Signal ziel) {
		return ziel
	}

	private def static dispatch Punkt_Objekt getZielPunktObjektDispatch(
		Markanter_Punkt ziel) {
		return ziel.markanteStelle
	}

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @returns the Zielsignal
	 */
	def static Signal getZielSignal(Fstr_Fahrweg fahrweg) {
		val ziel = fahrweg?.IDZiel?.value
		if (ziel instanceof Signal) {
			return ziel
		}
		return null
	}

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @returns the list of Fstr_Abhaengigkeit
	 */
	def static List<Fstr_Abhaengigkeit> getAbhaengigkeiten(
		Fstr_Fahrweg fahrweg) {
		val result = new LinkedList<Fstr_Abhaengigkeit>
		for (abhaengigkeit : fahrweg.container.fstrAbhaengigkeit) {
			if (abhaengigkeit.IDFstrFahrweg?.wert == fahrweg.identitaet.wert) {
				result.add(abhaengigkeit)
			}
		}
		return result
	}

	/**
	 * @param fahrweg this Fahrweg
	 * @param start start of the segment
	 * @param end end of the segment
	 * 
	 * @returns the segment
	 */
	def static TeilFahrweg getTeilFahrweg(
		Fstr_Fahrweg fahrweg,
		Punkt_Objekt start,
		Punkt_Objekt end
	) {
		return new TeilFahrweg(fahrweg, start, end)
	}

	/**
	 * @param fahrweg this Fahrweg
	 * @param komponente Weichenkomponente on this Fahrweg
	 * 
	 * @return the gestellte Weiche on this Fahrweg, or <code>null</code> if
	 * the Befahrung of the element is not Spitz
	 */
	def static GestellteWeiche getGestellteWeiche(Fstr_Fahrweg fahrweg,
		W_Kr_Gsp_Komponente komponente) {
		val path = fahrweg.path
		val weichenKnoten = komponente.topKnoten
		val schenkel = path.getEdgeForTail(weichenKnoten).element
		val anschluss = schenkel.getTOPAnschluss(weichenKnoten)

		if (anschluss == ENUMTOP_ANSCHLUSS_LINKS) {
			return new GestellteWeiche(komponente.WKrGspElement, Lage.L)
		}

		if (anschluss == ENUMTOP_ANSCHLUSS_RECHTS) {
			return new GestellteWeiche(komponente.WKrGspElement, Lage.R)
		}

		return null
	}

	/**
	 * @param fahrweg this Fahrweg
	 * @param komponente Weichenkomponente on this Fahrweg
	 * 
	 * @return the Weichenschenkel on this Fahrweg
	 */
	def static WeichenSchenkel getWeichenSchenkel(Fstr_Fahrweg fahrweg,
		W_Kr_Gsp_Komponente komponente) {
		val path = fahrweg.path
		val weichenKnoten = komponente.topKnoten
		val schenkels = path.getEdges(weichenKnoten).filter [
			element.getTOPAnschluss(weichenKnoten) != ENUMTOP_ANSCHLUSS_SPITZE
		]
		Assert.isTrue(schenkels.size == 1)
		val schenkel = schenkels.get(0)
		val anschluss = schenkel.element.getTOPAnschluss(weichenKnoten)
		if (anschluss === ENUMTOP_ANSCHLUSS_LINKS) {
			return new WeichenSchenkel(schenkel, WeichenSchenkel.Lage.L)
		}
		if (anschluss === ENUMTOP_ANSCHLUSS_RECHTS) {
			return new WeichenSchenkel(schenkel, WeichenSchenkel.Lage.R)
		}
		throw new IllegalArgumentException(komponente.identitaet.wert)
	}

	/**
	 * @param fahrweg this Fahrweg
	 * @param notUsable not usable Gleisart
	 * @param komponente Weichenkomponente on this Fahrweg
	 * 
	 * @return the Entscheidungsweiche on this Fahrweg, or <code>null</code> if
	 * the Komponente belongs to no Entscheidungsweiche (i.e. the Befahrung of the
	 * element is not Spitz).
	 */
	def static GestellteWeiche getEntscheidungsweiche(Fstr_Fahrweg fahrweg,
		W_Kr_Gsp_Komponente komponente, List<ENUMGleisart> notUsable) {
		if (logger.debugEnabled) {
			logger.
				debug('''fahrweg=«fahrweg.debugName» komponente=«komponente.debugName»''')
		}
		val cache = fahrweg.getCache(ToolboxConstants.CacheId.FAHRWEG_TO_ROUTES)
		val routes = cache.get(
			fahrweg.getCacheKey(notUsable),
			[fahrweg.routes(notUsable)]
		)

		if (routes.size < 2) {
			// with at most one route this Weiche won't become an
			// Entscheidungsweiche any more
			return null
		}

		val routesGraph = Digraphs.toDigraph(routes);
		val weichenKnoten = komponente.topKnoten
		val nodesAfterWeiche = routesGraph.getDirectSuccessors(weichenKnoten)
		val edgesAfterWeiche = nodesAfterWeiche.map [
			routesGraph.getEdges(weichenKnoten, it)
		].flatten.map['''«it.element.identitaet.wert»,«it.forwards»'''].toSet
		val numberOfEdgesAfterWeiche = edgesAfterWeiche.size

		if (logger.debugEnabled) {
			logger.
				debug('''numberOfEdgesAfterWeiche=«numberOfEdgesAfterWeiche»''')
			logger.
				debug('''edgesAfterWeiche=«FOR e : edgesAfterWeiche SEPARATOR ", "»«e»«ENDFOR»''')
			logger.
				debug('''routes=«FOR r : routes SEPARATOR ", "»«r.debugName»«ENDFOR»''')
		}

		if (numberOfEdgesAfterWeiche == 0) {
			// no valid route over Weichenkomponente
			return null
		}

		if (numberOfEdgesAfterWeiche == 1) {
			// the Weiche has the same Lage for all alternative routes
			return null
		}

		val fahrwegPath = fahrweg.path
		val fahrwegEdgeAfterWeiche = fahrwegPath.getEdgeForTail(weichenKnoten)
		Assert.isNotNull(fahrwegEdgeAfterWeiche)

		val anschluss = fahrwegEdgeAfterWeiche.element.
			getTOPAnschluss(weichenKnoten)

		var GestellteWeiche entscheidungsweiche = null

		if (anschluss == ENUMTOP_ANSCHLUSS_LINKS) {
			entscheidungsweiche = new GestellteWeiche(komponente.WKrGspElement,
				Lage.L)
		}

		if (anschluss == ENUMTOP_ANSCHLUSS_RECHTS) {
			entscheidungsweiche = new GestellteWeiche(komponente.WKrGspElement,
				Lage.R)
		}

		if (logger.debugEnabled) {
			logger.
				debug('''Entscheidungsweiche=«entscheidungsweiche?.bezeichnung»''')
		}

		// wrong orientation for Entscheidungsweiche
		return entscheidungsweiche
	}

	private def static List<DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> routes(
		Fstr_Fahrweg fahrweg,
		List<ENUMGleisart> notUsable
	) {
		val fahrwegPath = fahrweg.path
		val startEdge = fahrwegPath.edgeList.get(0)
		val notUsableGleisArt = fahrweg.container.gleisArt.filter [
			notUsable.contains(gleisart.wert)
		]

		val routes = startEdge.getPaths(new TopRouting, fahrwegPath.start,
			fahrwegPath.end).filter [ r |
			notUsableGleisArt.forall[!areaIntersects(r)]
		].toList

		return routes
	}

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @return the path of this Fahrweg
	 */
	def static DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> getPath(
		Fstr_Fahrweg fahrweg) {
		return fahrweg.getPath(fahrweg.start, fahrweg.zielPunktObjekt)
	}
	
		/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @return the path of this Fahrweg
	 */
	def static TopPath getPath(
		Fstr_Fahrweg fahrweg, TopologicalGraphService topGraphService) {
		return fahrweg.getPath(fahrweg.start, fahrweg.zielPunktObjekt, topGraphService)
	}
	

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @return the Gleisabschnitte intersecting with this Fahrweg
	 */
	def static Set<Gleis_Abschnitt> getGleisabschnitte(Fstr_Fahrweg fahrweg) {
		return fahrweg.container.gleisAbschnitt.filter[intersects(fahrweg)].
			toSet
	}

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @return the Signalbegriffe within the Fahrweg (and not at the Startsignal)
	 */
	def static Set<Signal_Signalbegriff> getSignalbegriffeImFahrweg(
		Fstr_Fahrweg fahrweg) {
		val startSignal = fahrweg.start
		val signale = fahrweg.filterContained(fahrweg.container.signal).filter [
			identitaet.wert != startSignal.identitaet.wert
		].filter [
			fahrweg.containsInWirkrichtung(it)
		]
		if (logger.traceEnabled) {
			logger.trace('''fahrweg=«fahrweg.path.debugString»''')
			logger.trace('''signale=«signale.toList.debugString»''')
		}
		return signale.map[signalbegriffe].flatten.toSet
	}

	/**
	 * @param fahrweg this Fahrweg
	 * @param punktObjekt the Punkt Objekt
	 * 
	 * @return whether this Fahrweg contains the given Punkt Objekt in Wirkrichtung
	 */
	def static boolean containsInWirkrichtung(Fstr_Fahrweg fahrweg,
		Punkt_Objekt punktObjekt) {
		val wirkrichtung = fahrweg.getWirkrichtung(punktObjekt)
		if (wirkrichtung === null) {
			return false
		}
		return wirkrichtung != ENUM_WIRKRICHTUNG_GEGEN
	}

	/**
	 * @param fahrweg this Fahrweg
	 * @param punktObjekt the Punkt Objekt
	 * 
	 * @return the Wirkrichtung of this Punkt Objekt on this Fahrweg
	 */
	def static ENUMWirkrichtung getWirkrichtung(Fstr_Fahrweg fahrweg,
		Punkt_Objekt punktObjekt) {
		val p = fahrweg.intersection(punktObjekt).unique
		val directions = fahrweg.path.edgeList.filter[contains(p)].map [
			getWirkrichtung(p)
		].toList
		if (directions.size == 1) {
			return directions.get(0)
		}
		throw new IllegalArgumentException(punktObjekt.identitaet.wert)
	}

	/**
	 * @param fahrweg this Fahrweg
	 * 
	 * @return BUes within this Fahrweg
	 */
	def static Set<BUE_Anlage> getBUes(Fstr_Fahrweg fahrweg) {
		return fahrweg.container.BUEAnlage.filter [
			gleisbezogeneGefahrraeume.exists[intersects(fahrweg)]
		].toSet
	}

	def static List<Fstr_Umfahrpunkt> getUmfahrpunkte(Fstr_Fahrweg fahrweg) {
		return fahrweg.container.fstrUmfahrpunkt.filter [
			IDFstrFahrweg?.value?.identitaet?.wert == fahrweg.identitaet.wert
		].toList
	}

	/**
	 * @param fahrweg this Fahrweg
	 * @param kreuzung the Kreuzung
	 * 
	 * @return how the Fahrweg crosses the Kreuzung
	 */
	def static CrossingRoute getCrossingRoute(
		Fstr_Fahrweg fahrweg,
		Kreuzung_AttributeGroup kreuzung
	) {
		val Punkt_Objekt center = kreuzung.eContainer as Punkt_Objekt
		try {
			// find the crossing center
			val centerCoordinate = center.coordinate

			// find the tangents
			val topKanten = center.topKanten
			Assert.isTrue(topKanten.size == 2)
			val fwEdge = fahrweg.path.edgeList.findFirst [ edge |
				center.singlePoints.exists[edge.contains(it)]
			]
			if (fwEdge === null) {
				return CrossingRoute.NO_CROSSING
			}
			Assert.isTrue(topKanten.contains(fwEdge.element))
			val topKanteCr = topKanten.filter[it !== fwEdge.element].get(0)
			val tangentFw = fwEdge.getGeoKanten.map [
				getSegmentWith(centerCoordinate,
					ToolboxConfiguration.pathFindingTolerance)
			].filterNull.toList.unique
			val tangentCr = topKanteCr.geoKanten.map [
				getSegmentWith(centerCoordinate,
					ToolboxConfiguration.pathFindingTolerance)
			].filterNull.toList.unique

			// find the vectors
			val tipFw = tangentFw.p1
			val tipCr0 = tangentCr.p0
			val tipCr1 = tangentCr.p1

			// find the acute angle
			var Coordinate tipCr
			if (Angle.isAcute(tipFw, centerCoordinate, tipCr0)) {
				tipCr = tipCr0
			} else if (Angle.isAcute(tipFw, centerCoordinate, tipCr1)) {
				tipCr = tipCr1
			} else {
				throw new RuntimeException('''Orthogonal crossings are not supported.''')
			}

			// test the orientation of the angle
			if (Angle.angleBetweenOriented(
				tipFw,
				centerCoordinate,
				tipCr
			) < 0) {
				// the fw vector is rotated clockwise, therefore the fw takes the left Gleis
				return CrossingRoute.LEFT
			}
			return CrossingRoute.RIGHT
		} catch (GeometryException e) {
			throw new RuntimeException(e)
		}
	}

	private def static ENUMWirkrichtung getWirkrichtung(
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		var wirkrichtung = singlePoint?.wirkrichtung?.wert
		if (wirkrichtung === null) {
			wirkrichtung = ENUM_WIRKRICHTUNG_BEIDE
		}
		val topKante = edge.element
		if (edge.tail.identitaet.wert == topKante.TOPKnotenA.identitaet.wert) {
			return wirkrichtung
		}
		return wirkrichtung.inverted
	}
}
