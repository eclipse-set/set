/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.util.List;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.part.ToolboxPart;
import org.eclipse.set.basis.part.ViewVisibility;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.DataEvent;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.NewActiveViewEvent;
import org.eclipse.set.utils.events.NewTableTypeEvent;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.events.SessionDirtyChanged;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.widgets.Banderole;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

/**
 * A toolbox specific part with a title.
 * 
 * @author Schaefer
 */
public abstract class BasePart implements ToolboxPart {

	static final Logger LOGGER = LoggerFactory.getLogger(BasePart.class);
	private MApplication application;

	private Banderole banderole;

	@Inject
	private IEventBroker broker;

	private ToolboxEventHandler<DataEvent> dataEventHandler;

	@Inject
	private DialogService dialogService;

	@Inject
	private EnumTranslationService enumTranslationService;

	@Inject
	@Translation
	private Messages messages;

	private final List<Container_AttributeGroup> modifiedContainer = Lists
			.newLinkedList();

	private final List<Notification> modifiedProjectData = Lists
			.newLinkedList();

	private ToolboxEventHandler<NewActiveViewEvent> newActiveViewHandler;

	private ToolboxEventHandler<NewTableTypeEvent> newTableTypeHandler;

	private IModelSession session;

	private DefaultToolboxEventHandler<SessionDirtyChanged> sessionDirtyHandler;

	@Inject
	private SessionService sessionService;

	private Shell toolboxShell;

	private boolean updateViewDelayed;

	private String viewTitle;

	protected LocalResourceManager localResourceManager;

	boolean active;
	MPart toolboxPart;

	/**
	 * @return the application
	 */
	public MApplication getApplication() {
		return application;
	}

	/**
	 * @return the broker
	 */
	public IEventBroker getBroker() {
		return broker;
	}

	/**
	 * @return the containerInFocus
	 */
	public MultiContainer_AttributeGroup getContainerInFocus() {
		return session.getContainer(getContainerType());
	}

	/**
	 * @return the containerType
	 */
	@SuppressWarnings("static-method")
	public ContainerType getContainerType() {
		return null;
	}

	/**
	 * @return the dialog service
	 */
	public DialogService getDialogService() {
		return dialogService;
	}

	/**
	 * @return the session
	 */
	public IModelSession getModelSession() {
		return session;
	}

	/**
	 * @return the session service
	 */
	public SessionService getSessionService() {
		return sessionService;
	}

	/**
	 * @return the table type
	 */
	public TableType getTableType() {
		final ContainerType containerType = getContainerType();
		if (containerType != null) {
			return containerType.getDefaultTableType();
		}
		return null;
	}

	/**
	 * @return the toolbox part
	 */
	public MPart getToolboxPart() {
		return toolboxPart;
	}

	/**
	 * @return the toolbox shell
	 */
	public Shell getToolboxShell() {
		return toolboxShell;
	}

	/**
	 * @return the utility translations
	 */
	public Messages getUtilMessages() {
		return messages;
	}

	/**
	 * @return whether this part is the active view
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return whether the view is marked as outdated
	 */
	public boolean isOutdated() {
		return banderole.isOutdated();
	}

	/**
	 * @param outdated
	 *            whether the view is outdated
	 */
	public void setOutdated(final boolean outdated) {
		if (outdated != banderole.isOutdated()) {
			banderole.setOutdated(outdated);
		}
	}

	@PostConstruct
	private void postConstruct(final Composite parent, final Shell shell,
			final MPart part, final MApplication pApplication,
			final IModelSession modelSession) {
		try {
			localResourceManager = new LocalResourceManager(
					JFaceResources.getResources(), parent);
			toolboxShell = shell;
			toolboxPart = part;
			viewTitle = part.getLabel();
			application = pApplication;
			session = modelSession;

			// parent layout
			parent.setLayout(new GridLayout());

			// the view title
			final Control title = createTitle(parent);
			GridDataFactory.fillDefaults().applyTo(title);

			// the view composite
			final Composite viewComposite = new Composite(parent, SWT.NONE);
			viewComposite.setLayout(new GridLayout());
			GridDataFactory.fillDefaults()
					.grab(true, true)
					.applyTo(viewComposite);

			createView(viewComposite);

			// subscribe activation events
			newActiveViewHandler = new DefaultToolboxEventHandler<>() {
				@Override
				public void accept(final NewActiveViewEvent e) {
					final MPart newActivePart = e.getNewActivePart();
					if (newActivePart == toolboxPart) {
						// this view is active now
						active = true;
						LOGGER.debug(
								toolboxPart.getElementId() + " activated."); //$NON-NLS-1$

						// update the view
						handleActivation();
					} else {
						active = false;
						LOGGER.debug(
								toolboxPart.getElementId() + " deactivated."); //$NON-NLS-1$
					}
				}
			};
			ToolboxEvents.subscribe(broker, NewActiveViewEvent.class,
					newActiveViewHandler);

			// register for table type changes
			newTableTypeHandler = new DefaultToolboxEventHandler<>() {
				@Override
				public void accept(final NewTableTypeEvent e) {
					handleNewTableType(e);
				}
			};
			ToolboxEvents.subscribe(broker, NewTableTypeEvent.class,
					newTableTypeHandler);

			// subscribe new data events
			dataEventHandler = new DefaultToolboxEventHandler<>() {
				@Override
				public void accept(final DataEvent e) {
					handleDataEvent(e);
				}
			};
			ToolboxEvents.subscribe(broker, DataEvent.class, dataEventHandler);

			// subscribe session dirty events
			sessionDirtyHandler = new DefaultToolboxEventHandler<>() {
				@Override
				public void accept(final SessionDirtyChanged e) {
					handleSessionDirtyEvent(e);
				}
			};
			ToolboxEvents.subscribe(broker, SessionDirtyChanged.class,
					sessionDirtyHandler);
		} catch (final Exception e) {
			dialogService.error(shell, e);
		}
	}

	@PreDestroy
	private void preDestroy() {
		toolboxPart.setVisible(false);
		toolboxPart.setToBeRendered(false);
		if (toolboxPart.getWidget() instanceof final Widget widget) {
			widget.dispose();
		}
		ToolboxEvents.unsubscribe(broker, newActiveViewHandler);
		ToolboxEvents.unsubscribe(broker, newTableTypeHandler);
		ToolboxEvents.unsubscribe(broker, dataEventHandler);
		ToolboxEvents.unsubscribe(broker, sessionDirtyHandler);
	}

	protected Banderole createBanderole(final Composite parent) {
		banderole = new Banderole(parent, messages.ViewHeading_OutdatedText,
				enumTranslationService);
		banderole.setHeading(getViewTitle());
		banderole.setOutdatedAction(getOutdatedAction());
		return banderole;
	}

	protected Control createTitle(final Composite parent) {
		return createBanderole(parent).getControl();
	}

	protected abstract void createView(Composite parent);

	protected Banderole getBanderole() {
		return banderole;
	}

	@SuppressWarnings("static-method")
	protected SelectableAction getOutdatedAction() {
		// override to use outdated action
		return null;
	}

	protected String getViewTitle() {
		return viewTitle;
	}

	protected void handleActivation() {
		if (updateViewDelayed) {
			updateView();
		}
	}

	@SuppressWarnings("static-method")
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		// override to handle event
		LOGGER.debug("{} ignored", e); //$NON-NLS-1$
	}

	protected void handleDataEvent(final DataEvent e) {
		// the editing is complete
		if (e instanceof EditingCompleted) {
			handleEditingComplete();
		}

		// collect notifications
		if (e instanceof ProjectDataChanged) {
			final ProjectDataChanged p = (ProjectDataChanged) e;
			modifiedProjectData.add(p.getNotification());
			handleProjectDataChanged(p);
		}

		// collect containers
		if (e instanceof ContainerDataChanged) {
			final ContainerDataChanged c = (ContainerDataChanged) e;
			modifiedContainer.add(c.getContainer());
			handleContainerDataChanged(c);
		}
	}

	protected void handleEditingComplete() {
		// test if the part is exposed
		final MPart part = getToolboxPart();
		final ViewVisibility visibility = MApplicationElementExtensions
				.getVisibility(part);
		if (visibility == ViewVisibility.EXPOSED) {
			updateView();
		} else {
			updateViewDelayed = true;
		}
	}

	@SuppressWarnings("static-method")
	protected void handleNewTableType(final NewTableTypeEvent e) {
		// override to handle event
		LOGGER.debug("{} ignored", e); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		// override to handle event
		LOGGER.debug("{} ignored", e); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	protected void handleSessionDirtyEvent(final SessionDirtyChanged e) {
		// override to handle event
		LOGGER.debug("{} ignored", e); //$NON-NLS-1$
	}

	protected void updateView() {
		// reset delay state
		updateViewDelayed = false;

		// dispatch collected notifications and containers
		if (!modifiedProjectData.isEmpty() || banderole.isOutdated()) {
			updateViewProjectDataChanged(modifiedProjectData);
			modifiedProjectData.clear();
		}
		if (!modifiedContainer.isEmpty() || banderole.isOutdated()) {
			updateViewContainerDataChanged(modifiedContainer);
			modifiedContainer.clear();
		}
	}

	@SuppressWarnings("static-method")
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		// override to update
		LOGGER.debug("update for {} ignored", container); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		// override to update
		LOGGER.debug("update for {} ignored", notifications); //$NON-NLS-1$
	}
}
