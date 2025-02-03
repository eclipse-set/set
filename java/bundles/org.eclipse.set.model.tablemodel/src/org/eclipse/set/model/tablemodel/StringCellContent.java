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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>String
 * Cell Content</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Standard cell with text. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.StringCellContent#getValue
 * <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getStringCellContent()
 * @model
 * @generated
 */
public interface StringCellContent extends CellContent {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute list. The list
	 * contents are of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <!-- end-user-doc --> <!-- begin-model-doc --> The text. <!--
	 * end-model-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getStringCellContent_Value()
	 * @model
	 * @generated
	 */
	EList<String> getValue();

} // StringCellContent
