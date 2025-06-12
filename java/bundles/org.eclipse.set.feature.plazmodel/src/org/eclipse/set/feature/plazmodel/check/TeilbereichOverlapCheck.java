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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Streams;

/**
 * 
 */
@Component
public class TeilbereichOverlapCheck extends AbstractPlazContainerCheck
		implements PlazCheck {

	@Override
	public String checkType() {
		return "Überlappende Teilbereiche"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Teilbereichsgrenzen der LST-Objekte sind plausibel"; //$NON-NLS-1$
	}

	@Override
	public String getGeneralErrMsg() {
		return "Es gibt überlappende Teilbereiche in Objekt {GUID}"; //$NON-NLS-1$
	}

	@Override
	protected List<PlazError> run(
			final MultiContainer_AttributeGroup container) {
		return Streams.stream(container.getBereichObjekt()).flatMap(bo -> {
			final List<List<Bereich_Objekt_Teilbereich_AttributeGroup>> sameTopKanteTeilBereich = bo
					.getBereichObjektTeilbereich()
					.stream()
					.filter(botb -> EObjectExtensions
							.getNullableObject(botb,
									e -> e.getIDTOPKante().getValue())
							.isPresent())
					.collect(Collectors.groupingBy(
							botb -> botb.getIDTOPKante().getValue()))
					.values()
					.stream()
					.filter(l -> l.size() > 1)
					.toList();
			final Set<Bereich_Objekt_Teilbereich_AttributeGroup> overlapBotb = new HashSet<>();
			sameTopKanteTeilBereich.forEach(botbs -> {
				for (int i = 0; i < botbs.size(); i++) {
					for (int j = i + 1; j < botbs.size(); j++) {
						if (isTeilbereichOverlap(botbs.get(i), botbs.get(j))) {
							overlapBotb.add(botbs.get(i));
							overlapBotb.add(botbs.get(j));
						}
					}
				}
			});
			if (!overlapBotb.isEmpty()) {
				return overlapBotb.stream()
						.map(botb -> createPlaZError(bo, botb));
			}
			return null;
		}).filter(Objects::nonNull).toList();
	}

	private static boolean isTeilbereichOverlap(
			final Bereich_Objekt_Teilbereich_AttributeGroup botb1,
			final Bereich_Objekt_Teilbereich_AttributeGroup botb2) {
		if (botb1.getIDTOPKante().getValue() != botb2.getIDTOPKante()
				.getValue()) {
			return false;
		}
		final BigDecimal limitA1 = botb1.getBegrenzungA().getWert();
		final BigDecimal limitB1 = botb1.getBegrenzungB().getWert();
		final BigDecimal limitA2 = botb2.getBegrenzungA().getWert();
		final BigDecimal limitB2 = botb2.getBegrenzungB().getWert();
		return limitA1.compareTo(limitB2) < 0 && limitA1.compareTo(limitA2) > 0
				|| limitB1.compareTo(limitB2) < 0
						&& limitB1.compareTo(limitA2) > 0;
	}

	private PlazError createPlaZError(final Bereich_Objekt bo,
			final Bereich_Objekt_Teilbereich_AttributeGroup botb) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setSeverity(ValidationSeverity.ERROR);
		plazError.setMessage(transformErrorMsg(
				Map.of("GUID", bo.getIdentitaet().getWert()))); //$NON-NLS-1$
		plazError.setType(checkType());
		plazError.setObject(botb);
		return plazError;
	}

}
