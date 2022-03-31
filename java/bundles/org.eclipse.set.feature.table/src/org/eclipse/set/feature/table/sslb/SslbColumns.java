/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslb;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * TableColumns for the Sslb-table.
 * 
 * @author rumpf
 *
 */
public class SslbColumns extends AbstractTableColumns {

	/**
	 * Erlaubnis.Abgabespeicherung
	 */
	public final ColumnDescriptor abgabespeicherung;

	/**
	 * Erlaubnis.Abh_D_Weg_Rf
	 */
	public final ColumnDescriptor abh_d_weg_rf;

	/**
	 * Blockmeldung.Anrueckabschnitt.Anordnung
	 */
	public final ColumnDescriptor anrueckabschnitt_anordnung;

	/**
	 * Blockmeldung.Anrueckabschnitt.Bezeichnung
	 */
	public final ColumnDescriptor anrueckabschnitt_bezeichnung;

	/**
	 * Awanst.Bez_Bed
	 */
	public final ColumnDescriptor awanst_bez_bed;

	/**
	 * Grundsatzangaben.von.Bauform_Start
	 */
	public final ColumnDescriptor bauform_Start;

	/**
	 * Grundsatzangaben.nach.Bauform_Ziel
	 */
	public final ColumnDescriptor bauform_Ziel;

	/**
	 * Strecke.Betriebsfuehrung
	 */
	public final ColumnDescriptor betriebsfuehrung;

	/**
	 * Grundsatzangaben.von.Betriebsst_Start
	 */
	public final ColumnDescriptor betriebsst_Start;

	/**
	 * Grundsatzangaben.nach.Betriebsst_Ziel
	 */
	public final ColumnDescriptor betriebsst_Ziel;

	/**
	 * Grundsatzangaben.Blockschaltung
	 */
	public final ColumnDescriptor blockschaltung;

	/**
	 * Strecke.Gleis
	 */
	public final ColumnDescriptor gleis;

	/**
	 * Erlaubnis.holen
	 */
	public final ColumnDescriptor holen;

	/**
	 * Strecke.Nummer
	 */
	public final ColumnDescriptor nummer;

	/**
	 * Blockmeldung.Raeumungspruefung
	 */
	public final ColumnDescriptor raeumungspruefung;

	/**
	 * Akustische_Meldung.Rueckblock
	 */
	public final ColumnDescriptor rueckblock;

	/**
	 * Erlaubnis.Ruecklauf_autom
	 */
	public final ColumnDescriptor ruecklauf_autom;

	/**
	 * Grundsatzangaben.Schutzuebertrager
	 */
	public final ColumnDescriptor schutzuebertrager;

	/**
	 * Erlaubnis.staendig
	 */
	public final ColumnDescriptor staendig;

	/**
	 * Akustische_Meldung.Vorblock
	 */
	public final ColumnDescriptor vorblock;

	/**
	 * Blockmeldung.Zugschluss
	 */
	public final ColumnDescriptor zugschluss;

	/**
	 * creates the column descriptions
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SslbColumns(final Messages messages) {
		super(messages);
		nummer = createNew(messages.SslbTableView_Nummer);
		gleis = createNew(messages.SslbTableView_Gleis);
		betriebsfuehrung = createNew(messages.SslbTableView_Betriebsfuehrung);
		betriebsst_Start = createNew(messages.SslbTableView_Betriebsstelle);
		bauform_Start = createNew(messages.SslbTableView_Bauform);
		betriebsst_Ziel = createNew(messages.SslbTableView_Betriebsstelle);
		bauform_Ziel = createNew(messages.SslbTableView_Bauform);
		blockschaltung = createNew(messages.SslbTableView_Blockschaltung);
		schutzuebertrager = createNew(messages.SslbTableView_Schutzuebertrager);
		staendig = createNew(messages.SslbTableView_StaendigVorhanden);
		holen = createNew(messages.SslbTableView_Holen);
		ruecklauf_autom = createNew(
				messages.SslbTableView_RuecklaufAutomatisch);
		abgabespeicherung = createNew(messages.SslbTableView_Abgabespeicherung);
		abh_d_weg_rf = createNew(
				messages.SslbTableView_AbhaengigkeitDurchrutschwegRangierzielsperre);
		anrueckabschnitt_bezeichnung = createNew(
				messages.SsldTableView_HeadingFundamentalIndication);
		anrueckabschnitt_anordnung = createNew(
				messages.SslbTableView_Anordnung);
		zugschluss = createNew(messages.SslbTableView_Zugschlussmeldung);
		raeumungspruefung = createNew(messages.SslbTableView_Raeumungspruefung);
		vorblock = createNew(messages.SslbTableView_Vorblockwecker);
		rueckblock = createNew(messages.SslbTableView_Rueckblockwecker);
		awanst_bez_bed = createNew(
				messages.SslbTableView_AwanstBezeichnungBedienungVon);
	}

}