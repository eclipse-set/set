/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.service;

import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;

import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.feature.plazmodel.check.PlazCheck;
import org.eclipse.set.model.plazmodel.PlazReport;
import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.model.validationreport.ValidationSeverity;

/**
 * Service for running PlaZ model checks
 * 
 * @author Stuecker
 *
 */
public interface PlazModelService {
	/**
	 * Order of severity
	 */
	public static List<ValidationSeverity> severityOrder = List.of(
			ValidationSeverity.ERROR, ValidationSeverity.WARNING,
			ValidationSeverity.SUCCESS);

	/**
	 * @param modelSession
	 *            the model session
	 * @return a report containing issues found by PlaZ Model
	 */
	public PlazReport runPlazModel(final IModelSession modelSession);

	/**
	 * Run determin check
	 * 
	 * @param <T>
	 *            check class
	 * 
	 * @param modelSession
	 *            the model session
	 * @param checkClass
	 *            the check class should be run
	 * @return a report containing issue found by this check
	 */
	public <T extends PlazCheck> PlazReport runPlazModel(
			final IModelSession modelSession, Class<T> checkClass);

	/**
	 * Sort problem by severity, type and line number
	 * 
	 * @param problems
	 *            the problems
	 */
	public static void sortProblems(final List<ValidationProblem> problems) {
		final Comparator<ValidationProblem> comparator = Comparator
				.comparingInt(
						(ToIntFunction<ValidationProblem>) t -> severityOrder
								.indexOf(t.getSeverity()))
				.thenComparing(ValidationProblem::getType)
				.thenComparingInt(ValidationProblem::getLineNumber)
				.thenComparing(t -> getNullableObject(t,
						ValidationProblem::getObjectArt).orElse("")) //$NON-NLS-1$
				.thenComparing(t -> getNullableObject(t,
						ValidationProblem::getAttributeName).orElse("")) //$NON-NLS-1$
				.thenComparing(t -> getNullableObject(t,
						problem -> problem.getObjectState().getLiteral())
								.orElse("")) //$NON-NLS-1$
				.thenComparing(
						t -> getNullableObject(t, ValidationProblem::getMessage)
								.orElse("")); //$NON-NLS-1$
		problems.sort(comparator);
	}

	/**
	 * Sort and registerd id of problem by severity, type and line number
	 * 
	 * @param problems
	 *            the problems
	 */
	public static void sortAndIndexedProblems(
			final List<ValidationProblem> problems) {
		sortProblems(problems);
		int i = 1;
		for (final ValidationProblem problem : problems) {
			problem.setId(i);
			i++;
		}
	}

}
