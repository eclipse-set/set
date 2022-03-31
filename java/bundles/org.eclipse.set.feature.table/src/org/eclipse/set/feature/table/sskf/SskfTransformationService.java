/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskf;

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
 * Service for creating the sskf table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sskf" })
public final class SskfTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SskfColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SskfTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SskfColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskfTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskfTableView_Heading);
		final GroupBuilder fundamental = root
				.addGroup(messages.SskfTableView_Grundsatzangaben);
		fundamental.add(cols.bezeichnung_freimeldeabschnitt).width(2.83f);

		final GroupBuilder teilabschnitt = fundamental.addGroup(
				messages.SskfTableView_Grundsatzangaben_Teilabschnitt);
		teilabschnitt.add(cols.ta_bez).width(1.14f);
		teilabschnitt.add(cols.ta_e_a).width(1.14f);

		fundamental.add(cols.art).width(1.22f);
		fundamental.add(cols.typ).width(2.59f);

		final GroupBuilder auswertung = root
				.addGroup(messages.SskfTableView_Auswertung);
		auswertung.add(cols.aea).width(1.69f);
		final GroupBuilder uebertragung = auswertung
				.addGroup(messages.SskfTableView_Auswertung_Uebertragung);
		uebertragung.add(cols.uebertragung_von).width(1.43f);
		uebertragung.add(cols.uebertragung_nach).width(1.43f);
		uebertragung.add(cols.uebertragung_typ).width(3.28f);

		final GroupBuilder ftgs = root.addGroup(messages.SskfTableView_Ftgs);
		final GroupBuilder ftgslaenge = ftgs
				.addGroup(messages.SskfTableView_Ftgs_Laenge);
		ftgslaenge.add(cols.ls, messages.Common_UnitMeter).width(1.14f);
		ftgslaenge.add(cols.l1, messages.Common_UnitMeter).width(1.14f);
		ftgslaenge.add(cols.l2, messages.Common_UnitMeter).width(1.14f);
		ftgslaenge.add(cols.l3, messages.Common_UnitMeter).width(1.14f);

		final GroupBuilder nftf = root
				.addGroup(messages.SskfTableView_NfTf_GSK);
		final GroupBuilder nftflaenge = nftf
				.addGroup(messages.SskfTableView_NfTf_GSK_Laenge);
		nftflaenge.add(cols.elektr, messages.Common_UnitMeter).width(1.14f);
		nftflaenge.add(cols.beeinfl, messages.Common_UnitMeter).width(1.14f);

		final GroupBuilder sonstiges = root
				.addGroup(messages.SskfTableView_Sonstiges);
		sonstiges.add(cols.Weiche).width(1.69f);
		final GroupBuilder ola = sonstiges
				.addGroup(messages.SskfTableView_Sonstiges_OlA)
				.height(LINE_HEIGHT * 2);
		ola.add(cols.schaltgruppe).width(1.69f);
		ola.add(cols.Bezeichner).width(1.69f);
		final GroupBuilder rbmin = sonstiges
				.addGroup(messages.SskfTableView_Sonstiges_Rbmin);
		rbmin.add(cols.rbmin_mit, messages.Common_UnitOhm_m).width(1.43f);
		sonstiges.add(cols.hfmeldung).width(1.43f);
		sonstiges.add(cols.funktion).width(1.43f);

		root.add(cols.basis_bemerkung).width(5.82f);
		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", MIXED_STRING, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSskfLong,
				toolboxTableName.ToolboxTableNameSskfPlanningNumber,
				toolboxTableName.ToolboxTableNameSskfShort);
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