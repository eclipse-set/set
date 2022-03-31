/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssko;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Ssko-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public class SskoColumns extends AbstractTableColumns {

	/**
	 * E: Ssko.Schluessel.Bartform
	 */
	public final ColumnDescriptor bartform;

	/**
	 * A: Ssko.Grundsatzangaben.Bezeichnung_Schloss
	 */
	public final ColumnDescriptor bezeichnung_schloss;

	/**
	 * C: Ssko.Grundsatzangaben.Grundstellung_eingeschl
	 */
	public final ColumnDescriptor grundstellung_eingeschl;

	/**
	 * F: Ssko.Schluessel.Gruppe
	 */
	public final ColumnDescriptor gruppe;

	/**
	 * L: Ssko.Sk_Ssp.Hauptschloss
	 */
	public final ColumnDescriptor hauptschloss;

	/**
	 * I: Ssko.W_Gsp_Bue.Verschl_Element.Komponente
	 */
	public final ColumnDescriptor komponente;

	/**
	 * H: Ssko.W_Gsp_Bue.Verschl_Element.Lage
	 */
	public final ColumnDescriptor lage;

	/**
	 * B: Ssko.Grundsatzangaben.Schloss_an
	 */
	public final ColumnDescriptor schloss_an;

	/**
	 * J: Ssko.W_Gsp_Bue.Schlossart
	 */
	public final ColumnDescriptor schlossart;

	/**
	 * D: Ssko.Schluessel.Bezeichnung
	 */
	public final ColumnDescriptor schluessel_bezeichnung;

	/**
	 * K: Ssko.Sk_Ssp.Bezeichnung
	 */
	public final ColumnDescriptor sk_ssp_bezeichnung;

	/**
	 * Q: Ssko.Sonderanlage
	 */
	public final ColumnDescriptor sonderanlage;

	/**
	 * R: Ssko.Technisch_Berechtigter
	 */
	public final ColumnDescriptor Technisch_Berechtigter;

	/**
	 * M: Ssko.Sk_Ssp.Unterbringung.Art
	 */
	public final ColumnDescriptor unterbringung_art;

	/**
	 * P: Ssko.Sk_Ssp.Unterbringung.km
	 */
	public final ColumnDescriptor unterbringung_km;

	/**
	 * N: Ssko.Sk_Ssp.Unterbringung.Ort
	 */
	public final ColumnDescriptor unterbringung_ort;

	/**
	 * Ssko.Sk_Ssp.Unterbringung.Strecke
	 */
	public final ColumnDescriptor unterbringung_strecke;

	/**
	 * G: Ssko.W_Gsp_Bue.Verschl_Element.Bezeichnung
	 */
	public final ColumnDescriptor wgspbue_bezeichnung;

	/**
	 * Ssko.Fahrweg.Bezeichnung.Zug
	 */
	public final ColumnDescriptor fahrwegZug;

	/**
	 * Ssko.Fahrweg.Bezeichnung.Rangier
	 */
	public final ColumnDescriptor fahrwegRangier;

	/**
	 * create a new columns instance.
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SskoColumns(final Messages messages) {
		super(messages);
		bezeichnung_schloss = createNew(
				messages.SskoTableView_Grundsatzangaben_Bezeichnung);
		schloss_an = createNew(
				messages.SskoTableView_Grundsatzangaben_Schlossan);
		grundstellung_eingeschl = createNew(
				messages.SskoTableView_Grundsatzangaben_InGrundstellung);
		schluessel_bezeichnung = createNew(
				messages.SskoTableView_Schluessel_Bezeichnung);
		bartform = createNew(messages.SskoTableView_Schluessel_Bartform);
		gruppe = createNew(messages.SskoTableView_Schluessel_Gruppe);
		wgspbue_bezeichnung = createNew(
				messages.SskoTableView_WeicheGspBue_VerschlossenesElement_Bezeichnung);
		lage = createNew(
				messages.SskoTableView_WeicheGspBue_VerschlossenesElement_Lage);
		komponente = createNew(
				messages.SskoTableView_WeicheGspBue_VerschlossenesElement_Komponente);
		schlossart = createNew(messages.SskoTableView_WeicheGspBue_Schlossart);
		sk_ssp_bezeichnung = createNew(
				messages.SskoTableView_SchlosskombiSchluesselsperre_Bezeichnung);
		hauptschloss = createNew(
				messages.SskoTableView_SchlosskombiSchluesselsperre_Hauptschloss);
		unterbringung_art = createNew(
				messages.SskoTableView_SchlosskombiSchluesselsperre_Unterbringung_Art);
		unterbringung_ort = createNew(
				messages.SskoTableView_SchlosskombiSchluesselsperre_Unterbringung_Ort);
		unterbringung_strecke = createNew(
				messages.SskoTableView_SchlosskombiSchluesselsperre_Unterbringung_Strecke);
		unterbringung_km = createNew(
				messages.SskoTableView_SchlosskombiSchluesselsperre_Unterbringung_km);
		sonderanlage = createNew(messages.SskoTableView_Sonderanlage);
		Technisch_Berechtigter = createNew(
				messages.SskoTableView_Technisch_Berechtigter);
		fahrwegZug = createNew(messages.SskoTableView_Fahrweg_Bezeichnung_Zug);
		fahrwegRangier = createNew(
				messages.SskoTableView_Fahrweg_Bezeichnung_Rangier);
	}

}
