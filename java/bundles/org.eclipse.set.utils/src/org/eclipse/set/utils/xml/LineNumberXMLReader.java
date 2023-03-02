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
import java.util.Stack;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
	 * key for the line number userdata
	 */
	public final static String LINE_NUMBER_KEY = "lineNumber"; //$NON-NLS-1$

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
	public static Document read(final Path location)
			throws IOException, SAXException, ParserConfigurationException {
		try (final InputStream inputStream = Files.newInputStream(location)) {
			return read(inputStream);
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
	public static Document read(final InputStream input)
			throws IOException, SAXException, ParserConfigurationException {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		final SAXParser parser = factory.newSAXParser();
		parser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, ""); //$NON-NLS-1$
		parser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); //$NON-NLS-1$
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		final DocumentBuilder docBuilder = docBuilderFactory
				.newDocumentBuilder();
		final Document doc = docBuilder.newDocument();
		final Stack<Element> elementStack = new Stack<>();
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
			public void characters(final char ch[], final int start,
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
				element.setUserData(LINE_NUMBER_KEY,
						String.valueOf(this.locator.getLineNumber()), null);
				elementStack.push(element);
			}
		};
		parser.parse(input, handler);

		return doc;
	}

	/**
	 * @param node
	 *            a node of a document read with LineNumberXMLReader
	 * @return the last line number of the node
	 */
	public static int getNodeLastLineNumber(final Node node) {
		if (node == null) {
			return -1;
		}
		final Node nextNode = node.getNextSibling();
		if (nextNode == null) {
			return -1;
		}
		if (nextNode.getNodeType() == Node.ELEMENT_NODE) {
			return getLineNumber(nextNode) - 1;
		}
		return getNodeLastLineNumber(nextNode);
	}

	/**
	 * @param node
	 *            a node of a document read with LineNumberXMLReader
	 * 
	 * @return the line number of the node
	 */
	public static int getLineNumber(final Node node) {
		final String lineNum = (String) node.getUserData(LINE_NUMBER_KEY);
		if (lineNum != null) {
			return Integer.parseInt(lineNum);
		}
		return -1;
	}
}
