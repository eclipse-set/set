/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.model.validationreport.ObjectState;
import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.model.validationreport.ValidationreportPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Validation Problem</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getSeverityText <em>Severity Text</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getObjectArt <em>Object Art</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getAttributeName <em>Attribute Name</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getObjectScope <em>Object Scope</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.ValidationProblemImpl#getObjectState <em>Object State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ValidationProblemImpl extends MinimalEObjectImpl.Container implements ValidationProblem {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final ValidationSeverity SEVERITY_EDEFAULT = ValidationSeverity.ERROR;

	/**
	 * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected ValidationSeverity severity = SEVERITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSeverityText() <em>Severity Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverityText()
	 * @generated
	 * @ordered
	 */
	protected static final String SEVERITY_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSeverityText() <em>Severity Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverityText()
	 * @generated
	 * @ordered
	 */
	protected String severityText = SEVERITY_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected int lineNumber = LINE_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getObjectArt() <em>Object Art</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectArt()
	 * @generated
	 * @ordered
	 */
	protected static final String OBJECT_ART_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectArt() <em>Object Art</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectArt()
	 * @generated
	 * @ordered
	 */
	protected String objectArt = OBJECT_ART_EDEFAULT;

	/**
	 * The default value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeName()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTRIBUTE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeName()
	 * @generated
	 * @ordered
	 */
	protected String attributeName = ATTRIBUTE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getObjectScope() <em>Object Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectScope()
	 * @generated
	 * @ordered
	 */
	protected static final ObjectScope OBJECT_SCOPE_EDEFAULT = ObjectScope.UNKNOWN;

	/**
	 * The cached value of the '{@link #getObjectScope() <em>Object Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectScope()
	 * @generated
	 * @ordered
	 */
	protected ObjectScope objectScope = OBJECT_SCOPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getObjectState() <em>Object State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectState()
	 * @generated
	 * @ordered
	 */
	protected static final ObjectState OBJECT_STATE_EDEFAULT = ObjectState.INITIAL;

	/**
	 * The cached value of the '{@link #getObjectState() <em>Object State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectState()
	 * @generated
	 * @ordered
	 */
	protected ObjectState objectState = OBJECT_STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValidationProblemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationreportPackage.Literals.VALIDATION_PROBLEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValidationSeverity getSeverity() {
		return severity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSeverity(ValidationSeverity newSeverity) {
		ValidationSeverity oldSeverity = severity;
		severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY, oldSeverity, severity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSeverityText() {
		return severityText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSeverityText(String newSeverityText) {
		String oldSeverityText = severityText;
		severityText = newSeverityText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY_TEXT, oldSeverityText, severityText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__LINE_NUMBER, oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__MESSAGE, oldMessage, message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getObjectArt() {
		return objectArt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObjectArt(String newObjectArt) {
		String oldObjectArt = objectArt;
		objectArt = newObjectArt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_ART, oldObjectArt, objectArt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttributeName(String newAttributeName) {
		String oldAttributeName = attributeName;
		attributeName = newAttributeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__ATTRIBUTE_NAME, oldAttributeName, attributeName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ObjectScope getObjectScope() {
		return objectScope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObjectScope(ObjectScope newObjectScope) {
		ObjectScope oldObjectScope = objectScope;
		objectScope = newObjectScope == null ? OBJECT_SCOPE_EDEFAULT : newObjectScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_SCOPE, oldObjectScope, objectScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ObjectState getObjectState() {
		return objectState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObjectState(ObjectState newObjectState) {
		ObjectState oldObjectState = objectState;
		objectState = newObjectState == null ? OBJECT_STATE_EDEFAULT : newObjectState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_STATE, oldObjectState, objectState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ValidationreportPackage.VALIDATION_PROBLEM__ID:
				return getId();
			case ValidationreportPackage.VALIDATION_PROBLEM__TYPE:
				return getType();
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY:
				return getSeverity();
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY_TEXT:
				return getSeverityText();
			case ValidationreportPackage.VALIDATION_PROBLEM__LINE_NUMBER:
				return getLineNumber();
			case ValidationreportPackage.VALIDATION_PROBLEM__MESSAGE:
				return getMessage();
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_ART:
				return getObjectArt();
			case ValidationreportPackage.VALIDATION_PROBLEM__ATTRIBUTE_NAME:
				return getAttributeName();
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_SCOPE:
				return getObjectScope();
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_STATE:
				return getObjectState();
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
			case ValidationreportPackage.VALIDATION_PROBLEM__ID:
				setId((Integer)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__TYPE:
				setType((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY:
				setSeverity((ValidationSeverity)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY_TEXT:
				setSeverityText((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__LINE_NUMBER:
				setLineNumber((Integer)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__MESSAGE:
				setMessage((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_ART:
				setObjectArt((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__ATTRIBUTE_NAME:
				setAttributeName((String)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_SCOPE:
				setObjectScope((ObjectScope)newValue);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_STATE:
				setObjectState((ObjectState)newValue);
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
			case ValidationreportPackage.VALIDATION_PROBLEM__ID:
				setId(ID_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY:
				setSeverity(SEVERITY_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY_TEXT:
				setSeverityText(SEVERITY_TEXT_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__LINE_NUMBER:
				setLineNumber(LINE_NUMBER_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_ART:
				setObjectArt(OBJECT_ART_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__ATTRIBUTE_NAME:
				setAttributeName(ATTRIBUTE_NAME_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_SCOPE:
				setObjectScope(OBJECT_SCOPE_EDEFAULT);
				return;
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_STATE:
				setObjectState(OBJECT_STATE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("null")
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ValidationreportPackage.VALIDATION_PROBLEM__ID:
				return id != ID_EDEFAULT;
			case ValidationreportPackage.VALIDATION_PROBLEM__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY:
				return severity != SEVERITY_EDEFAULT;
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY_TEXT:
				return SEVERITY_TEXT_EDEFAULT == null ? severityText != null : !SEVERITY_TEXT_EDEFAULT.equals(severityText);
			case ValidationreportPackage.VALIDATION_PROBLEM__LINE_NUMBER:
				return lineNumber != LINE_NUMBER_EDEFAULT;
			case ValidationreportPackage.VALIDATION_PROBLEM__MESSAGE:
				return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_ART:
				return OBJECT_ART_EDEFAULT == null ? objectArt != null : !OBJECT_ART_EDEFAULT.equals(objectArt);
			case ValidationreportPackage.VALIDATION_PROBLEM__ATTRIBUTE_NAME:
				return ATTRIBUTE_NAME_EDEFAULT == null ? attributeName != null : !ATTRIBUTE_NAME_EDEFAULT.equals(attributeName);
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_SCOPE:
				return objectScope != OBJECT_SCOPE_EDEFAULT;
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_STATE:
				return objectState != OBJECT_STATE_EDEFAULT;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", type: ");
		result.append(type);
		result.append(", severity: ");
		result.append(severity);
		result.append(", severityText: ");
		result.append(severityText);
		result.append(", lineNumber: ");
		result.append(lineNumber);
		result.append(", message: ");
		result.append(message);
		result.append(", objectArt: ");
		result.append(objectArt);
		result.append(", attributeName: ");
		result.append(attributeName);
		result.append(", objectScope: ");
		result.append(objectScope);
		result.append(", objectState: ");
		result.append(objectState);
		result.append(')');
		return result.toString();
	}

} //ValidationProblemImpl
