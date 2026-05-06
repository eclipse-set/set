/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslz;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Abhaengiger_BUe;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Durchrutschweg_Bezeichnung;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Entscheidungsweiche;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Fahrweg;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Geschwindigkeit_Startsignal_Zs3;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Im_Fahrweg_Zs3;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Im_Fahrweg_Zs6;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Kennlicht;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Nummer;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Start;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Vorsignalisierung;
import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.Ziel;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.ppmodel.extensions.FahrwegExtensions;
import org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.eclipse.set.utils.table.sorting.LexicographicalCellComparator;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * Service for creating the ssks table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=sslz" })
public final class SslzTransformationService
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
	public SslzTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslzTransformator(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSslzLong,
				messages.ToolboxTableNameSslzPlanningNumber,
				messages.ToolboxTableNameSslzShort,
				messages.ToolboxTableNameSslzRil);
	}

	@Override
	protected String getTableHeading() {
		return messages.SslzTableView_Heading;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSslzShort.toLowerCase();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator(
			final TableType tableType) {
		return TableRowGroupComparator.builder(tableType)
				.sortByRouteAndKm(obj -> {
					if (obj instanceof final Fstr_Zug_Rangier fstr) {
						return FahrwegExtensions.getStart(
								FstrZugRangierExtensions.getFstrFahrweg(fstr));
					}
					return null;
				})
				.sort(Start, LEXICOGRAPHICAL, ASC)
				.sort(Ziel, LEXICOGRAPHICAL, ASC)
				.sort(Nummer, LEXICOGRAPHICAL, ASC)
				.sort(Durchrutschweg_Bezeichnung,
						new LexicographicalCellComparator(ASC) {
							@SuppressWarnings("nls")
							@Override
							public int compareString(final String text1,
									final String text2) {
								// Ignore '*' in Dweg name
								return super.compareString(
										text1.replace("*", ""),
										text2.replace("*", ""));
							}
						})
				.build();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return List.of(Entscheidungsweiche, Abhaengiger_BUe, Fahrweg,
				Geschwindigkeit_Startsignal_Zs3, Im_Fahrweg_Zs3, Im_Fahrweg_Zs6,
				Kennlicht, Vorsignalisierung);
	}

	@Override
	protected Map<Class<?>, String> getFootnotesColumnReferences() {
		return Collections.emptyMap();
	}

	@Override
	protected Pt1TableCategory getTableCategory() {
		return Pt1TableCategory.ESTW;
	}
}
