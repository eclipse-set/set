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
import java.util.Optional;

import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehungslinie;

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

	/**
	 * @param line
	 *            the bank line
	 * @param path
	 *            the top path between the two points
	 */
	record BankingInformation(Ueberhoehungslinie line, TopPath path) {
		public boolean isOnBankingLine(final TopPoint point) {
			final BigDecimal pointDistance = point.distance();
			if (path == null) {
				final BigDecimal ueLeft = line.getIDUeberhoehungA()
						.getPunktObjektTOPKante().get(0).getAbstand().getWert();
				final BigDecimal ueRight = line.getIDUeberhoehungB()
						.getPunktObjektTOPKante().get(0).getAbstand().getWert();
				final BigDecimal min = ueLeft.min(ueRight);
				final BigDecimal max = ueLeft.max(ueRight);

				return min.compareTo(pointDistance) <= 0
						&& max.compareTo(pointDistance) >= 0;
			}

			final Optional<BigDecimal> pDistance = path.getDistance(point);
			if (!pDistance.isPresent()) {
				return false;
			}

			// Validate bank line start is before the point object on the
			// first edge, if the point is on the first edge
			final Optional<BigDecimal> a = path
					.getDistance(new TopPoint(line.getIDUeberhoehungA()));
			final Optional<BigDecimal> b = path
					.getDistance(new TopPoint(line.getIDUeberhoehungB()));

			if (!a.isPresent() || !b.isPresent()) {
				return false;
			}
			final BigDecimal min = a.get().min(b.get());
			final BigDecimal max = a.get().max(b.get());

			return min.compareTo(pDistance.get()) <= 0
					&& max.compareTo(pDistance.get()) >= 0;

		}
	}

	/**
	 * @param point
	 *            a point to look up
	 * @return all relevant bank informations for the point
	 */
	List<BankingInformation> findRelevantLineBankings(TopPoint point);

	/**
	 * @param bankingLine
	 *            the banking line
	 * @return a banking information for the banking line, which includes the
	 *         top path
	 */
	BankingInformation findTOPBanking(Ueberhoehungslinie bankingLine);

	/**
	 * @return true, if find banking handle is complete
	 */
	boolean isFindBankingComplete();
}
