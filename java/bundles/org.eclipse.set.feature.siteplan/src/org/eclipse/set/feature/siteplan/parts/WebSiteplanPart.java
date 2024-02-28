/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.parts;

import java.io.IOException;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.plazmodel.check.CRSValid;
import org.eclipse.set.feature.siteplan.Messages;
import org.eclipse.set.feature.siteplan.SiteplanBrowser;
import org.eclipse.set.feature.siteplan.SiteplanConstants;
import org.eclipse.set.feature.siteplan.browserfunctions.GetSessionStateBrowserFunction;
import org.eclipse.set.feature.siteplan.browserfunctions.JumpToSourceLineBrowserFunction;
import org.eclipse.set.feature.siteplan.browserfunctions.LayoutChangeCRSBrowserFunction;
import org.eclipse.set.feature.siteplan.browserfunctions.SelectFolderDialogBrowserFunction;
import org.eclipse.set.feature.siteplan.browserfunctions.SignalSelectBrowserFunction;
import org.eclipse.set.feature.siteplan.browserfunctions.TableSelectRowBrowserFunction;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.FunctionalToolboxEventHandler;
import org.eclipse.set.utils.events.NewTableTypeEvent;
import org.eclipse.set.utils.events.SelectedRowEvent;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Provides a part for the web-based site plan by launching an embedded
 * webserver and webbrowser.
 * 
 * @author Stuecker
 */
public class WebSiteplanPart extends BasePart {
	private SignalSelectBrowserFunction signalSelectBrowserFunction;

	@Inject
	@Translation
	protected Messages messages;

	@Inject
	IEclipseContext applicationContext;

	@Inject
	ToolboxPartService partService;

	@Inject
	private DialogService dialogService;

	@Inject
	CacheService cacheService;

	ToolboxEventHandler<SelectedRowEvent> selectRowEvent;

	private SiteplanBrowser webBrowser;

	/**
	 * Create the part.
	 */
	public WebSiteplanPart() {
		super();
	}

	@PostConstruct
	private void postConstruct() {
		selectRowEvent = new FunctionalToolboxEventHandler<>(this::handleEvent);
		final String topic = SelectedRowEvent
				.getTopic(SiteplanConstants.SIGNAL_TABLE);
		ToolboxEvents.subscribe(getBroker(), SelectedRowEvent.class,
				selectRowEvent, topic);
	}

	private void handleEvent(final SelectedRowEvent selectedRowEvent) {
		signalSelectBrowserFunction.execute(selectedRowEvent);
	}

	@Override
	protected void handleNewTableType(final NewTableTypeEvent e) {
		webBrowser.executeJavascript(String.format("%s()", //$NON-NLS-1$
				SiteplanConstants.WINDOW_PLANPRO_NEW_TABLE_TYPE));
	}

	@PreDestroy
	private void preDestroyWebSiteplanPart() {
		ToolboxEvents.unsubscribe(getBroker(), selectRowEvent);
		webBrowser.stop();
	}

	@Override
	protected void createView(final Composite parent) {
		if (!isCRSValid()) {
			MApplicationElementExtensions.setViewState(getToolboxPart(),
					ToolboxViewState.ERROR);
			return;
		}
		try {
			webBrowser = new SiteplanBrowser(parent, applicationContext,
					getToolboxShell());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		registerJavascriptFunctions();
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true)
				.span(2, 1).applyTo(webBrowser.getControl());
		webBrowser.setUrl("https://toolbox/?"); //$NON-NLS-1$

	}

	private boolean isCRSValid() {
		final CRSValid test = new CRSValid();
		final IModelSession modelSession = applicationContext
				.get(IModelSession.class);
		final List<PlazError> listValid = test.run(modelSession);
		if (!listValid.isEmpty()) {
			final String fileName = modelSession.getToolboxFile().getPath()
					.toString();
			if (!dialogService.sitePlanError(getToolboxShell(), fileName)) {
				return false;
			}
		}
		return true;
	}

	private void registerJavascriptFunctions() {
		webBrowser.registerJSFunction(new TableSelectRowBrowserFunction(
				webBrowser, "planproSelectTableRow", getBroker())); //$NON-NLS-1$
		webBrowser.registerJSFunction(new SelectFolderDialogBrowserFunction(
				webBrowser, "planproSelectFolderDialog", getToolboxShell(), //$NON-NLS-1$
				getDialogService()));
		webBrowser.registerJSFunction(new GetSessionStateBrowserFunction(
				webBrowser, "planproGetSessionState", getModelSession())); //$NON-NLS-1$
		webBrowser.registerJSFunction(new JumpToSourceLineBrowserFunction(
				webBrowser, "planproJumpToTextView", getBroker(), partService)); //$NON-NLS-1$
		webBrowser.registerJSFunction(new LayoutChangeCRSBrowserFunction(
				webBrowser, "planproChangeLayoutCRS", cacheService)); //$NON-NLS-1$
		signalSelectBrowserFunction = new SignalSelectBrowserFunction(
				webBrowser);
	}
}
