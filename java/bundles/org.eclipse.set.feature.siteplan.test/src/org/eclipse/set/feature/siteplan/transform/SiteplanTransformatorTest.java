/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.eclipse.set.feature.siteplan.transform.utils.SiteplanTest;
import org.eclipse.set.model.siteplan.Siteplan;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Stuecker
 *
 */
public class SiteplanTransformatorTest extends SiteplanTest {
	SiteplanTransformatorImpl testee;
	Siteplan siteplan;

	@ParameterizedTest
	@MethodSource("getSiteplanReferenceFiles")
	void testSiteplanTransformSuccessful(final String file) throws Exception {
		givenPlanProFile(file);
		givenGeoKanteGeometryService();
		givenSiteplanTransformator();
		assertDoesNotThrow(this::whenTransformingToSiteplanModel);
		// then no exception occurs
	}

	private void whenTransformingToSiteplanModel() {
		siteplan = testee.transform(planProSchnittstelle);
	}

	private void givenSiteplanTransformator() {
		testee = new SiteplanTransformatorImpl();
		setupTransformators(testee);
	}

}
