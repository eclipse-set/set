/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssit;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Symbolic addressing for Ssit columns.
 * 
 * @author Schaefer
 */
public class SsitColumns extends AbstractTableColumns {

	/**
	 * Q: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.An_Zeit_Hupe
	 */
	public final ColumnDescriptor An_Zeit_Hupe = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_An_Zeit_Hupe);

	/**
	 * J: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Anf_NB
	 */
	public final ColumnDescriptor Anf_NB = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Anf_NB);

	/**
	 * D: Ssit.Grundsatzangaben.Befestigung.Art
	 */
	public final ColumnDescriptor Art = createNew(
			messages.SsitTableView_Grundsatzangaben_Befestigung_Art);

	/**
	 * C: Ssit.Grundsatzangaben.Bauart
	 */
	public final ColumnDescriptor Bauart = createNew(
			messages.SsitTableView_Grundsatzangaben_Bauart);

	/**
	 * A: Ssit.Grundsatzangaben.Bezeichnung
	 */
	public final ColumnDescriptor Bezeichnung = createNew(
			messages.SsitTableView_Grundsatzangaben_Bezeichnung);

	/**
	 * K: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Fertigmeldung
	 */
	public final ColumnDescriptor Fertigmeldung = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Fertigmeldung);

	/**
	 * P: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Freigabe_Ssp
	 */
	public final ColumnDescriptor Freigabe_Ssp = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Freigabe_Ssp);

	/**
	 * F: Ssit.Grundsatzangaben.Befestigung.km
	 */
	public final ColumnDescriptor km = createNew(
			messages.SsitTableView_Grundsatzangaben_Befestigung_km);

	/**
	 * G: Ssit.Bedien_Anz_Elemente.Melder
	 */
	public final ColumnDescriptor Melder = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Melder);

	/**
	 * H: Ssit.Bedien_Anz_Elemente.Schalter
	 */
	public final ColumnDescriptor Schalter = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Schalter);

	/**
	 * E: Ssit.Grundsatzangaben.Befestigung.Strecke
	 */
	public final ColumnDescriptor Strecke = createNew(
			messages.SsitTableView_Grundsatzangaben_Befestigung_Strecke);

	/**
	 * I: Ssit.Bedien_Anz_Elemente.Taste
	 */
	public final ColumnDescriptor Taste = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Taste);

	/**
	 * N: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Gs
	 */
	public final ColumnDescriptor Umst_Gs = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Umst_Gs);

	/**
	 * O: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Sig
	 */
	public final ColumnDescriptor Umst_Sig = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Umst_Sig);

	/**
	 * M: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Weiche
	 */
	public final ColumnDescriptor Umst_Weiche = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Umst_Weiche);

	/**
	 * L: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Weichengruppe
	 */
	public final ColumnDescriptor Weichengruppe = createNew(
			messages.SsitTableView_Bedien_Anz_Elemente_Nahbedienbereich_Weichengruppe);

	/**
	 * B: Ssit.Grundsatzangaben.Zug_AEA
	 */
	public final ColumnDescriptor Zug_AEA = createNew(
			messages.SsitTableView_Grundsatzangaben_Zug_AEA);

	/**
	 * @param messages
	 *            the messages
	 */
	public SsitColumns(final Messages messages) {
		super(messages);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SsitTableView_Heading);

		final GroupBuilder Grundsatzangaben = root
				.addGroup(messages.SsitTableView_Grundsatzangaben);
		Grundsatzangaben.add(Bezeichnung).width(2.38f);
		Grundsatzangaben.add(Zug_AEA).width(2.22f);
		Grundsatzangaben.add(Bauart).width(2.33f);

		final GroupBuilder Befestigung = Grundsatzangaben
				.addGroup(messages.SsitTableView_Befestigung);
		Befestigung.add(Art).width(1.9f);
		Befestigung.add(Strecke).width(1.31f);
		Befestigung.add(km).width(1.24f);

		final GroupBuilder Bedien_Anz_Elemente = root
				.addGroup(messages.SsitTableView_Bedien_Anz_Elemente);
		Bedien_Anz_Elemente.add(Melder).width(2.70f);
		Bedien_Anz_Elemente.add(Schalter).width(2.70f);
		Bedien_Anz_Elemente.add(Taste).width(2.70f);

		final GroupBuilder Nahbedienbereich = Bedien_Anz_Elemente
				.addGroup(messages.SsitTableView_Nahstellbereich);
		Nahbedienbereich.add(Anf_NB).width(1.86f).height(LINE_HEIGHT * 1.5f);
		Nahbedienbereich.add(Fertigmeldung).width(1.52f);
		Nahbedienbereich.add(Weichengruppe).width(1.42f);
		Nahbedienbereich.add(Umst_Weiche).width(1.69f);
		Nahbedienbereich.add(Umst_Gs).width(1.71f);
		Nahbedienbereich.add(Umst_Sig).width(1.71f);
		Nahbedienbereich.add(Freigabe_Ssp).width(2.29f);
		Nahbedienbereich.add(An_Zeit_Hupe, messages.Common_UnitSeconds)
				.width(1.86f);

		root.add(basis_bemerkung).width(3.81f);

		return root.getGroupRoot();
	}

}
