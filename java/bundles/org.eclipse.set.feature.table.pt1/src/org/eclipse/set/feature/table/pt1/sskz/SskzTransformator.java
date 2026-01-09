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
import static org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.isFindGeometryComplete;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.ssks.SignalSideDistance;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ENUMAussenelementansteuerungArt;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Tueranschlag_TypeClass;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung_Befestigung_TypeClass;
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.PZB.PZB_Art_TypeClass;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Verweise.ID_Aussenelementansteuerung_TypeClass;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.ppmodel.extensions.PZBElementExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.Case;
import org.eclipse.set.utils.math.BigDecimalExtensions;
import org.eclipse.set.utils.table.TMFactory;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.osgi.service.event.EventAdmin;

import com.google.common.collect.Streams;

/**
 * Table transformation forZuordnungstabelle FEAK/FEAS (Sskz).
 * 
 * @author Truong
 */
public class SskzTransformator extends AbstractPlanPro2TableModelTransformator {

	private final BankService bankService;
	private final String tableShortcut;

	private record OperationalIdentifierFieldElement(
			Class<? extends Ur_Objekt> clazz,
			List<String> elementIdentifierts) {

		public static int compare(final OperationalIdentifierFieldElement first,
				final OperationalIdentifierFieldElement second) {
			final ArrayList<Class<? extends Ur_Objekt>> clazzList = new ArrayList<>(
					fieldElementClasses.keySet());
			final int firstIndex = clazzList.indexOf(first.clazz());
			final int secondIndex = clazzList.indexOf(second.clazz());

			return Integer.compare(firstIndex, secondIndex);
		}

	}

	private static final LinkedHashMap<Class<? extends Ur_Objekt>, Function<MultiContainer_AttributeGroup, Iterable<? extends Ur_Objekt>>> fieldElementClasses = new LinkedHashMap<>();
	static {
		fieldElementClasses.put(W_Kr_Gsp_Element.class,
				MultiContainer_AttributeGroup::getWKrGspElement);
		fieldElementClasses.put(Signal.class,
				MultiContainer_AttributeGroup::getSignal);
		fieldElementClasses.put(FMA_Komponente.class,
				MultiContainer_AttributeGroup::getFMAKomponente);
		fieldElementClasses.put(PZB_Element.class,
				MultiContainer_AttributeGroup::getPZBElement);
		fieldElementClasses.put(Schluesselsperre.class,
				MultiContainer_AttributeGroup::getSchluesselsperre);
	}

	/**
	 * Constructor
	 * 
	 * @param cols
	 *            the table columns
	 * @param bankService
	 *            the {@link BankService}
	 * @param enumTranslationService
	 *            {@link EnumTranslationService}
	 * @param eventAdmin
	 *            the {@link EventAdmin}
	 * @param tableShortcut
	 *            the table shortcut
	 */
	public SskzTransformator(final Set<ColumnDescriptor> cols,
			final BankService bankService,
			final EnumTranslationService enumTranslationService,
			final EventAdmin eventAdmin, final String tableShortcut) {
		super(cols, enumTranslationService, eventAdmin);
		this.bankService = bankService;
		this.tableShortcut = tableShortcut;
	}

	@Override
	public Table transformTableContent(
			final MultiContainer_AttributeGroup container,
			final TMFactory factory) {
		final List<Aussenelementansteuerung> outsideControls = Streams
				.stream(container.getAussenelementansteuerung())
				.toList();

		return transform(outsideControls, factory);
	}

	private Table transform(final Iterable<Aussenelementansteuerung> controls,
			final TMFactory factory) {
		for (final Aussenelementansteuerung control : controls) {
			Thread.currentThread();
			if (Thread.interrupted()) {
				return null;
			}

			final ENUMAussenelementansteuerungArt controlType = getNullableObject(
					control,
					ele -> ele.getAEAAllg()
							.getAussenelementansteuerungArt()
							.getWert()).orElse(null);
			if (controlType != ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_FE_AK
					&& controlType != ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_FE_AS) {
				continue;
			}

			final TableRow row = factory.newTableRow(control);

			// A: Sskz.Betriebl_Bez_FEAx
			fill(row, getColumn(cols, Betribl_Bez_FEAx), control,
					(final Aussenelementansteuerung ele) -> ele.getBezeichnung()
							.getBezeichnungAEA()
							.getWert());

			// B: Sskz.Betriebl_Bez_Feldelem
			fillIterable(row, getColumn(cols, Betriebl_Bez_Feldelem), control,
					this::getFieldElementIdentifier,
					OperationalIdentifierFieldElement::compare,
					t -> String.join(ITERABLE_FILLING_SEPARATOR,
							t.elementIdentifierts()));

			// C: Sskz.Techn_Bez_OC
			fill(row, getColumn(cols, Techn_Bez_OC), control, e -> ""); //$NON-NLS-1$

			// D: Sskz.Tueranschlag
			fill(row, getColumn(cols, Tueranschlag), control,
					(final Aussenelementansteuerung element) -> {
						final Tueranschlag_TypeClass enumTueranschlag = getNullableObject(
								element,
								e -> e.getIDUnterbringung()
										.getValue()
										.getUnterbringungAllg()
										.getTueranschlag()).orElse(null);
						return translate(enumTueranschlag);
					});

			// E: Sskz.Montage
			fill(row, getColumn(cols, Montage), control,
					(final Aussenelementansteuerung element) -> {
						final Unterbringung_Befestigung_TypeClass befestigung = getNullableObject(
								element,
								e -> e.getIDUnterbringung()
										.getValue()
										.getUnterbringungAllg()
										.getUnterbringungBefestigung())
												.orElse(null);
						return translate(befestigung);
					});

			if (getNullableObject(control,
					e -> e.getIDUnterbringung()
							.getValue()
							.getPunktObjektTOPKante()).isPresent()) {
				final Punkt_Objekt_TOP_Kante_AttributeGroup potk = control
						.getIDUnterbringung()
						.getValue()
						.getPunktObjektTOPKante();

				// F: Sskz.Ueberhoehung
				fillIterableSingleCellWhenAllowed(row,
						getColumn(cols, Ueberhoehung), control,
						() -> Boolean
								.valueOf(bankService.isFindBankingComplete()),
						ele -> bankService.findBankValue(new TopPoint(potk))
								.stream()
								.filter(Objects::nonNull)
								.map(value -> value
										.multiply(BigDecimal.valueOf(1000)))
								.map(BigDecimalExtensions::toTableInteger)
								.toList(),
						MIXED_STRING_COMPARATOR, ITERABLE_FILLING_SEPARATOR,
						tableShortcut);

				// G: Sskz.Abstand_FEAx_Gleismitte
				if (getNullableObject(potk,
						p -> p.getSeitlicherAbstand().getWert()).isPresent()) {
					fillTrackMitteDistance(row, control, potk);
				}
			}

			// F: Sskz.Blattnummer
			fill(row, getColumn(cols, Blattnummer), control, element -> ""); //$NON-NLS-1$

			// G: Sskz.Bemerkung
			fillFootnotes(row, control);
		}
		return factory.getTable();

	}

	@SuppressWarnings("boxing")
	private void fillTrackMitteDistance(final TableRow row,
			final Aussenelementansteuerung control,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		fillIterableMultiCellWhenAllowed(row,
				getColumn(cols, Abstand_FEAx_Gleismitte), control,
				() -> isFindGeometryComplete(), ele -> {
					final Pair<Long, Long> sideDistance = getSideDistance(potk);
					if (sideDistance != null) {
						final String trackDistance = sideDistance.getSecond()
								.longValue() > 0 ? String.format("(%d)", //$NON-NLS-1$
										sideDistance.getSecond()) : ""; //$NON-NLS-1$
						if (!trackDistance.isEmpty()) {
							addTopologicalCell(row, getColumn(cols,
									SskzColumns.Abstand_FEAx_Gleismitte));
						}
						return String.format("%s%s", //$NON-NLS-1$
								Math.abs(sideDistance.getFirst()),
								trackDistance.isEmpty() ? "" //$NON-NLS-1$
										: " " + trackDistance); //$NON-NLS-1$
					}
					return ""; //$NON-NLS-1$
				});
	}

	private List<OperationalIdentifierFieldElement> getFieldElementIdentifier(
			final Aussenelementansteuerung control) {
		final HashMap<Class<? extends Ur_Objekt>, Case<Aussenelementansteuerung>> fillCases = new LinkedHashMap<>();
		fieldElementClasses.forEach((clazz, elements) -> fillCases.put(clazz,
				fieldElementCase(clazz)));

		final List<OperationalIdentifierFieldElement> result = new LinkedList<>();
		fillCases.forEach((clazz, fillCase) -> {
			if (fillCase.condition.apply(control).booleanValue()) {
				final Iterable<String> fillValuesIterable = fillCase.filling
						.apply(control);
				final List<String> sortList = IterableExtensions
						.sortWith(fillValuesIterable, fillCase.comparator);
				result.add(
						new OperationalIdentifierFieldElement(clazz, sortList));
			}
		});
		return result;

	}

	private <T extends Ur_Objekt> Case<Aussenelementansteuerung> fieldElementCase(
			final Class<T> clazz) {
		final Function<Aussenelementansteuerung, List<T>> getRelevantElementFunc = control -> getRelevantFieldElements(
				clazz, control);
		return new Case<>(
				control -> Boolean.valueOf(
						!getRelevantElementFunc.apply(control).isEmpty()),
				control -> getRelevantElementFunc.apply(control)
						.stream()
						.map(this::getFieldElementDesignation)
						.toList(),
				ITERABLE_FILLING_SEPARATOR, MIXED_STRING_COMPARATOR

		);
	}

	private List<Aussenelementansteuerung> getControlFromFieldELement(
			final Ur_Objekt object) {
		final Function<Ur_Objekt, List<Aussenelementansteuerung>> getAussElement = urObject -> switch (urObject) {
			case final W_Kr_Gsp_Element gsp -> getControlFromFieldELement(
					gsp.getIDStellelement().getValue());
			case final Signal signal -> getControlFromFieldELement(
					signal.getSignalReal()
							.getSignalRealAktiv()
							.getIDStellelement()
							.getValue());
			case final FMA_Komponente fma -> fma
					.getFMAKomponenteAchszaehlpunkt()
					.getIDInformation()
					.stream()
					.map(ID_Aussenelementansteuerung_TypeClass::getValue)
					.toList();
			case final PZB_Element pzb -> getControlFromFieldELement(
					pzb.getIDStellelement().getValue());
			case final Schluesselsperre schluessel -> getControlFromFieldELement(
					schluessel.getIDStellelement().getValue());
			case final Stellelement stellement -> List
					.of(stellement.getIDInformation().getValue());
			default -> null;
		};
		return getNullableObject(object, getAussElement::apply)
				.orElse(Collections.emptyList());
	}

	@SuppressWarnings("nls")
	private String getFieldElementDesignation(final Ur_Objekt object) {
		final Function<Ur_Objekt, String> destignationFunc = urObject -> switch (urObject) {
			case final W_Kr_Gsp_Element gsp -> gsp.getBezeichnung()
					.getBezeichnungTabelle()
					.getWert();
			case final Signal signal -> signal.getBezeichnung()
					.getBezeichnungTabelle()
					.getWert();
			case final FMA_Komponente fma -> fma.getBezeichnung()
					.getBezeichnungTabelle()
					.getWert();
			case final PZB_Element pzb -> getPzbDesignation(pzb);
			case final Schluesselsperre schluessel -> schluessel
					.getBezeichnung()
					.getBezeichnungTabelle()
					.getWert();
			default -> null;
		};
		return getNullableObject(object, destignationFunc::apply).orElse("");
	}

	@SuppressWarnings("nls")
	private String getPzbDesignation(final PZB_Element pzb) {
		final PZB_Art_TypeClass pzbArt = getNullableObject(pzb,
				ele -> ele.getPZBArt()).orElse(null);
		if (pzbArt == null) {
			return "";
		}
		final List<String> pzbElementBezugspunkt = PZBElementExtensions
				.getPZBElementBezugspunkt(pzb)
				.stream()
				.map(ele -> switch (ele) {
					case final Signal signal -> signal.getBezeichnung()
							.getBezeichnungTabelle()
							.getWert();
					case final W_Kr_Gsp_Element gsp -> gsp.getBezeichnung()
							.getBezeichnungTabelle()
							.getWert();
					default -> "";

				})
				.toList();
		if (pzb.getPZBElementGM() != null) {
			return String.format("%s (%s)", translate(pzbArt),
					String.join(",", pzbElementBezugspunkt));
		}

		if (pzb.getPZBElementGUE() != null) {
			final BigInteger speedCheck = getNullableObject(
					pzb.getPZBElementGUE(),
					gue -> gue.getPruefgeschwindigkeit().getWert())
							.orElse(null);
			if (speedCheck != null) {
				final String[] pzbArtEnum = translate(pzbArt).split("/");
				final List<String> shortPzbArt = Stream.of(pzbArtEnum)
						.map(ele -> {
							try {
								final double hz = Double.parseDouble(ele);
								final double shortHz = hz / 1000;
								if (shortHz == (int) shortHz) {
									return Integer.toString((int) shortHz);
								}
								return Double.toString(shortHz);
							} catch (final NumberFormatException e) {
								return ele;
							}
						})
						.toList();
				return String.format("%s/GÜ %s (%s)",
						String.join("/", shortPzbArt), speedCheck,
						String.join(",", pzbElementBezugspunkt));
			}
		}
		return "";
	}

	private <T extends Ur_Objekt> List<T> getRelevantFieldElements(
			final Class<T> clazz, final Aussenelementansteuerung control) {
		final Function<MultiContainer_AttributeGroup, Iterable<? extends Ur_Objekt>> getElementFunc = fieldElementClasses
				.getOrDefault(clazz, null);
		if (getElementFunc == null) {
			throw new IllegalArgumentException("Unexpect Class: " + clazz); //$NON-NLS-1$
		}
		final MultiContainer_AttributeGroup container = getContainer(control);
		final List<T> elements = Streams.stream(getElementFunc.apply(container))
				.map(clazz::cast)
				.toList();

		return elements.parallelStream()
				.filter(ele -> getControlFromFieldELement(ele)
						.contains(control))
				.toList();
	}

	private static Pair<Long, Long> getSideDistance(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		final double rotation = PunktObjektTopKanteExtensions
				.getCoordinate(potk)
				.getEffectiveRotation();
		final ENUMWirkrichtung direction = getNullableObject(potk,
				p -> p.getWirkrichtung().getWert()).orElse(null);
		// Find neighbor track in two side
		if (direction == null
				|| direction == ENUMWirkrichtung.ENUM_WIRKRICHTUNG_BEIDE) {
			return Optional
					.ofNullable(SignalSideDistance.getSideDistance(potk,
							ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN, rotation))
					.orElse(SignalSideDistance.getSideDistance(potk,
							ENUMWirkrichtung.ENUM_WIRKRICHTUNG_GEGEN,
							rotation));
		}
		return SignalSideDistance.getSideDistance(potk,
				potk.getWirkrichtung().getWert(), rotation);

	}
}
