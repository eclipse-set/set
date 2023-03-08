/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.set.model.validationreport.FileInfo;
import org.eclipse.set.model.validationreport.ValidationreportPackage;
import org.eclipse.set.model.validationreport.VersionInfo;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>File
 * Info</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.validationreport.impl.FileInfoImpl#getFileName
 * <em>File Name</em>}</li>
 * <li>{@link org.eclipse.set.model.validationreport.impl.FileInfoImpl#getUsedVersion
 * <em>Used Version</em>}</li>
 * <li>{@link org.eclipse.set.model.validationreport.impl.FileInfoImpl#getChecksum
 * <em>Checksum</em>}</li>
 * <li>{@link org.eclipse.set.model.validationreport.impl.FileInfoImpl#getGuid
 * <em>Guid</em>}</li>
 * <li>{@link org.eclipse.set.model.validationreport.impl.FileInfoImpl#getTimeStamp
 * <em>Time Stamp</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FileInfoImpl extends MinimalEObjectImpl.Container
		implements FileInfo {
	/**
	 * The default value of the '{@link #getChecksum() <em>Checksum</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChecksum()
	 * @generated
	 * @ordered
	 */
	protected static final String CHECKSUM_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getFileName() <em>File Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getGuid() <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGuid()
	 * @generated
	 * @ordered
	 */
	protected static final String GUID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getTimeStamp() <em>Time Stamp</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTimeStamp()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_STAMP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getChecksum() <em>Checksum</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChecksum()
	 * @generated
	 * @ordered
	 */
	protected String checksum = CHECKSUM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGuid() <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGuid()
	 * @generated
	 * @ordered
	 */
	protected String guid = GUID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTimeStamp() <em>Time Stamp</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTimeStamp()
	 * @generated
	 * @ordered
	 */
	protected String timeStamp = TIME_STAMP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUsedVersion() <em>Used Version</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUsedVersion()
	 * @generated
	 * @ordered
	 */
	protected VersionInfo usedVersion;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FileInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetUsedVersion(
			final VersionInfo newUsedVersion, NotificationChain msgs) {
		final VersionInfo oldUsedVersion = usedVersion;
		usedVersion = newUsedVersion;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					ValidationreportPackage.FILE_INFO__USED_VERSION,
					oldUsedVersion, newUsedVersion);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(final int featureID, final boolean resolve,
			final boolean coreType) {
		switch (featureID) {
		case ValidationreportPackage.FILE_INFO__FILE_NAME:
			return getFileName();
		case ValidationreportPackage.FILE_INFO__USED_VERSION:
			return getUsedVersion();
		case ValidationreportPackage.FILE_INFO__CHECKSUM:
			return getChecksum();
		case ValidationreportPackage.FILE_INFO__GUID:
			return getGuid();
		case ValidationreportPackage.FILE_INFO__TIME_STAMP:
			return getTimeStamp();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd,
			final int featureID, final NotificationChain msgs) {
		switch (featureID) {
		case ValidationreportPackage.FILE_INFO__USED_VERSION:
			return basicSetUsedVersion(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(final int featureID) {
		switch (featureID) {
		case ValidationreportPackage.FILE_INFO__FILE_NAME:
			return FILE_NAME_EDEFAULT == null ? fileName != null
					: !FILE_NAME_EDEFAULT.equals(fileName);
		case ValidationreportPackage.FILE_INFO__USED_VERSION:
			return usedVersion != null;
		case ValidationreportPackage.FILE_INFO__CHECKSUM:
			return CHECKSUM_EDEFAULT == null ? checksum != null
					: !CHECKSUM_EDEFAULT.equals(checksum);
		case ValidationreportPackage.FILE_INFO__GUID:
			return GUID_EDEFAULT == null ? guid != null
					: !GUID_EDEFAULT.equals(guid);
		case ValidationreportPackage.FILE_INFO__TIME_STAMP:
			return TIME_STAMP_EDEFAULT == null ? timeStamp != null
					: !TIME_STAMP_EDEFAULT.equals(timeStamp);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case ValidationreportPackage.FILE_INFO__FILE_NAME:
			setFileName((String) newValue);
			return;
		case ValidationreportPackage.FILE_INFO__USED_VERSION:
			setUsedVersion((VersionInfo) newValue);
			return;
		case ValidationreportPackage.FILE_INFO__CHECKSUM:
			setChecksum((String) newValue);
			return;
		case ValidationreportPackage.FILE_INFO__GUID:
			setGuid((String) newValue);
			return;
		case ValidationreportPackage.FILE_INFO__TIME_STAMP:
			setTimeStamp((String) newValue);
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
	public void eUnset(final int featureID) {
		switch (featureID) {
		case ValidationreportPackage.FILE_INFO__FILE_NAME:
			setFileName(FILE_NAME_EDEFAULT);
			return;
		case ValidationreportPackage.FILE_INFO__USED_VERSION:
			setUsedVersion((VersionInfo) null);
			return;
		case ValidationreportPackage.FILE_INFO__CHECKSUM:
			setChecksum(CHECKSUM_EDEFAULT);
			return;
		case ValidationreportPackage.FILE_INFO__GUID:
			setGuid(GUID_EDEFAULT);
			return;
		case ValidationreportPackage.FILE_INFO__TIME_STAMP:
			setTimeStamp(TIME_STAMP_EDEFAULT);
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
	public String getChecksum() {
		return checksum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getGuid() {
		return guid;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VersionInfo getUsedVersion() {
		return usedVersion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setChecksum(final String newChecksum) {
		final String oldChecksum = checksum;
		checksum = newChecksum;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					ValidationreportPackage.FILE_INFO__CHECKSUM, oldChecksum,
					checksum));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFileName(final String newFileName) {
		final String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					ValidationreportPackage.FILE_INFO__FILE_NAME, oldFileName,
					fileName));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setGuid(final String newGuid) {
		final String oldGuid = guid;
		guid = newGuid;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					ValidationreportPackage.FILE_INFO__GUID, oldGuid, guid));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTimeStamp(final String newTimeStamp) {
		final String oldTimeStamp = timeStamp;
		timeStamp = newTimeStamp;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					ValidationreportPackage.FILE_INFO__TIME_STAMP, oldTimeStamp,
					timeStamp));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setUsedVersion(final VersionInfo newUsedVersion) {
		if (newUsedVersion != usedVersion) {
			NotificationChain msgs = null;
			if (usedVersion != null) {
				msgs = ((InternalEObject) usedVersion).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- ValidationreportPackage.FILE_INFO__USED_VERSION,
						null, msgs);
			}
			if (newUsedVersion != null) {
				msgs = ((InternalEObject) newUsedVersion).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- ValidationreportPackage.FILE_INFO__USED_VERSION,
						null, msgs);
			}
			msgs = basicSetUsedVersion(newUsedVersion, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					ValidationreportPackage.FILE_INFO__USED_VERSION,
					newUsedVersion, newUsedVersion));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (fileName: ");
		result.append(fileName);
		result.append(", checksum: ");
		result.append(checksum);
		result.append(", guid: ");
		result.append(guid);
		result.append(", timeStamp: ");
		result.append(timeStamp);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationreportPackage.Literals.FILE_INFO;
	}

} // FileInfoImpl
