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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Cant
 * Point</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.CantPoint#getHeight
 * <em>Height</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCantPoint()
 * @model
 * @generated
 */
public interface CantPoint extends PositionedObject {
	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(double)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCantPoint_Height()
	 * @model
	 * @generated
	 */
	double getHeight();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.CantPoint#getHeight
	 * <em>Height</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(double value);

} // CantPoint
