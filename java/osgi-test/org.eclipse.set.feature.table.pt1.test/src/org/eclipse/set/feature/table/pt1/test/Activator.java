package org.eclipse.set.feature.table.pt1.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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
		copyResource();
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		bundleContext = null;
	}

	protected void copyResource() throws Exception {
		final Path source = Path.of(TEMPLATE_DIR);
		final Path target = Path.of(TEMPLATE_LOCAL_DIR);
		if (!Files.exists(Path.of(TEMPLATE_LOCAL_DIR))) {
			Files.createDirectories(Path.of(TEMPLATE_LOCAL_DIR));
		} else {
			try (Stream<Path> stream = Files.walk(target)) {
				stream.filter(path -> !path.equals(target))
						.sorted(Comparator.reverseOrder())
						.forEach(path -> {
							try {
								Files.delete(path);
							} catch (final IOException e) {
								throw new RuntimeException(e);
							}
						});
			}
		}
		try (Stream<Path> stream = Files.walk(source)) {
			stream.forEach(path -> {
				try {
					final Path relative = source.relativize(path);
					final Path targetPath = target.resolve(relative);
					if (Files.isDirectory(path)) {
						Files.createDirectories(targetPath);
					} else {
						Files.copy(path, targetPath,
								StandardCopyOption.REPLACE_EXISTING,
								StandardCopyOption.COPY_ATTRIBUTES);
					}
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}

}
