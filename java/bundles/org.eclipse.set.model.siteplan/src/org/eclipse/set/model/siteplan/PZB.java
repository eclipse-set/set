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
 * '<em><b>PZB</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.PZB#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.PZB#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.PZB#isRightSide <em>Right Side</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.PZB#getEffectivity <em>Effectivity</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZB()
 * @model
 * @generated
 */
public interface PZB extends RouteObject, PositionedObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.PZBType}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.PZBType
	 * @see #setType(PZBType)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZB_Type()
	 * @model
	 * @generated
	 */
	PZBType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.PZB#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.PZBType
	 * @see #getType()
	 * @generated
	 */
	void setType(PZBType value);

	/**
	 * Returns the value of the '<em><b>Element</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.PZBElement}.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the value of the '<em>Element</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.PZBElement
	 * @see #setElement(PZBElement)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZB_Element()
	 * @model
	 * @generated
	 */
	PZBElement getElement();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.PZB#getElement <em>Element</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.PZBElement
	 * @see #getElement()
	 * @generated
	 */
	void setElement(PZBElement value);

	/**
	 * Returns the value of the '<em><b>Right Side</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Right Side</em>' attribute.
	 * @see #setRightSide(boolean)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZB_RightSide()
	 * @model
	 * @generated
	 */
	boolean isRightSide();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.PZB#isRightSide <em>Right Side</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right Side</em>' attribute.
	 * @see #isRightSide()
	 * @generated
	 */
	void setRightSide(boolean value);

	/**
	 * Returns the value of the '<em><b>Effectivity</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.PZBEffectivity}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Effectivity</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.PZBEffectivity
	 * @see #setEffectivity(PZBEffectivity)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZB_Effectivity()
	 * @model
	 * @generated
	 */
	PZBEffectivity getEffectivity();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.PZB#getEffectivity <em>Effectivity</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Effectivity</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.PZBEffectivity
	 * @see #getEffectivity()
	 * @generated
	 */
	void setEffectivity(PZBEffectivity value);

} // PZB
