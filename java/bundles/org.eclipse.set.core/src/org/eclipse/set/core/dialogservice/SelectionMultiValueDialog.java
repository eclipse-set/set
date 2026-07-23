/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.core.dialogservice;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.AbstractSelectionDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

/**
 * Dialog for selection values
 * 
 * @author Truong
 */
public class SelectionMultiValueDialog extends AbstractSelectionDialog<String> {
	CheckboxTableViewer listView;
	String selectAllButtonLabel;
	String deselectAllButtonLabel;
	List<String> items;

	/**
	 * @param parentShell
	 *            the shell
	 * @param dialogTitle
	 *            the dialog title
	 * @param dialogMessage
	 *            the dialog message
	 * @param selectAllButtonLabel
	 *            the select all button label
	 * @param deselectAllButtonLabel
	 *            the deselect all button label
	 * @param items
	 *            the items to selection
	 */
	public SelectionMultiValueDialog(final Shell parentShell,
			final String dialogTitle, final String dialogMessage,
			final String selectAllButtonLabel,
			final String deselectAllButtonLabel, final List<String> items) {
		super(parentShell);
		setTitle(dialogTitle);
		setMessage(dialogMessage);
		this.selectAllButtonLabel = selectAllButtonLabel;
		this.deselectAllButtonLabel = deselectAllButtonLabel;
		this.items = items;
		setInitialSelection(items);

	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);
		initializeDialogUnits(composite);
		createMessageArea(composite);

		listView = CheckboxTableViewer.newCheckList(composite, SWT.BORDER);
		final GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = convertHeightInCharsToPixels(10);
		data.widthHint = convertWidthInCharsToPixels(60);
		listView.getTable().setLayoutData(data);
		listView.setContentProvider(ArrayContentProvider.getInstance());
		listView.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				// Return the features's label.
				return element == null ? "" //$NON-NLS-1$
						: (String) element;
			}
		});
		listView.addCheckStateListener(e -> updateButtonOnSelection());
		listView.addDoubleClickListener(e -> {
			if (e.getSelection() instanceof final StructuredSelection structuredSelection) {
				listView.setChecked(structuredSelection.getFirstElement(),
						!listView.getChecked(
								structuredSelection.getFirstElement()));
			}
		});
		addSelectionButtons(composite);
		listView.setInput(items);

		return composite;
	}

	private void addSelectionButtons(final Composite composite) {
		final Composite buttonComposite = new Composite(composite, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(
				IDialogConstants.HORIZONTAL_SPACING);
		buttonComposite.setLayout(layout);
		buttonComposite
				.setLayoutData(new GridData(SWT.END, SWT.TOP, true, false));
		final Button selectAllButton = createButton(buttonComposite,
				IDialogConstants.SELECT_ALL_ID, selectAllButtonLabel, false);
		selectAllButton.setToolTipText(selectAllButtonLabel);
		selectAllButton.addSelectionListener(
				widgetSelectedAdapter(e -> setAllChecked(true)));
		final Button deselectAllButton = createButton(buttonComposite,
				IDialogConstants.DESELECT_ALL_ID, deselectAllButtonLabel,
				false);
		deselectAllButton.setToolTipText(deselectAllButtonLabel);
		deselectAllButton.addSelectionListener(
				widgetSelectedAdapter(e -> setAllChecked(false)));
	}

	private void setAllChecked(final boolean state) {
		listView.setAllChecked(state);
		updateButtonOnSelection();
	}

	private void updateButtonOnSelection() {
		final long selectedCount = Arrays.stream(listView.getTable().getItems())
				.filter(TableItem::getChecked)
				.count();
		final int totalCount = listView.getTable().getItemCount();
		getButton(IDialogConstants.SELECT_ALL_ID)
				.setEnabled(selectedCount < totalCount);
		getButton(IDialogConstants.DESELECT_ALL_ID)
				.setEnabled(selectedCount > 0);
		getButton(IDialogConstants.OK_ID).setEnabled(selectedCount > 0);
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	@Override
	protected Label createMessageArea(final Composite composite) {
		final Label label = new Label(composite, SWT.WRAP);
		if (getMessage() != null && !getMessage().isEmpty()) {
			label.setText(getMessage());
			GridDataFactory.fillDefaults()
					.align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false)
					.hint(convertHorizontalDLUsToPixels(
							IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH),
							SWT.DEFAULT)
					.applyTo(label);
		}

		return label;
	}

	@Override
	protected void okPressed() {

		// Get the input children.
		final Object[] checkedItems = listView.getCheckedElements();
		final List<String> result = new ArrayList<>();
		for (final Object item : checkedItems) {
			if (item instanceof final String value) {
				result.add(value);
			}
		}
		setResult(result);

		super.okPressed();
	}
}
