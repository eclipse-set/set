/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.Collections
import java.util.Comparator
import org.eclipse.set.basis.graph.Digraph
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.Routing
import org.eclipse.set.ppmodel.extensions.utils.Distance
import org.eclipse.set.ppmodel.extensions.utils.TopKantePath
import org.eclipse.set.ppmodel.extensions.utils.TopRouting
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehung


/**
 * Routing of the bank
 * @author Schaefer
 */
class BankRouting implements Routing<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> {

		val Digraph<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> graph
		val Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
		val TopRouting topRouting

		new(
			Digraph<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> graph,
			Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
		) {
			this.graph = graph
			this.singlePoint = singlePoint
			this.topRouting = new TopRouting
		}

		override getDirectPredecessors(
			DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> directedEdge
		) {
			if (directedEdge.bankingFound(false)) {
				return Collections.emptySet
			}
			return topRouting.getDirectPredecessors(directedEdge)
		}

		override getDirectSuccessors(
			DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> directedEdge
		) {
			if (directedEdge.bankingFound(true)) {
				return Collections.emptySet
			}
			return topRouting.getDirectSuccessors(directedEdge)
		}

		override getDistanceComparator() {
			return new Comparator<Double>() {
				override compare(Double d1, Double d2) {
					return Distance.compare(d1, d2)
				}
			}
		}

		override getEdge(TOP_Knoten tail, TOP_Knoten head) {
			return graph.getEdge(tail, head)
		}

		override getEdges(TOP_Knoten tail, TOP_Knoten head) {
			return graph.getEdges(tail, head)
		}

		override getEmptyPath() {
			return new TopKantePath
		}

		private def boolean bankingFound(
			DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> directedEdge,
			boolean afterPunkt) {
			val singlePoints = directedEdge.iterator.toList
			val index = singlePoints.indexOf(singlePoint)
			if (index < 0) {
				return !singlePoints.filter(Ueberhoehung).empty
			}
			if (afterPunkt) {
				return !singlePoints.subList(index, singlePoints.size).filter(
					Ueberhoehung).empty
			}
			return !singlePoints.subList(0, index).filter(Ueberhoehung).empty
		}

		override getCacheKey() {
			return '''«graph.cacheKey»/singlePoint.cacheKey'''
		}

	}