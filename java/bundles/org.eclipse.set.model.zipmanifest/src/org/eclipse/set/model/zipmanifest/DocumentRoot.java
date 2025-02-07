/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.zipmanifest;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Document
 * Root</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.zipmanifest.DocumentRoot#getMixed
 * <em>Mixed</em>}</li>
 * <li>{@link org.eclipse.set.model.zipmanifest.DocumentRoot#getManifest
 * <em>Manifest</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list. The list
	 * contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry"
	 *        many="true" extendedMetaData="kind='elementWildcard'
	 *        name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>Manifest</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Manifest</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Manifest</em>' containment reference.
	 * @see #setManifest(Manifest)
	 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getDocumentRoot_Manifest()
	 * @model containment="true" upper="-2" transient="true" volatile="true"
	 *        derived="true" extendedMetaData="kind='element' name='manifest'
	 *        namespace='##targetNamespace'"
	 * @generated
	 */
	Manifest getManifest();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.zipmanifest.DocumentRoot#getManifest
	 * <em>Manifest</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Manifest</em>' containment
	 *            reference.
	 * @see #getManifest()
	 * @generated
	 */
	void setManifest(Manifest value);

} // DocumentRoot
