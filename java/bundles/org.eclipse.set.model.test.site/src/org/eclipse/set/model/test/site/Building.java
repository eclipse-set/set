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
 * A representation of the model object '<em><b>Building</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.Building#getNames <em>Names</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Building#getBuildingsInTheSameStreet <em>Buildings In The Same Street</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Building#getData <em>Data</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.test.site.SitePackage#getBuilding()
 * @model
 * @generated
 */
public interface Building extends Identified {
	/**
	 * Returns the value of the '<em><b>Names</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Names</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Names</em>' containment reference.
	 * @see #setNames(BuildingNames)
	 * @see org.eclipse.set.model.test.site.SitePackage#getBuilding_Names()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BuildingNames getNames();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.Building#getNames <em>Names</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Names</em>' containment reference.
	 * @see #getNames()
	 * @generated
	 */
	void setNames(BuildingNames value);

	/**
	 * Returns the value of the '<em><b>Buildings In The Same Street</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.set.model.test.site.Building}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Buildings In The Same Street</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buildings In The Same Street</em>' reference list.
	 * @see org.eclipse.set.model.test.site.SitePackage#getBuilding_BuildingsInTheSameStreet()
	 * @model
	 * @generated
	 */
	EList<Building> getBuildingsInTheSameStreet();

	/**
	 * Returns the value of the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' attribute.
	 * @see #setData(byte[])
	 * @see org.eclipse.set.model.test.site.SitePackage#getBuilding_Data()
	 * @model
	 * @generated
	 */
	byte[] getData();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.Building#getData <em>Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data</em>' attribute.
	 * @see #getData()
	 * @generated
	 */
	void setData(byte[] value);

} // Building
