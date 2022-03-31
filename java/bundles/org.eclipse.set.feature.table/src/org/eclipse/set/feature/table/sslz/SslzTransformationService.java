/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslz;

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
 * Service for creating the sslz table model.
 * 
 * @author Rumpf/Schaefer
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sslz" })
public class SslzTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SslzColumns columns;

	private Messages messages;
	private ToolboxTableName toolboxTableName;

	/**
	 * Initialization.
	 */
	public SslzTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		columns = new SslzColumns(messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslzTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SslzTableView_Heading);

		addGrundsatzangaben(root);
		addEinstellung(root);
		addAbhaengigkeiten(root);
		addSignalisierung(root);

		root.add(columns.basis_bemerkung).width(3.64f);

		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("B", MIXED_STRING, ASCENDING) //$NON-NLS-1$
				.sort("C", MIXED_STRING, ASCENDING) //$NON-NLS-1$
				.sort("D", MIXED_STRING, ASCENDING) //$NON-NLS-1$
				.sort("F", MIXED_STRING, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSslzLong,
				toolboxTableName.ToolboxTableNameSslzPlanningNumber,
				toolboxTableName.ToolboxTableNameSslzShort);
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

	private void addAbhaengigkeiten(final GroupBuilder builder) {
		final GroupBuilder abhaengigkeiten = builder
				.addGroup(messages.SslzTableView_Abhaengigkeiten);
		abhaengigkeiten.add(columns.inselgleis).width(1.29f);
		abhaengigkeiten.add(columns.Ueberwachte_Ssp).width(1.57f);
		abhaengigkeiten.add(columns.abhaengiger_bue).width(1.33f);
		abhaengigkeiten.add(columns.nichthaltfallabschnitt).width(1.5f);
		abhaengigkeiten.add(columns.Zweites_Haltfallkrit).width(1.33f);
		abhaengigkeiten.add(columns.anrueckverschluss).width(1.5f);
	}

	private void addDweg(final GroupBuilder builder) {
		final GroupBuilder dweg = builder
				.addGroup(messages.SslzTableView_Grundsatzangaben_Dweg);
		dweg.add(columns.durchrutschweg_bezeichnung).width(1.16f);
	}

	private void addEinstellung(final GroupBuilder builder) {
		final GroupBuilder einstellung = builder
				.addGroup(messages.SslzTableView_Einstellung);
		einstellung.add(columns.autom_einstellung).width(1.06f);
		einstellung.add(columns.f_bedienung).width(0.93f);
	}

	private void addFahrweg(final GroupBuilder builder) {
		final GroupBuilder fahrweg = builder
				.addGroup(messages.SslzTableView_Grundsatzangaben_Fahrweg);
		fahrweg.add(columns.start).width(1.12f);
		fahrweg.add(columns.ziel).width(1.12f);
		fahrweg.add(columns.nummer).width(0.57f);
		fahrweg.add(columns.entscheidungsweiche).width(1.93f)
				.height(LINE_HEIGHT * 1.5f);
	}

	private void addGeschwindigkeit(final GroupBuilder builder) {
		final GroupBuilder vStart = builder.addGroup(
				messages.SslzTableView_Signalisierung_GeschwAmStartsignal);
		vStart.add(columns.hg, messages.Common_UnitKmh).width(0.78f);
		vStart.add(columns.fahrweg, messages.Common_UnitKmh).width(0.83f);
		vStart.add(columns.dweg, messages.Common_UnitKmh).width(0.89f);
		vStart.add(columns.besonders, messages.Common_UnitKmh).width(0.99f);
		vStart.add(columns.signalisierung_zs3).width(0.97f);
		vStart.add(columns.aufwertung_mwtfstr).width(1.5f);
	}

	private void addGrundsatzangaben(final GroupBuilder builder) {
		final GroupBuilder grundsatzangaben = builder
				.addGroup(messages.SslzTableView_Grundsatzangaben);
		grundsatzangaben.add(columns.grundsatzangaben_bezeichnung).width(2.86f);
		addFahrweg(grundsatzangaben);
		addDweg(grundsatzangaben);
		grundsatzangaben.add(columns.art).width(0.51f);
	}

	private void addImFahrweg(final GroupBuilder builder) {
		final GroupBuilder imFw = builder
				.addGroup(messages.SslzTableView_Signalisierung_ImFahrweg);
		imFw.add(columns.im_fahrweg_zs3).width(1.61f);
		imFw.add(columns.im_fahrweg_zs6).width(1.27f);
		imFw.add(columns.kennlicht).width(1.27f);
		imFw.add(columns.vorsignalisierung).width(1.27f);
	}

	private void addSignalisierung(final GroupBuilder builder) {
		final GroupBuilder signalisierung = builder
				.addGroup(messages.SslzTableView_Signalisierung);
		addGeschwindigkeit(signalisierung);
		addSonstiges(signalisierung);
		addImFahrweg(signalisierung);
	}

	private void addSonstiges(final GroupBuilder builder) {
		final GroupBuilder sonstiges = builder
				.addGroup(messages.SslzTableView_Signalisierung_Sonstiges);
		sonstiges.add(columns.zusatzlicht).width(0.57f);
		sonstiges.add(columns.zs3v).width(0.59f);
		sonstiges.add(columns.zs2).width(0.66f);
		sonstiges.add(columns.zs2v).width(0.61f);
		sonstiges.add(columns.sonstiges_startsignal_zs6).width(0.66f);
		sonstiges.add(columns.zs13).width(0.61f);
	}
}