/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskw;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sskw-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SskwColumns extends AbstractTableColumns {

	/**
	 * Weiche.Auffahrortung
	 */
	public final ColumnDescriptor auffahrortung;
	/**
	 * Gleissperre.Auswurfrichtung
	 */
	public final ColumnDescriptor auswurfrichtung;
	/**
	 * Vorzugslage.Automatik
	 */
	public final ColumnDescriptor automatik;
	/**
	 * Sonstiges.DWs
	 */
	public final ColumnDescriptor dws;
	/**
	 * Freimeldung.Fma
	 */
	public final ColumnDescriptor fma;
	/**
	 * Weiche_Kreuzung_Gleissperre.Form
	 */
	public final ColumnDescriptor form;
	/**
	 * Gleissperre.Antriebe
	 */
	public final ColumnDescriptor gleissperre_antriebe;
	/**
	 * Gleissperre.Gsp_signal
	 */
	public final ColumnDescriptor gsp_signal;
	/**
	 * Herzstueck.Antriebe
	 */
	public final ColumnDescriptor herzstuecke_antriebe;
	/**
	 * Freimeldung.Isolierfall
	 */
	public final ColumnDescriptor isolierfall;
	/**
	 * Vorzugslage.Lage
	 */
	public final ColumnDescriptor lage;
	/**
	 * Freimeldung.nicht_grenzzeichenfrei.Links
	 */
	public final ColumnDescriptor links;
	/**
	 * Weiche.Pruefkontakte
	 */
	public final ColumnDescriptor pruefkontakte;

	/**
	 * Freimeldung.nicht_grenzzeichenfrei.Rechts
	 */
	public final ColumnDescriptor rechts;
	/**
	 * Sonstiges.Regelzeichnung_Nr
	 */
	public final ColumnDescriptor regelzeichnung_nr;
	/**
	 * Gleissperre.Schutzschiene
	 */
	public final ColumnDescriptor schutzschiene;

	/**
	 * X: Sskw.Sonderanlage
	 */
	public final ColumnDescriptor Sonderanlage_Art;

	/**
	 * O: Sskw.Kreuzung.v_zul_K.Links
	 */
	public final ColumnDescriptor v_zul_k_links;
	/**
	 * P: Sskw.Kreuzung.v_zul_K.Rechts
	 */
	public final ColumnDescriptor v_zul_k_rechts;
	/**
	 * Weiche.v_zul_W.Links
	 */
	public final ColumnDescriptor v_zul_w_links;

	/**
	 * Weiche.v_zul_W.Rechts
	 */
	public final ColumnDescriptor v_zul_w_rechts;

	/**
	 * Weiche.Antriebe
	 */
	public final ColumnDescriptor weiche_antriebe;

	/**
	 * Weiche.Weichensignal
	 */
	public final ColumnDescriptor weichensignal;

	/**
	 * @param messages
	 *            the i8n messages
	 */
	public SskwColumns(final Messages messages) {
		super(messages);
		form = createNew(
				messages.SskwTableView_Weiche_Kreuzung_Gleissperre_Form);
		fma = createNew(messages.SskwTableView_Freimeldung_Fma);
		links = createNew(
				messages.SskwTableView_Freimeldung_nicht_grenzzeichenfrei_Links);
		rechts = createNew(
				messages.SskwTableView_Freimeldung_nicht_grenzzeichenfrei_Rechts);
		isolierfall = createNew(messages.SskwTableView_Freimeldung_Isolierfall);
		lage = createNew(messages.SskwTableView_Vorzugslage_Lage);
		automatik = createNew(messages.SskwTableView_Vorzugslage_Automatik);
		auffahrortung = createNew(messages.SskwTableView_Weiche_Auffahrortung);
		weiche_antriebe = createNew(messages.SskwTableView_Weiche_Antriebe);
		weichensignal = createNew(messages.SskwTableView_Weiche_Weichensignal);
		pruefkontakte = createNew(messages.SskwTableView_Weiche_Pruefkontakte);
		v_zul_w_links = createNew(messages.SskwTableView_Weiche_v_zul_W_Links);
		v_zul_w_rechts = createNew(
				messages.SskwTableView_Weiche_v_zul_W_Rechts);
		v_zul_k_links = createNew(
				messages.SskwTableView_Kreuzung_v_zul_K_Links);
		v_zul_k_rechts = createNew(
				messages.SskwTableView_Kreuzung_v_zul_K_Rechts);
		herzstuecke_antriebe = createNew(
				messages.SskwTableView_Herzstueck_Antriebe);
		gleissperre_antriebe = createNew(
				messages.SskwTableView_Gleissperre_Antriebe);
		gsp_signal = createNew(messages.SskwTableView_Gleissperre_Gsp_signal);
		auswurfrichtung = createNew(
				messages.SskwTableView_Gleissperre_Auswurfrichtung);
		schutzschiene = createNew(
				messages.SskwTableView_Gleissperre_Schutzschiene);
		regelzeichnung_nr = createNew(
				messages.SskwTableView_Sonstiges_Regelzeichnung_Nr);
		dws = createNew(messages.SskwTableView_Sonstiges_DWs);
		Sonderanlage_Art = createNew(messages.SskwTableView_Sonderanlage_Art);
	}

}
