/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import java.util.Map
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang
import org.osgi.service.component.annotations.Component

/**
 * Validates that all attachments referenced are present on the filesystem
 * 
 * @author Stuecker
 */
@Component
class AttachmentPresent implements PlazCheck {
	override run(IModelSession modelSession) {
		return modelSession.planProSchnittstelle.eAllContents.filter(Anhang).
			filter [
				!attachmentPresent(it, modelSession)
			].map [
				val err = PlazFactory.eINSTANCE.createPlazError
				err.message = transformErrorMsg(
					Map.of("GUID", it.identitaet?.wert ?: "(ohne Identität)"))
				err.type = checkType
				err.object = it
				return err
			].toList
	}

	private def boolean attachmentPresent(Anhang attachment,
		IModelSession modelSession) {
		return modelSession.toolboxFile.hasMedia(attachment.identitaet?.wert)
	}

	override checkType() {
		return "Anhänge"
	}

	override getDescription() {
		return "Referenzierte Anhänge sind in der .planpro-Datei vorhanden."
	}

	override getGeneralErrMsg() {
		return "Der Anhang {GUID} wird referenziert, ist aber nicht vorhanden."
	}
}
