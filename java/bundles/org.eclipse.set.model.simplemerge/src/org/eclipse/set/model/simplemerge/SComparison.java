/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.simplemerge;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>SComparison</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.simplemerge.SComparison#getMatches
 * <em>Matches</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getSComparison()
 * @model
 * @generated
 */
public interface SComparison extends EObject {
	/**
	 * Returns the value of the '<em><b>Matches</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.simplemerge.SMatch}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Matches</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Matches</em>' containment reference list.
	 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getSComparison_Matches()
	 * @model containment="true"
	 * @generated
	 */
	EList<SMatch> getMatches();

} // SComparison
