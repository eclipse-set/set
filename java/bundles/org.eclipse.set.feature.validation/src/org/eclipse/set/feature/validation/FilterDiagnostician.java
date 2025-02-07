/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.Diagnostician;

/**
 * Diagnostician implementation that also supports filtering diagnostics
 */
public class FilterDiagnostician extends Diagnostician {

	private final List<Predicate<? super Diagnostic>> filters = new ArrayList<>();

	/**
	 * @param filter
	 *            a filter for diagnostics
	 */
	public void addFilter(final Predicate<? super Diagnostic> filter) {
		filters.add(filter);
	}

	private class DiagnosticCollector implements DiagnosticChain {
		private final List<Diagnostic> diagnostics = new ArrayList<>();

		@Override
		public void add(final Diagnostic diagnostic) {
			diagnostics.add(diagnostic);
		}

		@Override
		public void addAll(final Diagnostic diagnostic) {
			for (final Diagnostic child : diagnostic.getChildren()) {
				add(child);
			}
		}

		@Override
		public void merge(final Diagnostic diagnostic) {
			if (diagnostic.getChildren().isEmpty()) {
				add(diagnostic);
			} else {
				addAll(diagnostic);
			}
		}

		public List<Diagnostic> getDiagnostics() {
			return diagnostics;
		}

	}

	@Override
	protected boolean doValidate(final EValidator eValidator,
			final EClass eClass, final EObject eObject,
			final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		final DiagnosticCollector filter = new DiagnosticCollector();
		final boolean result = eValidator.validate(eClass, eObject, filter,
				context);
		filter.getDiagnostics()
				.stream()
				.filter(diagnostic -> filters.stream()
						.allMatch(c -> c.test(diagnostic)))
				.forEach(diagnostics::add);
		return result;
	}

}
