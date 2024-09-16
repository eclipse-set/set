/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.core.services.geometry.PointObjectPositionService
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehung
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehungslinie
import org.eclipse.set.model.siteplan.CantPoint
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

/**
 * Transforms PlanPro Ueberhoehungslinie to siteplan Cants 
 */
@Component(service=Transformator)
class CantTransform extends BaseTransformator<Ueberhoehungslinie> {
	@Reference
	PointObjectPositionService pointObjectPositionService

	@Reference
	PositionService positionService

	override void transform(Ueberhoehungslinie ue) {
		// Do not process incomplete cants
		if (ue.IDUeberhoehungA === null || ue.IDUeberhoehungA === null) {
			return
		}

		val it = SiteplanFactory.eINSTANCE.createCant

		guid = ue.identitaet?.wert
		form = ue.ueberhoehungslinieAllg?.ueberhoehungslinieForm?.wert?.
			toString ?: ""
		val ueLength = ue.ueberhoehungslinieAllg?.ueberhoehungslinieLaenge?.wert
		if (ueLength !== null)
			length = ueLength.doubleValue

		pointA = transformUeberhoehung(ue.IDUeberhoehungA.value)
		pointB = transformUeberhoehung(ue.IDUeberhoehungB.value)

		addSiteplanElement(it, SiteplanPackage.eINSTANCE.siteplanState_Cants)
	}

	private def CantPoint transformUeberhoehung(Ueberhoehung ue) {
		val result = SiteplanFactory.eINSTANCE.createCantPoint
		result.guid = ue.identitaet?.wert
		result.position = positionService.transformPosition(
			pointObjectPositionService.getCoordinate(ue)
		)
		result.height = (ue?.ueberhoehungAllg?.ueberhoehungHoehe?.wert ?:
			Double.valueOf(0.0)).doubleValue
		return result
	}

}
