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
package org.eclipse.set.feature.validation.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.SetFormat;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.core.services.validation.CustomValidator;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.feature.validation.AbstractCustomValidator.MessagesProvider;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.feature.validation.report.SessionToValidationReportTransformation;
import org.eclipse.set.feature.validation.service.ValidationServiceImpl;
import org.eclipse.set.feature.validation.session.ModelSession;
import org.eclipse.set.feature.validation.table.ValidationTableTransformationService;
import org.eclipse.set.feature.validation.table.ValidationTableView;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.PlanPro.DocumentRoot;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.osgi.test.utils.AbstractOsgiTest;
import org.eclipse.set.osgi.test.utils.AbstractOsgiValidationTableTest;
import org.eclipse.set.osgi.test.utils.TreeTableTestFailed;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.ToolboxVersion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

/**
 * 
 */
@ExtendWith(ServiceExtension.class)
@ExtendWith(BundleContextExtension.class)
public class ValidationReportTest extends AbstractOsgiValidationTableTest {
	private static final String REF_DIR = "res/";

	private ValidationTableTransformationService tableTransformationService;
	private ValidationReport validationReport;

	@InjectService
	CacheService cacheService;
	@InjectBundleContext
	BundleContext context;

	@InjectService
	List<CustomValidator> customValidators;

	@InjectService
	EnumTranslationService enumTranslationService;

	ValidationResult layoutValidationResult;

	Messages message;

	ValidationResult modelValidationResult;
	SessionToValidationReportTransformation reportTransformation;
	@InjectService
	SessionService sessionService;

	ValidationServiceImpl validationService;

	@InjectService
	PlanProVersionService versionService;

	@Override
	public String getTableCSVHeader(final Table table) {
		return ValidationTableView.CSV_HEADER_PATTERN;
	}

	@Override
	public String getTestTableReferenceName() {
		return "validation_report";
	}

	private void givenCustomValidator() throws Exception {
		final MessagesProvider messagesProvider = new MessagesProvider();
		final Field field = FieldUtils.getField(MessagesProvider.class,
				"messages", true);
		FieldUtils.writeField(field, messagesProvider, message, true);
		customValidators.forEach(validator -> {
			validator.setMessagesProvider(messagesProvider);
			validationService.addCustomValidator(validator);
		});
	}

	private void givenReferenceData() {
		referenceData = loadReferenceFile(getTestTableReferenceName());
	}

	private void givenSessionToValidationReportTransformation() {
		reportTransformation = new SessionToValidationReportTransformation(
				message, versionService, enumTranslationService);
	}

	private void givenTranslationMessages() throws IllegalAccessException {
		message = new Messages();
		FieldUtils.writeField(message, "NilTestProblem_Type",
				"Fachliche Vollständigkeit");
		FieldUtils.writeField(message, "NilTestProblem_Message",
				"Die nach Datenmodell erforderliche Angabe ist nicht vorhanden.");
		FieldUtils.writeField(message, "NilTestProblem_Description",
				"Validiert, dass die nach Datenmodell erforderliche Angabe vorhanden ist.");
		FieldUtils.writeField(message, "XsdProblemMsg", "XML-Schema");
		FieldUtils.writeField(message, "XsdWarningMsg", "Schema Warnung");
		FieldUtils.writeField(message, "IoProblemMsg", "Eingabe/Ausgabe");
		FieldUtils.writeField(message, "IoSuccessMsg",
				"Es sind keine Ein-/Ausgabefehler aufgetreten.");
		FieldUtils.writeField(message, "XsdSuccessMsg",
				"Es sind keine Schemawarnungen aufgetreten.");
		FieldUtils.writeField(message, "XsdErrorSuccessMsg",
				"Es sind keine Schemafehler aufgetreten.");
		FieldUtils.writeField(message, "YesMsg", "Ja");
		FieldUtils.writeField(message, "NoMsg", "Nein");
	}

	private void givenValidationReport() {
		validationReport = reportTransformation.transform(modelSession);
	}

	private void givenValidationResult() {
		modelValidationResult = new ValidationResult(
				PlanPro_Schnittstelle.class);
		validationService.validateSource(modelValidationResult,
				modelSession.getToolboxFile());
		if (modelSession.getToolboxFile().getLayoutPath() != null) {
			layoutValidationResult = new ValidationResult(
					PlanPro_Layoutinfo.class);
			validationService.validateSource(layoutValidationResult,
					modelSession.getToolboxFile());
		}

	}

	private void givenValidationService() {
		validationService = new ValidationServiceImpl();
	}

	private void givenValidationTableTransformationService() {
		tableTransformationService = new ValidationTableTransformationService(
				message);
	}

	private void whenTransformationValidationTable() {
		testee = tableTransformationService.transform(validationReport);
	}

	@AfterEach
	@Override
	protected void afterEach() {
		// do nothing
	}

	@Override
	protected CacheService getCacheService() {
		return cacheService;
	}

	@Override
	protected EnumTranslationService getEnumTranslationService() {
		return enumTranslationService;
	}

	@Override
	protected EventAdmin getEventAdmin() {
		return null;
	}

	@Override
	protected GeoKanteGeometryService getGeometryService() {
		return null;
	}

	@Override
	protected String getReferenceDir() {
		return REF_DIR + "table_reference/" + getTestFile().getShortName()
				+ "/";
	}

	@Override
	protected SessionService getSessionService() {
		return sessionService;
	}

	@Override
	protected Class<? extends AbstractOsgiTest> getTestResourceClass() {
		return getClass();
	}

	@Override
	protected TopologicalGraphService getTopologicalGraphService() {
		return null;
	}

	@SuppressWarnings("boxing")
	@Override
	protected void mockToolboxConfigurationMethode(
			final MockedStatic<ToolboxConfiguration> mockConfiguration) {
		super.mockToolboxConfigurationMethode(mockConfiguration);
		mockConfiguration.when(ToolboxConfiguration::getToolboxVersion)
				.thenReturn(new ToolboxVersion(null));
		mockConfiguration.when(ToolboxConfiguration::getTableTreeMinimum)
				.thenReturn(5);
	}

	@Override
	protected ToolboxFile mockToolboxFile() {
		final ToolboxFile mockToolboxFile = super.mockToolboxFile();
		Mockito.when(mockToolboxFile.getModelPath())
				.thenReturn(Path.of(UNZIP_DIR, "content.xml"));
		Mockito.when(mockToolboxFile.getLayoutPath()).then(invocation -> {
			final Path layoutPath = Path.of(UNZIP_DIR, "layout.xml");
			if (layoutPath.toFile().exists()) {
				return layoutPath;
			}
			return null;
		});
		Mockito.when(
				mockToolboxFile.getXMLDocument(ArgumentMatchers.anyString()))
				.thenReturn(null);
		Mockito.when(mockToolboxFile.getFormat())
				.thenReturn(SetFormat.createZippedPlanPro());
		Mockito.when(mockToolboxFile.getPath())
				.thenReturn(Path.of(getTestFile().getTestFilePath()));
		Mockito.when(mockToolboxFile.getPlanProResource())
				.thenReturn(modelResource);
		Mockito.when(mockToolboxFile.getPlanProDocumentRoot())
				.thenReturn((DocumentRoot) modelResource.getContents().get(0));
		Mockito.when(mockToolboxFile.getLayoutResource())
				.thenReturn(layoutResource);
		Mockito.when(mockToolboxFile.getLayoutDocumentRoot())
				.thenReturn(
						(org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot) layoutResource
								.getContents()
								.get(0));
		return mockToolboxFile;
	}

	@Override
	protected void setup() {
		setupModelSession(getEventAdmin());
		getSessionService().getLoadedSessions()
				.put(ToolboxFileRole.SESSION, modelSession);
	}

	@Override
	protected void setupModelSession(final EventAdmin eventAdmin) {
		if (planProSchnittstelle == null) {
			modelSession = null;
		}

		final ToolboxFile mockToolboxFile = mockToolboxFile();
		modelSession = Mockito.mock(ModelSession.class);
		Mockito.when(modelSession.getPlanProSchnittstelle())
				.thenReturn(planProSchnittstelle);
		Mockito.when(modelSession.getToolboxFile()).thenReturn(mockToolboxFile);
		Mockito.when(modelSession.getValidationResult(ArgumentMatchers.any()))
				.then(invotation -> {
					final Object argument = invotation.getArgument(0);
					if (argument == PlanPro_Schnittstelle.class) {
						return modelValidationResult;
					} else if (argument == PlanPro_Layoutinfo.class) {
						return layoutValidationResult;
					}
					return null;
				});
	}

	@Test
	@ExtendWith(TreeTableTestFailed.class)
	void testValidationReport() throws Throwable {
		doTestInMockEnvironment(() -> {
			givenTranslationMessages();
			givenValidationService();
			givenCustomValidator();
			givenValidationResult();
			assertNotNull(modelValidationResult);
			givenSessionToValidationReportTransformation();
			givenValidationReport();
			assertNotNull(validationReport);
			givenValidationTableTransformationService();
			givenReferenceData();
			whenTransformationValidationTable();
			thenExpectRowAndColumnCountEqualReferenceCSV();
			thenExpectTableDataEqualReferenceCSV();
		});
	}
}
