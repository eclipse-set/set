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
package org.eclipse.set.feature.table.pt1.test.utils;

import org.dom4j.Branch;
import org.dom4j.io.DOMReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Individual {@link DOMReader} with tag namespace identification
 */
public class CustomDOMReader extends DOMReader {
	private static record TagNameWithNS(String prefix, String uri,
			String tagName) {
	}

	/**
	 * XSL namespace uri
	 */
	public static final String XSL_NS_URI = "http://www.w3.org/1999/XSL/Transform";

	/**
	 * FO namepsace uri
	 */
	public static final String FO_NS_URI = "http://www.w3.org/1999/XSL/Format";

	/**
	 * FOX namespace uri
	 */
	public static final String FOX_NS_URI = "http://xmlgraphics.apache.org/fop/extensions";

	public CustomDOMReader() {
		super();
	}

	@Override
	protected void readElement(org.w3c.dom.Node node, Branch current) {
		String nodeName = node.getNodeName();
		TagNameWithNS nodeNSAndName = extractNS(nodeName);
		if (nodeNSAndName.prefix() == null) {
			super.readElement(node, current);
			return;
		}
		Document ownerDocument = node.getOwnerDocument();
		Node renameNode = ownerDocument.renameNode(node, nodeNSAndName.uri(),
				nodeNSAndName.tagName());
		renameNode.setPrefix(nodeNSAndName.prefix);
		setAttributesNS(renameNode);
		super.readElement(renameNode, current);

	}

	private void setAttributesNS(org.w3c.dom.Node node) {
		org.w3c.dom.NamedNodeMap attributeList = node.getAttributes();
		if (attributeList == null) {
			return;
		}
		Document ownerDocument = node.getOwnerDocument();
		for (int i = 0; i < attributeList.getLength(); i++) {
			org.w3c.dom.Node attribute = attributeList.item(i);
			TagNameWithNS ns = extractNS(attribute.getNodeName());
			if (ns.prefix() == null) {
				continue;
			}
			Node attrWithNS = ownerDocument.renameNode(attribute, ns.uri,
					ns.tagName);
			attrWithNS.setPrefix(ns.prefix());
		}
	}

	private TagNameWithNS extractNS(String nodeName) {
		String[] split = nodeName.split(":");
		if (split.length == 2) {
			return switch (split[0].toLowerCase()) {
				case "fo" -> new TagNameWithNS("fo", FO_NS_URI, split[1]);
				case "xsl" -> new TagNameWithNS("xsl", XSL_NS_URI, split[1]);
				case "fox" -> new TagNameWithNS("fox", FOX_NS_URI, split[1]);
				default -> new TagNameWithNS(null, null, nodeName);
			};
		}
		return new TagNameWithNS(null, null, nodeName);
	}
}
