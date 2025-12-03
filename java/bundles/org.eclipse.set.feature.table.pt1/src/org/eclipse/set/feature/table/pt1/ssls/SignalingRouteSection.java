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
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;

/**
 * The route section belong to the signaling section
 * 
 * @author truong
 */
public class SignalingRouteSection {
	protected final List<Fstr_Zug_Rangier> routeSections;
	private final Signal startSignal;
	protected Signal endSignal;

	private final List<Signal> signalsBetween;

	// The Fstr_DWeg of the last route sections;
	protected Fstr_DWeg dweg;

	/**
	 * @param start
	 *            the start {@link Signal} of the signaling section
	 */
	public SignalingRouteSection(final Signal start) {
		this.startSignal = start;
		this.routeSections = new LinkedList<>();
		this.signalsBetween = new LinkedList<>();
	}

	/**
	 * @return the list of {@link Signal} between the start signal and the end
	 *         signal
	 */
	public List<Signal> getSignalsBetween() {
		return signalsBetween;
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

	@Override
	public String toString() {
		final List<String> listStr = new LinkedList<>();
		listStr.add(getSignalBezeichnung(startSignal));
		signalsBetween.forEach(s -> listStr.add(getSignalBezeichnung(s)));
		listStr.add(String.format("(%s%s)", getSignalBezeichnung(endSignal), //$NON-NLS-1$
				dweg != null ? "/" + dweg.getBezeichnung() //$NON-NLS-1$
						.getBezeichnungFstrDWeg()
						.getWert() : "")); //$NON-NLS-1$
		return listStr.stream().collect(Collectors.joining(" - ")); //$NON-NLS-1$
	}

	/**
	 * @return comparator to sort the route section
	 */
	public static Comparator<SignalingRouteSection> routeSectionComparator() {
		return (first, second) -> {
			final List<Signal> firstSignals = new ArrayList<>(
					first.signalsBetween);
			firstSignals.add(first.endSignal);

			final List<Signal> secondSignals = new ArrayList<>(
					second.signalsBetween);
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
}
