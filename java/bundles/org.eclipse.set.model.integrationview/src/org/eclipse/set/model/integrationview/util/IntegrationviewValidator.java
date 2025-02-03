/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview.util;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.set.model.integrationview.*;

import org.eclipse.set.utils.StringExtensions;
import org.eclipse.set.utils.ToolboxResourceLocator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage
 * @generated
 */
public class IntegrationviewValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final IntegrationviewValidator INSTANCE = new IntegrationviewValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.set.model.integrationview";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegrationviewValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return IntegrationviewPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case IntegrationviewPackage.INTEGRATION_VIEW:
				return validateIntegrationView((IntegrationView)value, diagnostics, context);
			case IntegrationviewPackage.OBJECT_QUANTITY:
				return validateObjectQuantity((ObjectQuantity)value, diagnostics, context);
			case IntegrationviewPackage.CONFLICT:
				return validateConflict((Conflict)value, diagnostics, context);
			case IntegrationviewPackage.DETAILS:
				return validateDetails((Details)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntegrationView(IntegrationView integrationView, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(integrationView, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateObjectQuantity(ObjectQuantity objectQuantity, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(objectQuantity, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConflict(Conflict conflict, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(conflict, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDetails(Details details, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(details, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(details, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(details, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(details, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(details, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(details, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(details, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(details, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(details, diagnostics, context);
		if (result || diagnostics != null) result &= validateDetails_equalPlanningValues(details, diagnostics, context);
		return result;
	}

	/**
	 * Validates the equalPlanningValues constraint of '<em>Details</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean validateDetails_equalPlanningValues(Details details, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (!StringExtensions.nullSafeEquals(
				details.getValuePrimaryPlanning(),
				details.getValueSecondaryPlanning()))
		{
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 1,
						 "message_validateDetails_equalPlanningValues",
						 new Object[] { "equalPlanningValues", getObjectLabel(details, context) },
						 new Object[] { details, IntegrationviewPackage.eINSTANCE.getDetails_ValuePrimaryPlanning() },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return new ToolboxResourceLocator(super.getResourceLocator());
	}

} //IntegrationviewValidator
