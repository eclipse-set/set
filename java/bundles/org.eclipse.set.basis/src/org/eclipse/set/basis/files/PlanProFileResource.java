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

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

/**
 * Adapted resource implementation for the EMF-XML Resources
 */
public class PlanProFileResource extends XMLResourceImpl {

	private String standalone;
	private XMLHelper xmlHelper;

	/**
	 * Creates an instance of the resource. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param uri
	 *            the URI of the new resource.
	 * @param xmlHelper
	 *            the {@link XMLHelper}
	 * @generated
	 */
	public PlanProFileResource(final URI uri, final XMLHelper xmlHelper) {
		this(uri);
		this.xmlHelper = xmlHelper;
	}

	/**
	 * Creates an instance of the resource. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param uri
	 *            the URI of the new resource.
	 * @generated
	 */
	public PlanProFileResource(final URI uri) {
		super(uri);
		this.xmlHelper = null;
		getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
				Boolean.TRUE);
		getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
				Boolean.TRUE);
		getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION,
				Boolean.TRUE);
		getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING,
				StandardCharsets.UTF_8.name());

		getDefaultLoadOptions().put(
				XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		getDefaultSaveOptions().put(
				XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER,
				Boolean.TRUE);
		getDefaultLoadOptions().put(
				XMLResource.OPTION_USE_PACKAGE_NS_URI_AS_LOCATION,
				Boolean.FALSE);
	}

	static final String STANDALONE = "standalone"; //$NON-NLS-1$

	@Override
	protected XMLSave createXMLSave() {
		if (standalone != null) {
			return new PlanProXmlSave(createXMLHelper(),
					Map.of(STANDALONE, standalone));

		}
		return super.createXMLSave();
	}

	/**
	 * @param value
	 *            the new standalone flag
	 */
	public void setStandalone(final String value) {
		standalone = value;
	}

	@Override
	protected XMLHelper createXMLHelper() {
		if (xmlHelper != null) {
			return xmlHelper;
		}
		return super.createXMLHelper();
	}
}
