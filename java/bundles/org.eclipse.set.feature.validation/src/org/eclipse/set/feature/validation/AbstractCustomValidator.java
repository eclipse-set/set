/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.validation;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.exceptions.CustomValidationProblem;
import org.eclipse.set.core.services.validation.CustomValidator;
import org.eclipse.set.model.validationreport.ValidationSeverity;

/**
 * Absctract class for custom validation 
 * @author Truong
 *
 */
public abstract class AbstractCustomValidator implements CustomValidator {
	/**
	 * Messages Provide
	 * 
	 * @author Truong
	 */
	public static class MessagesProvider {

		@Inject
		@Translation
		private Messages messages;
	}

	protected MessagesProvider messagesProvider;

	@Override
	public Class<?> getMessageProviderClass() {
		return MessagesProvider.class;
	}

	@Override
	public void setMessagesProvider(final Object messageProvider) {
		this.messagesProvider = (MessagesProvider) messageProvider;
	}

	protected CustomValidationProblem getSuccessValidationReport(
			final String msg) {
		return new CustomValidationProblemImpl(msg, ValidationSeverity.SUCCESS,
				this.validationType(), null, null, null);
	}

	protected Messages getMessages() {
		return this.messagesProvider.messages;
	}

}
