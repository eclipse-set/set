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
 * A representation of the model object '<em><b>Simple Footnote Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.SimpleFootnoteContainer#getFootnotes <em>Footnotes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getSimpleFootnoteContainer()
 * @model
 * @generated
 */
public interface SimpleFootnoteContainer extends FootnoteContainer {
	/**
	 * Returns the value of the '<em><b>Footnotes</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Footnotes</em>' attribute list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getSimpleFootnoteContainer_Footnotes()
	 * @model
	 * @generated
	 */
	EList<String> getFootnotes();

} // SimpleFootnoteContainer
