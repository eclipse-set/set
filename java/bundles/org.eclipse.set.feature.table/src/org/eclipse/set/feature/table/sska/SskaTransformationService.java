/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sska;

import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;
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
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.ESTW_Zentraleinheit;
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
				"table.shortcut=sska" })
public final class SskaTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SskaColumns columns;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SskaTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.columns = new SskaColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskaTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskaTableView_Heading);

		final GroupBuilder fundamental = root
				.addGroup(messages.SskaTableView_Grundsatzangaben);
		fundamental.add(columns.zentraleinheit_bezeichnung).width(2.37f);
		fundamental.add(columns.art).width(1.93f);
		fundamental.add(columns.bauart).width(1.86f);

		final GroupBuilder unterbringung = fundamental.addGroup(
				messages.SskaTableView_Grundsatzangaben_Unterbringung);
		unterbringung.add(columns.unterbringung_art).width(1.88f);
		unterbringung.add(columns.ort).width(2.92f);
		unterbringung.add(columns.strecke).width(1.08f);
		unterbringung.add(columns.km).width(1.12f);

		final GroupBuilder verknuepfungen = root
				.addGroup(messages.SskaTableView_Verknuepfungen);
		final GroupBuilder information = verknuepfungen
				.addGroup(messages.SskaTableView_Verknuepfungen_Information);
		information.add(columns.information_primaer).width(2.12f);
		information.add(columns.information_sekundaer).width(2.12f);

		final GroupBuilder energie = verknuepfungen
				.addGroup(messages.SskaTableView_Verknuepfungen_Energie);
		energie.add(columns.energie_primaer).width(2.12f);
		energie.add(columns.energie_sekundaer).width(2.41f);

		final GroupBuilder bedienung = verknuepfungen
				.addGroup(messages.SskaTableView_Verknuepfungen_Bedienung);
		bedienung.add(columns.lokal).width(0.97f);
		bedienung.add(columns.bezirk).width(1.27f);
		bedienung.add(columns.zentrale).width(1.19f);
		bedienung.add(columns.notbp).width(1.1f);

		final GroupBuilder ipAdressangaben = root
				.addGroup(messages.SskaTableView_IP_Adressangaben);
		ipAdressangaben.add(columns.GFK_Kategorie).width(1.29f);
		ipAdressangaben.add(columns.Regionalbereich).width(1.16f);
		final GroupBuilder blau = ipAdressangaben.addGroup(
				messages.SskaTableView_IP_Adressangaben_Adressblock_Blau);
		blau.add(columns.IPv4_Blau).width(1.59f);
		blau.add(columns.IPv6_Blau).width(1.99f);
		final GroupBuilder grau = ipAdressangaben.addGroup(
				messages.SskaTableView_IP_Adressangaben_Adressblock_Grau);
		grau.add(columns.IPv4_Grau).width(1.59f);
		grau.add(columns.IPv6_Grau).width(1.99f);

		root.add(columns.basis_bemerkung).width(2.6f);

		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort(ESTW_Zentraleinheit.class, Aussenelementansteuerung.class)
				.sort("A", LEXICOGRAPHICAL, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSskaLong,
				toolboxTableName.ToolboxTableNameSskaPlanningNumber,
				toolboxTableName.ToolboxTableNameSskaShort);
	}

	/**
	 * sets the i8n messages.
	 * 
	 * @param messagesWrapper
	 *            the messages wrapper
	 */
	@Reference
	public void setMessages(final MessagesWrapper messagesWrapper) {
		this.messages = messagesWrapper.getMessages();
		toolboxTableName = messagesWrapper.getToolboxTableName();
		this.messagesWrapper = messagesWrapper;
	}
}