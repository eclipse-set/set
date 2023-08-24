/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.utils;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.utils.xml.LineNumberXMLReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * XML Reader which offers additional metadata for PlanPro XML files
 * 
 * @author Stuecker
 *
 */
public class ObjectMetadataXMLReader {
	private static final String METADATA_READER_KEY = "metadataReader"; //$NON-NLS-1$

	private final Path documentPath;
	private final ValidationObjectTypeProvider validationObjectTypeProvider;
	private final ValidationObjectScopeProvider validationObjectScopeProvider;
	private final ValidationObjectStateProvider validationObjectStateProvider;
	private final ValidationAttributeNameProvider validationAttributeNameProvider;

	/**
	 * Reads a XML Document with additional metadata
	 * 
	 * @param toolboxFile
	 *            the ToolboxFile
	 * @param docPath
	 *            the path of document
	 * @return The document
	 * @throws ParserConfigurationException
	 *             if the underlying LineNumberXMLReader throws
	 * @throws SAXException
	 *             if the underlying LineNumberXMLReader throws
	 * @throws IOException
	 *             if the underlying LineNumberXMLReader throws
	 */
	public static Document read(final ToolboxFile toolboxFile,
			final Path docPath)
			throws IOException, SAXException, ParserConfigurationException {
		if (docPath == null) {
			return null;
		}
		final String docName = PathExtensions.getBaseFileName(docPath);
		Document document = toolboxFile.getXMLDocument(docName);
		if (document == null) {
			final ObjectMetadataXMLReader reader = new ObjectMetadataXMLReader(
					docPath);
			document = reader.read();
			toolboxFile.setXMLDocument(PathExtensions.getBaseFileName(docPath),
					document);
		}
		return document;
	}

	private Document read()
			throws IOException, SAXException, ParserConfigurationException {
		final Document document = LineNumberXMLReader.read(documentPath);
		document.setUserData(METADATA_READER_KEY, this, null);
		return document;
	}

	private ObjectMetadataXMLReader(final Path path) {
		documentPath = path;
		validationObjectScopeProvider = new ValidationObjectScopeProvider();
		validationObjectStateProvider = new ValidationObjectStateProvider();
		validationObjectTypeProvider = new ValidationObjectTypeProvider();
		validationAttributeNameProvider = new ValidationAttributeNameProvider();
	}

	/**
	 * Returns the scope of an object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the scope of the object
	 */
	public static ObjectScope getObjectScope(final Node node) {
		final ObjectMetadataXMLReader metadata = getMetadataReader(node);
		return metadata.validationObjectScopeProvider.getObjectScope(node);
	}

	/**
	 * Returns the scope of an object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the scope of the object
	 */
	public static String getObjectScopeLiteral(final Node node) {
		return getObjectScope(node).getLiteral();
	}

	/**
	 * Returns the type of an object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the type of the object
	 */
	public static String getObjectType(final Node node) {
		final ObjectMetadataXMLReader metadata = getMetadataReader(node);
		return metadata.validationObjectTypeProvider.getObjectType(node);
	}

	/**
	 * Returns the containing state of an object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the state of the object or null
	 */
	public static String getObjectState(final Node node) {
		final ObjectMetadataXMLReader metadata = getMetadataReader(node);
		return metadata.validationObjectStateProvider.getObjectState(node);
	}

	/**
	 * Returns the attribute name of a object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the type of the object
	 */
	public static String getAttributeName(final Node node) {
		final ObjectMetadataXMLReader metadata = getMetadataReader(node);
		return metadata.validationAttributeNameProvider.getAttributeName(node);
	}

	/**
	 * Returns the ObjectMetadataXMLReader for a XML Node.
	 * 
	 * @param node
	 *            the XML node
	 * @return the ObjectMetadataXMLReader or null if the document was not
	 *         opened using a ObjectMetadataXMLReader
	 */
	private static ObjectMetadataXMLReader getMetadataReader(final Node node) {
		return (ObjectMetadataXMLReader) node.getOwnerDocument()
				.getUserData(METADATA_READER_KEY);
	}
}
