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
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.model.planpro.PlanPro.Planung_Einzel;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.Streams;

/**
 * Load equivalent plan handler
 * 
 * @author truong
 */
public class ReadEquivalentPlanHandler extends ReadReferencePlanHandler {

	/**
	 * @return can execute only when main project was loaded and exists
	 *         reference plan id
	 */
	@Override
	@CanExecute
	public boolean canExecute() {
		return oldModelSession != null && existsEquivalentPlan(oldModelSession);
	}

	@Override
	protected ToolboxFileRole getRole() {
		return ToolboxFileRole.EQUIVALENT_PLAN;
	}

	@Override
	protected void closeOldSession(final Shell shell) {
		final IModelSession currentSession = sessionService
				.getLoadedSession(getRole());
		if (currentSession != null) {
			currentSession.close();
		}
	}

	private static boolean existsEquivalentPlan(final IModelSession session) {
		final Iterable<Planung_Gruppe> groups = PlanProSchnittstelleExtensions
				.getLSTPlanungGruppe(session.getPlanProSchnittstelle())
				.orElse(Collections.emptyList());
		return !Streams.stream(groups)
				.map(Planung_Gruppe::getLSTPlanungEinzel)
				.map(Planung_Einzel::getPlanungEHandlung)
				.flatMap(planungEHandlung -> planungEHandlung
						.getPlanungEGleichstellung()
						.stream())
				.collect(Collectors.toSet())
				.isEmpty();
	}

	@Override
	protected void success(final IModelSession modelSession,
			final MApplication application) {
		eventBroker.send(Events.ADDITIONAL_PLAN_LOADED, modelSession);
	}

	@Override
	protected IModelSession validation(final IModelSession modelSession,
			final Shell sell, final Path path) {
		if (modelSession == null) {
			Services.getPlanningAccessService()
					.getAdditionalPlansInformation()
					.remove(ToolboxFileRole.EQUIVALENT_PLAN);
		}
		return modelSession;
	}
}