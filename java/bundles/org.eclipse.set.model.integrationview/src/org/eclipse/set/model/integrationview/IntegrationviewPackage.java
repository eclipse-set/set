/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.eclipse.set.model.integrationview.IntegrationviewFactory
 * @model kind="package"
 * @generated
 */
public interface IntegrationviewPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "integrationview";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://scheidt-bachmann-st.de/2018-06-29/planpro/integrationview";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "integrationview";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	IntegrationviewPackage eINSTANCE = org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.integrationview.impl.IntegrationViewImpl
	 * <em>Integration View</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.integrationview.impl.IntegrationViewImpl
	 * @see org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl#getIntegrationView()
	 * @generated
	 */
	int INTEGRATION_VIEW = 0;

	/**
	 * The feature id for the '<em><b>Primary Planning</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGRATION_VIEW__PRIMARY_PLANNING = 0;

	/**
	 * The feature id for the '<em><b>Secondary Planning</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGRATION_VIEW__SECONDARY_PLANNING = 1;

	/**
	 * The feature id for the '<em><b>Composite Planning</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGRATION_VIEW__COMPOSITE_PLANNING = 2;

	/**
	 * The feature id for the '<em><b>Objectquantities</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGRATION_VIEW__OBJECTQUANTITIES = 3;

	/**
	 * The feature id for the '<em><b>Conflicts</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGRATION_VIEW__CONFLICTS = 4;

	/**
	 * The feature id for the '<em><b>Integration Directory</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGRATION_VIEW__INTEGRATION_DIRECTORY = 5;

	/**
	 * The number of structural features of the '<em>Integration View</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGRATION_VIEW_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Integration View</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGRATION_VIEW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.integrationview.impl.ObjectQuantityImpl
	 * <em>Object Quantity</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.integrationview.impl.ObjectQuantityImpl
	 * @see org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl#getObjectQuantity()
	 * @generated
	 */
	int OBJECT_QUANTITY = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_QUANTITY__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_QUANTITY__INITIAL = 1;

	/**
	 * The feature id for the '<em><b>Final</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_QUANTITY__FINAL = 2;

	/**
	 * The number of structural features of the '<em>Object Quantity</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_QUANTITY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Object Quantity</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_QUANTITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.integrationview.impl.ConflictImpl
	 * <em>Conflict</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.integrationview.impl.ConflictImpl
	 * @see org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl#getConflict()
	 * @generated
	 */
	int CONFLICT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFLICT__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFLICT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Container</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFLICT__CONTAINER = 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFLICT__VERSION = 3;

	/**
	 * The feature id for the '<em><b>Resolution</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFLICT__RESOLUTION = 4;

	/**
	 * The feature id for the '<em><b>Details</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFLICT__DETAILS = 5;

	/**
	 * The number of structural features of the '<em>Conflict</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFLICT_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Conflict</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFLICT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.integrationview.impl.DetailsImpl
	 * <em>Details</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.integrationview.impl.DetailsImpl
	 * @see org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl#getDetails()
	 * @generated
	 */
	int DETAILS = 3;

	/**
	 * The feature id for the '<em><b>Attribute Path</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DETAILS__ATTRIBUTE_PATH = 0;

	/**
	 * The feature id for the '<em><b>Value Primary Planning</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DETAILS__VALUE_PRIMARY_PLANNING = 1;

	/**
	 * The feature id for the '<em><b>Value Secondary Planning</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DETAILS__VALUE_SECONDARY_PLANNING = 2;

	/**
	 * The number of structural features of the '<em>Details</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DETAILS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Details</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DETAILS_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.integrationview.IntegrationView
	 * <em>Integration View</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Integration View</em>'.
	 * @see org.eclipse.set.model.integrationview.IntegrationView
	 * @generated
	 */
	EClass getIntegrationView();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.IntegrationView#getPrimaryPlanning
	 * <em>Primary Planning</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Primary Planning</em>'.
	 * @see org.eclipse.set.model.integrationview.IntegrationView#getPrimaryPlanning()
	 * @see #getIntegrationView()
	 * @generated
	 */
	EAttribute getIntegrationView_PrimaryPlanning();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.IntegrationView#getSecondaryPlanning
	 * <em>Secondary Planning</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Secondary Planning</em>'.
	 * @see org.eclipse.set.model.integrationview.IntegrationView#getSecondaryPlanning()
	 * @see #getIntegrationView()
	 * @generated
	 */
	EAttribute getIntegrationView_SecondaryPlanning();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.IntegrationView#getCompositePlanning
	 * <em>Composite Planning</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Composite Planning</em>'.
	 * @see org.eclipse.set.model.integrationview.IntegrationView#getCompositePlanning()
	 * @see #getIntegrationView()
	 * @generated
	 */
	EAttribute getIntegrationView_CompositePlanning();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.integrationview.IntegrationView#getObjectquantities
	 * <em>Objectquantities</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Objectquantities</em>'.
	 * @see org.eclipse.set.model.integrationview.IntegrationView#getObjectquantities()
	 * @see #getIntegrationView()
	 * @generated
	 */
	EReference getIntegrationView_Objectquantities();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.integrationview.IntegrationView#getConflicts
	 * <em>Conflicts</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Conflicts</em>'.
	 * @see org.eclipse.set.model.integrationview.IntegrationView#getConflicts()
	 * @see #getIntegrationView()
	 * @generated
	 */
	EReference getIntegrationView_Conflicts();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.IntegrationView#getIntegrationDirectory
	 * <em>Integration Directory</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Integration
	 *         Directory</em>'.
	 * @see org.eclipse.set.model.integrationview.IntegrationView#getIntegrationDirectory()
	 * @see #getIntegrationView()
	 * @generated
	 */
	EAttribute getIntegrationView_IntegrationDirectory();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.integrationview.ObjectQuantity <em>Object
	 * Quantity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Object Quantity</em>'.
	 * @see org.eclipse.set.model.integrationview.ObjectQuantity
	 * @generated
	 */
	EClass getObjectQuantity();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.ObjectQuantity#getSource
	 * <em>Source</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.eclipse.set.model.integrationview.ObjectQuantity#getSource()
	 * @see #getObjectQuantity()
	 * @generated
	 */
	EAttribute getObjectQuantity_Source();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.ObjectQuantity#getInitial
	 * <em>Initial</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Initial</em>'.
	 * @see org.eclipse.set.model.integrationview.ObjectQuantity#getInitial()
	 * @see #getObjectQuantity()
	 * @generated
	 */
	EAttribute getObjectQuantity_Initial();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.ObjectQuantity#getFinal
	 * <em>Final</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Final</em>'.
	 * @see org.eclipse.set.model.integrationview.ObjectQuantity#getFinal()
	 * @see #getObjectQuantity()
	 * @generated
	 */
	EAttribute getObjectQuantity_Final();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.integrationview.Conflict
	 * <em>Conflict</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Conflict</em>'.
	 * @see org.eclipse.set.model.integrationview.Conflict
	 * @generated
	 */
	EClass getConflict();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.Conflict#getId
	 * <em>Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.set.model.integrationview.Conflict#getId()
	 * @see #getConflict()
	 * @generated
	 */
	EAttribute getConflict_Id();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.Conflict#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.set.model.integrationview.Conflict#getName()
	 * @see #getConflict()
	 * @generated
	 */
	EAttribute getConflict_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.Conflict#getContainer
	 * <em>Container</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Container</em>'.
	 * @see org.eclipse.set.model.integrationview.Conflict#getContainer()
	 * @see #getConflict()
	 * @generated
	 */
	EAttribute getConflict_Container();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.Conflict#getVersion
	 * <em>Version</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.set.model.integrationview.Conflict#getVersion()
	 * @see #getConflict()
	 * @generated
	 */
	EAttribute getConflict_Version();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.Conflict#getResolution
	 * <em>Resolution</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Resolution</em>'.
	 * @see org.eclipse.set.model.integrationview.Conflict#getResolution()
	 * @see #getConflict()
	 * @generated
	 */
	EAttribute getConflict_Resolution();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.integrationview.Conflict#getDetails
	 * <em>Details</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Details</em>'.
	 * @see org.eclipse.set.model.integrationview.Conflict#getDetails()
	 * @see #getConflict()
	 * @generated
	 */
	EReference getConflict_Details();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.integrationview.Details <em>Details</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Details</em>'.
	 * @see org.eclipse.set.model.integrationview.Details
	 * @generated
	 */
	EClass getDetails();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.Details#getAttributePath
	 * <em>Attribute Path</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Attribute Path</em>'.
	 * @see org.eclipse.set.model.integrationview.Details#getAttributePath()
	 * @see #getDetails()
	 * @generated
	 */
	EAttribute getDetails_AttributePath();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.Details#getValuePrimaryPlanning
	 * <em>Value Primary Planning</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value Primary
	 *         Planning</em>'.
	 * @see org.eclipse.set.model.integrationview.Details#getValuePrimaryPlanning()
	 * @see #getDetails()
	 * @generated
	 */
	EAttribute getDetails_ValuePrimaryPlanning();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.integrationview.Details#getValueSecondaryPlanning
	 * <em>Value Secondary Planning</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value Secondary
	 *         Planning</em>'.
	 * @see org.eclipse.set.model.integrationview.Details#getValueSecondaryPlanning()
	 * @see #getDetails()
	 * @generated
	 */
	EAttribute getDetails_ValueSecondaryPlanning();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IntegrationviewFactory getIntegrationviewFactory();

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
		 * '{@link org.eclipse.set.model.integrationview.impl.IntegrationViewImpl
		 * <em>Integration View</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.integrationview.impl.IntegrationViewImpl
		 * @see org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl#getIntegrationView()
		 * @generated
		 */
		EClass INTEGRATION_VIEW = eINSTANCE.getIntegrationView();

		/**
		 * The meta object literal for the '<em><b>Primary Planning</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INTEGRATION_VIEW__PRIMARY_PLANNING = eINSTANCE
				.getIntegrationView_PrimaryPlanning();

		/**
		 * The meta object literal for the '<em><b>Secondary Planning</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INTEGRATION_VIEW__SECONDARY_PLANNING = eINSTANCE
				.getIntegrationView_SecondaryPlanning();

		/**
		 * The meta object literal for the '<em><b>Composite Planning</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INTEGRATION_VIEW__COMPOSITE_PLANNING = eINSTANCE
				.getIntegrationView_CompositePlanning();

		/**
		 * The meta object literal for the '<em><b>Objectquantities</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INTEGRATION_VIEW__OBJECTQUANTITIES = eINSTANCE
				.getIntegrationView_Objectquantities();

		/**
		 * The meta object literal for the '<em><b>Conflicts</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INTEGRATION_VIEW__CONFLICTS = eINSTANCE
				.getIntegrationView_Conflicts();

		/**
		 * The meta object literal for the '<em><b>Integration
		 * Directory</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INTEGRATION_VIEW__INTEGRATION_DIRECTORY = eINSTANCE
				.getIntegrationView_IntegrationDirectory();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.integrationview.impl.ObjectQuantityImpl
		 * <em>Object Quantity</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.integrationview.impl.ObjectQuantityImpl
		 * @see org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl#getObjectQuantity()
		 * @generated
		 */
		EClass OBJECT_QUANTITY = eINSTANCE.getObjectQuantity();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute OBJECT_QUANTITY__SOURCE = eINSTANCE
				.getObjectQuantity_Source();

		/**
		 * The meta object literal for the '<em><b>Initial</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute OBJECT_QUANTITY__INITIAL = eINSTANCE
				.getObjectQuantity_Initial();

		/**
		 * The meta object literal for the '<em><b>Final</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute OBJECT_QUANTITY__FINAL = eINSTANCE.getObjectQuantity_Final();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.integrationview.impl.ConflictImpl
		 * <em>Conflict</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.integrationview.impl.ConflictImpl
		 * @see org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl#getConflict()
		 * @generated
		 */
		EClass CONFLICT = eINSTANCE.getConflict();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONFLICT__ID = eINSTANCE.getConflict_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONFLICT__NAME = eINSTANCE.getConflict_Name();

		/**
		 * The meta object literal for the '<em><b>Container</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONFLICT__CONTAINER = eINSTANCE.getConflict_Container();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONFLICT__VERSION = eINSTANCE.getConflict_Version();

		/**
		 * The meta object literal for the '<em><b>Resolution</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONFLICT__RESOLUTION = eINSTANCE.getConflict_Resolution();

		/**
		 * The meta object literal for the '<em><b>Details</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONFLICT__DETAILS = eINSTANCE.getConflict_Details();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.integrationview.impl.DetailsImpl
		 * <em>Details</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.integrationview.impl.DetailsImpl
		 * @see org.eclipse.set.model.integrationview.impl.IntegrationviewPackageImpl#getDetails()
		 * @generated
		 */
		EClass DETAILS = eINSTANCE.getDetails();

		/**
		 * The meta object literal for the '<em><b>Attribute Path</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DETAILS__ATTRIBUTE_PATH = eINSTANCE
				.getDetails_AttributePath();

		/**
		 * The meta object literal for the '<em><b>Value Primary
		 * Planning</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DETAILS__VALUE_PRIMARY_PLANNING = eINSTANCE
				.getDetails_ValuePrimaryPlanning();

		/**
		 * The meta object literal for the '<em><b>Value Secondary
		 * Planning</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DETAILS__VALUE_SECONDARY_PLANNING = eINSTANCE
				.getDetails_ValueSecondaryPlanning();

	}

} // IntegrationviewPackage
