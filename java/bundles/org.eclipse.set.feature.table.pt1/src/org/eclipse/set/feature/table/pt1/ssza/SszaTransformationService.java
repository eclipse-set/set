/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.ssza;

import static org.eclipse.set.feature.table.pt1.ssza.SszaColumns.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Basisobjekte.Strecke_Km_TypeClass;
import org.eclipse.set.model.planpro.Verweise.ID_Strecke_TypeClass;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.RowMergeMode;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * Service for creating the ssza table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=etcs", "table.shortcut=ssza" })
public final class SszaTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private TopologicalGraphService topGraphService;
	@Reference
	private EventAdmin eventAdmin;

	/**
	 * constructor.
	 */
	public SszaTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SszaTransformator(cols, enumTranslationService,
				topGraphService, eventAdmin);
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSszaLong,
				messages.ToolboxTableNameSszaPlanningNumber,
				messages.ToolboxTableNameSszaShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SszaTableView_Heading;
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final ColumnDescriptor cd = super.fillHeaderDescriptions(builder);
		// Merge all columns except E to I
		cd.setMergeCommonValues(RowMergeMode.ENABLED);
		final List<String> notMergeColumns = List.of(Datenpunkt_Typ,
				Bezugspunkt_Bezeichnung, Bezugspunkt_Standort_Strecke,
				Bezugspunkt_Standort_km, DP_Standort_rel_Lage_zu_BP);

		cols.stream()
				.filter(col -> notMergeColumns.stream()
						.anyMatch(ele -> Objects.equals(ele,
								col.getColumnPosition())))
				.forEach(
						col -> col.setMergeCommonValues(RowMergeMode.DISABLED));
		return cd;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSszaShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return List.of(SszaColumns.DP_Standort_rel_Lage_zu_BP);
	}

	@Override
	protected Map<Class<?>, String> getFootnotesColumnReferences() {
		return Map.of(ID_Strecke_TypeClass.class,
				SszaColumns.Bezugspunkt_Standort_Strecke,
				Strecke_Km_TypeClass.class,
				SszaColumns.Bezugspunkt_Standort_km);
	}

}