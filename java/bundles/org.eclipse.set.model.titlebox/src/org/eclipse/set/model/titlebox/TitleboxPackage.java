/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.titlebox.TitleboxFactory
 * @model kind="package"
 * @generated
 */
public interface TitleboxPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "titlebox";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2017-03-23:planpro/tbm";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tbm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TitleboxPackage eINSTANCE = org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.titlebox.impl.TitleboxImpl <em>Titlebox</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.titlebox.impl.TitleboxImpl
	 * @see org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl#getTitlebox()
	 * @generated
	 */
	int TITLEBOX = 0;

	/**
	 * The feature id for the '<em><b>Field</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLEBOX__FIELD = 0;

	/**
	 * The number of structural features of the '<em>Titlebox</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLEBOX_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Titlebox</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLEBOX_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.titlebox.Titlebox <em>Titlebox</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Titlebox</em>'.
	 * @see org.eclipse.set.model.titlebox.Titlebox
	 * @generated
	 */
	EClass getTitlebox();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.set.model.titlebox.Titlebox#getFieldList <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Field</em>'.
	 * @see org.eclipse.set.model.titlebox.Titlebox#getFieldList()
	 * @see #getTitlebox()
	 * @generated
	 */
	EAttribute getTitlebox_Field();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TitleboxFactory getTitleboxFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.titlebox.impl.TitleboxImpl <em>Titlebox</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.titlebox.impl.TitleboxImpl
		 * @see org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl#getTitlebox()
		 * @generated
		 */
		EClass TITLEBOX = eINSTANCE.getTitlebox();

		/**
		 * The meta object literal for the '<em><b>Field</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TITLEBOX__FIELD = eINSTANCE.getTitlebox_Field();

	}

} //TitleboxPackage
