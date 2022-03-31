/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.basis.files;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl;

/**
 * An {@link XMLSave} implementation for special PlanPro requirements.
 * 
 * @author Schaefer
 */
public class PlanProXmlSave extends XMLSaveImpl {

	private static String getAssignment(final String key, final String value) {
		return " " + key + "=\"" + value + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private final Map<String, String> additionalDeclarations;

	/**
	 * Create a PlanProXmlSave instance.
	 * 
	 * @param helper
	 *            the XML helper
	 * @param additionalDeclarations
	 *            additional declarations
	 */
	public PlanProXmlSave(final XMLHelper helper,
			final Map<String, String> additionalDeclarations) {
		super(helper);
		this.additionalDeclarations = additionalDeclarations;
	}

	/**
	 * @return the additionalDeclarations
	 */
	public Map<String, String> getAdditionalDeclarations() {
		return additionalDeclarations;
	}

	@Override
	public void traverse(final List<? extends EObject> contents) {
		doc.add("<?xml" + getAssignments() + "?>"); //$NON-NLS-1$ //$NON-NLS-2$
		doc.addLine();
		declareXML = false;
		super.traverse(contents);
	}

	private String getAssignments() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getAssignment("version", xmlVersion)); //$NON-NLS-1$
		builder.append(getAssignment("encoding", encoding)); //$NON-NLS-1$
		additionalDeclarations.forEach(
				(key, value) -> builder.append(getAssignment(key, value)));
		return builder.toString();
	}
}
