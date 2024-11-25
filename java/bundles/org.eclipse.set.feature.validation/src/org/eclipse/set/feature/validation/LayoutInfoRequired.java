/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.validation;

import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.PlanProFileResource;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.validation.CustomValidator;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.osgi.service.component.annotations.Component;

/**
 * 
 * @author mariusheine
 */
@Component(immediate = true, service = CustomValidator.class)
public class LayoutInfoRequired extends AbstractCustomValidator {
	public static final String LAYOUT_VALIDATION_TYPE = "Metainformationen"; //$NON-NLS-1$

	@Override
	public void validate(final ToolboxFile toolboxFile,
			final ValidationResult result, final FileType type) {
		try {
			if (type == FileType.Layout) {
				result.addCustomProblem(new CustomValidationProblemImpl(
						"Layoutinformationen sind vorhanden", //$NON-NLS-1$
						ValidationSeverity.SUCCESS, this.validationType(), null,
						null, null));
			} else if (type == FileType.Model) {
				toolboxFile.openLayout();
				final PlanProFileResource layoutResource = toolboxFile
						.getLayoutResource();
				if (layoutResource == null) {
					result.addCustomProblem(layoutMissing());
				}
			}
		} catch (final Exception ex) {
			result.addCustomProblem(layoutMissing());
		}
	}

	private CustomValidationProblemImpl layoutMissing() {
		return new CustomValidationProblemImpl("Layoutinformationen fehlen", //$NON-NLS-1$
				ValidationSeverity.WARNING, this.validationType(), null, null,
				null);
	}

	@Override
	public String validationType() {
		return LAYOUT_VALIDATION_TYPE;
	}

}
