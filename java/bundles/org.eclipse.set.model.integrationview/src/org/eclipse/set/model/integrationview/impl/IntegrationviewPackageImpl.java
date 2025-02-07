/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.set.model.integrationview.Conflict;
import org.eclipse.set.model.integrationview.Details;
import org.eclipse.set.model.integrationview.IntegrationView;
import org.eclipse.set.model.integrationview.IntegrationviewFactory;
import org.eclipse.set.model.integrationview.IntegrationviewPackage;
import org.eclipse.set.model.integrationview.ObjectQuantity;

import org.eclipse.set.model.integrationview.util.IntegrationviewValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class IntegrationviewPackageImpl extends EPackageImpl
		implements IntegrationviewPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass integrationViewEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass objectQuantityEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass conflictEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass detailsEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IntegrationviewPackageImpl() {
		super(eNS_URI, IntegrationviewFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize
	 * {@link IntegrationviewPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access
	 * that field to obtain the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IntegrationviewPackage init() {
		if (isInited)
			return (IntegrationviewPackage) EPackage.Registry.INSTANCE
					.getEPackage(IntegrationviewPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredIntegrationviewPackage = EPackage.Registry.INSTANCE
				.get(eNS_URI);
		IntegrationviewPackageImpl theIntegrationviewPackage = registeredIntegrationviewPackage instanceof IntegrationviewPackageImpl
				? (IntegrationviewPackageImpl) registeredIntegrationviewPackage
				: new IntegrationviewPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theIntegrationviewPackage.createPackageContents();

		// Initialize created meta-data
		theIntegrationviewPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theIntegrationviewPackage,
				new EValidator.Descriptor() {
					@Override
					public EValidator getEValidator() {
						return IntegrationviewValidator.INSTANCE;
					}
				});

		// Mark meta-data to indicate it can't be changed
		theIntegrationviewPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IntegrationviewPackage.eNS_URI,
				theIntegrationviewPackage);
		return theIntegrationviewPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getIntegrationView() {
		return integrationViewEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getIntegrationView_PrimaryPlanning() {
		return (EAttribute) integrationViewEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getIntegrationView_SecondaryPlanning() {
		return (EAttribute) integrationViewEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getIntegrationView_CompositePlanning() {
		return (EAttribute) integrationViewEClass.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getIntegrationView_Objectquantities() {
		return (EReference) integrationViewEClass.getEStructuralFeatures()
				.get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getIntegrationView_Conflicts() {
		return (EReference) integrationViewEClass.getEStructuralFeatures()
				.get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getIntegrationView_IntegrationDirectory() {
		return (EAttribute) integrationViewEClass.getEStructuralFeatures()
				.get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getObjectQuantity() {
		return objectQuantityEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getObjectQuantity_Source() {
		return (EAttribute) objectQuantityEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getObjectQuantity_Initial() {
		return (EAttribute) objectQuantityEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getObjectQuantity_Final() {
		return (EAttribute) objectQuantityEClass.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getConflict() {
		return conflictEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getConflict_Id() {
		return (EAttribute) conflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getConflict_Name() {
		return (EAttribute) conflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getConflict_Container() {
		return (EAttribute) conflictEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getConflict_Version() {
		return (EAttribute) conflictEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getConflict_Resolution() {
		return (EAttribute) conflictEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getConflict_Details() {
		return (EReference) conflictEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDetails() {
		return detailsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDetails_AttributePath() {
		return (EAttribute) detailsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDetails_ValuePrimaryPlanning() {
		return (EAttribute) detailsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDetails_ValueSecondaryPlanning() {
		return (EAttribute) detailsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IntegrationviewFactory getIntegrationviewFactory() {
		return (IntegrationviewFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		integrationViewEClass = createEClass(INTEGRATION_VIEW);
		createEAttribute(integrationViewEClass,
				INTEGRATION_VIEW__PRIMARY_PLANNING);
		createEAttribute(integrationViewEClass,
				INTEGRATION_VIEW__SECONDARY_PLANNING);
		createEAttribute(integrationViewEClass,
				INTEGRATION_VIEW__COMPOSITE_PLANNING);
		createEReference(integrationViewEClass,
				INTEGRATION_VIEW__OBJECTQUANTITIES);
		createEReference(integrationViewEClass, INTEGRATION_VIEW__CONFLICTS);
		createEAttribute(integrationViewEClass,
				INTEGRATION_VIEW__INTEGRATION_DIRECTORY);

		objectQuantityEClass = createEClass(OBJECT_QUANTITY);
		createEAttribute(objectQuantityEClass, OBJECT_QUANTITY__SOURCE);
		createEAttribute(objectQuantityEClass, OBJECT_QUANTITY__INITIAL);
		createEAttribute(objectQuantityEClass, OBJECT_QUANTITY__FINAL);

		conflictEClass = createEClass(CONFLICT);
		createEAttribute(conflictEClass, CONFLICT__ID);
		createEAttribute(conflictEClass, CONFLICT__NAME);
		createEAttribute(conflictEClass, CONFLICT__CONTAINER);
		createEAttribute(conflictEClass, CONFLICT__VERSION);
		createEAttribute(conflictEClass, CONFLICT__RESOLUTION);
		createEReference(conflictEClass, CONFLICT__DETAILS);

		detailsEClass = createEClass(DETAILS);
		createEAttribute(detailsEClass, DETAILS__ATTRIBUTE_PATH);
		createEAttribute(detailsEClass, DETAILS__VALUE_PRIMARY_PLANNING);
		createEAttribute(detailsEClass, DETAILS__VALUE_SECONDARY_PLANNING);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(integrationViewEClass, IntegrationView.class,
				"IntegrationView", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegrationView_PrimaryPlanning(),
				ecorePackage.getEString(), "primaryPlanning", null, 0, 1,
				IntegrationView.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getIntegrationView_SecondaryPlanning(),
				ecorePackage.getEString(), "secondaryPlanning", null, 0, 1,
				IntegrationView.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getIntegrationView_CompositePlanning(),
				ecorePackage.getEString(), "compositePlanning", null, 0, 1,
				IntegrationView.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getIntegrationView_Objectquantities(),
				this.getObjectQuantity(), null, "objectquantities", null, 0, -1,
				IntegrationView.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIntegrationView_Conflicts(), this.getConflict(), null,
				"conflicts", null, 0, -1, IntegrationView.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIntegrationView_IntegrationDirectory(),
				ecorePackage.getEString(), "integrationDirectory", null, 0, 1,
				IntegrationView.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(objectQuantityEClass, ObjectQuantity.class, "ObjectQuantity",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getObjectQuantity_Source(), ecorePackage.getEString(),
				"source", null, 0, 1, ObjectQuantity.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjectQuantity_Initial(), ecorePackage.getEInt(),
				"initial", null, 0, 1, ObjectQuantity.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjectQuantity_Final(), ecorePackage.getEInt(),
				"final", null, 0, 1, ObjectQuantity.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(conflictEClass, Conflict.class, "Conflict", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConflict_Id(), ecorePackage.getEInt(), "id", null, 1,
				1, Conflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConflict_Name(), ecorePackage.getEString(), "name",
				null, 0, 1, Conflict.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getConflict_Container(), ecorePackage.getEString(),
				"container", null, 0, 1, Conflict.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getConflict_Version(), ecorePackage.getEString(),
				"version", null, 0, 1, Conflict.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getConflict_Resolution(), ecorePackage.getEString(),
				"resolution", null, 0, 1, Conflict.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getConflict_Details(), this.getDetails(), null,
				"details", null, 0, -1, Conflict.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(detailsEClass, Details.class, "Details", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDetails_AttributePath(), ecorePackage.getEString(),
				"attributePath", null, 0, 1, Details.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getDetails_ValuePrimaryPlanning(),
				ecorePackage.getEString(), "valuePrimaryPlanning", null, 1, 1,
				Details.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDetails_ValueSecondaryPlanning(),
				ecorePackage.getEString(), "valueSecondaryPlanning", null, 0, 1,
				Details.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
	}

	/**
	 * Initializes the annotations for
	 * <b>http://www.eclipse.org/emf/2002/Ecore</b>. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";
		addAnnotation(detailsEClass, source,
				new String[] { "constraints", "equalPlanningValues" });
	}

} // IntegrationviewPackageImpl
