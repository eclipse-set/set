/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssit;

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
 * Service for creating the Ssit table model.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssit" })
public class SsitTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SsitColumns columns;

	private Messages messages;
	private ToolboxTableName toolboxTableName;

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsitTransformator(columns, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SsitTableView_Heading);

		final GroupBuilder Grundsatzangaben = root
				.addGroup(messages.SsitTableView_Grundsatzangaben);
		Grundsatzangaben.add(columns.Bezeichnung).width(3.62f);
		Grundsatzangaben.add(columns.Zug_AEA).width(2.22f);
		Grundsatzangaben.add(columns.Bauart).width(2.33f);

		final GroupBuilder Befestigung = Grundsatzangaben
				.addGroup(messages.SsitTableView_Befestigung);
		Befestigung.add(columns.Art).width(1.9f);
		Befestigung.add(columns.Strecke).width(1.31f);
		Befestigung.add(columns.km).width(1.74f);

		final GroupBuilder Bedien_Anz_Elemente = root
				.addGroup(messages.SsitTableView_Bedien_Anz_Elemente);
		Bedien_Anz_Elemente.add(columns.Melder).width(2.12f);
		Bedien_Anz_Elemente.add(columns.Schalter).width(2.12f);
		Bedien_Anz_Elemente.add(columns.Taste).width(2.12f);

		final GroupBuilder Nahbedienbereich = Bedien_Anz_Elemente
				.addGroup(messages.SsitTableView_Nahstellbereich);
		Nahbedienbereich.add(columns.Anf_NB).width(1.86f)
				.height(LINE_HEIGHT * 1.5f);
		Nahbedienbereich.add(columns.Fertigmeldung).width(1.52f);
		Nahbedienbereich.add(columns.Weichengruppe).width(1.42f);
		Nahbedienbereich.add(columns.Umst_Weiche).width(1.69f);
		Nahbedienbereich.add(columns.Umst_Gs).width(1.71f);
		Nahbedienbereich.add(columns.Umst_Sig).width(1.71f);
		Nahbedienbereich.add(columns.Freigabe_Ssp).width(2.29f);
		Nahbedienbereich.add(columns.An_Zeit_Hupe, messages.Common_UnitSeconds)
				.width(1.86f);

		root.add(columns.basis_bemerkung).width(3.81f);

		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", LEXICOGRAPHICAL, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSsitLong,
				toolboxTableName.ToolboxTableNameSsitPlanningNumber,
				toolboxTableName.ToolboxTableNameSsitShort);
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
		columns = new SsitColumns(messages);
	}
}
