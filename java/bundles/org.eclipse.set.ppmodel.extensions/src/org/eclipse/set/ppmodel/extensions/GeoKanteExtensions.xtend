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
import java.util.ArrayList
import java.util.List
import org.eclipse.set.basis.geometry.Chord
import org.eclipse.set.basis.geometry.Circle
import org.eclipse.set.basis.geometry.GeoPosition
import org.eclipse.set.basis.geometry.Geometries
import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.basis.geometry.SegmentPosition
import org.eclipse.set.basis.graph.DirectedElement
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOForm
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.locationtech.jts.algorithm.distance.DistanceToPoint
import org.locationtech.jts.algorithm.distance.PointPairDistance
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LineSegment
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.LineStringExtensions.*
import org.eclipse.set.basis.constants.ToolboxConstants
import java.math.RoundingMode

/**
 * This class extends {@link GEO_Kante}.
 * 
 * @author Schaefer
 */
class GeoKanteExtensions extends BasisObjektExtensions {
	static final Logger logger = LoggerFactory.getLogger(
		typeof(GeoKanteExtensions));


	def static GeoPosition getCoordinate(
		GEO_Kante geoKante,
		GEO_Knoten startGeoKnoten,
		BigDecimal abstand,
		BigDecimal seitlicherAbstand,
		ENUMWirkrichtung wirkrichtung
	) {
		val geometry = geoKante.getGeometry
		// If the geometry size is smaller than the GEO_Laenge of the GEO_Kante
		// adjust the distance to fit within the GEO_Kante
		val edgeLength =
			geoKante.GEOKanteAllg?.GEOLaenge?.wert?.abs
		var distance = abstand
		val geoLength = BigDecimal.valueOf(geometry.length)
		if (geoLength < edgeLength) {
			distance = abstand * (geoLength.divide(edgeLength, ToolboxConstants.ROUNDING_TO_PLACE, RoundingMode.HALF_UP))
		}
		val SegmentPosition position = Geometries.getSegmentPosition(
			geometry, startGeoKnoten.coordinate, distance)
		val LineSegment tangent = geoKante.getTangent(position)

		return getCoordinate(tangent, position, seitlicherAbstand,
			wirkrichtung);
	}

	def static GeoPosition getCoordinate(
		LineSegment tangent,
		SegmentPosition position,
		BigDecimal seitlicherAbstand,
		ENUMWirkrichtung wirkrichtung
	) {
		val LineSegment lateralSegement = Geometries.clone(tangent)
		Geometries.translate(lateralSegement, lateralSegement.p0,
			position.getCoordinate())
			
				
			
		var double angle
		if (seitlicherAbstand.doubleValue < 0) {
			angle = 90
		} else {
			angle = -90
		}
		Geometries.turn(lateralSegement, angle)
		Geometries.scale(lateralSegement, seitlicherAbstand.abs.doubleValue)

		// direction
		val DirectedElement<LineSegment> directedLineSegment = position.
			directedLineSegment
		val LineSegment direction = Geometries.clone(
			directedLineSegment.getElement)
		var double rotation = Geometries.getRotationToVertical(direction);

		return new GeoPosition(lateralSegement.p1, rotation, wirkrichtung)
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
		return kante.IDGEOKnotenA?.value
	}

	/**
	 * @param kante this GEO Kante
	 * 
	 * @returns the GEO-Knoten B
	 */
	def static GEO_Knoten getGeoKnotenB(
		GEO_Kante kante
	) {
		return kante.IDGEOKnotenB?.value
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
		val result = kante.parentKante

		if (result instanceof TOP_Kante) {
			return result
		}
		return null
	}

	/**
	 * @return the TOP_Kante or Strecke, which contains this GEO_Kante
	 */
	def static Basis_Objekt getParentKante(
		GEO_Kante kante
	) {
		return kante.IDGEOArt?.value
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
			return getGeometry(edge).segments.getSegmentWith(coordinate, tolerance)
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
			return directedEdge.getGeometry.segments.getSegmentWith(coordinate,
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
		val coordinates = getGeometry(geoKante).coordinates
		geoKante.container
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
				
				throw new IllegalArgumentException('''GEOKnoten: «geoKnoten.identitaet.wert» doesn't belong to GEOKanten: «geoKante.identitaet.wert»''')
			}
		}
	}
}
