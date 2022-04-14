/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.simplemerge.extensions

import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage
import org.eclipse.set.core.services.merge.MergeService.Context
import java.util.List
import java.util.Optional
import org.eclipse.emf.ecore.EObject
import org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration
import org.eclipse.set.model.simplemerge.SMatch

/**
 * Extensions for {@link SMatch}.
 * 
 * @author Schaefer
 */
class SMatchExtensions {

	/**
	 * @param match the match
	 * @param context the merge context
	 * 
	 * @return a representation, optimized for log output
	 */
	static def String logString(SMatch match, Context context) {
		val elementProvider = context.configuration.elementProvider
		val matcher = context.configuration.matcher
		val labelProvider = context.configuration.labelProvider
		val primaryContainer = context.primaryContainer
		val secondaryContainer = context.secondaryContainer

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
		val differences = matcher.getDifferences(
			primaryElement,
			secondaryElement
		)
		val differencesLabel = '''[«FOR diff : differences SEPARATOR " "»«diff.getLabel(
			primaryElement, secondaryElement, context
		)»«ENDFOR»]'''
		val primaryLabel = labelProvider.getElementLabel(primaryElement)
		val secondaryLabel = labelProvider.getElementLabel(secondaryElement)
		return '''{primary=«primaryLabel» secondary=«secondaryLabel» differences=«differencesLabel»}'''
	}

	static def String theNonNullGuid(SMatch match) {
		val guids = #{match.guidPrimary, match.guidSecondary}.filterNull

		if (guids.size !== 1) {
			throw new IllegalArgumentException(match.toString)
		}

		return guids.head
	}

	static def ContainerType getContainerType(SMatch match) {
		val containingFeature = match.eContainer.eContainingFeature

		if (containingFeature === TemporaryintegrationPackage.eINSTANCE.
			toolboxTemporaryIntegration_ComparisonInitialState) {
			return ContainerType.INITIAL
		}

		if (containingFeature === TemporaryintegrationPackage.eINSTANCE.
			toolboxTemporaryIntegration_ComparisonFinalState) {
			return ContainerType.FINAL
		}

		throw new IllegalArgumentException(match.toString)
	}

	static def ToolboxTemporaryIntegration getIntegration(SMatch match) {
		return getIntegrationDispatch(match)
	}

	private static def dispatch ToolboxTemporaryIntegration getIntegrationDispatch(
		ToolboxTemporaryIntegration object
	) {
		return object
	}

	private static def dispatch ToolboxTemporaryIntegration getIntegrationDispatch(
		EObject object
	) {
		return object.eContainer.integrationDispatch
	}

	static def Optional<EObject> getPrimaryElement(SMatch match,
		Context context) {
		return context.configuration.elementProvider.getElement(
			context.primaryContainer,
			match.guidPrimary,
			match.elementType
		)
	}

	static def Optional<EObject> getSecondaryElement(SMatch match,
		Context context) {
		return context.configuration.elementProvider.getElement(
			context.secondaryContainer,
			match.guidSecondary,
			match.elementType
		)
	}

	private static def String getLabel(
		List<String> diff,
		EObject primaryElement,
		EObject secondaryElement,
		Context context
	) {
		val labelProvider = context.configuration.labelProvider

		val diffPath = '''«FOR segment : diff SEPARATOR "."»«segment»«ENDFOR»'''
		val primaryValue = labelProvider.getAttributeLabel(
			primaryElement,
			diff
		)
		val secondaryValue = labelProvider.getAttributeLabel(
			secondaryElement,
			diff
		)

		return '''{«diffPath»: «primaryValue» -> «secondaryValue»}'''
	}
}
