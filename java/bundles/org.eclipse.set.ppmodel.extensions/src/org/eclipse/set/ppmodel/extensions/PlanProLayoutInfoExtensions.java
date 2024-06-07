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
package org.eclipse.set.ppmodel.extensions;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;

/**
 * Extensions for {@link PlanPro_Layoutinfo}
 */
public class PlanProLayoutInfoExtensions {

	/**
	 * Get {@link PlanPro_Layoutinfo} from xml resource
	 * 
	 * @param resource
	 *            the xml resource
	 * @return the layout infomartion
	 */
	public static PlanPro_Layoutinfo readFrom(final Resource resource) {
		final EList<EObject> contents = resource.getContents();
		if (contents.isEmpty()) {
			return null;
		}
		final EObject root = contents.getFirst();
		if (root instanceof final DocumentRoot layoutRoot) {
			return layoutRoot.getPlanProLayoutinfo();
		}
		throw new IllegalArgumentException(
				"Ressource contains no PlanPro layout info with the requested version."); //$NON-NLS-1$
	}

	/**
	 * Get objects count of layout information
	 * 
	 * @param layoutInfo
	 *            the layout
	 * @return object count
	 */
	public static int getSize(final PlanPro_Layoutinfo layoutInfo) {
		if (layoutInfo == null) {
			return 0;
		}
		return layoutInfo.eContents().size();
	}
}
