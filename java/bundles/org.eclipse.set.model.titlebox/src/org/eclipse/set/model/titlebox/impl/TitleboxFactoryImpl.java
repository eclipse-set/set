/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.set.model.titlebox.*;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TitleboxFactoryImpl extends EFactoryImpl
		implements TitleboxFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static TitleboxFactory init() {
		try {
			TitleboxFactory theTitleboxFactory = (TitleboxFactory) EPackage.Registry.INSTANCE
					.getEFactory(TitleboxPackage.eNS_URI);
			if (theTitleboxFactory != null) {
				return theTitleboxFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TitleboxFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public TitleboxFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TitleboxPackage.TITLEBOX:
				return createTitlebox();
			case TitleboxPackage.PLANNING_OFFICE:
				return createPlanningOffice();
			case TitleboxPackage.STRING_FIELD:
				return createStringField();
			default:
				throw new IllegalArgumentException("The class '"
						+ eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Titlebox createTitlebox() {
		TitleboxImpl titlebox = new TitleboxImpl();
		return titlebox;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PlanningOffice createPlanningOffice() {
		PlanningOfficeImpl planningOffice = new PlanningOfficeImpl();
		return planningOffice;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public StringField createStringField() {
		StringFieldImpl stringField = new StringFieldImpl();
		return stringField;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TitleboxPackage getTitleboxPackage() {
		return (TitleboxPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TitleboxPackage getPackage() {
		return TitleboxPackage.eINSTANCE;
	}

} // TitleboxFactoryImpl
