/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.service

import java.util.List
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.feature.plazmodel.check.PlazCheck
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.model.plazmodel.PlazReport
import org.eclipse.set.model.validationreport.ObjectScope
import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.set.model.validationreport.ValidationreportFactory
import org.eclipse.set.utils.xml.EObjectXMLFinder
import org.eclipse.set.utils.xml.EObjectXMLFinder.LineNotFoundException
import org.eclipse.set.utils.xml.EObjectXMLFinder.XmlParseException
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference
import org.osgi.service.component.annotations.ReferenceCardinality
import org.osgi.service.component.annotations.ReferencePolicy
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class PlazModelServiceImpl implements PlazModelService {
	@Reference(cardinality=ReferenceCardinality.
		MULTIPLE, policy=ReferencePolicy.DYNAMIC)
	final List<PlazCheck> checks = newArrayList

	static final Logger logger = LoggerFactory.getLogger(PlazModelServiceImpl)
	EObjectXMLFinder finder

	override runPlazModel(IModelSession modelSession) {
		return runPlazModel(modelSession, checks)
	}

	override <T extends PlazCheck> PlazReport runPlazModel(
		IModelSession modelSession, Class<T> checkClass) {
		return runPlazModel(modelSession,
			checks.filter(checkClass) as Iterable<PlazCheck>)
	}

	private def PlazReport runPlazModel(IModelSession modelSession,
		Iterable<PlazCheck> plazChecks) {
		val PlazReport report = PlazFactory.eINSTANCE.createPlazReport
		try {
			finder = new EObjectXMLFinder(modelSession.toolboxFile,
				modelSession.toolboxFile.modelPath)
		} catch (XmlParseException exc) {
			finder = null
			logger.error("Parsing XML document failed", exc) // $NON-NLS-1$
		}

		val errors = <PlazCheck, List<PlazError>>newHashMap
		plazChecks.forEach [
			try {
				errors.put(it, it.run(modelSession))
			} catch (Exception e) {
				val error = PlazFactory.eINSTANCE.createPlazError
				error.severity = ValidationSeverity.ERROR
				error.type = it.checkType
				error.message = e.message
				errors.put(it, #[error])
			}
			
		]
		val entries = <ValidationProblem>newLinkedList
		for (var m = 0, var n = 0; m < errors.size; n +=
			errors.values.get(m).length, m++) {
			val startCount = n
			val errorsList = errors.values.get(m)
			val generalErroMsg = errors.keySet?.get(m)?.generalErrMsg
			if (errorsList.length === 0) {
				entries.add(
					errors.keySet.get(m).sucessfulReport(n)
				)
				n++
			} else {
				errorsList.forEach [ error, index |
					entries.add(
						error.transform(index + startCount, finder,
							generalErroMsg))
				]
			}
		}

		entries.sortAndIndexedProblems
		report.entries.addAll(entries)
		return report
	}

	def ValidationProblem transform(PlazError error, int index,
		EObjectXMLFinder finder, String generalErroMsg) {
		val entry = ValidationreportFactory.eINSTANCE.createValidationProblem
		val node = finder.find(error.object)
		// Add +1 to start counting from one rather than zero
		entry.id = index + 1
		entry.message = error.message
		entry.type = error.type
		entry.severity = error.severity
		entry.generalMsg = generalErroMsg
		if (node !== null) {
			var line = 0
			try {
				line = finder.getLineNumber(node)
			} catch (NullPointerException exc) {
				// ignore: EObjectXMLFinder not initialized (probably due to XmlParseException)
			} catch (LineNotFoundException exc) {
				logger.warn(
				'''Line number not found for PlaZ entry with index «entry.id»''')
			}
			entry.lineNumber = line
			entry.objectArt = finder.getObjectType(node)
			entry.objectScope = finder.getObjectScope(node)
			entry.objectState = finder.getObjectState(node)
			entry.attributeName = finder.getAttributeName(node)
		}
		return entry
	}

	def ValidationProblem sucessfulReport(PlazCheck check, int index) {
		val entry = ValidationreportFactory.eINSTANCE.createValidationProblem
		entry.id = index + 1
		entry.severity = ValidationSeverity.SUCCESS
		entry.type = check.checkType
		entry.message = check.description
		entry.objectScope = ObjectScope.CONTENT
		return entry

	}
}
