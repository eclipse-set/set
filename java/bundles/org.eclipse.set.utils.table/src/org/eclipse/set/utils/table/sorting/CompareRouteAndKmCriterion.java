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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * 
 */
public class CompareRouteAndKmCriterion implements Comparator<TableRow> {

	private final SortDirectionEnum direction;
	private final Function<Ur_Objekt, Punkt_Objekt> getPunktObjectFunc;
	private final NumericCellComparator numericComparator;

	public CompareRouteAndKmCriterion(
			final Function<Ur_Objekt, Punkt_Objekt> getPunktObjectFunc) {
		this(getPunktObjectFunc, SortDirectionEnum.ASC);
	}

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
		return 0;
	}

	private static <T> Optional<Integer> compareObj(final T first,
			final T second) {
		if (first == second && first == null) {
			return Optional.of(Integer.valueOf(0));
		}

		if (first == null) {
			return Optional.of(Integer.valueOf(-1));
		}

		if (second == null) {
			return Optional.of(Integer.valueOf(1));
		}
		return Optional.empty();
	}

}
