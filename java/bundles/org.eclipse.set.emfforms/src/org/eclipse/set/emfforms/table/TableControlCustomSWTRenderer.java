/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.table;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import jakarta.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.table.DefaultTableViewerCompositeBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.utils.emfforms.Annotations;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Renders a table without a detail panel and the following additional features:
 * 
 * <ul>
 * <li>Additional modifications to the table are possible via a {@link Consumer
 * viewer consumer} registered with the key <b>viewerConsumer</b> and provided
 * by {@link ToolboxViewModelService#get(String)}.</li>
 * <li>The following properties can be set by a view annotation with the key
 * <b>properties</b>:
 * <ul>
 * <li><i>hideHeading</i>: hide the table heading</li>
 * </ul>
 * </li>
 * </ul>
 * <p>
 * 
 * @author Schaefer
 */
public class TableControlCustomSWTRenderer extends TableControlSWTRenderer {

	private static final String PROPERTIES_KEY = "properties"; //$NON-NLS-1$
	private static final String PROPERTIES_SPLIT_PATTERN = " *, *"; //$NON-NLS-1$
	private static final String PROPERTY_HIDE_HEADING = "hideHeading"; //$NON-NLS-1$
	private static final String VIEWER_CONSUMER_KEY = "viewerConsumer"; //$NON-NLS-1$

	private static boolean isSet(final String properties, final String value) {
		if (properties == null) {
			return false;
		}
		final List<String> propertyList = Arrays
				.asList(properties.split(PROPERTIES_SPLIT_PATTERN));
		return propertyList.contains(value);
	}

	private final boolean hideHeading;

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
	 * @param localizationService
	 *            The {@link EMFFormsLocalizationService}
	 */
	@Inject
	public TableControlCustomSWTRenderer(final VTableControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabindingEMF emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final ImageRegistryService imageRegistryService,
			final EMFFormsEditSupport emfFormsEditSupport,
			final EMFFormsLocalizationService localizationService) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider,
				imageRegistryService, emfFormsEditSupport, localizationService);
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();

		// table consumer
		final String consumerKey = Annotations.getViewModelValue(vElement,
				VIEWER_CONSUMER_KEY);
		@SuppressWarnings("unchecked")
		final Consumer<ColumnViewer> consumer = (Consumer<ColumnViewer>) toolboxViewModelService
				.get(consumerKey);
		viewerConsumer = consumer;

		// properties
		final String properties = Annotations.getViewModelValue(vElement,
				PROPERTIES_KEY);
		hideHeading = isSet(properties, PROPERTY_HIDE_HEADING);
	}

	@Override
	protected TableViewerCompositeBuilder createTableViewerCompositeBuilder() {
		if (hideHeading) {
			return new DefaultTableViewerCompositeBuilder() {

				@Override
				public Optional<Label> getTitleLabel() {
					return Optional.empty();
				}

				@Override
				protected Label createTitleLabel(
						final Composite parentComposite,
						final Color background) {
					return null;
				}

				@Override
				protected Label createValidationLabel(
						final Composite topComposite) {
					return null;
				}
			};
		}
		return super.createTableViewerCompositeBuilder();
	}

	@Override
	protected Control renderControl(final SWTGridCell gridCell,
			final Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control control = super.renderControl(gridCell, parent);

		// apply viewer consumer, if available
		if (viewerConsumer != null) {
			viewerConsumer.accept(getTableViewer());
		}

		return control;
	}
}
