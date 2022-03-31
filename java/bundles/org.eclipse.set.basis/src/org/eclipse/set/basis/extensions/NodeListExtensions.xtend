/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import java.util.Iterator
import org.w3c.dom.Node
import org.w3c.dom.NodeList

/** 
 * Extensions for {@link NodeList}.
 * 
 * @author Schaefer
 */
class NodeListExtensions {

	/**
	 * @param nodeList this node list
	 * 
	 * @return an iterable over this node list
	 */
	static def Iterable<Node> iterable(NodeList nodeList) {
		return new Iterable<Node>() {
			var current = 0

			override iterator() {
				return new Iterator<Node>() {
					override hasNext() {
						return current < nodeList.length
					}

					override next() {
						return nodeList.item(current++)
					}
				}
			}
		}
	}
}
