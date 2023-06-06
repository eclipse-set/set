/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.plazmodel;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.set.model.validationreport.ValidationSeverity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.plazmodel.PlazError#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.plazmodel.PlazError#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.set.model.plazmodel.PlazError#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.eclipse.set.model.plazmodel.PlazError#getObject <em>Object</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.plazmodel.PlazPackage#getPlazError()
 * @model
 * @generated
 */
public interface PlazError extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.set.model.plazmodel.PlazPackage#getPlazError_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.plazmodel.PlazError#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.eclipse.set.model.plazmodel.PlazPackage#getPlazError_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.plazmodel.PlazError#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.validationreport.ValidationSeverity}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.set.model.validationreport.ValidationSeverity
	 * @see #setSeverity(ValidationSeverity)
	 * @see org.eclipse.set.model.plazmodel.PlazPackage#getPlazError_Severity()
	 * @model
	 * @generated
	 */
	ValidationSeverity getSeverity();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.plazmodel.PlazError#getSeverity <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.set.model.validationreport.ValidationSeverity
	 * @see #getSeverity()
	 * @generated
	 */
	void setSeverity(ValidationSeverity value);

	/**
	 * Returns the value of the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object</em>' reference.
	 * @see #setObject(EObject)
	 * @see org.eclipse.set.model.plazmodel.PlazPackage#getPlazError_Object()
	 * @model
	 * @generated
	 */
	EObject getObject();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.plazmodel.PlazError#getObject <em>Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object</em>' reference.
	 * @see #getObject()
	 * @generated
	 */
	void setObject(EObject value);

} // PlazError
