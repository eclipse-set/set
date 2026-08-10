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
package org.eclipse.set.utils.table.sorting;

import java.util.Comparator;

/**
 * Abstract class for criterion, which dependency on other process/service
 * 
 * @param <T>
 *            the comparable type
 * 
 */
public abstract class AbstractCompareWithDependencyOnServiceCriterion<T>
		implements Comparator<T> {

	/**
	 * @return the event topic, which trigger comparison
	 */
	public abstract String getTriggerComparisonEventTopic();
}
