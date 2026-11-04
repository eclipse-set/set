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
package org.eclipse.set.feature.plazmodel.test;

import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.set.osgi.test.utils.AbstractOsgiTableTest;
import org.eclipse.set.osgi.test.utils.OsgiTestTestFile;
import org.eclipse.set.osgi.test.utils.TestFile;
import org.osgi.framework.BundleContext;

/**
 * 
 */
public abstract class AbstractPlaZModelTest extends AbstractOsgiTableTest {
	private static final String REF_DIR = "resource/";

	protected abstract BundleContext getBundleContext();

	@Override
	protected int getExpectedColumnCount() {
		return referenceData.get(0).size();
	}

	@Override
	protected String getReferenceDir() {
		return REF_DIR + "table_reference/" + getTestFile().getShortName()
				+ "/";
	}

	@Override
	protected TestFile getTestFile() {
		return OsgiTestTestFile.PPHN_1_10_0_3;
	}

	protected void givenReferenceData() {
		referenceData = loadReferenceFile(getTestTableReferenceName());
	}

	@Override
	protected List<CSVRecord> loadReferenceFile(final String tableName) {
		final List<CSVRecord> records = super.loadReferenceFile(tableName);
		final Optional<CSVRecord> csvHeader = records.stream()
				.filter(r -> r.get(0).equals(ROW_INDEX_COL))
				.findFirst();
		if (csvHeader.isEmpty()) {
			throw new IllegalArgumentException("Wrong csv reference format");
		}

		// Remove meta data line
		return records.subList(records.indexOf(csvHeader.get()) + 1,
				records.size());
	}

	@Override
	protected boolean skipCompareCountColumn() {
		return false;
	}
}
