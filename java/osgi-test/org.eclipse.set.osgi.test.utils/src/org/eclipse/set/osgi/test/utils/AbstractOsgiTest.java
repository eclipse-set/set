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
package org.eclipse.set.osgi.test.utils;

import static org.mockito.Mockito.when;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.mockito.Mockito;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.test.common.annotation.InjectService;

/**
 * 
 */
public abstract class AbstractOsgiTest extends AbstractToolboxTest {
	protected IModelSession modelSession;

	/**
	 * @param eventAdmin
	 *            injection throw {@link InjectService}
	 */
	protected void setupModelSession(final EventAdmin eventAdmin) {
		if (planProSchnittstelle == null) {
			modelSession = null;
		}

		final ToolboxFile mockToolboxFile = Mockito.mock(ToolboxFile.class);
		when(mockToolboxFile.getRole()).thenReturn(ToolboxFileRole.SESSION);
		modelSession = Mockito.mock(IModelSession.class);
		Mockito.when(modelSession.getPlanProSchnittstelle())
				.thenReturn(planProSchnittstelle);
		Mockito.when(modelSession.getToolboxFile()).thenReturn(mockToolboxFile);
		final Dictionary<String, Object> d = new Hashtable<>(2);
		d.put(EventConstants.EVENT_TOPIC, Events.MODEL_CHANGED);
		d.put(IEventBroker.DATA, modelSession);
		eventAdmin.sendEvent(new Event(Events.MODEL_CHANGED, d));
	}
}
