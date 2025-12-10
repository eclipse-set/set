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
import java.util.stream.Collectors;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.table.RowFactory;
import org.eclipse.set.utils.table.TMFactory;
import org.osgi.service.event.EventAdmin;

import com.google.common.collect.Streams;

/**
 * 
 */
public class SslsTransformator extends AbstractPlanPro2TableModelTransformator {

	private List<SignalingSection> signalingSections;

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
		final Set<Signal> startSignals = getFstrZugStartSignal(container);
		signalingSections = new ArrayList<>();
		startSignals.forEach(this::determineSignalingSections);
		signalingSections.forEach(section -> transform(factory, section));
		return factory.getTable();
	}

	private static Set<Signal> getFstrZugStartSignal(
			final MultiContainer_AttributeGroup container) {
		return Streams.stream(container.getFstrZugRangier())
				.filter(FstrZugRangierExtensions::isZ)
				.map(FstrZugRangierExtensions::getStartSignal)
				.collect(Collectors.toSet());
	}

	private void determineSignalingSections(final Signal startSignal) {
		// When the signal is already in another signaling section, then break
		final boolean isSignalBetween = signalingSections.stream()
				.anyMatch(section -> section.getSignalBetween()
						.contains(startSignal));
		if (isSignalBetween) {
			return;
		}

		final SignalingSection newSection = new SignalingSection(startSignal);
		// Remove the already determined sections that have an overlap with the
		// new section
		final List<SignalingSection> overlappingSections = signalingSections
				.stream()
				.filter(section -> newSection.getSignalBetween()
						.contains(section.getStartSignal()))
				.toList();
		signalingSections.removeAll(overlappingSections);
		signalingSections.add(newSection);

	}

	private void transform(final TMFactory factory,
			final SignalingSection signalingSection) {
		final Signal startSignal = signalingSection.getStartSignal();
		final RowFactory rg = factory.newRowGroup(startSignal);

		fill(rg.newTableRow(), getColumn(cols, SslsColumns.Signal_Abschnitt),
				startSignal, signal -> String.format("(%s)", //$NON-NLS-1$
						signal.getBezeichnung()
								.getBezeichnungTabelle()
								.getWert()));
		final List<SignalingRouteSection> abschnitte = new ArrayList<>(
				signalingSection.getSignalingRouteSections());

		abschnitte.sort(new SignalingRouteSectionComparator());
		abschnitte.forEach(abschintt -> {
			final TableRow row = rg.newTableRow();
			fill(row, getColumn(cols, SslsColumns.Signal_Abschnitt), abschintt,
					SignalingRouteSection::toString);
		});
	}
}
