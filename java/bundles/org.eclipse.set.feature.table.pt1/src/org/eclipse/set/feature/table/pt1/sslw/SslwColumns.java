/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslw;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Sslw-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 */
public final class SslwColumns extends AbstractTableColumns {

	/**
	 * Sslw.Ersatzschutz_unmittelbar.Signal.Bezeichnung_Sig
	 */
	public ColumnDescriptor bezeichnung_sig;

	/**
	 * Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Bezeichnung_W
	 */
	public ColumnDescriptor bezeichnung_w;

	/**
	 * Sslw.Ersatzschutz_Weitergabe.Weiche_Kreuzung.Bezeichnung_W_Kr
	 */
	public ColumnDescriptor bezeichnung_w_kr;

	/**
	 * Sslw.Art.Echt
	 */
	public ColumnDescriptor echt;

	/**
	 * Sslw.Art.Eigen
	 */
	public ColumnDescriptor eigen;

	/**
	 * Sslw.Ersatzschutz_Weitergabe.Zusaetzlich_EKW.Bezeichnung_W_Kr
	 */
	public ColumnDescriptor ekw_bezeichnung_w_kr;

	/**
	 * Sslw.Ersatzschutz_Weitergabe.Zusaetzlich_EKW.wie_Fahrt_ueber
	 */
	public ColumnDescriptor ekw_wie_fahrt_ueber;

	/**
	 * O: Sslw.Schutzraumueberwachung.freigemeldet
	 */
	public ColumnDescriptor freigemeldet;

	/**
	 * Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Lage
	 */
	public ColumnDescriptor lage;

	/**
	 * Sslw.Nachlaufverhinderung
	 */
	public ColumnDescriptor nachlaufverhinderung;

	/**
	 * P: Sslw.Schutzraumueberwachung.nicht_freigemeldet
	 */
	public ColumnDescriptor nicht_freigemeldet;

	/**
	 * Sslw.Technischer_Verzicht
	 */
	public ColumnDescriptor technischer_verzicht;

	/**
	 * Sslw.Verschluss
	 */
	public ColumnDescriptor verschluss;

	/**
	 * Sslw.W_Kr_Stellung
	 */
	public ColumnDescriptor w_kr_stellung;

	/**
	 * Sslw.Ersatzschutz_Weitergabe.Weiche_Kreuzung.wie_Fahrt_ueber
	 */
	public ColumnDescriptor wie_fahrt_ueber;

	/**
	 * Sslw.Ersatzschutz_unmittelbar.Signal.Zielsperrung
	 */
	public ColumnDescriptor rangierzielsperre;

	/**
	 * Sslw.Ersatzschutz_unmittelbar.Weiche_Gleissperre.Zwieschutz
	 */
	public ColumnDescriptor zwieschutz;

	/**
	 * creates the columns instance.
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SslwColumns(final Messages messages) {
		super(messages);
		w_kr_stellung = createNew(messages.SslwTableView_W_Kr_Stellung);
		eigen = createNew(messages.SslwTableView_Art_Eigen);
		echt = createNew(messages.SslwTableView_Art_Echt);
		verschluss = createNew(messages.SslwTableView_Verschluss);
		bezeichnung_w = createNew(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Weiche_Gleissperre_Bezeichnung_W);
		lage = createNew(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Weiche_Gleissperre_Lage);
		zwieschutz = createNew(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Weiche_Gleissperre_Zwieschutz);
		bezeichnung_sig = createNew(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Signal_Bezeichnung_Sig);
		rangierzielsperre = createNew(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Signal_Rangierzielsperre);
		bezeichnung_w_kr = createNew(
				messages.SslwTableView_Ersatzschutz_Weitergabe_Weiche_Kreuzung_Bezeichnung_W_Kr);
		wie_fahrt_ueber = createNew(
				messages.SslwTableView_Ersatzschutz_Weitergabe_Weiche_Kreuzung_wie_Fahrt_ueber);
		ekw_bezeichnung_w_kr = createNew(
				messages.SslwTableView_Ersatzschutz_Weitergabe_Zusaetzlich_EKW_Bezeichnung_W_Kr);
		ekw_wie_fahrt_ueber = createNew(
				messages.SslwTableView_Ersatzschutz_Weitergabe_Zusaetzlich_EKW_wie_Fahrt_ueber);
		technischer_verzicht = createNew(
				messages.SslwTableView_Technischer_Verzicht);
		freigemeldet = createNew(
				messages.SslwTableView_Schutzraumueberwachung_freigemeldet);
		nicht_freigemeldet = createNew(
				messages.SslwTableView_Schutzraumueberwachung_nicht_freigemeldet);
		nachlaufverhinderung = createNew(
				messages.SslwTableView_Nachlaufverhinderung);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SslwTableView_Heading);
		root.add(w_kr_stellung).width(2.46f);
		final GroupBuilder art = root.addGroup(messages.SslwTableView_Art);
		art.add(eigen).width(0.87f);
		art.add(echt).width(0.7f);
		root.add(verschluss).width(1.61f);
		final GroupBuilder ersatzschutz = root
				.addGroup(messages.SslwTableView_Ersatzschutz_unmittelbar);
		final GroupBuilder weicheGleissperre = ersatzschutz.addGroup(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Weiche_Gleissperre);
		weicheGleissperre.add(bezeichnung_w).width(1.71f);
		weicheGleissperre.add(lage).width(0.78f);
		weicheGleissperre.add(zwieschutz).width(1.57f);
		final GroupBuilder signal = ersatzschutz.addGroup(
				messages.SslwTableView_Ersatzschutz_unmittelbar_Signal);
		signal.add(bezeichnung_sig).width(1.71f);
		signal.add(rangierzielsperre).width(1.65f);

		final GroupBuilder ersatzschutzWeitergabe = root
				.addGroup(messages.SslwTableView_Ersatzschutz_Weitergabe);
		final GroupBuilder weicheKreuzung = ersatzschutzWeitergabe.addGroup(
				messages.SslwTableView_Ersatzschutz_Weitergabe_Weiche_Kreuzung);
		weicheKreuzung.add(bezeichnung_w_kr).width(1.71f);
		weicheKreuzung.add(wie_fahrt_ueber).dimension(1.27f, LINE_HEIGHT * 2);

		final GroupBuilder zusaetzlichBeiEkw = ersatzschutzWeitergabe.addGroup(
				messages.SslwTableView_Ersatzschutz_Weitergabe_Zusaetzlich_EKW);
		zusaetzlichBeiEkw.add(ekw_bezeichnung_w_kr).width(1.71f);
		zusaetzlichBeiEkw.add(ekw_wie_fahrt_ueber).width(1.23f);

		root.add(technischer_verzicht).width(1.74f);
		final GroupBuilder schutzraumUeberwachung = root
				.addGroup(messages.SslwTableView_Schutzraumueberwachung);
		schutzraumUeberwachung.add(freigemeldet).width(1.57f);
		schutzraumUeberwachung.add(nicht_freigemeldet).width(1.8f);

		root.add(nachlaufverhinderung).width(1.99f);
		root.add(basis_bemerkung).width(7.51f);
		return root.getGroupRoot();
	}

}