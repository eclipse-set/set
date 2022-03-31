/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.set.basis.constants.ToolboxViewState
import org.eclipse.set.basis.part.PartDescription
import org.eclipse.set.basis.part.ViewVisibility
import org.eclipse.e4.ui.model.application.MApplicationElement
import org.eclipse.e4.ui.model.application.ui.basic.MPart

import static org.eclipse.set.basis.constants.ToolboxViewState.*

/**
 * Extensions for {@link MApplicationElement}.
 * 
 * @author Schaefer
 */
class MApplicationElementExtensions {

	static val String TOOLBOX_VIEW_STATE = "toolbox.view.state"
	static val String TOOLBOX_VIEW_DESCRIPTION = "toolbox.view.description"
	static val String TOOLBOX_VIEW_VISIBILITY = "toolbox.view.visibility"
	static val String TOOLBOX_VIEW_MARK = "toolbox.view.mark"
	static val String MARK_VALUE = "true"

	/**
	 * @param element this element
	 * @param state the view state
	 */
	static def void setViewState(MApplicationElement element,
		ToolboxViewState state) {
		element.transientData.put(TOOLBOX_VIEW_STATE, state)
	}

	/**
	 * @param element this element
	 * 
	 * @return the view state
	 */
	static def ToolboxViewState getViewState(MApplicationElement element) {
		val value = element.transientData.get(
			TOOLBOX_VIEW_STATE) as ToolboxViewState
		if (value === null) {
			return UNKNOWN
		}
		return value
	}
	
	/**
	 * Resets the view state to the default state
	 * 
	 * @param element this element
	 */
	static def void resetViewState(MApplicationElement element)
	{
		element.transientData.remove(TOOLBOX_VIEW_STATE);
	} 

	/**
	 * @param element this element
	 * 
	 * @return whether the creation of this view was successful
	 */
	static def boolean isSuccessful(MApplicationElement element) {
		return #{UNKNOWN, CREATED}.contains(element.getViewState)
	}

	/**
	 * @param element this element
	 * 
	 * @return the view description 
	 */
	static def PartDescription getDescription(MApplicationElement element) {
		return element.transientData.get(
			TOOLBOX_VIEW_DESCRIPTION) as PartDescription
	}

	/**
	 * @param element this element
	 * @param description the view description
	 */
	static def void setDescription(MApplicationElement element,
		PartDescription description) {
		element.transientData.put(TOOLBOX_VIEW_DESCRIPTION, description)
	}

	/**
	 * @param element this element
	 * 
	 * @return the view visibility 
	 */
	static def ViewVisibility getVisibility(MApplicationElement element) {
		return element.transientData.get(
			TOOLBOX_VIEW_VISIBILITY) as ViewVisibility
	}

	/**
	 * @param element this element
	 * @param visibility the view visibility
	 */
	static def void setVisibility(MApplicationElement element,
		ViewVisibility visibility) {
		element.transientData.put(TOOLBOX_VIEW_VISIBILITY, visibility)
	}

	/**
	 * Mark the element as a toolbox view.
	 * 
	 * @param element this element
	 */
	static def void markAsToolboxView(MApplicationElement element) {
		element.transientData.put(TOOLBOX_VIEW_MARK, MARK_VALUE)
	}

	/**
	 * @param element this element
	 * 
	 * @return whether the element is marked as a toolbox view 
	 */
	static def boolean isToolboxView(MApplicationElement element) {
		val mark_value = element?.transientData?.get(TOOLBOX_VIEW_MARK)
		if (mark_value === null) {
			return false
		}
		return mark_value == MARK_VALUE
	}

	static def boolean isOpenPart(MApplicationElement element) {
		if (element instanceof MPart) {
			return element.visible && element.toBeRendered
		}
		return false
	}
}
