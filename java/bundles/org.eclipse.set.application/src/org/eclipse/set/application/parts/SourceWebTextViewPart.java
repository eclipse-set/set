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
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.ProblemMessage;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.browser.RequestHandler.Request;
import org.eclipse.set.browser.RequestHandler.Response;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.font.FontService;
import org.eclipse.set.model.validationreport.ContainerContent;
import org.eclipse.set.model.validationreport.ObjectScope;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Displays PlanPro model as source text via the monaco editor.
 * 
 * @author Stuecker
 */
@SuppressWarnings("nls")
public class SourceWebTextViewPart extends BasePart {
	private static final String JUMP_TO_LINE_FUNCTION = "window.planproJumpToLine";
	private static final String JUMP_TO_GUID_FUNCTION = "window.planproJumpToGuid";
	private static final String UPDATE_PROBLEMS_FUNCTION = "window.planproUpdateProblems";

	private static final String SWITCH_MODEL_VIEW_FUNCTION = "window.planproSwitchModel";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SourceWebTextViewPart.class);

	@Inject
	@Translation
	private Messages messages;
	@Inject
	IModelSession session;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	@Inject
	FontService fontService;

	@Inject
	CacheService cacheService;

	private FileWebBrowser browser;
	private EventRegistration eventRegistration;

	private final EventHandler problemsChangeEventHandler = event -> onProblemsChange();
	private static final String TEXT_VIEWER_PATH = "./web/textviewer";
	private static final String PROBLEMS_JSON = "problems.json";
	private static final String MODEL_PPXML = "model.ppxml";
	private static final String LAYOUT_XML = "layout.xml";

	private void onProblemsChange() {
		browser.executeJavascript(
				String.format("%s()", UPDATE_PROBLEMS_FUNCTION));
	}

	@Override
	protected void createView(final Composite parent) {
		parent.setLayout(new FillLayout());
		browser = new FileWebBrowser(parent);
		try {
			browser.serveRootDirectory(Path.of(TEXT_VIEWER_PATH));
			browser.serveFile(MODEL_PPXML, "text/plain",
					session.getToolboxFile().getModelPath());
			browser.serveUri(PROBLEMS_JSON, this::serveProblems);
			browser.serveFile(LAYOUT_XML, "text/plain",
					session.getToolboxFile().getLayoutPath());

			browser.setToolboxUrl("index.html");
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
	private void serveProblems(
			@SuppressWarnings("unused") final Request request,
			final Response response) throws JsonProcessingException {
		// Get the list of validation problems
		final Iterable<Object> problems = cacheService
				.getCache(ToolboxConstants.CacheId.PROBLEM_MESSAGE).values();
		final List<ProblemMessage> problemMessages = new ArrayList<>();
		problems.forEach(problemContainer -> problemMessages
				.addAll((List<ProblemMessage>) problemContainer));

		response.setMimeType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setResponseData(
				new ObjectMapper().writerWithDefaultPrettyPrinter()
						.writeValueAsString(problemMessages));

	}

	private void handleJumpToSourceLineEvent(
			final JumpToSourceLineEvent event) {
		final Pair<ObjectScope, Integer> lineNumber = event.getLineNumber();
		final String objectGuid = event.getObjectGuid();
		if (lineNumber.getSecond().intValue() != -1) {
			this.jumpToLine(lineNumber);
		} else if (objectGuid != null && !objectGuid.isEmpty()) {
			this.jumpToGUID(objectGuid);
		} else {
			LOGGER.warn("Invalid jump to line event ignored.");
		}
	}

	private void jumpToLine(final Pair<ObjectScope, Integer> lineNumber) {
		String modelName = ""; //$NON-NLS-1$
		if (lineNumber.getFirst() == ObjectScope.LAYOUT) {
			modelName = ContainerContent.LAYOUT.getLiteral();
		} else {
			modelName = ContainerContent.MODEL.getLiteral();
		}
		@SuppressWarnings("boxing")
		final String js = String.format("""
				{
					let intervalId = 0
					const jumpToLineWrapper = () => {
						if(%s) {
							%s('%s')
						}
						if(%s) {
							%s(%d);
							clearInterval(intervalId);
						}
					}
					intervalId = setInterval(jumpToLineWrapper, 100);
				}
				""", SWITCH_MODEL_VIEW_FUNCTION, SWITCH_MODEL_VIEW_FUNCTION,
				modelName, JUMP_TO_LINE_FUNCTION, JUMP_TO_LINE_FUNCTION,
				lineNumber.getSecond().intValue());
		browser.executeJavascript(js);
	}

	private void jumpToGUID(final String guid) {
		TableType tableType = getModelSession().getTableType();
		if (tableType == null) {
			tableType = getModelSession().getNature().getDefaultContainer()
					.getTableTypeForTables();
		}
		String tableState = "";
		switch (tableType) {
		case INITIAL: {
			tableState = "initial";
			break;
		}
		case FINAL: {
			tableState = "final";
			break;
		}
		case DIFF: {
			tableState = "diff";
			break;
		}
		case SINGLE: {
			tableState = "single";
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
				""", JUMP_TO_GUID_FUNCTION, JUMP_TO_GUID_FUNCTION, guid,
				tableState);
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
			setOutdated(false);
		}
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		updateViewer();
	}
}
