/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
