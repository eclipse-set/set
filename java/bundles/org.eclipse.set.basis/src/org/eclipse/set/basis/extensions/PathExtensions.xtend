/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.set.basis.exceptions.NotReadable
import org.eclipse.set.basis.exceptions.NotWritable
import java.nio.file.Path
import java.nio.file.Paths
import java.util.List

/**
 * Extensions for {@link Path}.
 * 
 * @author Schaefer
 */
class PathExtensions {
	
	/**
	 * image file extensions
	 */
	public static val List<String> IMAGE_FILE_EXTENSIONS = #["jpg", "gif", "png"]
	
	/**
	 * csv file extensions
	 */
	public static val List<String> CSV_FILE_EXTENSIONS = #["csv"]
	
	/*
	 * Dokument file extensions
	 */
	public static val List<String> DOKUMENT_FILE_EXTENSIONS = #["pdf"]
	
	/*
	 * Excel file extensions
	 */
	public static val List<String> EXCEL_FILE_EXTENSION = #["xlsx"]
	
	/*
	 * all file extensions
	 */
	public static val List<String> ALL_FILE_EXTENSION = #["*"]

	/**
	 * @param path this path
	 * @param separator the separator
	 * 
	 * @return the string representation of this path using the given separator
	 */
	static def String toString(Path path, String separator) {
		return '''«path.root(separator)»«FOR segment : path SEPARATOR separator»«segment.toString»«ENDFOR»'''
	}

	static def String rootWithoutSeparator(Path path) {
		val root = path.root
		if (root === null) {
			return null
		}
		val rootString = root.toString
		return rootString.substring(0, rootString.length - 1)
	}

	static def String root(Path path, String separator) {
		val r = path.rootWithoutSeparator
		if (r === null) {
			return ""
		}
		return '''«r»«separator»'''
	}

	static def String getBaseFileName(Path path) {
		return path.fileName.toString.replaceFirst("\\.[^.]*$", "")
	}

	/**
	 * @param path this path
	 * 
	 * @return the extension of the path's filename
	 */
	static def String getExtension(Path path) {
		val parts = path.fileName.toString.split("\\.")
		if (parts.size > 1) {
			return parts.last
		}
		return ""
	}

	/**
	 * Returns a new path with the new file extension.
	 * 
	 * @param path this path
	 * @param fileExtension the new file extension
	 * 
	 * @return a new path with the new file extension
	 */
	static def Path replaceExtension(Path path, String fileExtension) {
		return Paths.get(
			path.parent?.toString ?: "", '''«path.baseFileName».«fileExtension»''');
	}

	/**
	 * @param path this path
	 * 
	 * @throws NotReadable if the file at the given path is not readable
	 */
	static def void checkCanRead(Path path) throws NotReadable {
		val file = path.toFile
		if (!file.canRead) {
			throw new NotReadable(file)
		}
	}

	/**
	 * @param path this path
	 * 
	 * @throws NotWritable if the file at the given path is not writable
	 */
	static def void checkCanWrite(Path path) throws NotWritable {
		val file = path.toFile
		if (file.exists && !file.canWrite) {
			throw new NotWritable(path)
		}
	}
}
