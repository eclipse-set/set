/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plazmodel.check;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup;
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Anhang;
import org.eclipse.set.model.planpro.Basisobjekte.Anhang_Allg_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.Anhang_Art_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * Validates that any enum value "Sonstige" has an according bearbeitungsvermerk
 * anywhere
 * 
 * @author mariusheine
 */
@Component
public class SonstigeRequiresBearbeitungsvermerk implements PlazCheck {
	private static final String SONSTIGE_ENUM_WERT = "sonstige"; //$NON-NLS-1$
	private static final String ENUM_WERT_FEATURE_NAME = "wert"; //$NON-NLS-1$
	private static final String BEARBEITUNGSVERMERK_FEATURE_NAME = "iDBearbeitungsvermerk"; //$NON-NLS-1$

	@Override
	public List<PlazError> run(final IModelSession modelSession) {
		final TreeIterator<EObject> contents = modelSession
				.getPlanProSchnittstelle()
				.eAllContents();

		final Iterator<BasisAttribut_AttributeGroup> attributes = Iterators
				.filter(contents, BasisAttribut_AttributeGroup.class);

		final Iterator<BasisAttribut_AttributeGroup> attributesWithSonstige = Iterators
				.filter(attributes, this::isSonstigeEnumWert);

		final Iterator<BasisAttribut_AttributeGroup> attributesWithMissingBearbeitungsvermerk = Iterators
				.filter(attributesWithSonstige, this::hasNoBearbeitungsVermerk);

		final Iterator<PlazError> errors = Iterators.transform(
				attributesWithMissingBearbeitungsvermerk, this::createError);

		return Lists.newArrayList(errors);
	}

	private boolean isSonstigeEnumWert(
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

	private boolean hasNoBearbeitungsVermerk(final EObject obj) {
		final EStructuralFeature feature = obj.eClass()
				.getEStructuralFeature(BEARBEITUNGSVERMERK_FEATURE_NAME);
		final boolean hasBearbeitungsVermerk = feature != null
				&& obj.eGet(feature) instanceof final EList<?> list
				&& !list.isEmpty()
				&& list.get(0) instanceof ID_Bearbeitungsvermerk_TypeClass;
		if (!hasBearbeitungsVermerk && !(obj instanceof Ur_Objekt)) {
			// if we have not found a bearbeitungs vermerk and have not reached
			// the ur objekt in the container chain then we keep searching in
			// the container of the current object
			return this.hasNoBearbeitungsVermerk(obj.eContainer());
		}
		return !hasBearbeitungsVermerk;
	}

	private PlazError createError(
			final BasisAttribut_AttributeGroup attribute) {
		final PlazError err = PlazFactory.eINSTANCE.createPlazError();
		err.setMessage(this.getGeneralErrMsg());
		err.setType(this.checkType());
		err.setObject(attribute);
		err.setSeverity(ValidationSeverity.ERROR);
		return err;

	}

	@Override
	public String checkType() {
		return "Bearbeitungsvermerk"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return String.format(
				"Alles mit Referenz '%s' hat einen Bearbeitungsvermerk.", //$NON-NLS-1$
				SONSTIGE_ENUM_WERT);
	}

	@Override
	public String getGeneralErrMsg() {
		return String.format("Fehlender Bearbeitungsvermerk bei Wert '%s'.", //$NON-NLS-1$
				SONSTIGE_ENUM_WERT);
	}
}
