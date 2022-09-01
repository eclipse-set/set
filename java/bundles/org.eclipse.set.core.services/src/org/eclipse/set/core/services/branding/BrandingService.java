/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.branding;

import java.util.Optional;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Provide logos and other branding information.
 * 
 * @author Schaefer
 */
public interface BrandingService {

	/**
	 * Names that differ between various products.
	 */
	public interface Names {

		/**
		 * @return the application title (shown in the about box)
		 */
		String getApplicationTitle();

		/**
		 * @return the tool name used to identify the tool within the PlanPro
		 *         file
		 */
		String getToolName();
	}

	/**
	 * @return an optional logo for the action part
	 */
	Optional<ImageDescriptor> getActionLogo();

	/**
	 * @return names that differ between various products
	 */
	Names getNames();

	/**
	 * @return image descriptor for splash image
	 */
	ImageDescriptor getSplashImage();

	/**
	 * @return an optional logo for the vendor
	 */
	Optional<ImageDescriptor> getVendorLogo();
}
