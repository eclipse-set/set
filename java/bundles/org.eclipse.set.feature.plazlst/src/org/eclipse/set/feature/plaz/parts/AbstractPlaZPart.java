/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plaz.parts;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.exceptions.XsltServiceException;
import org.eclipse.set.basis.exceptions.XsltTransformationAbortedException;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.threads.Threads;
import org.eclipse.set.feature.plaz.Messages;
import org.eclipse.set.feature.plaz.dialog.PlazDialogService;
import org.eclipse.set.feature.plaz.service.PlazService;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

/**
 * Common PlaZ functionality.
 * 
 * @author Schaefer
 */
public abstract class AbstractPlaZPart extends BasePart {
	protected abstract static class PlaZConfiguration {
		private boolean aborted = false;

		abstract void create(final IProgressMonitor monitor);

		abstract boolean isAvailable();

		abstract void setAvailable(boolean value);

		boolean isAborted() {
			return aborted;
		}

		void setAborted(final boolean value) {
			aborted = value;
		}
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractPlaZPart.class);

	@Inject
	private PlazDialogService plazDialogService;

	@Inject
	@Translation
	protected Messages messages;

	@Inject
	protected PlazService plaZService;

	/**
	 * Create the part.
	 */
	@Inject
	protected AbstractPlaZPart() {
		super();
	}

	protected void executePlaZ(final PlaZConfiguration config)
			throws UserAbortion {
		try {
			// if the report is available the generated report is used
			if (!config.isAvailable()) {
				// we start our new defined thread with a progress dialog
				new ProgressMonitorDialog(getToolboxShell()).run(true, true,
						m -> run(config, m));
				if (config.isAborted()) {
					throw new UserAbortion();
				}
			}
		} catch (final InvocationTargetException | InterruptedException e) {
			handleException(getToolboxShell(), getToolboxPart(), e);
		}
	}

	private void run(final PlaZConfiguration config,
			final IProgressMonitor monitor) {
		Threads.stopCurrentOnCancel(monitor);
		monitor.beginTask(messages.PlazExecutionPart_monitorDialogText, 4);
		// this is the actual creation of the report
		try {
			config.create(monitor);
			// announce the report availability
			config.setAvailable(true);
			// stop progress
			monitor.done();
		} catch (final XsltTransformationAbortedException e) {
			// The transformation has been interrupted
			config.setAborted(true);
		}
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
		resetReportAvailability();
	}

	protected String handleException(final Shell shell, final MPart part,
			final Exception e) {
		LOGGER.error(e.toString(), e);
		Throwable reportException = e;
		if (e instanceof InvocationTargetException) {
			reportException = ((InvocationTargetException) e)
					.getTargetException();
		}
		plazDialogService.plazError(shell);
		MApplicationElementExtensions.setViewState(part,
				ToolboxViewState.ERROR);
		if (reportException instanceof XsltServiceException) {
			return ((XsltServiceException) reportException)
					.getExpandedMessage();
		}
		return String.format("%s: %s", //$NON-NLS-1$
				reportException.getClass().getSimpleName(),
				reportException.getMessage());
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
		resetReportAvailability();
	}

	protected abstract void resetReportAvailability();

	/**
	 * @throws UserAbortion
	 *             if the update was aborted by the user
	 */
	protected void updatePlazView() throws UserAbortion {
		// override to update specific view
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		try {
			updatePlazView();
		} catch (final UserAbortion e) {
			// Do nothing
		}
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		try {
			updatePlazView();
		} catch (final UserAbortion e) {
			// Do nothing
		}
	}
}
