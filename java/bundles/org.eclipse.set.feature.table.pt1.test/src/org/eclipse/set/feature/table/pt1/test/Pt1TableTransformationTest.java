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

import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.table.TMFactory;
import org.eclipse.set.utils.table.TableModelTransformator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.osgi.service.component.annotations.Component;

/**
 * 
 */
@Component
public class Pt1TableTransformationTest extends Pt1TableTest {
	@ParameterizedTest
	@MethodSource("getReferenceFiles")
	void testTransformator(final String file) throws Exception {
		givenPlanProFile(file);
		setupTransformationService();
		for (final AbstractPlanPro2TableTransformationService transformationService : transformationServices) {
			for (final MultiContainer_AttributeGroup container : getLSTContainer()) {
				assertDoesNotThrow(() -> {
					final TableModelTransformator<MultiContainer_AttributeGroup> transformator = transformationService
							.createTransformator();
					if (transformator instanceof final AbstractPlanPro2TableModelTransformator tableTransformator) {
						final AbstractPlanPro2TableModelTransformator mock = Mockito
								.mock(tableTransformator);
						Mockito.when(mock.getColumn(ArgumentMatchers.anySet(),
								ArgumentMatchers.anyString()))
								.thenReturn(TablemodelFactory.eINSTANCE
										.createColumnDescriptor());
						Mockito.when(mock.transformTableContent(
								ArgumentMatchers.any(), ArgumentMatchers.any()))
								.thenCallRealMethod();
					}
					transformator.transformTableContent(container,
							new TMFactory(
									TablemodelFactory.eINSTANCE.createTable()));
				});
			}
		}
	}
}
