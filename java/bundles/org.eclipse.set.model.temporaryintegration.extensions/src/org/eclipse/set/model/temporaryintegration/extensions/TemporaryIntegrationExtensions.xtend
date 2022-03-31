/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration.extensions

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Container_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.model.simplemerge.Resolution
import org.eclipse.set.model.simplemerge.SComparison
import org.eclipse.set.model.simplemerge.SMatch
import org.eclipse.set.model.temporaryintegration.TemporaryIntegration
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationFactory
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage
import org.eclipse.set.model.temporaryintegration.extensions.command.MergeCommand
import org.eclipse.set.core.services.merge.MergeService
import org.eclipse.set.core.services.merge.MergeService.Context
import org.eclipse.set.core.services.merge.MergeService.ContextProvider
import org.eclipse.set.utils.IntegerGenerator
import java.util.List
import java.util.Map
import org.eclipse.emf.edit.domain.EditingDomain

import static extension org.eclipse.set.model.simplemerge.extensions.ResolutionExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions.*
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * Extensions for {@link TemporaryIntegration}.
 * 
 * @author Schaefer
 */
class TemporaryIntegrationExtensions {

	/**
	 * Merge the plannings connected to this session.
	 * 
	 * @param integration this temporary integration
	 * @param service the merge service to use
	 * @param contextProvider provides merge context for primary container, secondary container
	 */
	static def void automaticMerge(
		TemporaryIntegration integration,
		MergeService<SComparison> service,
		ContextProvider contextProvider
	) {
		if (integration.merged) {
			throw new IllegalStateException("Session already merged.")
		}
		val matchIdGenerator = new IntegerGenerator

		// calculate total work
		val initialContext = integration.getInitialMergeContext(contextProvider)
		val finalContext = integration.getFinalMergeContext(contextProvider)

		// merge initial
		val initialChangeDescription = service.createChangeDescription(
			initialContext,
			matchIdGenerator
		)
		integration.comparisonInitialState = initialChangeDescription.model
		service.automaticMerge(
			initialContext,
			initialChangeDescription
		)

		// merge final
		val finalChangeDescription = service.createChangeDescription(
			finalContext,
			matchIdGenerator
		)
		integration.comparisonFinalState = finalChangeDescription.model
		service.automaticMerge(
			finalContext,
			finalChangeDescription
		)
	}

	static def void manualMerge(
		TemporaryIntegration integration,
		EditingDomain editingDomain,
		SMatch match,
		Resolution resolution,
		ContextProvider contextProvider
	) {
		// find the source planning and source/replaced element GUIDs
		var PlanPro_Schnittstelle sourcePlanning
		var String sourceGuid
		var String replacedElementGuid
		switch (resolution) {
			case PRIMARY_UNRESOLVED,
			case PRIMARY_MANUAL: {
				// we use the primary planning and primary guid for unresolved and primary resolutions
				sourcePlanning = integration.primaryPlanning
				sourceGuid = match.guidPrimary
				replacedElementGuid = match.guidSecondary
			}
			case SECONDARY_MANUAL: {
				sourcePlanning = integration.secondaryPlanning
				sourceGuid = match.guidSecondary
				replacedElementGuid = match.guidPrimary
			}
			default: {
				// we consider *_AUTO resolutions as illegal in the context of a manual merge
				throw new IllegalArgumentException(resolution.toString)
			}
		}

		// the destination planning is the composite planning
		val destinationPlanning = integration.compositePlanning

		// whether the match refers to the initial or final container
		val containerType = match.type

		// find the source/destination containers
		var Container_AttributeGroup sourceContainer
		var Container_AttributeGroup destinationContainer
		switch (containerType) {
			case FINAL: {
				sourceContainer = sourcePlanning.LSTPlanungProjekt.
					planungGruppe.LSTPlanungEinzel.LSTZustandZiel.container
				destinationContainer = destinationPlanning.LSTPlanungProjekt.
					planungGruppe.LSTPlanungEinzel.LSTZustandZiel.container
			}
			case INITIAL: {
				sourceContainer = sourcePlanning.LSTPlanungProjekt.
					planungGruppe.LSTPlanungEinzel.LSTZustandStart.container
				destinationContainer = destinationPlanning.LSTPlanungProjekt.
					planungGruppe.LSTPlanungEinzel.LSTZustandStart.container
			}
			default: {
				// we consider SINGLE containers as illegal
				throw new IllegalArgumentException(containerType.toString)
			}
		}

		// find the source/replaced elements and containing features
		val context = contextProvider.getContext(destinationContainer,
			sourceContainer)
		val elementProvider = context.configuration.elementProvider
		val sourceElement = elementProvider.getElement(sourceContainer,
			sourceGuid, match.elementType).orElse(null) as Ur_Objekt
		val replacedElement = elementProvider.getElement(destinationContainer,
			replacedElementGuid, match.elementType).orElse(null) as Ur_Objekt
		val containingFeature = #{sourceElement, replacedElement}.filterNull.
			head?.eContainingFeature

		// execute the merge command
		val replace = MergeCommand.create(
			editingDomain,
			destinationContainer,
			containingFeature,
			replacedElement,
			sourceElement,
			match,
			resolution
		)
		editingDomain.commandStack.execute(replace)
	}

	/**
	 * @param session this temporary integration
	 * 
	 * @return the conflicts of the initial containers
	 */
	static def List<SMatch> getConflictsInitial(
		TemporaryIntegration integration) {
		if (!integration.merged) {
			throw new IllegalStateException("Session not yet merged.")
		}
		return integration.comparisonInitialState.matches.filter [
			!resolution.automatic
		].toList
	}

	static def List<SMatch> getConflictsFinal(
		TemporaryIntegration integration) {
		if (!integration.merged) {
			throw new IllegalStateException("Session not yet merged.")
		}
		return integration.comparisonFinalState.matches.filter [
			!resolution.automatic
		].toList
	}

	/**
	 * @param integration this temporary integration
	 * @param contextProvider the context provider
	 * @param type the container type
	 * 
	 * @return the merge context
	 */
	static def Context getMergeContext(TemporaryIntegration integration,
		ContextProvider contextProvider, ContainerType type) {
		switch (type) {
			case INITIAL: {
				return integration.getInitialMergeContext(contextProvider)
			}
			case FINAL: {
				return integration.getFinalMergeContext(contextProvider)
			}
			default: {
				throw new IllegalArgumentException(type.toString)
			}
		}
	}

	private static def Context getInitialMergeContext(
		TemporaryIntegration integration, ContextProvider contextProvider) {
		val primaryContainer = integration.compositePlanning.LSTPlanungProjekt.
			planungGruppe.LSTPlanungEinzel.LSTZustandStart.container
		val secondaryContainer = integration.secondaryPlanning.
			LSTPlanungProjekt.planungGruppe.LSTPlanungEinzel.LSTZustandStart.
			container
		return contextProvider.getContext(primaryContainer, secondaryContainer)
	}

	private static def Context getFinalMergeContext(
		TemporaryIntegration integration, ContextProvider contextProvider) {
		val primaryContainer = integration.compositePlanning.LSTPlanungProjekt.
			planungGruppe.LSTPlanungEinzel.LSTZustandZiel.container
		val secondaryContainer = integration.secondaryPlanning.
			LSTPlanungProjekt.planungGruppe.LSTPlanungEinzel.LSTZustandZiel.
			container
		return contextProvider.getContext(primaryContainer, secondaryContainer)
	}

	/**
	 * @param session this temporary integration
	 * 
	 * @return whether the session was merged
	 */
	static def boolean isMerged(TemporaryIntegration integration) {
		if (integration.comparisonInitialState !== null &&
			integration.comparisonFinalState !== null) {
			return true
		}
		if (integration.comparisonInitialState === null &&
			integration.comparisonFinalState === null) {
			return false
		}
		throw new IllegalStateException("Illegal merge state.")
	}

	/**
	 * @param session this temporary integration
	 * 
	 * @return a map of initial and final automatic matches
	 */
	static def Map<ContainerType, List<SMatch>> getAutomaticMatches(
		TemporaryIntegration integration
	) {
		return #{
			ContainerType.INITIAL ->
				integration.comparisonInitialState.matches.filter [
					resolution.automatic
				].toList,
			ContainerType.FINAL ->
				integration.comparisonFinalState.matches.filter [
					resolution.automatic
				].toList
		}
	}

	/**
	 * @param session this temporary integration
	 * 
	 * @return a map of initial and final conflict matches
	 */
	static def Map<ContainerType, List<SMatch>> getConflictMatches(
		TemporaryIntegration integration
	) {
		return #{
			ContainerType.INITIAL ->
				integration.comparisonInitialState.matches.filter [
					!resolution.automatic
				].toList,
			ContainerType.FINAL ->
				integration.comparisonFinalState.matches.filter [
					!resolution.automatic
				].toList
		}
	}

	/**
	 * @param session this temporary integration
	 * 
	 * @return a map of initial and final open conflict matches
	 */
	static def Map<ContainerType, List<SMatch>> getOpenConflictMatches(
		TemporaryIntegration integration
	) {
		return #{
			ContainerType.INITIAL ->
				integration.comparisonInitialState.matches.filter [
					resolution === Resolution.PRIMARY_UNRESOLVED
				].toList,
			ContainerType.FINAL ->
				integration.comparisonFinalState.matches.filter [
					resolution === Resolution.PRIMARY_UNRESOLVED
				].toList
		}
	}

	/**
	 * @param session this temporary integration
	 * 
	 * @return a map of initial and final manual resolved matches
	 */
	static def Map<ContainerType, List<SMatch>> getManualResolvedMatches(
		TemporaryIntegration integration
	) {
		return #{
			ContainerType.INITIAL ->
				integration.comparisonInitialState.matches.filter [
					resolution.manualResolved
				].toList,
			ContainerType.FINAL ->
				integration.comparisonFinalState.matches.filter [
					resolution.manualResolved
				].toList
		}
	}

	static def TemporaryIntegration create(
		ToolboxFile primaryPlanningToolboxFile,
		boolean primaryPlanningWasValid,
		ToolboxFile secondaryPlanningToolboxFile,
		boolean secondaryPlanningWasValid,
		String mergeDir
	) {
		// content
		val temporaryIntegration = TemporaryintegrationFactory.eINSTANCE.
			createTemporaryIntegration
		val primaryPlanning = readFrom(primaryPlanningToolboxFile.resource)
		temporaryIntegration.primaryPlanning = EcoreUtil.copy(primaryPlanning)
		temporaryIntegration.primaryPlanningFilename = primaryPlanningToolboxFile.path.fileName.toString
		temporaryIntegration.primaryPlanningWasValid = primaryPlanningWasValid
		temporaryIntegration.compositePlanning = EcoreUtil.copy(primaryPlanning)
		temporaryIntegration.secondaryPlanning = EcoreUtil.copy(readFrom(secondaryPlanningToolboxFile.resource))
		temporaryIntegration.secondaryPlanningFilename = secondaryPlanningToolboxFile.path.fileName.toString
		temporaryIntegration.secondaryPlanningWasValid = secondaryPlanningWasValid
		temporaryIntegration.integrationDirectory = mergeDir.toString

		return temporaryIntegration
	}

	private static def ContainerType getType(SMatch match) {
		switch (match.eContainer.eContainingFeature) {
			case TemporaryintegrationPackage.eINSTANCE.
				temporaryIntegration_ComparisonInitialState: {
				return ContainerType.INITIAL
			}
			case TemporaryintegrationPackage.eINSTANCE.
				temporaryIntegration_ComparisonFinalState: {
				return ContainerType.FINAL
			}
			default: {
				throw new IllegalArgumentException(match.toString)
			}
		}
	}
}
