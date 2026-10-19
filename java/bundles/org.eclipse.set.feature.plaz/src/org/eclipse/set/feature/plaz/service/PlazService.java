/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plaz.service;

import java.nio.file.Path;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;

/**
 * Interface for the PlaZ service.
 * 
 * @author Schaefer
 */
public interface PlazService {

	/**
	 * @param monitor
	 *            optional progress monitor
	 * @param tmpDir
	 *            temporary directory
	 * @param toolboxFile
	 *            the toolbox file
	 * @param pdfLocation
	 *            location for the pdf file
	 * @param schemaValid
	 *            the reported schema validity
	 * @param svrlAvailable
	 *            if the SVRL file was already generated
	 * @param overwriteHandling
	 *            what to do when overwriting files
	 */
	void createCompletePdfReport(IProgressMonitor monitor, Path tmpDir,
			ToolboxFile toolboxFile, Path pdfLocation, boolean schemaValid,
			boolean svrlAvailable, OverwriteHandling overwriteHandling);

	/**
	 * @param monitor
	 *            optional progress monitor
	 * @param tmpDir
	 *            temporary directory
	 * @param toolboxFile
	 *            the toolbox file
	 * @param htmlLocation
	 *            location for the html file
	 * @param schemaValid
	 *            the reported schema validity
	 * @param svrlAvailable
	 *            if the SVRL file was already generated
	 */
	void createHtmlReport(IProgressMonitor monitor, Path tmpDir,
			ToolboxFile toolboxFile, Path htmlLocation, boolean schemaValid,
			boolean svrlAvailable);

	/**
	 * @param monitor
	 *            optional progress monitor
	 * @param tmpDir
	 *            temporary directory
	 * @param toolboxFile
	 *            the toolbox file
	 * @param pdfLocation
	 *            location for the pdf file
	 * @param schemaValid
	 *            the reported schema validity
	 * @param svrlAvailable
	 *            if the SVRL file was already generated
	 * @param overwriteHandling
	 *            what to do when overwriting files
	 */
	void createPdfSummary(IProgressMonitor monitor, Path tmpDir,
			ToolboxFile toolboxFile, Path pdfLocation, boolean schemaValid,
			boolean svrlAvailable, OverwriteHandling overwriteHandling);

	/**
	 * @return the PlaZ Version
	 */
	String getVersion();

	/**
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 * 
	 * @return whether the PlaZ html report is available
	 */
	boolean isHtmlReportAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle);

	/**
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 * 
	 * @return whether the PlaZ pdf report is available
	 */
	boolean isPdfReportAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle);

	/**
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 * 
	 * @return whether the PlaZ pdf summary is available
	 */
	boolean isPdfSummaryAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle);

	/**
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 * 
	 * @return whether the svrl file is available
	 */
	boolean isSvrlAvailable(final PlanPro_Schnittstelle planProSchnittstelle);

	/**
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 * @param value
	 *            whether the PlaZ html report is available
	 */
	void setHtmlReportAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle, boolean value);

	/**
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 * @param value
	 *            whether the PlaZ pdf report is available
	 */
	void setPdfReportAvailable(final PlanPro_Schnittstelle planProSchnittstelle,
			boolean value);

	/**
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 * @param value
	 *            whether the PlaZ pdf summary is available
	 */
	void setPdfSummaryAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle, boolean value);

	/**
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 * @param value
	 *            whether the svrl file is available
	 */
	void setSvrlFileAvailable(final PlanPro_Schnittstelle planProSchnittstelle,
			boolean value);
}
