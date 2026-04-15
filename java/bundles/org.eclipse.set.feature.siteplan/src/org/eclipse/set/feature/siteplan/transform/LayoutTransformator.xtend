/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.util.Map
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.siteplan.Coordinate
import org.eclipse.set.model.siteplan.SheetCut
import org.eclipse.set.model.siteplan.Siteplan
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem
import org.eclipse.set.model.planpro.Layoutinformationen.Lageplan_Blattschnitt
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo
import org.eclipse.set.utils.ToolboxConfiguration

/**
 * Transform layout informationen from the PlanPro model to a siteplan layout
 * 
 * @author Truong
 */
class LayoutTransformator {

	public static ENUMGEOKoordinatensystem selectedCRS
	PlanPro_Layoutinfo layoutInfo
	PositionService positionService

	static Map<String, ENUMGEOKoordinatensystem> crsMap = newHashMap(
		new Pair("DR0", ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_DR0),
		new Pair("CR0", ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_CR0),
		new Pair("FR0", ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_FR0),
		new Pair("ER0", ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_ER0)
	)

	new(PlanPro_Layoutinfo layoutInfo, PositionService positionService) {
		if (selectedCRS === null) {
			ToolboxConfiguration.defaultSheetCutCRS.setCRS
		}

		this.layoutInfo = layoutInfo
		this.positionService = positionService
	}

	def void transformLayout(Siteplan siteplan) {
		layoutInfo?.lageplan?.forEach [
			val layoutInfo = SiteplanFactory.eINSTANCE.createLayoutinfo
			layoutInfo.guid = identitaet?.wert
			layoutInfo.label = bezeichnung?.bezeichnungLageplan?.wert
			IDLageplanBlattschnitt.forEach [
				layoutInfo.sheetsCut.add(value.transformSheetCut)
			]
			siteplan.layoutInfo.add(layoutInfo)
		]
	}

	private def SheetCut transformSheetCut(Lageplan_Blattschnitt sheetCut) {
		val result = SiteplanFactory.eINSTANCE.createSheetCut
		result.guid = sheetCut?.identitaet?.wert
		result.label = sheetCut?.bezeichnung?.bezLageplanBlattschnitt?.wert?.
			toString
		result.polygonDirection.addAll(
			sheetCut?.polygonzugAusrichtung?.wert?.transformCoordinate ?: #[])
		result.polygon.addAll(
			sheetCut?.polygonzugBlattschnitt?.wert?.transformCoordinate ?: #[])
		return result
	}

	private def Iterable<Coordinate> transformCoordinate(String coorString) {
		val coors = coorString.split(" ")
		val result = <Coordinate>newLinkedList
		coors.forEach [ value, index |
			val coor = SiteplanFactory.eINSTANCE.createCoordinate
			try {
				if (index % 2 === 0) {
					coor.x = Double.valueOf(value)
				} else {
					result.get(index - 1).y = Double.valueOf(value)
				}
				result.add(coor)
			} catch (NumberFormatException e) {
			}
		]
		return result.filter[x !== 0 && y !== 0].map [
			positionService.transformCoordinate(x, y,
				LayoutTransformator.
					selectedCRS)
		]
	}

	static def ENUMGEOKoordinatensystem setCRS(String newCRS) {
		//By default fallback to CR0 System
		selectedCRS = crsMap.getOrDefault(newCRS,
			ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_CR0)
	}

}
