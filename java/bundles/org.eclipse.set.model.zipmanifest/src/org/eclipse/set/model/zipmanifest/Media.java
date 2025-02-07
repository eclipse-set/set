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

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Media</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.zipmanifest.Media#getGuid
 * <em>Guid</em>}</li>
 * <li>{@link org.eclipse.set.model.zipmanifest.Media#getType
 * <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getMedia()
 * @model
 * @generated
 */
public interface Media extends EObject {
	/**
	 * Returns the value of the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guid</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Guid</em>' attribute.
	 * @see #setGuid(String)
	 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getMedia_Guid()
	 * @model
	 * @generated
	 */
	String getGuid();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.zipmanifest.Media#getGuid <em>Guid</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Guid</em>' attribute.
	 * @see #getGuid()
	 * @generated
	 */
	void setGuid(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getMedia_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.zipmanifest.Media#getType <em>Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // Media
