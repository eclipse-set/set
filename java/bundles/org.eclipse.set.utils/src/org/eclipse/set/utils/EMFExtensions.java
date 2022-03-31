/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Provides extension methods for working with EMF Models
 * 
 * @author Stuecker
 *
 */
public class EMFExtensions {

	/**
	 * Registers a file extension to be used with a specific EMF model factory
	 * 
	 * @param extension
	 *            The file extension to register, without a leading dot (for
	 *            example: "xml")
	 * @param factory
	 *            The factory to register
	 */
	public static void registerExtensionFactory(final String extension,
			final Object factory) {
		final Map<String, Object> extensionToFactoryMap = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();

		// If not present yet, register
		if (!extensionToFactoryMap.containsKey(factory)) {
			extensionToFactoryMap.put(extension, factory);
			return;
		}

		// If it is already present, validate that both factories are of the
		// same type
		final Object oldFactory = extensionToFactoryMap.get(extension);
		Assert.isTrue(oldFactory.getClass().equals(factory.getClass()));
	}

	/**
	 * Saves a EMF object to a file.
	 * 
	 * @param <T>
	 *            Type to save
	 * 
	 * @param path
	 *            Path to the file
	 * @param value
	 *            the object to save
	 * @throws IOException
	 *             if the file could not be saved
	 */
	public static <T extends EObject> void saveToFile(final T value,
			final Path path) throws IOException {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(
				URI.createFileURI(path.toAbsolutePath().toString()));
		resource.getContents().add(value);
		resource.save(null);
	}
}
