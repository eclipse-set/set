/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskw;

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
 * Service for creating the ssld table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=sskw" })
public final class SskwTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SskwColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SskwTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SskwColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskwTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskwTableView_Heading);
		final GroupBuilder fundamental = root.addGroup(
				messages.SskwTableView_Weiche_Kreuzung_Gleissperre_Sonderanlage);
		fundamental.add(cols.basis_bezeichnung).width(1.93f);
		fundamental.add(cols.form).width(3.56f);

		final GroupBuilder freimeldung = root
				.addGroup(messages.SskwTableView_Freimeldung);
		freimeldung.add(cols.fma).width(1.42f);
		final GroupBuilder nichtgrenzzeichenfrei = freimeldung.addGroup(
				messages.SskwTableView_Freimeldung_nicht_grenzzeichenfrei)
				.height(LINE_HEIGHT * 1.5f);
		nichtgrenzzeichenfrei.add(1.42f, cols.links, cols.rechts);
		freimeldung.add(cols.isolierfall).width(1.42f);

		final GroupBuilder vorzugslage = root
				.addGroup(messages.SskwTableView_Vorzugslage);
		vorzugslage.add(cols.lage).dimension(1.1f, LINE_HEIGHT * 2);
		vorzugslage.add(cols.automatik).width(1.1f);

		final GroupBuilder weiche = root
				.addGroup(messages.SskwTableView_Weiche);
		weiche.add(cols.auffahrortung).width(1.42f);
		weiche.add(cols.weiche_antriebe, messages.Common_UnitPiece)
				.width(1.42f);
		weiche.add(cols.weichensignal).width(1.42f);
		weiche.add(cols.pruefkontakte, messages.Common_UnitPiece).width(1.42f);

		final GroupBuilder zulaessigeGeschwindigkeit = weiche
				.addGroup(messages.SskwTableView_Weiche_v_zul_W);
		zulaessigeGeschwindigkeit
				.add(cols.v_zul_w_links, messages.Common_UnitKmh).width(1.19f);
		zulaessigeGeschwindigkeit
				.add(cols.v_zul_w_rechts, messages.Common_UnitKmh).width(1.19f);

		final GroupBuilder kreuzung = root
				.addGroup(messages.SskwTableView_Kreuzung);
		final GroupBuilder zulaessigeGeschwKreuzung = kreuzung
				.addGroup(messages.SskwTableView_Kreuzung_v_zul_K);

		zulaessigeGeschwKreuzung
				.add(cols.v_zul_k_links, messages.Common_UnitKmh).width(1.19f);
		zulaessigeGeschwKreuzung
				.add(cols.v_zul_k_rechts, messages.Common_UnitKmh).width(1.19f);

		final GroupBuilder herzstueck = root
				.addGroup(messages.SskwTableView_Herzstueck);
		herzstueck.add(cols.herzstuecke_antriebe, messages.Common_UnitPiece)
				.width(1.1f);

		final GroupBuilder gleissperre = root
				.addGroup(messages.SskwTableView_Gleissperre);
		gleissperre.add(cols.gleissperre_antriebe, messages.Common_UnitPiece)
				.width(1.1f);
		gleissperre.add(1.1f, cols.gsp_signal, cols.auswurfrichtung,
				cols.schutzschiene);

		final GroupBuilder sonstiges = root
				.addGroup(messages.SskwTableView_Sonstiges);
		sonstiges.add(cols.regelzeichnung_nr).width(2.56f);
		sonstiges.add(cols.dws).width(0.83f);

		final GroupBuilder sonderanlage = root
				.addGroup(messages.SskwTableView_Sonderanlage);
		sonderanlage.add(cols.Sonderanlage_Art).width(1.82f);

		root.add(cols.basis_bemerkung).width(3.75f);

		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", MIXED_STRING, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSskwLong,
				toolboxTableName.ToolboxTableNameSskwPlanningNumber,
				toolboxTableName.ToolboxTableNameSskwShort);
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