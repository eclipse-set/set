/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.initialization;

import org.eclipse.set.core.services.branding.BrandingService;

/**
 * A single initialization step for a model.
 * 
 * @author Schaefer
 */
public interface InitializationStep {

	/**
	 * Initialization step configuration.
	 */
	public interface Configuration {

		/**
		 * @return the branding service
		 */
		BrandingService getBrandingService();

		/**
		 * @return the initialization data
		 */
		Object getData();
	}

	/**
	 * @return the priority of this step
	 */
	int getPriority();

	/**
	 * @param <M>
	 *            the model type
	 * @param <C>
	 *            the configuration type
	 * @param model
	 *            the model
	 * @param configuaration
	 *            the configuration
	 */
	<M, C extends Configuration> void init(M model, C configuaration);
}
