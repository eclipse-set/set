/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.validation.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.xml.type.internal.XMLCalendar;
import org.eclipse.set.basis.PlanProXMLNode;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.model.validationreport.ContainerContent;
import org.eclipse.set.model.validationreport.FileInfo;
import org.eclipse.set.model.validationreport.ValidationreportFactory;
import org.eclipse.set.utils.xml.XMLNodeFinder;

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

	private static final String MODEL_XML_NAME = "content.xml"; //$NON-NLS-1$
	private static final String LAYOUT_XML_NAME = "layout.xml"; //$NON-NLS-1$
	private static final String ATTACHMENT_XML_NAME = "manifest.xml"; //$NON-NLS-1$

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
		nodeFinder.read(toolboxFile, toolboxFile.getModelPath());
	}

	/**
	 * @return gereral file information
	 */
	public FileInfo getFileInfo() {
		final FileInfo fileInfo = ValidationreportFactory.eINSTANCE
				.createFileInfo();
		fileInfo.setFileName(toolboxFile.getPath().toString());
		fileInfo.setUsedVersion(
				versionService.createUsedVersion(toolboxFile.getModelPath()));
		fileInfo.setTimeStamp(getFileTimeStamp());
		fileInfo.setGuid(getFileGuid());
		fileInfo.setChecksum(toolboxFile.getChecksum());
		final String fileContents = getContainerContents().stream()
				.map(ContainerContent::getLiteral)
				.collect(Collectors.joining(", ")); //$NON-NLS-1$
		fileInfo.setContainerContents(fileContents);
		return fileInfo;
	}

	private String getFileTimeStamp() {
		final PlanProXMLNode timeStampValueNode = getValueNode(
				FILE_TIME_STAMP_NODE_NAME);
		if (timeStampValueNode != null) {
			final XMLCalendar xmlCalendar = new XMLCalendar(
					timeStampValueNode.getTextValue(), XMLCalendar.DATETIME);
			return String.format("%1$td.%1$tm.%1$tY %1$tT", //$NON-NLS-1$
					xmlCalendar.toGregorianCalendar());
		}
		return null;
	}

	private String getFileGuid() {
		final PlanProXMLNode guidValueNode = getValueNode(FILE_GUID_NODE_NAME);
		if (guidValueNode != null) {
			return guidValueNode.getTextValue();
		}
		return null;

	}

	private PlanProXMLNode getValueNode(final String nodeName) {
		final PlanProXMLNode node = nodeFinder
				.findFirstNodeByNodeName(nodeName);
		final PlanProXMLNode valueNode = nodeFinder
				.findFirstNodeByNodeName(node, VALUE_NODE_NAME);
		if (valueNode != null) {

			return valueNode;
		}
		return null;
	}

	private List<ContainerContent> getContainerContents() {
		if (this.toolboxFile.getFormat().isPlain()) {
			return List.of(ContainerContent.MODEL);
		}
		final File modelDir = this.toolboxFile.getModelPath()
				.getParent()
				.toFile();
		if (!modelDir.isDirectory()) {
			return Collections.emptyList();
		}
		final List<ContainerContent> fileContents = new ArrayList<>();
		Arrays.asList(modelDir.listFiles()).forEach(file -> {
			if (!file.isDirectory()) {
				final String fileName = file.getName();
				if (fileName.equals(MODEL_XML_NAME)) {
					fileContents.add(ContainerContent.MODEL);
				} else if (fileName.equals(LAYOUT_XML_NAME)) {
					fileContents.add(ContainerContent.LAYOUT);
				} else if (fileName.equals(ATTACHMENT_XML_NAME)) {
					fileContents.add(ContainerContent.ATTACHMENT);
				}
			}
		});
		return fileContents;
	}

}
