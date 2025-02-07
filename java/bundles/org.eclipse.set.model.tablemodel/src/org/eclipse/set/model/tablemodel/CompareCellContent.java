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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Compare
 * Cell Content</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Result of a compare operation between two cells.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareCellContent#getOldValue
 * <em>Old Value</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareCellContent#getNewValue
 * <em>New Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareCellContent()
 * @model
 * @generated
 */
public interface CompareCellContent extends CellContent {
	/**
	 * Returns the value of the '<em><b>Old Value</b></em>' attribute list. The
	 * list contents are of type {@link java.lang.String}. <!-- begin-user-doc
	 * --> <!-- end-user-doc --> <!-- begin-model-doc --> The old value <!--
	 * end-model-doc -->
	 * 
	 * @return the value of the '<em>Old Value</em>' attribute list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareCellContent_OldValue()
	 * @model
	 * @generated
	 */
	EList<String> getOldValue();

	/**
	 * Returns the value of the '<em><b>New Value</b></em>' attribute list. The
	 * list contents are of type {@link java.lang.String}. <!-- begin-user-doc
	 * --> <!-- end-user-doc --> <!-- begin-model-doc --> The new value <!--
	 * end-model-doc -->
	 * 
	 * @return the value of the '<em>New Value</em>' attribute list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareCellContent_NewValue()
	 * @model
	 * @generated
	 */
	EList<String> getNewValue();

} // CompareCellContent
