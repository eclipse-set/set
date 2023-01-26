/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssli;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Ssli-
 * 
 * @author Schaefer
 * 
 * @see ColumnDescriptor
 */
public class SsliColumns extends AbstractTableColumns {

	/**
	 * G: Ssli.Ausschluss_Fahrten.Rangierfahrt.Ausfahrt
	 */
	public final ColumnDescriptor Ausfahrt;

	/**
	 * A: Ssli.Grundsatzangaben.Bezeichnung_Inselgleis
	 */
	public final ColumnDescriptor Bezeichnung_Inselgleis;

	/**
	 * F: Ssli.Ausschluss_Fahrten.Rangierfahrt.Einfahrt
	 */
	public final ColumnDescriptor Einfahrt;

	/**
	 * B: Ssli.Grundsatzangaben.Laenge
	 */
	public final ColumnDescriptor Laenge;

	/**
	 * D: Ssli.Grundsatzangaben.Begrenzende_Signale.NX_Richtung
	 */
	public final ColumnDescriptor NX_Richtung;

	/**
	 * C: Ssli.Grundsatzangaben.Begrenzende_Signale.PY_Richtung
	 */
	public final ColumnDescriptor PY_Richtung;

	/**
	 * E: Ssli.Ausschluss_Fahrten.Zugausfahrt
	 */
	public final ColumnDescriptor Zugausfahrt;

	/**
	 * @param messages
	 *            the messages
	 */
	public SsliColumns(final Messages messages) {
		super(messages);
		Bezeichnung_Inselgleis = createNew(
				messages.Ssli_Grundsatzangaben_Bezeichnung_Inselgleis);
		Laenge = createNew(messages.Ssli_Grundsatzangaben_Laenge);
		PY_Richtung = createNew(
				messages.Ssli_Grundsatzangaben_Begrenzende_Signale_PY_Richtung);
		NX_Richtung = createNew(
				messages.Ssli_Grundsatzangaben_Begrenzende_Signale_NX_Richtung);
		Zugausfahrt = createNew(messages.Ssli_Ausschluss_Fahrten_Zugausfahrt);
		Einfahrt = createNew(
				messages.Ssli_Ausschluss_Fahrten_Rangierfahrt_Einfahrt);
		Ausfahrt = createNew(
				messages.Ssli_Ausschluss_Fahrten_Rangierfahrt_Ausfahrt);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder rootBuilder = builder
				.createRootColumn(messages.Ssli_Heading);
		final GroupBuilder grundsatzangaben = rootBuilder
				.addGroup(messages.Ssli_Grundsatzangaben);
		grundsatzangaben.add(Bezeichnung_Inselgleis).width(1.99f);
		grundsatzangaben.add(Laenge, messages.Common_UnitMeter).width(1.71f);
		final GroupBuilder begrenzendeSignale = grundsatzangaben
				.addGroup(messages.Ssli_Grundsatzangaben_Begrenzende_Signale);
		begrenzendeSignale.add(PY_Richtung).width(1.86f);
		begrenzendeSignale.add(NX_Richtung).width(1.86f);
		final GroupBuilder ausschlussFahrten = rootBuilder
				.addGroup(messages.Ssli_Ausschluss_Fahrten);
		ausschlussFahrten.add(Zugausfahrt).width(2.12f);
		final GroupBuilder rangierfahrt = ausschlussFahrten
				.addGroup(messages.Ssli_Ausschluss_Fahrten_Rangierfahrt);
		rangierfahrt.add(Einfahrt).width(1.93f);
		rangierfahrt.add(Ausfahrt).width(1.93f);
		rootBuilder.add(basis_bemerkung).width(23.79f);
		return rootBuilder.getGroupRoot();
	}

}
