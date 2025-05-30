/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.modelloader;

import java.util.function.Consumer;

import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.swt.widgets.Shell;

/**
 * Manage UI oriented loading of a PlanPro Model.
 * 
 * @author Schaefer
 */
public interface ModelLoader {

	/**
	 * Contains all contents in planpro file
	 * 
	 * @param schnittStelle
	 *            the {@link PlanPro_Schnittstelle}
	 * @param layoutInfo
	 *            the {@link PlanPro_Layoutinfo}
	 */
	public record ModelContents(PlanPro_Schnittstelle schnittStelle,
			PlanPro_Layoutinfo layoutInfo) {

		/**
		 * @return true, when given't any data
		 */
		public boolean isEmpty() {
			return schnittStelle == null && layoutInfo == null;
		}
	}

	/**
	 * @param toolboxFile
	 *            the toolbox file to load the model from
	 * @param storeValidationResult
	 *            the consumer to consume the validation result
	 * @return whether the model was loaded (and stored) successfully
	 */
	public ModelContents loadModel(ToolboxFile toolboxFile,
			Consumer<ValidationResult> storeValidationResult);

	/**
	 * @param toolboxFile
	 *            the toolbox file to load the model from
	 * @param storeValidationResult
	 *            the consumer to consume the validation result
	 * @param shell
	 *            the shell
	 * @return whether the model was loaded (and stored) successfully
	 */
	public ModelContents loadModelSync(ToolboxFile toolboxFile,
			Consumer<ValidationResult> storeValidationResult, Shell shell);
}
