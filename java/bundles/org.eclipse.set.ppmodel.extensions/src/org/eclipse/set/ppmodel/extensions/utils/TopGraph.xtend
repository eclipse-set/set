/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import java.math.BigDecimal
import java.util.Comparator
import java.util.Set
import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.graph.AbstractRouting
import org.eclipse.set.basis.graph.Digraph
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.ppmodel.extensions.TopKanteExtensions

import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*

/**
 * Implementation of {@link Digraph} for PlanPro topology.
 * 
 * @author Schaefer
 */
class TopGraph extends AbstractRouting<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> implements Digraph<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> {

	val Set<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> edges

	new(Iterable<TOP_Kante> topKanten) {
		super()
		edges = topKanten.map[#{transform(true), transform(false)}].flatten.
			toSet
	}

	private def DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> create new DirectedTopKante
	transform(TOP_Kante topKante, boolean isForwards) {
		element = topKante
		forwards = isForwards
		return
	}

	override getEdges() {
		return edges
	}

	override getNodes() {
		return edges.map[#{tail, head}].flatten.toSet
	}

	override getDirectPredecessors(TOP_Knoten node) {
		return nodes.filter[n|edges.exists[tail == n && head == node]].toSet
	}

	override getDirectSuccessors(TOP_Knoten node) {
		return nodes.filter[n|edges.exists[tail == node && head == n]].toSet
	}

	override getEdges(TOP_Knoten node) {
		return edgesOfNode.computeIfAbsent(node, [topKanten.toSet])
	}

	override getHeadEdge(TOP_Knoten head, TOP_Kante edge) {
		Assert.isTrue(edge.TOPKnoten.contains(head))
		return new DirectedTopKante(edge, edge.TOPKnotenB == head)
	}

	override getTailEdge(TOP_Knoten tail, TOP_Kante edge) {
		Assert.isTrue(edge.TOPKnoten.contains(tail))
		return new DirectedTopKante(edge, edge.TOPKnotenA == tail)
	}

	override isRoute(TOP_Kante origin, TOP_Kante destination,
		TOP_Knoten transition) {
		return TopKanteExtensions.isRoute(origin, destination, transition)
	}

	override getDistanceComparator() {
		return new Comparator<BigDecimal>() {
			override compare(BigDecimal d1, BigDecimal d2) {
				return new Distance().compare(d1, d2)
			}
		}
	}

	override getEmptyPath() {
		return new TopKantePath
	}

	override getCacheKey() {
		return edges.toList.map[getCacheKey].join("/")
	}

}
