/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.about;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.set.basis.ui.VersionInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides the version infomation as a json response
 * 
 * @author Truong
 */
public class VersionInfoServlet extends HttpServlet {

	VersionInfo[] versionInfos;

	/**
	 * @param versionInfos
	 *            version information
	 */
	public VersionInfoServlet(final VersionInfo[] versionInfos) {
		super();
		this.versionInfos = versionInfos;
	}

	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		response.setContentType("application/json"); //$NON-NLS-1$
		response.setStatus(HttpServletResponse.SC_OK);

		try (PrintWriter writer = response.getWriter()) {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(writer,
					this.versionInfos);
		}
	}
}
