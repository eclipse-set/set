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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Track
 * Switch Component</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getPreferredLocation
 * <em>Preferred Location</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getPointDetectorCount
 * <em>Point Detector Count</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getStart
 * <em>Start</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getLabelPosition
 * <em>Label Position</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getLabel
 * <em>Label</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getOperatingMode
 * <em>Operating Mode</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getMainLeg
 * <em>Main Leg</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getSideLeg
 * <em>Side Leg</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent()
 * @model
 * @generated
 */
public interface TrackSwitchComponent extends RouteObject {
	/**
	 * Returns the value of the '<em><b>Preferred Location</b></em>' attribute.
	 * The literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.LeftRight}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Preferred Location</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.LeftRight
	 * @see #setPreferredLocation(LeftRight)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent_PreferredLocation()
	 * @model
	 * @generated
	 */
	LeftRight getPreferredLocation();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getPreferredLocation
	 * <em>Preferred Location</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Preferred Location</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.LeftRight
	 * @see #getPreferredLocation()
	 * @generated
	 */
	void setPreferredLocation(LeftRight value);

	/**
	 * Returns the value of the '<em><b>Point Detector Count</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Point Detector Count</em>' attribute.
	 * @see #setPointDetectorCount(int)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent_PointDetectorCount()
	 * @model
	 * @generated
	 */
	int getPointDetectorCount();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getPointDetectorCount
	 * <em>Point Detector Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Point Detector Count</em>'
	 *            attribute.
	 * @see #getPointDetectorCount()
	 * @generated
	 */
	void setPointDetectorCount(int value);

	/**
	 * Returns the value of the '<em><b>Start</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start</em>' containment reference.
	 * @see #setStart(Position)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent_Start()
	 * @model containment="true"
	 * @generated
	 */
	Position getStart();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getStart
	 * <em>Start</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Start</em>' containment reference.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(Position value);

	/**
	 * Returns the value of the '<em><b>Label Position</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label Position</em>' containment reference.
	 * @see #setLabelPosition(Position)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent_LabelPosition()
	 * @model containment="true"
	 * @generated
	 */
	Position getLabelPosition();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getLabelPosition
	 * <em>Label Position</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Label Position</em>' containment
	 *            reference.
	 * @see #getLabelPosition()
	 * @generated
	 */
	void setLabelPosition(Position value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' containment reference.
	 * @see #setLabel(Label)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent_Label()
	 * @model containment="true"
	 * @generated
	 */
	Label getLabel();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getLabel
	 * <em>Label</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Label</em>' containment reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Label value);

	/**
	 * Returns the value of the '<em><b>Operating Mode</b></em>' attribute. The
	 * default value is <code>"Undefined"</code>. The literals are from the
	 * enumeration {@link org.eclipse.set.model.siteplan.TurnoutOperatingMode}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operating Mode</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TurnoutOperatingMode
	 * @see #setOperatingMode(TurnoutOperatingMode)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent_OperatingMode()
	 * @model default="Undefined" required="true"
	 * @generated
	 */
	TurnoutOperatingMode getOperatingMode();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getOperatingMode
	 * <em>Operating Mode</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Operating Mode</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TurnoutOperatingMode
	 * @see #getOperatingMode()
	 * @generated
	 */
	void setOperatingMode(TurnoutOperatingMode value);

	/**
	 * Returns the value of the '<em><b>Main Leg</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Main Leg</em>' containment reference.
	 * @see #setMainLeg(TrackSwitchLeg)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent_MainLeg()
	 * @model containment="true"
	 * @generated
	 */
	TrackSwitchLeg getMainLeg();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getMainLeg
	 * <em>Main Leg</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Main Leg</em>' containment
	 *            reference.
	 * @see #getMainLeg()
	 * @generated
	 */
	void setMainLeg(TrackSwitchLeg value);

	/**
	 * Returns the value of the '<em><b>Side Leg</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Side Leg</em>' containment reference.
	 * @see #setSideLeg(TrackSwitchLeg)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchComponent_SideLeg()
	 * @model containment="true"
	 * @generated
	 */
	TrackSwitchLeg getSideLeg();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getSideLeg
	 * <em>Side Leg</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Side Leg</em>' containment
	 *            reference.
	 * @see #getSideLeg()
	 * @generated
	 */
	void setSideLeg(TrackSwitchLeg value);

} // TrackSwitchComponent
