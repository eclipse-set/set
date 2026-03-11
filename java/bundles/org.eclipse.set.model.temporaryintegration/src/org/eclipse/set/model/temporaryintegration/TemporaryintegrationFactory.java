/**
 * Copyright (c) 2026 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.set.model.temporaryintegration;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage
 * @generated
 */
public interface TemporaryintegrationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TemporaryintegrationFactory eINSTANCE = org.eclipse.set.model.temporaryintegration.impl.TemporaryintegrationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Temporary Integration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Temporary Integration</em>'.
	 * @generated
	 */
	TemporaryIntegration createTemporaryIntegration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TemporaryintegrationPackage getTemporaryintegrationPackage();

} //TemporaryintegrationFactory
