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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Overviewplan Track contains TOPKanteMetadata of this track and left- right-
 * track, which intersect this track
 * 
 * @author truong
 *
 */
public class OverviewplanTrack {
	/**
	 * Left intersect track
	 */
	public Map<TOP_Knoten, OverviewplanTrack> leftTracks = new HashMap<>();

	/**
	 * Track Lvl
	 */
	public int lvl;

	/**
	 * First found out track will fixed
	 */
	public boolean fixedLvl = false;
	/**
	 * Right intersect track
	 */
	public Map<TOP_Knoten, OverviewplanTrack> rightTracks = new HashMap<>();

	private final List<TOPKanteMetaData> topEdges = new LinkedList<>();

	private final List<TOP_Knoten> topNodes = new LinkedList<>();

	/**
	 * @param edge
	 *            TOPKanteMetaData
	 */
	public OverviewplanTrack(final TOPKanteMetaData edge) {
		lvl = 0;
		topEdges.add(edge);
		addTrackSections(edge, edge.getTopNodeA());
		Collections.reverse(topEdges);
		addTrackSections(edge, edge.getTopNodeB());

	}

	/**
	 * @return the TOPKanteMetaData of this track
	 */
	public List<TOPKanteMetaData> getTopEdges() {
		return topEdges;
	}

	/**
	 * Give list of TOP_Knoten with sort by start to end of Track
	 * 
	 * @return list TOP_Knoten
	 */
	public List<TOP_Knoten> getTopNodes() {
		if (!topNodes.isEmpty()) {
			return topNodes;
		}
		final TOPKanteMetaData first = topEdges.get(0);
		final List<Pair<TOP_Knoten, TOPKanteMetaData>> continuousEdges = first
				.getContinuousEdges();
		if (continuousEdges.isEmpty()) {
			topNodes.add(first.getTopNodeA());
			topNodes.add(first.getTopNodeB());
			return topNodes;
		}
		final Pair<TOP_Knoten, TOPKanteMetaData> pair = continuousEdges.get(0);
		TOP_Knoten connectNode = pair.getKey();
		topNodes.add(first.getNextTopNode(connectNode));
		TOPKanteMetaData continuous = pair.getValue();
		while (continuous != null) {
			topNodes.add(connectNode);
			connectNode = continuous.getNextTopNode(connectNode);
			continuous = continuous.getContinuousEdgeAt(connectNode);
		}
		topNodes.add(connectNode);
		return topNodes;
	}

	/**
	 * @return true, if this track contains only one TOPKanteMetaData
	 */
	public boolean isSingleEdgeTrack() {
		return topEdges.size() == 1;
	}

	private void addTrackSections(final TOPKanteMetaData md,
			final TOP_Knoten topNode) {
		if (md == null) {
			return;
		}
		final TOPKanteMetaData continuous = md.getContinuousEdgeAt(topNode);
		if (continuous == null || topEdges.contains(continuous)) {
			return;
		}
		topEdges.add(continuous);
		addTrackSections(continuous, continuous.getNextTopNode(topNode));
	}
}
