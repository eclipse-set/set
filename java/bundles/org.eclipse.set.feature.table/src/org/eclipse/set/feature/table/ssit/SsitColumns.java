/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssit;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Symbolic addressing for Ssit columns.
 * 
 * @author Schaefer
 */
public class SsitColumns extends AbstractTableColumns {

	/**
	 * Q: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.An_Zeit_Hupe
	 */
	public final ColumnDescriptor An_Zeit_Hupe;

	/**
	 * J: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Anf_NB
	 */
	public final ColumnDescriptor Anf_NB;

	/**
	 * D: Ssit.Grundsatzangaben.Befestigung.Art
	 */
	public final ColumnDescriptor Art;

	/**
	 * C: Ssit.Grundsatzangaben.Bauart
	 */
	public final ColumnDescriptor Bauart;

	/**
	 * A: Ssit.Grundsatzangaben.Bezeichnung
	 */
	public final ColumnDescriptor Bezeichnung;

	/**
	 * K: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Fertigmeldung
	 */
	public final ColumnDescriptor Fertigmeldung;

	/**
	 * P: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Freigabe_Ssp
	 */
	public final ColumnDescriptor Freigabe_Ssp;

	/**
	 * F: Ssit.Grundsatzangaben.Befestigung.km
	 */
	public final ColumnDescriptor km;

	/**
	 * G: Ssit.Bedien_Anz_Elemente.Melder
	 */
	public final ColumnDescriptor Melder;

	/**
	 * H: Ssit.Bedien_Anz_Elemente.Schalter
	 */
	public final ColumnDescriptor Schalter;

	/**
	 * E: Ssit.Grundsatzangaben.Befestigung.Strecke
	 */
	public final ColumnDescriptor Strecke;

	/**
	 * I: Ssit.Bedien_Anz_Elemente.Taste
	 */
	public final ColumnDescriptor Taste;

	/**
	 * N: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Gs
	 */
	public final ColumnDescriptor Umst_Gs;

	/**
	 * O: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Sig
	 */
	public final ColumnDescriptor Umst_Sig;

	/**
	 * M: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Weiche
	 */
	public final ColumnDescriptor Umst_Weiche;

	/**
	 * L: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Weichengruppe
	 */
	public final ColumnDescriptor Weichengruppe;

	/**
	 * B: Ssit.Grundsatzangaben.Zug_AEA
	 */
	public final ColumnDescriptor Zug_AEA;

	/**
	 * @param messages
	 *            the messages
	 */
	public SsitColumns(final Messages messages) {
		super(messages);
		Bezeichnung = createNew(
				messages.SsitTableView_Grundsatzangaben_Bezeichnung);
		Zug_AEA = createNew(messages.SsitTableView_Grundsatzangaben_Zug_AEA);
		Bauart = createNew(messages.SsitTableView_Grundsatzangaben_Bauart);
		Art = createNew(
				messages.SsitTableView_Grundsatzangaben_Befestigung_Art);
		Strecke = createNew(
				messages.SsitTableView_Grundsatzangaben_Befestigung_Strecke);
		km = createNew(messages.SsitTableView_Grundsatzangaben_Befestigung_km);
		Melder = createNew(messages.SsitTableView_Bedien_Anz_Elemente_Melder);
		Schalter = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Schalter);
		Taste = createNew(messages.SsitTableView_Bedien_Anz_Elemente_Taste);
		Anf_NB = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Anf_NB);
		Fertigmeldung = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Fertigmeldung);
		Weichengruppe = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Weichengruppe);
		Umst_Weiche = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Umst_Weiche);
		Umst_Gs = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Umst_Gs);
		Umst_Sig = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Umst_Sig);
		Freigabe_Ssp = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Freigabe_Ssp);
		An_Zeit_Hupe = createNew(
				messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_An_Zeit_Hupe);
	}
}
