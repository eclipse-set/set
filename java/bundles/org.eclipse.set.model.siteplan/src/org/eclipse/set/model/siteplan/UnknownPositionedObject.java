/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Unknown
 * Positioned Object</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.UnknownPositionedObject#getObjectType
 * <em>Object Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getUnknownPositionedObject()
 * @model
 * @generated
 */
public interface UnknownPositionedObject extends PositionedObject {
	/**
	 * Returns the value of the '<em><b>Object Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Object Type</em>' attribute.
	 * @see #setObjectType(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getUnknownPositionedObject_ObjectType()
	 * @model
	 * @generated
	 */
	String getObjectType();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.UnknownPositionedObject#getObjectType
	 * <em>Object Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Object Type</em>' attribute.
	 * @see #getObjectType()
	 * @generated
	 */
	void setObjectType(String value);

} // UnknownPositionedObject
