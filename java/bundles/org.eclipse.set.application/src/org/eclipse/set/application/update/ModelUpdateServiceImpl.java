/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.update;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.core.services.update.ConcreteModelUpdateService;
import org.eclipse.set.core.services.update.ModelUpdateService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Implementaion for {@link ModelUpdateService}
 * 
 * @author Truong
 *
 */
@Component(immediate = true)
public class ModelUpdateServiceImpl implements ModelUpdateService {

	private final List<ConcreteModelUpdateService> concreteUpdateServices = new ArrayList<>();

	@Override
	public void add(final EObject model) {
		concreteUpdateServices.forEach(s -> s.add(model));
	}

	/**
	 * @param concreteModelUpdateService
	 *            the concrete ModelUpdate
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addConcreteModelUpdateService(
			final ConcreteModelUpdateService concreteModelUpdateService) {
		concreteUpdateServices.add(concreteModelUpdateService);
	}

	@Override
	public List<ConcreteModelUpdateService> getConcreteModelUpdateServices() {
		return concreteUpdateServices;
	}

	/**
	 * @param concreteModelUpdateService
	 *            the concrete ModelUpdate
	 */
	public void removeConcreteModelUpdateService(
			final ConcreteModelUpdateService concreteModelUpdateService) {
		concreteUpdateServices.remove(concreteModelUpdateService);
	}

}
