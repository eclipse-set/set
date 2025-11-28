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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Compare the route and the km of TableRow leadings object
 * 
 * @author truong
 */
public class CompareRouteAndKmCriterion
		extends AbstractCompareWithDependencyOnServiceCriterion<TableRow> {

	private final SortDirectionEnum direction;
	private final Function<Ur_Objekt, Punkt_Objekt> getPunktObjectFunc;
	private final NumericCellComparator numericComparator;
	private boolean isWaitingOnService = false;

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
	}

	@Override
	public int compare(final TableRow o1, final TableRow o2) {
		final Ur_Objekt firstLeadingObj = TableRowExtensions
				.getLeadingObject(o1);
		final Ur_Objekt secondLeadingObj = TableRowExtensions
				.getLeadingObject(o2);

		final Optional<Integer> compareObj = compareObj(firstLeadingObj,
				secondLeadingObj);
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
		final Optional<Integer> compareObj = compareObj(first, second);
		if (compareObj.isPresent()) {
			return compareObj.get().intValue();
		}

		final List<Pair<String, List<String>>> firstStreckeAndKm = PunktObjektExtensions
				.getStreckeAndKm(first);
		final List<Pair<String, List<String>>> secondStreckeAndKm = PunktObjektExtensions
				.getStreckeAndKm(second);
		final Set<String> firstRouten = firstStreckeAndKm.stream()
				.map(Pair::getKey)
				.collect(Collectors.toSet());
		final Set<String> secondRouten = secondStreckeAndKm.stream()
				.map(Pair::getKey)
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
		return numericComparator.compareCell(firstKms, secondKms);
	}

	private <T> Optional<Integer> compareObj(final T first, final T second) {
		if (first == second && first == null) {
			return Optional.of(Integer.valueOf(0));
		}

		if (first == null) {
			return Optional
					.of(direction == SortDirectionEnum.ASC ? Integer.valueOf(-1)
							: Integer.valueOf(1));
		}

		if (second == null) {
			return Optional
					.of(direction == SortDirectionEnum.ASC ? Integer.valueOf(1)
							: Integer.valueOf(-1));
		}
		return Optional.empty();
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
