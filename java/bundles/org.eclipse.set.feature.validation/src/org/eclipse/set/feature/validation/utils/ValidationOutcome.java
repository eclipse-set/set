/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.validation.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.FileValidateState;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;

/**
 * Validation result outcome util
 * 
 * @author Truong
 */
public class ValidationOutcome {
	/**
	 * Get validate staus of the open file
	 * 
	 * @param results
	 *            the list of validation result
	 * @return {@link FileValidateState}
	 */
	public static FileValidateState getFileValidateState(
			final List<ValidationResult> results) {
		final Outcome validationOutcome = getValidationsOutcome(results,
				ValidationResult::getOutcome);
		if (validationOutcome == Outcome.VALID
				|| validationOutcome == Outcome.NOT_SUPPORTED) {
			return FileValidateState.VALID;
		}

		final Outcome xsdOutcome = getValidationsOutcome(results,
				ValidationResult::getXsdOutcome);
		final Outcome emfOutcome = getValidationsOutcome(results,
				ValidationResult::getEmfOutcome);
		if ((xsdOutcome == Outcome.VALID || xsdOutcome == Outcome.NOT_SUPPORTED)
				&& (emfOutcome == Outcome.VALID
						|| emfOutcome == Outcome.NOT_SUPPORTED)) {
			return FileValidateState.INCOMPLETE;
		}
		return FileValidateState.INVALID;
	}

	/**
	 * Get outcome result of validations
	 * 
	 * @param results
	 *            the list of validation result
	 * 
	 * @param outcome
	 *            outcome type
	 * @return {@link Outcome}
	 */
	public static Outcome getValidationsOutcome(
			final List<ValidationResult> results,
			final Function<ValidationResult, Outcome> outcome) {
		final Stream<ValidationResult> resultsStream = results.stream()
				.filter(Objects::nonNull);
		if (resultsStream
				.anyMatch(result -> outcome.apply(result) == Outcome.INVALID)) {
			return Outcome.INVALID;
		}
		return Outcome.VALID;
	}
}
