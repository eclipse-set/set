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

import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.basis.MixedStringComparator;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.feature.table.pt1.sslz.SslzTransformator;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;
import org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions;
import org.eclipse.set.ppmodel.extensions.utils.GestellteWeiche;
import org.eclipse.set.utils.table.sorting.MixedStringCellComparator;

/**
 * The route section belong to the signaling section
 * 
 * @author truong
 */
public class SignalingRouteSection {

	private static final String SECTION_STR_DELIMITER = " - "; //$NON-NLS-1$
	private static final String DWEG_SEPERATOR = "/"; //$NON-NLS-1$
	private static final String SECTION_WITH_TRACK_DECISION_TYPE = "U"; //$NON-NLS-1$
	/**
	 * Previous route section between start signal and this route section
	 */
	private final List<SignalingRouteSection> preRouteSections;

	/**
	 * @return the previous route sections
	 */
	public List<SignalingRouteSection> getPreRouteSections() {
		return preRouteSections;
	}

	private final Signal startSignalingSectionSignal;
	private final Signal endRouteSecionSignal;

	/**
	 * The Fstr_DWeg of the the routeSection
	 */
	private final Fstr_DWeg dweg;
	/**
	 * The train route type of the routeSection
	 */
	private final String sectionType;
	/**
	 * The decision {@link W_Kr_Gsp_Element} of the routeSection
	 */
	private List<GestellteWeiche> decisionTrackSwitches;
	private final Fstr_Zug_Rangier routeSection;

	/**
	 * @return the end {@link Signal} of this section
	 */
	public Signal getEndRouteSecionSignal() {
		return endRouteSecionSignal;
	}

	/**
	 * @return the {@link Fstr_DWeg} of this section
	 */
	public Fstr_DWeg getDweg() {
		return dweg;
	}

	/**
	 * @return the section type
	 */
	public String getSectionType() {
		return sectionType;
	}

	/**
	 * @return the decision track switch of this section
	 */
	public List<GestellteWeiche> getDecisionTrackSwitches() {
		return decisionTrackSwitches;
	}

	/**
	 * @return the train route section
	 */
	public Fstr_Zug_Rangier getRouteSection() {
		return routeSection;
	}

	/**
	 * @return the signals and the decision track switch lie between start and
	 *         end signal
	 */
	public List<Pair<Signal, List<GestellteWeiche>>> getElementBetween() {
		return preRouteSections.stream()
				.map(section -> new Pair<>(section.endRouteSecionSignal,
						section.decisionTrackSwitches))
				.toList();
	}

	/**
	 * @param start
	 *            the start {@link Signal} of the signaling section
	 * @param routeSection
	 *            the {@link Fstr_Zug_Rangier}
	 */
	public SignalingRouteSection(final Signal start,
			final Fstr_Zug_Rangier routeSection) {
		this(start, routeSection, new LinkedList<>());
	}

	/**
	 * @param start
	 *            the start {@link Signal} of the signaling section
	 * @param routeSection
	 *            the train route section
	 * @param preRouteSection
	 *            the previous train route sections
	 */
	public SignalingRouteSection(final Signal start,
			final Fstr_Zug_Rangier routeSection,
			final List<SignalingRouteSection> preRouteSection) {
		this.startSignalingSectionSignal = start;
		this.preRouteSections = preRouteSection;
		this.routeSection = routeSection;
		this.endRouteSecionSignal = FstrZugRangierExtensions
				.getZielSignal(routeSection);
		this.dweg = getNullableObject(routeSection,
				FstrZugRangierExtensions::getFstrDWeg).orElse(null);
		this.sectionType = SslzTransformator.getFstrZugArt(routeSection);
		determinDecisionTrackSwitches(routeSection, sectionType);
	}

	private void determinDecisionTrackSwitches(final Fstr_Zug_Rangier section,
			final String type) {
		if (type.startsWith(SECTION_WITH_TRACK_DECISION_TYPE)) {
			decisionTrackSwitches = FstrZugRangierExtensions
					.getEntscheidungsweichen(section, Collections.emptyList());
		} else {
			decisionTrackSwitches = Collections.emptyList();
		}
	}

	/**
	 * Use for comparator of {@link SignalingRouteSection}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(startSignalingSectionSignal, endRouteSecionSignal,
				dweg, sectionType, getElementBetween());
	}

	@Override
	public boolean equals(final Object obj) {
		return hashCode() == Objects.hashCode(obj);
	}

	@SuppressWarnings("nls")
	private List<String> betweenSignalsToString() {
		return preRouteSections.stream().map(preSection -> {
			final List<String> listStr = new LinkedList<>();
			listStr.addAll(preSection.decisionSwitchesToStr());
			listStr.add(
					getSignalBezeichnung(preSection.getEndRouteSecionSignal()));
			return listStr.stream().collect(Collectors.joining(" - "));
		}).toList();
	}

	private List<String> decisionSwitchesToStr() {
		if (decisionTrackSwitches != null && !decisionTrackSwitches.isEmpty()) {
			return decisionTrackSwitches.stream()
					.map(ele -> String.format("%s[%s]", //$NON-NLS-1$
							SECTION_WITH_TRACK_DECISION_TYPE,
							ele.getBezeichnung()))
					.filter(Objects::nonNull)
					.toList();
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		final List<String> listStr = new LinkedList<>();
		listStr.add(getSignalBezeichnung(startSignalingSectionSignal));
		listStr.addAll(betweenSignalsToString());
		listStr.addAll(decisionSwitchesToStr());
		final String dwegStr = getNullableObject(dweg,
				d -> DWEG_SEPERATOR
						+ d.getBezeichnung().getBezeichnungFstrDWeg().getWert())
								.orElse("");
		listStr.add(String.format("(%s%s)",
				getSignalBezeichnung(endRouteSecionSignal), dwegStr));
		return listStr.stream()
				.collect(Collectors.joining(SECTION_STR_DELIMITER));

	}

	/**
	 * @return comparator to sort the route section
	 */
	public static Comparator<SignalingRouteSection> routeSectionComparator() {
		return (first, second) -> {
			final List<Pair<Signal, List<GestellteWeiche>>> firstList = new ArrayList<>(
					first.getElementBetween());
			firstList.add(new Pair<>(first.endRouteSecionSignal,
					first.decisionTrackSwitches));
			final List<Pair<Signal, List<GestellteWeiche>>> secondList = new ArrayList<>(
					second.getElementBetween());
			secondList.add(new Pair<>(second.endRouteSecionSignal,
					second.decisionTrackSwitches));
			final int compareBetweenElement = compareListObj(firstList,
					secondList, elementComparator());
			if (compareBetweenElement != 0) {
				return compareBetweenElement;
			}

			final String firstDweg = getNullableObject(first.dweg,
					dweg -> dweg.getBezeichnung()
							.getBezeichnungFstrDWeg()
							.getWert()).orElse(""); //$NON-NLS-1$
			final String secondDweg = getNullableObject(second.dweg,
					dweg -> dweg.getBezeichnung()
							.getBezeichnungFstrDWeg()
							.getWert()).orElse(""); //$NON-NLS-1$
			return firstDweg.compareTo(secondDweg);
		};
	}

	private static Comparator<Pair<Signal, List<GestellteWeiche>>> elementComparator() {
		return (first, second) -> {
			final int compareSignal = signalComparator()
					.compare(first.getFirst(), second.getFirst());
			if (compareSignal != 0) {
				return compareSignal;
			}
			final Optional<Integer> compareNullableValue = MixedStringComparator
					.compareNullableValue(first.getSecond(), second.getSecond(),
							Objects::isNull);
			if (compareNullableValue.isPresent()) {
				return compareNullableValue.get().intValue();
			}
			return compareListObj(first.getSecond(), second.getSecond(),
					decisionTrackSwitchComparator());
		};
	}

	/**
	 * Each element of the list will be compare, then compare size of list.
	 * Example: the List: {1, 2, 3, 4} < List: {1, 3, 4}
	 * 
	 * @param <T>
	 *            the object type
	 * @param first
	 *            the first List
	 * @param second
	 *            the second list
	 * @param comparator
	 *            the object comparator
	 * @return the comapre value
	 */
	private static <T> int compareListObj(final List<T> first,
			final List<T> second, final Comparator<T> comparator) {

		for (int i = 0; i < first.size(); i++) {
			if (second.size() < i + 1) {
				return 1;
			}
			final int compareValue = comparator.compare(first.get(i),
					second.get(i));
			if (compareValue != 0) {
				return compareValue;
			}
		}
		if (first.size() < second.size()) {
			return -1;
		}
		return 0;
	}

	private static Comparator<GestellteWeiche> decisionTrackSwitchComparator() {
		return (first, second) -> {
			final MixedStringCellComparator comparator = new MixedStringCellComparator(
					SortDirectionEnum.ASC);
			return comparator.compareCell(first.getBezeichnung(),
					second.getBezeichnung());
		};
	}

	private static Comparator<Signal> signalComparator() {
		return (first, second) -> {
			final String firstNumberPrefix = getNullableObject(first,
					s -> s.getBezeichnung()
							.getBezeichnungTabelle()
							.getWert()
							.substring(0, 2)).orElse(null);
			final String secondNumberPrefix = getNullableObject(second,
					s -> s.getBezeichnung()
							.getBezeichnungTabelle()
							.getWert()
							.substring(0, 2)).orElse(null);
			final Optional<Integer> compareNullable = MixedStringComparator
					.compareNullableValue(firstNumberPrefix, secondNumberPrefix,
							s -> s == null || !NumberUtils.isCreatable(s));
			if (compareNullable.isPresent()) {
				return compareNullable.get().intValue();
			}

			final int comparePrefix = Integer.valueOf(firstNumberPrefix)
					.compareTo(Integer.valueOf(secondNumberPrefix));
			if (comparePrefix != 0) {
				return comparePrefix;
			}
			// TODO: need to compare signal group before compare singal
			// designation
			final MixedStringCellComparator comparator = new MixedStringCellComparator(
					SortDirectionEnum.ASC);
			return comparator.compareCell(getSignalBezeichnung(first),
					getSignalBezeichnung(second));
		};
	}

	private static String getSignalBezeichnung(final Signal signal) {
		return getNullableObject(signal,
				s -> s.getBezeichnung().getBezeichnungTabelle().getWert())
						.orElse(""); //$NON-NLS-1$
	}
}
