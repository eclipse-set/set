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
package org.eclipse.set.feature.table.pt1.test;

import static org.mockito.Mockito.when;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.mockito.Mockito;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.test.common.annotation.InjectService;

/**
 * Abstract class for test transformation of Pt1 tables
 * 
 * @author Truong
 */
public class Pt1TableTest extends AbstractToolboxTest {

	protected IModelSession modelSession;

	/**
	 * @param eventAdmin
	 *            injection throw {@link InjectService}
	 */
	private void givenModelSession(final EventAdmin eventAdmin) {
		if (planProSchnittstelle == null) {
			modelSession = null;
		}

		modelSession = Mockito.mock(IModelSession.class);
		final ToolboxFile mockToolboxFile = Mockito.mock(ToolboxFile.class);
		when(mockToolboxFile.getRole()).thenReturn(ToolboxFileRole.SESSION);
		when(modelSession.getPlanProSchnittstelle())
				.thenReturn(planProSchnittstelle);
		when(modelSession.getToolboxFile()).thenReturn(mockToolboxFile);
		final Dictionary<String, Object> d = new Hashtable<>(2);
		d.put(EventConstants.EVENT_TOPIC, Events.MODEL_CHANGED);
		d.put(IEventBroker.DATA, modelSession);
		eventAdmin.sendEvent(new Event(Events.MODEL_CHANGED, d));
	}

	protected List<MultiContainer_AttributeGroup> getLSTContainer() {
		return List
				.of(ContainerType.FINAL, ContainerType.INITIAL,
						ContainerType.SINGLE)
				.stream()
				.map(containerType -> PlanProSchnittstelleExtensions
						.getContainer(planProSchnittstelle, containerType))
				.filter(Objects::nonNull)
				.toList();
	}

	protected void setupTransformationService(final EventAdmin eventAdmin)
			throws Exception {
		givenModelSession(eventAdmin);
	}

}