/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.utils;

import org.w3c.dom.Node;

/**
 * Determines an attribute name for an XML node
 * 
 * @author Stuecker
 *
 */
public class ValidationAttributeNameProvider {
	private final static String WERT_NODE = "Wert"; //$NON-NLS-1$
	private final static String TEXT_NODE = "#text"; //$NON-NLS-1$

	/**
	 * @param node
	 *            The node to find the attribute name
	 * @return The closest XML attribute name (or null)
	 */
	public String getAttributeName(final Node node) {
		if (node == null) {
			return null;
		}
		if (node.getNodeName().equals(WERT_NODE)
				|| node.getNodeName().equals(TEXT_NODE)) {
			return getAttributeName(node.getParentNode());
		}

		return node.getNodeName();
	}
}
