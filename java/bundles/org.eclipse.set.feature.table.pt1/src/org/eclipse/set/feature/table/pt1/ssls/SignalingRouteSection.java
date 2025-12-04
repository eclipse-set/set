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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.set.feature.table.pt1.sslz.SslzTransformator;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions;
import org.eclipse.set.ppmodel.extensions.utils.GestellteWeiche;

/**
 * The route section belong to the signaling section
 * 
 * @author truong
 */
public class SignalingRouteSection {
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

	private final Signal startSignal;
	private final Signal endSignal;

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
	 * @return the start {@link Signal} of the signaling section
	 */
	public Signal getStartSignal() {
		return startSignal;
	}

	/**
	 * @return the end {@link Signal} of this section
	 */
	public Signal getEndSignal() {
		return endSignal;
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
		this.startSignal = start;
		this.preRouteSections = preRouteSection;
		this.routeSection = routeSection;
		this.endSignal = FstrZugRangierExtensions.getZielSignal(routeSection);
		this.dweg = EObjectExtensions
				.getNullableObject(routeSection,
						FstrZugRangierExtensions::getFstrDWeg)
				.orElse(null);
		this.sectionType = SslzTransformator.getFstrZugArt(routeSection);
		determinDecisionTrackSwitches(routeSection, sectionType);
	}

	private void determinDecisionTrackSwitches(final Fstr_Zug_Rangier section,
			final String type) {
		if (type.startsWith("U")) { //$NON-NLS-1$
			decisionTrackSwitches = FstrZugRangierExtensions
					.getEntscheidungsweichen(section, Collections.emptyList());
		}
	}

	/**
	 * @return the start {@link Signal} of the signaling section
	 */
	public Signal getStart() {
		return startSignal;
	}

	/**
	 * @return the end {@link Signal} of this route section
	 */
	public Signal getZiel() {
		return endSignal;
	}

	/**
	 * @return the train route section
	 */
	public Fstr_Zug_Rangier getRouteSection() {
		return routeSection;
	}

	/**
	 * @return the signals lie between start signal and the end signal
	 */
	public List<Signal> getSignalsBetween() {
		return preRouteSections.stream()
				.map(SignalingRouteSection::getZiel)
				.toList();
	}

	@SuppressWarnings("nls")
	private List<String> betweenSignalsToString() {
		return preRouteSections.stream().map(preSection -> {
			final List<String> listStr = new LinkedList<>();
			listStr.addAll(preSection.decisionSwitchesToStr());
			listStr.add(getSignalBezeichnung(preSection.getZiel()));
			return listStr.stream().collect(Collectors.joining(" - "));
		}).toList();
	}

	private List<String> decisionSwitchesToStr() {
		if (decisionTrackSwitches != null && !decisionTrackSwitches.isEmpty()) {
			return decisionTrackSwitches.stream()
					.map(ele -> String.format("U[%s]", ele.getBezeichnung())) //$NON-NLS-1$
					.filter(Objects::nonNull)
					.toList();
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		final List<String> listStr = new LinkedList<>();
		listStr.add(getSignalBezeichnung(startSignal));
		listStr.addAll(betweenSignalsToString());
		listStr.addAll(decisionSwitchesToStr());
		final String dwegStr = EObjectExtensions
				.getNullableObject(dweg, d -> "/"
						+ d.getBezeichnung().getBezeichnungFstrDWeg().getWert())
				.orElse("");
		listStr.add(String.format("(%s%s)", getSignalBezeichnung(endSignal),
				dwegStr));
		return listStr.stream().collect(Collectors.joining(" - "));
	}

	/**
	 * @return comparator to sort the route section
	 */
	public static Comparator<SignalingRouteSection> routeSectionComparator() {
		return (first, second) -> {
			final List<Signal> firstSignals = new ArrayList<>(
					first.getSignalsBetween());
			firstSignals.add(first.endSignal);

			final List<Signal> secondSignals = new ArrayList<>(
					second.getSignalsBetween());
			secondSignals.add(second.endSignal);

			for (int i = 0; i < firstSignals.size(); i++) {
				if (secondSignals.size() < i + 1) {
					return 1;
				}
				final int compareSignal = comparatorSignal()
						.compare(firstSignals.get(i), secondSignals.get(i));
				if (compareSignal != 0) {
					return compareSignal;
				}
			}
			if (firstSignals.size() < secondSignals.size()) {
				return -1;
			}

			final String firstDweg = EObjectExtensions
					.getNullableObject(first.dweg,
							dweg -> dweg.getBezeichnung()
									.getBezeichnungFstrDWeg()
									.getWert())
					.orElse(""); //$NON-NLS-1$
			final String secondDweg = EObjectExtensions
					.getNullableObject(second.dweg,
							dweg -> dweg.getBezeichnung()
									.getBezeichnungFstrDWeg()
									.getWert())
					.orElse(""); //$NON-NLS-1$
			return firstDweg.compareTo(secondDweg);
		};
	}

	private static Comparator<Signal> comparatorSignal() {
		return (first, second) -> getSignalBezeichnung(first)
				.compareTo(getSignalBezeichnung(second));
	}

	private static String getSignalBezeichnung(final Signal signal) {
		return EObjectExtensions.getNullableObject(signal,
				s -> s.getBezeichnung().getBezeichnungTabelle().getWert())
				.orElse(""); //$NON-NLS-1$
	}

	// @Override
	// public int hashCode() {
	// return Objects.hash(startSignal, endSignal, dweg, sectionType,
	// decisionTrackSwitches);
	// }
	//
	// @Override
	// public boolean equals(final Object obj) {
	// return hashCode() == Objects.hashCode(obj);
	// }
}
