/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.basis.exceptions.CustomValidationProblem;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.xml.sax.SAXParseException;

import com.google.common.collect.Lists;

/**
 * Represents a validation result.
 * 
 * @author Schaefer
 */
public class ValidationResult {

	/**
	 * 
	 */
	public static enum Outcome {
		/**
		 * The model is not valid
		 */
		INVALID,

		/**
		 * Validation is not supported for the model.
		 */
		NOT_SUPPORTED,

		/**
		 * The mode is valid.
		 */
		VALID
	}

	private final Class<? extends EObject> validatedSourceClass;

	/**
	 * @return class of validated object
	 */
	public Class<? extends EObject> getValidatedSourceClass() {
		return validatedSourceClass;
	}

	/**
	 * Constructor
	 * 
	 * @param validatedSourceClass
	 *            class of validated object
	 */
	public ValidationResult(
			final Class<? extends EObject> validatedSourceClass) {
		this.validatedSourceClass = validatedSourceClass;
	}

	private final List<CustomValidationProblem> customProblems = Lists
			.newLinkedList();
	private Diagnostic diagnostic;
	private final List<Exception> ioErrors = Lists.newLinkedList();
	private final List<ParserConfigurationException> parseError = new LinkedList<>();
	private boolean passedXsdValidation = false;
	private final List<SAXParseException> saxErrors = new LinkedList<>();
	private final List<SAXParseException> saxWarnings = new LinkedList<>();
	private boolean validationSupported = true;

	/**
	 * @param customProblem
	 *            the custom problem
	 */
	public void addCustomProblem(final CustomValidationProblem customProblem) {
		customProblems.add(customProblem);
	}

	/**
	 * @param e
	 *            the IO exception
	 */
	public void addIoError(final Exception e) {
		ioErrors.add(e);
	}

	/**
	 * @param exception
	 *            the Parser exception
	 */
	public void addParserException(
			final ParserConfigurationException exception) {
		parseError.add(exception);
	}

	/**
	 * @param exception
	 *            the SAX parse exception
	 */
	public void addXsdError(final SAXParseException exception) {
		saxErrors.add(exception);
	}

	/**
	 * @param exception
	 *            the SAX parse exception
	 */
	public void addXsdWarning(final SAXParseException exception) {
		saxWarnings.add(exception);
	}

	/**
	 * @return whether this result indicates passed custom checks
	 */
	public Outcome getCustomOutcome() {
		if (!validationSupported) {
			return Outcome.NOT_SUPPORTED;
		}
		final Stream<CustomValidationProblem> errors = customProblems.stream()
				.filter(x -> x.getSeverity() != ValidationSeverity.SUCCESS);
		if (errors.toList().isEmpty()) {
			return Outcome.VALID;
		}
		return Outcome.INVALID;
	}

	/**
	 * @return the custom problems
	 */
	public List<CustomValidationProblem> getCustomProblems() {
		return customProblems;
	}

	/**
	 * @return whether this result indicates a valid EMF model
	 */
	public Outcome getEmfOutcome() {
		if (!validationSupported) {
			return Outcome.NOT_SUPPORTED;
		}
		if (diagnostic != null && ioErrors.isEmpty()
				&& diagnostic.getSeverity() == Diagnostic.OK) {
			return Outcome.VALID;
		}
		return Outcome.INVALID;
	}

	/**
	 * @return the IO errors
	 */
	public List<Exception> getIoErrors() {
		return ioErrors;
	}

	/**
	 * @return whether this result indicates a valid PlanPro file
	 */
	public Outcome getOutcome() {
		if (!validationSupported) {
			return Outcome.NOT_SUPPORTED;
		}
		if (getEmfOutcome() == Outcome.VALID && getXsdOutcome() == Outcome.VALID
				&& getCustomOutcome() == Outcome.VALID) {
			return Outcome.VALID;
		}
		return Outcome.INVALID;
	}

	/**
	 * @return the ParserConfigurationException
	 */
	public List<ParserConfigurationException> getParserError() {
		return parseError;
	}

	/**
	 * @return the SAX errors
	 */
	public List<SAXParseException> getXsdErrors() {
		return saxErrors;
	}

	/**
	 * @return whether this result indicates a valid XSD file
	 */
	public Outcome getXsdOutcome() {
		if (!validationSupported) {
			return Outcome.NOT_SUPPORTED;
		}
		if (passedXsdValidation && ioErrors.isEmpty() && saxErrors.isEmpty()
				&& saxWarnings.isEmpty() && parseError.isEmpty()) {
			return Outcome.VALID;
		}
		return Outcome.INVALID;
	}

	/**
	 * @return the SAX warnings
	 */
	public List<SAXParseException> getXsdWarnings() {
		return saxWarnings;
	}

	/**
	 * @param newDiagnostic
	 *            the new diagnostic
	 */
	public void put(final Diagnostic newDiagnostic) {
		this.diagnostic = newDiagnostic;
	}

	/**
	 * @param value
	 *            the new passed XSD validation value
	 */
	public void setPassedXsdValidation(final boolean value) {
		passedXsdValidation = value;
	}

	/**
	 * @param value
	 *            the new support value
	 */
	public void setValidationSupported(final boolean value) {
		this.validationSupported = value;
	}
}
