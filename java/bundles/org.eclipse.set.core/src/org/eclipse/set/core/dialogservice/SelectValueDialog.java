/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.core.dialogservice;

import java.util.List;

import org.eclipse.jface.dialogs.AbstractSelectionDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Create dialog for selection value
 * 
 * @author Truong
 */
public class SelectValueDialog extends AbstractSelectionDialog<String> {
	ListViewer listViewer;

	/**
	 * @param parentShell
	 *            the parent shell
	 * @param dialogTitle
	 *            the title of dialog
	 * @param dialogMessage
	 *            the message of dialog
	 * @param items
	 *            the selection values
	 */
	public SelectValueDialog(final Shell parentShell, final String dialogTitle,
			final String dialogMessage, final List<String> items) {
		super(parentShell);
		this.setTitle(dialogTitle);
		this.setMessage(dialogMessage);
		setInitialSelection(items);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);
		createMessageArea(composite);

		final GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		data.heightHint = convertHeightInCharsToPixels(10);
		data.widthHint = convertWidthInCharsToPixels(60);
		composite.setLayoutData(data);

		listViewer = new ListViewer(composite,
				SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		listViewer.getList().setLayoutData(data);
		listViewer.getList().setFont(parent.getFont());
		// Set the label provider
		listViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				// Return the features's label.
				return element == null ? "" //$NON-NLS-1$
						: (String) element;
			}
		});

		// Set the content provider
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setInput(getInitialSelection());

		// Add a selection change listener
		listViewer.addSelectionChangedListener(
				event -> getButton(IDialogConstants.OK_ID)
						.setEnabled(!event.getSelection().isEmpty()));

		// Add double-click listener
		listViewer.addDoubleClickListener(event -> okPressed());
		return composite;
	}

	@Override
	protected void okPressed() {
		setResult(listViewer.getStructuredSelection(), String.class);
		super.okPressed();
	}

	@Override
	protected Label createMessageArea(final Composite composite) {
		final Label label = new Label(composite, SWT.WRAP);
		if (getMessage() != null && !getMessage().isEmpty()) {
			label.setText(getMessage());
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false)
					.hint(convertHorizontalDLUsToPixels(
							IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH),
							SWT.DEFAULT)
					.applyTo(label);
		}

		return label;
	}
}
