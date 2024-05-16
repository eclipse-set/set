/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.utils;

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
public class ValidationObjectScopeProvider {
	private static final String NODE_AUSGABE_FACHDATEN = "Ausgabe_Fachdaten"; //$NON-NLS-1$
	private static final String NODE_IDENTITAET = "Identitaet"; //$NON-NLS-1$
	private static final String NODE_WERT = "Wert"; //$NON-NLS-1$
	private static final String XPATH_PLANUNGSBEREICH = "//ID_LST_Objekt_Planungsbereich/Wert/text()"; //$NON-NLS-1$

	/**
	 * @param node
	 *            the node
	 * @return guid of the object contain this node
	 */
	public static String findNearestNodeGUID(final Node node) {
		if (node == null) {
			return null;
		}

		// Try to find <Identitaet><Wert>[GUID]</Identitaet></Wert>
		// as a child node of the current node
		final Node identitaet = getChildNodeByName(node, NODE_IDENTITAET);
		if (identitaet != null) {
			// Read <Wert>[GUID]</Wert> from the <Identitaet> node
			final Node valueNode = getChildNodeByName(identitaet, NODE_WERT);
			if (valueNode != null) {
				final Node firstChild = valueNode.getFirstChild();
				if (firstChild != null) {
					return firstChild.getNodeValue();
				}
			}
		}

		// Walk up the XML tree
		return findNearestNodeGUID(node.getParentNode());
	}

	private static Node getChildNodeByName(final Node node, final String name) {
		final NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); ++i) {
			final Node child = children.item(i);
			if (child.getNodeName().equals(name)) {
				return child;
			}
		}
		return null;
	}

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
		final String guid = findNearestNodeGUID(node);
		if (guid == null) {
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
}
