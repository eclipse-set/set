/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import com.google.common.collect.Lists
import org.eclipse.set.model.planpro.Basisobjekte.Anhang
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjektePackage
import org.eclipse.set.model.planpro.Basisobjekte.ENUMAnhangArt
import org.eclipse.set.basis.DomainElementList
import org.eclipse.set.basis.attachments.Attachment
import org.eclipse.set.basis.attachments.AttachmentInfo
import org.eclipse.set.basis.attachments.FileKind
import org.eclipse.set.basis.files.AttachmentContentService
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.ppmodel.extensions.internal.AttachmentProxyList
import java.io.IOException
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.SetCommand
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.xtend.lib.annotations.Accessors

import static org.eclipse.set.model.planpro.Basisobjekte.ENUMDateityp.*

/**
 * This class extends {@link Anhang}.
 */
class AnhangTransformation {

	final EnumTranslationService translationService
	final AttachmentContentService contentService

	static def AnhangTransformation createTransformation(
		EnumTranslationService translationService,
		AttachmentContentService contentService
	) {
		return new AnhangTransformation(translationService, contentService)
	}

	private new(
		EnumTranslationService translationService,
		AttachmentContentService contentService
	) {
		this.translationService = translationService
		this.contentService = contentService
	}

	private static class AnhangProxy implements Attachment {

		@Accessors
		Anhang anhang

		@Accessors
		AnhangTransformation transformation

		override getBaseFilename() {
			return anhang?.anhangAllg?.dateiname?.wert
		}

		override getData() {
			try {
				return transformation.contentService.getContent(
					anhang
				)
			} catch (IOException e) {
				throw new RuntimeException(e)
			}
		}

		override getFileExtension() {
			return anhang?.anhangAllg?.dateityp?.wert?.literal
		}

		override getFileKind() {
			return transformation.toFileKind(anhang)
		}

		override getFullFilename() {
			return '''«baseFilename».«fileExtension»'''
		}

		override getId() {
			return anhang.identitaet.wert
		}

		override isPdf() {
			return anhang?.pdf ?: false
		}

		override setFileKind(FileKind fileKind) {
			val editingDomain = AdapterFactoryEditingDomain.
				getEditingDomainFor(anhang)
			val Command command = SetCommand.create(editingDomain,
				anhang.anhangAllg.anhangArt,
				BasisobjektePackage.eINSTANCE.anhang_Art_TypeClass_Wert,
				ENUMAnhangArt.get(fileKind.id))
			editingDomain.getCommandStack().execute(command)
		}

		override getOriginal() {
			return anhang
		}
	}

	/**
	 * @param anhaenge the PlanPro Anhänge
	 * 
	 * @return the table attachments
	 */
	def List<Attachment> toAttachment(List<Anhang> anhaenge) {
		val attachments = Lists.newLinkedList
		attachments.addAll(anhaenge.map[toAttachment])
		return attachments
	}

	/**
	 * @param anhaenge the PlanPro Anhänge
	 * 
	 * @return the table attachments
	 */
	def DomainElementList<Attachment, AttachmentInfo<Attachment>> toAttachment(
		DomainElementList<Anhang, AttachmentInfo<Anhang>> anhaenge
	) {
		return new AttachmentProxyList(anhaenge, translationService,
			contentService)
	}

	/**
	 * @param anhang the Anhang
	 * 
	 * @return the Attachment
	 */
	def Attachment create new AnhangProxy() toAttachment(Anhang anhang) {
		it.anhang = anhang
		it.transformation = this
		return
	}

	/**
	 * @param anhang the Anhang
	 * 
	 * @return the file kind
	 */
	def FileKind toFileKind(Anhang anhang) {
		val enumAnhangArt = anhang?.anhangAllg?.anhangArt?.wert
		return new FileKind(
			enumAnhangArt.value,
			translationService.translate(enumAnhangArt).alternative
		)
	}

	/**
	 * @param anhang this Anhang
	 * 
	 * @return whether this Anhang is of pdf type
	 */
	def static Boolean isPdf(Anhang anhang) {
		return anhang?.anhangAllg?.dateityp?.wert == ENUM_DATEITYP_PDF
	}
}
