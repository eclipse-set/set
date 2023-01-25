/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang
import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang_Art_TypeClass
import org.eclipse.set.toolboxmodel.Basisobjekte.BasisobjekteFactory
import org.eclipse.set.toolboxmodel.Basisobjekte.Dateiname_TypeClass
import org.eclipse.set.toolboxmodel.Basisobjekte.Dateityp_TypeClass
import org.eclipse.set.toolboxmodel.Basisobjekte.ENUMAnhangArt
import org.eclipse.set.toolboxmodel.Basisobjekte.ENUMDateityp
import org.eclipse.set.toolboxmodel.Basisobjekte.Identitaet_TypeClass
import org.eclipse.set.basis.attachments.Attachment
import org.eclipse.set.basis.attachments.AttachmentInfo
import org.eclipse.set.basis.guid.Guid

/**
 * This class extends {@link Attachment}.
 */
class AttachmentExtensions {

	/**
	 * @param attachment this attachment
	 * 
	 * @return a new Anhang
	 */
	def static AttachmentInfo<Anhang> createAnhang(
		AttachmentInfo<Attachment> attachmentInfo) {
		val anhangAllg = BasisobjekteFactory.eINSTANCE.
			createAnhang_Allg_AttributeGroup
		val attachment = attachmentInfo.element
		anhangAllg.anhangArt = attachment.transformToAnhangArt
		anhangAllg.dateiname = attachment.transformToDateiname
		anhangAllg.dateityp = attachment.transformToDateityp

		val info = new AttachmentInfo
		if (attachmentInfo.toolboxFile.hasDetachedAttachments) {
			info.data = attachment.data
		} 
		info.toolboxFile = attachmentInfo.toolboxFile
		info.guidProvider = [Anhang a|Guid.create(a.identitaet.wert)]

		val anhang = BasisobjekteFactory.eINSTANCE.createAnhang
		anhang.identitaet = attachment.transformToIdentitaet
		anhang.anhangAllg = anhangAllg
		info.element = anhang

		return info
	}

	/**
	 * @param attachment this attachment
	 * 
	 * @return the Anhang Art
	 */
	def static ENUMAnhangArt transformToAnhangArtWert(Attachment attachment) {
		var result = ENUMAnhangArt.get(attachment.fileKind.id)
		if (result === null) {
			result = ENUMAnhangArt.ENUM_ANHANG_ART_SONSTIGE
		}
		return result
	}

	/**
	 * @param attachment this attachment
	 * 
	 * @return the Dateityp
	 */
	def static ENUMDateityp transformToDateitypWert(Attachment attachment) {
		val result = ENUMDateityp.get(attachment.fileExtension.toLowerCase)
		if (result === null) {
			throw new IllegalArgumentException('''Unsupported filetype "«attachment.fileExtension»"''')
		}
		return result
	}

	private def static Anhang_Art_TypeClass transformToAnhangArt(
		Attachment attachment) {
		val anhangArt = BasisobjekteFactory.eINSTANCE.createAnhang_Art_TypeClass
		anhangArt.wert = attachment.transformToAnhangArtWert
		return anhangArt
	}

	private def static Dateiname_TypeClass transformToDateiname(
		Attachment attachment) {
		val dateiname = BasisobjekteFactory.eINSTANCE.createDateiname_TypeClass
		dateiname.wert = attachment.baseFilename
		return dateiname
	}

	private def static Dateityp_TypeClass transformToDateityp(
		Attachment attachment) {
		val dateityp = BasisobjekteFactory.eINSTANCE.createDateityp_TypeClass
		dateityp.wert = attachment.transformToDateitypWert
		return dateityp
	}

	private def static Identitaet_TypeClass transformToIdentitaet(
		Attachment attachment) {
		val identitaet = BasisobjekteFactory.eINSTANCE.
			createIdentitaet_TypeClass
		identitaet.wert = attachment.id
		return identitaet
	}
}
