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
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import java.util.Set
import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.graph.AbstractDirectedEdgePath
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.ppmodel.extensions.exception.EndOfTopPathNotFound

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*

/**
 * Implementation of directed edge path for TOP-Kanten.
 * 
 * @author Schaefer
 */
class TopKantePath extends AbstractDirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> {
	protected String cacheKey

	/**
	 * Constructs a path from a set of TOP-Kanten. There is no assumption about
	 * the order of the provided TOP-Kanten.
	 *  
	 * @param topKanten the set of TOP-Kanten this path is constructed of
	 * @param start the starting single point
	 * @param end the ending single point
	 */
	new(
		Bereich_Objekt bereich,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end
	) {
		this(bereich.topKanten, start, end)
		// test if all edges are consumed
		val mySet = edgeIterator.map[element].toSet
		
		if (!bereich.topKanten.equals(mySet)) {
			throw new IllegalArgumentException('''sort top edges failed, bereich=«bereich.debugString»''')
		}

		val cacheKeyPathEnds = #[start, end].map [
			#[abstand?.wert, IDTOPKante?.value?.identitaet?.wert,
				wirkrichtung?.wert, seitlicheLage?.wert,
				seitlicherAbstand?.wert]
		].flatten.join
		cacheKey = '''«bereich?.identitaet?.wert»/«cacheKeyPathEnds»'''
	}

	/**
	 * Constructs a path from a list of directed TOP-Kanten. The TOP-Kanten are
	 * assumed to be in the correct order.
	 *  
	 * @param topKanten the list of TOP-Kanten this path is constructed of
	 * @param start the starting single point
	 * @param end the ending single point
	 */
	new(
		Set<TOP_Kante> topKanten,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end
	) {
		super(sortEdges(topKanten, start, end), start, end)
	}

	/**
	 * Constructs a path from a list of directed TOP-Kanten. The TOP-Kanten are
	 * assumed to be in the correct order.
	 *  
	 * @param topKanten the list of TOP-Kanten this path is constructed of
	 * @param start the starting single point
	 * @param end the ending single point
	 */
	new(
		List<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> topKanten,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end
	) {
		super(topKanten, start, end)
	}
	
	/**
	 * Constructs an empty path.
	 */
	new() {
		super(null, null, null)
	}

	static def List<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> sortEdges(
		Set<TOP_Kante> topKanten,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end
	) {
		val topKantenCopy = new HashSet<TOP_Kante>
		topKantenCopy.addAll(
			topKanten
		)
		val result = new LinkedList<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>>
		val startKante = find(start, topKantenCopy)
		val removed = topKantenCopy.remove(startKante)
		if (!removed) {
			throw new IllegalArgumentException(
				"start object is not on top edge")
		}
		if (startKante.isConnectedTo(end)) {
			result.add(new DirectedTopKante(startKante, start, end))
			return result
		}
		if (topKanten.empty) {
			throw new EndOfTopPathNotFound(start, end, result)
		}
		return sortEdges(startKante, topKantenCopy, start, end, result)
	}

	private static def List<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> sortEdges(
		TOP_Kante current,
		Set<TOP_Kante> topKanten,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end,
		List<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> result
	) {
		val nextKante = find(current, topKanten)
		Assert.isTrue(topKanten.remove(nextKante.element))
		val connector = current.connectionTo(nextKante.element)
		val currentStart = current.opposite(connector)
		result.add(new DirectedTopKante(current, currentStart))
		result.add(nextKante)
		if (nextKante.element.isConnectedTo(end)) {
			return result
		}
		if (topKanten.empty) {
			throw new EndOfTopPathNotFound(start, end, result)
		}
		return sortEdges(nextKante, topKanten, start, end, result)
	}

	private static def List<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> sortEdges(
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> current,
		Set<TOP_Kante> topKanten,
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end,
		List<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> result
	) {
		val nextKante = find(current.element, topKanten)
		Assert.isTrue(topKanten.remove(nextKante.element))
		result.add(nextKante)
		if (nextKante.element.isConnectedTo(end)) {
			return result
		}
		if (topKanten.empty) {
			throw new EndOfTopPathNotFound(start, end, result)
		}
		return sortEdges(nextKante, topKanten, start, end, result)
	}

	private static def TOP_Kante find(
		Punkt_Objekt_TOP_Kante_AttributeGroup connection,
		Set<TOP_Kante> topKanten
	) {
		// Is the point directly located on a TOP_Kante?
		for (topKante : topKanten) {
			if (connection.IDTOPKante.value === topKante) {
				return topKante
			}
		}

		// Is the point located on the end of an adjacent TOP_Kante?
		for (topKante : topKanten) {
			if (topKante.isConnectedTo(connection)) {
				return topKante
			}
		}
		return null
	}

	private static def DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> find(
		TOP_Kante connection, Set<TOP_Kante> topKanten) {
		for (topKante : topKanten) {
			if (topKante.isConnectedTo(connection)) {
				val connector = topKante.connectionTo(connection)
				return new DirectedTopKante(topKante,
					topKante.TOPKnotenA == connector)
			}
		}
		return null
	}

	override copy() {
		return new TopKantePath(edgeList, start, end)
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
		return cacheKey
	}
}
