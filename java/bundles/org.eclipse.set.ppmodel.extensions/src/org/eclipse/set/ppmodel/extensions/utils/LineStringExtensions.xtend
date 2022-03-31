/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import com.google.common.collect.Lists
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LineSegment
import org.locationtech.jts.geom.LineString
import java.util.List

/**
 * This class extends {@link LineString}.
 * 
 * @author Schaefer
 */
class LineStringExtensions {

	/**
	 * @param lineSting this line string
	 * 
	 * @return the list of all segments of this line string
	 */
	static def List<LineSegment> getSegments(LineString lineString) {
		val result = Lists.newArrayList
		val coordinates = lineString.coordinates.toList
		if (coordinates.empty) {
			return result
		}
		val first = coordinates.head
		return getSegments(first, coordinates.tail, result)
	}

	static private def List<LineSegment> getSegments(
		Coordinate from,
		Iterable<Coordinate> coordinates,
		List<LineSegment> result
	) {
		if (coordinates.empty) {
			return result
		}
		val to = coordinates.head
		result.add(new LineSegment(from, to))

		return getSegments(to, coordinates.tail, result)
	}
}
