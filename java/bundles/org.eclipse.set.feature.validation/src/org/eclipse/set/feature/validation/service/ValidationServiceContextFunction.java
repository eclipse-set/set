/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.service;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import org.eclipse.set.core.services.validation.CustomValidator;
import org.eclipse.set.core.services.validation.ValidationService;

/**
 * Create and publish the {@link ValidationService}.
 * 
 * @author Schaefer
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.core.services.validation.ValidationService")
public class ValidationServiceContextFunction extends ContextFunction {

	private final Set<CustomValidator> customValidators = new HashSet<>();
	private IEclipseContext eclipseContext;
	private ValidationServiceImpl validationService;

	/**
	 * @param validator
	 *            the custom validator
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addCustomValidator(final CustomValidator validator) {
		customValidators.add(validator);
		if (validationService != null) {
			validator.setMessagesProvider(ContextInjectionFactory
					.make(validator.getMessageProviderClass(), eclipseContext));
			validationService.addCustomValidator(validator);
		}
	}

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		this.eclipseContext = context;
		validationService = ContextInjectionFactory
				.make(ValidationServiceImpl.class, context);
		customValidators.forEach(validator -> {
			validator.setMessagesProvider(ContextInjectionFactory
					.make(validator.getMessageProviderClass(), context));
			validationService.addCustomValidator(validator);
		});
		return validationService;
	}

	/**
	 * @param validator
	 *            the custom validator
	 */
	public void removeCustomValidator(final CustomValidator validator) {
		customValidators.remove(validator);
		validationService.removeCustomValidator(validator);
	}
}
