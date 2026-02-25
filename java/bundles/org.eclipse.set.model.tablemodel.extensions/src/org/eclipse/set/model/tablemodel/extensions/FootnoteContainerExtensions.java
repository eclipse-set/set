/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.model.tablemodel.extensions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer;
import org.eclipse.set.model.tablemodel.FootnoteContainer;
import org.eclipse.set.model.tablemodel.FootnoteMetaInformation;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;

/**
 * Extensions for {@link FootnoteContainer}
 * 
 * @author truong
 */
public class FootnoteContainerExtensions {

	/**
	 * @param first
	 *            the first container
	 * @param second
	 *            the second contanier
	 * @return true, if the comment values of two container are same
	 */
	public static boolean isSameFootnotesComment(final FootnoteContainer first,
			final FootnoteContainer second) {
		final List<String> firstComments = getFootnotesComment(first);
		final List<String> secondComments = getFootnotesComment(second);
		if (firstComments.size() != secondComments.size()) {
			return false;
		}

		return firstComments.stream()
				.allMatch(firstComment -> secondComments.stream()
						.anyMatch(firstComment::equals));
	}

	/**
	 * 
	 * @param fc
	 *            the {@link FootnoteContainer}
	 * @return the comments of the container
	 */
	public static List<String> getFootnotesComment(final FootnoteContainer fc) {
		return getFootnotes(fc).stream()
				.map(footnote -> EObjectExtensions
						.getNullableObject(footnote,
								fn -> fn.getBearbeitungsvermerkAllg()
										.getKommentar()
										.getWert())
						.orElse(null))
				.filter(Objects::nonNull)
				.toList();
	}

	/**
	 * 
	 * @param fc
	 *            the {@link FootnoteContainer}
	 * @return the footnotes of the container
	 */
	public static List<Bearbeitungsvermerk> getFootnotes(
			final FootnoteContainer fc) {
		return getFootnoteMetaInformations(fc).stream()
				.map(meta -> meta.getFootnote())
				.toList();
	}

	/**
	 * 
	 * @param fc
	 *            the {@link FootnoteContainer}
	 * @return the footnotes of the container
	 */
	public static List<FootnoteMetaInformation> getFootnoteMetaInformations(
			final FootnoteContainer fc) {
		if (fc == null) {
			return Collections.emptyList();
		}
		return switch (fc) {
			case final SimpleFootnoteContainer simpleContainer -> simpleContainer
					.getFootnotes();
			case final CompareFootnoteContainer compareContainer -> {
				final List<FootnoteMetaInformation> result = new ArrayList<>();
				result.addAll(getFootnoteMetaInformations(
						compareContainer.getNewFootnotes()));
				result.addAll(getFootnoteMetaInformations(
						compareContainer.getOldFootnotes()));
				result.addAll(getFootnoteMetaInformations(
						compareContainer.getUnchangedFootnotes()));
				yield result;
			}
			case final CompareTableFootnoteContainer compareTableContainer -> getFootnoteMetaInformations(
					compareTableContainer.getMainPlanFootnoteContainer());
			default -> Collections.emptyList();
		};
	}

}
