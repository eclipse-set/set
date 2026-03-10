/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.math.BigDecimal
import java.util.Collection
import java.util.List
import java.util.Set
import org.eclipse.core.runtime.Assert
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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*
import org.eclipse.emf.ecore.util.EcoreUtil
import org.apache.commons.lang3.Range
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.basis.graph.TopPoint

/**
 * Extensions for {@link Bereich_Objekt}.
 */
class BereichObjektExtensions extends BasisObjektExtensions {

	static final Logger logger = LoggerFactory.getLogger(
		typeof(BereichObjektExtensions)
	);

	private static class TopArea {
		new(Bereich_Objekt_Teilbereich_AttributeGroup tb) {
			topGUID = tb?.IDTOPKante?.wert
			if (tb?.begrenzungA?.wert === null ||
				tb?.begrenzungB?.wert === null) {
				start = BigDecimal.valueOf(-1)
				end = BigDecimal.valueOf(-1)
			} else if (tb?.begrenzungA?.wert <= tb?.begrenzungB?.wert) {
				start = tb?.begrenzungA?.wert
				end = tb?.begrenzungB?.wert
			} else {
				end = tb?.begrenzungA?.wert
				start = tb?.begrenzungB?.wert
			}

		}

		def BigDecimal length() {
			return end - start
		}

		def BigDecimal getOverlappingLength(TopArea other) {
			if (topGUID != other.topGUID || start < BigDecimal.ZERO ||
				end < BigDecimal.ZERO)
				return BigDecimal.ZERO

			// Find the point where either area ends
			val end = this.end.min(other.end)

			// Determine whether this or other starts first
			if (this.start < other.start) {
				// This area starts first
				// If this area ends before the other area begins, the length is zero
				if (this.end <= other.start) {
					return BigDecimal.ZERO
				}

				// Otherwise the length is the distance from the start of the other area to either area end 
				return end - other.start
			} else {
				// other area starts first
				// If B ends before A begins, the length is zero
				if (other.end <= this.start) {
					return BigDecimal.ZERO
				}
				// Otherwise the length is the distance from the start of this area to either area end 
				return end - this.start
			}
		}

		String topGUID
		BigDecimal start
		BigDecimal end
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
		return bereich.bereichObjektTeilbereich.map[new TopArea(it)].groupBy [
			topGUID
		].values.flatMap[removeOverlaps].map[length].reduce[a, b|a + b]
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

		val tA = teilbereich.begrenzungA.wert
		val tB = teilbereich.begrenzungB.wert
		val oA = other.begrenzungA.wert
		val oB = other.begrenzungB.wert

		return intersects(tA, tB, oA, oB)
	}

	protected def static boolean intersects(BigDecimal a1, BigDecimal a2,
		BigDecimal b1, BigDecimal b2) {
		val distance = new Distance()
		Assert.isTrue(distance.compare(a1, a2) <= 0)
		Assert.isTrue(distance.compare(b1, b2) <= 0)

		// [a1,a2] < [b1,b2]
		if (distance.compare(a2, b1) < 0) {
			return false
		}

		// [b1,b2] < [a1,a2]
		if (distance.compare(b2, a1) < 0) {
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

		val tA = teilbereich?.begrenzungA?.wert
		val tB = teilbereich?.begrenzungB?.wert
		val oA = other?.begrenzungA?.wert
		val oB = other?.begrenzungB?.wert

		return intersectsStrictly(tA, tB, oA, oB)
	}

	protected def static boolean intersectsStrictly(BigDecimal a1,
		BigDecimal a2, BigDecimal b1, BigDecimal b2) {
		if (#[a1, a2, b1, b2].exists[it === null]) {
			return false
		}

		val distance = new Distance()
		Assert.isTrue(distance.compare(a1, a2) <= 0)
		Assert.isTrue(distance.compare(b1, b2) <= 0)

		// If the second tb starts after the first tb ends, there is no intersection
		val aEndBStart = distance.compare(a2, b1)
		if (aEndBStart < 0) {
			return false
		}

		// If the first tb starts after the second tb ends, there is no intersection
		val bEndAStart = distance.compare(b2, a1)
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
	 * @param object the object
	 * 
	 * @returns whether this Bereichsobjekt contains the given object
	 */
	def static boolean contains(
		Bereich_Objekt bereich,
		Punkt_Objekt object
	) {
		return bereich.bereichObjektTeilbereich.exists [ bo |
			object.singlePoints.exists [
				bo.contains(it)
			]
		]
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param object the object
	 * 
	 * @returns whether this Bereichsobjekt contains the given object
	 */
	def static boolean contains(
		Bereich_Objekt bereich,
		Punkt_Objekt object,
		double tolerance
	) {
		if (tolerance == 0) {
			return bereich.contains(object)
		}
		return bereich.bereichObjektTeilbereich.exists [ bo |
			object.singlePoints.exists [
				bo.contains(it, tolerance)
			]
		]
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
		return bereich.bereichObjektTeilbereich.exists[contains(singlePoint)]
	}

	/**
	 * @param bereich this Bereichsobjekt
	 * @param singlePoint the single point
	 * @param tolerant the tolerant distance in meter
	 * 
	 * @returns whether this Bereichsobjekt contains the given 
	 * 			single point with tolerant distance
	 */
	def static boolean contains(Bereich_Objekt bereich,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint, double tolerant) {
		return bereich.bereichObjektTeilbereich.exists [
			contains(singlePoint, tolerant)
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
		val distance = new Distance()
		if (sameTopKante) {
			val A = teilbereich.begrenzungA.wert
			val B = teilbereich.begrenzungB.wert

			if (distance.compare(A, B) > 0) {
				throw new IllegalArgumentException('''Teilbereich with Begrenzungen A=«A» B=«B»''')
			}

			val abstand = singlePoint.abstand.wert

			return distance.compare(A, abstand) <= 0 &&
				distance.compare(abstand, B) <= 0
		}

		return false
	}

	/**
	 * @param teilbereich the Teilbereich
	 * @param singlePoint the single point
	 * @param tolerant the tolerant distance in meter
	 * 
	 * @returns whether this teilbereich contains the given 
	 * 			single point with tolerant distance
	 */
	def static boolean contains(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
		double tolerant
	) {
		val tolerantBigDecimal = BigDecimal.valueOf(tolerant)
		val teilBereichTopKante = teilbereich.IDTOPKante?.value
		val sameTopKante = teilbereich.IDTOPKante?.wert ==
			singlePoint.IDTOPKante?.wert

		// The teilbereich have same TOP_Kante with the point
		if (sameTopKante) {
			val clone = EcoreUtil.copy(teilbereich)
			if (teilbereich.begrenzungA.wert !== BigDecimal.ZERO) {
				val tolerantLimitA = teilbereich.begrenzungA.wert -
					tolerantBigDecimal
				clone.begrenzungA.wert = tolerantLimitA <=
					BigDecimal.ZERO ? BigDecimal.ZERO : tolerantLimitA
			}

			if (teilbereich.begrenzungB.wert !== teilBereichTopKante.laenge) {
				val tolerantLimitB = teilbereich.begrenzungB.wert +
					tolerantBigDecimal
				clone.begrenzungB.wert = tolerantLimitB >=
					teilBereichTopKante.laenge
					? teilBereichTopKante.laenge
					: tolerantLimitB
			}

			val isContains = clone.contains(singlePoint)
			if (clone.begrenzungA.wert === BigDecimal.ZERO &&
				clone.begrenzungB.wert === teilBereichTopKante.laenge &&
				!isContains) {
				throw new IllegalArgumentException('''The TOP_Kante: «teilbereich.IDTOPKante.wert» should contain the Punkt_Objekt: «singlePoint.identitaet»''')
			}
			return isContains
		}
		// The point should lie on the connect TOP_Kanten of this teilbereich TOP_Kante
		if (teilBereichTopKante.adjacentTopKanten.forall [
			it !== singlePoint.topKante
		]) {
			return false;
		}

		val A = teilbereich.begrenzungA.wert
		val B = teilbereich.begrenzungB.wert
		val topKanteRange = Range.of(BigDecimal.ZERO,
			teilBereichTopKante.laenge)

		// When the point and the teilbereich not in same TopKante,
		// then the teilbereich with tolerant muss out of topkante range
		if (topKanteRange.contains(A - tolerantBigDecimal) &&
			topKanteRange.contains(B + tolerantBigDecimal)) {
			return false
		}
		val tolerantDistanceFromA = topKanteRange.
				isStartedBy(A) ? tolerantBigDecimal : tolerantBigDecimal - A
		val tolerantDistanceFromB = topKanteRange.isEndedBy(B)
				? tolerantBigDecimal
				: (tolerantBigDecimal + B) - topKanteRange.maximum
		return teilbereich.containsWithinTolerant(singlePoint,
			teilBereichTopKante.TOPKnotenA, tolerantDistanceFromA) ||
			teilbereich.containsWithinTolerant(singlePoint,
				teilBereichTopKante.TOPKnotenB, tolerantDistanceFromB)
	}

	private def static boolean containsWithinTolerant(
		Bereich_Objekt_Teilbereich_AttributeGroup botb,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint, TOP_Knoten topKnote,
		BigDecimal tolerant) {
		val targetTopKante = topKnote.topKanten.findFirst [
			it !== botb.topKante && it === singlePoint.IDTOPKante?.value
		]
		return targetTopKante !== null &&
			targetTopKante.getAbstand(topKnote, singlePoint) <= tolerant
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
		val middleEdges = edges.filter [
			it !== edges.head && it !== edges.lastOrNull
		]
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
		val tA = teilbereich.begrenzungA.wert
		val tB = teilbereich.begrenzungB.wert
		Assert.isNotNull(start)
		Assert.isNotNull(end)
		val aStart = eTopKante.getAbstand(start)
		val aEnd = eTopKante.getAbstand(end)
		val eA = aStart.min(aEnd)
		val eB = aStart.max(aEnd)
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
		val tA = teilbereich.begrenzungA.wert
		val tB = teilbereich.begrenzungB.wert
		Assert.isNotNull(start)
		val aStart = eTopKante.getAbstand(start)
		var aEnd = eTopKante.laenge
		if (!edge.forwards) {
			aEnd = BigDecimal.ZERO
		}
		val eA = aStart.min(aEnd)
		val eB = aStart.max(aEnd)
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
		val tA = teilbereich.begrenzungA.wert
		val tB = teilbereich.begrenzungB.wert
		Assert.isNotNull(end)
		var aStart = BigDecimal.ZERO
		if (!edge.forwards) {
			aStart = eTopKante.laenge
		}
		val aEnd = eTopKante.getAbstand(end)
		val eA = aStart.min(aEnd)
		val eB = aStart.max(aEnd)
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
		val comparator = new Distance()
		val A = teilbereich.begrenzungA.wert
		val B = teilbereich.begrenzungB.wert
		val toBigDecimal = BigDecimal.valueOf(distance)
		return comparator.compare(A, toBigDecimal) <= 0 &&
			comparator.compare(toBigDecimal, B) <= 0
	}

	def static BigDecimal getOverlappingLength(Bereich_Objekt bo,
		Bereich_Objekt_Teilbereich_AttributeGroup tb) {
		if (bo === null || tb === null)
			return BigDecimal.ZERO

		val tb1 = bo.bereichObjektTeilbereich.filter [
			begrenzungA?.wert !== null && begrenzungB?.wert !== null
		].map[new TopArea(it)].toList
		val tb2 = new TopArea(tb)

		val noOverlap1 = tb1.groupBy[topGUID].values.flatMap[removeOverlaps]

		return noOverlap1.map[getOverlappingLength(tb2)].reduce[a, b|a + b] ?:
			BigDecimal.ZERO
	}

	def static BigDecimal getOverlappingLength(Bereich_Objekt bo,
		Bereich_Objekt bo2) {
		if (bo === null || bo2 === null)
			return BigDecimal.ZERO

		val tb1 = bo.bereichObjektTeilbereich.map[new TopArea(it)].toList
		val tb2 = bo2.bereichObjektTeilbereich.map[new TopArea(it)].toList

		val noOverlap1 = tb1.groupBy[topGUID].values.flatMap[removeOverlaps]
		val noOverlap2 = tb2.groupBy[topGUID].values.flatMap[removeOverlaps]

		return noOverlap1.flatMap [ tb |
			noOverlap2.map [
				tb.getOverlappingLength(it)
			]
		].reduce[a, b|a + b] ?: BigDecimal.ZERO
	}

	private def static List<TopArea> removeOverlaps(List<TopArea> areas) {
		// all areas are on the same top edge
		var current = BigDecimal.ZERO

		for (area : areas.sortBy[start]) {
			// If this subarea starts before the previous one has ended, move the start to the end of the previous area
			if (area.start >= BigDecimal.ZERO && area.end >= BigDecimal.ZERO) {
				if (area.start < current) {
					area.start = current
				}
				current = area.end
			}
		}
		return areas
	}

	/**
	 * @param bo this Bereich_Objekt
	 * 
	 * @return the control area, which the most overlap with the area
	 */
	def static Stell_Bereich getMostOverlapControlArea(Bereich_Objekt bo) {
		val areas = bo.container.stellBereich
		return areas.max [ first, second |
			val firstDistance = bo.getOverlappingLength(first)
			val secondDistance = bo.getOverlappingLength(second)
			return firstDistance.compareTo(secondDistance)
		]
	}

	def static List<List<TopPoint>> toTopPoints(Bereich_Objekt bo) {
		return bo.bereichObjektTeilbereich.map[toTopPoints]
	}

	def static List<TopPoint> toTopPoints(
		Bereich_Objekt_Teilbereich_AttributeGroup tb) {
		return List.of(
			new TopPoint(tb.topKante, tb.begrenzungA.wert),
			new TopPoint(tb.topKante, tb.begrenzungB.wert)
		)
	}
}
