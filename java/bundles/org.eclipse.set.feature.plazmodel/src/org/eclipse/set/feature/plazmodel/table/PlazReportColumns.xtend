/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.table

import org.eclipse.set.feature.plazmodel.Messages
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.nattable.utils.AbstractColumns
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder
import org.eclipse.set.utils.table.GroupBuilder

/** 
 * Plaz Model table columns
 * 
 * @author Stuecker
 */
class PlazReportColumns extends AbstractColumns {
	public final ColumnDescriptor RowIndex;
	public final ColumnDescriptor Severity;
	public final ColumnDescriptor ProblemType;
	public final ColumnDescriptor LineNumber;
	public final ColumnDescriptor ObjectType;
	public final ColumnDescriptor AttributeGroup;
	public final ColumnDescriptor ObjectScope;
	public final ColumnDescriptor ObjectState;
	public final ColumnDescriptor Message;

	/** 
	 * @param messages the messages
	 */
	new(Messages messages) {
		super()
		RowIndex = createNew(messages.PlazReportColumns_RowIndex)
		Severity = createNew(messages.PlazReportColumns_Severity)
		ProblemType = createNew(messages.PlazReportColumns_ProblemType)
		LineNumber = createNew(messages.PlazReportColumns_LineNumber)
		ObjectType = createNew(messages.PlazReportColumns_ObjectType)
		AttributeGroup = createNew(messages.PlazReportColumns_AttributeGroup)
		ObjectScope = createNew(messages.PlazReportColumns_ObjectScope)
		ObjectState = createNew(messages.PlazReportColumns_ObjectState)
		Message = createNew(messages.PlazReportColumns_Message)
	}

	def ColumnDescriptor fillHeaderDescriptions(
		ColumnDescriptorModelBuilder builder) {
		val GroupBuilder root = builder.createRootColumn()
		root.add(RowIndex).width(1.25f)
		root.add(Severity).width(2.1f)
		root.add(ProblemType).width(3.5f)
		root.add(LineNumber).width(2.2f)
		root.add(ObjectType).width(3)
		root.add(AttributeGroup).width(3)
		root.add(ObjectScope).width(2)
		root.add(ObjectState).width(1.5f)
		root.add(Message).width(8.5f)
		return root.getGroupRoot()
	}
}
