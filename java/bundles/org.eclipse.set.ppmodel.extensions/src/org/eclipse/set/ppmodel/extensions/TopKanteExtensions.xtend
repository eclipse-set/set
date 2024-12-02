/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import com.google.common.collect.Lists
import java.math.BigDecimal
import java.util.Collection
import java.util.Collections
import java.util.LinkedList
import java.util.List
import java.util.Set
import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.MissingSupplier
import org.eclipse.set.basis.cache.Cache
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.basis.geometry.GeoPosition
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.DirectedElement
import org.eclipse.set.basis.graph.DirectedElementImpl
import org.eclipse.set.core.services.Services
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Gleis.Gleis_Lichtraum
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.ppmodel.extensions.utils.Distance
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss.*

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ContainerExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.SetExtensions.*

/**
 * Diese Klasse erweitert {@link TOP_Kante}.
 * 
 * @author Schaefer
 */
class TopKanteExtensions extends BasisObjektExtensions {

	private static class PunktObjektTopKanteLoader implements MissingSupplier<Object> {

		val TOP_Kante topKante

		new(TOP_Kante topKante) {
			this.topKante = topKante
		}

		override get() {
			return topKante.findPunktObjektTopKanten
		}
	}

	static final BigDecimal TOLERANCE = BigDecimal.valueOf(0.002);

	static final Logger logger = LoggerFactory.getLogger(
		typeof(TopKanteExtensions));

	static Cache cache

	/**
	 * @param topKante this TOP Kante
	 * 
	 * @return TOP Knoten A of the TOP Kante
	 */
	def static TOP_Knoten getTOPKnotenA(
		TOP_Kante topKante
	) {
		return topKante.IDTOPKnotenA?.value
	}

	/**
	 * @param topKante this TOP Kante
	 * 
	 * @return TOP Knoten A of the TOP Kante
	 */
	def static TOP_Knoten getTOPKnotenB(
		TOP_Kante topKante
	) {
		return topKante.IDTOPKnotenB?.value
	}

	def static List<GEO_Kante> getGeoKanten(
		TOP_Kante topKante
	) {
		return topKante.container.GEOKante.filter[k|k.topKante == topKante].
			toList
	}

	/**
	 * Get GEOKante at TOPKnoten
	 * @param topKante this TOPKante
	 * @parem topKnoten the TOP Knoten of this TOP Kante
	 * @return the GEO Kante at the TOP Knoten on this TOP Kante
	 */
	def static GEO_Kante getGeoKanteAtKnoten(TOP_Kante topKante,
		TOP_Knoten topKnoten) {
		val geoKnoten = topKnoten.GEOKnoten
		val geoKanten = geoKnoten.getGeoKantenOnParentKante(topKante)
		// The GEOKnoten reference to this TOP Knoten should have only one GEOKante on this TOPKante
		if (geoKanten.size > 1) {
			throw new IllegalArgumentException('''The TOP_Knoten: «topKnoten.identitaet.wert» on TOP_Kante: «topKante.identitaet.wert» reference to more than one GEO_Kante''')
		}
		return geoKanten.get(0)
	}

	/**
	 * This method returns a list of directed GEO edges (a "GEO path") in the
	 * direction, implied by the directed TOP edge. If there is no complete
	 * coverage of the given TOP edge by GEO edges, the method will return the
	 * starting sequence, beginning at the GEO node which is mapped by the
	 * start TOP node of the TOP edge.
	 * 
	 * @param directedTopEdge the directed TOP edge
	 * 
	 * @return list of directed GEO edges
	 */
	def static List<DirectedElement<GEO_Kante>> getGeoKanten(
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> directedTopEdge
	) {
		val start = directedTopEdge?.tail?.GEOKnoten
		if (start === null) {
			return Collections.emptyList
		}
		val result = Lists.newArrayList
		return directedTopEdge.getGeoKanten(start, result)
	}

	/**
	 * @param topKante the TOP_Kante to find the point on
	 * @param abstand the distance from the start of the TOP_Kante
	 * @param seitlicherAbstand the lateral position at the distance from the TOP_Kante
	 * @param wirkrichtung the direction to use for detemining the side of the lateral position
	 * @return a GeoPosition for the requested coordinate on the TOP_Kante
	 */
	def static GeoPosition getCoordinate(
		TOP_Kante topKante,
		BigDecimal abstand,
		BigDecimal seitlicherAbstand,
		ENUMWirkrichtung wirkrichtung
	) {
		val topKnotenA = topKante.TOPKnotenA
		return topKante.getCoordinate(topKnotenA, abstand, seitlicherAbstand,
			wirkrichtung)
	}

	/**
	 * @param topKante the TOP_Kante to find the point on
	 * @param topStart the TOP_Knoten from which to start
	 * @param abstand the distance from the start of the TOP_Kante
	 * @param seitlicherAbstand the lateral position at the distance from the TOP_Kante
	 * @param wirkrichtung the direction to use for detemining the side of the lateral position
	 * @return a GeoPosition for the requested coordinate on the TOP_Kante
	 */
	def static GeoPosition getCoordinate(
		TOP_Kante topKante,
		TOP_Knoten topStart,
		BigDecimal abstand,
		BigDecimal seitlicherAbstand,
		ENUMWirkrichtung wirkrichtung
	) {
		val start = topStart.GEOKnoten

		// Betrachtung relativer Abstand
		val geoKantenOnTopKante = topKante.geoKanten
		val lengthGeoKanten = geoKantenOnTopKante.fold(BigDecimal.ZERO, [ l, k |
			l + k.GEOKanteAllg.GEOLaenge.wert
		])
		val lengthTopKante = topKante.TOPKanteAllg.TOPLaenge.wert
		val difference = (lengthGeoKanten - lengthTopKante).abs

		if (difference > TOLERANCE) {
			logger.debug("lengthTopKante={}", lengthTopKante)
			logger.debug("lengthGeoKanten={}", lengthGeoKanten)
			logger.debug(
				"geoKantenOnTopKante={}",
				Integer.valueOf(geoKantenOnTopKante.size())
			)
			logger.warn(
				"Difference of GEO Kanten length and TOP Kante length for TOP Kante {} greater than tolerance {} ({}).",
				topKante.getIdentitaet().getWert(),
				TOLERANCE,
				difference
			);
		}
		val relativeAbstand = abstand * lengthGeoKanten / lengthTopKante

		return getCoordinate(start, null, topKante, relativeAbstand,
			seitlicherAbstand, wirkrichtung);
	}

	/**
	 * @param topKante this TOP Kante
	 * @param otherTopKante the other TOP Kante
	 * 
	 * @return whether this TOP Kante has a distinct connecting TOP Knoten with
	 * the other TOP Kante
	 */
	def static boolean isConnectedTo(TOP_Kante topKante,
		TOP_Kante otherTopKante) {
		try {
			return topKante.connectionTo(otherTopKante) !== null
		} catch (IllegalArgumentException e) {
			return false
		}
	}

	/**
	 * @param topKante this TOP Kante
	 * @param singlePoint a single point
	 * 
	 * @return whether the given single point touches this TOP Kante
	 */
	def static boolean isConnectedTo(
		TOP_Kante topKante,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		if (singlePoint.topKante == topKante) {
			// in this case the given TOP-Kante is the TOP-Kante of the given single point
			return true
		}

		val adjacentTopKanten = topKante.adjacentTopKanten

		val qualifiedAdjacentTopKanten = adjacentTopKanten.filter [ t |
			singlePoint.isOnProperEndOf(t, topKante)
		].toList

		if (!qualifiedAdjacentTopKanten.empty) {
			// in this case the single point is located at the end of an adjacent Top-Kante
			return true
		}

		return false
	}

	/**
	 * Returns the connecting TOP Knoten of this TOP Kante and the other TOP Kante
	 * or null, if there is no connecting TOP Knoten. If there is more than one
	 * connecting TOP Knoten (for example if this TOP Kante is equal to the other TOP
	 * Kante) an IllegalArgumentException is thrown.
	 * 
	 * @param topKante this TOP Kante
	 * @param otherTopKante the other TOP Kante
	 * 
	 * @return the connecting TOP Knoten of this TOP Kante and the other TOP Kante
	 * 
	 * @throws IllegalArgumentException if the connection is ambiguous
	 */
	def static TOP_Knoten connectionTo(TOP_Kante topKante,
		TOP_Kante otherTopKante) {
		val thisKnoten = #[topKante.TOPKnotenA, topKante.TOPKnotenB]
		val otherKnoten = #[otherTopKante.TOPKnotenA, otherTopKante.TOPKnotenB]
		val commonKnoten = otherKnoten.filter[k|thisKnoten.contains(k)]

		val size = commonKnoten.size

		if (size == 0) {
			return null
		}

		if (size == 1) {
			return commonKnoten.get(0)
		}

		if (size > 1) {
			throw new IllegalArgumentException(
				'''Ambiguous connection for «topKante.identitaet.wert» and «otherTopKante.identitaet.wert»'''
			)
		}
	}

	/**
	 * @param topKante this TOP Kante
	 * 
	 * @return the Gleis Lichtraum of this TOP Kante (or <code>null</code> if
	 * no Gleis Lichtraum is available for this TOP Kante)
	 */
	def static Gleis_Lichtraum getGleisLichtraum(TOP_Kante topKante) {
		val lichtraeume = topKante.container.gleisLichtraum.filter [ l |
			l.bereichObjektTeilbereich.map[it.topKante].contains(topKante)
		]
		if (lichtraeume.empty) {
			return null
		}
		if (lichtraeume.size !== 1) {
			throw new IllegalArgumentException(
				'''Unexpected number of Gleis Lichträume for Top-Kante: topKante=«topKante.identitaet.wert» size=«lichtraeume.size»'''
			)
		}
		return lichtraeume.get(0)
	}

	/**
	 * @param topKante this TOP Kante
	 * @param topKanten list of TOP Kanten
	 * 
	 * @return list of TOP Kanten in <b>topKanten</b> connected to this TOP-Kante
	 */
	def static List<TOP_Kante> getAdjacentTopKanten(TOP_Kante topKante,
		Iterable<TOP_Kante> topKanten) {
		createCache
		return cache.get(topKante.cacheKey, [
			calcAdjacentTopKanten(topKante, topKanten)
		])
	}

	def static void createCache() {
		if (cache === null) {
			cache = Services.cacheService.getCache(
				ToolboxConstants.CacheId.TOPKANTE_TO_ADJACENT_TOPKANTEN
			)
		}
	}

	/**
	 * @param edge this TOP Kante
	 * 
	 * @return cache key for this TOP Kante
	 */
	def static String getCacheKey(TOP_Kante edge) {
		return '''«edge.container.cacheString»/«edge.identitaet.wert»'''
	}

	private def static List<TOP_Kante> calcAdjacentTopKanten(TOP_Kante topKante,
		Iterable<TOP_Kante> topKanten) {
		return topKanten.filter[t|t != topKante && t.isConnectedTo(topKante)].
			toList
	}

	/**
	 * @param topKante this TOP Kante
	 * 
	 * @return list of TOP Kanten connected to this TOP-Kante
	 */
	def static List<TOP_Kante> getAdjacentTopKanten(TOP_Kante topKante) {
		return topKante.getAdjacentTopKanten(topKante.container.TOPKante)
	}

	/**
	 * @param topKante this TOP Kante
	 * 
	 * @return list of all single points connected to this TOP-Kante
	 */
	def static List<Punkt_Objekt_TOP_Kante_AttributeGroup> getConnected(
		TOP_Kante topKante) {
		val cacheService = Services.getCacheService();
		val cache = cacheService.getCache(
			ToolboxConstants.CacheId.TOPKANTE_TO_SINGLEPOINTS,
			topKante.container.cacheString);
		return cache.get(
			topKante.identitaet.wert,
			new PunktObjektTopKanteLoader(topKante)
		) as List<Punkt_Objekt_TOP_Kante_AttributeGroup>
	}

	private def static List<Punkt_Objekt_TOP_Kante_AttributeGroup> findPunktObjektTopKanten(
		TOP_Kante topKante) {
		return topKante.container.punktObjekte.map[singlePoints].flatten.filter [
			topKante.isConnectedTo(it)
		].toList
	}

	/**
	 * @param topKante this TOP Kante
	 * @param singlePoint the single point
	 * 
	 * @return the Abstand of the given single point from the start of this TOP-Kante
	 * 
	 * @throws IllegalArgumentException if the single point is not connected to this TOP-Kante
	 */
	def static BigDecimal getAbstand(TOP_Kante topKante,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		if (singlePoint.topKante == topKante) {
			return singlePoint.abstand.wert
		}

		// the Punktobjekt may be on adjacent TOP Kanten
		val topKnotenSet = singlePoint.topKnoten.intersection(
			topKante.TOPKnoten)
		if (!topKnotenSet.empty) {
			Assert.isTrue(topKnotenSet.size == 1)
			val topKnoten = topKnotenSet.head
			return topKante.getAbstand(topKnoten)
		}

		throw new IllegalArgumentException(singlePoint.identitaet)
	}

	/**
	 * @param topKante this TOP Kante
	 * @param singlePoint the single point
	 * 
	 * @return the Abstand of the given single point from the start of this TOP-Kante
	 * 
	 * @throws IllegalArgumentException if the single point is not connected to this TOP-Kante
	 */
	def static Pair<BigDecimal, BigDecimal> getAbstand(TOP_Kante topKante,
		Bereich_Objekt_Teilbereich_AttributeGroup tb) {
		if (tb.IDTOPKante == topKante) {
			return tb?.begrenzungA?.wert -> tb?.begrenzungB?.wert
		}

		throw new IllegalArgumentException()
	}

	/**
	 * @param topKante this TOP Kante
	 * @param topKnoten a TOP Knoten
	 * 
	 * @return the Abstand of the given TOP Knoten from the start of this TOP-Kante
	 * 
	 * @throws IllegalArgumentException if the TOP Knoten is no node of this TOP-Kante
	 */
	def static BigDecimal getAbstand(TOP_Kante topKante, TOP_Knoten topKnoten) {
		if (topKante.TOPKnotenA == topKnoten) {
			return BigDecimal.ZERO
		}

		if (topKante.TOPKnotenB == topKnoten) {
			return topKante.TOPKanteAllg.TOPLaenge.wert
		}

		throw new IllegalArgumentException(topKnoten.identitaet.wert)
	}

	/**
	 * @param topKante this TOP Kante
	 * @param singlePoint1 a single point
	 * @param singlePoint2 a single point
	 * 
	 * @return the Abstand of the given single points from each other on this TOP-Kante
	 * 
	 * @throws IllegalArgumentException if the single points are not connected to this TOP-Kante
	 */
	def static BigDecimal getAbstand(TOP_Kante topKante,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint1,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint2) {
		val d1 = topKante.getAbstand(singlePoint1)
		val d2 = topKante.getAbstand(singlePoint2)

		return d1.max(d2) - d1.min(d2)
	}

	/**
	 * @param topKante this TOP Kante
	 * @param singlePoint1 a single point
	 * @param point a single point
	 * 
	 * @return the Abstand of the given single points from each other on this TOP-Kante
	 * 
	 * @throws IllegalArgumentException if the single points are not connected to this TOP-Kante
	 */
	def static Pair<BigDecimal, BigDecimal> getAbstand(TOP_Kante topKante,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint1,
		Bereich_Objekt_Teilbereich_AttributeGroup tb) {
		val d1 = topKante.getAbstand(singlePoint1)
		val d2 = topKante.getAbstand(tb)

		val distanceA = d2.key.max(d1) -
			d2.key.min(d1)
		val distanceB = d2.value.max(d1) -
			d2.value.min(d1)

		return distanceA -> distanceB
	}

	/**
	 * @param topKante this TOP Kante
	 * @param punktObjekt1 a Punkt Objekt
	 * @param punktObjekt2 a Punkt Objekt
	 * 
	 * @return the Abstand of the given Punkt Objekte from each other on this TOP-Kante
	 * 
	 * @throws IllegalArgumentException if the Punkt Objekte are not
	 * unambiguously connected to this TOP-Kante
	 */
	def static BigDecimal getAbstand(TOP_Kante topKante, Punkt_Objekt punktObjekt1,
		Punkt_Objekt punktObjekt2) {
		return topKante.getAbstand(
			punktObjekt1.singlePoints.filter[topKante.isConnectedTo(it)].toSet.
				unique,
			punktObjekt2.singlePoints.filter[topKante.isConnectedTo(it)].toSet.
				unique
		)
	}

	/**
	 * @param topKante this TOP Kante
	 * @param punktObjekt1 a Punkt Objekt
	 * @param tb the Bereich_Objekt_Teilbereich_AttributeGroup
	 * 
	 * @return the Abstand of the given Punkt Objekt and the point from each other on this TOP-Kante
	 * 
	 * @throws IllegalArgumentException if the Punkt Objekte are not
	 * unambiguously connected to this TOP-Kante
	 */
	def static Pair<BigDecimal, BigDecimal> getAbstand(TOP_Kante topKante,
		Punkt_Objekt punktObjekt1,
		Bereich_Objekt_Teilbereich_AttributeGroup tb) {
		return topKante.getAbstand(
			punktObjekt1.singlePoints.filter[topKante.isConnectedTo(it)].toSet.
				unique,
			tb
		)
	}

	/**
	 * @param topKante this TOP Kante
	 * @param topKnoten a TOP Knoten
	 * @param singlePoint a single point
	 * 
	 * @return the Abstand of the given single point from the given TOP Knoten
	 * on this TOP-Kante
	 * 
	 * @throws IllegalArgumentException if the single point is not
	 * connected to this TOP Kante or the TOP Knoten is no node of this TOP Kante
	 */
	def static BigDecimal getAbstand(
		TOP_Kante topKante,
		TOP_Knoten topKnoten,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		if (topKnoten == topKante.TOPKnotenA) {
			return topKante.getAbstand(singlePoint)
		}
		if (topKnoten == topKante.TOPKnotenB) {
			return topKante.laenge - topKante.getAbstand(singlePoint)
		}
		throw new IllegalArgumentException(topKnoten.toString)
	}

	def static BigDecimal getAbstand(TOP_Kante topKante, GEO_Knoten geoKnoten) {
		val isGeoKnotenOnTopKkante = geoKnoten.geoKanten.exists [
			topKante.geoKanten.contains(it)
		]
		if (!isGeoKnotenOnTopKkante) {
			throw new IllegalArgumentException('''GeoKnoten: «geoKnoten.identitaet.wert» isn't belong to TOP_Kante: «topKante.identitaet.wert» ''')
		}

		if (topKante.TOPKnotenA.IDGEOKnoten.value === geoKnoten) {
			return BigDecimal.ZERO
		}

		if (topKante.TOPKnotenB.IDGEOKnoten.value === geoKnoten) {
			return topKante.laenge
		}

		val distance = topKante.geoKanten.getAbstand(
			topKante.TOPKnotenA.IDGEOKnoten.value, geoKnoten, BigDecimal.ZERO)
		return distance
	}

	private def static BigDecimal getAbstand(List<GEO_Kante> geoKanten,
		GEO_Knoten startKnoten, GEO_Knoten targetKnoten, BigDecimal distance) {
		if (startKnoten === targetKnoten || startKnoten === null ||
			targetKnoten === null) {
			return distance
		}

		val geoKante = startKnoten.geoKanten.filter[geoKanten.contains(it)].
			firstOrNull
		if (geoKante === null) {
			throw new IllegalArgumentException
		}
		val nextKnoten = geoKante.IDGEOKnotenA.value === startKnoten
				? geoKante.IDGEOKnotenB.value
				: geoKante.IDGEOKnotenA.value
		val remainingGeoKanten = geoKanten.filter[it !== geoKante].toList
		return getAbstand(remainingGeoKanten, nextKnoten, targetKnoten,
			distance +
				(geoKante.GEOKanteAllg?.GEOLaenge?.wert ?: BigDecimal.ZERO))
	}

	/**
	 * @param topKante this TOP Kante
	 * @param basisObjekt1 the first object
	 * @param basisObjekt2 the second object
	 * 
	 * @return the Abstand of the given objects on this TOP Kante
	 */
	static def dispatch BigDecimal getAbstandDispatch(TOP_Kante topKante,
		Basis_Objekt basisObjekt1, Basis_Objekt basisObjekt2) {
		throw new IllegalArgumentException(
			'''Unexpected Argumenttypes «basisObjekt1.class.simpleName», «basisObjekt2.class.simpleName»'''
		)
	}

	static def dispatch BigDecimal getAbstandDispatch(TOP_Kante topKante,
		Punkt_Objekt punktObject1, Punkt_Objekt punktObjekt2) {
		return topKante.getAbstand(punktObject1, punktObjekt2)
	}

	static def Pair<BigDecimal, BigDecimal> getAbstandBO(TOP_Kante topKante,
		Punkt_Objekt punktObject,
		Bereich_Objekt_Teilbereich_AttributeGroup tb) {
		return topKante.getAbstand(punktObject, tb)
	}

	static def dispatch BigDecimal getAbstandDispatch(TOP_Kante topKante,
		TOP_Knoten topKnoten, Punkt_Objekt punktObjekt) {
		return topKante.getAbstand(
			topKnoten,
			punktObjekt.singlePoints.filter[topKante.isConnectedTo(it)].toList.
				unique
		)
	}

	static def dispatch BigDecimal getAbstandDispatch(TOP_Kante topKante,
		Punkt_Objekt punktObjekt, TOP_Knoten topKnoten) {
		return topKante.getAbstand(
			topKnoten,
			punktObjekt.singlePoints.filter[topKante.isConnectedTo(it)].toList.
				unique
		)
	}

	/**
	 * @param topKante this TOP Kante
	 * 
	 * @return the Länge of this TOP Kante
	 */
	def static BigDecimal getLaenge(TOP_Kante topKante) {
		return topKante.TOPKanteAllg.TOPLaenge.wert
	}

	/**
	 * @param topKante this TOP Kante
	 * @param singlePoint a single point
	 * 
	 * @return whether the single point lies on this TOP Kante
	 */
	def static boolean intersect(TOP_Kante topKante,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		return !topKante.intersection(singlePoint.singlePoints).empty
	}

	/**
	 * @param topKante this TOP Kante
	 * @param singlePoints the single points
	 * 
	 * @return the common single points of this TOP Kante and the given
	 * collection of single points
	 */
	def static Set<Punkt_Objekt_TOP_Kante_AttributeGroup> intersection(
		TOP_Kante topKante,
		Collection<Punkt_Objekt_TOP_Kante_AttributeGroup> singlePoints
	) {
		return singlePoints.filter [
			IDTOPKante?.wert == topKante.identitaet.wert
		].toSet
	}

	/**
	 * @param topKante this TOP Kante
	 * @param topKnoten a TOP Knoten of this TOP Kante
	 * 
	 * @return the Anschluss of this TOP Kante at the TOP Knoten
	 */
	def static ENUMTOPAnschluss getTOPAnschluss(TOP_Kante topKante,
		TOP_Knoten topKnoten) {
		val idA = topKante.IDTOPKnotenA
		val idB = topKante.IDTOPKnotenB

		if (topKnoten.identitaet.wert == idA?.wert) {
			return topKante.TOPKanteAllg.TOPAnschlussA.wert
		}

		if (topKnoten.identitaet.wert == idB?.wert) {
			return topKante.TOPKanteAllg.TOPAnschlussB.wert
		}

		throw new IllegalArgumentException('''topKnoten=«topKnoten.identitaet.wert»''')
	}

	def static ENUMTOPAnschluss getTOPAnschlussA(TOP_Kante topKante) {
		return topKante.TOPKanteAllg.TOPAnschlussA.wert
	}

	def static ENUMTOPAnschluss getTOPAnschlussB(TOP_Kante topKante) {
		return topKante.TOPKanteAllg.TOPAnschlussB.wert
	}

	/**
	 * @param topKante this TOP Kante
	 * @param topKnoten a TOP Knoten of this TOP Kante
	 * 
	 * @return the opposite TOP Knoten on this TOP Kante
	 */
	def static TOP_Knoten opposite(TOP_Kante topKante, TOP_Knoten topKnoten) {
		val idA = topKante.IDTOPKnotenA
		val idB = topKante.IDTOPKnotenB

		if (topKnoten.identitaet.wert == idA?.wert) {
			return topKante.TOPKnotenB
		}

		if (topKnoten.identitaet.wert == idB?.wert) {
			return topKante.TOPKnotenA
		}

		throw new IllegalArgumentException('''topKnoten=«topKnoten.identitaet.wert»''')
	}

	/**
	 * @param topKante this TOP Kante (origin)
	 * @param destination the destination TOP Kante
	 * @param transition the transition TOP Knoten
	 * 
	 * @return whether a routing from this TOP Kante to the destination TOP Kante is possible
	 */
	def static boolean isRoute(TOP_Kante topKante, TOP_Kante destination,
		TOP_Knoten transition) {
		val anschlussOrigin = topKante.getTOPAnschluss(transition)
		val anschlussDestination = destination.getTOPAnschluss(transition)

		if (anschlussOrigin.point) {
			return anschlussDestination.branch
		}

		if (anschlussOrigin.branch) {
			return anschlussDestination.point
		}

		return false
	}

	/**
	 * @param topKante this TOP Kante
	 * 
	 * @return the TOP Knoten of this TOP Kante
	 */
	def static List<TOP_Knoten> getTOPKnoten(TOP_Kante topKante) {
		val List<TOP_Knoten> result = new LinkedList
		result.add(topKante.TOPKnotenA)
		result.add(topKante.TOPKnotenB)
		return result
	}

	/**
	 * @param topKante this TOP Kante
	 * 
	 * @return the Weichen, Kreuzung, Gleissperre Elemente of this TOP Kante
	 */
	def static List<W_Kr_Gsp_Element> getWKrGspElemente(TOP_Kante topKante) {
		val result = Lists.newLinkedList
		result.add(topKante.TOPKnotenA.WKrGspElement)
		result.add(topKante.TOPKnotenB.WKrGspElement)
		return result
	}

	private def static boolean isPoint(ENUMTOPAnschluss anschluss) {
		return anschluss == ENUMTOP_ANSCHLUSS_SPITZE
	}

	private def static boolean isBranch(ENUMTOPAnschluss anschluss) {
		return anschluss == ENUMTOP_ANSCHLUSS_LINKS ||
			anschluss == ENUMTOP_ANSCHLUSS_RECHTS
	}

	private def static boolean isOnProperEndOf(
		Punkt_Objekt_TOP_Kante_AttributeGroup p,
		TOP_Kante adjacentTopKante,
		TOP_Kante originTopKante
	) {
		if (p.topKante != adjacentTopKante) {
			// p not on adjacentTopKante
			return false
		}

		val abstand = p.abstand.wert

		val A = adjacentTopKante.TOPKnotenA
		val B = adjacentTopKante.TOPKnotenB
		val C = adjacentTopKante.connectionTo(originTopKante)

		if (A == C) {
			// p at start of adjacentTopKante
			return abstand.isApproxEqual(BigDecimal.ZERO)
		}

		if (B == C) {
			// p at end of adjacentTopKante
			return abstand.isApproxEqual(adjacentTopKante.laenge)
		}

		return false
	}

	private def static boolean isApproxEqual(BigDecimal a, BigDecimal b) {
		return new Distance().compare(a, b) == 0
	}

	def private static List<DirectedElement<GEO_Kante>> getGeoKanten(
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> directedTopEdge,
		GEO_Knoten start,
		List<DirectedElement<GEO_Kante>> result
	) {
		// if we reached the end of the TOP edge we are through
		if (start === directedTopEdge?.head?.GEOKnoten) {
			return result
		}

		// these are the GEO edges starting at the start GEO node
		val startEdges = start.getGeoKantenOnParentKante(directedTopEdge.element)

		// we do not want to go backwards and remove all previously found edges
		startEdges.removeAll(result.map[element])

		// if we found no more edges, the TOP edge is not covered properly
		if (startEdges.empty) {
			return result
		}

		// the list should now only contain the start edge
		Assert.isTrue(startEdges.size == 1)
		val startEdge = startEdges.get(0)

		// we convert it to an directed element and add it to the result
		val directedStartEdge = new DirectedElementImpl<GEO_Kante>(startEdge,
			startEdge.geoKnotenA === start)
		result.add(directedStartEdge)

		// we continue our search with the end node of the found edge
		return directedTopEdge.getGeoKanten(startEdge.getOpposite(start),
			result)
	}

	/**
	 * Returns all GEO_Kanten with their distance from the TOP_Knoten_A
	 * 
	 * @param topKante the TOP_Kante
	 * @return an iterable of pairs of a GEO_Kante and its associated distance from the start of the TOP_Kante
	 */
	def static Iterable<Pair<GEO_Kante, BigDecimal>> getGeoKantenWithDistance(
		TOP_Kante topKante) {
		val topKnotenA = topKante.TOPKnotenA
		val start = topKnotenA.GEOKnoten
		return start.getGeoKantenWithDistance(topKante)
	}
}
