/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.extensions

import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.model.validationreport.ObjectScope

/**
 * Extensions for {@link ValidationProblem}.
 * 
 * @author Schaefer
 */
class ValidationProblemExtensions {
	
	/**
	 * @param problem this validation problem
	 * 
	 * @return the CSV export
	 */
	static def String getCsvExport(ValidationProblem problem) {
		var objectScope = problem.objectScope === ObjectScope.UNKNOWN ? ' ' : problem.objectScope
		return '''"«problem.id»";"«problem.severityText»";"«problem.type»";"«problem.lineNumber»";"«problem.objectArt»";"«problem.attributeName»";"«objectScope»";"«problem.message»"«System.lineSeparator»'''
	}
}
