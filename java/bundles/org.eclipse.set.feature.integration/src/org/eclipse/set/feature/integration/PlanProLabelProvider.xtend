/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration

import com.google.common.collect.Lists
import org.eclipse.set.toolboxmodel.Bahnuebergang.BUE_Anlage
import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehung
import org.eclipse.set.toolboxmodel.Gleis.Gleis_Abschnitt
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.core.services.name.NameService
import org.eclipse.set.utils.AbstractDiffLabelProvider
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.utils.emfforms.Annotations.*

/**
 * Label provider for PlanPro model.
 * 
 * @author Schaefer
 */
class PlanProLabelProvider extends AbstractDiffLabelProvider {

	val NameService nameService

	new(NameService nameService) {
		this.nameService = nameService
	}

	static val EXTENDED_META_DATA = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"

	override getElementLabel(EObject element) {
		return getElementLabelDispatch(element)
	}

	private dispatch def String getElementLabelDispatch(EObject element) {
		return element.toString
	}

	private dispatch def String getElementLabelDispatch(Void element) {
		return ""
	}

	private dispatch def String getElementLabelDispatch(Ur_Objekt object) {
		return '''«object.eClass.name» «object.identitaet.wert»'''
	}

	private dispatch def String getElementLabelDispatch(Anhang anhang) {
		// IMPROVE after using the name provider we probably do not need the dispatch anymore
		return '''Anhang "«nameService.getName(anhang)»"'''
	}

	private dispatch def String getElementLabelDispatch(
		Ueberhoehung ueberhoehung) {
		return '''Überhöhung "«nameService.getName(ueberhoehung)»"'''
	}

	private dispatch def String getElementLabelDispatch(
		W_Kr_Gsp_Element wKrGspElement) {
		var x = "Sonstiges Weichen"
		if (wKrGspElement?.weicheElement !== null) {
			x = wKrGspElement?.WKrAnlage?.WKrAnlageAllg?.WKrArt?.wert?.
				toString ?: "(keine Weichenart)"
		}
		if (wKrGspElement?.gleissperreElement !== null) {
			x = "Gsp"
		}
		return '''«x»-Element «nameService.getName(wKrGspElement)»'''
	}

	private dispatch def String getElementLabelDispatch(BUE_Anlage bueAnlage) {
		return '''Bahnübergang "«nameService.getName(bueAnlage)»"'''
	}

	private dispatch def String getElementLabelDispatch(Fstr_Zug_Rangier fstr) {
		return nameService.getName(fstr)
	}

	private dispatch def String getElementLabelDispatch(TOP_Kante kante) {
		return '''Top-Kante "«kante?.TOPKnotenA?.knotenname?.wert ?: "?"»/«kante?.TOPKnotenB?.knotenname?.wert ?: "?"»"'''
	}

	private dispatch def String getElementLabelDispatch(
		Gleis_Abschnitt abschnitt
	) {
		return '''Gleisabschnitt "«abschnitt?.bezeichnung?.bezeichnungTabelle?.wert ?: "(ohne Tabellenbezeichnung)"»"'''
	}

	override protected getAttributeLabel(Object value, EObject element) {
		return getAttributeLabelDispatch(value, element.container)
	}

	private def dispatch Container_AttributeGroup getContainer(EObject object) {
		return object.eContainer.container
	}

	private def dispatch Container_AttributeGroup getContainer(
		Container_AttributeGroup container) {
		return container
	}
	
	private def dispatch String getAttributeLabelDispatch(
		Void value,
		Container_AttributeGroup container
	) {
		return ""
	}

	private def dispatch String getAttributeLabelDispatch(
		Object value,
		Container_AttributeGroup container
	) {
		return value.toString
	}
	
	private def dispatch String getAttributeLabelDispatch(
		Ur_Objekt value,
		Container_AttributeGroup container
	) {
		return value?.identitaet?.wert
	}

	private def dispatch String getAttributeLabelDispatch(
		List<?> value,
		Container_AttributeGroup container
	) {
		if (value.empty) {
			return ""
		}
		return value.toString
	}

	private def dispatch String getAttributeLabelDispatch(byte[] element,
		Container_AttributeGroup container) {
		return '''«element.size / 1000» kB binary data'''
	}

	override getPathLabel(EObject element, List<String> path) {
		val List<String> transfomed = Lists.newLinkedList.getPathLabel(
			element.eClass, path)
		return '''«FOR s : transfomed SEPARATOR "."»«s»«ENDFOR»'''
	}

	private def List<String> getPathLabel(
		List<String> transformed,
		EClassifier type,
		Iterable<String> path
	) {
		if (path.empty) {
			return transformed
		}

		val head = path.head
		val tail = path.tail

		// we skip "wert" segments
		if (head == "wert") {
			return transformed
		}

		// find the segment name
		val feature = (type as EClass).getEStructuralFeature(head)
		val segment = feature.getValue(EXTENDED_META_DATA, "name")
		transformed.add(segment)

		return transformed.getPathLabel(feature.EType, tail)
	}
}
