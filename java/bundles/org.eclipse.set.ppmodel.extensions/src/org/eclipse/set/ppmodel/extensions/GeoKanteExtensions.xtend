/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Geodaten.ENUMGEOForm
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Kante
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Knoten
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import java.util.List
import org.eclipse.set.basis.geometry.Chord
import org.eclipse.set.basis.geometry.Circle
import org.eclipse.set.basis.geometry.Geometries
import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.basis.geometry.SegmentPosition
import org.eclipse.set.basis.graph.DirectedElement
import org.locationtech.jts.algorithm.distance.DistanceToPoint
import org.locationtech.jts.algorithm.distance.PointPairDistance
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.LineSegment
import org.locationtech.jts.geom.LineString
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CoordinateExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.LineStringExtensions.*
import org.eclipse.set.utils.math.Clothoid
import org.eclipse.set.utils.math.Bloss
import java.util.ArrayList
import org.eclipse.set.toolboxmodel.Geodaten.ENUMGEOKoordinatensystem
import org.eclipse.set.ppmodel.extensions.utils.GeoPosition

/**
 * This class extends {@link GEO_Kante}.
 * 
 * @author Schaefer
 */
class GeoKanteExtensions extends BasisObjektExtensions {
	// Configuration options for Bloss curves
	static int BLOSS_SEGMENTS_MIN = 10
	static double BLOSS_SEGMENTS_PER_LENGTH = 0.5
	static int BLOSS_PRECISION = 20

	// Configuration options for Clothoids
	static int CLOTHOID_SEGMENTS_MIN = 10
	static double CLOTHOID_SEGMENTS_PER_LENGTH = 0.5
	static int CLOTHOID_PRECISION = 5

	static final Logger logger = LoggerFactory.getLogger(
		typeof(GeoKanteExtensions));

	private def static LineString arc(GEO_Kante kante) {
		val Coordinate[] coordinates = kante.coordinates
		val double radius = kante.GEOKanteAllg.GEORadiusA.wert.doubleValue
		return Geometries.createArc(
			getGeometryFactory(),
			new Chord(
				coordinates.get(0),
				coordinates.get(1),
				Math.abs(radius),
				if (radius < 0)
					Chord.Orientation.ARC_RIGHT
				else
					Chord.Orientation.ARC_LEFT
			)
		)
	}

	private def static LineString clothoid(Coordinate fromCoordinate,
		Coordinate toCoordinate, double radius, double length) {
		// Calculate the base clothoid
		val segmentCount = Math.max(length * CLOTHOID_SEGMENTS_PER_LENGTH,
			CLOTHOID_SEGMENTS_MIN) as int
		val clothoid = new Clothoid(Math.abs(radius), length,
			CLOTHOID_PRECISION);
		val clothoidCoordinates = clothoid.getPoints(segmentCount)
		val coords = clothoidCoordinates.map [
			val coordinate = new Coordinate(get(0), get(1), 0)
			// Mirror along y-axis for left curves
			if (radius > 0)
				return coordinate.mirrorY
			else
				return coordinate
		]
		// Move the curve to the right position 
		val coordinates = coords.map [
			offsetBy(fromCoordinate.x, fromCoordinate.y)
		]
		// Rotate the curve so that the last coordinates from the GEO_Kante and curve line up
		val angleA = fromCoordinate.getAngleBetweenPoints(toCoordinate)
		val angleB = fromCoordinate.getAngleBetweenPoints(coordinates.last)
		val angleOffset = angleA - angleB
		return getGeometryFactory().createLineString(coordinates.map [
			rotateAroundPoint(angleOffset, fromCoordinate)
		])
	}

	private def static LineString clothoid(GEO_Kante kante) {
		val radiusA = (kante.GEOKanteAllg?.GEORadiusA?.wert ?: 0).doubleValue
		val radiusB = (kante.GEOKanteAllg?.GEORadiusB?.wert ?: 0).doubleValue
		val angle = kante.GEOKanteAllg.GEORichtungswinkel.wert.doubleValue
		val coordinateA = kante.geoKnotenA.coordinate
		var coordinateB = kante.coordinateKnotenB
		val length = kante.GEOKanteAllg.GEOLaenge.wert.doubleValue
		if (radiusA != 0 && radiusB != 0) {
			// Clothoid connecting two curved tracks	
			// Split the clothoid into two connected clothoids, each of half length
			// Determine the center point
			val clothoid = new Clothoid(Math.abs(radiusB), length,
				CLOTHOID_PRECISION);
			val angleRad = (Math.PI / 200) * (100 - angle)
			val point = clothoid.getPoint(length / 2)
			var centerCoordinate = new Coordinate(point.get(0), point.get(1))
			if (radiusB > 0)
				centerCoordinate = centerCoordinate.mirrorY

			val center = centerCoordinate.rotateAroundOrigin(angleRad).offsetBy(
				coordinateA.x, coordinateA.y)

			// Draw the segments
			val segmentA = clothoid(coordinateA, center, radiusB, length / 2).
				coordinates
			val segmentB = clothoid(center, coordinateB, radiusA, length / 2).
				coordinates
			return getGeometryFactory().createLineString(segmentA + segmentB)
		} else if (radiusA == 0) {
			// Curve from node A to node B
			return clothoid(coordinateA, coordinateB, radiusB, length)
		} else {
			// Curve from node B to node A
			// Invert the radius, as left-right is also inverted due to the
			// drawing direction				
			return clothoid(coordinateB, coordinateA, -radiusA, length)
		}
	}

	private def static LineString blosscurve(Coordinate fromCoordinate,
		Coordinate toCoordinate, double radius, double length) {
		val bloss = new Bloss(Math.abs(radius), length, BLOSS_PRECISION);
		val segmentCount = Math.max(length * BLOSS_SEGMENTS_PER_LENGTH,
			BLOSS_SEGMENTS_MIN) as int
		val coords = bloss.calculate(segmentCount).map [
			val coordinate = new Coordinate(get(0), get(1), 0)
			// Mirror along y-axis for left curves
			if (radius < 0)
				return coordinate.mirrorY
			else
				return coordinate
		]
		// Move the curve to the right position 
		val coordinates = coords.map [
			offsetBy(fromCoordinate.x, fromCoordinate.y)
		]
		// Rotate the curve so that the last coordinates from the GEO_Kante and curve line up
		val angleA = fromCoordinate.getAngleBetweenPoints(toCoordinate)
		val angleB = fromCoordinate.getAngleBetweenPoints(coordinates.last)
		val angleOffset = angleA - angleB
		return getGeometryFactory().createLineString(coordinates.map [
			rotateAroundPoint(angleOffset, fromCoordinate)
		])
	}

	private def static LineString blosscurve(GEO_Kante kante) {
		val radiusA = (kante.GEOKanteAllg?.GEORadiusA?.wert ?: 0).doubleValue
		val radiusB = (kante.GEOKanteAllg?.GEORadiusB?.wert ?: 0).doubleValue
		val coordinateA = kante.geoKnotenA.coordinate
		var coordinateB = kante.coordinateKnotenB
		val length = kante.GEOKanteAllg.GEOLaenge.wert.doubleValue
		if (radiusA != 0 && radiusB != 0) {
			// Bloss curve connecting two straight tracks
			logger.warn("Form Bloss between straight tracks not supported.")
			return getGeometryFactory().createLineString(kante.coordinates)
		} else if (radiusA == 0) {
			// Curve from node B to node A
			return blosscurve(coordinateB, coordinateA, radiusB, length)
		} else {
			// Curve from node A to node B
			// Invert the radius, as left-right is also inverted due to the
			// drawing direction				
			return blosscurve(coordinateA, coordinateB, -radiusA, length)
		}
	}

	def static GeoPosition getCoordinate(
		GEO_Kante geoKante,
		GEO_Knoten startGeoKnoten,
		double abstand,
		double seitlicherAbstand,
		ENUMWirkrichtung wirkrichtung
	) {
		val LineString edgeGeometry = geoKante.geometry

		// If the geometry size is smaller than the GEO_Laenge of the GEO_Kante
		// adjust the distance to fit within the GEO_Kante
		val edgeLength = Math.abs(
			geoKante.GEOKanteAllg.GEOLaenge.wert.doubleValue)
		var distance = abstand
		val geoLength = edgeGeometry.length
		if (geoLength < edgeLength) {
			distance = abstand * (geoLength / edgeLength)
		}
		val SegmentPosition position = Geometries.getSegmentPosition(
			edgeGeometry, startGeoKnoten.coordinate, distance)
		val LineSegment tangent = geoKante.getTangent(position)

		return getCoordinate(tangent, position, seitlicherAbstand,
			wirkrichtung);
	}

	def static GeoPosition getCoordinate(
		LineSegment tangent,
		SegmentPosition position,
		double seitlicherAbstand,
		ENUMWirkrichtung wirkrichtung
	) {
		val LineSegment lateralSegement = Geometries.clone(tangent)
		Geometries.translate(lateralSegement, lateralSegement.p0,
			position.getCoordinate())
			
				
			
		var double angle
		if (seitlicherAbstand < 0) {
			angle = 90
		} else {
			angle = -90
		}
		Geometries.turn(lateralSegement, angle)
		Geometries.scale(lateralSegement, Math.abs(seitlicherAbstand))

		// direction
		val DirectedElement<LineSegment> directedLineSegment = position.
			directedLineSegment
		val LineSegment direction = Geometries.clone(
			directedLineSegment.getElement)
		var double rotation = Geometries.getRotationToVertical(direction);

		return new GeoPosition(lateralSegement.p1, rotation, wirkrichtung)
	}

	private def static Coordinate[] getCoordinates(GEO_Kante kante) {
		return #[kante.geoKnotenA.coordinate, kante.coordinateKnotenB]
	}
	
	private def static Coordinate getCoordinateKnotenB(GEO_Kante kante) {
		val knotenB = kante.geoKnotenB
		var coordinateB = knotenB.coordinate
		if (!kante.isCRSConsistent) {
			coordinateB = coordinateB.transformCRS(knotenB.CRS, kante.CRS)
		} 
		return coordinateB
	}

	/**
	 * Returns the CRS for a GEO_Kante
	 * 
	 * @param geoKante a GEO_Kante
	 * @return the CRS of the GEO_Kante or null
	 */
	static def ENUMGEOKoordinatensystem getCRS(GEO_Kante geoKante) {
		// When exists GEO_Kante stay on two other CRS,
		// then the coordiatenes of KnotenB will be transform into CRS of KnotenA
		return geoKante.geoKnotenA.CRS
	}

	static def boolean isCRSConsistent(GEO_Kante geoKante) {
		val crsA = geoKante.geoKnotenA.CRS
		val crsB = geoKante.geoKnotenB.CRS
		return crsA === crsB
	}

	/**
	 * @param edge the directed GEO edge
	 * 
	 * @return the head (last) node of this directed GEO edge
	 */
	def static GEO_Knoten getHead(DirectedElement<GEO_Kante> edge) {
		return if (edge.isForwards)
			edge?.element?.geoKnotenB
		else
			edge?.element?.geoKnotenA
	}

	/**
	 * @param edge the directed GEO edge
	 * 
	 * @return the tail (first) node of this directed GEO edge
	 */
	def static GEO_Knoten getTail(DirectedElement<GEO_Kante> edge) {
		return if (edge.isForwards)
			edge?.element?.geoKnotenA
		else
			edge?.element?.geoKnotenB
	}

	/**
	 * @param kante this GEO Kante
	 * 
	 * @returns the GEO-Knoten A
	 */
	def static GEO_Knoten getGeoKnotenA(
		GEO_Kante kante
	) {
		return kante.IDGEOKnotenA
	}

	/**
	 * @param kante this GEO Kante
	 * 
	 * @returns the GEO-Knoten B
	 */
	def static GEO_Knoten getGeoKnotenB(
		GEO_Kante kante
	) {
		return kante.IDGEOKnotenB
	}

	/**
	 * @param kante this GEO Kante
	 * 
	 * @returns whether this GEO Kante is a loop
	 */
	def static boolean isLoop(
		GEO_Kante kante
	) {
		return kante.geoKnotenA.identitaet.wert ==
			kante.geoKnotenB.identitaet.wert;
	}

	/**
	 * @param kante this GEO Kante
	 * 
	 * @returns the TOP Kante of this GEO Kante or <code>null</code> if this GEO Kante is no Gleislinie 
	 */
	def static TOP_Kante topKante(
		GEO_Kante kante
	) {
		val result = kante.geoArt

		if (result instanceof TOP_Kante) {
			return result
		}
		return null
	}

	def static Basis_Objekt getGeoArt(
		GEO_Kante kante
	) {
		return kante.IDGEOArt
	}

	/**
	 * @param kante this GEO Kante
	 * @param geoKnoten a GEO Knoten of this GEO Kante
	 * 
	 * @returns the opposite GEO Knoten
	 * 
	 * @throws IllegalArgumentException if the given GEO Knoten is no node
	 * on GEO Kante
	 */
	def static GEO_Knoten getOpposite(
		GEO_Kante kante,
		GEO_Knoten geoKnoten
	) {
		if (kante.geoKnotenA.identitaet.wert == geoKnoten.identitaet.wert) {
			return kante.geoKnotenB
		}
		if (kante.geoKnotenB.identitaet.wert == geoKnoten.identitaet.wert) {
			return kante.geoKnotenA
		}
		throw new IllegalArgumentException(
			geoKnoten.identitaet.wert + " is no node of " +
				kante.identitaet.wert
		);
	}

	def static LineSegment getTangent(
		GEO_Kante geoKante,
		SegmentPosition position
	) {
		val ENUMGEOForm form = geoKante.GEOKanteAllg.GEOForm.wert
		switch (form) {
			case ENUMGEO_FORM_GERADE,
			case ENUMGEO_FORM_BLOSSKURVE,
			case ENUMGEO_FORM_KLOTHOIDE,
			case ENUMGEO_FORM_BLOSS_EINFACH_GESCHWUNGEN,
			case ENUMGEO_FORM_SONSTIGE,
			case ENUMGEO_FORM_RICHTGERADE_KNICK_AM_ENDE_200_GON,
			case ENUMGEO_FORM_KM_SPRUNG:
				return position.directedLineSegment.element
			case ENUMGEO_FORM_BOGEN: {
				val double radius = geoKante.GEOKanteAllg.GEORadiusA.wert.
					doubleValue
				val LineSegment tangent = new Circle(
					new Chord(
						geoKante.geoKnotenA.coordinate,
						geoKante.geoKnotenB.coordinate,
						Math.abs(radius),
						if (radius < 0)
							Chord.Orientation.ARC_RIGHT
						else
							Chord.Orientation.ARC_LEFT
					)
				).getTangent(position.coordinate)
				if (radius > 0 && position.directedLineSegment.forwards) {
					Geometries.turn(tangent, 180)
				}
				if (radius < 0 && !position.directedLineSegment.forwards) {
					Geometries.turn(tangent, 180)
				}
				return tangent
			}
			default: {
				logger.warn("Form {} not supported.", form.getName());
				return position.directedLineSegment.element
			}
		}
	}

	static val geoFactory = new GeometryFactory()

	private def static GeometryFactory getGeometryFactory() {
		return geoFactory
	}

	/**
	 * @param kante this GEO Kante
	 * 
	 * @returns the geometry of this GEO Kante
	 */
	def static LineString getGeometry(GEO_Kante kante) {
		val ENUMGEOForm form = kante.GEOKanteAllg.GEOForm.wert
		switch (form) {
			case ENUMGEO_FORM_GERADE,
			case ENUMGEO_FORM_SONSTIGE,
			case ENUMGEO_FORM_RICHTGERADE_KNICK_AM_ENDE_200_GON,
			case ENUMGEO_FORM_KM_SPRUNG:
				return getGeometryFactory().createLineString(kante.coordinates)
			case ENUMGEO_FORM_BOGEN:
				return kante.arc
			case ENUMGEO_FORM_KLOTHOIDE:
				return kante.clothoid
			case ENUMGEO_FORM_BLOSSKURVE,
			case ENUMGEO_FORM_BLOSS_EINFACH_GESCHWUNGEN:
				return kante.blosscurve
			default: {
				logger.warn("Form {} not supported.", form.getName())
				return getGeometryFactory().createLineString(kante.coordinates)
			}
		}
	}

	/**
	 * @param kante this directed GEO Kante
	 * 
	 * @return the line string of this GEO Kante (with the implied direction of the GEO Kante)
	 */
	def static LineString getGeometry(
		DirectedElement<GEO_Kante> directedEdge
	) {
		val geometry = directedEdge.element.geometry

		if (directedEdge.isForwards) {
			return geometry
		} else {
			return geometry.reverse
		}
	}

	/**
	 * @param edge this GEO Kante
	 * @param coordinate a coordinate
	 * @param tolerance the tolerance to test with
	 * 
	 * @return the first line segment with the given coordinate or {@code null}, if the distance
	 * of the coordinate to any line segment is greater that the given tolerance
	 */
	def static LineSegment getSegmentWith(
		GEO_Kante edge,
		Coordinate coordinate,
		double tolerance
	) {
		try {
			return edge.geometry.segments.getSegmentWith(coordinate, tolerance)
		} catch (GeometryException e) {
			throw new RuntimeException(e)
		}
	}

	/**
	 * @param edge this directed GEO Kante
	 * @param coordinate a coordinate
	 * @param tolerance the tolerance to test with
	 * 
	 * @return the first line segment (in the implied direction) with the given
	 * coordinate or {@code null}, if the distance of the coordinate to any
	 * line segment is greater that the given tolerance
	 */
	def static LineSegment getSegmentWith(
		DirectedElement<GEO_Kante> directedEdge,
		Coordinate coordinate,
		double tolerance
	) {
		try {
			return directedEdge.geometry.segments.getSegmentWith(coordinate,
				tolerance)
		} catch (GeometryException e) {
			throw new RuntimeException(e)
		}
	}

	private def static LineSegment getSegmentWith(
		List<LineSegment> segments,
		Coordinate coordinate,
		double tolerance
	) {
		for (segment : segments) {
			val result = new PointPairDistance
			DistanceToPoint.computeDistance(
				segment,
				coordinate,
				result
			)
			if (result.distance <= tolerance) {
				return segment
			}
		}

		return null
	}

	/**
	 * @param geoKante the GEO_Kante
	 * @return an iterable of all coordinates of the GEO_Kante with 
	 * 			their respective distances from the start of the GEO_Kante
	 */
	def static Iterable<Pair<Coordinate, Double>> getCoordinatesWithDistances(
		GEO_Kante geoKante
	) {
		val result = new ArrayList<Pair<Coordinate, Double>>
		val coordinates = geoKante.geometry.coordinates

		// Add the first coordinate
		var lastCoordinate = coordinates.head
		var double distance = 0
		result.add(lastCoordinate -> distance)

		for (coordinate : coordinates.tail) {
			// Add the segment length	
			distance += lastCoordinate.distance(coordinate)
			// Add the end coordinate of the segment
			lastCoordinate = coordinate
			result.add(lastCoordinate -> distance)
		}

		return result
	}
	
	def static Double getRadiusAtKnoten(GEO_Kante geoKante,
		GEO_Knoten geoKnoten) {
		switch (geoKante?.GEOKanteAllg?.GEOForm?.wert) {
			case ENUMGEO_FORM_GERADE:
				return 0.0
			case ENUMGEO_FORM_BOGEN,
			case ENUMGEO_FORM_RICHTGERADE_KNICK_AM_ENDE_200_GON:
				return (geoKante?.GEOKanteAllg?.GEORadiusA?.wert ?: 0).
					doubleValue
			default: {
				if (geoKnoten === geoKante.geoKnotenA) {
					return (geoKante?.GEOKanteAllg?.GEORadiusA?.wert ?: 0).doubleValue
				}
		
				if (geoKnoten === geoKante.geoKnotenB) {
					return (geoKante?.GEOKanteAllg?.GEORadiusB?.wert ?: 0).doubleValue
				}
				
				throw new IllegalArgumentException('''GEOKnoten: «geoKnoten.identitaet.wert» gehört nicht zu GEOKanten: «geoKante.identitaet.wert»''')
			}
				
		}
	}
}
