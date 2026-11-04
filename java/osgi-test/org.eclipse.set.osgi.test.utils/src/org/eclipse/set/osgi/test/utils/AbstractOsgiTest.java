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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.junit.jupiter.api.BeforeEach;
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

	@BeforeEach
	protected void beforeEach() throws Exception {
		givenPlanProFile(getTestFile().getTestFilePath());
	}

	protected abstract TestFile getTestFile();

	@SuppressWarnings("static-method")
	protected ToolboxFile mockToolboxFile() {
		final ToolboxFile mock = Mockito.mock(ToolboxFile.class);
		when(mock.getRole()).thenReturn(ToolboxFileRole.SESSION);
		return mock;

	}

	/**
	 * @param eventAdmin
	 *            injection throw {@link InjectService}
	 */
	protected void setupModelSession(final EventAdmin eventAdmin) {
		if (planProSchnittstelle == null) {
			modelSession = null;
		}

		final ToolboxFile mockToolboxFile = mockToolboxFile();
		modelSession = Mockito.mock(IModelSession.class);
		Mockito.when(modelSession.getPlanProSchnittstelle())
				.thenReturn(planProSchnittstelle);
		Mockito.when(modelSession.getToolboxFile()).thenReturn(mockToolboxFile);
		final Map<String, Object> d = new HashMap<>(2);
		d.put(EventConstants.EVENT_TOPIC, Events.MODEL_CHANGED);
		d.put(IEventBroker.DATA, modelSession);
		eventAdmin.sendEvent(new Event(Events.MODEL_CHANGED, d));
	}
}