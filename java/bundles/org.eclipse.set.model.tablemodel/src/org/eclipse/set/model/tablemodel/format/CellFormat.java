/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.format;

import org.eclipse.set.model.tablemodel.CellAnnotation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Cell
 * Format</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.format.CellFormat#getTextAlignment
 * <em>Text Alignment</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.format.CellFormat#isTopologicalCalculation
 * <em>Topological Calculation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.format.TableformatPackage#getCellFormat()
 * @model
 * @generated
 */
public interface CellFormat extends CellAnnotation {
	/**
	 * Returns the value of the '<em><b>Text Alignment</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.tablemodel.format.TextAlignment}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Text Alignment</em>' attribute.
	 * @see org.eclipse.set.model.tablemodel.format.TextAlignment
	 * @see #setTextAlignment(TextAlignment)
	 * @see org.eclipse.set.model.tablemodel.format.TableformatPackage#getCellFormat_TextAlignment()
	 * @model required="true"
	 * @generated
	 */
	TextAlignment getTextAlignment();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.format.CellFormat#getTextAlignment
	 * <em>Text Alignment</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Text Alignment</em>' attribute.
	 * @see org.eclipse.set.model.tablemodel.format.TextAlignment
	 * @see #getTextAlignment()
	 * @generated
	 */
	void setTextAlignment(TextAlignment value);

	/**
	 * Returns the value of the '<em><b>Topological Calculation</b></em>'
	 * attribute. The default value is <code>"false"</code>. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Topological Calculation</em>' attribute.
	 * @see #setTopologicalCalculation(boolean)
	 * @see org.eclipse.set.model.tablemodel.format.TableformatPackage#getCellFormat_TopologicalCalculation()
	 * @model default="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        required="true"
	 * @generated
	 */
	boolean isTopologicalCalculation();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.format.CellFormat#isTopologicalCalculation
	 * <em>Topological Calculation</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Topological Calculation</em>'
	 *            attribute.
	 * @see #isTopologicalCalculation()
	 * @generated
	 */
	void setTopologicalCalculation(boolean value);

} // CellFormat
