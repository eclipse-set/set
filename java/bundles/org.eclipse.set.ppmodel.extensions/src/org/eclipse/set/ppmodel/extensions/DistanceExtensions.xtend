/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Punkt
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.emf.ecore.EObject

import static org.eclipse.set.ppmodel.extensions.DistanceExtensions.MeasuringStrategy.*

import static extension org.eclipse.set.basis.graph.Digraphs.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoPunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UnterbringungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*

/**
 * Provides extensions for calculating distances.
 */
class DistanceExtensions {

	/**
	 * Measuring strategy for determining distances.
	 */
	enum MeasuringStrategy {
		NONE,
		USE_BEELINE_IF_ROUTING_FAILS
	}

	/**
	 * @param object1 the first object
	 * @param object2 the second object
	 * 
	 * @return the (minimal) distance of the objects in m without a measuring strategy
	 * 
	 * @throws IllegalArgumentException if the distance of the objects cannot
	 * be obtained
	 */
	def static double distance(EObject object1, EObject object2) {
		return distance(object1, object2, NONE)
	}

	/**
	 * @param object1 the first object
	 * @param object2 the second object
	 * @param strategy the measuring strategy
	 * 
	 * @return the (minimal) distance of the objects in m
	 * 
	 * @throws IllegalArgumentException if the distance of the objects cannot
	 * be obtained
	 */
	def static double distance(EObject object1, EObject object2,
		MeasuringStrategy strategy) {
		return distanceDispatch(object1, object2, strategy)
	}

	private def static dispatch double distanceDispatch(EObject object1,
		EObject object2, MeasuringStrategy strategy) {
		throw new IllegalArgumentException(
			'''Distance cannot be obtained for types «object1.class.name», «object2.class.name»'''
		)
	}

	private def static dispatch double distanceDispatch(Punkt_Objekt object1,
		Punkt_Objekt object2, MeasuringStrategy strategy) {
		val topGraph = new TopGraph(object1.container.TOPKante)
		val paths = topGraph.getPaths(object1.singlePoints,
			object2.singlePoints)
		if (paths.empty) {
			if (strategy == USE_BEELINE_IF_ROUTING_FAILS) {
				return distanceBeeline(object1, object2)
			}
			throw new IllegalArgumentException(
				'''No path from «object1.debugString» to «object2.debugString»'''
			)
		}
		return paths.fold(Double.MAX_VALUE, [d, p|Math.min(d, p.length)])
	}

	private def static dispatch double distanceDispatch(
		Punkt_Objekt punktObjekt,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
		MeasuringStrategy strategy
	) {
		val topGraph = new TopGraph(punktObjekt.container.TOPKante)
		val paths = topGraph.getPaths(punktObjekt.singlePoints, #{singlePoint});
		if (paths.empty) {
			if (strategy == USE_BEELINE_IF_ROUTING_FAILS) {
				return distanceBeeline(punktObjekt, singlePoint)
			}
			throw new IllegalArgumentException(
				'''No path from «punktObjekt.debugString» to «singlePoint.debugString»'''
			)
		}
		return paths.fold(Double.MAX_VALUE, [d, p|Math.min(d, p.length)])
	}

	private def static dispatch double distanceDispatch(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
		Punkt_Objekt punktObjekt,
		MeasuringStrategy strategy
	) {
		return distanceDispatch(punktObjekt, singlePoint, strategy)
	}

	private def static dispatch double distanceDispatch(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint1,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint2,
		MeasuringStrategy strategy
	) {
		val topGraph = new TopGraph(singlePoint1.punktObjekt.container.TOPKante)
		val paths = topGraph.getPaths(singlePoint1, singlePoint2)
		if (paths.empty) {
			if (strategy == USE_BEELINE_IF_ROUTING_FAILS) {
				return distanceBeeline(singlePoint1, singlePoint2)
			}
			throw new IllegalArgumentException(
				'''No path from «singlePoint1.debugString» to «singlePoint2.debugString»'''
			)
		}
		return paths.fold(Double.MAX_VALUE, [d, p|Math.min(d, p.length)])
	}

	private def static dispatch double distanceDispatch(
		Punkt_Objekt punktObjekt,
		Unterbringung unterbringung,
		MeasuringStrategy strategy
	) {
		if (unterbringung.punktObjektTOPKante !== null) {
			return distanceDispatch(punktObjekt,
				unterbringung.punktObjektTOPKante, strategy)
		} else {
			return distanceBeeline(punktObjekt, unterbringung.geoPunkt)
		}
	}

	private def static dispatch double distanceDispatch(
		Unterbringung unterbringung,
		Punkt_Objekt punktObjekt,
		MeasuringStrategy strategy
	) {
		return distanceDispatch(punktObjekt, unterbringung, strategy)
	}

	private def static dispatch double distanceDispatch(
		GEO_Punkt geoPunkt1,
		GEO_Punkt geoPunkt2,
		MeasuringStrategy strategy
	) {
		try {
			val c1 = geoPunkt1.coordinate
			val c2 = geoPunkt2.coordinate
			return c1.distance(c2)
		} catch (GeometryException e) {
			throw new IllegalArgumentException(e)
		}
	}

	private def static dispatch double distanceBeeline(
		EObject object1,
		EObject object2
	) {
		throw new IllegalArgumentException(
			'''Distance cannot be obtained for types «object1.class.name», «object2.class.name»'''
		)
	}

	private def static dispatch double distanceBeeline(
		Punkt_Objekt punktObjekt1,
		Punkt_Objekt punktObjekt2
	) {
		try {
			val c1 = punktObjekt1.coordinate
			val c2 = punktObjekt2.coordinate
			return c1.distance(c2)
		} catch (GeometryException e) {
			throw new IllegalArgumentException(e)
		}
	}

	private def static dispatch double distanceBeeline(
		Punkt_Objekt punktObjekt,
		GEO_Punkt geoPunkt
	) {
		try {
			val c1 = punktObjekt.coordinate
			val c2 = geoPunkt.coordinate
			return c1.distance(c2)
		} catch (GeometryException e) {
			throw new IllegalArgumentException(e)
		}
	}

	private def static dispatch double distanceBeeline(
		GEO_Punkt geoPunkt,
		Punkt_Objekt punktObjekt
	) {
		return distanceBeeline(punktObjekt, geoPunkt)
	}

	private def static dispatch double distanceBeeline(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint1,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint2
	) {
		try {
			val c1 = singlePoint1.coordinate.geometricInformation
			val c2 = singlePoint2.coordinate.geometricInformation
			return c1.distance(c2)
		} catch (GeometryException e) {
			throw new IllegalArgumentException(e)
		}
	}

	private def static dispatch double distanceBeeline(
		Punkt_Objekt punktObjekt,
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		try {
			val c1 = punktObjekt.coordinate
			val c2 = singlePoint.coordinate.geometricInformation
			return c1.distance(c2)
		} catch (GeometryException e) {
			throw new IllegalArgumentException(e)
		}
	}

	private def static dispatch double distanceBeeline(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
		Punkt_Objekt punktObjekt
	) {
		return distanceBeeline(punktObjekt, singlePoint)
	}
}
