/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration.service.parts;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.SetFormat;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.action.ActionService;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.core.services.merge.MergeService;
import org.eclipse.set.core.services.modelloader.ModelLoader;
import org.eclipse.set.core.services.name.NameService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.integration.Messages;
import org.eclipse.set.feature.integration.PlanProMergeContextProvider;
import org.eclipse.set.feature.integration.SessionToIntegrationViewTransformation;
import org.eclipse.set.feature.integration.service.actions.ConflictsTableAction;
import org.eclipse.set.feature.integration.service.actions.ShowAttachmentAction;
import org.eclipse.set.model.integrationview.IntegrationView;
import org.eclipse.set.model.simplemerge.SComparison;
import org.eclipse.set.model.simplemerge.SMatch;
import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.model.temporaryintegration.extensions.TemporaryIntegrationExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.utils.RefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.StatefulButtonAction;
import org.eclipse.set.utils.emfforms.AbstractEmfFormsPart;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.widgets.WidgetExtensions;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import com.google.common.base.Strings;

/**
 * The merge part using EMF Forms.
 * 
 * @author Schaefer
 */
public class PlanProMergeFormsPart extends AbstractEmfFormsPart<IModelSession> {

	private static final String CONFLICTS_TABLE_ACTION = "conflictsTableAction"; //$NON-NLS-1$

	private static final String CREATE_MERGEFILE_ACTION = "createMergeFileAction"; //$NON-NLS-1$

	private static final String DELETE_MERGE_SESSION_ACTION = "deleteTemporaryIntegrationAction"; //$NON-NLS-1$

	private static final String EXIT_MERGE_MODE_ACTION = "exitMergeModeAction"; //$NON-NLS-1$
	private static final String LOAD_DIRECTORY_ACTION = "loadDirectoryAction"; //$NON-NLS-1$
	private static final String LOAD_FILE_ACTION = "loadFileAction"; //$NON-NLS-1$
	private static final String SAVE_MERGE_SESSION_ACTION = "saveTemporaryIntegrationAction"; //$NON-NLS-1$
	private static final String SHOW_ATTACHMENT_ACTION = "showAttachmentAction"; //$NON-NLS-1$
	protected static final int BUTTON_WIDTH = 160;
	protected static final int BUTTON_WIDTH_EXIT_MERGE_MODE = 320;

	@Inject
	private DialogService errorReporter;

	@Inject
	private MergeService<SComparison> mergeService;

	@Inject
	private NameService nameService;

	@Inject
	protected CacheService cacheService;

	protected PlanProMergeContextProvider contextProvider;

	@Inject
	ActionService actionService;

	StatefulButtonAction createMergefileAction;

	StatefulButtonAction deleteTemporaryIntegrationAction;

	StatefulButtonAction exitMergeModeAction;

	@Inject
	ToolboxFileService fileService;

	StatefulButtonAction loadDirectoryAction;
	StatefulButtonAction loadFileAction;

	IntegrationView mergeView;

	@Inject
	@Translation
	Messages messages;

	@Inject
	ModelLoader modelLoader;

	@Inject
	ToolboxPartService partService;

	StatefulButtonAction saveTemporaryIntegrationAction;

	PlanPro_Schnittstelle secondaryPlanning;

	ToolboxFile secondaryPlanningToolboxfile;

	boolean secondaryPlanningWasValid;

	@Inject
	IModelSession session;

	Shell shell;

	SessionToIntegrationViewTransformation transformation;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	/**
	 * Create the part.
	 */
	@Inject
	public PlanProMergeFormsPart() {
		super(IModelSession.class);
	}

	private boolean allConflictsResolved() {
		final Map<ContainerType, List<SMatch>> openConflictMatches = TemporaryIntegrationExtensions
				.getOpenConflictMatches(
						session.getTemporaryIntegration().get());
		@SuppressWarnings("boxing")
		final Set<Integer> nonZeroSizes = openConflictMatches.values().stream()
				.map(l -> l.size()).filter(i -> i > 0)
				.collect(Collectors.toSet());
		return nonZeroSizes.isEmpty();
	}

	private ToolboxFile createTemporaryToolboxFile(final String mergeDir,
			final TemporaryIntegration newTemporaryIntegration) {
		final Path mergeDirFileName = Paths.get(mergeDir,
				session.getToolboxFile().getPath().getFileName().toString());
		final Path mergeDirFileNameExtension = PathExtensions
				.replaceExtension(mergeDirFileName,
						fileService.extensionsForCategory(
								ToolboxConstants.EXTENSION_CATEGORY_PPMERGE)
								.get(0));
		final ToolboxFile temporaryFile = fileService.load(
				mergeDirFileNameExtension,
				ToolboxFileRole.TEMPORARY_INTEGRATION);
		temporaryFile.setTemporaryDirectory(session.getTempDir());
		temporaryFile.getResource().getContents().add(newTemporaryIntegration);

		try {
			temporaryFile.copyAllMedia(getConvertedPrimaryPlanning());
			temporaryFile.copyAllMedia(secondaryPlanningToolboxfile);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		return temporaryFile;
	}

	private ToolboxFile getConvertedPrimaryPlanning() {
		return fileService.convertFormat(session.getToolboxFile(),
				ToolboxFileRole.SESSION, session.getTempDir(),
				SetFormat.createZippedPlanPro());
	}

	private void mergeModel(final IProgressMonitor monitor)
			throws InvocationTargetException {
		monitor.beginTask(messages.PlanProMergePart_TaskName,
				IProgressMonitor.UNKNOWN);
		final String mergeDir = mergeView.getIntegrationDirectory();
		final TemporaryIntegration temporaryIntegration = TemporaryIntegrationExtensions
				.create(getConvertedPrimaryPlanning(),
						session.getValidationResult()
								.getOutcome() == Outcome.VALID,
						secondaryPlanningToolboxfile, secondaryPlanningWasValid,
						mergeDir);

		TemporaryIntegrationExtensions.automaticMerge(temporaryIntegration,
				mergeService, contextProvider);

		PlanProSchnittstelleExtensions.updateForIntegrationCopy(
				temporaryIntegration.getCompositePlanning(),
				session.getEditingDomain());

		try {
			// IMPROVE move function createTemporaryToolboxFile to File Service
			// or Extension
			session.switchToMergeMode(temporaryIntegration, mergeDir, shell,
					createTemporaryToolboxFile(mergeDir, temporaryIntegration));
			secondaryPlanningToolboxfile.close();
		} catch (final IOException | UserAbortion e) {
			throw new InvocationTargetException(e);
		}

		monitor.done();
	}

	private void update() {
		if (isOutdated()) {
			updateMergeView();
			setOutdated(false);
			updateButtonStates();
		}
	}

	private void updateMergeView() {
		transformation.transform(getModelSession(),
				Optional.ofNullable(secondaryPlanning),
				Optional.ofNullable(getSecondaryPlanningName()),
				Optional.ofNullable(mergeView.getIntegrationDirectory()));
	}

	@Override
	protected void createFormsView(final Composite parent)
			throws ECPRendererException {
		contextProvider = new PlanProMergeContextProvider(nameService);

		shell = parent.getShell();

		// create merge view
		transformation = new SessionToIntegrationViewTransformation(messages,
				nameService);
		final Path mergeDir = getModelSession().getToolboxFile().getPath()
				.getParent();
		mergeView = transformation.transform(getModelSession(),
				Optional.empty(), Optional.empty(),
				Optional.of(mergeDir.toString()));

		// register button actions
		loadFileAction = new StatefulButtonAction(
				messages.IntegrationView_LoadNewPlanning, BUTTON_WIDTH) {

			@Override
			public void selected(final SelectionEvent e) {
				shell = WidgetExtensions.getShell((Widget) e.getSource());
				final DialogService dialogService = getDialogService();
				final Optional<Path> openFile = dialogService.openFileDialog(
						shell, dialogService.getModelFileFilters());

				if (openFile.isPresent()) {
					final Path path = openFile.get();
					secondaryPlanningToolboxfile = fileService.load(path,
							ToolboxFileRole.SECONDARY_PLANNING);
					secondaryPlanningToolboxfile
							.setTemporaryDirectory(session.getTempDir());
					final ValidationResult validationResult = modelLoader
							.loadModel(secondaryPlanningToolboxfile,
									model -> secondaryPlanning = model, shell,
									false);

					secondaryPlanningToolboxfile = fileService.convertFormat(
							secondaryPlanningToolboxfile,
							ToolboxFileRole.SECONDARY_PLANNING,
							session.getTempDir(),
							SetFormat.createZippedPlanPro());

					// possible states
					final boolean canNotLoad = secondaryPlanning == null;
					final boolean isInvalid = validationResult
							.getOutcome() == Outcome.INVALID;
					final boolean isPlanning = PlanProSchnittstelleExtensions
							.isPlanning(secondaryPlanning);

					// user message
					if (canNotLoad) {
						dialogService.error(shell,
								messages.PlanProMergeFormsPart_CanNotLoad);
					} else {
						if (!isPlanning) {
							dialogService.error(shell,
									messages.PlanProMergeFormsPart_MustBePlanungInformation);
						} else {
							if (!isInvalid || dialogService
									.loadInvalidModel(shell, path.toString())) {
								transformation.transform(getModelSession(),
										Optional.of(secondaryPlanning),
										Optional.of(getSecondaryPlanningName()),
										Optional.ofNullable(mergeView
												.getIntegrationDirectory()));
								secondaryPlanningWasValid = validationResult
										.getOutcome() == Outcome.VALID;
							}
						}
					}
				}
				updateButtonStates();
			}
		};
		loadDirectoryAction = new StatefulButtonAction(
				messages.IntegrationView_SelectDirectory, BUTTON_WIDTH) {

			@Override
			public void selected(final SelectionEvent e) {
				shell = WidgetExtensions.getShell((Widget) e.getSource());
				final DirectoryDialog dialog = new DirectoryDialog(shell);
				final String currentDir = mergeView.getIntegrationDirectory();
				if (currentDir != null) {
					dialog.setFilterPath(currentDir);
				}
				final String dirname = dialog.open();
				if (dirname != null) {
					transformation.transform(getModelSession(),
							Optional.ofNullable(secondaryPlanning),
							Optional.ofNullable(getSecondaryPlanningName()),
							Optional.of(dirname));
				}
				updateButtonStates();
			}
		};
		createMergefileAction = new StatefulButtonAction(
				messages.IntegrationView_CreateMergeFile, BUTTON_WIDTH) {

			@Override
			public void selected(final SelectionEvent e) {
				mergeModelProgress();
				session.refreshValidation();
				actionService.update();

				// update view
				transformation.transform(getModelSession(),
						Optional.of(secondaryPlanning),
						Optional.of(getSecondaryPlanningName()),
						Optional.ofNullable(
								mergeView.getIntegrationDirectory()));
				updateButtonStates();
			}
		};
		exitMergeModeAction = new StatefulButtonAction(
				messages.IntegrationView_ExitMergeMode,
				BUTTON_WIDTH_EXIT_MERGE_MODE) {

			@Override
			public void selected(final SelectionEvent e) {
				shell = WidgetExtensions.getShell((Widget) e.getSource());
				try {
					session.exitMergeMode(shell);

					// test validation result
					session.refreshValidation();
					if (session.getValidationResult()
							.getOutcome() == Outcome.INVALID) {
						if (!getDialogService().loadInvalidModel(shell, session
								.getToolboxFile().getPath().toString())) {
							session.close();
							return;
						}
					}

					// refresh buttons in action part
					ToolboxEvents.send(getBroker(), new EditingCompleted());

					// refresh buttons in merge part
					exitMergeModeAction.setEnabled(false);
					deleteTemporaryIntegrationAction.setEnabled(false);
					saveTemporaryIntegrationAction.setEnabled(false);
				} catch (final IOException ex) {
					throw new RuntimeException(ex);
				} catch (final UserAbortion ex) {
					// We ignore an user abortion
				}
			}
		};
		deleteTemporaryIntegrationAction = new StatefulButtonAction(
				messages.IntegrationView_DeleteTemporaryIntegration,
				BUTTON_WIDTH_EXIT_MERGE_MODE) {

			@Override
			public void selected(final SelectionEvent e) {
				shell = WidgetExtensions.getShell((Widget) e.getSource());
				try {
					session.cancelMergeMode(path -> getDialogService()
							.confirmDeletion(shell, path));
				} catch (final IOException ex) {
					throw new RuntimeException(ex);
				} catch (final UserAbortion ex) {
					// We ignore an user abortion
				}
			}
		};
		saveTemporaryIntegrationAction = new StatefulButtonAction(
				messages.IntegrationView_SaveTemporaryIntegration,
				BUTTON_WIDTH_EXIT_MERGE_MODE) {

			@Override
			public void selected(final SelectionEvent e) {
				save();
			}
		};
		modelService.put(LOAD_FILE_ACTION, loadFileAction);
		modelService.put(LOAD_DIRECTORY_ACTION, loadDirectoryAction);
		modelService.put(CREATE_MERGEFILE_ACTION, createMergefileAction);
		modelService.put(EXIT_MERGE_MODE_ACTION, exitMergeModeAction);
		modelService.put(DELETE_MERGE_SESSION_ACTION,
				deleteTemporaryIntegrationAction);
		modelService.put(SAVE_MERGE_SESSION_ACTION,
				saveTemporaryIntegrationAction);

		// register menus
		final ConflictsTableAction conflictsTableAction = new ConflictsTableAction(
				session, transformation, contextProvider, () -> {
					updateButtonStates();
					return null;
				}, messages, getBroker());
		modelService.put(CONFLICTS_TABLE_ACTION, conflictsTableAction);
		modelService.put(SHOW_ATTACHMENT_ACTION,
				new ShowAttachmentAction(conflictsTableAction, transformation,
						session.getTempDir().toString(), messages, partService,
						nameService, null));

		// create form content
		createEmfFormsPart(parent, mergeView);

		// initial update of button states
		updateButtonStates();
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new RefreshAction(this, e -> update());
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
		updateButtonStates();
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
		updateButtonStates();
	}

	protected void mergeModelProgress() {
		try {
			final ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
					shell);
			progressMonitorDialog.run(true, false, this::mergeModel);
			getBroker().send(Events.MODEL_CHANGED,
					session.getPlanProSchnittstelle());
		} catch (final InvocationTargetException | InterruptedException e) {
			final Throwable cause = e.getCause();
			if (!(cause instanceof UserAbortion)) {
				errorReporter.error(shell, e);
			}
		}
	}

	protected void updateButtonStates() {
		createMergefileAction.setEnabled(!isOutdated()
				&& !Strings.isNullOrEmpty(mergeView.getSecondaryPlanning())
				&& !Strings.isNullOrEmpty(mergeView.getIntegrationDirectory())
				&& Strings.isNullOrEmpty(mergeView.getCompositePlanning()));
		loadFileAction.setEnabled(!isOutdated()
				&& Strings.isNullOrEmpty(mergeView.getCompositePlanning()));
		loadDirectoryAction.setEnabled(!isOutdated()
				&& Strings.isNullOrEmpty(mergeView.getCompositePlanning()));
		exitMergeModeAction.setEnabled(!isOutdated() && session.isMergeMode()
				&& allConflictsResolved());
		deleteTemporaryIntegrationAction
				.setEnabled(!isOutdated() && session.isMergeMode());
		saveTemporaryIntegrationAction.setEnabled(getModelSession().isDirty());
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		update();
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		update();
	}

	String getSecondaryPlanningName() {
		if (secondaryPlanningToolboxfile == null) {
			return null;
		}
		return secondaryPlanningToolboxfile.getPath().getFileName().toString();
	}

	@Persist
	void save() {
		try {
			session.save(getToolboxShell());
			updateButtonStates();
			getBroker().send(Events.MODEL_CHANGED,
					session.getPlanProSchnittstelle());
		} catch (final UserAbortion e) {
			// We continue normally after an user abortion
		}
	}

}
