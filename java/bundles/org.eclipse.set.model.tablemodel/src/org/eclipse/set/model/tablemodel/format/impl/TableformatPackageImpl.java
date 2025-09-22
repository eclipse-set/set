/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.format.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.set.model.planpro.ATO.ATOPackage;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Ansteuerung_ElementPackage;
import org.eclipse.set.model.planpro.Bahnsteig.BahnsteigPackage;
import org.eclipse.set.model.planpro.Bahnuebergang.BahnuebergangPackage;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Balisentechnik_ETCSPackage;
import org.eclipse.set.model.tablemodel.TablemodelPackage;
import org.eclipse.set.model.tablemodel.format.CellFormat;
import org.eclipse.set.model.tablemodel.format.TableformatFactory;
import org.eclipse.set.model.tablemodel.format.TableformatPackage;
import org.eclipse.set.model.tablemodel.format.TextAlignment;
import org.eclipse.set.model.planpro.BasisTypen.BasisTypenPackage;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjektePackage;
import org.eclipse.set.model.planpro.Bedienung.BedienungPackage;
import org.eclipse.set.model.planpro.Block.BlockPackage;
import org.eclipse.set.model.planpro.Fahrstrasse.FahrstrassePackage;
import org.eclipse.set.model.planpro.Flankenschutz.FlankenschutzPackage;
import org.eclipse.set.model.planpro.Geodaten.GeodatenPackage;
import org.eclipse.set.model.planpro.Gleis.GleisPackage;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenPackage;
import org.eclipse.set.model.planpro.Medien_und_Trassen.Medien_und_TrassenPackage;
import org.eclipse.set.model.planpro.Nahbedienung.NahbedienungPackage;
import org.eclipse.set.model.planpro.Ortung.OrtungPackage;
import org.eclipse.set.model.planpro.PZB.PZBPackage;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.set.model.planpro.Regelzeichnung.RegelzeichnungPackage;
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.SchluesselabhaengigkeitenPackage;
import org.eclipse.set.model.planpro.Signalbegriffe_Struktur.Signalbegriffe_StrukturPackage;
import org.eclipse.set.model.planpro.Signale.SignalePackage;
import org.eclipse.set.model.planpro.Verweise.VerweisePackage;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Weichen_und_GleissperrenPackage;
import org.eclipse.set.model.planpro.Zuglenkung.ZuglenkungPackage;
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZugnummernmeldeanlagePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TableformatPackageImpl extends EPackageImpl
		implements TableformatPackage {
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
	 * This method is used to initialize {@link TableformatPackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TableformatPackage init() {
		if (isInited)
			return (TableformatPackage) EPackage.Registry.INSTANCE
					.getEPackage(TableformatPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTableformatPackage = EPackage.Registry.INSTANCE
				.get(eNS_URI);
		TableformatPackageImpl theTableformatPackage = registeredTableformatPackage instanceof TableformatPackageImpl
				? (TableformatPackageImpl) registeredTableformatPackage
				: new TableformatPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		TablemodelPackage.eINSTANCE.eClass();
		BasisobjektePackage.eINSTANCE.eClass();
		BasisTypenPackage.eINSTANCE.eClass();
		VerweisePackage.eINSTANCE.eClass();
		GeodatenPackage.eINSTANCE.eClass();
		ATOPackage.eINSTANCE.eClass();
		PlanProPackage.eINSTANCE.eClass();
		Ansteuerung_ElementPackage.eINSTANCE.eClass();
		BahnsteigPackage.eINSTANCE.eClass();
		Balisentechnik_ETCSPackage.eINSTANCE.eClass();
		BedienungPackage.eINSTANCE.eClass();
		BlockPackage.eINSTANCE.eClass();
		BahnuebergangPackage.eINSTANCE.eClass();
		OrtungPackage.eINSTANCE.eClass();
		FlankenschutzPackage.eINSTANCE.eClass();
		FahrstrassePackage.eINSTANCE.eClass();
		GleisPackage.eINSTANCE.eClass();
		SignalePackage.eINSTANCE.eClass();
		LayoutinformationenPackage.eINSTANCE.eClass();
		NahbedienungPackage.eINSTANCE.eClass();
		PZBPackage.eINSTANCE.eClass();
		RegelzeichnungPackage.eINSTANCE.eClass();
		SchluesselabhaengigkeitenPackage.eINSTANCE.eClass();
		Weichen_und_GleissperrenPackage.eINSTANCE.eClass();
		Medien_und_TrassenPackage.eINSTANCE.eClass();
		ZuglenkungPackage.eINSTANCE.eClass();
		ZugnummernmeldeanlagePackage.eINSTANCE.eClass();
		Signalbegriffe_StrukturPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTableformatPackage.createPackageContents();

		// Initialize created meta-data
		theTableformatPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTableformatPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TableformatPackage.eNS_URI,
				theTableformatPackage);
		return theTableformatPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cellFormatEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum textAlignmentEEnum = null;

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
	 * @see org.eclipse.set.model.tablemodel.format.TableformatPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TableformatPackageImpl() {
		super(eNS_URI, TableformatFactory.eINSTANCE);
	}

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
		cellFormatEClass = createEClass(CELL_FORMAT);
		createEAttribute(cellFormatEClass, CELL_FORMAT__TEXT_ALIGNMENT);
		createEAttribute(cellFormatEClass,
				CELL_FORMAT__TOPOLOGICAL_CALCULATION);

		// Create enums
		textAlignmentEEnum = createEEnum(TEXT_ALIGNMENT);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCellFormat() {
		return cellFormatEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCellFormat_TextAlignment() {
		return (EAttribute) cellFormatEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCellFormat_TopologicalCalculation() {
		return (EAttribute) cellFormatEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TableformatFactory getTableformatFactory() {
		return (TableformatFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getTextAlignment() {
		return textAlignmentEEnum;
	}

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

		// Obtain other dependent packages
		TablemodelPackage theTablemodelPackage = (TablemodelPackage) EPackage.Registry.INSTANCE
				.getEPackage(TablemodelPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE
				.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		cellFormatEClass.getESuperTypes()
				.add(theTablemodelPackage.getCellAnnotation());

		// Initialize classes, features, and operations; add parameters
		initEClass(cellFormatEClass, CellFormat.class, "CellFormat",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCellFormat_TextAlignment(), this.getTextAlignment(),
				"textAlignment", null, 1, 1, CellFormat.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCellFormat_TopologicalCalculation(),
				theXMLTypePackage.getBoolean(), "topologicalCalculation",
				"false", 1, 1, CellFormat.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(textAlignmentEEnum, TextAlignment.class, "TextAlignment");
		addEEnumLiteral(textAlignmentEEnum, TextAlignment.CENTER);
		addEEnumLiteral(textAlignmentEEnum, TextAlignment.LEFT);
		addEEnumLiteral(textAlignmentEEnum, TextAlignment.RIGHT);

		// Create resource
		createResource(eNS_URI);
	}

} // TableformatPackageImpl
