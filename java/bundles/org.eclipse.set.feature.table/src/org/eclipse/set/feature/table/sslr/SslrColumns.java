/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslr;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sslr Columns.
 * 
 * @author Schaefer
 * 
 * @see ColumnDescriptor
 */
public class SslrColumns extends AbstractTableColumns {

	/**
	 * N: Sslr.Abhaengigkeiten.Abhaengiger_BUe
	 */
	public final ColumnDescriptor Abhaengiger_BUe;

	/**
	 * F: Sslr.Grundsatzangaben.Art
	 */
	public final ColumnDescriptor Art;

	/**
	 * P: Sslr.Abhaengigkeiten.Aufloes_Fstr
	 */
	public final ColumnDescriptor Aufloes_Fstr;

	/**
	 * G: Sslr.Einstellung.Autom_Einstellung
	 */
	public final ColumnDescriptor Autom_Einstellung;

	/**
	 * A: Sslr.Grundsatzangaben.Bezeichnung
	 */
	public ColumnDescriptor Bezeichnung;

	/**
	 * E: Sslr.Grundsatzangaben.Fahrweg.Entscheidungsweiche
	 */
	public final ColumnDescriptor Entscheidungsweiche;

	/**
	 * H: Sslr.Einstellung.F_Bedienung
	 */
	public final ColumnDescriptor F_Bedienung;

	/**
	 * L: Sslr.Abhaengigkeiten.FwWeichen_mit_Fla
	 */
	public final ColumnDescriptor FwWeichen_mit_Fla;

	/**
	 * K: Sslr.Abhaengigkeiten.Gleisfreimeldung
	 */
	public final ColumnDescriptor Gleisfreimeldung;

	/**
	 * I: Sslr.Abhaengigkeiten.Inselgleis.Bezeichnung
	 */
	public final ColumnDescriptor InselgleisBezeichnung;

	/**
	 * J: Sslr.Abhaengigkeiten.Inselgleis.Gegenfahrtausschluss
	 */
	public final ColumnDescriptor Gegenfahrtausschluss;

	/**
	 * D: Sslr.Grundsatzangaben.Fahrweg.Nummer
	 */
	public final ColumnDescriptor Nummer;

	/**
	 * B: Sslr.Grundsatzangaben.Fahrweg.Start
	 */
	public final ColumnDescriptor Start;

	/**
	 * M: Sslr.Abhaengigkeiten.Ueberwachte_Ssp
	 */
	public final ColumnDescriptor Ueberwachte_Ssp;

	/**
	 * C: Sslr.Grundsatzangaben.Fahrweg.Ziel
	 */
	public final ColumnDescriptor Ziel;

	/**
	 * O: Sslr.Abhaengigkeiten.Ziel_erlaubnisabh
	 */
	public final ColumnDescriptor Ziel_erlaubnisabh;

	/**
	 * @param messages
	 *            the messages
	 */
	public SslrColumns(final Messages messages) {
		super(messages);
		Bezeichnung = createNew(messages.Sslr_Grundsatzangaben_Bezeichnung);
		Start = createNew(messages.Sslr_Grundsatzangaben_Fahrweg_Start);
		Ziel = createNew(messages.Sslr_Grundsatzangaben_Fahrweg_Ziel);
		Nummer = createNew(messages.Sslr_Grundsatzangaben_Fahrweg_Nummer);
		Entscheidungsweiche = createNew(
				messages.Sslr_Grundsatzangaben_Fahrweg_Entscheidungsweiche);
		Art = createNew(messages.Sslr_Grundsatzangaben_Art);
		Autom_Einstellung = createNew(
				messages.Sslr_Einstellung_Autom_Einstellung);
		F_Bedienung = createNew(messages.Sslr_Einstellung_F_Bedienung);
		InselgleisBezeichnung = createNew(
				messages.Sslr_Abhaengigkeiten_Inselgleis_Bezeichnung);
		Gegenfahrtausschluss = createNew(
				messages.Sslr_Abhaengigkeiten_Inselgleis_Gegenfahrtausschluss);
		Gleisfreimeldung = createNew(
				messages.Sslr_Abhaengigkeiten_Gleisfreimeldung);
		FwWeichen_mit_Fla = createNew(
				messages.Sslr_Abhaengigkeiten_FwWeichen_mit_Fla);
		Ueberwachte_Ssp = createNew(
				messages.Sslr_Abhaengigkeiten_Ueberwachte_Ssp);
		Abhaengiger_BUe = createNew(
				messages.Sslr_Abhaengigkeiten_Abhaengiger_BUe);
		Ziel_erlaubnisabh = createNew(
				messages.Sslr_Abhaengigkeiten_Ziel_erlaubnisabh);
		Aufloes_Fstr = createNew(messages.Sslr_Abhaengigkeiten_Aufloes_Fstr);
	}
}
