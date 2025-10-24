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
package org.eclipse.set.feature.plazmodel.export;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.feature.plazmodel.Messages;
import org.eclipse.set.feature.plazmodel.check.GeoCoordinateValid;
import org.eclipse.set.feature.plazmodel.check.PlazCheck;
import org.eclipse.set.utils.viewgroups.SetViewGroups;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import jakarta.inject.Inject;

/**
 * 
 */
@Component
public class ExportTopologicalCoordinateDescriptionService
		implements PartDescriptionService {
	@Reference(target = "(type=geoCoordinate)")
	PlazCheck plazCheck;

	private static class InjectionHelper {

		@Inject
		@Translation
		Messages messages;

		@SuppressWarnings("unused")
		public InjectionHelper() {
		}
	}

	@Override
	public PartDescription getDescription(final IEclipseContext context) {
		if (plazCheck instanceof final GeoCoordinateValid geoCoordinateValid) {
			context.set(GeoCoordinateValid.class, geoCoordinateValid);
		}
		final InjectionHelper injectionHelper = ContextInjectionFactory
				.make(InjectionHelper.class, context);
		return new PartDescription(
				// ID
				this.getClass().getName(),
				// contributionURI
				"bundleclass://org.eclipse.set.feature.plazmodel/org.eclipse.set.feature.plazmodel.export.ExportTopologicalCoordinatePart", //$NON-NLS-1$
				// toolboxViewName
				injectionHelper.messages.PlazExport_Topological_Coordinate_Part,
				// toolboxViewToolTip
				"", //$NON-NLS-1$
				// toolboxViewType
				getToolboxViewGroup(),
				// defaultForNature
				null,
				// toolboxViewNeedsLoadedModel
				true,
				// toolboxViewNeedsXsdValidation
				false,
				// toolboxViewNeedsEmfValidation
				false,
				// toolboxViewProcessState
				true,
				// toolboxViewProcessPlanning
				true,
				// toolboxViewProcessIntegration
				true,
				// toolboxViewProcessInvalid
				false,
				// toolboxViewNeedsCleanSession
				false,
				// toolboxViewExclusiveEditor
				false);
	}

	@Override
	public ToolboxViewGroup getToolboxViewGroup() {
		return SetViewGroups.getDevelopment();
	}

}
