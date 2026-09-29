/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.nattable.utils;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.fop.hyphenation.Hyphenation;
import org.apache.fop.hyphenation.HyphenationTree;

/**
 * 
 */
public class Hyphenator {
	HyphenationTree hyphenation;

	private Hyphenator(final HyphenationTree hyphenationTree) {
		hyphenation = hyphenationTree;
	}

	/**
	 * @param word
	 *            the word to hyphenate
	 * @return the split index
	 */
	public int[] points(final String word) {
		final Hyphenation hyphenate = hyphenation.hyphenate(word, 2, 2);
		return hyphenate == null ? new int[0]
				: hyphenate.getHyphenationPoints();
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Hyphenator createInstance()
			throws IOException, ClassNotFoundException {
		try (var inputStream = Hyphenator.class.getClassLoader()
				.getResourceAsStream("hyph/de.hyp")) {
			final ObjectInputStream objectInputStream = new ObjectInputStream(
					inputStream);
			if (objectInputStream
					.readObject() instanceof final HyphenationTree hyphenationTree) {
				return new Hyphenator(hyphenationTree);
			}
		}
		return null;
	}
}
