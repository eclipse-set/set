/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiPredicate;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.eclipse.set.basis.InitializationData;
import org.eclipse.set.core.services.dialog.DialogService;

/**
 * Common functions for filename initialization.
 * 
 * @param <T>
 *            initialization data type
 * 
 * @author Schaefer
 */
public abstract class FilenameInitialization<T extends InitializationData>
		extends TitleAreaDialog {

	private static Color invalid() {
		return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	}

	private static Color valid() {
		return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	}

	private BiPredicate<Shell, Path> createDirectoryPermission;
	private final DialogService dialogService;
	private BiPredicate<Shell, T> initAction;

	/**
	 * @param parentShell
	 *            the parent shell
	 * @param dialogService
	 *            the dialog service
	 */
	public FilenameInitialization(final Shell parentShell,
			final DialogService dialogService) {
		super(parentShell);
		this.dialogService = dialogService;
	}

	@Override
	public void create() {
		super.create();
		setTitle(getPageTitle());
		setMessage(getInitMessage(), IMessageProvider.INFORMATION);
	}

	/**
	 * @return the dialog title
	 */
	public abstract String getDialogTitle();

	/**
	 * @return the text for the initialization button
	 */
	public abstract String getInitButtonText();

	/**
	 * @return the initialization data collected by this dialog
	 */
	public abstract T getInitializationData();

	/**
	 * @return the message describing the initialization prompt
	 */
	public abstract String getInitMessage();

	/**
	 * @return the page title
	 */
	public abstract String getPageTitle();

	/**
	 * @param createDirectoryPermission
	 *            function for asking the user for a permission to create a
	 *            directory
	 */
	public void setCreateDirectoryPermission(
			final BiPredicate<Shell, Path> createDirectoryPermission) {
		this.createDirectoryPermission = createDirectoryPermission;
	}

	/**
	 * Sets the initialization action. The initialization action shall return
	 * true, if the action is successful and false otherwise.
	 * 
	 * @param initAction
	 *            the initialization action
	 */
	public void setInitAction(final BiPredicate<Shell, T> initAction) {
		this.initAction = initAction;
	}

	private boolean checkCreateDirectory() {
		final Path dir = Paths.get(getInitializationData().getDirectory());
		final File file = dir.toFile();

		// if the file exists the check succeeds
		if (file.exists()) {
			return true;
		}

		// ask if a new directory should be created
		final boolean permission = createDirectoryPermission.test(getShell(),
				dir);

		if (permission) {
			// create the directory
			file.mkdirs();
			return true;
		}

		// we did not create the directory
		return false;
	}

	protected abstract String compileTooltip(final String pattern);

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(getDialogTitle());
	}

	@Override
	protected Control createButtonBar(final Composite parent) {
		final Control control = super.createButtonBar(parent);
		validate();
		return control;
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, getInitButtonText(), true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY),
				false);
		validate();
	}

	protected DialogService getDialogService() {
		return dialogService;
	}

	@Override
	protected void okPressed() {
		if (checkCreateDirectory()) {
			if (initAction.test(getShell(), getInitializationData())) {
				super.okPressed();
			}
		}
	}

	protected abstract void validate();

	protected boolean validate(final Text text, final String pattern) {
		if (text.getText().matches(pattern)) {
			text.setBackground(valid());
			text.setToolTipText(""); //$NON-NLS-1$
			return true;
		}
		text.setBackground(invalid());
		text.setToolTipText(compileTooltip(pattern));
		return false;
	}
}
