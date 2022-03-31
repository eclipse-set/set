/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain.EditingDomainProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.set.emfforms.basisattribute.BasisAttributeSetting;
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
 * A group renderer can render an optional or mandatory, hierarchical domain
 * element as a group. Leaf elements (e.g. basis attributes with a "wert" field)
 * are not rendered by this renderer. Rendering of such or other sub-elements
 * are delegated to further renderer calls. If the rendered domain element does
 * not exists the renderer provides an empty "shadow" domain model for rendering
 * sub-elements. This shadow model is initially not part of the original domain
 * model, but will be added, as soon as a sub-element becomes non-empty or if
 * the rendered element is mandatory. The controlled model part will be removed,
 * if it is not mandatory and all sub-elements are empty.
 * 
 * @author Schaefer
 */
public class GroupRenderer extends SimpleControlSWTControlSWTRenderer {

	private static final String SHADOW_URI = "tag:scheidt-bachmann-st.de,2017-10-25:toolbox/shadow"; //$NON-NLS-1$

	static final Logger LOGGER = LoggerFactory.getLogger(GroupRenderer.class);

	/**
	 * @param type
	 *            the type to test
	 * 
	 * @return whether this renderer can render an element with the given domain
	 *         type
	 */
	public static boolean isApplicable(final EClassifier type) {
		return !BasisAttributeSetting.isWertFeatureTypeAssignableTo(type,
				Object.class);
	}

	private static void refreshElement(
			final TypedSetting<Object> typedSetting) {
		// the setting
		final EObject object = typedSetting.getEObject();
		final EStructuralFeature feature = typedSetting.getEStructuralFeature();
		final Object value = typedSetting.getValue().orElse(null);

		// a new value
		final EClass eClass = (EClass) feature.getEType();
		final EObject newValue = eClass.getEPackage().getEFactoryInstance()
				.create(eClass);

		// change and reset the value
		object.eSet(feature, newValue);
		object.eSet(feature, value);
	}

	private static void refreshList(final TypedSetting<Object> typedSetting) {
		final Object value = typedSetting.getValue().orElse(null);

		@SuppressWarnings("unchecked")
		final Collection<Object> collection = (Collection<Object>) value;

		final ArrayList<Object> saved = com.google.common.collect.Lists
				.newArrayList(collection);

		collection.clear();
		collection.addAll(saved);
	}

	private CommandStackListener commandStackListener;

	private final EditingDomain editingDomain;

	private boolean linking = false;

	private boolean recentlyUndone = false;

	private ECPSWTView renderedView;

	private EObject shadow;

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
	public GroupRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider);
		LOGGER.debug("Render {}", vElement); //$NON-NLS-1$
		editingDomain = AdapterFactoryEditingDomain
				.getEditingDomainFor(getTypedSetting().getEObject());
	}

	private void createShadowModel() {
		final TypedSetting<EObject> typedSetting = getTypedSetting();
		final EStructuralFeature feature = typedSetting.getEStructuralFeature();
		final Optional<EObject> value = typedSetting.getValue();

		if (value.isPresent()) {
			// we use the present model value as the shadow
			shadow = value.get();
		} else {
			// we use a new value as the shadow
			final EClass eClass = (EClass) feature.getEType();
			shadow = eClass.getEPackage().getEFactoryInstance().create(eClass);

			// link shadow to editing domain
			final Resource shadowResource = editingDomain.getResourceSet()
					.createResource(URI.createURI(SHADOW_URI));
			shadowResource.eAdapters()
					.add(new EditingDomainProvider(editingDomain));
			shadowResource.getContents().add(shadow);
		}
	}

	private EObject getShadow() {
		return shadow;
	}

	private TypedSetting<EObject> getTypedSetting() {
		return Renderers.getTypedSetting(EObject.class, getVElement(),
				getViewModelContext());
	}

	private boolean isEmpty(final VControl control) {
		final TypedSetting<Object> typedSetting = Renderers.getTypedSetting(
				Object.class, control, renderedView.getViewModelContext());
		// if the value is not present the control is considered empty
		if (!typedSetting.getValue().isPresent()) {
			return true;
		}

		// if the value is present, we consider the control empty, if the value
		// is an empty collection
		final Object object = typedSetting.getValue().get();
		if (object instanceof Collection) {
			final Collection<?> collection = (Collection<?>) object;
			return collection.isEmpty();
		}
		return false;
	}

	private boolean isEmptyView() {
		if (renderedView == null) {
			LOGGER.warn(
					"renderView == null, but renderedView should never be null"); //$NON-NLS-1$
			return true;
		}
		final TreeIterator<EObject> allContents = renderedView
				.getViewModelContext().getViewModel().eAllContents();
		while (allContents.hasNext()) {
			final EObject object = allContents.next();
			if (object instanceof VControl) {
				if (!isEmpty((VControl) object)) {
					return false;
				}
			}
		}
		return true;
	}

	private void refresh(final VControl control) {
		final TypedSetting<Object> typedSetting = Renderers.getTypedSetting(
				Object.class, control, renderedView.getViewModelContext());
		if (typedSetting == null) {
			return;
		}
		if (typedSetting.getEStructuralFeature().getUpperBound() != 1) {
			refreshList(typedSetting);
		} else {
			refreshElement(typedSetting);
		}
	}

	private void refreshShadowView() {
		final TreeIterator<EObject> allContents = renderedView
				.getViewModelContext().getViewModel().eAllContents();
		while (allContents.hasNext()) {
			final EObject object = allContents.next();
			if (object instanceof VControl) {
				refresh((VControl) object);
			}
		}
	}

	private void registerNewCommandStackListener() {
		final CommandStack commandStack = editingDomain.getCommandStack();
		commandStackListener = new CommandStackListener() {
			@Override
			public void commandStackChanged(final EventObject event) {
				checkLinkShadow();
			}
		};
		commandStack.addCommandStackListener(commandStackListener);
	}

	private void setCommand(final EObject object,
			final EStructuralFeature feature, final EObject value) {
		final Command command = SetCommand.create(editingDomain, object,
				feature, value);
		editingDomain.getCommandStack().execute(command);
	}

	private boolean shadowShouldBeLinked(final EStructuralFeature feature) {
		if (feature.getLowerBound() == 1) {
			// the shadow is mandatory
			return true;
		}
		return !isEmptyView();
	}

	private boolean undoing() {
		final boolean undoing = editingDomain.getCommandStack().canUndo()
				&& editingDomain.getCommandStack().canRedo();
		if (undoing) {
			recentlyUndone = true;
		}
		return undoing;
	}

	private void unregisterCommandStackListener() {
		editingDomain.getCommandStack()
				.removeCommandStackListener(commandStackListener);
	}

	@Override
	protected Binding[] createBindings(final Control control)
			throws DatabindingFailedException {
		return null;
	}

	/**
	 * Creates the container element for the contained features to be rendered
	 * into
	 * 
	 * Can be overridden to change the container used
	 * 
	 * @param parent
	 *            The parent view element
	 * @return the sub element container composite
	 */
	@SuppressWarnings("static-method")
	protected Composite createSubElementContainer(final Composite parent) {
		final Composite subElementComposite = new Group(parent,
				SWT.SHADOW_ETCHED_IN);
		subElementComposite.setLayout(new GridLayout());
		return subElementComposite;
	}

	@Override
	protected Control createSWTControl(final Composite parent)
			throws DatabindingFailedException {

		createShadowModel();

		registerNewCommandStackListener();

		// area for sub-elements
		final Composite subElementComposite = this
				.createSubElementContainer(parent);

		// the domain element to be rendered
		final EObject domainElement = getShadow();

		// create the view model context
		final VElement view = ViewProviderHelper.getView(domainElement, null);
		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(view, domainElement);

		// renderer call
		try {
			renderedView = ECPSWTViewRenderer.INSTANCE
					.render(subElementComposite, viewModelContext);
			Assert.isNotNull(renderedView);
		} catch (final ECPRendererException e) {
			throw new RuntimeException(e);
		}

		checkLinkShadow();

		return subElementComposite;
	}

	@Override
	protected void dispose() {
		unregisterCommandStackListener();
		super.dispose();
	}

	@Override
	protected String getUnsetText() {
		return null;
	}

	void checkLinkShadow() {
		if (!linking && !undoing()) {
			linking = true;
			try {
				final TypedSetting<EObject> typedSetting = getTypedSetting();
				final EObject object = typedSetting.getEObject();
				final EStructuralFeature feature = typedSetting
						.getEStructuralFeature();
				if (shadowShouldBeLinked(feature)) {
					if (object.eGet(feature) != shadow) {
						setCommand(object, feature, shadow);
						LOGGER.debug("Shadow linked."); //$NON-NLS-1$
						refreshShadowView();
					}
				} else {
					if (object.eGet(feature) != null) {
						setCommand(object, feature, null);
						LOGGER.debug("Shadow unlinked."); //$NON-NLS-1$
						refreshShadowView();
					}
				}
			} finally {
				linking = false;
			}
			if (recentlyUndone) {
				try {
					refreshShadowView();
				} finally {
					recentlyUndone = false;
				}
			}
		}
	}
}
