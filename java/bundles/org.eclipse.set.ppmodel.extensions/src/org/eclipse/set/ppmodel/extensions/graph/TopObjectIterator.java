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
package org.eclipse.set.ppmodel.extensions.graph;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.ppmodel.extensions.BasisAttributExtensions;
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;

/**
 * Helper functions to iterate with objects on topological edges
 */
public class TopObjectIterator {

	/**
	 * Finds objects on a TOP_Kante in order of their location
	 * 
	 * @param <T>
	 *            type of object to look for
	 * @param edge
	 *            edge to search on
	 * @param followTopDirection
	 *            whether to search in the topological direction (from A to B)
	 *            or against it (from B to A)
	 * @param classType
	 *            a type hint to identify <T>
	 * @return a stream of objects
	 */
	public static <T extends Punkt_Objekt> Stream<T> getEdgeObjects(
			final TOP_Kante edge, final boolean followTopDirection,
			final Class<T> classType) {
		Comparator<Punkt_Objekt> comp = Comparator.comparing(po -> po
				.getPunktObjektTOPKante().get(0).getAbstand().getWert());
		if (!followTopDirection) {
			comp = comp.reversed();
		}
		return StreamSupport
				.stream(BasisAttributExtensions.getContainer(edge)
						.get(classType).spliterator(), false)
				.filter(c -> c.getPunktObjektTOPKante().get(0).getIDTOPKante()
						.equals(edge))
				.sorted(comp);
	}

	/**
	 * Finds objects on a TOP_Kante in order of their location from a given
	 * starting point
	 * 
	 * @param <T>
	 *            type of object to look for
	 * @param edge
	 *            edge to search on
	 * @param followTopDirection
	 *            whether to search in the topological direction (from A to B)
	 *            or against it (from B to A)
	 * @param startAt
	 *            the location from which to start from
	 * @param classType
	 *            a type hint to identify <T>
	 * @return a stream of objects
	 */
	public static <T extends Punkt_Objekt> Stream<T> getEdgeObjectsFromPoint(
			final TOP_Kante edge, final boolean followTopDirection,
			final BigDecimal startAt, final Class<T> classType) {
		Comparator<Punkt_Objekt> comp = Comparator.comparing(po -> po
				.getPunktObjektTOPKante().get(0).getAbstand().getWert());
		final int startAtDirection = followTopDirection ? 1 : -1;
		if (!followTopDirection) {
			comp = comp.reversed();
		}
		return StreamSupport
				.stream(BasisAttributExtensions.getContainer(edge)
						.get(classType).spliterator(), false)
				.filter(c -> c.getPunktObjektTOPKante().get(0).getIDTOPKante()
						.equals(edge))
				.filter(c -> {
					final int value = c.getPunktObjektTOPKante().get(0)
							.getAbstand().getWert().compareTo(startAt);
					return value == 0 || value == startAtDirection;
				}).sorted(comp);
	}

	/**
	 * Finds objects on a TopPath in order of their location
	 * 
	 * @param <T>
	 *            type of object to look for
	 * @param path
	 *            path to search on
	 * @param classType
	 *            a type hint to identify <T>
	 * @return a stream of objects
	 */
	public static <T extends Punkt_Objekt> Stream<T> getPathObjects(
			final TopPath path, final Class<T> classType) {
		if (path.edges().isEmpty()) {
			return Stream.of();
		}

		final List<Stream<T>> its = new ArrayList<>();

		TOP_Kante previousEdge = null;
		for (final TOP_Kante edge : path.edges()) {
			final boolean followTopDirection = previousEdge == null
					|| previousEdge.getIDTOPKnotenB() == edge.getIDTOPKnotenA();
			its.add(getEdgeObjects(edge, followTopDirection, classType));
			previousEdge = edge;
		}

		return its.stream().flatMap(s -> s);
	}

	/**
	 * Finds objects on a TopPath in order of their location with limits for the
	 * first and last edge
	 * 
	 * @param <T>
	 *            type of object to look for
	 * @param path
	 *            path to search on
	 * @param startLimit
	 *            the location from which to start from on the first edge
	 * @param endLimit
	 *            the location from which to end at for the last edge
	 * @param classType
	 *            a type hint to identify <T>
	 * @return a stream of objects
	 */
	public static <T extends Punkt_Objekt> Stream<T> getPathObjectsBetween(
			final TopPath path, final Class<T> classType,
			final BigDecimal startLimit, final BigDecimal endLimit) {
		if (path.edges().isEmpty()) {
			return Stream.of();
		}

		final TOP_Kante firstEdge = path.edges().get(0);
		final TOP_Kante lastEdge = path.edges().get(path.edges().size() - 1);

		return getPathObjects(path, classType).filter(c -> {
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk = c
					.getPunktObjektTOPKante().get(0);
			final TOP_Kante tk = potk.getIDTOPKante();
			if (tk == firstEdge
					&& potk.getAbstand().getWert().compareTo(startLimit) < 0) {
				return false;
			}
			if (tk == lastEdge
					&& potk.getAbstand().getWert().compareTo(endLimit) > 0) {
				return false;
			}
			return true;
		});
	}
}
