/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.w3c.dom.Element
import org.w3c.dom.Node

import static extension org.eclipse.set.basis.extensions.NodeListExtensions.*

/** 
 * Extensions for {@link Node}.
 * 
 * @author Schaefer
 */
class NodeExtensions {

	/**
	 * @param node this node
	 * @param tagName the wanted tag name
	 * 
	 * @return whether this node is or has a child element with the wanted tag name
	 */
	static def boolean hasChildElementWithTag(Node node, String tagName) {
		if (node instanceof Element) {
			if (node.tagName == tagName) {
				return true
			}
		}
		return node.childNodes.iterable.exists[hasChildElementWithTag(tagName)]
	}

	/**
	 * @param node this node
	 * @param tagName the wanted tag name
	 * 
	 * @return the first child element with the wanted tag name or <code>null</code>
	 */
	static def Element getFirstChildWithTagName(Node node, String tagName) {
		return node.childNodes.iterable.filter(Element).findFirst [
			it.tagName == tagName
		]
	}
}
