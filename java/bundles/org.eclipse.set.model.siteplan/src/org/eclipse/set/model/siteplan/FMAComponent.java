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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>FMA
 * Component</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.FMAComponent#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.FMAComponent#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.FMAComponent#isRightSide <em>Right Side</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getFMAComponent()
 * @model
 * @generated
 */
public interface FMAComponent extends RouteObject, PositionedObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' containment reference.
	 * @see #setLabel(Label)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getFMAComponent_Label()
	 * @model containment="true"
	 * @generated
	 */
	Label getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.FMAComponent#getLabel <em>Label</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' containment reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Label value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. The literals
	 * are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.FMAComponentType}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.FMAComponentType
	 * @see #setType(FMAComponentType)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getFMAComponent_Type()
	 * @model
	 * @generated
	 */
	FMAComponentType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.FMAComponent#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.FMAComponentType
	 * @see #getType()
	 * @generated
	 */
	void setType(FMAComponentType value);

	/**
	 * Returns the value of the '<em><b>Right Side</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Right Side</em>' attribute.
	 * @see #setRightSide(boolean)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getFMAComponent_RightSide()
	 * @model
	 * @generated
	 */
	boolean isRightSide();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.FMAComponent#isRightSide <em>Right Side</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right Side</em>' attribute.
	 * @see #isRightSide()
	 * @generated
	 */
	void setRightSide(boolean value);

} // FMAComponent
