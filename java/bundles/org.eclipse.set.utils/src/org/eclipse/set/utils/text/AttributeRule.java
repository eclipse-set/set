/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import com.google.common.collect.Lists;

/**
 * A rule to detect attributes within tags.
 * 
 * @author Schaefer
 */
public class AttributeRule implements IRule {

	private static boolean findEquals(final ICharacterScanner scanner) {
		int i = 0;
		for (int c = scanner.read(); c != '='; c = scanner.read()) {
			i++;
			if (isBreak((char) c)) {
				for (int j = 0; j < i; j++) {
					scanner.unread();
				}
				return false;
			}
		}
		scanner.unread();
		return true;
	}

	@SuppressWarnings("boxing") // keep the expressions readable
	private static boolean isBreak(final char c) {
		return Lists.newArrayList('"', '\'', '>').contains(c);
	}

	private final IToken successToken;

	private final TagRule tagRule;

	/**
	 * @param tagRule
	 *            the tag rule for testing open tags
	 * @param successToken
	 *            the success token
	 */
	public AttributeRule(final TagRule tagRule, final IToken successToken) {
		this.tagRule = tagRule;
		this.successToken = successToken;
	}

	@Override
	public IToken evaluate(final ICharacterScanner scanner) {
		if (!tagRule.isOpen()) {
			return Token.UNDEFINED;
		}
		final int read = scanner.read();
		if (Character.isAlphabetic(read)) {
			if (findEquals(scanner)) {
				return successToken;
			}
		}
		scanner.unread();
		return Token.UNDEFINED;
	}
}
