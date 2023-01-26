/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssla;

import org.eclipse.set.feature.table.pt1.AbstractTableColumns;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Ssla-<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SslaColumns extends AbstractTableColumns {

	/**
	 * Ssla.Grundsatzangaben.Art
	 */
	public ColumnDescriptor art;
	/**
	 * Ssla.Grundsatzangaben.Durchrutschweg_Ziel
	 */
	public final ColumnDescriptor durchrutschweg_ziel;
	/**
	 * Ssla.Grundsatzangaben.Fahrweg.Start
	 */
	public final ColumnDescriptor start;
	/**
	 * Ssla.Unterwegssignal
	 */
	public final ColumnDescriptor unterwegssignal;
	/**
	 * Ssla.Grundsatzangaben.Fahrweg.Ziel
	 */
	public final ColumnDescriptor ziel;

	/**
	 * creates the columns
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public SslaColumns(final Messages messages) {
		super(messages);
		start = createNew(
				messages.SslaTableView_Grundsatzangaben_Fahrweg_Start);
		ziel = createNew(messages.SslaTableView_Grundsatzangaben_Fahrweg_Ziel);
		durchrutschweg_ziel = createNew(
				messages.SslaTableView_Grundsatzangaben_Durchrutschweg_Ziel);
		art = createNew(messages.SslaTableView_Grundsatzangaben_Art);
		unterwegssignal = createNew(messages.SslaTableView_Unterwegssignal);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder
				.createRootColumn(messages.SslaTableView_Heading);
		final GroupBuilder fundamental = root
				.addGroup(messages.SslaTableView_Grundsatzangaben_Fahrweg);

		fundamental.add(basis_bezeichnung).width(2.06f);

		final GroupBuilder fahrweg = fundamental
				.addGroup(messages.SslaTableView_Grundsatzangaben_Fahrweg);
		fahrweg.add(start).width(1.14f);
		fahrweg.add(ziel).width(1.24f);

		fundamental.add(durchrutschweg_ziel).width(1.01f);
		fundamental.add(art).width(0.98f);

		root.add(unterwegssignal).width(9.26f);
		root.add(basis_bemerkung).width(5.05f);

		return root.getGroupRoot();
	}

}