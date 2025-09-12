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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.siteplan.ContinuousTrackSegment;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.SwitchType;
import org.eclipse.set.model.siteplan.TrackSwitch;
import org.eclipse.set.model.siteplan.TrackSwitchComponent;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Track
 * Switch</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchImpl#getDesign
 * <em>Design</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchImpl#getComponents
 * <em>Components</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchImpl#getContinuousSegments
 * <em>Continuous Segments</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchImpl#getSwitchType
 * <em>Switch Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackSwitchImpl extends SiteplanObjectImpl implements TrackSwitch {
	/**
	 * The default value of the '{@link #getDesign() <em>Design</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDesign()
	 * @generated
	 * @ordered
	 */
	protected static final String DESIGN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDesign() <em>Design</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDesign()
	 * @generated
	 * @ordered
	 */
	protected String design = DESIGN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getComponents() <em>Components</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackSwitchComponent> components;

	/**
	 * The cached value of the '{@link #getContinuousSegments() <em>Continuous
	 * Segments</em>}' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getContinuousSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<ContinuousTrackSegment> continuousSegments;

	/**
	 * The default value of the '{@link #getSwitchType() <em>Switch Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSwitchType()
	 * @generated
	 * @ordered
	 */
	protected static final SwitchType SWITCH_TYPE_EDEFAULT = SwitchType.SIMPLE;

	/**
	 * The cached value of the '{@link #getSwitchType() <em>Switch Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSwitchType()
	 * @generated
	 * @ordered
	 */
	protected SwitchType switchType = SWITCH_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TrackSwitchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK_SWITCH;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getDesign() {
		return design;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDesign(String newDesign) {
		String oldDesign = design;
		design = newDesign;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH__DESIGN, oldDesign, design));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<TrackSwitchComponent> getComponents() {
		if (components == null) {
			components = new EObjectContainmentEList<TrackSwitchComponent>(
					TrackSwitchComponent.class, this,
					SiteplanPackage.TRACK_SWITCH__COMPONENTS);
		}
		return components;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ContinuousTrackSegment> getContinuousSegments() {
		if (continuousSegments == null) {
			continuousSegments = new EObjectContainmentEList<ContinuousTrackSegment>(
					ContinuousTrackSegment.class, this,
					SiteplanPackage.TRACK_SWITCH__CONTINUOUS_SEGMENTS);
		}
		return continuousSegments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SwitchType getSwitchType() {
		return switchType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSwitchType(SwitchType newSwitchType) {
		SwitchType oldSwitchType = switchType;
		switchType = newSwitchType == null ? SWITCH_TYPE_EDEFAULT
				: newSwitchType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH__SWITCH_TYPE, oldSwitchType,
					switchType));
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
			case SiteplanPackage.TRACK_SWITCH__COMPONENTS:
				return ((InternalEList<?>) getComponents())
						.basicRemove(otherEnd, msgs);
			case SiteplanPackage.TRACK_SWITCH__CONTINUOUS_SEGMENTS:
				return ((InternalEList<?>) getContinuousSegments())
						.basicRemove(otherEnd, msgs);
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
			case SiteplanPackage.TRACK_SWITCH__DESIGN:
				return getDesign();
			case SiteplanPackage.TRACK_SWITCH__COMPONENTS:
				return getComponents();
			case SiteplanPackage.TRACK_SWITCH__CONTINUOUS_SEGMENTS:
				return getContinuousSegments();
			case SiteplanPackage.TRACK_SWITCH__SWITCH_TYPE:
				return getSwitchType();
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
			case SiteplanPackage.TRACK_SWITCH__DESIGN:
				setDesign((String) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH__COMPONENTS:
				getComponents().clear();
				getComponents().addAll(
						(Collection<? extends TrackSwitchComponent>) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH__CONTINUOUS_SEGMENTS:
				getContinuousSegments().clear();
				getContinuousSegments().addAll(
						(Collection<? extends ContinuousTrackSegment>) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH__SWITCH_TYPE:
				setSwitchType((SwitchType) newValue);
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
			case SiteplanPackage.TRACK_SWITCH__DESIGN:
				setDesign(DESIGN_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_SWITCH__COMPONENTS:
				getComponents().clear();
				return;
			case SiteplanPackage.TRACK_SWITCH__CONTINUOUS_SEGMENTS:
				getContinuousSegments().clear();
				return;
			case SiteplanPackage.TRACK_SWITCH__SWITCH_TYPE:
				setSwitchType(SWITCH_TYPE_EDEFAULT);
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
			case SiteplanPackage.TRACK_SWITCH__DESIGN:
				return DESIGN_EDEFAULT == null ? design != null
						: !DESIGN_EDEFAULT.equals(design);
			case SiteplanPackage.TRACK_SWITCH__COMPONENTS:
				return components != null && !components.isEmpty();
			case SiteplanPackage.TRACK_SWITCH__CONTINUOUS_SEGMENTS:
				return continuousSegments != null
						&& !continuousSegments.isEmpty();
			case SiteplanPackage.TRACK_SWITCH__SWITCH_TYPE:
				return switchType != SWITCH_TYPE_EDEFAULT;
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
		result.append(" (design: ");
		result.append(design);
		result.append(", switchType: ");
		result.append(switchType);
		result.append(')');
		return result.toString();
	}

} // TrackSwitchImpl
