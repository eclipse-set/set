/**
 * Copyright (c) 2024 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.set.basis.files;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.set.model.planpro.PlanPro.util.PlanProValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Extended EMF validator for PlanPro models, which adds custom pattern error
 * descriptions from the model
 */
public class ExtendedPlanProValidator extends PlanProValidator {
	private static final ExtendedPlanProValidator EXTENDED_INSTANCE = new ExtendedPlanProValidator();

	/**
	 * Registers this validator for the planpro package
	 */
	public static void registerValidator() {
		EValidator.Registry.INSTANCE.put(PlanProPackage.eINSTANCE,
				(EValidator.Descriptor) () -> ExtendedPlanProValidator.EXTENDED_INSTANCE);
	}

	private static boolean nullSafeEquals(final String string,
			final String other) {
		if (string == null || other == null) {
			return string == other; // NOSONAR this is intended to compare by
									// reference
		}
		return string.equals(other);
	}

	private static String getEMFAnnotationValue(
			final EStructuralFeature feature, final String source,
			final String key) {
		final EList<EAnnotation> annotations = feature.getEAnnotations();
		for (final EAnnotation annotation : annotations) {
			if (nullSafeEquals(annotation.getSource(), source)) {
				return annotation.getDetails().get(key);
			}
		}
		return null;
	}

	private static String getPatternDescription(
			final DiagnosticChain diagnostics)
			throws ParserConfigurationException, SAXException, IOException {
		// find annotation value
		if (!(diagnostics instanceof BasicDiagnostic)) {
			return null;
		}
		final BasicDiagnostic basicDiagnostic = (BasicDiagnostic) diagnostics;
		final Object object = basicDiagnostic.getData().get(0);
		if (!(object instanceof EObject)) {
			return null;
		}
		final EObject objectWithWert = (EObject) object;
		final EStructuralFeature featureWithPatternDescription = objectWithWert
				.eContainingFeature();
		final String value = getEMFAnnotationValue(
				featureWithPatternDescription, null, "appinfo"); //$NON-NLS-1$
		if (value == null) {
			return null;
		}

		// parse the value
		final DocumentBuilder documentBuilder = DocumentBuilderFactory
				.newInstance().newDocumentBuilder();
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(value);
		final ByteArrayInputStream input = new ByteArrayInputStream(
				stringBuilder.toString()
						.getBytes(StandardCharsets.UTF_8.name()));
		final Document document = documentBuilder.parse(input);
		final Element root = document.getDocumentElement();

		// we use the first variant if present
		final NodeList descriptionNodesVariant = root
				.getElementsByTagName("Patternbeschreibung_Abweichend"); //$NON-NLS-1$
		if (descriptionNodesVariant.getLength() > 0) {
			return descriptionNodesVariant.item(0).getTextContent();
		}

		// if we have no variant, we use the first pattern description if
		// present
		final NodeList descriptionNodes = root
				.getElementsByTagName("Patternbeschreibung"); //$NON-NLS-1$
		if (descriptionNodes.getLength() > 0) {
			return descriptionNodes.item(0).getTextContent();
		}

		return null;
	}

	private static boolean canReport(final DiagnosticChain diagnostics) {
		try {
			return getPatternDescription(diagnostics) != null;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void reportPlanProDataValuePatternViolation(
			final DiagnosticChain diagnostics) {
		try {
			diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
					EObjectValidator.DIAGNOSTIC_SOURCE,
					EObjectValidator.DATA_VALUE__MATCHES_PATTERN,
					getPatternDescription(diagnostics), new Object[] {}));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void reportDataValuePatternViolation(final EDataType eDataType,
			final Object value, final PatternMatcher[] patterns,
			final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (canReport(diagnostics)) {
			reportPlanProDataValuePatternViolation(diagnostics);
			return;
		}
		super.reportDataValuePatternViolation(eDataType, value, patterns,
				diagnostics, context);
	}
}
