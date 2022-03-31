/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskt;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sskt Columns.
 * 
 * @author Schaefer
 * 
 * @see ColumnDescriptor
 */
public class SsktColumns extends AbstractTableColumns {

	/**
	 * C: Sskt.Grundsatzangaben.Bedien_Standort
	 */
	public ColumnDescriptor Bedien_Standort;

	/**
	 * A: Sskt.Grundsatzangaben.Bezeichnung
	 */
	public ColumnDescriptor Bezeichnung;

	/**
	 * B: Sskt.Grundsatzangaben.Art
	 */
	public ColumnDescriptor Grundsatzangaben_Art;

	/**
	 * I: Sskt.IP_Adressangaben.Adressblock_Blau.IPv4_Blau
	 */
	public ColumnDescriptor IPv4_Blau;

	/**
	 * K: Sskt.IP_Adressangaben.Adressblock_Grau.IPv4_Grau
	 */
	public ColumnDescriptor IPv4_Grau;

	/**
	 * J: Sskt.IP_Adressangaben.Adressblock_Blau.IPv6_Blau
	 */
	public ColumnDescriptor IPv6_Blau;

	/**
	 * L: Sskt.IP_Adressangaben.Adressblock_Grau.IPv6_Grau
	 */
	public ColumnDescriptor IPv6_Grau;

	/**
	 * G: Sskt.Grundsatzangaben.Unterbringung.km
	 */
	public ColumnDescriptor km;

	/**
	 * E: Sskt.Grundsatzangaben.Unterbringung.Ort
	 */
	public ColumnDescriptor Ort;

	/**
	 * H: Sskt.IP_Adressangaben.Regionalbereich
	 */
	public ColumnDescriptor Regionalbereich;

	/**
	 * F: Sskt.Grundsatzangaben.Unterbringung.Strecke
	 */
	public ColumnDescriptor Strecke;

	/**
	 * M: Sskt.IP_Adressangaben.Teilsystem.Art
	 */
	public ColumnDescriptor Teilsystem_Art;

	/**
	 * N: Sskt.IP_Adressangaben.Teilsystem.TS_Blau
	 */
	public ColumnDescriptor TS_Blau;

	/**
	 * O: Sskt.IP_Adressangaben.Teilsystem.TS_Grau
	 */
	public ColumnDescriptor TS_Grau;

	/**
	 * D: Sskt.Grundsatzangaben.Unterbringung.Art
	 */
	public ColumnDescriptor Unterbringung_Art;

	/**
	 * @param messages
	 *            the messages
	 */
	public SsktColumns(final Messages messages) {
		super(messages);
		Bezeichnung = createNew(messages.Sskt_Grundsatzangaben_Bezeichnung);
		Grundsatzangaben_Art = createNew(messages.Sskt_Grundsatzangaben_Art);
		Bedien_Standort = createNew(
				messages.Sskt_Grundsatzangaben_Bedien_Standort);
		Unterbringung_Art = createNew(
				messages.Sskt_Grundsatzangaben_Unterbringung_Art);
		Ort = createNew(messages.Sskt_Grundsatzangaben_Unterbringung_Ort);
		Strecke = createNew(
				messages.Sskt_Grundsatzangaben_Unterbringung_Strecke);
		km = createNew(messages.Sskt_Grundsatzangaben_Unterbringung_km);
		Regionalbereich = createNew(
				messages.Sskt_IP_Adressangaben_Regionalbereich);
		IPv4_Blau = createNew(
				messages.Sskt_IP_Adressangaben_Adressblock_Blau_IPv4_Blau);
		IPv6_Blau = createNew(
				messages.Sskt_IP_Adressangaben_Adressblock_Blau_IPv6_Blau);
		IPv4_Grau = createNew(
				messages.Sskt_IP_Adressangaben_Adressblock_Grau_IPv4_Grau);
		IPv6_Grau = createNew(
				messages.Sskt_IP_Adressangaben_Adressblock_Grau_IPv6_Grau);
		Teilsystem_Art = createNew(
				messages.Sskt_IP_Adressangaben_Teilsystem_Art);
		TS_Blau = createNew(messages.Sskt_IP_Adressangaben_Teilsystem_TS_Blau);
		TS_Grau = createNew(messages.Sskt_IP_Adressangaben_Teilsystem_TS_Grau);
	}
}
