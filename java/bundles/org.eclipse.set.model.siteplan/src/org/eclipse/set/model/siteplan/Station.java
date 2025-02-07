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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Station</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.Station#getPlatforms
 * <em>Platforms</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.Station#getLabel
 * <em>Label</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getStation()
 * @model
 * @generated
 */
public interface Station extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Platforms</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Platform}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Platforms</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getStation_Platforms()
	 * @model containment="true"
	 * @generated
	 */
	EList<Platform> getPlatforms();

	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' containment reference.
	 * @see #setLabel(Label)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getStation_Label()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Label getLabel();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.Station#getLabel <em>Label</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Label</em>' containment reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Label value);

} // Station
