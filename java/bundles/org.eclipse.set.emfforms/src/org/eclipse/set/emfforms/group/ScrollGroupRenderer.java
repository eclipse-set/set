/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.group;

import java.util.Collection;
import java.util.function.Function;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Renders content within a scrolled composite.
 * 
 * @author Schaefer
 */
public class ScrollGroupRenderer extends ContainerSWTRenderer<VGroup> {

	private static Control createScrolledComposite(final Composite parent,
			final Function<Composite, Composite> createContent) {
		final ScrolledComposite scrolledComposite = new ScrolledComposite(
				parent, SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(scrolledComposite);
		final Composite content = createContent.apply(scrolledComposite);
		GridLayoutFactory.fillDefaults().applyTo(content);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(content);
		content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite.setContent(content);
		return scrolledComposite;
	}

	/**
	 * @param vElement
	 *            the view model element to be rendered
	 * @param viewContext
	 *            the view context
	 * @param reportService
	 *            the {@link ReportService}
	 * @param factory
	 *            the {@link EMFFormsRendererFactory}
	 */
	@Inject
	public ScrollGroupRenderer(final VGroup vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsRendererFactory factory) {
		super(vElement, viewContext, reportService, factory);
	}

	@Override
	protected Collection<VContainedElement> getChildren() {
		return getVElement().getChildren();
	}

	@Override
	protected String getCustomVariant() {
		return "ScrollGroup"; //$NON-NLS-1$
	}

	@Override
	protected Control renderControl(final SWTGridCell gridCell,
			final Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		return createScrolledComposite(parent, p -> {
			try {
				return (Composite) super.renderControl(gridCell, p);
			} catch (NoRendererFoundException
					| NoPropertyDescriptorFoundExeption e) {
				throw new RuntimeException(e);
			}
		});
	}
}
