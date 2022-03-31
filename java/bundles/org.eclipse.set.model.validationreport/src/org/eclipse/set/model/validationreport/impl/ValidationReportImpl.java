/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.impl;

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

import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.model.validationreport.ValidationreportPackage;
import org.eclipse.set.model.validationreport.VersionInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Validation Report</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getModelLoaded <em>Model Loaded</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getValid <em>Valid</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getXsdValid <em>Xsd Valid</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getEmfValid <em>Emf Valid</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getProblems <em>Problems</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getSupportedVersion <em>Supported Version</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getUsedVersion <em>Used Version</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getToolboxVersion <em>Toolbox Version</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getSubworkCount <em>Subwork Count</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationReportImpl#getSubworkTypes <em>Subwork Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ValidationReportImpl extends MinimalEObjectImpl.Container implements ValidationReport {
	/**
	 * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getModelLoaded() <em>Model Loaded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelLoaded()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_LOADED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModelLoaded() <em>Model Loaded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelLoaded()
	 * @generated
	 * @ordered
	 */
	protected String modelLoaded = MODEL_LOADED_EDEFAULT;

	/**
	 * The default value of the '{@link #getValid() <em>Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValid()
	 * @generated
	 * @ordered
	 */
	protected static final String VALID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValid() <em>Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValid()
	 * @generated
	 * @ordered
	 */
	protected String valid = VALID_EDEFAULT;

	/**
	 * The default value of the '{@link #getXsdValid() <em>Xsd Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXsdValid()
	 * @generated
	 * @ordered
	 */
	protected static final String XSD_VALID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXsdValid() <em>Xsd Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXsdValid()
	 * @generated
	 * @ordered
	 */
	protected String xsdValid = XSD_VALID_EDEFAULT;

	/**
	 * The default value of the '{@link #getEmfValid() <em>Emf Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmfValid()
	 * @generated
	 * @ordered
	 */
	protected static final String EMF_VALID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmfValid() <em>Emf Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmfValid()
	 * @generated
	 * @ordered
	 */
	protected String emfValid = EMF_VALID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProblems() <em>Problems</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProblems()
	 * @generated
	 * @ordered
	 */
	protected EList<ValidationProblem> problems;

	/**
	 * The cached value of the '{@link #getSupportedVersion() <em>Supported Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedVersion()
	 * @generated
	 * @ordered
	 */
	protected VersionInfo supportedVersion;

	/**
	 * The cached value of the '{@link #getUsedVersion() <em>Used Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsedVersion()
	 * @generated
	 * @ordered
	 */
	protected VersionInfo usedVersion;

	/**
	 * The default value of the '{@link #getToolboxVersion() <em>Toolbox Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolboxVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOLBOX_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getToolboxVersion() <em>Toolbox Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolboxVersion()
	 * @generated
	 * @ordered
	 */
	protected String toolboxVersion = TOOLBOX_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSubworkCount() <em>Subwork Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubworkCount()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBWORK_COUNT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubworkCount() <em>Subwork Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubworkCount()
	 * @generated
	 * @ordered
	 */
	protected String subworkCount = SUBWORK_COUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSubworkTypes() <em>Subwork Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubworkTypes()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBWORK_TYPES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubworkTypes() <em>Subwork Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubworkTypes()
	 * @generated
	 * @ordered
	 */
	protected String subworkTypes = SUBWORK_TYPES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValidationReportImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationreportPackage.Literals.VALIDATION_REPORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__FILE_NAME, oldFileName, fileName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModelLoaded() {
		return modelLoaded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModelLoaded(String newModelLoaded) {
		String oldModelLoaded = modelLoaded;
		modelLoaded = newModelLoaded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__MODEL_LOADED, oldModelLoaded, modelLoaded));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValid() {
		return valid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValid(String newValid) {
		String oldValid = valid;
		valid = newValid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__VALID, oldValid, valid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getXsdValid() {
		return xsdValid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setXsdValid(String newXsdValid) {
		String oldXsdValid = xsdValid;
		xsdValid = newXsdValid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__XSD_VALID, oldXsdValid, xsdValid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEmfValid() {
		return emfValid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmfValid(String newEmfValid) {
		String oldEmfValid = emfValid;
		emfValid = newEmfValid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__EMF_VALID, oldEmfValid, emfValid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ValidationProblem> getProblems() {
		if (problems == null) {
			problems = new EObjectContainmentEList<ValidationProblem>(ValidationProblem.class, this, ValidationreportPackage.VALIDATION_REPORT__PROBLEMS);
		}
		return problems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionInfo getSupportedVersion() {
		return supportedVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSupportedVersion(VersionInfo newSupportedVersion, NotificationChain msgs) {
		VersionInfo oldSupportedVersion = supportedVersion;
		supportedVersion = newSupportedVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION, oldSupportedVersion, newSupportedVersion);
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
	public void setSupportedVersion(VersionInfo newSupportedVersion) {
		if (newSupportedVersion != supportedVersion) {
			NotificationChain msgs = null;
			if (supportedVersion != null)
				msgs = ((InternalEObject)supportedVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION, null, msgs);
			if (newSupportedVersion != null)
				msgs = ((InternalEObject)newSupportedVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION, null, msgs);
			msgs = basicSetSupportedVersion(newSupportedVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION, newSupportedVersion, newSupportedVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionInfo getUsedVersion() {
		return usedVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUsedVersion(VersionInfo newUsedVersion, NotificationChain msgs) {
		VersionInfo oldUsedVersion = usedVersion;
		usedVersion = newUsedVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__USED_VERSION, oldUsedVersion, newUsedVersion);
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
	public void setUsedVersion(VersionInfo newUsedVersion) {
		if (newUsedVersion != usedVersion) {
			NotificationChain msgs = null;
			if (usedVersion != null)
				msgs = ((InternalEObject)usedVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ValidationreportPackage.VALIDATION_REPORT__USED_VERSION, null, msgs);
			if (newUsedVersion != null)
				msgs = ((InternalEObject)newUsedVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ValidationreportPackage.VALIDATION_REPORT__USED_VERSION, null, msgs);
			msgs = basicSetUsedVersion(newUsedVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__USED_VERSION, newUsedVersion, newUsedVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getToolboxVersion() {
		return toolboxVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setToolboxVersion(String newToolboxVersion) {
		String oldToolboxVersion = toolboxVersion;
		toolboxVersion = newToolboxVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__TOOLBOX_VERSION, oldToolboxVersion, toolboxVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSubworkCount() {
		return subworkCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubworkCount(String newSubworkCount) {
		String oldSubworkCount = subworkCount;
		subworkCount = newSubworkCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__SUBWORK_COUNT, oldSubworkCount, subworkCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSubworkTypes() {
		return subworkTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubworkTypes(String newSubworkTypes) {
		String oldSubworkTypes = subworkTypes;
		subworkTypes = newSubworkTypes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_REPORT__SUBWORK_TYPES, oldSubworkTypes, subworkTypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ValidationreportPackage.VALIDATION_REPORT__PROBLEMS:
				return ((InternalEList<?>)getProblems()).basicRemove(otherEnd, msgs);
			case ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION:
				return basicSetSupportedVersion(null, msgs);
			case ValidationreportPackage.VALIDATION_REPORT__USED_VERSION:
				return basicSetUsedVersion(null, msgs);
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
			case ValidationreportPackage.VALIDATION_REPORT__FILE_NAME:
				return getFileName();
			case ValidationreportPackage.VALIDATION_REPORT__MODEL_LOADED:
				return getModelLoaded();
			case ValidationreportPackage.VALIDATION_REPORT__VALID:
				return getValid();
			case ValidationreportPackage.VALIDATION_REPORT__XSD_VALID:
				return getXsdValid();
			case ValidationreportPackage.VALIDATION_REPORT__EMF_VALID:
				return getEmfValid();
			case ValidationreportPackage.VALIDATION_REPORT__PROBLEMS:
				return getProblems();
			case ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION:
				return getSupportedVersion();
			case ValidationreportPackage.VALIDATION_REPORT__USED_VERSION:
				return getUsedVersion();
			case ValidationreportPackage.VALIDATION_REPORT__TOOLBOX_VERSION:
				return getToolboxVersion();
			case ValidationreportPackage.VALIDATION_REPORT__SUBWORK_COUNT:
				return getSubworkCount();
			case ValidationreportPackage.VALIDATION_REPORT__SUBWORK_TYPES:
				return getSubworkTypes();
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
			case ValidationreportPackage.VALIDATION_REPORT__FILE_NAME:
				setFileName((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__MODEL_LOADED:
				setModelLoaded((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__VALID:
				setValid((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__XSD_VALID:
				setXsdValid((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__EMF_VALID:
				setEmfValid((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__PROBLEMS:
				getProblems().clear();
				getProblems().addAll((Collection<? extends ValidationProblem>)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION:
				setSupportedVersion((VersionInfo)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__USED_VERSION:
				setUsedVersion((VersionInfo)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__TOOLBOX_VERSION:
				setToolboxVersion((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__SUBWORK_COUNT:
				setSubworkCount((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__SUBWORK_TYPES:
				setSubworkTypes((String)newValue);
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
			case ValidationreportPackage.VALIDATION_REPORT__FILE_NAME:
				setFileName(FILE_NAME_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__MODEL_LOADED:
				setModelLoaded(MODEL_LOADED_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__VALID:
				setValid(VALID_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__XSD_VALID:
				setXsdValid(XSD_VALID_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__EMF_VALID:
				setEmfValid(EMF_VALID_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__PROBLEMS:
				getProblems().clear();
				return;
			case ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION:
				setSupportedVersion((VersionInfo)null);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__USED_VERSION:
				setUsedVersion((VersionInfo)null);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__TOOLBOX_VERSION:
				setToolboxVersion(TOOLBOX_VERSION_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__SUBWORK_COUNT:
				setSubworkCount(SUBWORK_COUNT_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_REPORT__SUBWORK_TYPES:
				setSubworkTypes(SUBWORK_TYPES_EDEFAULT);
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
			case ValidationreportPackage.VALIDATION_REPORT__FILE_NAME:
				return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
			case ValidationreportPackage.VALIDATION_REPORT__MODEL_LOADED:
				return MODEL_LOADED_EDEFAULT == null ? modelLoaded != null : !MODEL_LOADED_EDEFAULT.equals(modelLoaded);
			case ValidationreportPackage.VALIDATION_REPORT__VALID:
				return VALID_EDEFAULT == null ? valid != null : !VALID_EDEFAULT.equals(valid);
			case ValidationreportPackage.VALIDATION_REPORT__XSD_VALID:
				return XSD_VALID_EDEFAULT == null ? xsdValid != null : !XSD_VALID_EDEFAULT.equals(xsdValid);
			case ValidationreportPackage.VALIDATION_REPORT__EMF_VALID:
				return EMF_VALID_EDEFAULT == null ? emfValid != null : !EMF_VALID_EDEFAULT.equals(emfValid);
			case ValidationreportPackage.VALIDATION_REPORT__PROBLEMS:
				return problems != null && !problems.isEmpty();
			case ValidationreportPackage.VALIDATION_REPORT__SUPPORTED_VERSION:
				return supportedVersion != null;
			case ValidationreportPackage.VALIDATION_REPORT__USED_VERSION:
				return usedVersion != null;
			case ValidationreportPackage.VALIDATION_REPORT__TOOLBOX_VERSION:
				return TOOLBOX_VERSION_EDEFAULT == null ? toolboxVersion != null : !TOOLBOX_VERSION_EDEFAULT.equals(toolboxVersion);
			case ValidationreportPackage.VALIDATION_REPORT__SUBWORK_COUNT:
				return SUBWORK_COUNT_EDEFAULT == null ? subworkCount != null : !SUBWORK_COUNT_EDEFAULT.equals(subworkCount);
			case ValidationreportPackage.VALIDATION_REPORT__SUBWORK_TYPES:
				return SUBWORK_TYPES_EDEFAULT == null ? subworkTypes != null : !SUBWORK_TYPES_EDEFAULT.equals(subworkTypes);
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
		result.append(" (fileName: ");
		result.append(fileName);
		result.append(", modelLoaded: ");
		result.append(modelLoaded);
		result.append(", valid: ");
		result.append(valid);
		result.append(", xsdValid: ");
		result.append(xsdValid);
		result.append(", emfValid: ");
		result.append(emfValid);
		result.append(", toolboxVersion: ");
		result.append(toolboxVersion);
		result.append(", subworkCount: ");
		result.append(subworkCount);
		result.append(", subworkTypes: ");
		result.append(subworkTypes);
		result.append(')');
		return result.toString();
	}

} //ValidationReportImpl
