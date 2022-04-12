/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration

import com.google.common.collect.Lists
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.basis.integration.DiffLabelProvider
import org.eclipse.set.core.services.name.NameService
import org.eclipse.set.model.integrationview.Conflict
import org.eclipse.set.model.integrationview.Details
import org.eclipse.set.model.integrationview.IntegrationView
import org.eclipse.set.model.integrationview.IntegrationviewFactory
import org.eclipse.set.model.integrationview.ObjectQuantity
import org.eclipse.set.model.simplemerge.SMatch
import org.eclipse.set.model.temporaryintegration.TemporaryIntegration
import org.eclipse.set.core.services.merge.MergeService.Context
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Optional
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.set.model.simplemerge.extensions.ResolutionExtensions.*
import static extension org.eclipse.set.model.temporaryintegration.extensions.TemporaryIntegrationExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ContainerExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions.*
import static extension org.eclipse.set.utils.UriExtensions.*
import org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration

/**
 * Transforms a {@link TemporaryIntegration} into a {@link IntegrationView}.
 * 
 * @author Schaefer
 */
class SessionToIntegrationViewTransformation {

	val Messages messages

	val PlanProMergeContextProvider contextProvider

	val Map<Conflict, SMatch> conflictToMatch = new HashMap

	/**
	 * @param messages the translations
	 * @param nameService the name service
	 */
	new(Messages messages, NameService nameService) {
		this.messages = messages
		contextProvider = new PlanProMergeContextProvider(nameService)
	}

	/**
	 * The optional new model and merge directory can be provided, because the
	 * session may not be in merge mode.
	 * 
	 * @param session the model session
	 * @param secondaryPlanning an optional new model
	 * @param integrationDirectory an optional merge directory
	 * 
	 * @return the merge view
	 */
	def IntegrationView transform(
		IModelSession session,
		Optional<PlanPro_Schnittstelle> secondaryPlanning,
		Optional<String> secondaryPlanningName,
		Optional<String> integrationDirectory
	) {
		return session.transformUpdate(secondaryPlanning, secondaryPlanningName, integrationDirectory)
	}

	/**
	 * Lookup an already transformed match.
	 * 
	 * @param conflict the conflict
	 * 
	 * @return the match
	 */
	def SMatch getMatch(Conflict conflict) {
		return conflictToMatch.get(conflict)
	}

	private def IntegrationView create IntegrationviewFactory.eINSTANCE.createIntegrationView transformCreate(
		IModelSession session
	) {
	}

	private def IntegrationView transformUpdate(
		IModelSession session,
		Optional<PlanPro_Schnittstelle> secondaryPlanning,
		Optional<String> secondaryPlanningName,
		Optional<String> integrationDirectory
	) {
		val view = session.transformCreate

		// filenames
		view.primaryPlanning = session.primaryPlanningName
		view.secondaryPlanning =session.
			getSecondaryPlanningName(secondaryPlanningName)
		view.integrationDirectory = session.getMergeDirectory(
			integrationDirectory)
		view.compositePlanning = session.temporaryName

		// quantities
		val quantities = session.transformToObjectQuantities(secondaryPlanning)
		quantities.filter[!view.objectquantities.contains(it)].forEach [
			view.objectquantities.add(it)
		]

		// conflicts
		val conflicts = session.transformToConflicts
		conflicts.filter[!view.conflicts.contains(it)].forEach [
			view.conflicts.add(it)
		]

		return view
	}

	private def List<ObjectQuantity> transformToObjectQuantities(
		IModelSession session,
		Optional<PlanPro_Schnittstelle> secondaryPlanning
	) {
		val quantities = #[
			session.primaryPlanning?.transformToObjectQuantity(
				messages.IntegrationView_PrimaryPlanning),
			session.getSecondaryPlanning(secondaryPlanning)?.
				transformToObjectQuantity(
					messages.IntegrationView_SecondaryPlanning),
			session.compositePlanning?.transformToObjectQuantity(
				messages.IntegrationView_CompositePlanning),
			session.temporaryIntegration.orElse(null)?.automaticMatches?.
				transformToObjectQuantity(messages.IntegrationView_AutoMatches),
			session.temporaryIntegration.orElse(null)?.openConflictMatches?.
				transformToObjectQuantity(
					messages.IntegrationView_OpenConflicts),
			session.temporaryIntegration.orElse(null)?.manualResolvedMatches?.
				transformToObjectQuantity(
					messages.IntegrationView_ManualResolved)
		]
		return quantities.filterNull.toList
	}

	private def dispatch ObjectQuantity transformToObjectQuantity(
		Object object,
		String title
	) {
		throw new IllegalArgumentException(object.toString)
	}

	private def dispatch ObjectQuantity transformToObjectQuantity(
		PlanPro_Schnittstelle planning,
		String title
	) {
		val quantity = transformToObjectQuantityCreate(title)
		quantity.source = title
		quantity.initial = planning.LSTPlanungProjekt.planungGruppe.
			LSTPlanungEinzel.LSTZustandStart.container.size
		quantity.final = planning.LSTPlanungProjekt.planungGruppe.
			LSTPlanungEinzel.LSTZustandZiel.container.size
		return quantity
	}

	private def dispatch ObjectQuantity transformToObjectQuantity(
		Map<ContainerType, List<SMatch>> matches,
		String title
	) {
		val quantity = transformToObjectQuantityCreate(title)
		quantity.source = title
		quantity.initial = matches.get(ContainerType.INITIAL).size
		quantity.final = matches.get(ContainerType.FINAL).size
		return quantity
	}

	private def ObjectQuantity create IntegrationviewFactory.eINSTANCE.createObjectQuantity
	transformToObjectQuantityCreate(
		String title
	) {
	}

	private def List<Conflict> transformToConflicts(IModelSession session) {
		val result = Lists.newLinkedList
		if (session.mergeMode) {
			val temporaryIntegration = session.temporaryIntegration.get
			val conflictMatches = temporaryIntegration.conflictMatches
			result.addAll(
				conflictMatches.get(ContainerType.INITIAL).map [
					transformToConflict(ContainerType.INITIAL,
						temporaryIntegration)
				]
			)
			result.addAll(
				conflictMatches.get(ContainerType.FINAL).map [
					transformToConflict(ContainerType.FINAL,
						temporaryIntegration)
				]
			)
		}
		return result
	}

	private def Conflict transformToConflict(
		SMatch match,
		ContainerType type,
		ToolboxTemporaryIntegration integration
	) {
		val conflict = transformToConflictCreate(match, type, integration)

		// merge context
		val context = integration.getMergeContext(contextProvider, type)
		val elementProvider = context.configuration.elementProvider
		val matcher = context.configuration.matcher
		val labelProvider = context.configuration.labelProvider

		// container
		val primaryContainer = switch (type) {
			case INITIAL: {
				integration.primaryPlanning.LSTPlanungProjekt.planungGruppe.
					LSTPlanungEinzel.LSTZustandStart.container
			}
			case FINAL: {
				integration.primaryPlanning.LSTPlanungProjekt.planungGruppe.
					LSTPlanungEinzel.LSTZustandZiel.container
			}
			default: {
				throw new IllegalArgumentException(type.toString)
			}
		}
		val secondaryContainer = switch (type) {
			case INITIAL: {
				integration.secondaryPlanning.LSTPlanungProjekt.planungGruppe.
					LSTPlanungEinzel.LSTZustandStart.container
			}
			case FINAL: {
				integration.secondaryPlanning.LSTPlanungProjekt.planungGruppe.
					LSTPlanungEinzel.LSTZustandZiel.container
			}
			default: {
				throw new IllegalArgumentException(type.toString)
			}
		}

		// elements		
		val primaryElement = elementProvider.getElement(
			primaryContainer,
			match.guidPrimary,
			match.elementType
		).orElse(null)

		val secondaryElement = elementProvider.getElement(
			secondaryContainer,
			match.guidSecondary,
			match.elementType
		).orElse(null)
		val elements = #[primaryElement, secondaryElement].filterNull.toList

		// attributes
		conflict.id = match.id
		conflict.name = elements.getName(context)
		conflict.container = type.translate
		conflict.version = match.version
		conflict.resolution = if (match.resolution.resolved)
			messages.IntegrationView_Resolved
		else
			messages.IntegrationView_Unresolved

		// details
		if (conflict.details.empty) {
			val attributes = matcher.getNonEmptyAttributes(primaryElement,
				secondaryElement)
			conflict.details.addAll(
				attributes.map [
					transformToDetails(primaryElement, secondaryElement,
						labelProvider)
				]
			)
		}

		// remember conflict -> match mapping
		conflictToMatch.put(conflict, match);

		return conflict
	}

	private def Conflict create IntegrationviewFactory.eINSTANCE.createConflict
	transformToConflictCreate(
		SMatch match,
		ContainerType type,
		ToolboxTemporaryIntegration integration
	) {
	}

	private def Details create IntegrationviewFactory.eINSTANCE.createDetails
	transformToDetails(
		List<String> path,
		EObject primaryElement,
		EObject secondaryElement,
		DiffLabelProvider labelProvider
	) {
		attributePath = labelProvider.getPathLabel(
			#{primaryElement, secondaryElement}.filterNull.head, path)
		valuePrimaryPlanning = labelProvider.getAttributeLabel(primaryElement,
			path)
		valueSecondaryPlanning = labelProvider.
			getAttributeLabel(secondaryElement, path)

		return
	}

	private static def String getPrimaryPlanningName(IModelSession session) {
		if (session.mergeMode) {
			return session.temporaryIntegration.get.primaryPlanningFilename
		}
		return session.toolboxFile.path.fileName.toString
	}

	private static def String getSecondaryPlanningName(
		IModelSession session,
		Optional<String> secondaryPlanningName
	) {
		if (session.mergeMode) {
			return session.temporaryIntegration.get.secondaryPlanningFilename
		}
		return secondaryPlanningName.orElse(null)?.toString
	}
	
	private static def String getTemporaryName(
		IModelSession session) {
		if (session.mergeMode) {
			return session.toolboxFile.path.fileName.toString
		}
		return null
	}

	private static def String getMergeDirectory(
		IModelSession session,
		Optional<String> integrationDirectory
	) {
		if (session.mergeMode) {
			return session.temporaryIntegration.get.integrationDirectory
		}
		if (integrationDirectory.isPresent) {
			return integrationDirectory.get
		}
		return session.defaultDir
	}

	private static def String getDefaultDir(IModelSession session) {
		return session.planProSchnittstelle.eResource.URI.directory.toString
	}

	private static def PlanPro_Schnittstelle getPrimaryPlanning(
		IModelSession session
	) {
		if (session.mergeMode) {
			return session.temporaryIntegration.get.primaryPlanning
		}
		return session.planProSchnittstelle
	}

	private static def PlanPro_Schnittstelle getSecondaryPlanning(
		IModelSession session,
		Optional<PlanPro_Schnittstelle> secondaryPlanning
	) {
		if (session.mergeMode) {
			return session.temporaryIntegration.get.secondaryPlanning
		}
		if (secondaryPlanning.present) {
			return secondaryPlanning.get
		}
		return null
	}

	private static def PlanPro_Schnittstelle getCompositePlanning(
		IModelSession session
	) {
		if (session.mergeMode) {
			return session.temporaryIntegration.get.compositePlanning
		}
		return null
	}

	private def dispatch String translate(Enum<?> e) {
		throw new IllegalArgumentException(e.toString)
	}

	private def dispatch String translate(ContainerType type) {
		switch (type) {
			case INITIAL:
				return messages.ContainerValues_Start
			case FINAL:
				return messages.ContainerValues_Ziel
			default: {
				throw new IllegalArgumentException(type.toString)
			}
		}
	}

	private def String getName(
		List<EObject> elements,
		Context context
	) {
		// element labels
		val labels = elements.map [
			context.configuration.labelProvider.getElementLabel(it)
		].toSet

		return '''«FOR label : labels SEPARATOR "/"»«label»«ENDFOR»'''
	}

	private def String getVersion(SMatch match) {
		val resolution = match.resolution
		switch (resolution) {
			case PRIMARY_UNRESOLVED,
			case PRIMARY_AUTO,
			case PRIMARY_MANUAL:
				return messages.IntegrationView_Primary
			case SECONDARY_AUTO,
			case SECONDARY_MANUAL:
				return messages.IntegrationView_Secondary
			default: {
				throw new IllegalArgumentException(resolution.toString)
			}
		}
	}
}
