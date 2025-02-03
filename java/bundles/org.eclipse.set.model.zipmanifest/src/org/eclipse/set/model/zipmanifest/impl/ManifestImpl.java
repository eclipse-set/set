/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.zipmanifest.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.zipmanifest.ContentList;
import org.eclipse.set.model.zipmanifest.Manifest;
import org.eclipse.set.model.zipmanifest.MediaList;
import org.eclipse.set.model.zipmanifest.ZipmanifestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.zipmanifest.impl.ManifestImpl#getContentList <em>Content List</em>}</li>
 *   <li>{@link org.eclipse.set.model.zipmanifest.impl.ManifestImpl#getMediaList <em>Media List</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ManifestImpl extends MinimalEObjectImpl.Container implements Manifest {
	/**
	 * The cached value of the '{@link #getContentList() <em>Content List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentList()
	 * @generated
	 * @ordered
	 */
	protected ContentList contentList;

	/**
	 * The cached value of the '{@link #getMediaList() <em>Media List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMediaList()
	 * @generated
	 * @ordered
	 */
	protected MediaList mediaList;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManifestImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ZipmanifestPackage.Literals.MANIFEST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContentList getContentList() {
		return contentList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContentList(ContentList newContentList, NotificationChain msgs) {
		ContentList oldContentList = contentList;
		contentList = newContentList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ZipmanifestPackage.MANIFEST__CONTENT_LIST, oldContentList, newContentList);
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
	public void setContentList(ContentList newContentList) {
		if (newContentList != contentList) {
			NotificationChain msgs = null;
			if (contentList != null)
				msgs = ((InternalEObject)contentList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ZipmanifestPackage.MANIFEST__CONTENT_LIST, null, msgs);
			if (newContentList != null)
				msgs = ((InternalEObject)newContentList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ZipmanifestPackage.MANIFEST__CONTENT_LIST, null, msgs);
			msgs = basicSetContentList(newContentList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZipmanifestPackage.MANIFEST__CONTENT_LIST, newContentList, newContentList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MediaList getMediaList() {
		return mediaList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMediaList(MediaList newMediaList, NotificationChain msgs) {
		MediaList oldMediaList = mediaList;
		mediaList = newMediaList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ZipmanifestPackage.MANIFEST__MEDIA_LIST, oldMediaList, newMediaList);
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
	public void setMediaList(MediaList newMediaList) {
		if (newMediaList != mediaList) {
			NotificationChain msgs = null;
			if (mediaList != null)
				msgs = ((InternalEObject)mediaList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ZipmanifestPackage.MANIFEST__MEDIA_LIST, null, msgs);
			if (newMediaList != null)
				msgs = ((InternalEObject)newMediaList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ZipmanifestPackage.MANIFEST__MEDIA_LIST, null, msgs);
			msgs = basicSetMediaList(newMediaList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZipmanifestPackage.MANIFEST__MEDIA_LIST, newMediaList, newMediaList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ZipmanifestPackage.MANIFEST__CONTENT_LIST:
				return basicSetContentList(null, msgs);
			case ZipmanifestPackage.MANIFEST__MEDIA_LIST:
				return basicSetMediaList(null, msgs);
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
			case ZipmanifestPackage.MANIFEST__CONTENT_LIST:
				return getContentList();
			case ZipmanifestPackage.MANIFEST__MEDIA_LIST:
				return getMediaList();
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
			case ZipmanifestPackage.MANIFEST__CONTENT_LIST:
				setContentList((ContentList)newValue);
				return;
			case ZipmanifestPackage.MANIFEST__MEDIA_LIST:
				setMediaList((MediaList)newValue);
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
			case ZipmanifestPackage.MANIFEST__CONTENT_LIST:
				setContentList((ContentList)null);
				return;
			case ZipmanifestPackage.MANIFEST__MEDIA_LIST:
				setMediaList((MediaList)null);
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
			case ZipmanifestPackage.MANIFEST__CONTENT_LIST:
				return contentList != null;
			case ZipmanifestPackage.MANIFEST__MEDIA_LIST:
				return mediaList != null;
		}
		return super.eIsSet(featureID);
	}

} //ManifestImpl
