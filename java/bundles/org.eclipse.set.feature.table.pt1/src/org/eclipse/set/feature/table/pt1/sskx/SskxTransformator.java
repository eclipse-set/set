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
package org.eclipse.set.feature.table.pt1.sskx;

import static org.eclipse.set.model.planpro.Signale.ENUMBefestigungArt.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.feature.table.pt1.ssks.AbstractSignalTableTransform;
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Geodaten.Technischer_Punkt;
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne14;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne31str;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne32str;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne33str;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne34str;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne35str;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.OzBk;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ra12;
import org.eclipse.set.model.planpro.Signalbegriffe_Struktur.Signalbegriff_ID_TypeClass;
import org.eclipse.set.model.planpro.Signale.ENUMBefestigungArt;
import org.eclipse.set.model.planpro.Signale.ENUMBeleuchtet;
import org.eclipse.set.model.planpro.Signale.ENUMGeltungsbereich;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Signale.Signal_Rahmen;
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.FootnoteExtensions;
import org.eclipse.set.ppmodel.extensions.BasisAttributExtensions;
import org.eclipse.set.ppmodel.extensions.BereichObjektExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.SignalExtensions;
import org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.table.TMFactory;
import org.osgi.service.event.EventAdmin;

import com.google.common.collect.Streams;

/**
 * Table transformation for Tabelle der Tafeln (Sskx).
 * 
 * @author Truong
 */
public class SskxTransformator extends AbstractSignalTableTransform {

	private static final String SIGNAL_BEGRIFFE_BEZEICHNUNG_SEPERATOR = ", "; //$NON-NLS-1$
	private static final List<Class<? extends Signalbegriff_ID_TypeClass>> SPEICAL_BEZEICHNUNG_HANDLE_BEGRIFFE = List
			.of(Ne31str.class, Ne32str.class, Ne33str.class, Ne34str.class,
					Ne35str.class);

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
	SskxTransformator(final Set<ColumnDescriptor> cols,
			final EnumTranslationService enumTranslationService,
			final BankService bankingService, final EventAdmin eventAdmin,
			final String tableShortCut) {
		super(cols, enumTranslationService, bankingService, eventAdmin,
				tableShortCut);
	}

	private static Predicate<Signal> generalCondition() {
		return signal -> {
			if (signal.getSignalReal() == null) {
				return false;
			}

			if (signal.getSignalReal().getSignalRealAktiv() != null
					|| signal.getSignalReal()
							.getSignalRealAktivSchirm() != null) {
				return false;
			}

			final Set<Signal_Signalbegriff> signalbegriffe = SignalExtensions
					.getSignalbegriffe(signal);
			return signalbegriffe.stream()
					.map(Signal_Signalbegriff::getSignalbegriffID)
					.noneMatch(begriff -> begriff instanceof Ne14
							|| begriff instanceof OzBk
							|| begriff instanceof Ra12);
		};
	}

	private static List<Gleis_Bezeichnung> getRelevantGleisBezeichnuung(
			final Signal signal) {
		final MultiContainer_AttributeGroup container = BasisAttributExtensions
				.getContainer(signal);
		return Streams.stream(container.getGleisBezeichnung())
				.filter(g -> BereichObjektExtensions.contains(g, signal))
				.toList();
	}

	@Override
	protected Iterable<Signal> getRelevantSignal(
			final MultiContainer_AttributeGroup container) {
		return Streams.stream(container.getSignal())
				.filter(generalCondition())
				.toList();
	}

	@Override
	protected void fillSpecifyColumns(final TableRow row,
			final MultiContainer_AttributeGroup container, final Signal signal,
			final boolean isHauptbefestigung,
			final List<Signal_Rahmen> signalRahmen) {

		// D: Sskx.Standortmerkmale.Standort.Gleis
		fillIterable(row, getColumn(cols, SskxColumns.Gleis), signal,
				s -> getRelevantGleisBezeichnuung(signal).stream()
						.map(gleis -> EObjectExtensions
								.getNullableObject(gleis,
										g -> g.getBezeichnung()
												.getBezGleisBezeichnung()
												.getWert())
								.orElse(null))
						.filter(Objects::nonNull)
						.toList(),
				MIXED_STRING_COMPARATOR);

		// M: Sskx.Signalisierung.Signalbegriffe.Bezeichnung
		fillIterable(row,
				getColumn(cols, SskxColumns.Signalbegriffe_Bezeichnung), signal,
				this::transformSignalbegriffeBezeichnung,
				MIXED_STRING_COMPARATOR, s -> s,
				SIGNAL_BEGRIFFE_BEZEICHNUNG_SEPERATOR);

		// Sskx.Bermerkung
		fillFootnotes(row, signal);
		fillIterable(row, getColumn(cols, SskxColumns.Bemerkung), signalRahmen,
				rahmen -> rahmen.stream().map(r -> {
					final boolean isAngestrahlt = Streams
							.stream(SignalRahmenExtensions.getSignalbegriffe(r))
							.anyMatch(begriffe -> EObjectExtensions
									.getNullableObject(begriffe,
											b -> b.getSignalSignalbegriffAllg()
													.getBeleuchtet()
													.getWert())
									.orElse(null) == ENUMBeleuchtet.ENUM_BELEUCHTET_ANGESTRAHLT);
					if (isAngestrahlt) {
						return "angestrahlt"; //$NON-NLS-1$
					}
					final Basis_Objekt befestigungBauwerk = EObjectExtensions
							.getNullableObject(r,
									e -> e.getIDSignalBefestigung()
											.getValue()
											.getIDBefestigungBauwerk()
											.getValue())
							.orElse(null);
					if (befestigungBauwerk != null
							&& befestigungBauwerk instanceof final Technischer_Punkt tp) {
						return "Befestigung an " //$NON-NLS-1$
								+ tp.getTPBeschreibung().getWert();
					}

					if (r.getRahmenHoehe() != null
							&& r.getRahmenHoehe().getWert() != null) {
						return "Rahmenhöhen beachten"; //$NON-NLS-1$
					}
					return null;
				}).filter(Objects::nonNull).toList(), MIXED_STRING_COMPARATOR,
				s -> s, SIGNAL_BEGRIFFE_BEZEICHNUNG_SEPERATOR);
	}

	@Override
	protected ColumnDescriptor getFundamentHoeheColumn() {
		return getColumn(cols, SskxColumns.Hoehe);
	}

	@Override
	protected ColumnDescriptor getArtRegelzeichnungColumn() {
		return getColumn(cols, SskxColumns.Art_Regelzeichnung);
	}

	@Override
	protected ColumnDescriptor getRegelzeichnungColumn() {
		return getColumn(cols, SskxColumns.Regelzeichnung);
	}

	@Override
	protected ColumnDescriptor getBefestigungColumn() {
		return getColumn(cols, SskxColumns.Befestigung);
	}

	@Override
	protected Map<ENUMLinksRechts, ColumnDescriptor> getAbstandMastMitteColumn() {
		return Map.of(ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS,
				getColumn(cols, SskxColumns.Abstand_Mastmitte_links),
				ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS,
				getColumn(cols, SskxColumns.Abstand_Mastmitte_rechts));
	}

	@Override
	protected ColumnDescriptor getUeberhoehungColumn() {
		return getColumn(cols, SskxColumns.Ueberhoehung);
	}

	@Override
	protected ColumnDescriptor getLichtraumProfilColumn() {
		return getColumn(cols, SskxColumns.Lichtraumprofil);
	}

	@Override
	protected ColumnDescriptor getStreckeColumn() {
		return getColumn(cols, SskxColumns.Strecke);
	}

	@Override
	protected ColumnDescriptor getKmColumn() {
		return getColumn(cols, SskxColumns.km);
	}

	@Override
	protected ColumnDescriptor getBezeichnungColumn() {
		return getColumn(cols, SskxColumns.Bezeichnung_Signal);
	}

	@Override
	protected void handledThrowException(final TMFactory factory,
			final Signal signal, final Exception e) {
		// do nothing
	}

	@Override
	protected List<ENUMBefestigungArt> getSideDistanceMastType() {
		return List.of(ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_MAST_HOCH,
				ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_MAST_NIEDRIG,
				ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_SONSTIGE_HOCH,
				ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_SONSTIGE_NIEDRIG,
				ENUM_BEFESTIGUNG_ART_SONDERANORDNUNG_MAST_HOCH,
				ENUM_BEFESTIGUNG_ART_SONDERANORDNUNG_MAST_NIEDRIG,
				ENUM_BEFESTIGUNG_ART_PFOSTEN_HOCH,
				ENUM_BEFESTIGUNG_ART_PFOSTEN_NIEDRIG,
				ENUM_BEFESTIGUNG_ART_ARBEITSBUEHNE,
				ENUM_BEFESTIGUNG_ART_OL_MAST, ENUM_BEFESTIGUNG_ART_WAND,
				ENUM_BEFESTIGUNG_ART_DACH_DECKE,
				ENUM_BEFESTIGUNG_ART_SCHIENENFUSS);
	}

	private List<String> transformSignalbegriffeBezeichnung(
			final Signal signal) {
		final List<ENUMGeltungsbereich> geltungsbereich = signal.getSignalReal()
				.getGeltungsbereich()
				.stream()
				.map(bereich -> bereich.getWert())
				.filter(Objects::nonNull)
				.toList();
		if (geltungsbereich.isEmpty()) {
			return getSignalbegriffeBezeichnung(signal,
					signalbegriffBezeichnungSelections(null));
		}
		return geltungsbereich.stream().flatMap(geltung -> switch (geltung) {
			case ENUM_GELTUNGSBEREICH_DS -> getSignalbegriffeBezeichnung(signal,
					signalbegriffBezeichnungSelections(
							ENUMGeltungsbereich.ENUM_GELTUNGSBEREICH_DS))
									.stream();
			case ENUM_GELTUNGSBEREICH_DV -> getSignalbegriffeBezeichnung(signal,
					signalbegriffBezeichnungSelections(
							ENUMGeltungsbereich.ENUM_GELTUNGSBEREICH_DV))
									.stream();
			default -> Stream.empty();
		}).toList();
	}

	private static List<Function<Signalbegriff_ID_TypeClass, String>> signalbegriffBezeichnungSelections(
			final ENUMGeltungsbereich geltung) {
		final LinkedList<Function<Signalbegriff_ID_TypeClass, String>> selectBezeichnungFuncs = new LinkedList<>();
		// When Geltungsbereich_DS or without Geltungsbereich
		if (geltung == ENUMGeltungsbereich.ENUM_GELTUNGSBEREICH_DS
				|| geltung == null) {
			selectBezeichnungFuncs
					.add(Signalbegriff_ID_TypeClass::getKurzbezeichnungDS);
		}

		// When Geltungsbereich_DV or without Geltungsbereich
		if (geltung == ENUMGeltungsbereich.ENUM_GELTUNGSBEREICH_DV
				|| geltung == null) {
			selectBezeichnungFuncs
					.add(Signalbegriff_ID_TypeClass::getKurzbezeichnungDV);
		}

		selectBezeichnungFuncs
				.add(Signalbegriff_ID_TypeClass::getLangbezeichnung);
		selectBezeichnungFuncs.add(FootnoteExtensions::getSignalBregiffIDName);
		return selectBezeichnungFuncs;
	}

	private static List<String> getSignalbegriffeBezeichnung(
			final Signal signal,
			final List<Function<Signalbegriff_ID_TypeClass, String>> selectBezeichnungFunc) {
		return SignalExtensions.getSignalbegriffe(signal)
				.stream()
				.map(Signal_Signalbegriff::getSignalbegriffID)
				.map(signalBegriffe -> {
					if (SPEICAL_BEZEICHNUNG_HANDLE_BEGRIFFE.stream()
							.anyMatch(c -> c.isInstance(signalBegriffe))) {
						return FootnoteExtensions
								.getSignalBregiffIDName(signalBegriffe);
					}
					for (final Function<Signalbegriff_ID_TypeClass, String> function : selectBezeichnungFunc) {
						final String bezeichnung = function
								.apply(signalBegriffe);
						if (bezeichnung != null && !bezeichnung.isEmpty()) {
							if (signalBegriffe.getSymbol() != null
									&& !signalBegriffe.getSymbol().isEmpty()) {
								return String.format("%s (%s)", bezeichnung, //$NON-NLS-1$
										signalBegriffe.getSymbol());
							}
							return bezeichnung;
						}
					}
					return null;
				})
				.filter(Objects::nonNull)
				.toList();

	}
}
