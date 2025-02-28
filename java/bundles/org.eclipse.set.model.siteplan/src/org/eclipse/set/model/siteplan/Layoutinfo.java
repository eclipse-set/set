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
 * '<em><b>Layoutinfo</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.Layoutinfo#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Layoutinfo#getSheetsCut <em>Sheets Cut</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getLayoutinfo()
 * @model
 * @generated
 */
public interface Layoutinfo extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getLayoutinfo_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Layoutinfo#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Sheets Cut</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.SheetCut}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sheets Cut</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getLayoutinfo_SheetsCut()
	 * @model containment="true"
	 * @generated
	 */
	EList<SheetCut> getSheetsCut();

} // Layoutinfo
