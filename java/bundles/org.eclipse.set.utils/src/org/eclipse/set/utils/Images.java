/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common functions for images.
 * 
 * @author Schaefer
 */
public class Images {

	private static final Logger LOGGER = LoggerFactory.getLogger(Images.class);

	protected static ImageDescriptor createDescriptor(final Bundle bundle,
			final String filename) {
		try {
			final URL url = FileLocator.find(bundle, new Path(filename), null);
			if (url == null) {
				throw new IllegalArgumentException(filename);
			}
			return org.eclipse.jface.resource.ImageDescriptor
					.createFromURL(url);
		} catch (final Exception e) {
			LOGGER.error(e.toString(), e);
			throw e;
		}
	}
}
