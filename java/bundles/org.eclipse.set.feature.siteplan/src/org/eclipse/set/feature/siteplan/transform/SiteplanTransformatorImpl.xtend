/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.text.ParseException
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.model.siteplan.Position
import org.eclipse.set.model.siteplan.Siteplan
import org.eclipse.set.model.siteplan.SiteplanObject
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.model.siteplan.SiteplanState
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference
import org.osgi.service.component.annotations.ReferenceCardinality
import org.osgi.service.component.annotations.ReferencePolicy
import org.osgi.service.component.annotations.ReferencePolicyOption
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckePunktExtensions.*

/**
 * Transforms the PlanPro data model into the siteplan data model 
 * 
 * @author Stuecker
 */
@Component
class SiteplanTransformatorImpl implements SiteplanTransformator {
	@Reference(cardinality=ReferenceCardinality.
		MULTIPLE, policy=ReferencePolicy.
		DYNAMIC, policyOption=ReferencePolicyOption.GREEDY)
	public final List<Transformator> transformators = newArrayList

	static final Logger logger = LoggerFactory.getLogger(
		typeof(SiteplanTransformator))

	@Reference
	protected TrackService trackService

	@Reference
	protected PositionService positionService

	/**
	 * Transforms a Container_AttributeGroup with PlanPro data to a Siteplan model
	 * 
	 * @params container the PlanPro data
	 * @return a siteplan model 
	 */
	override Siteplan transform(IModelSession modelSession) {
		transformThreads.clear
		return transform(modelSession.planProSchnittstelle)
	}

	private static class SiteplanMultiConainer {
		MultiContainer_AttributeGroup multiContainer
		ContainerType type

		new(MultiContainer_AttributeGroup container, ContainerType type) {
			this.multiContainer = container
			this.type = type
		}
	}

	/**
	 * Transforms a Container_AttributeGroup with PlanPro data to a Siteplan model
	 * 
	 * @params planproSchnittstelle the PlanPro data
	 * @return a siteplan model 
	 */
	def Siteplan transform(PlanPro_Schnittstelle planproSchnittstelle) {
		val siteplan = SiteplanPackage.eINSTANCE.siteplanFactory.createSiteplan
		// Create siteplan states
		siteplan.initialState = SiteplanPackage.eINSTANCE.siteplanFactory.
			createSiteplanState
		siteplan.commonState = SiteplanPackage.eINSTANCE.siteplanFactory.
			createSiteplanState
		siteplan.finalState = SiteplanPackage.eINSTANCE.siteplanFactory.
			createSiteplanState
		siteplan.changedInitialState = SiteplanPackage.eINSTANCE.
			siteplanFactory.createSiteplanState
		siteplan.changedFinalState = SiteplanPackage.eINSTANCE.siteplanFactory.
			createSiteplanState

		val multiContainer = planproSchnittstelle.multiContainer
		// Determine the initial and final states
		val initialLSTState = multiContainer.findFirst [
			type === ContainerType.INITIAL
		]?.multiContainer
		val finalLSTState = multiContainer.findFirst [
			type === ContainerType.FINAL
		]?.multiContainer
		val singleLSTState = multiContainer.findFirst [
			type === ContainerType.SINGLE
		]?.multiContainer

		if (initialLSTState !== null && finalLSTState !== null) {
			val state = Collections.synchronizedMap(
				<ContainerType, SiteplanState>newHashMap)
			multiContainer.filter[type !== ContainerType.SINGLE].toList.
				createTransformatorThread("TransformSiteplan", 2, [
					synchronized (state) {
						state.put(it.type, transformState(it.multiContainer))
					}
				])

			val initialState = state.get(ContainerType.INITIAL)
			val finalState = state.get(ContainerType.FINAL)

			// Create and add diffs to the siteplan
			#[
				SiteplanPackage.eINSTANCE.getSiteplanState_Signals(),
				SiteplanPackage.eINSTANCE.getSiteplanState_TrackSwitches(),
				SiteplanPackage.eINSTANCE.
					getSiteplanState_TrackSwitchEndMarkers(),
				SiteplanPackage.eINSTANCE.getSiteplanState_FmaComponents(),
				SiteplanPackage.eINSTANCE.getSiteplanState_Pzb(),
				SiteplanPackage.eINSTANCE.getSiteplanState_PzbGU(),
				SiteplanPackage.eINSTANCE.getSiteplanState_Routes(),
				SiteplanPackage.eINSTANCE.getSiteplanState_Stations(),
				SiteplanPackage.eINSTANCE.getSiteplanState_TrackLock(),
				SiteplanPackage.eINSTANCE.getSiteplanState_Tracks(),
				SiteplanPackage.eINSTANCE.getSiteplanState_TrackClosures(),
				SiteplanPackage.eINSTANCE.
					getSiteplanState_ExternalElementControls(),
				SiteplanPackage.eINSTANCE.getSiteplanState_Lockkeys(),
				SiteplanPackage.eINSTANCE.getSiteplanState_Cants(),
				SiteplanPackage.eINSTANCE.getSiteplanState_UnknownObjects()
			].forEach [
				siteplan.createDiffView(initialState, finalState, it)
			]
			// Add errors (no diff view) 
			siteplan.initialState.errors.addAll(initialState.errors.sortBy [
				relevantGUIDs.get(0)
			] ?: #[])
			siteplan.finalState.errors.addAll(finalState.errors.sortBy [
				relevantGUIDs.get(0)
			] ?: #[])
		} else if (singleLSTState !== null) {
			siteplan.commonState = transformState(singleLSTState)
		}

		siteplan.transformPlanningRegion(planproSchnittstelle)
		val layoutTransform = new LayoutTransformator(
			planproSchnittstelle.planpro_layoutinfo, positionService)
		layoutTransform.transformLayout(siteplan)

		// Set the leading position for centering the view
		siteplan.centerPosition = getLeadingPosition(planproSchnittstelle,
			planproSchnittstelle.getContainer(ContainerType.FINAL))

		if (transformThreads.exists[interrupted]) {
			logger.warn("Transformator Cancel")
			trackService.clearMetaData
			return null
		}
		return siteplan
	}

	private def List<SiteplanMultiConainer> getMultiContainer(
		PlanPro_Schnittstelle schnitstelle) {
		return ContainerType.values.map [
			new SiteplanMultiConainer(schnitstelle.getContainer(it), it)
		]
	}

	private def createDiffView(Siteplan siteplan, SiteplanState start,
		SiteplanState target, EReference ref) {

		val initialList = start.eGet(ref) as EList<SiteplanObject>
		val finalList = target.eGet(ref) as EList<SiteplanObject>

		val siteplanInitialList = siteplan.initialState.
			eGet(ref) as EList<SiteplanObject>
		val siteplanCommonList = siteplan.commonState.
			eGet(ref) as EList<SiteplanObject>
		val siteplanFinalList = siteplan.finalState.
			eGet(ref) as EList<SiteplanObject>

		val siteplanChangedInitialList = siteplan.changedInitialState.
			eGet(ref) as EList<SiteplanObject>
		val siteplanChangedFinalList = siteplan.changedFinalState.
			eGet(ref) as EList<SiteplanObject>
		val diffObjects = createListDiff(initialList, finalList, [guid])
		diffObjects.forEach [
			if (key === null)
				siteplanFinalList.add(value)
			else if (value === null)
				siteplanInitialList.add(key)
			else if (EcoreUtil.equals(key, value))
				siteplanCommonList.add(key)
			else {
				/* unequal but matched objects */
				siteplanChangedInitialList.add(key)
				siteplanChangedFinalList.add(value)
			}
		]
	}

	/**
	 * Determines the difference list between two lists according to a key
	 * 
	 * Returns a list of pairs <start, target> where
	 * 	<start, null> indicates the element was removed 
	 *  <null, target> indicates the element was added
	 * 	<start, target> indicates the element was neither removed nor added
	 * 
	 * Note that in the last case the element may still have been changed internally 
	 */
	private def <T> createListDiff(List<T> startList, List<T> targetList,
		Function1<T, String> keyFunc) {
		val outList = newArrayList

		val start = startList.sortBy(keyFunc)
		val target = targetList.sortBy(keyFunc)

		val startIt = start.iterator
		val targetIt = target.iterator

		if (!targetIt.hasNext)
			return start.map[it -> null]

		if (!startIt.hasNext)
			return target.map[null -> it]

		var startValue = startIt.next
		var targetValue = targetIt.next

		while (true) {
			val startGuid = keyFunc.apply(startValue)
			val targetGuid = keyFunc.apply(targetValue)

			if (startGuid > targetGuid) {
				outList.add(null -> targetValue)
				if (!startIt.hasNext) {
					while (targetIt.hasNext)
						outList.add(null -> targetIt.next)
					return outList
				}
				targetValue = targetIt.next
			} else if (startGuid < targetGuid) {
				outList.add(startValue -> null)

				if (!startIt.hasNext) {
					while (targetIt.hasNext)
						outList.add(null -> targetIt.next)
					return outList
				}
				startValue = startIt.next
			} else {
				// startGuid === targetGuid
				outList.add(startValue -> targetValue)
				if (!startIt.hasNext) {
					while (targetIt.hasNext)
						outList.add(null -> targetIt.next)
					return outList
				}

				if (!targetIt.hasNext) {
					while (startIt.hasNext)
						outList.add(startIt.next -> null)
					return outList
				}

				startValue = startIt.next
				targetValue = targetIt.next
			}

		}
	}

	private def create SiteplanPackage.eINSTANCE.siteplanFactory.createSiteplanState transformState(
		MultiContainer_AttributeGroup container) {
		transformators.createTransformatorThread(
			this.class.name,
			transformators.size,
			[ transformator |
				transformator.transformContainer(it, container)
			]
		)
	}

	/**
	 * Returns the leading position
	 * @param planProSchnittstelle the PlanPro_Schnittstelle
	 * @param container the container
	 * @route the leading position or null
	 */
	def Position getLeadingPosition(PlanPro_Schnittstelle planproSchnittstelle,
		MultiContainer_AttributeGroup container) {
		// Add main track information
		val mainRouteInfo = planproSchnittstelle?.LSTPlanungProjekt?.
			planungGruppe?.planungGFuehrendeStrecke

		if (mainRouteInfo === null) {
			return null
		}

		// Main route lookup from the list of routes
		val trackNumber = mainRouteInfo.streckeNummer.wert
		val mainRoutes = container.strecke.filter [ s |
			s.bezeichnung.bezeichnungStrecke.wert == trackNumber
		]

		if (mainRoutes.empty) {
			return null
		}

		var double trackKm
		try {
			trackKm = Double.parseDouble(
				mainRouteInfo.streckeKm.wert.replace(",", "")).doubleValue
		} catch (ParseException | NumberFormatException exc) {
			return null
		}
		val mainRoute = mainRoutes.head
		// Determine the coordinate at the point
		try {
			val mainCoordinate = mainRoute.getKilometerCoordinate(trackKm)
			val startEnd = mainRoute.startEnd
			if (startEnd === null)
				return null
			val crs = startEnd.head.geoKnoten.CRS
			return positionService.transformPosition(mainCoordinate, crs)
		} catch (RuntimeException exc) {
			return null
		}
	}

	override void stopTransform() {
		try {
			transformThreads.forEach [
				if (alive) {
					interrupt
				}
			]
		} catch (Exception e) {
			return;
		}
	}

}
