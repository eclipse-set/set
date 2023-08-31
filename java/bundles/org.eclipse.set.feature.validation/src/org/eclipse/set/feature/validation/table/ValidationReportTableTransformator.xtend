/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.table

import org.eclipse.set.model.validationreport.ValidationReport
import org.eclipse.set.utils.table.AbstractTableModelTransformator
import org.eclipse.set.utils.table.TMFactory

class ValidationReportTableTransformator extends AbstractTableModelTransformator<ValidationReport> {	
	ValidationTableColumns columns;
	

	new(ValidationTableColumns columns) {
		super()
		this.columns = columns;
	}
	
	override transformTableContent(ValidationReport report, TMFactory factory) {
		for (problem : report.problems) {
			val instance = factory.newTableRow(null, problem.id)
			fill(instance, columns.RowIndex, problem, [problem.id.toString])
			fill(instance, columns.Severity, problem, [severityText])
			fill(instance, columns.ProblemType, problem, [type])
			if (problem.lineNumber != 0) {
				fillNumeric(instance, columns.LineNumber, problem, [lineNumber])				
			}
			fill(instance, columns.ObjectType, problem, [objectArt])
			fill(instance, columns.AttributeGroup, problem, [attributeName])
			fill(instance, columns.ObjectScope, problem, [objectScope?.toString])
			fill(instance, columns.ObjectState, problem, [objectState?.literal])
			fill(instance, columns.Message, problem, [message])			
		}
		
		return factory.table
	}
}
