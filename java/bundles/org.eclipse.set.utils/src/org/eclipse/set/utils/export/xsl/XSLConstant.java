/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.export.xsl;

import org.eclipse.set.model.tablemodel.CompareTableCellContent;

/**
 * XSL tag, attribute
 * 
 * @author Truong
 */
@SuppressWarnings("nls")
public class XSLConstant {

	/**
	 * The style of folding mark
	 */
	public static final String FOLDING_MARK_STYLE = "0.5mm solid black";

	/**
	 * The value of the name attribute
	 */
	public static class XSLNodeName {
		/**
		 * page-master-style
		 */
		public static final String PAGE_MASTER_STYLE = "page-master-style";

		/**
		 * title-box-region
		 */
		public static final String TITLE_BOX_REGION = "title-box-region";

		/**
		 * page-post-fix
		 */
		public static final String PAGE_POST_FIX = "page-post-fix";

		/**
		 * region-body-height
		 */
		public static final String REGION_BODY_HEIGHT = "region-body-height";

		/**
		 * region-body-width
		 */
		public static final String REGION_BODY_WIDTH = "region-body-width";

		/**
		 * significant-height
		 */
		public static final String SIGNIFICANT_HEIGHT = "significant-height";

		/**
		 * significant-width
		 */
		public static final String SIGNIFICANT_WIDTH = "significant-width";

		/**
		 * siteplan-freefeld-height
		 */
		public static final String SITEPLAN_FREEFELD_HEIGHT = "siteplan-freefeld-height";

		/**
		 * water-mark-content
		 */
		public static final String WATER_MARK_TEMPLATE_NAME = "water-mark-content";

		/**
		 * siteplan-folding-mark-top-bottom
		 */
		public static final String SITEPLAN_FOLDING_MARK_TOP_BOTTOM = "siteplan-folding-mark-top-bottom";

		/**
		 * siteplan-folding-mark-top-bottom
		 */
		public static final String SITEPLAN_FOLDING_MARK_SIDE = "siteplan-folding-mark-side";

		/**
		 * siteplan-folding-mark-right-width
		 */
		public static final String SITEPLAN_FOLDING_MARK_RIGHT_WIDTH = "siteplan-folding-mark-right-width";

		/**
		 * pagePosition
		 */
		public static final String SITEPLAN_PAGEPOSITION = "pagePosition";

		/**
		 * pagePostFix
		 */
		public static final String SITEPLAN_PAGE_POSTFIX = "pagePostFix";

	}

	/**
	 * XSL Tag
	 */
	public static class XSLTag {

		private XSLTag() {
		}

		/**
		 * xsl:attribute
		 */
		public static final String XSL_ATTRIBUTE = "xsl:attribute"; //$NON-NLS-1$

		/**
		 * xsl:attribute-set
		 */
		public static final String XSL_ATTRIBUTE_SET = "xsl:attribute-set"; //$NON-NLS-1$

		/**
		 * "xsl:stylesheet
		 */
		public static final String XSL_STYLESHEET = "xsl:stylesheet"; //$NON-NLS-1$
		/**
		 * xsl:template
		 */
		public static final String XSL_TEMPLATE = "xsl:template"; //$NON-NLS-1$

		/**
		 * xsl:apply-templates
		 */
		public static final String XSL_APPLY_TEMPLATE = "xsl:apply-templates"; //$NON-NLS-1$

		/**
		 * xsl:value-of
		 */
		public static final String XSL_VALUE_OF = "xsl:value-of"; //$NON-NLS-1$

		/**
		 * xsl:variable
		 */
		public static final String XSL_VARIABLE = "xsl:variable"; //$NON-NLS-1$

		/**
		 * xsl:param
		 */
		public static final String XSL_PARAM = "xsl:param"; //$NON-NLS-1$

		/**
		 * fo:table
		 */
		public static final String FO_TABLE = "fo:table"; //$NON-NLS-1$
		/**
		 * fo:table-header
		 */
		public static final String FO_TABLE_HEADER = "fo:table-header"; //$NON-NLS-1$
		/**
		 * fo:table-body
		 */
		public static final String FO_TABLE_BODY = "fo:table-body"; //$NON-NLS-1$
		/**
		 * fo:table-column
		 */
		public static final String FO_TABLE_COLUMN = "fo:table-column"; //$NON-NLS-1$
		/**
		 * 
		 */
		public static final String FO_TABLE_ROW = "fo:table-row"; //$NON-NLS-1$
		/**
		 * fo:table-cell
		 */
		public static final String FO_TABLE_CELL = "fo:table-cell"; //$NON-NLS-1$
		/**
		 * fo:block
		 */
		public static final String FO_BLOCK = "fo:block"; //$NON-NLS-1$

		/**
		 * fo:inline
		 */
		public static final String FO_INLINE = "fo:inline"; //$NON-NLS-1$

		/**
		 * fo:layout-master-set
		 */
		public static final String FO_LAYOUT_MASTER_SET = "fo:layout-master-set"; //$NON-NLS-1$

		/**
		 * fo:simple-page-master
		 */
		public static final String FO_SIMPLE_PAGE_MASTER = "fo:simple-page-master"; //$NON-NLS-1$

		/**
		 * xsl:choose
		 */
		public static final String XSL_CHOOSE = "xsl:choose";

		/**
		 * xsl:when
		 */
		public static final String XSL_WHEN = "xsl:when";

		/**
		 * xsl:otherwise
		 */
		public static final String XSL_OTHERWISE = "xsl:otherwise";

		/**
		 * xsl:call-template
		 */
		public static final String XSL_CALL_TEMPLATE = "xsl:call-template";
	}

	/**
	 * XSL & Fo attribute
	 * 
	 * @author Truong
	 *
	 */
	public static class XSLFoAttributeName {
		/**
		 * name
		 */
		public static final String ATTR_NAME = "name";

		/**
		 * id
		 */
		public static final String ATTR_ID = "id";

		/**
		 * page-height
		 */
		public static final String ATTR_PAGE_HEIGHT = "page-height";

		/**
		 * page-width
		 */
		public static final String ATTR_PAGE_WIDTH = "page-width";

		/**
		 * margin-left
		 */
		public static final String ATTR_CONTENT_MARGIN_LEFT = "margin-left";

		/**
		 * margin-right
		 */
		public static final String ATTR_CONTENT_MARGIN_RIGHT = "margin-right";

		/**
		 * margin-top
		 */
		public static final String ATTR_CONTENT_MARGIN_TOP = "margin-top";

		/**
		 * margin-right
		 */
		public static final String ATTR_CONTENT_MARGIN_BOTTOM = "margin-bottom";

		/**
		 * match
		 */
		public static final String ATTR_MATCH = "match";

		/**
		 * select
		 */
		public static final String ATTR_SELECT = "select";

		/**
		 * start-indent
		 */
		public static final String ATTR_START_INDENT = "start-indent";

		/**
		 * region-name
		 */
		public static final String REGION_NAME = "region-name";

		/**
		 * precedence
		 */
		public static final String PRECEDENCE = "precedence";

		/**
		 * overflow
		 */
		public static final String OVERFLOW = "overflow";

		/**
		 * height
		 */
		public static final String ATTR_HEIGHT = "height";

		/**
		 * width
		 */
		public static final String ATTR_WIDTH = "width";

		/**
		 * color
		 */
		public static final String ATTR_COLOR = "color";
	}

	/**
	 * @author Truong
	 *
	 */
	public static class TableAttrValue {
		/**
		 * Table[Rows/Row]
		 */
		public static final String TABLE_ROWS_ROW = "Table[Rows/Row]";

		/**
		 * Table[not(Rows/Row)]
		 */
		public static final String TABLE_NOT_ROWS_ROW = "Table[not(Rows/Row)]";

		/**
		 * Rows/Row
		 */
		public static final String ROWS_ROW = "Rows/Row";

		/**
		 * Footnotes
		 */
		public static final String FOOTNOTES = "Footnotes";

		/**
		 * fixed
		 */
		public static final String LAYOUT_FIXED = "fixed";

	}

	/**
	 * XSl style set
	 */
	public class XSLStyleSets {

		/**
		 * wide-border-style
		 */
		public static final String WIDE_BORDER_STYLE = "wide-border-style";
		/**
		 * small-border-style
		 */
		public static final String SMALL_BORDER_STYLE = "small-border-style";

		/**
		 * no-border-style
		 */
		public static final String NO_BORDER_STYLE = "no-border-style";

		/**
		 * "default-cell-style
		 */
		public static final String DEFAULT_CELL_STYLE = "default-cell-style";

		/**
		 * first-column-cell-style
		 */
		public static final String FIRST_COLUMN_CELL_STYLE = "first-column-cell-style";

		/**
		 * last-row-cell-style
		 */
		public static final String LAST_ROW_CELL_STYLE = "last-row-cell-style";

		/**
		 * body-row-cell-style
		 */
		public static final String BODY_ROW_CELL_STYLE = "body-row-cell-style";

		/**
		 * thin-border-style
		 */
		public static final String THIN_BORDER_STYLE = "thin-border-style";

		/**
		 * table-header-style
		 */
		public static final String TABLE_HEADER_STYLE = "table-header-style";

		/**
		 * Style template name for {@link CompareTableCellContent}
		 */
		public static final String PLAN_COMPARE_CONTENT_TEMPLATE = "CompareCellContentStyle";
	}

	/**
	 * @author truong
	 *
	 */
	public class TableAttribute {
		/**
		 * xsl:use-attribute-sets
		 */
		public static String XSL_USE_ATTRIBUTE_SETS = "xsl:use-attribute-sets"; //$NON-NLS-1$

		/**
		 * table-layout
		 */
		public static String TABLE_LAYOUT = "table-layout"; //$NON-NLS-1$

		/**
		 * width
		 */
		public static String TABLE_WIDTH = "width"; //$NON-NLS-1$
		/**
		 * 
		 */
		public static String COLUMN_NUMBER = "column-number"; //$NON-NLS-1$

		/**
		 * column-width
		 */
		public static String COLUMN_WIDTH = "column-width"; //$NON-NLS-1$

		/**
		 * display-align
		 */
		public static String DISPLAY_ALIGN = "display-align"; //$NON-NLS-1$

		/**
		 * text-align
		 */
		public static String TEXT_ALIGN = "text-align"; //$NON-NLS-1$

		/**
		 * number-columns-spanned
		 */
		public static String NUMBER_COLUMNS_SPANNED = "number-columns-spanned"; //$NON-NLS-1$

		/**
		 * number-rows-spanned
		 */
		public static String NUMBER_ROWS_SPANNED = "number-rows-spanned"; //$NON-NLS-1$

		/**
		 * start-indent
		 */
		public static String START_INDENT = "start-indent"; //$NON-NLS-1$

		/**
		 * font-weight
		 */
		public static String FONT_WEIGHT = "font-weight"; //$NON-NLS-1$

		/**
		 * font-style
		 */
		public static String FONT_STYLE = "font-style"; //$NON-NLS-1$

		/**
		 * @author truong
		 *
		 */
		public enum BorderDirection {
			/**
			 * 
			 */
			TOP("border-top"), //$NON-NLS-1$

			/**
			 * 
			 */
			BOTTOM("border-bottom"), //$NON-NLS-1$

			/**
			 * 
			 */
			LEFT("border-left"), //$NON-NLS-1$

			/**
			 * 
			 */
			RIGHT("border-right"); //$NON-NLS-1$

			private final String direction;

			private BorderDirection(final String direction) {
				this.direction = direction;
			}

			/**
			 * @return the direction string
			 */
			public String getDirectionString() {
				return direction;
			}
		}
	}
}
