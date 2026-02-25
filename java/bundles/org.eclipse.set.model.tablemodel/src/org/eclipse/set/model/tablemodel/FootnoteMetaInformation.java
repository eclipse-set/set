/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Footnote
 * Meta Information</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.FootnoteMetaInformation#getOwnerObject
 * <em>Owner Object</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.FootnoteMetaInformation#getFootnote
 * <em>Footnote</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getFootnoteMetaInformation()
 * @model
 * @generated
 */
public interface FootnoteMetaInformation extends EObject {
	/**
	 * Returns the value of the '<em><b>Owner Object</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Owner Object</em>' reference.
	 * @see #setOwnerObject(Ur_Objekt)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getFootnoteMetaInformation_OwnerObject()
	 * @model
	 * @generated
	 */
	EObject getOwnerObject();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.FootnoteMetaInformation#getOwnerObject
	 * <em>Owner Object</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Owner Object</em>' reference.
	 * @see #getOwnerObject()
	 * @generated
	 */
	void setOwnerObject(EObject value);

	/**
	 * Returns the value of the '<em><b>Footnote</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Footnote</em>' reference.
	 * @see #setFootnote(Bearbeitungsvermerk)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getFootnoteMetaInformation_Footnote()
	 * @model
	 * @generated
	 */
	Bearbeitungsvermerk getFootnote();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.FootnoteMetaInformation#getFootnote
	 * <em>Footnote</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Footnote</em>' reference.
	 * @see #getFootnote()
	 * @generated
	 */
	void setFootnote(Bearbeitungsvermerk value);

} // FootnoteMetaInformation
