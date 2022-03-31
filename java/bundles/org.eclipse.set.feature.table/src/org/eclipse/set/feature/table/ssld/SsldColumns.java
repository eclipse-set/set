/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssld;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Ssld-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SsldColumns extends AbstractTableColumns {

	/**
	 * Ssld.Aufloesung.Zielgleisabschnitt.Bezeichnung
	 */
	public final ColumnDescriptor bezeichnung;

	/**
	 * Ssld.Grundsatzangaben.bis
	 */
	public final ColumnDescriptor bis;

	/**
	 * Ssld.Abhaengigkeiten.Erlaubnisabhaengig
	 */
	public final ColumnDescriptor erlaubnisabhaengig;

	/**
	 * I: Ssld.Eigenschaften.Laenge.Freigemeldet
	 */
	public final ColumnDescriptor freigemeldet;

	/**
	 * Ssld.Grundsatzangaben.Gefahrpunkt
	 */
	public final ColumnDescriptor gefahrpunkt;

	/**
	 * Ssld.Eigenschaften.Laenge.Ist
	 */
	public final ColumnDescriptor ist;

	/**
	 * Ssld.Aufloesung.Zielgleisabschnitt.Laenge
	 */
	public final ColumnDescriptor laenge;

	/**
	 * Ssld.Aufloesung.Manuell
	 */
	public final ColumnDescriptor manuell;

	/**
	 * Ssld.Eigenschaften.massgebende_Neigung
	 */
	public final ColumnDescriptor massgebende_neigung;

	/**
	 * Ssld.Abhaengigkeiten.Weichen_Kreuzungen.mit_Verschluss
	 */
	public final ColumnDescriptor mit_verschluss;

	/**
	 * Ssld.Abhaengigkeiten.Weichen_Kreuzungen.ohne_Verschluss
	 */
	public final ColumnDescriptor ohne_verschluss;

	/**
	 * Ssld.Grundsatzangaben.PZB_Gefahrpunkt
	 */
	public final ColumnDescriptor pzb_gefahrpunkt;

	/**
	 * Ssld.Abhaengigkeiten.relevante_FmA
	 */
	public final ColumnDescriptor relevante_fma;

	/**
	 * Ssld.Eigenschaften.Laenge.Soll
	 */
	public final ColumnDescriptor soll;

	/**
	 * Ssld.Abhaengigkeiten.v_Aufwertung_Verzicht
	 */
	public final ColumnDescriptor v_aufwertung_verzicht;

	/**
	 * Ssld.Aufloesung.Verzoegerung
	 */
	public final ColumnDescriptor verzoegerung;

	/**
	 * Ssld.Grundsatzangaben.von
	 */
	public final ColumnDescriptor von;

	/**
	 * Ssld.Eigenschaften.Zielgeschwindigkeit_moeglich
	 */
	public final ColumnDescriptor zielgeschwindigkeit_moeglich;

	/**
	 * creates the columns.
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SsldColumns(final Messages messages) {
		super(messages);
		von = createNew(messages.SsldTableView_HeadingFundamentalFrom);

		bis = createNew(messages.SsldTableView_HeadingFundamentalTo);

		gefahrpunkt = createNew(
				messages.SsldTableView_HeadingFundamentalDangerousPoint);

		pzb_gefahrpunkt = createNew(
				messages.SsldTableView_HeadingFundamentalProtectionPointPzb);

		zielgeschwindigkeit_moeglich = createNew(
				messages.SsldTableView_HeadingPropertiesTargetSpeedPossible);

		soll = createNew(messages.SsldTableView_HeadingPropertiesLengthNominal);

		ist = createNew(messages.SsldTableView_HeadingPropertiesLengthActual);

		freigemeldet = createNew(messages.SsldTableView_HeadingPropertiesFree);

		massgebende_neigung = createNew(
				messages.SsldTableView_HeadingPropertiesDecisiveTilt);

		mit_verschluss = createNew(
				messages.SsldTableView_HeadingDependenciesPointsCrossingsLock);

		ohne_verschluss = createNew(
				messages.SsldTableView_HeadingDependenciesPointsCrossingsNoLock);

		relevante_fma = createNew(
				messages.SsldTableView_HeadingDependenciesFma);

		v_aufwertung_verzicht = createNew(
				messages.SsldTableView_HeadingDependenciesVWaiver);

		erlaubnisabhaengig = createNew(
				messages.SsldTableView_HeadingDependenciesPermissionDependent);

		manuell = createNew(messages.SsldTableView_HeadingUnblockingManual);

		bezeichnung = createNew(
				messages.SsldTableView_HeadingUnblockingDestinationTrackIndication);

		laenge = createNew(
				messages.SsldTableView_HeadingUnblockingDestinationTrackLength);

		verzoegerung = createNew(messages.SsldTableView_HeadingUnblockingDelay);
	}
}