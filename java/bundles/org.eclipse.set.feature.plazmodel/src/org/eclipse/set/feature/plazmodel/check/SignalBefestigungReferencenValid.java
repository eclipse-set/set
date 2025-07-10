/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plazmodel.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.set.model.planpro.Signale.Signal_Befestigung;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Streams;

/**
 * Valid the references chain of {@link Signal_Befestigung}
 * 
 * @author Truong
 */
@Component
public class SignalBefestigungReferencenValid extends AbstractPlazContainerCheck
		implements PlazCheck {

	@Override
	public String checkType() {
		return "Signalbefestigungsketten"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Die Verweisketten der Signalbefestigungen sind pausibel"; //$NON-NLS-1$
	}

	@Override
	public String getGeneralErrMsg() {
		return "Die Signalbefestigung {GUID} enthält einen Ring in der Verweiskette"; //$NON-NLS-1$
	}

	@Override
	protected List<PlazError> run(
			final MultiContainer_AttributeGroup container) {
		return Streams.stream(container.getSignalBefestigung()).filter(mast -> {
			final List<Signal_Befestigung> refChain = new ArrayList<>();
			Optional<Signal_Befestigung> current = Optional.of(mast);
			while (current.isPresent()) {
				if (refChain.contains(current.get())) {
					return true;
				}
				refChain.add(current.get());
				current = EObjectExtensions.getNullableObject(current.get(),
						e -> e.getIDSignalBefestigung().getValue());
			}
			return false;
		}).map(this::createError).toList();
	}

	private PlazError createError(final Signal_Befestigung mast) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		final String guid = EObjectExtensions
				.getNullableObject(mast, e -> e.getIdentitaet().getWert())
				.orElse(""); //$NON-NLS-1$
		plazError.setSeverity(ValidationSeverity.ERROR);
		plazError.setObject(mast);
		plazError.setMessage(transformErrorMsg(Map.of("GUID", guid))); //$NON-NLS-1$
		plazError.setType(checkType());
		return plazError;
	}

}
