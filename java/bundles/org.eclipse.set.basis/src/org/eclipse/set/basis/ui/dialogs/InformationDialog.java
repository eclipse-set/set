/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Dittmer
 *
 */
public class InformationDialog extends Dialog {

	private static final String DIALOG_FONT_NAME = "Segoe UI Semibold"; //$NON-NLS-1$
	private final static FontDescriptor INFO_FONT_DESCRIPTOR = FontDescriptor
			.createFrom(DIALOG_FONT_NAME, 10, SWT.BOLD);

	private static Point computeControl(final Control control) {
		return control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

	private static Point computeControls(final Control... controls) {
		final Point result = new Point(0, 0);
		for (final Control control : controls) {
			result.x = Math.max(result.x, computeControl(control).x);
			result.y = result.y + computeControl(control).y;
		}
		return result;
	}

	private final String cause;
	private final String causeLabeltext;
	private Button detailsButton;
	private Text detailsText;
	private final Throwable exception;

	private final String info;

	private final int severity;

	private final String title;

	/**
	 * Creates the information dialog without error.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param title
	 *            the dialog title
	 * @param info
	 *            the message text
	 */
	public InformationDialog(final Shell parentShell, final String title,
			final String info) {
		super(parentShell);
		setShellStyle(SWT.MAX | SWT.RESIZE | SWT.TITLE);
		this.info = info;
		this.title = title;
		this.causeLabeltext = ""; //$NON-NLS-1$
		this.cause = ""; //$NON-NLS-1$
		this.exception = null;
		this.severity = OK;

	}

	/**
	 * Creates the information dialog with error.
	 * 
	 * @wbp.parser.constructor
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param title
	 *            the dialog title
	 * @param info
	 *            the message text
	 * @param causeLabeltext
	 *            the label text of an error (if message is not ok)
	 * @param cause
	 *            the cause of an error (if message is not ok)
	 * @param throwable
	 *            an exception (if occurred)
	 * @param severity
	 *            the severity
	 */
	public InformationDialog(final Shell parentShell, final String title,
			final String info, final int severity, final String causeLabeltext,
			final String cause, final Throwable throwable) {
		super(parentShell);
		setShellStyle(SWT.MAX | SWT.RESIZE | SWT.TITLE);
		this.info = info;
		this.title = title;
		this.causeLabeltext = causeLabeltext;
		this.cause = cause;
		this.exception = throwable;
		this.severity = severity;

	}

	/**
	 * @return cause
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * @return the label text
	 */
	public String getCauseLabeltext() {
		return causeLabeltext;
	}

	/**
	 * @return the details button
	 */
	public Button getDetailsButton() {
		return detailsButton;
	}

	/**
	 * @return the details text
	 */
	public Text getDetailsText() {
		return detailsText;
	}

	/**
	 * @return exception (if occurred)
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * @return text of message
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Returns the severity. The severities are as follows (in descending
	 * order):
	 * <ul>
	 * <li><code>CANCEL</code> - cancelation occurred</li>
	 * <li><code>ERROR</code> - a serious error (most severe)</li>
	 * <li><code>WARNING</code> - a warning (less severe)</li>
	 * <li><code>INFO</code> - an informational ("fyi") message (least severe)
	 * </li>
	 * <li><code>OK</code> - everything is just fine</li>
	 * </ul>
	 *
	 * @return the severity: one of <code>OK</code>, <code>ERROR</code>,
	 *         <code>INFO</code>, <code>WARNING</code>, or <code>CANCEL</code>
	 */
	public int getSeverity() {
		return severity;
	}

	/**
	 * @return window title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns whether this status indicates everything is okay (neither info,
	 * warning, nor error).
	 *
	 * @return <code>true</code> if this status has severity <code>OK</code>,
	 *         and <code>false</code> otherwise
	 */
	public boolean isOK() {
		return severity == OK;
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		if (severity != OK) {
			detailsButton = createButton(parent, IDialogConstants.DETAILS_ID,
					IDialogConstants.SHOW_DETAILS_LABEL, true);
			detailsButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseDoubleClick(final MouseEvent e) {
					toggleDetails();
				}

				@Override
				public void mouseDown(final MouseEvent e) {
					toggleDetails();
				}

				@Override
				public void mouseUp(final MouseEvent e) {
					// do nothing
				}

				/**
				 * Displays or hides derail information about an error
				 */
				private void toggleDetails() {
					final boolean isVisible = getDetailsText().isVisible();
					if (isVisible) {
						getDetailsButton()
								.setText(IDialogConstants.HIDE_DETAILS_LABEL);
					} else {
						getDetailsButton()
								.setText(IDialogConstants.SHOW_DETAILS_LABEL);
					}
					getDetailsText().setVisible(!isVisible);
				}
			});
		}
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		parent.getShell().setText(title);
		container.setLayout(new GridLayout(2, false));

		final LocalResourceManager localResourceManager = new LocalResourceManager(
				JFaceResources.getResources(), container);
		final Font infoFont = localResourceManager.create(INFO_FONT_DESCRIPTOR);

		final Text infoText = new Text(container, SWT.CENTER);
		infoText.setLayoutData(
				new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		infoText.setFont(infoFont);
		infoText.setText(info);
		infoText.setEditable(false);
		final Point size = computeControls(infoText);
		parent.getShell().setSize((int) (size.x * 1.25), size.y * 6);

		if (!cause.isEmpty()) {
			final Label causeLabel = new Label(container, SWT.NONE);
			causeLabel.setFont(infoFont);
			causeLabel.setLayoutData(
					new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1));
			causeLabel.setText(causeLabeltext);

			final Text causeText = new Text(container, SWT.LEFT);
			causeText.setFont(infoFont);
			causeText.setText(cause);
			causeText.setEditable(false);
			final Point size2 = computeControls(infoText, causeText);
			parent.getShell().setSize((int) (size2.x * 1.25), size2.y * 6);
		}

		if (exception != null) {
			detailsText = new Text(container, SWT.H_SCROLL);
			detailsText.setLayoutData(
					new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
			if (exception.getMessage() != null) {
				detailsText.setText(exception.getMessage());
			}
			detailsText.setVisible(false);
			detailsText.setEditable(false);
		}

		return container;
	}

}
