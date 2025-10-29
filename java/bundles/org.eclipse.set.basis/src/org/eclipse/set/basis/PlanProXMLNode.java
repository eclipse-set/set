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

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 */
public class PlanProXMLNode {

	public static PlanProXMLNode read(final InputStream input)
			throws IOException, SAXException, ParserConfigurationException {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setFeature(
				"http://xml.org/sax/features/external-general-entities", false); //$NON-NLS-1$
		factory.setFeature(
				"http://xml.org/sax/features/external-parameter-entities", //$NON-NLS-1$
				false);
		final SAXParser parser = factory.newSAXParser();
		final PlanProXMLNode root = new PlanProXMLNode("document");
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
					text = text.replace("\n", "");
					text = text.replace("\r", "");
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
					element.addAttribute(new XMLAttributeToJavaObject(
							attributes.getQName(i), attributes.getValue(i)));
				}
				element.setStartLineNumber(this.locator.getLineNumber());
				elementStack.push(element);
			}
		};

		parser.parse(input, hanlder);
		return root;
	}

	protected void setStartLineNumber(final int lineNumber) {
		startLineNumber = Optional.of(Integer.valueOf(lineNumber));

	}

	protected void addAttribute(
			final XMLAttributeToJavaObject xmlAttributeToJavaObject) {
		attributes.add(xmlAttributeToJavaObject);

	}

	protected void setEndLineNumber(final int lineNumber) {
		endLineNumber = Optional.of(Integer.valueOf(lineNumber));
	}

	public static record XMLAttributeToJavaObject(String attributeName,
			String value) {

	}

	String nodeName;
	String textValue;
	List<XMLAttributeToJavaObject> attributes;
	PlanProXMLNode parent;
	List<PlanProXMLNode> children;
	Optional<Integer> startLineNumber;
	Optional<Integer> endLineNumber;
	private static Map<String, Object> addtionsObject = new HashMap<>();

	private static Set<PlanProXMLNode> allNodes = new HashSet<>();

	public PlanProXMLNode() {
		this.nodeName = null;
		this.parent = null;
		this.children = new ArrayList<>();
		this.attributes = new ArrayList<>();
	}

	public PlanProXMLNode(final String nodeName) {
		this.nodeName = nodeName;
		this.children = new ArrayList<>();
		this.attributes = new ArrayList<>();
	}

	public void addChild(final PlanProXMLNode child) {
		children.add(child);
		child.setParent(this);
	}

	public void setTextValue(final String value) {
		textValue = value;
	}

	public void setParent(final PlanProXMLNode parent) {
		this.parent = parent;
	}

	public List<PlanProXMLNode> getChildrens() {
		return children;
	}

	public PlanProXMLNode getParent() {
		return parent;
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getStartLineNumber() {
		return startLineNumber.isPresent()
				? Integer.toString(startLineNumber.get().intValue())
				: null;
	}

	public String getEndLineNumber() {
		return endLineNumber.isPresent()
				? Integer.toString(endLineNumber.get().intValue())
				: null;
	}

	public String getTextValue() {
		return textValue;
	}

	public PlanProXMLNode getRootNode() {
		if (parent == null) {
			return this;
		}
		return parent.getRootNode();
	}

	// public Set<PlanProXMLNode> getAllNodes() {
	// if (!allNodes.isEmpty()) {
	// return allNodes;
	// }
	// final Set<PlanProXMLNode> result = new HashSet<>();
	// traverserNode(this, result);
	//
	// return result;
	// }
	//
	// private void traverserNode(final PlanProXMLNode node,
	// final Set<PlanProXMLNode> result) {
	// if (result.add(node)) {
	// node.getChildrens().forEach(child -> traverserNode(child, result));
	// }
	//
	// if (node.getParent() != null) {
	// traverserNode(node.getParent(), result);
	// }
	// }

	public void addAdditionsObject(final String key, final Object obj) {
		addtionsObject.put(key, obj);
	}

	public Object getAdditionsObject(final String key) {
		return addtionsObject.get(key);
	}

	public List<XMLAttributeToJavaObject> getAttributes() {
		return attributes;
	}

	public Set<PlanProXMLNode> evaluateXPath(final String expression) {
		if (parent != null) {
			return parent.evaluateXPath(expression);
		}
		final String[] nodePath = expression.split("/");
		final Set<PlanProXMLNode> result = new HashSet<>();
		for (final String name : nodePath) {
			if (name.trim().isEmpty()) {
				continue;
			}
			if (result.isEmpty()) {
				result.addAll(findNodesWithNames(name));
				continue;
			}
			final Set<PlanProXMLNode> clone = new HashSet<>();
			clone.addAll(result);
			result.clear();
			clone.forEach(node -> result.addAll(node.findNodesWithNames(name)));
		}

		return result;

	}

	private Set<PlanProXMLNode> findNodesWithNames(final String nodeName) {
		if (this.nodeName.equals(nodeName)) {
			return Set.of(this);
		}
		final Set<PlanProXMLNode> result = new HashSet<>();
		children.forEach(
				child -> result.addAll(child.findNodesWithNames(nodeName)));
		return result;
	}
}
