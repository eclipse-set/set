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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Positioned Object</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.PositionedObject#getPosition
 * <em>Position</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPositionedObject()
 * @model
 * @generated
 */
public interface PositionedObject extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Position</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Position</em>' containment reference.
	 * @see #setPosition(Position)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPositionedObject_Position()
	 * @model containment="true"
	 * @generated
	 */
	Position getPosition();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.PositionedObject#getPosition
	 * <em>Position</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Position</em>' containment
	 *            reference.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(Position value);

} // PositionedObject
