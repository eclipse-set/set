/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sska;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import org.eclipse.set.model.planpro.Basisobjekte.Strecke_Km_TypeClass;
import org.eclipse.set.model.planpro.Verweise.ID_Strecke_TypeClass;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * Service for creating the ssld table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=sska" })
public final class SskaTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private EventAdmin eventAdmin;

	/**
	 * constructor.
	 */
	public SskaTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskaTransformator(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort(ESTW_Zentraleinheit.class, Aussenelementansteuerung.class)
				.sort("A", LEXICOGRAPHICAL, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSskaLong,
				messages.ToolboxTableNameSskaPlanningNumber,
				messages.ToolboxTableNameSskaShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SskaTableView_Heading;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSskaShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return Collections.emptyList();
	}

	@Override
	protected Map<Class<?>, String> getFootnotesColumnReferences() {
		return Map.of(ID_Strecke_TypeClass.class,
				SskaColumns.Unterbringung_Strecke, Strecke_Km_TypeClass.class,
				SskaColumns.Unterbringung_km);
	}

}
