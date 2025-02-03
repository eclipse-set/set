/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.set.model.simplemerge.SimplemergePackage;

import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationFactory;
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage;
import org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration;

import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Ansteuerung_ElementPackage;

import org.eclipse.set.toolboxmodel.Bahnsteig.BahnsteigPackage;

import org.eclipse.set.toolboxmodel.Bahnuebergang.BahnuebergangPackage;

import org.eclipse.set.toolboxmodel.Balisentechnik_ETCS.Balisentechnik_ETCSPackage;

import org.eclipse.set.toolboxmodel.BasisTypen.BasisTypenPackage;

import org.eclipse.set.toolboxmodel.Basisobjekte.BasisobjektePackage;

import org.eclipse.set.toolboxmodel.Bedienung.BedienungPackage;

import org.eclipse.set.toolboxmodel.Block.BlockPackage;

import org.eclipse.set.toolboxmodel.Fahrstrasse.FahrstrassePackage;

import org.eclipse.set.toolboxmodel.Flankenschutz.FlankenschutzPackage;

import org.eclipse.set.toolboxmodel.Geodaten.GeodatenPackage;

import org.eclipse.set.toolboxmodel.Gleis.GleisPackage;

import org.eclipse.set.toolboxmodel.Medien_und_Trassen.Medien_und_TrassenPackage;
import org.eclipse.set.toolboxmodel.Nahbedienung.NahbedienungPackage;

import org.eclipse.set.toolboxmodel.Ortung.OrtungPackage;

import org.eclipse.set.toolboxmodel.PZB.PZBPackage;

import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;

import org.eclipse.set.toolboxmodel.Regelzeichnung.RegelzeichnungPackage;

import org.eclipse.set.toolboxmodel.Schluesselabhaengigkeiten.SchluesselabhaengigkeitenPackage;

import org.eclipse.set.toolboxmodel.Signalbegriffe_Struktur.Signalbegriffe_StrukturPackage;

import org.eclipse.set.toolboxmodel.Signale.SignalePackage;

import org.eclipse.set.toolboxmodel.Verweise.VerweisePackage;

import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.Weichen_und_GleissperrenPackage;

import org.eclipse.set.toolboxmodel.Zuglenkung.ZuglenkungPackage;

import org.eclipse.set.toolboxmodel.Zugnummernmeldeanlage.ZugnummernmeldeanlagePackage;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass toolboxTemporaryIntegrationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType idReferenceEDataType = null;

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
		PlanProPackage.eINSTANCE.eClass();
		BasisobjektePackage.eINSTANCE.eClass();
		BasisTypenPackage.eINSTANCE.eClass();
		VerweisePackage.eINSTANCE.eClass();
		Ansteuerung_ElementPackage.eINSTANCE.eClass();
		BahnsteigPackage.eINSTANCE.eClass();
		BedienungPackage.eINSTANCE.eClass();
		BlockPackage.eINSTANCE.eClass();
		BahnuebergangPackage.eINSTANCE.eClass();
		FlankenschutzPackage.eINSTANCE.eClass();
		OrtungPackage.eINSTANCE.eClass();
		FahrstrassePackage.eINSTANCE.eClass();
		GeodatenPackage.eINSTANCE.eClass();
		GleisPackage.eINSTANCE.eClass();
		NahbedienungPackage.eINSTANCE.eClass();
		PZBPackage.eINSTANCE.eClass();
		RegelzeichnungPackage.eINSTANCE.eClass();
		SchluesselabhaengigkeitenPackage.eINSTANCE.eClass();
		SignalePackage.eINSTANCE.eClass();
		Signalbegriffe_StrukturPackage.eINSTANCE.eClass();
		Weichen_und_GleissperrenPackage.eINSTANCE.eClass();
		ZuglenkungPackage.eINSTANCE.eClass();
		ZugnummernmeldeanlagePackage.eINSTANCE.eClass();
		SimplemergePackage.eINSTANCE.eClass();
		Balisentechnik_ETCSPackage.eINSTANCE.eClass();
		Medien_und_TrassenPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.PlanPro.PlanProPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Basisobjekte.BasisobjektePackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.BasisTypen.BasisTypenPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Verweise.VerweisePackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Ansteuerung_Element.Ansteuerung_ElementPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Bahnsteig.BahnsteigPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Bedienung.BedienungPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Block.BlockPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Bahnuebergang.BahnuebergangPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Flankenschutz.FlankenschutzPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Ortung.OrtungPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Fahrstrasse.FahrstrassePackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Geodaten.GeodatenPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Gleis.GleisPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Nahbedienung.NahbedienungPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.PZB.PZBPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Regelzeichnung.RegelzeichnungPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Schluesselabhaengigkeiten.SchluesselabhaengigkeitenPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Signale.SignalePackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Signalbegriffe_Struktur.Signalbegriffe_StrukturPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Weichen_und_Gleissperren.Weichen_und_GleissperrenPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Zuglenkung.ZuglenkungPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Zugnummernmeldeanlage.ZugnummernmeldeanlagePackage.eINSTANCE.eClass();
		SimplemergePackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Balisentechnik_ETCS.Balisentechnik_ETCSPackage.eINSTANCE.eClass();
		org.eclipse.set.model.model11001.Medien_und_Trassen.Medien_und_TrassenPackage.eINSTANCE.eClass();
		PlanProPackage.eINSTANCE.eClass();
		BasisobjektePackage.eINSTANCE.eClass();
		BasisTypenPackage.eINSTANCE.eClass();
		VerweisePackage.eINSTANCE.eClass();
		Ansteuerung_ElementPackage.eINSTANCE.eClass();
		BahnsteigPackage.eINSTANCE.eClass();
		BedienungPackage.eINSTANCE.eClass();
		BlockPackage.eINSTANCE.eClass();
		BahnuebergangPackage.eINSTANCE.eClass();
		FlankenschutzPackage.eINSTANCE.eClass();
		OrtungPackage.eINSTANCE.eClass();
		FahrstrassePackage.eINSTANCE.eClass();
		GeodatenPackage.eINSTANCE.eClass();
		GleisPackage.eINSTANCE.eClass();
		NahbedienungPackage.eINSTANCE.eClass();
		PZBPackage.eINSTANCE.eClass();
		RegelzeichnungPackage.eINSTANCE.eClass();
		SchluesselabhaengigkeitenPackage.eINSTANCE.eClass();
		SignalePackage.eINSTANCE.eClass();
		Signalbegriffe_StrukturPackage.eINSTANCE.eClass();
		Weichen_und_GleissperrenPackage.eINSTANCE.eClass();
		ZuglenkungPackage.eINSTANCE.eClass();
		ZugnummernmeldeanlagePackage.eINSTANCE.eClass();
		SimplemergePackage.eINSTANCE.eClass();
		Balisentechnik_ETCSPackage.eINSTANCE.eClass();
		Medien_und_TrassenPackage.eINSTANCE.eClass();

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
	public EClass getToolboxTemporaryIntegration() {
		return toolboxTemporaryIntegrationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getToolboxTemporaryIntegration_PrimaryPlanning() {
		return (EReference)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getToolboxTemporaryIntegration_PrimaryPlanningFilename() {
		return (EAttribute)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getToolboxTemporaryIntegration_PrimaryPlanningWasValid() {
		return (EAttribute)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getToolboxTemporaryIntegration_PrimaryPlanningIDReferences() {
		return (EAttribute)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getToolboxTemporaryIntegration_SecondaryPlanning() {
		return (EReference)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getToolboxTemporaryIntegration_SecondaryPlanningFilename() {
		return (EAttribute)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getToolboxTemporaryIntegration_SecondaryPlanningWasValid() {
		return (EAttribute)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getToolboxTemporaryIntegration_SecondaryPlanningIDReferences() {
		return (EAttribute)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getToolboxTemporaryIntegration_CompositePlanning() {
		return (EReference)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getToolboxTemporaryIntegration_CompositePlanningIDReferences() {
		return (EAttribute)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getToolboxTemporaryIntegration_IntegrationDirectory() {
		return (EAttribute)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getToolboxTemporaryIntegration_ComparisonInitialState() {
		return (EReference)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getToolboxTemporaryIntegration_ComparisonFinalState() {
		return (EReference)toolboxTemporaryIntegrationEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIDReference() {
		return idReferenceEDataType;
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

		toolboxTemporaryIntegrationEClass = createEClass(TOOLBOX_TEMPORARY_INTEGRATION);
		createEReference(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING);
		createEAttribute(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME);
		createEAttribute(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID);
		createEAttribute(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_ID_REFERENCES);
		createEReference(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING);
		createEAttribute(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME);
		createEAttribute(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID);
		createEAttribute(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_ID_REFERENCES);
		createEReference(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING);
		createEAttribute(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING_ID_REFERENCES);
		createEAttribute(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY);
		createEReference(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE);
		createEReference(toolboxTemporaryIntegrationEClass, TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE);

		// Create data types
		idReferenceEDataType = createEDataType(ID_REFERENCE);
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
		org.eclipse.set.model.model11001.PlanPro.PlanProPackage thePlanProPackage_1 = (org.eclipse.set.model.model11001.PlanPro.PlanProPackage)EPackage.Registry.INSTANCE.getEPackage(org.eclipse.set.model.model11001.PlanPro.PlanProPackage.eNS_URI);
		SimplemergePackage theSimplemergePackage = (SimplemergePackage)EPackage.Registry.INSTANCE.getEPackage(SimplemergePackage.eNS_URI);
		PlanProPackage thePlanProPackage = (PlanProPackage)EPackage.Registry.INSTANCE.getEPackage(PlanProPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(temporaryIntegrationEClass, TemporaryIntegration.class, "TemporaryIntegration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemporaryIntegration_PrimaryPlanning(), thePlanProPackage_1.getPlanPro_Schnittstelle(), null, "primaryPlanning", null, 1, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_PrimaryPlanningFilename(), ecorePackage.getEString(), "primaryPlanningFilename", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_PrimaryPlanningWasValid(), ecorePackage.getEBoolean(), "primaryPlanningWasValid", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporaryIntegration_SecondaryPlanning(), thePlanProPackage_1.getPlanPro_Schnittstelle(), null, "secondaryPlanning", null, 1, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_SecondaryPlanningFilename(), ecorePackage.getEString(), "secondaryPlanningFilename", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_SecondaryPlanningWasValid(), ecorePackage.getEBoolean(), "secondaryPlanningWasValid", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporaryIntegration_CompositePlanning(), thePlanProPackage_1.getPlanPro_Schnittstelle(), null, "compositePlanning", null, 1, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporaryIntegration_IntegrationDirectory(), ecorePackage.getEString(), "integrationDirectory", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporaryIntegration_ComparisonInitialState(), theSimplemergePackage.getSComparison(), null, "comparisonInitialState", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporaryIntegration_ComparisonFinalState(), theSimplemergePackage.getSComparison(), null, "comparisonFinalState", null, 0, 1, TemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(toolboxTemporaryIntegrationEClass, ToolboxTemporaryIntegration.class, "ToolboxTemporaryIntegration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getToolboxTemporaryIntegration_PrimaryPlanning(), thePlanProPackage.getPlanPro_Schnittstelle(), null, "primaryPlanning", null, 1, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolboxTemporaryIntegration_PrimaryPlanningFilename(), ecorePackage.getEString(), "primaryPlanningFilename", null, 0, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolboxTemporaryIntegration_PrimaryPlanningWasValid(), ecorePackage.getEBoolean(), "primaryPlanningWasValid", null, 0, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolboxTemporaryIntegration_PrimaryPlanningIDReferences(), this.getIDReference(), "primaryPlanningIDReferences", null, 0, -1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getToolboxTemporaryIntegration_SecondaryPlanning(), thePlanProPackage.getPlanPro_Schnittstelle(), null, "secondaryPlanning", null, 1, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolboxTemporaryIntegration_SecondaryPlanningFilename(), ecorePackage.getEString(), "secondaryPlanningFilename", null, 0, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolboxTemporaryIntegration_SecondaryPlanningWasValid(), ecorePackage.getEBoolean(), "secondaryPlanningWasValid", null, 0, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolboxTemporaryIntegration_SecondaryPlanningIDReferences(), this.getIDReference(), "secondaryPlanningIDReferences", null, 0, -1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getToolboxTemporaryIntegration_CompositePlanning(), thePlanProPackage.getPlanPro_Schnittstelle(), null, "compositePlanning", null, 1, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolboxTemporaryIntegration_CompositePlanningIDReferences(), this.getIDReference(), "compositePlanningIDReferences", null, 0, -1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolboxTemporaryIntegration_IntegrationDirectory(), ecorePackage.getEString(), "integrationDirectory", null, 0, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getToolboxTemporaryIntegration_ComparisonInitialState(), theSimplemergePackage.getSComparison(), null, "comparisonInitialState", null, 0, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getToolboxTemporaryIntegration_ComparisonFinalState(), theSimplemergePackage.getSComparison(), null, "comparisonFinalState", null, 0, 1, ToolboxTemporaryIntegration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(idReferenceEDataType, IDReference.class, "IDReference", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //TemporaryintegrationPackageImpl
