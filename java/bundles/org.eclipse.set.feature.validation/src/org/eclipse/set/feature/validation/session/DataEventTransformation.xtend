/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.session

import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Projekt
import org.eclipse.set.utils.events.ContainerDataChanged
import org.eclipse.set.utils.events.DataEvent
import java.util.Set
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import org.eclipse.set.utils.events.ProjectDataChanged

/**
 * Transform {@link Notification}s to {@link DataEvent}s.
 * 
 * @author Schaefer
 */
class DataEventTransformation {

	var Notification notification

	/**
	 * @param notification the notification
	 * 
	 * @return the data events
	 */
	def Set<DataEvent> transform(Notification notification) {
		this.notification = notification
		return #{notification.notifier}.map [
			transformToContainer
		].toSet.filterNull.map [
			transformToDataEvent
		].toSet
	}

	private dispatch def EObject transformToContainer(Object notifier) {
		return null
	}

	private dispatch def EObject transformToContainer(EObject notifier) {
		// test for ContainerDataChanged
		val container = notifier.localContainer
		if (container !== null) {
			return container
		}

		// test for ProjectDataChangedEvent
		val project = notifier.project
		if (project !== null) {
			return project
		}

		// no valid container found
		return null
	}

	private dispatch def DataEvent transformToDataEvent(EObject container) {
		throw new IllegalArgumentException(container.toString)
	}

	private dispatch def DataEvent transformToDataEvent(
		Container_AttributeGroup container
	) {
		return new ContainerDataChanged(container)
	}

	private dispatch def DataEvent transformToDataEvent(
		Planung_Projekt container
	) {
		return new ProjectDataChanged(notification)
	}

	private dispatch def Planung_Projekt getProject(EObject object) {
		return object.eContainer.project
	}

	private dispatch def Planung_Projekt getProject(Void object) {
		return null
	}

	private dispatch def Planung_Projekt getProject(Planung_Projekt object) {
		return object
	}
}
