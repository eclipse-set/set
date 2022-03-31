/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssld;

import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.MIXED_STRING;
import static org.eclipse.set.utils.table.sorting.SortDirection.ASCENDING;

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
 * Service for creating the ssld table model.
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssld" })
public final class SsldTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SsldColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SsldTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SsldColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsldTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SsldTableView_Heading);
		final GroupBuilder fundamental = root
				.addGroup(messages.SsldTableView_HeadingFundamental);
		fundamental.add(cols.von).width(1.29f);
		fundamental.add(cols.bis).dimension(1.59f, LINE_HEIGHT * 2);
		fundamental.add(cols.gefahrpunkt).width(1.59f);
		fundamental.add(cols.pzb_gefahrpunkt).width(1.59f);
		fundamental.add(cols.basis_bezeichnung).width(1.71f);
		final GroupBuilder properties = root
				.addGroup(messages.SsldTableView_HeadingProperties);
		properties
				.add(cols.zielgeschwindigkeit_moeglich, messages.Common_UnitKmh)
				.width(2.5f);
		final GroupBuilder length = properties
				.addGroup(messages.SsldTableView_HeadingPropertiesLength);
		length.add(cols.soll, messages.Common_UnitMeter).width(0.61f);
		length.add(cols.ist, messages.Common_UnitMeter).width(0.61f);
		length.add(cols.freigemeldet, messages.Common_UnitMeter).width(1.31f);
		properties.add(cols.massgebende_neigung, messages.Common_UnitIncline)
				.width(1.74f);

		final GroupBuilder dependencies = root
				.addGroup(messages.SsldTableView_HeadingDependencies);
		final GroupBuilder crossings = dependencies.addGroup(
				messages.SsldTableView_HeadingDependenciesPointsCrossings);
		crossings.add(cols.mit_verschluss).width(1.52f);
		crossings.add(cols.ohne_verschluss).width(1.52f);

		dependencies.add(cols.relevante_fma).width(1.29f);
		dependencies.add(cols.v_aufwertung_verzicht).width(1.5f);
		dependencies.add(cols.erlaubnisabhaengig).width(1.38f);
		final GroupBuilder unblocking = root
				.addGroup(messages.SsldTableView_HeadingUnblocking);
		unblocking.add(cols.manuell).width(1.1f);
		final GroupBuilder destinationTrack = unblocking.addGroup(
				messages.SsldTableView_HeadingUnblockingDestinationTrack);
		destinationTrack.add(cols.bezeichnung).width(2.2f);
		destinationTrack.add(cols.laenge, messages.Common_UnitMeter)
				.width(2.2f);
		unblocking.add(cols.verzoegerung, messages.Common_UnitSeconds)
				.width(2.29f);
		root.add(cols.basis_bemerkung).width(7.39f);
		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", MIXED_STRING, ASCENDING) //$NON-NLS-1$
				.sort("E", MIXED_STRING, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSsldLong,
				toolboxTableName.ToolboxTableNameSsldPlanningNumber,
				toolboxTableName.ToolboxTableNameSsldShort);
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