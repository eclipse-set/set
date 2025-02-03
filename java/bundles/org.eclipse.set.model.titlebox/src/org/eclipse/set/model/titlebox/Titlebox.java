/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Titlebox</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.titlebox.Titlebox#getFieldList <em>Field</em>}</li>
 *   <li>{@link org.eclipse.set.model.titlebox.Titlebox#getPlanningOffice <em>Planning Office</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getTitlebox()
 * @model
 * @generated
 */
public interface Titlebox extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String[] getField();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String getField(int index);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	int getFieldLength();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	void setField(String[] newField);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	void setField(int index, String element);

	/**
	 * Returns the value of the '<em><b>Field</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Field</em>' attribute list.
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getTitlebox_Field()
	 * @model unique="false" required="true" upper="100"
	 * @generated
	 */
	EList<String> getFieldList();

	/**
	 * Returns the value of the '<em><b>Planning Office</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Planning Office</em>' containment reference.
	 * @see #setPlanningOffice(PlanningOffice)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getTitlebox_PlanningOffice()
	 * @model containment="true"
	 * @generated
	 */
	PlanningOffice getPlanningOffice();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.titlebox.Titlebox#getPlanningOffice <em>Planning Office</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Planning Office</em>' containment reference.
	 * @see #getPlanningOffice()
	 * @generated
	 */
	void setPlanningOffice(PlanningOffice value);

} // Titlebox
