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
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.Lists;

/**
 * A text field with a button to choose a file.
 * 
 * @author Schaefer
 */
public class FileField {

	private final Button button;
	private final DialogService dialogService;
	private final List<ToolboxFileFilter> filters;
	private Path path;
	private final List<Consumer<Path>> pathListeners = Lists.newArrayList();
	private Function<Path, Boolean> pathValidation = null;
	private final Text text;
	protected final Composite composite;

	/**
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * @param filters
	 *            the used toolbox file filters
	 * @param dialogService
	 *            the dialog service
	 */
	public FileField(final Composite parent,
			final List<ToolboxFileFilter> filters,
			final DialogService dialogService) {
		this.filters = filters;
		this.dialogService = dialogService;

		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.setEditable(false);
		button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				selectFile(parent.getShell());
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				selectFile(parent.getShell());
			}
		});
	}

	/**
	 * @param pathListener
	 *            the path listener
	 */
	public void addPathListener(final Consumer<Path> pathListener) {
		pathListeners.add(pathListener);
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

	private boolean isValid(final Path selectedPath) {
		if (pathValidation == null) {
			return true;
		}
		return pathValidation.apply(selectedPath).booleanValue();
	}

	protected void selectFile(final Shell shell) {
		final Optional<Path> optionalSelectedPath = dialogService
				.openFileDialog(shell, filters, Optional.empty());
		if (optionalSelectedPath.isPresent()) {
			final Path selectedPath = optionalSelectedPath.get();
			if (isValid(selectedPath)) {
				path = selectedPath;
				text.setText(path.getFileName().toString());
				text.setToolTipText(path.toString());
				pathListeners.forEach(l -> l.accept(path));
			}
		}
	}
}
