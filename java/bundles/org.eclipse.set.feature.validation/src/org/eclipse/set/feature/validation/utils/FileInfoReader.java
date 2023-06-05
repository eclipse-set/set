/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.validation.utils;

import java.nio.file.Path;

import org.eclipse.emf.ecore.xml.type.internal.XMLCalendar;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.model.validationreport.FileInfo;
import org.eclipse.set.model.validationreport.ValidationreportFactory;
import org.w3c.dom.Node;

/**
 * @author Truong
 *
 */
public class FileInfoReader {

	PlanProVersionService versionService;
	ToolboxFile toolboxFile;

	XMLNodeFinder nodeFinder;

	private static final String FILE_TIME_STAMP_NODE_NAME = "Erzeugung_Zeitstempel"; //$NON-NLS-1$
	private static final String FILE_GUID_NODE_NAME = "Identitaet"; //$NON-NLS-1$
	private static final String VALUE_NODE_NAME = "Wert"; //$NON-NLS-1$

	/**
	 * @param versionService
	 *            {@link PlanProVersionService}
	 * @param toolboxFile
	 *            {@link ToolboxFile}
	 */
	public FileInfoReader(final PlanProVersionService versionService,
			final ToolboxFile toolboxFile) {
		this.versionService = versionService;
		this.toolboxFile = toolboxFile;
		this.nodeFinder = new XMLNodeFinder();
		nodeFinder.read(toolboxFile);
	}

	/**
	 * @return gereral file information
	 */
	public FileInfo getFileInfo() {
		final FileInfo fileInfo = ValidationreportFactory.eINSTANCE
				.createFileInfo();
		final Path filePath = toolboxFile.getPath();
		fileInfo.setFileName(filePath.toString());
		fileInfo.setUsedVersion(versionService.createUsedVersion(filePath));
		fileInfo.setTimeStamp(getFileTimeStamp());
		fileInfo.setGuid(getFileGuid());
		fileInfo.setChecksum(toolboxFile.getChecksum());
		return fileInfo;
	}

	private String getFileTimeStamp() {
		final Node timeStampValueNode = getValueNode(FILE_TIME_STAMP_NODE_NAME);
		if (timeStampValueNode != null) {
			final XMLCalendar xmlCalendar = new XMLCalendar(
					timeStampValueNode.getTextContent(), XMLCalendar.DATETIME);
			return String.format("%1$td.%1$tm.%1$tY %1$tT", //$NON-NLS-1$
					xmlCalendar.toGregorianCalendar());
		}
		return null;
	}

	private String getFileGuid() {
		final Node guidValueNode = getValueNode(FILE_GUID_NODE_NAME);
		if (guidValueNode != null) {
			return guidValueNode.getTextContent();
		}
		return null;

	}

	private Node getValueNode(final String nodeName) {
		final Node node = nodeFinder.findFirstNodeByNodeName(nodeName);
		final Node valueNode = nodeFinder.findFirstNodeByNodeName(node,
				VALUE_NODE_NAME);
		if (valueNode != null) {

			return valueNode;
		}
		return null;
	}

}
