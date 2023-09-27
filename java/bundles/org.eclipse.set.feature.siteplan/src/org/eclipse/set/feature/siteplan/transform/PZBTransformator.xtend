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
import org.eclipse.set.model.siteplan.PZB
import org.eclipse.set.model.siteplan.PZBEffectivity
import org.eclipse.set.model.siteplan.PZBElement
import org.eclipse.set.model.siteplan.PZBType
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.toolboxmodel.BasisTypen.ENUMLinksRechts
import org.eclipse.set.toolboxmodel.PZB.ENUMGUEAnordnung
import org.eclipse.set.toolboxmodel.PZB.ENUMPZBArt
import org.eclipse.set.toolboxmodel.PZB.ENUMWirksamkeit
import org.eclipse.set.toolboxmodel.PZB.PZB_Element
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*
import static extension org.eclipse.set.ppmodel.extensions.PZBElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*

/**
 * Transforms PlanPro PZB_Element to Siteplan PZB
 * 
 * @author Stuecker
 */
@Component(service=Transformator)
class PZBTransformator extends BaseTransformator<PZB_Element> {
	@Reference
	TrackService trackService
	
	@Reference
	PositionService positionService
	
	static val GSA_DISTANCE_AE = 7.0
	static val GSA_DISTANCE_EA = 3.0

	/**
	 * Transforms a PlanPro PZB_Element Komponente to Siteplan PZB(s) and adds it to the siteplan
	 * 
	 * @param pzb the PlanPro PZB_Element
	 */
	override void transform(PZB_Element pzb) {
		if (pzb.PZBElementGM !== null) {
			pzb.transformGM.addSiteplanElement(SiteplanPackage.eINSTANCE.siteplanState_Pzb)
		} else if (pzb.PZBElementGUE !== null) {
			// The PZB magnet only affects trains if the magnet is positioned to the 
			// right (in the direction the train is moving). As a result, the lateral 
			// position determines the direction of the GU, relative to the PZB_Element 
			val directionFollowsTopEdge = pzb.singlePoints.get(0)?.
				seitlicheLage?.wert === ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS
			val gu = SiteplanFactory.eINSTANCE.createPZBGU
			val gm = pzb.transformGM
			val gsm = pzb.transformGSA(directionFollowsTopEdge)
			val gse = pzb.transformGSE(directionFollowsTopEdge)
			#[gm, gsm, gse].forEach[
				gu.pzbs.add(it)
			]
			val length = pzb.PZBElementGUE.GUEMessstrecke
			if (length !== null) {
				gu.length = length.wert.intValue
			}
	
			// Use the guid of the first pzb as reference for this pzb
			gu.guid = gu.pzbs.head.guid
			gu.addSiteplanElement(SiteplanPackage.eINSTANCE.siteplanState_PzbGU)
		}
	}

	private def PZB transformPZB(PZB_Element pzb) {
		val result = SiteplanFactory.eINSTANCE.createPZB
		result.guid = pzb.identitaet?.wert
		result.position = positionService.transformPosition(
			trackService.getCoordinate(pzb)
		)
		result.element = pzb.PZBArt?.wert?.toElement
		result.rightSide = pzb.singlePoints.get(0)?.seitlicheLage?.wert ===
			ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS
		val pzbEl = pzb.PZBElementZuordnung.PZBElementZuordnungBP
		result.effectivity = pzbEl?.map[wirksamkeit?.wert?.toEffectivity]?.head
		pzb.transformPunktObjektStrecke(result)
		return result
	}

	private def PZB transformGM(PZB_Element pzb) {
		val gm = pzb.transformPZB
		gm.type = PZBType.GM
		return gm
	}

	private def PZB transformGSA(PZB_Element pzb,
		boolean directionFollowsTopEdge) {
		val isAE = pzb.PZBElementGUE?.GUEAnordnung?.wert ===
			ENUMGUEAnordnung.ENUMGUE_ANORDNUNG_2AE
		val distance = pzb.PZBElementGUE?.GUEMessstrecke?.wert.doubleValue
		val gsa = pzb.transformPZB
		gsa.type = PZBType.GUE_GSA
		var gsaDistance = isAE ? GSA_DISTANCE_AE + distance : -GSA_DISTANCE_EA
		if (!directionFollowsTopEdge)
			gsaDistance = -gsaDistance
		gsa.position = positionService.transformPosition(
			trackService.getCoordinateAt(pzb, gsaDistance)
		)
		return gsa
	}

	private def PZB transformGSE(PZB_Element pzb,
		boolean directionFollowsTopEdge) {
		var distance = pzb.PZBElementGUE?.GUEMessstrecke?.wert.doubleValue
		val gse = pzb.transformPZB
		gse.type = PZBType.GUE_GSE
		if (!directionFollowsTopEdge)
			distance = -distance
		gse.position = positionService.transformPosition(
			trackService.getCoordinateAt(pzb, distance)
		)
		return gse
	}

	def PZBElement toElement(ENUMPZBArt element) {
		switch (element) {
			case ENUMPZB_ART_1000_2000_HZ:
				return PZBElement.F1000_HZ2000_HZ
			case ENUMPZB_ART_1000_HZ:
				return PZBElement.F1000_HZ
			case ENUMPZB_ART_2000_HZ:
				return PZBElement.F2000_HZ
			case ENUMPZB_ART_500_HZ:
				return PZBElement.F500_HZ
		}
	}

	def PZBEffectivity toEffectivity(ENUMWirksamkeit value) {
		switch (value) {
			case ENUM_WIRKSAMKEIT_SCHALTBAR_VON_SIGNAL:
				return PZBEffectivity.SIGNAL
			case ENUM_WIRKSAMKEIT_STAENDIG_WIRKSAM:
				return PZBEffectivity.ALWAYS
			default:
				return PZBEffectivity.NONE
		}
	}
}
