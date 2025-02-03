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

import org.eclipse.set.model.siteplan.ControlStationType;
import org.eclipse.set.model.siteplan.ExternalElementControl;
import org.eclipse.set.model.siteplan.ExternalElementControlArt;
import org.eclipse.set.model.siteplan.Label;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.PositionedObject;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Element Control</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl#getControlArt <em>Control Art</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl#getElementType <em>Element Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl#getControlStation <em>Control Station</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExternalElementControlImpl extends RouteObjectImpl implements ExternalElementControl {
	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Position position;

	/**
	 * The default value of the '{@link #getControlArt() <em>Control Art</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControlArt()
	 * @generated
	 * @ordered
	 */
	protected static final ExternalElementControlArt CONTROL_ART_EDEFAULT = ExternalElementControlArt.FE_AK;

	/**
	 * The cached value of the '{@link #getControlArt() <em>Control Art</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControlArt()
	 * @generated
	 * @ordered
	 */
	protected ExternalElementControlArt controlArt = CONTROL_ART_EDEFAULT;

	/**
	 * The default value of the '{@link #getElementType() <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected static final ExternalElementControlArt ELEMENT_TYPE_EDEFAULT = ExternalElementControlArt.FE_AK;

	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected ExternalElementControlArt elementType = ELEMENT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getControlStation() <em>Control Station</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControlStation()
	 * @generated
	 * @ordered
	 */
	protected static final ControlStationType CONTROL_STATION_EDEFAULT = ControlStationType.DEFAULT_CONTROL;

	/**
	 * The cached value of the '{@link #getControlStation() <em>Control Station</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControlStation()
	 * @generated
	 * @ordered
	 */
	protected ControlStationType controlStation = CONTROL_STATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected Label label;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExternalElementControlImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.EXTERNAL_ELEMENT_CONTROL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(Position newPosition, NotificationChain msgs) {
		Position oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION, oldPosition, newPosition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPosition(Position newPosition) {
		if (newPosition != position) {
			NotificationChain msgs = null;
			if (position != null)
				msgs = ((InternalEObject)position).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject)newPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION, newPosition, newPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExternalElementControlArt getControlArt() {
		return controlArt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setControlArt(ExternalElementControlArt newControlArt) {
		ExternalElementControlArt oldControlArt = controlArt;
		controlArt = newControlArt == null ? CONTROL_ART_EDEFAULT : newControlArt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_ART, oldControlArt, controlArt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExternalElementControlArt getElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setElementType(ExternalElementControlArt newElementType) {
		ExternalElementControlArt oldElementType = elementType;
		elementType = newElementType == null ? ELEMENT_TYPE_EDEFAULT : newElementType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__ELEMENT_TYPE, oldElementType, elementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ControlStationType getControlStation() {
		return controlStation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setControlStation(ControlStationType newControlStation) {
		ControlStationType oldControlStation = controlStation;
		controlStation = newControlStation == null ? CONTROL_STATION_EDEFAULT : newControlStation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_STATION, oldControlStation, controlStation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Label getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLabel(Label newLabel, NotificationChain msgs) {
		Label oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL, oldLabel, newLabel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(Label newLabel) {
		if (newLabel != label) {
			NotificationChain msgs = null;
			if (label != null)
				msgs = ((InternalEObject)label).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL, null, msgs);
			if (newLabel != null)
				msgs = ((InternalEObject)newLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL, null, msgs);
			msgs = basicSetLabel(newLabel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL, newLabel, newLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION:
				return basicSetPosition(null, msgs);
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL:
				return basicSetLabel(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION:
				return getPosition();
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_ART:
				return getControlArt();
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__ELEMENT_TYPE:
				return getElementType();
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_STATION:
				return getControlStation();
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL:
				return getLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION:
				setPosition((Position)newValue);
				return;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_ART:
				setControlArt((ExternalElementControlArt)newValue);
				return;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__ELEMENT_TYPE:
				setElementType((ExternalElementControlArt)newValue);
				return;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_STATION:
				setControlStation((ControlStationType)newValue);
				return;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL:
				setLabel((Label)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION:
				setPosition((Position)null);
				return;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_ART:
				setControlArt(CONTROL_ART_EDEFAULT);
				return;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_STATION:
				setControlStation(CONTROL_STATION_EDEFAULT);
				return;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL:
				setLabel((Label)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION:
				return position != null;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_ART:
				return controlArt != CONTROL_ART_EDEFAULT;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__ELEMENT_TYPE:
				return elementType != ELEMENT_TYPE_EDEFAULT;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__CONTROL_STATION:
				return controlStation != CONTROL_STATION_EDEFAULT;
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__LABEL:
				return label != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == PositionedObject.class) {
			switch (derivedFeatureID) {
				case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION: return SiteplanPackage.POSITIONED_OBJECT__POSITION;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == PositionedObject.class) {
			switch (baseFeatureID) {
				case SiteplanPackage.POSITIONED_OBJECT__POSITION: return SiteplanPackage.EXTERNAL_ELEMENT_CONTROL__POSITION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (controlArt: ");
		result.append(controlArt);
		result.append(", elementType: ");
		result.append(elementType);
		result.append(", controlStation: ");
		result.append(controlStation);
		result.append(')');
		return result.toString();
	}

} //ExternalElementControlImpl
