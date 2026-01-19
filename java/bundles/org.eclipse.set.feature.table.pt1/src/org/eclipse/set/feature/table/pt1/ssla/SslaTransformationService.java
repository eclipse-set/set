/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssla;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.feature.table.pt1.ssla.SslaColumns.*;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;

import java.util.Comparator;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.ppmodel.extensions.FstrAneinanderExtensions;
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
				"table.category=estw", "table.shortcut=ssla" })
public final class SslaTransformationService
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
	public SslaTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslaTransformator(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				// Zugstraße - Z first, then Rangierstraßen - R
				.sort(Art, LEXICOGRAPHICAL, SortDirectionEnum.DESC)
				.sortByRouteAndKm(obj -> {
					if (obj instanceof final Fstr_Aneinander fstr) {
						return FstrAneinanderExtensions.getStartSignal(fstr);
					}
					return null;
				})
				.sort(Bezeichnung, LEXICOGRAPHICAL, ASC)
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSslaLong,
				messages.ToolboxTableNameSslaPlanningNumber,
				messages.ToolboxTableNameSslaShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SslaTableView_Heading;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSslaShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return List.of(Bezeichnung, Fahrweg_Start, Fahrweg_Ziel,
				Durchrutschweg_Ziel, Unterwegssignal);
	}

}