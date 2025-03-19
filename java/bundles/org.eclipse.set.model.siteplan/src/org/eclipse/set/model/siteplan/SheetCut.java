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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sheet
 * Cut</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.SheetCut#getLabel
 * <em>Label</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SheetCut#getPolygonDirection
 * <em>Polygon Direction</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SheetCut#getPolygon
 * <em>Polygon</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSheetCut()
 * @model
 * @generated
 */
public interface SheetCut extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSheetCut_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.SheetCut#getLabel <em>Label</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Polygon Direction</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Coordinate}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Polygon Direction</em>' containment
	 *         reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSheetCut_PolygonDirection()
	 * @model containment="true" upper="2"
	 * @generated
	 */
	EList<Coordinate> getPolygonDirection();

	/**
	 * Returns the value of the '<em><b>Polygon</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Coordinate}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Polygon</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSheetCut_Polygon()
	 * @model containment="true"
	 * @generated
	 */
	EList<Coordinate> getPolygon();

} // SheetCut
