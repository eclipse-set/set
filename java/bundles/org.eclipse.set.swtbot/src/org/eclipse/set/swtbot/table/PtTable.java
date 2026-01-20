/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.table;

import java.util.List;

/**
 * @author truong
 * @param shortcut
 *            table shortcut
 * @param tableName
 *            the table name
 * @param category
 *            the table category
 * @param fixedColumns
 *            index of fixed column
 *
 */
public record PtTable(String shortcut, String tableName, String category,
		List<Integer> fixedColumns) {
	/**
	 * List table to test
	 */
	@SuppressWarnings("boxing")
	public static List<PtTable> tablesToTest = List.of(
			new PtTable("ssbb", "Ssbb – Bedieneinrichtungstabelle BÜ", "estw",
					List.of(0)),
			new PtTable("ssit", "Ssit – Bedieneinrichtungstabelle Stw", "estw",
					List.of(0)),
			new PtTable("sska", "Sska – Elementansteuertabelle", "estw",
					List.of(0)),
			new PtTable("sskf", "Sskf – Freimeldetabelle", "estw", List.of(0)),
			new PtTable("sskg", "Sskg – Gleisschaltmitteltabelle", "estw",
					List.of(0)),
			new PtTable("ssko", "Ssko – Schlosstabelle", "estw", List.of(0)),
			new PtTable("ssks", "Ssks – Signaltabelle", "estw", List.of(0)),
			new PtTable("sskt",
					"Sskt – Tabelle der Technik- und Bedienstandorte", "estw",
					List.of(0)),
			new PtTable("sskw", "Sskw – Weichentabelle", "estw", List.of(0)),
			new PtTable("ssla",
					"Ssla – Tabelle der aneinandergereihten Fahrstraßen",
					"estw", List.of(0)),
			new PtTable("sslb", "Sslb – Streckenblocktabelle", "estw",
					List.of(0)),
			new PtTable("ssld", "Ssld – Durchrutschweg- und Gefahrpunkttabelle",
					"estw", List.of(0, 1, 2)),
			new PtTable("sslf", "Sslf – Flankenschutztabelle", "estw",
					List.of(0, 1)),
			new PtTable("ssli", "Ssli – Inselgleistabelle", "estw", List.of(0)),
			new PtTable("ssln", "Ssln – Nahbedienungstabelle", "estw",
					List.of(0)),
			new PtTable("sslr", "Sslr – Rangierstraßentabelle", "estw",
					List.of(0)),
			new PtTable("sslw", "Sslw – Zwieschutzweichentabelle", "estw",
					List.of(0)),
			new PtTable("sslz", "Sslz – Zugstraßentabelle", "estw", List.of(0)),
			new PtTable("ssvu", "Ssvu – Übertragungswegtabelle", "estw",
					List.of(0, 1)),
			new PtTable("sskp", "Sskp – PZB-Tabelle", "estw", List.of(0, 1)),
			new PtTable("sskp_dm", "Sskp_dm – PZB-Tabelle", "supplement-estw",
					List.of(0, 1)),
			new PtTable("ssza", "Ssza – ETCS-Datenpunkttabelle", "etcs",
					List.of(0)),
			new PtTable("sszs",
					"Sszs – ETCS Melde- und Kommandoanschaltung Signale",
					"etcs", List.of(0)),
			new PtTable("sszw",
					"Sszw – ETCS Melde- und Kommandoanschaltung Weichen",
					"etcs", List.of(0)),
			new PtTable("sskz", "Sskz – Zuordnungstabelle FEAK/FEAS",
					"supplement-estw", List.of(0)));

}
