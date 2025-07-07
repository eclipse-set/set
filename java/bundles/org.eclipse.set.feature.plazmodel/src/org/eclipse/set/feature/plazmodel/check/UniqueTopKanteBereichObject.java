/**
 * Copyright (c) 2025 DB InfraGO AG and others
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Streams;

/**
 * The {@link TOP_Kante} of the
 * {@link Bereich_Objekt_Teilbereich_AttributeGroup} in a
 * {@link Bereich_Objekt}} should be unique
 * 
 * @author truong
 */
@Component
public class UniqueTopKanteBereichObject extends AbstractPlazContainerCheck
		implements PlazCheck {

	@Override
	public String checkType() {
		return "Mehrfache Teilbereiche"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Alle Bereichsobjekte haben eindeutige TOP-Kanten definiert."; //$NON-NLS-1$
	}

	@Override
	public String getGeneralErrMsg() {
		return "Für das Bereichsobjekt {Objektart} {OBJEKT_GUID} sind mehrere überlappende/​mehrere ​Teilbereiche ​auf ​der TOP-Kante {TOP_KANTE_GUID} definiert."; //$NON-NLS-1$
	}

	@Override
	protected List<PlazError> run(
			final MultiContainer_AttributeGroup container) {
		final List<PlazError> result = new ArrayList<>();
		Streams.stream(container.getBereichObjekt()).forEach(bo -> {
			final List<TOP_Kante> topKanten = bo.getBereichObjektTeilbereich()
					.stream()
					.map(botb -> botb.getIDTOPKante().getValue())
					.toList();

			final Set<TOP_Kante> duplicateTopKanten = topKanten.parallelStream()
					.filter(tk -> Collections.frequency(topKanten, tk) > 1)
					.collect(Collectors.toSet());
			if (duplicateTopKanten.isEmpty()) {
				return;
			}
			duplicateTopKanten.forEach(topKante -> bo
					.getBereichObjektTeilbereich()
					.stream()
					.filter(botb -> botb.getIDTOPKante().getValue() == topKante)
					.forEach(botb -> result
							.add(createError(bo, botb, topKante))));

		});
		return result;
	}

	private PlazError createError(final Bereich_Objekt bo,
			final Bereich_Objekt_Teilbereich_AttributeGroup botb,
			final TOP_Kante topKante) {
		final PlazError error = PlazFactory.eINSTANCE.createPlazError();
		error.setSeverity(ValidationSeverity.WARNING);
		error.setObject(botb);
		error.setType(checkType());
		error.setMessage(
				transformErrorMsg(Map.of("Objektart", bo.eClass().getName(), //$NON-NLS-1$
						"OBJEKT_GUID", bo.getIdentitaet().getWert(), //$NON-NLS-1$
						"GUID", topKante.getIdentitaet().getWert()))); //$NON-NLS-1$
		return error;
	}

}
