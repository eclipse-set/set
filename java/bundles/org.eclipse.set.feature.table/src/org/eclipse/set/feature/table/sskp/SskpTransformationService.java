/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskp;

import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;
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
 * Service for creating the sskp table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sskp" })
public final class SskpTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SskpColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SskpTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SskpColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskpTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskpTableView_Heading);

		final GroupBuilder grundsatzangaben = root
				.addGroup(messages.SskpTableView_Grundsatzangaben);
		grundsatzangaben.add(cols.signal_weiche).width(1.88f);
		grundsatzangaben.add(cols.pzb_schutzpunkt).width(1.71f);
		final GroupBuilder schutzstrecke = grundsatzangaben.addGroup(
				messages.SskpTableView_Grundsatzangaben_PzbSchutzstrecke);
		schutzstrecke
				.add(cols.pzb_schutzstrecke_soll, messages.Common_UnitMeter)
				.width(1.12f);
		schutzstrecke.add(cols.pzb_schutzstrecke_ist, messages.Common_UnitMeter)
				.width(1.12f);
		grundsatzangaben.add(cols.wirkfrequenz, messages.Common_UnitKiloherz)
				.width(1.42f);
		grundsatzangaben.add(cols.abstand_zu, messages.Common_UnitMeter)
				.width(1.9f);
		grundsatzangaben.add(cols.wirksamkeit).width(1.59f);

		final GroupBuilder geschw = root.addGroup(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung);
		geschw.add(cols.pruefgeschwindigkeit).width(1.19f);
		geschw.add(cols.pruefzeit, messages.Common_UnitSeconds).width(0.93f);
		geschw.add(cols.messstrecke, messages.Common_UnitMeter).width(1.08f);
		geschw.add(cols.messfehler, messages.Common_UnitPercent).width(1.08f);
		geschw.add(cols.anordnung).width(1.08f);
		geschw.add(cols.abweich_Abstand, messages.Common_UnitMeter)
				.width(1.59f);
		geschw.add(cols.bauart).width(1.31f);
		geschw.add(cols.energieversorgung).width(1.5f);
		geschw.add(cols.bef_GPE).width(1.57f);

		final GroupBuilder abh = root
				.addGroup(messages.SskpTableView_Abhaengigkeit);
		abh.add(cols.weichenlage).width(1.57f);
		abh.add(cols.fstr).width(1.57f);

		final GroupBuilder ina = root.addGroup(messages.SskpTableView_INA);
		ina.add(cols.gef_Stelle).width(2.01f);
		ina.add(cols.gef_Stelle_Abstand, messages.Common_UnitMeter)
				.width(1.82f);
		ina.add(cols.abstand_H_Tafel, messages.Common_UnitMeter).width(1.86f);

		root.add(cols.basis_bemerkung).width(7.24f);
		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", MIXED_STRING, ASCENDING) //$NON-NLS-1$
				.sort("E", LEXICOGRAPHICAL, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSskpLong,
				toolboxTableName.ToolboxTableNameSskpPlanningNumber,
				toolboxTableName.ToolboxTableNameSskpShort);
	}

	/**
	 * sets the i8n messages.
	 * 
	 * @param messagesWrapper
	 *            the messages service
	 */
	@Reference
	public void setMessages(final MessagesWrapper messagesWrapper) {
		this.messages = messagesWrapper.getMessages();
		toolboxTableName = messagesWrapper.getToolboxTableName();
		this.messagesWrapper = messagesWrapper;
	}
}