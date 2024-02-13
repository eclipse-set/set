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
	 *         two points
	 */
	List<TopPath> findAllPathsBetween(final TopPoint from, final TopPoint to,
			int limit);

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

}
