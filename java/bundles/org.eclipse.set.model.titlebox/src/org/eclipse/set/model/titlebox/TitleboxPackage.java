/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.titlebox.TitleboxFactory
 * @model kind="package"
 * @generated
 */
public interface TitleboxPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "titlebox";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2017-03-23:planpro/tbm";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "tbm";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TitleboxPackage eINSTANCE = org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.titlebox.impl.TitleboxImpl
	 * <em>Titlebox</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.titlebox.impl.TitleboxImpl
	 * @see org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl#getTitlebox()
	 * @generated
	 */
	int TITLEBOX = 0;

	/**
	 * The feature id for the '<em><b>Field</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TITLEBOX__FIELD = 0;

	/**
	 * The feature id for the '<em><b>Planning Office</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TITLEBOX__PLANNING_OFFICE = 1;

	/**
	 * The number of structural features of the '<em>Titlebox</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TITLEBOX_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Titlebox</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TITLEBOX_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl
	 * <em>Planning Office</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl
	 * @see org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl#getPlanningOffice()
	 * @generated
	 */
	int PLANNING_OFFICE = 1;

	/**
	 * The feature id for the '<em><b>Variant</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE__VARIANT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Group</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE__GROUP = 2;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE__LOCATION = 3;

	/**
	 * The feature id for the '<em><b>Phone</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE__PHONE = 4;

	/**
	 * The feature id for the '<em><b>Email</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE__EMAIL = 5;

	/**
	 * The feature id for the '<em><b>Logo</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE__LOGO = 6;

	/**
	 * The number of structural features of the '<em>Planning Office</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Planning Office</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANNING_OFFICE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.titlebox.impl.StringFieldImpl <em>String
	 * Field</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.titlebox.impl.StringFieldImpl
	 * @see org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl#getStringField()
	 * @generated
	 */
	int STRING_FIELD = 2;

	/**
	 * The feature id for the '<em><b>Fontsize</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__FONTSIZE = 0;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD__TEXT = 1;

	/**
	 * The number of structural features of the '<em>String Field</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String Field</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.titlebox.Titlebox <em>Titlebox</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Titlebox</em>'.
	 * @see org.eclipse.set.model.titlebox.Titlebox
	 * @generated
	 */
	EClass getTitlebox();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.set.model.titlebox.Titlebox#getFieldList
	 * <em>Field</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Field</em>'.
	 * @see org.eclipse.set.model.titlebox.Titlebox#getFieldList()
	 * @see #getTitlebox()
	 * @generated
	 */
	EAttribute getTitlebox_Field();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.titlebox.Titlebox#getPlanningOffice
	 * <em>Planning Office</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Planning
	 *         Office</em>'.
	 * @see org.eclipse.set.model.titlebox.Titlebox#getPlanningOffice()
	 * @see #getTitlebox()
	 * @generated
	 */
	EReference getTitlebox_PlanningOffice();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice <em>Planning
	 * Office</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Planning Office</em>'.
	 * @see org.eclipse.set.model.titlebox.PlanningOffice
	 * @generated
	 */
	EClass getPlanningOffice();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getVariant
	 * <em>Variant</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Variant</em>'.
	 * @see org.eclipse.set.model.titlebox.PlanningOffice#getVariant()
	 * @see #getPlanningOffice()
	 * @generated
	 */
	EAttribute getPlanningOffice_Variant();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see org.eclipse.set.model.titlebox.PlanningOffice#getName()
	 * @see #getPlanningOffice()
	 * @generated
	 */
	EReference getPlanningOffice_Name();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getGroup
	 * <em>Group</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Group</em>'.
	 * @see org.eclipse.set.model.titlebox.PlanningOffice#getGroup()
	 * @see #getPlanningOffice()
	 * @generated
	 */
	EReference getPlanningOffice_Group();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getLocation
	 * <em>Location</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Location</em>'.
	 * @see org.eclipse.set.model.titlebox.PlanningOffice#getLocation()
	 * @see #getPlanningOffice()
	 * @generated
	 */
	EReference getPlanningOffice_Location();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getPhone
	 * <em>Phone</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Phone</em>'.
	 * @see org.eclipse.set.model.titlebox.PlanningOffice#getPhone()
	 * @see #getPlanningOffice()
	 * @generated
	 */
	EReference getPlanningOffice_Phone();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getEmail
	 * <em>Email</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Email</em>'.
	 * @see org.eclipse.set.model.titlebox.PlanningOffice#getEmail()
	 * @see #getPlanningOffice()
	 * @generated
	 */
	EReference getPlanningOffice_Email();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.titlebox.PlanningOffice#getLogo
	 * <em>Logo</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Logo</em>'.
	 * @see org.eclipse.set.model.titlebox.PlanningOffice#getLogo()
	 * @see #getPlanningOffice()
	 * @generated
	 */
	EAttribute getPlanningOffice_Logo();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.titlebox.StringField <em>String
	 * Field</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>String Field</em>'.
	 * @see org.eclipse.set.model.titlebox.StringField
	 * @generated
	 */
	EClass getStringField();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.titlebox.StringField#getFontsize
	 * <em>Fontsize</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Fontsize</em>'.
	 * @see org.eclipse.set.model.titlebox.StringField#getFontsize()
	 * @see #getStringField()
	 * @generated
	 */
	EAttribute getStringField_Fontsize();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.titlebox.StringField#getText
	 * <em>Text</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.set.model.titlebox.StringField#getText()
	 * @see #getStringField()
	 * @generated
	 */
	EAttribute getStringField_Text();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TitleboxFactory getTitleboxFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.titlebox.impl.TitleboxImpl
		 * <em>Titlebox</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.titlebox.impl.TitleboxImpl
		 * @see org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl#getTitlebox()
		 * @generated
		 */
		EClass TITLEBOX = eINSTANCE.getTitlebox();

		/**
		 * The meta object literal for the '<em><b>Field</b></em>' attribute
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TITLEBOX__FIELD = eINSTANCE.getTitlebox_Field();

		/**
		 * The meta object literal for the '<em><b>Planning Office</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TITLEBOX__PLANNING_OFFICE = eINSTANCE
				.getTitlebox_PlanningOffice();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl
		 * <em>Planning Office</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.titlebox.impl.PlanningOfficeImpl
		 * @see org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl#getPlanningOffice()
		 * @generated
		 */
		EClass PLANNING_OFFICE = eINSTANCE.getPlanningOffice();

		/**
		 * The meta object literal for the '<em><b>Variant</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLANNING_OFFICE__VARIANT = eINSTANCE
				.getPlanningOffice_Variant();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLANNING_OFFICE__NAME = eINSTANCE.getPlanningOffice_Name();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLANNING_OFFICE__GROUP = eINSTANCE.getPlanningOffice_Group();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLANNING_OFFICE__LOCATION = eINSTANCE
				.getPlanningOffice_Location();

		/**
		 * The meta object literal for the '<em><b>Phone</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLANNING_OFFICE__PHONE = eINSTANCE.getPlanningOffice_Phone();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLANNING_OFFICE__EMAIL = eINSTANCE.getPlanningOffice_Email();

		/**
		 * The meta object literal for the '<em><b>Logo</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLANNING_OFFICE__LOGO = eINSTANCE.getPlanningOffice_Logo();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.titlebox.impl.StringFieldImpl
		 * <em>String Field</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.titlebox.impl.StringFieldImpl
		 * @see org.eclipse.set.model.titlebox.impl.TitleboxPackageImpl#getStringField()
		 * @generated
		 */
		EClass STRING_FIELD = eINSTANCE.getStringField();

		/**
		 * The meta object literal for the '<em><b>Fontsize</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_FIELD__FONTSIZE = eINSTANCE.getStringField_Fontsize();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_FIELD__TEXT = eINSTANCE.getStringField_Text();

	}

} // TitleboxPackage
