/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration.service

import com.google.common.collect.Lists
import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.integration.ChangeDescription
import org.eclipse.set.basis.integration.Matcher
import org.eclipse.set.model.simplemerge.Resolution
import org.eclipse.set.model.simplemerge.SComparison
import org.eclipse.set.model.simplemerge.SMatch
import org.eclipse.set.model.simplemerge.SimplemergeFactory
import org.eclipse.set.core.services.merge.MergeService
import java.util.LinkedList
import java.util.List
import java.util.function.Supplier
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.osgi.service.component.annotations.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.model.simplemerge.extensions.SMatchExtensions.*

/**
 * A simple implementation of {@link MergeService}.
 * 
 * @author Schaefer
 */
@Component
class SimpleMergeService implements MergeService<SComparison> {

	static final Logger LOGGER = LoggerFactory.getLogger(
		typeof(SimpleMergeService)
	)

	Supplier<Integer> matchIdGenerator

	private static class ChangeDescriptionImpl implements ChangeDescription<SComparison> {

		val SComparison model

		new(SComparison model) {
			this.model = model
		}

		override getModel() {
			return model
		}
	}

	override createChangeDescription(
		Context context,
		Supplier<Integer> matchIdGenerator
	) {
		this.matchIdGenerator = matchIdGenerator
		val comparison = transform(context)
		return new ChangeDescriptionImpl(comparison)
	}

	private def SComparison create SimplemergeFactory.eINSTANCE.createSComparison transform(
		Context context
	) {
		val secondaryContainer = context.secondaryContainer
		val matchesPerType = secondaryContainer.elementTypes.map [
			it.transform(context)
		]
		matches.addAll(matchesPerType.flatten)
		return
	}

	private def List<SMatch> create new LinkedList transform(
		String elementType,
		Context context
	) {
		val configuration = context.configuration
		val primaryContainer = context.primaryContainer
		val secondaryContainer = context.secondaryContainer
		val matcher = configuration.matcher
		val primaryElements = primaryContainer.getElements(elementType)
		val secondaryElements = secondaryContainer.getElements(elementType)

		// find removed elements
		addAll(
			primaryElements.filter [
				secondaryElements.matchingElements(it, matcher).empty
			].map [
				it.transformToMatch(null, elementType, configuration)
			]
		)

		// find changed elements
		addAll(
			secondaryElements.map [
				transformToMatches(
					primaryElements.matchingElements(it, matcher),
					it,
					elementType,
					configuration
				)
			].flatten
		)
		return
	}

	private def SMatch create SimplemergeFactory.eINSTANCE.createSMatch transformToMatch(
		EObject primaryElement,
		EObject secondaryElement,
		String elementType,
		Configuration configuration
	) {
		Assert.isNotNull(elementType)
		id = matchIdGenerator.get
		guidPrimary = primaryElement?.getGuid(configuration)
		guidSecondary = secondaryElement?.getGuid(configuration)
		it.elementType = elementType
		return
	}

	private def List<SMatch> transformToMatches(
		List<EObject> primaryElements,
		EObject secondaryElement,
		String elementType,
		Configuration configuration
	) {
		val result = Lists.newLinkedList
		val matcher = configuration.matcher

		if (primaryElements.empty) {
			// if there is no matching primary element, we create an add-match
			result.add(
				transformToMatch(
					null,
					secondaryElement,
					elementType,
					configuration
				)
			)
		} else {
			// otherwise we create matches for different elements
			primaryElements.forEach [
				if (matcher.isDifferent(it, secondaryElement)) {
					result.add(
						transformToMatch(
							it,
							secondaryElement,
							elementType,
							configuration
						)
					)
				}
			]
		}

		return result
	}

	private def String getGuid(EObject element, Configuration configuration) {
		return configuration.guidProvider.getGuid(element)
	}

	private def List<String> getElementTypes(EObject container) {
		return container.eClass.EAllReferences.map[name]
	}

	private def List<EObject> getElements(
		EObject container,
		String elementType
	) {
		val feature = container.eClass.getEStructuralFeature(elementType)
		val value = container.eGet(feature)
		return value as EList<EObject>
	}

	private def List<EObject> matchingElements(
		List<EObject> elements,
		EObject element,
		Matcher matcher
	) {
		return elements.filter[matcher.match(it, element)].toList
	}

	override automaticMerge(
		Context context,
		ChangeDescription<SComparison> description
	) {
		val responsibility = context.responsibility
		for (match : description.model.matches) {
			val guidPrimary = match.guidPrimary
			val guidSecondary = match.guidSecondary
			val authority = responsibility.getAuthority(guidPrimary,
				guidSecondary)

			switch (authority) {
				case NONE: {
					// we leave the resolution unresolved
				}
				case SECONDARY: {
					match.merge(context)
					match.resolution = Resolution.SECONDARY_AUTO
				}
				case PRIMARY: {
					match.resolution = Resolution.PRIMARY_AUTO
				}
				default: {
					throw new IllegalArgumentException(authority?.toString ?:
						"null")
				}
			}
		}
	}

	private def void merge(
		SMatch match,
		Context context
	) {
		val elementProvider = context.configuration.elementProvider
		val guidPrimary = match.guidPrimary
		val guidSecondary = match.guidSecondary
		val primaryContainer = context.primaryContainer
		val secondaryContainer = context.secondaryContainer

		// delete the primary element (if present)
		if (guidPrimary !== null) {
			val primaryElement = elementProvider.getElement(
				primaryContainer,
				guidPrimary,
				match.elementType
			).get
			val feature = primaryElement.eContainingFeature
			val list = primaryContainer.eGet(feature) as List<EObject>
			list.remove(primaryElement)
			if (LOGGER.debugEnabled) {
				LOGGER.debug(
					'''Deleted primary for «match.logString(context)»'''
				)
			}
		}

		// insert the secondary element (if present)
		if (guidSecondary !== null) {
			val secondaryElement = elementProvider.getElement(
				secondaryContainer,
				guidSecondary,
				match.elementType
			).get
			val feature = secondaryElement.eContainingFeature
			val list = primaryContainer.eGet(feature) as List<EObject>
			list.add(EcoreUtil.copy(secondaryElement))
			if (LOGGER.debugEnabled) {
				LOGGER.debug(
					'''Inserted secondary for «match.logString(context)»'''
				)
			}
		}
	}
}
