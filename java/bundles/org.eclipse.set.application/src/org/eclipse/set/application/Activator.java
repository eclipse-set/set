/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Schaefer
 *
 */
public class Activator implements BundleActivator {

	private static BundleContext bundleContext;

	/**
	 * @return the bundle context
	 */
	public static BundleContext getContext() {
		return bundleContext;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		bundleContext = context;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		bundleContext = null;
	}
}
