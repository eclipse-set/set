/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.session;

import static org.eclipse.set.basis.constants.ToolboxConstants.TITLE_FILE_NAME_SEPARATOR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.FileValidateState;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.extensions.IModelSessionExtensions;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.branding.BrandingService;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.defaultvalue.DefaultValueService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.core.services.initialization.InitializationService;
import org.eclipse.set.core.services.initialization.InitializationStep.Configuration;
import org.eclipse.set.core.services.modelloader.ModelLoader;
import org.eclipse.set.core.services.modelloader.ModelLoader.ModelContents;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.rename.RenameService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.core.services.validation.ValidationService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.feature.validation.utils.ValidationOutcome;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenFactory;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.PlanPro.DocumentRoot;
import org.eclipse.set.model.planpro.PlanPro.PlanProFactory;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.DocumentRootExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleDebugExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.events.DataEvent;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent.ControlAreaValue;
import org.eclipse.set.utils.events.SessionDirtyChanged;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

/**
 * Implementation of model session.
 * 
 * @author bleidiessel
 */
public class ModelSession implements IModelSession {

	/**
	 * The class used for injection.
	 */
	@SuppressWarnings("javadoc") // Artificial class for injection
	public static class ServiceProvider {

		@Inject
		public MApplication application;

		@Inject
		public BrandingService brandingService;

		@Inject
		public IEventBroker broker;

		@Inject
		public CacheService cacheService;

		@Inject
		public DefaultValueService defaultValueService;

		@Inject
		public DialogService dialogService;

		@Inject
		public ToolboxFileService fileService;

		@Inject
		public InitializationService initializationService;

		@Inject
		@Translation
		public Messages messages;

		@Inject
		public RenameService renameService;

		@Inject
		public SessionService sessionService;

		@Inject
		public ToolboxPartService toolboxPartService;

		@Inject
		public ValidationService validationService;

		@Inject
		public ModelLoader modelLoader;

	}

	private static final String APPLICATION_NAME = "Werkzeugkoffer"; //$NON-NLS-1$
	private static final String SESSIONS_SUBDIR = "sessions"; //$NON-NLS-1$
	protected static final String TITLE_SEPARATOR = " - "; //$NON-NLS-1$
	static final Logger logger = LoggerFactory.getLogger(ModelSession.class);

	private static String getSessionsSubDir() {
		return Paths.get(ToolboxConstants.TMP_BASE_DIR, SESSIONS_SUBDIR)
				.toString();
	}

	private EContentAdapter contentAdapter;
	private final Guid guid;
	private boolean isNewProject = false;
	private DefaultToolboxEventHandler<SelectedControlAreaChangedEvent> selectedControlAreaChangedEventHandler;
	private PlanPro_Schnittstelle planPro_Schnittstelle;
	private PlanPro_Layoutinfo layoutInfo;
	private final Map<Integer, Boolean> reportSavedDialogSuppressed = new HashMap<>();
	private final SessionService sessionService;
	private double symbolRotation;
	/*
	 * TODO(1.10.0.1): Readd once temporary integrations are reenabled private
	 * ToolboxTemporaryIntegration temporaryIntegration;
	 */
	private final ToolboxPaths toolboxPaths;
	private boolean wasDirty = false;
	protected final Shell mainWindow;
	protected PlanProFileNature nature;
	protected ToolboxFile toolboxFile;
	protected ValidationResult schnittstelleValidationResult = new ValidationResult(
			PlanPro_Schnittstelle.class);
	ProjectInitializationData projectInitializationData;
	final ServiceProvider serviceProvider;
	TableType tableType = null;
	Set<ControlAreaValue> selectedControlAreas = new HashSet<>();

	private boolean isPlanningAreaIgnored = false;
	private SaveFixResult saveFixResult = SaveFixResult.NONE;
	protected ValidationResult layoutinfoValidationResult = null;

	private Path tempDir;

	/**
	 * @param toolboxFile
	 *            the toolbox file
	 * @param sessionService
	 *            the session service
	 * @param mainWindow
	 *            the main window of the application
	 * @param serviceProvider
	 *            the service provider
	 */
	public ModelSession(final ToolboxFile toolboxFile,
			final SessionService sessionService, final Shell mainWindow,
			final ServiceProvider serviceProvider) {
		this(toolboxFile, sessionService, mainWindow, serviceProvider, null);
	}

	/**
	 * @param toolboxFile
	 *            the toolbox file
	 * @param sessionService
	 *            the session service
	 * @param mainWindow
	 *            the main window of the application
	 * @param serviceProvider
	 *            the service provider
	 * @param tempDir
	 *            the temporary directory
	 */
	public ModelSession(final ToolboxFile toolboxFile,
			final SessionService sessionService, final Shell mainWindow,
			final ServiceProvider serviceProvider, final Path tempDir) {
		this.sessionService = sessionService;
		this.toolboxFile = toolboxFile;
		this.mainWindow = mainWindow;
		this.serviceProvider = serviceProvider;
		final IModelSession session = this;
		final EditingDomain editingDomain = toolboxFile.getEditingDomain();
		editingDomain.getCommandStack().addCommandStackListener(event -> {
			checkForDirtyEvent();
			if (isDirty()) {
				setTitleFilename(IModelSessionExtensions.getTitleFilename(
						session,
						ModelSession.this.serviceProvider.messages.ModelSession_ChangeIndicator));
				ModelSession.this.serviceProvider.broker.post(
						UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC,
						UIEvents.ALL_ELEMENT_ID);
			}
		});
		guid = Guid.create();
		this.tempDir = tempDir;
		createTempDir();
		toolboxFile.setTemporaryDirectory(getTempDir());
		toolboxPaths = new ToolboxPathsImpl(this);
		subcribeToolboxEvent();

		logger.debug("created session {}", this); //$NON-NLS-1$
	}

	private void subcribeToolboxEvent() {
		selectedControlAreaChangedEventHandler = new DefaultToolboxEventHandler<>() {

			@Override
			public void accept(final SelectedControlAreaChangedEvent t) {
				final Set<ControlAreaValue> changeAreas = t.getControlAreas();
				selectedControlAreas.clear();
				tableType = t.getTableType();
				changeAreas.forEach(area -> selectedControlAreas.add(area));
				isPlanningAreaIgnored = t.isPlanningAreaIgnored();
			}
		};
		ToolboxEvents.subscribe(this.serviceProvider.broker,
				SelectedControlAreaChangedEvent.class,
				selectedControlAreaChangedEventHandler);
	}

	@Override
	public void addSymbolRotation(final double rotation) {
		symbolRotation = symbolRotation + rotation;
	}

	@Override
	public void cancelMergeMode(final Predicate<Path> confirmDeletion)
			throws UserAbortion, IOException {
		// test for merge mode
		if (!isMergeMode()) {
			throw new IllegalStateException("Session not in merge mode."); //$NON-NLS-1$
		}

		// ask for deletion
		final Path temporaryIntegrationPath = toolboxFile.getPath();
		if (!confirmDeletion.test(temporaryIntegrationPath)) {
			throw new UserAbortion();
		}

		close();

		// delete temporary integration
		/*
		 * TODO(1.10.0.1): Readd once temporary integrations are reenabled
		 * temporaryIntegration = null;
		 */
		toolboxFile.delete(true);
	}

	@Override
	public void cleanUp() {
		try {
			// clean sessions subdirectory
			cleanSessionDirectory(Paths.get(getSessionsSubDir()), getGuid());
			// remove content adapter
			removeContentAdapter(getPlanProSchnittstelle());
			removeContentAdapter(getLayoutInformation());
			// unsubscribe event handler
			ToolboxEvents.unsubscribe(serviceProvider.broker,
					selectedControlAreaChangedEventHandler);
		} catch (final IOException e) {
			logger.warn("clean up failed: exception={} message={}", //$NON-NLS-1$
					e.getClass().getSimpleName(), e.getMessage());
		}
	}

	private static void cleanSessionDirectory(final Path unzipDirectory,
			final String guid) throws IOException {
		if (!Files.exists(unzipDirectory)) {
			return;
		}

		try (final Stream<Path> files = Files.list(unzipDirectory)) {
			files.filter(p -> {
				// Only consider directories
				if (!p.toFile().isDirectory()) {
					return false;
				}

				// Clean all directories which do not contain a PID file (e.g.
				// from older installations)
				final Path pidPath = p.resolve(ToolboxConstants.PID_FILE_NAME);
				if (!Files.exists(pidPath)) {
					return true;
				}

				try {
					// Check if process is still running
					// This may find another process, with a reused PID, however
					// this acceptable, as over time all directories will be
					// cleaned up
					final long pid = Long.parseLong(Files.readString(pidPath));
					return ProcessHandle.of(pid).isEmpty()
							|| ProcessHandle.current().pid() == pid
									&& p.getFileName().toString().equals(guid);
				} catch (NumberFormatException | IOException e) {
					return true;
				}
			}).forEach(p -> {
				try {
					FileUtils.deleteDirectory(p.toFile());
				} catch (final IOException e) {
					// ignore failed deletion
				}
			});
		}
	}

	@Override
	public void close() {
		// reset filename
		setTitleFilename(null);

		// close the project
		sessionService.close(this, getToolboxFile().getRole());
	}

	@Override
	public void discardChanges() {
		final CommandStack commandStack = getEditingDomain().getCommandStack();

		// undo commands
		while (commandStack.canUndo()) {
			commandStack.undo();
		}

		// send editing completed
		ToolboxEvents.send(serviceProvider.broker, new EditingCompleted());
	}

	@Override
	public void exitMergeMode(final Shell shell)
			throws IOException, UserAbortion {
		// test for merge mode
		if (!isMergeMode()) {
			throw new IllegalStateException("Session not in merge mode."); //$NON-NLS-1$
		}
		/*
		 * TODO(1.10.0.1): Readd once temporary integrations are reenabled //
		 * warning, if there were some invalid input plannings if
		 * (!temporaryIntegration.isPrimaryPlanningWasValid() ||
		 * !temporaryIntegration.isSecondaryPlanningWasValid()) { if
		 * (!serviceProvider.dialogService
		 * .createCompositePlanningWithInvalidInput(shell,
		 * temporaryIntegration.isPrimaryPlanningWasValid(),
		 * temporaryIntegration .isSecondaryPlanningWasValid())) { throw new
		 * UserAbortion(); } }
		 */

		// find model path
		final Format mergedFileFormat = sessionService.getMergedFileFormat();
		final Path modelPath = PlanProSchnittstelleExtensions.getDerivedPath(
				getPlanProSchnittstelle(),
				getToolboxFile().getPath().getParent().toString(),
				sessionService.getDefaultExtension(mergedFileFormat),
				ExportType.PLANNING_RECORDS);

		// test if model file exists
		if (Files.exists(modelPath) && !serviceProvider.dialogService
				.confirmOverwrite(shell, modelPath)) {
			throw new UserAbortion();

		}

		// write new model
		final ToolboxFile mergeFile = getToolboxFile();
		toolboxFile = serviceProvider.fileService.create(mergedFileFormat,
				ToolboxFileRole.EXPORT);
		toolboxFile.setTemporaryDirectory(getTempDir());
		toolboxFile.setPath(modelPath);

		// copy necessary media from Temporary
		PlanProSchnittstelleExtensions.getAttachments(getPlanProSchnittstelle())
				.forEach(anhang -> {
					try {
						toolboxFile.copyMedia(mergeFile,
								anhang.getIdentitaet().getWert());
					} catch (final IOException e) {
						throw new RuntimeException(e);
					}
				});
		final DocumentRoot documentRoot = PlanProFactory.eINSTANCE
				.createDocumentRoot();
		DocumentRootExtensions.fix(documentRoot);
		documentRoot.setPlanProSchnittstelle(getPlanProSchnittstelle());
		toolboxFile.getPlanProResource().getContents().add(documentRoot);
		save(shell);

		// delete temporary integration
		/*
		 * TODO(1.10.0.1): Readd once temporary integrations are reenabled
		 * temporaryIntegration = null;
		 */
		mergeFile.delete(true);

		// change role of composite planning file to session
		toolboxFile.close();
		toolboxFile.setRole(ToolboxFileRole.SESSION);
		init();
	}

	@Override
	public MultiContainer_AttributeGroup getContainer(
			final ContainerType container) {
		return PlanProSchnittstelleExtensions
				.getContainer(getPlanProSchnittstelle(), container);
	}

	@Override
	public EditingDomain getEditingDomain() {
		return getToolboxFile().getEditingDomain();
	}

	@Override
	public String getGuid() {
		return guid.toString();
	}

	@Override
	public Shell getMainWindow() {
		return mainWindow;
	}

	@Override
	public Set<EObject> getModels() {
		return Collections.singleton(getPlanProSchnittstelle());
	}

	@Override
	public PlanProFileNature getNature() {
		return nature;
	}

	@Override
	public PlanPro_Schnittstelle getPlanProSchnittstelle() {
		return planPro_Schnittstelle;
	}

	@Override
	public PlanPro_Layoutinfo getLayoutInformation() {
		return layoutInfo;
	}

	/**
	 * @return the service provider
	 */
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	@Override
	public double getSymbolRotation() {
		return symbolRotation;
	}

	@Override
	public TableType getTableType() {
		if (tableType == null) {
			return getNature().getDefaultContainer().getTableTypeForTables();
		}
		return tableType;
	}

	@Override
	public List<Pair<String, String>> getSelectedControlAreas() {
		return selectedControlAreas.stream()
				.map(area -> new Pair<>(area.areaName(), area.areaId()))
				.toList();
	}

	@Override
	public boolean isPlanningAreaIgnored() {
		return isPlanningAreaIgnored;
	}

	@Override
	public Path getTempDir() {
		if (tempDir == null) {
			tempDir = Paths.get(getSessionsSubDir(), getGuid());
		}
		return tempDir;
	}

	/*
	 * TODO(1.10.0.1): Readd once temporary integrations are reenabled
	 * 
	 * @Override public Optional<ToolboxTemporaryIntegration>
	 * getTemporaryIntegration() { return
	 * Optional.ofNullable(temporaryIntegration); }
	 */
	@Override
	public ToolboxFile getToolboxFile() {
		return toolboxFile;
	}

	@Override
	public ToolboxPaths getToolboxPaths() {
		return toolboxPaths;
	}

	@Override
	public ValidationResult getValidationResult(
			final Class<? extends EObject> sourceClass) {
		if (sourceClass.isAssignableFrom(PlanPro_Schnittstelle.class)) {
			return schnittstelleValidationResult;
		} else if (sourceClass.isAssignableFrom(PlanPro_Layoutinfo.class)) {
			return layoutinfoValidationResult;
		}
		return null;
	}

	private void setValidationResult(final ValidationResult result) {
		if (result.getValidatedSourceClass()
				.isAssignableFrom(PlanPro_Schnittstelle.class)) {
			schnittstelleValidationResult = result;
		} else if (result.getValidatedSourceClass()
				.isAssignableFrom(PlanPro_Layoutinfo.class)) {
			layoutinfoValidationResult = result;
		}
	}

	@Override
	public Outcome getValidationsOutcome(
			final Function<ValidationResult, Outcome> outcome) {
		return ValidationOutcome.getValidationsOutcome(Stream
				.of(schnittstelleValidationResult, layoutinfoValidationResult)
				.filter(Objects::nonNull)
				.toList(), outcome);
	}

	@Override
	public FileValidateState getFileValidateState() {
		return ValidationOutcome.getFileValidateState(Stream
				.of(schnittstelleValidationResult, layoutinfoValidationResult)
				.filter(Objects::nonNull)
				.toList());
	}

	@Override
	public void init() {
		try {
			nature = PlanProFileNature.INVALID;
			if (getToolboxFile().isLoadable()) {
				readModel();
				logger.info("Read PlanPro model {}", //$NON-NLS-1$
						getToolboxFile().getPath());
			} else {
				createEmptyModel();
				setDefaultValues();
				logger.info("New PlanPro model created."); //$NON-NLS-1$
			}
		} catch (final IOException e) {
			serviceProvider.dialogService.error(mainWindow, e);
			cleanUp();
		} finally {
			if (getPlanProSchnittstelle() != null) {
				setNature();
				if (ToolboxConfiguration.isDevelopmentMode()) {
					PlanProSchnittstelleDebugExtensions
							.validateTopKanten(getPlanProSchnittstelle());
				}
			}
		}
	}

	@Override
	public boolean isDirty() {
		return getEditingDomain().getCommandStack().canUndo();
	}

	@Override
	public boolean isLoaded() {
		return getPlanProSchnittstelle() != null;
	}

	@Override
	public boolean isMergeMode() {
		/*
		 * TODO(1.10.0.1): Readd once temporary integrations are reenabled
		 * return temporaryIntegration != null;
		 */
		return false;
	}

	@Override
	public boolean isNewProject() {
		return isNewProject;
	}

	@Override
	public boolean isReportSavedDialogSuppressed(final int kind) {
		return reportSavedDialogSuppressed
				.getOrDefault(Integer.valueOf(kind), Boolean.FALSE)
				.booleanValue();
	}

	@Override
	public void refreshValidation() {
		toolboxFile.clearXMLDocument();
		init();
	}

	@Override
	public void revert() {
		final CommandStack commandStack = getEditingDomain().getCommandStack();
		while (commandStack.canUndo()) {
			commandStack.undo();
		}
		// update title
		setTitleFilename();

		// send editing completed
		ToolboxEvents.send(serviceProvider.broker, new EditingCompleted());

		// refresh validation
		refreshValidation();
	}

	@Override
	public boolean save(final Shell shell) throws UserAbortion {
		return save(shell, true);
	}

	@Override
	public boolean save(final Shell shell, final boolean askUser)
			throws UserAbortion {
		return savePlanPro(shell, askUser);

	}

	@Override
	public boolean saveNew(final Shell shell) throws UserAbortion {
		return saveNewPlanPro(shell);
	}

	/**
	 * sets the newproject flag.
	 * 
	 * @param val
	 *            boolean
	 */
	public void setNewProject(final boolean val) {
		this.isNewProject = val;
	}

	/**
	 * sets the base data for the project initialization.
	 * 
	 * @param data
	 *            the data
	 */
	public void setNewProjectData(final ProjectInitializationData data) {
		this.projectInitializationData = data;
	}

	@Override
	public void setReportSavedDialogSuppressed(final int kind,
			final boolean value) {
		reportSavedDialogSuppressed.put(Integer.valueOf(kind),
				Boolean.valueOf(value));
	}

	/*
	 * TODO(1.10.0.1): Readd once temporary integrations are reenabled
	 * 
	 * @Override public void switchToMergeMode( final
	 * ToolboxTemporaryIntegration newTemporaryIntegration, final String
	 * mergeDir, final Shell shell, final ToolboxFile temporaryToolboxFile)
	 * throws IOException, UserAbortion { if (isMergeMode()) { throw new
	 * IllegalStateException("Session already in merge mode."); //$NON-NLS-1$ }
	 * 
	 * // remember the original planning final PlanPro_Schnittstelle
	 * originalPlanning = getPlanProSchnittstelle(); final ToolboxFile
	 * originalFile = getToolboxFile(); toolboxFile = temporaryToolboxFile;
	 * 
	 * // save temporaryIntegration = newTemporaryIntegration;
	 * setPlanProSchnittstelle(newTemporaryIntegration.getCompositePlanning());
	 * final Wrapper<UserAbortion> userAbortion = new Wrapper<>();
	 * Display.getDefault().syncExec(new Runnable() {
	 * 
	 * @Override public void run() { try { save(shell, false); } catch (final
	 * UserAbortion e) { userAbortion.setValue(e); } } });
	 * 
	 * // test for abortion if (userAbortion.getValue() != null) {
	 * temporaryIntegration = null; setPlanProSchnittstelle(originalPlanning);
	 * toolboxFile.close(); toolboxFile = originalFile; revert(); throw
	 * userAbortion.getValue(); }
	 * 
	 * // change role of temporary integration to session file
	 * originalFile.close(); toolboxFile.close();
	 * toolboxFile.setRole(ToolboxFileRole.SESSION); init(); }
	 */
	@Override
	public String toString() {
		return String.format("%s {guid=%s location=%s}", super.toString(), //$NON-NLS-1$
				getGuid(), getToolboxFile().getPath());
	}

	private void addContentAdapter(final EObject object) {
		if (object != null) {
			contentAdapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					super.notifyChanged(notification);
					sendDataEvent(notification);
				}
			};
			object.eAdapters().add(contentAdapter);
		}
	}

	private void createTempDir() {
		try {
			cleanUp();
			// create session tmp dir
			FileUtils.forceMkdir(getTempDir().toFile());
		} catch (final IOException e) {
			logger.warn("creation of tmp dir failed: exception={} message={}", //$NON-NLS-1$
					e.getClass().getSimpleName(), e.getMessage());
		}
	}

	private void readMergeModel() throws IOException {
		toolboxFile.openModel();
		/*
		 * TODO(1.10.0.1): Readd once temporary integrations are reenabled
		 * temporaryIntegration = (ToolboxTemporaryIntegration) toolboxFile
		 * .getResource().getContents().get(0);
		 * setPlanProSchnittstelle(temporaryIntegration.getCompositePlanning());
		 * validationResult.setValidationSupported(false);
		 */
	}

	private void removeContentAdapter(final EObject object) {
		if (object != null && contentAdapter != null) {
			object.eAdapters().remove(contentAdapter);
		}
	}

	private boolean saveNewPlanPro(final Shell shell) throws UserAbortion {
		try {
			// save
			final Path path = PlanProSchnittstelleExtensions
					.getDerivedPath(getPlanProSchnittstelle(),
							projectInitializationData.getDirectory(),
							serviceProvider.fileService.extensionsForCategory(
									ToolboxConstants.EXTENSION_CATEGORY_PPFILE)
									.get(0),
							ExportType.PLANNING_RECORDS);
			if (Files.exists(path)) {
				final boolean overwriteConfirmed = serviceProvider.dialogService
						.confirmOverwrite(shell, path);
				if (!overwriteConfirmed) {
					throw new UserAbortion();
				}
			}

			final DocumentRoot documentRoot = PlanProSchnittstelleExtensions
					.getDocumentRoot(getPlanProSchnittstelle());
			final XMLResource resource = toolboxFile.getPlanProResource();
			resource.getContents().add(documentRoot);
			toolboxFile.save();
			setNewProject(false);
			setTitleFilename();

			// refresh validation
			refreshValidation();
		} catch (final IOException e) {
			Display.getDefault()
					.syncExec(() -> serviceProvider.dialogService.error(shell,
							e));
			return false;
		}
		return true;
	}

	private boolean savePlanPro(final Shell shell, final boolean askUser)
			throws UserAbortion {
		try {
			// revise
			final DocumentRoot documentRoot = PlanProSchnittstelleExtensions
					.getDocumentRoot(getPlanProSchnittstelle());
			if (documentRoot != null) {
				DocumentRootExtensions.fix(documentRoot);
			}
			PlanProSchnittstelleExtensions.fix(getPlanProSchnittstelle());
			saveFixResult = SaveFixResult.NONE;
			// Ask user if all missing values in objectmanagement should be
			// filled
			if (PlanProSchnittstelleExtensions.containsUnfilledManagementValues(
					getPlanProSchnittstelle(), toolboxFile.getPlanProResource())
					&& serviceProvider.dialogService
							.confirmSetDefaultsManagement(shell)) {
				PlanProSchnittstelleExtensions
						.fixManagementDefaults(planPro_Schnittstelle);
				saveFixResult = SaveFixResult.OBJEKTMANAGEMENT;
			}
			PlanProSchnittstelleExtensions.updateErzeugung(
					getPlanProSchnittstelle(), APPLICATION_NAME,
					getToolboxFile().getEditingDomain());

			// rename
			final Path oldPath = getToolboxFile().getPath();
			final String directory = oldPath.getParent().toString();
			final String extension = PathExtensions.getExtension(oldPath);
			final Path newPath = PlanProSchnittstelleExtensions.getDerivedPath(
					getPlanProSchnittstelle(), directory, extension,
					ExportType.PLANNING_RECORDS);
			if (oldPath.equals(newPath)) {
				saveProcessDialog(shell, toolboxFile);
			} else {
				toolboxFile = serviceProvider.renameService.save(shell,
						toolboxFile, getPlanProSchnittstelle(), askUser,
						newToolboxFile -> saveProcessDialog(shell,
								newToolboxFile));
			}

			// update title
			setTitleFilename();

			// send editing completed
			ToolboxEvents.send(serviceProvider.broker, new EditingCompleted());

			// refresh validation
			refreshValidation();

			// report saved
			serviceProvider.dialogService.reportSavedSession(shell, this);
		} catch (final IOException e) {
			serviceProvider.dialogService.error(shell, e);
			return false;
		}
		return true;
	}

	private void saveProcessDialog(final Shell shell,
			final ToolboxFile toolbox) {
		final DialogService dialogService = serviceProvider.dialogService;
		dialogService.showProgressUISync(shell,
				serviceProvider.messages.SaveToolboxFile, () -> {
					try {
						toolbox.save();
					} catch (final IOException e) {
						dialogService.error(shell, e);
					}
				});
	}

	private void setPlanProSchnittstelle(
			final PlanPro_Schnittstelle schnittstelle) {
		removeContentAdapter(getPlanProSchnittstelle());
		planPro_Schnittstelle = schnittstelle;
		addContentAdapter(getPlanProSchnittstelle());
		setTitleFilename();
	}

	private void setPlanProLayoutinfo(final PlanPro_Layoutinfo layoutinfo) {
		removeContentAdapter(getLayoutInformation());
		layoutInfo = layoutinfo;
		addContentAdapter(getLayoutInformation());
	}

	protected void createEmptyModel() {
		setNewProject(true);

		createEmptyPlanProSchnittstelleModel();
		createEmptyLayoutinfoModel();

		// create toolbox file
		final Format format = toolboxFile.getFormat();
		final Path path = PlanProSchnittstelleExtensions.getDerivedPath(
				getPlanProSchnittstelle(),
				projectInitializationData.getDirectory(),
				sessionService.getDefaultExtension(format),
				ExportType.PLANNING_RECORDS);
		toolboxFile.setPath(path);
	}

	protected void createEmptyPlanProSchnittstelleModel() {
		final DocumentRoot documentRoot = PlanProFactory.eINSTANCE
				.createDocumentRoot();

		// initialization
		serviceProvider.initializationService.init(documentRoot,
				new Configuration() {

					@Override
					public BrandingService getBrandingService() {
						return serviceProvider.brandingService;
					}

					@Override
					public Object getData() {
						return projectInitializationData;
					}
				});
		setPlanProSchnittstelle(documentRoot.getPlanProSchnittstelle());

		// add contents
		final XMLResource resource = toolboxFile.getPlanProResource();
		resource.eAdapters()
				.add(new AdapterFactoryEditingDomain.EditingDomainProvider(
						toolboxFile.getEditingDomain()));
		resource.getContents().add(documentRoot);
	}

	protected void createEmptyLayoutinfoModel() {
		final org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot layoutDocumentRoot = LayoutinformationenFactory.eINSTANCE
				.createDocumentRoot();

		// initialization
		serviceProvider.initializationService.init(layoutDocumentRoot,
				new Configuration() {

					@Override
					public BrandingService getBrandingService() {
						return serviceProvider.brandingService;
					}

					@Override
					public Object getData() {
						return projectInitializationData;
					}
				});
		setPlanProLayoutinfo(layoutDocumentRoot.getPlanProLayoutinfo());

		// add layout contents
		final XMLResource layoutResource = toolboxFile.getLayoutResource();
		layoutResource.eAdapters()
				.add(new AdapterFactoryEditingDomain.EditingDomainProvider(
						toolboxFile.getEditingDomain()));
		layoutResource.getContents().add(layoutDocumentRoot);
	}

	protected void readModel() throws IOException {
		if (toolboxFile.getFormat().isTemporaryIntegration()) {
			readMergeModel();
		} else {
			final ModelContents modelContents = serviceProvider.modelLoader
					.loadModel(toolboxFile, this::setValidationResult);
			if (toolboxFile.getRole() != ToolboxFileRole.SESSION
					&& modelContents.schnittStelle() == null) {
				final String errorMsg = schnittstelleValidationResult
						.getIoErrors()
						.stream()
						.map(Exception::getMessage)
						.collect(Collectors.joining());
				throw new IOException(errorMsg);
			}
			setPlanProSchnittstelle(modelContents.schnittStelle());
			setPlanProLayoutinfo(modelContents.layoutInfo());
		}
	}

	protected void setDefaultValues() {
		serviceProvider.defaultValueService
				.setDefaultValues(getPlanProSchnittstelle());
	}

	protected void setNature() {
		try {
			if (isMergeMode()) {
				this.nature = PlanProFileNature.INTEGRATION;
			} else if (PlanProSchnittstelleExtensions
					.LSTPlanungProjekt(getPlanProSchnittstelle()) != null) {
				this.nature = PlanProFileNature.PLANNING;
			} else {
				this.nature = PlanProFileNature.INFORMATION_STATE;
			}
		} catch (final NullPointerException e) {
			// no nature is set if the expected structure does not exists
		}
	}

	protected void setTitleFilename() {
		setTitleFilename(IModelSessionExtensions.getTitleFilename(this,
				serviceProvider.messages.ModelSession_ChangeIndicator));
	}

	void checkForDirtyEvent() {
		final boolean dirty = isDirty();
		if (wasDirty != dirty) {
			ToolboxEvents.send(serviceProvider.broker,
					new SessionDirtyChanged(this, dirty));
			wasDirty = dirty;
		}
	}

	void sendDataEvent(final Notification notification) {
		// filter adapter removed notifications
		if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
			return;
		}

		// send the events
		final DataEventTransformation transformation = new DataEventTransformation();
		final Set<DataEvent> dataEvents = transformation
				.transform(notification);
		dataEvents.forEach(e -> ToolboxEvents.send(serviceProvider.broker, e));
	}

	void setTitleFilename(final String filename) {
		Display.getDefault().asyncExec(() -> {
			final String title = mainWindow.getText();
			final String[] split = title.split(String.format("%s|%s", //$NON-NLS-1$
					TITLE_SEPARATOR, TITLE_FILE_NAME_SEPARATOR));
			final String titleProgrammPart = split[0];
			if (toolboxFile.getRole() == ToolboxFileRole.COMPARE_PLANNING
					&& split.length > 1) {
				final String titleFileName = titleProgrammPart + TITLE_SEPARATOR
						+ split[1];
				if (filename == null) {
					mainWindow.setText(titleFileName);
				} else {
					mainWindow.setText(titleFileName + TITLE_FILE_NAME_SEPARATOR
							+ filename);
				}
				return;
			}

			if (filename != null && !filename.isEmpty()) {
				mainWindow.setText(
						titleProgrammPart + TITLE_SEPARATOR + filename);
			} else {
				mainWindow.setText(titleProgrammPart);
			}
		});
	}

	@Override
	public SaveFixResult getSaveFixResult() {
		return saveFixResult;
	}

	@Override
	public boolean isSingleState() {
		if (!isLoaded()) {
			return false;
		}
		return getPlanProSchnittstelle().getLSTZustand() != null;
	}
}
