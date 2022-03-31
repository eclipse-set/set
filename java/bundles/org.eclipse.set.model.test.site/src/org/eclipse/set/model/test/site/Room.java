/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Room</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.Room#getNames <em>Names</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Room#getFloorID <em>Floor ID</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Room#getWindows <em>Windows</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.test.site.SitePackage#getRoom()
 * @model
 * @generated
 */
public interface Room extends Identified {
	/**
	 * Returns the value of the '<em><b>Names</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Names</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Names</em>' containment reference.
	 * @see #setNames(RoomNames)
	 * @see org.eclipse.set.model.test.site.SitePackage#getRoom_Names()
	 * @model containment="true" required="true"
	 * @generated
	 */
	RoomNames getNames();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.Room#getNames <em>Names</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Names</em>' containment reference.
	 * @see #getNames()
	 * @generated
	 */
	void setNames(RoomNames value);

	/**
	 * Returns the value of the '<em><b>Floor ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Floor ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Floor ID</em>' attribute.
	 * @see #setFloorID(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getRoom_FloorID()
	 * @model
	 * @generated
	 */
	String getFloorID();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.Room#getFloorID <em>Floor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Floor ID</em>' attribute.
	 * @see #getFloorID()
	 * @generated
	 */
	void setFloorID(String value);

	/**
	 * Returns the value of the '<em><b>Windows</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.test.site.Window}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Windows</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Windows</em>' containment reference list.
	 * @see org.eclipse.set.model.test.site.SitePackage#getRoom_Windows()
	 * @model containment="true"
	 * @generated
	 */
	EList<Window> getWindows();

} // Room
