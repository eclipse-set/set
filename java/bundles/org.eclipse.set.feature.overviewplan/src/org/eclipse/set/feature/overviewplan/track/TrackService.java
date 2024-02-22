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

import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten;

/**
 * @author truong
 *
 */
public interface TrackService {
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
	void setupTrackNetz(MultiContainer_AttributeGroup container);

	/**
	 * @return list of track
	 */
	List<OverviewplanTrack> getTracksCache();

}
