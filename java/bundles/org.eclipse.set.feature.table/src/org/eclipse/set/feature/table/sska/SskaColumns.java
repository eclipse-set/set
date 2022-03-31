/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sska;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sska-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SskaColumns extends AbstractTableColumns {

	/**
	 * Sska.Grundsatzangaben.Art
	 */
	public final ColumnDescriptor art;

	/**
	 * Sska.Grundsatzangaben.Bauart
	 */
	public final ColumnDescriptor bauart;

	/**
	 * Sska.Verknüpfungen.Energie.primaer
	 */
	public final ColumnDescriptor energie_primaer;

	/**
	 * Sska.Verknüpfungen.Energie.sekundaer
	 */
	public final ColumnDescriptor energie_sekundaer;

	/**
	 * O: Sska.IP_Adressangaben.GFK_Kategorie
	 */
	public final ColumnDescriptor GFK_Kategorie;

	/**
	 * Sska.Verknüpfungen.Information.primaer
	 */
	public final ColumnDescriptor information_primaer;

	/**
	 * Sska.Verknüpfungen.Information.sekundaer
	 */
	public final ColumnDescriptor information_sekundaer;

	/**
	 * Q: Sska.IP_Adressangaben.Adressblock_Blau.IPv4_Blau
	 */
	public final ColumnDescriptor IPv4_Blau;

	/**
	 * S: Sska.IP_Adressangaben.Adressblock_Grau.IPv4_Grau
	 */
	public final ColumnDescriptor IPv4_Grau;

	/**
	 * R: Sska.IP_Adressangaben.Adressblock_Blau.IPv6_Blau
	 */
	public final ColumnDescriptor IPv6_Blau;

	/**
	 * T: Sska.IP_Adressangaben.Adressblock_Grau.IPv6_Grau
	 */
	public final ColumnDescriptor IPv6_Grau;

	/**
	 * Sska.Grundsatzangaben.Unterbringung.km
	 */
	public final ColumnDescriptor km;

	/**
	 * Sska.Verknüpfungen.Bedienung.lokal
	 */
	public final ColumnDescriptor lokal;

	/**
	 * Sska.Verknüpfungen.Bedienung.NotBP
	 */
	public final ColumnDescriptor notbp;

	/**
	 * Sska.Grundsatzangaben.Unterbringung.Ort
	 */
	public final ColumnDescriptor ort;

	/**
	 * P: Sska.IP_Adressangaben.Regionalbereich
	 */
	public final ColumnDescriptor Regionalbereich;

	/**
	 * Sska.Grundsatzangaben.Unterbringung.Strecke
	 */
	public final ColumnDescriptor strecke;

	/**
	 * Sska.Grundsatzangaben.Unterbringung.Art
	 */
	public final ColumnDescriptor unterbringung_art;

	/**
	 * Sska.Verknüpfungen.Bedienung.zentrale
	 */
	public final ColumnDescriptor zentrale;

	/**
	 * Sska.Grundsatzangaben.Bezeichnung
	 */
	public final ColumnDescriptor zentraleinheit_bezeichnung;

	/**
	 * Sska.Verknüpfungen.Bedienung.bezirk
	 */
	public final ColumnDescriptor bezirk;

	/**
	 * creates the columns
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SskaColumns(final Messages messages) {
		super(messages);
		art = createNew(messages.SskaTableView_Grundsatzangaben_Art);
		bauart = createNew(messages.SskaTableView_Grundsatzangaben_Bauart);
		unterbringung_art = createNew(
				messages.SskaTableView_Grundsatzangaben_Unterbringung_Art);
		ort = createNew(
				messages.SskaTableView_Grundsatzangaben_Unterbringung_Ort);
		strecke = createNew(
				messages.SskaTableView_Grundsatzangaben_Unterbringung_Strecke);
		km = createNew(
				messages.SskaTableView_Grundsatzangaben_Unterbringung_km);
		energie_primaer = createNew(
				messages.SskaTableView_Verknuepfungen_Energie_primaer);
		energie_sekundaer = createNew(
				messages.SskaTableView_Verknuepfungen_Energie_sekundaer);
		information_primaer = createNew(
				messages.SskaTableView_Verknuepfungen_Information_primaer);
		information_sekundaer = createNew(
				messages.SskaTableView_Verknuepfungen_Information_sekundaer);
		lokal = createNew(
				messages.SskaTableView_Verknuepfungen_Bedienung_lokal);
		bezirk = createNew(
				messages.SskaTableView_Verknuepfungen_Bedienung_Bezirk);
		zentrale = createNew(
				messages.SskaTableView_Verknuepfungen_Bedienung_Zentrale);
		notbp = createNew(
				messages.SskaTableView_Verknuepfungen_Bedienung_Not_BP);
		zentraleinheit_bezeichnung = createNew(
				messages.SskaTableView_Grundsatzangaben_Bezeichnung_AEA_ASTW_Zentraleinheit);
		GFK_Kategorie = createNew(
				messages.SskaTableView_IP_Adressangaben_GFK_Kategorie);
		Regionalbereich = createNew(
				messages.SskaTableView_IP_Adressangaben_Regionalbereich);
		IPv4_Blau = createNew(
				messages.SskaTableView_IP_Adressangaben_Adressblock_Blau_IPv4_Blau);
		IPv6_Blau = createNew(
				messages.SskaTableView_IP_Adressangaben_Adressblock_Blau_IPv6_Blau);
		IPv4_Grau = createNew(
				messages.SskaTableView_IP_Adressangaben_Adressblock_Blau_IPv4_Grau);
		IPv6_Grau = createNew(
				messages.SskaTableView_IP_Adressangaben_Adressblock_Blau_IPv6_Grau);
	}
}