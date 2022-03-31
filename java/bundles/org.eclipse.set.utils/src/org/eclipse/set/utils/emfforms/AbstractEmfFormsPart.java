/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.emfforms;

import javax.inject.Inject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * Renders EMF Forms view.
 * 
 * @param <S>
 *            the session type
 * @author Schaefer
 */
public abstract class AbstractEmfFormsPart<S extends IModelSession>
		extends BasePart<S> {

	private static VViewModelProperties getProperties(final String property) {
		if (property == null) {
			return null;
		}
		final VViewModelLoadingProperties properties = VViewFactory.eINSTANCE
				.createViewModelLoadingProperties();
		properties.addNonInheritableProperty(property, Boolean.TRUE);
		return properties;
	}

	private ECPSWTView view;

	@Inject
	protected ToolboxViewModelService modelService;

	/**
	 * @param sessionType
	 *            the session type
	 */
	public AbstractEmfFormsPart(final Class<S> sessionType) {
		super(sessionType);
	}

	/**
	 * Discard any changes to the session editing domain.
	 */
	public void discardChanges() {
		final CommandStack commandStack = getModelSession().getEditingDomain()
				.getCommandStack();

		// undo commands
		while (commandStack.canUndo()) {
			commandStack.undo();
		}

		// send editing completed
		ToolboxEvents.send(getBroker(), new EditingCompleted());
	}

	/**
	 * @return the view
	 */
	public ECPSWTView getView() {
		return view;
	}

	protected void createEmfFormsPart(final Composite parent,
			final EObject element) throws ECPRendererException {
		createEmfFormsPart(parent, element, null);
	}

	protected void createEmfFormsPart(final Composite parent,
			final EObject element, final String property)
			throws ECPRendererException {
		Assert.isNotNull(element);

		// create the view model context
		final VElement vElement = ViewProviderHelper.getView(element,
				getProperties(property));
		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(vElement, element);

		// renderer call
		view = ECPSWTViewRenderer.INSTANCE.render(parent, viewModelContext);
	}

	protected abstract void createFormsView(Composite parent)
			throws ECPRendererException;

	@Override
	protected void createView(final Composite parent) {
		try {
			createFormsView(parent);
		} catch (final ECPRendererException e) {
			throw new RuntimeException(e);
		}
	}

	protected Shell getShell() {
		return view.getSWTControl().getShell();
	}
}
