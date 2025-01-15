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

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.set.model.validationreport.ObjectScope;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Determines the scope of an object based on an XML node
 * 
 * @author Stuecker
 *
 */
public class NodeObjectScopeProvider {
	private static final String NODE_AUSGABE_FACHDATEN = "Ausgabe_Fachdaten"; //$NON-NLS-1$

	private static final String NODE_CONTAINER = "Container"; //$NON-NLS-1$

	private static final String XPATH_PLANUNGSBEREICH = "//ID_LST_Objekt_Planungsbereich/Wert/text()"; //$NON-NLS-1$

	/**
	 * Checks whether a node is inside a Ausgabe_Fachdaten container
	 * 
	 * @param node
	 *            the node to check
	 * @return whether the node is contained within a Ausgabe_Fachdaten
	 *         container
	 */
	private static boolean isFachdaten(final Node node) {
		if (node == null) {
			return false;
		}
		final String nodeName = node.getNodeName();
		if (nodeName != null && nodeName.equals(NODE_AUSGABE_FACHDATEN)) {
			return true;
		}
		return isFachdaten(node.getParentNode());
	}

	private List<String> guidPlanungsbereichCache;

	/**
	 * @param node
	 *            The node to find the scope for
	 * @return The object scope or ObjectScope.UNKNOWN
	 */
	public ObjectScope getObjectScope(final Node node) {
		if (node == null) {
			return ObjectScope.UNKNOWN;
		}
		final Node parent = node.getParentNode();
		if (parent == null) {
			return ObjectScope.UNKNOWN;
		}

		if (!isFachdaten(node)) {
			return ObjectScope.UNKNOWN;
		}
		final String guid = XMLNodeFinder.findNearestNodeGUID(node);
		if (guid == null) {
			return ObjectScope.UNKNOWN;
		}

		if (!isInContainerNode(node)) {
			return ObjectScope.UNKNOWN;
		}

		if (isGUIDInLSTPlanungsbereich(node, guid)) {
			return ObjectScope.PLAN;
		}

		return ObjectScope.BETRACHTUNG;

	}

	private boolean isGUIDInLSTPlanungsbereich(final Node node,
			final String guid) {
		// For performance reasons, build a cache of all guids for objects
		// inside the ID_LST_Objekt_Planungsbereich
		if (guidPlanungsbereichCache == null) {
			guidPlanungsbereichCache = new ArrayList<>();

			final XPath xPath = XPathFactory.newInstance().newXPath();
			final String expression = String.format(XPATH_PLANUNGSBEREICH,
					guid);
			try {
				final NodeList nodeList = (NodeList) xPath.compile(expression)
						.evaluate(node.getOwnerDocument(),
								XPathConstants.NODESET);
				for (int i = 0; i < nodeList.getLength(); ++i) {
					final Node entry = nodeList.item(i);
					guidPlanungsbereichCache.add(entry.getNodeValue());
				}
			} catch (final XPathExpressionException e) {
				throw new RuntimeException(e);
			}

		}

		return guidPlanungsbereichCache.contains(guid);
	}

	private boolean isInContainerNode(final Node node) {
		if (node == null || node.getNodeName().equals(NODE_CONTAINER)) {
			return false;
		}

		return node.getParentNode().equals(NODE_CONTAINER) || isInContainerNode(node.getParentNode());
	}
}
