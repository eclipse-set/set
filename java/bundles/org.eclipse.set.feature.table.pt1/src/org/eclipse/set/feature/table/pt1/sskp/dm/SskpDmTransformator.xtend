/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskp.dm

import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.sskp.SskpTransformator
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.osgi.service.event.EventAdmin

/**
 * Table transformation for a PZB_dm-Tabelle (Sskp)
 * 
 * @author Truong
 */
class SskpDmTransformator extends SskpTransformator {

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService,
		TopologicalGraphService topGraphService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, topGraphService, eventAdmin)
	}

	override int getDistanceScale(PZB_Element pzb) {
		return pzb?.PZBElementGM?.PZBINA !== null ? 1 : 0;
	}
	
	
}
