/**
 * Copyright (c) 2024 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.set.core.fileservice;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.set.model.planpro.BasisTypen.BasisTypenPackage;
import org.eclipse.set.model.planpro.BasisTypen.Zeiger_TypeClass;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.Verweise.VerweisePackage;

/**
 * 
 */
public class ToolboxIDResolver {
	private final GuidCache guidCache = new GuidCache();

	private ToolboxIDResolver(final PlanPro_Schnittstelle model) {
		guidCache.prepare(model);
	}

	private ToolboxIDResolver(final PlanPro_Layoutinfo model) {
		guidCache.prepare(model);
	}

	/**
	 * Resolves ID references in a planpro model and updates the model
	 * accordingly to have pointers point to specific objects
	 * 
	 * @param model
	 *            the model
	 */
	public static void resolveIDReferences(final PlanPro_Schnittstelle model) {
		final ToolboxIDResolver resolver = new ToolboxIDResolver(model);

		final EClass pointer = BasisTypenPackage.eINSTANCE
				.getZeiger_TypeClass();

		model.eAllContents().forEachRemaining(eo -> {
			if (!pointer.isSuperTypeOf(eo.eClass())) {
				return;
			}

			resolver.resolveIDReference((Zeiger_TypeClass) eo);
		});
	}

	/**
	 * Resolves ID references in a planpro layout model and updates the model
	 * accordingly to have pointers point to specific objects
	 * 
	 * @param model
	 *            the model
	 */
	public static void resolveIDReferences(final PlanPro_Layoutinfo model) {
		final ToolboxIDResolver resolver = new ToolboxIDResolver(model);

		final EClass pointer = BasisTypenPackage.eINSTANCE
				.getZeiger_TypeClass();

		model.eAllContents().forEachRemaining(eo -> {
			if (!pointer.isSuperTypeOf(eo.eClass())) {
				return;
			}

			resolver.resolveIDReference((Zeiger_TypeClass) eo);
		});
	}

	private static EReference getValueFeature(final Zeiger_TypeClass ref) {
		return (EReference) ref.eClass()
				.getEStructuralFeature(VerweisePackage.eINSTANCE
						.getID_Anforderer_Element_TypeClass_Value().getName());
	}

	private static EStructuralFeature getValidFeature(
			final Zeiger_TypeClass ref) {
		return ref.eClass()
				.getEStructuralFeature(VerweisePackage.eINSTANCE
						.getID_Anforderer_Element_TypeClass_InvalidReference()
						.getName());
	}

	private void resolveIDReference(final Zeiger_TypeClass ref) {
		final String guid = ref.getWert();

		if (guid == null) {
			// xsi:nil GUID -> null value, but valid reference
			return;
		}

		final EObject value = guidCache.get(guid, ref);
		if (value != null) {
			setIDReference(ref, value);
			return;
		}

		// non-nil GUID but object not found -> mark invalid
		ref.eSet(getValidFeature(ref), Boolean.FALSE);
	}

	private static void setIDReference(final Zeiger_TypeClass ref,
			final EObject value) {

		final EClass referenceType = getValueFeature(ref).getEReferenceType();

		// If we have a value, check whether it is applicable
		final Class<?> valueClass = value.getClass();
		final Class<?> referenceClass = referenceType.getInstanceClass();
		if (!referenceClass.isAssignableFrom(valueClass)) {
			// object found, but has wrong type -> mark invalid
			ref.eSet(getValidFeature(ref), Boolean.FALSE);
			return;
		}

		ref.eSet(getValueFeature(ref), value);
	}

}
