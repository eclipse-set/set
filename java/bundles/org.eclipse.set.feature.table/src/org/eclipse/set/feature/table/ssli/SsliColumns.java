/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssli;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Ssli-Columns.
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
}
