/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.tika.Tika;
import org.eclipse.set.browser.RequestHandler;
import org.eclipse.swt.widgets.Composite;

/**
 * WebBrowser with a minimal handler for serving local files via the built-in
 * http scheme handler
 *
 */
public class FileWebBrowser extends WebBrowser implements RequestHandler {
	/**
	 * Functional helper for a request-response consumer pair
	 */
	@FunctionalInterface
	public interface RequestCallback {
		/**
		 * @param request
		 *            request
		 * @param response
		 *            response
		 * @throws Exception
		 *             on failure
		 */
		void accept(Request request, Response response) throws Exception;
	}

	/**
	 * Functional helper for a response-only consumer
	 */
	@FunctionalInterface
	public interface SimpleRequestCallback {
		/**
		 * @param response
		 *            response
		 * @throws Exception
		 *             on failure
		 */
		void accept(Response response) throws Exception;
	}

	private static final String HOSTNAME = "toolbox"; //$NON-NLS-1$
	private static final String BASE_URI = "https://" + HOSTNAME + "/"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final Tika tika = new Tika();

	private final Map<String, RequestCallback> prefixUrlHandler = new HashMap<>();

	/**
	 * @param parent
	 *            Parent composite
	 */
	public FileWebBrowser(final Composite parent) {
		super(parent);

		getBrowser().registerRequestHandler(HOSTNAME, this);
	}

	/**
	 * Browses to the local scheme handler implementation
	 * 
	 * @param routePath
	 *            the local route path
	 */
	public void setToolboxUrl(final String routePath) {
		super.setUrl(BASE_URI + routePath);
	}

	@Override
	protected List<String> getAllowedPrefixes() {
		return List.of(BASE_URI, "chrome://"); //$NON-NLS-1$
	}

	@Override
	public void onRequest(final Request request, final Response response)
			throws Exception {
		final String uri = request.getURL().substring(BASE_URI.length());

		// Decode Uri to resolve to a regular path
		final String decoded = URLDecoder.decode(uri,
				StandardCharsets.UTF_8.name());

		for (final Map.Entry<String, RequestCallback> entry : prefixUrlHandler
				.entrySet()) {
			if (decoded.startsWith(entry.getKey())) {
				entry.getValue().accept(request, response);
				return;
			}
		}
		response.setMimeType("text/plain"); //$NON-NLS-1$
		response.setResponseData("404 - Page not found!"); //$NON-NLS-1$
		response.setStatus(404);
	}

	/**
	 * @param uri
	 *            uri to serve
	 * @param mime
	 *            mime type
	 * @param file
	 *            file to serve
	 */
	public void serveFile(final String uri, final String mime,
			final Path file) {
		serveUri(uri, response -> {
			response.setMimeType(mime);
			response.setStatus(200);
			// Response takes ownership of the resource
			@SuppressWarnings("resource")
			final InputStream stream = Files.newInputStream(file);
			response.setResponseData(stream);
		});
	}

	/**
	 * @param uri
	 *            uri to serve
	 * @param callback
	 *            callback to serve with
	 */
	public void serveUri(final String uri, final RequestCallback callback) {
		prefixUrlHandler.put(uri, callback);
	}

	/**
	 * @param uri
	 *            uri to serve
	 * @param callback
	 *            callback to serve with
	 */
	public void serveUri(final String uri,
			final SimpleRequestCallback callback) {
		prefixUrlHandler.put(uri, (req, resp) -> callback.accept(resp));
	}

	/**
	 * @param uri
	 *            URI to serve from
	 * @param directory
	 *            directory to serve
	 * @throws IOException
	 *             if the directory cannot be accessed
	 */
	public void serveDirectory(final String uri, final Path directory)
			throws IOException {
		try (Stream<Path> files = Files.list(directory)) {
			for (final Path file : files.toList()) {
				final String filename = file.getFileName().toString();
				final String fileuri = uri.length() != 0 ? uri + "/" + filename //$NON-NLS-1$
						: filename;
				if (Files.isDirectory(file)) {
					serveDirectory(fileuri, file);
				}

				else {
					final Path filepath = file.toAbsolutePath();
					final String contentType = tika.detect(filepath.toString());
					serveFile(fileuri, contentType, filepath);

				}

			}
		}

	}

	/**
	 * Serves a directory from the root uri
	 * 
	 * @param directory
	 *            directory to serve
	 * @throws IOException
	 *             if the directory cannot be accessed
	 */
	public void serveRootDirectory(final Path directory) throws IOException {
		serveDirectory("", directory); //$NON-NLS-1$
	}

	/**
	 * @param path
	 *            path to encode
	 * @return html encoded path
	 */
	@SuppressWarnings("nls")
	public static String encodePath(final String path) {
		try {
			return URLEncoder.encode(path, "UTF-8").replace("\\+", "%20");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
