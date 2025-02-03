/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.initializationservice;

import java.util.Comparator;
import java.util.Set;

import org.eclipse.set.core.services.initialization.InitializationService;
import org.eclipse.set.core.services.initialization.InitializationStep;
import org.eclipse.set.core.services.initialization.InitializationStep.Configuration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.google.common.collect.Sets;

/**
 * This implementation for {@link InitializationService} delegates the
 * initialization to {@link InitializationStep}s. All steps are applied in the
 * order of the step priorities, from the highest to the lowest. A not
 * applicable step may do nothing to the model, but it is still valid to call a
 * not applicable step.
 * 
 * @author Schaefer
 */
@Component(immediate = true)
public class InitializationServiceImpl implements InitializationService {

	static class StepComparator implements Comparator<InitializationStep> {

		@Override
		public int compare(final InitializationStep s1,
				final InitializationStep s2) {
			return Integer.compare(s2.getPriority(), s1.getPriority());
		}
	}

	private final Set<InitializationStep> steps = Sets.newHashSet();

	/**
	 * @param step
	 *            the initialization step
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addStep(final InitializationStep step) {
		steps.add(step);
	}

	@Override
	public <M, C extends Configuration> void init(final M model,
			final C configuaration) {
		steps.stream().sorted(new StepComparator())
				.forEach(step -> step.init(model, configuaration));
	}

	/**
	 * @param step
	 *            the initialization step
	 */
	public void removeStep(final InitializationStep step) {
		steps.remove(step);
	}
}
