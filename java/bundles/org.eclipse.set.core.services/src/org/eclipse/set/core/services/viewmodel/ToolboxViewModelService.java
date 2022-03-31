/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.viewmodel;

import java.nio.file.Path;
import java.util.Optional;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;

/**
 * Provides view model service for toolbox specific information.
 * 
 * @author Schaefer
 */
public interface ToolboxViewModelService {

	/**
	 * @return the optional model session
	 */
	public Optional<IModelSession> getSession();

	/**
	 * Put an object by type.
	 * 
	 * @param <T>
	 *            the type
	 * @param type
	 *            the class
	 * @param object
	 *            the object
	 */
	public <T> void put(final Class<T> type, final T object);

	/**
	 * Get an object by type.
	 * 
	 * @param <T>
	 *            the type
	 * @param type
	 *            the class
	 * 
	 * @return the object
	 */
	<T> T get(Class<T> type);

	/**
	 * Get a named object.
	 * 
	 * @param key
	 *            the key
	 * 
	 * @return the object
	 */
	Object get(String key);

	/**
	 * @param <T>
	 *            the wanted message type
	 * @param messageType
	 *            the wanted message type
	 * 
	 * @return the message instance or <code>null</code>
	 */
	<T> T getMessages(Class<T> messageType);

	/**
	 * @return the temporary directory
	 */
	Path getTempDir();

	/**
	 * 
	 * @return the translation service.
	 */
	EnumTranslationService getTranslationService();

	/**
	 * Put a named object.
	 * 
	 * @param key
	 *            the key
	 * @param object
	 *            the object
	 */
	void put(String key, Object object);
}
