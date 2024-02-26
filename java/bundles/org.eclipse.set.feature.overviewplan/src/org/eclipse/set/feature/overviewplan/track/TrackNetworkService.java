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
package org.eclipse.set.feature.overviewplan.track;

import java.util.List;

import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten;

/**
 * @author truong
 *
 */
public interface TrackNetworkService {
	/**
	 * Get TOPKanteMetaData belong to this TOPKante
	 * 
	 * @param topKante
	 * @return the TOPKanteMetaData
	 */
	TOPKanteMetaData getTOPKanteMetaData(TOP_Kante topKante);

	/**
	 * @param topKnoten
	 * @return TOPKanteMetaData
	 */
	List<TOPKanteMetaData> getTOPKanteMetaData(TOP_Knoten topKnoten);

	/**
	 * @param topKanten
	 * @param guid
	 * @return TOPKanteMetaData
	 */
	TOPKanteMetaData getTOPKanteMetaData(List<TOP_Kante> topKanten,
			String guid);

	/**
	 * @param container
	 */
	void setupTrackNet(MultiContainer_AttributeGroup container);

	/**
	 * @return list of track
	 */
	List<OverviewplanTrack> getTracksCache();

	/**
	 * @param md
	 * @return {@link OverviewplanTrack}
	 */
	OverviewplanTrack getTrack(TOPKanteMetaData md);

	/**
	 * @param node
	 * @param track
	 * @return position of node
	 */
	Position getTOPNodePosition(TOP_Knoten node, OverviewplanTrack track);

	/**
	 * Clear storage cache
	 * 
	 * @param container
	 *            the container
	 */
	void clean(MultiContainer_AttributeGroup container);
}
