/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.export;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.constants.ExportType;

/**
 * Interface for additional exports.
 * 
 * @author Schaefer
 */
public interface AdditionalExportService {

	/**
	 * @param elements
	 *            the list to add additional elements to
	 */
	public void createAdditionalCheckboxModelElements(
			List<CheckboxModelElement> elements);

	/**
	 * Create an additional export.
	 * 
	 * @param id
	 *            the id for the export
	 * @param modelSession
	 *            the model session
	 * @param monitor
	 *            optional progress monitor
	 * @param exportDirectory
	 *            the export directory
	 * @param exportType
	 *            the export type
	 * @param overwriteHandling
	 *            what to do when overwriting files
	 */
	public void createAdditionalExport(String id,
			final IModelSession modelSession, final IProgressMonitor monitor,
			final Path exportDirectory, final ExportType exportType,
			final OverwriteHandling overwriteHandling);

	/**
	 * @param id
	 *            the ID
	 * 
	 * @return whether the given ID is an additional export ID
	 */
	public boolean isAdditionalExportId(String id);
}
