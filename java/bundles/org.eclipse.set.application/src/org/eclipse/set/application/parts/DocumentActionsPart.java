/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.parts;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.core.services.statusreporter.StatusReporter;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.ActionProvider;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.extensions.IModelSessionExtensions;
import org.eclipse.set.basis.extensions.PartDescriptionExtensions;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.core.services.action.ActionService;
import org.eclipse.set.core.services.branding.BrandingService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.NewActiveViewEvent;
import org.eclipse.set.utils.events.SessionDirtyChanged;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.widgets.HighlightButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * @author bleidiessel
 * 
 *         Part stellt die für ein geladenes PlanPro-Dokument möglichen Aktionen
 *         zur Verfügung. Diese werden unterteilt in Kategorien.
 * 
 */
public class DocumentActionsPart implements ActionProvider {

	private static class ViewButton {

		private final Button button;
		private final PartDescription view;

		public ViewButton(final Button button, final PartDescription view) {
			this.button = button;
			this.view = view;
		}

		/**
		 * @return the button
		 */
		public Button getButton() {
			return button;
		}

		/**
		 * @return the view
		 */
		public PartDescription getView() {
			return view;
		}
	}

	private static final Logger logger = LoggerFactory
			.getLogger(DocumentActionsPart.class);

	private static final Comparator<? super PartDescription> PART_DESCRIPTION_COMPARATOR = //
			Comparator.comparing(PartDescription::getOrderPriority)
					.reversed()
					.thenComparing(PartDescription::getToolboxViewName);

	private static void highlightButton(
			final Entry<String, HighlightButton> entry,
			final Set<String> openIds) {
		final String id = entry.getKey();
		final HighlightButton button = entry.getValue();
		button.setHighlight(openIds.contains(id));
	}

	@Inject
	private ActionService actionService;

	@Inject
	private BrandingService brandingService;

	@Inject
	private IEventBroker broker;

	private LocalResourceManager localResourceManager;

	@Inject
	@Translation
	private Messages messages;

	@Inject
	MApplication application;

	ExpandBar bar;

	@Inject
	EHandlerService handlerService;

	final Map<String, HighlightButton> idToButton = new HashMap<>();

	int index = 0;

	@Inject
	SessionService sessionService;

	@Inject
	StatusReporter statusReporter;

	@Inject
	ToolboxPartService toolboxPartService;
	final List<ViewButton> viewButtons = Lists.newLinkedList();

	/**
	 * Aufbau der GUI
	 * 
	 * @param parent
	 *            Bereich in dem gezeichnet wird
	 * @param display
	 *            wird benötigt, um Bilder zu laden
	 * @param shell
	 *            the shell
	 */
	@PostConstruct
	public void postConstruct(final Composite parent, final Display display,
			final Shell shell) {
		logger.debug(statusReporter.toString());

		localResourceManager = new LocalResourceManager(
				JFaceResources.getResources(), parent);

		// set GridLayout to parent
		final GridLayout partLayout = new GridLayout();
		partLayout.marginWidth = 0;
		partLayout.marginHeight = 0;
		parent.setLayout(partLayout);

		logger.info(messages.DocumentActionsPart_StartupMessage);

		// Part wird als große Expandbar dargestellt
		bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		bar.setSpacing(8);

		// Overwrite default ExpandBar scrolling behavior (only scrolling 1px)
		final ScrollBar vScrollBar = bar.getVerticalBar();
		if (vScrollBar != null) {
			vScrollBar.setIncrement(15);
			vScrollBar.setPageIncrement(15);
		} else {
			logger.warn("Could not set scrolling increment"); //$NON-NLS-1$
		}

		// Add a logo zone
		final Composite logoZone = new Composite(parent, SWT.NONE);
		logoZone.setLayoutData(new GridData(SWT.CENTER, SWT.END, false, false));
		logoZone.setLayout(new GridLayout());
		final Label logoLabel = new Label(logoZone, SWT.NONE);
		logoLabel.setLayoutData(
				new GridData(GridData.CENTER, GridData.CENTER, true, false));
		brandingService.getActionLogo().ifPresent(descriptor -> {
			final Image actionLogo = localResourceManager.create(descriptor);
			logoLabel.setText("LOGO"); //$NON-NLS-1$
			logoLabel.setImage(actionLogo);
		});

		/*
		 * Registriere ein Runnable, welches das Vorhandensein einer Session
		 * überwacht. Ist eine Session vorhanden, so aktiviere die Sidebar, ist
		 * keine Session vorhanden, so setze die Bar inaktiv.
		 */
		application.getContext().runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(final IEclipseContext context) {
				// if the action area is not visible ignore the change
				if (!toolboxPartService.isActionAreaVisible()) {
					return true;
				}
				final IModelSession modelSession = context
						.get(IModelSession.class);
				if (modelSession != null) {
					createExpandItems();
					checkEnableViewButtons(modelSession);
					bar.setEnabled(true);
				} else {
					for (final Control item : bar.getChildren()) {
						item.dispose();
					}
					for (final ExpandItem item : bar.getItems()) {
						item.dispose();
					}
					index = 0;
					viewButtons.clear();
					idToButton.clear();
					bar.setEnabled(false);
				}
				return true;
			}
		});

		ToolboxEvents.subscribe(broker, NewActiveViewEvent.class,
				new DefaultToolboxEventHandler<NewActiveViewEvent>() {
					@Override
					public void accept(final NewActiveViewEvent e) {
						highlightButtons();
					}
				});
		ToolboxEvents.subscribe(broker, EditingCompleted.class,
				new DefaultToolboxEventHandler<EditingCompleted>() {
					@Override
					public void accept(final EditingCompleted e) {
						createExpandItems();
						checkEnableViewButtons(application.getContext()
								.get(IModelSession.class));
					}
				});
		ToolboxEvents.subscribe(broker, SessionDirtyChanged.class,
				new DefaultToolboxEventHandler<SessionDirtyChanged>() {
					@Override
					public void accept(final SessionDirtyChanged e) {
						createExpandItems();
						checkEnableViewButtons(application.getContext()
								.get(IModelSession.class));
					}
				});
		actionService.setActionProvider(this);
	}

	@Override
	public void update() {
		final IModelSession session = application.getContext()
				.get(IModelSession.class);
		if (session != null) {
			highlightButtons();
		}
	}

	private void checkEnabled(final ViewButton viewButton,
			final IModelSession modelSession) {
		final PartDescription view = viewButton.getView();
		viewButton.getButton().setEnabled(testEnable(view, modelSession));
	}

	private void createExpandItem(final ToolboxViewGroup group) {
		if (Arrays.stream(bar.getItems())
				.anyMatch(item -> item.getText().equals(group.text()))) {
			// we do not add groups with the same name as an existing group
			return;
		}
		final Composite composite = new Composite(bar, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;

		composite.setLayout(layout);
		createRegisteredViewsForExpandItem(composite, group);
		if (composite.getChildren().length == 0) {
			composite.dispose();
			return;
		}

		final ExpandItem expandItem = new ExpandItem(bar, SWT.NONE, index);
		expandItem.setText(group.text());
		expandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem.setControl(composite);
		final Image image = localResourceManager
				.create(group.imageDescriptor());
		expandItem.setImage(image);
		expandItem.setControl(composite);
		expandItem.setExpanded(group.isInitiallyExpanded());
		index++;
	}

	// Erstellt einen Button für einen View
	private void createRegisteredViewsForExpandItem(final Composite parent,
			final ToolboxViewGroup group) {
		final List<PartDescription> registeredDescriptions = toolboxPartService
				.getRegisteredDescriptions(group);
		registeredDescriptions.sort(PART_DESCRIPTION_COMPARATOR);
		final Object[] listEnableView = registeredDescriptions.stream()
				.filter(registered -> testEnable(registered,
						application.getContext().get(IModelSession.class)))
				.toArray();
		if (listEnableView.length == 0) {
			return;
		}
		for (final PartDescription view : registeredDescriptions) {
			final HighlightButton button = new HighlightButton(parent,
					SWT.PUSH | SWT.LEFT);
			idToButton.put(view.getId(), button);
			button.getButton().setText(view.getToolboxViewName());
			button.getButton().setToolTipText(view.getToolboxViewToolTip());
			final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true,
					true);
			button.getButton().setLayoutData(layoutData);

			button.getButton()
					.addListener(SWT.Selection,
							event -> toolboxPartService.showPart(view.getId()));

			viewButtons.add(new ViewButton(button.getButton(), view));

			if (logger.isDebugEnabled()) {
				logger.debug(PartDescriptionExtensions.debugHtml(view,
						group.text()));
			}
		}
	}

	private boolean isExclusiveEditorOpen() {
		return viewButtons.stream()
				.map(ViewButton::getView)
				.filter(PartDescription::isExclusiveEditor)
				.filter(toolboxPartService::isOpen)
				.findAny()
				.isPresent();
	}

	private static boolean showDevelopmentItems() {
		return ToolboxConfiguration.isDevelopmentMode();
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	private void subscribeTopicSelectedElement(
			@SuppressWarnings("unused") @UIEventTopic(UIEvents.UIElement.TOPIC_TOBERENDERED) final Event event) {
		highlightButtons();
	}

	private boolean testEnable(final PartDescription view,
			final IModelSession modelSession) {
		if (modelSession == null) {
			return false;
		}

		boolean enable = true;

		enable = enable && (!view.needsLoadedModel()
				|| IModelSessionExtensions.hasLoadedModel(modelSession));
		enable = enable && (!view.needsXsdValidation()
				|| IModelSessionExtensions.isXsdValid(modelSession));
		enable = enable && (!view.needsEmfValidation()
				|| IModelSessionExtensions.isEmfValid(modelSession));
		enable = enable
				&& (!view.needsCleanSession() || !modelSession.isDirty());
		enable = enable && view.canProcess(modelSession.getNature());
		enable = enable && (!view.isExclusiveEditor()
				|| toolboxPartService.isOpen(view) || !isExclusiveEditorOpen());

		return enable;
	}

	protected void checkEnableViewButtons(final IModelSession modelSession) {
		viewButtons.forEach(vb -> checkEnabled(vb, modelSession));

	}

	protected void highlightButtons() {
		final Set<String> openParts = toolboxPartService.getOpenParts()
				.stream()
				.map(MApplicationElement::getElementId)
				.collect(Collectors.toSet());
		idToButton.entrySet()
				.stream()
				.forEach(entry -> highlightButton(entry, openParts));
		checkEnableViewButtons(
				application.getContext().get(IModelSession.class));
	}

	void createExpandItems() {
		toolboxPartService.getViewGroups()
				.stream()
				.filter(g -> !g.isInvisible())
				.filter(g -> showDevelopmentItems() || !g.isDevelopment())
				.sorted(Comparator.comparingInt(ToolboxViewGroup::orderPriority)
						.reversed())
				.forEach(this::createExpandItem);

	}
}
