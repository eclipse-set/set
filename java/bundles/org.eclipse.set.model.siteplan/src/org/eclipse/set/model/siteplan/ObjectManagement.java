/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Object
 * Management</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.ObjectManagement#getPlanningObjectIDs
 * <em>Planning Object IDs</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.ObjectManagement#getPlanningGroupID
 * <em>Planning Group ID</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getObjectManagement()
 * @model
 * @generated
 */
public interface ObjectManagement extends EObject {
	/**
	 * Returns the value of the '<em><b>Planning Object IDs</b></em>' attribute
	 * list. The list contents are of type {@link java.lang.String}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Planning Object IDs</em>' attribute list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getObjectManagement_PlanningObjectIDs()
	 * @model
	 * @generated
	 */
	EList<String> getPlanningObjectIDs();

	/**
	 * Returns the value of the '<em><b>Planning Group ID</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Planning Group ID</em>' attribute.
	 * @see #setPlanningGroupID(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getObjectManagement_PlanningGroupID()
	 * @model
	 * @generated
	 */
	String getPlanningGroupID();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.ObjectManagement#getPlanningGroupID
	 * <em>Planning Group ID</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Planning Group ID</em>' attribute.
	 * @see #getPlanningGroupID()
	 * @generated
	 */
	void setPlanningGroupID(String value);

} // ObjectManagement
