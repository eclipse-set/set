/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskt;

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
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Technik_Standort;
import org.eclipse.set.toolboxmodel.Bedienung.Bedien_Standort;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the sskt table model.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sskt" })
public class SsktTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SsktColumns columns;
	private Messages messages;
	private ToolboxTableName toolboxTableName;

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsktTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder rootBuilder = builder
				.createRootColumn(messages.Sskt_Heading);
		addGrundsatzangaben(rootBuilder);
		addIPAdressangaben(rootBuilder);
		rootBuilder.add(columns.basis_bemerkung).width(3.32f);
		return rootBuilder.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort(Technik_Standort.class, Bedien_Standort.class)
				.sort("A", MIXED_STRING, ASC).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSsktLong,
				toolboxTableName.ToolboxTableNameSsktPlanningNumber,
				toolboxTableName.ToolboxTableNameSsktShort);
	}

	/**
	 * @param messagesService
	 *            the messages service
	 */
	@Reference
	public void setMessages(final MessagesWrapper messagesService) {
		messages = messagesService.getMessages();
		toolboxTableName = messagesService.getToolboxTableName();
		messagesWrapper = messagesService;
	}

	private void addGrundsatzangaben(final GroupBuilder rootBuilder) {
		final GroupBuilder grundsatzangaben = rootBuilder
				.addGroup(messages.Sskt_Grundsatzangaben);
		grundsatzangaben.add(columns.Bezeichnung).width(2.37f);
		grundsatzangaben.add(columns.Grundsatzangaben_Art).width(1.93f);
		grundsatzangaben.add(columns.Bedien_Standort).width(1.86f);
		final GroupBuilder unterbringung = grundsatzangaben
				.addGroup(messages.Sskt_Unterbringung);
		unterbringung.add(columns.Unterbringung_Art).width(1.88f);
		unterbringung.add(columns.Ort).width(2.92f);
		unterbringung.add(columns.Strecke).width(1.08f);
		unterbringung.add(columns.km).width(1.12f);
	}

	private void addIPAdressangaben(final GroupBuilder rootBuilder) {
		final GroupBuilder ipAdressangaben = rootBuilder
				.addGroup(messages.Sskt_IP_Adressangaben);
		ipAdressangaben.add(columns.Regionalbereich).width(1.19f);
		final GroupBuilder blau = ipAdressangaben
				.addGroup(messages.Sskt_Adressblock_Blau);
		blau.add(columns.IPv4_Blau).width(2.5f);
		blau.add(columns.IPv6_Blau).width(2.98f);
		final GroupBuilder grau = ipAdressangaben
				.addGroup(messages.Sskt_Adressblock_Grau);
		grau.add(columns.IPv4_Grau).width(2.5f);
		grau.add(columns.IPv6_Grau).width(2.98f);
		final GroupBuilder teilsystem = ipAdressangaben
				.addGroup(messages.Sskt_Teilsystem);
		teilsystem.add(columns.Teilsystem_Art).width(1.93f);
		teilsystem.add(columns.TS_Blau).width(2.98f);
		teilsystem.add(columns.TS_Grau).width(2.98f);
	}

	@Override
	protected void buildColumns() {
		columns = new SsktColumns(messages);
	}
}
