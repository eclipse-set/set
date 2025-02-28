/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage
 * @generated
 */
public interface IntegrationviewFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	IntegrationviewFactory eINSTANCE = org.eclipse.set.model.integrationview.impl.IntegrationviewFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Integration View</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Integration View</em>'.
	 * @generated
	 */
	IntegrationView createIntegrationView();

	/**
	 * Returns a new object of class '<em>Object Quantity</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Object Quantity</em>'.
	 * @generated
	 */
	ObjectQuantity createObjectQuantity();

	/**
	 * Returns a new object of class '<em>Conflict</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Conflict</em>'.
	 * @generated
	 */
	Conflict createConflict();

	/**
	 * Returns a new object of class '<em>Details</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Details</em>'.
	 * @generated
	 */
	Details createDetails();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	IntegrationviewPackage getIntegrationviewPackage();

} // IntegrationviewFactory
