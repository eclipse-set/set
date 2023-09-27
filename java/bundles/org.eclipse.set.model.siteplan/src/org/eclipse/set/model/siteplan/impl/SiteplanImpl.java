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

import org.eclipse.set.model.siteplan.Layoutinfo;
import org.eclipse.set.model.siteplan.ObjectManagement;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.SiteplanState;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Siteplan</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl#getChangedInitialState <em>Changed Initial State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl#getCommonState <em>Common State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl#getChangedFinalState <em>Changed Final State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl#getFinalState <em>Final State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl#getCenterPosition <em>Center Position</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl#getObjectManagement <em>Object Management</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl#getLayoutInfo <em>Layout Info</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SiteplanImpl extends MinimalEObjectImpl.Container implements Siteplan {
	/**
	 * The cached value of the '{@link #getInitialState() <em>Initial State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected SiteplanState initialState;

	/**
	 * The cached value of the '{@link #getChangedInitialState() <em>Changed Initial State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangedInitialState()
	 * @generated
	 * @ordered
	 */
	protected SiteplanState changedInitialState;

	/**
	 * The cached value of the '{@link #getCommonState() <em>Common State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommonState()
	 * @generated
	 * @ordered
	 */
	protected SiteplanState commonState;

	/**
	 * The cached value of the '{@link #getChangedFinalState() <em>Changed Final State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangedFinalState()
	 * @generated
	 * @ordered
	 */
	protected SiteplanState changedFinalState;

	/**
	 * The cached value of the '{@link #getFinalState() <em>Final State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFinalState()
	 * @generated
	 * @ordered
	 */
	protected SiteplanState finalState;

	/**
	 * The cached value of the '{@link #getCenterPosition() <em>Center Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCenterPosition()
	 * @generated
	 * @ordered
	 */
	protected Position centerPosition;

	/**
	 * The cached value of the '{@link #getObjectManagement() <em>Object Management</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectManagement()
	 * @generated
	 * @ordered
	 */
	protected EList<ObjectManagement> objectManagement;

	/**
	 * The cached value of the '{@link #getLayoutInfo() <em>Layout Info</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayoutInfo()
	 * @generated
	 * @ordered
	 */
	protected EList<Layoutinfo> layoutInfo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SiteplanImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.SITEPLAN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SiteplanState getInitialState() {
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialState(SiteplanState newInitialState, NotificationChain msgs) {
		SiteplanState oldInitialState = initialState;
		initialState = newInitialState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__INITIAL_STATE, oldInitialState, newInitialState);
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
	public void setInitialState(SiteplanState newInitialState) {
		if (newInitialState != initialState) {
			NotificationChain msgs = null;
			if (initialState != null)
				msgs = ((InternalEObject)initialState).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__INITIAL_STATE, null, msgs);
			if (newInitialState != null)
				msgs = ((InternalEObject)newInitialState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__INITIAL_STATE, null, msgs);
			msgs = basicSetInitialState(newInitialState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__INITIAL_STATE, newInitialState, newInitialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SiteplanState getChangedInitialState() {
		return changedInitialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChangedInitialState(SiteplanState newChangedInitialState, NotificationChain msgs) {
		SiteplanState oldChangedInitialState = changedInitialState;
		changedInitialState = newChangedInitialState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE, oldChangedInitialState, newChangedInitialState);
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
	public void setChangedInitialState(SiteplanState newChangedInitialState) {
		if (newChangedInitialState != changedInitialState) {
			NotificationChain msgs = null;
			if (changedInitialState != null)
				msgs = ((InternalEObject)changedInitialState).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE, null, msgs);
			if (newChangedInitialState != null)
				msgs = ((InternalEObject)newChangedInitialState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE, null, msgs);
			msgs = basicSetChangedInitialState(newChangedInitialState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE, newChangedInitialState, newChangedInitialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SiteplanState getCommonState() {
		return commonState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommonState(SiteplanState newCommonState, NotificationChain msgs) {
		SiteplanState oldCommonState = commonState;
		commonState = newCommonState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__COMMON_STATE, oldCommonState, newCommonState);
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
	public void setCommonState(SiteplanState newCommonState) {
		if (newCommonState != commonState) {
			NotificationChain msgs = null;
			if (commonState != null)
				msgs = ((InternalEObject)commonState).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__COMMON_STATE, null, msgs);
			if (newCommonState != null)
				msgs = ((InternalEObject)newCommonState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__COMMON_STATE, null, msgs);
			msgs = basicSetCommonState(newCommonState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__COMMON_STATE, newCommonState, newCommonState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SiteplanState getChangedFinalState() {
		return changedFinalState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChangedFinalState(SiteplanState newChangedFinalState, NotificationChain msgs) {
		SiteplanState oldChangedFinalState = changedFinalState;
		changedFinalState = newChangedFinalState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE, oldChangedFinalState, newChangedFinalState);
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
	public void setChangedFinalState(SiteplanState newChangedFinalState) {
		if (newChangedFinalState != changedFinalState) {
			NotificationChain msgs = null;
			if (changedFinalState != null)
				msgs = ((InternalEObject)changedFinalState).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE, null, msgs);
			if (newChangedFinalState != null)
				msgs = ((InternalEObject)newChangedFinalState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE, null, msgs);
			msgs = basicSetChangedFinalState(newChangedFinalState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE, newChangedFinalState, newChangedFinalState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SiteplanState getFinalState() {
		return finalState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFinalState(SiteplanState newFinalState, NotificationChain msgs) {
		SiteplanState oldFinalState = finalState;
		finalState = newFinalState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__FINAL_STATE, oldFinalState, newFinalState);
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
	public void setFinalState(SiteplanState newFinalState) {
		if (newFinalState != finalState) {
			NotificationChain msgs = null;
			if (finalState != null)
				msgs = ((InternalEObject)finalState).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__FINAL_STATE, null, msgs);
			if (newFinalState != null)
				msgs = ((InternalEObject)newFinalState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__FINAL_STATE, null, msgs);
			msgs = basicSetFinalState(newFinalState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__FINAL_STATE, newFinalState, newFinalState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getCenterPosition() {
		return centerPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCenterPosition(Position newCenterPosition, NotificationChain msgs) {
		Position oldCenterPosition = centerPosition;
		centerPosition = newCenterPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__CENTER_POSITION, oldCenterPosition, newCenterPosition);
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
	public void setCenterPosition(Position newCenterPosition) {
		if (newCenterPosition != centerPosition) {
			NotificationChain msgs = null;
			if (centerPosition != null)
				msgs = ((InternalEObject)centerPosition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__CENTER_POSITION, null, msgs);
			if (newCenterPosition != null)
				msgs = ((InternalEObject)newCenterPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SITEPLAN__CENTER_POSITION, null, msgs);
			msgs = basicSetCenterPosition(newCenterPosition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SITEPLAN__CENTER_POSITION, newCenterPosition, newCenterPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ObjectManagement> getObjectManagement() {
		if (objectManagement == null) {
			objectManagement = new EObjectContainmentEList<ObjectManagement>(ObjectManagement.class, this, SiteplanPackage.SITEPLAN__OBJECT_MANAGEMENT);
		}
		return objectManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Layoutinfo> getLayoutInfo() {
		if (layoutInfo == null) {
			layoutInfo = new EObjectContainmentEList<Layoutinfo>(Layoutinfo.class, this, SiteplanPackage.SITEPLAN__LAYOUT_INFO);
		}
		return layoutInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.SITEPLAN__INITIAL_STATE:
				return basicSetInitialState(null, msgs);
			case SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE:
				return basicSetChangedInitialState(null, msgs);
			case SiteplanPackage.SITEPLAN__COMMON_STATE:
				return basicSetCommonState(null, msgs);
			case SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE:
				return basicSetChangedFinalState(null, msgs);
			case SiteplanPackage.SITEPLAN__FINAL_STATE:
				return basicSetFinalState(null, msgs);
			case SiteplanPackage.SITEPLAN__CENTER_POSITION:
				return basicSetCenterPosition(null, msgs);
			case SiteplanPackage.SITEPLAN__OBJECT_MANAGEMENT:
				return ((InternalEList<?>)getObjectManagement()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN__LAYOUT_INFO:
				return ((InternalEList<?>)getLayoutInfo()).basicRemove(otherEnd, msgs);
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
			case SiteplanPackage.SITEPLAN__INITIAL_STATE:
				return getInitialState();
			case SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE:
				return getChangedInitialState();
			case SiteplanPackage.SITEPLAN__COMMON_STATE:
				return getCommonState();
			case SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE:
				return getChangedFinalState();
			case SiteplanPackage.SITEPLAN__FINAL_STATE:
				return getFinalState();
			case SiteplanPackage.SITEPLAN__CENTER_POSITION:
				return getCenterPosition();
			case SiteplanPackage.SITEPLAN__OBJECT_MANAGEMENT:
				return getObjectManagement();
			case SiteplanPackage.SITEPLAN__LAYOUT_INFO:
				return getLayoutInfo();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.SITEPLAN__INITIAL_STATE:
				setInitialState((SiteplanState)newValue);
				return;
			case SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE:
				setChangedInitialState((SiteplanState)newValue);
				return;
			case SiteplanPackage.SITEPLAN__COMMON_STATE:
				setCommonState((SiteplanState)newValue);
				return;
			case SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE:
				setChangedFinalState((SiteplanState)newValue);
				return;
			case SiteplanPackage.SITEPLAN__FINAL_STATE:
				setFinalState((SiteplanState)newValue);
				return;
			case SiteplanPackage.SITEPLAN__CENTER_POSITION:
				setCenterPosition((Position)newValue);
				return;
			case SiteplanPackage.SITEPLAN__OBJECT_MANAGEMENT:
				getObjectManagement().clear();
				getObjectManagement().addAll((Collection<? extends ObjectManagement>)newValue);
				return;
			case SiteplanPackage.SITEPLAN__LAYOUT_INFO:
				getLayoutInfo().clear();
				getLayoutInfo().addAll((Collection<? extends Layoutinfo>)newValue);
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
			case SiteplanPackage.SITEPLAN__INITIAL_STATE:
				setInitialState((SiteplanState)null);
				return;
			case SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE:
				setChangedInitialState((SiteplanState)null);
				return;
			case SiteplanPackage.SITEPLAN__COMMON_STATE:
				setCommonState((SiteplanState)null);
				return;
			case SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE:
				setChangedFinalState((SiteplanState)null);
				return;
			case SiteplanPackage.SITEPLAN__FINAL_STATE:
				setFinalState((SiteplanState)null);
				return;
			case SiteplanPackage.SITEPLAN__CENTER_POSITION:
				setCenterPosition((Position)null);
				return;
			case SiteplanPackage.SITEPLAN__OBJECT_MANAGEMENT:
				getObjectManagement().clear();
				return;
			case SiteplanPackage.SITEPLAN__LAYOUT_INFO:
				getLayoutInfo().clear();
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
			case SiteplanPackage.SITEPLAN__INITIAL_STATE:
				return initialState != null;
			case SiteplanPackage.SITEPLAN__CHANGED_INITIAL_STATE:
				return changedInitialState != null;
			case SiteplanPackage.SITEPLAN__COMMON_STATE:
				return commonState != null;
			case SiteplanPackage.SITEPLAN__CHANGED_FINAL_STATE:
				return changedFinalState != null;
			case SiteplanPackage.SITEPLAN__FINAL_STATE:
				return finalState != null;
			case SiteplanPackage.SITEPLAN__CENTER_POSITION:
				return centerPosition != null;
			case SiteplanPackage.SITEPLAN__OBJECT_MANAGEMENT:
				return objectManagement != null && !objectManagement.isEmpty();
			case SiteplanPackage.SITEPLAN__LAYOUT_INFO:
				return layoutInfo != null && !layoutInfo.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SiteplanImpl
