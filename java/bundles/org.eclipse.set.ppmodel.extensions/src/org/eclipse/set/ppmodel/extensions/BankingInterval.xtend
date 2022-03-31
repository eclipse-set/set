/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.graph.Digraph
import org.eclipse.set.basis.graph.Digraphs
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.DirectedEdgePath
import org.eclipse.set.basis.graph.Routing
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Geodaten.TOP_Kante
import de.scheidtbachmann.planpro.model.model1902.Geodaten.TOP_Knoten
import de.scheidtbachmann.planpro.model.model1902.Geodaten.Ueberhoehung
import org.eclipse.set.ppmodel.extensions.utils.DirectedTopKante
import org.eclipse.set.ppmodel.extensions.utils.Distance
import org.eclipse.set.ppmodel.extensions.utils.TopKantePath
import org.eclipse.set.ppmodel.extensions.utils.TopRouting
import org.eclipse.set.ppmodel.extensions.utils.ValueInterval
import org.eclipse.set.core.services.Services
import org.eclipse.set.utils.math.BigSlopeInterceptForm
import java.math.BigDecimal
import java.util.Collections
import java.util.Comparator
import java.util.List
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*
import static extension org.eclipse.set.utils.graph.DigraphExtensions.*
import org.eclipse.set.basis.constants.ToolboxConstants

/**
 * A banking interval consists of two (possibly identical) banking values, the length
 * of the interval and a distance from the left boundary to a signal.
 * 
 * @author Schaefer
 */
class BankingInterval extends ValueInterval<BigDecimal, BigDecimal> {

	val boolean isPoint
	val DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path

	static final Logger LOGGER = LoggerFactory. //
	getLogger(
		typeof(BankingInterval)
	);

	private new(
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		super(path.left, path.right, BigDecimal.valueOf(path.length),
			path.distanceTo(singlePoint))
		this.path = path
		isPoint = false
	}

	private new(BigDecimal bankingValue) {
		super(bankingValue, bankingValue, BigDecimal.ZERO, BigDecimal.ZERO)
		path = null
		isPoint = true
	}
	
	/**
	 * @return the cache key for this banking interval
	 */
	def String getCacheKey() {
		return isPoint.toString + path.cacheKey
	}
	

	private static def BigDecimal getLeft(
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path
	) {
		val start = path.start.punktObjekt as Ueberhoehung
		return start.ueberhoehungAllg.ueberhoehungHoehe.wert
	}

	private static def BigDecimal getRight(
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path
	) {
		val end = path.end.punktObjekt as Ueberhoehung
		return end.ueberhoehungAllg.ueberhoehungHoehe.wert
	}

	private static def BigDecimal distanceTo(
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		return BigDecimal.valueOf(
			path.distance(path.start, singlePoint)
		)
	}

	private static class BankRouting implements Routing<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> {

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

	/**
	 * @param graph the graph
	 * @param singlePoint a single point
	 * @param digraph the digraph
	 * 
	 * @return the BankingInterval of the point; or {@code null}, if the
	 * point does not lie within a BankingInterval
	 */
	static def BankingInterval createInterval(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
		Digraph<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> digraph
	) {
		// test for bankings
		val noOfBankings = singlePoint.container.ueberhoehung.size
		if (noOfBankings === 0) {
			return null
		}

		// try to find a cached banking interval with the single point
		val storedBankingIntervals = Services.cacheService.getCache(ToolboxConstants.CacheId.BANKING_INTERVAL)
		
		val foundIntervals = storedBankingIntervals.values.filter(BankingInterval).filter [
			contains(singlePoint)
		]
		if (foundIntervals.size > 1) {
			throw new IllegalArgumentException(foundIntervals.size.toString)
		}
		if (foundIntervals.size === 1) {
			return foundIntervals.get(0)
		}

		// calculate a new interval
		val topKante = singlePoint.topKante

		val forward = new DirectedTopKante(topKante, true)
		val backward = new DirectedTopKante(topKante, false)
		val bankRouting = new BankRouting(digraph, singlePoint)

		val pathToStartBanking = Digraphs.getPaths(forward, bankRouting).sortBy [
			length
		].get(0)
		val startBanking = pathToStartBanking.getFirstBankingAfter(singlePoint)

		val pathToEndBanking = Digraphs.getPaths(backward, bankRouting).sortBy [
			length
		].get(0)
		val endBanking = pathToEndBanking.getFirstBankingAfter(singlePoint)

		if (startBanking === null && endBanking === null) {
			return null
		}

		if (startBanking === null) {
			return new BankingInterval(
				endBanking.ueberhoehungAllg.ueberhoehungHoehe.wert)
		}
		if (endBanking === null) {
			return new BankingInterval(
				startBanking.ueberhoehungAllg.ueberhoehungHoehe.wert)
		}

		val List<DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> intervals = digraph.
			getPaths(startBanking.singlePoints, endBanking.singlePoints).filter [
				pointIterator.toList.contains(singlePoint)
			].sortBy[length]

		if (intervals.empty) {
			LOGGER.
				error('''pathToStartBanking=«pathToStartBanking.debugString»''')
			LOGGER.error('''pathToEndBanking=«pathToEndBanking.debugString»''')
			LOGGER.
				error('''pathToStartBankingEdgeList=«FOR e : pathToStartBanking.edgeList»«e?.element?.identitaet?.wert»«ENDFOR»''')
			LOGGER.
				error('''pathToEndBankingEdgeList=«FOR e : pathToEndBanking.edgeList»«e?.element?.identitaet?.wert»«ENDFOR»''')
			LOGGER.
				error('''startBankingTopKanten=«FOR e : startBanking.topKanten»«e?.identitaet?.wert»«ENDFOR»''')
			LOGGER.
				error('''endBankingTopKanten=«FOR e : endBanking.topKanten»«e?.identitaet?.wert»«ENDFOR»''')

			throw new IllegalArgumentException(
				'''No path from «startBanking.debugString» to «endBanking.debugString» for «singlePoint.debugString».'''
			)
		}

		val interval = intervals.get(0)

		LOGGER.debug(
			'''Created Banking interval [«interval.start.debugString», «interval.end.debugString»].'''
		)
		val newInterval = new BankingInterval(interval, singlePoint)
		storedBankingIntervals.set(newInterval.cacheKey, newInterval)
		return newInterval
	}

	/**
	 * @param interval the banking interval
	 * @param punktObjekt the Punkt Objekt
	 */
	def BigDecimal getBanking() {
		if (isPoint) {
			return left
		}

		val h_start = left
		val h_end = right
		val length = length
		val slope = (h_end - h_start) / length

		val pointDistance = distance

		return new BigSlopeInterceptForm(slope, BigDecimal.ZERO).apply(
			pointDistance) + h_start
	}

	/**
	 * @param singlePoint a single point (of a signal)
	 * 
	 * @return whether this interval contains the given single point
	 */
	def boolean contains(Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		if (path === null) {
			return false
		}
		return path.pointIterator.exists[it === singlePoint]
	}

	private static def Ueberhoehung getFirstBankingAfter(
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {

		val singlePoints = path.pointIterator.toList
		val index = singlePoints.indexOf(singlePoint)
		Assert.isTrue(index >= 0)
		val pointsAfterPunkt = singlePoints.subList(index, singlePoints.size)
		Assert.isNotNull(pointsAfterPunkt)
		val bankings = pointsAfterPunkt.map[punktObjekt].filter(Ueberhoehung).
			toList
		if (!bankings.empty) {
			return bankings.get(0)
		}
		return null
	}
}
