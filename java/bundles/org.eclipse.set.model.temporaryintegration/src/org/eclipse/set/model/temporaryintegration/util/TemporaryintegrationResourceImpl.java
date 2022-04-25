/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationFactory;
import org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration;
import org.eclipse.set.toolboxmodel.PlanPro.util.IDReference;
import org.eclipse.set.toolboxmodel.PlanPro.util.ToolboxModelService;
import org.eclipse.set.toolboxmodel.transform.ToolboxModelServiceImpl;

/**
 * <!-- begin-user-doc --> The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.temporaryintegration.util.TemporaryintegrationResourceFactoryImpl
 * @generated NOT
 */
public class TemporaryintegrationResourceImpl extends XMLResourceImpl {
	private final ToolboxModelService compositeToolboxModelService;
	private final ToolboxModelService primaryToolboxModelService;
	private final ToolboxModelService secondaryToolboxModelService;

	/**
	 * Creates an instance of the resource. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param uri
	 *            the URI of the new resource.
	 * @generated NOT
	 */
	public TemporaryintegrationResourceImpl(final URI uri) {
		super(uri);
		getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
				Boolean.TRUE);
		getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
				Boolean.TRUE);

		getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION,
				Boolean.TRUE);

		getDefaultLoadOptions().put(
				XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		getDefaultSaveOptions().put(
				XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER,
				Boolean.TRUE);

		compositeToolboxModelService = new ToolboxModelServiceImpl();
		primaryToolboxModelService = new ToolboxModelServiceImpl();
		secondaryToolboxModelService = new ToolboxModelServiceImpl();
	}

	@Override
	public void doLoad(final InputStream inputStream, final Map<?, ?> options)
			throws IOException {
		try {
			super.doLoad(inputStream, options);
		} finally {
			// After loading, transform the loaded contents (if any)
			if (!getContents().isEmpty()) {
				final EObject content = getContents().get(0);
				if (content instanceof final TemporaryIntegration tmpInt) {
					final ToolboxTemporaryIntegration toolboxInt = TemporaryintegrationFactory.eINSTANCE
							.createToolboxTemporaryIntegration();
					// Transform Planpro Models
					toolboxInt.setPrimaryPlanning(primaryToolboxModelService
							.loadPlanProModel(tmpInt.getPrimaryPlanning()));
					toolboxInt.setSecondaryPlanning(secondaryToolboxModelService
							.loadPlanProModel(tmpInt.getSecondaryPlanning()));
					toolboxInt.setCompositePlanning(compositeToolboxModelService
							.loadPlanProModel(tmpInt.getCompositePlanning()));

					// Add ID references to model
					toolboxInt.getPrimaryPlanningIDReferences()
							.addAll(primaryToolboxModelService
									.getInvalidIDReferences());
					toolboxInt.getSecondaryPlanningIDReferences()
							.addAll(secondaryToolboxModelService
									.getInvalidIDReferences());
					toolboxInt.getCompositePlanningIDReferences()
							.addAll(compositeToolboxModelService
									.getInvalidIDReferences());

					// Copy other attributes
					toolboxInt.setPrimaryPlanningFilename(
							tmpInt.getPrimaryPlanningFilename());
					toolboxInt.setPrimaryPlanningWasValid(
							tmpInt.isPrimaryPlanningWasValid());
					toolboxInt.setSecondaryPlanningFilename(
							tmpInt.getSecondaryPlanningFilename());
					toolboxInt.setSecondaryPlanningWasValid(
							tmpInt.isSecondaryPlanningWasValid());
					toolboxInt.setIntegrationDirectory(
							tmpInt.getIntegrationDirectory());
					toolboxInt.setComparisonInitialState(
							EcoreUtil.copy(tmpInt.getComparisonInitialState()));
					toolboxInt.setComparisonFinalState(
							EcoreUtil.copy(tmpInt.getComparisonFinalState()));

					EcoreUtil.replace(tmpInt, toolboxInt);
				}
			}
		}
	}

	@Override
	public void doSave(final OutputStream outputStream, final Map<?, ?> options)
			throws IOException {
		if (!getContents().isEmpty()) {
			final EObject content = getContents().get(0);
			if (content instanceof final ToolboxTemporaryIntegration toolboxInt) {
				final TemporaryIntegration tmpInt = TemporaryintegrationFactory.eINSTANCE
						.createTemporaryIntegration();
				// Transform Planpro Models
				tmpInt.setPrimaryPlanning(primaryToolboxModelService
						.savePlanProModel(toolboxInt.getPrimaryPlanning()));
				tmpInt.setSecondaryPlanning(secondaryToolboxModelService
						.savePlanProModel(toolboxInt.getSecondaryPlanning()));
				tmpInt.setCompositePlanning(compositeToolboxModelService
						.savePlanProModel(toolboxInt.getCompositePlanning()));

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
				return;
			}
		}

		// Save
		super.doSave(outputStream, options);
	}

	public List<IDReference> getCompositeInvalidIDReferences() {
		final EObject content = getContents().get(0);
		if (content instanceof final ToolboxTemporaryIntegration tmpInt) {
			return tmpInt.getCompositePlanningIDReferences();
		}
		return List.of();
	}

	public List<IDReference> getPrimaryInvalidIDReferences() {
		final EObject content = getContents().get(0);
		if (content instanceof final ToolboxTemporaryIntegration tmpInt) {
			return tmpInt.getPrimaryPlanningIDReferences();
		}
		return List.of();
	}

	public List<IDReference> getSecondaryInvalidIDReferences() {
		final EObject content = getContents().get(0);
		if (content instanceof final ToolboxTemporaryIntegration tmpInt) {
			return tmpInt.getSecondaryPlanningIDReferences();
		}
		return List.of();
	}

	public List<IDReference> getTransformCompositeInvalidIDReferences() {
		return compositeToolboxModelService.getInvalidIDReferences();
	}

	public List<IDReference> getTransformPrimaryInvalidIDReferences() {
		return primaryToolboxModelService.getInvalidIDReferences();
	}

	public List<IDReference> getTransformSecondaryInvalidIDReferences() {
		return secondaryToolboxModelService.getInvalidIDReferences();
	}
} // TemporaryintegrationResourceImpl
