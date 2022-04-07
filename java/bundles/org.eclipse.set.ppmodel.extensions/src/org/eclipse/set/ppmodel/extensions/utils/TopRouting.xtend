/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.graph.AbstractRouting
import org.eclipse.set.basis.graph.Routing
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.eclipse.set.ppmodel.extensions.TopKanteExtensions
import java.util.Comparator

import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*

/** 
 * Implementation of {@link Routing} for PlanPro TOP-Graph routing.
 * 
 * @author Schaefer
 */
class TopRouting extends AbstractRouting<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> {

	override getEdges(TOP_Knoten node) {
		return node.topKanten.toSet
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
		return new Comparator<Double>() {
			override compare(Double d1, Double d2) {
				return Distance.compare(d1, d2)
			}
		}
	}

	override getEmptyPath() {
		return new TopKantePath
	}
	
	override getCacheKey() {
		return "TopRouting"
	}
	
}
