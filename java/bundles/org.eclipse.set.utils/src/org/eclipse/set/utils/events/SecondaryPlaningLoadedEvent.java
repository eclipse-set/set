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
package org.eclipse.set.utils.events;

import org.eclipse.set.basis.constants.Events;

/**
 * 
 */
public class SecondaryPlaningLoadedEvent implements ToolboxEvent {

	@Override
	public String getTopic() {
		return Events.SECONDARY_MODEL_LOADED;
	}

}
