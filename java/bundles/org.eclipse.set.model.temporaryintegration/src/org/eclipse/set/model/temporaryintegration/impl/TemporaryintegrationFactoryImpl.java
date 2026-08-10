/**
 * Copyright (c) 2026 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.set.model.temporaryintegration.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.set.model.temporaryintegration.*;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TemporaryintegrationFactoryImpl extends EFactoryImpl
		implements TemporaryintegrationFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static TemporaryintegrationFactory init() {
		try {
			TemporaryintegrationFactory theTemporaryintegrationFactory = (TemporaryintegrationFactory) EPackage.Registry.INSTANCE
					.getEFactory(TemporaryintegrationPackage.eNS_URI);
			if (theTemporaryintegrationFactory != null) {
				return theTemporaryintegrationFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TemporaryintegrationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public TemporaryintegrationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION:
				return createTemporaryIntegration();
			default:
				throw new IllegalArgumentException("The class '"
						+ eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TemporaryIntegration createTemporaryIntegration() {
		TemporaryIntegrationImpl temporaryIntegration = new TemporaryIntegrationImpl();
		return temporaryIntegration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TemporaryintegrationPackage getTemporaryintegrationPackage() {
		return (TemporaryintegrationPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TemporaryintegrationPackage getPackage() {
		return TemporaryintegrationPackage.eINSTANCE;
	}

} // TemporaryintegrationFactoryImpl
