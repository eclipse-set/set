/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.geometry;

import static org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.defineEdgeGeometry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.graph.DirectedElement;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.locationtech.jts.geom.LineString;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
@Component(service = { EventHandler.class }, property = {
		EventConstants.EVENT_TOPIC + "=" + Events.TOPMODEL_CHANGED,
		EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION })
public class GeoKanteGeometryServiceImpl
		implements GeoKanteGeometryService, EventHandler {
	private Thread findGeometryThread;

	static final Logger logger = LoggerFactory
			.getLogger(GeoKanteGeometryServiceImpl.class);

	private Map<GEO_Kante, LineString> edgeGeometry;
	private boolean isProcessComplete = false;

	/**
	 * Constructor
	 */
	public GeoKanteGeometryServiceImpl() {
		Services.setGeometryService(this);
	}

	@Override
	public void handleEvent(final Event event) {
		final String topic = event.getTopic();
		if (topic.equals(Events.TOPMODEL_CHANGED) && event.getProperty(
				IEventBroker.DATA) instanceof final PlanPro_Schnittstelle schnitstelle) {
			edgeGeometry = new ConcurrentHashMap<>();
			findGeometryThread = new Thread(() -> {
				try {
					logger.debug("Start find geometry of GEO_Kante"); //$NON-NLS-1$
					isProcessComplete = false;
					findGeoKanteGeometry(PlanProSchnittstelleExtensions
							.getContainer(schnitstelle, ContainerType.INITIAL));
					findGeoKanteGeometry(PlanProSchnittstelleExtensions
							.getContainer(schnitstelle, ContainerType.FINAL));
					findGeoKanteGeometry(PlanProSchnittstelleExtensions
							.getContainer(schnitstelle, ContainerType.SINGLE));
					isProcessComplete = true;
					logger.debug(
							"Find geometry of GEO_Kante process is complete"); //$NON-NLS-1$
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}, ToolboxConstants.CacheId.GEOKANTE_GEOMETRY);
			findGeometryThread.start();
		}

		if (topic.equals(Events.CLOSE_SESSION) && findGeometryThread != null
				&& findGeometryThread.isAlive()
				&& !findGeometryThread.isInterrupted()) {
			findGeometryThread.interrupt();
			isProcessComplete = false;
		}
	}

	private void findGeoKanteGeometry(
			final MultiContainer_AttributeGroup container)
			throws InterruptedException {
		if (container == null) {
			return;
		}
		for (final GEO_Kante edge : container.getGEOKante()) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			final LineString geometry = defineEdgeGeometry(edge);
			if (geometry != null) {
				edgeGeometry.put(edge, geometry);
			}
		}
	}

	/**
	 * @param edge
	 *            the geo kante
	 * @return the line string of this GEO Kante
	 */
	@Override
	public LineString getGeometry(final GEO_Kante edge) {
		while (!isFindGeometryComplete()) {
			try {
				Thread.sleep(4000);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
				return null;
			}
		}
		return edgeGeometry.getOrDefault(edge, null);
	}

	/**
	 * @param directedEdge
	 *            the GEO_Kante with direction
	 * @return the line string of this GEO Kante (with the implied direction of
	 *         the GEO Kante)
	 */
	@Override
	public LineString getGeometry(
			final DirectedElement<GEO_Kante> directedEdge) {
		final LineString geometry = getGeometry(directedEdge.getElement());
		if (geometry == null) {
			return null;
		}
		return directedEdge.isForwards() ? geometry : geometry.reverse();
	}

	/**
	 * Check if find geometry process still runing
	 * 
	 * @return true, if the process is done
	 */
	@Override
	public boolean isFindGeometryComplete() {
		return isProcessComplete;
	}
}
