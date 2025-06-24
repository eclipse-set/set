/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.nattable.utils;

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterIconPainter;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowPainter;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.group.painter.ColumnGroupHeaderTextPainter;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.AggregateConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.painter.cell.BackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.LineBorderDecorator;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle.LineStyleEnum;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.style.VerticalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.theme.ModernNatTableThemeConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.set.basis.ToolboxProperties;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.utils.Colors;
import org.eclipse.set.utils.SetImages;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

/**
 * Toolbox theme configuration.
 * 
 * @author Schaefer
 */
public class PlanProTableThemeConfiguration
		extends ModernNatTableThemeConfiguration {

	private static final String ALIGN_LEFT = "align_left"; //$NON-NLS-1$
	private static final String EXCEPTION_LABEL = "exception"; //$NON-NLS-1$
	private static final String GREYED_LABEL = "greyedout"; //$NON-NLS-1$

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

	private static Image createImage(
			final LocalResourceManager localResourceManager,
			final ImageDescriptor imageDescriptor) {
		if (imageDescriptor != null) {
			return localResourceManager.create(imageDescriptor);
		}
		return null;
	}

	private static void registerHandler(final ILayer layer) {
		layer.registerCommandHandler(
				new CustomizableExportCommandHandler(layer).setOpen(false));
	}

	private final Style alignLeftStyle;
	private final AggregateConfigLabelAccumulator bodyLayerAccumulator = new AggregateConfigLabelAccumulator();
	private final DataLayer dataLayer;
	private final AggregateConfigLabelAccumulator dataLayerAccumulator = new AggregateConfigLabelAccumulator();
	private final Style fatHeaderStyle;
	private final IStyle greyedOutStyle;
	private final Color greyedOutStyleBackgroundColor;
	private final Color greyedOutStyleForegroundColor;
	private Font headerFont;
	private final AbstractLayer headerLayer;
	private final AggregateConfigLabelAccumulator headerLayerAccumulator = new AggregateConfigLabelAccumulator();

	private final ColumnDescriptor heading;
	private final LocalResourceManager resourceManager;

	final IDataProvider bodyDataProvider;

	final AbstractLayer bodyLayer;

	{
		this.cGroupHeaderCellPainter = new BackgroundPainter(
				new PaddingDecorator(new TextPainter(true, true, false)));
		this.defaultHAlign = HorizontalAlignmentEnum.CENTER;

		final FontData defaultFontData = GUIHelper.DEFAULT_FONT
				.getFontData()[0];
		defaultFontData.setStyle(SWT.BOLD);
		this.headerFont = GUIHelper.getFont(defaultFontData);
	}

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
	 */
	public PlanProTableThemeConfiguration(final NatTable natTable,
			final AbstractLayer headerLayer, final DataLayer dataLayer,
			final GridLayer gridLayer, final ColumnDescriptor heading,
			final AbstractLayer bodyLayer,
			final IDataProvider bodyDataProvider) {
		resourceManager = new LocalResourceManager(
				JFaceResources.getResources(), natTable.getParent());

		// initialize colors
		final Color defaultBackground = createColor(
				Colors.DEFAULT_GREYEDOUT_COLOR);
		greyedOutStyleBackgroundColor = createColor(
				ToolboxProperties.GREYED_OUT_STYLE_BACKGROUND,
				defaultBackground);
		greyedOutStyleForegroundColor = createColor(
				ToolboxProperties.GREYED_OUT_STYLE_FOREGROUND,
				GUIHelper.COLOR_DARK_GRAY);
		this.defaultSelectionBgColor = GUIHelper.getColor(173, 216, 230);
		this.defaultSelectionFgColor = GUIHelper.COLOR_BLACK;
		this.selectionAnchorSelectionBgColor = GUIHelper.getColor(173, 216,
				230);
		this.selectionAnchorSelectionFgColor = GUIHelper.COLOR_BLACK;

		// initialize layers and providers
		this.headerLayer = headerLayer;
		this.dataLayer = dataLayer;
		this.bodyLayer = bodyLayer;
		this.bodyDataProvider = bodyDataProvider;

		// IMPROVE: currently exists bug by resize row height in Nattable, when
		// vertical alignment !== TOP
		// See: https://bugs.eclipse.org/bugs/show_bug.cgi?id=582373
		this.defaultVAlign = VerticalAlignmentEnum.TOP;
		// initialize heading
		this.heading = heading;

		// initialize styles
		greyedOutStyle = new Style();
		greyedOutStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR,
				greyedOutStyleBackgroundColor);
		greyedOutStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR,
				greyedOutStyleForegroundColor);
		alignLeftStyle = new Style();
		alignLeftStyle.setAttributeValue(
				CellStyleAttributes.HORIZONTAL_ALIGNMENT,
				HorizontalAlignmentEnum.LEFT);

		fatHeaderStyle = new Style();
		fatHeaderStyle.setAttributeValue(CellStyleAttributes.FONT, headerFont);
		fatHeaderStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR,
				greyedOutStyleBackgroundColor);
		fatHeaderStyle.setAttributeValue(CellStyleAttributes.VERTICAL_ALIGNMENT,
				VerticalAlignmentEnum.MIDDLE);

		// set label accumulators
		headerLayer.setConfigLabelAccumulator(headerLayerAccumulator);
		dataLayer.setConfigLabelAccumulator(dataLayerAccumulator);
		bodyLayer.setConfigLabelAccumulator(bodyLayerAccumulator);

		// add label accumulators
		addGreyedLabel();
		addLeftAlignmentLabel();
		addExceptionLabel();
		freezeSeparatorColor = GUIHelper.COLOR_BLACK;
		// width
		setDefaultColumnWidth();

		// add handler
		registerHandler(gridLayer);
	}

	@Override
	public void createPainterInstances() {
		super.createPainterInstances();
		final boolean wraptext = true;
		final boolean calculateByTextLength = false;
		final boolean calculateByTextHeight = true;

		final Image yellowWarningImage = createImage(resourceManager,
				SetImages.WARNING_YELLOW);
		final Image redWarningImage = createImage(resourceManager,
				SetImages.WARNING_RED);
		final Image blackWarningImage = createImage(resourceManager,
				SetImages.IC_WARNING_BLACK_18DP_1X);

		this.defaultCellPainter = new PlanProTableCellPainter(
				new PlanProRichTextCellPainter(wraptext, calculateByTextLength,
						calculateByTextHeight),
				yellowWarningImage, redWarningImage, blackWarningImage);
		this.filterRowCellPainter = new PaddingDecorator(
				new FilterRowPainter(
						new FilterIconPainter(GUIHelper.getImage("filter"))), //$NON-NLS-1$
				0, 0, 0, 5);
		this.cGroupHeaderCellPainter = new ColumnGroupHeaderTextPainter(
				new TextPainter(true, false), CellEdgeEnum.RIGHT, null);
	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		super.configureRegistry(configRegistry);
		registerGreyedStyle(configRegistry);
		registerExceptionStyle(configRegistry);
		registerAlignLeftStyle(configRegistry);
		registerHeaderStyle(configRegistry);
		registerCompareTableCellStyle(configRegistry);

	}

	private void addExceptionLabel() {
		final IConfigLabelAccumulator cellLabelAccumulator = (configLabels,
				columnPosition, rowPosition) -> {

			final int columnIndex = bodyLayer
					.getColumnIndexByPosition(columnPosition);
			final int rowIndex = bodyLayer.getRowIndexByPosition(rowPosition);

			final String dataValue = bodyDataProvider
					.getDataValue(columnIndex, rowIndex)
					.toString();
			if (CellContentExtensions.isErrorText(dataValue)) {
				configLabels.addLabel(EXCEPTION_LABEL);
			}
		};
		bodyLayerAccumulator.add(cellLabelAccumulator);
	}

	private void addGreyedLabel() {
		final ColumnOverrideLabelAccumulator headerGreyed = new ColumnOverrideLabelAccumulator(
				headerLayer);
		final ColumnOverrideLabelAccumulator dataGreyed = new ColumnOverrideLabelAccumulator(
				dataLayer);
		headerLayerAccumulator.add(headerGreyed);
		dataLayerAccumulator.add(dataGreyed);

		final int[] greyedColumns = ColumnDescriptorExtensions
				.getGreyedColumnIndices(heading);
		for (final int greyedColumn : greyedColumns) {
			dataGreyed.registerColumnOverrides(greyedColumn, GREYED_LABEL);
		}

	}

	private void addLeftAlignmentLabel() {
		final ColumnOverrideLabelAccumulator firstColumnLeft = new ColumnOverrideLabelAccumulator(
				dataLayer);
		dataLayerAccumulator.add(firstColumnLeft);
		firstColumnLeft.registerColumnOverrides(0, ALIGN_LEFT);
	}

	private Color createColor(final ColorDescriptor descriptor) {
		return resourceManager.create(descriptor);
	}

	private Color createColor(final String colorKey, final Color defaultColor) {
		final ColorDescriptor descriptor = Colors.keyToDescriptor(colorKey,
				defaultColor);
		return createColor(descriptor);
	}

	private void registerAlignLeftStyle(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE,
				alignLeftStyle, DisplayMode.NORMAL, ALIGN_LEFT);
	}

	private void registerExceptionStyle(final IConfigRegistry configRegistry) {
		if (!ToolboxConfiguration.isDevelopmentMode()) {
			// show warning icon only in presentation mode
			registerWarningPainter(configRegistry);
		}
	}

	private void registerGreyedStyle(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE,
				greyedOutStyle, DisplayMode.NORMAL, GREYED_LABEL);
	}

	private void registerHeaderStyle(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE,
				fatHeaderStyle, DisplayMode.NORMAL, GridRegion.COLUMN_HEADER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE,
				fatHeaderStyle, DisplayMode.SELECT, GridRegion.COLUMN_HEADER);
	}

	private void registerCompareTableCellStyle(
			final IConfigRegistry configRegistry) {
		final ICellPainter lineBorderDecorator = new LineBorderDecorator(
				defaultCellPainter, new BorderStyle(1,
						new Color(new RGB(0, 102, 255)), LineStyleEnum.SOLID));

		configRegistry.registerConfigAttribute(
				CellConfigAttributes.CELL_PAINTER, lineBorderDecorator,
				DisplayMode.NORMAL,
				ToolboxConstants.TABLE_COMPARE_TABLE_CELL_LABEL);
	}

	private void registerWarningPainter(final IConfigRegistry configRegistry) {
		final Style style = new Style();
		style.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT,
				HorizontalAlignmentEnum.CENTER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE,
				style, DisplayMode.NORMAL, EXCEPTION_LABEL);

		final Image bgImage = createImage(resourceManager,
				SetImages.IC_WARNING_BLACK_18DP_1X);
		final ICellPainter bgImagePainter = new ImagePainter(bgImage, true);
		configRegistry.registerConfigAttribute(
				CellConfigAttributes.CELL_PAINTER, bgImagePainter,
				DisplayMode.NORMAL, EXCEPTION_LABEL);
	}

	private void setDefaultColumnWidth() {
		final int[] columns = ColumnDescriptorExtensions
				.getColumnIndices(heading);
		for (final int column : columns) {

			final Float width = ColumnDescriptorExtensions
					.getColumnWidth(heading, column);
			if (width != null) {
				switch (ColumnDescriptorExtensions.getColumnWidthMode(heading,
						column)) {
					case WIDTH_CM:
						dataLayer.setColumnPercentageSizing(column, false);
						dataLayer.setColumnWidthByPosition(column,
								toPixel(width.floatValue()));
						break;
					case WIDTH_PERCENT:
						dataLayer.setColumnPercentageSizing(column, true);
						dataLayer.setColumnWidthPercentageByPosition(column,
								(int) width.floatValue());
						break;
					default:
						throw new IllegalArgumentException(
								"Invalid width mode"); //$NON-NLS-1$
				}
			}

		}
	}
}
