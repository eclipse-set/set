/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskp;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sskp-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public class SskpColumns extends AbstractTableColumns {

	/**
	 * U: Sskp.Ina.Abstand_H_Tafel
	 */
	public ColumnDescriptor abstand_H_Tafel;

	/**
	 * F: Sskp.Grundsatzangaben.Abstand_zu
	 */
	public ColumnDescriptor abstand_zu;

	/**
	 * Gue.Abweich_Abstand
	 */
	public ColumnDescriptor abweich_Abstand;

	/**
	 * Gue.Anordnung
	 */
	public ColumnDescriptor anordnung;
	/**
	 * Gue.Bauart
	 */
	public ColumnDescriptor bauart;
	/**
	 * Gue.Bef_GPE
	 */
	public ColumnDescriptor bef_GPE;
	/**
	 * Gue.Energeiversorgung
	 */
	public ColumnDescriptor energieversorgung;
	/**
	 * Abhaengigkeit.Fstr
	 */
	public ColumnDescriptor fstr;
	/**
	 * S: Sskp.Ina.Gef_Stelle
	 */
	public ColumnDescriptor gef_Stelle;
	/**
	 * T: Sskp.Ina.Gef_Stelle_Abstand
	 */
	public ColumnDescriptor gef_Stelle_Abstand;
	/**
	 * Gue.Messfehler
	 */
	public ColumnDescriptor messfehler;
	/**
	 * Gue.Messstrecke
	 */
	public ColumnDescriptor messstrecke;
	/**
	 * Gue.Pruefgeschwindigkeit
	 */
	public ColumnDescriptor pruefgeschwindigkeit;
	/**
	 * Gue.Pruefzeit
	 */
	public ColumnDescriptor pruefzeit;
	/**
	 * Grundsatzangaben.PZB_Schutzpunkt
	 */
	public ColumnDescriptor pzb_schutzpunkt;
	/**
	 * Grundsatzangaben.PZB_Schutzstrecke.PZB_Schutzstrecke_Ist
	 */
	public ColumnDescriptor pzb_schutzstrecke_ist;

	/**
	 * C: Sskp.Grundsatzangaben.PZB_Schutzstrecke.PZB_Schutzstrecke_Soll
	 */
	public ColumnDescriptor pzb_schutzstrecke_soll;

	/**
	 * Grundsatzangaben.Signal_Weiche
	 */
	public ColumnDescriptor signal_weiche;

	/**
	 * Q: Sskp.Abhaengigkeit.Weichenlage
	 */
	public ColumnDescriptor weichenlage;

	/**
	 * E: Sskp.Grundsatzangaben.Wirkfrequenz
	 */
	public ColumnDescriptor wirkfrequenz;

	/**
	 * Grundsatzangaben.Wirksamkeit
	 */
	public ColumnDescriptor wirksamkeit;

	/**
	 * creates the columns
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SskpColumns(final Messages messages) {
		super(messages);
		this.signal_weiche = createNew(
				messages.SskpTableView_Grundsatzangaben_SignalWeiche);
		this.pzb_schutzpunkt = createNew(
				messages.SskpTableView_Grundsatzangaben_PzbSchutzpunkt);
		this.pzb_schutzstrecke_soll = createNew(
				messages.SskpTableView_Grundsatzangaben_PzbSchutzstreckeSoll);
		this.pzb_schutzstrecke_ist = createNew(
				messages.SskpTableView_Grundsatzangaben_PzbSchutzstreckeIst);
		this.wirkfrequenz = createNew(
				messages.SskpTableView_Grundsatzangaben_Wirkfrequenz);
		this.abstand_zu = createNew(
				messages.SskpTableView_Grundsatzangaben_AbstandSignalWeiche);
		this.wirksamkeit = createNew(
				messages.SskpTableView_Grundsatzangaben_Wirksamkeit);
		this.pruefgeschwindigkeit = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_Vpruef);
		this.pruefzeit = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_Tpruef);
		this.messstrecke = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_Messstrecke);
		this.messfehler = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_Messfehler);
		this.anordnung = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_Anordnung);
		this.abweich_Abstand = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_Abstand_abweichend);
		this.bauart = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_Bauart);
		this.energieversorgung = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_Energieversorgung);
		this.bef_GPE = createNew(
				messages.SskpTableView_GeschwindigkeitsUeberwEinrichtung_BefestigungGPE);
		this.weichenlage = createNew(
				messages.SskpTableView_Abhaengigkeit_Weichenlage);
		this.fstr = createNew(messages.SskpTableView_Abhaengigkeit_Fahrstrasse);
		this.gef_Stelle = createNew(
				messages.SskpTableView_INA_MassgebendeGefahrenstelle);
		this.gef_Stelle_Abstand = createNew(messages.SskpTableView_INA_Abstand);
		this.abstand_H_Tafel = createNew(
				messages.SskpTableView_INA_AbstandHTafel);
	}
}