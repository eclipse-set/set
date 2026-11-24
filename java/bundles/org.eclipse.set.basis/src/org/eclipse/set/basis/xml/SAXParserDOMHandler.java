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

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * 
 */
public class SAXParserDOMHandler extends SAXParserHandler {
	final TransformerHandler domBuilder;

	public SAXParserDOMHandler(final PlanProXMLNode root,
			final DOMResult domResult) {
		super(root);
		domBuilder = createDomBuilder(domResult);
	}

	private TransformerHandler createDomBuilder(final DOMResult domResult) {
		try {
			final TransformerFactory newInstance = TransformerFactory
					.newInstance();
			if (newInstance instanceof final SAXTransformerFactory saxTransformerFactory) {
				final TransformerHandler handler = saxTransformerFactory
						.newTransformerHandler();
				handler.setResult(domResult);
				return handler;
			}
			return null;
		} catch (final TransformerConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		domBuilder.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		domBuilder.endDocument();
	}

	@Override
	public void startPrefixMapping(final String prefix, final String uri)
			throws SAXException {
		domBuilder.startPrefixMapping(prefix, uri);
	}

	@Override
	public void endPrefixMapping(final String prefix) throws SAXException {
		domBuilder.endPrefixMapping(prefix);
	}

	@Override
	public void ignorableWhitespace(final char[] ch, final int start,
			final int length) throws SAXException {
		domBuilder.ignorableWhitespace(ch, start, length);
	}

	@Override
	public void processingInstruction(final String target, final String data)
			throws SAXException {
		domBuilder.processingInstruction(target, data);
	}

	@Override
	public void characters(final char[] ch, final int start, final int length)
			throws SAXException {
		domBuilder.characters(ch, start, length);
		super.characters(ch, start, length);
	}

	@Override
	public void endElement(final String uri, final String localName,
			final String qName) throws SAXException {
		domBuilder.endElement(uri, localName, qName);
		super.endElement(uri, localName, qName);
	}

	@Override
	public void startElement(final String uri, final String localName,
			final String qName, final Attributes attributes)
			throws SAXException {
		domBuilder.startElement(uri, localName, qName, attributes);
		super.startElement(uri, localName, qName, attributes);
	}

}
