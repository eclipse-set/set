/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.xml;

import java.util.List;

import org.eclipse.set.basis.PlanProXMLNode;

/**
 * Determines the LST object type for a given XML node
 * 
 * @author Truong
 *
 */
public class NodeObjectTypeProvider {
	/**
	 * Identity node name
	 */
	private static final String IDENTITY_ATTRIBUTE_NAME = "Identitaet"; //$NON-NLS-1$

	/**
	 * @param node
	 *            XML node to find the LST object type for
	 * @return the lst object type
	 */
	public String getObjectType(final PlanProXMLNode node) {
		if (node == null) {
			return null;
		}

		final List<PlanProXMLNode> children = node.getChildrens();
		for (final PlanProXMLNode child : children) {
			if (child.getNodeName().equals(IDENTITY_ATTRIBUTE_NAME)) {
				return node.getNodeName();
			}
		}
		return getObjectType(node.getParent());
	}

}
