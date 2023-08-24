/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.set.basis.IModelSession
import org.eclipse.set.basis.constants.ValidationResult.Outcome
import java.io.IOException
import java.nio.file.Files
import java.util.ArrayList
import java.util.List
import org.eclipse.set.model.model11001.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.model.model11001.Layoutinformationen.PlanPro_Layoutinfo

/**
 * Extensions for {@link IModelSession}.
 * 
 * @author Schaefer
 */
class IModelSessionExtensions {

	/**
	 * @param session the model session
	 * 
	 * @return the lines of the model file
	 */
	static def List<String> getLines(IModelSession session) throws IOException {
		if (session.toolboxFile !== null) {
			return Files.readAllLines(session.toolboxFile.modelPath)
		} else {
			return new ArrayList<String>()
		}
	}

	/**
	 * @param session the model session
	 * 
	 * @return whether the session has a loaded model
	 */
	static def boolean hasLoadedModel(IModelSession session) {
		return session !== null && session.loaded
	}

	/**
	 * @param session the model session
	 * 
	 * @return whether the session has an XSD valid model
	 */
	static def boolean isXsdValid(IModelSession session) {
		return session !== null &&
			!#[session.getValidationResult(PlanPro_Schnittstelle), session.getValidationResult(PlanPro_Layoutinfo)].exists[
				xsdOutcome == Outcome.INVALID	
			]
	}

	/**
	 * @param session the model session
	 * 
	 * @return whether the session has an EMF valid model
	 */
	static def boolean isEmfValid(IModelSession session) {
		return session !== null &&
			!#[session.getValidationResult(PlanPro_Schnittstelle), session.getValidationResult(PlanPro_Layoutinfo)].exists[
				emfOutcome == Outcome.INVALID	
			]
	}

	/**
	 * @param session the model session
	 * 
	 * @return the title of the session
	 */
	static def String getTitleFilename(IModelSession session,
		String changeIndicator) {
		val filename = session?.toolboxFile?.path?.fileName
		if (filename !== null) {
			return '''«filename»«session.getChangeIndicator(changeIndicator)»'''
		}
		return null
	}

	private static def getChangeIndicator(IModelSession session,
		String changeIndicator) {
		return '''«IF session.dirty» «changeIndicator»«ELSE»«ENDIF»'''
	}
}
