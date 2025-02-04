/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.geometry

import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem
import org.locationtech.jts.geom.Coordinate
import org.locationtech.proj4j.CRSFactory
import org.locationtech.proj4j.CoordinateReferenceSystem
import org.locationtech.proj4j.CoordinateTransformFactory
import org.locationtech.proj4j.ProjCoordinate
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.GeometryFactory

/**
 * Extensions for Geotools Coordinates
 * 
 * @author Stuecker
 */
class CoordinateExtensions {
	public static String CRS_CR0_PARAMETER = "+proj=tmerc +lat_0=0 +lon_0=6  +k=1 +x_0=2500000 +y_0=0 +ellps=bessel +towgs84=598.1,73.7,418.2,0.202,0.045,-2.455,6.7 +units=m +no_defs"
	public static String CRS_DR0_PARAMETER = "+proj=tmerc +lat_0=0 +lon_0=9  +k=1 +x_0=3500000 +y_0=0 +ellps=bessel +towgs84=598.1,73.7,418.2,0.202,0.045,-2.455,6.7 +units=m +no_defs"
	public static String CRS_ERO_PARAMETER = "+proj=tmerc +lat_0=0 +lon_0=12 +k=1 +x_0=4500000 +y_0=0 +ellps=bessel +towgs84=598.1,73.7,418.2,0.202,0.045,-2.455,6.7 +units=m +no_defs"
	public static String CRS_FRO_PARAMETER = "+proj=tmerc +lat_0=0 +lon_0=15 +k=1 +x_0=5500000 +y_0=0 +ellps=bessel +towgs84=598.1,73.7,418.2,0.202,0.045,-2.455,6.7 +units=m +no_defs"

	/**
	 * Rotates a coordinate around another coordinate according to an angle in 2D
	 * 
	 * @param coordinate the coordinate to rotate
	 * @param angle the angle in radians to rotate by
	 * @param point the point to rotate around
	 * @return a new rotated point 
	 */
	def static Coordinate rotateAroundPoint(Coordinate coordinate, double angle,
		Coordinate point) {
		// Move coordinate so that point is at the origin
		val x = coordinate.x - point.x
		val y = coordinate.y - point.y

		val si = Math.sin(angle)
		val co = Math.cos(angle)

		// Rotate and move back to the original position	
		val newx = x * co - y * si + point.x
		val newy = x * si + y * co + point.y
		return new Coordinate(newx, newy, coordinate.getZ)
	}

	/**
	 * Rotates a coordinate around the origin point (0,0)
	 * 
	 * @param coordinate the coordinate to rotate
	 * @param angle the angle in radians to rotate by
	 * @return a rotated coordinate
	 */
	def static Coordinate rotateAroundOrigin(Coordinate coordinate,
		double angle) {
		return coordinate.rotateAroundPoint(angle, new Coordinate(0, 0, 0))
	}

	/**
	 * Adds a xy-offset to a coordinate coordinates. Does not affect Z-axis
	 * 
	 * @param coordinate the base coordinate
	 * @param x offset
	 * @param y offset
	 * @return a new coordinate offset by the defined amount
	 */
	def static Coordinate offsetBy(Coordinate coordinate, double xoffset,
		double yoffset) {
		return new Coordinate(coordinate.x + xoffset, coordinate.y + yoffset,
			coordinate.getZ)
	}

	/**
	 * Mirrors the Y-axis of the coordinate
	 * @param coordinate the coordinate
	 * @return a new coordinate with inverted x axis
	 */
	def static Coordinate mirrorY(Coordinate coordinate) {
		return new Coordinate(coordinate.x, -coordinate.y, coordinate.getZ)
	}

	/**
	 * Returns the angle from origin to coord
	 * @param origin the first coordinate
	 * @param coordinate the second coordinate
	 * @return the angle between the two coordinates
	 */
	def static double getAngleBetweenPoints(Coordinate origin,
		Coordinate coordinate) {
		val dx = origin.x - coordinate.x
		val dy = origin.y - coordinate.y
		return Math.atan2(dy, dx)
	}

	/**
	 * Transform coordinate into another CRS
	 * @param originCoor the coordinate to transform
	 * @param source the source CRS
	 * @param target the target CRS
	 * @return the coordinate in target CRS
	 */
	def static Coordinate transformCRS(Coordinate originCoor,
		ENUMGEOKoordinatensystem source, ENUMGEOKoordinatensystem target) {
		transformCRS(originCoor.x.doubleValue, originCoor.y.doubleValue, source,
			target)
	}

	/**
	 * Transform coordinate into another CRS
	 * @param x the x-coordinate to transform
	 * @param y the y-coordinate to transform
	 * @param source the source CRS
	 * @param target the target CRS
	 * @return the coordinate in target CRS
	 */
	def static Coordinate transformCRS(double x, double y,
		ENUMGEOKoordinatensystem source, ENUMGEOKoordinatensystem target) {
		val sourceCRS = source.createCRS
		val targetCRS = target.createCRS
		if (sourceCRS === null || targetCRS === null) {
			return new Coordinate(x, y)
		}
		val transformFactory = new CoordinateTransformFactory()
		val targetCoor = new ProjCoordinate()
		transformFactory.createTransform(sourceCRS, targetCRS).transform(
			new ProjCoordinate(x, y), targetCoor)
		return new Coordinate(targetCoor.x, targetCoor.y)

	}

	private def static CoordinateReferenceSystem createCRS(
		ENUMGEOKoordinatensystem crs) {
		val crsFactory = new CRSFactory()
		switch (crs) {
			case ENUMGEO_KOORDINATENSYSTEM_CR0:
				return crsFactory.createFromParameters(crs.literal,
					CRS_CR0_PARAMETER)
			case ENUMGEO_KOORDINATENSYSTEM_DR0:
				return crsFactory.createFromParameters(crs.literal,
					CRS_DR0_PARAMETER)
			case ENUMGEO_KOORDINATENSYSTEM_ER0:
				return crsFactory.createFromParameters(crs.literal,
					CRS_ERO_PARAMETER)
			case ENUMGEO_KOORDINATENSYSTEM_FR0:
				return crsFactory.createFromParameters(crs.literal,
					CRS_FRO_PARAMETER)
			default:
				return null
		}
	}
}
