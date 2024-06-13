/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 */
package org.eclipse.set.model.titlebox;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>String
 * Field</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.titlebox.StringField#getFontsize
 * <em>Fontsize</em>}</li>
 * <li>{@link org.eclipse.set.model.titlebox.StringField#getText
 * <em>Text</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getStringField()
 * @model
 * @generated
 */
public interface StringField extends EObject {
	/**
	 * Returns the value of the '<em><b>Fontsize</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Fontsize</em>' attribute.
	 * @see #setFontsize(String)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getStringField_Fontsize()
	 * @model
	 * @generated
	 */
	String getFontsize();

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see org.eclipse.set.model.titlebox.TitleboxPackage#getStringField_Text()
	 * @model
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.StringField#getFontsize
	 * <em>Fontsize</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Fontsize</em>' attribute.
	 * @see #getFontsize()
	 * @generated
	 */
	void setFontsize(String value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.titlebox.StringField#getText
	 * <em>Text</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

} // StringField
