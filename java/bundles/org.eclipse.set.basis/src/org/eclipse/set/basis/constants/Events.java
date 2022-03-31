/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants;

/**
 * Provides constants for events.
 * 
 * @author Schaefer
 */
// Idea: Use map as data. Compare
// http://www.vogella.com/tutorials/Eclipse4EventSystem/article.html
// 2.7. Which objects should be send out?
@SuppressWarnings("nls")
public interface Events {

	/**
	 * The container in focus of the model session changed.
	 * <ul>
	 * <li><b>DATA:</b> the new PlanPro Schnittstelle</li>
	 * </ul>
	 */
	String MODEL_CHANGED = "modelsession/change/model";

	/**
	 * The model session changed.
	 */
	String SESSION_CHANGED = "modelsession/change/*";

	/**
	 * new model created
	 * <ul>
	 * <li><b>DATA:</b> the new table type</li>
	 * </ul>
	 */
	String TABLETYPE_CHANGED = "modelsession/change/tabletype";
}
