/** 
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice

import com.google.common.collect.Lists
import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang
import org.eclipse.set.toolboxmodel.Basisobjekte.ENUMDateityp
import java.nio.charset.StandardCharsets
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.set.model.zipmanifest.Content
import org.eclipse.set.model.zipmanifest.ContentList
import org.eclipse.set.model.zipmanifest.Manifest
import org.eclipse.set.model.zipmanifest.Media
import org.eclipse.set.model.zipmanifest.MediaList
import org.eclipse.set.model.zipmanifest.ZipmanifestFactory

/** 
 * Transform a toolbox file into a manifest.
 * 
 * @author Schaefer
 */
class ManifestTransformation {

	def Manifest transform(ZippedPlanProToolboxFile toolboxFile) {
		val manifest = toolboxFile.toManifest
		toolboxFile.toResource.contents.add(manifest)

		return manifest
	}

	private def Resource toResource(ZippedPlanProToolboxFile toolboxFile) {
		val XMLResource resource = toolboxFile.editingDomain.resourceSet.createResource(
			toolboxFile.getManifestUri()) as XMLResource
		resource.encoding = StandardCharsets.UTF_8.name()

		return resource
	}

	private def Manifest create ZipmanifestFactory.eINSTANCE.createManifest
	toManifest(ZippedPlanProToolboxFile toolboxFile) {
		contentList = toolboxFile.toContentList
		mediaList = toolboxFile.toMediaList
		return
	}

	private def ContentList create ZipmanifestFactory.eINSTANCE.createContentList
	toContentList(ZippedPlanProToolboxFile toolboxFile) {
		content.add(toolboxFile.toContent)
		return
	}

	private def MediaList create ZipmanifestFactory.eINSTANCE.createMediaList
	toMediaList(ZippedPlanProToolboxFile toolboxFile) {
		val mediaList = it
		val attachments = toolboxFile.planProResource.allContents.filter(
			typeof(Anhang)
		).toList
		val attachmentGuids = attachments.map [
			identitaet?.wert
		].filterNull.toSet
		val uniqueAttachments = Lists.newLinkedList
		for (a : attachments) {
			if (attachmentGuids.contains(a.identitaet?.wert)) {
				attachmentGuids.remove(a.identitaet?.wert)
				uniqueAttachments.add(a)
			}
		}
		uniqueAttachments.forEach[mediaList.media.add(toMedia)]
		return
	}

	private def Content create ZipmanifestFactory.eINSTANCE.createContent
	toContent(ZippedPlanProToolboxFile toolboxFile) {
		name = toolboxFile.modelPath.fileName.toString
		type = "text/xml"
		return
	}

	private def Media create ZipmanifestFactory.eINSTANCE.createMedia
	toMedia(Anhang attachment) {
		guid = attachment?.identitaet?.wert
		type = attachment?.anhangAllg?.dateityp?.wert.toType
		return
	}

	private def String toType(ENUMDateityp type) {
		switch (type) {
			case ENUM_DATEITYP_JPG: {
				return "image/jpeg"
			}
			case ENUM_DATEITYP_MPEG: {
				return "image/mpeg"
			}
			case ENUM_DATEITYP_PDF: {
				return "application/pdf"
			}
			case ENUM_DATEITYP_PNG: {
				return "image/png"
			}
			case ENUM_DATEITYP_TIF: {
				return "image/tiff"
			}
			default: {
				throw new IllegalArgumentException('''Unexpected attachment type: «type.toString»''')
			}
		}
	}
}
