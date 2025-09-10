/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform.utils;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.application.geometry.GeoKanteGeometryServiceImpl;
import org.eclipse.set.application.geometry.GeoKanteGeometrySessionData;
import org.eclipse.set.application.geometry.PointObjectPositionServiceImpl;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.feature.siteplan.TrackSwitchMetadataProvider;
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
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
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

	protected static final String GEOMETRY_SERVICE_FIELD_NAME = "geometryService";
	protected static final String POINT_OBJECT_POSITION_SERVICE_FIELD_NAME = "pointObjectPositionService";
	protected static final String POSITION_SERVICE_FIELD_NAME = "positionService";

	GeoKanteGeometryService geometryService;
	private TrackSwitchMetadataProvider trackSwitchMetadataProvider;
	private TrackSwitchTransformator trackSwitchTransformator;

	// IMPROVE: OSGI-based test for dependency injection
	protected void setupTransformators(
			final SiteplanTransformatorImpl transformator) throws Exception {
		setupTransformator(transformator);
		givenTrackSwitchMetadata();
		givenTrackSwitchTransformator();
		transformator.transformators
				.addAll(List.of(new FMAComponentTransformator(),
						new PZBTransformator(), new RouteTransformator(),
						new SignalTransformator(), new StationTransformator(),
						new TrackLockTransformator(), trackSwitchTransformator,
						new TrackTransformator(), new TrackCloseTransformator(),
						new ExternalElementControlTransform(),
						new CantTransform(), new LockKeyTransformator()));
		transformator.transformators.forEach(this::setupTransformator);
	}

	private void givenTrackSwitchTransformator() throws Exception {
		trackSwitchTransformator = new TrackSwitchTransformator();
		FieldUtils.writeField(trackSwitchTransformator,
				"trackSwitchMetadataProvider", trackSwitchMetadataProvider,
				true);
	}

	protected void givenTrackSwitchMetadata() throws Exception {
		trackSwitchMetadataProvider = new TrackSwitchMetadataProvider() {
			@Override
			public Path getFormelPath() {
				try {
					return Path.of(SiteplanTest.class.getClassLoader()
							.getResource("Weichenbauformen.csv")
							.toURI());
				} catch (final URISyntaxException e) {
					return null;
				}
			}
		};
		trackSwitchMetadataProvider.initialize();
	}

	private <T> void setupTransformator(final T transformator) {

		try {
			registerService(transformator, GEOMETRY_SERVICE_FIELD_NAME);
			registerService(transformator,
					POINT_OBJECT_POSITION_SERVICE_FIELD_NAME);
			registerService(transformator, POSITION_SERVICE_FIELD_NAME);
		} catch (final Exception e) {
			// ignore (test will fail)
		}
	}

	private <T> void registerService(final T transformator,
			final String serviceName)
			throws IllegalAccessException, SecurityException {
		try {
			if (transformator.getClass()
					.getDeclaredField(serviceName) == null) {
				return;
			}

			switch (serviceName) {
				case GEOMETRY_SERVICE_FIELD_NAME: {
					FieldUtils.writeField(transformator, serviceName,
							geometryService, true);
					break;
				}

				case POINT_OBJECT_POSITION_SERVICE_FIELD_NAME: {
					final PointObjectPositionServiceImpl positionService = new PointObjectPositionServiceImpl();
					FieldUtils.writeField(positionService,
							GEOMETRY_SERVICE_FIELD_NAME, geometryService, true);
					FieldUtils.writeField(transformator, serviceName,
							positionService, true);

					break;
				}

				case POSITION_SERVICE_FIELD_NAME: {
					FieldUtils.writeField(transformator, serviceName,
							new PositionServiceImpl(), true);
					break;
				}
				default:
					return;
			}
		} catch (final NoSuchFieldException e) {
			// ignore
		}

	}

	@SuppressWarnings("boxing")
	protected void givenGeoKanteGeometryService() throws Exception {
		geometryService = new GeoKanteGeometryServiceImpl();
		final Map<PlanPro_Schnittstelle, GeoKanteGeometrySessionData> sessionesData = new HashMap<>();

		final GeoKanteGeometrySessionData geometrySessionData = new GeoKanteGeometrySessionData();
		sessionesData.put(planProSchnittstelle, geometrySessionData);

		FieldUtils.writeField(geometryService, "sessionesData", sessionesData,
				true);

		final Method declaredMethod = geometryService.getClass()
				.getDeclaredMethod("findGeoKanteGeometry",
						GeoKanteGeometrySessionData.class,
						MultiContainer_AttributeGroup.class);
		declaredMethod.setAccessible(true);

		declaredMethod.invoke(geometryService, geometrySessionData,
				PlanProSchnittstelleExtensions.getContainer(
						planProSchnittstelle, ContainerType.INITIAL));
		declaredMethod.invoke(geometryService, geometrySessionData,
				PlanProSchnittstelleExtensions.getContainer(
						planProSchnittstelle, ContainerType.FINAL));
		FieldUtils.writeField(geometryService, "isProcessComplete", true, true);
	}
}
