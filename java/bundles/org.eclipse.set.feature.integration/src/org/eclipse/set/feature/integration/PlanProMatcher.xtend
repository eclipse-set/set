/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration;

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt
import org.eclipse.set.utils.AbstractMatcher
import org.eclipse.set.basis.integration.DiffLabelProvider
import org.eclipse.set.basis.integration.Matcher
import org.eclipse.emf.ecore.EObject

/**
 * PlanPro implementation for {@link Matcher}.
 * 
 * @author Schaefer
 */
class PlanProMatcher extends AbstractMatcher {

	new(DiffLabelProvider labelProvider) {
		super(labelProvider);
	}

	override match(EObject first, EObject second) {
		val firstUrObject = first as Ur_Objekt
		val secondUrObject = second as Ur_Objekt

		// we currently check for same guid only
		val firstGuid = firstUrObject.identitaet.wert
		val secondGuid = secondUrObject.identitaet.wert

		return firstGuid == secondGuid;
	}
}
