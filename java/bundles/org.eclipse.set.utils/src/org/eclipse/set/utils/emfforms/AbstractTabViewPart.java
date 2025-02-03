/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.emfforms;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.utils.BasePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Abstract Part for create view with swt tab
 * 
 * @author truong
 *
 */
public abstract class AbstractTabViewPart extends BasePart {

	@Inject
	EMFFormsRendererFactory renderFactory;
	protected final Map<CTabItem, EObject> tabItemToObject = new LinkedHashMap<>();
	protected final Map<CTabItem, Composite> tabItemToComposite = new LinkedHashMap<>();

	protected CTabFolder createTabFolder(final Composite parent) {
		final CTabFolder cTabFolder = new CTabFolder(parent, SWT.BOTTOM);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(cTabFolder);
		cTabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				itemSelected(cTabFolder.getSelection());
			}
		});
		createTabItem(cTabFolder);
		return cTabFolder;
	}

	protected void renderTabItem(final CTabItem tabItem)
			throws ECPRendererException {
		// already rendered or invalid state
		if (!tabItemToObject.containsKey(tabItem)) {
			return;
		}
		// remove from the maps on first rendering
		final EObject tabItemObject = tabItemToObject.remove(tabItem);
		final VViewModelProperties properties = VViewFactory.eINSTANCE
				.createViewModelLoadingProperties();
		properties.addNonInheritableProperty(
				ToolboxConstants.PLANING_GROUP_VIEW_DETAIL_KEY,
				Boolean.valueOf(true));
		final VView view = ViewProviderHelper.getView(tabItemObject,
				properties);
		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(view, tabItemObject);
		AbstractSWTRenderer<VElement> renderer = null;
		try {
			renderer = renderFactory.getRendererInstance(view,
					viewModelContext);
		} catch (final EMFFormsNoRendererException e) {
			throw new RuntimeException(e);
		}
		if (renderer == null) {
			return;
		}
		final SWTGridDescription gridDescription = renderer.getGridDescription(
				GridDescriptionFactory.INSTANCE.createEmptyGridDescription());
		final Composite composite = tabItemToComposite.remove(tabItem);
		for (final SWTGridCell grid : gridDescription.getGrid()) {
			final Control render = renderer.render(grid, composite);

			renderer.finalizeRendering(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
					.grab(true, true).applyTo(render);
			final ScrolledComposite scrolledComposite = ScrolledComposite.class
					.cast(composite);
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setContent(render);
			tabItem.setControl(composite);
		}
	}

	protected abstract void createTabItem(CTabFolder folder);

	protected void setDefaultSelectionTab(final CTabFolder folder) {
		if (folder.getItemCount() > 0) {
			folder.setSelection(0);
			itemSelected(folder.getSelection());
		}
	}

	protected void itemSelected(final CTabItem selection) {
		try {
			renderTabItem(selection);
		} catch (final ECPRendererException e) {
			throw new RuntimeException(e);
		}
	}
}
