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
import static org.eclipse.set.ppmodel.extensions.SignalExtensions.getTableBezeichnung;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.basis.MixedStringComparator;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.ppmodel.extensions.utils.GestellteWeiche;
import org.eclipse.set.utils.table.sorting.MixedStringCellComparator;

/**
 * The comparator for {@link SignalingRouteSection}
 */
public class SignalingRouteSectionComparator
		implements Comparator<SignalingRouteSection> {

	@Override
	public int compare(final SignalingRouteSection first,
			final SignalingRouteSection second) {
		final List<Pair<Signal, List<GestellteWeiche>>> firstList = new ArrayList<>(
				first.getElementBetween());
		firstList.add(new Pair<>(first.getEndRouteSecionSignal(),
				first.getDecisionTrackSwitches()));
		final List<Pair<Signal, List<GestellteWeiche>>> secondList = new ArrayList<>(
				second.getElementBetween());
		secondList.add(new Pair<>(second.getEndRouteSecionSignal(),
				second.getDecisionTrackSwitches()));
		final int compareBetweenElement = compareListObj(firstList, secondList,
				elementComparator());
		if (compareBetweenElement != 0) {
			return compareBetweenElement;
		}

		final String firstDweg = getNullableObject(first.getDweg(),
				dweg -> dweg.getBezeichnung()
						.getBezeichnungFstrDWeg()
						.getWert()).orElse(""); //$NON-NLS-1$
		final String secondDweg = getNullableObject(second.getDweg(),
				dweg -> dweg.getBezeichnung()
						.getBezeichnungFstrDWeg()
						.getWert()).orElse(""); //$NON-NLS-1$
		return firstDweg.compareTo(secondDweg);
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
			return comparator.compareCell(getTableBezeichnung(first),
					getTableBezeichnung(second));
		};
	}
}
