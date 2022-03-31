/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssks;

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
 * Service for creating the ssks table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssks" })
public final class SsksTransformationService
		extends AbstractPlanPro2TableTransformationService {

	private SsksColumns cols;

	private Messages messages;

	private ToolboxTableName toolboxTableName;

	/**
	 * constructor.
	 */
	public SsksTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.cols = new SsksColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsksTransformator(cols, messagesWrapper);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SsksTableView_Heading);
		root.add(cols.bezeichnung_signal).width(1.73f);
		final GroupBuilder signalart = root
				.addGroup(messages.SsksTableView_HeadingSignalArt, 1.04f);
		signalart.add(cols.reales_Signal).height(LINE_HEIGHT * 2);
		signalart.add(cols.fiktives_Signal);

		final GroupBuilder standortmerkmale = root
				.addGroup(messages.SsksTableView_HeadingStandortmerkmale);
		final GroupBuilder standort = standortmerkmale.addGroup(
				messages.SsksTableView_HeadingStandortmerkmaleStandort, 1.32f);
		standort.add(cols.strecke);
		standort.add(cols.km);

		standortmerkmale.add(cols.sonstige_zulaessige_anordnung).width(1.32f);
		standortmerkmale.add(cols.lichtraumprofil).width(1.32f);
		standortmerkmale.add(cols.ueberhoehung, messages.Common_UnitMillimeter)
				.width(1.32f);
		final GroupBuilder abstand = standortmerkmale.addGroup(
				messages.SsksTableView_HeadingStandortmerkmaleAbstandGleismitteMastmitte)
				.height(LINE_HEIGHT * 2);
		abstand.add(cols.mastmitte_links, messages.Common_UnitMillimeter)
				.width(1.32f);
		abstand.add(cols.mastmitte_rechts, messages.Common_UnitMillimeter)
				.width(1.32f);

		final GroupBuilder sichtbarkeit = standortmerkmale.addGroup(
				messages.SsksTableView_HeadingStandortmerkmaleSichtbarkeit);
		sichtbarkeit.add(cols.sichtbarkeit_soll, messages.Common_UnitMeter)
				.width(1.32f);
		sichtbarkeit.add(cols.sichtbarkeit_mindest, messages.Common_UnitMeter)
				.width(1.32f);
		sichtbarkeit.add(cols.sichtbarkeit_ist, messages.Common_UnitMeter)
				.width(1.32f);

		final GroupBuilder ausrichtung = standortmerkmale.addGroup(
				messages.SsksTableView_HeadingStandortmerkmaleAusrichtung);
		ausrichtung.add(cols.entfernung, messages.Common_UnitMeter)
				.width(1.32f);
		ausrichtung.add(cols.richtpunkt).width(1.32f);

		final GroupBuilder konstruktiveMerkmale = root
				.addGroup(messages.SsksTableView_HeadingKonstruktivmerkmale);
		final GroupBuilder anordnung = konstruktiveMerkmale.addGroup(
				messages.SsksTableView_HeadingKonstruktivmerkmaleAnordnung);
		anordnung.add(cols.befestigung).width(2.4f);
		anordnung.add(cols.anordnung_regelzeichnung).width(2.4f);

		konstruktiveMerkmale
				.add(cols.obere_lichtpunkthoehe, messages.Common_UnitMillimeter)
				.width(1.32f);

		final GroupBuilder streuscheibe = konstruktiveMerkmale.addGroup(
				messages.SsksTableView_HeadingKonstruktivmerkmaleStreuscheibe);
		streuscheibe.add(cols.streuscheibe_art).width(0.65f);
		streuscheibe.add(cols.streuscheibe_stellung).width(1.14f);

		final GroupBuilder fundament = konstruktiveMerkmale.addGroup(
				messages.SsksTableView_HeadingKonstruktivmerkmaleFundament);
		fundament.add(cols.fundament_regelzeichnung).width(2.4f);
		fundament.add(cols.fundament_hoehe, messages.Common_UnitMillimeter)
				.width(1.04f);

		final GroupBuilder anschluss = root
				.addGroup(messages.SsksTableView_HeadingAnschluss);
		final GroupBuilder schaltkasten = anschluss
				.addGroup(messages.SsksTableView_HeadingAnschlussSchaltkasten);
		schaltkasten.add(cols.schaltkasten_bezeichnung).width(1.59f);
		schaltkasten
				.add(cols.schaltkasten_entfernung, messages.Common_UnitMeter)
				.width(1.32f);

		final GroupBuilder separaterSchaltkasten = anschluss.addGroup(
				messages.SsksTableView_HeadingAnschlussSeparaterSchaltkasten);
		separaterSchaltkasten.add(cols.schaltkasten_separat_bezeichnung)
				.width(1.59f);

		anschluss.add(cols.dauerhaft_nacht).width(1.32f);

		final GroupBuilder signalisierung = root
				.addGroup(messages.SsksTableView_HeadingSignalisierung);
		final GroupBuilder schirm = signalisierung.addGroup(
				messages.SsksTableView_HeadingSignalisierungSchirm, 0.81f);
		schirm.add(cols.schirm_hp_hl, cols.schirm_ks_vr, cols.schirm_zl_kl,
				cols.schirm_ra_sh, cols.schirm_zs);

		final GroupBuilder zusatzanzeiger = signalisierung.addGroup(
				messages.SsksTableView_HeadingSignalisierungZusatzanzeiger);
		zusatzanzeiger.add(0.81f, cols.zusatzanzeiger_zs_2,
				cols.zusatzanzeiger_zs_2v, cols.zusatzanzeiger_zs_3,
				cols.zusatzanzeiger_zs_3v, cols.zusatzanzeiger_zs,
				cols.zusatzanzeiger_zp);
		zusatzanzeiger.add(cols.zusatzanzeiger_kombination).width(1.59f);

		signalisierung.add(cols.nachgeordnetes_Signal).width(1.59f);
		signalisierung.add(cols.mastschild).width(1.32f);
		final GroupBuilder vorsignaltafel = signalisierung
				.addGroup(messages.SsksTableView_HeadingVorsignaltafel);
		vorsignaltafel.add(cols.vorsignaltafel_regelzeichnung).width(2.4f);

		final GroupBuilder sonstiges = root
				.addGroup(messages.SsksTableView_HeadingSonstiges);
		sonstiges.add(1.32f, cols.automatischer_betrieb, cols.dunkelschaltung,
				cols.durchfahrt_erlaubt, cols.Besetzte_Ausfahrt,
				cols.loeschung_Zs_1__Zs_7);
		final GroupBuilder ueberwachung = sonstiges.addGroup(
				messages.SsksTableView_HeadingSonstigesUeberwachung, 0.65f);
		ueberwachung.add(cols.ueberwachung_zs_2, cols.ueberwachung_zs_2v);

		root.add(cols.basis_bemerkung).width(8.88f);
		return root.getGroupRoot();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("A", MIXED_STRING, ASCENDING).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(toolboxTableName.ToolboxTableNameSsksLong,
				toolboxTableName.ToolboxTableNameSsksPlanningNumber,
				toolboxTableName.ToolboxTableNameSsksShort);
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
