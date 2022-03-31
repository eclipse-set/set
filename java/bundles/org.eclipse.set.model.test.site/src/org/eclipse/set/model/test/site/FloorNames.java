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
 * A representation of the model object '<em><b>Floor Names</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.FloorNames#getElevator <em>Elevator</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.FloorNames#getFloorPlanTitle <em>Floor Plan Title</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.test.site.SitePackage#getFloorNames()
 * @model
 * @generated
 */
public interface FloorNames extends EObject {
	/**
	 * Returns the value of the '<em><b>Elevator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elevator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elevator</em>' attribute.
	 * @see #setElevator(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getFloorNames_Elevator()
	 * @model
	 * @generated
	 */
	String getElevator();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.FloorNames#getElevator <em>Elevator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Elevator</em>' attribute.
	 * @see #getElevator()
	 * @generated
	 */
	void setElevator(String value);

	/**
	 * Returns the value of the '<em><b>Floor Plan Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Floor Plan Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Floor Plan Title</em>' attribute.
	 * @see #setFloorPlanTitle(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getFloorNames_FloorPlanTitle()
	 * @model
	 * @generated
	 */
	String getFloorPlanTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.FloorNames#getFloorPlanTitle <em>Floor Plan Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Floor Plan Title</em>' attribute.
	 * @see #getFloorPlanTitle()
	 * @generated
	 */
	void setFloorPlanTitle(String value);

} // FloorNames
