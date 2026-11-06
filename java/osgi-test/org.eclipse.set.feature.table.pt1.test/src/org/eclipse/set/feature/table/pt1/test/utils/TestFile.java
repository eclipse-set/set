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
package org.eclipse.set.feature.table.pt1.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 */
public interface TestFile {
	/**
	 * @return
	 */
	public String getFullName();

	/**
	 * @return
	 */
	public String getShortName();

	/**
	 * @param clazz
	 * @return
	 */
	default String getModel(final Class<?> clazz) {
		final URL res = clazz.getClassLoader().getResource(getFullName());

		// If the resource is inside an OSGi bundle, we need to extract it first
		if (res.toString().startsWith("bundleresource://")) { //$NON-NLS-1$
			try {
				final Path path = Files.createTempFile("set-test", //$NON-NLS-1$
						getFullName());
				Files.deleteIfExists(path);
				try (InputStream stream = res.openStream()) {
					Files.copy(stream, path);
				}
				return path.toAbsolutePath().toString();
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}

		}

		try {
			return Paths.get(res.toURI()).toAbsolutePath().toString();
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
