/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Floor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.Floor#getNames <em>Names</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Floor#getBuildingID <em>Building ID</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.test.site.SitePackage#getFloor()
 * @model
 * @generated
 */
public interface Floor extends Identified {
	/**
	 * Returns the value of the '<em><b>Names</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Names</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Names</em>' containment reference.
	 * @see #setNames(FloorNames)
	 * @see org.eclipse.set.model.test.site.SitePackage#getFloor_Names()
	 * @model containment="true" required="true"
	 * @generated
	 */
	FloorNames getNames();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.Floor#getNames <em>Names</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Names</em>' containment reference.
	 * @see #getNames()
	 * @generated
	 */
	void setNames(FloorNames value);

	/**
	 * Returns the value of the '<em><b>Building ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Building ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Building ID</em>' attribute.
	 * @see #setBuildingID(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getFloor_BuildingID()
	 * @model
	 * @generated
	 */
	String getBuildingID();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.Floor#getBuildingID <em>Building ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Building ID</em>' attribute.
	 * @see #getBuildingID()
	 * @generated
	 */
	void setBuildingID(String value);

} // Floor
