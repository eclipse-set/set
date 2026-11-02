/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.simplemerge;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.simplemerge.SimplemergeFactory
 * @model kind="package"
 * @generated
 */
public interface SimplemergePackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "simplemerge";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2018-05-30:planpro/tsm";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "tsm";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	SimplemergePackage eINSTANCE = org.eclipse.set.model.simplemerge.impl.SimplemergePackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.simplemerge.impl.SComparisonImpl
	 * <em>SComparison</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.set.model.simplemerge.impl.SComparisonImpl
	 * @see org.eclipse.set.model.simplemerge.impl.SimplemergePackageImpl#getSComparison()
	 * @generated
	 */
	int SCOMPARISON = 0;

	/**
	 * The feature id for the '<em><b>Matches</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCOMPARISON__MATCHES = 0;

	/**
	 * The number of structural features of the '<em>SComparison</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCOMPARISON_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>SComparison</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCOMPARISON_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.simplemerge.impl.SMatchImpl
	 * <em>SMatch</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.simplemerge.impl.SMatchImpl
	 * @see org.eclipse.set.model.simplemerge.impl.SimplemergePackageImpl#getSMatch()
	 * @generated
	 */
	int SMATCH = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SMATCH__ID = 0;

	/**
	 * The feature id for the '<em><b>Guid Primary</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SMATCH__GUID_PRIMARY = 1;

	/**
	 * The feature id for the '<em><b>Guid Secondary</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SMATCH__GUID_SECONDARY = 2;

	/**
	 * The feature id for the '<em><b>Resolution</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SMATCH__RESOLUTION = 3;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SMATCH__ELEMENT_TYPE = 4;

	/**
	 * The number of structural features of the '<em>SMatch</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SMATCH_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>SMatch</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SMATCH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.simplemerge.Resolution
	 * <em>Resolution</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.simplemerge.Resolution
	 * @see org.eclipse.set.model.simplemerge.impl.SimplemergePackageImpl#getResolution()
	 * @generated
	 */
	int RESOLUTION = 2;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.simplemerge.SComparison
	 * <em>SComparison</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>SComparison</em>'.
	 * @see org.eclipse.set.model.simplemerge.SComparison
	 * @generated
	 */
	EClass getSComparison();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.simplemerge.SComparison#getMatches
	 * <em>Matches</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Matches</em>'.
	 * @see org.eclipse.set.model.simplemerge.SComparison#getMatches()
	 * @see #getSComparison()
	 * @generated
	 */
	EReference getSComparison_Matches();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.simplemerge.SMatch <em>SMatch</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>SMatch</em>'.
	 * @see org.eclipse.set.model.simplemerge.SMatch
	 * @generated
	 */
	EClass getSMatch();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.simplemerge.SMatch#getId <em>Id</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.set.model.simplemerge.SMatch#getId()
	 * @see #getSMatch()
	 * @generated
	 */
	EAttribute getSMatch_Id();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.simplemerge.SMatch#getGuidPrimary <em>Guid
	 * Primary</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Guid Primary</em>'.
	 * @see org.eclipse.set.model.simplemerge.SMatch#getGuidPrimary()
	 * @see #getSMatch()
	 * @generated
	 */
	EAttribute getSMatch_GuidPrimary();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.simplemerge.SMatch#getGuidSecondary
	 * <em>Guid Secondary</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Guid Secondary</em>'.
	 * @see org.eclipse.set.model.simplemerge.SMatch#getGuidSecondary()
	 * @see #getSMatch()
	 * @generated
	 */
	EAttribute getSMatch_GuidSecondary();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.simplemerge.SMatch#getResolution
	 * <em>Resolution</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Resolution</em>'.
	 * @see org.eclipse.set.model.simplemerge.SMatch#getResolution()
	 * @see #getSMatch()
	 * @generated
	 */
	EAttribute getSMatch_Resolution();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.simplemerge.SMatch#getElementType
	 * <em>Element Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Element Type</em>'.
	 * @see org.eclipse.set.model.simplemerge.SMatch#getElementType()
	 * @see #getSMatch()
	 * @generated
	 */
	EAttribute getSMatch_ElementType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.simplemerge.Resolution
	 * <em>Resolution</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Resolution</em>'.
	 * @see org.eclipse.set.model.simplemerge.Resolution
	 * @generated
	 */
	EEnum getResolution();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SimplemergeFactory getSimplemergeFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.simplemerge.impl.SComparisonImpl
		 * <em>SComparison</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.simplemerge.impl.SComparisonImpl
		 * @see org.eclipse.set.model.simplemerge.impl.SimplemergePackageImpl#getSComparison()
		 * @generated
		 */
		EClass SCOMPARISON = eINSTANCE.getSComparison();

		/**
		 * The meta object literal for the '<em><b>Matches</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCOMPARISON__MATCHES = eINSTANCE.getSComparison_Matches();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.simplemerge.impl.SMatchImpl
		 * <em>SMatch</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.simplemerge.impl.SMatchImpl
		 * @see org.eclipse.set.model.simplemerge.impl.SimplemergePackageImpl#getSMatch()
		 * @generated
		 */
		EClass SMATCH = eINSTANCE.getSMatch();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SMATCH__ID = eINSTANCE.getSMatch_Id();

		/**
		 * The meta object literal for the '<em><b>Guid Primary</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SMATCH__GUID_PRIMARY = eINSTANCE.getSMatch_GuidPrimary();

		/**
		 * The meta object literal for the '<em><b>Guid Secondary</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SMATCH__GUID_SECONDARY = eINSTANCE.getSMatch_GuidSecondary();

		/**
		 * The meta object literal for the '<em><b>Resolution</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SMATCH__RESOLUTION = eINSTANCE.getSMatch_Resolution();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SMATCH__ELEMENT_TYPE = eINSTANCE.getSMatch_ElementType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.simplemerge.Resolution
		 * <em>Resolution</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.simplemerge.Resolution
		 * @see org.eclipse.set.model.simplemerge.impl.SimplemergePackageImpl#getResolution()
		 * @generated
		 */
		EEnum RESOLUTION = eINSTANCE.getResolution();

	}

} // SimplemergePackage
