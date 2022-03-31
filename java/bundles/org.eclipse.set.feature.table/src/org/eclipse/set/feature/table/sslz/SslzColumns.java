/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslz;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sslz Columns.
 * 
 * @author Schaefer
 * 
 * @see ColumnDescriptor
 */
public class SslzColumns extends AbstractTableColumns {

	/**
	 * L: Sslz.Abhaengigkeiten.Abhaengiger_BUe
	 */
	public final ColumnDescriptor abhaengiger_bue;

	/**
	 * K: Sslz.Abhaengigkeiten.Ueberwachte_Ssp
	 */
	public final ColumnDescriptor Ueberwachte_Ssp;

	/**
	 * O: Sslz.Abhaengigkeiten.Anrueckverschluss
	 */
	public final ColumnDescriptor anrueckverschluss;

	/**
	 * G: Sslz.Grundsatzangaben.Art
	 */
	public final ColumnDescriptor art;

	/**
	 * U: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Aufwertung_Mwtfstr
	 */
	public final ColumnDescriptor aufwertung_mwtfstr;

	/**
	 * H: Sslz.Einstellung.Autom_Einstellung
	 */
	public final ColumnDescriptor autom_einstellung;

	/**
	 * S: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Besonders
	 */
	public final ColumnDescriptor besonders;

	/**
	 * F: Sslz.Grundsatzangaben.Durchrutschweg.Bezeichnung
	 */
	public final ColumnDescriptor durchrutschweg_bezeichnung;

	/**
	 * R: Sslz.Signalisierung.Geschwindigkeit_Startsignal.DWeg
	 */
	public final ColumnDescriptor dweg;

	/**
	 * E: Sslz.Grundsatzangaben.Fahrweg.Entscheidungsweiche
	 */
	public final ColumnDescriptor entscheidungsweiche;

	/**
	 * I: Sslz.Einstellung.F_Bedienung
	 */
	public final ColumnDescriptor f_bedienung;

	/**
	 * Q: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Fahrweg
	 */
	public final ColumnDescriptor fahrweg;

	/**
	 * A: Sslz.Grundsatzangaben.Bezeichnung
	 */
	public final ColumnDescriptor grundsatzangaben_bezeichnung;

	/**
	 * P: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Hg
	 */
	public final ColumnDescriptor hg;

	/**
	 * AB: Sslz.Signalisierung.Im_Fahrweg.Zs3
	 */
	public final ColumnDescriptor im_fahrweg_zs3;

	/**
	 * AC: Sslz.Signalisierung.Im_Fahrweg.Zs6
	 */
	public final ColumnDescriptor im_fahrweg_zs6;

	/**
	 * J: Sslz.Abhaengigkeiten.Inselgleis
	 */
	public final ColumnDescriptor inselgleis;

	/**
	 * AD: Sslz.Signalisierung.Im_Fahrweg.Kennlicht
	 */
	public final ColumnDescriptor kennlicht;

	/**
	 * M: Sslz.Abhaengigkeiten.Nichthaltfallabschnitt
	 */
	public final ColumnDescriptor nichthaltfallabschnitt;

	/**
	 * D: Sslz.Grundsatzangaben.Fahrweg.Nummer
	 */
	public final ColumnDescriptor nummer;

	/**
	 * T: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Zs3
	 */
	public final ColumnDescriptor signalisierung_zs3;

	/**
	 * Z: Sslz.Signalisierung.Sonstiges_Startsignal.Zs6
	 */
	public final ColumnDescriptor sonstiges_startsignal_zs6;

	/**
	 * B: Sslz.Grundsatzangaben.Fahrweg.Start
	 */
	public final ColumnDescriptor start;

	/**
	 * AE: Sslz.Signalisierung.Im_Fahrweg.Vorsignalisierung
	 */
	public ColumnDescriptor vorsignalisierung;

	/**
	 * C: Sslz.Grundsatzangaben.Fahrweg.Ziel
	 */
	public final ColumnDescriptor ziel;

	/**
	 * AA: Sslz.Signalisierung.Sonstiges_Startsignal.Zs13
	 */
	public final ColumnDescriptor zs13;

	/**
	 * X: Sslz.Signalisierung.Sonstiges_Startsignal.Zs2
	 */
	public final ColumnDescriptor zs2;

	/**
	 * Y: Sslz.Signalisierung.Sonstiges_Startsignal.Zs2v
	 */
	public final ColumnDescriptor zs2v;

	/**
	 * W: Sslz.Signalisierung.Sonstiges_Startsignal.Zs3v
	 */
	public final ColumnDescriptor zs3v;

	/**
	 * V: Sslz.Signalisierung.Sonstiges_Startsignal.Zusatzlicht
	 */
	public final ColumnDescriptor zusatzlicht;

	/**
	 * N: Sslz.Abhaengigkeiten.Zweites_Haltfallkrit
	 */
	public final ColumnDescriptor Zweites_Haltfallkrit;

	/**
	 * @param messages
	 *            the messages
	 */
	public SslzColumns(final Messages messages) {
		super(messages);
		grundsatzangaben_bezeichnung = createNew(
				messages.SslzTableView_Grundsatzangaben_Bezeichnung);
		start = createNew(
				messages.SslzTableView_Grundsatzangaben_Fahrweg_Start);
		ziel = createNew(messages.SslzTableView_Grundsatzangaben_Fahrweg_Ziel);
		nummer = createNew(messages.SslzTableView_Grundsatzangaben_Fahrweg_Nr);
		entscheidungsweiche = createNew(
				messages.SslzTableView_Grundsatzangaben_Fahrweg_EntschWeiche);
		durchrutschweg_bezeichnung = createNew(
				messages.SslzTableView_Grundsatzangaben_Dweg_Bezeichnung);
		art = createNew(messages.SslzTableView_Grundsatzangaben_Art);
		autom_einstellung = createNew(messages.SslzTableView_Einstellung_Auto);
		f_bedienung = createNew(messages.SslzTableView_Einstellung_F);
		inselgleis = createNew(
				messages.SslzTableView_Abhaengigkeiten_Inselgleis);
		Ueberwachte_Ssp = createNew(
				messages.SslzTableView_Abhaengigkeiten_Ueberwachte_Ssp);
		abhaengiger_bue = createNew(messages.SslzTableView_Abhaengigkeiten_BUE);
		nichthaltfallabschnitt = createNew(
				messages.SslzTableView_Abhaengigkeiten_NichthaltfallAbschnitt);
		Zweites_Haltfallkrit = createNew(
				messages.SslzTableView_Abhaengigkeiten_Zweites_Haltfallkrit);
		anrueckverschluss = createNew(
				messages.SslzTableView_Abhaengigkeiten_Anrueckverschluss);
		hg = createNew(
				messages.SslzTableView_Signalisierung_GeschwAmStartsignal_VZG);
		fahrweg = createNew(
				messages.SslzTableView_Signalisierung_GeschwAmStartsignal_Fahrweg);
		dweg = createNew(
				messages.SslzTableView_Signalisierung_GeschwAmStartsignal_DWeg);
		besonders = createNew(
				messages.SslzTableView_Signalisierung_GeschwAmStartsignal_Besonders);
		signalisierung_zs3 = createNew(
				messages.SslzTableView_Signalisierung_GeschwAmStartsignal_Zs3);
		aufwertung_mwtfstr = createNew(
				messages.SslzTableView_Signalisierung_GeschwAmStartsignal_Aufwertung);
		zusatzlicht = createNew(
				messages.SslzTableView_Signalisierung_Sonstiges_Zusatzlicht);
		zs3v = createNew(messages.SslzTableView_Signalisierung_Sonstiges_Zs3v);
		zs2 = createNew(messages.SslzTableView_Signalisierung_Sonstiges_Zs2);
		zs2v = createNew(messages.SslzTableView_Signalisierung_Sonstiges_Zs2v);
		sonstiges_startsignal_zs6 = createNew(
				messages.SslzTableView_Signalisierung_Sonstiges_Zs6);
		zs13 = createNew(messages.SslzTableView_Signalisierung_Sonstiges_Zs13);
		im_fahrweg_zs3 = createNew(
				messages.SslzTableView_Signalisierung_ImFahrweg_Zs3);
		im_fahrweg_zs6 = createNew(
				messages.SslzTableView_Signalisierung_ImFahrweg_Zs6);
		kennlicht = createNew(
				messages.SslzTableView_Signalisierung_ImFahrweg_Kennlicht);
		vorsignalisierung = createNew(
				messages.SslzTableView_Signalisierung_ImFahrweg_Vorsignalisierung);
	}
}
