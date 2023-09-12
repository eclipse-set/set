/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.set.basis.PlanProSchemaDir;
import org.eclipse.set.basis.ResourceLoader;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.validation.CustomValidator;
import org.eclipse.set.core.services.validation.ValidationService;
import org.eclipse.set.model.model11001.PlanPro.DocumentRoot;
import org.eclipse.set.toolboxmodel.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

import com.google.common.collect.Lists;

/**
 * Implementation of {@link ValidationService}.
 * 
 * @author Schaefer
 */
public class ValidationServiceImpl implements ValidationService {

	private final List<CustomValidator> customValidators = Lists
			.newLinkedList();

	/**
	 * @param validator
	 *            the custom validator
	 */
	public void addCustomValidator(final CustomValidator validator) {
		customValidators.add(validator);
	}

	@Override
	public ValidationResult validateSource(final ValidationResult result,
			final ToolboxFile toolboxFile) {
		final Class<? extends EObject> sourceClass = result
				.getValidatedSourceClass();
		if (sourceClass == null) {
			return result;
		}
		if (sourceClass.isAssignableFrom(PlanPro_Schnittstelle.class)) {
			validateSchnittstelle(toolboxFile, result);
		} else if (sourceClass.isAssignableFrom(PlanPro_Layoutinfo.class)) {
			validateLayoutinfo(toolboxFile, result);
		}
		return result;
	}

	private void validateSchnittstelle(final ToolboxFile toolboxFile,
			final ValidationResult result) {
		xsdValidation(toolboxFile.getModelPath(), result);
		final DocumentRoot planProSourceModel = toolboxFile
				.getPlanProSourceModel();
		if (planProSourceModel != null) {
			emfValidation(planProSourceModel.getPlanProSchnittstelle(), result);
		}
		customValidation(toolboxFile, result);
	}

	private void validateLayoutinfo(final ToolboxFile toolboxFile,
			final ValidationResult result) {
		xsdValidation(toolboxFile.getLayoutPath(), result);
		final org.eclipse.set.model.model11001.Layoutinformationen.DocumentRoot layoutSourceModel = toolboxFile
				.getLayoutSourceModel();
		if (layoutSourceModel != null) {
			emfValidation(layoutSourceModel.getPlanProLayoutinfo(), result);
		}
		customValidation(toolboxFile, result);
	}

	@Override
	public <T extends EObject> T checkLoad(final ToolboxFile toolboxFile,
			final ResourceLoader resourceLoader,
			final Function<Resource, T> provider,
			final ValidationResult result) {
		try {
			final Resource resource = resourceLoader
					.load(toolboxFile.getModelPath());
			return provider.apply(resource);
		} catch (final Exception e) {
			result.addIoError(e);
		}
		return null;
	}

	@Override
	public ValidationResult customValidation(final ToolboxFile toolboxFile,
			final ValidationResult result) {
		customValidators
				.forEach(validator -> validator.validate(toolboxFile, result));
		return result;
	}

	@Override
	public <T extends EObject> ValidationResult emfValidation(final T object,
			final ValidationResult result) {
		if (object != null) {
			final Diagnostic diagnostic = Diagnostician.INSTANCE
					.validate(object);
			result.put(diagnostic);
		}
		return result;
	}

	/**
	 * @param validator
	 *            the custom validator
	 */
	public void removeCustomValidator(final CustomValidator validator) {
		customValidators.remove(validator);
	}

	@Override
	public ValidationResult xsdValidation(final Path sourcePath,
			final ValidationResult result) {
		if (sourcePath == null || !sourcePath.toFile().exists()) {
			return result;
		}

		final SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, ""); //$NON-NLS-1$
			factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); //$NON-NLS-1$
		} catch (SAXNotRecognizedException | SAXNotSupportedException e) {
			// Ignore failures
		}
		factory.setResourceResolver(PlanProSchemaDir.getResourceResolver());
		try {
			final Schema schema = factory
					.newSchema(PlanProSchemaDir.getSchemas());
			final Validator validator = schema.newValidator();
			validator.setErrorHandler(new ErrorHandler() {
				@Override
				public void error(final SAXParseException exception)
						throws SAXException {
					result.addXsdError(exception);
				}

				@Override
				public void fatalError(final SAXParseException exception)
						throws SAXException {
					throw exception;
				}

				@Override
				public void warning(final SAXParseException exception)
						throws SAXException {
					result.addXsdWarning(exception);
				}
			});
			validator.validate(new StreamSource(sourcePath.toFile()));
			result.setPassedXsdValidation(true);
		} catch (final SAXParseException e) {
			result.addXsdError(e);
		} catch (final IOException | SAXException e) {
			result.addIoError(e);
		}
		return result;
	}
}
