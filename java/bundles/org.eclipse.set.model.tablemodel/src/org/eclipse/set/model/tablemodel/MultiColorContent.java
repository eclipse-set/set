/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Color Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.MultiColorContent#getMultiColorValue <em>Multi Color Value</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.MultiColorContent#getStringFormat <em>String Format</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getMultiColorContent()
 * @model
 * @generated
 */
public interface MultiColorContent extends EObject {
	/**
	 * Returns the value of the '<em><b>Multi Color Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Color Value</em>' attribute.
	 * @see #setMultiColorValue(String)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getMultiColorContent_MultiColorValue()
	 * @model
	 * @generated
	 */
	String getMultiColorValue();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.tablemodel.MultiColorContent#getMultiColorValue <em>Multi Color Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multi Color Value</em>' attribute.
	 * @see #getMultiColorValue()
	 * @generated
	 */
	void setMultiColorValue(String value);

	/**
	 * Returns the value of the '<em><b>String Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Format</em>' attribute.
	 * @see #setStringFormat(String)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getMultiColorContent_StringFormat()
	 * @model
	 * @generated
	 */
	String getStringFormat();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.tablemodel.MultiColorContent#getStringFormat <em>String Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Format</em>' attribute.
	 * @see #getStringFormat()
	 * @generated
	 */
	void setStringFormat(String value);

} // MultiColorContent
