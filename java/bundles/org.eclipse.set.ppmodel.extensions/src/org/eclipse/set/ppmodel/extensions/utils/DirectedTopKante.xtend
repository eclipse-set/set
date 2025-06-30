/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.DirectedElementImpl
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.ppmodel.extensions.internal.SinglePointIterator

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*

/**
 * Implementation of {@link DirectedEdge} for TOP-Kanten with single points.
 * 
 * @author Schaefer
 */
class DirectedTopKante extends DirectedElementImpl<TOP_Kante> implements DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> {

	/**
	 * Create an invalid edge.
	 */
	new() {
		super(null, false)
	}

	/**
	 * @param topKante the TOP Kante
	 * @param isForward whether the direction is the same than the one of the
	 * TOP Kante (A->B)
	 */
	new(TOP_Kante topKante, boolean isForward) {
		super(topKante, isForward)
	}

	/**
	 * Create an edge with the implied direction from the start point to
	 * the end point.
	 * 
	 * @param topKante the TOP Kante
	 * @param start the start point
	 * @param end the end point
	 */
	new(TOP_Kante topKante, Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end) {
		super(topKante, isForwards(topKante, start, end))
	}

	/**
	 * Create an edge with the implied direction from the start node.
	 * 
	 * @param topKante the TOP Kante
	 * @param start the start node
	 */
	new(TOP_Kante topKante, TOP_Knoten start) {
		super(topKante, topKante.TOPKnotenA == start)
	}

	private static def boolean isForwards(TOP_Kante topKante,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end) {
		val abstandStart = topKante.getAbstand(start)
		val abstandEnd = topKante.getAbstand(end)
		return new Distance().compare(abstandStart, abstandEnd) <= 0
	}

	override contains(Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		return element.intersect(singlePoint)
	}

	override distance(Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint1,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint2) {
		return element.getAbstand(singlePoint1, singlePoint2)
	}

	override distanceFromTail(Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		return element.getAbstand(tail, singlePoint)
	}

	override distanceToHead(Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		return element.getAbstand(head, singlePoint)
	}

	override getHead() {
		if (forwards) {
			return element.TOPKnotenB
		}
		return element.TOPKnotenA
	}

	override getIterator() {
		return new SinglePointIterator(this)
	}

	override getLength() {
		return element.laenge
	}

	override getTail() {
		if (forwards) {
			return element.TOPKnotenA
		}
		return element.TOPKnotenB
	}
	
	override getCacheKey() {
		return '''«element.container.cacheString»/«element.identitaet.wert»/«forwards ? "/F" : "/B"»'''
	}
	
	override getPlanProSchnittstelle() {
		return element.planProSchnittstelle
	}
	
}
