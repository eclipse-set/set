/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.table

import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder
import org.eclipse.set.utils.table.GroupBuilder
import org.eclipse.set.feature.validation.Messages
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.nattable.utils.AbstractColumns

/** 
 * Validation table columns
 * 
 * @author Stuecker
 */
class ValidationTableColumns extends AbstractColumns {
	public final ColumnDescriptor RowIndex;
	public final ColumnDescriptor Severity;
	public final ColumnDescriptor ProblemType;
	public final ColumnDescriptor LineNumber;
	public final ColumnDescriptor ObjectType;
	public final ColumnDescriptor ObjectDesignation;
	public final ColumnDescriptor AttributeGroup;
	public final ColumnDescriptor ObjectScope;
	public final ColumnDescriptor ObjectState;
	public final ColumnDescriptor Message;

	/** 
	 * @param messages the messages
	 */
	new(Messages messages) {
		super()
		RowIndex = createNew(messages.ValidationTableColumns_Index)
		Severity = createNew(messages.ValidationTableColumns_Severity)
		ProblemType = createNew(messages.ValidationTableColumns_ProblemType)
		LineNumber = createNew(messages.ValidationTableColumns_LineNumber)
		ObjectType = createNew(messages.ValidationTableColumns_ObjectType)
		AttributeGroup = createNew(messages.ValidationTableColumns_AttributeGroup)
		ObjectDesignation = createNew(messages.ValidationTableColumns_ObjectDesignation)
		ObjectScope = createNew(messages.ValidationTableColumns_ObjectScope)
		ObjectState = createNew(messages.ValidationTableColumns_ObjectState)
		Message = createNew(messages.ValidationTableColumns_Message)
	}

	def ColumnDescriptor fillHeaderDescriptions(
		ColumnDescriptorModelBuilder builder) {
		val GroupBuilder root = builder.createRootColumn()
		root.add(RowIndex).widthPercent(5)
		root.add(Severity).widthPercent(5)
		root.add(ProblemType).widthPercent(8)
		root.add(LineNumber).widthPercent(5)
		root.add(ObjectType).widthPercent(8)
		root.add(ObjectDesignation).widthPercent(10)
		root.add(AttributeGroup).widthPercent(8)
		root.add(ObjectScope).widthPercent(5)
		root.add(ObjectState).widthPercent(4)
		root.add(Message).widthPercent(20)
		return root.getGroupRoot()
	}
}
