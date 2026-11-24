/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.basis.xml;

import java.util.ArrayDeque;
import java.util.Deque;

import org.eclipse.set.basis.xml.PlanProXMLNode.PlanProXMLNodeAttribute;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 */
public class SAXParserHandler extends DefaultHandler {

	private final PlanProXMLNode root;
	final Deque<PlanProXMLNode> elementStack;
	private Locator locator;
	final StringBuilder nodeText;

	public SAXParserHandler(final PlanProXMLNode root) {
		super();
		this.root = root;
		elementStack = new ArrayDeque<>();
		nodeText = new StringBuilder();
	}

	@Override
	public void setDocumentLocator(final Locator locator) {
		this.locator = locator;
	}

	private void addTextIfNeeded() {
		if (!nodeText.isEmpty()) {
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
	public void characters(final char[] ch, final int start, final int length)
			throws SAXException {
		nodeText.append(ch, start, length);
	}

	@Override
	public void endElement(final String uri, final String localName,
			final String qName) throws SAXException {
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

	/**
	 * @return the {@link PlanProXMLNode} root node
	 */
	public PlanProXMLNode getDocument() {
		return root;
	}
}
