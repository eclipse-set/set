/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.zipmanifest;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.zipmanifest.ZipmanifestFactory
 * @model kind="package"
 * @generated
 */
public interface ZipmanifestPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "zipmanifest";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "tag:org.eclipse.set,2020-02-21:planpro/ppzipmanifest";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ZipmanifestPackage eINSTANCE = org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.zipmanifest.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.zipmanifest.impl.DocumentRootImpl
	 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>Manifest</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MANIFEST = 1;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.zipmanifest.impl.ManifestImpl <em>Manifest</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.zipmanifest.impl.ManifestImpl
	 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getManifest()
	 * @generated
	 */
	int MANIFEST = 1;

	/**
	 * The feature id for the '<em><b>Content List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__CONTENT_LIST = 0;

	/**
	 * The feature id for the '<em><b>Media List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__MEDIA_LIST = 1;

	/**
	 * The number of structural features of the '<em>Manifest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Manifest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.zipmanifest.impl.ContentImpl <em>Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.zipmanifest.impl.ContentImpl
	 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getContent()
	 * @generated
	 */
	int CONTENT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.zipmanifest.impl.ContentListImpl <em>Content List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.zipmanifest.impl.ContentListImpl
	 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getContentList()
	 * @generated
	 */
	int CONTENT_LIST = 3;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_LIST__CONTENT = 0;

	/**
	 * The number of structural features of the '<em>Content List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_LIST_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Content List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_LIST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.zipmanifest.impl.MediaListImpl <em>Media List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.zipmanifest.impl.MediaListImpl
	 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getMediaList()
	 * @generated
	 */
	int MEDIA_LIST = 4;

	/**
	 * The feature id for the '<em><b>Media</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_LIST__MEDIA = 0;

	/**
	 * The number of structural features of the '<em>Media List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_LIST_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Media List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_LIST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.zipmanifest.impl.MediaImpl <em>Media</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.zipmanifest.impl.MediaImpl
	 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getMedia()
	 * @generated
	 */
	int MEDIA = 5;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA__GUID = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Media</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Media</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEDIA_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.zipmanifest.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.eclipse.set.model.zipmanifest.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.set.model.zipmanifest.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.eclipse.set.model.zipmanifest.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.zipmanifest.DocumentRoot#getManifest <em>Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Manifest</em>'.
	 * @see org.eclipse.set.model.zipmanifest.DocumentRoot#getManifest()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Manifest();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.zipmanifest.Manifest <em>Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manifest</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Manifest
	 * @generated
	 */
	EClass getManifest();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.zipmanifest.Manifest#getContentList <em>Content List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Content List</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Manifest#getContentList()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_ContentList();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.zipmanifest.Manifest#getMediaList <em>Media List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Media List</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Manifest#getMediaList()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_MediaList();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.zipmanifest.Content <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Content</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Content
	 * @generated
	 */
	EClass getContent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.zipmanifest.Content#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Content#getName()
	 * @see #getContent()
	 * @generated
	 */
	EAttribute getContent_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.zipmanifest.Content#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Content#getType()
	 * @see #getContent()
	 * @generated
	 */
	EAttribute getContent_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.zipmanifest.ContentList <em>Content List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Content List</em>'.
	 * @see org.eclipse.set.model.zipmanifest.ContentList
	 * @generated
	 */
	EClass getContentList();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.zipmanifest.ContentList#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Content</em>'.
	 * @see org.eclipse.set.model.zipmanifest.ContentList#getContent()
	 * @see #getContentList()
	 * @generated
	 */
	EReference getContentList_Content();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.zipmanifest.MediaList <em>Media List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Media List</em>'.
	 * @see org.eclipse.set.model.zipmanifest.MediaList
	 * @generated
	 */
	EClass getMediaList();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.zipmanifest.MediaList#getMedia <em>Media</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Media</em>'.
	 * @see org.eclipse.set.model.zipmanifest.MediaList#getMedia()
	 * @see #getMediaList()
	 * @generated
	 */
	EReference getMediaList_Media();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.zipmanifest.Media <em>Media</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Media</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Media
	 * @generated
	 */
	EClass getMedia();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.zipmanifest.Media#getGuid <em>Guid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guid</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Media#getGuid()
	 * @see #getMedia()
	 * @generated
	 */
	EAttribute getMedia_Guid();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.zipmanifest.Media#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.set.model.zipmanifest.Media#getType()
	 * @see #getMedia()
	 * @generated
	 */
	EAttribute getMedia_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ZipmanifestFactory getZipmanifestFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.zipmanifest.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.zipmanifest.impl.DocumentRootImpl
		 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>Manifest</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MANIFEST = eINSTANCE.getDocumentRoot_Manifest();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.zipmanifest.impl.ManifestImpl <em>Manifest</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.zipmanifest.impl.ManifestImpl
		 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getManifest()
		 * @generated
		 */
		EClass MANIFEST = eINSTANCE.getManifest();

		/**
		 * The meta object literal for the '<em><b>Content List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__CONTENT_LIST = eINSTANCE.getManifest_ContentList();

		/**
		 * The meta object literal for the '<em><b>Media List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__MEDIA_LIST = eINSTANCE.getManifest_MediaList();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.zipmanifest.impl.ContentImpl <em>Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.zipmanifest.impl.ContentImpl
		 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getContent()
		 * @generated
		 */
		EClass CONTENT = eINSTANCE.getContent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTENT__NAME = eINSTANCE.getContent_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTENT__TYPE = eINSTANCE.getContent_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.zipmanifest.impl.ContentListImpl <em>Content List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.zipmanifest.impl.ContentListImpl
		 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getContentList()
		 * @generated
		 */
		EClass CONTENT_LIST = eINSTANCE.getContentList();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTENT_LIST__CONTENT = eINSTANCE.getContentList_Content();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.zipmanifest.impl.MediaListImpl <em>Media List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.zipmanifest.impl.MediaListImpl
		 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getMediaList()
		 * @generated
		 */
		EClass MEDIA_LIST = eINSTANCE.getMediaList();

		/**
		 * The meta object literal for the '<em><b>Media</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEDIA_LIST__MEDIA = eINSTANCE.getMediaList_Media();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.zipmanifest.impl.MediaImpl <em>Media</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.zipmanifest.impl.MediaImpl
		 * @see org.eclipse.set.model.zipmanifest.impl.ZipmanifestPackageImpl#getMedia()
		 * @generated
		 */
		EClass MEDIA = eINSTANCE.getMedia();

		/**
		 * The meta object literal for the '<em><b>Guid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEDIA__GUID = eINSTANCE.getMedia_Guid();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEDIA__TYPE = eINSTANCE.getMedia_Type();

	}

} //ZipmanifestPackage
