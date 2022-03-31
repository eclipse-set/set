/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference

/**
 * Extensions for {@link VDomainModelReference}.
 * 
 * @author Schaefer
 */
class VDomainModelReferenceExtensions {

	/**
	 * The name of the value field.
	 */
	public static val String VALUE_FIELD_NAME = "wert"

	/**
	 * @param reference this VDomainModelReference
	 * 
	 * @return whether this reference points to a value ("wert") field
	 */
	static def dispatch boolean isValueField(VDomainModelReference reference) {
		throw new IllegalArgumentException('''«reference.class.simpleName»''')
	}

	static def dispatch boolean isValueField(
		VFeaturePathDomainModelReference reference) {
		return reference?.domainModelEFeature?.name == VALUE_FIELD_NAME
	}

	/**
	 * @param reference this VDomainModelReference
	 * @param start the start object for resolving the references
	 * 
	 * @return the object this reference is pointing at
	 */
	static def dispatch EObject resolve(
		VDomainModelReference reference,
		EObject start
	) {
		throw new IllegalArgumentException('''«reference.class.simpleName»''')
	}

	static def dispatch EObject resolve(
		VFeaturePathDomainModelReference reference,
		EObject start
	) {
		val path = reference.domainModelEReferencePath
		return resolvePath(path, start)
	}

	private static def EObject resolvePath(
		Iterable<EReference> path,
		EObject start
	) {
		if (path.empty) {
			return start
		}
		val head = path.head
		val tail = path.tail
		return resolvePath(tail, start.eGet(head) as EObject)
	}
}
