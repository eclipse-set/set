/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssli;

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
 * Service for creating the ssli table model.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssli" })
public class SsliTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SsliColumns columns;

	private Messages messages;
	private ToolboxTableName toolboxTableName;

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsliTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder rootBuilder = builder
				.createRootColumn(messages.Ssli_Heading);
		final GroupBuilder grundsatzangaben = rootBuilder
				.addGroup(messages.Ssli_Grundsatzangaben);
		grundsatzangaben.add(columns.Bezeichnung_Inselgleis).width(1.99f);
		grundsatzangaben.add(columns.Laenge, messages.Common_UnitMeter)
				.width(1.71f);
		final GroupBuilder begrenzendeSignale = grundsatzangaben
				.addGroup(messages.Ssli_Grundsatzangaben_Begrenzende_Signale);
		begrenzendeSignale.add(columns.PY_Richtung).width(1.86f);
		begrenzendeSignale.add(columns.NX_Richtung).width(1.86f);
		final GroupBuilder ausschlussFahrten = rootBuilder
				.addGroup(messages.Ssli_Ausschluss_Fahrten);
		ausschlussFahrten.add(columns.Zugausfahrt).width(2.12f);
		final GroupBuilder rangierfahrt = ausschlussFahrten
				.addGroup(messages.Ssli_Ausschluss_Fahrten_Rangierfahrt);
		rangierfahrt.add(columns.Einfahrt).width(1.93f);
		rangierfahrt.add(columns.Ausfahrt).width(1.93f);
		rootBuilder.add(columns.basis_bemerkung).width(23.79f);
		return rootBuilder.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder().sort("A", MIXED_STRING, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSsliLong,
				toolboxTableName.ToolboxTableNameSsliPlanningNumber,
				toolboxTableName.ToolboxTableNameSsliShort);
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

	@Override
	protected void buildColumns() {
		columns = new SsliColumns(messages);
	}
}
