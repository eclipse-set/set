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
package org.eclipse.set.swtbot.utils;

import java.util.function.Supplier;

import org.eclipse.set.core.services.font.FontService;
import org.osgi.service.component.annotations.Component;

/**
 * 
 */
@Component(property = "service.ranking:Integer=100")
public class MockFontService implements FontService {
	public static Supplier<Iterable<FopFont>> getFopFontsHandler;

	@Override
	public Iterable<FopFont> getFopFonts() {
		return getFopFontsHandler.get();
	}

}
