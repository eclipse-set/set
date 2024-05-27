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
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.BasisTypen.Zeiger_TypeClass;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.Verweise.VerweisePackage;

/**
 * Helper class to resolve ID reference GUIDs to their respective EMF objects
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
		resolveIDReferences(model, new ToolboxIDResolver(model));
	}

	/**
	 * Resolves ID references in a planpro layout model and updates the model
	 * accordingly to have pointers point to specific objects
	 * 
	 * @param model
	 *            the model
	 */
	public static void resolveIDReferences(final PlanPro_Layoutinfo model) {
		resolveIDReferences(model, new ToolboxIDResolver(model));
	}

	private static void resolveIDReferences(final EObject model,
			final ToolboxIDResolver resolver) {

		final EClass pointer = BasisTypenPackage.eINSTANCE
				.getZeiger_TypeClass();
		final EClass bearbeitungsvermerk = BasisTypenPackage.eINSTANCE
				.getID_Bearbeitungsvermerk_TypeClass();

		model.eAllContents().forEachRemaining(eo -> {
			// Resolve all regular pointers
			if (pointer.isSuperTypeOf(eo.eClass())) {
				resolver.resolveIDReference((Zeiger_TypeClass) eo);
				return;
			}

			// Also resolve all ID_Bearbeitungsvermerk (not a subclass of
			// Zeiger)
			if (bearbeitungsvermerk.equals(eo.eClass())) {
				resolver.resolveIDReference(
						(ID_Bearbeitungsvermerk_TypeClass) eo);
				return;
			}
		});

	}

	private static EReference getValueFeature(final EObject ref) {
		return (EReference) ref.eClass()
				.getEStructuralFeature(VerweisePackage.eINSTANCE
						.getID_Anforderer_Element_TypeClass_Value().getName());
	}

	private static EStructuralFeature getValidFeature(final EObject ref) {
		return ref.eClass()
				.getEStructuralFeature(VerweisePackage.eINSTANCE
						.getID_Anforderer_Element_TypeClass_InvalidReference()
						.getName());
	}

	private void resolveIDReference(final Zeiger_TypeClass ref) {
		resolveIDReference(ref, ref.getWert());
	}

	private void resolveIDReference(
			final ID_Bearbeitungsvermerk_TypeClass ref) {
		resolveIDReference(ref, ref.getWert());
	}

	private void resolveIDReference(final EObject ref, final String guid) {
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
		ref.eSet(getValidFeature(ref), Boolean.TRUE);
		ref.eSet(getValueFeature(ref), null);
	}

	private static void setIDReference(final EObject ref, final EObject value) {

		final EClass referenceType = getValueFeature(ref).getEReferenceType();

		// If we have a value, check whether it is applicable
		final Class<?> valueClass = value.getClass();
		final Class<?> referenceClass = referenceType.getInstanceClass();
		if (!referenceClass.isAssignableFrom(valueClass)) {
			// object found, but has wrong type -> mark invalid
			ref.eSet(getValidFeature(ref), Boolean.TRUE);
			ref.eSet(getValueFeature(ref), null);
			return;
		}

		ref.eSet(getValueFeature(ref), value);
	}

}
