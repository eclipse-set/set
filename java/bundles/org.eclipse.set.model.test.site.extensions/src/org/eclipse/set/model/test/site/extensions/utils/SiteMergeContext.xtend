/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site.extensions.utils

import org.eclipse.set.core.services.merge.MergeService.Configuration
import org.eclipse.set.core.services.merge.MergeService.Context
import org.eclipse.set.core.services.merge.MergeService.Responsibility
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Site merge context.
 *  
 * @author Schaefer
 */
class SiteMergeContext implements Context {

	new(
		EObject primaryContainer,
		EObject secondaryContainer,
		Configuration configuration,
		Responsibility responsibility
	) {
		this.primaryContainer = primaryContainer
		this.secondaryContainer = secondaryContainer
		this.configuration = configuration
		this.responsibility = responsibility
	}

	@Accessors
	EObject primaryContainer

	@Accessors
	EObject secondaryContainer

	@Accessors
	Configuration configuration

	@Accessors
	Responsibility responsibility
}
