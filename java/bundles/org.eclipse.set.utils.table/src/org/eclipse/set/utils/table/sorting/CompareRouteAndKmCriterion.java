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
package org.eclipse.set.utils.table.sorting;

import static org.eclipse.set.basis.MixedStringComparator.compareNullableValue;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.extensions.MatcherExtensions;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Compare the route and the km of TableRow leadings object
 * 
 * @author truong
 */
public class CompareRouteAndKmCriterion
		extends AbstractCompareWithDependencyOnServiceCriterion<TableRow> {
	private final Logger logger = LoggerFactory
			.getLogger(CompareRouteAndKmCriterion.class);
	private final SortDirectionEnum direction;
	private final Function<Ur_Objekt, Punkt_Objekt> getPunktObjectFunc;
	private final NumericCellComparator numericComparator;
	private static final String KILOMETRIERUNG_PATTERN = "(?<numberPrefix>-)?(?<numberN>[1-9]\\d{0,2}|0),((?<numberD1>\\d{3})|(?<numberD2>\\d)(?<numberN2Prefix>[\\+\\-])(?<numberN2>[1-9]\\d{0,4}))"; //$NON-NLS-1$
	private static final String EXTRA_LENGTH_GROUP_NAME = "numberN2"; //$NON-NLS-1$
	private final Pattern kmPattern;
	private boolean isWaitingOnService;

	/**
	 * @param getPunktObjectFunc
	 *            get {@link Punkt_Objekt} function
	 */
	public CompareRouteAndKmCriterion(
			final Function<Ur_Objekt, Punkt_Objekt> getPunktObjectFunc) {
		this(getPunktObjectFunc, SortDirectionEnum.ASC);
	}

	/**
	 * @param getPunktObjectFunc
	 *            get {@link Punkt_Objekt} function
	 * @param direction
	 *            the sort direction
	 */
	public CompareRouteAndKmCriterion(
			final Function<Ur_Objekt, Punkt_Objekt> getPunktObjectFunc,
			final SortDirectionEnum direction) {
		this.getPunktObjectFunc = getPunktObjectFunc;
		this.direction = direction;
		this.numericComparator = new NumericCellComparator(direction);
		this.kmPattern = getKilometrierungPattern();
	}

	@Override
	public int compare(final TableRow o1, final TableRow o2) {
		final Ur_Objekt firstLeadingObj = TableRowExtensions
				.getLeadingObject(o1);
		final Ur_Objekt secondLeadingObj = TableRowExtensions
				.getLeadingObject(o2);
		final Optional<Integer> compareObj = compareNullableValue(
				firstLeadingObj, secondLeadingObj, Objects::isNull);
		if (compareObj.isPresent()) {
			return compareObj.get().intValue();
		}
		final Punkt_Objekt firstPO = getPunktObjectFunc.apply(firstLeadingObj);
		final Punkt_Objekt secondPO = getPunktObjectFunc
				.apply(secondLeadingObj);
		return compareRouteAndKm(firstPO, secondPO);
	}

	// IMPROVE: the determine route and km can be depended on the
	// GeoKanteGeometryService, when the Punkt_Objekt more than one
	// Punkt_Objekt_Streck have. In this case should the compare do again after
	// the finding geometry process complete. Because this case is rarely occur
	// it will be for the first time ignore
	private int compareRouteAndKm(final Punkt_Objekt first,
			final Punkt_Objekt second) {
		final Optional<Integer> compareObj = compareNullableValue(first, second,
				Objects::isNull);
		if (compareObj.isPresent()) {
			return compareObj.get().intValue();
		}

		final List<Pair<String, List<String>>> firstStreckeAndKm = PunktObjektExtensions
				.getStreckeAndKm(first);
		final List<Pair<String, List<String>>> secondStreckeAndKm = PunktObjektExtensions
				.getStreckeAndKm(second);
		final Set<String> firstRouten = firstStreckeAndKm.stream()
				.map(Pair::getKey)
				// Compare only to fourth character
				.map(value -> value.substring(0, 3))
				.collect(Collectors.toSet());
		final Set<String> secondRouten = secondStreckeAndKm.stream()
				.map(Pair::getKey)
				// Compare only to fourth character
				.map(value -> value.substring(0, 3))
				.collect(Collectors.toSet());
		final int compareRouten = numericComparator.compareCell(firstRouten,
				secondRouten);
		if (compareRouten != 0) {
			return compareRouten;
		}

		final Set<String> firstKms = firstStreckeAndKm.stream()
				.flatMap(pair -> pair.getValue().stream())
				.collect(Collectors.toSet());
		final Set<String> secondKms = secondStreckeAndKm.stream()
				.flatMap(pair -> pair.getValue().stream())
				.collect(Collectors.toSet());
		final Optional<Integer> compareCollection = compareCollection(firstKms,
				secondKms);
		if (firstKms.isEmpty() || secondKms.isEmpty()) {
			isWaitingOnService = true;
		}
		if (compareCollection.isPresent()) {
			return compareCollection.get().intValue();
		}
		return compareKm(firstKms, secondKms);
	}

	/**
	 * @param firstKms
	 *            the first kilometer value
	 * @param secondKms
	 *            the second kilometer value
	 * @return compare value
	 */
	public int compareKm(final Set<String> firstKms,
			final Set<String> secondKms) {
		final Optional<Integer> compareCollection = compareNullableValue(
				firstKms, secondKms, Set::isEmpty);
		if (compareCollection.isPresent()) {
			return compareCollection.get().intValue();
		}

		for (final String firstKm : firstKms) {
			for (final String secondKm : secondKms) {
				final Optional<Integer> compareMatchPattern = compareNullableValue(
						firstKm, secondKm, km -> {
							final Matcher matcher = kmPattern.matcher(km);
							if (!matcher.matches()) {
								logger.error("Wrong Kilometer format: {}", km); //$NON-NLS-1$
								return true;
							}
							return false;
						});
				if (compareMatchPattern.isPresent()) {
					return compareMatchPattern.get().intValue();
				}

				final double firstDoubleValue = convertToDoubleValue(firstKm);
				final double secondDoubleValue = convertToDoubleValue(secondKm);
				final int compare = direction == SortDirectionEnum.ASC
						? Double.compare(firstDoubleValue, secondDoubleValue)
						: Double.compare(secondDoubleValue, firstDoubleValue);
				if (compare != 0) {
					return compare;
				}
			}
		}
		return 0;
	}

	private double convertToDoubleValue(final String km) {
		final Matcher matcher = kmPattern.matcher(km);
		final Optional<String> extraLength = MatcherExtensions.getGroup(matcher,
				EXTRA_LENGTH_GROUP_NAME);
		if (extraLength.isPresent()) {
			String originalKm = km.replace(extraLength.get(), ""); //$NON-NLS-1$
			originalKm = originalKm.substring(0, originalKm.length() - 1);
			final double doubleValue = Double
					.parseDouble(originalKm.replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$
			final double doubleValueExtraLength = Double
					.parseDouble(extraLength.get());
			return doubleValue + doubleValueExtraLength / 1000;
		}
		return Double.parseDouble(km.replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @return the kilometer pattern
	 */
	public static Pattern getKilometrierungPattern() {
		return Pattern.compile(KILOMETRIERUNG_PATTERN);
	}

	private <T> Optional<Integer> compareCollection(final Collection<T> first,
			final Collection<T> second) {
		if (first.isEmpty() == second.isEmpty() && first.isEmpty()) {
			return Optional.of(Integer.valueOf(0));
		}
		if (first.isEmpty()) {
			return Optional
					.of(direction == SortDirectionEnum.ASC ? Integer.valueOf(-1)
							: Integer.valueOf(1));
		}

		if (second.isEmpty()) {
			return Optional
					.of(direction == SortDirectionEnum.ASC ? Integer.valueOf(1)
							: Integer.valueOf(-1));
		}
		return Optional.empty();
	}

	@Override
	public String getTriggerComparisonEventTopic() {
		return isWaitingOnService ? Events.FIND_GEOMETRY_PROCESS_DONE : ""; //$NON-NLS-1$
	}
}
