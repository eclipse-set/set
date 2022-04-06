/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.math.BigDecimal
import org.eclipse.set.core.services.Services
import org.eclipse.set.core.services.cache.NoCacheService
import org.eclipse.set.ppmodel.extensions.utils.CrossingRoute
import org.eclipse.set.toolboxmodel.Basisobjekte.Abstand_TypeClass
import org.eclipse.set.toolboxmodel.Basisobjekte.BasisobjekteFactory
import org.eclipse.set.toolboxmodel.Basisobjekte.Identitaet_TypeClass
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.Fahrstrasse.FahrstrasseFactory
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.toolboxmodel.Geodaten.ENUMGEOForm
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Kante
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Knoten
import org.eclipse.set.toolboxmodel.Geodaten.GeodatenFactory
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante_Allg_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Laenge_TypeClass
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup
import org.eclipse.set.toolboxmodel.PlanPro.LST_Zustand
import org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Signale.SignaleFactory
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.Kreuzung_AttributeGroup
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.Weichen_und_GleissperrenFactory
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.LineSegment

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.is

import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*

/** 
 * Tests for {@link FahrwegExtensions} with crossing test data.
 * @author Schaefer
 */
class FahrwegExtensionsCrossingTest {

	def private static void connect(TOP_Kante topEdge, GEO_Kante geoEdge) {
		topEdge.TOPKnotenA.IDGEOKnoten = geoEdge.geoKnotenA
		topEdge.TOPKnotenB.IDGEOKnoten = geoEdge.geoKnotenB
		geoEdge.IDGEOArt = topEdge
	}

	def private static GEO_Kante createGeoEdge(int x1, int y1, int x2, int y2,
		Container_AttributeGroup container) {
		val GEO_Kante geoEdge = GeodatenFactory.eINSTANCE.createGEO_Kante()
		container.getGEOKante().add(geoEdge)

		// create id
		geoEdge.setIdentitaet(createId(getEdgeName("geo", x1, y1, x2, y2)))

		// create nodes
		val GEO_Knoten nodeA = createGeoNode(x1, y1, container)
		val GEO_Knoten nodeB = createGeoNode(x2, y2, container)
		geoEdge.setIDGEOKnotenA(nodeA)
		geoEdge.setIDGEOKnotenB(nodeB)

		// create edge common
		val geoEdgeCommon = GeodatenFactory.eINSTANCE.
			createGEO_Kante_Allg_AttributeGroup
		val length = GeodatenFactory.eINSTANCE.createGEO_Laenge_TypeClass
		length.setWert(BigDecimal.valueOf(getLength(x1, y1, x2, y2)))
		geoEdgeCommon.GEOLaenge = length
		val form = GeodatenFactory.eINSTANCE.createGEO_Form_TypeClass
		form.wert = ENUMGEOForm.ENUMGEO_FORM_GERADE
		geoEdgeCommon.GEOForm = form
		geoEdge.GEOKanteAllg = geoEdgeCommon

		return geoEdge
	}

	def private static GEO_Knoten createGeoNode(int x, int y,
		Container_AttributeGroup container) {
		val GEO_Knoten geoNode = GeodatenFactory.eINSTANCE.createGEO_Knoten()
		geoNode.setIdentitaet(createId(getNodeName("geo", x, y)))
		container.getGEOKnoten().add(geoNode)

		// create GEO point
		val geoPoint = GeodatenFactory.eINSTANCE.createGEO_Punkt
		geoPoint.IDGEOKnoten = geoNode
		val geoPointCommon = GeodatenFactory.eINSTANCE.
			createGEO_Punkt_Allg_AttributeGroup
		val gkX = GeodatenFactory.eINSTANCE.createGK_X_TypeClass
		val gkY = GeodatenFactory.eINSTANCE.createGK_Y_TypeClass
		gkX.wert = BigDecimal.valueOf(x)
		gkY.wert = BigDecimal.valueOf(y)
		geoPointCommon.GKX = gkX
		geoPointCommon.GKY = gkY
		geoPoint.GEOPunktAllg = geoPointCommon
		container.GEOPunkt.add(geoPoint)

		return geoNode
	}

	def private static Identitaet_TypeClass createId(String edgeName) {
		val Identitaet_TypeClass id = BasisobjekteFactory.eINSTANCE.
			createIdentitaet_TypeClass()
		id.setWert(edgeName)
		return id
	}

	def private static Signal createSignal(
		String signalName,
		Container_AttributeGroup container
	) {
		val signal = SignaleFactory.eINSTANCE.createSignal
		signal.identitaet = createId(signalName)
		container.signal.add(signal)
		return signal
	}

	def private static TOP_Kante createTopEdge(int x1, int y1, int x2, int y2,
		Container_AttributeGroup container) {
		val TOP_Kante topEdge = GeodatenFactory.eINSTANCE.createTOP_Kante()
		container.getTOPKante().add(topEdge)

		// create id
		topEdge.setIdentitaet(createId(getEdgeName("top", x1, y1, x2, y2)))

		// create nodes
		val TOP_Knoten nodeA = createTopNode(x1, y1, container)
		val TOP_Knoten nodeB = createTopNode(x2, y2, container)
		topEdge.setIDTOPKnotenA(nodeA)
		topEdge.setIDTOPKnotenB(nodeB)

		// create length
		val TOP_Kante_Allg_AttributeGroup topEdgeCommon = GeodatenFactory.
			eINSTANCE.createTOP_Kante_Allg_AttributeGroup()
		val TOP_Laenge_TypeClass length = GeodatenFactory.eINSTANCE.
			createTOP_Laenge_TypeClass()
		length.setWert(BigDecimal.valueOf(getLength(x1, y1, x2, y2)))
		topEdgeCommon.setTOPLaenge(length)
		topEdge.setTOPKanteAllg(topEdgeCommon)

		// create geo edges
		val GEO_Kante geoEdge = createGeoEdge(x1, y1, x2, y2, container)

		// connect the edges
		connect(topEdge, geoEdge)

		return topEdge
	}

	def private static TOP_Knoten createTopNode(int x, int y,
		Container_AttributeGroup container) {
		val TOP_Knoten topNode = GeodatenFactory.eINSTANCE.createTOP_Knoten()
		topNode.identitaet = createId(getNodeName("top", x, y))
		container.TOPKnoten.add(topNode)
		return topNode
	}

	def private static String getEdgeName(String type, int x1, int y1, int x2,
		int y2) {
		val StringBuilder builder = new StringBuilder()
		builder.append(type)
		builder.append("_")
		// $NON-NLS-1$
		builder.append(getNodeName(type, x1, y1))
		builder.append("__")
		// $NON-NLS-1$
		builder.append(getNodeName(type, x2, y2))
		return builder.toString()
	}

	def private static double getLength(int x1, int y1, int x2, int y2) {
		return new LineSegment(x1, y1, x2, y2).getLength()
	}

	def private static String getNamePart(int i_finalParam_) {
		var i = i_finalParam_
		if (i < 0) {
			return '''M«Integer.toString(-i)»''' // $NON-NLS-1$
		}
		return Integer.toString(i)
	}

	def private static String getNodeName(String type, int x, int y) {
		val StringBuilder builder = new StringBuilder()
		builder.append(type)
		builder.append("_")
		// $NON-NLS-1$
		builder.append(getNamePart(x))
		builder.append("_")
		// $NON-NLS-1$
		builder.append(getNamePart(y))
		return builder.toString()
	}

	def private static void givenNoCacheService() {
		Services.setCacheService(new NoCacheService())
	}

	W_Kr_Gsp_Komponente crossing
	CrossingRoute crossingRoute
	Fstr_Fahrweg testee
	TOP_Kante topEdgeCrossing
	TOP_Kante topEdgeFw
	TOP_Kante topEdge_0_0__10_0
	TOP_Kante topEdge_0_1__10_M1
	TOP_Kante topEdge_0_M1__10_1

	def private void givenTesteeFor(CrossingRoute wantedCrossingRoute) {
		// create container
		val LST_Zustand lstState = PlanProFactory.eINSTANCE.createLST_Zustand()
		val Container_AttributeGroup container = PlanProFactory.eINSTANCE.
			createContainer_AttributeGroup()
		lstState.setContainer(container)

		// create Fahrweg
		testee = FahrstrasseFactory.eINSTANCE.createFstr_Fahrweg()
		container.fstrFahrweg.add(testee)
		val start = createSignal("startSignal", container)
		val destination = createSignal("destinationSignal", container)
		testee.IDStart = start
		testee.IDZiel = destination

		// create state
		val LST_Zustand state = PlanProFactory.eINSTANCE.createLST_Zustand
		state.setContainer(container)
		state.setIdentitaet(createId("state_id"))

		// create crossing
		crossing = Weichen_und_GleissperrenFactory.eINSTANCE.
			createW_Kr_Gsp_Komponente()
		val Kreuzung_AttributeGroup attributeGroup = Weichen_und_GleissperrenFactory.
			eINSTANCE.createKreuzung_AttributeGroup()
		crossing.setKreuzung(attributeGroup)
		container.getWKrGspKomponente().add(crossing)

		// create TOP edges
		topEdge_0_0__10_0 = createTopEdge(0, 0, 10, 0, container)
		topEdge_0_1__10_M1 = createTopEdge(0, 1, 10, -1, container)
		topEdge_0_M1__10_1 = createTopEdge(0, -1, 10, 1, container)

		// choose TOP edges
		topEdgeFw = topEdge_0_0__10_0

		switch (wantedCrossingRoute) {
			case LEFT: {
				topEdgeCrossing = topEdge_0_1__10_M1
			}
			case RIGHT: {
				topEdgeCrossing = topEdge_0_M1__10_1
			}
			default: {
				throw new IllegalArgumentException(
					wantedCrossingRoute.toString())
			}
		}

		// create single point on the middle of the fw edge
		crossing.placeOnMiddleOf(topEdgeFw)

		// create a single point on the middle of the crossing edge
		crossing.placeOnMiddleOf(topEdgeCrossing)

		// mount the start and destination signal on the fw edge
		mount(start, topEdgeFw, topEdgeFw.TOPKnotenA)
		mount(destination, topEdgeFw, topEdgeFw.TOPKnotenA)

		// add fw edge to fw area
		place(topEdgeFw, testee)
	}

	private static def void placeOnMiddleOf(Punkt_Objekt pointObject,
		TOP_Kante edge) {
		val Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint = BasisobjekteFactory.
			eINSTANCE.createPunkt_Objekt_TOP_Kante_AttributeGroup()
		val Abstand_TypeClass distance = BasisobjekteFactory.eINSTANCE.
			createAbstand_TypeClass()
		distance.setWert(BigDecimal.valueOf(edge.laenge / 2))
		singlePoint.setAbstand(distance)
		singlePoint.setIDTOPKante(edge)
		pointObject.getPunktObjektTOPKante().add(singlePoint)
	}

	def private void mount(Signal signal, TOP_Kante edge, TOP_Knoten node) {
		val Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint = BasisobjekteFactory.
			eINSTANCE.createPunkt_Objekt_TOP_Kante_AttributeGroup()
		singlePoint.IDTOPKante = edge
		val Abstand_TypeClass distance = BasisobjekteFactory.eINSTANCE.
			createAbstand_TypeClass
		switch (node) {
			case edge.TOPKnotenA: {
				distance.wert = BigDecimal.ZERO
			}
			case edge.TOPKnotenB: {
				distance.wert = BigDecimal.valueOf(edge.laenge)
			}
			default: {
				throw new IllegalArgumentException(node?.identitaet?.wert)
			}
		}
		singlePoint.abstand = distance
		signal.punktObjektTOPKante.add(singlePoint)
	}

	def private void place(TOP_Kante edge, Fstr_Fahrweg fahrweg) {
		val area = BasisobjekteFactory.eINSTANCE.
			createBereich_Objekt_Teilbereich_AttributeGroup
		area.IDTOPKante = edge
		val a = BasisobjekteFactory.eINSTANCE.createBegrenzung_A_TypeClass
		a.wert = BigDecimal.ZERO
		area.begrenzungA = a
		val b = BasisobjekteFactory.eINSTANCE.createBegrenzung_B_TypeClass
		b.wert = BigDecimal.valueOf(edge.laenge)
		area.begrenzungB = b
		fahrweg.bereichObjektTeilbereich.add(area)
	}

	/** 
	 * Test for{@link FahrwegExtensions#getCrossingRoute(Fstr_Fahrweg, Kreuzung_AttributeGroup)}.
	 */
	@Test
	def void testGetCrossingRouteLeft() {
		val CrossingRoute testCrossingRoute = CrossingRoute.LEFT
		givenNoCacheService()
		givenTesteeFor(testCrossingRoute)
		whenGetCrossingRoute()
		thenExpectCrossingRoute(testCrossingRoute)
	}

	/** 
	 * Test for{@link FahrwegExtensions#getCrossingRoute(Fstr_Fahrweg, Kreuzung_AttributeGroup)}.
	 */
	@Test
	def void testGetCrossingRouteRight() {
		val CrossingRoute testCrossingRoute = CrossingRoute.RIGHT
		givenNoCacheService()
		givenTesteeFor(testCrossingRoute)
		whenGetCrossingRoute()
		thenExpectCrossingRoute(testCrossingRoute)
	}

	def private void thenExpectCrossingRoute(
		CrossingRoute expectedCrossingRoute) {
		assertThat(crossingRoute, is(expectedCrossingRoute))
	}

	def private void whenGetCrossingRoute() {
		crossingRoute = testee.getCrossingRoute(crossing.kreuzung)
	}
}
