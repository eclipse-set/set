/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.modelservice;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.files.AttachmentContentService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.helpmessage.HelpMessageService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.utils.Messages;

/**
 * A simple implementation for the toolbox view model service.
 * 
 * @author Schaefer
 */
public class SimpleToolboxViewModelService implements ToolboxViewModelService {

	@Inject
	private DialogService dialogService;

	@Inject
	private HelpMessageService helpMessageService;

	private final Map<String, Object> messages = new HashMap<>();

	private final Map<String, Object> namedObjects = new HashMap<>();

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	private IModelSession session;

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	ECommandService commandService;

	@Inject
	AttachmentContentService contentService;

	@Inject
	EnumTranslationService enumTranslationService;

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	EHandlerService handlerService;

	@Inject
	ToolboxPartService partService;

	@Inject
	ESelectionService selectionService;

	@Inject
	@Translation
	Messages utilMessages;

	@Override
	public <T> T get(final Class<T> type) {
		return type.cast(get(type.getName()));
	}

	@Override
	public Object get(final String key) {
		return namedObjects.get(key);
	}

	@Override
	public <T> T getMessages(final Class<T> messageType) {
		@SuppressWarnings("unchecked")
		final T result = (T) messages.get(messageType.getName());
		return result;
	}

	@Override
	public Optional<IModelSession> getSession() {
		return Optional.ofNullable(session);
	}

	@Override
	public Path getTempDir() {
		return session.getTempDir();
	}

	@Override
	public EnumTranslationService getTranslationService() {
		return enumTranslationService;
	}

	@Override
	public <T> void put(final Class<T> type, final T object) {
		namedObjects.put(type.getName(), object);
	}

	@Override
	public void put(final String key, final Object object) {
		namedObjects.put(key, object);
	}

	@PostConstruct
	private void postConstruct() {
		messages.put(Messages.class.getName(), utilMessages);
		put(ToolboxPartService.class, partService);
		put(DialogService.class, dialogService);
		put(HelpMessageService.class, helpMessageService);
		put(AttachmentContentService.class, contentService);
		put(ESelectionService.class, selectionService);
	}
}
