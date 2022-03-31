/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.eclipse.set.basis.integration.DiffLabelProvider
import java.util.List
import org.eclipse.emf.ecore.EObject

/**
 * Abstract implementation of {@link DiffLabelProvider}.
 * 
 * @author Schaefer
 */
abstract class AbstractDiffLabelProvider implements DiffLabelProvider {

	override getAttributeLabel(EObject element, List<String> path) {
		if (element === null) {
			return ""
		}
		return getText(element, path, true) as String
	}

	override getAttributeValue(EObject element, List<String> path) {
		if (element === null) {
			return null
		}
		return getText(element, path, false)
	}

	private def Object getText(
		EObject element,
		Iterable<String> path,
		boolean isLabel
	) {
		if (element === null) {
			if (isLabel) {
				return ""
			} else {
				return null
			}
		}
		if (path.empty) {
			throw new IllegalArgumentException("empty attributePath")
		}
		val head = path.head
		val tail = path.tail
		val value = element.eGet(element.eClass.getEStructuralFeature(head))
		if (tail.empty) {
			if (isLabel) {
				return getAttributeLabel(value, element)
			} else {
				return value
			}
		}
		return getText((value as EObject), tail, isLabel)
	}

	abstract protected def String getAttributeLabel(Object value,
		EObject element)
}
