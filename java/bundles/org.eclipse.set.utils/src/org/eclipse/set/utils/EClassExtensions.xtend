/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import com.google.common.collect.Lists
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

/**
 * Extensions for {@link EClass}.
 * 
 * @author Schaefer
 */
class EClassExtensions {

	/**
	 * @param type this class
	 * @param nestIntoLists whether to nest into lists types
	 * 
	 * @return path to all nested attributes of this class
	 */
	static def List<List<String>> getAttributePaths(
		EClass type,
		boolean nestIntoLists
	) {
		return Lists.newLinkedList.getAttributePaths(type, nestIntoLists)
	}

	private static def List<List<String>> getAttributePaths(
		List<String> path,
		EClass type,
		boolean nestIntoLists
	) {
		val results = Lists.newLinkedList

		// we filter references we want to nest into later 
		type.EAllStructuralFeatures.filter[!nestReference].forEach [
			results.add(
				(path + #[name]).toList
			)
		]
		type.EAllReferences.forEach [
			if (containment) {
				if (nestIntoLists || upperBound == 1) {
					results.addAll(
						(path + #[name]).toList.getAttributePaths(
							EReferenceType,
							nestIntoLists
						)
					)
				} else {
					results.add((path + #[name]).toList)
				}
			}
		]
		return results
	}

	private static def boolean isNestReference(EStructuralFeature feature) {
		if (feature instanceof EReference) {
			// we nest into containment references
			return feature.containment
		}

		// we do not nest into something else
		return false
	}
}
