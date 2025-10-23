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
package org.eclipse.set.feature.plazmodel.export;

import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;

/**
 * Helper class for export topological coordinate of Punkt_Objekt
 * 
 * @author truong
 * @param state
 *            the {@link ContainerType}
 * @param po
 *            the {@link Punkt_Objekt}
 * @param potk
 *            the {@link Punkt_Objekt_TOP_Kante_AttributeGroup} belong to the
 *            {@link Punkt_Objekt}
 * @param coordinate
 *            the {@link GEOKanteCoordinate}
 */
public record TopologischeCoordinate(ContainerType state, Punkt_Objekt po,
		Punkt_Objekt_TOP_Kante_AttributeGroup potk,
		GEOKanteCoordinate coordinate) {

}
