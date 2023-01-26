/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskw;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

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

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskwTableView_Heading);
		final GroupBuilder fundamental = root.addGroup(
				messages.SskwTableView_Weiche_Kreuzung_Gleissperre_Sonderanlage);
		fundamental.add(basis_bezeichnung).width(1.93f);
		fundamental.add(form).width(3.56f);

		final GroupBuilder freimeldung = root
				.addGroup(messages.SskwTableView_Freimeldung);
		freimeldung.add(fma).width(1.42f);
		final GroupBuilder nichtgrenzzeichenfrei = freimeldung.addGroup(
				messages.SskwTableView_Freimeldung_nicht_grenzzeichenfrei)
				.height(LINE_HEIGHT * 1.5f);
		nichtgrenzzeichenfrei.add(1.42f, links, rechts);
		freimeldung.add(isolierfall).width(1.42f);

		final GroupBuilder vorzugslage = root
				.addGroup(messages.SskwTableView_Vorzugslage);
		vorzugslage.add(lage).dimension(1.1f, LINE_HEIGHT * 2);
		vorzugslage.add(automatik).width(1.1f);

		final GroupBuilder weiche = root
				.addGroup(messages.SskwTableView_Weiche);
		weiche.add(auffahrortung).width(1.42f);
		weiche.add(weiche_antriebe, messages.Common_UnitPiece).width(1.42f);
		weiche.add(weichensignal).width(1.42f);
		weiche.add(pruefkontakte, messages.Common_UnitPiece).width(1.42f);

		final GroupBuilder zulaessigeGeschwindigkeit = weiche
				.addGroup(messages.SskwTableView_Weiche_v_zul_W);
		zulaessigeGeschwindigkeit.add(v_zul_w_links, messages.Common_UnitKmh)
				.width(1.19f);
		zulaessigeGeschwindigkeit.add(v_zul_w_rechts, messages.Common_UnitKmh)
				.width(1.19f);

		final GroupBuilder kreuzung = root
				.addGroup(messages.SskwTableView_Kreuzung);
		final GroupBuilder zulaessigeGeschwKreuzung = kreuzung
				.addGroup(messages.SskwTableView_Kreuzung_v_zul_K);

		zulaessigeGeschwKreuzung.add(v_zul_k_links, messages.Common_UnitKmh)
				.width(1.19f);
		zulaessigeGeschwKreuzung.add(v_zul_k_rechts, messages.Common_UnitKmh)
				.width(1.19f);

		final GroupBuilder herzstueck = root
				.addGroup(messages.SskwTableView_Herzstueck);
		herzstueck.add(herzstuecke_antriebe, messages.Common_UnitPiece)
				.width(1.1f);

		final GroupBuilder gleissperre = root
				.addGroup(messages.SskwTableView_Gleissperre);
		gleissperre.add(gleissperre_antriebe, messages.Common_UnitPiece)
				.width(1.1f);
		gleissperre.add(1.1f, gsp_signal, auswurfrichtung, schutzschiene);

		final GroupBuilder sonstiges = root
				.addGroup(messages.SskwTableView_Sonstiges);
		sonstiges.add(regelzeichnung_nr).width(2.56f);
		sonstiges.add(dws).width(0.83f);

		final GroupBuilder sonderanlage = root
				.addGroup(messages.SskwTableView_Sonderanlage);
		sonderanlage.add(Sonderanlage_Art).width(1.82f);

		root.add(basis_bemerkung).width(3.75f);

		return root.getGroupRoot();
	}

}
