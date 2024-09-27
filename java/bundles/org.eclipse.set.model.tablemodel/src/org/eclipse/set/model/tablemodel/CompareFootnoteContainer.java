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
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compare Footnote Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getOldFootnotes <em>Old Footnotes</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getNewFootnotes <em>New Footnotes</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getUnchangedFootnotes <em>Unchanged Footnotes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareFootnoteContainer()
 * @model
 * @generated
 */
public interface CompareFootnoteContainer extends FootnoteContainer {
	/**
	 * Returns the value of the '<em><b>Old Footnotes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Footnotes</em>' reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareFootnoteContainer_OldFootnotes()
	 * @model
	 * @generated
	 */
	EList<Bearbeitungsvermerk> getOldFootnotes();

	/**
	 * Returns the value of the '<em><b>New Footnotes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Footnotes</em>' reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareFootnoteContainer_NewFootnotes()
	 * @model
	 * @generated
	 */
	EList<Bearbeitungsvermerk> getNewFootnotes();

	/**
	 * Returns the value of the '<em><b>Unchanged Footnotes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unchanged Footnotes</em>' reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareFootnoteContainer_UnchangedFootnotes()
	 * @model
	 * @generated
	 */
	EList<Bearbeitungsvermerk> getUnchangedFootnotes();

} // CompareFootnoteContainer
