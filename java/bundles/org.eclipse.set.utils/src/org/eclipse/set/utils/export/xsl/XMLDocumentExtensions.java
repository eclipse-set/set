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

import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_NAME;

import java.util.Arrays;
import java.util.Optional;

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
		final Element element = doc.createElement(tag);
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
	 * @param attributes
	 *            the list of {@link XMLAttribute}
	 * @return the element
	 */
	public static Element createXMLElementWithAttr(final Document doc,
			final String tag, final XMLAttribute... attributes) {
		final Element element = doc.createElement(tag);
		Arrays.stream(attributes)
				.forEach(attribute -> element.setAttribute(attribute.name,
						attribute.value));
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
}
