/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.core.services.geometry.GeoKanteGeometryService
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehung
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Gleis_Abschluss
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

/**
 * Transforms unknown PlanPro Punkt_Objekte to siteplan UnknownPositiondObject 
 */
@Component(service=Transformator)
class UnknownObjectTransform extends BaseTransformator<Punkt_Objekt> {
	@Reference
	GeoKanteGeometryService geometryService

	@Reference
	PositionService positionService

	override void transform(Punkt_Objekt pu) {
		// Filter objects where a more concrete transformator exists
		for (Class<?> cl : #[
			FMA_Komponente,
			PZB_Element,
			Signal,
			Signal_Befestigung,
			W_Kr_Gsp_Komponente,
			Ueberhoehung,
			Gleis_Abschluss
		]) {
			if (cl.isInstance(pu))
				return
		}

		transformUnknownObject(pu)
	}

	private def void transformUnknownObject(Punkt_Objekt p) {
		val result = SiteplanFactory.eINSTANCE.createUnknownPositionedObject
		result.guid = p.identitaet?.wert
		result.position = positionService.transformPosition(
			geometryService.getCoordinate(p)
		)
		result.objectType = p.class.interfaces.get(0).simpleName
		addSiteplanElement(result,
			SiteplanPackage.eINSTANCE.siteplanState_UnknownObjects)
	}

}
