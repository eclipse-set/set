/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.bv

import org.eclipse.set.toolboxmodel.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.core.services.name.NameService
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.LstObjectAttribute

import static extension org.eclipse.set.ppmodel.extensions.ContainerExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.LstObjectAttributeExtensions.*

/**
 * Table transformation for a Rangierstraßentabelle (Sslr).
 * 
 * @author Schneider/Schaefer
 */
class BvTransformator extends AbstractPlanPro2TableModelTransformator {

	val BvColumns columns
	var TMFactory factory = null
	val NameService nameService

	new(
		BvColumns columns,
		MessagesWrapper messagesWrapper
	) {
		super(messagesWrapper)
		this.columns = columns
		this.nameService = messagesWrapper.context.get(NameService)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		container.objectAttributes.filter [
			generalbedingung
		].forEach [
			val attribute = it
			comments.forEach[transform(attribute)]
		]
		return
	}

	private def TableRow create factory.newTableRow(objectAttribute.object) transform(
		Bearbeitungsvermerk comment,
		LstObjectAttribute objectAttribute
	) {
		fill(
			columns.LstObjektName,
			objectAttribute,
			[nameService.getTypeName(object)]
		)
		fill(
			columns.Bezeichnung,
			objectAttribute,
			[nameService.getName(object)]
		)
		fill(
			columns.Attributname,
			objectAttribute,
			[nameService.getName(attribute)]
		)
		fill(
			columns.Attributwert,
			objectAttribute,
			[nameService.getValue(attribute)]
		)
		fill(
			columns.Bearbeitungsvermerk,
			objectAttribute,
			[comment.bearbeitungsvermerkAllg.kommentar.wert]
		)
		return
	}

	private def boolean getGeneralbedingung(LstObjectAttribute attribute) {
		return !attribute.comments.empty
	}
}
