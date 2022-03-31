/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssln;

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
 * Service for creating the Ssln table model.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssln" })
public class SslnTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SslnColumns columns;

	private Messages messages;
	private ToolboxTableName toolboxTableName;

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslnTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SslnTableView_Heading);

		final GroupBuilder grundsatzangaben = root
				.addGroup(messages.SslnTableView_Grundsatzangaben);
		grundsatzangaben.add(columns.bereich_zone).width(1.88f);
		grundsatzangaben.add(columns.art).width(0.97f);

		final GroupBuilder unterstellungsverhaeltnis = root
				.addGroup(messages.SslnTableView_Unterstellungsverhaeltnis);
		unterstellungsverhaeltnis.add(columns.untergeordnet).width(1.4f)
				.height(LINE_HEIGHT * 2);
		unterstellungsverhaeltnis.add(columns.rang_zuschaltung).width(1.57f);
		unterstellungsverhaeltnis.add(columns.aufloesung_grenze).width(2.31f);

		final GroupBuilder grenze = root
				.addGroup(messages.SslnTableView_Grenze);
		grenze.add(columns.bez_grenze).width(4.61f);

		final GroupBuilder element = root
				.addGroup(messages.SslnTableView_Element);
		final GroupBuilder weiche_gs = element
				.addGroup(messages.SslnTableView_Element_Weiche_Gs);
		weiche_gs.add(columns.weiche_gs_frei_stellbar).width(2.98f);
		weiche_gs.add(columns.verschlossen).width(1.74f);
		final GroupBuilder signal = element
				.addGroup(messages.SslnTableView_Element_Signal);
		signal.add(columns.signal_frei_stellbar).width(2.98f);
		signal.add(columns.kennlicht).width(1.33f);
		element.add(columns.ssp).width(3.01f);
		element.add(columns.bedien_einr).width(2.29f);

		final GroupBuilder nb_r = root.addGroup(messages.SslnTableView_NB_R);
		nb_r.add(columns.bedienungshandlung).width(2.67f);

		root.add(columns.basis_bemerkung).width(7.85f);

		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", MIXED_STRING, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSslnLong,
				toolboxTableName.ToolboxTableNameSslnPlanningNumber,
				toolboxTableName.ToolboxTableNameSslnShort);
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

	@Override
	protected void buildColumns() {
		columns = new SslnColumns(messages);
	}
}
