/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.validation.session;

import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.InitializationData;
import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.SetFormat;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.files.ToolboxFileExtension;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.feature.validation.NilValidator;
import org.eclipse.set.feature.validation.session.ModelSession.ServiceProvider;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Basic service functionality.
 * 
 * @author Schaefer
 */

@Component(service = SessionService.class)
public class SetSessionService implements SessionService {
	Logger logger = LoggerFactory.getLogger(SetSessionService.class);
	protected static final Map<String, Set<ToolboxFileExtension>> PLAIN_SUPPORT_MAP;

	protected static final Map<String, Set<ToolboxFileExtension>> ZIPPED_SUPPORT_MAP;

	protected static final Map<ToolboxFileRole, IModelSession> loadedModels;

	static {
		final Set<ToolboxFileExtension> ppfile = Sets.newHashSet(
				new ToolboxFileExtension(ToolboxConstants.EXTENSION_PPXML, 100),
				new ToolboxFileExtension(ToolboxConstants.EXTENSION_XML, 50));

		final Set<ToolboxFileExtension> ppmerge = Sets.newHashSet(
				new ToolboxFileExtension(ToolboxConstants.EXTENSION_MPLANPRO,
						100));

		PLAIN_SUPPORT_MAP = new HashMap<>();
		PLAIN_SUPPORT_MAP.put(ToolboxConstants.EXTENSION_CATEGORY_PPALL,
				ppfile);
		PLAIN_SUPPORT_MAP.put(ToolboxConstants.EXTENSION_CATEGORY_PPFILE,
				ppfile);

		final Set<ToolboxFileExtension> ppZip = Sets.newHashSet(
				new ToolboxFileExtension(ToolboxConstants.EXTENSION_PLANPRO,
						110));
		final Set<ToolboxFileExtension> ppall = Sets.newHashSet(ppZip);
		ppall.addAll(ppmerge);

		ZIPPED_SUPPORT_MAP = new HashMap<>();
		ZIPPED_SUPPORT_MAP.put(ToolboxConstants.EXTENSION_CATEGORY_PPFILE,
				ppZip);
		ZIPPED_SUPPORT_MAP.put(ToolboxConstants.EXTENSION_CATEGORY_PPALL,
				ppall);
		ZIPPED_SUPPORT_MAP.put(ToolboxConstants.EXTENSION_CATEGORY_PPMERGE,
				ppmerge);

		loadedModels = new EnumMap<>(ToolboxFileRole.class);
	}

	protected static String getDefaultExtensionPlainPlanPro() {
		return PLAIN_SUPPORT_MAP.get(ToolboxConstants.EXTENSION_CATEGORY_PPFILE)
				.stream()
				.max((a, b) -> Integer.compare(a.getPriority(),
						b.getPriority()))
				.get()
				.getExtension();
	}

	protected static String getDefaultExtensionTemporaryIntegration() {
		return ZIPPED_SUPPORT_MAP
				.get(ToolboxConstants.EXTENSION_CATEGORY_PPMERGE)
				.stream()
				.max((a, b) -> Integer.compare(a.getPriority(),
						b.getPriority()))
				.get()
				.getExtension();
	}

	protected static String getDefaultExtensionZippedPlanPro() {
		return ZIPPED_SUPPORT_MAP
				.get(ToolboxConstants.EXTENSION_CATEGORY_PPFILE)
				.stream()
				.max((a, b) -> Integer.compare(a.getPriority(),
						b.getPriority()))
				.get()
				.getExtension();
	}

	protected List<ToolboxViewGroup> actionItems = Lists.newLinkedList();

	protected ServiceProvider serviceProvider;

	@Override
	public boolean close(final IModelSession modelSession,
			final ToolboxFileRole role) {
		if (modelSession == null) {
			return false;
		}

		// show the no session part
		if (role == ToolboxFileRole.SESSION && getToolboxPartService()
				.showPart(ToolboxConstants.NO_SESSION_PART_ID)) {
			loadedModels.keySet().forEach(this::closeLoadedSession);

			actionItems.clear();
			// clean up the view area
			getToolboxPartService().clean();

			// remove the session from the application context
			getApplication().getContext().set(IModelSession.class, null);
			loadedModels.clear();
			return true;
		}

		return closeLoadedSession(role);
	}

	private boolean closeLoadedSession(final ToolboxFileRole role) {
		if (loadedModels.containsKey(role)) {
			try {
				final IModelSession session = loadedModels.get(role);
				if (session != null) {
					serviceProvider.broker.send(Events.CLOSE_SESSION, role);
					session.getToolboxFile().close();
					session.cleanUp();
				}
				return true;
			} catch (final Exception e) {
				logger.error(e.getMessage());
				return false;
			}
		}
		return false;
	}

	@Override
	public EditingDomain createEditingDomain() {
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				new AdapterFactory[] { new ComposedAdapterFactory(
						ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		return new AdapterFactoryEditingDomain(adapterFactory,
				new BasicCommandStack());
	}

	@Override
	public String getDefaultExtension(final Format format) {
		if (format.isTemporaryIntegration()) {
			return getDefaultExtensionTemporaryIntegration();
		}
		if (format.isPlain()) {
			return getDefaultExtensionPlainPlanPro();
		}
		if (format.isZippedPlanPro()) {
			return getDefaultExtensionZippedPlanPro();
		}
		throw new IllegalArgumentException(format.toString());
	}

	@Override
	public Format getFormat(final Path path) {
		final String extension = PathExtensions.getExtension(path);

		if (PLAIN_SUPPORT_MAP.get(ToolboxConstants.EXTENSION_CATEGORY_PPFILE)
				.stream()
				.map(ToolboxFileExtension::getExtension)
				.anyMatch(e -> e.equals(extension))) {
			return SetFormat.createPlainPlanPro();
		}
		if (ZIPPED_SUPPORT_MAP.get(ToolboxConstants.EXTENSION_CATEGORY_PPFILE)
				.stream()
				.map(ToolboxFileExtension::getExtension)
				.anyMatch(e -> e.equals(extension))) {
			return SetFormat.createZippedPlanPro();
		}
		if (ZIPPED_SUPPORT_MAP.get(ToolboxConstants.EXTENSION_CATEGORY_PPMERGE)
				.stream()
				.map(ToolboxFileExtension::getExtension)
				.anyMatch(e -> e.equals(extension))) {
			return SetFormat.createTemporaryIntegration();
		}

		throw new IllegalArgumentException("no format for " + path.toString()); //$NON-NLS-1$
	}

	@Override
	public Format getInitializationFormat() {
		return getZippedPlanProFormat();
	}

	@Override
	public Format getMergedFileFormat() {
		return SetFormat.createZippedPlanPro();
	}

	@Override
	public URI getPackageUri(final Format format) {
		return URI.createURI(PlanProPackage.eNS_URI);
	}

	@Override
	public Format getPlainPlanProFormat() {
		return SetFormat.createPlainPlanPro();
	}

	@Override
	public Map<String, Set<ToolboxFileExtension>> getPlainSupportMap() {
		return PLAIN_SUPPORT_MAP;
	}

	@Override
	public Format getZippedPlanProFormat() {
		return SetFormat.createZippedPlanPro();
	}

	@Override
	public Map<String, Set<ToolboxFileExtension>> getZippedSupportMap() {
		return ZIPPED_SUPPORT_MAP;
	}

	@Override
	public void setApplication(final MApplication application) {
		serviceProvider = ContextInjectionFactory.make(ServiceProvider.class,
				application.getContext());
	}

	protected MApplication getApplication() {
		return serviceProvider.application;
	}

	protected ToolboxFileService getFileService() {
		return serviceProvider.fileService;
	}

	protected Shell getMainWindow() {
		final List<MWindow> mWindows = getApplication().getChildren();
		if (mWindows.isEmpty()) {
			return null;
		}
		return mWindows.get(0).getContext().get(Shell.class);
	}

	protected Messages getMessages() {
		return serviceProvider.messages;
	}

	protected ToolboxPartService getToolboxPartService() {
		return serviceProvider.toolboxPartService;
	}

	@Override
	public ModelSession initModelSession(
			final InitializationData projectInitializationData) {
		// IMPROVE: Is there a better place to set this up?
		NilValidator.setup(getMessages());
		final ToolboxFile toolboxFile = getFileService()
				.create(getInitializationFormat(), ToolboxFileRole.SESSION);
		final ModelSession modelSession = new ModelSession(toolboxFile, this,
				getMainWindow(), serviceProvider);
		modelSession.setNewProject(true);
		modelSession.setNewProjectData(
				(ProjectInitializationData) projectInitializationData);
		loadedModels.put(ToolboxFileRole.SESSION, modelSession);
		return modelSession;
	}

	@Override
	public ModelSession loadModelSession(final Path path,
			final ToolboxFileRole role) {
		final Path tmpDir = role == ToolboxFileRole.SESSION ? null
				: getLoadedSession(ToolboxFileRole.SESSION).getTempDir();
		final ModelSession modelSession = new ModelSession(
				getFileService().load(path, role), this, getMainWindow(),
				serviceProvider, tmpDir);
		modelSession.setNewProject(false);
		loadedModels.put(role, modelSession);
		return modelSession;
	}

	@Override
	public Map<ToolboxFileRole, IModelSession> getLoadedSessions() {
		return loadedModels;
	}

	@Override
	public IModelSession getLoadedSession(final ToolboxFileRole role) {
		return loadedModels.getOrDefault(role, null);
	}

}
