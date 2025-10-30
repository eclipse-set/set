/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.xml;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.set.basis.PlanProXMLNode;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.model.validationreport.ObjectState;
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
	private final NodeObjectTypeProvider validationObjectTypeProvider;
	private final NodeObjectScopeProvider validationObjectScopeProvider;
	private final NodeObjectStateProvider validationObjectStateProvider;
	private final NodeAttributeNameProvider validationAttributeNameProvider;

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
	public static PlanProXMLNode read(final ToolboxFile toolboxFile,
			final Path docPath)
			throws IOException, SAXException, ParserConfigurationException {
		if (docPath == null) {
			return null;
		}
		final String docName = PathExtensions.getBaseFileName(docPath);
		PlanProXMLNode document = toolboxFile.getXMLDocument(docName);
		if (document == null) {
			final ObjectMetadataXMLReader reader = new ObjectMetadataXMLReader(
					docPath);
			document = reader.read();
			toolboxFile.setXMLDocument(PathExtensions.getBaseFileName(docPath),
					document);
		}
		return document;
	}

	private PlanProXMLNode read()
			throws IOException, SAXException, ParserConfigurationException {
		final PlanProXMLNode document = LineNumberXMLReader.read(documentPath);
		document.addAdditionsObject(METADATA_READER_KEY, this);
		return document;
	}

	private ObjectMetadataXMLReader(final Path path) {
		documentPath = path;
		validationObjectScopeProvider = new NodeObjectScopeProvider();
		validationObjectStateProvider = new NodeObjectStateProvider();
		validationObjectTypeProvider = new NodeObjectTypeProvider();
		validationAttributeNameProvider = new NodeAttributeNameProvider();
	}

	/**
	 * Returns the scope of an object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the scope of the object
	 */
	public static ObjectScope getObjectScope(final PlanProXMLNode node) {
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
	public static String getObjectScopeLiteral(final PlanProXMLNode node) {
		return getObjectScope(node).getLiteral();
	}

	/**
	 * Returns the type of an object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the type of the object
	 */
	public static String getObjectType(final PlanProXMLNode node) {
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
	public static ObjectState getObjectState(final PlanProXMLNode node) {
		final ObjectMetadataXMLReader metadata = getMetadataReader(node);
		return metadata.validationObjectStateProvider.getObjectState(node);
	}

	/**
	 * Returns the attribute name of an object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the type of the object
	 */
	public static String getAttributeName(final PlanProXMLNode node) {
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
	private static ObjectMetadataXMLReader getMetadataReader(
			final PlanProXMLNode node) {
		return (ObjectMetadataXMLReader) node
				.getAdditionsObject(METADATA_READER_KEY);
	}
}
