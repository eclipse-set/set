/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.partservice;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.internal.workbench.Activator;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.common.util.URI;
import org.eclipse.set.basis.ActionProvider;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Wrapper;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.part.ToolboxPart;
import org.eclipse.set.basis.part.ViewVisibility;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.NewActiveViewEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Implementation of {@link ToolboxPartService} with multi-part support.
 * 
 * @author Schaefer
 */
public class ToolboxMultiPartServiceImpl implements ToolboxPartService {

	private static final String ACTION_PART_ID = "org.eclipse.set.application.part.actions"; //$NON-NLS-1$
	private static final String APPLICATION_PARTSASHCONTAINER_ID = "org.eclipse.set.application.document.partsashcontainer"; //$NON-NLS-1$
	private static final String DEFAULT_ICON_URI = "platform:/plugin/org.eclipse.set.utils/icons/baseline_view_module_black_24dp.png"; //$NON-NLS-1$

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ToolboxMultiPartServiceImpl.class);
	private static final String PRIMARY_PART_STACK_ID = "org.eclipse.set.core.stack.primary"; //$NON-NLS-1$

	private static void checkPartContribution(
			final PartDescription description) {
		try {
			final URI uri = URI.createURI(description.getContributionUri());
			final Bundle bundle = Activator.getDefault()
					.getBundleForName(uri.authority());
			final String typeName = uri.segment(0);
			if (bundle == null) {
				throw new IllegalArgumentException(uri.toString());
			}
			final Class<?> type = bundle.loadClass(typeName);
			if (!ToolboxPart.class.isAssignableFrom(type)) {
				LOGGER.warn("Contribution " + type.getSimpleName() //$NON-NLS-1$
						+ " for view " + description.getToolboxViewName() //$NON-NLS-1$
						+ " is no subclass of " //$NON-NLS-1$
						+ BasePart.class.getSimpleName()
						+ " and possibly has no agreed title."); //$NON-NLS-1$
			}
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static MPart createPart(final PartDescription description) {
		final MPart part = MBasicFactory.INSTANCE.createPart();
		part.setContributionURI(description.getContributionUri());
		part.setContainerData("77"); //$NON-NLS-1$
		part.setLabel(description.getToolboxViewName());
		part.setElementId(description.getId());
		part.setVisible(false);
		part.setToBeRendered(false);
		part.setCloseable(true);
		MApplicationElementExtensions.setDescription(part, description);
		MApplicationElementExtensions.setVisibility(part,
				ViewVisibility.EXPOSED);

		return part;
	}

	private static String getPdfPartId(final Path path) {
		return ToolboxConstants.PDF_VIEWER_PART_ID + ":" + path; //$NON-NLS-1$
	}

	private MPart actionPart;

	private MPartStack primaryPartStack;

	@Inject
	private SessionService sessionService;

	MPart activePart;

	@Inject
	MApplication application;

	@Inject
	IEventBroker broker;

	@Inject
	EModelService modelService;

	@Inject
	EPartService partService;

	Map<ToolboxViewGroup, List<PartDescription>> registeredDescriptions;
	@Inject
	UISynchronize sync;

	/**
	 * Create the service.
	 */
	public ToolboxMultiPartServiceImpl() {
		registeredDescriptions = Maps.newHashMap();
	}

	@Override
	public void clean() {
		closeOpenParts();
	}

	@Override
	public MPart getActivePart() {
		return activePart;
	}

	@Override
	public List<MPart> getOpenParts() {
		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		return stacks.stream().flatMap(stack -> stack.getChildren().stream())
				.filter(e -> MApplicationElementExtensions.isOpenPart(e)
						&& e != actionPart)
				.map(e -> (MPart) e).collect(Collectors.toList());
	}

	@Override
	public PartDescription getRegisteredDescription(final String partId) {
		for (final List<PartDescription> descriptions : registeredDescriptions
				.values()) {
			for (final PartDescription description : descriptions) {
				if (description.getId().equals(partId)) {
					return description;
				}
			}
		}

		return null;
	}

	@Override
	public Map<ToolboxViewGroup, List<PartDescription>> getRegisteredDescriptions() {
		return registeredDescriptions;
	}

	@Override
	public List<PartDescription> getRegisteredDescriptions(
			final ToolboxViewGroup type) {
		final List<PartDescription> descriptions = registeredDescriptions
				.get(type);
		if (descriptions != null) {
			return descriptions;
		}
		return Collections.emptyList();
	}

	@Override
	public boolean isActionAreaVisible() {
		return actionPart.isVisible() && actionPart.isToBeRendered();
	}

	@Override
	public boolean isOpen(final PartDescription description) {
		return getOpenParts().stream()
				.filter(p -> p.getElementId() == description.getId()).findAny()
				.isPresent();
	}

	@Override
	public void showActionArea() {
		partService.activate(actionPart);
		final ActionProvider actionProvider = (ActionProvider) actionPart
				.getObject();
		actionProvider.update();
	}

	@Override
	public boolean showDefaultPart(final IModelSession session) {
		return showPart(getDefaultPartID(session));
	}

	@Override
	public boolean showPart(final PartDescription description) {
		return showPart(description.getId());
	}

	@Override
	public boolean showPart(final String id) {
		return forceFocusAndShowPart(id);
	}

	@Override
	public void showPdfPart(final Path path) {
		final String pdfPartId = getPdfPartId(path);
		final MPart pdfPart = partService.findPart(pdfPartId);
		if (pdfPart == null) {
			createAndPlacePdfPart(pdfPartId, path);
		}
		showPart(pdfPartId);
	}

	private void closeOpenParts() {
		getOpenParts().forEach(part -> part.setToBeRendered(false));
		activePart = null;
	}

	private void createAndPlacePart(final PartDescription description) {
		// check whether the part is derived from ToolboxTitlePart
		checkPartContribution(description);

		// create the part
		final MPart part = createPart(description);

		// place the part
		final MPartStack partStack = getPrimaryPartStack();
		if (partStack == null) {
			return;
		}
		partStack.getChildren().add(part);
	}

	private void createAndPlacePdfPart(final String pdfPartId,
			final Path path) {

		// create the part
		final PartDescription description = getRegisteredDescription(
				ToolboxConstants.PDF_VIEWER_PART_ID);
		final MPart part = createPart(description);

		// modify part
		final Path fileName = path.getFileName();
		if (fileName != null) {
			part.setLabel(fileName.toString());
		}
		part.setElementId(pdfPartId);
		part.getTransientData().put(ToolboxConstants.FILE_PARAMETER,
				path.toString());

		// place the part
		getPrimaryPartStack().getChildren().add(part);
	}

	private PartDescription createDescription(
			final PartDescriptionService descriptionService) {
		final PartDescription description = descriptionService
				.getDescription(application.getContext());
		getOrCreate(descriptionService.getToolboxViewGroup()).add(description);

		return description;
	}

	private MPartStack createPrimaryPartStack() {

		// find the part sash
		final List<MPartSashContainer> elements = modelService.findElements(
				application, APPLICATION_PARTSASHCONTAINER_ID,
				MPartSashContainer.class, null);
		if (elements.isEmpty()) {
			return null;
		}
		final MPartSashContainer sashContainer = elements.get(0);
		final List<MPartSashContainerElement> children = sashContainer
				.getChildren();

		// remove all children but the first (the action area)
		while (children.size() > 1) {
			final MPartSashContainerElement element = children.get(1);
			element.setVisible(false);
			element.setToBeRendered(false);
			children.remove(1);
		}

		// next to primary stack we find the container with the action part
		final MPartSashContainerElement actionPartContainer = children.get(0);

		// create a new part stack
		final MPartStack stack = MBasicFactory.INSTANCE.createPartStack();
		stack.setElementId(PRIMARY_PART_STACK_ID);
		children.add(stack);

		// the part stack shall be unclosable
		stack.getTags().add(IPresentationEngine.NO_AUTO_COLLAPSE);

		// claim the width, not used by the action part container
		final String oppositWidthText = actionPartContainer.getContainerData();
		final int oppositeWidth = Integer.parseInt(oppositWidthText);
		stack.setContainerData(Integer.toString(100 - oppositeWidth));

		primaryPartStack = stack;

		return stack;
	}

	// IMPROVE: This is a workaround for
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=502544
	// Possibly there will be a fix in Eclipse 4.10
	private boolean forceFocusAndShowPart(final String id) {
		final Wrapper<Boolean> result = new Wrapper<>();
		final List<MWindow> windows = application.getChildren();
		final MWindow mWindow = windows.get(0);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				((org.eclipse.swt.widgets.Shell) mWindow.getWidget())
						.forceFocus();
				result.setValue(Boolean.valueOf(showPartImpl(id)));
				broker.post(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC,
						UIEvents.ALL_ELEMENT_ID);
			}
		});
		return result.getValue().booleanValue();
	}

	private MPart getActionPart() {
		final List<MPart> parts = modelService.findElements(application,
				ACTION_PART_ID, MPart.class, null);
		if (parts.isEmpty()) {
			return null;
		}
		Assert.isTrue(parts.size() == 1);
		return parts.get(0);
	}

	private String getDefaultPartID(final IModelSession session) {
		if (session == null) {
			return null;
		}

		final Optional<String> serviceDefaultPartID = sessionService
				.getDefaultPartID(session);
		if (serviceDefaultPartID.isPresent()) {
			return serviceDefaultPartID.get();
		}

		if (session.getNature() == PlanProFileNature.INVALID) {
			return ToolboxConstants.VALIDATION_PART_ID;
		}
		final Outcome outcome = session
				.getValidationsOutcome(ValidationResult::getOutcome);

		if (outcome == Outcome.INVALID) {
			return ToolboxConstants.VALIDATION_PART_ID;
		}

		final Iterable<PartDescription> descriptions = Iterables
				.concat(registeredDescriptions.values());

		for (final PartDescription description : descriptions) {
			final PlanProFileNature defaultForNature = description
					.getDefaultForNature();
			if (session.getNature() == defaultForNature) {
				return description.getId();
			}
		}

		return null;
	}

	private List<PartDescription> getOrCreate(
			final ToolboxViewGroup toolboxViewGroup) {
		if (registeredDescriptions.containsKey(toolboxViewGroup)) {
			// get
			return registeredDescriptions.get(toolboxViewGroup);
		}

		// or create
		registeredDescriptions.put(toolboxViewGroup, Lists.newLinkedList());
		return registeredDescriptions.get(toolboxViewGroup);
	}

	private boolean isPrimaryPartStack(
			final MElementContainer<MUIElement> container) {
		final Object containerObject = container;
		return containerObject == getPrimaryPartStack();
	}

	@PostConstruct
	private void postConstruct() {
		actionPart = getActionPart();

		// register for session changes
		application.getContext().runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(final IEclipseContext context) {
				sessionChanged(context.get(IModelSession.class));
				return true;
			}
		});
	}

	private void setActivePart(final MPart part) {
		// ignore selection of the action part
		if (part == actionPart) {
			return;
		}

		// set the active part
		activePart = part;

		if (part == null) {
			// send (de)activation event
			ToolboxEvents.send(broker, new NewActiveViewEvent(part));
			return;
		}

		// Test, if the part is a toolbox view
		if (!MApplicationElementExtensions.isToolboxView(part)) {
			throw new IllegalArgumentException(part.toString());
		}

		// set visibility
		final MElementContainer<MUIElement> stack = part.getParent();
		stack.getChildren().forEach(e -> setVisibility(e));

		// select the active part within its stack
		stack.setSelectedElement(activePart);

		// send activation event
		ToolboxEvents.send(broker, new NewActiveViewEvent(activePart));
	}

	private void setVisibility(final MApplicationElement element) {
		if (element == activePart) {
			MApplicationElementExtensions.setVisibility(element,
					ViewVisibility.EXPOSED);
		} else {
			MApplicationElementExtensions.setVisibility(element,
					ViewVisibility.CONCEALED);
		}
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	private void subscribeTopicActivate(
			@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) final Event event) {
		if (event != null) {
			final Object element = event.getProperty(EventTags.ELEMENT);
			if (element instanceof MPart) {
				final MPart part = (MPart) element;
				if (MApplicationElementExtensions.isToolboxView(part)) {
					setActivePart((MPart) element);
				}
			}
		}
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	private void subscribeTopicBringToTop(
			@UIEventTopic(UIEvents.UILifeCycle.BRINGTOTOP) final Event event) {
		if (event != null) {
			final Object element = event.getProperty(EventTags.ELEMENT);

			// switch between parts in different shells
			if (element instanceof MPart) {
				final MPart part = (MPart) element;
				final MElementContainer<MUIElement> parent = part.getParent();

				// We do not activate parts of the primary part stack with this
				// event. Otherwise we would prevent the use of the table switch
				// with the active part in a secondary part stack.
				if (!isPrimaryPartStack(parent)
						&& MApplicationElementExtensions.isToolboxView(part)) {
					setActivePart(part);
				}
			}
		}
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	private void subscribeTopicSelectedElement(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) final Event event) {
		final Object element = event.getProperty(EventTags.ELEMENT);

		// switch stack element within stack
		if (element instanceof MPartStack) {
			final MPartStack stack = (MPartStack) element;
			final MStackElement selectedStackElement = stack
					.getSelectedElement();
			if (selectedStackElement != activePart) {
				setActivePart((MPart) selectedStackElement);
			}
		}

		// switch between stacks of the part sash container
		if (element instanceof MPartSashContainer) {
			final MPartSashContainer container = (MPartSashContainer) element;
			final MPartSashContainerElement selectedContainerElement = container
					.getSelectedElement();
			if (selectedContainerElement instanceof MPartStack) {
				final MPartStack stack = (MPartStack) selectedContainerElement;
				final MStackElement selectedStackElement = stack
						.getSelectedElement();
				if (selectedStackElement != activePart) {
					setActivePart((MPart) selectedStackElement);
				}

			}
		}
	}

	protected void sessionChanged(final IModelSession session) {
		if (session == null) {
			closeOpenParts();
		}
	}

	MPartStack getPrimaryPartStack() {
		if (primaryPartStack != null) {
			return primaryPartStack;
		}

		// find the stack
		final List<MPartStack> stacks = modelService.findElements(application,
				PRIMARY_PART_STACK_ID, MPartStack.class, null);
		if (stacks.isEmpty()) {
			return createPrimaryPartStack();
		}
		return stacks.get(0);
	}

	void put(final PartDescriptionService descriptionService) {
		final PartDescription description = createDescription(
				descriptionService);
		createAndPlacePart(description);
	}

	boolean showPartImpl(final String id) {
		LOGGER.trace("showPart: {}...", id); //$NON-NLS-1$

		final Stopwatch stopwatch = Stopwatch.createStarted();

		try {
			// we do not display any default parts
			if (id == null || ToolboxConstants.NO_SESSION_PART_ID.equals(id)) {
				return true;
			}

			// try to find the part
			final MPart part = partService.findPart(id);

			// test whether the part is already active
			if (part == activePart) {
				return true;
			}

			// test whether the part is already displayed
			if (part != null && part.isVisible() && part.isToBeRendered()) {
				setActivePart(part);
				return true;
			}

			// create the part
			final MPart created = partService.showPart(id, PartState.CREATE);
			if (created == null) {
				throw new IllegalArgumentException(id);
			}
			created.setIconURI(DEFAULT_ICON_URI);

			// test if creation was successful
			if (MApplicationElementExtensions.isSuccessful(created)) {
				MApplicationElementExtensions.markAsToolboxView(created);
				created.setVisible(true);
				created.setToBeRendered(true);

				// add the part at the end of the primary part stack
				final MPartStack stack = getPrimaryPartStack();
				stack.getChildren().remove(created);
				stack.getChildren().add(created);

				setActivePart(created);
			} else {
				partService.hidePart(created);
				// Reset the view state, as a subsequent attempt at
				// part creation may succeed.
				MApplicationElementExtensions.resetViewState(created);
			}
		} finally {
			LOGGER.info("showPart: {} done: {}", id, stopwatch); //$NON-NLS-1$
		}

		return true;
	}

}
