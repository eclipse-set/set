/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.abstracttableview;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CustomLineBorderDecorator;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.LineBorderDecorator;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle.LineStyleEnum;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.ui.matcher.KeyEventMatcher;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.nattable.utils.CustomLinePainter;
import org.eclipse.set.nattable.utils.PlanProTableThemeConfiguration;
import org.eclipse.set.utils.Colors;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * Toolbox theme configuration.
 * 
 * @author Schaefer
 */
public class ToolboxTableModelThemeConfiguration
		extends PlanProTableThemeConfiguration {
	private static final RGB TOPOLOGICAL_CELL_BG_COLOR = new RGB(232, 232, 232);
	private static final float SCALE_CM_TO_PIXEL = ToolboxConfiguration
			.getTablesScaleFactor();

	/**
	 * @param length
	 *            the length in cm
	 * 
	 * @return the length in pixel
	 */
	public static int toPixel(final float length) {
		return Math.round(length * SCALE_CM_TO_PIXEL);
	}

	private final Style topologicalCellStyle;

	/**
	 * @param natTable
	 *            the NAT-table
	 * @param headerLayer
	 *            the header layer
	 * @param dataLayer
	 *            the data layer
	 * @param gridLayer
	 *            the grid layer
	 * @param heading
	 *            the heading model
	 * @param bodyDataProvider
	 *            data provider
	 * @param bodyLayer
	 *            the body layer
	 * @param dialogService
	 *            the dialogService
	 */
	public ToolboxTableModelThemeConfiguration(final NatTable natTable,
			final AbstractLayer headerLayer, final DataLayer dataLayer,
			final GridLayer gridLayer, final ColumnDescriptor heading,
			final AbstractLayer bodyLayer, final IDataProvider bodyDataProvider,
			final DialogService dialogService) {
		super(natTable, headerLayer, dataLayer, gridLayer, heading, bodyLayer,
				bodyDataProvider);

		// register key bindings for table exports
		natTable.getUiBindingRegistry()
				.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'r'),
						new CsvExportAction(dialogService));

		topologicalCellStyle = new Style();
		topologicalCellStyle.setAttributeValue(
				CellStyleAttributes.BACKGROUND_COLOR,
				new Color(TOPOLOGICAL_CELL_BG_COLOR));
	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		super.configureRegistry(configRegistry);
		registerCompareTableCellStyle(configRegistry);
		registerTopologicalTableCellStyle(configRegistry);
		registerCompareTableRowStyle(configRegistry);
	}

	private void registerCompareTableCellStyle(
			final IConfigRegistry configRegistry) {
		final ICellPainter lineBorderDecorator = new LineBorderDecorator(
				defaultCellPainter,
				new BorderStyle(1, new Color(Colors.parseHexCode(
						ToolboxConstants.TABLE_COMPARE_TABLE_CELL_BORDER_COLOR)),
						LineStyleEnum.SOLID));
		configRegistry.registerConfigAttribute(
				CellConfigAttributes.CELL_PAINTER, lineBorderDecorator,
				DisplayMode.NORMAL,
				ToolboxConstants.TABLE_COMPARE_TABLE_CELL_LABEL);
	}

	private void registerCompareTableRowStyle(
			final IConfigRegistry configRegistry) {
		final CustomLinePainter customLinePainter = new CustomLinePainter(
				defaultCellPainter, bounds -> {
					final Integer[] pos = new Integer[2];
					pos[0] = Integer.valueOf(bounds.x);
					pos[1] = Integer.valueOf(bounds.y + bounds.height / 2);
					return pos;
				}, bounds -> {
					final Integer[] pos = new Integer[2];
					pos[0] = Integer.valueOf(bounds.x + bounds.width);
					pos[1] = Integer.valueOf(bounds.y + bounds.height / 2);
					return pos;
				},
				new Color(Colors.parseHexCode(
						ToolboxConstants.TABLE_COMPARE_TABLE_CELL_BORDER_COLOR)),
				1);

		configRegistry.registerConfigAttribute(
				CellConfigAttributes.CELL_PAINTER, customLinePainter,
				DisplayMode.NORMAL,
				ToolboxConstants.TABLE_COMPARE_CHANGED_GUID_ROW_CELL_LABEL);

		List.of(ToolboxConstants.TABLE_COMPARE_TABLE_ROW_CELL_LABEL,
				ToolboxConstants.TABLE_COMPARE_TABLE_ROW_FIRST_CELL_LABEL,
				ToolboxConstants.TABLE_COMPARE_TABLE_ROW_LAST_CELL_LABEL)
				.forEach(label -> configRegistry.registerConfigAttribute(
						CellConfigAttributes.CELL_PAINTER,
						new CustomLineBorderDecorator(defaultCellPainter,
								new BorderStyle(1,
										new Color(Colors.parseHexCode(
												ToolboxConstants.TABLE_COMPARE_TABLE_CELL_BORDER_COLOR)),
										LineStyleEnum.SOLID)),
						DisplayMode.NORMAL, label));
	}

	// private static Set<BorderDirection>
	// getCompareTableRowCellBorderDirections(
	// final String label) {
	// return switch (label) {
	// case ToolboxConstants.TABLE_COMPARE_TABLE_ROW_FIRST_CELL_LABEL -> Set
	// .of(BorderDirection.LEFT, BorderDirection.TOP,
	// BorderDirection.BOTTOM);
	// case ToolboxConstants.TABLE_COMPARE_TABLE_ROW_CELL_LABEL -> Set
	// .of(BorderDirection.TOP, BorderDirection.BOTTOM);
	// case ToolboxConstants.TABLE_COMPARE_TABLE_ROW_LAST_CELL_LABEL -> Set
	// .of(BorderDirection.RIGHT, BorderDirection.TOP,
	// BorderDirection.BOTTOM);
	// default -> Collections.emptySet();
	// };
	// }
	//
	// private CustomBorderPainter createCustomBorderPainter(
	// final Set<BorderDirection> borderDirections) {
	// return new CustomBorderPainter(defaultCellPainter,
	// new Color(Colors.parseHexCode(
	// ToolboxConstants.TABLE_COMPARE_TABLE_CELL_BORDER_COLOR)),
	// 1, borderDirections);
	// }

	private void registerTopologicalTableCellStyle(
			final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE,
				topologicalCellStyle, DisplayMode.NORMAL,
				ToolboxConstants.TABLE_TOPOLOGICAL_CELL);
	}

}
