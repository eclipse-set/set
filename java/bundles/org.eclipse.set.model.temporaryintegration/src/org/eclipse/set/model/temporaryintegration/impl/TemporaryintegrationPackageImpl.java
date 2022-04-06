/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.set.model.simplemerge.SimplemergePackage;

import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationFactory;
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TemporaryintegrationPackageImpl extends EPackageImpl implements TemporaryintegrationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass temporaryIntegrationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TemporaryintegrationPackageImpl() {
		super(eNS_URI, TemporaryintegrationFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link TemporaryintegrationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TemporaryintegrationPackage init() {
		if (isInited) return (TemporaryintegrationPackage)EPackage.Registry.INSTANCE.getEPackage(TemporaryintegrationPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTemporaryintegrationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TemporaryintegrationPackageImpl theTemporaryintegrationPackage = registeredTemporaryintegrationPackage instanceof TemporaryintegrationPackageImpl ? (TemporaryintegrationPackageImpl)registeredTemporaryintegrationPackage : new TemporaryintegrationPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Basisobjekte.BasisobjektePackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.BasisTypen.BasisTypenPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Verweise.VerweisePackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Ansteuerung_Element.Ansteuerung_ElementPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Bahnsteig.BahnsteigPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Bedienung.BedienungPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Block.BlockPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Bahnuebergang.BahnuebergangPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Flankenschutz.FlankenschutzPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Ortung.OrtungPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Fahrstrasse.FahrstrassePackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Geodaten.GeodatenPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Gleis.GleisPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Nahbedienbereich.NahbedienbereichPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.PZB.PZBPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Regelzeichnung.RegelzeichnungPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Schluesselabhaengigkeiten.SchluesselabhaengigkeitenPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Signale.SignalePackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Signalbegriffe_Struktur.Signalbegriffe_StrukturPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.Weichen_und_GleissperrenPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Zuglenkung.ZuglenkungPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Zugnummernmeldeanlage.ZugnummernmeldeanlagePackage.eINSTANCE.eClass();
		SimplemergePackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Balisentechnik_ETCS.Balisentechnik_ETCSPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Medien_und_Trassen.Medien_und_TrassenPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTemporaryintegrationPackage.createPackageContents();

		// Initialize created meta-data
		theTemporaryintegrationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTemporaryintegrationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TemporaryintegrationPackage.eNS_URI, theTemporaryintegrationPackage);
		return theTemporaryintegrationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTemporaryIntegration() {
		return temporaryIntegrationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTemporaryIntegration_PrimaryPlanning() {
		return (EReference)temporaryIntegrationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTemporaryIntegration_PrimaryPlanningFilename() {
		return (EAttribute)temporaryIntegrationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTemporaryIntegration_PrimaryPlanningWasValid() {
		return (EAttribute)temporaryIntegrationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTemporaryIntegration_SecondaryPlanning() {
		return (EReference)temporaryIntegrationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTemporaryIntegration_SecondaryPlanningFilename() {
		return (EAttribute)temporaryIntegrationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTemporaryIntegration_SecondaryPlanningWasValid() {
		return (EAttribute)temporaryIntegrationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTemporaryIntegration_CompositePlanning() {
		return (EReference)temporaryIntegrationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTemporaryIntegration_IntegrationDirectory() {
		return (EAttribute)temporaryIntegrationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTemporaryIntegration_ComparisonInitialState() {
		return (EReference)temporaryIntegrationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTemporaryIntegration_ComparisonFinalState() {
		return (EReference)temporaryIntegrationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TemporaryintegrationFactory getTemporaryintegrationFactory() {
		return (TemporaryintegrationFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		temporaryIntegrationEClass = createEClass(TEMPORARY_INTEGRATION);
		createEReference(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__PRIMARY_PLANNING);
		createEAttribute(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME);
		createEAttribute(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID);
		createEReference(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__SECONDARY_PLANNING);
		createEAttribute(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME);
		createEAttribute(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID);
		createEReference(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__COMPOSITE_PLANNING);
		createEAttribute(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY);
		createEReference(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE);
		createEReference(temporaryIntegrationEClass, TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage thePlanProPackage = (org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage)EPackage.Registry.INSTANCE.getEPackage(org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage.eNS_URI);
		SimplemergePackage theSimplemergePackage = (SimplemergePackage)EPackage.Registry.INSTANCE.getEPackage(SimplemergePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(temporaryIntegrationEClass, TemporaryIntegration.class, "TemporaryIntegration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemporaryIntegration_PrimaryPlanning(), thePlanProPackage.getPlanPro_Schnittstelle(), null, "primaryPlanning", null, 1, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_PrimaryPlanningFilename(), ecorePackage.getEString(), "primaryPlanningFilename", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_PrimaryPlanningWasValid(), ecorePackage.getEBoolean(), "primaryPlanningWasValid", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporaryIntegration_SecondaryPlanning(), thePlanProPackage.getPlanPro_Schnittstelle(), null, "secondaryPlanning", null, 1, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_SecondaryPlanningFilename(), ecorePackage.getEString(), "secondaryPlanningFilename", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_SecondaryPlanningWasValid(), ecorePackage.getEBoolean(), "secondaryPlanningWasValid", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporaryIntegration_CompositePlanning(), thePlanProPackage.getPlanPro_Schnittstelle(), null, "compositePlanning", null, 1, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_IntegrationDirectory(), ecorePackage.getEString(), "integrationDirectory", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporaryIntegration_ComparisonInitialState(), theSimplemergePackage.getSComparison(), null, "comparisonInitialState", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporaryIntegration_ComparisonFinalState(), theSimplemergePackage.getSComparison(), null, "comparisonFinalState", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TemporaryintegrationPackageImpl
