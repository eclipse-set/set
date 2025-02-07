/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.set.utils.emfforms.Annotations;
import org.eclipse.set.utils.emfforms.Renderers;
import org.eclipse.set.utils.emfforms.TypedSetting;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This renderer allows the usage of nested view models. Required model elements
 * will get created and linked to the domain model on demand.
 * 
 * @author Peters
 *
 */
public class ViewRenderer extends SimpleControlSWTControlSWTRenderer {

	/**
	 * Annotation to disable the rendering of the border
	 */
	public static final String BORDER_DISABLED_KEY = "viewBorderDisabled"; //$NON-NLS-1$
	/**
	 * Annotation to set control readonly
	 */
	private static final String READONLY_ACTION = "readonlyControlAction"; //$NON-NLS-1$

	static final Logger LOGGER = LoggerFactory.getLogger(ViewRenderer.class);

	/**
	 * set read only controls in View
	 * 
	 * @param view
	 *            {@link VView}
	 * @param controls
	 *            list control to set read only
	 */
	private static void setReadonlyControlinView(final VView view,
			final List<String> controls) {
		for (final VContainedElement vContainedElement : view.getChildren()) {
			if (vContainedElement instanceof VControl) {
				final VDomainModelReference domainModelReference = VControl.class
						.cast(vContainedElement)
						.getDomainModelReference();
				if (!(domainModelReference instanceof VFeaturePathDomainModelReference)) {
					return;
				}
				final VFeaturePathDomainModelReference featurepathdomainModelReference = (VFeaturePathDomainModelReference) domainModelReference;
				final boolean contains = controls.contains(
						featurepathdomainModelReference.getDomainModelEFeature()
								.getName()
								.toString());
				if (contains) {
					vContainedElement.setReadonly(true);
				}
			}
		}

	}

	/**
	 * @param vElement
	 *            the view model element to be rendered
	 * @param viewContext
	 *            the view context
	 * @param reportService
	 *            The {@link ReportService}
	 * @param emfFormsDatabinding
	 *            The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider
	 *            The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider
	 *            The {@link VTViewTemplateProvider}
	 */
	@Inject
	public ViewRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider);
		LOGGER.debug("Render {}", vElement); //$NON-NLS-1$
	}

	private TypedSetting<EObject> getTypedSetting() {
		return Renderers.getTypedSetting(EObject.class, getVElement(),
				getViewModelContext());
	}

	@Override
	protected Binding[] createBindings(final Control control)
			throws DatabindingFailedException {
		return null;
	}

	@Override
	protected Control createSWTControl(final Composite parent) {
		// only renderer view, when view visible
		if (!getVElement().isVisible()) {
			return null;
		}
		// area for sub-elements
		final boolean borderDisabled = Boolean.parseBoolean(Annotations
				.getViewModelValue(getVElement(), BORDER_DISABLED_KEY));
		Composite subElementComposite;
		if (borderDisabled) {
			subElementComposite = new Composite(parent, SWT.NONE);
		} else {
			subElementComposite = new Group(parent, SWT.SHADOW_ETCHED_IN);
		}
		subElementComposite.setLayout(new GridLayout(1, false));

		final String readonlycontrols = Annotations
				.getViewModelValue(getVElement(), READONLY_ACTION);
		final TypedSetting<EObject> typedSetting = getTypedSetting();
		final EStructuralFeature feature = typedSetting.getEStructuralFeature();
		final Optional<EObject> value = typedSetting.getValue();

		final EObject domainElement;
		if (value.isPresent()) {
			domainElement = value.get();
		} else {
			LOGGER.debug("Value of feature {} not present, creating...", //$NON-NLS-1$
					feature.getName());
			final EClass eClass = (EClass) feature.getEType();
			domainElement = eClass.getEPackage()
					.getEFactoryInstance()
					.create(eClass);
			final EObject eParent = typedSetting.getEObject();
			eParent.eSet(feature, domainElement);
		}

		// create the view model context
		final VElement view = ViewProviderHelper.getView(domainElement, null);
		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(view, domainElement);

		if (readonlycontrols != null && view instanceof VView) {
			final String[] listdisablecontrols = readonlycontrols.split(","); //$NON-NLS-1$
			setReadonlyControlinView((VView) view,
					Arrays.asList(listdisablecontrols));
		}
		// renderer call
		try {
			ECPSWTViewRenderer.INSTANCE.render(subElementComposite,
					viewModelContext);
		} catch (final ECPRendererException e) {
			throw new RuntimeException(e);
		}
		return subElementComposite;
	}

	@Override
	protected String getUnsetText() {
		return null;
	}
}
