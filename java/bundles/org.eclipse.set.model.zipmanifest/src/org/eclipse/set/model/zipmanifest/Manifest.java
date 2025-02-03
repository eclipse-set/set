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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.zipmanifest.Manifest#getContentList <em>Content List</em>}</li>
 *   <li>{@link org.eclipse.set.model.zipmanifest.Manifest#getMediaList <em>Media List</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getManifest()
 * @model extendedMetaData="name='manifest' kind='elementOnly'"
 * @generated
 */
public interface Manifest extends EObject {
	/**
	 * Returns the value of the '<em><b>Content List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content List</em>' containment reference.
	 * @see #setContentList(ContentList)
	 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getManifest_ContentList()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ContentList getContentList();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.zipmanifest.Manifest#getContentList <em>Content List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content List</em>' containment reference.
	 * @see #getContentList()
	 * @generated
	 */
	void setContentList(ContentList value);

	/**
	 * Returns the value of the '<em><b>Media List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Media List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Media List</em>' containment reference.
	 * @see #setMediaList(MediaList)
	 * @see org.eclipse.set.model.zipmanifest.ZipmanifestPackage#getManifest_MediaList()
	 * @model containment="true" required="true"
	 * @generated
	 */
	MediaList getMediaList();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.zipmanifest.Manifest#getMediaList <em>Media List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Media List</em>' containment reference.
	 * @see #getMediaList()
	 * @generated
	 */
	void setMediaList(MediaList value);

} // Manifest
