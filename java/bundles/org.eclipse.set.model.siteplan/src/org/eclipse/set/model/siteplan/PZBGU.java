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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>PZBGU</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.PZBGU#getPzbs <em>Pzbs</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.PZBGU#getLength <em>Length</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZBGU()
 * @model
 * @generated
 */
public interface PZBGU extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Pzbs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.PZB}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pzbs</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZBGU_Pzbs()
	 * @model containment="true"
	 * @generated
	 */
	EList<PZB> getPzbs();

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZBGU_Length()
	 * @model
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.PZBGU#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

} // PZBGU
