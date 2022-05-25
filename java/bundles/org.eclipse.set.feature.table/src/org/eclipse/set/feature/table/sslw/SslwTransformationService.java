/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslw;

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
 * Service for creating the ssld table model.
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sslw" })
public final class SslwTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SslwColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SslwTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SslwColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslwTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SslwTableView_Heading);
		root.add(cols.w_kr_stellung).width(2.46f);
		final GroupBuilder art = root.addGroup(messages.SslwTableView_Art);
		art.add(cols.eigen).width(0.87f);
		art.add(cols.echt).width(0.7f);
		root.add(cols.verschluss).width(1.61f);
		final GroupBuilder ersatzschutz = root
				.addGroup(messages.SslwTableView_Ersatzschutz_unmittelbar);
		final GroupBuilder weicheGleissperre = ersatzschutz.addGroup(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Weiche_Gleissperre);
		weicheGleissperre.add(cols.bezeichnung_w).width(1.71f);
		weicheGleissperre.add(cols.lage).width(0.78f);
		weicheGleissperre.add(cols.zwieschutz).width(1.57f);
		final GroupBuilder signal = ersatzschutz.addGroup(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Signal);
		signal.add(cols.bezeichnung_sig).width(1.71f);
		signal.add(cols.rangierzielsperre).width(1.65f);

		final GroupBuilder ersatzschutzWeitergabe = root
				.addGroup(messages.SslwTableView_Ersatzschutz_Weitergabe);
		final GroupBuilder weicheKreuzung = ersatzschutzWeitergabe.addGroup(
				messages.SslwTableView_Ersatzschutz_Weitergabe_Weiche_Kreuzung);
		weicheKreuzung.add(cols.bezeichnung_w_kr).width(1.71f);
		weicheKreuzung.add(cols.wie_fahrt_ueber).dimension(1.27f,
				LINE_HEIGHT * 2);

		final GroupBuilder zusaetzlichBeiEkw = ersatzschutzWeitergabe.addGroup(
				messages.SslwTableView_Ersatzschutz_Weitergabe_Zusaetzlich_EKW);
		zusaetzlichBeiEkw.add(cols.ekw_bezeichnung_w_kr).width(1.71f);
		zusaetzlichBeiEkw.add(cols.ekw_wie_fahrt_ueber).width(1.23f);

		root.add(cols.technischer_verzicht).width(1.74f);
		final GroupBuilder schutzraumUeberwachung = root
				.addGroup(messages.SslwTableView_Schutzraumueberwachung);
		schutzraumUeberwachung.add(cols.freigemeldet).width(1.57f);
		schutzraumUeberwachung.add(cols.nicht_freigemeldet).width(1.8f);

		root.add(cols.nachlaufverhinderung).width(1.99f);
		root.add(cols.basis_bemerkung).width(7.51f);
		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder().sort("A", MIXED_STRING, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSslwLong,
				toolboxTableName.ToolboxTableNameSslwPlanningNumber,
				toolboxTableName.ToolboxTableNameSslwShort);
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