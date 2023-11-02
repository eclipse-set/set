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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.siteplan.SignalScreen;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Signal Screen</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalScreenImpl#getScreen <em>Screen</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalScreenImpl#isSwitched <em>Switched</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalScreenImpl#getFrameType <em>Frame Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SignalScreenImpl extends MinimalEObjectImpl.Container implements SignalScreen {
	/**
	 * The default value of the '{@link #getScreen() <em>Screen</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScreen()
	 * @generated
	 * @ordered
	 */
	protected static final String SCREEN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getScreen() <em>Screen</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScreen()
	 * @generated
	 * @ordered
	 */
	protected String screen = SCREEN_EDEFAULT;

	/**
	 * The default value of the '{@link #isSwitched() <em>Switched</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSwitched()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SWITCHED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSwitched() <em>Switched</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSwitched()
	 * @generated
	 * @ordered
	 */
	protected boolean switched = SWITCHED_EDEFAULT;

	/**
	 * The default value of the '{@link #getFrameType() <em>Frame Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrameType()
	 * @generated
	 * @ordered
	 */
	protected static final String FRAME_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFrameType() <em>Frame Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrameType()
	 * @generated
	 * @ordered
	 */
	protected String frameType = FRAME_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SignalScreenImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.SIGNAL_SCREEN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getScreen() {
		return screen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setScreen(String newScreen) {
		String oldScreen = screen;
		screen = newScreen;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL_SCREEN__SCREEN, oldScreen, screen));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSwitched() {
		return switched;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSwitched(boolean newSwitched) {
		boolean oldSwitched = switched;
		switched = newSwitched;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL_SCREEN__SWITCHED, oldSwitched, switched));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFrameType() {
		return frameType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFrameType(String newFrameType) {
		String oldFrameType = frameType;
		frameType = newFrameType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL_SCREEN__FRAME_TYPE, oldFrameType, frameType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.SIGNAL_SCREEN__SCREEN:
				return getScreen();
			case SiteplanPackage.SIGNAL_SCREEN__SWITCHED:
				return isSwitched();
			case SiteplanPackage.SIGNAL_SCREEN__FRAME_TYPE:
				return getFrameType();
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
			case SiteplanPackage.SIGNAL_SCREEN__SCREEN:
				setScreen((String)newValue);
				return;
			case SiteplanPackage.SIGNAL_SCREEN__SWITCHED:
				setSwitched((Boolean)newValue);
				return;
			case SiteplanPackage.SIGNAL_SCREEN__FRAME_TYPE:
				setFrameType((String)newValue);
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
			case SiteplanPackage.SIGNAL_SCREEN__SCREEN:
				setScreen(SCREEN_EDEFAULT);
				return;
			case SiteplanPackage.SIGNAL_SCREEN__SWITCHED:
				setSwitched(SWITCHED_EDEFAULT);
				return;
			case SiteplanPackage.SIGNAL_SCREEN__FRAME_TYPE:
				setFrameType(FRAME_TYPE_EDEFAULT);
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
			case SiteplanPackage.SIGNAL_SCREEN__SCREEN:
				return SCREEN_EDEFAULT == null ? screen != null : !SCREEN_EDEFAULT.equals(screen);
			case SiteplanPackage.SIGNAL_SCREEN__SWITCHED:
				return switched != SWITCHED_EDEFAULT;
			case SiteplanPackage.SIGNAL_SCREEN__FRAME_TYPE:
				return FRAME_TYPE_EDEFAULT == null ? frameType != null : !FRAME_TYPE_EDEFAULT.equals(frameType);
		}
		return super.eIsSet(featureID);
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
		result.append(" (screen: ");
		result.append(screen);
		result.append(", switched: ");
		result.append(switched);
		result.append(", frameType: ");
		result.append(frameType);
		result.append(')');
		return result.toString();
	}

} //SignalScreenImpl
