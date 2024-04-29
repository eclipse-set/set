/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.titlebox.TitleboxPackage
 * @generated
 */
public interface TitleboxFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TitleboxFactory eINSTANCE = org.eclipse.set.model.titlebox.impl.TitleboxFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Titlebox</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Titlebox</em>'.
	 * @generated
	 */
	Titlebox createTitlebox();

	/**
	 * Returns a new object of class '<em>Planning Office</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Planning Office</em>'.
	 * @generated
	 */
	PlanningOffice createPlanningOffice();

	/**
	 * Returns a new object of class '<em>String Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Field</em>'.
	 * @generated
	 */
	StringField createStringField();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TitleboxPackage getTitleboxPackage();

} //TitleboxFactory
