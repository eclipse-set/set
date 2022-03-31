/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.testing

import org.eclipse.set.model.test.site.Identified
import org.eclipse.set.model.test.site.Passage
import org.eclipse.set.model.test.site.Room
import org.eclipse.set.model.test.site.Site
import org.eclipse.set.model.test.site.Window
import org.eclipse.set.utils.AbstractDiffLabelProvider
import java.util.List
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.set.test.utils.PassageExtensions.*

/**
 * Provide UI description of site model objects.
 */
class SiteLabelProvider extends AbstractDiffLabelProvider {
	
	override getElementLabel(EObject element) {
		return getElementLabelDispatch(element)
	}
	
	override protected getAttributeLabel(Object value, EObject element) {
		return getAttributeLabelDispatch(value, element.site)
	}

	private def dispatch Site getSite(EObject object) {
		return object.eContainer.site
	}

	private def dispatch Site getSite(Site site) {
		return site
	}

	private def dispatch String getElementLabelDispatch(EObject element) {
		return element.toString
	}

	private def dispatch String getElementLabelDispatch(Passage passage) {
		return '''Passage from «passage.roomA.names.floorPlan» to «passage.roomB.names.floorPlan»'''
	}

	private def dispatch String getElementLabelDispatch(Window window) {
		return '''Window «window.type»: position=«window.position»'''
	}

	private def dispatch String getElementLabelDispatch(Room room) {
		return '''Room «room.names.floorPlan»'''
	}

	private def dispatch String getAttributeLabelDispatch(Object value, Site container) {
		return value.toString
	}

	private def dispatch String getAttributeLabelDispatch(String value, Site container) {
		val element = value.getIdentified(container)

		// test whether the value is an guid
		if (element !== null) {
			return element.elementLabel
		} 
		
		return value.toString
	}

	private def dispatch String getAttributeLabelDispatch(EObject object, Site container) {
		return getElementLabelDispatch(object)
	}

	private def dispatch String getAttributeLabelDispatch(List<?> objects, Site container) {
		return '''[«FOR object: objects SEPARATOR " "»«object.getAttributeLabelDispatch(container)»«ENDFOR»]'''
	}
	
	private def Identified getIdentified(Object key, Site container) {
		val elements = container.eContents.filter(Identified).filter[guid == key]
		if (elements.empty) {
			return null
		}
		if (elements.size > 1) {
			throw new IllegalArgumentException('''«elements.size» elements for «key»''')
		}
		return elements.head
	}
	
	override getPathLabel(EObject element, List<String> path) {
		throw new UnsupportedOperationException()
	}
}
