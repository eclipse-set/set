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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.Streams;

import jakarta.inject.Inject;

/**
 * Load reference plan handler
 * 
 * @author truong
 */
public class ReadReferencePlanHandler extends OpenPlanProHandler {

	@Inject
	private ToolboxFileService fileService;

	/**
	 * @return can execute only when main project was loaded and exists
	 *         reference plan id
	 */
	@CanExecute
	public boolean canExecute() {
		return !getReferencesPlanId(oldModelSession).isEmpty()
				&& oldModelSession != null;
	}

	@Override
	protected ToolboxFileRole getRole() {
		return ToolboxFileRole.REFERENCE_BASIS_PLAN;
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
		final Set<String> referencesPlanId = getReferencesPlanId(
				oldModelSession);
		if (referencePlanId == null
				|| !referencesPlanId.contains(referencePlanId)) {
			getDialogService().error(sell,
					messages.OpenPlanProHandler_LoadingPlanProModelFileErrorMessage);
			return null;
		}
		return modelSession;
	}

	@Override
	protected void success(final IModelSession modelSession,
			final MApplication application) {
		eventBroker.send(Events.REFERENCE_PLAN_LOADED, modelSession);
	}

	private static Set<String> getReferencesPlanId(
			final IModelSession session) {
		final PlanPro_Schnittstelle planProSchnittstelle = session
				.getPlanProSchnittstelle();
		return Streams
				.stream(PlanProSchnittstelleExtensions
						.getLSTPlanungGruppe(planProSchnittstelle)
						.orElse(Collections.emptyList()))
				.map(Planung_Gruppe::getLSTPlanungEinzel)
				.filter(Objects::nonNull)
				.map(PlanungEinzelExtensions::getReferencePlanBasisGUID)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}
}