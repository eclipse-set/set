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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten;

/**
 * Overviewplan Track contains TOPKanteMetadata of this track and left- right-
 * track, which intersect this track
 * 
 * @author truong
 *
 */
public class OverviewplanTrack {
	List<TOPKanteMetaData> topEdges = new ArrayList<>();

	/**
	 * Left intersect track
	 */
	public Map<TOP_Knoten, OverviewplanTrack> leftTracks = new HashMap<>();
	/**
	 * Right intersect track
	 */
	public Map<TOP_Knoten, OverviewplanTrack> rightTracks = new HashMap<>();

	/**
	 * @return the TOPKanteMetaData of this track
	 */
	public List<TOPKanteMetaData> getTopEdges() {
		return topEdges;
	}

	/**
	 * @param edge
	 *            TOPKanteMetaData
	 */
	public OverviewplanTrack(final TOPKanteMetaData edge) {
		topEdges.add(edge);
	}

	/**
	 * @param edge
	 *            TOPKanteMetaData
	 */
	public void addTrackSections(final TOPKanteMetaData edge) {
		topEdges.add(edge);
	}

	/**
	 * @return the TOPKanteMetaData, which have only one continuous track
	 */
	public TOPKanteMetaData getFirstEdge() {
		if (isSingleEdgeTrack()) {
			return topEdges.get(0);
		}
		return topEdges.stream()
				.filter(edge -> edge.getContinuousEdges().size() == 1).toList()
				.get(0);
	}

	/**
	 * @return true, if this track contains only one TOPKanteMetaData
	 */
	public boolean isSingleEdgeTrack() {
		return topEdges.size() == 1;
	}
}
