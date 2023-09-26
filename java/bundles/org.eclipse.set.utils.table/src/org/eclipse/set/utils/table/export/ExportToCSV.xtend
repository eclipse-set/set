package org.eclipse.set.utils.table.export

import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.List
import java.util.Optional
import java.util.function.Function
import org.eclipse.set.utils.ToolboxConfiguration
import org.eclipse.xtend.lib.annotations.Accessors

import static org.eclipse.set.basis.extensions.ConsumerThrowingExceptionExtension.*

class ExportToCSV<T> {
	String headerPattern
	new(String headerPattern) {
		this.headerPattern = headerPattern
	}

	private static class HeaderInfo {
		@Accessors
		String file

		@Accessors
		String time

		@Accessors
		String version

		new(String fileName) {
			file = Paths.get(fileName).fileName.toString
			time = LocalDateTime.now.format(
				DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"))
			version = ToolboxConfiguration.toolboxVersion.longVersion
		}
	}

	def void exportToCSV(Optional<Path> filePath,
		List<T> dataToExport, Function<T, String> transformDataToString) {
		if (filePath.isPresent) {
			val fileName = filePath.get.toString
			dataToExport.exportToCSV(fileName, transformDataToString)
		}
	}

	def void exportToCSV(List<T> dataToExport, String fileName,
		Function<T, String> transformDataToString) {
		val headerInfo = new HeaderInfo(fileName)
		try (val writer = new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)) {
			writer.write(
				String.format(headerPattern, headerInfo.file, headerInfo.time,
					headerInfo.version))
			dataToExport.forEach [
				rethrowException([p|writer.write(transformDataToString.apply(p))]).
					accept(it)
			]
		} catch (IOException e) {
			throw new RuntimeException(e)
		}

	}
}
