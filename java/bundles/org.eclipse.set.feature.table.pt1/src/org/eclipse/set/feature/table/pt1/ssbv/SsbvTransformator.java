/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.ssbv;

import java.util.Set;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.table.TMFactory;
import org.osgi.service.event.EventAdmin;

/**
 * Table transformation for a Bearbeitungsvermerke tabelle
 * 
 * @author truong
 */
public class SsbvTransformator extends AbstractPlanPro2TableModelTransformator {

	/**
	 * @param cols
	 *            the columns descriptor
	 * @param enumTranslationService
	 *            the {@link EnumTranslationService}
	 * @param eventAdmin
	 *            the {@link EventAdmin}
	 */
	public SsbvTransformator(final Set<ColumnDescriptor> cols,
			final EnumTranslationService enumTranslationService,
			final EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public Table transformTableContent(
			final MultiContainer_AttributeGroup model, final TMFactory factory,
			final Stell_Bereich controlArea) {
		// TODO Auto-generated method stub
		return null;
	}

}
