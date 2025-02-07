/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.siteplan.Coordinate;
import org.eclipse.set.model.siteplan.Label;
import org.eclipse.set.model.siteplan.Platform;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Platform</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PlatformImpl#getGuid
 * <em>Guid</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PlatformImpl#getLabel
 * <em>Label</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PlatformImpl#getLabelPosition
 * <em>Label Position</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PlatformImpl#getPoints
 * <em>Points</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PlatformImpl extends MinimalEObjectImpl.Container
		implements Platform {
	/**
	 * The default value of the '{@link #getGuid() <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGuid()
	 * @generated
	 * @ordered
	 */
	protected static final String GUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuid() <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGuid()
	 * @generated
	 * @ordered
	 */
	protected String guid = GUID_EDEFAULT;

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
	 * The cached value of the '{@link #getPoints() <em>Points</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<Coordinate> points;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PlatformImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.PLATFORM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getGuid() {
		return guid;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setGuid(String newGuid) {
		String oldGuid = guid;
		guid = newGuid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.PLATFORM__GUID, oldGuid, guid));
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
					Notification.SET, SiteplanPackage.PLATFORM__LABEL, oldLabel,
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
				msgs = ((InternalEObject) label)
						.eInverseRemove(this,
								EOPPOSITE_FEATURE_BASE
										- SiteplanPackage.PLATFORM__LABEL,
								null, msgs);
			if (newLabel != null)
				msgs = ((InternalEObject) newLabel)
						.eInverseAdd(this,
								EOPPOSITE_FEATURE_BASE
										- SiteplanPackage.PLATFORM__LABEL,
								null, msgs);
			msgs = basicSetLabel(newLabel, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.PLATFORM__LABEL, newLabel, newLabel));
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
					Notification.SET, SiteplanPackage.PLATFORM__LABEL_POSITION,
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
								- SiteplanPackage.PLATFORM__LABEL_POSITION,
						null, msgs);
			if (newLabelPosition != null)
				msgs = ((InternalEObject) newLabelPosition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.PLATFORM__LABEL_POSITION,
						null, msgs);
			msgs = basicSetLabelPosition(newLabelPosition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.PLATFORM__LABEL_POSITION, newLabelPosition,
					newLabelPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Coordinate> getPoints() {
		if (points == null) {
			points = new EObjectContainmentEList<Coordinate>(Coordinate.class,
					this, SiteplanPackage.PLATFORM__POINTS);
		}
		return points;
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
			case SiteplanPackage.PLATFORM__LABEL:
				return basicSetLabel(null, msgs);
			case SiteplanPackage.PLATFORM__LABEL_POSITION:
				return basicSetLabelPosition(null, msgs);
			case SiteplanPackage.PLATFORM__POINTS:
				return ((InternalEList<?>) getPoints()).basicRemove(otherEnd,
						msgs);
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
			case SiteplanPackage.PLATFORM__GUID:
				return getGuid();
			case SiteplanPackage.PLATFORM__LABEL:
				return getLabel();
			case SiteplanPackage.PLATFORM__LABEL_POSITION:
				return getLabelPosition();
			case SiteplanPackage.PLATFORM__POINTS:
				return getPoints();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.PLATFORM__GUID:
				setGuid((String) newValue);
				return;
			case SiteplanPackage.PLATFORM__LABEL:
				setLabel((Label) newValue);
				return;
			case SiteplanPackage.PLATFORM__LABEL_POSITION:
				setLabelPosition((Position) newValue);
				return;
			case SiteplanPackage.PLATFORM__POINTS:
				getPoints().clear();
				getPoints().addAll((Collection<? extends Coordinate>) newValue);
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
			case SiteplanPackage.PLATFORM__GUID:
				setGuid(GUID_EDEFAULT);
				return;
			case SiteplanPackage.PLATFORM__LABEL:
				setLabel((Label) null);
				return;
			case SiteplanPackage.PLATFORM__LABEL_POSITION:
				setLabelPosition((Position) null);
				return;
			case SiteplanPackage.PLATFORM__POINTS:
				getPoints().clear();
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
			case SiteplanPackage.PLATFORM__GUID:
				return GUID_EDEFAULT == null ? guid != null
						: !GUID_EDEFAULT.equals(guid);
			case SiteplanPackage.PLATFORM__LABEL:
				return label != null;
			case SiteplanPackage.PLATFORM__LABEL_POSITION:
				return labelPosition != null;
			case SiteplanPackage.PLATFORM__POINTS:
				return points != null && !points.isEmpty();
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
		result.append(" (guid: ");
		result.append(guid);
		result.append(')');
		return result.toString();
	}

} // PlatformImpl
