/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssko;

import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.feature.table.messages.MessagesWrapper;
import org.eclipse.set.feature.table.messages.ToolboxTableName;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the ssko table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssko" })
public final class SskoTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SskoColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SskoTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SskoColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskoTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskoTableView_Heading);
		final GroupBuilder grundsatzangaben = root
				.addGroup(messages.SskoTableView_Grundsatzangaben);
		grundsatzangaben.add(cols.bezeichnung_schloss).width(1.67f)
				.height(LINE_HEIGHT * 2);
		grundsatzangaben.add(cols.schloss_an).width(1.08f);
		grundsatzangaben.add(cols.grundstellung_eingeschl).width(2.07f);
		final GroupBuilder schluessel = root
				.addGroup(messages.SskoTableView_Schluessel);
		schluessel.add(cols.schluessel_bezeichnung).width(1.69f);
		schluessel.add(cols.bartform).width(1.16f);
		schluessel.add(cols.gruppe).width(1.06f);

		final GroupBuilder fahrweg = root
				.addGroup(messages.SskoTableView_Fahrweg);
		final GroupBuilder fahrwegBezeichnung = fahrweg
				.addGroup(messages.SskoTableView_Fahrweg_Bezeichnung);
		fahrwegBezeichnung.add(cols.fahrwegZug).width(1.69f);
		fahrwegBezeichnung.add(cols.fahrwegRangier).width(1.69f);

		final GroupBuilder wgspbue = root
				.addGroup(messages.SskoTableView_WeicheGspBue);
		final GroupBuilder verschlossenesElement = wgspbue.addGroup(
				messages.SskoTableView_WeicheGspBue_VerschlossenesElement);
		verschlossenesElement.add(cols.wgspbue_bezeichnung).width(1.69f);
		verschlossenesElement.add(cols.lage).width(1.59f);
		verschlossenesElement.add(cols.komponente).width(2.39f);
		wgspbue.add(cols.schlossart).width(2.41f);
		final GroupBuilder schlosskombi = root
				.addGroup(messages.SskoTableView_SchlosskombiSchluesselsperre);
		schlosskombi.add(cols.sk_ssp_bezeichnung).width(1.65f);
		schlosskombi.add(cols.hauptschloss).width(1.1f);
		final GroupBuilder unterbringung = schlosskombi.addGroup(
				messages.SskoTableView_SchlosskombiSchluesselsperre_Unterbringung);
		unterbringung.add(cols.unterbringung_art).width(2.07f);
		unterbringung.add(cols.unterbringung_ort).width(1.61f);
		unterbringung.add(cols.unterbringung_strecke).width(1.12f);
		unterbringung.add(cols.unterbringung_km).width(1.12f);
		root.add(cols.sonderanlage).width(1.99f);
		root.add(cols.Technisch_Berechtigter).width(1.99f);
		root.add(cols.basis_bemerkung).width(7.66f);
		return root.getGroupRoot();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSskoLong,
				toolboxTableName.ToolboxTableNameSskoPlanningNumber,
				toolboxTableName.ToolboxTableNameSskoShort);
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
		this.messagesWrapper = messagesService;
	}
}
