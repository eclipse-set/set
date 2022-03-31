/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.widgets;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.eclipse.set.core.services.dialog.DialogService;

/**
 * A text field with a button to choose a folder.
 * 
 * @author Schaefer
 */
public class FolderField {

	private Function<Path, Boolean> pathValidation = null;
	protected final Button button;
	protected final Composite composite;
	protected final DialogService dialogService;
	protected Path path;
	protected final Text text;

	/**
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * @param dialogService
	 *            the Service calling the initialization
	 */
	public FolderField(final Composite parent,
			final DialogService dialogService) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		this.dialogService = dialogService;
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				selectFolder(parent.getShell());
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				selectFolder(parent.getShell());
			}
		});
	}

	/**
	 * @return the button
	 */
	public Button getButton() {
		return button;
	}

	/**
	 * @return the composite
	 */
	public Composite getComposite() {
		return composite;
	}

	/**
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

	/**
	 * @return whether the text field or the button are enabled
	 */
	public boolean isEnabled() {
		return button.getEnabled() || text.getEnabled();
	}

	/**
	 * @param value
	 *            the new enabled state for the button and the text
	 */
	public void setEnabled(final boolean value) {
		button.setEnabled(value);
		text.setEnabled(value);
	}

	/**
	 * @param pathValidation
	 *            test if a chosen path is valid
	 */
	public void setPathValidation(
			final Function<Path, Boolean> pathValidation) {
		this.pathValidation = pathValidation;
	}

	protected boolean isValid(final Path selectedPath) {
		if (pathValidation == null) {
			return true;
		}
		return pathValidation.apply(selectedPath).booleanValue();
	}

	/**
	 * @param shell
	 *            the parent shell
	 */
	protected void selectFolder(final Shell shell) {
		final String pathFilter = text.getText() == null ? "" : text.getText(); //$NON-NLS-1$
		dialogService.selectDirectory(shell, pathFilter)
				.ifPresent(selectedName -> {
					final Path selectedPath = Paths.get(selectedName);
					if (isValid(selectedPath)) {
						this.path = selectedPath;
						text.setText(path.toString());
						text.setToolTipText(path.toString());
					}
				});
	}

}
