/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslf;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sslf-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 */
public final class SslfColumns extends AbstractTableColumns {

	/**
	 * Sslf.Unmittelbarer_Flankenschutz.Signal.Bezeichnung_Sig
	 */
	public final ColumnDescriptor bezeichnung_sig;
	/**
	 * Sslf.Unmittelbarer_Flankenschutz.Weiche_Gleissperre.Bezeichnung_W
	 */
	public final ColumnDescriptor bezeichnung_w;
	/**
	 * Sslf.Weitergabe.Weiche_Kreuzung.Bezeichnung_W_Kr
	 */
	public final ColumnDescriptor bezeichnung_w_kr;

	/**
	 * Sslf.Flankenschutzanforderer.Bezeichnung_W_Nb
	 */
	public final ColumnDescriptor bezeichnung_w_nb;
	/**
	 * Sslf.Weitergabe.Zusaetzlich_EKW.Bezeichnung_W_Kr
	 */
	public final ColumnDescriptor ekw_bezeichnung_w_kr;
	/**
	 * Sslf.Weitergabe.Zusaetzlich_EKW.wie_Fahrt_ueber
	 */
	public final ColumnDescriptor ekw_wie_Fahrt_ueber;

	/**
	 * M: Sslf.Schutzraumueberwachung.freigemeldet
	 */
	public final ColumnDescriptor freigemeldet;

	/**
	 * Sslf.Unmittelbarer_Flankenschutz.Weiche_Gleissperre.Lage
	 */
	public final ColumnDescriptor lage;

	/**
	 * N: Sslf.Schutzraumueberwachung.nicht_freigemeldet
	 */
	public final ColumnDescriptor nicht_freigemeldet;

	/**
	 * Sslf.Technischer_Verzicht
	 */
	public final ColumnDescriptor technischer_verzicht;
	/**
	 * Sslf.Weitergabe.Weiche_Kreuzung.wie_Fahrt_ueber
	 */
	public final ColumnDescriptor wie_Fahrt_ueber;
	/**
	 * Sslf.Flankenschutzanforderer.WLage_NbGrenze
	 */
	public final ColumnDescriptor wlage_nbgrenze;

	/**
	 * Sslf.Unmittelbarer_Flankenschutz.Signal.Zielsperrung
	 */
	public final ColumnDescriptor rangierzielsperre;

	/**
	 * Sslf.Unmittelbarer_Flankenschutz.Weiche_Gleissperre.Zwieschutz
	 */
	public final ColumnDescriptor zwieschutz;

	/**
	 * @param messages
	 *            the i8n messages
	 */
	public SslfColumns(final Messages messages) {
		super(messages);
		bezeichnung_w_nb = createNew(
				messages.SslfTableView_Flankenschutzanforderer_Bezeichnung_W_Nb);
		wlage_nbgrenze = createNew(
				messages.SslfTableView_Flankenschutzanforderer_WLage_NbGrenze);
		bezeichnung_w = createNew(
				messages.SslfTableView_Unmittelbarer_Flankenschutz_Weiche_Gleissperre_Bezeichnung_W);
		lage = createNew(
				messages.SslfTableView_Unmittelbarer_Flankenschutz_Weiche_Gleissperre_Lage);
		zwieschutz = createNew(
				messages.SslfTableView_Unmittelbarer_Flankenschutz_Weiche_Gleissperre_Zwieschutz);
		bezeichnung_sig = createNew(
				messages.SslfTableView_Unmittelbarer_Flankenschutz_Signal_Bezeichnung_Sig
						+ " "); //$NON-NLS-1$
		rangierzielsperre = createNew(
				messages.SslfTableView_Unmittelbarer_Flankenschutz_Signal_Rangierzielsperre);
		bezeichnung_w_kr = createNew(
				messages.SslfTableView_Weitergabe_Weiche_Kreuzung_Bezeichnung_W_Kr
						+ " "); //$NON-NLS-1$
		wie_Fahrt_ueber = createNew(
				messages.SslfTableView_Weitergabe_Weiche_Kreuzung_wie_Fahrt_ueber);
		ekw_bezeichnung_w_kr = createNew(
				messages.SslfTableView_Weitergabe_Zusaetzlich_EKW_Bezeichnung_W_Kr);
		ekw_wie_Fahrt_ueber = createNew(
				messages.SslfTableView_Weitergabe_Zusaetzlich_EKW_wie_Fahrt_ueber);
		technischer_verzicht = createNew(
				messages.SslfTableView_Technischer_Verzicht);
		freigemeldet = createNew(
				messages.SslfTableView_Schutzraumueberwachung_freigemeldet);
		nicht_freigemeldet = createNew(
				messages.SslfTableView_Schutzraumueberwachung_nicht_freigemeldet);
	}
}
