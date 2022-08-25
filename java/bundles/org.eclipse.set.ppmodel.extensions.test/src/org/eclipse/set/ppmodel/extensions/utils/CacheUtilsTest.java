/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.ppmodel.extensions.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.ppmodel.extensions.UrObjectExtensions;
import org.eclipse.set.toolboxmodel.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.toolboxmodel.Basisobjekte.Identitaet_TypeClass;
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.LST_Zustand;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;
import org.eclipse.set.toolboxmodel.Signale.SignaleFactory;
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.Weichen_und_GleissperrenFactory;
import org.eclipse.set.unittest.utils.TestData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests für {@link UrObjectExtensions}.
 * 
 * @author Schaefer
 */
class CacheUtilsTest {

	private Ur_Objekt input;
	private String result;
	private String other;

	@ParameterizedTest
	@MethodSource
	void testGetCacheKey(final TestData<Ur_Objekt, String> testData) {
		givenUrObject(testData.getInput());
		whenGettingCacheKey();
		thenExpectResult(testData.getExpected());
	}

	@SuppressWarnings("nls") // we don't translate the test data
	private static Stream<TestData<Ur_Objekt, String>> testGetCacheKey() {
		return Stream.of( //
				new TestData<>( //
						create( //
								Weichen_und_GleissperrenFactory.eINSTANCE::createW_Kr_Gsp_Element,
								PlanProPackage.eINSTANCE
										.getContainer_AttributeGroup_WKrGspElement(),
								"element-id", "container-id"),
						"multi/container-id/element-id"), //
				new TestData<>( //
						create( //
								SignaleFactory.eINSTANCE::createSignal,
								PlanProPackage.eINSTANCE
										.getContainer_AttributeGroup_Signal(),
								"123", "456"),
						"multi/456/123") //
		);
	}

	// IMPROVE: mock the elements
	@SuppressWarnings("unchecked")
	private static <T extends Ur_Objekt> T create(
			final Supplier<T> elementSupplier, final EReference feature,
			final String elementIdValue, final String containerIdValue) {
		final LST_Zustand state = PlanProFactory.eINSTANCE.createLST_Zustand();
		final Container_AttributeGroup container = PlanProFactory.eINSTANCE
				.createContainer_AttributeGroup();
		final Identitaet_TypeClass containerId = BasisobjekteFactory.eINSTANCE
				.createIdentitaet_TypeClass();
		containerId.setWert(containerIdValue);
		state.setIdentitaet(containerId);
		state.setContainer(container);
		@SuppressWarnings("rawtypes")
		final List elements = (List) container.eGet(feature);
		final T created = elementSupplier.get();
		final Identitaet_TypeClass elementId = BasisobjekteFactory.eINSTANCE
				.createIdentitaet_TypeClass();
		elementId.setWert(elementIdValue);
		created.setIdentitaet(elementId);
		elements.add(created);
		return created;
	}

	private void givenUrObject(final Ur_Objekt object) {
		this.input = object;
	}

	private void whenGettingCacheKey() {
		result = CacheUtils.getCacheKey(input);
	}

	private void thenExpectResult(final String expected) {
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@MethodSource
	void testGetCacheKeyOther(
			final TestData<Pair<Ur_Objekt, String>, String> testData) {
		givenUrObject(testData.getInput().getFirst());
		givenOther(testData.getInput().getSecond());
		whenGettingCacheKeyWithOther();
		thenExpectResult(testData.getExpected());
	}

	@SuppressWarnings("nls") // we don't translate the test data
	private static Stream<TestData<Pair<Ur_Objekt, String>, String>> testGetCacheKeyOther() {
		return Stream.of( //
				new TestData<>( //
						new Pair<>(create( //
								Weichen_und_GleissperrenFactory.eINSTANCE::createW_Kr_Gsp_Element,
								PlanProPackage.eINSTANCE
										.getContainer_AttributeGroup_WKrGspElement(),
								"element-id", "container-id"), "other"),
						"multi/container-id/element-id/other=other"), //
				new TestData<>( //
						new Pair<>(create( //
								SignaleFactory.eINSTANCE::createSignal,
								PlanProPackage.eINSTANCE
										.getContainer_AttributeGroup_Signal(),
								"123", "456"), "xxx"),
						"multi/456/123/other=xxx") //
		);
	}

	private void givenOther(final String otherInput) {
		this.other = otherInput;
	}

	private void whenGettingCacheKeyWithOther() {
		result = CacheUtils.getCacheKey(input, other);
	}
}
