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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.text.StringSubstitutor;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.model.planpro.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.model.planpro.PlanPro.Untergewerk_Art_TypeClass;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Streams;

/**
 * Validate {@link Ausgabe_Fachdaten#getUntergewerkArt()} 
 * 	- Compare with {@link Untergewerk_Art_TypeClass} from belong {@link Planung_Gruppe}
 *  - The {@link ENUMUntergewerkArt} should be unique
 * 
 * @author truong
 */
@Component
@SuppressWarnings("nls")
public class SubworkValid implements PlazCheck {

	@Override
	public List<PlazError> run(final IModelSession modelSession) {
		final List<PlazError> errors = new ArrayList<>();
		final Map<Ausgabe_Fachdaten, ENUMUntergewerkArt> subworkFromPlanGroup = getSubworkFromPlanGroup(
				modelSession);
		final List<Ausgabe_Fachdaten> subworksList = Streams
				.stream(modelSession.getPlanProSchnittstelle().eAllContents())
				.filter(Ausgabe_Fachdaten.class::isInstance)
				.map(Ausgabe_Fachdaten.class::cast)
				.toList();
		final Set<ENUMUntergewerkArt> knowType = new HashSet<>();
		if (!subworkFromPlanGroup.isEmpty()) {
			subworkFromPlanGroup.forEach((subwork, type) -> errors.addAll(
					checkSubwork(subwork, Optional.of(type), knowType)));
			subworkFromPlanGroup.keySet().forEach(subworksList::remove);
		}

		subworksList.forEach(subwork -> errors
				.addAll(checkSubwork(subwork, Optional.empty(), knowType)));
		return errors;
	}

	private List<PlazError> checkSubwork(final Ausgabe_Fachdaten subwork,
			final Optional<ENUMUntergewerkArt> planGroupSubWorkType,
			final Set<ENUMUntergewerkArt> knowedType) {
		final List<PlazError> result = new ArrayList<>();
		final Optional<ENUMUntergewerkArt> subworkType = getNullableObject(
				subwork, s -> s.getUntergewerkArt().getWert());
		final Map<String, String> subworkGuidMap = Map.of("GUID",
				subwork.getIdentitaet().getWert());
		if (subworkType.isEmpty()) {
			return List.of(createPlazError(subwork,
					"Die Ausgabe_Fachdaten: {GUID} hat keine Untergerwerk",
					subworkGuidMap));
		}

		if (planGroupSubWorkType.isPresent()
				&& subworkType.get() != planGroupSubWorkType.get()) {
			result.add(createPlazError(subwork,
					"Die Ausgabe_Fachedaten: {GUID} und die gehörigen Planung_Gruppe haben unterschieden Untergewerk",
					subworkGuidMap));
		}

		if (knowedType.contains(subworkType.get())) {
			result.add(createPlazError(subwork, getGeneralErrMsg(),
					subworkGuidMap));
		}
		knowedType.add(subworkType.get());
		return result;
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
		return "Die Ausgabefach_Daten haben plausible Untergewerk";
	}

	@Override
	public String getGeneralErrMsg() {
		return "Die Untergewerk der Ausgabe_Fachdaten: {GUID} ist nicht eindeutig";
	}

}
