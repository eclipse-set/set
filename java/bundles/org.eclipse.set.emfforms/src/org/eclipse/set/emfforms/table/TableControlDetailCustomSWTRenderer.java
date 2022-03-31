/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.table;

import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlDetailPanelRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.utils.emfforms.Annotations;

/**
 * Renders a table with a detail panel and the following additional features:
 * 
 * <li>additional modifications to the table are possible via a {@link Consumer
 * viewer consumer} registered with the key "viewerConsumer" and provided by
 * {@link ToolboxViewModelService#get(String)}.</li>
 * <p>
 * 
 * @author Schaefer
 */
public class TableControlDetailCustomSWTRenderer
		extends TableControlDetailPanelRenderer {

	private static final String VIEWER_CONSUMER_KEY = "viewerConsumer"; //$NON-NLS-1$

	private final Consumer<ColumnViewer> viewerConsumer;

	/**
	 * @param vElement
	 *            the view model element to be rendered
	 * @param viewContext
	 *            the view context
	 * @param emfFormsDatabinding
	 *            The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider
	 *            The {@link EMFFormsLabelProvider}
	 * @param reportService
	 *            The {@link ReportService}
	 * @param vtViewTemplateProvider
	 *            The {@link VTViewTemplateProvider}
	 * @param imageRegistryService
	 *            The {@link ImageRegistryService}
	 * @param emfFormsEditSupport
	 *            The {@link EMFFormsEditSupport}
	 */
	@Inject
	public TableControlDetailCustomSWTRenderer(final VTableControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabindingEMF emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final ImageRegistryService imageRegistryService,
			final EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider,
				imageRegistryService, emfFormsEditSupport);
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();
		final String consumerKey = Annotations.getViewModelValue(vElement,
				VIEWER_CONSUMER_KEY);
		@SuppressWarnings("unchecked")
		final Consumer<ColumnViewer> consumer = (Consumer<ColumnViewer>) toolboxViewModelService
				.get(consumerKey);
		viewerConsumer = consumer;
	}

	@Override
	protected int getDetailPanelHeightHint() {
		return 600;
	}

	@Override
	protected Control renderControl(final SWTGridCell gridCell,
			final Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control control = super.renderControl(gridCell, parent);

		// apply table consumer, if available
		if (viewerConsumer != null) {
			viewerConsumer.accept(getTableViewer());
		}

		return control;
	}
}
