/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import java.util.List
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.getContainer

abstract class AbstractPlazContainerCheck {
	protected IModelSession modelSession

	def abstract protected List<PlazError> run(
		MultiContainer_AttributeGroup container)

	def List<PlazError> run(IModelSession modelSession) {
		this.modelSession = modelSession
		return List.of(ContainerType.INITIAL, ContainerType.FINAL,
			ContainerType.SINGLE).map [
			getContainer(modelSession.planProSchnittstelle, it)
		].filterNull.flatMap[run].toList
	}
}
