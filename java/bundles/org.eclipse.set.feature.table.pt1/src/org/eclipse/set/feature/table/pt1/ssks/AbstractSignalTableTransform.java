/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.ssks;

import static org.eclipse.set.model.planpro.Signale.ENUMBefestigungArt.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.ssks.SignalSideDistance.SideDistance;
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts;
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.Strecke;
import org.eclipse.set.model.planpro.Gleis.Gleis_Lichtraum;
import org.eclipse.set.model.planpro.Signale.ENUMBefestigungArt;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung;
import org.eclipse.set.model.planpro.Signale.Signal_Rahmen;
import org.eclipse.set.model.planpro.Verweise.ID_Regelzeichnung_TypeClass;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.ppmodel.extensions.BereichObjektExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions;
import org.eclipse.set.ppmodel.extensions.SignalExtensions;
import org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions;
import org.eclipse.set.utils.math.BigDecimalExtensions;
import org.eclipse.set.utils.table.RowFactory;
import org.eclipse.set.utils.table.TMFactory;
import org.eclipse.xtext.xbase.lib.Pair;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Streams;

/**
 * This
 */
public abstract class AbstractSignalTableTransform
		extends AbstractPlanPro2TableModelTransformator {
	static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractSignalTableTransform.class);
	BankService bankingService;
	String tableShortCut;
	Map<Signal, SignalSideDistance> sideDistancesSignal;

	private static final List<ENUMBefestigungArt> mastTypeOfSignalWithTwoMast = List
			.of(ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_MAST_HOCH,
					ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_MAST_NIEDRIG,
					ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_SONSTIGE_HOCH,
					ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_SONSTIGE_NIEDRIG,
					ENUM_BEFESTIGUNG_ART_SONDERANORDNUNG_MAST_HOCH,
					ENUM_BEFESTIGUNG_ART_SONDERANORDNUNG_MAST_NIEDRIG);

	protected AbstractSignalTableTransform(final Set<ColumnDescriptor> cols,
			final EnumTranslationService enumTranslationService,
			final BankService bankingService, final EventAdmin eventAdmin,
			final String tableShortCut) {
		super(cols, enumTranslationService, eventAdmin);
		this.bankingService = bankingService;
		this.tableShortCut = tableShortCut;
	}

	@Override
	public Table transformTableContent(
			final MultiContainer_AttributeGroup container,
			final TMFactory factory) {
		sideDistancesSignal = new HashMap<>();
		for (final Signal signal : getRelevantSignal(container)) {
			Thread.currentThread();
			if (Thread.interrupted()) {
				return null;
			}
			transformSignal(factory, container, signal);
		}
		return factory.getTable();
	}

	protected abstract Iterable<Signal> getRelevantSignal(
			MultiContainer_AttributeGroup contanier);

	protected void transformSignal(final TMFactory factory,
			final MultiContainer_AttributeGroup container,
			final Signal signal) {
		try {
			final RowFactory newRowGroup = factory.newRowGroup(signal);
			final List<List<Signal_Befestigung>> befestigungsgruppen = SignalExtensions
					.getBefestigungsgruppen(signal,
							mastTypeOfSignalWithTwoMast);
			for (int i = 0; i < 2 && i < befestigungsgruppen.size(); i++) {
				final boolean isHauptbefestigung = i == 0;
				final Set<Signal_Befestigung> gruppe = new HashSet<>(
						befestigungsgruppen.get(i));
				if (isHauptbefestigung || !gruppe.isEmpty()) {
					final List<Signal_Rahmen> signalRahmen = SignalExtensions
							.signalRahmenForBefestigung(signal, gruppe);
					final TableRow row = newRowGroup.newTableRow();
					fillGenerallyColumns(row, container, signal,
							isHauptbefestigung, signalRahmen);
					fillSpecifyColumns(row, container, signal,
							isHauptbefestigung, signalRahmen);
				}
			}
		} catch (final Exception e) {
			LOGGER.error("{0}: {1} - failed to transform table contents", //$NON-NLS-1$
					e.getClass().getSimpleName(), e.getMessage());
			handledThrowException(factory, signal, e);
		}
	}

	/**
	 * Fill the specify column of table
	 * 
	 * @param row
	 *            the {@link TableRow}
	 * @param container
	 *            the {@link MultiContainer_AttributeGroup}
	 * @param signal
	 *            the {@link Signal}
	 * @param isHauptbefestigung
	 *            is this signal an main mast
	 * @param signalRahmen
	 *            the list of {@link Signal_Rahmen}
	 */
	protected abstract void fillSpecifyColumns(TableRow row,
			MultiContainer_AttributeGroup container, Signal signal,
			boolean isHauptbefestigung, List<Signal_Rahmen> signalRahmen);

	/**
	 * Fill the generally columns, which are same at all Signal table
	 * 
	 * @param row
	 * @param container
	 * @param signal
	 * @param isHauptBefestigung
	 * @param signalRahmen
	 */
	@SuppressWarnings("boxing")
	protected void fillGenerallyColumns(final TableRow row,
			final MultiContainer_AttributeGroup container, final Signal signal,
			final boolean isHauptBefestigung,
			final List<Signal_Rahmen> signalRahmen) {
		// Bezeichnung.Signal
		fillConditional(row, getBezeichnungColumn(), signal,
				s -> isHauptBefestigung, SignalExtensions::getTableBezeichnung,
				s -> ""); //$NON-NLS-1$

		final List<Pair<String, List<String>>> streckeAndKm = PunktObjektExtensions
				.getStreckeAndKm(signal);
		if (signal.getPunktObjektStrecke().size() > 1
				&& signal.getPunktObjektStrecke()
						.stream()
						.noneMatch(pos -> pos.getKmMassgebend() != null
								&& pos.getKmMassgebend().getWert() != null)) {
			addTopologicalCell(row, getStreckeColumn());
			addTopologicalCell(row, getKmColumn());
		}
		// Standortmerkmale.Standort.Strecke
		fillIterableWithConditional(row, getStreckeColumn(), signal,
				s -> isHauptBefestigung,
				s -> streckeAndKm.stream().map(Pair::getKey).toList(),
				MIXED_STRING_COMPARATOR, ITERABLE_FILLING_SEPARATOR);

		// Standortmerkmale.Standort.km
		fillIterableSingleCellWhenAllowed(row, getKmColumn(), signal,
				() -> GEOKanteGeometryExtensions.isFindGeometryComplete()
						|| streckeAndKm.stream()
								.flatMap(p -> p.getValue().stream())
								.anyMatch(s -> s != null && !s.isEmpty()),
				s -> {
					final List<String> kmValues = streckeAndKm.stream()
							.flatMap(p -> p.getValue().stream())
							.toList();
					if (kmValues.stream()
							.anyMatch(km -> km != null && !km.isEmpty())) {
						return kmValues;
					}

					final Punkt_Objekt_TOP_Kante_AttributeGroup potk = PunktObjektExtensions
							.getSinglePoint(s);
					final List<Strecke> routeThroughBereichObjekt = PunktObjektTopKanteExtensions
							.getStreckenThroughBereichObjekt(potk);
					return PunktObjektExtensions.getStreckeKm(signal,
							routeThroughBereichObjekt);
				}, null, ITERABLE_FILLING_SEPARATOR, tableShortCut);

		// Standortmerkmale.Lichtraumprofil
		fillIterableWithConditional(row, getLichtraumProfilColumn(), signal,
				s -> signal.getSignalReal() != null, s -> {
					final List<Gleis_Lichtraum> lichtraeume = Streams
							.stream(container.getGleisLichtraum())
							.filter(lichtRaum -> BereichObjektExtensions
									.contains(lichtRaum, s))
							.toList();
					final Set<String> translateValues = lichtraeume.stream()
							.map(Gleis_Lichtraum::getLichtraumprofil)
							.filter(Objects::nonNull)
							.map(this::translate)
							.collect(Collectors.toSet());
					final List<String> sortedList = new ArrayList<>(
							translateValues);
					sortedList.sort(Comparable::compareTo);
					return sortedList;
				}, null, ITERABLE_FILLING_SEPARATOR);

		// Standortmerkmale.Ueberhoehung
		if (signal.getSignalReal() != null) {
			fillIterableSingleCellWhenAllowed(row, getUeberhoehungColumn(),
					signal, () -> bankingService.isFindBankingComplete(), s -> {
						final TopPoint topPoint = new TopPoint(s);
						final List<BigDecimal> bankValues = bankingService
								.findBankValue(topPoint);
						return bankValues.stream()
								.filter(Objects::nonNull)
								.map(value -> BigDecimalExtensions
										.toTableInteger(value.multiply(
												new BigDecimal(1000))))
								.toList();
					}, null, ITERABLE_FILLING_SEPARATOR, tableShortCut);
		}

		// Standortmerkmale.Abstand_Mastmitte.links
		// Standortmerkmale.Abstand_Mastmitte.rechts
		fillAbstandMastMitte(row, signal);

		// konstruktive_Merkmale.Anordnung.Befestigung
		fillIterable(row, getBefestigungColumn(), signalRahmen,
				rahmen -> rahmen.stream()
						.map(r -> fillBefestigung(
								SignalRahmenExtensions.getSignalBefestigung(r)))
						.collect(Collectors.toSet()),
				null);

		// konstruktive_Merkmale.Anordnung.Regelzeichnung
		fillIterable(row, getRegelzeichnungColumn(), signalRahmen,
				rahmen -> rahmen.stream().flatMap(r -> {
					final Signal_Befestigung signalBefestigung = SignalRahmenExtensions
							.getSignalBefestigung(r);
					if (signalBefestigung == null) {
						return Stream.empty();
					}
					return signalBefestigung.getIDRegelzeichnung()
							.stream()
							.map(ID_Regelzeichnung_TypeClass::getValue)
							.filter(Objects::nonNull)
							.map(z -> fillRegelzeichnung(z));
				}).toList(), null);

		// konstruktive_Merkmale.Fundament.Art_Regelzeichnung
		fillIterable(row, getArtRegelzeichnungColumn(), signalRahmen,
				rahmen -> transformRegelzeichnungArt(row, rahmen),
				MIXED_STRING_COMPARATOR);

		// konstruktive_Merkmale.Fundament.Hoehe
		fillIterable(row, getFundamentHoeheColumn(), signalRahmen,
				rahmen -> rahmen.stream()
						.map(r -> EObjectExtensions.getNullableObject(r,
								e -> SignalRahmenExtensions.getFundament(e)
										.getSignalBefestigungAllg()
										.getHoeheFundamentoberkante()
										.getWert())
								.orElse(null))
						.filter(Objects::nonNull)
						.toList(),
				null,
				value -> BigDecimalExtensions.toTableInteger(value, 1000));
	}

	protected Iterable<String> transformRegelzeichnungArt(final TableRow row,
			final List<Signal_Rahmen> rahmen) {
		final List<Signal_Befestigung> fundamenten = rahmen.stream()
				.map(SignalRahmenExtensions::getFundament)
				.filter(Objects::nonNull)
				.toList();
		final List<String> fundamentArt = fundamenten.stream()
				.map(fund -> EObjectExtensions
						.getNullableObject(fund,
								f -> f.getSignalBefestigungAllg()
										.getFundamentArt())
						.orElse(null))
				.filter(Objects::nonNull)
				.map(this::translate)
				.filter(Objects::nonNull)
				.toList();
		final List<String> regelzeichnungen = fundamenten.stream()
				.flatMap(fund -> fund.getIDRegelzeichnung().stream())
				.map(id -> {
					if (id == null) {
						return null;
					}
					return fillRegelzeichnung(id.getValue());
				})
				.filter(Objects::nonNull)
				.toList();
		if (!regelzeichnungen.isEmpty()) {
			addTopologicalCell(row, getArtRegelzeichnungColumn());
		}
		final Set<String> result = new HashSet<>(regelzeichnungen);
		result.addAll(fundamentArt);
		return result;
	}

	protected void fillAbstandMastMitte(final TableRow row,
			final Signal signal) {
		getAbstandMastMitteColumn().forEach(
				(linksrechts, column) -> fillIterableMultiCellWhenAllow(row,
						column, signal,
						GEOKanteGeometryExtensions::isFindGeometryComplete,
						s -> {
							final SignalSideDistance signalSideDistances = sideDistancesSignal
									.computeIfAbsent(s,
											ele -> new SignalSideDistance(ele,
													getSideDistanceMastType()));
							final Set<SideDistance> distances = switch (linksrechts) {
								case ENUM_LINKS_RECHTS_LINKS -> signalSideDistances
										.getSideDistancesLeft();
								case ENUM_LINKS_RECHTS_RECHTS -> signalSideDistances
										.getSideDistancesRight();
							};

							if (distances.stream()
									.anyMatch(v -> v
											.getDistanceToNeighborTrack() > 0)) {
								addTopologicalCell(row, column);
							}
							return distances.stream()
									.map(SideDistance::toString)
									.toList();
						}, null, ITERABLE_FILLING_SEPARATOR));
	}

	protected abstract List<ENUMBefestigungArt> getSideDistanceMastType();

	protected abstract ColumnDescriptor getFundamentHoeheColumn();

	protected abstract ColumnDescriptor getArtRegelzeichnungColumn();

	protected abstract ColumnDescriptor getRegelzeichnungColumn();

	protected abstract ColumnDescriptor getBefestigungColumn();

	protected abstract Map<ENUMLinksRechts, ColumnDescriptor> getAbstandMastMitteColumn();

	protected abstract ColumnDescriptor getUeberhoehungColumn();

	protected abstract ColumnDescriptor getLichtraumProfilColumn();

	protected abstract ColumnDescriptor getStreckeColumn();

	protected abstract ColumnDescriptor getKmColumn();

	protected abstract ColumnDescriptor getBezeichnungColumn();

	protected abstract void handledThrowException(TMFactory row, Signal signal,
			Exception e);

	@SuppressWarnings("nls")
	private String fillBefestigung(final Signal_Befestigung befestigung) {
		final ENUMBefestigungArt art = EObjectExtensions.getNullableObject(
				befestigung,
				b -> b.getSignalBefestigungAllg().getBefestigungArt().getWert())
				.orElse(null);
		return switch (art) {
			case ENUM_BEFESTIGUNG_ART_SONSTIGE -> {
				final List<ID_Bearbeitungsvermerk_TypeClass> bearbeitungsvermerke = EObjectExtensions
						.getNullableObject(befestigung,
								b -> b.getSignalBefestigungAllg()
										.getBefestigungArt()
										.getIDBearbeitungsvermerk())
						.orElse(null);
				if (bearbeitungsvermerke == null
						|| bearbeitungsvermerke.isEmpty()) {
					throw new IllegalArgumentException(String.format(
							"'Befestigung Art of Befestigung %s has no Bearbeitungsvermerke.",
							befestigung.getIdentitaet().getWert()));
				}
				yield bearbeitungsvermerke.stream()
						.map(b -> b.getValue()
								.getBearbeitungsvermerkAllg()
								.getKurztext()
								.getWert())
						.collect(Collectors.joining(","));
			}
			default -> translate(
					EObjectExtensions
							.getNullableObject(befestigung,
									b -> b.getSignalBefestigungAllg()
											.getBefestigungArt())
							.orElse(null));
		};
	}

}
