/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.utils;

import org.eclipse.set.model.validationreport.ObjectState;
import org.w3c.dom.Node;

/**
 * Determines the scope of an object based on an XML node
 * 
 * @author Stuecker
 *
 */
public class ValidationObjectStateProvider {
	private static final String NODE_ZUSTAND_START = "LST_Zustand_Start"; //$NON-NLS-1$
	private static final String NODE_ZUSTAND_ZIEL = "LST_Zustand_Ziel"; //$NON-NLS-1$
	private static final String NODE_ZUSTAND_INFO = "LST_Zustand"; //$NON-NLS-1$
	private static final String NODE_LAYOUTINFORMATION_ROOT = "nsLayoutinformationen:PlanPro_Layoutinfo"; //$NON-NLS-1$

	/**
	 * @param node
	 *            The node to find the state for
	 * @return The object state or null
	 */
	public ObjectState getObjectState(final Node node) {
		if (node == null) {
			return null;
		}

		final String nodeName = node.getNodeName();
		if (nodeName == null) {
			return getObjectState(node.getParentNode());
		}
		if (nodeName.equals(NODE_ZUSTAND_START)) {
			return ObjectState.INITIAL;
		}
		if (nodeName.equals(NODE_ZUSTAND_ZIEL)) {
			return ObjectState.FINAL;
		}
		if (nodeName.equals(NODE_ZUSTAND_INFO)) {
			return ObjectState.INFO;
		}
		if (nodeName.equals(NODE_LAYOUTINFORMATION_ROOT)) {
			return ObjectState.LAYOUT;
		}

		return getObjectState(node.getParentNode());
	}
}
