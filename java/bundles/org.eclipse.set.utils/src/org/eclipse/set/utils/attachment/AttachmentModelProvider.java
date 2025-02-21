/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.attachment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.model.planpro.Basisobjekte.Anhang;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;

import com.google.common.collect.Iterables;

/**
 * Provides a list of attachments as model for the tableviewer.
 * 
 * @author bleidiessel
 *
 */
public class AttachmentModelProvider {
	private final List<Anhang> domainAttachmentsFinal;
	private final List<Anhang> domainAttachmentsInitial;
	private final List<Anhang> domainAttachmentsSingle;
	private final List<Anhang> objectManagementAttachments;

	/**
	 * Construct the provider.
	 * 
	 * @param session
	 *            the current session
	 */
	public AttachmentModelProvider(final IModelSession session) {
		objectManagementAttachments = new ArrayList<>();
		domainAttachmentsInitial = new ArrayList<>();
		domainAttachmentsFinal = new ArrayList<>();
		domainAttachmentsSingle = new ArrayList<>();
		final MultiContainer_AttributeGroup initialContainer = session
				.getContainer(ContainerType.INITIAL);
		final MultiContainer_AttributeGroup finalContainer = session
				.getContainer(ContainerType.FINAL);
		final MultiContainer_AttributeGroup singleContainer = session
				.getContainer(ContainerType.SINGLE);

		final Iterator<EObject> allContents = session.getPlanProSchnittstelle()
				.eAllContents();
		for (EObject eObject = allContents.next(); allContents
				.hasNext(); eObject = allContents.next()) {
			if (eObject instanceof final Anhang attachment) {
				final EObject parent = attachment.eContainer();

				// if a attachment is part of a container, then it is a domain
				// attachment
				if (parent instanceof Container_AttributeGroup) {
					if (initialContainer != null && Iterables.contains(
							initialContainer.getContainers(), parent)) {
						addAttachment(domainAttachmentsInitial, attachment);
					} else if (finalContainer != null && Iterables
							.contains(finalContainer.getContainers(), parent)) {
						addAttachment(domainAttachmentsFinal, attachment);
					} else if (singleContainer != null && Iterables.contains(
							singleContainer.getContainers(), parent)) {
						addAttachment(domainAttachmentsSingle, attachment);
					}
				} else {
					addAttachment(objectManagementAttachments, attachment);
				}
			}

		}

	}

	private static void addAttachment(final List<Anhang> list,
			final Anhang attachment) {
		// Filter same attachment
		if (list.stream()
				.anyMatch(ele -> ele.getIdentitaet()
						.getWert()
						.equals(attachment.getIdentitaet().getWert()))) {
			return;
		}
		list.add(attachment);
	}

	/**
	 * @return the list of all domain attachments (final)
	 */
	public List<Anhang> getDomainAttachmentsFinal() {
		return domainAttachmentsFinal;
	}

	/**
	 * @return the list of all domain attachments (initial)
	 */
	public List<Anhang> getDomainAttachmentsInitial() {
		return domainAttachmentsInitial;
	}

	/**
	 * @return the list of all domain attachments (single)
	 */
	public List<Anhang> getDomainAttachmentsSingle() {
		return domainAttachmentsSingle;
	}

	/**
	 * @return the list of all object management attachments
	 */
	public List<Anhang> getObjectManagementAttachments() {
		return objectManagementAttachments;
	}
}
