/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.utils;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.util.function.Predicate;

import org.eclipse.set.browser.Browser;
import org.eclipse.swt.SWTException;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBotControl;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

/**
 * Helper widget for the SET Browser
 */
@SWTBotWidget(clasz = Browser.class, preferredName = "browser", referenceBy = {
		ReferenceBy.LABEL })
public class PlanProSWTBotBrowser extends AbstractSWTBotControl<Browser> {
	/**
	 * @param bot
	 *            an swtbot instance
	 * @return the first browser instance to be found
	 */
	public static PlanProSWTBotBrowser get(final SWTBot bot) {
		final Matcher<Browser> matcher = widgetOfType(
				org.eclipse.set.browser.Browser.class);
		return new PlanProSWTBotBrowser(bot.widget(matcher, 0));
	}

	Exception ex = null;

	/**
	 * @param browser
	 *            the browser
	 */
	public PlanProSWTBotBrowser(final Browser browser) {
		this(browser, null);
	}

	/**
	 * @param browser
	 *            the browser
	 * @param description
	 *            a descripion for the object
	 */
	public PlanProSWTBotBrowser(final Browser browser,
			final SelfDescribing description) {
		super(browser, description);
	}

	/**
	 * Evaluates some Javascript
	 * 
	 * @param script
	 *            the Javascript
	 * @return the value returned from Javascript
	 * @throws Exception
	 *             on error
	 */
	public Object evaluate(final String script) throws Exception {
		ex = null;
		final Object result = UIThreadRunnable.syncExec(() -> {
			try {
				return widget.evaluate(script);
			} catch (final Exception e) {
				ex = e;
				return null;
			}
		});
		if (ex != null) {
			throw ex;
		}
		return result;
	}

	/**
	 * Waits for some Javascript to fulfil a condidition
	 * 
	 * @param script
	 *            the script to retrieve data from
	 * @param pred
	 *            the condition which must be matched to continue
	 */
	public void waitJS(final String script, final Predicate<Object> pred) {
		new SWTBot().waitUntil(new DefaultCondition() {

			@Override
			public String getFailureMessage() {
				return "Failed to wait for Javascript expression";
			}

			@Override
			public boolean test() throws Exception {
				try {
					final Object result = evaluate(script);
					return pred.test(result);
				} catch (final SWTException e) {
					return false;
				}
			}

		}, 1000000);

	}
}
