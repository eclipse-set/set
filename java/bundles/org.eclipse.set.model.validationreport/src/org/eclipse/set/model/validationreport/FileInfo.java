/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>File
 * Info</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.validationreport.FileInfo#getFileName <em>File Name</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.FileInfo#getUsedVersion <em>Used Version</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.FileInfo#getChecksum <em>Checksum</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.FileInfo#getGuid <em>Guid</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.FileInfo#getTimeStamp <em>Time Stamp</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.FileInfo#getContainerContents <em>Container Contents</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getFileInfo()
 * @model
 * @generated
 */
public interface FileInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Checksum</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Checksum</em>' attribute.
	 * @see #setChecksum(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getFileInfo_Checksum()
	 * @model
	 * @generated
	 */
	String getChecksum();

	/**
	 * Returns the value of the '<em><b>File Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>File Name</em>' attribute.
	 * @see #setFileName(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getFileInfo_FileName()
	 * @model
	 * @generated
	 */
	String getFileName();

	/**
	 * Returns the value of the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Guid</em>' attribute.
	 * @see #setGuid(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getFileInfo_Guid()
	 * @model
	 * @generated
	 */
	String getGuid();

	/**
	 * Returns the value of the '<em><b>Time Stamp</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Time Stamp</em>' attribute.
	 * @see #setTimeStamp(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getFileInfo_TimeStamp()
	 * @model
	 * @generated
	 */
	String getTimeStamp();

	/**
	 * Returns the value of the '<em><b>Used Version</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Used Version</em>' containment reference.
	 * @see #setUsedVersion(VersionInfo)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getFileInfo_UsedVersion()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VersionInfo getUsedVersion();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.validationreport.FileInfo#getChecksum
	 * <em>Checksum</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Checksum</em>' attribute.
	 * @see #getChecksum()
	 * @generated
	 */
	void setChecksum(String value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.validationreport.FileInfo#getFileName
	 * <em>File Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>File Name</em>' attribute.
	 * @see #getFileName()
	 * @generated
	 */
	void setFileName(String value);

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.FileInfo#getGuid <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guid</em>' attribute.
	 * @see #getGuid()
	 * @generated
	 */
	void setGuid(String value);

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.FileInfo#getTimeStamp <em>Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Time Stamp</em>' attribute.
	 * @see #getTimeStamp()
	 * @generated
	 */
	void setTimeStamp(String value);

	/**
	 * Returns the value of the '<em><b>Container Contents</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container Contents</em>' attribute.
	 * @see #setContainerContents(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getFileInfo_ContainerContents()
	 * @model
	 * @generated
	 */
	String getContainerContents();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.FileInfo#getContainerContents <em>Container Contents</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container Contents</em>' attribute.
	 * @see #getContainerContents()
	 * @generated
	 */
	void setContainerContents(String value);

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.validationreport.FileInfo#getUsedVersion <em>Used Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Used Version</em>' containment reference.
	 * @see #getUsedVersion()
	 * @generated
	 */
	void setUsedVersion(VersionInfo value);

} // FileInfo
