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

import org.eclipse.set.model.siteplan.Label;
import org.eclipse.set.model.siteplan.Platform;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.Station;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Station</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.StationImpl#getPlatforms
 * <em>Platforms</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.StationImpl#getLabel
 * <em>Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StationImpl extends SiteplanObjectImpl implements Station {
	/**
	 * The cached value of the '{@link #getPlatforms() <em>Platforms</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPlatforms()
	 * @generated
	 * @ordered
	 */
	protected EList<Platform> platforms;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected StationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.STATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Platform> getPlatforms() {
		if (platforms == null) {
			platforms = new EObjectContainmentEList<Platform>(Platform.class,
					this, SiteplanPackage.STATION__PLATFORMS);
		}
		return platforms;
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
					Notification.SET, SiteplanPackage.STATION__LABEL, oldLabel,
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
						EOPPOSITE_FEATURE_BASE - SiteplanPackage.STATION__LABEL,
						null, msgs);
			if (newLabel != null)
				msgs = ((InternalEObject) newLabel).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SiteplanPackage.STATION__LABEL,
						null, msgs);
			msgs = basicSetLabel(newLabel, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.STATION__LABEL, newLabel, newLabel));
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
			case SiteplanPackage.STATION__PLATFORMS:
				return ((InternalEList<?>) getPlatforms()).basicRemove(otherEnd,
						msgs);
			case SiteplanPackage.STATION__LABEL:
				return basicSetLabel(null, msgs);
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
			case SiteplanPackage.STATION__PLATFORMS:
				return getPlatforms();
			case SiteplanPackage.STATION__LABEL:
				return getLabel();
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
			case SiteplanPackage.STATION__PLATFORMS:
				getPlatforms().clear();
				getPlatforms()
						.addAll((Collection<? extends Platform>) newValue);
				return;
			case SiteplanPackage.STATION__LABEL:
				setLabel((Label) newValue);
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
			case SiteplanPackage.STATION__PLATFORMS:
				getPlatforms().clear();
				return;
			case SiteplanPackage.STATION__LABEL:
				setLabel((Label) null);
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
			case SiteplanPackage.STATION__PLATFORMS:
				return platforms != null && !platforms.isEmpty();
			case SiteplanPackage.STATION__LABEL:
				return label != null;
		}
		return super.eIsSet(featureID);
	}

} // StationImpl
