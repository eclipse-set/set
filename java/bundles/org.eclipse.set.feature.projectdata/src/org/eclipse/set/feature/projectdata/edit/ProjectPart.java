/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.edit;

import java.util.EventObject;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.feature.projectdata.autofill.DateAutofill;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.utils.emfforms.AbstractEmfFormsPart;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.set.utils.widgets.ButtonRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_Projekt;

/**
 * This part can display project information.
 * 
 * @author Schaefer
 */
public class ProjectPart extends AbstractEmfFormsPart<IModelSession> {

	private static final String COPY_DATE_TRIGGER = "copyDateTrigger"; //$NON-NLS-1$

	private static final String COPY_NAME = "copyName"; //$NON-NLS-1$

	@Inject
	@Translation
	private org.eclipse.set.utils.Messages utilMessages;

	@Inject
	@Translation
	protected Messages messages;

	CommandStackListener stackListener;

	@Inject
	ToolboxViewModelService viewModelService;

	/**
	 * Create the part.
	 */
	@Inject
	public ProjectPart() {
		super(IModelSession.class);
	}

	@Override
	protected void createFormsView(final Composite parent)
			throws ECPRendererException {
		final IModelSession session = getModelSession();

		if (session.getNature() != PlanProFileNature.INFORMATION_STATE) {
			final Planung_Projekt element = PlanProSchnittstelleExtensions
					.LSTPlanungProjekt(session.getPlanProSchnittstelle());
			Assert.isNotNull(element);

			// date auto fill
			final DateAutofill dateAutofill = new DateAutofill(
					date -> getDialogService()
							.doApplyAutofill(getToolboxShell(), date),
					new ExceptionHandler(getToolboxShell(),
							getDialogService()));
			final Consumer<Text> dateAutofillConsumer = dateAutofill
					.getTextConsumer();
			viewModelService.put(COPY_DATE_TRIGGER, dateAutofillConsumer);
			dateAutofill.setEditingDomain(session.getEditingDomain());
			dateAutofill.setPlanning(parent, element);

			// copy name buttons
			final CopyNameButton copyNameButton = new CopyNameButton(messages);
			viewModelService.put(COPY_NAME, copyNameButton);

			// create dialog
			createEmfFormsPart(parent, element);

			final ButtonRow buttonRow = new ButtonRow(parent);
			final Button discardButton = buttonRow
					.add(messages.ProjectPart_discardChanges);
			discardButton.setEnabled(getModelSession().isDirty());

			final Button saveButton = buttonRow.add(messages.ProjectPart_save);
			saveButton.setEnabled(getModelSession().isDirty());

			final Composite rowComposite = buttonRow.getComposite();
			rowComposite.setLayoutData(
					new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));

			// button logic
			stackListener = new CommandStackListener() {
				@Override
				public void commandStackChanged(final EventObject event) {
					discardButton.setEnabled(getModelSession().isDirty());
					saveButton.setEnabled(getModelSession().isDirty());
				}
			};
			getModelSession().getEditingDomain().getCommandStack()
					.addCommandStackListener(stackListener);
			discardButton.addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(final DisposeEvent e) {
					getModelSession().getEditingDomain().getCommandStack()
							.removeCommandStackListener(stackListener);
				}
			});
			discardButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetDefaultSelected(final SelectionEvent e) {
					dateAutofill.setEnable(false);
					discardChanges();
					dateAutofill.setEnable(true);
				}

				@Override
				public void widgetSelected(final SelectionEvent e) {
					dateAutofill.setEnable(false);
					discardChanges();
					dateAutofill.setEnable(true);
				}
			});
			saveButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetDefaultSelected(final SelectionEvent e) {
					save();
				}

				@Override
				public void widgetSelected(final SelectionEvent e) {
					save();
				}
			});
		} else {
			// Display "no planning" in case of a loaded "Zustand"-XML
			final Label tip = new Label(parent,
					SWT.WRAP | SWT.BORDER | SWT.CENTER);
			final GridData data = new GridData(SWT.HORIZONTAL, SWT.TOP, true,
					false, 1, 1);
			tip.setLayoutData(data);
			tip.setText(messages.ProjectPart_noPlanning);
		}
	}

	@Persist
	void save() {
		try {
			getModelSession().save(getToolboxShell());
		} catch (final UserAbortion e) {
			// We continue normally after an user abortion
		}
	}
}
