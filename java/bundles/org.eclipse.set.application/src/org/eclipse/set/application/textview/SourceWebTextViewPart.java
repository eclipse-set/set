/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.textview;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.WebBrowser;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.EventRegistration;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Displays PlanPro model as source text via the monaco editor.
 * 
 * @author Stuecker
 */
public class SourceWebTextViewPart extends BasePart<IModelSession> {
	private static final String JUMP_TO_LINE_FUNCTION = "window.planproJumpToLine"; //$NON-NLS-1$
	@Inject
	@Translation
	private Messages messages;
	@Inject
	IModelSession session;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	private WebBrowser browser;
	private EventRegistration eventRegistration;

	private final TextViewServer server = new TextViewServer();

	/**
	 * Create View.
	 */
	public SourceWebTextViewPart() {
		super(IModelSession.class);
	}

	@Override
	protected void createView(final Composite parent) {
		parent.setLayout(new FillLayout());
		browser = new WebBrowser(parent);
		try {
			server.configure();
			server.serveFile(session.getToolboxFile().getModelPath());
			server.start();
			browser.setUrl(server.getRootUrl() + "index.html"); //$NON-NLS-1$
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		// test for outdated view
		if (session.isDirty()) {
			setOutdated(true);
		}
		eventRegistration = new EventRegistration(getBroker());
		eventRegistration.registerHandler(JumpToSourceLineEvent.class,
				this::handleJumpToSourceLineEvent);
	}

	@SuppressWarnings("boxing")
	private void handleJumpToSourceLineEvent(
			final JumpToSourceLineEvent event) {
		browser.executeJavascript(String.format("%s(%d)", JUMP_TO_LINE_FUNCTION, //$NON-NLS-1$
				event.getLineNumber()));
	}

	@PreDestroy
	private void preDestroy() {
		eventRegistration.unsubscribeAll();
		try {
			server.stop();
		} catch (final Exception e) {
			// Ignore exception
		}
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new SaveAndRefreshAction(this);
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		updateViewer();
	}

	protected void updateViewer() {
		if (isOutdated()) {
			browser.refresh();
		}
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		updateViewer();
	}
}
