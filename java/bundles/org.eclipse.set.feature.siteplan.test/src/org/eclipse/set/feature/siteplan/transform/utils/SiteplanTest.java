/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform.utils;

import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.application.geometry.GeoKanteGeometryServiceImpl;
import org.eclipse.set.feature.siteplan.positionservice.PositionServiceImpl;
import org.eclipse.set.feature.siteplan.transform.CantTransform;
import org.eclipse.set.feature.siteplan.transform.ExternalElementControlTransform;
import org.eclipse.set.feature.siteplan.transform.FMAComponentTransformator;
import org.eclipse.set.feature.siteplan.transform.LockKeyTransformator;
import org.eclipse.set.feature.siteplan.transform.PZBTransformator;
import org.eclipse.set.feature.siteplan.transform.RouteTransformator;
import org.eclipse.set.feature.siteplan.transform.SignalTransformator;
import org.eclipse.set.feature.siteplan.transform.SiteplanTransformatorImpl;
import org.eclipse.set.feature.siteplan.transform.StationTransformator;
import org.eclipse.set.feature.siteplan.transform.TrackCloseTransformator;
import org.eclipse.set.feature.siteplan.transform.TrackLockTransformator;
import org.eclipse.set.feature.siteplan.transform.TrackSwitchTransformator;
import org.eclipse.set.feature.siteplan.transform.TrackTransformator;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.junit.jupiter.params.provider.Arguments;

/**
 * Base class for Siteplan Tests with common utilities
 * 
 * @author Stuecker
 *
 */
@SuppressWarnings("nls")
public class SiteplanTest extends AbstractToolboxTest {

	/**
	 * @return the siteplan reference files
	 */
	protected static Stream<Arguments> getSiteplanReferenceFiles() {
		return Stream.of(Arguments.of(PPHN_1_10_0_3_20220517_PLANPRO, "pphn"));
	}

	// IMPROVE: OSGI-based test for dependency injection
	protected static void setupTransformators(
			final SiteplanTransformatorImpl transformator) {
		setupTransformator(transformator);
		transformator.transformators.addAll(List.of(
				new FMAComponentTransformator(), new PZBTransformator(),
				new RouteTransformator(), new SignalTransformator(),
				new StationTransformator(), new TrackLockTransformator(),
				new TrackSwitchTransformator(), new TrackTransformator(),
				new TrackCloseTransformator(),
				new ExternalElementControlTransform(), new CantTransform(),
				new LockKeyTransformator()));
		transformator.transformators.forEach(SiteplanTest::setupTransformator);
	}

	private static <T> void setupTransformator(final T transformator) {

		try {
			FieldUtils.writeField(transformator, "trackService", //$NON-NLS-1$
					new GeoKanteGeometryServiceImpl(), true);
			FieldUtils.writeField(transformator, "positionService",
					new PositionServiceImpl(), true);
		} catch (final IllegalAccessException e) {
			// ignore (test will fail)
		}
	}

}
