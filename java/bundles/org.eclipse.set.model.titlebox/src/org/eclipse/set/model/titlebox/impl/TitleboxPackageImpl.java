/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.set.model.titlebox.PlanningOffice;
import org.eclipse.set.model.titlebox.StringField;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.model.titlebox.TitleboxFactory;
import org.eclipse.set.model.titlebox.TitleboxPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TitleboxPackageImpl extends EPackageImpl
		implements TitleboxPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass titleboxEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass planningOfficeEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stringFieldEClass = null;

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
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TitleboxPackageImpl() {
		super(eNS_URI, TitleboxFactory.eINSTANCE);
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
	 * This method is used to initialize {@link TitleboxPackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TitleboxPackage init() {
		if (isInited)
			return (TitleboxPackage) EPackage.Registry.INSTANCE
					.getEPackage(TitleboxPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTitleboxPackage = EPackage.Registry.INSTANCE
				.get(eNS_URI);
		TitleboxPackageImpl theTitleboxPackage = registeredTitleboxPackage instanceof TitleboxPackageImpl
				? (TitleboxPackageImpl) registeredTitleboxPackage
				: new TitleboxPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theTitleboxPackage.createPackageContents();

		// Initialize created meta-data
		theTitleboxPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTitleboxPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TitleboxPackage.eNS_URI,
				theTitleboxPackage);
		return theTitleboxPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTitlebox() {
		return titleboxEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTitlebox_Field() {
		return (EAttribute) titleboxEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTitlebox_PlanningOffice() {
		return (EReference) titleboxEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPlanningOffice() {
		return planningOfficeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPlanningOffice_Variant() {
		return (EAttribute) planningOfficeEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPlanningOffice_Name() {
		return (EReference) planningOfficeEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPlanningOffice_Group() {
		return (EReference) planningOfficeEClass.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPlanningOffice_Location() {
		return (EReference) planningOfficeEClass.getEStructuralFeatures()
				.get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPlanningOffice_Phone() {
		return (EReference) planningOfficeEClass.getEStructuralFeatures()
				.get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPlanningOffice_Email() {
		return (EReference) planningOfficeEClass.getEStructuralFeatures()
				.get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPlanningOffice_Logo() {
		return (EAttribute) planningOfficeEClass.getEStructuralFeatures()
				.get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStringField() {
		return stringFieldEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStringField_Fontsize() {
		return (EAttribute) stringFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStringField_Text() {
		return (EAttribute) stringFieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TitleboxFactory getTitleboxFactory() {
		return (TitleboxFactory) getEFactoryInstance();
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
		titleboxEClass = createEClass(TITLEBOX);
		createEAttribute(titleboxEClass, TITLEBOX__FIELD);
		createEReference(titleboxEClass, TITLEBOX__PLANNING_OFFICE);

		planningOfficeEClass = createEClass(PLANNING_OFFICE);
		createEAttribute(planningOfficeEClass, PLANNING_OFFICE__VARIANT);
		createEReference(planningOfficeEClass, PLANNING_OFFICE__NAME);
		createEReference(planningOfficeEClass, PLANNING_OFFICE__GROUP);
		createEReference(planningOfficeEClass, PLANNING_OFFICE__LOCATION);
		createEReference(planningOfficeEClass, PLANNING_OFFICE__PHONE);
		createEReference(planningOfficeEClass, PLANNING_OFFICE__EMAIL);
		createEAttribute(planningOfficeEClass, PLANNING_OFFICE__LOGO);

		stringFieldEClass = createEClass(STRING_FIELD);
		createEAttribute(stringFieldEClass, STRING_FIELD__FONTSIZE);
		createEAttribute(stringFieldEClass, STRING_FIELD__TEXT);
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
		initEClass(titleboxEClass, Titlebox.class, "Titlebox", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTitlebox_Field(), ecorePackage.getEString(), "field",
				null, 1, 100, Titlebox.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTitlebox_PlanningOffice(), this.getPlanningOffice(),
				null, "planningOffice", null, 0, 1, Titlebox.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(planningOfficeEClass, PlanningOffice.class, "PlanningOffice",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPlanningOffice_Variant(), ecorePackage.getEString(),
				"variant", null, 0, 1, PlanningOffice.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getPlanningOffice_Name(), this.getStringField(), null,
				"name", null, 0, 1, PlanningOffice.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlanningOffice_Group(), this.getStringField(), null,
				"group", null, 0, 1, PlanningOffice.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlanningOffice_Location(), this.getStringField(),
				null, "location", null, 0, 1, PlanningOffice.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getPlanningOffice_Phone(), this.getStringField(), null,
				"phone", null, 0, 1, PlanningOffice.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlanningOffice_Email(), this.getStringField(), null,
				"email", null, 0, 1, PlanningOffice.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getPlanningOffice_Logo(), ecorePackage.getEString(),
				"logo", null, 0, 1, PlanningOffice.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(stringFieldEClass, StringField.class, "StringField",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringField_Fontsize(), ecorePackage.getEString(),
				"fontsize", null, 0, 1, StringField.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringField_Text(), ecorePackage.getEString(), "text",
				null, 0, 1, StringField.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // TitleboxPackageImpl
