/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Geodaten.GEO_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle
import java.util.List
import org.eclipse.set.basis.graph.DirectedElement
import org.eclipse.set.ppmodel.extensions.utils.DirectedTopKante

import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*

/**
 * Debugging Extensions for {@link PlanPro_Schnittstelle}.
 * 
 * @author Schaefer
 */
class PlanProSchnittstelleDebugExtensions {

	private static class Statistics {
		int total = 0
		int topCoverfailed = 0
	}

	/**
	 * Print validation information of TOP-Kanten to stdout.
	 * 
	 * @param planning the planning to validate
	 */
	static def void validateTopKanten(PlanPro_Schnittstelle planning) {
		val statistics = new Statistics
		planning.eAllContents.filter(TOP_Kante).forEach[validate(statistics)]

		println
		println('''Total edges = «statistics.total» failed to cover TOP-Kante = «statistics.topCoverfailed»''')
	}

	private static def void validate(TOP_Kante edge, Statistics statistics) {
		statistics.total++
		println
		println('''Analysis of TOP_Kante with identitaet=«edge?.identitaet?.wert»''')
		val topKnotenA = edge?.TOPKnotenA
		val topKnotenB = edge?.TOPKnotenB
		val geoKnotenA = topKnotenA?.GEOKnoten
		val geoKnotenB = topKnotenB?.GEOKnoten
		println('''TOP-Knoten A=«topKnotenA?.identitaet?.wert» GEO-Knoten A=«geoKnotenA?.identitaet?.wert»''')
		println('''TOP-Knoten B=«topKnotenB?.identitaet?.wert» GEO-Knoten B=«geoKnotenB?.identitaet?.wert»''')
		val directedTopEdge = new DirectedTopKante(edge, true)
		val directedGeoKanten = directedTopEdge.geoKanten
		directedGeoKanten.forEach[it.println]
		if (doesCover(directedTopEdge, directedGeoKanten)) {
			println("GEO-Kanten cover TOP-Kante.")
		} else {
			println("Error: GEO-Kanten do not cover TOP-Kante!")
			statistics.topCoverfailed++
		}
	}

	private static def void println(DirectedElement<GEO_Kante> edge) {
		println('''Directed GEO-Kante «edge?.element?.identitaet?.wert» [«edge?.tail?.identitaet?.wert», «edge?.head?.identitaet?.wert»]''')
	}

	private static def boolean doesCover(
		DirectedTopKante topEdge,
		List<DirectedElement<GEO_Kante>> geoEdges
	) {
		if (geoEdges.empty) {
			return false
		}
		val topFirst = topEdge.tail.GEOKnoten
		val topLast = topEdge.head.GEOKnoten
		val geoFirst = geoEdges.head.tail
		val geoLast = geoEdges.last.head

		return topFirst === geoFirst && topLast === geoLast
	}
}
