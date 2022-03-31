/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Building Names</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.BuildingNames#getAddress <em>Address</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.BuildingNames#getEntrance <em>Entrance</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.BuildingNames#getSitePlan <em>Site Plan</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.test.site.SitePackage#getBuildingNames()
 * @model
 * @generated
 */
public interface BuildingNames extends EObject {
	/**
	 * Returns the value of the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' attribute.
	 * @see #setAddress(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getBuildingNames_Address()
	 * @model
	 * @generated
	 */
	String getAddress();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.BuildingNames#getAddress <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address</em>' attribute.
	 * @see #getAddress()
	 * @generated
	 */
	void setAddress(String value);

	/**
	 * Returns the value of the '<em><b>Entrance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entrance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entrance</em>' attribute.
	 * @see #setEntrance(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getBuildingNames_Entrance()
	 * @model
	 * @generated
	 */
	String getEntrance();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.BuildingNames#getEntrance <em>Entrance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entrance</em>' attribute.
	 * @see #getEntrance()
	 * @generated
	 */
	void setEntrance(String value);

	/**
	 * Returns the value of the '<em><b>Site Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Site Plan</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Site Plan</em>' attribute.
	 * @see #setSitePlan(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getBuildingNames_SitePlan()
	 * @model
	 * @generated
	 */
	String getSitePlan();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.BuildingNames#getSitePlan <em>Site Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Site Plan</em>' attribute.
	 * @see #getSitePlan()
	 * @generated
	 */
	void setSitePlan(String value);

} // BuildingNames
