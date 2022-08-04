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
		 * The id of the banking interval cache.
		 */
		public static final String BANKING_INTERVAL = "toolbox.cache.banking-interval";

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
		 * The id of the table error cache.
		 */
		public static final String TABLE_ERRORS = "toolbox.cache.table-errors";

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
	 * The id of the pdf viewer part.
	 */
	public static final String PDF_VIEWER_PART_ID = "org.eclipse.set.application.parts.ViewPdfPart";

	/**
	 * The id of the project part.
	 */
	public static final String PROJECT_PART_ID = "org.eclipse.set.feature.projectdata.edit.ProjectPart";

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
	 * The name for the temporary directory of a toolbox file used as a
	 * secondary planning.
	 */
	public static final String TOOLBOX_DIRECTORY_NAME_SECONDARY_PLANNING = "secondaryplanningfile";

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
	 * The id of the validation table part.
	 */
	public static final String VALIDATION_TABLE_PART_ID = "org.eclipse.set.feature.validation.parts.ValidationTablePart";

	/**
	 * The id of the table overview part.
	 */
	public static final String TABLE_OVERVIEW_PART_ID = "org.eclipse.set.feature.table.overview.TableOverviewPart";

	private static final String DEFAULT_HOME_DIR;

	static {
		DEFAULT_HOME_DIR = "./";
		TMP_BASE_DIR = System.getProperty(ToolboxProperties.TMP_BASE_DIR,
				DEFAULT_HOME_DIR);
	}
}
