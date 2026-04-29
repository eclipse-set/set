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
package org.eclipse.set.core.fileservice;

import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.core.services.version.PlanProVersionService.PlanProVersionFormat;
import org.eclipse.set.model.validationreport.ValidationreportFactory;
import org.eclipse.set.model.validationreport.VersionInfo;
import org.junit.function.ThrowingRunnable;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * The {@link PlanProVersionService} is needed for open planpro file
 */
public class MockPlanProVersionService {
	private static final PlanProVersionFormat currentVersion = new PlanProVersionFormat(
			"1.10", "0", "3"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private static VersionInfo givenCurrentVersionInfo() {
		final VersionInfo currentVersionInfo = ValidationreportFactory.eINSTANCE
				.createVersionInfo();
		currentVersionInfo.getPlanProVersions().add("1.10.0.3"); //$NON-NLS-1$
		currentVersionInfo.getSignalbegriffeVersions().add("1.10.0.4"); //$NON-NLS-1$
		return currentVersionInfo;
	}

	/**
	 * Mock {@link PlanProVersionService}
	 * 
	 * @param doTest
	 *            the test code
	 * @throws Throwable
	 *             the exception
	 */
	@SuppressWarnings("boxing")
	public static void mockPlanProVersionService(final ThrowingRunnable doTest)
			throws Throwable {
		final PlanProVersionService mockVersionService = Mockito
				.mock(PlanProVersionService.class);

		Mockito.when(mockVersionService.getCurrentVersion())
				.thenReturn(givenCurrentVersionInfo());
		Mockito.when(mockVersionService
				.isSupportedVersion(ArgumentMatchers.anyString()))
				.thenReturn(Boolean.TRUE);
		Mockito.when(mockVersionService.getSupportedVersionFormat())
				.thenReturn(currentVersion);
		try (MockedStatic<Services> mockStatic = Mockito
				.mockStatic(Services.class)) {
			mockStatic.when(Services::getPlanProVersionService)
					.thenReturn(mockVersionService);
			doTest.run();
		}
	}
}
