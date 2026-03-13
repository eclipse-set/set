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
 * A representation of the model object '<em><b>Object Quantity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.integrationview.ObjectQuantity#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.ObjectQuantity#getInitial <em>Initial</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.ObjectQuantity#getFinal <em>Final</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getObjectQuantity()
 * @model
 * @generated
 */
public interface ObjectQuantity extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getObjectQuantity_Source()
	 * @model
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.ObjectQuantity#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

	/**
	 * Returns the value of the '<em><b>Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial</em>' attribute.
	 * @see #setInitial(int)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getObjectQuantity_Initial()
	 * @model
	 * @generated
	 */
	int getInitial();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.ObjectQuantity#getInitial <em>Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial</em>' attribute.
	 * @see #getInitial()
	 * @generated
	 */
	void setInitial(int value);

	/**
	 * Returns the value of the '<em><b>Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Final</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Final</em>' attribute.
	 * @see #setFinal(int)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getObjectQuantity_Final()
	 * @model
	 * @generated
	 */
	int getFinal();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.ObjectQuantity#getFinal <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Final</em>' attribute.
	 * @see #getFinal()
	 * @generated
	 */
	void setFinal(int value);

} // ObjectQuantity
