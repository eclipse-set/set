/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.internal

import java.util.Comparator
import java.util.Iterator
import java.util.List
import java.util.concurrent.ExecutionException
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.ppmodel.extensions.utils.Distance

import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*

/**
 * An iterator for single points of a TOP Kante.
 */
class SinglePointIterator implements Iterator<Punkt_Objekt_TOP_Kante_AttributeGroup> {


	private static enum Direction {
		AB,
		BA
	}

	private static class SinglePointComparator implements Comparator<Punkt_Objekt_TOP_Kante_AttributeGroup> {

		val DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge

		new(
			DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge
		) {
			this.edge = edge
		}

		override compare(Punkt_Objekt_TOP_Kante_AttributeGroup o1,
			Punkt_Objekt_TOP_Kante_AttributeGroup o2) {
			val d1 = edge.distanceFromTail(o1)
			val d2 = edge.distanceFromTail(o2)

			return new Distance().compare(d1, d2)
		}
	}

	val Iterator<Punkt_Objekt_TOP_Kante_AttributeGroup> internalIterator

	/**
	 * @param edge the directed edge
	 * @param start the start TOP Knoten
	 */
	new(
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge
	) {
		try {
			val cache = edge.element.getCache(
				ToolboxConstants.CacheId.DIRECTED_EDGE_TO_SINGLEPOINTS)
			val singlePoints = cache.get(
				edge.cacheKey,
				[edge.singlePoints]
			)
			internalIterator = singlePoints.iterator
		} catch (ExecutionException e) {
			throw new RuntimeException(e)
		}
	}

	private def List<Punkt_Objekt_TOP_Kante_AttributeGroup> singlePoints(
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge
	) {
		return edge.element.connected.sortWith(
			new SinglePointComparator(edge)
		)
	}

	override hasNext() {
		return internalIterator.hasNext
	}

	override next() {
		return internalIterator.next
	}

	override remove() {
		throw new UnsupportedOperationException
	}
}
