/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslb;

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
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the sslb table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sslb" })
public final class SslbTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SslbColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SslbTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SslbColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslbTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SslbTableView_Heading);

		final GroupBuilder strecke = root
				.addGroup(messages.SslbTableView_Strecke);
		strecke.add(cols.nummer).width(1.33f);
		strecke.add(cols.gleis).width(0.89f);
		strecke.add(cols.betriebsfuehrung).width(2.29f);

		final GroupBuilder fundamental = root
				.addGroup(messages.SslbTableView_Grundsatzangaben);
		final GroupBuilder von = fundamental
				.addGroup(messages.SslbTableView_Von);
		von.add(cols.betriebsst_Start).width(1.33f);
		von.add(cols.bauform_Start).width(1.88f);

		final GroupBuilder nach = fundamental
				.addGroup(messages.SslbTableView_Nach);
		nach.add(cols.betriebsst_Ziel).width(1.33f);
		nach.add(cols.bauform_Ziel).width(1.88f);

		fundamental.add(cols.blockschaltung).width(1.48f);
		fundamental.add(cols.schutzuebertrager).width(1.08f);

		final GroupBuilder erlaubnis = root
				.addGroup(messages.SslbTableView_Erlaubnis);
		erlaubnis.add(cols.staendig).width(1.61f).height(LINE_HEIGHT * 2);
		erlaubnis.add(cols.holen).width(1.06f);
		erlaubnis.add(cols.ruecklauf_autom).width(1.86f);
		erlaubnis.add(cols.abgabespeicherung).width(1.86f);
		erlaubnis.add(cols.abh_d_weg_rf).width(2.46f);

		final GroupBuilder blockmeldung = root
				.addGroup(messages.SslbTableView_Blockmeldung);
		final GroupBuilder anrueckabschnitt = blockmeldung
				.addGroup(messages.SslbTableView_Anrueckabschnitt);
		anrueckabschnitt.add(cols.anrueckabschnitt_bezeichnung).width(1.9f);
		anrueckabschnitt.add(cols.anrueckabschnitt_anordnung).width(1.74f);

		blockmeldung.add(cols.zugschluss).width(1.8f);
		blockmeldung.add(cols.raeumungspruefung).width(1.69f);

		final GroupBuilder akustischeMeldung = root
				.addGroup(messages.SslbTableView_AkustischeMeldung);
		akustischeMeldung.add(cols.vorblock).width(1.38f);
		akustischeMeldung.add(cols.rueckblock).width(1.65f);

		final GroupBuilder awanst = root
				.addGroup(messages.SslbTableView_Awanst);
		awanst.add(cols.awanst_bez_bed).width(2.41f);

		root.add(cols.basis_bemerkung).width(2.48f);

		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("D", LEXICOGRAPHICAL, ASCENDING) //$NON-NLS-1$
				.sort("F", LEXICOGRAPHICAL, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSslbLong,
				toolboxTableName.ToolboxTableNameSslbPlanningNumber,
				toolboxTableName.ToolboxTableNameSslbShort);
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