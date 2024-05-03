/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.trackservice

import java.math.BigDecimal
import java.util.ArrayList
import java.util.List
import org.eclipse.set.basis.geometry.Geometries
import org.eclipse.set.basis.geometry.SegmentPosition
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.ppmodel.extensions.GeoKanteExtensions
import org.eclipse.set.ppmodel.extensions.utils.GeoPosition
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.LineSegment
import org.locationtech.jts.geom.LineString

import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.*

/** 
 * A GEO_Kante with additional cached metadata
 */
class GEOKanteMetadata {
	
	/**
	 * The minimal length to segment a track
	 */
	final static double SEGMENT_THRESHOLD = 0.001
	/** 
	 * The referenced GEO_Kante
	 */
	final GEO_Kante geoKante
	/** 
	 * GEO_Knoten
	 */
	final GEO_Knoten geoKnoten
	/** 
	 * The geometry of the GEO_Kante
	 */
	final LineString geometry
	/** 
	 * The segments of the GEO_Kante defined by splitting the GEO_Kante at
	 * either start or end of a Bereich_Objekt
	 */
	final List<GEOKanteSegment> segments = new ArrayList()
	/** 
	 * The distance of the GEO_Kante from the TOP_Knoten
	 */
	final double start
	/** 
	 * Length of the GEO_Kante on the TOP_Kante
	 */
	final double length

	new(GEO_Kante geoKante, double start, double length,
		List<Bereich_Objekt> bereichObjekte, TOP_Kante topKante,
		GEO_Knoten geoKnoten) {
		this.geoKante = geoKante
		this.start = start
		this.length = length
		this.geometry = geoKante.geometry
		this.geoKnoten = geoKnoten
		val double end = getEnd()
		// Determine segments
		// Create an initial segment covering the entire length
		segments.add(new GEOKanteSegment(start, length))
		// Filter Bereich_Objekte to only include those that are relevant to the
		// GEO_Kante
		val relevantBOs = bereichObjekte.filter([ bo |
			bo.bereichObjektTeilbereich.exists([ tb |
				// Only consider Bereich_Objekte which are on the same TOP_Kante
				if (tb.IDTOPKante?.value !== topKante) {
					return false
				}
				val begrenzungA = tb.begrenzungA?.wert
				if (begrenzungA === null)
					return false

				val begrenzungB = tb.begrenzungB?.wert
				if (begrenzungB === null)
					return false
				return true
			])
		])

		// Split the GEO_Kante into segments according to the BOs found
		relevantBOs.forEach([ bo |
			bo.bereichObjektTeilbereich.forEach([ tb |
				// Skip Bereich_Objekte which are not affecting this GEO_Kante
				val tbStart = tb.begrenzungA.wert.doubleValue
				val tbEnd = tb.begrenzungB.wert.doubleValue
				if (tbStart > end || tbEnd < start) {
					return
				}
				if (tbStart <= start && tbEnd >= end) {
					// Case 1: Bereich_Objekt covers the entire GEO_Kante
					// No need to split the GEO_Kante
				} else if (tbStart >= start && tbEnd <= end) {
					// Case 2: Bereich_Objekt starts and ends within the GEO_Kante
					// Split the segment at tbStart and tbEnd
					splitSegmentsAt(tbStart)
					splitSegmentsAt(tbEnd)
				} else if (tbStart <= start && tbEnd <= end) {
					// Case 3: Bereich_Objekt starts before the GEO_Kante and ends within it
					// Split the segment at tbEnd
					splitSegmentsAt(tbEnd)
				} else if (tbStart >= start && tbEnd >= end) {
					// Case 4: Bereich_Objekt starts within the GEO_Kante and ends after it
					// Split the segment at tbStart
					splitSegmentsAt(tbStart)
				}
			])
		])

		// For the relevant BOs, check if they apply to any segment
		segments.forEach([ segment |
			val segStart = segment.start
			val segEnd = segment.end

			relevantBOs.forEach([ bo |
				bo.bereichObjektTeilbereich.forEach([ tb |
					// Skip Bereich_Objekte which are not affecting this GEO_Kante
					val tbStart = tb.begrenzungA.wert.doubleValue
					val tbEnd = tb.begrenzungB.wert.doubleValue
					if (tbStart > end || tbEnd < start) {
						return
					}
					if (tbStart > segEnd) {
						// TB begins after segment ends
						return
					}

					if (tbEnd < segStart) {
						// TB ends before segment starts
						return
					}
					segment.bereichObjekte.add(bo)
				])
			])
		])
	}

	new(
		GEO_Kante geoKante,
		double start,
		GEO_Knoten geoKnoten
	) {
		this.geoKante = geoKante
		this.start = start
		this.length = (geoKante.GEOKanteAllg?.GEOLaenge?.wert ?:
			BigDecimal.ZERO).doubleValue
		this.geometry = geoKante.geometry
		this.geoKnoten = geoKnoten
		// As there is no segmenting information, only use one segment
		segments.add(new GEOKanteSegment(start, length))
	}

	/** 
	 * Determines the segment which contains a point at a given distance
	 * @param distance distance to consider
	 * @return the GEOKanteSegment for the given distance or null if distance is
	 * outside the GEO_Kante
	 */
	def GEOKanteSegment getContainingSegment(double distance) {
		return segments.filter([ segment |
			segment.start <= distance &&
				segment.start + segment.length >= distance
		]).head
	}

	/** 
	 * Returns the GEOKanteCoordinate for a given distance on this GEO_Kante
	 * @param distance the distance on the GEO_Kante as defined by the distance from
	 * the start of the original TOP_Kante
	 * @param lateralDistance the lateral distance to consider
	 * @param wirkrichtung the direction of the object
	 * @return a GEOKanteCoordinate specifying the geographical position of a
	 * point with the given distance and lateralDistance
	 * @if the geometry cannot be determined
	 */
	def GEOKanteCoordinate getCoordinate(double distance,
		double lateralDistance, ENUMWirkrichtung wirkrichtung) {
		// Find the segment of the GEO_Kante which contains the coordinate
		val GEOKanteSegment segment = getContainingSegment(distance)
		if (segment === null) {
			throw new RuntimeException("Missing GEO_Kante segment") // $NON-NLS-1$
		}
		// If the geometry size is smaller than the GEO_Laenge of the GEO_Kante
		// adjust the distance to fit within the GEO_Kante
		val double edgeLength = Math.abs(length)
		val double geoLength = geometry.length
		val double localDistance = distance - getStart()
		val double scaledDistance = localDistance * (geoLength / edgeLength)
		val SegmentPosition position = Geometries.getSegmentPosition(geometry,
			geoKnoten.coordinate, scaledDistance)
		val LineSegment tangent = geoKante.getTangent(position)
		val GeoPosition coordinate = GeoKanteExtensions.
			getCoordinate(tangent, position, lateralDistance, wirkrichtung)
		return new GEOKanteCoordinate(coordinate, segment.bereichObjekte,
			getCRS())
	}

	/** 
	 * Splits the segments at a given distance
	 * @param distance
	 * @return the two segments
	 */
	private def splitSegmentsAt(double distance) {
		val GEOKanteSegment originalSegment = getContainingSegment(distance)
		if (originalSegment === null) {
			throw new RuntimeException("Missing GEO_Kante segment") // $NON-NLS-1$
		}
		// Split the original segment
		val GEOKanteSegment nextSegment = new GEOKanteSegment(originalSegment)
		val double firstSegmentLength = distance - originalSegment.getStart()
		val double secondSegmentLength = originalSegment.getEnd() - distance

		// Discard segments that are too short
		if (firstSegmentLength < SEGMENT_THRESHOLD ||
			secondSegmentLength < SEGMENT_THRESHOLD) {
			return
		}
		originalSegment.setLength(firstSegmentLength)
		nextSegment.setStart(distance)
		nextSegment.setLength(secondSegmentLength)
		segments.add(nextSegment)
	}

	/** 
	 * @return the end of the GEO_Kante relative to the start of the TOP_Kante
	 */
	def double getEnd() {
		return start + length
	}

	/** 
	 * @return the start of the GEO_Kante relative to the start of the TOP_Kante
	 */
	def double getStart() {
		return start
	}

	/** 
	 * @return the length of the GEO_Kante on the TOP_Kante
	 */
	def double getLength() {
		return length
	}

	/** 
	 * @return segments defining this GEO_Kante
	 */
	def List<GEOKanteSegment> getSegments() {
		return segments
	}

	/** 
	 * @return the coordinate reference system of this GEO_Kante
	 */
	def ENUMGEOKoordinatensystem getCRS() {
		return geoKnoten.CRS
	}

	/** 
	 * @return the GEO_Kante
	 */
	def GEO_Kante getGeoKante() {
		return geoKante
	}

	/** 
	 * @return the geometry of the GEO_Kante
	 */
	def Geometry getGeometry() {
		return geometry
	}
}
