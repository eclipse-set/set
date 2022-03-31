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
import org.eclipse.set.utils.AbstractMatcher
import org.eclipse.emf.ecore.EObject

import org.eclipse.set.basis.integration.DiffLabelProvider
import org.eclipse.set.basis.integration.Matcher

/**
 * Site implementation for {@link Matcher}.
 * 
 * @author Schaefer
 */
class SiteMatcher extends AbstractMatcher {

	new(DiffLabelProvider labelProvider) {
		super(labelProvider)
	}

	override match(EObject first, EObject second) {
		val firstIdentified = first as Identified
		val secondIdentified = second as Identified

		val firstGuid = firstIdentified.guid
		val secondGuid = secondIdentified.guid

		return firstGuid == secondGuid
	}
}
