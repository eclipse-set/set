/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ttwd

import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_Einzel
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static extension org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions.*

/**
 * Table transformation for a Testtabelle Warndreieck (TTWD).
 * 
 * @author Schaefer
 */
class TtwdTransformator extends AbstractPlanPro2TableModelTransformator {

	val TtwdColumns columns

	var TMFactory factory

	new(TtwdColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.columns = columns
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		newArrayList(1, 2, 3).forEach[it|
			if (Thread.currentThread.interrupted) {
				return
			}
			it.transform(container)
		]
		return
	}

	private def TableRow create factory.newTableRow() transform(
		int i, MultiContainer_AttributeGroup container) {

		if (i == 3) {
			fill(columns.kwdkwd, i, [toDo])
			fill(columns.kwdwd, i, [toBeSpecified])
			fill(columns.wdkwd, i, [futureVersion])
			fill(columns.wdwd, i, [noTestData])
		} else {
			// A: Kein Warndreieck/Kein Warndreieck
			fillConditional(columns.kwdkwd, i, [isStart(container)], [
				createValue
			], [createValue])

			// B: Kein Warndreieck/Warndreieck
			fillConditional(columns.kwdwd,
				i, [isStart(container)], [createValue], [
					createWarndreieck
				])

			// C: Warndreieck/Kein Warndreieck
			fillConditional(columns.wdkwd, i, [isStart(container)], [
				createWarndreieck
			], [createValue])

			// D: Warndreieck/Warndreieck
			fillConditional(columns.wdwd, i, [isStart(container)], [
				createWarndreieck
			], [createWarndreieck])
		}
		return
	}

	private def String createValue(Integer i) {
		return i.toString
	}

	private def String createWarndreieck(Integer i) {
		throw new IllegalArgumentException(i.toString)
	}

	private def boolean isStart(MultiContainer_AttributeGroup container) {
		val lstState = container.firstLSTZustand
		val planung = lstState.eContainer
		if (planung instanceof Planung_Einzel) {
			return planung.LSTZustandStart === lstState
		}
		return false
	}
}
