/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.Container_AttributeGroup;

/**
 * The data within a container have changed.
 * 
 * @author Schaefer
 */
public class ContainerDataChanged extends DataEvent {

	private static final String SUBTOPIC = "/container/changed"; //$NON-NLS-1$

	private final Container_AttributeGroup container;

	/**
	 * @param container
	 *            the changed container
	 */
	public ContainerDataChanged(final Container_AttributeGroup container) {
		this.container = container;
	}

	/**
	 * @return the changed container
	 */
	public Container_AttributeGroup getContainer() {
		return container;
	}

	@Override
	public String getTopic() {
		return getBaseTopic() + SUBTOPIC;
	}
}
