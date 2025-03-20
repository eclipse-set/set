/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Coordinate</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.Coordinate#getX <em>X</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.Coordinate#getY <em>Y</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCoordinate()
 * @model
 * @generated
 */
public interface Coordinate extends EObject {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute. The default value
	 * is <code>"0"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(double)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCoordinate_X()
	 * @model default="0"
	 * @generated
	 */
	double getX();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.Coordinate#getX <em>X</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(double value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute. The default value
	 * is <code>"0"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(double)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCoordinate_Y()
	 * @model default="0"
	 * @generated
	 */
	double getY();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.Coordinate#getY <em>Y</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(double value);

} // Coordinate
