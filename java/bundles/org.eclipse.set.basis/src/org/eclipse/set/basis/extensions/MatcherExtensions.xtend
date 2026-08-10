/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import com.google.common.collect.Lists
import java.util.List
import java.util.Optional
import java.util.regex.Matcher
import org.eclipse.jface.text.IRegion
import org.eclipse.jface.text.Region

/**
 * Extensions for {@link Matcher}.
 * 
 * @author Schaefer
 */
class MatcherExtensions {

	/**
	 * @param matcher the matcher
	 * @param document the document
	 * @param region the region the matcher is applied to
	 * 
	 * @return the list of matching regions
	 */
	static def List<IRegion> getAllMatches(Matcher matcher, IRegion region) {
		val result = Lists.newLinkedList
		while (matcher.find) {
			result.add(
				new Region(
					region.offset + matcher.start,
					matcher.end - matcher.start
				)
			)
		}
		return result
	}
	
	static def Optional<String> getGroup(Matcher matcher, String groupName) {
		try {
			if (!matcher.matches) {
				return Optional.empty
			}
			val value = matcher.group(groupName);
			return Optional.ofNullable(value);
		} catch (IllegalStateException e) {
			return Optional.empty
		}
	}
}
