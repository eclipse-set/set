/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.enumtransformation.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslation;
import org.eclipse.set.utils.enums.EnumTranslationUtils;

import com.google.common.collect.Maps;

/**
 * Transformations for enumeration translations.
 * 
 * @author Schaefer
 */
public class EnumTransformation {

	private static final int COLUMN_ALTERNATIVE_VALUE = 5;
	private static final int COLUMN_ENUM_TYPE = 1;
	private static final int COLUMN_ENUM_VALUE = 2;
	private static final int COLUMN_PRESENTATION_VALUE = 3;
	private static final int COLUMN_SORTING_VALUE = 4;
	private static final String EMPTY = "<leer>"; //$NON-NLS-1$
	private static final String ENUMS = "ENUMs"; //$NON-NLS-1$
	private static final String XS_PREFIX = "xs:"; //$NON-NLS-1$

	/**
	 * Exports the given translations to a java and a property file.
	 * 
	 * @param translations
	 *            the translations to export
	 * 
	 * @return the translation code
	 */
	public static TranslationCode transform(
			final Map<String, EnumTranslation> translations) {
		final TranslationCode translationCode = new TranslationCode();
		translations.keySet().stream()
				.sorted((a, b) -> a.compareToIgnoreCase(b))
				.forEach(key -> translationCode.add(translations.get(key)));
		return translationCode;
	}

	/**
	 * @param path
	 *            the path to the enumeration definition file
	 * 
	 * @return the translations
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static Map<String, EnumTranslation> transform(final Path path)
			throws IOException {
		try (FileInputStream input = new FileInputStream(path.toFile());
				HSSFWorkbook document = new HSSFWorkbook(input)) {
			return transform(document);
		}
	}

	private static String transform(final double value) {
		return transformToValue(Long.toString(Math.round(value)));
	}

	private static String transform(final HSSFCell cell) {
		if (cell == null) {
			return null;
		}
		final CellType cellType = cell.getCellType();
		switch (cellType) {
		case BLANK:
			return null;
		case STRING:
			return transformToValue(cell.getStringCellValue());
		case NUMERIC:
			return transform(cell.getNumericCellValue());
		default:
			throw new IllegalArgumentException(cellType.toString());
		}
	}

	private static EnumTranslation transform(final HSSFRow row) {
		return new EnumTranslationImpl(transformToKeyBasis(row),
				transformToPresentation(row), transformToAlternative(row),
				transformToSorting(row));
	}

	private static Map<String, EnumTranslation> transform(
			final HSSFWorkbook document) {
		final Map<String, EnumTranslation> result = Maps.newHashMap();
		final HSSFSheet sheet = document.getSheet(ENUMS);
		for (int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet
				.getLastRowNum(); rowIndex++) {
			final HSSFRow row = sheet.getRow(rowIndex);
			try {
				final EnumTranslation translation = transform(row);
				result.put(translation.getKeyBasis(), translation);
			} catch (final Exception e) {
				throw new EnumTransformationException(e, row);
			}
		}
		return result;
	}

	private static String transformToValue(final String value) {
		String result = value;
		result = result.replaceAll(EMPTY + "|" + XS_PREFIX, ""); //$NON-NLS-1$ //$NON-NLS-2$
		return result;
	}

	static String transformToAlternative(final HSSFRow row) {
		final String alternative = transform(
				row.getCell(COLUMN_ALTERNATIVE_VALUE));
		if (alternative != null) {
			return alternative;
		}
		return transformToPresentation(row);
	}

	static String transformToKeyBasis(final HSSFRow row) {
		final String enumType = transform(row.getCell(COLUMN_ENUM_TYPE));
		final String enumValue = transform(row.getCell(COLUMN_ENUM_VALUE));
		return EnumTranslationUtils.getKeyBasis(enumType, enumValue);
	}

	static String transformToPresentation(final HSSFRow row) {
		return transform(row.getCell(COLUMN_PRESENTATION_VALUE));
	}

	static String transformToSorting(final HSSFRow row) {
		return transform(row.getCell(COLUMN_SORTING_VALUE));
	}
}
