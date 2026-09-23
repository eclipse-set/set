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
package org.eclipse.set.feature.table.pt1.test;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.test.common.annotation.InjectService;

/**
 * Abstract class for test transformation of Pt1 tables
 * 
 * @author Truong
 */
public abstract class Pt1TableTest extends AbstractToolboxTest {
	protected static final String TEST_RESOURCE_DIR = "res/";
	protected List<String> expectedTransformationServices;
	protected IModelSession modelSession;

	protected String TEMPLATE_DIR = "../org.eclipse.set.feature/rootdir/data/export";

	protected String TEMPLATE_LOCAL_DIR = "./data/export";

	protected boolean assertInjectedAllTransformationService(
			final List<PlanPro2TableTransformationService> transformationServices) {
		givenExpectedTransformationServices();
		return expectedTransformationServices.size() == transformationServices
				.size()
				&& expectedTransformationServices.stream()
						.allMatch(expect -> transformationServices.stream()
								.anyMatch(actual -> actual.getClass()
										.getName()
										.endsWith(expect)));
	}

	@BeforeAll
	protected void beforeAll() throws Exception {
		copyResource();
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

	protected List<MultiContainer_AttributeGroup> getLSTContainer() {
		return List
				.of(ContainerType.FINAL, ContainerType.INITIAL,
						ContainerType.SINGLE)
				.stream()
				.map(containerType -> PlanProSchnittstelleExtensions
						.getContainer(planProSchnittstelle, containerType))
				.filter(Objects::nonNull)
				.toList();
	}

	protected void givenExpectedTransformationServices() {
		expectedTransformationServices = List.of( //
				"SsbbTransformationService", //
				"SsitTransformationService", //
				"SskaTransformationService", //
				"SskfTransformationService", //
				"SskgTransformationService", //
				"SskoTransformationService", //
				"SskpTransformationService", //
				"SskpDmTransformationService", //
				"SsksTransformationService", //
				"SsktTransformationService", //
				"SskwTransformationService", //
				"SskxTransformationService", //
				"SskzTransformationService", //
				"SslaTransformationService", //
				"SslbTransformationService", //
				"SsldTransformationService", //
				"SslfTransformationService", //
				"SsliTransformationService", //
				"SslnTransformationService", //
				"SslrTransformationService", //
				"SslsTransformationService", //
				"SslwTransformationService", //
				"SslzTransformationService", //
				"SsvuTransformationService", //
				"SszaTransformationService", //
				"SszsTransformationService", //
				"SszwTransformationService", //
				"SxxxTransformationService" //

		);
	}

	/**
	 * @param eventAdmin
	 *            injection throw {@link InjectService}
	 */
	protected void setupModelSession(final EventAdmin eventAdmin) {
		if (planProSchnittstelle == null) {
			modelSession = null;
		}

		final ToolboxFile mockToolboxFile = Mockito.mock(ToolboxFile.class);
		when(mockToolboxFile.getRole()).thenReturn(ToolboxFileRole.SESSION);
		modelSession = Mockito.mock(IModelSession.class);
		Mockito.when(modelSession.getPlanProSchnittstelle())
				.thenReturn(planProSchnittstelle);
		Mockito.when(modelSession.getToolboxFile()).thenReturn(mockToolboxFile);
		final Dictionary<String, Object> d = new Hashtable<>(2);
		d.put(EventConstants.EVENT_TOPIC, Events.MODEL_CHANGED);
		d.put(IEventBroker.DATA, modelSession);
		eventAdmin.sendEvent(new Event(Events.MODEL_CHANGED, d));
	}
}
