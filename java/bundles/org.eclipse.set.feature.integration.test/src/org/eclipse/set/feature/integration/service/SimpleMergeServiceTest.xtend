/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration.service

import org.eclipse.set.basis.Pair
import org.eclipse.set.core.services.merge.MergeService
import org.eclipse.set.core.services.merge.MergeService.Authority
import org.eclipse.set.core.services.merge.MergeService.Configuration
import org.eclipse.set.model.simplemerge.SComparison
import org.eclipse.set.model.test.site.Site
import org.eclipse.set.model.test.site.WindowType
import org.eclipse.set.model.test.site.extensions.utils.SiteMergeContext
import org.eclipse.set.model.test.site.extensions.utils.SiteResponsibility
import org.eclipse.set.test.utils.SiteBuilder
import org.eclipse.set.utils.IntegerGenerator
import org.eclipse.set.utils.testing.SiteMergeConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*
import static org.junit.jupiter.api.Assertions.*

import static extension org.eclipse.set.model.simplemerge.extensions.ResolutionExtensions.*
import static extension org.eclipse.set.test.utils.PassageExtensions.*
import static extension org.eclipse.set.test.utils.RoomExtensions.*

/**
 * Tests for {@link SimpleMergeService}.
 * 
 * @author Schaefer 
 */
class SimpleMergeServiceTest {

	MergeService<SComparison> mergeService
	Site primaryContainer
	Configuration configuration
	SiteResponsibility responsibility
	IntegerGenerator matchIdGenerator

	@BeforeEach
	def void setUp() {
		mergeService = new SimpleMergeService
		primaryContainer = createPrimarySite
		configuration = new SiteMergeConfiguration
		responsibility = new SiteResponsibility
		matchIdGenerator = new IntegerGenerator
	}

	def Site createPrimarySite() {
		return SiteBuilder.createSite.addBuilding("Turmstraße 1a", "1a",
			"Turm").addFloor("EG", "Erdgeschoss").addRoom("Eingangshalle",
			"EH").build
	}

	@Test
	def void testCreateANewElement() {
		val builder = SiteBuilder.createCopy(primaryContainer).selectByName(
			"Eingangshalle").addRoom("Treppenhaus", "Treppenhaus")
		val newRoom = builder.selectedRoom
		val secondaryContainer = builder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val result = description.getModel()
		val matches = result.matches
		assertThat(matches.size, is(2))
		val newRoomGuid = newRoom.guid
		val newPassageGuid = newRoom.passages.head.guid
		assertThat(
			matches.filter [
				guidPrimary === null && guidSecondary == newRoomGuid
			].size,
			is(1)
		)
		assertThat(
			matches.filter [
				guidPrimary === null && guidSecondary == newPassageGuid
			].size,
			is(1)
		)
	}

	@Test
	def void testChangeAnAttribute() {
		val builder = SiteBuilder.createCopy(primaryContainer).selectByName(
			"Eingangshalle")
		val entry = builder.selectedRoom
		entry.names.floorPlan = "Foyer"
		val secondaryContainer = builder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val result = description.getModel()
		val matches = result.matches
		assertThat(matches.size, is(1))
		val match = matches.head
		assertThat(match.guidPrimary, is(entry.guid))
		assertThat(match.guidSecondary, is(entry.guid))
		val matcher = configuration.matcher
		val elementProvider = configuration.elementProvider
		val primaryElement = elementProvider.getElement(primaryContainer,
			match.guidPrimary, null).orElse(null)
		val secondaryElement = elementProvider.getElement(secondaryContainer,
			match.guidSecondary, null).orElse(null)
		val differences = matcher.getDifferences(primaryElement,
			secondaryElement)
		assertThat(differences.size, is(1))
		val diff = differences.head
		assertThat(diff, is(#["names", "floorPlan"]))
		val labelProvider = configuration.labelProvider
		val primaryValue = labelProvider.getAttributeLabel(primaryElement, diff)
		val secondaryValue = labelProvider.getAttributeLabel(secondaryElement,
			diff)
		assertThat(primaryValue, is("EH"))
		assertThat(secondaryValue, is("Foyer"))
	}

	@Test
	def void testChangeSeveralAttributes() {
		val builder = SiteBuilder.createCopy(primaryContainer).selectByName(
			"Eingangshalle")
		val entry = builder.selectedRoom
		entry.names.floorPlan = "Foyer"
		val ground = builder.selectedFloor
		ground.names.floorPlanTitle = "Parterre"
		val secondaryContainer = builder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val result = description.getModel()
		val matches = result.matches
		assertThat(matches.size, is(2))
		val matcher = configuration.matcher
		val elementProvider = configuration.elementProvider

		val diffs = matches.map [
			val secondaryElement = elementProvider.getElement(
				secondaryContainer, guidSecondary, null).orElse(null)

			// new element, diffs
			new Pair(
				secondaryElement,
				matcher.getDifferences(
					elementProvider.getElement(primaryContainer, guidPrimary,
						null).orElse(null),
					secondaryElement
				)
			)
		]
		assertThat(diffs.size, is(2))
		val labelProvider = configuration.labelProvider
		val newValues = diffs.map [
			val pair = it
			pair.second.map [
				labelProvider.getAttributeLabel(pair.first, it)
			]
		].flatten.toSet
		assertThat(newValues, is(#{"Foyer", "Parterre"}))
	}

	@Test
	def void testChangeAReference() {
		val primaryBuilder = SiteBuilder.use(primaryContainer).
			selectByName("EH")
		primaryBuilder.addRoom("Pförtner", "P")
		primaryBuilder.addRoom("Nebeneingang", "NE")
		val newBuilder = SiteBuilder.createCopy(primaryContainer)
		val entrance = newBuilder.selectByName("EH").selectedRoom
		val sideEntrance = newBuilder.selectByName("NE").selectedRoom
		val porter = newBuilder.selectByName("P").selectedRoom
		val passages = porter.passages.filter[isConnectedTo(entrance)]
		assertThat(passages.size, is(1))
		val passage = passages.head
		passage.connect(sideEntrance, porter)
		val secondaryContainer = newBuilder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val result = description.getModel()
		val matches = result.matches
		assertThat(matches.size, is(1))
		val match = matches.head
		val elementProvider = configuration.elementProvider
		val primaryElement = elementProvider.getElement(primaryContainer,
			match.guidPrimary, null).orElse(null)
		val secondaryElement = elementProvider.getElement(secondaryContainer,
			match.guidSecondary, null).orElse(null)
		val matcher = configuration.matcher
		val diffs = matcher.getDifferences(primaryElement, secondaryElement)
		assertThat(diffs.size, is(1))
		val diff = diffs.head
		val labelProvider = configuration.labelProvider
		val primaryLabel = labelProvider.getAttributeLabel(primaryElement, diff)
		val secondaryLabel = labelProvider.getAttributeLabel(
			secondaryElement,
			diff
		)
		assertThat(primaryLabel, is("Room EH"))
		assertThat(secondaryLabel, is("Room NE"))
	}

	@Test
	def void testChangeAttributeInList() {
		val primaryBuilder = SiteBuilder.use(primaryContainer).
			selectByName("EH")
		primaryBuilder.addWindow("Ostwand", WindowType.BOTTOM_HUNG)
		primaryBuilder.addWindow("Westwand", WindowType.MOSAIC)
		primaryBuilder.addWindow("Südwand", WindowType.BARRED)
		val newBuilder = SiteBuilder.createCopy(primaryContainer)
		val entrance = newBuilder.selectByName("EH").selectedRoom
		val window = entrance.windows.filter[position == "Westwand"].head
		window.type = WindowType.TOP_HUNG
		val secondaryContainer = newBuilder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val result = description.getModel()
		val matches = result.matches
		assertThat(matches.size, is(1))
		val match = matches.head
		val elementProvider = configuration.elementProvider
		val primaryElement = elementProvider.getElement(primaryContainer,
			match.guidPrimary, null).orElse(null)
		val secondaryElement = elementProvider.getElement(secondaryContainer,
			match.guidSecondary, null).orElse(null)
		val matcher = configuration.matcher
		val diffs = matcher.getDifferences(primaryElement, secondaryElement)
		assertThat(diffs.size, is(1))
		val diff = diffs.head
		val labelProvider = configuration.labelProvider
		val primaryLabel = labelProvider.getAttributeLabel(primaryElement, diff)
		val secondaryLabel = labelProvider.getAttributeLabel(
			secondaryElement,
			diff
		)
		assertThat(secondaryLabel,
			is(primaryLabel.replaceAll("MOSAIC", "TOP_HUNG")))
	}

	@Test
	def void testAddListElement() {
		val primaryBuilder = SiteBuilder.use(primaryContainer).
			selectByName("EH")
		primaryBuilder.addWindow("Ostwand", WindowType.BOTTOM_HUNG)
		primaryBuilder.addWindow("Westwand", WindowType.MOSAIC)
		primaryBuilder.addWindow("Südwand", WindowType.BARRED)
		val newBuilder = SiteBuilder.createCopy(primaryContainer).
			selectByName("EH").addWindow("Nordwand", WindowType.SIMPLE)
		val secondaryContainer = newBuilder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val result = description.getModel()
		val matches = result.matches
		assertThat(matches.size, is(1))
		val match = matches.head
		val elementProvider = configuration.elementProvider
		val primaryElement = elementProvider.getElement(primaryContainer,
			match.guidPrimary, null).orElse(null)
		val secondaryElement = elementProvider.getElement(secondaryContainer,
			match.guidSecondary, null).orElse(null)
		val matcher = configuration.matcher
		val diffs = matcher.getDifferences(primaryElement, secondaryElement)
		assertThat(diffs.size, is(1))
		val diff = diffs.head
		val labelProvider = configuration.labelProvider
		val primaryLabel = labelProvider.getAttributeLabel(primaryElement, diff)
		val secondaryLabel = labelProvider.getAttributeLabel(
			secondaryElement,
			diff
		)
		assertThat(
			secondaryLabel,
			is(primaryLabel.replace("]", " Window SIMPLE: position=Nordwand]"))
		)
	}

	@Test
	def void testRemoveListElement() {
		val primaryBuilder = SiteBuilder.use(primaryContainer).
			selectByName("EH")
		primaryBuilder.addWindow("Ostwand", WindowType.BOTTOM_HUNG)
		primaryBuilder.addWindow("Westwand", WindowType.MOSAIC)
		primaryBuilder.addWindow("Südwand", WindowType.BARRED)
		val newBuilder = SiteBuilder.createCopy(primaryContainer)
		val entrance = newBuilder.selectByName("EH").selectedRoom
		val window = entrance.windows.filter[position == "Westwand"].head
		entrance.windows.remove(window)
		val secondaryContainer = newBuilder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val result = description.getModel()
		val matches = result.matches
		assertThat(matches.size, is(1))
		val match = matches.head
		val elementProvider = configuration.elementProvider
		val primaryElement = elementProvider.getElement(primaryContainer,
			match.guidPrimary, null).orElse(null)
		val secondaryElement = elementProvider.getElement(secondaryContainer,
			match.guidSecondary, null).orElse(null)
		val matcher = configuration.matcher
		val diffs = matcher.getDifferences(primaryElement, secondaryElement)
		assertThat(diffs.size, is(1))
		val diff = diffs.head
		val labelProvider = configuration.labelProvider
		val primaryLabel = labelProvider.getAttributeLabel(primaryElement, diff)
		val secondaryLabel = labelProvider.getAttributeLabel(
			secondaryElement,
			diff
		)
		assertThat(
			secondaryLabel,
			is(primaryLabel.replace(" Window MOSAIC: position=Westwand", ""))
		)
	}

	@Test
	def void testMergeNewElement() {
		val builder = SiteBuilder.createCopy(primaryContainer).selectByName(
			"Eingangshalle").addRoom("Treppenhaus", "Treppenhaus")
		val secondaryContainer = builder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val primaryBuilder = SiteBuilder.use(primaryContainer)

		// we assume that Treppenhaus is not part of the primary site
		assertNull(primaryBuilder.getByName("Treppenhaus"))

		mergeService.automaticMerge(context, description)

		// after the merge we assume that Treppenhaus is part of the primary site
		assertNotNull(primaryBuilder.getByName("Treppenhaus"))

		// we assume to have no conflicts
		assertThat(description.model.matches.filter[!resolution.automatic].size,
			is(0))
	}

	@Test
	def void testMergeAttributeChange() {
		val builder = SiteBuilder.createCopy(primaryContainer).selectByName(
			"Eingangshalle")
		val entry = builder.selectedRoom
		entry.names.floorPlan = "Foyer"
		val secondaryContainer = builder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)
		val primaryBuilder = SiteBuilder.use(primaryContainer)

		// we assume the old floor plan name "EH" in the primary model
		primaryBuilder.selectByName("Eingangshalle")
		assertThat(primaryBuilder.selectedRoom.names.floorPlan, is("EH"))

		mergeService.automaticMerge(context, description)

		// after the merge we assume that "Foyer" is the floor name
		primaryBuilder.selectByName("Eingangshalle")
		assertThat(primaryBuilder.selectedRoom.names.floorPlan, is("Foyer"))

		// we assume to have no conflicts
		assertThat(description.model.matches.filter[!resolution.automatic].size,
			is(0))
	}

	@Test
	def void testConflict() {
		val builder = SiteBuilder.createCopy(primaryContainer).selectByName(
			"Eingangshalle")
		val entry = builder.selectedRoom
		entry.names.floorPlan = "Foyer"
		val secondaryContainer = builder.build()
		val context = new SiteMergeContext(primaryContainer, secondaryContainer,
			configuration, responsibility)
		val description = mergeService.createChangeDescription(context,
			matchIdGenerator)

		// we manipulate the site responsibility, so no one is responsible for the entry
		responsibility.add(entry.guid, Authority.NONE)

		mergeService.automaticMerge(context, description)

		// we assume to have one conflict now
		assertThat(description.model.matches.filter[!resolution.automatic].size,
			is(1))
	}
}
