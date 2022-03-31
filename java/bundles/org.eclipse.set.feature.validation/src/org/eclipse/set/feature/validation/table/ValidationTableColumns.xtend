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
	public final ColumnDescriptor Severity;
	public final ColumnDescriptor ProblemType;
	public final ColumnDescriptor LineNumber;
	public final ColumnDescriptor ObjectType;
	public final ColumnDescriptor AttributeGroup;
	public final ColumnDescriptor ObjectScope;
	public final ColumnDescriptor Message;

	/** 
	 * @param messages the messages
	 */
	new(Messages messages) {
		super()
		Severity = createNew(messages.ValidationTableColumns_Severity)
		ProblemType = createNew(messages.ValidationTableColumns_ProblemType)
		LineNumber = createNew(messages.ValidationTableColumns_LineNumber)
		ObjectType = createNew(messages.ValidationTableColumns_ObjectType)
		AttributeGroup = createNew(
			messages.ValidationTableColumns_AttributeGroup)
		ObjectScope = createNew(messages.ValidationTableColumns_ObjectScope)
		Message = createNew(messages.ValidationTableColumns_Message)
	}

	def ColumnDescriptor fillHeaderDescriptions(
		ColumnDescriptorModelBuilder builder) {
		val GroupBuilder root = builder.createRootColumn()
		root.add(Severity).width(1.8f)
		root.add(ProblemType).width(4)
		root.add(LineNumber).width(1.9f)
		root.add(ObjectType).width(3)
		root.add(AttributeGroup).width(3)
		root.add(ObjectScope).width(2)
		root.add(Message).width(9)
		return root.getGroupRoot()
	}
}
