/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.validationreport.ValidationreportPackage
 * @generated
 */
public interface ValidationreportFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ValidationreportFactory eINSTANCE = org.eclipse.set.model.validationreport.impl.ValidationreportFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Validation Report</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Validation Report</em>'.
	 * @generated
	 */
	ValidationReport createValidationReport();

	/**
	 * Returns a new object of class '<em>Validation Problem</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Validation Problem</em>'.
	 * @generated
	 */
	ValidationProblem createValidationProblem();

	/**
	 * Returns a new object of class '<em>Version Info</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Version Info</em>'.
	 * @generated
	 */
	VersionInfo createVersionInfo();

	/**
	 * Returns a new object of class '<em>File Info</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>File Info</em>'.
	 * @generated
	 */
	FileInfo createFileInfo();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	ValidationreportPackage getValidationreportPackage();

} // ValidationreportFactory
