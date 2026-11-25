/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.plaz.export;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.basis.export.CheckBoxTreeElement;
import org.eclipse.set.core.services.export.AdditionalExportService;
import org.eclipse.set.feature.plaz.Messages;
import org.eclipse.set.feature.plaz.service.PlazService;
import org.eclipse.set.feature.plaz.utils.PptPaths;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;

import jakarta.inject.Inject;

/**
 * Add PlaZ export.
 * 
 * @author Schaefer
 */
public class PlazAdditionalExportService
		implements AdditionalExportService<CheckBoxTreeElement> {

	private static final String PLAZ_ID = "plaz"; //$NON-NLS-1$

	@Inject
	@Translation
	private Messages messages;

	@Inject
	private PlazService plaZService;

	@Override
	public void createAdditionalCheckboxModelElements(
			final List<CheckBoxTreeElement> elements) {
		elements.add(new CheckBoxTreeElement(PLAZ_ID,
				messages.PlazAdditionalExportService_PlazLabel));
	}

	@Override
	public void createAdditionalExport(final IModelSession modelSession,
			final IProgressMonitor monitor, final Path exportDirectory,
			final ExportType exportType,
			final OverwriteHandling overwriteHandling) {
		final Path pdfLocation = getExportPaths(modelSession, exportDirectory,
				exportType).getFirst();
		plaZService.createCompletePdfReport(monitor, modelSession.getTempDir(),
				modelSession.getToolboxFile(), pdfLocation,
				modelSession.getValidationResult(PlanPro_Schnittstelle.class)
						.getXsdOutcome() == Outcome.VALID,
				plaZService.isSvrlAvailable(
						modelSession.getPlanProSchnittstelle()),
				overwriteHandling);
	}

	@Override
	public List<Path> getExportPaths(final IModelSession modelSession,
			final Path exportDirectory, final ExportType exportType) {
		return List.of(PptPaths.getPdfFullPlazReport(modelSession,
				exportDirectory, exportType));
	}

	@Override
	public boolean isAdditionalExportId(final String id) {
		return PLAZ_ID.equals(id);
	}
}
