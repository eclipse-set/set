/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.set.basis.PlanProXMLNode;
import org.eclipse.set.model.validationreport.ObjectScope;

/**
 * Determines the scope of an object based on an XML node
 * 
 * @author Stuecker
 *
 */
public class NodeObjectScopeProvider {
	private static final String NODE_AUSGABE_FACHDATEN = "Ausgabe_Fachdaten"; //$NON-NLS-1$

	private static final String NODE_CONTAINER = "Container"; //$NON-NLS-1$

	private static final String XPATH_PLANUNGSBEREICH = "//LST_Planung_Gruppe/LST_Planung_Einzel/LST_Objekte_Planungsbereich/ID_LST_Objekt_Planungsbereich/Wert"; //$NON-NLS-1$

	/**
	 * Checks whether a node is inside a Ausgabe_Fachdaten container
	 * 
	 * @param node
	 *            the node to check
	 * @return whether the node is contained within a Ausgabe_Fachdaten
	 *         container
	 */
	private static boolean isChildOfNode(final PlanProXMLNode node,
			final String parentNodeName) {
		if (node == null) {
			return false;
		}
		final String nodeName = node.getNodeName();
		if (nodeName != null && nodeName.equals(parentNodeName)) {
			return true;
		}
		return isChildOfNode(node.getParent(), parentNodeName);
	}

	private List<String> guidPlanungsbereichCache;

	/**
	 * @param node
	 *            The node to find the scope for
	 * @return The object scope or ObjectScope.UNKNOWN
	 */
	public ObjectScope getObjectScope(final PlanProXMLNode node) {
		if (node == null) {
			return ObjectScope.UNKNOWN;
		}
		final PlanProXMLNode parent = node.getParent();
		if (parent == null) {
			return ObjectScope.UNKNOWN;
		}

		if (!isChildOfNode(node, NODE_AUSGABE_FACHDATEN)
				|| !isChildOfNode(node, NODE_CONTAINER)) {
			return ObjectScope.UNKNOWN;
		}
		final String guid = XMLNodeFinder.findNearestNodeGUID(node);
		if (guid == null) {
			return ObjectScope.UNKNOWN;
		}

		if (isGUIDInLSTPlanungsbereich(node, guid)) {
			return ObjectScope.PLAN;
		}

		return ObjectScope.BETRACHTUNG;

	}

	private boolean isGUIDInLSTPlanungsbereich(final PlanProXMLNode node,
			final String guid) {
		// For performance reasons, build a cache of all guids for objects
		// inside the ID_LST_Objekt_Planungsbereich
		if (guidPlanungsbereichCache == null) {
			guidPlanungsbereichCache = new ArrayList<>();

			// final XPath xPath = XPathFactory.newInstance().newXPath();
			// final String expression = String.format(XPATH_PLANUNGSBEREICH,
			// guid);
			try {
				final PlanProXMLNode rootNode = node.getRootNode();
				final Set<PlanProXMLNode> evaluateXPath = rootNode
						.evaluateXPath(XPATH_PLANUNGSBEREICH);
				guidPlanungsbereichCache.addAll(evaluateXPath.stream()
						.map(PlanProXMLNode::getTextValue)
						.toList());
				// final Set<PlanProXMLNode> allNodes = node.getAllNodes();
				// allNodes.stream()
				// .filter(n -> n.getNodeName()
				// .equals("ID_LST_Objekt_Planungsbereich"))
				// .forEach(n -> {
				// if (n.getChildrens()
				// .getFirst()
				// .getNodeName()
				// .equals("Wert")) {
				// guidPlanungsbereichCache.add(n.getChildrens()
				// .getFirst()
				// .getTextValue());
				// }
				// });
				// final NodeList nodeList = (NodeList)
				// xPath.compile(expression)
				// .evaluate(node.getOwnerDocument(),
				// XPathConstants.NODESET);
				// for (int i = 0; i < nodeList.getLength(); ++i) {
				// final Node entry = nodeList.item(i);
				// guidPlanungsbereichCache.add(entry.getNodeValue());
				// }
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}

		}

		return guidPlanungsbereichCache.contains(guid);
	}
}
