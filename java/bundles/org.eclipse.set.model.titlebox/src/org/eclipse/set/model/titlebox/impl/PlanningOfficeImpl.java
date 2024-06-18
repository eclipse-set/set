/**
 * Copyright (c) {Jahr} DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.titlebox.PlanningOffice;
import org.eclipse.set.model.titlebox.StringField;
import org.eclipse.set.model.titlebox.TitleboxPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Planning Office</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl#getVariant <em>Variant</em>}</li>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl#getPhone <em>Phone</em>}</li>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl#getLogo <em>Logo</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PlanningOfficeImpl extends MinimalEObjectImpl.Container implements PlanningOffice {
	/**
	 * The default value of the '{@link #getVariant() <em>Variant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariant()
	 * @generated
	 * @ordered
	 */
	protected static final String VARIANT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVariant() <em>Variant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariant()
	 * @generated
	 * @ordered
	 */
	protected String variant = VARIANT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected StringField name;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected StringField group;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected StringField location;

	/**
	 * The cached value of the '{@link #getPhone() <em>Phone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhone()
	 * @generated
	 * @ordered
	 */
	protected StringField phone;

	/**
	 * The cached value of the '{@link #getEmail() <em>Email</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected StringField email;

	/**
	 * The default value of the '{@link #getLogo() <em>Logo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLogo()
	 * @generated
	 * @ordered
	 */
	protected static final String LOGO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLogo() <em>Logo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLogo()
	 * @generated
	 * @ordered
	 */
	protected String logo = LOGO_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlanningOfficeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TitleboxPackage.Literals.PLANNING_OFFICE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getVariant() {
		return variant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVariant(String newVariant) {
		String oldVariant = variant;
		variant = newVariant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__VARIANT, oldVariant, variant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringField getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetName(StringField newName, NotificationChain msgs) {
		StringField oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__NAME, oldName, newName);
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
	public void setName(StringField newName) {
		if (newName != name) {
			NotificationChain msgs = null;
			if (name != null)
				msgs = ((InternalEObject)name).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__NAME, null, msgs);
			if (newName != null)
				msgs = ((InternalEObject)newName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__NAME, null, msgs);
			msgs = basicSetName(newName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__NAME, newName, newName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringField getGroup() {
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroup(StringField newGroup, NotificationChain msgs) {
		StringField oldGroup = group;
		group = newGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__GROUP, oldGroup, newGroup);
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
	public void setGroup(StringField newGroup) {
		if (newGroup != group) {
			NotificationChain msgs = null;
			if (group != null)
				msgs = ((InternalEObject)group).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__GROUP, null, msgs);
			if (newGroup != null)
				msgs = ((InternalEObject)newGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__GROUP, null, msgs);
			msgs = basicSetGroup(newGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__GROUP, newGroup, newGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringField getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocation(StringField newLocation, NotificationChain msgs) {
		StringField oldLocation = location;
		location = newLocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__LOCATION, oldLocation, newLocation);
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
	public void setLocation(StringField newLocation) {
		if (newLocation != location) {
			NotificationChain msgs = null;
			if (location != null)
				msgs = ((InternalEObject)location).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__LOCATION, null, msgs);
			if (newLocation != null)
				msgs = ((InternalEObject)newLocation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__LOCATION, null, msgs);
			msgs = basicSetLocation(newLocation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__LOCATION, newLocation, newLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringField getPhone() {
		return phone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPhone(StringField newPhone, NotificationChain msgs) {
		StringField oldPhone = phone;
		phone = newPhone;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__PHONE, oldPhone, newPhone);
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
	public void setPhone(StringField newPhone) {
		if (newPhone != phone) {
			NotificationChain msgs = null;
			if (phone != null)
				msgs = ((InternalEObject)phone).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__PHONE, null, msgs);
			if (newPhone != null)
				msgs = ((InternalEObject)newPhone).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__PHONE, null, msgs);
			msgs = basicSetPhone(newPhone, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__PHONE, newPhone, newPhone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringField getEmail() {
		return email;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEmail(StringField newEmail, NotificationChain msgs) {
		StringField oldEmail = email;
		email = newEmail;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__EMAIL, oldEmail, newEmail);
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
	public void setEmail(StringField newEmail) {
		if (newEmail != email) {
			NotificationChain msgs = null;
			if (email != null)
				msgs = ((InternalEObject)email).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__EMAIL, null, msgs);
			if (newEmail != null)
				msgs = ((InternalEObject)newEmail).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.PLANNING_OFFICE__EMAIL, null, msgs);
			msgs = basicSetEmail(newEmail, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__EMAIL, newEmail, newEmail));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLogo() {
		return logo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLogo(String newLogo) {
		String oldLogo = logo;
		logo = newLogo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TitleboxPackage.PLANNING_OFFICE__LOGO, oldLogo, logo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TitleboxPackage.PLANNING_OFFICE__NAME:
				return basicSetName(null, msgs);
			case TitleboxPackage.PLANNING_OFFICE__GROUP:
				return basicSetGroup(null, msgs);
			case TitleboxPackage.PLANNING_OFFICE__LOCATION:
				return basicSetLocation(null, msgs);
			case TitleboxPackage.PLANNING_OFFICE__PHONE:
				return basicSetPhone(null, msgs);
			case TitleboxPackage.PLANNING_OFFICE__EMAIL:
				return basicSetEmail(null, msgs);
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
			case TitleboxPackage.PLANNING_OFFICE__VARIANT:
				return getVariant();
			case TitleboxPackage.PLANNING_OFFICE__NAME:
				return getName();
			case TitleboxPackage.PLANNING_OFFICE__GROUP:
				return getGroup();
			case TitleboxPackage.PLANNING_OFFICE__LOCATION:
				return getLocation();
			case TitleboxPackage.PLANNING_OFFICE__PHONE:
				return getPhone();
			case TitleboxPackage.PLANNING_OFFICE__EMAIL:
				return getEmail();
			case TitleboxPackage.PLANNING_OFFICE__LOGO:
				return getLogo();
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
			case TitleboxPackage.PLANNING_OFFICE__VARIANT:
				setVariant((String)newValue);
				return;
			case TitleboxPackage.PLANNING_OFFICE__NAME:
				setName((StringField)newValue);
				return;
			case TitleboxPackage.PLANNING_OFFICE__GROUP:
				setGroup((StringField)newValue);
				return;
			case TitleboxPackage.PLANNING_OFFICE__LOCATION:
				setLocation((StringField)newValue);
				return;
			case TitleboxPackage.PLANNING_OFFICE__PHONE:
				setPhone((StringField)newValue);
				return;
			case TitleboxPackage.PLANNING_OFFICE__EMAIL:
				setEmail((StringField)newValue);
				return;
			case TitleboxPackage.PLANNING_OFFICE__LOGO:
				setLogo((String)newValue);
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
			case TitleboxPackage.PLANNING_OFFICE__VARIANT:
				setVariant(VARIANT_EDEFAULT);
				return;
			case TitleboxPackage.PLANNING_OFFICE__NAME:
				setName((StringField)null);
				return;
			case TitleboxPackage.PLANNING_OFFICE__GROUP:
				setGroup((StringField)null);
				return;
			case TitleboxPackage.PLANNING_OFFICE__LOCATION:
				setLocation((StringField)null);
				return;
			case TitleboxPackage.PLANNING_OFFICE__PHONE:
				setPhone((StringField)null);
				return;
			case TitleboxPackage.PLANNING_OFFICE__EMAIL:
				setEmail((StringField)null);
				return;
			case TitleboxPackage.PLANNING_OFFICE__LOGO:
				setLogo(LOGO_EDEFAULT);
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
			case TitleboxPackage.PLANNING_OFFICE__VARIANT:
				return VARIANT_EDEFAULT == null ? variant != null : !VARIANT_EDEFAULT.equals(variant);
			case TitleboxPackage.PLANNING_OFFICE__NAME:
				return name != null;
			case TitleboxPackage.PLANNING_OFFICE__GROUP:
				return group != null;
			case TitleboxPackage.PLANNING_OFFICE__LOCATION:
				return location != null;
			case TitleboxPackage.PLANNING_OFFICE__PHONE:
				return phone != null;
			case TitleboxPackage.PLANNING_OFFICE__EMAIL:
				return email != null;
			case TitleboxPackage.PLANNING_OFFICE__LOGO:
				return LOGO_EDEFAULT == null ? logo != null : !LOGO_EDEFAULT.equals(logo);
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
		result.append(" (variant: ");
		result.append(variant);
		result.append(", logo: ");
		result.append(logo);
		result.append(')');
		return result.toString();
	}

} //PlanningOfficeImpl
