/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.set.basis.ui.dialogs.AboutDialog;
import org.eclipse.set.basis.ui.dialogs.InformationDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

/**
 * This Utility provides common dialogs.
 * 
 * @author Schaefer
 */
public class CommonDialogs {

	/**
	 * Status type severity (bit mask, value 8) indicating this status
	 * represents a cancellation
	 */
	public static final int CANCEL = 0x08;

	/**
	 * Status type severity (bit mask, value 4) indicating this status
	 * represents an error.
	 */
	public static final int ERROR = 0x04;

	/**
	 * Status type severity (bit mask, value 1) indicating this status is
	 * informational only.
	 */
	public static final int INFO = 0x01;

	/**
	 * Status severity constant (value 0) indicating this status represents the
	 * nominal case. This constant is also used as the status code representing
	 * the nominal case.
	 */
	public static final int OK = 0;

	/**
	 * Status type severity (bit mask, value 2) indicating this status
	 * represents a warning.
	 */
	public static final int WARNING = 0x02;

	/**
	 * Displays an about dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param title
	 *            the dialog title
	 * @param info
	 *            the about info
	 * @param versionInfo
	 *            the version info
	 * @param license
	 *            the license text
	 */
	public static void openAbout(final Shell shell, final String title,
			final String info, final VersionInfo[] versionInfo,
			final String license) {
		openAbout(shell, title, info, versionInfo, license, null, null);
	}

	/**
	 * Displays an about dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param title
	 *            the dialog title
	 * @param info
	 *            the about info
	 * @param versionInfo
	 *            the version info
	 * @param license
	 *            the license text
	 * @param leftImage
	 *            the left image
	 * @param rightImage
	 *            the right image
	 */
	public static void openAbout(final Shell shell, final String title,
			final String info, final VersionInfo[] versionInfo,
			final String license, final ImageDescriptor leftImage,
			final ImageDescriptor rightImage) {
		final AboutDialog aboutDialog = new AboutDialog(shell, title, info,
				versionInfo, license, leftImage, rightImage);
		aboutDialog.create();
		final Shell aboutShell = aboutDialog.getShell();
		final Point size = aboutShell.getSize();
		aboutShell.setSize(size.x + 1, size.y + 1);
		aboutShell.setSize(size.x, size.y);
		aboutDialog.open();
	}

	/**
	 * Displays an information dialog.
	 * 
	 * @param shell
	 *            the parent shell
	 * @param title
	 *            the dialog title
	 * @param message
	 *            the message text
	 * @param severity
	 *            the severity
	 * @param causeLabeltext
	 *            the label text of an error (if message is not ok)
	 * @param cause
	 *            the cause of an error (if message is not ok)
	 * @param throwable
	 *            an exception (if occurred)
	 */
	public static void openInformation(final Shell shell, final String title,
			final String message, final int severity,
			final String causeLabeltext, final String cause,
			final Throwable throwable) {
		final InformationDialog dialog = new InformationDialog(shell, title,
				message, severity, causeLabeltext, cause, throwable);
		dialog.create();
		final Shell dialogShell = dialog.getShell();
		final Point size = dialogShell.getSize();
		dialogShell.setSize(size.x, Math.round(size.y * 1.3f));
		dialog.open();
	}
}
