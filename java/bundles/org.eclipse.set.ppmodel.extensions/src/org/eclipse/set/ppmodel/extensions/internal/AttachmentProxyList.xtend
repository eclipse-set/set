/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.internal

import org.eclipse.set.model.planpro.Basisobjekte.Anhang
import org.eclipse.set.basis.DomainElementList
import org.eclipse.set.basis.DomainElementList.ChangeListener
import org.eclipse.set.basis.attachments.Attachment
import org.eclipse.set.basis.attachments.AttachmentInfo
import org.eclipse.set.basis.files.AttachmentContentService
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.ppmodel.extensions.AnhangTransformation
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.notify.Notification

import static extension org.eclipse.set.ppmodel.extensions.AttachmentExtensions.*

/**
 * Implements an attachment element list by delegating to the original Anhang
 * list.
 */
class AttachmentProxyList implements DomainElementList<Attachment, AttachmentInfo<Attachment>> {

	val DomainElementList<Anhang, AttachmentInfo<Anhang>> original
	val List<ChangeListener<Attachment>> changeListenerList = new LinkedList
	var ChangeListener<Anhang> originalChangeListener
	val AnhangTransformation transformation

	/**
	 * @param original the original list
	 */
	new(
		DomainElementList<Anhang, AttachmentInfo<Anhang>> original,
		EnumTranslationService translationService,
		AttachmentContentService contentService
	) {
		this.original = original
		this.transformation = AnhangTransformation.
			createTransformation(translationService, contentService)
	}

	override getElements() {
		return transformation.toAttachment(original.elements)
	}

	override add(AttachmentInfo<Attachment> attachmentInfo) {
		original.add(attachmentInfo.createAnhang)
	}

	override addChangeListener(ChangeListener<Attachment> changeListener) {
		if (originalChangeListener === null) {
			originalChangeListener = new ChangeListener<Anhang>() {
				override listChanged(Notification msg) {
					changeListenerList.forEach [
						listChanged(msg)
					]
				}

			}
			original.addChangeListener(originalChangeListener)
		}
		changeListenerList.add(changeListener)
	}

	override removeChangeListener(ChangeListener<Attachment> changeListener) {
		changeListenerList.remove(changeListener)
		if (changeListenerList.empty && originalChangeListener !== null) {
			original.removeChangeListener(originalChangeListener)
			originalChangeListener = null
		}
	}

	override remove(Attachment attachment) {
		val anhaenge = original.elements.filter [
			identitaet.wert == attachment.id
		].toList
		if (anhaenge.size == 1) {
			original.remove(anhaenge.get(0))
		} else {
			throw new IllegalArgumentException('''Attachment «attachment.id» not found.''')
		}
	}

	override getContainer() {
		return original.container
	}

	override getContainingFeature() {
		return original.containingFeature
	}

	override getFeature() {
		return original.feature
	}
}
