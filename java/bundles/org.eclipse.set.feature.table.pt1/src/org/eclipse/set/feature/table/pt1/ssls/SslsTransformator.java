/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.ssls;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.table.RowFactory;
import org.eclipse.set.utils.table.TMFactory;
import org.osgi.service.event.EventAdmin;

import com.google.common.collect.Streams;

/**
 * 
 */
public class SslsTransformator extends AbstractPlanPro2TableModelTransformator {
	/**
	 * @param cols
	 *            the table columns descriptor
	 * @param enumTranslationService
	 *            the {@link EnumTranslationService}
	 * @param eventAdmin
	 *            the {@link EventAdmin}
	 */
	public SslsTransformator(final Set<ColumnDescriptor> cols,
			final EnumTranslationService enumTranslationService,
			final EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public Table transformTableContent(
			final MultiContainer_AttributeGroup container,
			final TMFactory factory, final Stell_Bereich controlArea) {
		List.of("60A", "60AA", "60B") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				.forEach(signal -> transform(factory, container, signal));
		return factory.getTable();
	}

	private void transform(final TMFactory factory,
			final MultiContainer_AttributeGroup container,
			final String startSignalBezeichung) {
		final Signal startSignal = Streams.stream(container.getSignal())
				.filter(signal -> EObjectExtensions
						.getNullableObject(signal,
								s -> s.getBezeichnung()
										.getBezeichnungTabelle()
										.getWert())
						.orElse("") //$NON-NLS-1$
						.equals(startSignalBezeichung))
				.findFirst()
				.orElse(null);
		if (startSignal == null) {
			return;
		}

		final RowFactory rg = factory.newRowGroup(startSignal);

		fill(rg.newTableRow(), getColumn(cols, SslsColumns.Signal_Abschnitt),
				startSignal, signal -> String.format("(%s)", //$NON-NLS-1$
						signal.getBezeichnung()
								.getBezeichnungTabelle()
								.getWert()));
		final SignalingSection signalisierungsabschnitte = new SignalingSection(
				startSignal);
		final List<SignalingRouteSection> abschnitte = new ArrayList<>(
				signalisierungsabschnitte.getSignalingRouteSections());
		abschnitte.sort(SignalingRouteSection.routeSectionComparator());
		abschnitte.forEach(abschintt -> {
			final TableRow row = rg.newTableRow();
			fill(row, getColumn(cols, SslsColumns.Signal_Abschnitt), abschintt,
					SignalingRouteSection::toString);
		});
	}
}
