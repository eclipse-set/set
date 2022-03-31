/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.locationtech.jts.geom.Coordinate

/**
 * Extensions for Geotools Coordinates
 * 
 * @author Stuecker
 */
class CoordinateExtensions {
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
	def static Coordinate rotateAroundOrigin(Coordinate coordinate, double angle) {
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
}
