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
package org.eclipse.set.application.handler;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.Streams;

/**
 * Load reference plan handler
 * 
 * @author truong
 */
public class ReadReferencePlanHandler extends OpenPlanProHandler {

	/**
	 * @return can execute only when main project was loaded and exists
	 *         reference plan id
	 */
	@CanExecute
	public boolean canExecute() {
		return oldModelSession != null
				&& !getReferencesPlanId(oldModelSession).isEmpty();
	}

	@Override
	protected ToolboxFileRole getRole() {
		return ToolboxFileRole.REFERENCE_BASIS_PLAN;
	}

	@Override
	protected void closeOldSession(final Shell shell) {
		final IModelSession currentSession = sessionService
				.getLoadedSession(getRole());
		if (currentSession != null) {
			currentSession.close();
		}
	}

	@Override
	protected void setLastOpenPath(final Path path) {
		// do nothing
	}

	@Override
	protected IModelSession validation(final IModelSession modelSession,
			final Shell sell, final Path path) {
		final String referencePlanId = EObjectExtensions
				.getNullableObject(modelSession,
						session -> session.getPlanProSchnittstelle()
								.getIdentitaet()
								.getWert())
				.orElse(null);
		final Map<ENUMUntergewerkArt, String> expectedReferencePlan = getReferencesPlanId(
				oldModelSession);
		final Set<String> referencesId = expectedReferencePlan.values()
				.stream()
				.collect(Collectors.toSet());
		final Iterable<Planung_Gruppe> referencePlanPlanungGruppe = PlanProSchnittstelleExtensions
				.getLSTPlanungGruppe(modelSession.getPlanProSchnittstelle())
				.orElse(Collections.emptyList());
		final Set<ENUMUntergewerkArt> referencePlanSubwords = Streams
				.stream(referencePlanPlanungGruppe)
				.map(group -> EObjectExtensions.getNullableObject(group,
						g -> g.getPlanungGAllg().getUntergewerkArt().getWert())
						.orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (referencePlanId == null || !referencesId.contains(referencePlanId)
				|| referencePlanSubwords.stream()
						.noneMatch(refEle -> expectedReferencePlan.keySet()
								.stream()
								.anyMatch(ele -> ele != refEle))) {
			getDialogService().error(sell,
					messages.OpenPlanProHandler_LoadingWrongReferencePlanMessage);
			return null;
		}
		return modelSession;
	}

	@Override
	protected void success(final IModelSession modelSession,
			final MApplication application) {
		eventBroker.send(Events.ADDITIONAL_PLAN_LOADED, modelSession);
	}

	private static Map<ENUMUntergewerkArt, String> getReferencesPlanId(
			final IModelSession session) {
		final PlanPro_Schnittstelle planProSchnittstelle = session
				.getPlanProSchnittstelle();
		return Streams
				.stream(PlanProSchnittstelleExtensions
						.getLSTPlanungGruppe(planProSchnittstelle)
						.orElse(Collections.emptyList()))
				.filter(group -> group.getLSTPlanungEinzel() != null
						&& !PlanungEinzelExtensions
								.getReferencePlanBasisGUID(
										group.getLSTPlanungEinzel())
								.isEmpty()
						&& EObjectExtensions
								.getNullableObject(group,
										g -> g.getPlanungGAllg()
												.getUntergewerkArt()
												.getWert())
								.isPresent())
				.collect(Collectors.toMap(
						group -> group.getPlanungGAllg()
								.getUntergewerkArt()
								.getWert(),
						group -> PlanungEinzelExtensions
								.getReferencePlanBasisGUID(
										group.getLSTPlanungEinzel())));
	}
}