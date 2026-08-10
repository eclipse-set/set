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
package org.eclipse.set.application.graph;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.set.core.services.graph.BankService.BankingInformation;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehungslinie;

/**
 * Helper class for find BankingInformation
 */
public class BankingInformationSession {
	private final Map<TOP_Kante, Set<BankingInformation>> topEdgeBanking;
	private final Map<Ueberhoehungslinie, Optional<BankingInformation>> bankingInformations;

	/**
	 * Constructor
	 */
	public BankingInformationSession() {
		this.topEdgeBanking = new ConcurrentHashMap<>();
		this.bankingInformations = new ConcurrentHashMap<>();
	}

	public Map<TOP_Kante, Set<BankingInformation>> getTopEdgeBanking() {
		return topEdgeBanking;
	}

	public Map<Ueberhoehungslinie, Optional<BankingInformation>> getBankingInformations() {
		return bankingInformations;
	}
}
