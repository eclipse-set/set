/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.edit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.feature.projectdata.autofill.DateAutofill;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Gruppe;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_P_Allg_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Projekt;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.set.utils.widgets.ButtonRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This part can display project information.
 * 
 * @author Schaefer
 */
public class ProjectPart extends BasePart {

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
	EMFFormsRendererFactory renderFactory;

	@Inject
	ToolboxViewModelService viewModelService;
	private final Map<CTabItem, EObject> tabItemToObject = new LinkedHashMap<>();
	private final Map<CTabItem, Composite> tabItemToComposite = new LinkedHashMap<>();

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
			final Planung_Projekt element = PlanProSchnittstelleExtensions
					.LSTPlanungProjekt(session.getPlanProSchnittstelle());
			Assert.isNotNull(element);
			// copy name buttons
			final CopyNameButton copyNameButton = new CopyNameButton(messages);
			viewModelService.put(COPY_NAME, copyNameButton);

			// date auto fill
			final DateAutofill dateAutofill = createDateAutoFill(parent,
					session, element);

			try {
				createTabView(parent, element);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
			createButtonRow(parent, dateAutofill);
			getModelSession().getEditingDomain().getCommandStack()
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

	private void createTabView(final Composite parent,
			final Planung_Projekt element) throws ECPRendererException {
		final CTabFolder cTabFolder = new CTabFolder(parent, SWT.BOTTOM);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(cTabFolder);
		cTabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				itemSelected(cTabFolder.getSelection());
			}
		});

		createPlanungProjektAllgemeinTab(cTabFolder, element.getPlanungPAllg());
		createPlanungGruppeTabs(cTabFolder, element.getLSTPlanungGruppe());
		cTabFolder.setSelection(0);
	}

	private void createPlanungProjektAllgemeinTab(final CTabFolder folder,
			final Planung_P_Allg_AttributeGroup pAllg)
			throws ECPRendererException {
		final CTabItem cTabItem = new CTabItem(folder, SWT.BOTTOM);
		cTabItem.setText("Projekt"); //$NON-NLS-1$
		tabItemToObject.put(cTabItem, pAllg);
		tabItemToComposite.put(cTabItem,
				new ScrolledComposite(folder, SWT.V_SCROLL | SWT.H_SCROLL));
		renderTabItem(cTabItem);
	}

	private void createPlanungGruppeTabs(final CTabFolder folder,
			final EList<Planung_Gruppe> lstPlanungGruppe) {
		lstPlanungGruppe.forEach(gruppe -> {
			final CTabItem cTabItem = new CTabItem(folder, SWT.BOTTOM);
			final String gruppeName = enumTranslationService.translate(
					gruppe.getPlanungGAllg().getUntergewerkArt().getWert())
					.getPresentation();
			cTabItem.setText(gruppeName);
			tabItemToObject.put(cTabItem, gruppe);
			tabItemToComposite.put(cTabItem,
					new ScrolledComposite(folder, SWT.V_SCROLL | SWT.H_SCROLL));
			try {
				renderTabItem(cTabItem);
			} catch (final ECPRendererException e) {
				throw new RuntimeException(e);
			}
		});
	}

	private void renderTabItem(final CTabItem tabItem)
			throws ECPRendererException {
		// already rendered or invalid state
		if (!tabItemToObject.containsKey(tabItem)) {
			return;
		}
		// remove from the maps on first rendering
		final EObject tabItemObject = tabItemToObject.remove(tabItem);
		final VViewModelProperties properties = VViewFactory.eINSTANCE
				.createViewModelLoadingProperties();
		properties.addNonInheritableProperty(
				ToolboxConstants.PLANING_GROUP_VIEW_DETAIL_KEY,
				Boolean.valueOf(true));
		final VView view = ViewProviderHelper.getView(tabItemObject,
				properties);
		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(view, tabItemObject);
		AbstractSWTRenderer<VElement> renderer = null;
		try {
			renderer = renderFactory.getRendererInstance(view,
					viewModelContext);
		} catch (final EMFFormsNoRendererException e) {
			throw new RuntimeException(e);
		}
		if (renderer == null) {
			return;
		}
		final SWTGridDescription gridDescription = renderer.getGridDescription(
				GridDescriptionFactory.INSTANCE.createEmptyGridDescription());
		final Composite composite = tabItemToComposite.remove(tabItem);
		for (final SWTGridCell grid : gridDescription.getGrid()) {
			final Control render = renderer.render(grid, composite);
			renderer.finalizeRendering(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
					.grab(true, true).applyTo(render);
			final ScrolledComposite scrolledComposite = ScrolledComposite.class
					.cast(composite);
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setContent(render);
			scrolledComposite
					.setMinSize(render.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			tabItem.setControl(composite);
		}

	}

	private void itemSelected(final CTabItem selection) {
		try {
			renderTabItem(selection);
		} catch (final ECPRendererException e) {
			throw new RuntimeException(e);
		}
	}

	private DateAutofill createDateAutoFill(final Composite parent,
			final IModelSession session, final Planung_Projekt element) {
		final DateAutofill dateAutofill = new DateAutofill(
				date -> getDialogService().doApplyAutofill(getToolboxShell(),
						date),
				new ExceptionHandler(getToolboxShell(), getDialogService()));
		final Consumer<Text> dateAutofillConsumer = dateAutofill
				.getTextConsumer();
		viewModelService.put(COPY_DATE_TRIGGER, dateAutofillConsumer);
		dateAutofill.setEditingDomain(session.getEditingDomain());
		dateAutofill.setPlanning(parent, element);
		return dateAutofill;
	}

	private void createButtonRow(final Composite parent,
			final DateAutofill dateAutoFill) {
		final ButtonRow buttonRow = new ButtonRow(parent);
		final Button discardButton = createDiscardButton(buttonRow,
				dateAutoFill);
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

	private Button createDiscardButton(final ButtonRow buttonRow,
			final DateAutofill dateAutofill) {
		final Button discardButton = buttonRow
				.add(messages.ProjectPart_discardChanges);
		discardButton.setEnabled(getModelSession().isDirty());

		discardButton.addDisposeListener(
				event -> getModelSession().getEditingDomain().getCommandStack()
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
				save();
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
