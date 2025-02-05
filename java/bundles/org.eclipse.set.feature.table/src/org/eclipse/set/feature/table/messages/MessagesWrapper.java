/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.messages;

import jakarta.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.feature.table.messages.internal.MessagesContextFunction;

/**
 * Helper class to publish the table i8n messages. An instance of this class
 * will be created by the {@link MessagesContextFunction} and registered to the
 * osgi service registry.
 * 
 * @author Rumpf / Schaefer
 */
public final class MessagesWrapper {

	@Inject
	@Translation
	private ToolboxTableName toolboxTableName;

	@Inject
	MApplication application;

	@Inject
	@Translation
	Messages messages;

	/**
	 * constructor.
	 */
	public MessagesWrapper() {
		super();
	}

	/**
	 * @return the application context
	 */
	public IEclipseContext getContext() {
		return application.getContext();
	}

	/**
	 * @return the Messages object
	 */
	public Messages getMessages() {
		return messages;
	}

	/**
	 * @return the toolbox table name
	 */
	public ToolboxTableName getToolboxTableName() {
		return toolboxTableName;
	}
}
