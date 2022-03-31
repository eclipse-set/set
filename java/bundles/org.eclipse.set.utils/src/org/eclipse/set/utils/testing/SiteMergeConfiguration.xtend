/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.testing

import org.eclipse.set.basis.integration.DiffLabelProvider
import org.eclipse.set.basis.integration.Matcher
import org.eclipse.set.core.services.merge.MergeService.Configuration
import org.eclipse.set.core.services.merge.MergeService.ElementProvider
import org.eclipse.set.core.services.merge.MergeService.GuidProvider

/**
 * Merge configuration for site model.
 * 
 * @author Schaefer
 */
class SiteMergeConfiguration implements Configuration {

	val GuidProvider guidProvider
	val Matcher matcher
	val ElementProvider elementProvider
	val DiffLabelProvider labelProvider

	new() {
		guidProvider = new SiteGuidProvider
		labelProvider = new SiteLabelProvider
		matcher = new SiteMatcher(labelProvider)
		elementProvider = new SiteElementProvider(guidProvider)
	}

	override getGuidProvider() {
		return guidProvider
	}

	override getMatcher() {
		return matcher
	}

	override getElementProvider() {
		return elementProvider
	}

	override getLabelProvider() {
		return labelProvider
	}
}
