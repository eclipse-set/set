/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssln;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Symbolic addressing for Ssln columns.
 * 
 * @author Schaefer
 */
public class SslnColumns extends AbstractTableColumns {

	/**
	 * B: Ssln.Grundsatzangaben.Art
	 */
	public final ColumnDescriptor art;

	/**
	 * E: Ssln.Unterstellungsverhaeltnis.Aufloesung_Grenze
	 */
	public final ColumnDescriptor aufloesung_grenze;

	/**
	 * L: Ssln.Element.Bedien_Einr
	 */
	public final ColumnDescriptor bedien_einr;

	/**
	 * M: Ssln.NB_R.Bedienungshandlung
	 */
	public final ColumnDescriptor bedienungshandlung;

	/**
	 * A: Ssln.Grundsatzangaben.Bereich_Zone
	 */
	public final ColumnDescriptor bereich_zone;

	/**
	 * F: Ssln.Grenze.Bez_Grenze
	 */
	public final ColumnDescriptor bez_grenze;

	/**
	 * J: Ssln.Element.Signal.Kennlicht
	 */
	public final ColumnDescriptor kennlicht;

	/**
	 * D: Ssln.Unterstellungsverhaeltnis.Rang_Zuschaltung
	 */
	public final ColumnDescriptor rang_zuschaltung;

	/**
	 * I: Ssln.Element.Signal.frei_stellbar
	 */
	public final ColumnDescriptor signal_frei_stellbar;

	/**
	 * K: Ssln.Element.Ssp
	 */
	public final ColumnDescriptor ssp;

	/**
	 * C: Ssln.Unterstellungsverhaeltnis.untergeordnet
	 */
	public final ColumnDescriptor untergeordnet;

	/**
	 * H: Ssln.Element.Weiche_Gs.verschlossen
	 */
	public final ColumnDescriptor verschlossen;

	/**
	 * G: Ssln.Element.Weiche_Gs.frei_stellbar
	 */
	public final ColumnDescriptor weiche_gs_frei_stellbar;

	/**
	 * @param messages
	 *            the messages
	 */
	public SslnColumns(final Messages messages) {
		super(messages);
		bereich_zone = createNew(
				messages.SslnTableView_Grundsatzangaben_Bereich_Zone);
		art = createNew(messages.SslnTableView_Grundsatzangaben_Art);
		untergeordnet = createNew(
				messages.SslnTableView_Unterstellungsverhaeltnis_untergeordnet);
		rang_zuschaltung = createNew(
				messages.SslnTableView_Unterstellungsverhaeltnis_Rang_Zuschaltung);
		aufloesung_grenze = createNew(
				messages.SslnTableView_Unterstellungsverhaeltnis_Aufloesung_Grenze);
		bez_grenze = createNew(messages.SslnTableView_Grenze_Bez_Grenze);
		weiche_gs_frei_stellbar = createNew(
				messages.SslnTableView_Element_Weiche_Gs_frei_stellbar);
		verschlossen = createNew(
				messages.SslnTableView_Element_Weiche_Gs_verschlossen);
		signal_frei_stellbar = createNew(
				messages.SslnTableView_Element_Signal_frei_stellbar);
		kennlicht = createNew(messages.SslnTableView_Element_Signal_Kennlicht);
		ssp = createNew(messages.SslnTableView_Element_Ssp);
		bedien_einr = createNew(messages.SslnTableView_Element_Bedien_Einr);
		bedienungshandlung = createNew(
				messages.SslnTableView_Ssln_NB_R_Bedienungshandlung);
	}
}
