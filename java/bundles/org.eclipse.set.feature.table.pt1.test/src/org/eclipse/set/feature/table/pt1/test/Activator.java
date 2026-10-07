package org.eclipse.set.feature.table.pt1.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

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
