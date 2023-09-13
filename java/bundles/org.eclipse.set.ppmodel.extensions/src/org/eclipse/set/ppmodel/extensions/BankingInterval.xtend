/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.math.BigDecimal
import java.util.List
import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.basis.graph.Digraph
import org.eclipse.set.basis.graph.Digraphs
import org.eclipse.set.basis.graph.DirectedEdgePath
import org.eclipse.set.core.services.Services
import org.eclipse.set.ppmodel.extensions.utils.DirectedTopKante
import org.eclipse.set.ppmodel.extensions.utils.ValueInterval
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehung
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehungslinie
import org.eclipse.set.utils.math.BigSlopeInterceptForm
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*
import static extension org.eclipse.set.utils.graph.DigraphExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*

/**
 * A banking interval consists of two (possibly identical) banking values, the length
 * of the interval and a distance from the left boundary to a signal.
 * 
 * @author Schaefer
 */
class BankingInterval extends ValueInterval<BigDecimal, BigDecimal> {
	val boolean isPoint
	val DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path
	Ueberhoehungslinie bankingLine

	static final Logger LOGGER = LoggerFactory. //
	getLogger(
		typeof(BankingInterval)
	);

	private new(
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
		Ueberhoehungslinie bankingLine
	) {
		super(path.left, path.right, path.getBankingLength(bankingLine),
			path.distance(path.start, singlePoint).toBigDecimal,
			path.distance(path.end, singlePoint).toBigDecimal)
		this.bankingLine = bankingLine
		this.path = path
		isPoint = false
	}

	private new(BigDecimal bankingValue) {
		super(bankingValue, bankingValue, BigDecimal.ZERO, BigDecimal.ZERO,
			BigDecimal.ZERO)
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

	private static def BigDecimal getBankingLength(
		DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> path,
		Ueberhoehungslinie bankingLine
	) {
		val length = bankingLine?.ueberhoehungslinieAllg?.
			ueberhoehungslinieLaenge?.wert
		if (length !== null) {
			return length
		}
		return BigDecimal.valueOf(path.length)
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
		val storedBankingIntervals = Services.cacheService.getCache(
			ToolboxConstants.CacheId.BANKING_INTERVAL)

		val foundIntervals = storedBankingIntervals.values.filter(
			BankingInterval).filter [
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
		val bankingLine = startBanking.getBankingLine(endBanking)
		val newInterval = new BankingInterval(interval, singlePoint,
			bankingLine)
		storedBankingIntervals.set(newInterval.cacheKey, newInterval)
		return newInterval
	}

	static def Ueberhoehungslinie getBankingLine(Ueberhoehung start,
		Ueberhoehung end) {
		val lines = start.container.ueberhoehungslinie.filter [
			IDUeberhoehungA === start && IDUeberhoehungB === end
		]
		if (lines.empty) {
			return null
		}

		return lines.get(0)
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
		val h_between = (h_start - h_end).abs
		var result = BigDecimal.ZERO
		switch (bankingLine?.ueberhoehungslinieAllg?.ueberhoehungslinieForm?.wert) {
			case ENUM_UEBERHOEHUNGSLINIE_FORM_RAMPE_BLOSS: {
				result = h_between.bankingOfRampeBloss
			}
			case ENUM_UEBERHOEHUNGSLINIE_FORM_RAMPE_S: {
				result = h_between.bankingOfRampeS
			}
			default: {
				result = h_between.bankingDefault
			}
		}
		return result.add(h_start)
	}
	
	/**
	 * BankingIntervall S-Form to Bloss
	 * When x <= L / 2, then:
	 * 		u = (3 * U / L^2) * x^2 - (2 * U / L^3) * x^3
	 * else:
	 * 		u = U / L * x
	 * with:
	 * 		U: the height between banking start and banking end
	 * 		L: the length of banking
	 * 		x: distance form start of banking to point
	 */
	private def BigDecimal bankingOfRampeBloss(BigDecimal h_between) {
		if (distanceFromLeft.compareTo(length.divideValue(2)) > 0) {
			return h_between.bankingDefault
		}
		val a = h_between
			.multiplyValue(3)
			.divide(length.pow(2))
			.multiply(distanceFromLeft.pow(2)) 
		val b = h_between
			.multiplyValue(2)
			.divide(length.pow(3))
			.multiply(distanceFromLeft.pow(3)) 
		return a.add(b.negate) 
	}
	
	/**
	 * BankingIntervall S-Form to Rampe
	 * 1. Case: when x =< L/2 => u = 2 * U * x^2 / L^2
	 * 2. Case: when x > L/2 => u = U - 2 * U * z^2 / L^2
	 * with:
	 * 		U: the height between banking start and banking end
	 * 		L: the length of banking
	 * 		x: distance form start of banking to point
	 * 		z: distance from end of banking to point
	 */
	private def BigDecimal bankingOfRampeS(BigDecimal h_between) {
		if (distanceFromLeft.compareTo(length.divideValue(2)) < 1) {
			return h_between
				.multiplyValue(2)
				.multiplyValue(distanceFromLeft.pow(2))
				.divide(length.pow(2))
		}
		return h_between.add(h_between
				.multiplyValue(2)
				.multiplyValue(distanceFromRight.pow(2))
				.divide(length.pow(2))
				.negate)
	}
	
	/**
	 * Default BankingIntervall
	 * 		u = U / L * x
	 * with:
	 * 		U: the height between banking start and banking end
	 * 		L: the length of banking
	 * 		x: distance form start of banking to point
	 */
	private def BigDecimal bankingDefault(BigDecimal h_between) {
		return h_between
				.multiplyValue(2)
				.multiplyValue(distanceFromLeft.pow(2))
				.divide(length.pow(2))
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
