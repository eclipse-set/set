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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Column
 * Descriptor</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Header column description. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidth
 * <em>Width</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidthMode
 * <em>Width Mode</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getChildren
 * <em>Children</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getLabel
 * <em>Label</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isGreyed
 * <em>Greyed</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isUnit
 * <em>Unit</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getParent
 * <em>Parent</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getHeight
 * <em>Height</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getMergeCommonValues
 * <em>Merge Common Values</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getColumnPosition
 * <em>Column Position</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor()
 * @model
 * @generated
 */
public interface ColumnDescriptor extends EObject {
	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
	 * width of the column in cm. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(Float)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_Width()
	 * @model
	 * @generated
	 */
	Float getWidth();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidth
	 * <em>Width</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(Float value);

	/**
	 * Returns the value of the '<em><b>Width Mode</b></em>' attribute. The
	 * default value is <code>"WIDTH_CM"</code>. The literals are from the
	 * enumeration {@link org.eclipse.set.model.tablemodel.ColumnWidthMode}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Width Mode</em>' attribute.
	 * @see org.eclipse.set.model.tablemodel.ColumnWidthMode
	 * @see #setWidthMode(ColumnWidthMode)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_WidthMode()
	 * @model default="WIDTH_CM"
	 * @generated
	 */
	ColumnWidthMode getWidthMode();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidthMode
	 * <em>Width Mode</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Width Mode</em>' attribute.
	 * @see org.eclipse.set.model.tablemodel.ColumnWidthMode
	 * @see #getWidthMode()
	 * @generated
	 */
	void setWidthMode(ColumnWidthMode value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' reference list. The
	 * list contents are of type
	 * {@link org.eclipse.set.model.tablemodel.ColumnDescriptor}. It is
	 * bidirectional and its opposite is
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getParent
	 * <em>Parent</em>}'. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> Header children. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Children</em>' reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_Children()
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getParent
	 * @model opposite="parent"
	 * @generated
	 */
	EList<ColumnDescriptor> getChildren();

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
	 * label of the column. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_Label()
	 * @model required="true"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getLabel
	 * <em>Label</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Greyed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Flag
	 * for greyed out columns. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Greyed</em>' attribute.
	 * @see #setGreyed(boolean)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_Greyed()
	 * @model required="true"
	 * @generated
	 */
	boolean isGreyed();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isGreyed
	 * <em>Greyed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Greyed</em>' attribute.
	 * @see #isGreyed()
	 * @generated
	 */
	void setGreyed(boolean value);

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Whether
	 * this descriptor describes a unit. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see #setUnit(boolean)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_Unit()
	 * @model required="true"
	 * @generated
	 */
	boolean isUnit();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isUnit
	 * <em>Unit</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Unit</em>' attribute.
	 * @see #isUnit()
	 * @generated
	 */
	void setUnit(boolean value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference. It is
	 * bidirectional and its opposite is
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getChildren
	 * <em>Children</em>}'. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The parent of this header. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(ColumnDescriptor)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_Parent()
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getChildren
	 * @model opposite="children"
	 * @generated
	 */
	ColumnDescriptor getParent();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getParent
	 * <em>Parent</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(ColumnDescriptor value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
	 * height of the row in cm. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(double)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_Height()
	 * @model
	 * @generated
	 */
	double getHeight();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getHeight
	 * <em>Height</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(double value);

	/**
	 * Returns the value of the '<em><b>Merge Common Values</b></em>' attribute.
	 * The default value is <code>"DEFAULT"</code>. The literals are from the
	 * enumeration {@link org.eclipse.set.model.tablemodel.RowMergeMode}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Merge Common Values</em>' attribute.
	 * @see org.eclipse.set.model.tablemodel.RowMergeMode
	 * @see #setMergeCommonValues(RowMergeMode)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_MergeCommonValues()
	 * @model default="DEFAULT"
	 * @generated
	 */
	RowMergeMode getMergeCommonValues();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getMergeCommonValues
	 * <em>Merge Common Values</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Merge Common Values</em>' attribute.
	 * @see org.eclipse.set.model.tablemodel.RowMergeMode
	 * @see #getMergeCommonValues()
	 * @generated
	 */
	void setMergeCommonValues(RowMergeMode value);

	/**
	 * Returns the value of the '<em><b>Column Position</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
	 * The position of this column <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Column Position</em>' attribute.
	 * @see #setColumnPosition(String)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnDescriptor_ColumnPosition()
	 * @model
	 * @generated
	 */
	String getColumnPosition();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getColumnPosition
	 * <em>Column Position</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Column Position</em>' attribute.
	 * @see #getColumnPosition()
	 * @generated
	 */
	void setColumnPosition(String value);

} // ColumnDescriptor
