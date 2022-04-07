/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration;

import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup
import org.eclipse.set.core.services.merge.MergeService.Authority
import org.eclipse.set.core.services.merge.MergeService.Responsibility
import java.util.Collections
import java.util.Set

import static extension org.eclipse.set.ppmodel.extensions.ContainerExtensions.*

/**
 * PlanPro responsibility is inferred by the Planungsbereich of the particular
 * container.
 * 
 * @author Schaefer
 */
class PlanProResponsibility implements Responsibility {

	val Set<String> primaryPlanningArea
	val Set<String> secondaryPlanningArea

	new(Container_AttributeGroup primaryContainer,
		Container_AttributeGroup secondaryContainer) {
		val primaryPlanning = primaryContainer.planungEinzel
		val secondaryPlanning = secondaryContainer.planungEinzel
		primaryPlanningArea = (primaryPlanning.LSTObjektePlanungsbereich?.
			IDLSTObjektPlanungsbereich?.map[identitaet?.wert] ?: Collections.emptyList).
			filterNull.toSet
		secondaryPlanningArea = (secondaryPlanning.LSTObjektePlanungsbereich?.
			IDLSTObjektPlanungsbereich?.map[identitaet?.wert] ?: Collections.emptyList).
			filterNull.toSet
	}

	override Authority getAuthority(String primaryGuid, String secondaryGuid) {
		if (primaryPlanningArea.contains(primaryGuid) &&
			!secondaryPlanningArea.contains(secondaryGuid)) {
			return Authority.PRIMARY
		}
		if (!primaryPlanningArea.contains(primaryGuid) &&
			secondaryPlanningArea.contains(secondaryGuid)) {
			return Authority.SECONDARY
		}
		return Authority.NONE;
	}
}
