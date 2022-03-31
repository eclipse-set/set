/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.sessionservice;

import java.nio.file.Path;
import java.util.Optional;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.InitializationData;
import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.validation.session.AbstractSessionService;
import org.osgi.service.component.annotations.Component;

/**
 * Implementation of {@link SessionService} for the SET product.
 * 
 * @author Schaefer
 */
@Component(service = SessionService.class)
public class SetSessionService extends AbstractSessionService {

	@Override
	public void createViewGroups() {
		createSetViewGroups();
	}

	@Override
	public Optional<String> getDefaultPartID(final IModelSession modelSession) {
		return Optional.empty();
	}

	@Override
	public IModelSession initModelSession(
			final InitializationData initializationData) {
		return initSetSession((ProjectInitializationData) initializationData);
	}

	@Override
	public IModelSession loadModelSession(final Path path) {
		return loadSetSession(path);
	}
}
