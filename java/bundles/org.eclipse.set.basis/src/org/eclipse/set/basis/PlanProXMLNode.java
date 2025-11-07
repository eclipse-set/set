/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.basis;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Replacement class for {@link Node} to avoid OutOfMemory, when a big File
 * loaded
 * 
 * @author truong
 */
public class PlanProXMLNode {

	/**
	 * The replacement for DOM attribute
	 * 
	 * @param attributeName
	 *            the attribute name
	 * @param value
	 *            the attribute value
	 */
	public static record PlanProXMLNodeAttribute(String attributeName,
			String value) {

	}

	/**
	 * @param location
	 *            the file location of the document
	 * 
	 * @return the {@link PlanProXMLNode}
	 * 
	 * @throws IOException
	 *             if any IO errors occur
	 * @throws SAXException
	 *             for SAX errors
	 * @throws ParserConfigurationException
	 *             if a parser cannot be created which satisfies the requested
	 *             configuration
	 */
	public static PlanProXMLNode read(final Path location)
			throws IOException, SAXException, ParserConfigurationException {
		try (final InputStream inputStream = Files.newInputStream(location)) {
			return read(inputStream);
		}
	}

	/**
	 * @param input
	 *            the file input stream
	 * @return the root node
	 * @throws IOException
	 *             the {@link IOException}
	 * @throws SAXException
	 *             the {@link SAXException}
	 * @throws ParserConfigurationException
	 *             the {@link ParserConfigurationException}
	 */
	public static PlanProXMLNode read(final InputStream input)
			throws IOException, SAXException, ParserConfigurationException {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setFeature(
				"http://xml.org/sax/features/external-general-entities", false); //$NON-NLS-1$
		factory.setFeature(
				"http://xml.org/sax/features/external-parameter-entities", //$NON-NLS-1$
				false);
		final SAXParser parser = factory.newSAXParser();
		final PlanProXMLNode root = new PlanProXMLNode("document"); //$NON-NLS-1$
		final Deque<PlanProXMLNode> elementStack = new ArrayDeque<>();
		final StringBuilder nodeText = new StringBuilder();
		final DefaultHandler hanlder = new DefaultHandler() {
			private Locator locator;

			@Override
			public void setDocumentLocator(final Locator locator) {
				this.locator = locator;
			}

			private void addTextIfNeeded() {
				if (nodeText.length() > 0) {
					String text = nodeText.toString();
					text = text.replace("\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
					text = text.replace("\r", ""); //$NON-NLS-1$ //$NON-NLS-2$
					text = text.trim();
					if (!text.isEmpty()) {
						final PlanProXMLNode el = elementStack.peek();
						el.setTextValue(text);
					}
					nodeText.delete(0, nodeText.length());
				}
			}

			@Override
			public void characters(final char[] ch, final int start,
					final int length) throws SAXException {
				nodeText.append(ch, start, length);
			}

			@Override
			public void endElement(final String uri, final String localName,
					final String qName) {
				addTextIfNeeded();
				final PlanProXMLNode closedElement = elementStack.pop();
				if (elementStack.isEmpty()) {
					root.addChild(closedElement);
				} else {
					final PlanProXMLNode parentElement = elementStack.peek();
					parentElement.addChild(closedElement);
				}
				closedElement.setEndLineNumber(this.locator.getLineNumber());
			}

			@Override
			public void startElement(final String uri, final String localName,
					final String qName, final Attributes attributes)
					throws SAXException {
				addTextIfNeeded();
				final PlanProXMLNode element = new PlanProXMLNode(qName);
				for (int i = 0; i < attributes.getLength(); i++) {
					element.addAttribute(new PlanProXMLNodeAttribute(
							attributes.getQName(i), attributes.getValue(i)));
				}
				element.setStartLineNumber(this.locator.getLineNumber());
				elementStack.push(element);
			}
		};

		parser.parse(input, hanlder);
		return root;
	}

	String nodeName;
	String textValue;
	List<PlanProXMLNodeAttribute> attributes;
	PlanProXMLNode parent;
	List<PlanProXMLNode> children;
	Optional<Integer> startLineNumber;
	Optional<Integer> endLineNumber;
	private static Map<String, Object> addtionsObject = new HashMap<>();

	/**
	 * @return the node name
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @return the node text value
	 */
	public String getTextValue() {
		return textValue;
	}

	protected void addAttribute(
			final PlanProXMLNodeAttribute xmlAttributeToJavaObject) {
		attributes.add(xmlAttributeToJavaObject);

	}

	/**
	 * @return the node attribute
	 */
	public List<PlanProXMLNodeAttribute> getAttributes() {
		return attributes;
	}

	protected void setParent(final PlanProXMLNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the node parent
	 */
	public PlanProXMLNode getParent() {
		return parent;
	}

	protected void addChild(final PlanProXMLNode child) {
		children.add(child);
		child.setParent(this);
	}

	/**
	 * @return the children node
	 */
	public List<PlanProXMLNode> getChildren() {
		return children;
	}

	protected void setStartLineNumber(final int lineNumber) {
		startLineNumber = Optional.of(Integer.valueOf(lineNumber));
	}

	/**
	 * @return the start line number of node
	 */
	public String getStartLineNumber() {
		return startLineNumber.isPresent()
				? Integer.toString(startLineNumber.get().intValue())
				: null;
	}

	protected void setEndLineNumber(final int lineNumber) {
		endLineNumber = Optional.of(Integer.valueOf(lineNumber));
	}

	/**
	 * @return the end line number of node
	 */
	public String getEndLineNumber() {
		return endLineNumber.isPresent()
				? Integer.toString(endLineNumber.get().intValue())
				: null;
	}

	protected void setTextValue(final String value) {
		textValue = value;
	}

	protected PlanProXMLNode(final String nodeName) {
		this.nodeName = nodeName;
		this.children = new ArrayList<>();
		this.attributes = new ArrayList<>();
	}

	/**
	 * @return the root node
	 */
	public PlanProXMLNode getRootNode() {
		if (parent == null) {
			return this;
		}
		return parent.getRootNode();
	}

	/**
	 * @param key
	 *            the addition key
	 * @param obj
	 *            the addition object
	 */
	public static void addAdditionsObject(final String key, final Object obj) {
		addtionsObject.put(key, obj);
	}

	/**
	 * @param key
	 *            the addition key
	 * @return the addition object, or null if the key not exist
	 */
	public static Object getAdditionsObject(final String key) {
		return addtionsObject.get(key);
	}

	/**
	 * Find nodes through XPath
	 * 
	 * @param node
	 *            the {@link PlanProXMLNode}
	 * @param expression
	 *            the XPath expression
	 * @return the XPath result
	 */
	public static Set<PlanProXMLNode> evaluateXPath(final PlanProXMLNode node,
			final String expression) {
		if (node.getParent() != null) {
			return evaluateXPath(node.getParent(), expression);
		}
		final String[] nodePath = expression.split("/"); //$NON-NLS-1$
		final Set<PlanProXMLNode> result = new HashSet<>();
		for (final String name : nodePath) {
			if (name.trim().isEmpty()) {
				continue;
			}
			if (result.isEmpty()) {
				result.addAll(findNodesWithNames(node, name));
				continue;
			}
			final Set<PlanProXMLNode> clone = new HashSet<>();
			clone.addAll(result);
			result.clear();
			clone.forEach(n -> result.addAll(findNodesWithNames(n, name)));
		}

		return result;

	}

	private static Set<PlanProXMLNode> findNodesWithNames(
			final PlanProXMLNode node, final String nodeName) {
		if (node.nodeName.equals(nodeName)) {
			return Set.of(node);
		}
		final Set<PlanProXMLNode> result = new HashSet<>();
		node.getChildren()
				.forEach(child -> result
						.addAll(findNodesWithNames(child, nodeName)));
		return result;
	}
}
