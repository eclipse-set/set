/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.export;

import static org.eclipse.set.basis.extensions.ConsumerThrowingExceptionExtension.rethrowException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.set.basis.ConsumerThrowingException;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.xtend.lib.annotations.Accessors;

/**
 * Export to csv file
 * 
 * @author truong
 * @param <T>
 *            type of data to exprot
 *
 */
public class ExportToCSV<T> {
	// UTF-8 BOM byte sequence
	byte[] utf8Bom = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };

	private static class HeaderInfo {
		@Accessors
		private final String file;
		@Accessors
		private final String time;
		@Accessors
		private final String version;

		public HeaderInfo(final String filePath) {
			this.file = Paths.get(filePath).getFileName().toString();
			this.time = LocalDateTime.now()
					.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")); //$NON-NLS-1$
			this.version = ToolboxConfiguration.getToolboxVersion()
					.getLongVersion();
		}
	}

	String headerPatter;

	/**
	 * Constructor
	 * 
	 * @param headerPattern
	 *            header pattern of csv
	 */
	public ExportToCSV(final String headerPattern) {
		this.headerPatter = headerPattern;
	}

	/**
	 * Export data list to csv file
	 * 
	 * @param filePath
	 *            the output file path
	 * @param dataToExport
	 *            the data
	 * @param transformDataToString
	 *            the function to transform data to string
	 */
	public void exportToCSV(final Optional<Path> filePath,
			final List<T> dataToExport,
			final Function<T, String> transformDataToString) {
		if (filePath.isPresent()) {
			final HeaderInfo headerInfo = new HeaderInfo(
					filePath.get().toString());
			try (final FileOutputStream fos = new FileOutputStream(
					filePath.get().toString());
					final OutputStreamWriter writer = new OutputStreamWriter(
							fos, StandardCharsets.UTF_8)) {
				// Write the BOM to the beginning of the file to Excel detect
				// special character
				fos.write(utf8Bom);
				writer.write(String.format(headerPatter, headerInfo.file,
						headerInfo.time, headerInfo.version));

				final ConsumerThrowingException<T> consumerException = (
						final T t) -> writer
								.write(transformDataToString.apply(t));
				dataToExport
						.forEach(rethrowException(consumerException)::accept);

			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * @param filePath
	 *            the output file path
	 * @param dataToExport
	 *            the data
	 */
	public void exportToCSV(final Optional<Path> filePath,
			final List<String> dataToExport) {
		if (filePath.isPresent()) {
			final HeaderInfo headerInfo = new HeaderInfo(
					filePath.get().toString());
			try (final FileOutputStream fos = new FileOutputStream(
					filePath.get().toString());
					OutputStreamWriter writer = new OutputStreamWriter(fos,
							StandardCharsets.UTF_8)) {
				// Write the BOM to the beginning of the file to Excel detect
				// special character
				fos.write(utf8Bom);
				writer.write(String.format(headerPatter, headerInfo.file,
						headerInfo.time, headerInfo.version));
				final ConsumerThrowingException<String> consumerException = writer::write;
				dataToExport
						.forEach(rethrowException(consumerException)::accept);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
