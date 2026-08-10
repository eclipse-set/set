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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Footnote</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.Footnote#getOwnerObject <em>Owner
 * Object</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.Footnote#getBearbeitungsvermerk
 * <em>Bearbeitungsvermerk</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.Footnote#getReferenceColumn
 * <em>Reference Column</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getFootnote()
 * @model
 * @generated
 */
public interface Footnote extends EObject {
	/**
	 * Returns the value of the '<em><b>Owner Object</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Owner Object</em>' reference.
	 * @see #setOwnerObject(EObject)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getFootnote_OwnerObject()
	 * @model
	 * @generated
	 */
	EObject getOwnerObject();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.Footnote#getOwnerObject
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
	 * Returns the value of the '<em><b>Bearbeitungsvermerk</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Bearbeitungsvermerk</em>' reference.
	 * @see #setBearbeitungsvermerk(Bearbeitungsvermerk)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getFootnote_Bearbeitungsvermerk()
	 * @model
	 * @generated
	 */
	Bearbeitungsvermerk getBearbeitungsvermerk();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.Footnote#getBearbeitungsvermerk
	 * <em>Bearbeitungsvermerk</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Bearbeitungsvermerk</em>' reference.
	 * @see #getBearbeitungsvermerk()
	 * @generated
	 */
	void setBearbeitungsvermerk(Bearbeitungsvermerk value);

	/**
	 * Returns the value of the '<em><b>Reference Column</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Reference Column</em>' attribute.
	 * @see #setReferenceColumn(String)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getFootnote_ReferenceColumn()
	 * @model
	 * @generated
	 */
	String getReferenceColumn();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.Footnote#getReferenceColumn
	 * <em>Reference Column</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Reference Column</em>' attribute.
	 * @see #getReferenceColumn()
	 * @generated
	 */
	void setReferenceColumn(String value);

} // Footnote
