/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.model.plazmodel.PlazFactory
import org.osgi.service.component.annotations.Component
import java.io.IOException
import java.util.Map
import org.apache.commons.text.StringSubstitutor

/**
 * Validates that all attachment files on the filesystem are referenced
 * 
 * @author Stuecker
 */
@Component
class AttachmentReferenced implements PlazCheck {
	override run(IModelSession modelSession) {
		val attachments = modelSession.planProSchnittstelle.eAllContents.filter(
			Anhang).map [
			identitaet?.wert
		].toSet

		try {
			return modelSession.toolboxFile.allMedia.filter [
				!attachments.contains(it)
			].map [
				val err = PlazFactory.eINSTANCE.createPlazError
				err.message = transformErrorMsg(Map.of("GUID", it))
				err.type = checkType
				err.object = null
				return err
			].toList
		} catch (IOException exc) {
			// Rethrow to notify user
			throw new RuntimeException(exc)
		}
	}

	override checkType() {
		return "Anhänge"
	}

	override getDescription() {
		return "Anhänge in der .planpro-Datei werden referenziert."
	}

	override getGeneralErrMsg() {
		return "Der Anhang {GUID} ist vorhanden, wird aber nicht referenziert."
	}

	private def transformErrorMsg(Map<String, String> params) {
		return StringSubstitutor.replace(getGeneralErrMsg(), params, "{", "}"); // $NON-NLS-1$//$NON-NLS-2$
	}
}
