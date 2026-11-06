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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Branch;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.util.NodeComparator;

/**
 * Custom {@link NodeComparator} with ignore the order of the node
 * 
 * @author truong
 */
public class CustomNodeComparator extends NodeComparator {
	/**
	 * Constructor
	 */
	public CustomNodeComparator() {
		super();
	}

	@Override
	public int compare(final Element n1, final Element n2) {
		int answer = compare(n1.getQName(), n2.getQName());

		if (answer == 0) {
			// lets compare attributes
			final int c1 = n1.attributeCount();
			final int c2 = n2.attributeCount();
			answer = c1 - c2;

			if (answer == 0) {
				for (int i = 0; i < c1; i++) {
					final Attribute a1 = n1.attribute(i);
					final Attribute a2 = findRelevantAttribute(n2,
							a1.getQName());
					if (a2 == null) {
						return -1;
					}
					answer = compare(a1, a2);

					if (answer != 0) {
						return answer;
					}
				}

				answer = compareContent(n1, n2);
			}
		}

		return answer;
	}

	@Override
	public int compareContent(final Branch b1, final Branch b2) {
		final int c1 = b1.nodeCount();
		final int c2 = b2.nodeCount();
		int answer = c1 - c2;
		final Set<Node> alreadyCompare = new HashSet<>();
		if (answer == 0) {
			for (int i = 0; i < c1; i++) {
				final Node n1 = b1.node(i);
				final Node n2 = b2.node(i);
				answer = compare(n1, n2);
				if (answer != 0) {
					final boolean isSame = b2.content()
							.stream()
							.filter(n -> n != n2 && !alreadyCompare.contains(n))
							.anyMatch(node -> {
								if (compare(n1, node) == 0) {
									alreadyCompare.add(node);
									return true;
								}
								return false;
							});
					if (isSame) {
						answer = 0;
					}
				}

				if (answer != 0) {
					break;
				}
			}
		}

		return answer;
	}

	protected Attribute findRelevantAttribute(final Element e,
			final QName qName) {
		final Iterator<Attribute> attributeIterator = e.attributeIterator();
		while (attributeIterator.hasNext()) {
			final Attribute next = attributeIterator.next();
			if (compare(next.getQName(), qName) == 0) {
				return next;
			}
		}
		return null;
	}
}
