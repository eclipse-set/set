/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plazmodel.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Streams;

/**
 * Validates that any bearbeitungsvermerk is used anywhere
 * 
 * @author mariusheine
 */
@Component
public class UnusedBearbeitungsvermerke implements PlazCheck {

	@Override
	public List<PlazError> run(final IModelSession modelSession) {
		final List<ID_Bearbeitungsvermerk_TypeClass> idReferences = Streams
				.stream(modelSession.getPlanProSchnittstelle().eAllContents())
				.parallel()
				.filter(ID_Bearbeitungsvermerk_TypeClass.class::isInstance)
				.map(ID_Bearbeitungsvermerk_TypeClass.class::cast)
				.toList();

		final List<Bearbeitungsvermerk> bearbeitungsVermerke = Streams
				.stream(modelSession.getPlanProSchnittstelle().eAllContents())
				.parallel()
				.filter(Bearbeitungsvermerk.class::isInstance)
				.map(Bearbeitungsvermerk.class::cast)
				.toList();

		final List<PlazError> errors = new ArrayList<>();

		for (final Bearbeitungsvermerk bv : bearbeitungsVermerke) {
			final List<EObject> referencedByList = idReferences.stream()
					.parallel()
					.filter(ref -> ref.getValue().equals(bv))
					.map(EObject::eContainer)
					.toList();
			if (referencedByList.isEmpty()) {
				errors.add(createError(bv));
			}
		}
		return errors;
	}

	private PlazError createError(
			final Bearbeitungsvermerk bearbeitungsvermerk) {
		final PlazError err = PlazFactory.eINSTANCE.createPlazError();
		err.setMessage(transformErrorMsg(
				Map.of("GUID", bearbeitungsvermerk.getIdentitaet().getWert() //$NON-NLS-1$
				)));
		err.setType(this.checkType());
		err.setObject(bearbeitungsvermerk);
		err.setSeverity(ValidationSeverity.ERROR);
		return err;

	}

	@Override
	public String checkType() {
		return "Bearbeitungsvermerk"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Alle Bearbeitungsvermerke werden genutzt."; //$NON-NLS-1$
	}

	@Override
	public String getGeneralErrMsg() {
		return "Ungenutzter Bearbeitungsvermerk {GUID}."; //$NON-NLS-1$
	}
}
