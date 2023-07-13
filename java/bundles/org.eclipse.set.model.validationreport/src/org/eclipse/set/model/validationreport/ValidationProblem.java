/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Validation Problem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getSeverityText <em>Severity Text</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectArt <em>Object Art</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getAttributeName <em>Attribute Name</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectScope <em>Object Scope</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectState <em>Object State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem()
 * @model
 * @generated
 */
public interface ValidationProblem extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_Id()
	 * @model
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.validationreport.ValidationSeverity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Severity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.set.model.validationreport.ValidationSeverity
	 * @see #setSeverity(ValidationSeverity)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_Severity()
	 * @model
	 * @generated
	 */
	ValidationSeverity getSeverity();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getSeverity <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.set.model.validationreport.ValidationSeverity
	 * @see #getSeverity()
	 * @generated
	 */
	void setSeverity(ValidationSeverity value);

	/**
	 * Returns the value of the '<em><b>Severity Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Severity Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Severity Text</em>' attribute.
	 * @see #setSeverityText(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_SeverityText()
	 * @model
	 * @generated
	 */
	String getSeverityText();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getSeverityText <em>Severity Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Severity Text</em>' attribute.
	 * @see #getSeverityText()
	 * @generated
	 */
	void setSeverityText(String value);

	/**
	 * Returns the value of the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Number</em>' attribute.
	 * @see #setLineNumber(int)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_LineNumber()
	 * @model
	 * @generated
	 */
	int getLineNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getLineNumber <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Number</em>' attribute.
	 * @see #getLineNumber()
	 * @generated
	 */
	void setLineNumber(int value);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Object Art</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Art</em>' attribute.
	 * @see #setObjectArt(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_ObjectArt()
	 * @model
	 * @generated
	 */
	String getObjectArt();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectArt <em>Object Art</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Art</em>' attribute.
	 * @see #getObjectArt()
	 * @generated
	 */
	void setObjectArt(String value);

	/**
	 * Returns the value of the '<em><b>Attribute Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Name</em>' attribute.
	 * @see #setAttributeName(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_AttributeName()
	 * @model
	 * @generated
	 */
	String getAttributeName();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getAttributeName <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Name</em>' attribute.
	 * @see #getAttributeName()
	 * @generated
	 */
	void setAttributeName(String value);

	/**
	 * Returns the value of the '<em><b>Object Scope</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.set.model.validationreport.ObjectScope}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Scope</em>' attribute.
	 * @see org.eclipse.set.model.validationreport.ObjectScope
	 * @see #setObjectScope(ObjectScope)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_ObjectScope()
	 * @model default=""
	 * @generated
	 */
	ObjectScope getObjectScope();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectScope <em>Object Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Scope</em>' attribute.
	 * @see org.eclipse.set.model.validationreport.ObjectScope
	 * @see #getObjectScope()
	 * @generated
	 */
	void setObjectScope(ObjectScope value);

	/**
	 * Returns the value of the '<em><b>Object State</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.set.model.validationreport.ObjectState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object State</em>' attribute.
	 * @see org.eclipse.set.model.validationreport.ObjectState
	 * @see #setObjectState(ObjectState)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getValidationProblem_ObjectState()
	 * @model default=""
	 * @generated
	 */
	ObjectState getObjectState();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.ValidationProblem#getObjectState <em>Object State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object State</em>' attribute.
	 * @see org.eclipse.set.model.validationreport.ObjectState
	 * @see #getObjectState()
	 * @generated
	 */
	void setObjectState(ObjectState value);

} // ValidationProblem
