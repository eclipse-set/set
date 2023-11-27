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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.Cant#getPointA <em>Point A</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Cant#getPointB <em>Point B</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Cant#getForm <em>Form</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Cant#getLength <em>Length</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCant()
 * @model
 * @generated
 */
public interface Cant extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Point A</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Point A</em>' containment reference.
	 * @see #setPointA(PositionedObject)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCant_PointA()
	 * @model containment="true"
	 * @generated
	 */
	PositionedObject getPointA();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Cant#getPointA <em>Point A</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Point A</em>' containment reference.
	 * @see #getPointA()
	 * @generated
	 */
	void setPointA(PositionedObject value);

	/**
	 * Returns the value of the '<em><b>Point B</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Point B</em>' containment reference.
	 * @see #setPointB(PositionedObject)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCant_PointB()
	 * @model containment="true"
	 * @generated
	 */
	PositionedObject getPointB();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Cant#getPointB <em>Point B</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Point B</em>' containment reference.
	 * @see #getPointB()
	 * @generated
	 */
	void setPointB(PositionedObject value);

	/**
	 * Returns the value of the '<em><b>Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Form</em>' attribute.
	 * @see #setForm(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCant_Form()
	 * @model
	 * @generated
	 */
	String getForm();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Cant#getForm <em>Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Form</em>' attribute.
	 * @see #getForm()
	 * @generated
	 */
	void setForm(String value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(double)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getCant_Length()
	 * @model
	 * @generated
	 */
	double getLength();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Cant#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(double value);

} // Cant
