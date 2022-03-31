/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskf;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sskf-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SskfColumns extends AbstractTableColumns {

	/**
	 * Auswertung.AeA
	 */
	public final ColumnDescriptor aea;
	/**
	 * Grundsatzangaben.Art
	 */
	public ColumnDescriptor art;
	/**
	 * NfTf_GSK.Laenge.beeinfl
	 */
	public ColumnDescriptor beeinfl;
	/**
	 * R: Sskf.Sonstiges.OlA.Bezeichner
	 */
	public ColumnDescriptor Bezeichner;
	/**
	 * Grundsatzangaben.Bezeichnung
	 */
	public ColumnDescriptor bezeichnung_freimeldeabschnitt;
	/**
	 * NfTf_GSK.Laenge.elektr
	 */
	public ColumnDescriptor elektr;
	/**
	 * Sonstiges.Funktion
	 */
	public ColumnDescriptor funktion;
	/**
	 * Sonstiges.HFmeldung
	 */
	public ColumnDescriptor hfmeldung;
	/**
	 * Ftgs.Laenge.l1
	 */
	public ColumnDescriptor l1;
	/**
	 * Ftgs.Laenge.l2
	 */
	public ColumnDescriptor l2;
	/**
	 * Ftgs.Laenge.l3
	 */
	public ColumnDescriptor l3;
	/**
	 * Ftgs.Laenge.ls
	 */
	public ColumnDescriptor ls;
	/**
	 * Sonstiges.Rbmin
	 */
	public ColumnDescriptor rbmin_mit;
	/**
	 * Sonstiges.OlA.Schaltgruppe
	 */
	public ColumnDescriptor schaltgruppe;
	/**
	 * Grundsatzangaben.Teilabschnitt.TA_Bez
	 */
	public ColumnDescriptor ta_bez;
	/**
	 * Grundsatzangaben.Teilabschnitt.TA_E_A
	 */
	public ColumnDescriptor ta_e_a;
	/**
	 * Grundsatzangaben.Typ
	 */
	public ColumnDescriptor typ;
	/**
	 * Auswertung.Uebertragung.nach
	 */
	public ColumnDescriptor uebertragung_nach;
	/**
	 * Auswertung.Uebertragung.Typ
	 */
	public final ColumnDescriptor uebertragung_typ;
	/**
	 * Auswertung.Uebertragung.von
	 */
	public ColumnDescriptor uebertragung_von;
	/**
	 * P: Sskf.Sonstiges.Weiche
	 */
	public ColumnDescriptor Weiche;

	/**
	 * creates the columns
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SskfColumns(final Messages messages) {
		super(messages);
		this.bezeichnung_freimeldeabschnitt = createNew(
				messages.SskfTableView_Grundsatzangaben_Bezeichnung);
		ta_bez = createNew(
				messages.SskfTableView_Grundsatzangaben_Teilabschnitt_TA_Bez);
		ta_e_a = createNew(
				messages.SskfTableView_Grundsatzangaben_Teilabschnitt_TA_E_A);
		art = createNew(messages.SskfTableView_Grundsatzangaben_Art);
		typ = createNew(messages.SskfTableView_Grundsatzangaben_Typ);
		aea = createNew(messages.SskfTableView_Auswertung_AeA);
		uebertragung_von = createNew(
				messages.SskfTableView_Auswertung_Uebertragung_von);
		uebertragung_nach = createNew(
				messages.SskfTableView_Auswertung_Uebertragung_nach);
		uebertragung_typ = createNew(
				messages.SskfTableView_Auswertung_Uebertragung_Typ);
		ls = createNew(messages.SskfTableView_Ftgs_Laenge_ls);
		l1 = createNew(messages.SskfTableView_Ftgs_Laenge_l1);
		l2 = createNew(messages.SskfTableView_Ftgs_Laenge_l2);
		l3 = createNew(messages.SskfTableView_Ftgs_Laenge_l3);
		elektr = createNew(messages.SskfTableView_NfTf_GSK_Laenge_elektr);
		beeinfl = createNew(messages.SskfTableView_NfTf_GSK_Laenge_beeinfl);
		Weiche = createNew(messages.Sskf_Sonstiges_Weiche);
		schaltgruppe = createNew(
				messages.SskfTableView_Sonstiges_OlA_Schaltgruppe);
		Bezeichner = createNew(messages.SskfTableView_Sonstiges_OlA_Bezeichner);
		rbmin_mit = createNew(messages.SskfTableView_Sonstiges_Rbmin_mit);
		hfmeldung = createNew(messages.SskfTableView_Sonstiges_HFmeldung);
		funktion = createNew(messages.SskfTableView_Sonstiges_Funktion);
	}
}