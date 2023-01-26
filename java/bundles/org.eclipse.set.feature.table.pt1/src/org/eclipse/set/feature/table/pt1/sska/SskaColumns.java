/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sska;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Sska-<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SskaColumns extends AbstractTableColumns {

	/**
	 * Sska.Grundsatzangaben.Art
	 */
	public final ColumnDescriptor art = createNew(
			messages.SskaTableView_Grundsatzangaben_Art);

	/**
	 * Sska.Grundsatzangaben.Bauart
	 */
	public final ColumnDescriptor bauart = createNew(
			messages.SskaTableView_Grundsatzangaben_Bauart);

	/**
	 * Sska.Verknüpfungen.Energie.primaer
	 */
	public final ColumnDescriptor energie_primaer = createNew(
			messages.SskaTableView_Verknuepfungen_Energie_primaer);

	/**
	 * Sska.Verknüpfungen.Energie.sekundaer
	 */
	public final ColumnDescriptor energie_sekundaer = createNew(
			messages.SskaTableView_Verknuepfungen_Energie_sekundaer);

	/**
	 * O: Sska.IP_Adressangaben.GFK_Kategorie
	 */
	public final ColumnDescriptor GFK_Kategorie = createNew(
			messages.SskaTableView_IP_Adressangaben_GFK_Kategorie);

	/**
	 * Sska.Verknüpfungen.Information.primaer
	 */
	public final ColumnDescriptor information_primaer = createNew(
			messages.SskaTableView_Verknuepfungen_Information_primaer);

	/**
	 * Sska.Verknüpfungen.Information.sekundaer
	 */
	public final ColumnDescriptor information_sekundaer = createNew(
			messages.SskaTableView_Verknuepfungen_Information_sekundaer);

	/**
	 * Q: Sska.IP_Adressangaben.Adressblock_Blau.IPv4_Blau
	 */
	public final ColumnDescriptor IPv4_Blau = createNew(
			messages.SskaTableView_IP_Adressangaben_Adressblock_Blau_IPv4_Blau);

	/**
	 * S: Sska.IP_Adressangaben.Adressblock_Grau.IPv4_Grau
	 */
	public final ColumnDescriptor IPv4_Grau = createNew(
			messages.SskaTableView_IP_Adressangaben_Adressblock_Blau_IPv4_Grau);

	/**
	 * R: Sska.IP_Adressangaben.Adressblock_Blau.IPv6_Blau
	 */
	public final ColumnDescriptor IPv6_Blau = createNew(
			messages.SskaTableView_IP_Adressangaben_Adressblock_Blau_IPv6_Blau);

	/**
	 * T: Sska.IP_Adressangaben.Adressblock_Grau.IPv6_Grau
	 */
	public final ColumnDescriptor IPv6_Grau = createNew(
			messages.SskaTableView_IP_Adressangaben_Adressblock_Blau_IPv6_Grau);

	/**
	 * Sska.Grundsatzangaben.Unterbringung.km
	 */
	public final ColumnDescriptor km = createNew(
			messages.SskaTableView_Grundsatzangaben_Unterbringung_km);
	/**
	 * Sska.Verknüpfungen.Bedienung.lokal
	 */
	public final ColumnDescriptor lokal = createNew(
			messages.SskaTableView_Verknuepfungen_Bedienung_lokal);

	/**
	 * Sska.Verknüpfungen.Bedienung.NotBP
	 */
	public final ColumnDescriptor notbp = createNew(
			messages.SskaTableView_Verknuepfungen_Bedienung_Not_BP);

	/**
	 * Sska.Grundsatzangaben.Unterbringung.Ort
	 */
	public final ColumnDescriptor ort = createNew(
			messages.SskaTableView_Grundsatzangaben_Unterbringung_Ort);

	/**
	 * P: Sska.IP_Adressangaben.Regionalbereich
	 */
	public final ColumnDescriptor Regionalbereich = createNew(
			messages.SskaTableView_IP_Adressangaben_Regionalbereich);

	/**
	 * Sska.Grundsatzangaben.Unterbringung.Strecke
	 */
	public final ColumnDescriptor strecke = createNew(
			messages.SskaTableView_Grundsatzangaben_Unterbringung_Strecke);

	/**
	 * Sska.Grundsatzangaben.Unterbringung.Art
	 */
	public final ColumnDescriptor unterbringung_art = createNew(
			messages.SskaTableView_Grundsatzangaben_Unterbringung_Art);

	/**
	 * Sska.Verknüpfungen.Bedienung.zentrale
	 */
	public final ColumnDescriptor zentrale = createNew(
			messages.SskaTableView_Verknuepfungen_Bedienung_Zentrale);

	/**
	 * Sska.Grundsatzangaben.Bezeichnung
	 */
	public final ColumnDescriptor zentraleinheit_bezeichnung = createNew(
			messages.SskaTableView_Grundsatzangaben_Bezeichnung_AEA_ASTW_Zentraleinheit);

	/**
	 * Sska.Verknüpfungen.Bedienung.bezirk
	 */
	public final ColumnDescriptor bezirk = createNew(
			messages.SskaTableView_Verknuepfungen_Bedienung_Bezirk);

	/**
	 * creates the columns
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SskaColumns(final Messages messages) {
		super(messages);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskaTableView_Heading);

		final GroupBuilder fundamental = root
				.addGroup(messages.SskaTableView_Grundsatzangaben);
		fundamental.add(zentraleinheit_bezeichnung).width(2.37f);
		fundamental.add(art).width(1.93f);
		fundamental.add(bauart).width(1.86f);

		final GroupBuilder unterbringung = fundamental.addGroup(
				messages.SskaTableView_Grundsatzangaben_Unterbringung);
		unterbringung.add(unterbringung_art).width(1.88f);
		unterbringung.add(ort).width(2.92f);
		unterbringung.add(strecke).width(1.08f);
		unterbringung.add(km).width(1.12f);

		final GroupBuilder verknuepfungen = root
				.addGroup(messages.SskaTableView_Verknuepfungen);
		final GroupBuilder information = verknuepfungen
				.addGroup(messages.SskaTableView_Verknuepfungen_Information);
		information.add(information_primaer).width(2.12f);
		information.add(information_sekundaer).width(2.12f);

		final GroupBuilder energie = verknuepfungen
				.addGroup(messages.SskaTableView_Verknuepfungen_Energie);
		energie.add(energie_primaer).width(2.12f);
		energie.add(energie_sekundaer).width(2.41f);

		final GroupBuilder bedienung = verknuepfungen
				.addGroup(messages.SskaTableView_Verknuepfungen_Bedienung);
		bedienung.add(lokal).width(0.97f);
		bedienung.add(bezirk).width(1.27f);
		bedienung.add(zentrale).width(1.19f);
		bedienung.add(notbp).width(1.1f);

		final GroupBuilder ipAdressangaben = root
				.addGroup(messages.SskaTableView_IP_Adressangaben);
		ipAdressangaben.add(GFK_Kategorie).width(1.29f);
		ipAdressangaben.add(Regionalbereich).width(1.16f);
		final GroupBuilder blau = ipAdressangaben.addGroup(
				messages.SskaTableView_IP_Adressangaben_Adressblock_Blau);
		blau.add(IPv4_Blau).width(1.59f);
		blau.add(IPv6_Blau).width(1.99f);
		final GroupBuilder grau = ipAdressangaben.addGroup(
				messages.SskaTableView_IP_Adressangaben_Adressblock_Grau);
		grau.add(IPv4_Grau).width(1.59f);
		grau.add(IPv6_Grau).width(1.99f);

		root.add(basis_bemerkung).width(2.6f);

		return root.getGroupRoot();
	}
}