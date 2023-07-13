/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

/**
 * A problem message indicating an issue with the supplied planning
 * 
 * @param message
 *            the message
 * @param type
 *            the source of the problem
 * @param line
 *            the line
 * @param severity
 *            the severity (1-4) of the problem
 * @param model
 *            the model container of this problem
 */
public record ProblemMessage(String message, String type, int line,
		int severity, String model) {
	//
}