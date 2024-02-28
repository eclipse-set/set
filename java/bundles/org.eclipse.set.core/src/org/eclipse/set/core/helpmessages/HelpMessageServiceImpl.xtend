/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.helpmessages

import org.eclipse.set.basis.emfforms.RendererContext
import org.eclipse.set.core.services.helpmessage.HelpMessageService
import jakarta.inject.Inject
import org.eclipse.e4.core.services.nls.Translation
import org.eclipse.emf.ecore.EStructuralFeature
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/** 
 * Implementation of {@link HelpMessageService}.
 * @author Schaefer
 */
class HelpMessageServiceImpl implements HelpMessageService {

	static final Logger LOGGER = LoggerFactory.getLogger(
		typeof(HelpMessageServiceImpl));

	@Inject
	@Translation
	HelpMessages messages

	override String getMessage(RendererContext rendererContext) {
		val String key = getKey(rendererContext)
		return getMessage(key)
	}

	def private String getKey(RendererContext rendererContext) {
		val EStructuralFeature structuralFeature = rendererContext.get(
			EStructuralFeature)
		return '''HelpMessage_«structuralFeature.containerClass.simpleName»_«structuralFeature.name»'''
	}

	def private String getMessage(String key) {
		try {
			val message = typeof(HelpMessages).declaredFields.findFirst [
				name == key
			]?.get(messages) as String
			if (message === null) {
				LOGGER.warn("Help message missing: key={}", key)
			}
			return message?.trim
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e)
		}
	}
}
