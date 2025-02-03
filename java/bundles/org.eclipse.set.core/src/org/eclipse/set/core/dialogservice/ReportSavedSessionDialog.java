/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice;

import java.nio.file.Path;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.core.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Report the result of a save session action.
 * 
 * @author Schaefer
 */
public class ReportSavedSessionDialog extends MessageDialog {

	private static int getKind(final IModelSession session) {
		final Outcome outcome = session
				.getValidationsOutcome(ValidationResult::getOutcome);
		switch (outcome) {
		case VALID, NOT_SUPPORTED:
			return MessageDialog.INFORMATION;
		case INVALID:
			return MessageDialog.WARNING;
		default:
			throw new IllegalArgumentException(outcome.toString());
		}
	}

	private final Messages messages;
	final int kind;
	final IModelSession session;

	/**
	 * @param parent
	 *            the parent shell
	 * @param session
	 *            the session
	 * @param messages
	 *            the translations
	 */
	public ReportSavedSessionDialog(final Shell parent,
			final IModelSession session, final Messages messages) {
		super(parent, messages.DialogService_ReportSavedSessionTitle, null,
				getMessage(session, messages)
						+ getSaveFixMessage(session, messages),
				getKind(session), new String[] { JFaceResources
						.getString(IDialogLabelKeys.OK_LABEL_KEY) },
				0);
		this.session = session;
		this.messages = messages;
		kind = getKind(session);
	}

	private static String getMessage(final IModelSession session,
			final Messages messages) {
		final Outcome outcome = session
				.getValidationsOutcome(ValidationResult::getOutcome);
		final Path location = session.getToolboxFile().getPath();
		switch (outcome) {
		case VALID, NOT_SUPPORTED:
			return String.format(
					messages.DialogService_ReportSavedSessionValidPattern,
					location.toString());
		case INVALID:
			return String.format(
					messages.DialogService_ReportSavedSessionInvalidPattern,
					location.toString());
		default:
			throw new IllegalArgumentException(outcome.toString());
		}
	}

	private static String getSaveFixMessage(final IModelSession session,
			final Messages messages) {
		switch (session.getSaveFixResult()) {
		case GLOBAL:
			return "\n\n" + messages.DialogService_DefaultsFilledGlobal; //$NON-NLS-1$
		case OBJEKTMANAGEMENT:
			return "\n\n" //$NON-NLS-1$
					+ messages.DialogService_DefaultsFilledObjektmanagement;
		case NONE:
		default:
			return ""; //$NON-NLS-1$
		}
	}

	@Override
	public int open() {
		if (!isSuppressed()) {
			return super.open();
		}
		return IDialogConstants.OK_ID;
	}

	private boolean isSuppressed() {
		return session.isReportSavedDialogSuppressed(kind);
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		final Composite customArea = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(customArea);

		// suppress dialog
		final Button checkbox = new Button(customArea, SWT.CHECK);
		checkbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				session.setReportSavedDialogSuppressed(kind,
						checkbox.getSelection());
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				session.setReportSavedDialogSuppressed(kind,
						checkbox.getSelection());
			}
		});
		GridDataFactory.swtDefaults().applyTo(checkbox);
		final Label label = new Label(customArea, SWT.NONE);
		label.setText(messages.DialogService_ReportSavedSession_SuppressDialog);
		GridDataFactory.fillDefaults().applyTo(label);

		return customArea;
	}
}
