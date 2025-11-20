/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.overviewplan.transformator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.feature.overviewplan.track.TrackNetworkServiceImpl;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 */
class OverviewplanTransformatorTest extends AbstractToolboxTest {

	private static <T> void setupTransformator(final T transformator) {

		try {
			FieldUtils.writeField(transformator, "trackService", //$NON-NLS-1$
					new TrackNetworkServiceImpl(), true);
		} catch (final IllegalAccessException e) {
			// ignore (test will fail)
		}
	}

	/**
	 * @return the siteplan reference files
	 */
	protected static Stream<Arguments> getSiteplanReferenceFiles() {
		return Stream.of(Arguments.of(PPHN_1_10_0_3_20220517_PLANPRO, "pphn"));
	}

	// IMPROVE: OSGI-based test for dependency injection
	protected static void setupTransformators(
			final OverviewplanTransformatorImpl transformator) {
		setupTransformator(transformator);
		transformator.transformators.add(new TrackTransformator());
		transformator.transformators
				.forEach(OverviewplanTransformatorTest::setupTransformator);
	}

	Siteplan siteplan;

	OverviewplanTransformatorImpl testee;

	private void givenSiteplanTransformator() {
		testee = new OverviewplanTransformatorImpl();
		setupTransformators(testee);
	}

	private void whenTransformingToOverviewplanModel() {
		siteplan = testee.transform(planProSchnittstelle);
	}

	@ParameterizedTest
	@MethodSource("getSiteplanReferenceFiles")
	void testSiteplanTransformSuccessful(final String file) throws IOException {
		givenSiteplanTransformator();
		givenPlanProFile(file);

		assertDoesNotThrow(() -> whenTransformingToOverviewplanModel());
		// then no exception occurs
	}
}
