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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Track
 * Switch</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitch#getDesign
 * <em>Design</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitch#getComponents
 * <em>Components</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitch#getContinuousSegments
 * <em>Continuous Segments</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitch#getSwitchType
 * <em>Switch Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitch()
 * @model
 * @generated
 */
public interface TrackSwitch extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Design</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Design</em>' attribute.
	 * @see #setDesign(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitch_Design()
	 * @model
	 * @generated
	 */
	String getDesign();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitch#getDesign
	 * <em>Design</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Design</em>' attribute.
	 * @see #getDesign()
	 * @generated
	 */
	void setDesign(String value);

	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.TrackSwitchComponent}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Components</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitch_Components()
	 * @model containment="true" upper="2"
	 * @generated
	 */
	EList<TrackSwitchComponent> getComponents();

	/**
	 * Returns the value of the '<em><b>Continuous Segments</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.ContinuousTrackSegment}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Continuous Segments</em>' containment
	 *         reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitch_ContinuousSegments()
	 * @model containment="true" upper="2"
	 * @generated
	 */
	EList<ContinuousTrackSegment> getContinuousSegments();

	/**
	 * Returns the value of the '<em><b>Switch Type</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.SwitchType}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Switch Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.SwitchType
	 * @see #setSwitchType(SwitchType)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitch_SwitchType()
	 * @model
	 * @generated
	 */
	SwitchType getSwitchType();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitch#getSwitchType
	 * <em>Switch Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Switch Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.SwitchType
	 * @see #getSwitchType()
	 * @generated
	 */
	void setSwitchType(SwitchType value);

} // TrackSwitch
