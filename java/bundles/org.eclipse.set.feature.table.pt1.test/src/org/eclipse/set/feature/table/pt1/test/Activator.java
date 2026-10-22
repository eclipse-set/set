package org.eclipse.set.feature.table.pt1.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * 
 */
public class Activator implements BundleActivator {
	public static final String TEST_RESOURCE_DIR = "res/";
	private static BundleContext bundleContext;

	/**
	 * @return the bundle context
	 */
	public static BundleContext getContext() {
		return bundleContext;
	}

	public String TEMPLATE_DIR = "../org.eclipse.set.feature/rootdir/data/export";

	public String TEMPLATE_LOCAL_DIR = "./data/export";

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
		final Path source = Path.of(getTemplateDir());
		final Path target = Path.of(getTemplateLocalDir());
		if (!Files.exists(Path.of(getTemplateLocalDir()))) {
			Files.createDirectories(Path.of(getTemplateLocalDir()));
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

	protected String getTemplateDir() {
		return TEMPLATE_DIR;
	}

	protected String getTemplateLocalDir() {
		return TEMPLATE_LOCAL_DIR;
	}
}
