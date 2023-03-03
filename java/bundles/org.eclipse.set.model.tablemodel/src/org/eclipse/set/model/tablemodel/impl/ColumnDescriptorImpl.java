/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.ColumnWidthMode;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Column Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#getWidthMode <em>Width Mode</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#isGreyed <em>Greyed</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#isUnit <em>Unit</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#isMergeCommonValues <em>Merge Common Values</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl#getColumnPosition <em>Column Position</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ColumnDescriptorImpl extends MinimalEObjectImpl.Container implements ColumnDescriptor {
	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final Float WIDTH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected Float width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getWidthMode() <em>Width Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidthMode()
	 * @generated
	 * @ordered
	 */
	protected static final ColumnWidthMode WIDTH_MODE_EDEFAULT = ColumnWidthMode.WIDTH_CM;

	/**
	 * The cached value of the '{@link #getWidthMode() <em>Width Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidthMode()
	 * @generated
	 * @ordered
	 */
	protected ColumnWidthMode widthMode = WIDTH_MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<ColumnDescriptor> children;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #isGreyed() <em>Greyed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGreyed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GREYED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGreyed() <em>Greyed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGreyed()
	 * @generated
	 * @ordered
	 */
	protected boolean greyed = GREYED_EDEFAULT;

	/**
	 * The default value of the '{@link #isUnit() <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnit()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UNIT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUnit() <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnit()
	 * @generated
	 * @ordered
	 */
	protected boolean unit = UNIT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected ColumnDescriptor parent;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final double HEIGHT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected double height = HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #isMergeCommonValues() <em>Merge Common Values</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMergeCommonValues()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MERGE_COMMON_VALUES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMergeCommonValues() <em>Merge Common Values</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMergeCommonValues()
	 * @generated
	 * @ordered
	 */
	protected boolean mergeCommonValues = MERGE_COMMON_VALUES_EDEFAULT;

	/**
	 * The default value of the '{@link #getColumnPosition() <em>Column Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnPosition()
	 * @generated
	 * @ordered
	 */
	protected static final String COLUMN_POSITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColumnPosition() <em>Column Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnPosition()
	 * @generated
	 * @ordered
	 */
	protected String columnPosition = COLUMN_POSITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ColumnDescriptorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.COLUMN_DESCRIPTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Float getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWidth(Float newWidth) {
		Float oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ColumnWidthMode getWidthMode() {
		return widthMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWidthMode(ColumnWidthMode newWidthMode) {
		ColumnWidthMode oldWidthMode = widthMode;
		widthMode = newWidthMode == null ? WIDTH_MODE_EDEFAULT : newWidthMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH_MODE, oldWidthMode, widthMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ColumnDescriptor> getChildren() {
		if (children == null) {
			children = new EObjectWithInverseResolvingEList<ColumnDescriptor>(ColumnDescriptor.class, this, TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN, TablemodelPackage.COLUMN_DESCRIPTOR__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGreyed() {
		return greyed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGreyed(boolean newGreyed) {
		boolean oldGreyed = greyed;
		greyed = newGreyed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__GREYED, oldGreyed, greyed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUnit() {
		return unit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUnit(boolean newUnit) {
		boolean oldUnit = unit;
		unit = newUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__UNIT, oldUnit, unit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ColumnDescriptor getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (ColumnDescriptor)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TablemodelPackage.COLUMN_DESCRIPTOR__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColumnDescriptor basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(ColumnDescriptor newParent, NotificationChain msgs) {
		ColumnDescriptor oldParent = parent;
		parent = newParent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__PARENT, oldParent, newParent);
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
	public void setParent(ColumnDescriptor newParent) {
		if (newParent != parent) {
			NotificationChain msgs = null;
			if (parent != null)
				msgs = ((InternalEObject)parent).eInverseRemove(this, TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN, ColumnDescriptor.class, msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN, ColumnDescriptor.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeight(double newHeight) {
		double oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMergeCommonValues() {
		return mergeCommonValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMergeCommonValues(boolean newMergeCommonValues) {
		boolean oldMergeCommonValues = mergeCommonValues;
		mergeCommonValues = newMergeCommonValues;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES, oldMergeCommonValues, mergeCommonValues));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getColumnPosition() {
		return columnPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setColumnPosition(String newColumnPosition) {
		String oldColumnPosition = columnPosition;
		columnPosition = newColumnPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.COLUMN_DESCRIPTOR__COLUMN_POSITION, oldColumnPosition, columnPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
			case TablemodelPackage.COLUMN_DESCRIPTOR__PARENT:
				if (parent != null)
					msgs = ((InternalEObject)parent).eInverseRemove(this, TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN, ColumnDescriptor.class, msgs);
				return basicSetParent((ColumnDescriptor)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case TablemodelPackage.COLUMN_DESCRIPTOR__PARENT:
				return basicSetParent(null, msgs);
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
			case TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH:
				return getWidth();
			case TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH_MODE:
				return getWidthMode();
			case TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN:
				return getChildren();
			case TablemodelPackage.COLUMN_DESCRIPTOR__LABEL:
				return getLabel();
			case TablemodelPackage.COLUMN_DESCRIPTOR__GREYED:
				return isGreyed();
			case TablemodelPackage.COLUMN_DESCRIPTOR__UNIT:
				return isUnit();
			case TablemodelPackage.COLUMN_DESCRIPTOR__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case TablemodelPackage.COLUMN_DESCRIPTOR__HEIGHT:
				return getHeight();
			case TablemodelPackage.COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES:
				return isMergeCommonValues();
			case TablemodelPackage.COLUMN_DESCRIPTOR__COLUMN_POSITION:
				return getColumnPosition();
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
			case TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH:
				setWidth((Float)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH_MODE:
				setWidthMode((ColumnWidthMode)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends ColumnDescriptor>)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__LABEL:
				setLabel((String)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__GREYED:
				setGreyed((Boolean)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__UNIT:
				setUnit((Boolean)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__PARENT:
				setParent((ColumnDescriptor)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__HEIGHT:
				setHeight((Double)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES:
				setMergeCommonValues((Boolean)newValue);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__COLUMN_POSITION:
				setColumnPosition((String)newValue);
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
			case TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH_MODE:
				setWidthMode(WIDTH_MODE_EDEFAULT);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN:
				getChildren().clear();
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__GREYED:
				setGreyed(GREYED_EDEFAULT);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__UNIT:
				setUnit(UNIT_EDEFAULT);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__PARENT:
				setParent((ColumnDescriptor)null);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES:
				setMergeCommonValues(MERGE_COMMON_VALUES_EDEFAULT);
				return;
			case TablemodelPackage.COLUMN_DESCRIPTOR__COLUMN_POSITION:
				setColumnPosition(COLUMN_POSITION_EDEFAULT);
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
			case TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH:
				return WIDTH_EDEFAULT == null ? width != null : !WIDTH_EDEFAULT.equals(width);
			case TablemodelPackage.COLUMN_DESCRIPTOR__WIDTH_MODE:
				return widthMode != WIDTH_MODE_EDEFAULT;
			case TablemodelPackage.COLUMN_DESCRIPTOR__CHILDREN:
				return children != null && !children.isEmpty();
			case TablemodelPackage.COLUMN_DESCRIPTOR__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case TablemodelPackage.COLUMN_DESCRIPTOR__GREYED:
				return greyed != GREYED_EDEFAULT;
			case TablemodelPackage.COLUMN_DESCRIPTOR__UNIT:
				return unit != UNIT_EDEFAULT;
			case TablemodelPackage.COLUMN_DESCRIPTOR__PARENT:
				return parent != null;
			case TablemodelPackage.COLUMN_DESCRIPTOR__HEIGHT:
				return height != HEIGHT_EDEFAULT;
			case TablemodelPackage.COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES:
				return mergeCommonValues != MERGE_COMMON_VALUES_EDEFAULT;
			case TablemodelPackage.COLUMN_DESCRIPTOR__COLUMN_POSITION:
				return COLUMN_POSITION_EDEFAULT == null ? columnPosition != null : !COLUMN_POSITION_EDEFAULT.equals(columnPosition);
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
		result.append(" (width: ");
		result.append(width);
		result.append(", widthMode: ");
		result.append(widthMode);
		result.append(", label: ");
		result.append(label);
		result.append(", greyed: ");
		result.append(greyed);
		result.append(", unit: ");
		result.append(unit);
		result.append(", height: ");
		result.append(height);
		result.append(", mergeCommonValues: ");
		result.append(mergeCommonValues);
		result.append(", columnPosition: ");
		result.append(columnPosition);
		result.append(')');
		return result.toString();
	}

} //ColumnDescriptorImpl
