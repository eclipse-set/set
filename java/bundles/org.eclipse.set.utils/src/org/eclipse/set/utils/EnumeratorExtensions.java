/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.util.regex.Pattern;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.Anhang;
import org.eclipse.set.model.planpro.Basisobjekte.Anhang_Allg_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.Anhang_Art_TypeClass;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;

/**
 * Extensions for {@link Enumerator}.
 * 
 * @author Schaefer
 */
public class EnumeratorExtensions {
	/**
	 * the generic value for enums with an item sonstige
	 */
	public static final String SONSTIGE_ENUM_WERT = "sonstige"; //$NON-NLS-1$
	private static final String ENUM_WERT_FEATURE_NAME = "wert"; //$NON-NLS-1$

	/**
	 * @param enumerator
	 *            this enumerator
	 * @param regex
	 *            the regular expression
	 * 
	 * @return whether the enumerator literal matches the given expression
	 */
	public static boolean matches(final Enumerator enumerator,
			final String regex) {
		return Pattern.matches(regex, enumerator.getLiteral());
	}

	private static boolean isPlanungBueroAnhangArt(
			final BasisAttribut_AttributeGroup attribute) {
		if (attribute instanceof final Anhang_Art_TypeClass anhangArt
				&& anhangArt
						.eContainer() instanceof final Anhang_Allg_AttributeGroup allgGroup
				&& allgGroup.eContainer() instanceof final Anhang anhang) {
			final EReference planungbueroLogoRef = PlanProPackage.eINSTANCE
					.getPlanung_G_Schriftfeld_AttributeGroup_PlanungsbueroLogo();
			return anhang.eContainingFeature().equals(planungbueroLogoRef);
		}

		return false;
	}

	/**
	 * Determines if a BasisAttribut is an enum with the value "sonstige"
	 * 
	 * @param attribute
	 *            the attribute to check for
	 * @return true if it is an enum with the value "sonstige" otherwise false
	 */
	public static boolean isSonstigeEnumWert(
			final BasisAttribut_AttributeGroup attribute) {
		try {
			final EStructuralFeature feature = attribute.eClass()
					.getEStructuralFeature(ENUM_WERT_FEATURE_NAME);
			final Object value = attribute.eGet(feature);
			return value instanceof final Enumerator enumValue
					&& enumValue.getLiteral().equals(SONSTIGE_ENUM_WERT)
					&& !isPlanungBueroAnhangArt(attribute);
		} catch (final Exception e) {
			return false;
		}
	}
}
