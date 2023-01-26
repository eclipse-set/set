/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskt;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Sskt
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

		// Allow merging cells
		Bezeichnung.setMergeCommonValues(true);
		Grundsatzangaben_Art.setMergeCommonValues(true);
		Bedien_Standort.setMergeCommonValues(true);
		Unterbringung_Art.setMergeCommonValues(true);
		Ort.setMergeCommonValues(true);
		Strecke.setMergeCommonValues(true);
		km.setMergeCommonValues(true);
		Regionalbereich.setMergeCommonValues(true);
		IPv4_Blau.setMergeCommonValues(true);
		IPv6_Blau.setMergeCommonValues(true);
		IPv4_Grau.setMergeCommonValues(true);
		IPv6_Grau.setMergeCommonValues(true);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder rootBuilder = builder
				.createRootColumn(messages.Sskt_Heading);
		addGrundsatzangaben(rootBuilder);
		addIPAdressangaben(rootBuilder);
		rootBuilder.add(basis_bemerkung).width(3.32f);
		return rootBuilder.getGroupRoot();
	}

	private void addGrundsatzangaben(final GroupBuilder rootBuilder) {
		final GroupBuilder grundsatzangaben = rootBuilder
				.addGroup(messages.Sskt_Grundsatzangaben);
		grundsatzangaben.add(Bezeichnung).width(2.37f);
		grundsatzangaben.add(Grundsatzangaben_Art).width(1.93f);
		grundsatzangaben.add(Bedien_Standort).width(1.86f);
		final GroupBuilder unterbringung = grundsatzangaben
				.addGroup(messages.Sskt_Unterbringung);
		unterbringung.add(Unterbringung_Art).width(1.88f);
		unterbringung.add(Ort).width(2.92f);
		unterbringung.add(Strecke).width(1.08f);
		unterbringung.add(km).width(1.12f);
	}

	private void addIPAdressangaben(final GroupBuilder rootBuilder) {
		final GroupBuilder ipAdressangaben = rootBuilder
				.addGroup(messages.Sskt_IP_Adressangaben);
		ipAdressangaben.add(Regionalbereich).width(1.19f);
		final GroupBuilder blau = ipAdressangaben
				.addGroup(messages.Sskt_Adressblock_Blau);
		blau.add(IPv4_Blau).width(2.5f);
		blau.add(IPv6_Blau).width(2.98f);
		final GroupBuilder grau = ipAdressangaben
				.addGroup(messages.Sskt_Adressblock_Grau);
		grau.add(IPv4_Grau).width(2.5f);
		grau.add(IPv6_Grau).width(2.98f);
		final GroupBuilder teilsystem = ipAdressangaben
				.addGroup(messages.Sskt_Teilsystem);
		teilsystem.add(Teilsystem_Art).width(1.93f);
		teilsystem.add(TS_Blau).width(2.98f);
		teilsystem.add(TS_Grau).width(2.98f);
	}

}
