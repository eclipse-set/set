/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.sskz;

import static org.eclipse.set.feature.table.pt1.sskz.SskzColumns.*;
import static org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.getContainer;
import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ENUMAussenelementansteuerungArt;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ENUMTueranschlag;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ENUMUnterbringungBefestigung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.ppmodel.extensions.UrObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.table.TMFactory;

import com.google.common.collect.Streams;

/**
 * 
 */
public class SskzTransformator extends AbstractPlanPro2TableModelTransformator {

	/**
	 * Constructor
	 * 
	 * @param cols
	 *            the table columns
	 * @param enumTranslationService
	 *            {@link EnumTranslationService}
	 */
	public SskzTransformator(final Set<ColumnDescriptor> cols,
			final EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService);
	}

	@Override
	public Table transformTableContent(
			final MultiContainer_AttributeGroup container,
			final TMFactory factory, final Stell_Bereich controlArea) {
		final List<Aussenelementansteuerung> outsideControl = Streams
				.stream(container.getAussenelementansteuerung())
				.filter(UrObjectExtensions::isPlanningObject).toList();
		final Iterable<Aussenelementansteuerung> relevantControlsInArea = UrObjectExtensions
				.filterObjectsInControlArea(outsideControl, controlArea);
		return transform(relevantControlsInArea, factory);
	}

	private Table transform(final Iterable<Aussenelementansteuerung> controls,
			final TMFactory factory) {
		for (final Aussenelementansteuerung control : controls) {
			Thread.currentThread();
			if (Thread.interrupted()) {
				return null;
			}

			final ENUMAussenelementansteuerungArt controlType = getNullableObject(
					control, ele -> ele.getAEAAllg()
							.getAussenelementansteuerungArt().getWert())
									.orElse(null);
			if (controlType != ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_FE_AK
					&& controlType != ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_FE_AS) {
				continue;
			}

			final TableRow row = factory.newTableRow(control);

			// A: Sskz.Betriebl_Bez_FEAx
			fill(row, getColumn(cols, Betribl_Bez_FEAx), control,
					(final Aussenelementansteuerung ele) -> ele.getBezeichnung()
							.getBezeichnungAEA().getWert());

			// B: Sskz.Betriebl_Bez_Feldelem
			fillIterable(row, getColumn(cols, Betriebl_Bez_Feldelem), control,
					SskzTransformator::getBezFeldelem, null);

			// C: Sskz.Techn_Bez_OC
			fill(row, getColumn(cols, Techn_Bez_OC), control, e -> ""); //$NON-NLS-1$

			// D: Sskz.Tueranschlag
			fill(row, getColumn(cols, Tueranschlag), control,
					(final Aussenelementansteuerung element) -> {
						final ENUMTueranschlag enumTueranschlag = getNullableObject(
								element,
								e -> e.getIDUnterbringung().getValue()
										.getUnterbringungAllg()
										.getTueranschlag().getWert())
												.orElse(null);
						return translate(enumTueranschlag);
					});

			// E: Sskz.Montage
			fill(row, getColumn(cols, Montage), control,
					(final Aussenelementansteuerung element) -> {
						final ENUMUnterbringungBefestigung befestigung = getNullableObject(
								element,
								e -> e.getIDUnterbringung().getValue()
										.getUnterbringungAllg()
										.getUnterbringungBefestigung()
										.getWert()).orElse(null);
						return translate(befestigung);
					});

			// F: Sskz.Blattnummer
			fill(row, getColumn(cols, Blattnummer), control, element -> ""); //$NON-NLS-1$

		}
		return factory.getTable();

	}

	private static List<String> getBezFeldelem(
			final Aussenelementansteuerung control) {
		final List<W_Kr_Gsp_Element> gspElements = Streams
				.stream(getContainer(control).getWKrGspElement())
				.filter(gspElement -> control.equals(getStellementInformation(
						gspElement,
						element -> element.getIDStellelement().getValue())))
				.filter(Objects::nonNull).toList();
		if (!gspElements.isEmpty()) {
			return gspElements.stream()
					.map(SskzTransformator::getSignalWGspElementLabel)
					.filter(Optional::isPresent).map(Optional::get).toList();
		}

		final List<Signal> signals = Streams
				.stream(getContainer(control).getSignal())
				.filter(signal -> control.equals(getStellementInformation(
						signal,
						element -> element.getSignalReal().getSignalRealAktiv()
								.getIDStellelement().getValue())))
				.filter(Objects::nonNull).toList();
		if (!signals.isEmpty()) {
			return signals.stream()
					.map(SskzTransformator::getSignalWGspElementLabel)
					.filter(Optional::isPresent).map(Optional::get).toList();
		}
		return Collections.emptyList();
	}

	private static <T> Aussenelementansteuerung getStellementInformation(
			final T object, final Function<T, Stellelement> getStellementFunc) {
		final Stellelement stellement = getNullableObject(object,
				e -> getStellementFunc.apply(object)).orElse(null);
		return getNullableObject(stellement,
				e -> e.getIDInformation().getValue()).orElse(null);
	}

	private static Optional<String> getSignalWGspElementLabel(
			final Ur_Objekt object) {
		return switch (object) {
		case final Signal signal -> getNullableObject(signal,
				e -> e.getBezeichnung().getBezeichnungTabelle().getWert());
		case final W_Kr_Gsp_Element gspElement -> getNullableObject(gspElement,
				e -> e.getBezeichnung().getBezeichnungTabelle().getWert());
		default -> Optional.empty();
		};
	}
}
