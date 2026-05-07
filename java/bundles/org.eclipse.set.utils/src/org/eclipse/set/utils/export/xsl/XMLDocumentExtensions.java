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
package org.eclipse.set.utils.export.xsl;

import static org.eclipse.set.utils.export.xsl.XSLConstant.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_NAME;

import java.util.Arrays;
import java.util.Optional;

import org.eclipse.set.basis.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The extensions of XML document
 * 
 * @author truong
 */
@SuppressWarnings("nls")
public class XMLDocumentExtensions {

	/**
	 * Helper class for define xml element attribute
	 * 
	 * @param name
	 *            the attribute name
	 * @param value
	 *            the attribute value
	 */
	public record XMLAttribute(String name, String value) {
		/**
		 * @param name
		 *            the attribute name
		 * @param doubleValue
		 *            the attribute value
		 */
		public XMLAttribute(final String name, final double doubleValue) {
			this(name, String.valueOf(doubleValue) + "mm");
		}
	}

	/**
	 * Create xml element with attribute "name"
	 * 
	 * @param doc
	 *            the Document
	 * @param tag
	 *            the element tag
	 * @param name
	 *            the attribute name value
	 * @return the element
	 */
	public static Element createXMLElementWithAttrName(final Document doc,
			final String tag, final String name) {
		final Element element = createElementWithNS(doc, tag);
		element.setAttribute("name", name);
		return element;
	}

	/**
	 * Create xml elemnt with some attribute
	 * 
	 * @param doc
	 *            the Document
	 * @param tag
	 *            the element tag
	 * @param attrName
	 *            the name of attribute
	 * @param attrValue
	 *            the value of attribute
	 * @return the element
	 */
	public static Element createXMLElementWithAttr(final Document doc,
			final String tag, final String attrName, final String attrValue) {
		return createXMLElementWithAttr(doc, tag,
				new XMLAttribute(attrName, attrValue));
	}

	/**
	 * Create xml elemnt with some attribute
	 * 
	 * @param doc
	 *            the Document
	 * @param tag
	 *            the element tag
	 * @param attributes
	 *            the list of {@link XMLAttribute}
	 * @return the element
	 */
	public static Element createXMLElementWithAttr(final Document doc,
			final String tag, final XMLAttribute... attributes) {
		final Element element = createElementWithNS(doc, tag);
		Arrays.stream(attributes)
				.forEach(attribute -> setAttributeWithNS(element,
						attribute.name, attribute.value));
		return element;
	}

	/**
	 * Find and set text content for the element, which have attribute name
	 * 
	 * @param doc
	 *            the document
	 * @param tagName
	 *            the element tag
	 * @param elementName
	 *            the value of attribute "name"
	 * @param value
	 *            the text content value
	 * @throws NullPointerException
	 *             throw exception, when element not found
	 */
	public static void setXSLElementValue(final Document doc,
			final String tagName, final String elementName, final double value)
			throws NullPointerException {
		final String stringValue = String.valueOf(value) + "mm";
		setXSLElementValue(doc, tagName, elementName, stringValue);
	}

	/**
	 * Find and set text content for the element, which have attribute name
	 * 
	 * @param doc
	 *            the document
	 * @param tagName
	 *            the element tag
	 * @param elementName
	 *            the value of attribute "name"
	 * @param value
	 *            the text content value
	 * @throws NullPointerException
	 *             throw exception, when element not found
	 */
	public static void setXSLElementValue(final Document doc,
			final String tagName, final String elementName, final String value)
			throws NullPointerException {
		final Optional<Element> element = AbstractTransformTableHeader
				.findNodebyTagName(doc, tagName, ATTR_NAME, elementName);
		if (element.isEmpty()) {
			throw new NullPointerException(String.format(
					"Can't find element with tag: %s and name attribute: %s",
					tagName, elementName));
		}
		element.get().setTextContent(value);
	}

	public static Element createElementWithNS(final Document doc,
			final String tagName) {
		final Pair<String, String> nsAndTagName = extractNS(tagName);
		if (nsAndTagName.getFirst() != null) {
			return doc.createElementNS(nsAndTagName.getFirst(),
					nsAndTagName.getSecond());
		}
		return doc.createElement(nsAndTagName.getSecond());
	}

	public static void setAttributeWithNS(final Element element,
			final String attributeName, final String value) {
		final Pair<String, String> nsAndAttrName = extractNS(attributeName);
		if (nsAndAttrName.getFirst() != null) {
			element.setAttributeNS(nsAndAttrName.getFirst(),
					nsAndAttrName.getSecond(), value);
		} else {
			element.setAttribute(nsAndAttrName.getSecond(), value);
		}
	}

	private static Pair<String, String> extractNS(final String name) {
		final String[] prefix = name.split(":");
		if (prefix.length == 2) {
			return switch (prefix[0]) {
				case "xsl" -> new Pair<>(XSL_NS_URI, prefix[1]);
				case "fo" -> new Pair<>(FO_NS_URI, prefix[1]);
				case "fox" -> new Pair<>(FOX_NS_URI, prefix[1]);
				default -> new Pair<>(null, name);
			};
		}
		return new Pair<>(null, name);
	}
}
