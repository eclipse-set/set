/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.model.siteplan.FMAComponentType
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.toolboxmodel.BasisTypen.ENUMLinksRechts
import org.eclipse.set.toolboxmodel.Ortung.FMA_Komponente
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*

/**
 * Transforms PlanPro FMA_Komponente to Siteplan FMAComponents
 * 
 * @author Stuecker
 */
@Component(service=Transformator)
class FMAComponentTransformator extends BaseTransformator<FMA_Komponente> {
	@Reference
	TrackService trackService

	@Reference
	PositionService positionService

	/**
	 * Transforms a PlanPro FMA_Komponente to a Siteplan FMAComponent and adds it to the siteplan
	 * 
	 * @param fma the PlanPro FMA_Komponente
	 */
	override void transform(FMA_Komponente fma) {
		val result = SiteplanFactory.eINSTANCE.createFMAComponent
		// Currently only the axle counter is supported
		if (fma.FMAKomponenteAchszaehlpunkt === null)
			return;

		result.guid = fma.identitaet.wert
		result.position = positionService.transformPosition(
			trackService.getCoordinate(fma))
		result.type = FMAComponentType.AXLE
		result.rightSide = fma.singlePoints.get(0)?.seitlicheLage?.wert ===
			ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS
		result.label = fma.bezeichnung?.label
		fma.transformPunktObjektStrecke(result)
		result.addSiteplanElement(
			SiteplanPackage.eINSTANCE.siteplanState_FmaComponents)
	}
}
