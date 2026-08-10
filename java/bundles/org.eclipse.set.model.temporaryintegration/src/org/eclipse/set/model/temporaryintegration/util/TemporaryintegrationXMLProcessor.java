/**
 * Copyright (c) 2026 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.set.model.temporaryintegration.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class TemporaryintegrationXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TemporaryintegrationXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		TemporaryintegrationPackage.eINSTANCE.eClass();
	}

	/**
	 * Register for "*" and "xml" file extensions the
	 * TemporaryintegrationResourceFactoryImpl factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION,
					new TemporaryintegrationResourceFactoryImpl());
			registrations.put(STAR_EXTENSION,
					new TemporaryintegrationResourceFactoryImpl());
		}
		return registrations;
	}

} // TemporaryintegrationXMLProcessor
