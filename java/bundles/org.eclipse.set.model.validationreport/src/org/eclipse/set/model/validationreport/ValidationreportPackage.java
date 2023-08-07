/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.eclipse.set.model.validationreport.ValidationreportFactory
 * @model kind="package"
 * @generated
 */
public interface ValidationreportPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "validationreport";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2018-09-06:planpro/vr";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "vr";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ValidationreportPackage eINSTANCE = org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl <em>Validation Report</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.validationreport.impl.ValidationReportImpl
	 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getValidationReport()
	 * @generated
	 */
	int VALIDATION_REPORT = 0;

	/**
	 * The feature id for the '<em><b>Model Loaded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__MODEL_LOADED = 0;

	/**
	 * The feature id for the '<em><b>Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__VALID = 1;

	/**
	 * The feature id for the '<em><b>Xsd Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__XSD_VALID = 2;

	/**
	 * The feature id for the '<em><b>Emf Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__EMF_VALID = 3;

	/**
	 * The feature id for the '<em><b>Problems</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__PROBLEMS = 4;

	/**
	 * The feature id for the '<em><b>Supported Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__SUPPORTED_VERSION = 5;

	/**
	 * The feature id for the '<em><b>Toolbox Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__TOOLBOX_VERSION = 6;

	/**
	 * The feature id for the '<em><b>Subwork Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__SUBWORK_COUNT = 7;

	/**
	 * The feature id for the '<em><b>Subwork Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__SUBWORK_TYPES = 8;

	/**
	 * The feature id for the '<em><b>File Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT__FILE_INFO = 9;

	/**
	 * The number of structural features of the '<em>Validation Report</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Validation Report</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_REPORT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl <em>Validation Problem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.validationreport.impl.ValidationProblemImpl
	 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getValidationProblem()
	 * @generated
	 */
	int VALIDATION_PROBLEM = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__ID = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__SEVERITY = 2;

	/**
	 * The feature id for the '<em><b>Severity Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__SEVERITY_TEXT = 3;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__LINE_NUMBER = 4;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__MESSAGE = 5;

	/**
	 * The feature id for the '<em><b>Object Art</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__OBJECT_ART = 6;

	/**
	 * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__ATTRIBUTE_NAME = 7;

	/**
	 * The feature id for the '<em><b>Object Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__OBJECT_SCOPE = 8;

	/**
	 * The feature id for the '<em><b>Object State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM__OBJECT_STATE = 9;

	/**
	 * The number of structural features of the '<em>Validation Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Validation Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_PROBLEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.validationreport.impl.VersionInfoImpl <em>Version Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.validationreport.impl.VersionInfoImpl
	 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getVersionInfo()
	 * @generated
	 */
	int VERSION_INFO = 2;

	/**
	 * The feature id for the '<em><b>Plan Pro</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__PLAN_PRO = 0;

	/**
	 * The feature id for the '<em><b>Signals</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__SIGNALS = 1;

	/**
	 * The number of structural features of the '<em>Version Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Version Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.validationreport.impl.FileInfoImpl <em>File Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.validationreport.impl.FileInfoImpl
	 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getFileInfo()
	 * @generated
	 */
	int FILE_INFO = 3;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__FILE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Used Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__USED_VERSION = 1;

	/**
	 * The feature id for the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__CHECKSUM = 2;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__GUID = 3;

	/**
	 * The feature id for the '<em><b>Time Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__TIME_STAMP = 4;

	/**
	 * The feature id for the '<em><b>Container Contents</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__CONTAINER_CONTENTS = 5;

	/**
	 * The number of structural features of the '<em>File Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>File Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.validationreport.ValidationSeverity <em>Validation Severity</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.validationreport.ValidationSeverity
	 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getValidationSeverity()
	 * @generated
	 */
	int VALIDATION_SEVERITY = 4;


	/**
	 * The meta object id for the '{@link org.eclipse.set.model.validationreport.ObjectScope <em>Object Scope</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.validationreport.ObjectScope
	 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getObjectScope()
	 * @generated
	 */
	int OBJECT_SCOPE = 5;


	/**
	 * The meta object id for the '{@link org.eclipse.set.model.validationreport.ContainerContent <em>Container Content</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.validationreport.ContainerContent
	 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getContainerContent()
	 * @generated
	 */
	int CONTAINER_CONTENT = 6;


	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.validationreport.ValidationReport <em>Validation Report</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Validation Report</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport
	 * @generated
	 */
	EClass getValidationReport();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationReport#getModelLoaded <em>Model Loaded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model Loaded</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getModelLoaded()
	 * @see #getValidationReport()
	 * @generated
	 */
	EAttribute getValidationReport_ModelLoaded();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationReport#getValid <em>Valid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getValid()
	 * @see #getValidationReport()
	 * @generated
	 */
	EAttribute getValidationReport_Valid();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationReport#getXsdValid <em>Xsd Valid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xsd Valid</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getXsdValid()
	 * @see #getValidationReport()
	 * @generated
	 */
	EAttribute getValidationReport_XsdValid();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationReport#getEmfValid <em>Emf Valid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Emf Valid</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getEmfValid()
	 * @see #getValidationReport()
	 * @generated
	 */
	EAttribute getValidationReport_EmfValid();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.validationreport.ValidationReport#getProblems <em>Problems</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Problems</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getProblems()
	 * @see #getValidationReport()
	 * @generated
	 */
	EReference getValidationReport_Problems();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.validationreport.ValidationReport#getSupportedVersion <em>Supported Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Supported Version</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getSupportedVersion()
	 * @see #getValidationReport()
	 * @generated
	 */
	EReference getValidationReport_SupportedVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationReport#getToolboxVersion <em>Toolbox Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Toolbox Version</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getToolboxVersion()
	 * @see #getValidationReport()
	 * @generated
	 */
	EAttribute getValidationReport_ToolboxVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationReport#getSubworkCount <em>Subwork Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subwork Count</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getSubworkCount()
	 * @see #getValidationReport()
	 * @generated
	 */
	EAttribute getValidationReport_SubworkCount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationReport#getSubworkTypes <em>Subwork Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subwork Types</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getSubworkTypes()
	 * @see #getValidationReport()
	 * @generated
	 */
	EAttribute getValidationReport_SubworkTypes();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.validationreport.ValidationReport#getFileInfo <em>File Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>File Info</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationReport#getFileInfo()
	 * @see #getValidationReport()
	 * @generated
	 */
	EReference getValidationReport_FileInfo();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.validationreport.ValidationProblem <em>Validation Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Validation Problem</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem
	 * @generated
	 */
	EClass getValidationProblem();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getId()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getType()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getSeverity()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_Severity();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getSeverityText <em>Severity Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity Text</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getSeverityText()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_SeverityText();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getLineNumber()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_LineNumber();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getMessage()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_Message();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectArt <em>Object Art</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Art</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getObjectArt()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_ObjectArt();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getAttributeName <em>Attribute Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Name</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getAttributeName()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_AttributeName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectScope <em>Object Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Scope</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getObjectScope()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_ObjectScope();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectState <em>Object State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object State</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationProblem#getObjectState()
	 * @see #getValidationProblem()
	 * @generated
	 */
	EAttribute getValidationProblem_ObjectState();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.validationreport.VersionInfo <em>Version Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Info</em>'.
	 * @see org.eclipse.set.model.validationreport.VersionInfo
	 * @generated
	 */
	EClass getVersionInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.VersionInfo#getPlanPro <em>Plan Pro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plan Pro</em>'.
	 * @see org.eclipse.set.model.validationreport.VersionInfo#getPlanPro()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_PlanPro();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.VersionInfo#getSignals <em>Signals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Signals</em>'.
	 * @see org.eclipse.set.model.validationreport.VersionInfo#getSignals()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Signals();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.validationreport.FileInfo <em>File Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Info</em>'.
	 * @see org.eclipse.set.model.validationreport.FileInfo
	 * @generated
	 */
	EClass getFileInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.FileInfo#getFileName <em>File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see org.eclipse.set.model.validationreport.FileInfo#getFileName()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_FileName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.validationreport.FileInfo#getUsedVersion <em>Used Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Used Version</em>'.
	 * @see org.eclipse.set.model.validationreport.FileInfo#getUsedVersion()
	 * @see #getFileInfo()
	 * @generated
	 */
	EReference getFileInfo_UsedVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.FileInfo#getChecksum <em>Checksum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Checksum</em>'.
	 * @see org.eclipse.set.model.validationreport.FileInfo#getChecksum()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_Checksum();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.FileInfo#getGuid <em>Guid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guid</em>'.
	 * @see org.eclipse.set.model.validationreport.FileInfo#getGuid()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_Guid();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.FileInfo#getTimeStamp <em>Time Stamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Stamp</em>'.
	 * @see org.eclipse.set.model.validationreport.FileInfo#getTimeStamp()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_TimeStamp();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.validationreport.FileInfo#getContainerContents <em>Container Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Container Contents</em>'.
	 * @see org.eclipse.set.model.validationreport.FileInfo#getContainerContents()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_ContainerContents();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.set.model.validationreport.ValidationSeverity <em>Validation Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Validation Severity</em>'.
	 * @see org.eclipse.set.model.validationreport.ValidationSeverity
	 * @generated
	 */
	EEnum getValidationSeverity();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.set.model.validationreport.ObjectScope <em>Object Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Object Scope</em>'.
	 * @see org.eclipse.set.model.validationreport.ObjectScope
	 * @generated
	 */
	EEnum getObjectScope();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.set.model.validationreport.ContainerContent <em>Container Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Container Content</em>'.
	 * @see org.eclipse.set.model.validationreport.ContainerContent
	 * @generated
	 */
	EEnum getContainerContent();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ValidationreportFactory getValidationreportFactory();

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
		 * The meta object literal for the '{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl <em>Validation Report</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.validationreport.impl.ValidationReportImpl
		 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getValidationReport()
		 * @generated
		 */
		EClass VALIDATION_REPORT = eINSTANCE.getValidationReport();

		/**
		 * The meta object literal for the '<em><b>Model Loaded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_REPORT__MODEL_LOADED = eINSTANCE.getValidationReport_ModelLoaded();

		/**
		 * The meta object literal for the '<em><b>Valid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_REPORT__VALID = eINSTANCE.getValidationReport_Valid();

		/**
		 * The meta object literal for the '<em><b>Xsd Valid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_REPORT__XSD_VALID = eINSTANCE.getValidationReport_XsdValid();

		/**
		 * The meta object literal for the '<em><b>Emf Valid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_REPORT__EMF_VALID = eINSTANCE.getValidationReport_EmfValid();

		/**
		 * The meta object literal for the '<em><b>Problems</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALIDATION_REPORT__PROBLEMS = eINSTANCE.getValidationReport_Problems();

		/**
		 * The meta object literal for the '<em><b>Supported Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALIDATION_REPORT__SUPPORTED_VERSION = eINSTANCE.getValidationReport_SupportedVersion();

		/**
		 * The meta object literal for the '<em><b>Toolbox Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_REPORT__TOOLBOX_VERSION = eINSTANCE.getValidationReport_ToolboxVersion();

		/**
		 * The meta object literal for the '<em><b>Subwork Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_REPORT__SUBWORK_COUNT = eINSTANCE.getValidationReport_SubworkCount();

		/**
		 * The meta object literal for the '<em><b>Subwork Types</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_REPORT__SUBWORK_TYPES = eINSTANCE.getValidationReport_SubworkTypes();

		/**
		 * The meta object literal for the '<em><b>File Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALIDATION_REPORT__FILE_INFO = eINSTANCE.getValidationReport_FileInfo();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl <em>Validation Problem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.validationreport.impl.ValidationProblemImpl
		 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getValidationProblem()
		 * @generated
		 */
		EClass VALIDATION_PROBLEM = eINSTANCE.getValidationProblem();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__ID = eINSTANCE.getValidationProblem_Id();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__TYPE = eINSTANCE.getValidationProblem_Type();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__SEVERITY = eINSTANCE.getValidationProblem_Severity();

		/**
		 * The meta object literal for the '<em><b>Severity Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__SEVERITY_TEXT = eINSTANCE.getValidationProblem_SeverityText();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__LINE_NUMBER = eINSTANCE.getValidationProblem_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__MESSAGE = eINSTANCE.getValidationProblem_Message();

		/**
		 * The meta object literal for the '<em><b>Object Art</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__OBJECT_ART = eINSTANCE.getValidationProblem_ObjectArt();

		/**
		 * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__ATTRIBUTE_NAME = eINSTANCE.getValidationProblem_AttributeName();

		/**
		 * The meta object literal for the '<em><b>Object Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__OBJECT_SCOPE = eINSTANCE.getValidationProblem_ObjectScope();

		/**
		 * The meta object literal for the '<em><b>Object State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_PROBLEM__OBJECT_STATE = eINSTANCE.getValidationProblem_ObjectState();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.validationreport.impl.VersionInfoImpl <em>Version Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.validationreport.impl.VersionInfoImpl
		 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getVersionInfo()
		 * @generated
		 */
		EClass VERSION_INFO = eINSTANCE.getVersionInfo();

		/**
		 * The meta object literal for the '<em><b>Plan Pro</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__PLAN_PRO = eINSTANCE.getVersionInfo_PlanPro();

		/**
		 * The meta object literal for the '<em><b>Signals</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__SIGNALS = eINSTANCE.getVersionInfo_Signals();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.validationreport.impl.FileInfoImpl <em>File Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.validationreport.impl.FileInfoImpl
		 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getFileInfo()
		 * @generated
		 */
		EClass FILE_INFO = eINSTANCE.getFileInfo();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__FILE_NAME = eINSTANCE.getFileInfo_FileName();

		/**
		 * The meta object literal for the '<em><b>Used Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_INFO__USED_VERSION = eINSTANCE.getFileInfo_UsedVersion();

		/**
		 * The meta object literal for the '<em><b>Checksum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__CHECKSUM = eINSTANCE.getFileInfo_Checksum();

		/**
		 * The meta object literal for the '<em><b>Guid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__GUID = eINSTANCE.getFileInfo_Guid();

		/**
		 * The meta object literal for the '<em><b>Time Stamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__TIME_STAMP = eINSTANCE.getFileInfo_TimeStamp();

		/**
		 * The meta object literal for the '<em><b>Container Contents</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__CONTAINER_CONTENTS = eINSTANCE.getFileInfo_ContainerContents();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.validationreport.ValidationSeverity <em>Validation Severity</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.validationreport.ValidationSeverity
		 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getValidationSeverity()
		 * @generated
		 */
		EEnum VALIDATION_SEVERITY = eINSTANCE.getValidationSeverity();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.validationreport.ObjectScope <em>Object Scope</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.validationreport.ObjectScope
		 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getObjectScope()
		 * @generated
		 */
		EEnum OBJECT_SCOPE = eINSTANCE.getObjectScope();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.validationreport.ContainerContent <em>Container Content</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.validationreport.ContainerContent
		 * @see org.eclipse.set.model.validationreport.impl.ValidationreportPackageImpl#getContainerContent()
		 * @generated
		 */
		EEnum CONTAINER_CONTENT = eINSTANCE.getContainerContent();

	}

} //ValidationreportPackage
