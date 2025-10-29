/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.set.basis.PlanProXMLNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Reads an XML Document adding line numbers to Elements.
 * 
 * @author Schaefer
 */
public class LineNumberXMLReader {

	/**
	 * key for the start line number userdata
	 */
	public static final String START_LINE_NUMBER_KEY = "startLineNumber"; //$NON-NLS-1$

	/**
	 * key for the end line number userdata
	 */
	public static final String END_LINE_NUMBER_KEY = "endLineNumber"; //$NON-NLS-1$

	/**
	 * @param location
	 *            the file location of the document
	 * 
	 * @return the XML document
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
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param input
	 *            the input stream
	 * 
	 * @return the XML document
	 * 
	 * @throws IOException
	 *             if any IO errors occur
	 * @throws SAXException
	 *             for SAX errors
	 * @throws ParserConfigurationException
	 *             if a parser cannot be created which satisfies the requested
	 *             configuration
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
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		final DocumentBuilder docBuilder = docBuilderFactory
				.newDocumentBuilder();
		final Document doc = docBuilder.newDocument();
		final Deque<Element> elementStack = new ArrayDeque<>();
		final StringBuilder nodeText = new StringBuilder();
		final DefaultHandler handler = new DefaultHandler() {
			private Locator locator;

			private void addTextIfNeeded() {
				if (nodeText.length() > 0) {
					final Element el = elementStack.peek();
					final Node textNode = doc
							.createTextNode(nodeText.toString());
					el.appendChild(textNode);
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
				final Element closedElement = elementStack.pop();
				if (elementStack.isEmpty()) {
					doc.appendChild(closedElement);
				} else {
					final Element parentElement = elementStack.peek();
					parentElement.appendChild(closedElement);
				}
				closedElement.setUserData(END_LINE_NUMBER_KEY,
						String.valueOf(this.locator.getLineNumber()), null);
			}

			@Override
			public void setDocumentLocator(final Locator locator) {
				this.locator = locator;
			}

			@Override
			public void startElement(final String uri, final String localName,
					final String qName, final Attributes attributes)
					throws SAXException {
				addTextIfNeeded();
				final Element element = doc.createElement(qName);
				for (int i = 0; i < attributes.getLength(); i++) {
					element.setAttribute(attributes.getQName(i),
							attributes.getValue(i));
				}
				element.setUserData(START_LINE_NUMBER_KEY,
						String.valueOf(this.locator.getLineNumber()), null);
				elementStack.push(element);
			}
		};
		// parser.parse(input, handler);
		final PlanProXMLNode test = PlanProXMLNode.read(input);
		return test;
	}

	/**
	 * @param node
	 *            a node of a document read with LineNumberXMLReader
	 * @return the last line number of the node
	 */
	public static int getNodeLastLineNumber(final PlanProXMLNode node) {
		final String lineNum = String.valueOf(node.getEndLineNumber());
		if (lineNum != null) {
			return Integer.parseInt(lineNum);
		}
		return -1;
	}

	/**
	 * @param node
	 *            a node of a document read with LineNumberXMLReader
	 * 
	 * @return the line number of the node
	 */
	public static int getStartLineNumber(final PlanProXMLNode node) {
		final String lineNum = String.valueOf(node.getStartLineNumber());
		if (lineNum != null) {
			return Integer.parseInt(lineNum);
		}
		return -1;
	}
}
