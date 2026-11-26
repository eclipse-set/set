/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plaz.parts;

import java.nio.file.Path;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.feature.plaz.utils.PptPaths;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;

/**
 * Execute PlaZ and display the result.
 * 
 * @author Schaefer
 */
public class PlazExecutionPart extends AbstractPlaZPdfPart {
	@Override
	Path getPdfPath() {
		return PptPaths.getPdfFullPlazReport(getModelSession(),
				getModelSession().getTempDir(), ExportType.PLANNING_RECORDS);
	}

	@Override
	PlaZConfiguration getPlaZConfiguration() {
		final IModelSession modelSession = getModelSession();
		return new PlaZConfiguration() {
			@Override
			public void create(final IProgressMonitor monitor) {
				plaZService.createCompletePdfReport(monitor,
						modelSession.getTempDir(),
						modelSession.getToolboxFile(), getPdfPath(),
						modelSession
								.getValidationResult(
										PlanPro_Schnittstelle.class)
								.getXsdOutcome() == Outcome.VALID,
						plaZService.isSvrlAvailable(
								modelSession.getPlanProSchnittstelle()),
						OverwriteHandling.forCheckbox(true));
			}

			@Override
			public boolean isAvailable() {
				return plaZService.isPdfReportAvailable(
						modelSession.getPlanProSchnittstelle());
			}

			@Override
			public void setAvailable(final boolean value) {
				plaZService.setPdfReportAvailable(
						modelSession.getPlanProSchnittstelle(), value);
				plaZService.setSvrlFileAvailable(
						modelSession.getPlanProSchnittstelle(), value);
			}
		};
	}
}
