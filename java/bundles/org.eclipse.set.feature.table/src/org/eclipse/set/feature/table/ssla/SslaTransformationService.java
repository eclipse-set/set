/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssla;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;

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
 * Service for creating the ssld table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssla" })
public final class SslaTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SslaColumns columns;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SslaTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.columns = new SslaColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslaTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SslaTableView_Heading);
		final GroupBuilder fundamental = root
				.addGroup(messages.SslaTableView_Grundsatzangaben_Fahrweg);

		fundamental.add(columns.basis_bezeichnung).width(2.06f);

		final GroupBuilder fahrweg = fundamental
				.addGroup(messages.SslaTableView_Grundsatzangaben_Fahrweg);
		fahrweg.add(columns.start).width(1.14f);
		fahrweg.add(columns.ziel).width(1.24f);

		fundamental.add(columns.durchrutschweg_ziel).width(1.01f);
		fundamental.add(columns.art).width(0.98f);

		root.add(columns.unterwegssignal).width(9.26f);
		root.add(columns.basis_bemerkung).width(5.05f);

		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder().sort("A", LEXICOGRAPHICAL, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSslaLong,
				toolboxTableName.ToolboxTableNameSslaPlanningNumber,
				toolboxTableName.ToolboxTableNameSslaShort);
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