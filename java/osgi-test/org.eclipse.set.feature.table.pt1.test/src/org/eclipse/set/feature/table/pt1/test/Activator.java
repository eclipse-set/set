package org.eclipse.set.feature.table.pt1.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.test.utils.PtTable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * 
 */
public class Activator implements BundleActivator {
	private static BundleContext bundleContext;

	/**
	 * Validate, that table transform services complete loaded
	 * 
	 * @param transformationServices
	 *            the load table transform services
	 */
	public static void assertInjectedAllTransformationService(
			final Collection<PlanPro2TableTransformationService> transformationServices) {
		assertEquals(PtTable.expectedTransformationServices.size(),
				transformationServices.size());
		final List<String> actualServicesName = transformationServices.stream()
				.map(service -> service.getClass().getName())
				.map(className -> className
						.substring(className.lastIndexOf(".") + 1))
				.toList();
		assertTrue(PtTable.expectedTransformationServices
				.containsAll(actualServicesName));
		assertTrue(actualServicesName
				.containsAll(PtTable.expectedTransformationServices));
	}

	/**
	 * @return the bundle context
	 */
	public static BundleContext getContext() {
		return bundleContext;
	}

	protected String TEMPLATE_DIR = "../data/export";

	protected String TEMPLATE_LOCAL_DIR = "./data/export";

	@Override
	public void start(final BundleContext context) throws Exception {
		bundleContext = context;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		bundleContext = null;
	}

}
