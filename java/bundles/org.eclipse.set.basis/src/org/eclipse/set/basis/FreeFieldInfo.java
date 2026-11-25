/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.List;

import org.eclipse.set.basis.files.ToolboxFileRole;

/**
 * Additional information for (and around) the free field of the pdf export.
 * 
 * @author Schaefer
 * @param significantInformation
 *            the {@link SignificantInformation}
 * 
 */
public record FreeFieldInfo(SignificantInformation significantInformation) {
	/**
	 * @param loadedPlansInformation
	 *            the loaed plans information
	 */
	public record SignificantInformation(
			List<LoadedPlanInformation> loadedPlansInformation) {

	}

	/**
	 * @param role
	 *            the loaded plan role
	 * @param name
	 *            the plan name
	 * @param checksumZip
	 *            the checksum of zip file
	 * @param checksumContent
	 *            the checksum of file content
	 * @param timestamp
	 *            the time stamp
	 */
	public record LoadedPlanInformation(ToolboxFileRole role, String name, String checksumZip,
			String checksumContent, String timestamp) {

	}
}
