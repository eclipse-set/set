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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.model.planpro.Basisobjekte.Identitaet_TypeClass;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Streams;

/**
 * Validation, that all GUID are upper case
 * 
 * @author Truong
 */
@Component
@SuppressWarnings("nls")
public class GuidUpperCaseValidator implements PlazCheck {

	@Override
	public List<PlazError> run(final IModelSession modelSession) {
		final List<Identitaet_TypeClass> containsUndercaseGUID = Streams
				.stream(modelSession.getPlanProSchnittstelle().eAllContents())
				.filter(Identitaet_TypeClass.class::isInstance)
				.map(Identitaet_TypeClass.class::cast)
				.filter(guid -> guid.getWert()
						.chars()
						.anyMatch(Character::isLowerCase))
				.toList();
		if (containsUndercaseGUID.isEmpty()) {
			return Collections.emptyList();
		}
		return containsUndercaseGUID.stream().map(guid -> {
			final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
			plazError.setObject(guid);
			plazError.setSeverity(ValidationSeverity.ERROR);
			plazError.setType(checkType());
			plazError.setMessage(
					transformErrorMsg(Map.of("GUID", guid.getWert())));
			return plazError;
		}).toList();
	}

	@Override
	public String checkType() {
		return "GUID-Großbuchstaben";
	}

	@Override
	public String getDescription() {
		return "Alle GUIDs sind in Großbuchstaben geschrieben";
	}

	@Override
	public String getGeneralErrMsg() {
		return "GUID {GUID} enthält Kleinbuchstaben.";
	}

}
