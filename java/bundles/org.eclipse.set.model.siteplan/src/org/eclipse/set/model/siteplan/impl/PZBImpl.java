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

import org.eclipse.set.model.siteplan.PZB;
import org.eclipse.set.model.siteplan.PZBEffectivity;
import org.eclipse.set.model.siteplan.PZBElement;
import org.eclipse.set.model.siteplan.PZBType;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.PositionedObject;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>PZB</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PZBImpl#getPosition
 * <em>Position</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PZBImpl#getType
 * <em>Type</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PZBImpl#getElement
 * <em>Element</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PZBImpl#isRightSide <em>Right
 * Side</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.PZBImpl#getEffectivity
 * <em>Effectivity</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PZBImpl extends RouteObjectImpl implements PZB {
	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Position position;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final PZBType TYPE_EDEFAULT = PZBType.GM;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected PZBType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getElement() <em>Element</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected static final PZBElement ELEMENT_EDEFAULT = PZBElement.NONE;

	/**
	 * The cached value of the '{@link #getElement() <em>Element</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected PZBElement element = ELEMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isRightSide() <em>Right Side</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isRightSide()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RIGHT_SIDE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRightSide() <em>Right Side</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isRightSide()
	 * @generated
	 * @ordered
	 */
	protected boolean rightSide = RIGHT_SIDE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEffectivity() <em>Effectivity</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEffectivity()
	 * @generated
	 * @ordered
	 */
	protected static final PZBEffectivity EFFECTIVITY_EDEFAULT = PZBEffectivity.NONE;

	/**
	 * The cached value of the '{@link #getEffectivity() <em>Effectivity</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEffectivity()
	 * @generated
	 * @ordered
	 */
	protected PZBEffectivity effectivity = EFFECTIVITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PZBImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.PZB;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PZBType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setType(PZBType newType) {
		PZBType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.PZB__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PZBElement getElement() {
		return element;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setElement(PZBElement newElement) {
		PZBElement oldElement = element;
		element = newElement == null ? ELEMENT_EDEFAULT : newElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.PZB__ELEMENT, oldElement, element));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isRightSide() {
		return rightSide;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRightSide(boolean newRightSide) {
		boolean oldRightSide = rightSide;
		rightSide = newRightSide;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.PZB__RIGHT_SIDE, oldRightSide, rightSide));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PZBEffectivity getEffectivity() {
		return effectivity;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEffectivity(PZBEffectivity newEffectivity) {
		PZBEffectivity oldEffectivity = effectivity;
		effectivity = newEffectivity == null ? EFFECTIVITY_EDEFAULT
				: newEffectivity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.PZB__EFFECTIVITY, oldEffectivity,
					effectivity));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Position getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPosition(Position newPosition,
			NotificationChain msgs) {
		Position oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, SiteplanPackage.PZB__POSITION,
					oldPosition, newPosition);
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
	public void setPosition(Position newPosition) {
		if (newPosition != position) {
			NotificationChain msgs = null;
			if (position != null)
				msgs = ((InternalEObject) position).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SiteplanPackage.PZB__POSITION,
						null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject) newPosition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SiteplanPackage.PZB__POSITION,
						null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.PZB__POSITION, newPosition, newPosition));
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
			case SiteplanPackage.PZB__POSITION:
				return basicSetPosition(null, msgs);
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
			case SiteplanPackage.PZB__POSITION:
				return getPosition();
			case SiteplanPackage.PZB__TYPE:
				return getType();
			case SiteplanPackage.PZB__ELEMENT:
				return getElement();
			case SiteplanPackage.PZB__RIGHT_SIDE:
				return isRightSide();
			case SiteplanPackage.PZB__EFFECTIVITY:
				return getEffectivity();
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
			case SiteplanPackage.PZB__POSITION:
				setPosition((Position) newValue);
				return;
			case SiteplanPackage.PZB__TYPE:
				setType((PZBType) newValue);
				return;
			case SiteplanPackage.PZB__ELEMENT:
				setElement((PZBElement) newValue);
				return;
			case SiteplanPackage.PZB__RIGHT_SIDE:
				setRightSide((Boolean) newValue);
				return;
			case SiteplanPackage.PZB__EFFECTIVITY:
				setEffectivity((PZBEffectivity) newValue);
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
			case SiteplanPackage.PZB__POSITION:
				setPosition((Position) null);
				return;
			case SiteplanPackage.PZB__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case SiteplanPackage.PZB__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case SiteplanPackage.PZB__RIGHT_SIDE:
				setRightSide(RIGHT_SIDE_EDEFAULT);
				return;
			case SiteplanPackage.PZB__EFFECTIVITY:
				setEffectivity(EFFECTIVITY_EDEFAULT);
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
			case SiteplanPackage.PZB__POSITION:
				return position != null;
			case SiteplanPackage.PZB__TYPE:
				return type != TYPE_EDEFAULT;
			case SiteplanPackage.PZB__ELEMENT:
				return element != ELEMENT_EDEFAULT;
			case SiteplanPackage.PZB__RIGHT_SIDE:
				return rightSide != RIGHT_SIDE_EDEFAULT;
			case SiteplanPackage.PZB__EFFECTIVITY:
				return effectivity != EFFECTIVITY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID,
			Class<?> baseClass) {
		if (baseClass == PositionedObject.class) {
			switch (derivedFeatureID) {
				case SiteplanPackage.PZB__POSITION:
					return SiteplanPackage.POSITIONED_OBJECT__POSITION;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID,
			Class<?> baseClass) {
		if (baseClass == PositionedObject.class) {
			switch (baseFeatureID) {
				case SiteplanPackage.POSITIONED_OBJECT__POSITION:
					return SiteplanPackage.PZB__POSITION;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (type: ");
		result.append(type);
		result.append(", element: ");
		result.append(element);
		result.append(", rightSide: ");
		result.append(rightSide);
		result.append(", effectivity: ");
		result.append(effectivity);
		result.append(')');
		return result.toString();
	}

} // PZBImpl
