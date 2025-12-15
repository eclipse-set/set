/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.unittest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.FeatureNotFoundException;
import org.eclipse.emf.ecore.xmi.IllegalValueException;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.set.basis.files.PlanProFileResource;
import org.eclipse.set.core.fileservice.PlanProXMLHelper;
import org.eclipse.set.core.fileservice.ToolboxIDResolver;
import org.eclipse.set.core.modelservice.PlanningAccessServiceImpl;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.NoCacheService;
import org.eclipse.set.core.version.PlanProVersionServiceImpl;
import org.eclipse.set.model.planpro.PlanPro.DocumentRoot;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.util.PlanProResourceFactoryImpl;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for Siteplan Tests with common utilities
 * 
 * @author Stuecker
 *
 */
public class AbstractToolboxTest {

	/**
	 * PPHN_1.10.0.1_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg2.planpro file
	 */
	public static String PPHN_1_10_0_1_20220517_PLANPRO = getModel(
			"PPHN_1.10.0.1_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg2.planpro"); //$NON-NLS-1$
	/**
	 * PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.planpro file
	 */
	public static String PPHN_1_10_0_3_20220517_PLANPRO = getModel(
			"PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.planpro"); //$NON-NLS-1$

	/**
	 * PPHN_1.10.0.1_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg2.ppxml file
	 */
	public static String PPHN_1_10_0_1_20220517_PPXML = getModel(
			"PPHN_1.10.0.1_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg2.ppxml"); //$NON-NLS-1$

	public static String NB_INFO_TEST = getModel(
			"NB_Test_Info__2025-10-16_16-35.planpro");

	private static final String UNZIP_DIR = "res/toolbox"; //$NON-NLS-1$
	private static final String CONTENT_MODEL = "content"; //$NON-NLS-1$
	private static final String LAYOUT_MODEL = "layout"; //$NON-NLS-1$
	private static final String PLANPRO_ZIPPED_EXTENSION = "planpro"; //$NON-NLS-1$
	private static final String PLANPRO_PLAIN_EXTENSION = "ppxml"; //$NON-NLS-1$

	protected PlanPro_Schnittstelle planProSchnittstelle;
	private ResourceSet resourceSet;

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
	 * @throws IOException
	 */
	protected void givenPlanProFile(final String filename) throws IOException {
		initPackages();
		resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry()
				.put(PlanProPackage.eNS_URI, PlanProPackage.eINSTANCE);
		resourceSet.getPackageRegistry()
				.put(Signalbegriffe_Ril_301Package.eNS_URI,
						Signalbegriffe_Ril_301Package.eINSTANCE);
		if (isZippedPlanProFile(filename)) {
			loadZippedPlanProFile(filename);
		} else {
			final URI testFileURI = URI.createFileURI(filename);
			final XMLResource resource = loadResource(testFileURI);
			if (!resource.getContents().isEmpty() && resource.getContents()
					.get(0) instanceof final DocumentRoot docRoot) {
				planProSchnittstelle = docRoot.getPlanProSchnittstelle();
				ToolboxIDResolver.resolveIDReferences(planProSchnittstelle);
			} else {
				throw new IllegalArgumentException(
						"Resource contains no PlanPro model with the requested version."); //$NON-NLS-1$
			}
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
		org.eclipse.set.model.planpro.PlanPro.PlanProPackage.eINSTANCE.eClass();
		org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package.eINSTANCE
				.eClass();
		org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenPackage.eINSTANCE
				.eClass();

		Services.setCacheService(new NoCacheService());
		Services.setPlanningAccessService(new PlanningAccessServiceImpl());

		final PlanProResourceFactoryImpl resourceFactory = new PlanProResourceFactoryImpl();

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
				.put("ppxml", resourceFactory); //$NON-NLS-1$
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
				.put("xml", //$NON-NLS-1$
						resourceFactory);

	}

	private void loadZippedPlanProFile(final String filePath)
			throws IOException {
		final List<XMLResource> listResource = new ArrayList<>();
		unzip(filePath);
		final URI contentURI = URI
				.createFileURI(getModelPath(CONTENT_MODEL).toString());
		listResource.add(loadResource(contentURI));

		final Path layoutModelPath = getModelPath(LAYOUT_MODEL);
		if (Files.exists(layoutModelPath)) {
			final URI layout = URI.createFileURI(layoutModelPath.toString());
			listResource.add(loadResource(layout));
		}

		listResource.forEach(resource -> {
			final EObject root = resource.getContents().get(0);
			if (root instanceof final DocumentRoot model) {
				planProSchnittstelle = model.getPlanProSchnittstelle();
				ToolboxIDResolver.resolveIDReferences(planProSchnittstelle);
			} else {
				throw new IllegalArgumentException(
						"Resource contains no PlanPro model with the requested version."); //$NON-NLS-1$
			}
		});
	}

	private static void unzip(final String filePath) throws IOException {
		final Path unzipDir = Paths.get(UNZIP_DIR);
		Files.createDirectories(unzipDir);
		// unzip the archive
		final byte[] buffer = new byte[1024];
		try (final ZipInputStream zipIn = new ZipInputStream(
				new FileInputStream(new File(filePath)))) {
			for (ZipEntry zipEntry = zipIn
					.getNextEntry(); zipEntry != null; zipEntry = zipIn
							.getNextEntry()) {
				final File newFile = newFile(unzipDir, zipEntry);
				if (zipEntry.getName().endsWith("/")) { //$NON-NLS-1$
					newFile.mkdirs();
				} else {
					// Create parent directories if not present
					newFile.getParentFile().mkdirs();

					// Extract the file
					try (final FileOutputStream fos = new FileOutputStream(
							newFile)) {
						int len;
						while ((len = zipIn.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
					}
				}
			}
			zipIn.closeEntry();
		}
	}

	private static File newFile(final Path dir, final ZipEntry entry)
			throws IOException {
		final File file = new File(dir.toString(), entry.getName());

		// test file position against dir
		final String filePath = file.getCanonicalPath();
		final String dirPath = dir.toFile().getCanonicalPath() + File.separator;
		if (filePath.startsWith(dirPath)) {
			return file;
		}
		throw new IOException(
				String.format("%s is outside of %s", filePath, dirPath)); //$NON-NLS-1$
	}

	private static boolean isZippedPlanProFile(final String filePath) {
		final List<String> split = Arrays.asList(filePath.split("\\.")); //$NON-NLS-1$
		if (split.size() > 1) {
			return split.get(split.size() - 1).equals(PLANPRO_ZIPPED_EXTENSION);
		}
		return false;
	}

	private static Path getModelPath(final String modelName) {
		return Paths.get(UNZIP_DIR, modelName + ".xml"); //$NON-NLS-1$
	}

	private XMLResource loadResource(final URI resourceURI) throws IOException {
		final XMLResource resource = (XMLResource) resourceSet
				.createResource(resourceURI);
		final PlanProFileResource planproResource = new PlanProFileResource(
				resourceURI, new PlanProXMLHelper(resource,
						new PlanProVersionServiceImpl()));
		try {
			planproResource.load(null);
		} catch (final Exception e) {
			if (!(e.getCause() instanceof FeatureNotFoundException
					|| e.getCause() instanceof IllegalValueException)) {
				throw e;
			}
		}
		return planproResource;
	}

}
