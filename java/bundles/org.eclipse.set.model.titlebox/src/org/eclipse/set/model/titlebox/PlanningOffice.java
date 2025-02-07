/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 */
package org.eclipse.set.model.titlebox;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Planning
 * Office</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.titlebox.PlanningOffice#getVariant
 * <em>Variant</em>}</li>
 * <li>{@link org.eclipse.set.model.titlebox.PlanningOffice#getName
 * <em>Name</em>}</li>
 * <li>{@link org.eclipse.set.model.titlebox.PlanningOffice#getGroup
 * <em>Group</em>}</li>
 * <li>{@link org.eclipse.set.model.titlebox.PlanningOffice#getLocation
 * <em>Location</em>}</li>
 * <li>{@link org.eclipse.set.model.titlebox.PlanningOffice#getPhone
 * <em>Phone</em>}</li>
 * <li>{@link org.eclipse.set.model.titlebox.PlanningOffice#getEmail
 * <em>Email</em>}</li>
 * <li>{@link org.eclipse.set.model.titlebox.PlanningOffice#getLogo
 * <em>Logo</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getPlanningOffice()
 * @model
 * @generated
 */
public interface PlanningOffice extends EObject {
	/**
	 * Returns the value of the '<em><b>Variant</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Variant</em>' attribute.
	 * @see #setVariant(String)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getPlanningOffice_Variant()
	 * @model
	 * @generated
	 */
	String getVariant();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getVariant
	 * <em>Variant</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Variant</em>' attribute.
	 * @see #getVariant()
	 * @generated
	 */
	void setVariant(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' containment reference.
	 * @see #setName(StringField)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getPlanningOffice_Name()
	 * @model containment="true"
	 * @generated
	 */
	StringField getName();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getName
	 * <em>Name</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(StringField value);

	/**
	 * Returns the value of the '<em><b>Group</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Group</em>' containment reference.
	 * @see #setGroup(StringField)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getPlanningOffice_Group()
	 * @model containment="true"
	 * @generated
	 */
	StringField getGroup();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getGroup
	 * <em>Group</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Group</em>' containment reference.
	 * @see #getGroup()
	 * @generated
	 */
	void setGroup(StringField value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Location</em>' containment reference.
	 * @see #setLocation(StringField)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getPlanningOffice_Location()
	 * @model containment="true"
	 * @generated
	 */
	StringField getLocation();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getLocation
	 * <em>Location</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Location</em>' containment
	 *            reference.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(StringField value);

	/**
	 * Returns the value of the '<em><b>Phone</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Phone</em>' containment reference.
	 * @see #setPhone(StringField)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getPlanningOffice_Phone()
	 * @model containment="true"
	 * @generated
	 */
	StringField getPhone();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getPhone
	 * <em>Phone</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Phone</em>' containment reference.
	 * @see #getPhone()
	 * @generated
	 */
	void setPhone(StringField value);

	/**
	 * Returns the value of the '<em><b>Email</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Email</em>' containment reference.
	 * @see #setEmail(StringField)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getPlanningOffice_Email()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	StringField getEmail();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getEmail
	 * <em>Email</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Email</em>' containment reference.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(StringField value);

	/**
	 * Returns the value of the '<em><b>Logo</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Logo</em>' attribute.
	 * @see #setLogo(String)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getPlanningOffice_Logo()
	 * @model
	 * @generated
	 */
	String getLogo();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getLogo
	 * <em>Logo</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Logo</em>' attribute.
	 * @see #getLogo()
	 * @generated
	 */
	void setLogo(String value);

} // PlanningOffice
