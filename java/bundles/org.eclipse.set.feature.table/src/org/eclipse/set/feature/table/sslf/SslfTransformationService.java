/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslf;

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
 * Service for creating the ssld table model.
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sslf" })
public final class SslfTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SslfColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SslfTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SslfColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslfTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SslfTableView_Heading);

		final GroupBuilder flankenschutzanforderer = root
				.addGroup(messages.SslfTableView_Flankenschutzanforderer);
		flankenschutzanforderer.add(cols.bezeichnung_w_nb).dimension(1.8f,
				LINE_HEIGHT * 2);
		flankenschutzanforderer.add(cols.wlage_nbgrenze).width(1.48f);

		final GroupBuilder unmittelbarerFlankenschutz = root
				.addGroup(messages.SslfTableView_Unmittelbarer_Flankenschutz);
		final GroupBuilder weicheGleissperre = unmittelbarerFlankenschutz
				.addGroup(
						messages.SslfTableView_Unmittelbarer_Flankenschutz_Weiche_Gleissperre);
		weicheGleissperre.add(cols.bezeichnung_w).width(1.71f);
		weicheGleissperre.add(cols.lage).width(0.78f);
		weicheGleissperre.add(cols.zwieschutz).width(1.57f);

		final GroupBuilder signal = unmittelbarerFlankenschutz.addGroup(
				messages.SslfTableView_Unmittelbarer_Flankenschutz_Signal);
		signal.add(cols.bezeichnung_sig).width(1.71f);
		signal.add(cols.rangierzielsperre).width(1.65f);

		final GroupBuilder weitergabe = root
				.addGroup(messages.SslfTableView_Weitergabe);

		final GroupBuilder weicheKreuzung = weitergabe
				.addGroup(messages.SslfTableView_Weitergabe_Weiche_Kreuzung);
		weicheKreuzung.add(cols.bezeichnung_w_kr).width(1.71f);
		weicheKreuzung.add(cols.wie_Fahrt_ueber).width(1.31f);

		final GroupBuilder weitergabeZusaetzlichEkw = weitergabe
				.addGroup(messages.SslfTableView_Weitergabe_Zusaetzlich_EKW);
		weitergabeZusaetzlichEkw.add(cols.ekw_bezeichnung_w_kr).width(1.71f);
		weitergabeZusaetzlichEkw.add(cols.ekw_wie_Fahrt_ueber).width(1.31f);

		root.add(cols.technischer_verzicht).width(1.59f);

		final GroupBuilder schutzraumueberwachung = root
				.addGroup(messages.SslfTableView_Schutzraumueberwachung);
		schutzraumueberwachung.add(cols.freigemeldet).width(1.46f);
		schutzraumueberwachung.add(cols.nicht_freigemeldet).width(1.52f);

		root.add(cols.basis_bemerkung).width(4.91f);
		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", MIXED_STRING, ASCENDING) //$NON-NLS-1$
				.sort("B", MIXED_STRING, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSslfLong,
				toolboxTableName.ToolboxTableNameSslfPlanningNumber,
				toolboxTableName.ToolboxTableNameSslfShort);
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