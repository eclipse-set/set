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

import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.text.StringSubstitutor;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.model.planpro.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.model.planpro.PlanPro.Untergewerk_Art_TypeClass;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.common.collect.Streams;

/**
 * Validate {@link Ausgabe_Fachdaten#getUntergewerkArt()} - Compare with
 * {@link Untergewerk_Art_TypeClass} from belong {@link Planung_Gruppe} - The
 * {@link ENUMUntergewerkArt} should be unique
 * 
 * @author truong
 */
@Component
@SuppressWarnings("nls")
public class SubworkValid implements PlazCheck {

	@Reference
	EnumTranslationService enumTranslationService;

	@Override
	public List<PlazError> run(final IModelSession modelSession) {
		final List<PlazError> errors = new ArrayList<>();
		final Map<Ausgabe_Fachdaten, ENUMUntergewerkArt> subworkFromPlanGroup = getSubworkFromPlanGroup(
				modelSession);
		// Validate the subwork type of Planung_Gruppe and of the reference
		// Ausgabe_Fachdaten
		if (!subworkFromPlanGroup.isEmpty()) {
			subworkFromPlanGroup.forEach((subwork, type) -> {
				final PlazError error = validatePlanGroupSubwork(subwork,
						Optional.of(type));
				if (error != null) {
					errors.add(error);
				}
			});
		}

		errors.addAll(validateSubworkTypeUnique(
				modelSession.getPlanProSchnittstelle()));
		return errors;
	}

	/**
	 * The Subwork type of {@link Planung_Gruppe} and of the relevant
	 * {@link Ausgabe_Fachdaten} should be equals
	 * 
	 * @param subwork
	 *            the {@link Ausgabe_Fachdaten}
	 * @param planGroupSubWorkType
	 *            the subwork type of {@link Planung_Gruppe}
	 * @return errors
	 */
	private PlazError validatePlanGroupSubwork(final Ausgabe_Fachdaten subwork,
			final Optional<ENUMUntergewerkArt> planGroupSubWorkType) {
		final Optional<ENUMUntergewerkArt> subworkType = getNullableObject(
				subwork, s -> s.getUntergewerkArt().getWert());
		final Map<String, String> subworkGuidMap = Map.of("GUID",
				subwork.getIdentitaet().getWert());
		if (subworkType.isPresent() && planGroupSubWorkType.isPresent()
				&& subworkType.get() != planGroupSubWorkType.get()) {
			return createPlazError(subwork,
					"Die Ausgabe_Fachdaten {GUID} und die dazu gehörige Planung_Gruppe haben verschiedene Untergewerk_Art",
					subworkGuidMap);
		}
		return null;
	}

	private List<PlazError> validateSubworkTypeUnique(
			final PlanPro_Schnittstelle schnittstelle) {
		final List<PlazError> errors = new ArrayList<>();
		final Map<Optional<Object>, List<Ausgabe_Fachdaten>> groupBySubworkType = Streams
				.stream(schnittstelle.eAllContents())
				.filter(Ausgabe_Fachdaten.class::isInstance)
				.map(Ausgabe_Fachdaten.class::cast)
				.collect(Collectors
						.groupingBy(subwork -> getNullableObject(subwork,
								s -> s.getUntergewerkArt().getWert())));
		groupBySubworkType.forEach((type, subworks) -> {
			final Function<Ausgabe_Fachdaten, Map<String, String>> getSubworkGuidMap = subwork -> Map
					.of("GUID",
							getNullableObject(subwork,
									s -> s.getIdentitaet().getWert())
											.orElse(""));
			if (type.isEmpty()) {
				subworks.forEach(ele -> errors.add(createPlazError(ele,
						"Die Ausgabe_Fachdaten {GUID} hat keine Untergewerk_Art",
						getSubworkGuidMap.apply(ele))));
				return;
			}
			if (subworks.size() > 1 && type
					.get() instanceof final ENUMUntergewerkArt untergewerkArt) {
				final String subworkTypeStr = enumTranslationService
						.translate(untergewerkArt)
						.getAlternative();
				subworks.forEach(ele -> errors.add(createPlazError(ele,
						"Die Ausgabe_Fachdaten zu Untergewerk_Art {Type} ist nicht eindeutig.",
						Map.of("Type", subworkTypeStr))));
			}
		});
		return errors;
	}

	/**
	 * Find the {@link Ausgabe_Fachdaten} and the {@link ENUMUntergewerkArt},
	 * which belong to this Ausgabe_Fachdaten from {@link Planung_Gruppe}
	 * 
	 * @param modelSession
	 *            the {@link IModelSession}
	 * @return the {@link Ausgabe_Fachdaten} and the {@link ENUMUntergewerkArt}
	 */
	private static Map<Ausgabe_Fachdaten, ENUMUntergewerkArt> getSubworkFromPlanGroup(
			final IModelSession modelSession) {
		final List<Planung_Gruppe> lstPlanungProjekt = getNullableObject(
				modelSession,
				session -> session.getPlanProSchnittstelle()
						.getLSTPlanung()
						.getObjektmanagement()
						.getLSTPlanungProjekt()
						.stream()
						.flatMap(lstPlanung -> lstPlanung.getLSTPlanungGruppe()
								.stream())
						.toList()).orElse(Collections.emptyList());
		return lstPlanungProjekt.stream()
				.map(group -> getNullableObject(group, g -> new Pair<>(
						g.getLSTPlanungEinzel()
								.getIDAusgabeFachdaten()
								.getValue(),
						g.getPlanungGAllg().getUntergewerkArt().getWert())))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
	}

	private PlazError createPlazError(final Ausgabe_Fachdaten subwork,
			final String messages, final Map<String, String> data) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setObject(subwork);
		plazError.setType(checkType());
		plazError.setMessage(
				StringSubstitutor.replace(messages, data, "{", "}"));
		return plazError;
	}

	@Override
	public String checkType() {
		return "Untergewerk Art";
	}

	@Override
	public String getDescription() {
		return "Jedes Untergewerk hat maximal eine Ausgabe_Fachdaten";
	}

	@Override
	public String getGeneralErrMsg() {
		return "Die Ausgabe_Fachdaten je Untergewerk sind nicht eindeutig";
	}

}
