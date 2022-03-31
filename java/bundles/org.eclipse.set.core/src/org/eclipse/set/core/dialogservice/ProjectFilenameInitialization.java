/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice;

import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.validation.ValidationService;
import org.eclipse.emf.ecp.view.spi.validation.ViewValidationListener;
import org.eclipse.emfforms.swt.core.EMFFormsSWTConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.core.Messages;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.widgets.FolderField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.Index_Ausgabe_TypeClass;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Laufende_Nummer_Ausgabe_TypeClass;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanProFactory;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_E_Allg_AttributeGroup;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_Einzel;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_Gruppe;

/**
 * Dialog for project initialization.
 * 
 * @author Schaefer/Rumpf
 */
final class ProjectFilenameInitialization
		extends FilenameInitialization<ProjectInitializationData> {

	private static final String PATTERN_FOLDER_FIELD = ".+"; //$NON-NLS-1$

	private FolderField folderField;
	private Planung_Gruppe formModel;
	private boolean formValidated = false;
	private ECPSWTView formView;
	final Messages messages;

	/**
	 * Creates the dialog.
	 * 
	 * @param shell
	 *            the shell for the dialog
	 * @param messages
	 *            the translations
	 * @param dialogService
	 *            the parent service calling initialization
	 */
	public ProjectFilenameInitialization(final Shell shell,
			final Messages messages, final DialogService dialogService) {
		super(shell, dialogService);
		this.messages = messages;
	}

	@Override
	public String getDialogTitle() {
		return messages.ProjectInit_DialogTitle;
	}

	@Override
	public String getInitButtonText() {
		return messages.ProjectInit_InitButton;
	}

	@Override
	public ProjectInitializationData getInitializationData() {
		final ProjectInitializationData data = new ProjectInitializationData();
		data.setFuehrendeOertlichkeit(
				formModel.getFuehrendeOertlichkeit().getWert());
		data.setBauzustand(formModel.getLSTPlanungEinzel().getPlanungEAllg()
				.getBauzustandKurzbezeichnung().getWert());
		data.setLfdNummer(formModel.getLSTPlanungEinzel().getPlanungEAllg()
				.getLaufendeNummerAusgabe().getWert());
		data.setIndex(formModel.getLSTPlanungEinzel().getPlanungEAllg()
				.getIndexAusgabe().getWert());
		data.setDirectory(folderField.getText().getText());
		return data;
	}

	@Override
	public String getInitMessage() {
		return messages.ProjectInit_TitleAreaMessage;
	}

	@Override
	public String getPageTitle() {
		return messages.ProjectInit_Title;
	}

	private void setFormModelDefaults() {
		final Planung_Einzel planungEinzel = PlanProFactory.eINSTANCE
				.createPlanung_Einzel();
		final Planung_E_Allg_AttributeGroup planungEAllg = PlanProFactory.eINSTANCE
				.createPlanung_E_Allg_AttributeGroup();
		final Laufende_Nummer_Ausgabe_TypeClass laufendeNummerAusgabe = PlanProFactory.eINSTANCE
				.createLaufende_Nummer_Ausgabe_TypeClass();
		planungEAllg.setLaufendeNummerAusgabe(laufendeNummerAusgabe);
		final Index_Ausgabe_TypeClass indexAusgabe = PlanProFactory.eINSTANCE
				.createIndex_Ausgabe_TypeClass();
		planungEAllg.setIndexAusgabe(indexAusgabe);
		planungEinzel.setPlanungEAllg(planungEAllg);
		formModel.setLSTPlanungEinzel(planungEinzel);
		formModel.getLSTPlanungEinzel().getPlanungEAllg()
				.getLaufendeNummerAusgabe().setWert("00"); //$NON-NLS-1$
		formModel.getLSTPlanungEinzel().getPlanungEAllg().getIndexAusgabe()
				.setWert("00"); //$NON-NLS-1$
	}

	@Override
	protected String compileTooltip(final String pattern) {
		return String.format(
				messages.ProjectFilenameInitialization_TooltipPattern, pattern);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		container.setLayout(new GridLayout());

		formModel = PlanProFactory.eINSTANCE.createPlanung_Gruppe();
		setFormModelDefaults();

		final VViewModelProperties properties = VViewFactory.eINSTANCE
				.createViewModelLoadingProperties();
		properties.addInheritableProperty(
				EMFFormsSWTConstants.USE_ON_MODIFY_DATABINDING_KEY,
				EMFFormsSWTConstants.USE_ON_MODIFY_DATABINDING_VALUE);
		final VElement vElement = ViewProviderHelper.getView(formModel,
				properties);
		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(vElement, formModel);
		try {
			formView = ECPSWTViewRenderer.INSTANCE.render(container,
					viewModelContext);
		} catch (final ECPRendererException e) {
			throw new RuntimeException(e);
		}

		final Label spaceLabel1 = new Label(container, SWT.NONE);
		spaceLabel1.setText(new String());

		final Label label = new Label(container, SWT.NONE);
		label.setText(messages.ProjectInit_DateiSpeichern);
		final GridData gridData = new GridData();
		label.setLayoutData(gridData);

		folderField = new FolderField(container, getDialogService());
		folderField.getButton().setText(messages.ProjectInit_Ordner);
		folderField.getComposite()
				.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		folderField.getText()
				.setText(ToolboxConfiguration.getDefaultPath().toString());

		final ModifyListener listener = new ModifyListener() {

			/** {@inheritDoc} */
			@Override
			public void modifyText(final ModifyEvent e) {
				validate();
			}
		};

		folderField.getText().addModifyListener(listener);

		final Label spaceLabel2 = new Label(container, SWT.NONE);
		spaceLabel2.setText(new String());

		// Validation
		final ValidationService validationService = formView
				.getViewModelContext().getService(ValidationService.class);
		validationService
				.registerValidationListener(new ViewValidationListener() {
					@Override
					public void onNewValidation(
							final Set<Diagnostic> validationResults) {
						setFormValidation(validationResults);
						validate();
					}
				});

		return area;
	}

	protected void setFormValidation(final Set<Diagnostic> validationResults) {
		formValidated = validationResults.isEmpty();
	}

	@Override
	protected void validate() {
		boolean okEnabled = true;

		okEnabled = formValidated && okEnabled;
		okEnabled = validate(folderField.getText(), PATTERN_FOLDER_FIELD)
				&& okEnabled;

		final Button button = getButton(IDialogConstants.OK_ID);
		if (button != null) {
			button.setEnabled(okEnabled);
		}
	}
}
