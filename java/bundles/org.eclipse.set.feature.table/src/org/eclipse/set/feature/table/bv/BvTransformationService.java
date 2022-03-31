/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.bv;

import org.eclipse.set.feature.table.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.feature.table.messages.MessagesWrapper;
import org.eclipse.set.feature.table.messages.ToolboxTableName;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;
import org.eclipse.set.utils.table.TableModelTransformator;
import org.eclipse.set.utils.table.TableTransformationService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the bv table model.
 * 
 * @author Schaefer
 * 
 * @usage development
 */
@Component(service = {
		TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=bv" })
public class BvTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private BvColumns columns;
	private Messages messages;
	private ToolboxTableName toolboxTableName;

	@Override
	public TableModelTransformator<MultiContainer_AttributeGroup> createTransformator() {
		return new BvTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder rootBuilder = builder
				.createRootColumn(messages.Bv_Heading);
		rootBuilder.add(columns.LstObjektName);
		rootBuilder.add(columns.Bezeichnung);
		rootBuilder.add(columns.Attributname);
		rootBuilder.add(columns.Attributwert);
		rootBuilder.add(columns.Bearbeitungsvermerk);
		return rootBuilder.getGroupRoot();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameBvLong,
				toolboxTableName.ToolboxTableNameBvPlanningNumber,
				toolboxTableName.ToolboxTableNameBvShort);
	}

	/**
	 * @param messagesWrapper
	 *            the messages wrapper
	 */
	@Reference
	public void setMessages(final MessagesWrapper messagesWrapper) {
		messages = messagesWrapper.getMessages();
		toolboxTableName = messagesWrapper.getToolboxTableName();
		this.messagesWrapper = messagesWrapper;
	}

	@Override
	protected void buildColumns() {
		columns = new BvColumns(messages);
	}
}
