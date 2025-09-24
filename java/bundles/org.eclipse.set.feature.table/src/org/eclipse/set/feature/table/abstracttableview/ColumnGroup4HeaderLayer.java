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

import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel.ColumnGroup;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupUtils;
import org.eclipse.nebula.widgets.nattable.group.config.DefaultColumnGroupHeaderLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.SizeConfig;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.layer.cell.LayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

/**
 * Adds the group group group grouping functionality to the column headers.
 * 
 * @author Schaefer
 */
public class ColumnGroup4HeaderLayer extends AbstractLayerTransform {

	private final ColumnGroupGroupGroupHeaderLayer columnGroupGroupGroupHeaderLayer;
	private final ColumnGroupGroupHeaderLayer columnGroupGroupHeaderLayer;
	private final ColumnGroupHeaderLayer columnGroupHeaderLayer;
	private final ColumnGroupModel model;
	private final SizeConfig rowHeightConfig = new SizeConfig(
			DataLayer.DEFAULT_ROW_HEIGHT);

	/**
	 * @param columnGroupGroupGroupHeaderLayer
	 *            the group group group header layer
	 * @param columnGroupGroupHeaderLayer
	 *            the group group header layer
	 * @param columnGroupHeaderLayer
	 *            the group header layer
	 * @param selectionLayer
	 *            the selection layer
	 * @param columnGroupGroupGroupModel
	 *            the group group group model
	 */
	public ColumnGroup4HeaderLayer(
			final ColumnGroupGroupGroupHeaderLayer columnGroupGroupGroupHeaderLayer,
			final ColumnGroupGroupHeaderLayer columnGroupGroupHeaderLayer,
			final ColumnGroupHeaderLayer columnGroupHeaderLayer,
			final SelectionLayer selectionLayer,
			final ColumnGroupModel columnGroupGroupGroupModel) {
		super(columnGroupGroupGroupHeaderLayer);
		this.columnGroupGroupGroupHeaderLayer = columnGroupGroupGroupHeaderLayer;
		this.columnGroupGroupHeaderLayer = columnGroupGroupHeaderLayer;
		this.columnGroupHeaderLayer = columnGroupHeaderLayer;
		this.model = columnGroupGroupGroupModel;

		addConfiguration(new DefaultColumnGroupHeaderLayerConfiguration(
				columnGroupGroupGroupModel, true));
	}

	/**
	 * @param colGroupGroupGroupName
	 *            name of the group group group
	 * @param colIndexes
	 *            column indexes of the group group group
	 */
	public void addColumnsIndexesToGroup(final String colGroupGroupGroupName,
			final int... colIndexes) {
		this.model.addColumnsIndexesToGroup(colGroupGroupGroupName, colIndexes);
	}

	@Override
	public ILayerCell getCellByPosition(final int columnPosition,
			final int rowPosition) {
		final int bodyColumnIndex = getColumnIndexByPosition(columnPosition);

		if (rowPosition == 0) {
			if (this.model.isPartOfAGroup(bodyColumnIndex)) {
				return new LayerCell(this,
						getStartPositionOfGroup(columnPosition), rowPosition,
						columnPosition, rowPosition,
						getColumnSpan(columnPosition), 1);
			}
			final ILayerCell underlyingCell = this.columnGroupGroupGroupHeaderLayer
					.getCellByPosition(columnPosition, rowPosition);
			return new LayerCell(this, underlyingCell.getOriginColumnPosition(),
					underlyingCell.getOriginRowPosition(), columnPosition,
					rowPosition, underlyingCell.getColumnSpan(),
					underlyingCell.getRowSpan() + 1);
		} else if (rowPosition == 1) {
			final ILayerCell underlyingCell = this.columnGroupGroupGroupHeaderLayer
					.getCellByPosition(columnPosition, rowPosition - 1);
			final boolean partOfAGroup = this.model
					.isPartOfAGroup(bodyColumnIndex);
			return new LayerCell(this, underlyingCell.getOriginColumnPosition(),
					underlyingCell.getOriginRowPosition()
							+ (partOfAGroup ? 1 : 0),
					columnPosition, rowPosition, underlyingCell.getColumnSpan(),
					underlyingCell.getRowSpan() + (partOfAGroup ? 0 : 1));
		} else if (rowPosition == 2) {
			final ILayerCell underlyingCell = this.columnGroupGroupGroupHeaderLayer
					.getCellByPosition(columnPosition, rowPosition - 1);
			final boolean partOfAGroup = this.model
					.isPartOfAGroup(bodyColumnIndex)
					|| this.columnGroupGroupGroupHeaderLayer
							.isColumnInGroup(bodyColumnIndex);
			return new LayerCell(this, underlyingCell.getOriginColumnPosition(),
					underlyingCell.getOriginRowPosition()
							+ (partOfAGroup ? 1 : 0),
					columnPosition, rowPosition, underlyingCell.getColumnSpan(),
					underlyingCell.getRowSpan() + (partOfAGroup ? 0 : 1));
		} else if (rowPosition == 3) {
			final ILayerCell underlyingCell = this.columnGroupGroupGroupHeaderLayer
					.getCellByPosition(columnPosition, rowPosition - 1);
			final boolean partOfAGroup = this.model
					.isPartOfAGroup(bodyColumnIndex)
					|| this.columnGroupGroupGroupHeaderLayer
							.isColumnInGroup(bodyColumnIndex)
					|| this.columnGroupGroupHeaderLayer
							.isColumnInGroup(bodyColumnIndex);
			return new LayerCell(this, underlyingCell.getOriginColumnPosition(),
					underlyingCell.getOriginRowPosition()
							+ (partOfAGroup ? 1 : 0),
					columnPosition, rowPosition, underlyingCell.getColumnSpan(),
					underlyingCell.getRowSpan() + (partOfAGroup ? 0 : 1));
		} else if (rowPosition == 4) {
			final ILayerCell underlyingCell = this.columnGroupGroupGroupHeaderLayer
					.getCellByPosition(columnPosition, rowPosition - 1);
			final boolean partOfAGroup = this.model
					.isPartOfAGroup(bodyColumnIndex)
					|| this.columnGroupGroupGroupHeaderLayer
							.isColumnInGroup(bodyColumnIndex)
					|| this.columnGroupGroupHeaderLayer
							.isColumnInGroup(bodyColumnIndex)
					|| this.columnGroupHeaderLayer
							.isColumnInGroup(bodyColumnIndex);
			return new LayerCell(this, underlyingCell.getOriginColumnPosition(),
					underlyingCell.getOriginRowPosition()
							+ (partOfAGroup ? 1 : 0),
					columnPosition, rowPosition, underlyingCell.getColumnSpan(),
					underlyingCell.getRowSpan() + (partOfAGroup ? 0 : 1));
		}
		return null;
	}

	@Override
	public LabelStack getConfigLabelsByPosition(final int columnPosition,
			final int rowPosition) {
		int localRowPosition = rowPosition;
		final int columnIndex = getColumnIndexByPosition(columnPosition);
		if (localRowPosition == 0 && this.model.isPartOfAGroup(columnIndex)) {
			final LabelStack stack = new LabelStack(
					GridRegion.COLUMN_GROUP_HEADER);

			if (this.model.isPartOfACollapseableGroup(columnIndex)) {
				final ColumnGroup group = this.model
						.getColumnGroupByIndex(columnIndex);
				if (group.isCollapsed()) {
					stack.addLabelOnTop(
							DefaultColumnGroupHeaderLayerConfiguration.GROUP_COLLAPSED_CONFIG_TYPE);
				} else {
					stack.addLabelOnTop(
							DefaultColumnGroupHeaderLayerConfiguration.GROUP_EXPANDED_CONFIG_TYPE);
				}
			}

			return stack;
		}
		if (localRowPosition != 0) {
			localRowPosition--;
		}
		return this.columnGroupGroupGroupHeaderLayer
				.getConfigLabelsByPosition(columnPosition, localRowPosition);
	}

	@Override
	public Object getDataValueByPosition(final int columnPosition,
			final int rowPosition) {
		int localRowPosition = rowPosition;
		final int columnIndex = getColumnIndexByPosition(columnPosition);
		final ColumnGroup columnGroup = this.model
				.getColumnGroupByIndex(columnIndex);

		if (localRowPosition == 0) {
			if (this.model.isPartOfAGroup(columnIndex)) {
				return columnGroup.getName();
			}
		} else {
			localRowPosition--;
		}

		return this.columnGroupGroupGroupHeaderLayer
				.getDataValueByPosition(columnPosition, localRowPosition);
	}

	@Override
	public DisplayMode getDisplayModeByPosition(final int columnPosition,
			final int rowPosition) {
		final int columnIndex = getColumnIndexByPosition(columnPosition);
		if (rowPosition == 0 && this.model.isPartOfAGroup(columnIndex)) {
			return DisplayMode.NORMAL;
		}
		return this.columnGroupGroupGroupHeaderLayer
				.getDisplayModeByPosition(columnPosition, rowPosition);
	}

	@Override
	public int getHeight() {
		return this.rowHeightConfig.getAggregateSize(1)
				+ this.columnGroupGroupGroupHeaderLayer.getHeight();
	}

	@Override
	public int getPreferredHeight() {
		return this.rowHeightConfig.getAggregateSize(1)
				+ this.columnGroupGroupGroupHeaderLayer.getPreferredHeight();
	}

	@Override
	public int getPreferredRowCount() {
		return this.columnGroupGroupHeaderLayer.getPreferredRowCount() + 1;
	}

	@Override
	public LabelStack getRegionLabelsByXY(final int x, final int y) {
		final int columnIndex = getColumnIndexByPosition(
				getColumnPositionByX(x));
		if (this.model.isPartOfAGroup(columnIndex)
				&& y < getRowHeightByPosition(0)) {
			return new LabelStack(GridRegion.COLUMN_GROUP_HEADER);
		}
		return this.columnGroupGroupGroupHeaderLayer.getRegionLabelsByXY(x,
				y - getRowHeightByPosition(0));
	}

	@Override
	public int getRowCount() {
		return this.columnGroupGroupGroupHeaderLayer.getRowCount() + 1;
	}

	@Override
	public int getRowHeightByPosition(final int rowPosition) {
		if (rowPosition == 0) {
			return this.rowHeightConfig.getSize(rowPosition);
		}
		return this.columnGroupGroupGroupHeaderLayer
				.getRowHeightByPosition(rowPosition - 1);
	}

	@Override
	public int getRowIndexByPosition(final int rowPosition) {
		if (rowPosition == 0) {
			return rowPosition;
		}
		return this.columnGroupGroupGroupHeaderLayer
				.getRowIndexByPosition(rowPosition - 1);
	}

	@Override
	public int getRowPositionByY(final int y) {
		final int row0Height = getRowHeightByPosition(0);
		if (y < row0Height) {
			return 0;
		}
		return 1 + this.columnGroupGroupGroupHeaderLayer
				.getRowPositionByY(y - row0Height);
	}

	@Override
	public int getStartYOfRowPosition(final int rowPosition) {
		if (rowPosition == 0) {
			return this.rowHeightConfig.getAggregateSize(rowPosition);
		}
		return getRowHeightByPosition(0) + this.columnGroupGroupGroupHeaderLayer
				.getStartYOfRowPosition(rowPosition - 1);
	}

	@Override
	public boolean isRowPositionResizable(final int rowPosition) {
		if (rowPosition == 0) {
			return this.rowHeightConfig.isPositionResizable(rowPosition);
		}
		return this.columnGroupGroupGroupHeaderLayer
				.isRowPositionResizable(rowPosition - 1);
	}

	/**
	 * @param rowHeight
	 *            the row height
	 */
	public void setRowHeight(final int rowHeight) {
		this.rowHeightConfig.setSize(0, rowHeight);
	}

	private int getStartPositionOfGroup(final int columnPosition) {
		final int bodyColumnIndex = getColumnIndexByPosition(columnPosition);
		final ColumnGroup columnGroup = this.model
				.getColumnGroupByIndex(bodyColumnIndex);

		final int leastPossibleStartPositionOfGroup = columnPosition
				- columnGroup.getSize();
		int i = 0;
		for (i = leastPossibleStartPositionOfGroup; i < columnPosition; i++) {
			if (ColumnGroupUtils.isInTheSameGroup(getColumnIndexByPosition(i),
					bodyColumnIndex, this.model)) {
				break;
			}
		}
		return i;
	}

	protected int getColumnSpan(final int columnPosition) {
		final int columnIndex = getColumnIndexByPosition(columnPosition);
		final ColumnGroup columnGroup = this.model
				.getColumnGroupByIndex(columnIndex);

		int sizeOfGroup = columnGroup.getSize();

		if (columnGroup.isCollapsed()) {
			final int sizeOfStaticColumns = columnGroup.getStaticColumnIndexes()
					.size();
			if (sizeOfStaticColumns == 0) {
				return 1;
			}
			sizeOfGroup = sizeOfStaticColumns;
		}

		final int startPositionOfGroup = getStartPositionOfGroup(
				columnPosition);
		final int endPositionOfGroup = startPositionOfGroup + sizeOfGroup;
		final List<Integer> columnIndexesInGroup = columnGroup.getMembers();

		for (int i = startPositionOfGroup; i < endPositionOfGroup; i++) {
			final int index = getColumnIndexByPosition(i);
			if (!columnIndexesInGroup.contains(Integer.valueOf(index))) {
				sizeOfGroup--;
			}
		}
		return sizeOfGroup;
	}
}
