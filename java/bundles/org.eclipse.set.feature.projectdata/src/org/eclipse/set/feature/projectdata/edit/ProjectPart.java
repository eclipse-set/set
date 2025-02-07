/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.edit;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import jakarta.inject.Inject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.feature.projectdata.autofill.DateAutofill;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.model.planpro.PlanPro.Planung_P_Allg_AttributeGroup;
import org.eclipse.set.model.planpro.PlanPro.Planung_Projekt;
import org.eclipse.set.utils.emfforms.AbstractTabViewPart;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.set.utils.widgets.ButtonRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This part can display project information.
 * 
 * @author Schaefer
 */
public class ProjectPart extends AbstractTabViewPart {

	private static final String COPY_DATE_TRIGGER = "copyDateTrigger"; //$NON-NLS-1$

	private static final String COPY_NAME = "copyName"; //$NON-NLS-1$

	@Inject
	@Translation
	private org.eclipse.set.utils.Messages utilMessages;

	@Inject
	@Translation
	protected Messages messages;

	@Inject
	EnumTranslationService enumTranslationService;

	CommandStackListener stackListener;

	@Inject
	ToolboxViewModelService viewModelService;

	private CopyNameButton copyNameButton;

	private DateAutofill dateAutofill;

	private Planung_Projekt planingProject;

	private CTabFolder cTabFolder;

	private final List<CTabItem> tabItems = new LinkedList<>();

	/**
	 * Create the part.
	 */
	@Inject
	public ProjectPart() {
		super();
	}

	@Override
	protected void createView(final Composite parent) {
		final IModelSession session = getModelSession();

		if (session.getNature() != PlanProFileNature.INFORMATION_STATE) {
			planingProject = PlanProSchnittstelleExtensions
					.LSTPlanungProjekt(session.getPlanProSchnittstelle());
			Assert.isNotNull(planingProject);
			// copy name buttons
			copyNameButton = new CopyNameButton(messages,
					shouldCopyNameButtonEnable(planingProject));
			viewModelService.put(COPY_NAME, copyNameButton);

			// date auto fill
			createDateAutoFill(parent, session, planingProject);

			try {
				cTabFolder = createTabFolder(parent);
				setDefaultSelectionTab(cTabFolder);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
			createButtonRow(parent);
			getModelSession().getEditingDomain()
					.getCommandStack()
					.addCommandStackListener(stackListener);
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

	@Override
	protected void createTabItem(final CTabFolder folder) {
		createPlanungProjektAllgemeinTab(folder,
				planingProject.getPlanungPAllg());
		createPlanungGruppeTabs(folder, planingProject.getLSTPlanungGruppe());
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		final Notification notification = e.getNotification();
		if (notification.getEventType() != Notification.SET) {
			return;
		}
		copyNameButton.refresh(notification);
	}

	private static boolean shouldCopyNameButtonEnable(
			final Planung_Projekt planungProject) {
		String nameAkteur = ""; //$NON-NLS-1$
		try {
			nameAkteur = planungProject.getPlanungPAllg()
					.getProjektleiter()
					.getAkteurAllg()
					.getNameAkteur()
					.getWert();
		} catch (final NullPointerException e) {
			return false;
		}
		return nameAkteur != null && !nameAkteur.isBlank();
	}

	private void createPlanungProjektAllgemeinTab(final CTabFolder folder,
			final Planung_P_Allg_AttributeGroup pAllg) {
		final CTabItem cTabItem = new CTabItem(folder, SWT.BOTTOM);
		cTabItem.setText("Projekt"); //$NON-NLS-1$
		tabItems.add(cTabItem);
		tabItemToObject.put(cTabItem, pAllg);
		tabItemToComposite.put(cTabItem,
				new ScrolledComposite(folder, SWT.V_SCROLL | SWT.H_SCROLL));

	}

	private void createPlanungGruppeTabs(final CTabFolder folder,
			final EList<Planung_Gruppe> lstPlanungGruppe) {
		lstPlanungGruppe.forEach(group -> {
			final CTabItem cTabItem = new CTabItem(folder, SWT.BOTTOM);
			String gruppeName = ""; //$NON-NLS-1$

			try {
				gruppeName = enumTranslationService.translate(
						group.getPlanungGAllg().getUntergewerkArt().getWert())
						.getPresentation();
			} catch (final Exception e) {
				getDialogService().error(getToolboxShell(), e);
			}
			cTabItem.setText(gruppeName);
			tabItems.add(cTabItem);
			dateAutofill.setGroup(group);
			tabItemToObject.put(cTabItem, group);
			tabItemToComposite.put(cTabItem,
					new ScrolledComposite(folder, SWT.V_SCROLL | SWT.H_SCROLL));
		});
	}

	private void createDateAutoFill(final Composite parent,
			final IModelSession session, final Planung_Projekt element) {
		dateAutofill = new DateAutofill(
				date -> getDialogService().doApplyAutofill(getToolboxShell(),
						date),
				new ExceptionHandler(getToolboxShell(), getDialogService()));
		final Consumer<Text> dateAutofillConsumer = dateAutofill
				.getTextConsumer();
		viewModelService.put(COPY_DATE_TRIGGER, dateAutofillConsumer);
		dateAutofill.setEditingDomain(session.getEditingDomain());
		dateAutofill.setPlanning(parent, element);
	}

	private void createButtonRow(final Composite parent) {
		final ButtonRow buttonRow = new ButtonRow(parent);
		final Button discardButton = createDiscardButton(buttonRow);
		final Button saveButton = createSaveButton(buttonRow);

		final Composite rowComposite = buttonRow.getComposite();
		rowComposite.setLayoutData(
				new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));
		// button logic
		stackListener = event -> {
			discardButton.setEnabled(getModelSession().isDirty());
			saveButton.setEnabled(getModelSession().isDirty());
		};

	}

	private Button createDiscardButton(final ButtonRow buttonRow) {
		final Button discardButton = buttonRow
				.add(messages.ProjectPart_discardChanges);
		discardButton.setEnabled(getModelSession().isDirty());

		discardButton.addDisposeListener(
				event -> getModelSession().getEditingDomain()
						.getCommandStack()
						.removeCommandStackListener(stackListener));
		discardButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				dateAutofill.setEnable(false);
				discardChanges();
				dateAutofill.setEnable(true);
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
		return discardButton;
	}

	private Button createSaveButton(final ButtonRow buttonRow) {
		final Button saveButton = buttonRow.add(messages.ProjectPart_save);
		saveButton.setEnabled(getModelSession().isDirty());
		saveButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				save();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
		return saveButton;
	}

	@Persist
	void save() {
		try {
			getModelSession().save(getToolboxShell());
		} catch (final UserAbortion e) {
			// We continue normally after an user abortion
		}
	}

	/**
	 * Discard any changes to the session editing domain.
	 */
	public void discardChanges() {
		final CommandStack commandStack = getModelSession().getEditingDomain()
				.getCommandStack();

		// undo commands
		while (commandStack.canUndo()) {
			commandStack.undo();
		}

		// send editing completed
		ToolboxEvents.send(getBroker(), new EditingCompleted());
	}
}
