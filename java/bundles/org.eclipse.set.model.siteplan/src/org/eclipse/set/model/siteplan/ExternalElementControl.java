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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>External
 * Element Control</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.ExternalElementControl#getControlArt
 * <em>Control Art</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.ExternalElementControl#getElementType
 * <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.ExternalElementControl#getControlStation
 * <em>Control Station</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.ExternalElementControl#getLabel
 * <em>Label</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getExternalElementControl()
 * @model
 * @generated
 */
public interface ExternalElementControl extends RouteObject, PositionedObject {
	/**
	 * Returns the value of the '<em><b>Control Art</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.ExternalElementControlArt}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Control Art</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControlArt
	 * @see #setControlArt(ExternalElementControlArt)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getExternalElementControl_ControlArt()
	 * @model
	 * @generated
	 */
	ExternalElementControlArt getControlArt();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl#getControlArt
	 * <em>Control Art</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Control Art</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControlArt
	 * @see #getControlArt()
	 * @generated
	 */
	void setControlArt(ExternalElementControlArt value);

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.ExternalElementControlArt}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Element Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControlArt
	 * @see #setElementType(ExternalElementControlArt)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getExternalElementControl_ElementType()
	 * @model
	 * @generated
	 */
	ExternalElementControlArt getElementType();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl#getElementType
	 * <em>Element Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Element Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControlArt
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(ExternalElementControlArt value);

	/**
	 * Returns the value of the '<em><b>Control Station</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.ControlStationType}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Control Station</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.ControlStationType
	 * @see #setControlStation(ControlStationType)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getExternalElementControl_ControlStation()
	 * @model
	 * @generated
	 */
	ControlStationType getControlStation();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl#getControlStation
	 * <em>Control Station</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Control Station</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.ControlStationType
	 * @see #getControlStation()
	 * @generated
	 */
	void setControlStation(ControlStationType value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' containment reference.
	 * @see #setLabel(Label)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getExternalElementControl_Label()
	 * @model containment="true"
	 * @generated
	 */
	Label getLabel();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl#getLabel
	 * <em>Label</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Label</em>' containment reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Label value);

} // ExternalElementControl
