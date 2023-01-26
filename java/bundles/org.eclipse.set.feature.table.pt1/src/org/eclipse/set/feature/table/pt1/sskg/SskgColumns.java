/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskg;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Sskg-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SskgColumns extends AbstractTableColumns {

	/**
	 * Standortmerkmale.Bezugspunkt.Abstand
	 */
	public final ColumnDescriptor abstand;
	/**
	 * Achszaehlpunkte.Anschluss_Energie.AEA_E
	 */
	public final ColumnDescriptor aea_e;
	/**
	 * Achszaehlpunkte.Anschluss_Energie.AEA_E_separat
	 */
	public final ColumnDescriptor aea_e_separat;
	/**
	 * Achszaehlpunkte.Anschluss_Info.AEA_I
	 */
	public final ColumnDescriptor aea_i;
	/**
	 * Achszaehlpunkte.Schienenprofil
	 */
	public final ColumnDescriptor schienenprofil;
	/**
	 * Grundsatzangaben.Art
	 */
	public final ColumnDescriptor art;
	/**
	 * Grundsatzangaben.Bezeichnung
	 */
	public final ColumnDescriptor bezeichnung_gleisschaltmittel;
	/**
	 * Standortmerkmale.Bezugspunkt.Bezeichnung
	 */
	public final ColumnDescriptor bezugspunkt_bezeichnung;

	/**
	 * Funktion
	 */
	public final ColumnDescriptor funktion;
	/**
	 * Standortmerkmale.Standort.km
	 */
	public final ColumnDescriptor km;
	/**
	 * Standortmerkmale.Standort.Strecke
	 */
	public final ColumnDescriptor strecke;
	/**
	 * Grundsatzangaben.Typ
	 */
	public final ColumnDescriptor typ;

	/**
	 * creates the columns
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SskgColumns(final Messages messages) {
		super(messages);
		bezeichnung_gleisschaltmittel = createNew(
				messages.SskgTableView_Grundsatzangaben_Bezeichnung);
		art = createNew(messages.SskgTableView_Grundsatzangaben_Art);
		typ = createNew(messages.SskgTableView_Grundsatzangaben_Typ);
		aea_i = createNew(messages.SskgTableView_Achszaehlpunkte_Anschluss_AEA);
		aea_e = createNew(
				messages.SskgTableView_Achszaehlpunkte_Anschluss_Energieversorgung_AEA);
		aea_e_separat = createNew(
				messages.SskgTableView_Achszaehlpunkte_Anschluss_separateAdern);
		schienenprofil = createNew(
				messages.SskgTableView_Achszaehlpunkte_Schienenprofil);
		bezugspunkt_bezeichnung = createNew(
				messages.SskgTableView_Standortmerkmale_Bezugspunkt_Bezeichnung);
		abstand = createNew(
				messages.SskgTableView_Standortmerkmale_Bezugspunkt_Abstand);
		strecke = createNew(
				messages.SskgTableView_Standortmerkmale_Standort_Strecke);
		km = createNew(messages.SskgTableView_Standortmerkmale_Standort_km);
		funktion = createNew(messages.SskgTableView_Funktion);

	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SskgTableView_Heading);
		final GroupBuilder fundamental = root
				.addGroup(messages.SskgTableView_Grundsatzangaben);
		fundamental.add(bezeichnung_gleisschaltmittel).width(4.28f);
		fundamental.add(art).width(2.86f);
		fundamental.add(typ).width(2.86f);

		final GroupBuilder achszaehlpunkte = root
				.addGroup(messages.SskgTableView_Achszaehlpunkte);
		final GroupBuilder anschlussAchszaehlRechner = achszaehlpunkte.addGroup(
				messages.SskgTableView_Achszaehlpunkte_Anschluss_Rechner);
		anschlussAchszaehlRechner.add(aea_i).width(1.42f);

		final GroupBuilder anschlussEnergieVersorgung = achszaehlpunkte
				.addGroup(
						messages.SskgTableView_Achszaehlpunkte_Anschluss_Energieversorgung)
				.height(LINE_HEIGHT * 1.5f);
		anschlussEnergieVersorgung.add(aea_e).width(1.42f);
		anschlussEnergieVersorgung.add(aea_e_separat).width(1.12f)
				.height(LINE_HEIGHT * 2);

		achszaehlpunkte.add(schienenprofil).width(1.80f);

		final GroupBuilder standortmerkmale = root
				.addGroup(messages.SskgTableView_Standortmerkmale);
		final GroupBuilder bezugspunkt = standortmerkmale
				.addGroup(messages.SskgTableView_Standortmerkmale_Bezugspunkt);
		bezugspunkt.add(bezugspunkt_bezeichnung).width(2.86f);
		bezugspunkt.add(abstand,
				messages.SskgTableView_Standortmerkmale_Bezugspunkt_Abstand_m)
				.width(1.42f);

		final GroupBuilder standort = standortmerkmale
				.addGroup(messages.SskgTableView_Standortmerkmale_Standort);
		standort.add(strecke).width(1.42f);
		standort.add(km).width(1.42f);

		root.add(funktion).width(3.56f);
		root.add(basis_bemerkung).width(14.27f);
		return root.getGroupRoot();
	}
}