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
package org.eclipse.set.feature.plazmodel.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * 
 */
public class Activator implements BundleActivator {
	private static BundleContext bundleContext;

	@Override
	public void start(final BundleContext context) throws Exception {
		bundleContext = context;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		bundleContext = null;
	}
}
