/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssvu;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Ssvu-<br>
 * see {@link ColumnDescriptor}
 * 
 * @author Schneider
 *
 */
public final class SsvuColumns extends AbstractTableColumns {

	/**
	 * Ssvu.Grundsatzangaben.von
	 */
	public ColumnDescriptor von;
	/**
	 * Ssvu.Grundsatzangaben.nach
	 */
	public final ColumnDescriptor nach;
	/**
	 * Ssvu.Grundsatzangaben.Verwendung
	 */
	public final ColumnDescriptor verwendung;
	/**
	 * Ssvu.Technik.Netzart
	 */
	public final ColumnDescriptor netzart;
	/**
	 * Ssvu.Technik.Technikart
	 */
	public final ColumnDescriptor technikart;
	/**
	 * Ssvu.Technik.Technikart
	 */
	public final ColumnDescriptor schnittstelle;
	/**
	 * Ssvu.Technik.Technikart
	 */
	public final ColumnDescriptor bandbreite;
	/**
	 * Ssvu.Bemerkung
	 */
	public final ColumnDescriptor bemerkung;

	/**
	 * creates the columns
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SsvuColumns(final Messages messages) {
		super(messages);
		von = createNew(messages.SsvuTableView_Grundsatzangaben_von);
		nach = createNew(messages.SsvuTableView_Grundsatzangaben_nach);
		verwendung = createNew(
				messages.SsvuTableView_Grundsatzangaben_Verwendung);
		netzart = createNew(messages.SsvuTableView_Technik_Netzart);
		technikart = createNew(messages.SsvuTableView_Technik_Technikart);
		schnittstelle = createNew(messages.SsvuTableView_Technik_Schnittstelle);
		bandbreite = createNew(messages.SsvuTableView_Technik_Bandbreite);
		bemerkung = createNew(messages.SsvuTableView_Bemerkung);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SsvuTableView_Heading);
		final GroupBuilder grundsatzangaben = root
				.addGroup(messages.SsvuTableView_Grundsatzangaben);
		grundsatzangaben.add(von).width(1.69f);
		grundsatzangaben.add(nach).width(1.69f);
		grundsatzangaben.add(verwendung).width(1.85f);

		final GroupBuilder technik = root
				.addGroup(messages.SsvuTableView_Technik);
		technik.add(netzart).width(2.14f);
		technik.add(technikart).width(2.14f);
		technik.add(schnittstelle).width(2.14f);
		technik.add(bandbreite).width(2.14f);

		root.add(bemerkung).width(11.32f);

		return root.getGroupRoot();
	}
}