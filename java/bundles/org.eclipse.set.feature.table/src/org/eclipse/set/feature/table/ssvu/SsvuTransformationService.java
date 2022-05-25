/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssvu;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.MIXED_STRING;

import java.util.Comparator;

import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.feature.table.messages.MessagesWrapper;
import org.eclipse.set.feature.table.messages.ToolboxTableName;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the ssvu table model. org.eclipse.set.feature.table
 * 
 * @author Schneider
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssvu" })
public final class SsvuTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SsvuColumns columns;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SsvuTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.columns = new SsvuColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsvuTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SsvuTableView_Heading);
		final GroupBuilder grundsatzangaben = root
				.addGroup(messages.SsvuTableView_Grundsatzangaben);
		grundsatzangaben.add(columns.von).width(1.69f);
		grundsatzangaben.add(columns.nach).width(1.69f);
		grundsatzangaben.add(columns.verwendung).width(1.85f);

		final GroupBuilder technik = root
				.addGroup(messages.SsvuTableView_Technik);
		technik.add(columns.netzart).width(2.14f);
		technik.add(columns.technikart).width(2.14f);
		technik.add(columns.schnittstelle).width(2.14f);
		technik.add(columns.bandbreite).width(2.14f);

		root.add(columns.bemerkung).width(11.32f);

		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder().sort("A", MIXED_STRING, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSsvuLong,
				toolboxTableName.ToolboxTableNameSsvuPlanningNumber,
				toolboxTableName.ToolboxTableNameSsvuShort);
	}

	/**
	 * sets the i8n messages.
	 * 
	 * @param messagesService
	 *            the messages service
	 */
	@Reference
	public void setMessages(final MessagesWrapper messagesService) {
		this.messages = messagesService.getMessages();
		toolboxTableName = messagesService.getToolboxTableName();
		messagesWrapper = messagesService;
	}
}