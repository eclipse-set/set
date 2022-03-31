/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.simplemerge;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SMatch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.simplemerge.SMatch#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.set.model.simplemerge.SMatch#getGuidPrimary <em>Guid Primary</em>}</li>
 *   <li>{@link org.eclipse.set.model.simplemerge.SMatch#getGuidSecondary <em>Guid Secondary</em>}</li>
 *   <li>{@link org.eclipse.set.model.simplemerge.SMatch#getResolution <em>Resolution</em>}</li>
 *   <li>{@link org.eclipse.set.model.simplemerge.SMatch#getElementType <em>Element Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getSMatch()
 * @model
 * @generated
 */
public interface SMatch extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getSMatch_Id()
	 * @model required="true"
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.simplemerge.SMatch#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Returns the value of the '<em><b>Guid Primary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guid Primary</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guid Primary</em>' attribute.
	 * @see #setGuidPrimary(String)
	 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getSMatch_GuidPrimary()
	 * @model
	 * @generated
	 */
	String getGuidPrimary();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.simplemerge.SMatch#getGuidPrimary <em>Guid Primary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guid Primary</em>' attribute.
	 * @see #getGuidPrimary()
	 * @generated
	 */
	void setGuidPrimary(String value);

	/**
	 * Returns the value of the '<em><b>Guid Secondary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guid Secondary</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guid Secondary</em>' attribute.
	 * @see #setGuidSecondary(String)
	 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getSMatch_GuidSecondary()
	 * @model
	 * @generated
	 */
	String getGuidSecondary();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.simplemerge.SMatch#getGuidSecondary <em>Guid Secondary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guid Secondary</em>' attribute.
	 * @see #getGuidSecondary()
	 * @generated
	 */
	void setGuidSecondary(String value);

	/**
	 * Returns the value of the '<em><b>Resolution</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.simplemerge.Resolution}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resolution</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resolution</em>' attribute.
	 * @see org.eclipse.set.model.simplemerge.Resolution
	 * @see #setResolution(Resolution)
	 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getSMatch_Resolution()
	 * @model
	 * @generated
	 */
	Resolution getResolution();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.simplemerge.SMatch#getResolution <em>Resolution</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resolution</em>' attribute.
	 * @see org.eclipse.set.model.simplemerge.Resolution
	 * @see #getResolution()
	 * @generated
	 */
	void setResolution(Resolution value);

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Type</em>' attribute.
	 * @see #setElementType(String)
	 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getSMatch_ElementType()
	 * @model
	 * @generated
	 */
	String getElementType();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.simplemerge.SMatch#getElementType <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Type</em>' attribute.
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(String value);

} // SMatch
