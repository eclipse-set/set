/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.toolcontrol;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.application.Messages;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;

import jakarta.inject.Inject;

/**
 * Helper clasf for injection service
 */
@SuppressWarnings("javadoc")
public class ServiceProvider {

	@Inject
	public MApplication application;

	@Inject
	public IEventBroker broker;

	@Inject
	public EnumTranslationService enumTranslationService;

	@Inject
	@Translation
	public Messages messages;

	@Inject
	public ToolboxPartService partService;
}
