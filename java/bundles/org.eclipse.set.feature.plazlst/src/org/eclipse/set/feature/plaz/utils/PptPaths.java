/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.plaz.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;

/**
 * Utility for PPT paths.
 * 
 * @author Schaefer
 */
public class PptPaths {

	private static final String PLAZ_REPORT_FULL_PATTERN = "%s_PlaZ_Bericht.pdf"; //$NON-NLS-1$
	private static final String PLAZ_REPORT_SUMMARY_PATTERN = "%s_PlaZ_Bericht_Übersicht.pdf"; //$NON-NLS-1$

	/**
	 * @param modelSession
	 *            the model session
	 * @param base
	 *            the base path
	 * @param exportType
	 *            the export type
	 * 
	 * @return the path to the full PlaZ pdf report
	 */
	public static Path getPdfFullPlazReport(final IModelSession modelSession,
			final Path base, final ExportType exportType) {
		return Paths.get(base.toString(),
				getPdfFullPlazReport(modelSession, exportType));
	}

	/**
	 * @param modelSession
	 *            the model session
	 * @param base
	 *            the base path
	 * @param exportType
	 *            the export type
	 * 
	 * @return the path to the summary PlaZ pdf report
	 */
	public static Path getPdfSummaryPlazReport(final IModelSession modelSession,
			final Path base, final ExportType exportType) {
		return Paths.get(base.toString(),
				getPdfSummaryPlazReport(modelSession, exportType));
	}

	// IMPROVE: is there a problem with overlapping exports from different
	// toolbox file roles?
	private static String getModelBaseName(final IModelSession modelSession,
			final ExportType exportType) {
		final Path derivedPath = PlanProSchnittstelleExtensions.getDerivedPath(
				modelSession.getPlanProSchnittstelle(), "xxx", "xxx", //$NON-NLS-1$ //$NON-NLS-2$
				exportType);
		return PathExtensions.getBaseFileName(derivedPath);
	}

	private static String getPdfFullPlazReport(final IModelSession modelSession,
			final ExportType exportType) {
		return String.format(PLAZ_REPORT_FULL_PATTERN,
				getModelBaseName(modelSession, exportType));
	}

	private static String getPdfSummaryPlazReport(
			final IModelSession modelSession, final ExportType exportType) {
		return String.format(PLAZ_REPORT_SUMMARY_PATTERN,
				getModelBaseName(modelSession, exportType));
	}
}
