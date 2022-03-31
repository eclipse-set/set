/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.patternviolation;

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
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator.PatternMatcher;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.scheidtbachmann.planpro.model.modelservice.ReportDataValuePatternViolation;
import org.eclipse.set.utils.emfforms.Annotations;

/**
 * Look up pattern descriptions in the PlanPro model.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true)
public class ReportPatternViolationWithPlanProDescriptions
		implements ReportDataValuePatternViolation {

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
		final String value = Annotations.getValue(featureWithPatternDescription,
				null, "appinfo"); //$NON-NLS-1$
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
				.getElementsByTagName("ppi:Patternbeschreibung_Abweichend"); //$NON-NLS-1$
		if (descriptionNodesVariant.getLength() > 0) {
			return descriptionNodesVariant.item(0).getTextContent();
		}

		// if we have no variant, we use the first pattern description if
		// present
		final NodeList descriptionNodes = root
				.getElementsByTagName("ppi:Patternbeschreibung"); //$NON-NLS-1$
		if (descriptionNodes.getLength() > 0) {
			return descriptionNodes.item(0).getTextContent();
		}

		return null;
	}

	@Override
	public boolean canReport(final DiagnosticChain diagnostics) {
		try {
			return getPatternDescription(diagnostics) != null;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void reportDataValuePatternViolation(final EDataType eDataType,
			final Object value, final PatternMatcher[] patterns,
			final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		try {
			diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
					EObjectValidator.DIAGNOSTIC_SOURCE,
					EObjectValidator.DATA_VALUE__MATCHES_PATTERN,
					getPatternDescription(diagnostics), new Object[] {}));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
