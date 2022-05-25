/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslr;

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
 * Service for creating the sslr table model.
 * 
 * @author Schneider/Schaefer
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sslr" })
public class SslrTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SslrColumns columns;

	private Messages messages;
	private ToolboxTableName toolboxTableName;

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslrTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder rootBuilder = builder
				.createRootColumn(messages.Sslr_Heading);
		addGrundsatzangaben(rootBuilder);
		addEinstellung(rootBuilder);
		addAbhaengigkeiten(rootBuilder);
		rootBuilder.add(columns.basis_bemerkung).width(12.15f);
		return rootBuilder.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder().sort("B", MIXED_STRING, ASC) //$NON-NLS-1$
				.sort("C", MIXED_STRING, ASC) //$NON-NLS-1$
				.sort("D", MIXED_STRING, ASC).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSslrLong,
				toolboxTableName.ToolboxTableNameSslrPlanningNumber,
				toolboxTableName.ToolboxTableNameSslrShort);
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

	private void addAbhaengigkeiten(final GroupBuilder rootBuilder) {
		final GroupBuilder abhaengigkeiten = rootBuilder
				.addGroup(messages.Sslr_Abhaengigkeiten);

		final GroupBuilder inselgleis = abhaengigkeiten
				.addGroup(messages.Sslr_Abhaengigkeiten_Inselgleis);
		inselgleis.add(columns.InselgleisBezeichnung).width(1.29f);
		inselgleis.add(columns.Gegenfahrtausschluss).width(1.29f);

		abhaengigkeiten.add(columns.Gleisfreimeldung).width(1.42f);
		abhaengigkeiten.add(columns.FwWeichen_mit_Fla).width(2.33f);
		abhaengigkeiten.add(columns.Ueberwachte_Ssp).width(2.37f);
		abhaengigkeiten.add(columns.Abhaengiger_BUe).width(1.5f);
		abhaengigkeiten.add(columns.Ziel_erlaubnisabh).width(1.33f);
		abhaengigkeiten.add(columns.Aufloes_Fstr).width(2.33f);
	}

	private void addEinstellung(final GroupBuilder rootBuilder) {
		final GroupBuilder einstellung = rootBuilder
				.addGroup(messages.Sslr_Einstellung);
		einstellung.add(columns.Autom_Einstellung).width(1.78f);
		einstellung.add(columns.F_Bedienung).width(1.42f);
	}

	private void addGrundsatzangaben(final GroupBuilder rootBuilder) {
		final GroupBuilder grundsatzangaben = rootBuilder
				.addGroup(messages.Sslr_Grundsatzangaben);
		grundsatzangaben.add(columns.Bezeichnung).width(2.71f);
		final GroupBuilder fahrweg = grundsatzangaben
				.addGroup(messages.Sslr_Grundsatzangaben_Fahrweg);
		fahrweg.add(columns.Start).width(1.12f);
		fahrweg.add(columns.Ziel).width(1.12f);
		fahrweg.add(columns.Nummer).width(0.57f);
		fahrweg.add(columns.Entscheidungsweiche).width(2.77f)
				.height(LINE_HEIGHT * 2);
		grundsatzangaben.add(columns.Art).width(0.61f);
	}

	@Override
	protected void buildColumns() {
		columns = new SslrColumns(messages);
	}
}
