/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview

import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder
import org.eclipse.set.utils.table.GroupBuilder
import org.eclipse.set.feature.table.messages.Messages
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.nattable.utils.AbstractColumns

/** 
 * Table error table columns
 * 
 * @author Peters
 */
class TableErrorTableColumns extends AbstractColumns {
	public final ColumnDescriptor LeadingObject;
	public final ColumnDescriptor RowNumber;
	public final ColumnDescriptor Source;
	public final ColumnDescriptor Message;

	/** 
	 * @param messages the messages
	 */
	new(Messages messages) {
		super()
		Source = createNew(messages.TableErrorTableColumns_Source)
		LeadingObject = createNew(messages.TableErrorTableColumns_LeadingObject)
		Message = createNew(messages.TableErrorTableColumns_Message)
		RowNumber = createNew(messages.TableErrorTableColumns_RowNumber)
		
	}

	def ColumnDescriptor fillHeaderDescriptions(
		ColumnDescriptorModelBuilder builder) {
		val GroupBuilder root = builder.createRootColumn()
		root.add(Source).widthPercent(10)
		root.add(RowNumber).widthPercent(5)
		root.add(LeadingObject).widthPercent(20)
		root.add(Message).widthPercent(65)
		return root.getGroupRoot()
	}
}
