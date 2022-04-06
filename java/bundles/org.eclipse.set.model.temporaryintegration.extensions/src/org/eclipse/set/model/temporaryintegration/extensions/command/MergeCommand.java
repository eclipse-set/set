/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration.extensions.command;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.model.simplemerge.Resolution;
import org.eclipse.set.model.simplemerge.SMatch;
import org.eclipse.set.model.simplemerge.SimplemergePackage;
import org.eclipse.set.model.simplemerge.extensions.ResolutionExtensions;
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt;

/**
 * Describes a manual merge of a single match.
 * 
 * @author Schaefer
 */
public class MergeCommand extends CompoundCommand {

	/**
	 * Create the merge command. First the old element (which may be the primary
	 * or secondary element) will be removed from the destination container (if
	 * it is present). Next a copy of the new element will be added to the
	 * destination container (if it is not already present). The replacement
	 * will take place at the given list feature. Finally the new resolution
	 * will be set for the given match.
	 * 
	 * @param domain
	 *            the editing domain
	 * @param destination
	 *            the destination container
	 * @param listFeature
	 *            the list feature of the destination container
	 * @param oldElement
	 *            the old element
	 * @param newElement
	 *            the new element
	 * @param match
	 *            the match
	 * @param resolution
	 *            the new resolution
	 * 
	 * @return the merge command
	 */
	public static MergeCommand create(final EditingDomain domain,
			final EObject destination, final EStructuralFeature listFeature,
			final Ur_Objekt oldElement, final Ur_Objekt newElement,
			final SMatch match, final Resolution resolution) {
		final MergeCommand replaceCommand = new MergeCommand(domain);

		// test if the selection of the resolution changed
		if (ResolutionExtensions.isPrimary(resolution) != ResolutionExtensions
				.isPrimary(match.getResolution())) {
			final List<?> list = (List<?>) destination.eGet(listFeature);
			final int index = list.indexOf(oldElement);
			replaceCommand.remove(oldElement, destination, listFeature);
			replaceCommand.add(newElement, destination, listFeature, index);
		}
		replaceCommand.setResolution(match, resolution);
		return replaceCommand;
	}

	private final EditingDomain domain;

	private MergeCommand(final EditingDomain domain) {
		super(MergeCommand.class.getSimpleName());
		this.domain = domain;
	}

	private void add(final Ur_Objekt newElement, final EObject destination,
			final EStructuralFeature listFeature, final int index) {
		if (newElement != null) {
			if (index >= 0) {
				final Command add = AddCommand.create(domain, destination,
						listFeature, EcoreUtil.copy(newElement), index);
				append(add);
			} else {
				final Command add = AddCommand.create(domain, destination,
						listFeature, EcoreUtil.copy(newElement));
				append(add);
			}
		}
	}

	private void remove(final EObject oldElement, final EObject destination,
			final EStructuralFeature listFeature) {
		if (oldElement != null) {
			final Command remove = RemoveCommand.create(domain, destination,
					listFeature, oldElement);
			append(remove);
		}
	}

	private void setResolution(final SMatch match,
			final Resolution resolution) {
		final Command set = SetCommand.create(domain, match,
				SimplemergePackage.eINSTANCE.getSMatch_Resolution(),
				resolution);
		append(set);
	}
}
