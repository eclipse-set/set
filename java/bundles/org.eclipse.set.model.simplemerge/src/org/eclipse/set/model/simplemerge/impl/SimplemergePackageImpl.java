/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.simplemerge.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.set.model.simplemerge.Resolution;
import org.eclipse.set.model.simplemerge.SComparison;
import org.eclipse.set.model.simplemerge.SMatch;
import org.eclipse.set.model.simplemerge.SimplemergeFactory;
import org.eclipse.set.model.simplemerge.SimplemergePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class SimplemergePackageImpl extends EPackageImpl
		implements SimplemergePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass sComparisonEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass sMatchEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum resolutionEEnum = null;

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
	 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SimplemergePackageImpl() {
		super(eNS_URI, SimplemergeFactory.eINSTANCE);
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
	 * This method is used to initialize {@link SimplemergePackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SimplemergePackage init() {
		if (isInited)
			return (SimplemergePackage) EPackage.Registry.INSTANCE
					.getEPackage(SimplemergePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSimplemergePackage = EPackage.Registry.INSTANCE
				.get(eNS_URI);
		SimplemergePackageImpl theSimplemergePackage = registeredSimplemergePackage instanceof SimplemergePackageImpl
				? (SimplemergePackageImpl) registeredSimplemergePackage
				: new SimplemergePackageImpl();

		isInited = true;

		// Create package meta-data objects
		theSimplemergePackage.createPackageContents();

		// Initialize created meta-data
		theSimplemergePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSimplemergePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SimplemergePackage.eNS_URI,
				theSimplemergePackage);
		return theSimplemergePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSComparison() {
		return sComparisonEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSComparison_Matches() {
		return (EReference) sComparisonEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSMatch() {
		return sMatchEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSMatch_Id() {
		return (EAttribute) sMatchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSMatch_GuidPrimary() {
		return (EAttribute) sMatchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSMatch_GuidSecondary() {
		return (EAttribute) sMatchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSMatch_Resolution() {
		return (EAttribute) sMatchEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSMatch_ElementType() {
		return (EAttribute) sMatchEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getResolution() {
		return resolutionEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SimplemergeFactory getSimplemergeFactory() {
		return (SimplemergeFactory) getEFactoryInstance();
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
		sComparisonEClass = createEClass(SCOMPARISON);
		createEReference(sComparisonEClass, SCOMPARISON__MATCHES);

		sMatchEClass = createEClass(SMATCH);
		createEAttribute(sMatchEClass, SMATCH__ID);
		createEAttribute(sMatchEClass, SMATCH__GUID_PRIMARY);
		createEAttribute(sMatchEClass, SMATCH__GUID_SECONDARY);
		createEAttribute(sMatchEClass, SMATCH__RESOLUTION);
		createEAttribute(sMatchEClass, SMATCH__ELEMENT_TYPE);

		// Create enums
		resolutionEEnum = createEEnum(RESOLUTION);
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
		initEClass(sComparisonEClass, SComparison.class, "SComparison",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSComparison_Matches(), this.getSMatch(), null,
				"matches", null, 0, -1, SComparison.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sMatchEClass, SMatch.class, "SMatch", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSMatch_Id(), ecorePackage.getEInt(), "id", null, 1, 1,
				SMatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSMatch_GuidPrimary(), ecorePackage.getEString(),
				"guidPrimary", null, 0, 1, SMatch.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getSMatch_GuidSecondary(), ecorePackage.getEString(),
				"guidSecondary", null, 0, 1, SMatch.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getSMatch_Resolution(), this.getResolution(),
				"resolution", null, 0, 1, SMatch.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getSMatch_ElementType(), ecorePackage.getEString(),
				"elementType", null, 0, 1, SMatch.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(resolutionEEnum, Resolution.class, "Resolution");
		addEEnumLiteral(resolutionEEnum, Resolution.PRIMARY_UNRESOLVED);
		addEEnumLiteral(resolutionEEnum, Resolution.PRIMARY_AUTO);
		addEEnumLiteral(resolutionEEnum, Resolution.PRIMARY_MANUAL);
		addEEnumLiteral(resolutionEEnum, Resolution.SECONDARY_AUTO);
		addEEnumLiteral(resolutionEEnum, Resolution.SECONDARY_MANUAL);

		// Create resource
		createResource(eNS_URI);
	}

} // SimplemergePackageImpl
