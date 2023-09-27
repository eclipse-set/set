/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.extensions

import org.eclipse.set.model.siteplan.Coordinate
import org.eclipse.set.model.siteplan.SiteplanFactory

/**
 * Extensions for the Coordinate type
 * 
 * @author Stuecker
 */
class CoordinateExtensions {

	/**
	 * Returns the magnitude of the vectors defined by the coordinate
	 * 
	 * @param coordinate the coordinate vector 
	 * @returns the magnitude of the vector
	 */
	static def double magnitude(Coordinate coordinate) {
		return Math.sqrt(coordinate.x * coordinate.x + coordinate.y * coordinate.y)
	}

	/**
	 * Returns the dot product of the two vectors defined by the coordinates
	 * 
	 * @param coordinate the first coordinate vector 
	 * @param coordinate the second coordinate vector
	 * @returns the dot product of the two vectors
	 */
	static def double dot(Coordinate coordinate, Coordinate coordinate2) {
		return coordinate.x * coordinate2.x + coordinate.y * coordinate2.y
	}

	/**
	 * Subtracts two coordinates from each other
	 * 
	 * @param coordinate the first coordinate
	 * @param coordinate2 the coordinate to subtract
	 * @returns a new coordinate at a subtracted point
	 */
	static def Coordinate operator_minus(Coordinate coordinate, Coordinate coordinate2) {
		val result = SiteplanFactory.eINSTANCE.createCoordinate
		result.x = coordinate.x - coordinate2.x
		result.y = coordinate.y - coordinate2.y
		return result
	}

	/**
	 * Adds two coordinates to each other
	 * 
	 * @param coordinate the first coordinate
	 * @param coordinate2 the coordinate to add
	 * @returns a new coordinate at an added point
	 */
	static def Coordinate operator_plus(Coordinate coordinate, Coordinate coordinate2) {
		val result = SiteplanFactory.eINSTANCE.createCoordinate
		result.x = coordinate.x + coordinate2.x
		result.y = coordinate.y + coordinate2.y
		return result
	}

	static def Coordinate operator_multiply(Coordinate coordinate, double d) {
		val result = SiteplanFactory.eINSTANCE.createCoordinate
		result.x = coordinate.x * d
		result.y = coordinate.y * d
		return result
	}

	/**
	 * Normalizes a coordinate vector
	 * 
	 * @param coordinate the coordinate vector to normalize
	 * @returns a new normalized coordinate 
	 */
	static def Coordinate normalize(Coordinate coordinate) {
		val result = SiteplanFactory.eINSTANCE.createCoordinate
		val magnitude = coordinate.magnitude
		result.x = coordinate.x / magnitude
		result.y = coordinate.y / magnitude
		return result
	}

}
