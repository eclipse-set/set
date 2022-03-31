/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.widgets;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.utils.Fonts;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * A banderole for a view.
 * 
 * @author Schaefer
 */
public class Banderole {

	private static final int OUTDATED_COLOR = SWT.COLOR_RED;
	private static final String OUTDATED_SEPARATOR = " "; //$NON-NLS-1$
	private static final String TABLE_TYPE_SEPARATOR = " - "; //$NON-NLS-1$

	private final Composite actionPanel;
	private final StackLayout actionPanelLayout;
	private final EnumTranslationService enumTranslationService;
	private SelectableAction exportAction;
	private final Composite exportButtonComposite;
	private String heading;
	private final Composite headingComposite;
	private final Label label;
	private final LocalResourceManager localResourceManager;
	private final Color originalColor;
	private boolean outdated;
	private SelectableAction outdatedAction;
	private final Composite outdatedButtonComposite;
	private final Composite outdatedEmpty;
	private final String outdatedText;
	private TableType tableType;
	final Button exportButton;
	final Button outdatedButton;

	/**
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * @param outdatedText
	 *            the outdated text
	 * @param enumTranslationService
	 *            the enum translation service
	 */
	public Banderole(final Composite parent, final String outdatedText,
			final EnumTranslationService enumTranslationService) {
		this.enumTranslationService = enumTranslationService;

		// heading composite
		headingComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2)
				.applyTo(headingComposite);

		// label
		label = new Label(headingComposite, SWT.LEFT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(label);
		localResourceManager = new LocalResourceManager(
				JFaceResources.getResources(), parent);
		final Font font = localResourceManager.createFont(Fonts.TABLE_HEADING);
		label.setFont(font);
		originalColor = label.getBackground();

		// action panel
		actionPanel = new Composite(headingComposite, SWT.NONE);
		actionPanelLayout = new StackLayout();
		actionPanel.setLayout(actionPanelLayout);

		// empty panel for no action
		outdatedEmpty = new Composite(actionPanel, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(outdatedEmpty);

		// the outdated action button
		outdatedButtonComposite = new Composite(actionPanel, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(outdatedButtonComposite);
		outdatedButtonComposite.setBackground(
				Display.getCurrent().getSystemColor(OUTDATED_COLOR));
		outdatedButton = new Button(outdatedButtonComposite, SWT.NONE);

		// the export action button
		exportButtonComposite = new Composite(actionPanel, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(exportButtonComposite);
		exportButton = new Button(exportButtonComposite, SWT.NONE);

		// the empty panel is the default top control
		actionPanelLayout.topControl = outdatedEmpty;

		// outdated text
		this.outdatedText = outdatedText;
	}

	/**
	 * @return the control containing the view heading
	 */
	public Control getControl() {
		return headingComposite;
	}

	/**
	 * @return whether the heading is marked as outdated
	 */
	public boolean isOutdated() {
		return outdated;
	}

	/**
	 * @param enabled
	 *            whether to enable the export button
	 */
	public void setEnableExport(final boolean enabled) {
		exportButton.setEnabled(enabled);
	}

	/**
	 * @param exportAction
	 *            the export action
	 */
	public void setExportAction(final SelectableAction exportAction) {
		if (this.exportAction != null) {
			throw new IllegalStateException("export action already set"); //$NON-NLS-1$
		}
		if (exportAction == null) {
			return;
		}
		this.exportAction = exportAction;
		exportButton.setText(exportAction.getText());
		final SelectionListener listener = new SelectionListener() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				exportAction.selected(e);
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				exportAction.selected(e);
			}
		};
		exportButton.addSelectionListener(listener);
		exportButton.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(final DisposeEvent e) {
				exportButton.removeSelectionListener(listener);
			}
		});

		// the export action is now the default
		setTopControl(exportButtonComposite);
	}

	/**
	 * @param heading
	 *            the text for the heading
	 */
	public void setHeading(final String heading) {
		this.heading = heading;
		updateLabel();
	}

	/**
	 * @param outdated
	 *            whether the heading is marked as outdated
	 */
	public void setOutdated(final boolean outdated) {
		this.outdated = outdated;
		updateLabel();
	}

	/**
	 * @param outdatedAction
	 *            the outdated action
	 */
	public void setOutdatedAction(final SelectableAction outdatedAction) {
		if (this.outdatedAction != null) {
			throw new IllegalStateException("outdated action already set"); //$NON-NLS-1$
		}
		if (outdatedAction == null) {
			return;
		}
		this.outdatedAction = outdatedAction;
		outdatedButton.setText(outdatedAction.getText());
		final SelectionListener listener = new SelectionListener() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				outdatedAction.selected(e);
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				outdatedAction.selected(e);
			}
		};
		outdatedButton.addSelectionListener(listener);
		outdatedButton.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(final DisposeEvent e) {
				outdatedButton.removeSelectionListener(listener);
			}
		});
	}

	/**
	 * @param tableType
	 *            the table type to be displayed
	 */
	public void setTableType(final TableType tableType) {
		this.tableType = tableType;
		updateLabel();
	}

	private String getTableTypeString() {
		return enumTranslationService.translate(tableType).getPresentation();
	}

	private void setTopControl(final Control control) {
		actionPanelLayout.topControl = control;
		headingComposite.layout(true, true);
	}

	private void updateLabel() {
		final StringBuilder text = new StringBuilder();

		// heading
		text.append(heading);

		// table state
		if (tableType != null) {
			text.append(TABLE_TYPE_SEPARATOR);
			text.append(getTableTypeString());
		}

		// outdated state
		if (outdated) {
			label.setBackground(
					Display.getCurrent().getSystemColor(OUTDATED_COLOR));
			text.append(OUTDATED_SEPARATOR);
			text.append(outdatedText);
			if (outdatedAction != null) {
				setTopControl(outdatedButtonComposite);
			}
		} else {
			label.setBackground(originalColor);
			if (exportAction != null) {
				setTopControl(exportButtonComposite);
			} else {
				setTopControl(outdatedEmpty);
			}
		}

		label.setText(text.toString());
	}
}
