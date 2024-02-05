/********************************************************************************
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 
 * 
 ********************************************************************************/
package org.eclipse.set.core.services.graph;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.set.basis.graph.TopPoint;

/**
 * Helper service to calculate bank values for points
 */
public interface BankService {
	/**
	 * @param point
	 *            the point
	 * @return a list of bank values for the point
	 */
	List<BigDecimal> findBankValue(TopPoint point);
}
