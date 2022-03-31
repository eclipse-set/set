/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.nio.file.Path;
import java.util.function.Function;

import org.eclipse.set.basis.exceptions.NotWritable;
import org.eclipse.set.basis.extensions.PathExtensions;

/**
 * Description of what to do when overwriting files.
 * 
 * @author schaefer
 */
public class OverwriteHandling {

	private static enum Method {
		/**
		 * Ask the user for each file to be overwritten.
		 */
		ASK_USER,

		/**
		 * Never overwrite and do not ask the user.
		 */
		NO_OVERWRITE,

		/**
		 * Always overwrite and do not ask the user.
		 */
		OVERWRITE
	}

	/**
	 * Creates an overwrite handling which always or never overwrites files
	 * (depending on the given selection) and never asks the user.
	 * 
	 * @param selection
	 *            whether to overwrite files or not
	 * 
	 * @return the new overwrite handling
	 */
	public static OverwriteHandling forCheckbox(final boolean selection) {
		return new OverwriteHandling(
				selection ? Method.OVERWRITE : Method.NO_OVERWRITE, null);
	}

	/**
	 * Creates an overwrite handling which always asks the user before
	 * overwriting files.
	 * 
	 * @param overwriteConfirmation
	 *            the overwrite confirmation
	 * 
	 * @return the new overwrite handling
	 */
	public static OverwriteHandling forUserConfirmation(
			final Function<Path, Boolean> overwriteConfirmation) {
		return new OverwriteHandling(Method.ASK_USER, overwriteConfirmation);
	}

	private final Method method;

	private final Function<Path, Boolean> overwriteConfirmation;

	private OverwriteHandling(final Method method,
			final Function<Path, Boolean> overwriteConfirmation) {
		this.method = method;
		this.overwriteConfirmation = overwriteConfirmation;
	}

	/**
	 * Execute the overwrite handling including any tests whether overwriting
	 * applies.
	 * 
	 * @param path
	 *            the path to test
	 * 
	 * @return whether overwriting is permitted
	 * 
	 * @throws NotWritable
	 *             if overwriting is permitted, but the file is not writable
	 */
	public boolean test(final Path path) throws NotWritable {
		if (!path.toFile().exists()) {
			// we do not need a confirmation
			return true;
		}
		switch (method) {
		case NO_OVERWRITE:
			// overwriting is not permitted
			return false;
		case OVERWRITE:
			// test if the file is writable
			PathExtensions.checkCanWrite(path);
			// overwriting is permitted
			return true;
		case ASK_USER:
			return askUser(path);

		default:
			throw new IllegalArgumentException(method.toString());
		}
	}

	private boolean askUser(final Path path) throws NotWritable {
		final boolean confirmed = overwriteConfirmation.apply(path)
				.booleanValue();
		if (confirmed) {
			// test if the file is writable
			PathExtensions.checkCanWrite(path);
		}
		return confirmed;
	}
}
