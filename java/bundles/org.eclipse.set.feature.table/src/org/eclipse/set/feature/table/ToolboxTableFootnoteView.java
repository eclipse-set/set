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
package org.eclipse.set.feature.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions.FootnoteInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * The table footnote view
 */
public class ToolboxTableFootnoteView extends StyledText {
	private static Color GRAY_BORDER_TOP = new Color(Display.getCurrent(), 171,
			171, 171);
	private static Color GRAY_BACKGROUND = new Color(Display.getCurrent(), 240,
			240, 240);

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent
	 */
	public ToolboxTableFootnoteView(final Composite parent) {
		super(parent, SWT.MULTI | SWT.V_SCROLL);
		addPaintListener(drawTopBorder());
		setTopMargin(2);
		GridDataFactory.fillDefaults()
				.grab(true, false)
				.minSize(-1, 500)
				.applyTo(this);
		setBackground(GRAY_BACKGROUND);
		setAlwaysShowScrollBars(false);
		setEditable(false);
	}

	private PaintListener drawTopBorder() {
		return e -> {
			final Rectangle clientArea = getClientArea();
			e.gc.setForeground(GRAY_BORDER_TOP);
			e.gc.drawLine(0, 0, clientArea.width - 1, 0);
		};
	}

	/**
	 * @param table
	 *            the {@link Table}
	 */
	public void updateFootnotes(final Table table) {
		final List<String> lines = new ArrayList<>();
		final List<StyleRange> styles = new ArrayList<>();
		int startOffset = 0;
		for (final FootnoteInfo footnote : TableExtensions
				.getAllFootnotes(table)) {
			final String text = footnote.toReferenceText();
			lines.add(text);

			switch (footnote.type) {
				case NEW_FOOTNOTE:
					styles.add(new StyleRange(startOffset, text.length(),
							new Color(255, 0, 0), null));
					break;
				case OLD_FOOTNOTE:
					final StyleRange styleRange = new StyleRange(startOffset,
							text.length(), null, new Color(255, 255, 0));
					styleRange.strikeout = true;
					styles.add(styleRange);

					break;
				case COMMON_FOOTNOTE:
				default:
					break;
			}
			startOffset += text.length() + 1;

		}
		setVisible(!lines.isEmpty());

		if (lines.isEmpty()) {
			getParent().layout();
			return;
		}

		if (!(getParent() instanceof SashForm)) {
			setFootnoteDynamicResize();
		}

		if (lines.size() > ToolboxConstants.FOOTNOTE_ACTIVE_SCROLL_MINIMUM) {
			GridDataFactory.fillDefaults().grab(true, true).applyTo(this);
		}

		setText(StringUtils.join(lines, "\n")); //$NON-NLS-1$
		setStyleRanges(styles.toArray(new StyleRange[0]));
	}

	private void setFootnoteDynamicResize() {
		getParent().setLayout(new FillLayout());
		final Control[] children = getParent().getChildren();
		final SashForm sashForm = new SashForm(getParent(), SWT.VERTICAL);
		for (final Control child : children) {
			child.setParent(sashForm);
		}
		// Default weight value nattable - 8, footnote - 2
		sashForm.setWeights(8, 2);
	}
}
