/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Signalisierung
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehung
import org.eclipse.set.model.planpro.Gleis.Gleis_Art
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_10_TypeClass
import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_5_TypeClass
import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_TypeClass
import org.eclipse.set.model.planpro.Signalbegriffe_Struktur.Signalbegriff_ID_TypeClass
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung
import org.eclipse.set.model.planpro.Signale.Signal_Rahmen
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import java.util.Collection
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.SetCommand
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.DirectedEdgePath
import org.eclipse.set.model.tablemodel.ColumnDescriptor

import static extension org.eclipse.set.basis.graph.DirectedEdgePathExtension.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrSignalisierungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektStreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*

/**
 * Utilities for debugging.
 * 
 * @author Schaefer
 */
class Debug {

	static boolean TOP_KANTE_PATH_EDGEWISE = false

	/**
	 * @param geoKante
	 *            the GEO Kante
	 * 
	 * @return "[topKante:GeoKnotenA->GeoKnotenB]"
	 */
	static def dispatch String debugString(GEO_Kante geoKante) {
		if (geoKante === null) {
			return "null"
		}
		return '''[«geoKante.topKante.identitaet.wert»:«geoKante.geoKnotenA.identitaet.wert»->«geoKante.geoKnotenB.identitaet.wert»]'''
	}

	static def dispatch String debugString(Punkt_Objekt punktObjekt) {
		return '''«punktObjekt.typeName» («punktObjekt.identitaet.wert»)'''
	}

	static def dispatch String debugString(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		return '''«singlePoint.typeName» («singlePoint.identitaet»)'''
	}

	static def dispatch String debugString(Ur_Objekt urObjekt) {
		return '''«urObjekt?.typeName» («urObjekt?.identitaet?.wert»)'''
	}

	static def dispatch String debugString(FMA_Komponente komponente) {
		return '''«komponente.typeName» bezeichnungTabelle=«komponente?.bezeichnung?.bezeichnungTabelle?.wert» («komponente?.identitaet?.wert»)'''
	}

	static def dispatch String debugString(W_Kr_Gsp_Komponente komponente) {
		return '''«komponente.typeName» fmaAnlage=«komponente.fmaAnlage.toList.debugString»'''
	}

	static def dispatch String debugString(W_Kr_Gsp_Element element) {
		return '''«element.typeName» bezeichnungTabelle=«element.bezeichnung.bezeichnungTabelle.wert»'''
	}

	static def dispatch String debugString(FMA_Anlage anlage) {
		return '''«anlage.typeName» «anlage.tableName»'''
	}

	static def dispatch String debugString(Signal signal) {
		if (signal.vorsignalbake) {
			return '''Vorsignalbake «signal?.punktObjektStrecke?.map['''«strecke?.bezeichnung.bezeichnungStrecke.wert» «streckeKm.wert»''']»'''
		}
		return '''«signal.typeName» bezeichnungTabelle=«signal?.bezeichnung?.bezeichnungTabelle?.wert.debugString» («signal?.identitaet?.wert»)'''
	}

	static def dispatch String debugString(Ueberhoehung ueberhoehung) {
		return '''«ueberhoehung.typeName» hoehe=«ueberhoehung?.ueberhoehungAllg?.ueberhoehungHoehe?.wert» («ueberhoehung?.identitaet?.wert»)'''
	}

	static def dispatch String debugString(TOP_Kante topKante) {
		return '''«topKante.typeName» id=«topKante?.identitaet?.wert»'''
	}

	static def dispatch String debugString(Signal_Rahmen rahmen) {
		return '''«rahmen.typeName» id=«rahmen?.identitaet?.wert» befestigung=«rahmen?.getIDSignalBefestigung?.wert»'''
	}

	static def dispatch String debugString(Signal_Befestigung befestigung) {
		return '''«befestigung.typeName» id=«befestigung?.identitaet?.wert» art=«befestigung?.signalBefestigungAllg?.befestigungArt?.wert»'''
	}

	static def dispatch String debugString(TopKantePath path) {
		if (TOP_KANTE_PATH_EDGEWISE) {
			return '''[start=«path.start.debugString» end=«path.end.debugString» edges=«FOR edge : path.edgeIterator.toList BEFORE '\n' SEPARATOR '\n' AFTER '\n'»«
					edge.debugString»«ENDFOR»]'''
		}
		return '''points=«path.pointIterator.map[punktObjekt].toList.debugString»'''
	}

	static def dispatch String debugString(DirectedTopKante edge) {
		return '''[guid=«edge.element.identitaet.wert» points=«edge.iterator.map[punktObjekt].toList.debugString»]'''
	}

	static def dispatch String debugString(String string) {
		return string ?: "<leer>"
	}

	static def dispatch String debugString(Signal_Signalbegriff begriff) {
		return '''«begriff.signalbegriffID.debugString»'''
	}

	static def dispatch String debugString(
		Signalbegriff_ID_TypeClass begriffId) {
		return '''«begriffId.typeName»'''
	}

	static def dispatch String debugString(Fstr_Signalisierung signalisierung) {
		return '''(begriff=«signalisierung.signalSignalbegriff.debugString» begriffZiel=«signalisierung.signalSignalbegriffZiel.debugString»)'''
	}

	/**
	 * @param list
	 *            the list
	 * 
	 * @return "{Item1,...,ItemN}"
	 */
	static def dispatch String debugString(Collection<?> list) {
		if (list.empty) {
			return "{}"
		} else {
			return '''«FOR item : list BEFORE '{' SEPARATOR ', ' AFTER '}'»«item.debugString»«ENDFOR»'''
		}
	}

	static def dispatch String debugString(DirectedEdge<?, ?, ?> edge) {
		return '''points=«edge.iterator.toList.debugString»'''
	}

	/**
	 * @param object
	 *            the object
	 * 
	 * @return object.toString
	 */
	static def dispatch String debugString(Object object) {
		return object.toString
	}

	/**
	 * @param nill
	 *            the null object
	 * 
	 * @return "null"
	 */
	static def dispatch String debugString(Void nill) {
		return "null"
	}

	static def dispatch String debugString(Command command) {
		return '''«command.class.simpleName» «command.label»'''
	}

	static def dispatch String debugString(SetCommand command) {
		return '''«command.class.simpleName» «command.feature.name»: «command.oldValue.debugString» -> «command.value.debugString»'''
	}

	static def dispatch String debugString(Name_Akteur_TypeClass name) {
		return name.wert
	}

	static def dispatch String debugString(Name_Akteur_10_TypeClass name) {
		return name.wert
	}

	static def dispatch String debugString(Name_Akteur_5_TypeClass name) {
		return name.wert
	}

	static def dispatch String debugString(ColumnDescriptor descriptor) {
		return descriptor.label
	}

	static def dispatch String debugString(
		Bereich_Objekt_Teilbereich_AttributeGroup teilbereich
	) {
		return '''topKante=«teilbereich.IDTOPKante?.wert» begrenzungA=«teilbereich.begrenzungA.wert» begrenzungB=«teilbereich.begrenzungB.wert»'''
	}

	/**
	 * @param the object
	 * 
	 * @return the name
	 */
	static def dispatch String debugName(Object object) {
		return '''«object.class.name»@«Integer.toHexString(object.hashCode())»'''
	}

	static def dispatch String debugName(Gleis_Art art) {
		return '''«art.typeName»(id=«art.identitaet.wert» art=«art.gleisart.wert»)'''
	}

	static def dispatch String debugName(TopKantePath path) {
		return '''TopKantePath(«FOR node : path.nodeList SEPARATOR "->"»«node.debugName»«ENDFOR»)'''
	}

	static def dispatch String debugName(W_Kr_Gsp_Element element) {
		return element.bezeichnung?.bezeichnungTabelle?.wert ?: ""
	}

	static def dispatch String debugName(TOP_Knoten topKnoten) {
		val edges = topKnoten.topKanten
		val noEdges = edges.size
		switch (noEdges) {
			case 1: return edges.get(0).getTOPAnschluss(topKnoten).toString
			case 3: return topKnoten.WKrGspElement.debugName
			default: return '''TopKnoten(«noEdges»)'''
		}
	}

	static def dispatch String debugName(TOP_Kante topKante) {
		return topKante.identitaet.wert
	}

	static def dispatch String debugName(Fstr_Fahrweg fahrweg) {
		return '''Fstr_Fahrweg(start=«fahrweg.start.debugName» ziel=«fahrweg.zielObjekt.debugName»)'''
	}

	static def dispatch String debugName(Signal signal) {
		return signal?.bezeichnung?.bezeichnungTabelle?.wert.debugString
	}

	static def dispatch String debugName(W_Kr_Gsp_Komponente komponente) {
		return komponente.debugString
	}

	static def <E, N, P> String debugNodesAndEdges(
		DirectedEdgePath<E, N, P> path) {
		val edges = path.edgeList
		if (edges.size == 0) {
			return "[]"
		}
		return '''[«edges.get(0).tail.debugName»«FOR edge : edges»-{«edge.element.debugName»}->«edge.tail.debugName»«ENDFOR»]'''
	}
}
