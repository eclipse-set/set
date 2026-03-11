/**
 * Copyright (c) 2026 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.set.basis.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationFactory;
import org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration;

public class TemporaryIntegrationFileResoutce extends PlanProFileResource {
	public TemporaryIntegrationFileResoutce(final URI uri) {
		this(uri, null);
	}

	/**
	 * Creates an instance of the resource. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param uri
	 *            the URI of the new resource.
	 * @param xmlHelper
	 *            the {@link XMLHelper}
	 * @generated
	 */
	public TemporaryIntegrationFileResoutce(final URI uri,
			final XMLHelper xmlHelper) {
		super(uri, xmlHelper);
	}

	@Override
	public void doLoad(final InputStream inputStream, final Map<?, ?> options)
			throws IOException {
		try {
			super.doLoad(inputStream, defaultDeleteOptions);
		} finally {
			if (getContents().isEmpty()) {
				return;
			}

			if (getContents()
					.get(0) instanceof final TemporaryIntegration tmpInt) {
				final ToolboxTemporaryIntegration toolboxTemporaryInt = TemporaryintegrationFactory.eINSTANCE
						.createToolboxTemporaryIntegration();
				final PlanPro_Schnittstelle primaryPlanning = tmpInt
						.getPrimaryPlanning();
				ToolboxIDResolver.resolveIDReferences(primaryPlanning);
				toolboxTemporaryInt.setPrimaryPlanning(primaryPlanning);

				final PlanPro_Schnittstelle secondaryPlanning = tmpInt
						.getSecondaryPlanning();
				ToolboxIDResolver.resolveIDReferences(secondaryPlanning);
				toolboxTemporaryInt.setSecondaryPlanning(secondaryPlanning);

				final PlanPro_Schnittstelle compositePlanning = tmpInt
						.getCompositePlanning();
				ToolboxIDResolver.resolveIDReferences(compositePlanning);
				toolboxTemporaryInt.setCompositePlanning(compositePlanning);

				// Copy other attributes
				toolboxTemporaryInt.setPrimaryPlanningFilename(
						tmpInt.getPrimaryPlanningFilename());
				toolboxTemporaryInt.setPrimaryPlanningWasValid(
						tmpInt.isPrimaryPlanningWasValid());
				toolboxTemporaryInt.setSecondaryPlanningFilename(
						tmpInt.getSecondaryPlanningFilename());
				toolboxTemporaryInt.setSecondaryPlanningWasValid(
						tmpInt.isSecondaryPlanningWasValid());
				toolboxTemporaryInt.setIntegrationDirectory(
						tmpInt.getIntegrationDirectory());
				toolboxTemporaryInt.setComparisonInitialState(
						EcoreUtil.copy(tmpInt.getComparisonInitialState()));
				toolboxTemporaryInt.setComparisonFinalState(
						EcoreUtil.copy(tmpInt.getComparisonFinalState()));

				EcoreUtil.replace(tmpInt, toolboxTemporaryInt);
			}
		}
	}

	@Override
	public void doSave(final OutputStream outputStream, final Map<?, ?> options)
			throws IOException {
		if (getContents().isEmpty()) {
			super.doSave(outputStream, options);
		}

		if (getContents()
				.getFirst() instanceof final ToolboxTemporaryIntegration toolboxInt) {
			final TemporaryIntegration tmpInt = TemporaryintegrationFactory.eINSTANCE
					.createTemporaryIntegration();
			tmpInt.setPrimaryPlanning(toolboxInt.getPrimaryPlanning());
			tmpInt.setSecondaryPlanning(toolboxInt.getSecondaryPlanning());
			tmpInt.setCompositePlanning(toolboxInt.getCompositePlanning());

			// Copy other attributes
			tmpInt.setPrimaryPlanningFilename(
					toolboxInt.getPrimaryPlanningFilename());
			tmpInt.setPrimaryPlanningWasValid(
					toolboxInt.isPrimaryPlanningWasValid());
			tmpInt.setSecondaryPlanningFilename(
					toolboxInt.getSecondaryPlanningFilename());
			tmpInt.setSecondaryPlanningWasValid(
					toolboxInt.isSecondaryPlanningWasValid());
			tmpInt.setIntegrationDirectory(
					toolboxInt.getIntegrationDirectory());
			tmpInt.setComparisonInitialState(
					EcoreUtil.copy(toolboxInt.getComparisonInitialState()));
			tmpInt.setComparisonFinalState(
					EcoreUtil.copy(toolboxInt.getComparisonFinalState()));

			// Swap, save and restore
			EcoreUtil.replace(toolboxInt, tmpInt);
			super.doSave(outputStream, options);
			EcoreUtil.replace(tmpInt, toolboxInt);
		}
	}
}
