/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import org.eclipse.set.core.services.part.ToolboxPartService;

/**
 * Abstract handler for a given contribution type.
 * 
 * @param <T>
 *            the contribution type
 * 
 * @author Schaefer
 */
public class ContributionHandler<T> {

	private final Class<T> type;

	protected T contribution;

	/**
	 * @param type
	 *            the contribution type
	 */
	public ContributionHandler(final Class<T> type) {
		this.type = type;
	}

	@CanExecute
	private boolean canExecute(final ToolboxPartService service) {
		final MPart part = service.getActivePart();
		if (part != null) {
			final Object partContribution = part.getObject();
			if (type.isInstance(partContribution)) {
				contribution = type.cast(partContribution);
				return true;
			}
		}
		return false;
	}
}
