/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Validation Report</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getFileName <em>File Name</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getModelLoaded <em>Model Loaded</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getValid <em>Valid</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getXsdValid <em>Xsd Valid</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getEmfValid <em>Emf Valid</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getProblems <em>Problems</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getSupportedVersion <em>Supported Version</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getUsedVersion <em>Used Version</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getToolboxVersion <em>Toolbox Version</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getSubworkCount <em>Subwork Count</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationReport#getSubworkTypes <em>Subwork Types</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport()
 * @model
 * @generated
 */
public interface ValidationReport extends EObject {
	/**
	 * Returns the value of the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Name</em>' attribute.
	 * @see #setFileName(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_FileName()
	 * @model
	 * @generated
	 */
	String getFileName();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getFileName <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Name</em>' attribute.
	 * @see #getFileName()
	 * @generated
	 */
	void setFileName(String value);

	/**
	 * Returns the value of the '<em><b>Model Loaded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Loaded</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Loaded</em>' attribute.
	 * @see #setModelLoaded(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_ModelLoaded()
	 * @model
	 * @generated
	 */
	String getModelLoaded();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getModelLoaded <em>Model Loaded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Loaded</em>' attribute.
	 * @see #getModelLoaded()
	 * @generated
	 */
	void setModelLoaded(String value);

	/**
	 * Returns the value of the '<em><b>Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valid</em>' attribute.
	 * @see #setValid(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_Valid()
	 * @model
	 * @generated
	 */
	String getValid();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getValid <em>Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid</em>' attribute.
	 * @see #getValid()
	 * @generated
	 */
	void setValid(String value);

	/**
	 * Returns the value of the '<em><b>Xsd Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xsd Valid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xsd Valid</em>' attribute.
	 * @see #setXsdValid(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_XsdValid()
	 * @model
	 * @generated
	 */
	String getXsdValid();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getXsdValid <em>Xsd Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xsd Valid</em>' attribute.
	 * @see #getXsdValid()
	 * @generated
	 */
	void setXsdValid(String value);

	/**
	 * Returns the value of the '<em><b>Emf Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Emf Valid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Emf Valid</em>' attribute.
	 * @see #setEmfValid(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_EmfValid()
	 * @model
	 * @generated
	 */
	String getEmfValid();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getEmfValid <em>Emf Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Emf Valid</em>' attribute.
	 * @see #getEmfValid()
	 * @generated
	 */
	void setEmfValid(String value);

	/**
	 * Returns the value of the '<em><b>Problems</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.validationreport.ValidationProblem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Problems</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Problems</em>' containment reference list.
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_Problems()
	 * @model containment="true"
	 * @generated
	 */
	EList<ValidationProblem> getProblems();

	/**
	 * Returns the value of the '<em><b>Supported Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Supported Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Supported Version</em>' containment reference.
	 * @see #setSupportedVersion(VersionInfo)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_SupportedVersion()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VersionInfo getSupportedVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getSupportedVersion <em>Supported Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Supported Version</em>' containment reference.
	 * @see #getSupportedVersion()
	 * @generated
	 */
	void setSupportedVersion(VersionInfo value);

	/**
	 * Returns the value of the '<em><b>Used Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Used Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Used Version</em>' containment reference.
	 * @see #setUsedVersion(VersionInfo)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_UsedVersion()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VersionInfo getUsedVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getUsedVersion <em>Used Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Used Version</em>' containment reference.
	 * @see #getUsedVersion()
	 * @generated
	 */
	void setUsedVersion(VersionInfo value);

	/**
	 * Returns the value of the '<em><b>Toolbox Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Toolbox Version</em>' attribute.
	 * @see #setToolboxVersion(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_ToolboxVersion()
	 * @model
	 * @generated
	 */
	String getToolboxVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getToolboxVersion <em>Toolbox Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Toolbox Version</em>' attribute.
	 * @see #getToolboxVersion()
	 * @generated
	 */
	void setToolboxVersion(String value);

	/**
	 * Returns the value of the '<em><b>Subwork Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subwork Count</em>' attribute.
	 * @see #setSubworkCount(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_SubworkCount()
	 * @model
	 * @generated
	 */
	String getSubworkCount();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getSubworkCount <em>Subwork Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subwork Count</em>' attribute.
	 * @see #getSubworkCount()
	 * @generated
	 */
	void setSubworkCount(String value);

	/**
	 * Returns the value of the '<em><b>Subwork Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subwork Types</em>' attribute.
	 * @see #setSubworkTypes(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationReport_SubworkTypes()
	 * @model
	 * @generated
	 */
	String getSubworkTypes();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationReport#getSubworkTypes <em>Subwork Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subwork Types</em>' attribute.
	 * @see #getSubworkTypes()
	 * @generated
	 */
	void setSubworkTypes(String value);

} // ValidationReport
