/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Bereich_Objekt
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_Fahrweg
import de.scheidtbachmann.planpro.model.model1902.Geodaten.TOP_Kante
import org.eclipse.set.ppmodel.extensions.exception.AreaNotContinuous
import java.util.LinkedList
import java.util.List

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import org.eclipse.core.runtime.Assert

/**
 * A Teilfahrweg describes a continuous part of the Fahrweg starting and ending
 * at a Punkt Objekt. A Teilfahrweg may stretch across several
 * Bereich Objekt Teilbereiche. The Bereich Objekt Teilbereiche with the
 * <code>start</code> or <code>end</code> may or may not be completely
 * contained within the Teilfahrweg. All in-between Bereich Objekt Teilbereiche
 * are always contained by the Teilfahrweg.
 */
class TeilFahrweg {

	public Bereich_Objekt bereichObjekt

	public Punkt_Objekt start

	public Punkt_Objekt end

	int startPosition

	int endPosition

	double startAbstand

	double endAbstand

	List<TOP_Kante> sortedTopKanten

	/**
	 * @param fstrFahrweg the Fahrweg containing this Teilfahrweg
	 * @param start start of the Teilfahrweg
	 * @param end end of the Teilfahrweg
	 */
	new(
		Fstr_Fahrweg fstrFahrweg,
		Punkt_Objekt start,
		Punkt_Objekt end
	) {
		val topKanten = fstrFahrweg.bereichObjektTeilbereich.map[t|t.topKante]
		val startTopKante = fstrFahrweg.getTopKanteFor(start.singlePoints)
		val endTopKante = fstrFahrweg.getTopKanteFor(end.singlePoints)
		this.startAbstand = start.getAbstand(startTopKante)
		this.endAbstand = end.getAbstand(endTopKante)
		try {
			this.bereichObjekt = fstrFahrweg
			this.start = start
			this.end = end
			this.sortedTopKanten = sortTopKanten(
				topKanten,
				startTopKante,
				endTopKante
			)
			this.startPosition = getPosition(startTopKante)
			this.endPosition = getPosition(endTopKante)
			Assert.isTrue(startPosition > -1)
			Assert.isTrue(endPosition > -1)
			Assert.isTrue(startPosition <= endPosition)
		} catch (AreaNotContinuous e) {
			throw new AreaNotContinuous(startTopKante, endTopKante, topKanten)
		}
	}

	def double getAbstand(Punkt_Objekt punktObjekt, TOP_Kante topKante) {
		val singlePoints = punktObjekt.singlePoints.filter [ p |
			p.topKante == topKante
		].toList

		if (!singlePoints.empty) {
			// Punkt Objekt is on topKante
			Assert.isTrue(singlePoints.size == 1)
			val singlePoint = singlePoints.get(0)
			return singlePoint.abstand.wert.doubleValue
		}

		// Punkt Objekt is on adjacent topKante
		val adjacentKanten = punktObjekt.singlePoints.map[it.topKante].filter [
			isConnectedTo(topKante)
		].toList
		Assert.isTrue(!adjacentKanten.empty)
		val adjacentKante = adjacentKanten.get(0)

		val A = topKante.getTOPKnotenA
		val B = topKante.getTOPKnotenB
		val C = topKante.connectionTo(adjacentKante)

		if (A == C) {
			return 0.0
		}

		if (B == C) {
			return topKante.getTOPKanteAllg.getTOPLaenge.wert.doubleValue
		}

		throw new IllegalArgumentException
	}

	private def List<TOP_Kante> sortTopKanten(
		List<TOP_Kante> topKanten,
		TOP_Kante start,
		TOP_Kante end
	) {
		val result = new LinkedList

		val rest = topKanten.filter[t|t != start].toList
		Assert.isTrue(rest.size == topKanten.size - 1)

		if (start == end) {
			Assert.isTrue(rest.size == 0)
			result.add(start)
			return result
		}

		val connectedToStart = rest.filter[isConnectedTo(start)].toList

		if (connectedToStart.empty) {
			throw new AreaNotContinuous
		}

		Assert.isTrue(connectedToStart.size == 1)
		val newStart = connectedToStart.get(0)
		result.add(start)
		result.addAll(sortTopKanten(
			rest,
			newStart,
			end
		))
		return result
	}

	private def int getPosition(TOP_Kante topKante) {
		val size = sortedTopKanten.size
		for (var int i = 0; i < size; i++) {
			if (sortedTopKanten.get(i) == topKante) {
				return i
			}
		}
		return -1
	}

	/**
	 * @param object the Bereich Objekt
	 * 
	 * @result the length of the intersection of this Teilfahrweg and the given
	 * Bereich Objekt in m
	 */
	def double intersectionLength(Bereich_Objekt object) {
		val result = object.bereichObjektTeilbereich.fold(
			0.0,
			[result, p|result + this.intersectionLength(p)]
		)
		return result
	}

	def double intersectionLength(
		Bereich_Objekt_Teilbereich_AttributeGroup portion) {
		val topKante = portion.topKante
		val portionPosition = getPosition(topKante)

		if (portionPosition > startPosition && portionPosition < endPosition) {
			return portion.length.doubleValue
		}

		if (portionPosition == startPosition &&
			portionPosition == endPosition) {
			return intersectionLengthOnSingleEdge(portion)
		}

		if (portionPosition == startPosition) {
			return intersectionLengthOnStartEdge(portion)
		}

		if (portionPosition == endPosition) {
			return intersectionLengthOnEndEdge(portion)
		}

		return 0.0
	}

	private def double intersectionLengthOnSingleEdge(
		Bereich_Objekt_Teilbereich_AttributeGroup portion
	) {
		val topKanteStart = sortedTopKanten.get(startPosition)
		val topKanteEnd = sortedTopKanten.get(endPosition)
		val topKantePortion = portion.topKante

		Assert.isTrue(topKanteStart == topKanteEnd)
		Assert.isTrue(topKanteStart == topKantePortion)

		val begrenzungA = portion.begrenzungA.wert.doubleValue
		val begrenzungB = portion.begrenzungB.wert.doubleValue

		Assert.isTrue(begrenzungA <= begrenzungB)

		if (startAbstand <= endAbstand) {
			return intersectionLength(startAbstand, endAbstand, begrenzungA,
				begrenzungB)
		}
		return intersectionLength(endAbstand, startAbstand, begrenzungA,
			begrenzungB)
	}

	private def double intersectionLength(double a1, double a2, double b1,
		double b2) {
		Assert.isTrue(a1 <= a2)
		Assert.isTrue(b1 <= b2)
		if (b1 <= a1) {
			if (b2 <= a1) {
				return 0.0
			}
			if (b2 <= a2) {
				return b2 - a1
			}
			return a2 - a1
		}
		if (b1 <= a2) {
			if (b2 <= a2) {
				return b2 - b1
			}
			return a2 - b1
		}
		return 0.0
	}

	private def double intersectionLengthOnStartEdge(
		Bereich_Objekt_Teilbereich_AttributeGroup portion
	) {
		val startTopKante = sortedTopKanten.get(startPosition)
		val adjacentTopKante = sortedTopKanten.get(startPosition + 1)
		val A = startTopKante.getTOPKnotenA
		val B = startTopKante.getTOPKnotenB
		val C = startTopKante.connectionTo(adjacentTopKante)

		Assert.isNotNull(C)

		val portionA = portion.begrenzungA.wert.doubleValue
		val portionB = portion.begrenzungB.wert.doubleValue

		Assert.isTrue(portionA <= portionB)

		// the section greater than startAbstand belongs to the Teilfahrweg
		if (B == C) {
			return intersectionLengthRight(startAbstand, portionA, portionB)
		}

		// the section lesser than startAbstand belongs to the segment
		if (A == C) {
			return intersectionLengthLeft(startAbstand, portionA, portionB)
		}

		return 0.0
	}

	private def double intersectionLengthOnEndEdge(
		Bereich_Objekt_Teilbereich_AttributeGroup portion
	) {
		val endTopKante = sortedTopKanten.get(endPosition)
		val adjacentTopKante = sortedTopKanten.get(endPosition - 1)
		val A = endTopKante.getTOPKnotenA
		val B = endTopKante.getTOPKnotenB
		val C = endTopKante.connectionTo(adjacentTopKante)

		Assert.isNotNull(C)

		val portionA = portion.begrenzungA.wert.doubleValue
		val portionB = portion.begrenzungB.wert.doubleValue

		Assert.isTrue(portionA <= portionB)

		// the section greater than endAbstand belongs to the Teilfahrweg
		if (B == C) {
			return intersectionLengthRight(endAbstand, portionA, portionB)
		}

		// the section lesser than endAbstand belongs to the segment
		if (A == C) {
			return intersectionLengthLeft(endAbstand, portionA, portionB)
		}

		return 0.0
	}

	private def double intersectionLengthRight(double p, double a, double b) {
		if (p <= a) {
			return b - a
		}
		if (p <= b) {
			return b - p
		}
		return 0.0
	}

	private def double intersectionLengthLeft(double p, double a, double b) {
		if (b <= p) {
			return b - a
		}
		if (a <= p) {
			return p - a
		}
		return 0.0
	}
}
