/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot;

import static org.junit.Assert.assertEquals;

import org.eclipse.set.swtbot.utils.AbstractPPHNTest;
import org.eclipse.set.swtbot.utils.PlanProSWTBotBrowser;
import org.junit.jupiter.api.Test;

public class TextViewTest extends AbstractPPHNTest {
	private static String jsGetTextContent = """
				return document.getElementsByClassName("view-lines")[0].children[0].textContent.replace(/\u00a0/g, " ");
			""";

	@Override
	public String getReferenceDir() {
		return null;
	}

	@Test
	void testTextViewContent() throws Exception {
		bot.button("Datei in Textsicht").click();
		final var browser = PlanProSWTBotBrowser.get(bot);
		browser.waitJS(jsGetTextContent, x -> {
			final String content = (String) x;
			return content != null && !content.isBlank()
					&& !content.contains("Wird geladen...");
		});
		final Object text = browser.evaluate(jsGetTextContent);
		assertEquals("Expected loaded file",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>", text);
	}
}
