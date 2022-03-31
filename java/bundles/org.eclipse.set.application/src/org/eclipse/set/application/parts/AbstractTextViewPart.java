/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.parts;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.extensions.IModelSessionExtensions;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.text.LineTextViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.Container_AttributeGroup;

/**
 * Displays PlanPro model as text.
 * 
 * @param <S>
 *            the session type
 * 
 * @author Schaefer
 */
public abstract class AbstractTextViewPart<S extends IModelSession>
		extends BasePart<S> {

	@Inject
	@Translation
	private Messages messages;

	private final boolean showProgress;

	@Inject
	IModelSession session;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	/**
	 * @param sessionType
	 *            the session type
	 * @param showProgress
	 *            whether to show a progress information
	 */
	public AbstractTextViewPart(final Class<S> sessionType,
			final boolean showProgress) {
		super(sessionType);
		this.showProgress = showProgress;
	}

	@Override
	protected void createView(final Composite parent) {
		parent.setLayout(new FillLayout());
		try {
			if (showProgress) {
				getDialogService().showProgressUISync(getToolboxShell(),
						messages.AbstractTextViewPart_Progress, () -> {
							try {
								getViewer().init(parent, IModelSessionExtensions
										.getLines(session));
							} catch (final IOException e) {
								throw new RuntimeException(e);
							}
						});
			} else {
				getViewer().init(parent,
						IModelSessionExtensions.getLines(session));
			}

			// test for outdated view
			if (session.isDirty()) {
				setOutdated(true);
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new SaveAndRefreshAction(this);
	}

	protected abstract LineTextViewer getViewer();

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
		final DialogService dialogService = getDialogService();
		if (isOutdated()) {
			if (showProgress) {
				dialogService.showProgressUISync(getToolboxShell(),
						messages.AbstractTextViewPart_UpdateView,
						new Runnable() {
							@Override
							public void run() {
								try {
									getViewer().update(IModelSessionExtensions
											.getLines(session));
									setOutdated(false);
								} catch (final IOException ex) {
									dialogService.error(getToolboxShell(), ex);
								}
							}
						});
			} else {
				try {
					getViewer()
							.update(IModelSessionExtensions.getLines(session));
					setOutdated(false);
				} catch (final IOException ex) {
					dialogService.error(getToolboxShell(), ex);
				}
			}
		}
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		updateViewer();
	}
}
