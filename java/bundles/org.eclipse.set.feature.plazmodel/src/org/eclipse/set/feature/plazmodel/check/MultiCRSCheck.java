/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plazmodel.check;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.osgi.service.component.annotations.Component;

/**
 * Validates that all GEO_Punkt_Allg have a consistent coordinate reference
 * system
 * 
 */
@Component(service = PlazCheck.class)
public class MultiCRSCheck extends AbstractPlazContainerCheck
		implements PlazCheck {

	@Override
	public String checkType() {
		return "Mehrfache Koordinatensystem"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Instanzen von GEO_Punkt_Allg haben ein konsistentes Koordinatensystem."; //$NON-NLS-1$
	}

	@Override
	public String getGeneralErrMsg() {
		return "Es gibt Objekte in unterschiedlichen Koordinatensystemen. Der sicherungstechnische Lageplan kann unvollständig sein."; //$NON-NLS-1$
	}

	@Override
	protected List<PlazError> run(
			final MultiContainer_AttributeGroup container) {
		final Set<ENUMGEOKoordinatensystem> crsList = StreamSupport
				.stream(container.getGEOPunkt().spliterator(), false)
				.map(gp -> {
					try {
						return gp.getGEOPunktAllg()
								.getGEOKoordinatensystem()
								.getWert();
					} catch (final NullPointerException e) {
						return null;
					}
				})
				.collect(Collectors.toSet());
		if (crsList.size() > 1) {
			final PlazError error = PlazFactory.eINSTANCE.createPlazError();
			error.setMessage(getGeneralErrMsg());
			error.setSeverity(ValidationSeverity.WARNING);
			error.setType(checkType());
			error.setObject(null);
			return List.of(error);
		}
		return Collections.emptyList();
	}

}
