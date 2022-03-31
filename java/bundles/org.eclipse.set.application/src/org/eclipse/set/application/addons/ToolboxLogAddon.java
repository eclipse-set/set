/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.addons;

import java.io.PrintStream;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.Assert;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * Toolbox specific logging.
 * 
 * @author Schaefer
 */
public class ToolboxLogAddon {

	private static final String CONSOLE_APPENDER_NAME = "CONSOLE"; //$NON-NLS-1$

	private static final String FILE_APPENDER_NAME = "FILE"; //$NON-NLS-1$

	static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(ToolboxLogAddon.class);

	private static void enableFilelog(final String logfile) {
		final LoggerContext lc = (LoggerContext) LoggerFactory
				.getILoggerFactory();
		StatusPrinter.print(lc);
		final Logger root = lc.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
		final ConsoleAppender<ILoggingEvent> console = (ConsoleAppender<ILoggingEvent>) root
				.getAppender(CONSOLE_APPENDER_NAME);
		final FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
		fileAppender.setContext(lc);
		fileAppender.setName(FILE_APPENDER_NAME);
		fileAppender.setFile(logfile);
		fileAppender.setEncoder(console.getEncoder());
		fileAppender.start();
		Assert.isTrue(root.detachAppender(CONSOLE_APPENDER_NAME));
		root.addAppender(fileAppender);
		StatusPrinter.print(lc);
	}

	@PostConstruct
	private static void postConstruct() {
		final String logfile = ToolboxConfiguration.getToolboxLogfile();
		if (logfile != null && !logfile.isEmpty()) {
			enableFilelog(logfile);
			redirectToLog();
		}
	}

	private static void redirectToLog() {
		logger.info("redirect stdout and stderr to logfile..."); //$NON-NLS-1$
		System.setOut(new PrintStream(System.out) {
			@Override
			public void print(final String s) {
				logger.info(s);
			}
		});
		System.setErr(new PrintStream(System.err) {
			@Override
			public void print(final String s) {
				logger.error(s);
			}
		});
		System.out.println("done."); //$NON-NLS-1$
	}
}
