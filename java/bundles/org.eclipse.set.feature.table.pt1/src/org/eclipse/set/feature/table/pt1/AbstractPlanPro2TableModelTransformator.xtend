/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1

import java.util.Comparator
import java.util.Set
import org.eclipse.emf.common.util.Enumerator
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.AbstractTableModelTransformator
import org.eclipse.set.utils.table.TMFactory

import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*

abstract class AbstractPlanPro2TableModelTransformator extends AbstractTableModelTransformator<MultiContainer_AttributeGroup> {
	protected val FootnoteTransformation footnoteTransformation = new FootnoteTransformation()
	protected val EnumTranslationService enumTranslationService
	protected val Set<ColumnDescriptor> cols = newHashSet

	/**
	 * Compares mixed strings groupwise.
	 */
	protected static val Comparator<String> MIXED_STRING_COMPARATOR = ToolboxConstants.
		LST_OBJECT_NAME_COMPARATOR

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super()
		this.enumTranslationService = enumTranslationService
		this.cols.addAll(cols)
	}

	protected def void fillFootnotes(TableRow row, Basis_Objekt object) {
		footnoteTransformation.transform(object, row)

		// Ensure a cell exists for the last column to fill footnotes into
		val column = this.cols.filter[columnPosition !== null].sortBy [
			columnPosition
		].last
		val cell = row.getCell(column)
		if (cell.content === null)
			cell.content = TablemodelFactory.eINSTANCE.createStringCellContent
	}

	/**
	 * Translates the value of the field "wert" of the provided basis attribute.
	 * 
	 * @param owner the basis attribute owner
	 * 
	 * @return the translation or <code>null</code>, if the owner is <code>null</code> 
	 * or the owner has no field "wert" or the value of "wert" is neither of type Boolean 
	 * nor of type Enumerator
	 */
	def String translate(BasisAttribut_AttributeGroup owner) {
		try {
			if (owner === null) {
				return null
			}
			val wertField = owner.class.declaredFields.findFirst [
				it.name === "wert"
			]
			if (wertField === null) {
				return null
			}
			wertField.accessible = true
			val wert = wertField.get(owner);
			if (wert instanceof Boolean) {
				return translate(wert);
			}
			if (wert instanceof Enumerator) {
				return enumTranslationService.translate(owner, wert).alternative
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e)
		}

	}

	/**
	 * Translates the boolean via the enum translation service.
	 * 
	 * @param value the value
	 * 
	 * @return the translation or <code>null</code>, if the value is <code>null</code>
	 */
	def String translate(Boolean value) {
		if (value === null) {
			return null
		}
		return enumTranslationService.translate(value).alternative
	}

	def ColumnDescriptor getColumn(Set<ColumnDescriptor> columns, String pos) {
		val column = columns.findFirst [
			columnPosition !== null && columnPosition.equals(pos)
		]
		if (column === null) {
			throw new RuntimeException("Missing column " + pos);
		}
		return column;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		return transformTableContent(container, factory, null)
	}
}
