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
package org.eclipse.set.unittest.utils;

/**
 * 
 */
public abstract class AbstractOSGiServiceTest extends AbstractToolboxTest {
	// protected IModelSession modelSession;
	//
	// /**
	// * @param eventAdmin
	// * injection throw {@link InjectService}
	// */
	// protected void setupModelSession(final EventAdmin eventAdmin) {
	// if (planProSchnittstelle == null) {
	// modelSession = null;
	// }
	//
	// final ToolboxFile mockToolboxFile = Mockito.mock(ToolboxFile.class);
	// Mockito.when(mockToolboxFile.getRole())
	// .thenReturn(ToolboxFileRole.SESSION);
	// modelSession = Mockito.mock(IModelSession.class);
	// Mockito.when(modelSession.getPlanProSchnittstelle())
	// .thenReturn(planProSchnittstelle);
	// Mockito.when(modelSession.getToolboxFile()).thenReturn(mockToolboxFile);
	// final Map<String, Object> d = new HashMap<>();
	// d.put(EventConstants.EVENT_TOPIC, Events.MODEL_CHANGED);
	// d.put(IEventBroker.DATA, modelSession);
	// eventAdmin.sendEvent(new Event(Events.MODEL_CHANGED, d));
	// }
	//
	// protected abstract EventAdmin getEventAdmin();
}
