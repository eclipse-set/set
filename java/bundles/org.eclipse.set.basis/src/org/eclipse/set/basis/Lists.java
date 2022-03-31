/**
 * Copyright (c) 2014 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Diese Klasse stellt Funktionen für Listen zur Verfügung.
 *
 * @author Schaefer
 */
public class Lists {

	/**
	 * Fügt Elemente einer Kollektion zu einer Liste hinzu, falls diese in der
	 * Liste noch nicht vorhanden sind.
	 *
	 * @param <T>
	 *            der Typ für die Elemente der Liste
	 * @param list
	 *            die zu erweiternde Liste
	 * @param added
	 *            die Kollektion
	 */
	public static <T> void addAllNew(final List<T> list,
			final Collection<T> added) {
		for (final T element : added) {
			addNew(list, element);
		}
	}

	/**
	 * Fügt ein Element zu einer Liste hinzu, falls es in dieser Liste noch
	 * nicht vorhanden ist.
	 *
	 * @param <T>
	 *            der Typ für die Elemente der Liste
	 * @param list
	 *            die zu erweiternde Liste
	 * @param element
	 *            das Element
	 */
	public static <T> void addNew(final List<T> list, final T element) {
		if (!list.contains(element)) {
			list.add(element);
		}
	}

	/**
	 * Filtert die Elemente der Quellliste nach der angegebenen Bedingung und
	 * gibt diese in einer neuen Ergebnisliste zurück.
	 *
	 * @param <T>
	 *            der Typ für die Elemente der Liste
	 * @param source
	 *            die Quellliste
	 * @param condition
	 *            die Bedingung
	 *
	 * @return die Ergebnisliste
	 */
	public static <T> List<T> filter(final List<T> source,
			final Predicate<T> condition) {
		final LinkedList<T> result = new LinkedList<>();
		for (final T element : source) {
			if (condition.apply(element)) {
				result.add(element);
			}
		}
		return result;
	}

	/**
	 * Erstellt eine neue Liste, deren Elemente durch Anwendung der angegebenen
	 * Transformation auf die Elemente der Originalliste erstellt werden.
	 *
	 * @param <F>
	 *            der Typ für die Elemente der Originalliste
	 * @param <T>
	 *            der Typ für die Elemente der Zielliste
	 * @param original
	 *            die Originalliste
	 * @param transformation
	 *            die Transformation
	 *
	 * @return die Liste mit den transformierten Elementen
	 */
	public static <F, T> List<T> forEach(final List<F> original,
			final Function<F, T> transformation) {
		final LinkedList<T> result = new LinkedList<>();
		for (final F element : original) {
			result.add(transformation.apply(element));
		}
		return result;
	}

	/**
	 * Ermittelt das erste Element der angegebenen Liste. Falls die Liste leer
	 * ist, wird <code>null</code> zurückgeliefert.
	 *
	 * @param <T>
	 *            der Typ für die Elemente der Liste
	 * @param list
	 *            die Liste
	 *
	 * @return das erste Element dieser Liste oder <code>null</code>
	 */
	public static <T> T head(final List<T> list) {
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * Ermittelt das letzte Element der angegebenen Liste. Falls die Liste leer
	 * ist, wird <code>null</code> zurückgeliefert.
	 *
	 * @param <T>
	 *            der Typ für die Elemente der Liste
	 * @param list
	 *            die Liste
	 *
	 * @return das letzte Element dieser Liste oder <code>null</code>
	 */
	public static <T> T last(final List<T> list) {
		final int index = list.size() - 1;
		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	/**
	 * Ermittelt die Liste hinter dem ersten Element der angegebenen Liste.
	 * Falls diese Liste leer ist, wird <code>null</code> zurückgeliefert. Falls
	 * sie nur ein Element enthält, so wird eine leere Liste zurückgegeben.
	 *
	 * @param <T>
	 *            der Typ für die Elemente der Liste
	 * @param list
	 *            die Liste
	 *
	 * @return die Liste hinter dem ersten Element oder <code>null</code>
	 */
	public static <T> List<T> tail(final List<T> list) {
		if (list.isEmpty()) {
			return null;
		}
		return list.subList(1, list.size());
	}

	/**
	 * Erstellt eine neue Liste, die alle Elemente der Kollektion enthält.
	 *
	 * @param <T>
	 *            der Typ für die Elemente der Liste
	 * @param collection
	 *            die Kollektion
	 *
	 * @return die Menge
	 */
	public static <T> List<T> toList(final Collection<T> collection) {
		final List<T> result = new LinkedList<>();
		result.addAll(collection);
		return result;
	}

	/**
	 * Erstellt eine neue Menge, die alle Elemente der Kollektion enthält.
	 *
	 * @param <T>
	 *            der Typ für die Elemente der Kollektion
	 * @param collection
	 *            die Kollektion
	 *
	 * @return die Menge
	 */
	public static <T> Set<T> toSet(final Collection<T> collection) {
		final HashSet<T> result = new HashSet<>();
		result.addAll(collection);
		return result;
	}
}
