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
 * A representation of the model object '<em><b>Room Names</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.RoomNames#getDoorSign <em>Door Sign</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.RoomNames#getFloorPlan <em>Floor Plan</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.test.site.SitePackage#getRoomNames()
 * @model
 * @generated
 */
public interface RoomNames extends EObject {
	/**
	 * Returns the value of the '<em><b>Door Sign</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Door Sign</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Door Sign</em>' attribute.
	 * @see #setDoorSign(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getRoomNames_DoorSign()
	 * @model
	 * @generated
	 */
	String getDoorSign();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.RoomNames#getDoorSign <em>Door Sign</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Door Sign</em>' attribute.
	 * @see #getDoorSign()
	 * @generated
	 */
	void setDoorSign(String value);

	/**
	 * Returns the value of the '<em><b>Floor Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Floor Plan</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Floor Plan</em>' attribute.
	 * @see #setFloorPlan(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getRoomNames_FloorPlan()
	 * @model
	 * @generated
	 */
	String getFloorPlan();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.RoomNames#getFloorPlan <em>Floor Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Floor Plan</em>' attribute.
	 * @see #getFloorPlan()
	 * @generated
	 */
	void setFloorPlan(String value);

} // RoomNames
