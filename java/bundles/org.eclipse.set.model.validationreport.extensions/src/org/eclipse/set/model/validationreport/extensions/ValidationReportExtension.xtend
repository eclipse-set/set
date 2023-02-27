/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.extensions

import org.eclipse.set.model.validationreport.ValidationReport
import org.eclipse.set.utils.ToolboxConfiguration
import java.io.IOException
import java.io.Writer
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.eclipse.xtend.lib.annotations.Accessors

import static org.eclipse.set.basis.extensions.ConsumerThrowingExceptionExtension.*

import static extension org.eclipse.set.model.validationreport.extensions.ValidationProblemExtensions.*

/**
 * Extensions for {@link ValidationReport}.
 * 
 * @author Schaefer
 */
class ValidationReportExtension {

	static val HEADER_PATTERN = '''Validierungsmeldungen
Datei: %s
Validierung: %s
Werkzeugkofferversion: %s

"Lfd. Nr.";"Schweregrad";"Problemart";"Zeilennummer";"Objektart";"Attribut/-gruppe";"Bereich";"Meldung"
'''

	private static class HeaderInfo {
		new(ValidationReport report) {
			file = Paths.get(report.fileInfo.fileName).fileName.toString
			time = LocalDateTime.now.format(
				DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"))
			version = ToolboxConfiguration.toolboxVersion.longVersion
		}

		@Accessors
		String file

		@Accessors
		String time

		@Accessors
		String version
	}

	/**
	 * Export the problems of the given report as CSV.
	 * 
	 * @param report the validation report
	 * @param writer the writer
	 */
	static def void problemCsvExport(ValidationReport report, Writer writer) {
		val headerInfo = new HeaderInfo(report)
		try {
			writer.write(
				String.format(HEADER_PATTERN, headerInfo.file, headerInfo.time,
					headerInfo.version))
		} catch (IOException e) {
			throw new RuntimeException(e)
		}
		report.problems.forEach [
			rethrowException([p|writer.write(p.csvExport)]).accept(it)
		]
	}
}
