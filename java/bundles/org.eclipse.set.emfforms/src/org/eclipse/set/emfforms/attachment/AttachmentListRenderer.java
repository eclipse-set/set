/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.attachment;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.set.basis.DomainElementList;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.attachments.AttachmentInfo;
import org.eclipse.set.basis.attachments.FileKind;
import org.eclipse.set.basis.files.AttachmentContentService;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.files.ToolboxFileFilterBuilder;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.emfforms.utils.DomainModelTypeRendererService;
import org.eclipse.set.ppmodel.extensions.AnhangTransformation;
import org.eclipse.set.utils.Messages;
import org.eclipse.set.utils.widgets.AttachmentTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Anhang;
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.ENUMAnhangArt;
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.ENUMDateityp;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanProPackage;

/**
 * Renders a list of attachments.
 * 
 * @author Schaefer
 */
public class AttachmentListRenderer extends SimpleControlSWTRenderer {

	private static final List<ENUMAnhangArt> DEFAULT_FILE_KIND_LIST;

	private static final Map<Pair<EStructuralFeature, EStructuralFeature>, List<ENUMAnhangArt>> FILE_KIND_LIST;

	private static final String FILTER_NAME = "AttachmentListRenderer_FilterName"; //$NON-NLS-1$

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AttachmentListRenderer.class);

	private static final int TABLE_HEIGHT = 120;

	static {
		DEFAULT_FILE_KIND_LIST = new LinkedList<>();
		DEFAULT_FILE_KIND_LIST.add(ENUMAnhangArt.ENUM_ANHANG_ART_SONSTIGE);
		fillFileKinds(DEFAULT_FILE_KIND_LIST);

		FILE_KIND_LIST = new HashMap<>();

		// BAST
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getPlanung_G_Allg_AttributeGroup_AnhangBAST(),
				PlanProPackage.eINSTANCE
						.getObjektmanagement_AttributeGroup_LSTPlanungProjekt(),
				Lists.newArrayList(ENUMAnhangArt.ENUM_ANHANG_ART_BAST));

		// Erläuterungsbericht
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getPlanung_Einzel_AnhangErlaeuterungsbericht(),
				PlanProPackage.eINSTANCE
						.getObjektmanagement_AttributeGroup_LSTPlanungProjekt(),
				Lists.newArrayList(
						ENUMAnhangArt.ENUM_ANHANG_ART_ERLAEUTERUNGSBERICHT));

		// VzG
		newFileKinds(PlanProPackage.eINSTANCE.getPlanung_Einzel_AnhangVzG(),
				PlanProPackage.eINSTANCE
						.getObjektmanagement_AttributeGroup_LSTPlanungProjekt(),
				Lists.newArrayList(ENUMAnhangArt.ENUM_ANHANG_ART_VZ_G));

		// Material Besonders
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getPlanung_Einzel_AnhangMaterialBesonders(),
				PlanProPackage.eINSTANCE
						.getObjektmanagement_AttributeGroup_LSTPlanungProjekt(),
				Lists.newArrayList(
						ENUMAnhangArt.ENUM_ANHANG_ART_MATERIAL_BESONDERS));

		// Abnahmeniederschrift
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungEAbnahme(),
				Lists.newArrayList(
						ENUMAnhangArt.ENUM_ANHANG_ART_ABNAHMENIEDERSCHRIFT));

		// Planverzeichnis
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungEErstellung(),
				Lists.newArrayList(
						ENUMAnhangArt.ENUM_ANHANG_ART_PLANVERZEICHNIS));

		// Freigabe_Bvb
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungEFreigabe(),
				Lists.newArrayList(ENUMAnhangArt.ENUM_ANHANG_ART_FREIGABE_BVB));

		// Genehmigung_AG_Bh_Bhv
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungEGenehmigung(),
				Lists.newArrayList(
						ENUMAnhangArt.ENUM_ANHANG_ART_GENEHMIGUNG_AG_BH_BHV));

		// Bestaetig_Gleichstellung
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungEGleichstellung(),
				Lists.newArrayList(
						ENUMAnhangArt.ENUM_ANHANG_ART_BESTAETIG_GLEICHSTELLUNG));

		// Prüfbericht
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungEPruefung(),
				Lists.newArrayList(ENUMAnhangArt.ENUM_ANHANG_ART_PRUEFBERICHT));

		// Bestaetig_Qualitaetspruefung
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungEQualitaetspruefung(),
				Lists.newArrayList(
						ENUMAnhangArt.ENUM_ANHANG_ART_BESTAETIG_QUALITAETSPRUEFUNG));

		// sonstige
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungESonstige(),
				Lists.newArrayList(ENUMAnhangArt.ENUM_ANHANG_ART_SONSTIGE));

		// Bestaetig_Uebernahme
		newFileKinds(
				PlanProPackage.eINSTANCE
						.getAkteur_Zuordnung_AnhangDokumentation(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Handlung_AttributeGroup_PlanungEUebernahme(),
				Lists.newArrayList(
						ENUMAnhangArt.ENUM_ANHANG_ART_BESTAETIG_UEBERNAHME));
	}

	private static void fillFileKinds(final List<ENUMAnhangArt> fileKinds) {
		ENUMAnhangArt.VALUES.stream()
				.sorted((a, b) -> a.getLiteral().compareTo(b.getLiteral()))
				.forEach(enumAnhangArt -> {
					if (!fileKinds.contains(enumAnhangArt)) {
						fileKinds.add(enumAnhangArt);
					}
				});
	}

	private static List<ENUMAnhangArt> getFileKindList(
			final DomainElementList<Anhang, AttachmentInfo<Anhang>> domainElementList) {
		final EStructuralFeature feature = domainElementList.getFeature();
		final EStructuralFeature containingFeature = domainElementList
				.getContainingFeature();
		final Pair<EStructuralFeature, EStructuralFeature> key = new Pair<>(
				feature, containingFeature);
		final List<ENUMAnhangArt> result = FILE_KIND_LIST.get(key);
		if (result == null) {
			LOGGER.error("No file kind list for feature=" + feature.getName() //$NON-NLS-1$
					+ " containingFeature=" + containingFeature.getName()); //$NON-NLS-1$
			return DEFAULT_FILE_KIND_LIST;
		}
		LOGGER.debug("File kinds for feature=" + feature.getName() //$NON-NLS-1$
				+ " containingFeature=" + containingFeature.getName() + ": " //$NON-NLS-1$ //$NON-NLS-2$
				+ result);
		return result;
	}

	private static ToolboxFile getToolboxFile() {
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();
		return toolboxViewModelService.getSession().get().getToolboxFile();
	}

	private static void newFileKinds(final EStructuralFeature feature,
			final EStructuralFeature containingFeature,
			final List<ENUMAnhangArt> fileKinds) {
		fillFileKinds(fileKinds);
		final Pair<EStructuralFeature, EStructuralFeature> key = new Pair<>(
				feature, containingFeature);
		FILE_KIND_LIST.put(key, fileKinds);
	}

	private static List<FileKind> toFileKinds(
			final List<ENUMAnhangArt> fileKindList,
			final EnumTranslationService translationService) {
		return fileKindList.stream()
				.map(enumAnhangArt -> new FileKind(enumAnhangArt.getValue(),
						translationService.translate(enumAnhangArt)
								.getAlternative()))
				.collect(Collectors.toList());
	}

	/**
	 * @param vElement
	 *            the element to be rendered
	 * @param viewContext
	 *            the view context
	 * @param reportService
	 *            the report service
	 * @param emfFormsDatabinding
	 *            the databinding service
	 * @param emfFormsLabelProvider
	 *            the label provider
	 * @param vtViewTemplateProvider
	 *            the template provider
	 */
	@Inject
	public AttachmentListRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider);
		LOGGER.debug("Render {}", vElement); //$NON-NLS-1$
	}

	private List<ToolboxFileFilter> getExtensionFilter() {
		final String filterName = LocalizationServiceHelper
				.getString(getClass(), FILTER_NAME);
		final List<String> extensions = Arrays.stream(ENUMDateityp.values())
				.map(ENUMDateityp::getLiteral).collect(Collectors.toList());
		return newArrayList(ToolboxFileFilterBuilder.forName(filterName)
				.add(extensions).create());
	}

	@Override
	protected Control createControl(final Composite parent)
			throws DatabindingFailedException {
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();
		final EnumTranslationService translationService = toolboxViewModelService
				.getTranslationService();
		final Messages messages = toolboxViewModelService
				.getMessages(Messages.class);
		final String tempDir = toolboxViewModelService.getTempDir().toString();
		final DomainElementList<Anhang, AttachmentInfo<Anhang>> domainElementList = DomainModelTypeRendererService
				.getDomainElementList(Anhang.class, getVElement(),
						getViewModelContext());
		final DialogService dialogService = toolboxViewModelService
				.get(DialogService.class);
		Assert.isNotNull(dialogService);
		final AttachmentContentService contentService = toolboxViewModelService
				.get(AttachmentContentService.class);
		Assert.isNotNull(contentService);
		final AttachmentTable attachmentTable = new AttachmentTable(parent,
				messages, contentService, dialogService, getToolboxFile());
		final AnhangTransformation transformation = AnhangTransformation
				.createTransformation(translationService, contentService);
		attachmentTable.setModel(transformation.toAttachment(domainElementList),
				toFileKinds(getFileKindList(domainElementList),
						translationService));
		attachmentTable.setTempDir(tempDir);
		final ToolboxPartService partService = toolboxViewModelService
				.get(ToolboxPartService.class);
		attachmentTable.setPdfViewer(path -> partService.showPdfPart(path));
		attachmentTable.setExtensionFilter(getExtensionFilter());
		return attachmentTable.createControl();
	}

	@Override
	protected SWTGridCell createControlCell(final int column) {
		final SWTGridCell controlCell = super.createControlCell(column);
		controlCell.setVerticalFill(true);
		controlCell.setPreferredSize(SWT.DEFAULT, TABLE_HEIGHT);
		return controlCell;
	}

	@Override
	protected String getUnsetText() {
		return null;
	}
}
