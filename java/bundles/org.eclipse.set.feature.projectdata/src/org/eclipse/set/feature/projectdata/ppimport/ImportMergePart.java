/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.ppimport;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.modelloader.ModelLoader;
import org.eclipse.set.core.services.validation.ValidationService;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.ppmodel.extensions.ContainerExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Einzel;
import org.eclipse.set.toolboxmodel.PlanPro.util.PlanProResourceFactoryImpl;
import org.eclipse.set.utils.BasePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Common functions for importing and merging.
 * 
 * @param <S>
 *            the session type
 * 
 * @author Schaefer
 */
public abstract class ImportMergePart<S extends IModelSession>
		extends BasePart<S> {

	private static Label createCountInfo(final Composite parent,
			final String text, final int size) {
		final Label labelText = new Label(parent, SWT.NONE);
		labelText.setText(text);
		final Label labelCount = new Label(parent, SWT.NONE);
		labelCount.setText(Integer.toString(size));
		return labelCount;
	}

	protected static void createParentLayout(final Composite parent) {
		parent.setLayout(new GridLayout());
	}

	protected static Resource load(final Path location) throws IOException {
		final PlanProResourceFactoryImpl resourceFactory = new PlanProResourceFactoryImpl();
		final Resource resource = resourceFactory
				.createResource(URI.createFileURI(location.toString()));
		resource.load(null);
		return resource;
	}

	private Label countFinal;

	private Label countInitial;

	@Inject
	protected CacheService cacheService;

	@Inject
	@Translation
	protected Messages messages;

	@Inject
	@Translation
	protected org.eclipse.set.utils.Messages utilMessages;

	@Inject
	protected ValidationService validationService;

	@Inject
	ModelLoader modelLoader;

	@Inject
	IModelSession session;

	/**
	 * @param sessionType
	 *            the session type
	 */
	public ImportMergePart(final Class<S> sessionType) {
		super(sessionType);
	}

	protected void createInfoGroup(final Composite parent) {
		final Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(messages.PlanProImportPart_infoGroup);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());

		final PlanPro_Schnittstelle planProSchnittstelle = session
				.getPlanProSchnittstelle();
		final Planung_Einzel singlePlanningState = PlanungProjektExtensions
				.getPlanungGruppe(PlanProSchnittstelleExtensions
						.LSTPlanungProjekt(planProSchnittstelle))
				.getLSTPlanungEinzel();

		final Container_AttributeGroup initialContainer = PlanungEinzelExtensions
				.LSTZustandStart(singlePlanningState).getContainer();
		final Container_AttributeGroup finalContainer = PlanungEinzelExtensions
				.LSTZustandZiel(singlePlanningState).getContainer();

		countInitial = createCountInfo(group,
				messages.PlanProImportPart_countStart,
				ContainerExtensions.getSize(initialContainer));
		countFinal = createCountInfo(group,
				messages.PlanProImportPart_countZiel,
				ContainerExtensions.getSize(finalContainer));
	}

	protected void createNotSupportedInfo(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(messages.PlanProImportPart_notSupportedInfo);
	}

	protected boolean isPlanning() {
		return PlanProSchnittstelleExtensions
				.isPlanning(session.getPlanProSchnittstelle());
	}

	protected Boolean testPath(final ToolboxFile toolboxFile,
			final Consumer<PlanPro_Schnittstelle> storeModel,
			final Shell shell) {
		final ValidationResult validationResult = modelLoader
				.loadModel(toolboxFile, storeModel, shell, true);

		final boolean valid = validationResult.getOutcome() == Outcome.VALID;
		final boolean xsdValid = validationResult
				.getXsdOutcome() == Outcome.VALID;
		if (!valid && !xsdValid) {
			// XSD-invalid file, unable to load
			getDialogService().error(shell,
					messages.ImportMergePart_InvalidModel);
			return Boolean.valueOf(false);
		} else if (!valid /* && xsdValid */) {
			// XSD-valid, but otherwise invalid file, warn user and continue
			getDialogService().openInformation(shell,
					messages.PlanProImportPart_incompletePlanProFile,
					String.format(
							messages.PlanProImportPart_incompletePlanProFileMessage,
							toolboxFile.getPath()));
		}
		return Boolean.valueOf(true);
	}

	protected void updateInfoGroup() {
		final PlanPro_Schnittstelle planProSchnittstelle = session
				.getPlanProSchnittstelle();
		final Planung_Einzel singlePlanningState = PlanungProjektExtensions
				.getPlanungGruppe(PlanProSchnittstelleExtensions
						.LSTPlanungProjekt(planProSchnittstelle))
				.getLSTPlanungEinzel();

		final Container_AttributeGroup initialContainer = PlanungEinzelExtensions
				.LSTZustandStart(singlePlanningState).getContainer();
		final Container_AttributeGroup finalContainer = PlanungEinzelExtensions
				.LSTZustandZiel(singlePlanningState).getContainer();

		countInitial.setText(Integer
				.toString(ContainerExtensions.getSize(initialContainer)));
		countFinal.setText(
				Integer.toString(ContainerExtensions.getSize(finalContainer)));
		countInitial.getParent().layout();
		countFinal.getParent().layout();
	}
}
