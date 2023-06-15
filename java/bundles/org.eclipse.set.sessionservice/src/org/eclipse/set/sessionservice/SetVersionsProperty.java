/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.sessionservice;

import org.eclipse.set.utils.configuration.AbstractVersionsProperty;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author truong
 *
 */
@Component(service = { AbstractVersionsProperty.class })
public class SetVersionsProperty extends AbstractVersionsProperty {
	private static final String SET_VERSION = "set-versions"; //$NON-NLS-1$

	@Override
	protected JsonNode getChildNode() {
		return versionNode.get(SET_VERSION);
	}

	@Override
	protected String getNestedPropertyName() {
		return SET_VERSION;
	}
}
