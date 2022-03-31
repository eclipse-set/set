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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Site</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.Site#getAllBuildings <em>All Buildings</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Site#getAllRooms <em>All Rooms</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Site#getAllFloors <em>All Floors</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Site#getAllPassages <em>All Passages</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.test.site.SitePackage#getSite()
 * @model
 * @generated
 */
public interface Site extends EObject {
	/**
	 * Returns the value of the '<em><b>All Buildings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.test.site.Building}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Buildings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Buildings</em>' containment reference list.
	 * @see org.eclipse.set.model.test.site.SitePackage#getSite_AllBuildings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Building> getAllBuildings();

	/**
	 * Returns the value of the '<em><b>All Rooms</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.test.site.Room}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Rooms</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Rooms</em>' containment reference list.
	 * @see org.eclipse.set.model.test.site.SitePackage#getSite_AllRooms()
	 * @model containment="true"
	 * @generated
	 */
	EList<Room> getAllRooms();

	/**
	 * Returns the value of the '<em><b>All Floors</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.test.site.Floor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Floors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Floors</em>' containment reference list.
	 * @see org.eclipse.set.model.test.site.SitePackage#getSite_AllFloors()
	 * @model containment="true"
	 * @generated
	 */
	EList<Floor> getAllFloors();

	/**
	 * Returns the value of the '<em><b>All Passages</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.test.site.Passage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Passages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Passages</em>' containment reference list.
	 * @see org.eclipse.set.model.test.site.SitePackage#getSite_AllPassages()
	 * @model containment="true"
	 * @generated
	 */
	EList<Passage> getAllPassages();

} // Site
