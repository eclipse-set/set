/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskg;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sskg-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SskgColumns extends AbstractTableColumns {

	/**
	 * Standortmerkmale.Bezugspunkt.Abstand
	 */
	public final ColumnDescriptor abstand;
	/**
	 * Achszaehlpunkte.Anschluss_Energie.AEA_E
	 */
	public final ColumnDescriptor aea_e;
	/**
	 * Achszaehlpunkte.Anschluss_Energie.AEA_E_separat
	 */
	public final ColumnDescriptor aea_e_separat;
	/**
	 * Achszaehlpunkte.Anschluss_Info.AEA_I
	 */
	public final ColumnDescriptor aea_i;
	/**
	 * Achszaehlpunkte.Schienenprofil
	 */
	public final ColumnDescriptor schienenprofil;
	/**
	 * Grundsatzangaben.Art
	 */
	public final ColumnDescriptor art;
	/**
	 * Grundsatzangaben.Bezeichnung
	 */
	public final ColumnDescriptor bezeichnung_gleisschaltmittel;
	/**
	 * Standortmerkmale.Bezugspunkt.Bezeichnung
	 */
	public final ColumnDescriptor bezugspunkt_bezeichnung;

	/**
	 * Funktion
	 */
	public final ColumnDescriptor funktion;
	/**
	 * Standortmerkmale.Standort.km
	 */
	public final ColumnDescriptor km;
	/**
	 * Standortmerkmale.Standort.Strecke
	 */
	public final ColumnDescriptor strecke;
	/**
	 * Grundsatzangaben.Typ
	 */
	public final ColumnDescriptor typ;

	/**
	 * creates the columns
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SskgColumns(final Messages messages) {
		super(messages);
		bezeichnung_gleisschaltmittel = createNew(
				messages.SskgTableView_Grundsatzangaben_Bezeichnung);
		art = createNew(messages.SskgTableView_Grundsatzangaben_Art);
		typ = createNew(messages.SskgTableView_Grundsatzangaben_Typ);
		aea_i = createNew(messages.SskgTableView_Achszaehlpunkte_Anschluss_AEA);
		aea_e = createNew(
				messages.SskgTableView_Achszaehlpunkte_Anschluss_Energieversorgung_AEA);
		aea_e_separat = createNew(
				messages.SskgTableView_Achszaehlpunkte_Anschluss_separateAdern);
		schienenprofil = createNew(
				messages.SskgTableView_Achszaehlpunkte_Schienenprofil);
		bezugspunkt_bezeichnung = createNew(
				messages.SskgTableView_Standortmerkmale_Bezugspunkt_Bezeichnung);
		abstand = createNew(
				messages.SskgTableView_Standortmerkmale_Bezugspunkt_Abstand);
		strecke = createNew(
				messages.SskgTableView_Standortmerkmale_Standort_Strecke);
		km = createNew(messages.SskgTableView_Standortmerkmale_Standort_km);
		funktion = createNew(messages.SskgTableView_Funktion);

	}
}