/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.unittest.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.set.core.modelservice.PlanningAccessServiceImpl;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.NoCacheService;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.util.PlanProResourceFactoryImpl;
import org.eclipse.set.toolboxmodel.PlanPro.util.ToolboxModelService;
import org.eclipse.set.toolboxmodel.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package;
import org.eclipse.set.toolboxmodel.transform.ToolboxModelServiceImpl;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for Siteplan Tests with common utilities
 * 
 * @author Stuecker
 *
 */
public class AbstractToolboxTest {
	/**
	 * ABC_01_01_Ibn-Zustand_DT.ppxml file
	 */
	public static String PHausen_ABC_01_01_PPXML = getModel(
			"ABC_01_01_Ibn-Zustand_DT.ppxml"); //$NON-NLS-1$
	/**
	 * ABC_01_01_Ibn-Zustand_DT.planpro file
	 */
	public static String PHausen_ABC_01_01_PLANPRO = getModel(
			"ABC_01_01_Ibn-Zustand_DT.planpro"); //$NON-NLS-1$
	/**
	 * PPHN_01-02_Ibn-Z. - 2. AeM_2022-05-17_13-44.ppxml file
	 */
	public static String PHausen_IBN_20220517 = getModel(
			"PPHN_01-02_Ibn-Z.-2.AeM_2022-07-26_16-36.ppxml"); //$NON-NLS-1$

	protected static PlanPro_Schnittstelle planProSchnittstelle;

	protected static String getModel(final Class<?> clazz, final String name) {
		final URL res = clazz.getClassLoader().getResource(name);

		// If the resource is inside an OSGi bundle, we need to extract it first
		if (res.toString().startsWith("bundleresource://")) { //$NON-NLS-1$
			try {
				final Path path = Files.createTempFile("set-test", name); //$NON-NLS-1$
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

	private static String getModel(final String name) {
		return getModel(AbstractToolboxTest.class, name);
	}

	/**
	 * Loads a .ppxml file into the planProSchnittstelle variable
	 * 
	 * @param filename
	 *            The path to the .ppxml
	 */
	protected void givenPlanProFile(final String filename) {
		initPackages();
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(PlanProPackage.eNS_URI,
				PlanProPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				Signalbegriffe_Ril_301Package.eNS_URI,
				Signalbegriffe_Ril_301Package.eINSTANCE);

		final XMLResource resource = (XMLResource) resourceSet
				.createResource(URI.createFileURI(filename), "ppxml"); //$NON-NLS-1$
		try {
			resource.load(resourceSet.getLoadOptions());
		} catch (final Exception e) {
			/* do nothing */
		}
		final EObject root = resource.getContents().get(0);
		if (root instanceof final org.eclipse.set.toolboxmodel.PlanPro.DocumentRoot docRoot) {
			planProSchnittstelle = docRoot.getPlanProSchnittstelle();
		} else {
			throw new IllegalArgumentException(
					"Resurce contains no PlanPro model with the requested version."); //$NON-NLS-1$
		}
	}

	/**
	 * Ensure model packages are initialized
	 */
	@SuppressWarnings("static-method") // @BeforeEach may not be static
	@BeforeEach
	protected void initPackages() {
		// Required for EMF Initialization according to EMF FAQ
		// See
		// https://wiki.eclipse.org/EMF/FAQ#How_do_I_use_EMF_in_standalone_applications_.28such_as_an_ordinary_main.29.3F
		org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage.eINSTANCE.eClass();
		org.eclipse.set.toolboxmodel.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package.eINSTANCE
				.eClass();
		org.eclipse.set.toolboxmodel.Layoutinformationen.LayoutinformationenPackage.eINSTANCE
				.eClass();

		org.eclipse.set.model.model11001.PlanPro.PlanProPackage.eINSTANCE
				.eClass();
		org.eclipse.set.model.model11001.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package.eINSTANCE
				.eClass();
		org.eclipse.set.model.model11001.Layoutinformationen.LayoutinformationenPackage.eINSTANCE
				.eClass();

		Services.setCacheService(new NoCacheService());
		Services.setPlanningAccessService(new PlanningAccessServiceImpl());

		final PlanProResourceFactoryImpl resourceFactory = new PlanProResourceFactoryImpl();
		final ToolboxModelService toolboxModelService = new ToolboxModelServiceImpl();
		resourceFactory
				.setToolboxModelServiceProvider(() -> toolboxModelService);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
				.put("ppxml", resourceFactory); //$NON-NLS-1$

	}
}
