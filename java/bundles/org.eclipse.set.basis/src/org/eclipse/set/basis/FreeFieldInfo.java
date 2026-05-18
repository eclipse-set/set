/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

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
	 * @param mainPlan
	 *            the main {@link LoadedPlanInformation}
	 * @param comparePlan
	 *            the compare {@link LoadedPlanInformation}
	 */
	public record SignificantInformation(LoadedPlanInformation mainPlan,
			LoadedPlanInformation comparePlan) {

	}

	/**
	 * @param name
	 *            the plan name
	 * @param checksum
	 *            the checksum
	 * @param timestamp
	 *            the timestamp
	 */
	public record LoadedPlanInformation(String name, String checksum,
			String timestamp) {

	}
}
