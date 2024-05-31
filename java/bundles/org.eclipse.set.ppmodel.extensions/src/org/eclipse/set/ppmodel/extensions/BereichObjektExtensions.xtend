/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.DirectedEdgePath
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.ppmodel.extensions.utils.Distance
import org.eclipse.set.ppmodel.extensions.utils.TopKantePath
import java.math.BigDecimal
import java.util.Collection
import java.util.List
import java.util.Set
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.eclipse.core.runtime.Assert

import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*

/**
 * Extensions for {@link Bereich_Objekt}.
 */
class BereichObjektExtensions extends BasisObjektExtensions {

	static final Logger logger = LoggerFactory.getLogger(
		typeof(BereichObjektExtensions)
	);

	/**
	 * Returns an directed edge path of this linear Bereich Objekt.
	 * 
	 * @param bereich this Bereichsobjekt
	 * @param start the start element
	 * @param end the end element
	 * 
	 * @return directed edge path for this linear Bereich Objekt
	 */
	def static DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> getPath(
		Bereich_Objekt bereich,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end
	) {
		return new TopKantePath(bereich, start, end)
	}

	/**
	 * Returns an directed edge path of this linear Bereich Objekt.
	 * 
	 * @param bereich this Bereichsobjekt
	 * @param start the start element
	 * @param end the end element
	 * 
	 * @return directed edge path for this linear Bereich Objekt
	 */
	def static DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> getPath(
		Bereich_Objekt bereich,
		Punkt_Objekt start,
		Punkt_Objekt end
	) {
		val s = bereich.intersection(start)
		val e = bereich.intersection(end)
		if (s.empty) {
			logger.error(
				'''start: topKante=«start.topKanten.unique.identitaet.wert» abstand=«start.singlePoint.abstand.wert»'''
			)
			logger.
				error('''bereichObjektTeilbereich: «bereich.bereichObjektTeilbereich.debugString»''')
			throw new IllegalArgumentException('''Startsignal «start.debugString» not in Bereich «bereich.debugName»''')
		}
		if (e.empty) {
			throw new IllegalArgumentException('''Zielelement «end.debugString» not in Bereich «bereich.debugName»''')
		}

		return new TopKantePath(bereich, s.unique, e.unique)
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * 
	 * @return set of all TOP-Kanten intersecting with this Bereich
	 */
	def static Set<TOP_Kante> getTopKanten(Bereich_Objekt bereich) {
		return bereich.bereichObjektTeilbereich.map[topKante].toSet
	}

	/**
	 * @param object this Bereichsobjekt
	 * 
	 * @returns length of the Bereichsobjekt
	 */
	def static BigDecimal getLength(Bereich_Objekt bereich) {
		return bereich.bereichObjektTeilbereich.fold(
			BigDecimal.valueOf(0),
			[ BigDecimal current, Bereich_Objekt_Teilbereich_AttributeGroup portion |
				current + portion.length
			]
		)
	}

	/**
	 * Returns the TOP-Kante of a Teilbereich of this Bereich for the given
	 * single points. The TOP-Kante needs not to be a TOP-Kante of one of the given
	 * single points. A TOP-Kante may qualify if a single point is located at
	 * the end of an adjacent Top-Kante.
	 * 
	 * @param bereich this Bereichsobjekt
	 * @param singlePoints the single points
	 * 
	 * @return the TOP-Kante for the single points or <code>null</code> if no
	 * TOP-Kante for the single points is found
	 * 
	 * @throws AssertionError if more than one TOP-Kante qualifies as a result
	 */
	def static TOP_Kante getTopKanteFor(Bereich_Objekt bereich,
		Collection<Punkt_Objekt_TOP_Kante_AttributeGroup> singlePoints) {
		val topKanten = bereich.topKanten.filter [ e |
			singlePoints.exists[e.isConnectedTo(it)]
		].toList
		if (topKanten.empty) {
			return null
		}
		if (topKanten.size != 1) {
			throw new IllegalArgumentException('''bereich=«bereich.debugString» punktObjekte=«singlePoints.map[punktObjekt.debugString].toSet» topKanten=«topKanten.debugString»''')
		}
		return topKanten.get(0)
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param punktObjekt the Punkt Objekt
	 * 
	 * @return whether this Bereichsobjekt and the given Punkt Objekt share a
	 * common single point
	 */
	def static boolean intersects(Bereich_Objekt bereich,
		Punkt_Objekt punktObjekt) {
		return !bereich.intersection(punktObjekt).empty
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param path the path
	 * 
	 * @return whether this Bereichsobjekt and the given path share a
	 * common single point
	 */
	def static boolean intersects(
		Bereich_Objekt bereich,
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt> path
	) {
		return !bereich.intersection(path).empty
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param path the path
	 * 
	 * @returns whether this Bereichsobjekt and the given path share a
	 * common portion of a TOP-Kante
	 */
	def static boolean areaIntersects(Bereich_Objekt bereich,
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path) {
		val result = bereich.bereichObjektTeilbereich.exists [
			areaIntersects(path)
		]
		if (logger.debugEnabled) {
			logger.
				debug('''«bereich.debugName» areaIntersects «path.debugNodesAndEdges» = «result»''')
		}
		return result
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param other another Bereichsobjekt
	 * 
	 * @return whether this Bereichsobjekt intersect with the other
	 * Bereichobjekt
	 */
	def static boolean intersects(
		Bereich_Objekt bereich,
		Bereich_Objekt other
	) {
		return bereich.bereichObjektTeilbereich.exists[other.intersects(it)]
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param teilbereich a Teilbereich
	 * 
	 * @return whether this Bereichsobjekt intersect with the given
	 * Teilbereich
	 */
	def static boolean intersects(
		Bereich_Objekt bereich,
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich
	) {
		return bereich.bereichObjektTeilbereich.exists[intersects(teilbereich)]
	}

	/**
	 * @param teilbereich a Teilbereich
	 * @param other another Teilbereich
	 * 
	 * @return whether the Teilbereich intersect with the other
	 * Teilbereich
	 */
	def static boolean intersects(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		Bereich_Objekt_Teilbereich_AttributeGroup other
	) {
		if (teilbereich.topKante.identitaet.wert !=
			other.topKante.identitaet.wert) {
			return false
		}

		val tA = teilbereich.begrenzungA.wert.doubleValue
		val tB = teilbereich.begrenzungB.wert.doubleValue
		val oA = other.begrenzungA.wert.doubleValue
		val oB = other.begrenzungB.wert.doubleValue

		return intersects(tA, tB, oA, oB)
	}

	protected def static boolean intersects(double a1, double a2, double b1,
		double b2) {
		Assert.isTrue(Distance.compare(a1, a2) <= 0)
		Assert.isTrue(Distance.compare(b1, b2) <= 0)

		// [a1,a2] < [b1,b2]
		if (Distance.compare(a2, b1) < 0) {
			return false
		}

		// [b1,b2] < [a1,a2]
		if (Distance.compare(b2, a1) < 0) {
			return false
		}

		return true
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param other another Bereichsobjekt
	 * 
	 * @return whether this Bereichsobjekt strictly intersects with the other
	 * Bereichobjekt
	 */
	def static boolean intersectsStrictly(
		Bereich_Objekt bereich,
		Bereich_Objekt other
	) {
		return bereich.bereichObjektTeilbereich.exists [
			other.intersectsStrictly(it)
		]
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param teilbereich a Teilbereich
	 * 
	 * @return whether this Bereichsobjekt strictly intersects with the given
	 * Teilbereich
	 */
	def static boolean intersectsStrictly(
		Bereich_Objekt bereich,
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich
	) {
		return bereich.bereichObjektTeilbereich.exists [
			intersectsStrictly(teilbereich)
		]
	}

	/**
	 * @param teilbereich a Teilbereich
	 * @param other another Teilbereich
	 * 
	 * @return whether this Teilbereich strictly intersects with the other
	 * Teilbereich 
	 */
	def static boolean intersectsStrictly(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		Bereich_Objekt_Teilbereich_AttributeGroup other
	) {
		if (teilbereich.topKante.identitaet.wert !=
			other.topKante.identitaet.wert) {
			return false
		}

		val tA = teilbereich.begrenzungA.wert.doubleValue
		val tB = teilbereich.begrenzungB.wert.doubleValue
		val oA = other.begrenzungA.wert.doubleValue
		val oB = other.begrenzungB.wert.doubleValue

		return intersectsStrictly(tA, tB, oA, oB)
	}

	protected def static boolean intersectsStrictly(double a1, double a2,
		double b1, double b2) {
		Assert.isTrue(Distance.compare(a1, a2) <= 0)
		Assert.isTrue(Distance.compare(b1, b2) <= 0)

		// If the second tb starts after the first tb ends, there is no intersection
		val aEndBStart = Distance.compare(a2, b1)
		if (aEndBStart < 0) {
			return false
		}

		// If the first tb starts after the second tb ends, there is no intersection
		val bEndAStart = Distance.compare(b2, a1)
		if (bEndAStart < 0) {
			return false
		}

		// If the two TBs are equal, they intersect
		if (aEndBStart === 0) {
			return bEndAStart === 0
		}

		// Intersection as either
		// - a starts before b ends
		// - b starts before a ends
		return true
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param punktObjekt the Punkt Objekt
	 * 
	 * @return the common single points of this Bereichsobjekt and
	 * the given Punkt Objekt
	 */
	def static Set<Punkt_Objekt_TOP_Kante_AttributeGroup> intersection(
		Bereich_Objekt bereich,
		Punkt_Objekt punktObjekt
	) {
		return punktObjekt.singlePoints.filter[bereich.contains(it)].toSet
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param path the path
	 * 
	 * @returns the common single points of this Bereichsobjekt and
	 * the given path
	 */
	def static Set<Punkt_Objekt_TOP_Kante_AttributeGroup> intersection(
		Bereich_Objekt bereich,
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt> path
	) {
		return bereich.intersection(path.pointIterator.toSet)
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param punktObjekte the Punkt Objekte
	 * 
	 * @returns the common single points of this Bereichsobjekt and
	 * the given Punkt Objekte
	 */
	def static Set<Punkt_Objekt_TOP_Kante_AttributeGroup> intersection(
		Bereich_Objekt bereich,
		Set<Punkt_Objekt> punktObjekte
	) {
		return punktObjekte.map[singlePoints].flatten.filter [
			bereich.contains(it)
		].toSet
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param singlePoint the single point
	 * 
	 * @returns whether this Bereichsobjekt contains the given single point
	 */
	def static boolean contains(
		Bereich_Objekt bereich,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		return bereich.bereichObjektTeilbereich.exists [
			contains(singlePoint)
		]
	}

	/**
	 * @param teilbereich the Teilbereich
	 * @param singlePoint the single point
	 * 
	 * @return whether the Teilbereich contains the given single point
	 */
	def static boolean contains(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		val sameTopKante = teilbereich.IDTOPKante?.wert ==
			singlePoint.IDTOPKante?.wert

		if (sameTopKante) {
			val A = teilbereich.begrenzungA.wert.doubleValue
			val B = teilbereich.begrenzungB.wert.doubleValue

			if (Distance.compare(A, B) > 0) {
				throw new IllegalArgumentException('''Teilbereich with Begrenzungen A=«A» B=«B»''')
			}

			val abstand = singlePoint.abstand.wert.doubleValue

			return Distance.compare(A, abstand) <= 0 &&
				Distance.compare(abstand, B) <= 0
		}

		return false
	}

	/**
	 * @param portion dieser Teilbereich
	 * 
	 * @returns die Länge des Teilbereichs
	 */
	def static BigDecimal getLength(
		Bereich_Objekt_Teilbereich_AttributeGroup portion) {
		return portion.begrenzungB.wert - portion.begrenzungA.wert
	}

	/**
	 * @param portion dieser Teilbereich
	 * 
	 * @returns die TOP Kante des Teilbereichs
	 */
	def static TOP_Kante getTopKante(
		Bereich_Objekt_Teilbereich_AttributeGroup portion
	) {
		return portion.IDTOPKante?.value
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param punktObjekte the Punkt Objekte
	 * 
	 * @returns all Punkt Objekte contained in this Bereichsobjekt
	 */
	def static <T extends Punkt_Objekt> List<T> filterContained(
		Bereich_Objekt bereich,
		Iterable<T> punktObjekte
	) {
		return punktObjekte.filter[bereich.intersects(it)].toList
	}

	private def static boolean areaIntersects(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path
	) {
		val edges = path.edgeList
		val size = edges.size
		if (size == 0) {
			return false
		}
		if (size == 1) {
			return areaIntersects(teilbereich, path.start, edges.get(0),
				path.end)
		}
		val middleEdges = edges.filter[it !== edges.head && it !== edges.lastOrNull]
		return areaIntersects(teilbereich, path.start, edges.head) ||
			areaIntersects(teilbereich, edges.lastOrNull, path.end) ||
			middleEdges.exists[areaIntersects(teilbereich, it)]
	}

	private def static boolean areaIntersects(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge,
		Punkt_Objekt_TOP_Kante_AttributeGroup end
	) {
		val tTopKante = teilbereich.topKante
		val eTopKante = edge.element
		if (tTopKante.identitaet.wert != eTopKante.identitaet.wert) {
			return false
		}
		val tA = teilbereich.begrenzungA.wert.doubleValue
		val tB = teilbereich.begrenzungB.wert.doubleValue
		Assert.isNotNull(start)
		Assert.isNotNull(end)
		val aStart = eTopKante.getAbstand(start)
		val aEnd = eTopKante.getAbstand(end)
		val eA = Math.min(aStart, aEnd)
		val eB = Math.max(aStart, aEnd)
		return intersects(tA, tB, eA, eB)
	}

	private def static boolean areaIntersects(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge
	) {
		val tTopKante = teilbereich.IDTOPKante
		val eTopKante = edge.element
		if (tTopKante?.wert != eTopKante.identitaet.wert) {
			return false
		}
		val tA = teilbereich.begrenzungA.wert.doubleValue
		val tB = teilbereich.begrenzungB.wert.doubleValue
		Assert.isNotNull(start)
		val aStart = eTopKante.getAbstand(start)
		var aEnd = eTopKante.laenge
		if (!edge.forwards) {
			aEnd = 0.0
		}
		val eA = Math.min(aStart, aEnd)
		val eB = Math.max(aStart, aEnd)
		return intersects(tA, tB, eA, eB)
	}

	private def static boolean areaIntersects(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge,
		Punkt_Objekt_TOP_Kante_AttributeGroup end
	) {
		val tTopKante = teilbereich.IDTOPKante
		val eTopKante = edge.element
		if (tTopKante?.wert != eTopKante.identitaet.wert) {
			return false
		}
		val tA = teilbereich.begrenzungA.wert.doubleValue
		val tB = teilbereich.begrenzungB.wert.doubleValue
		Assert.isNotNull(end)
		var aStart = 0.0
		if (!edge.forwards) {
			aStart = eTopKante.laenge
		}
		val aEnd = eTopKante.getAbstand(end)
		val eA = Math.min(aStart, aEnd)
		val eB = Math.max(aStart, aEnd)
		return intersects(tA, tB, eA, eB)
	}

	private def static boolean areaIntersects(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge
	) {
		val tTopKante = teilbereich.IDTOPKante
		val eTopKante = edge.element
		return tTopKante?.wert == eTopKante.identitaet.wert
	}

	/**
	 * @param teilbereich the Teilbereich
	 * @param topKante the TOP_Kante
	 * @param distance distance from the TOP_Knoten_A of the TOP_Kante 
	 * 
	 * @return whether the Teilbereich contains the given single point
	 */
	def static boolean contains(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		TOP_Kante topKante,
		double distance
	) {
		if (teilbereich.IDTOPKante?.wert != topKante.identitaet.wert)
			return false;

		val A = teilbereich.begrenzungA.wert.doubleValue
		val B = teilbereich.begrenzungB.wert.doubleValue

		return Distance.compare(A, distance) <= 0 &&
			Distance.compare(distance, B) <= 0
	}

	def static BigDecimal getOverlappingLength(
		Bereich_Objekt_Teilbereich_AttributeGroup tba,
		Bereich_Objekt_Teilbereich_AttributeGroup tbb) {
		if (tba.IDTOPKante?.wert != tbb.IDTOPKante?.wert)
			return BigDecimal.ZERO

		val taA = tba.begrenzungA?.wert
		val taB = tba.begrenzungB?.wert
		val tbA = tbb.begrenzungA?.wert
		val tbB = tbb.begrenzungB?.wert

		val minA = taA.min(taB)
		val maxA = taA.max(taB)

		val minB = tbA.min(tbB)
		val maxB = tbA.max(tbB)

		// Determine whether A or B starts first
		if (minA < minB) {
			// A starts first
			// If A ends before B begins, the length is zero
			if (maxA <= minB) {
				return BigDecimal.ZERO
			}
			// Otherwise the length is the distance from minB to either maxB or maxA (whichever is less) 
			return maxA.min(maxB) - minB
		} else // minB <= minA 
		{
			// B starts first
			// If B ends before A begins, the length is zero
			if (maxB <= minA) {
				return BigDecimal.ZERO
			}
			// Otherwise the length is the distance from minB to either maxB or maxA (whichever is less)			
			return maxB.min(maxA) - minA
		}
	}

	def static BigDecimal getOverlappingLength(Bereich_Objekt bo,
		Bereich_Objekt_Teilbereich_AttributeGroup tbb) {
		return bo.bereichObjektTeilbereich.map[getOverlappingLength(tbb)].reduce [ a, b |
			a + b
		]
	}

	def static BigDecimal getOverlappingLength(Bereich_Objekt bo,
		Bereich_Objekt bo2) {
		return bo.bereichObjektTeilbereich.map[bo2.getOverlappingLength(it)].
			reduce[a, b|a + b]
	}

}
