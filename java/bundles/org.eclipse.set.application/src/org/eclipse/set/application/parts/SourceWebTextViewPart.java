/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.parts;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.ProblemMessage;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.browser.RequestHandler.Request;
import org.eclipse.set.browser.RequestHandler.Response;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.FileWebBrowser;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.EventRegistration;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.EventHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Displays PlanPro model as source text via the monaco editor.
 * 
 * @author Stuecker
 */
public class SourceWebTextViewPart extends BasePart<IModelSession> {
	private static final String JUMP_TO_LINE_FUNCTION = "window.planproJumpToLine"; //$NON-NLS-1$
	private static final String UPDATE_PROBLEMS_FUNCTION = "window.planproUpdateProblems"; //$NON-NLS-1$

	@Inject
	@Translation
	private Messages messages;
	@Inject
	IModelSession session;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	private FileWebBrowser browser;
	private EventRegistration eventRegistration;

	private final EventHandler problemsChangeEventHandler = event -> onProblemsChange();
	private static final String TEXT_VIEWER_PATH = "./web/textviewer"; //$NON-NLS-1$
	private static final String PROBLEMS_JSON = "problems.json"; //$NON-NLS-1$
	private static final String MODEL_PPXML = "model.ppxml"; //$NON-NLS-1$

	/**
	 * Constructor
	 */
	public SourceWebTextViewPart() {
		super(IModelSession.class);
	}

	private void onProblemsChange() {
		browser.executeJavascript(
				String.format("%s()", UPDATE_PROBLEMS_FUNCTION)); //$NON-NLS-1$
	}

	@Override
	protected void createView(final Composite parent) {
		parent.setLayout(new FillLayout());
		browser = new FileWebBrowser(parent);
		try {
			browser.serveRootDirectory(Path.of(TEXT_VIEWER_PATH));
			browser.serveFile(MODEL_PPXML, "text/plain", //$NON-NLS-1$
					session.getToolboxFile().getModelPath());
			browser.serveUri(PROBLEMS_JSON,
					SourceWebTextViewPart::serveProblems);
			browser.setToolboxUrl("index.html"); //$NON-NLS-1$
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

		getBroker().subscribe(Events.PROBLEMS_CHANGED,
				problemsChangeEventHandler);
	}

	@SuppressWarnings("unchecked")
	private static void serveProblems(
			@SuppressWarnings("unused") final Request request,
			final Response response) throws JsonProcessingException {
		// Get the list of validation problems
		final Iterable<Object> problems = Services.getCacheService()
				.getCache(ToolboxConstants.CacheId.PROBLEM_MESSAGE).values();
		final List<ProblemMessage> problemMessages = new ArrayList<>();
		problems.forEach(problemContainer -> problemMessages
				.addAll((List<ProblemMessage>) problemContainer));

		response.setMimeType("application/json;charset=UTF-8"); //$NON-NLS-1$
		response.setStatus(HttpServletResponse.SC_OK);
		response.setResponseData(
				new ObjectMapper().writerWithDefaultPrettyPrinter()
						.writeValueAsString(problemMessages));

	}

	private void handleJumpToSourceLineEvent(
			final JumpToSourceLineEvent event) {
		if (event.getLineNumber() != -1) {
			this.jumpToLine(event);
		} else if (!event.getObjectGuid().isEmpty()) {
			this.jumpToGUID(event);
		}
	}

	@SuppressWarnings("boxing")
	private void jumpToLine(final JumpToSourceLineEvent event) {
		final String js = String.format("""
				{
					let intervalId = 0
					const jumpToLineWrapper = () => {
						if(%s) {
							%s(%d);
							clearInterval(intervalId);
						}
					}
					intervalId = setInterval(jumpToLineWrapper, 100);
				}
				""", JUMP_TO_LINE_FUNCTION, JUMP_TO_LINE_FUNCTION,
				event.getLineNumber());
		browser.executeJavascript(js);
	}

	private void jumpToGUID(final JumpToSourceLineEvent event) {
		final TableType tableTyle = getModelSession().getTableType();
		String tableState = ""; //$NON-NLS-1$
		switch (tableTyle) {
		case INITIAL: {
			tableState = "initial"; //$NON-NLS-1$
			break;
		}
		case FINAL: {
			tableState = "final"; //$NON-NLS-1$
			break;
		}
		case DIFF: {
			tableState = "diff"; //$NON-NLS-1$
			break;
		}
		default:
			break;
		}

		final String js = String.format("""
				{
					let intervalId = 0
					const jumpToLineWrapper = () => {
						if(%s) {
							%s('%s', '%s');
							clearInterval(intervalId);
						}
					}
					intervalId = setInterval(jumpToLineWrapper, 100);
				}
				""", JUMP_TO_GUID_FUNCTION, JUMP_TO_GUID_FUNCTION,
				event.getObjectGuid(), tableState);
		browser.executeJavascript(js);

	}

	@PreDestroy
	private void preDestroy() {
		eventRegistration.unsubscribeAll();
		getBroker().unsubscribe(problemsChangeEventHandler);
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
