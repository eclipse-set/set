/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Color Cell Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.MultiColorCellContent#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.MultiColorCellContent#getSeperator <em>Seperator</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getMultiColorCellContent()
 * @model
 * @generated
 */
public interface MultiColorCellContent extends CellContent {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.set.model.tablemodel.MultiColorContent}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getMultiColorCellContent_Value()
	 * @model
	 * @generated
	 */
	EList<MultiColorContent> getValue();

	/**
	 * Returns the value of the '<em><b>Seperator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Seperator</em>' attribute.
	 * @see #setSeperator(String)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getMultiColorCellContent_Seperator()
	 * @model
	 * @generated
	 */
	String getSeperator();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.tablemodel.MultiColorCellContent#getSeperator <em>Seperator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Seperator</em>' attribute.
	 * @see #getSeperator()
	 * @generated
	 */
	void setSeperator(String value);

} // MultiColorCellContent
