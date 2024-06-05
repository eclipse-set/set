/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview

import java.util.Collection
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.utils.table.AbstractTableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.utils.table.TableError
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService

class TableErrorTableTransformator extends AbstractTableModelTransformator<Collection<TableError>> {	
	TableErrorTableColumns columns;
	EnumTranslationService enumTranslationService;

	new(TableErrorTableColumns columns, EnumTranslationService enumTranslationService) {
		super()
		this.columns = columns;
		this.enumTranslationService = enumTranslationService;
	}
	
	override transformTableContent(Collection<TableError> errors, TMFactory factory) {
		for (error : errors) {
			val instance = factory.newTableRow(error)
			fill(instance, columns.Source, error, [error.source])
			fill(instance, columns.TableType, error, [enumTranslationService.translate(error.tableType).presentation])
			fill(instance, columns.RowNumber, error, [error.rowNumber])
			fill(instance, columns.LeadingObject, error, [error.leadingObject])
			fill(instance, columns.Message, error, [error.message])		
		}
		
		return factory.table
	}

	override transformTableContent(Collection<TableError> errors, TMFactory factory, Stell_Bereich controlArea) {
		transformTableContent(errors, factory)
	}
}
