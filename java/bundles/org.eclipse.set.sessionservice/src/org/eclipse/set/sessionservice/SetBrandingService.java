/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.sessionservice;

import java.util.Optional;

import jakarta.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.set.core.services.branding.BrandingService;
import org.eclipse.set.core.services.branding.BrandingService.Names;

/**
 * SET implementation of {@link BrandingService}.
 * 
 * @author Schaefer
 */
public class SetBrandingService implements BrandingService, Names {
	@Inject
	@Translation
	private Messages messages;

	@Override
	public Optional<ImageDescriptor> getActionLogo() {
		return Optional.empty();
	}

	@Override
	public String getApplicationTitle() {
		return messages.SetBrandingService_ApplicationTitle;
	}

	@Override
	public Names getNames() {
		return this;
	}

	@Override
	public Optional<ImageDescriptor> getSplashImage() {
		return Optional.empty();
	}

	@Override
	public String getToolName() {
		return messages.SetBrandingService_ToolName;
	}

	@Override
	public Optional<ImageDescriptor> getVendorLogo() {
		return Optional.empty();
	}
}
