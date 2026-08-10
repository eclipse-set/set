/********************************************************************************
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 
 * 
 ********************************************************************************/
package org.eclipse.set.core.services.graph;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;

/**
 * @author Stuecker
 *
 */
public interface TopologicalGraphService {
	/**
	 * @param from
	 *            the source point
	 * @param to
	 *            the target point
	 * @param limit
	 *            maximum path distance to consider
	 * @return a list of all possible not-self intersecting paths between the
	 *         two points, include not complete path
	 */
	List<TopPath> findAllPathsBetween(final TopPoint from, final TopPoint to,
			int limit);

	/**
	 * @param from
	 *            the source point
	 * @param to
	 *            the target point
	 * @param limit
	 *            maximum path distance to consider
	 * @param condition
	 *            the condition
	 * @return the path, which satisfy the condition
	 */
	TopPath findPathBetween(final TopPoint from, final TopPoint to, int limit,
			Predicate<TopPath> condition);

	/**
	 * @param from
	 *            starting point to search from
	 * @param to
	 *            end point to search towards
	 * @param searchInTopDirection
	 *            whether to start the search in the top direction of 'from' or
	 *            against it
	 * @return the shortest distance between from and to, or empty if no path is
	 *         found
	 */
	Optional<BigDecimal> findShortestDistanceInDirection(final TopPoint from,
			final TopPoint to, final boolean searchInTopDirection);

	/**
	 * @param from
	 *            starting point to search from
	 * @param to
	 *            end point to search towards
	 * @return the shortest distance between from and to, or empty if no path is
	 *         found
	 */
	Optional<BigDecimal> findShortestDistance(final TopPoint from,
			final TopPoint to);

	/**
	 * @param from
	 *            starting point to search from
	 * @param to
	 *            end point to search towards
	 * @return the shortest distance between from and to, or empty if no path is
	 *         found
	 */
	Optional<TopPath> findShortestPath(final TopPoint from, final TopPoint to);

	/**
	 * @param from
	 *            starting point to search from
	 * @param to
	 *            end point to search toward
	 * @param inTopDirection
	 *            is in topological direction
	 * @return the optional of {@link TopPath} in direction of start point
	 */
	Optional<TopPath> findShortestPathInDirection(TopPoint from, TopPoint to,
			boolean inTopDirection);

	/**
	 * Finds the closest point of a set
	 * 
	 * @param from
	 *            starting point to search from
	 * @param points
	 *            list of points to search
	 * @param searchInTopDirection
	 *            whether to search in top direction from the starting point
	 * @return the closest point or empty if no path exists between any points
	 */
	Optional<TopPoint> findClosestPoint(final TopPoint from,
			final List<TopPoint> points, final boolean searchInTopDirection);
}
