/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskg;

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
 * Service for creating the sskg table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sskg" })
public final class SskgTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SskgColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SskgTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SskgColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskgTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskgTableView_Heading);
		final GroupBuilder fundamental = root
				.addGroup(messages.SskgTableView_Grundsatzangaben);
		fundamental.add(cols.bezeichnung_gleisschaltmittel).width(4.28f);
		fundamental.add(cols.art).width(2.86f);
		fundamental.add(cols.typ).width(2.86f);

		final GroupBuilder achszaehlpunkte = root
				.addGroup(messages.SskgTableView_Achszaehlpunkte);
		final GroupBuilder anschlussAchszaehlRechner = achszaehlpunkte.addGroup(
				messages.SskgTableView_Achszaehlpunkte_Anschluss_Rechner);
		anschlussAchszaehlRechner.add(cols.aea_i).width(1.42f);

		final GroupBuilder anschlussEnergieVersorgung = achszaehlpunkte
				.addGroup(
						messages.SskgTableView_Achszaehlpunkte_Anschluss_Energieversorgung)
				.height(LINE_HEIGHT * 1.5f);
		anschlussEnergieVersorgung.add(cols.aea_e).width(1.42f);
		anschlussEnergieVersorgung.add(cols.aea_e_separat).width(1.12f)
				.height(LINE_HEIGHT * 2);

		achszaehlpunkte.add(cols.schienenprofil).width(1.80f);

		final GroupBuilder standortmerkmale = root
				.addGroup(messages.SskgTableView_Standortmerkmale);
		final GroupBuilder bezugspunkt = standortmerkmale
				.addGroup(messages.SskgTableView_Standortmerkmale_Bezugspunkt);
		bezugspunkt.add(cols.bezugspunkt_bezeichnung).width(2.86f);
		bezugspunkt.add(cols.abstand,
				messages.SskgTableView_Standortmerkmale_Bezugspunkt_Abstand_m)
				.width(1.42f);

		final GroupBuilder standort = standortmerkmale
				.addGroup(messages.SskgTableView_Standortmerkmale_Standort);
		standort.add(cols.strecke).width(1.42f);
		standort.add(cols.km).width(1.42f);

		root.add(cols.funktion).width(3.56f);
		root.add(cols.basis_bemerkung).width(14.27f);
		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", LEXICOGRAPHICAL, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSskgLong,
				toolboxTableName.ToolboxTableNameSskgPlanningNumber,
				toolboxTableName.ToolboxTableNameSskgShort);
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