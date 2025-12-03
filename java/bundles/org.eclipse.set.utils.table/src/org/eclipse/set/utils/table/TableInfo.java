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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.set.utils.viewgroups.SetViewGroups;

/**
 * @param category
 *            the table area (ESTW or ETCS)
 * @param shortcut
 *            the table shortcut
 * @param isDevMode
 *            should the table only in development mode available
 */
public record TableInfo(Pt1TableCategory category, String shortcut,
		boolean isDevMode) {
	/**
	 * The table category enum
	 */
	public enum Pt1TableCategory {
		/**
		 * ESTW table
		 */
		ESTW("estw"), //$NON-NLS-1$
		/**
		 * ETCS table
		 */
		ETCS("etcs"), //$NON-NLS-1$
		/**
		 * ESTW supplement table
		 */
		ESTW_SUPPLEMENT("supplement-estw"); //$NON-NLS-1$

		private static final Map<String, Pt1TableCategory> categories = new HashMap<>();
		static {
			for (final Pt1TableCategory category : values()) {
				categories.put(category.getId(), category);
			}
		}

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

		/**
		 * Get Pt1Category enumerator from category id
		 * 
		 * @param categoryId
		 *            the category id
		 * @return the category enumerator
		 */
		public static Pt1TableCategory getCategoryEnum(
				final String categoryId) {
			return categories.get(categoryId);
		}
	}

	/**
	 * @param categoryId
	 *            the category id
	 * @param shortcut
	 *            the table shortcut
	 * @param isDevMode
	 *            should the table only in development mode available
	 */
	public TableInfo(final String categoryId, final String shortcut,
			final boolean isDevMode) {
		this(Pt1TableCategory.getCategoryEnum(categoryId), shortcut, isDevMode);
	}
}