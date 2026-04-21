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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import org.eclipse.osgi.service.localization.BundleLocalization;
import org.eclipse.osgi.storage.BundleLocalizationImpl;
import org.eclipse.set.application.graph.TopologicalGraphServiceImpl;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslation;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.eclipse.set.utils.graph.AsSplitTopGraph;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.osgi.service.event.EventAdmin;

/**
 * 
 */
public class Pt1TableTransformTest extends AbstractToolboxTest {
	private static EnumTranslation mockEnumTranlsation(final Object value) {
		return new EnumTranslation() {

			@Override
			public String getAlternative() {
				return value.toString();
			}

			@Override
			public String getKeyBasis() {
				return value.toString();
			}

			@Override
			public String getPresentation() {
				return value.toString();
			}

			@Override
			public String getSorting() {
				return "";
			}
		};
	}

	/**
	 * @return the siteplan reference files
	 */
	protected static Stream<Arguments> getSiteplanReferenceFiles() {
		return Stream.of(Arguments.of(PPHN_1_10_0_3_20220517_PLANPRO, "pphn"));
	}

	private TopologicalGraphServiceImpl topologicalGraphService;
	List<PlanPro2TableTransformationService> transformationServices;

	protected void mockServiceVariable(
			final PlanPro2TableTransformationService service) {

		final Messages messages = new Messages() {
			@Override
			protected void bindBundleLocalization(
					final BundleLocalization bundleLocalization)
					throws IllegalArgumentException, IllegalAccessException {
				final BundleLocalization localization = new BundleLocalizationImpl();
				super.setupLocalization(localization);
			}
		};

		final EnumTranslationService mockEnumTranslation = Mockito
				.mock(EnumTranslationService.class);
		Mockito.when(mockEnumTranslation.translate(ArgumentMatchers.any(),
				ArgumentMatchers.any())).thenAnswer(invocation -> {
					return mockEnumTranlsation(invocation.getArguments()[1]);
				});

		final EventAdmin eventAdmin = Mockito.mock(EventAdmin.class);

	}

	protected void setupTopGraphService() throws Exception {
		topologicalGraphService = new TopologicalGraphServiceImpl();
		final Method declaredMethod = topologicalGraphService.getClass()
				.getDeclaredMethod("addContainerToGraph",
						WeightedPseudograph.class,
						MultiContainer_AttributeGroup.class);
		declaredMethod.setAccessible(true);
		final WeightedPseudograph<Object, Edge> weightedPseudograph = new WeightedPseudograph<>(
				AsSplitTopGraph.Edge.class);

		final List<MultiContainer_AttributeGroup> containers = List
				.of(ContainerType.FINAL, ContainerType.INITIAL,
						ContainerType.SINGLE)
				.stream()
				.map(containerType -> PlanProSchnittstelleExtensions
						.getContainer(planProSchnittstelle, containerType))
				.filter(Objects::nonNull)
				.toList();
		for (final MultiContainer_AttributeGroup contanier : containers) {
			declaredMethod.invoke(topologicalGraphService, weightedPseudograph,
					contanier);
		}
	}

	protected void setupTransformationService() {
		transformationServices = new ArrayList<>();
		final ServiceLoader<PlanPro2TableTransformationService> loader = ServiceLoader
				.load(PlanPro2TableTransformationService.class);
		for (final PlanPro2TableTransformationService impl : loader) {

			transformationServices.add(impl);
		}
	}
}
