/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.transform;

/**
 * XSL tag, attribute
 * 
 * @author Truong
 */
public class XSLConstant {
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
		 * fo:layout-master-set
		 */
		public static final String FO_LAYOUT_MASTER_SET = "fo:layout-master-set"; //$NON-NLS-1$

		/**
		 * fo:simple-page-master
		 */
		public static final String FO_SIMPLE_PAGE_MASTER = "fo:simple-page-master"; //$NON-NLS-1$
	}

	/**
	 * XSL & Fo attribute
	 * 
	 * @author Truong
	 *
	 */
	@SuppressWarnings("nls")
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
	}

	/**
	 * @author Truong
	 *
	 */
	@SuppressWarnings("nls")
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
	@SuppressWarnings("nls")
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
