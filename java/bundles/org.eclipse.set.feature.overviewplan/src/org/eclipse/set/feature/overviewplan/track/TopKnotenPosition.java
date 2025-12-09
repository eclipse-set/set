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

import static org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.getTopKanten;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.SiteplanFactory;

import com.google.common.collect.Streams;

/**
 * Find out all position of node through edge length. The position of node is
 * relativ, it depending on choose of first node, which have position (0, 0)
 * 
 * @author Truong
 */
public class TopKnotenPosition {
	Map<TOP_Knoten, Double> topNodeHorizontalCoor;
	Map<TOP_Knoten, Position> topNodePosition;
	TrackNetworkService trackService;
	TOP_Knoten firstNode;

	/**
	 * @param trackService
	 *            {@link TrackNetworkService}
	 * @param firstNode
	 *            the node
	 */
	public TopKnotenPosition(final TrackNetworkService trackService,
			final TOP_Knoten firstNode) {
		this.trackService = trackService;
		this.firstNode = firstNode;
		topNodeHorizontalCoor = new HashMap<>();
		findAllTOPNodeHorizontalCoor();
	}

	/**
	 * @return all node with horizontal coordinate
	 */
	public Map<TOP_Knoten, Double> findAllTOPNodeHorizontalCoor() {
		final List<TOP_Knoten> allTopNodes = trackService.getTracksCache()
				.stream()
				.flatMap(track -> track.getTopNodes().stream())
				.distinct()
				.toList();
		if (!topNodeHorizontalCoor.isEmpty()
				&& topNodeHorizontalCoor.keySet().containsAll(allTopNodes)) {
			return topNodeHorizontalCoor;
		}
		final List<TOP_Kante> edges = Streams.stream(getTopKanten(firstNode))
				.collect(Collectors.toList());
		if (edges.isEmpty()) {
			throw new IllegalArgumentException(
					String.format("TOP_Knoten: %s haven't TOP_Kante", //$NON-NLS-1$
							firstNode.getIdentitaet().getWert()));
		}

		findTOPNodeHorizontalCoor(
				trackService.getTOPKanteMetaData(edges.get(0)), firstNode, 0,
				true);
		return topNodeHorizontalCoor;
	}

	private void findTOPNodeHorizontalCoor(final TOPKanteMetaData md,
			final TOP_Knoten topNode, final double posX,
			final boolean isForward) {
		if (topNodeHorizontalCoor.containsKey(topNode)) {
			return;
		}
		topNodeHorizontalCoor.put(topNode, Double.valueOf(posX));
		md.getIntersectEdgeAt(topNode)
				.forEach(intersectEdge -> findTOPNodeHorizontalCoor(md,
						intersectEdge, topNode, posX, isForward));
	}

	private void findTOPNodeHorizontalCoor(final TOPKanteMetaData md,
			final TOPKanteMetaData intersectEdge, final TOP_Knoten topNode,
			final double posX, final boolean inDirection) {
		final TOP_Knoten nextNode = intersectEdge.getNextTopNode(topNode);
		if (nextNode == null) {
			throw new IllegalArgumentException(String.format(
					"TOP_Kante: %s haven only one TOP_Knoten", //$NON-NLS-1$
					intersectEdge.getTopEdge().getIdentitaet().getWert()));
		}
		final boolean isSameDirection = md.isSameDirection(intersectEdge);
		final int edgeLength = intersectEdge.getLength();
		final boolean newDirection = isSameDirection ? inDirection
				: !inDirection;
		final double nextNodePosX = posX + (newDirection ? 1 : -1) * edgeLength;
		findTOPNodeHorizontalCoor(intersectEdge, nextNode, nextNodePosX,
				newDirection);
	}

	/**
	 * Get position of a node
	 * 
	 * @param node
	 *            the node
	 * @param track
	 *            the track, which contain node
	 * @return node position
	 */
	public Position getTopNodePosition(final TOP_Knoten node,
			final OverviewplanTrack track) {
		if (topNodeHorizontalCoor.isEmpty()
				|| !topNodeHorizontalCoor.containsKey(node)) {
			throw new IllegalArgumentException(
					"TOP_Knoten horizontal coordinate aren't registered"); //$NON-NLS-1$
		}
		if (!track.getTopNodes().contains(node)) {
			throw new IllegalArgumentException(
					"The track isn't contains TOP_Knoten"); //$NON-NLS-1$
		}
		final Position pos = SiteplanFactory.eINSTANCE.createPosition();
		pos.setX(topNodeHorizontalCoor.get(node).doubleValue());
		if (isConnectNode(node, track)) {
			final List<OverviewplanTrack> connect = trackService
					.getTracksCache()
					.stream()
					.filter(connectTrack -> connectTrack.getTopNodes()
							.contains(node) && track != connectTrack)
					.toList();
			pos.setY(connect.get(0).lvl);
		} else {
			pos.setY(track.lvl);
		}

		return pos;
	}

	// A connect node of a track is the track end at this node and intersect
	// another track
	private static boolean isConnectNode(final TOP_Knoten node,
			final OverviewplanTrack track) {
		final List<TOPKanteMetaData> edges = track.getTopEdges()
				.stream()
				.filter(edge -> edge.getTopNodes().contains(node))
				.toList();
		if (edges.size() > 1) {
			return false;
		}
		return !edges.get(0).getIntersectEdgeAt(node).isEmpty();
	}
}
