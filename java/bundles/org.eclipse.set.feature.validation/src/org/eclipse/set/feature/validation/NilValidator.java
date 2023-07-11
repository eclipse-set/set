/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.set.toolboxmodel.Layoutinformationen.LayoutinformationenPackage;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;

/**
 * Extra validator to report EMF errors if an unset value is present in the
 * model
 * 
 * @author Stuecker
 *
 */
public class NilValidator extends EObjectValidator {
	/**
	 * Registers the NilValidator with the EMF EValidator Registry
	 * 
	 * @param messages
	 *            a Messages instance
	 */
	public static void setup(final Messages messages) {
		final EValidator planproValidator = (EValidator) EValidator.Registry.INSTANCE
				.get(PlanProPackage.eINSTANCE);
		// Register only if not the current validator
		if (!(planproValidator instanceof NilValidator)) {
			EValidator.Registry.INSTANCE.put(PlanProPackage.eINSTANCE,
					new NilValidator(planproValidator, messages));
			EValidator.Registry.INSTANCE.put(
					LayoutinformationenPackage.eINSTANCE,
					new NilValidator(planproValidator, messages));
		}

		final EValidator layoutValidator = (EValidator) EValidator.Registry.INSTANCE
				.get(LayoutinformationenPackage.eINSTANCE);
		if (!(layoutValidator instanceof NilValidator)) {
			EValidator.Registry.INSTANCE.put(
					LayoutinformationenPackage.eINSTANCE,
					new NilValidator(planproValidator, messages));
		}

	}

	private final EValidator baseValidator;
	private final Messages messages;

	private NilValidator(final EValidator baseValidator,
			final Messages messages) {
		this.baseValidator = baseValidator;
		this.messages = messages;
	}

	@Override
	public boolean validate(final EObject eObject,
			final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		return baseValidator.validate(eObject, diagnostics, context);
	}

	@Override
	public boolean validate(final EClass eClass, final EObject eObject,
			final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		for (final EAttribute attr : eClass.getEAttributes()) {
			// Record a diagnostic if the value is unsettable and null
			if (attr.isUnsettable() && eObject.eGet(attr) == null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, null, 1,
						messages.NilValidator_NilWarningMessage,
						new Object[] { eObject, attr }));
				return false;
			}
		}
		return baseValidator.validate(eClass, eObject, diagnostics, context);
	}

	@Override
	public boolean validate(final EDataType eDataType, final Object value,
			final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		return baseValidator.validate(eDataType, value, diagnostics, context);
	}

}
