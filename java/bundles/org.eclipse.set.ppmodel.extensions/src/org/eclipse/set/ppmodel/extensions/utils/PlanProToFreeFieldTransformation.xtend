/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.basis.FreeFieldInfo
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.core.services.session.SessionService
import org.eclipse.set.basis.files.ToolboxFileRole
import org.eclipse.set.basis.FreeFieldInfo.SignificantInformation
import org.eclipse.set.basis.FreeFieldInfo.LoadedPlanInformation

/**
 * Transformation from {@link IModelSession} to {@link FreeFieldInfo}.
 * 
 * @author Schaefer
 */
class PlanProToFreeFieldTransformation {
	SessionService sessionService

	private new(SessionService sessionService) {
		this.sessionService = sessionService
	}

	private def IModelSession mainSession() {
		return sessionService.getLoadedSession(ToolboxFileRole.SESSION)
	}

	private def IModelSession compareSession() {
		return sessionService.getLoadedSession(ToolboxFileRole.COMPARE_PLANNING)
	}

	/**
	 * Creates a new transformation.
	 */
	def static PlanProToFreeFieldTransformation create(
		SessionService sessionService) {
		return new PlanProToFreeFieldTransformation(sessionService)
	}

	/**
	 * Transforms a PlanPro Schnittstelle to a FreeFieldInfo.
	 */
	def FreeFieldInfo transform() {

		return new FreeFieldInfo(
			new SignificantInformation(mainSession.transformLoadedPlan,
				compareSession.transformLoadedPlan))
	}

	def LoadedPlanInformation transformLoadedPlan(IModelSession session) {
		if (session === null) {
			return null
		}
		val name = session?.toolboxFile?.path?.fileName?.toString
		val timestamp = session.planProSchnittstelle.timestamp
		val checksum = session.toolboxFile.checksum
		return new LoadedPlanInformation(name, checksum, timestamp)
	}

	private def String getTimestamp(PlanPro_Schnittstelle schnittstelle) {
		return String.format("%1$td.%1$tm.%1$tY %1$tT",
			schnittstelle.planProSchnittstelleAllg.erzeugungZeitstempel.wert.
				toGregorianCalendar)
	}
}
