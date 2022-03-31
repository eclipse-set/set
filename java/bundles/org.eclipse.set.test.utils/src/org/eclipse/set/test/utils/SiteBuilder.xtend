/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.test.utils

import org.eclipse.set.basis.guid.Guid
import org.eclipse.set.model.test.site.Building
import org.eclipse.set.model.test.site.Floor
import org.eclipse.set.model.test.site.Identified
import org.eclipse.set.model.test.site.Room
import org.eclipse.set.model.test.site.Site
import org.eclipse.set.model.test.site.SiteFactory
import org.eclipse.set.model.test.site.WindowType
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl

import static extension org.eclipse.set.test.utils.FloorExtensions.*
import static extension org.eclipse.set.test.utils.IdentifiedExtensions.*
import static extension org.eclipse.set.test.utils.RoomExtensions.*

/**
 * Build a site.
 */
class SiteBuilder {

	val Site site
	Building building = null
	Floor floor = null
	Room room = null

	private new() {
		site = SiteFactory.eINSTANCE.createSite
		createResource.contents.add(site)
	}

	def Resource createResource() {
		val reg = Resource.Factory.Registry.INSTANCE;
		val m = reg.getExtensionToFactoryMap();
		m.put("site", new XMIResourceFactoryImpl());
		val set = new ResourceSetImpl
		val resource = set.createResource(URI.createURI("primary.site"))
		return resource
	}

	private new(Site site) {
		this.site = site
		if (site.eResource === null) {
			createResource.contents.add(site)
		}
	}

	/**
	 * Create a builder with a new site.
	 */
	def static SiteBuilder createSite() {
		return new SiteBuilder
	}

	/**
	 * Add a new building to the site.
	 * 
	 * @param address the address
	 * @param entrance the entrance
	 * @param sitePlan the site plan name
	 */
	def SiteBuilder addBuilding(String address, String entrance,
		String sitePlan) {
		val building = SiteFactory.eINSTANCE.createBuilding
		building.guid = Guid.create.toString
		val names = SiteFactory.eINSTANCE.createBuildingNames
		names.address = address
		names.entrance = entrance
		names.sitePlan = sitePlan
		building.names = names
		site.allBuildings.add(building)
		this.building = building
		return this
	}

	/**
	 * Add a new floor the the current building.
	 */
	def SiteBuilder addFloor(String elevator, String floorPlanTitle) {
		val floor = SiteFactory.eINSTANCE.createFloor
		floor.guid = Guid.create.toString
		val names = SiteFactory.eINSTANCE.createFloorNames
		names.elevator = elevator
		names.floorPlanTitle = floorPlanTitle
		floor.names = names
		floor.buildingID = building.guid
		site.allFloors.add(floor)
		this.floor = floor
		return this
	}

	/**
	 * Add a new room to the current floor. If there is a current room, also
	 * create a passage to and from the current room to the new room.
	 */
	def SiteBuilder addRoom(String doorSign, String floorPlan) {
		val room = SiteFactory.eINSTANCE.createRoom
		room.guid = Guid.create.toString
		val names = SiteFactory.eINSTANCE.createRoomNames
		names.doorSign = doorSign
		names.floorPlan = floorPlan
		room.names = names
		room.floorID = floor.guid
		site.allRooms.add(room)
		if (this.room !== null) {
			room.addPassage(this.room)
		}
		this.room = room
		return this
	}

	def SiteBuilder addWindow(String position, WindowType type) {
		val window = SiteFactory.eINSTANCE.createWindow
		window.position = position
		window.type = type
		room.windows.add(window)
		return this
	}

	/**
	 * Connect two rooms with a passage.
	 */
	def SiteBuilder addPassage(Room roomA, Room roomB) {
		val passage = SiteFactory.eINSTANCE.createPassage
		passage.guid = Guid.create.toString
		passage.roomID_A = roomA.guid
		passage.roomID_B = roomB.guid
		site.allPassages.add(passage)
		return this
	}

	/**
	 * @return the site build
	 */
	def Site build() {
		return site
	}

	/**
	 * Create a builder with a copy of the given site.
	 */
	def static SiteBuilder createCopy(Site site) {
		return new SiteBuilder(EcoreUtil.copy(site))
	}

	/**
	 * Create a builder with the given site.
	 */
	def static SiteBuilder use(Site site) {
		return new SiteBuilder(site)
	}

	/**
	 * Select the first element of the site which has the given name.
	 */
	def SiteBuilder selectByName(String name) {
		val first = site.eContents.filter(Identified).findFirst[hasName(name)]
		if (first === null) {
			throw new IllegalArgumentException('''No object with name=«name» found.''')
		}
		select(first)
		return this
	}

	/**
	 * Provide the first element of the site which has the given name (if it exists).
	 */
	def Identified getByName(String name) {
		return site.eContents.filter(Identified).findFirst[hasName(name)]
	}

	/**
	 * @return the selected building
	 */
	def Building getSelectedBuilding() {
		return building
	}

	/**
	 * @return the selected floor
	 */
	def Floor getSelectedFloor() {
		return floor
	}

	/**
	 * @return the selected room
	 */
	def Room getSelectedRoom() {
		return room
	}

	private def dispatch void select(EObject object) {
		throw new IllegalArgumentException('''Illegal object «object».''')
	}

	private def dispatch void select(Building building) {
		this.building = building
		this.floor = null
		this.room = null
	}

	private def dispatch void select(Floor floor) {
		this.building = floor.building
		this.floor = floor
		this.room = null
	}

	private def dispatch void select(Room room) {
		this.building = room.building
		this.floor = room.floor
		this.room = room
	}
}
