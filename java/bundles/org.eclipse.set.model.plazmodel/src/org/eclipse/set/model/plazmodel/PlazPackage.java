/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.plazmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.set.model.plazmodel.PlazFactory
 * @model kind="package"
 * @generated
 */
public interface PlazPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "plazmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2023-26-05:planpro/plaz";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "plaz";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PlazPackage eINSTANCE = org.eclipse.set.model.plazmodel.impl.PlazPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.plazmodel.impl.PlazReportImpl <em>Report</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.plazmodel.impl.PlazReportImpl
	 * @see org.eclipse.set.model.plazmodel.impl.PlazPackageImpl#getPlazReport()
	 * @generated
	 */
	int PLAZ_REPORT = 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_REPORT__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Report</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_REPORT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Report</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_REPORT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.plazmodel.impl.PlazErrorImpl <em>Error</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.plazmodel.impl.PlazErrorImpl
	 * @see org.eclipse.set.model.plazmodel.impl.PlazPackageImpl#getPlazError()
	 * @generated
	 */
	int PLAZ_ERROR = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_ERROR__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_ERROR__MESSAGE = 1;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_ERROR__SEVERITY = 2;

	/**
	 * The feature id for the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_ERROR__OBJECT = 3;

	/**
	 * The number of structural features of the '<em>Error</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_ERROR_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Error</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAZ_ERROR_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.plazmodel.PlazReport <em>Report</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Report</em>'.
	 * @see org.eclipse.set.model.plazmodel.PlazReport
	 * @generated
	 */
	EClass getPlazReport();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.plazmodel.PlazReport#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see org.eclipse.set.model.plazmodel.PlazReport#getEntries()
	 * @see #getPlazReport()
	 * @generated
	 */
	EReference getPlazReport_Entries();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.plazmodel.PlazError <em>Error</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Error</em>'.
	 * @see org.eclipse.set.model.plazmodel.PlazError
	 * @generated
	 */
	EClass getPlazError();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.plazmodel.PlazError#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.set.model.plazmodel.PlazError#getType()
	 * @see #getPlazError()
	 * @generated
	 */
	EAttribute getPlazError_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.plazmodel.PlazError#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.set.model.plazmodel.PlazError#getMessage()
	 * @see #getPlazError()
	 * @generated
	 */
	EAttribute getPlazError_Message();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.plazmodel.PlazError#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see org.eclipse.set.model.plazmodel.PlazError#getSeverity()
	 * @see #getPlazError()
	 * @generated
	 */
	EAttribute getPlazError_Severity();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.set.model.plazmodel.PlazError#getObject <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object</em>'.
	 * @see org.eclipse.set.model.plazmodel.PlazError#getObject()
	 * @see #getPlazError()
	 * @generated
	 */
	EReference getPlazError_Object();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PlazFactory getPlazFactory();

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
		 * The meta object literal for the '{@link org.eclipse.set.model.plazmodel.impl.PlazReportImpl <em>Report</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.plazmodel.impl.PlazReportImpl
		 * @see org.eclipse.set.model.plazmodel.impl.PlazPackageImpl#getPlazReport()
		 * @generated
		 */
		EClass PLAZ_REPORT = eINSTANCE.getPlazReport();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAZ_REPORT__ENTRIES = eINSTANCE.getPlazReport_Entries();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.plazmodel.impl.PlazErrorImpl <em>Error</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.plazmodel.impl.PlazErrorImpl
		 * @see org.eclipse.set.model.plazmodel.impl.PlazPackageImpl#getPlazError()
		 * @generated
		 */
		EClass PLAZ_ERROR = eINSTANCE.getPlazError();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAZ_ERROR__TYPE = eINSTANCE.getPlazError_Type();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAZ_ERROR__MESSAGE = eINSTANCE.getPlazError_Message();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAZ_ERROR__SEVERITY = eINSTANCE.getPlazError_Severity();

		/**
		 * The meta object literal for the '<em><b>Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAZ_ERROR__OBJECT = eINSTANCE.getPlazError_Object();

	}

} //PlazPackage
