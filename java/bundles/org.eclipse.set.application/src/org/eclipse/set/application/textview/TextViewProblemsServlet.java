/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.textview;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.module.EMFModule.Feature;
import org.eclipse.set.basis.ProblemMessage;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides the problem data model as a json response
 * 
 * @author Stuecker
 */
public class TextViewProblemsServlet extends HttpServlet {
	/**
	 * Send the problem model as a response to HTTP GET requests
	 * 
	 * Note: This will be executed in a worker thread rather than the main
	 * thread
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		// Get the list of validation problems
		final Iterable<Object> problems = Services.getCacheService()
				.getCache(ToolboxConstants.CacheId.PROBLEM_MESSAGE).values();
		final List<ProblemMessage> messages = new ArrayList<>();
		problems.forEach(problemContainer -> messages
				.addAll((List<ProblemMessage>) problemContainer));

		response.setContentType("application/json;charset=UTF-8"); //$NON-NLS-1$
		response.setStatus(HttpServletResponse.SC_OK);

		// Configure the EMF JSON mapper
		final ObjectMapper mapper = new ObjectMapper();
		final EMFModule module = new EMFModule();
		// Do not serialize type information in JSON
		module.configure(Feature.OPTION_SERIALIZE_TYPE, false);
		// Serialize default attributes
		module.configure(Feature.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		mapper.registerModule(module);
		// Write response
		try (final PrintWriter writer = response.getWriter()) {
			mapper.writerWithDefaultPrettyPrinter().writeValue(writer,
					messages);
		}
	}
}
