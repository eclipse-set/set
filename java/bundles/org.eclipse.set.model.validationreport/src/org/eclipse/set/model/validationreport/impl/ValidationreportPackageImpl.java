/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.set.model.validationreport.ContainerContent;
import org.eclipse.set.model.validationreport.FileInfo;
import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.model.validationreport.ValidationreportFactory;
import org.eclipse.set.model.validationreport.ValidationreportPackage;
import org.eclipse.set.model.validationreport.VersionInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ValidationreportPackageImpl extends EPackageImpl implements ValidationreportPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass validationReportEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass validationProblemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum validationSeverityEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum objectScopeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum containerContentEEnum = null;

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
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ValidationreportPackageImpl() {
		super(eNS_URI, ValidationreportFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ValidationreportPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ValidationreportPackage init() {
		if (isInited) return (ValidationreportPackage)EPackage.Registry.INSTANCE.getEPackage(ValidationreportPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredValidationreportPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ValidationreportPackageImpl theValidationreportPackage = registeredValidationreportPackage instanceof ValidationreportPackageImpl ? (ValidationreportPackageImpl)registeredValidationreportPackage : new ValidationreportPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theValidationreportPackage.createPackageContents();

		// Initialize created meta-data
		theValidationreportPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theValidationreportPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ValidationreportPackage.eNS_URI, theValidationreportPackage);
		return theValidationreportPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValidationReport() {
		return validationReportEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationReport_ModelLoaded() {
		return (EAttribute)validationReportEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationReport_Valid() {
		return (EAttribute)validationReportEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationReport_XsdValid() {
		return (EAttribute)validationReportEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationReport_EmfValid() {
		return (EAttribute)validationReportEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getValidationReport_Problems() {
		return (EReference)validationReportEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getValidationReport_SupportedVersion() {
		return (EReference)validationReportEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationReport_ToolboxVersion() {
		return (EAttribute)validationReportEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationReport_SubworkCount() {
		return (EAttribute)validationReportEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationReport_SubworkTypes() {
		return (EAttribute)validationReportEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getValidationReport_FileInfo() {
		return (EReference)validationReportEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValidationProblem() {
		return validationProblemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_Id() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_Type() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_Severity() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_SeverityText() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_LineNumber() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_Message() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_ObjectArt() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_AttributeName() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_ObjectScope() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValidationProblem_ObjectState() {
		return (EAttribute)validationProblemEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVersionInfo() {
		return versionInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_PlanPro() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Signals() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFileInfo() {
		return fileInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_FileName() {
		return (EAttribute)fileInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFileInfo_UsedVersion() {
		return (EReference)fileInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_Checksum() {
		return (EAttribute)fileInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_Guid() {
		return (EAttribute)fileInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_TimeStamp() {
		return (EAttribute)fileInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_ContainerContents() {
		return (EAttribute)fileInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getValidationSeverity() {
		return validationSeverityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getObjectScope() {
		return objectScopeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getContainerContent() {
		return containerContentEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValidationreportFactory getValidationreportFactory() {
		return (ValidationreportFactory)getEFactoryInstance();
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
		validationReportEClass = createEClass(VALIDATION_REPORT);
		createEAttribute(validationReportEClass, VALIDATION_REPORT__MODEL_LOADED);
		createEAttribute(validationReportEClass, VALIDATION_REPORT__VALID);
		createEAttribute(validationReportEClass, VALIDATION_REPORT__XSD_VALID);
		createEAttribute(validationReportEClass, VALIDATION_REPORT__EMF_VALID);
		createEReference(validationReportEClass, VALIDATION_REPORT__PROBLEMS);
		createEReference(validationReportEClass, VALIDATION_REPORT__SUPPORTED_VERSION);
		createEAttribute(validationReportEClass, VALIDATION_REPORT__TOOLBOX_VERSION);
		createEAttribute(validationReportEClass, VALIDATION_REPORT__SUBWORK_COUNT);
		createEAttribute(validationReportEClass, VALIDATION_REPORT__SUBWORK_TYPES);
		createEReference(validationReportEClass, VALIDATION_REPORT__FILE_INFO);

		validationProblemEClass = createEClass(VALIDATION_PROBLEM);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__ID);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__TYPE);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__SEVERITY);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__SEVERITY_TEXT);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__LINE_NUMBER);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__MESSAGE);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__OBJECT_ART);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__ATTRIBUTE_NAME);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__OBJECT_SCOPE);
		createEAttribute(validationProblemEClass, VALIDATION_PROBLEM__OBJECT_STATE);

		versionInfoEClass = createEClass(VERSION_INFO);
		createEAttribute(versionInfoEClass, VERSION_INFO__PLAN_PRO);
		createEAttribute(versionInfoEClass, VERSION_INFO__SIGNALS);

		fileInfoEClass = createEClass(FILE_INFO);
		createEAttribute(fileInfoEClass, FILE_INFO__FILE_NAME);
		createEReference(fileInfoEClass, FILE_INFO__USED_VERSION);
		createEAttribute(fileInfoEClass, FILE_INFO__CHECKSUM);
		createEAttribute(fileInfoEClass, FILE_INFO__GUID);
		createEAttribute(fileInfoEClass, FILE_INFO__TIME_STAMP);
		createEAttribute(fileInfoEClass, FILE_INFO__CONTAINER_CONTENTS);

		// Create enums
		validationSeverityEEnum = createEEnum(VALIDATION_SEVERITY);
		objectScopeEEnum = createEEnum(OBJECT_SCOPE);
		containerContentEEnum = createEEnum(CONTAINER_CONTENT);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(validationReportEClass, ValidationReport.class, "ValidationReport", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValidationReport_ModelLoaded(), ecorePackage.getEString(), "modelLoaded", null, 0, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationReport_Valid(), ecorePackage.getEString(), "valid", null, 0, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationReport_XsdValid(), ecorePackage.getEString(), "xsdValid", null, 0, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationReport_EmfValid(), ecorePackage.getEString(), "emfValid", null, 0, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getValidationReport_Problems(), this.getValidationProblem(), null, "problems", null, 0, -1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getValidationReport_SupportedVersion(), this.getVersionInfo(), null, "supportedVersion", null, 1, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationReport_ToolboxVersion(), ecorePackage.getEString(), "toolboxVersion", null, 0, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationReport_SubworkCount(), ecorePackage.getEString(), "subworkCount", null, 0, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationReport_SubworkTypes(), ecorePackage.getEString(), "subworkTypes", null, 0, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getValidationReport_FileInfo(), this.getFileInfo(), null, "fileInfo", null, 0, 1, ValidationReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(validationProblemEClass, ValidationProblem.class, "ValidationProblem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValidationProblem_Id(), ecorePackage.getEInt(), "id", null, 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_Type(), ecorePackage.getEString(), "type", null, 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_Severity(), this.getValidationSeverity(), "severity", null, 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_SeverityText(), ecorePackage.getEString(), "severityText", null, 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_Message(), ecorePackage.getEString(), "message", null, 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_ObjectArt(), ecorePackage.getEString(), "objectArt", null, 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_AttributeName(), ecorePackage.getEString(), "attributeName", null, 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_ObjectScope(), this.getObjectScope(), "objectScope", "", 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationProblem_ObjectState(), ecorePackage.getEString(), "objectState", "", 0, 1, ValidationProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionInfoEClass, VersionInfo.class, "VersionInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersionInfo_PlanPro(), ecorePackage.getEString(), "planPro", null, 0, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionInfo_Signals(), ecorePackage.getEString(), "signals", null, 0, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileInfoEClass, FileInfo.class, "FileInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileInfo_FileName(), ecorePackage.getEString(), "fileName", null, 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileInfo_UsedVersion(), this.getVersionInfo(), null, "usedVersion", null, 1, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_Checksum(), ecorePackage.getEString(), "checksum", null, 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_Guid(), ecorePackage.getEString(), "guid", null, 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_TimeStamp(), ecorePackage.getEString(), "timeStamp", null, 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_ContainerContents(), ecorePackage.getEString(), "containerContents", null, 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(validationSeverityEEnum, ValidationSeverity.class, "ValidationSeverity");
		addEEnumLiteral(validationSeverityEEnum, ValidationSeverity.ERROR);
		addEEnumLiteral(validationSeverityEEnum, ValidationSeverity.WARNING);
		addEEnumLiteral(validationSeverityEEnum, ValidationSeverity.SUCCESS);

		initEEnum(objectScopeEEnum, ObjectScope.class, "ObjectScope");
		addEEnumLiteral(objectScopeEEnum, ObjectScope.BETRACHTUNG);
		addEEnumLiteral(objectScopeEEnum, ObjectScope.PLAN);
		addEEnumLiteral(objectScopeEEnum, ObjectScope.UNKNOWN);

		initEEnum(containerContentEEnum, ContainerContent.class, "ContainerContent");
		addEEnumLiteral(containerContentEEnum, ContainerContent.MODEL);
		addEEnumLiteral(containerContentEEnum, ContainerContent.LAYOUT);
		addEEnumLiteral(containerContentEEnum, ContainerContent.ATTACHMENT);

		// Create resource
		createResource(eNS_URI);
	}

} //ValidationreportPackageImpl
