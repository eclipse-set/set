/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.table;

import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.utils.viewgroups.SetViewGroups;

/**
 * @param category
 *            the table area (ESTW or ETCS)
 * @param shortcut
 *            the table shortcut
 */
public record TableInfo(Pt1TableCategory category, String shortcut) {
	/**
	 * The table category enum
	 */
	public enum Pt1TableCategory {
		/**
		 * ESTW table
		 */
		ESTW(ToolboxConstants.ESTW_CATEGORY),
		/**
		 * ETCS table
		 */
		ETCS(ToolboxConstants.ETCS_CATEGORY),
		/**
		 * ESTW supplement table
		 */
		ESTW_SUPPLEMENT(ToolboxConstants.ESTW_SUPPLEMENT_CATEGORY);

		private final String id;

		private Pt1TableCategory(final String id) {
			this.id = id;
		}

		/**
		 * @return category id
		 */
		public String getId() {
			return id;
		}

		@Override
		public String toString() {
			return switch (this) {
				case ESTW -> SetViewGroups.getTable_ESTW().text();
				case ETCS -> SetViewGroups.getTable_ETCS().text();
				case ESTW_SUPPLEMENT -> SetViewGroups.getTable_ESTW_Supplement()
						.text();
			};
		}
	}

	/**
	 * @param categoryId
	 *            the category id
	 * @param shortcut
	 *            the table shortcut
	 */
	public TableInfo(final String categoryId, final String shortcut) {
		this(switch (categoryId) {
			case ToolboxConstants.ESTW_CATEGORY -> Pt1TableCategory.ESTW;
			case ToolboxConstants.ETCS_CATEGORY -> Pt1TableCategory.ETCS;
			case ToolboxConstants.ESTW_SUPPLEMENT_CATEGORY -> Pt1TableCategory.ESTW_SUPPLEMENT;
			default -> null;
		}, shortcut);
	}
}