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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Comparator;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.set.basis.ToolboxProperties;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * Test Pt1 Table transformation
 * 
 * @author Truong
 */
class Pt1TableTransformationTest extends Pt1TableTest {

	/**
	 * @return the reference files
	 */
	protected static Stream<Arguments> getReferenceFiles() {
		return Stream.of(Arguments.of(PPHN_1_10_0_3_20220517_PLANPRO, "pphn"),
				Arguments.of(SINGLE_STATE_PLAN, "zustandpphn"));
	}

	@ParameterizedTest
	@MethodSource("getReferenceFiles")
	void testTransformator(final String file) throws Exception {
		givenPlanProFile(file);
		setupTransformationService();
		System.setProperty(ToolboxProperties.DEVELOPMENT_MODE,
				Boolean.FALSE.toString());
		try (final MockedStatic<Services> mockServices = Mockito
				.mockStatic(Services.class);) {
			setupMockServices(mockServices);
			for (final AbstractPlanPro2TableTransformationService transformationService : transformationServices) {
				for (final MultiContainer_AttributeGroup container : getLSTContainer()) {
					// Test transformation table
					final Table transformedTable = assertDoesNotThrow(
							() -> transformationService.transform(container),
							() -> "Error by transformation Table: "
									+ transformationService.getClass()
											.getPackageName());
					final TableType defaultTableType = MultiContainer_AttributeGroupExtensions
							.getContainerType(container)
							.getDefaultTableType();
					// Test sorting table
					assertDoesNotThrow(() -> {
						final Comparator<RowGroup> comparator = transformationService
								.getRowGroupComparator(defaultTableType);
						ECollections.sort(transformedTable.getTablecontent()
								.getRowgroups(), comparator);
					}, () -> "Error by sort table: "
							+ transformationService.getClass()
									.getPackageName());
				}
			}
		}
	}
}
