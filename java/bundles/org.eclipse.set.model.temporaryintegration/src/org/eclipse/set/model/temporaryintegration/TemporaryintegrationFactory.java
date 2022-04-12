/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
	 * Returns a new object of class '<em>Toolbox Temporary Integration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Toolbox Temporary Integration</em>'.
	 * @generated
	 */
	ToolboxTemporaryIntegration createToolboxTemporaryIntegration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TemporaryintegrationPackage getTemporaryintegrationPackage();

} //TemporaryintegrationFactory
