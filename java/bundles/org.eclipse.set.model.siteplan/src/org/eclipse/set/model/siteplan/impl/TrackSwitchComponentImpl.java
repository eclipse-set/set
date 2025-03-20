/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.set.model.siteplan.Label;
import org.eclipse.set.model.siteplan.LeftRight;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.TrackSwitchComponent;
import org.eclipse.set.model.siteplan.TrackSwitchLeg;
import org.eclipse.set.model.siteplan.TurnoutOperatingMode;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Track
 * Switch Component</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl#getPreferredLocation
 * <em>Preferred Location</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl#getPointDetectorCount
 * <em>Point Detector Count</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl#getStart
 * <em>Start</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl#getLabelPosition
 * <em>Label Position</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl#getLabel
 * <em>Label</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl#getOperatingMode
 * <em>Operating Mode</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl#getMainLeg
 * <em>Main Leg</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl#getSideLeg
 * <em>Side Leg</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackSwitchComponentImpl extends RouteObjectImpl
		implements TrackSwitchComponent {
	/**
	 * The default value of the '{@link #getPreferredLocation() <em>Preferred
	 * Location</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPreferredLocation()
	 * @generated
	 * @ordered
	 */
	protected static final LeftRight PREFERRED_LOCATION_EDEFAULT = LeftRight.LEFT;

	/**
	 * The cached value of the '{@link #getPreferredLocation() <em>Preferred
	 * Location</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPreferredLocation()
	 * @generated
	 * @ordered
	 */
	protected LeftRight preferredLocation = PREFERRED_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getPointDetectorCount() <em>Point
	 * Detector Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getPointDetectorCount()
	 * @generated
	 * @ordered
	 */
	protected static final int POINT_DETECTOR_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPointDetectorCount() <em>Point
	 * Detector Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getPointDetectorCount()
	 * @generated
	 * @ordered
	 */
	protected int pointDetectorCount = POINT_DETECTOR_COUNT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected Position start;

	/**
	 * The cached value of the '{@link #getLabelPosition() <em>Label
	 * Position</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getLabelPosition()
	 * @generated
	 * @ordered
	 */
	protected Position labelPosition;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected Label label;

	/**
	 * The default value of the '{@link #getOperatingMode() <em>Operating
	 * Mode</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperatingMode()
	 * @generated
	 * @ordered
	 */
	protected static final TurnoutOperatingMode OPERATING_MODE_EDEFAULT = TurnoutOperatingMode.UNDEFINED;

	/**
	 * The cached value of the '{@link #getOperatingMode() <em>Operating
	 * Mode</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperatingMode()
	 * @generated
	 * @ordered
	 */
	protected TurnoutOperatingMode operatingMode = OPERATING_MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMainLeg() <em>Main Leg</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMainLeg()
	 * @generated
	 * @ordered
	 */
	protected TrackSwitchLeg mainLeg;

	/**
	 * The cached value of the '{@link #getSideLeg() <em>Side Leg</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSideLeg()
	 * @generated
	 * @ordered
	 */
	protected TrackSwitchLeg sideLeg;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TrackSwitchComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK_SWITCH_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LeftRight getPreferredLocation() {
		return preferredLocation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPreferredLocation(LeftRight newPreferredLocation) {
		LeftRight oldPreferredLocation = preferredLocation;
		preferredLocation = newPreferredLocation == null
				? PREFERRED_LOCATION_EDEFAULT
				: newPreferredLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__PREFERRED_LOCATION,
					oldPreferredLocation, preferredLocation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getPointDetectorCount() {
		return pointDetectorCount;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPointDetectorCount(int newPointDetectorCount) {
		int oldPointDetectorCount = pointDetectorCount;
		pointDetectorCount = newPointDetectorCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__POINT_DETECTOR_COUNT,
					oldPointDetectorCount, pointDetectorCount));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Position getStart() {
		return start;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetStart(Position newStart,
			NotificationChain msgs) {
		Position oldStart = start;
		start = newStart;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__START, oldStart,
					newStart);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setStart(Position newStart) {
		if (newStart != start) {
			NotificationChain msgs = null;
			if (start != null)
				msgs = ((InternalEObject) start).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__START,
						null, msgs);
			if (newStart != null)
				msgs = ((InternalEObject) newStart).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__START,
						null, msgs);
			msgs = basicSetStart(newStart, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__START, newStart,
					newStart));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Position getLabelPosition() {
		return labelPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLabelPosition(Position newLabelPosition,
			NotificationChain msgs) {
		Position oldLabelPosition = labelPosition;
		labelPosition = newLabelPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION,
					oldLabelPosition, newLabelPosition);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLabelPosition(Position newLabelPosition) {
		if (newLabelPosition != labelPosition) {
			NotificationChain msgs = null;
			if (labelPosition != null)
				msgs = ((InternalEObject) labelPosition).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION,
						null, msgs);
			if (newLabelPosition != null)
				msgs = ((InternalEObject) newLabelPosition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION,
						null, msgs);
			msgs = basicSetLabelPosition(newLabelPosition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION,
					newLabelPosition, newLabelPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Label getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLabel(Label newLabel,
			NotificationChain msgs) {
		Label oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL, oldLabel,
					newLabel);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLabel(Label newLabel) {
		if (newLabel != label) {
			NotificationChain msgs = null;
			if (label != null)
				msgs = ((InternalEObject) label).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL,
						null, msgs);
			if (newLabel != null)
				msgs = ((InternalEObject) newLabel).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL,
						null, msgs);
			msgs = basicSetLabel(newLabel, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL, newLabel,
					newLabel));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TurnoutOperatingMode getOperatingMode() {
		return operatingMode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOperatingMode(TurnoutOperatingMode newOperatingMode) {
		TurnoutOperatingMode oldOperatingMode = operatingMode;
		operatingMode = newOperatingMode == null ? OPERATING_MODE_EDEFAULT
				: newOperatingMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__OPERATING_MODE,
					oldOperatingMode, operatingMode));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackSwitchLeg getMainLeg() {
		return mainLeg;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMainLeg(TrackSwitchLeg newMainLeg,
			NotificationChain msgs) {
		TrackSwitchLeg oldMainLeg = mainLeg;
		mainLeg = newMainLeg;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG,
					oldMainLeg, newMainLeg);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMainLeg(TrackSwitchLeg newMainLeg) {
		if (newMainLeg != mainLeg) {
			NotificationChain msgs = null;
			if (mainLeg != null)
				msgs = ((InternalEObject) mainLeg).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG,
						null, msgs);
			if (newMainLeg != null)
				msgs = ((InternalEObject) newMainLeg).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG,
						null, msgs);
			msgs = basicSetMainLeg(newMainLeg, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG,
					newMainLeg, newMainLeg));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackSwitchLeg getSideLeg() {
		return sideLeg;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSideLeg(TrackSwitchLeg newSideLeg,
			NotificationChain msgs) {
		TrackSwitchLeg oldSideLeg = sideLeg;
		sideLeg = newSideLeg;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG,
					oldSideLeg, newSideLeg);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSideLeg(TrackSwitchLeg newSideLeg) {
		if (newSideLeg != sideLeg) {
			NotificationChain msgs = null;
			if (sideLeg != null)
				msgs = ((InternalEObject) sideLeg).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG,
						null, msgs);
			if (newSideLeg != null)
				msgs = ((InternalEObject) newSideLeg).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG,
						null, msgs);
			msgs = basicSetSideLeg(newSideLeg, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG,
					newSideLeg, newSideLeg));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__START:
				return basicSetStart(null, msgs);
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION:
				return basicSetLabelPosition(null, msgs);
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL:
				return basicSetLabel(null, msgs);
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG:
				return basicSetMainLeg(null, msgs);
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG:
				return basicSetSideLeg(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__PREFERRED_LOCATION:
				return getPreferredLocation();
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__POINT_DETECTOR_COUNT:
				return getPointDetectorCount();
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__START:
				return getStart();
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION:
				return getLabelPosition();
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL:
				return getLabel();
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__OPERATING_MODE:
				return getOperatingMode();
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG:
				return getMainLeg();
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG:
				return getSideLeg();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__PREFERRED_LOCATION:
				setPreferredLocation((LeftRight) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__POINT_DETECTOR_COUNT:
				setPointDetectorCount((Integer) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__START:
				setStart((Position) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION:
				setLabelPosition((Position) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL:
				setLabel((Label) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__OPERATING_MODE:
				setOperatingMode((TurnoutOperatingMode) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG:
				setMainLeg((TrackSwitchLeg) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG:
				setSideLeg((TrackSwitchLeg) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__PREFERRED_LOCATION:
				setPreferredLocation(PREFERRED_LOCATION_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__POINT_DETECTOR_COUNT:
				setPointDetectorCount(POINT_DETECTOR_COUNT_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__START:
				setStart((Position) null);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION:
				setLabelPosition((Position) null);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL:
				setLabel((Label) null);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__OPERATING_MODE:
				setOperatingMode(OPERATING_MODE_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG:
				setMainLeg((TrackSwitchLeg) null);
				return;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG:
				setSideLeg((TrackSwitchLeg) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__PREFERRED_LOCATION:
				return preferredLocation != PREFERRED_LOCATION_EDEFAULT;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__POINT_DETECTOR_COUNT:
				return pointDetectorCount != POINT_DETECTOR_COUNT_EDEFAULT;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__START:
				return start != null;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL_POSITION:
				return labelPosition != null;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__LABEL:
				return label != null;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__OPERATING_MODE:
				return operatingMode != OPERATING_MODE_EDEFAULT;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__MAIN_LEG:
				return mainLeg != null;
			case SiteplanPackage.TRACK_SWITCH_COMPONENT__SIDE_LEG:
				return sideLeg != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (preferredLocation: ");
		result.append(preferredLocation);
		result.append(", pointDetectorCount: ");
		result.append(pointDetectorCount);
		result.append(", operatingMode: ");
		result.append(operatingMode);
		result.append(')');
		return result.toString();
	}

} // TrackSwitchComponentImpl
