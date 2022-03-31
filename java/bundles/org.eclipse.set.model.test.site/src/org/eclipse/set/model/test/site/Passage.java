/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Passage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.Passage#getRoomID_A <em>Room ID A</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.Passage#getRoomID_B <em>Room ID B</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.test.site.SitePackage#getPassage()
 * @model
 * @generated
 */
public interface Passage extends Identified {
	/**
	 * Returns the value of the '<em><b>Room ID A</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Room ID A</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Room ID A</em>' attribute.
	 * @see #setRoomID_A(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getPassage_RoomID_A()
	 * @model
	 * @generated
	 */
	String getRoomID_A();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.Passage#getRoomID_A <em>Room ID A</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Room ID A</em>' attribute.
	 * @see #getRoomID_A()
	 * @generated
	 */
	void setRoomID_A(String value);

	/**
	 * Returns the value of the '<em><b>Room ID B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Room ID B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Room ID B</em>' attribute.
	 * @see #setRoomID_B(String)
	 * @see org.eclipse.set.model.test.site.SitePackage#getPassage_RoomID_B()
	 * @model
	 * @generated
	 */
	String getRoomID_B();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.test.site.Passage#getRoomID_B <em>Room ID B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Room ID B</em>' attribute.
	 * @see #getRoomID_B()
	 * @generated
	 */
	void setRoomID_B(String value);

} // Passage
