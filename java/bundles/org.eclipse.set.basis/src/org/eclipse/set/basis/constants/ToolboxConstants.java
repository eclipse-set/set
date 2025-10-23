/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants;

import java.util.Comparator;

import org.eclipse.set.basis.MixedStringComparator;
import org.eclipse.set.basis.NumericFirstComparatorDecorator;
import org.eclipse.set.basis.ToolboxProperties;

/**
 * Common toolbox constants.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls") // Constants are not translated
public final class ToolboxConstants {

	/**
	 * Nested part for cache ids.
	 */
	public interface CacheId {

		/**
		 * The id of the guid to object cache.
		 */
		public static final String GUID_TO_OBJECT = "toolbox.cache.guid-to-object";

		/**
		 * The id of the Top-Kante to single point cache.
		 */
		public static final String TOPKANTE_TO_SINGLEPOINTS = "toolbox.cache.topkante-to-singlepoints";

		/**
		 * The id of the Fahrweg key to single Fahrweg routes cache.
		 */
		public static final String FAHRWEG_TO_ROUTES = "toolbox.cache.fahrweg-key-to-fahrweg-routes";

		/**
		 * The id of the edge to single single points cache.
		 */
		public static final String DIRECTED_EDGE_TO_SINGLEPOINTS = "toolbox.cache.directed-edge-to-singlepoints";

		/**
		 * The id of the edge to subpath cache.
		 */
		public static final String DIRECTED_EDGE_TO_SUBPATH = "toolbox.cache.directed-edge-to-subpath";

		/**
		 * The id of the Top-Kante to adjacent Top-Kanten cache.
		 */
		public static final String TOPKANTE_TO_ADJACENT_TOPKANTEN = "toolbox.cache.topkante-to-adjacent-topkanten";

		/**
		 * The geometry of the GEO_Kante
		 */
		public static final String GEOKANTE_GEOMETRY = "toolbox.cache.geokante-geometry";

		/**
		 * The id of the problem message cache.
		 */
		public static final String PROBLEM_MESSAGE = "toolbox.cache.problem-message";

		/**
		 * The id of the table error cache of initial state.
		 */
		public static final String TABLE_ERRORS_INITIAL = "toolbox.cache.table-errors-initial";

		/**
		 * The id of the table error cache of final state.
		 */
		public static final String TABLE_ERRORS_FINAL = "toolbox.cache.table-errors-final";

		/**
		 * The id of the table error cache of single state.
		 */
		public static final String TABLE_ERRORS_SINGLE = "toolbox.cache.table-errors-single";

		/**
		 * The id of the table error cache.
		 */
		public static final String TABLE_ERRORS = "toolbox.cache.table-errors";

		/**
		 * The id of the siteplan
		 */
		public static final String SITEPLAN_CACHE_ID = "toolbox.cache.siteplan";
	}

	/**
	 * Extension category for all PlanPro files (PlanPro models + merge).
	 */
	public static final String EXTENSION_CATEGORY_PPALL = "ppall";

	/**
	 * Extension category for PlanPro models.
	 */
	public static final String EXTENSION_CATEGORY_PPFILE = "ppfile";

	/**
	 * Extension category for PlanPro models
	 */
	public static final String EXTENSION_CATEGORY_PPMERGE = "ppmerge";

	/**
	 * mplanpro extension
	 */
	public static final String EXTENSION_MPLANPRO = "mplanpro";

	/**
	 * planpro extension
	 */
	public static final String EXTENSION_PLANPRO = "planpro";

	/**
	 * ppxml extension
	 */
	public static final String EXTENSION_PPXML = "ppxml";

	/**
	 * xml extension
	 */
	public static final String EXTENSION_XML = "xml";

	/**
	 * PID file name
	 */
	public static final String PID_FILE_NAME = ".pid";

	/**
	 * File parameter.
	 */
	public static final String FILE_PARAMETER = "org.eclipse.set.application.commandparameter.file"; //$NON-NLS-1$

	/**
	 * Compares LST object names
	 */
	public static final Comparator<String> LST_OBJECT_NAME_COMPARATOR = new NumericFirstComparatorDecorator(
			new MixedStringComparator(
					"(?<numberkennzahl>[0-9]{2})?(?<letters1>[A-Za-zÄÖÜßäöü]*)(?<number2>[0-9]*)(?<point>[.]*)(?<number3>[0-9]*)(?<letters2>[A-Za-zÄÖÜßäöü]*)(?<arbitrary>.*)"));

	/**
	 * The id of the no session part.
	 */
	public static final String NO_SESSION_PART_ID = "org.eclipse.set.application.part.nosessionpart";

	/**
	 * Compares strings numerical.
	 */
	public static final MixedStringComparator NUMERIC_COMPARATOR = new MixedStringComparator(
			"(?<numberN>[0-9]+)");

	/**
	 * The id of the attachment viewer part.
	 */
	public static final String ATTACHMENT_VIEWER_PART_ID = "org.eclipse.set.application.parts.AttachmentViewerPart";

	/**
	 * The id of the project part.
	 */
	public static final String PROJECT_PART_ID = "org.eclipse.set.feature.projectdata.edit.ProjectPart";

	/**
	 * The id of the project part.
	 */
	public static final String PROJECT_IMPORTPART_ID = "org.eclipse.set.feature.projectdata.ppimport.PlanProImportPart";

	/**
	 * The id of the table key to table cache.
	 */
	public static final String SHORTCUT_TO_TABLE_CACHE_ID = "toolbox.cache.shortcut-to-table";

	/**
	 * The base directory for temporary subdirectories.
	 */
	public static final String TMP_BASE_DIR;

	/**
	 * The name for the temporary directory of a toolbox file used to be
	 * exported.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_EXPORT = "exportfile";

	/**
	 * The name for the temporary directory of a toolbox file used to be
	 * imported into the final state.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_IMPORT_FINAL_STATE = "importfinalfile";

	/**
	 * The name for the temporary directory of a toolbox file used to be
	 * imported into the initial state.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_IMPORT_INITIAL_STATE = "importinitialfile";

	/**
	 * The name for the temporary directory of a toolbox file used as a first
	 * planning to import.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_FIRST_PLANNING_TO_IMPORT = "firstimportplanningfile";

	/**
	 * The name for the temporary directory of a toolbox file used as a second
	 * planning to import.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_SECOND_PLANNING_TO_IMPORT = "secondimportplanningfile";

	/**
	 * The name for the temporary directory of a toolbox file used as a
	 * secondary planning.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_COMPARE_PLANNING = "compareplanning";

	/**
	 * The name for the temporary directory of a toolbox file used to start a
	 * new session.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_SESSION = "toolboxfile";

	/**
	 * The name for the temporary directory of a toolbox file used as a
	 * temporary integration.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_TEMPORARY_INTEGRATION = "tempintegrationfile";

	/**
	 * The id of the validation part.
	 */
	public static final String VALIDATION_PART_ID = "org.eclipse.set.feature.validation.parts.ValidationPart";

	/**
	 * The id of the plaz model part
	 */
	public static final String PLAZ_MODEL_PART_ID = "org.eclipse.set.feature.plazmodel.PlaZModelPart";

	/**
	 * The id of the validation table part.
	 */
	public static final String VALIDATION_TABLE_PART_ID = "org.eclipse.set.feature.validation.parts.ValidationTablePart";

	/**
	 * The id of the table overview part.
	 */
	public static final String TABLE_OVERVIEW_ID = "overview.TableOverviewPart";

	private static final String DEFAULT_HOME_DIR;

	/**
	 * The id of the news part
	 */
	public static final String WEB_NEWS_PART_ID = "org.eclipse.set.application.nosessionpart.WebNewsPart";

	/**
	 * The id of the about part
	 */
	public static final String ABOUT_PART_ID = "org.eclipse.set.application.about.AboutPart";

	/**
	 * The id of the chromium credits part
	 */
	public static final String CHROMIUM_CREDITS_PART_ID = "org.eclipse.set.application.about.ChromiumCreditsPart";

	/**
	 * The key for decide with view model should be renderer
	 */
	public static final String PLANING_GROUP_VIEW_DETAIL_KEY = "detail";

	/**
	 * The prefix of ESTW table part
	 */
	public static final String ESTW_TABLE_PART_ID_PREFIX = "org.eclipse.set.feature.table.estw";

	/**
	 * The prefix of ETCS table part
	 */
	public static final String ETCS_TABLE_PART_ID_PREFIX = "org.eclipse.set.feature.table.etcs";

	/**
	 * The prefix of ESTW Supplement table part
	 */
	public static final String ESTW_SUPPLEMENT_PART_ID_PREFIX = "org.eclipse.set.feature.table.supplement-estw";

	/**
	 * Rounding result of BigDecimal.divide to place after comma
	 */
	public static final int ROUNDING_TO_PLACE = 5;

	/**
	 * Labels for CompareTableCellContent cell
	 */
	public static final String TABLE_COMPARE_TABLE_CELL_LABEL = "tableCompareCell";

	/**
	 * Labels for converter search cell
	 */
	public static final String SEARCH_CELL_DISPLAY_CONVERTER = "searchCellConverter";

	/**
	 * Color of the compare table cell border
	 */
	public static final String TABLE_COMPARE_TABLE_CELL_BORDER_COLOR = "#0066FF";

	/**
	 * The id of the web developer help part
	 */
	public static String WEB_DEVELOPER_HELP_PART_ID = "org.eclipse.set.application.ppt.nosessionpart.WebDeveloperHelpPart";

	/**
	 * The name of compare project cell content
	 */
	public static final String XSL_PROJECT_COMPARE_CELL = "CompareProjectContent";

	/**
	 * The attribute name for compare row
	 */
	public static final String XSL_COMPARE_ROW_TYPE_ATTRIBUTE = "compareType";

	/**
	 * Label for cells of table row, which completely changed.
	 */
	public static final String TABLE_COMPARE_CHANGED_GUID_ROW_CELL_LABEL = "tableGuidChangeRowCell";

	/**
	 * Label for first cell of table row, which completely changed.
	 */
	public static final String TABLE_COMPARE_TABLE_ROW_FIRST_CELL_LABEL = "tableCompareTableRowFirstCell";

	/**
	 * Label for last cell of table row, which completely changed.
	 */
	public static final String TABLE_COMPARE_TABLE_ROW_LAST_CELL_LABEL = "tableCompareTableRowLastCell";

	/**
	 * Label for cell of the table row, which completely changed.
	 */
	public static final String TABLE_COMPARE_TABLE_ROW_CELL_LABEL = "tableCompareTableRowCell";

	/**
	 * Label for Topological Cell
	 */
	public static final String TABLE_TOPOLOGICAL_CELL = "topologicalCell";

	/**
	 * The tolerance value between TOP_Kante length and the sum of GEO_Kanten
	 * length, which belong to this TOP_Kante (in Meter)
	 */
	public static final double TOP_GEO_LENGTH_TOLERANCE = 0.01;
	static {
		DEFAULT_HOME_DIR = "./";
		TMP_BASE_DIR = System.getProperty(ToolboxProperties.TMP_BASE_DIR,
				DEFAULT_HOME_DIR);
	}
}
