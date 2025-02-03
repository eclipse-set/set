/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Details</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.integrationview.Details#getAttributePath <em>Attribute Path</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.Details#getValuePrimaryPlanning <em>Value Primary Planning</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.Details#getValueSecondaryPlanning <em>Value Secondary Planning</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getDetails()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='equalPlanningValues'"
 * @generated
 */
public interface Details extends EObject {
	/**
	 * Returns the value of the '<em><b>Attribute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Path</em>' attribute.
	 * @see #setAttributePath(String)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getDetails_AttributePath()
	 * @model
	 * @generated
	 */
	String getAttributePath();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.Details#getAttributePath <em>Attribute Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Path</em>' attribute.
	 * @see #getAttributePath()
	 * @generated
	 */
	void setAttributePath(String value);

	/**
	 * Returns the value of the '<em><b>Value Primary Planning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Primary Planning</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Primary Planning</em>' attribute.
	 * @see #setValuePrimaryPlanning(String)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getDetails_ValuePrimaryPlanning()
	 * @model required="true"
	 * @generated
	 */
	String getValuePrimaryPlanning();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.Details#getValuePrimaryPlanning <em>Value Primary Planning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Primary Planning</em>' attribute.
	 * @see #getValuePrimaryPlanning()
	 * @generated
	 */
	void setValuePrimaryPlanning(String value);

	/**
	 * Returns the value of the '<em><b>Value Secondary Planning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Secondary Planning</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Secondary Planning</em>' attribute.
	 * @see #setValueSecondaryPlanning(String)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getDetails_ValueSecondaryPlanning()
	 * @model
	 * @generated
	 */
	String getValueSecondaryPlanning();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.Details#getValueSecondaryPlanning <em>Value Secondary Planning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Secondary Planning</em>' attribute.
	 * @see #getValueSecondaryPlanning()
	 * @generated
	 */
	void setValueSecondaryPlanning(String value);

} // Details
