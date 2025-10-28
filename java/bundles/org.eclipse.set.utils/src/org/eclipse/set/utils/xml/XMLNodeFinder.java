/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.xml;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.set.basis.files.ToolboxFile;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Helper class to find nodes in a XML Document
 * 
 * @author Stuecker
 *
 */
public class XMLNodeFinder {
	Node rootNode;
	private static final String NODE_IDENTITAET = "Identitaet"; //$NON-NLS-1$
	/**
	 * Wert
	 */
	public static final String NODE_WERT = "Wert"; //$NON-NLS-1$

	/**
	 * Returns the node for a line number. This attempts to find the most local
	 * node for the line number.
	 * 
	 * @param lineNumber
	 *            the line number to look at
	 * @return the found node or null
	 */
	public Node findNodeByLineNumber(final int lineNumber) {
		return findNodeByLineNumber(rootNode, lineNumber);
	}

	private Node findNodeByLineNumber(final Node currentNode,
			final int lineNumber) {
		if (currentNode == null || lineNumber < 0) {
			return null;
		}
		final NodeList children = currentNode.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			final Node node = children.item(i);
			if (isLineNumberInNode(lineNumber, node)) {
				return findNodeByLineNumber(node, lineNumber);
			}
		}

		return currentNode;
	}

	/**
	 * Returns the first node match the name
	 * 
	 * @param nodeName
	 *            the name of node
	 * @return the found node or null
	 */
	public Node findFirstNodeByNodeName(final String nodeName) {
		return findFirstNodeByNodeName(rootNode, nodeName);
	}

	/**
	 * @param node
	 *            root node
	 * @param nodeName
	 *            the node of node
	 * @return the foudn node or null
	 */
	public Node findFirstNodeByNodeName(final Node node,
			final String nodeName) {
		if (node == null) {
			return null;
		}
		if (node.getNodeName().equals(nodeName)) {
			return node;
		}

		final NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			final Node childNode = findFirstNodeByNodeName(nodeList.item(i),
					nodeName);
			if (childNode != null) {
				return childNode;
			}
		}
		return null;
	}

	/**
	 * Returns whether a line number is contained within a XML node
	 * 
	 * @param lineNumber
	 *            The line number to check
	 * @param node
	 *            The node to check
	 * @return whether the line number is within the XML node
	 */
	private static boolean isLineNumberInNode(final int lineNumber,
			final Node node) {
		final int start = LineNumberXMLReader.getStartLineNumber(node);
		final int end = LineNumberXMLReader.getNodeLastLineNumber(node);
		return lineNumber >= start && lineNumber <= end;
	}

	/**
	 * Loads a document
	 * 
	 * @param toolboxfile
	 *            the ToolboxFile
	 * @param docPath
	 *            the path of document
	 */
	public void read(final ToolboxFile toolboxfile, final Path docPath) {
		try {
			rootNode = ObjectMetadataXMLReader.read(toolboxfile, docPath);
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// Discard exceptions
		}
	}

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

}
